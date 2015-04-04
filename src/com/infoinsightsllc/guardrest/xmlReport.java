package com.infoinsightsllc.guardrest;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.infoinsightsllc.guardJSON.GuardiumRestController;

/**
 * Servlet implementation class xmlReport
 */
@WebServlet("/xmlReport")
public class xmlReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public xmlReport() {
        super();
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter writer = response.getWriter();
		
		ServletContext context = request.getSession().getServletContext();
		
		String clientID = context.getInitParameter("client_id");
		String clientSecret = context.getInitParameter("client_secret");
		String guiUser = context.getInitParameter("gui_user");
		String guiPassword = context.getInitParameter("gui_password");
		String sqlguardAppl = context.getInitParameter("sqlguard_appl");
		String sqlguardPort = context.getInitParameter("sqlguard_port");

		
		GuardiumRestController rc = new GuardiumRestController(sqlguardAppl, Integer.parseInt(sqlguardPort), clientID, clientSecret, guiUser, guiPassword);
		
		try 
		{
			rc.connectAndGetToken();
			int numResults = 1;
			String reportResults = rc.getReportResults(request.getParameterMap(), numResults);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			response.setContentType("text/xml;charset=UTF-8");
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<dataset xmlns=\"http://developer.cognos.com/schemas/xmldata/1/\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema-instance\">");
			
			boolean metadataPrinted = false;
			
			SimpleTypeMapper mapper = new SimpleTypeMapper();
			
			while(!reportResults.contains("\"ID\": 0"))
			{
				@SuppressWarnings("unchecked")
				LinkedTreeMap<String, String>[] treeArray = gson.fromJson(reportResults, LinkedTreeMap[].class);
				
				for(int i = 0; i < treeArray.length; i++)
				{
					LinkedTreeMap<String, String> tree = treeArray[i];
					
					if(!metadataPrinted)
					{
						writer.println("  <metadata>");
						for (Map.Entry<String, String> keyValuePair : tree.entrySet())
						{
							XMLTypeFormatter formatter = mapper.getFormatter(keyValuePair.getKey());
							writer.println("   <item name=\"" + keyValuePair.getKey() + 
								"\" " + formatter.getType() + "/>");
						}				
						writer.println("  </metadata>");

						writer.println("  <data>");
						metadataPrinted= true;
					}
					
					writer.print("    <row>");
					for (Map.Entry<String, String> keyValuePair : tree.entrySet())
					{
						XMLTypeFormatter formatter = mapper.getFormatter(keyValuePair.getKey());
						writer.print("<value>");
						
						String value = formatter.getFormattedValue(keyValuePair.getValue());
						writer.print(value);
						writer.print("</value>");
					}					
					writer.println("</row>");
				}
				
				numResults = numResults + 20;
				reportResults = rc.getReportResults(request.getParameterMap(), numResults);

			}
			
			writer.println("  </data>");
			writer.println("</dataset>");
		} 
		catch (KeyManagementException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

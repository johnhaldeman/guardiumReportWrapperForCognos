package com.infoinsightsllc.guardJSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

public class GuardiumRestController 
{
	private String strURL;
	private String clientID;
	private String secret;
	private String guiUser;
	private String guiPassword;
	private int port;
	private GuardJSONToken token;
		
	public GuardiumRestController(String strURL, int port, String clientID, String secret, String guiUser, String guiPassword)
	{
		this.strURL = strURL;
		this.clientID = clientID;
		this.secret = secret;
		this.guiUser = guiUser;
		this.guiPassword = guiPassword;
		this.port = port;
	}
	
	public void connectAndGetToken() throws IOException, KeyManagementException, NoSuchAlgorithmException
	{
		URL url = new URL("https", strURL, port, "/oauth/token");		
		HttpsURLConnection conn = this.getConnection(url);
		
		String parameters = "client_id=" + clientID;
		parameters += "&client_secret=" + secret;
		parameters += "&grant_type=" + "password";
		parameters += "&username=" + guiUser;
		parameters += "&password=" + URLEncoder.encode(guiPassword, "UTF-8");
		
		conn.setRequestMethod("POST");
		
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write(parameters);
		writer.flush();
		
		Gson gson = new Gson();
		
		BufferedReader br = getConnBufferedReader(conn);
		GuardJSONToken jsonToken = gson.fromJson(br, GuardJSONToken.class);
		
		token = jsonToken;
		writer.close();
	}
	
	private BufferedReader getConnBufferedReader(HttpsURLConnection conn) throws IOException
	{
		BufferedReader br = 
				new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
		
		return br;
	}
	
	
	public String getWholeReturnedMessage(HttpsURLConnection conn)
			throws IOException {
		BufferedReader br = getConnBufferedReader(conn);
		 
		StringBuffer msgFromServer = new StringBuffer();
		String readString = "";

		while ((readString = br.readLine()) != null) 
		{
			msgFromServer.append(readString);
		}
		br.close();
		return msgFromServer.toString();
	}
	

	private static SSLSocketFactory getTrustingTrustManager() throws NoSuchAlgorithmException, KeyManagementException {
         // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() 
            {
                return null;
            }

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
			}
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        return sc.getSocketFactory();
	}
	
	private static HostnameVerifier getTrustingHostnameVerifier() {
		// Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {

			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
        };
        
        return allHostsValid;
        
	}

	public HttpsURLConnection getConnection(URL url) throws IOException, KeyManagementException, NoSuchAlgorithmException 
	{
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setSSLSocketFactory(getTrustingTrustManager());
			conn.setHostnameVerifier(getTrustingHostnameVerifier());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		return conn;
	}

	public String getToken() 
	{
		return token.getAccess_token();
	}

	
	public String getReportResults(Map<String, String[]> params, int startAt) throws KeyManagementException, NoSuchAlgorithmException, IOException
	{
		Gson gson = new Gson();
		
		LinkedTreeMap<String, Object> tm = new LinkedTreeMap<String, Object>();
		tm.put("indexFrom", Integer.toString(startAt));
		
		LinkedTreeMap<String, Object> tm2 = new LinkedTreeMap<String, Object>();
		
		for (Map.Entry<String, String[]> param : params.entrySet())
		{
			if(param.getKey().equals("reportName"))
				tm.put("reportName", params.get("reportName")[0]);
			else
				tm2.put(param.getKey(), param.getValue()[0]);
		}
		
		tm.put("reportParameter", tm2);
		
		
		URL url = new URL("https", strURL, port, "/restAPI/online_report");
		HttpsURLConnection conn = getConnection(url);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type" , "application/json");
		conn.setRequestProperty("Authorization" , "Bearer " + getToken());
		
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		
		writer.write(gson.toJson(tm));
		writer.flush();
		
		String currResults = getWholeReturnedMessage(conn);
		
		return currResults;
		
	}
	
}

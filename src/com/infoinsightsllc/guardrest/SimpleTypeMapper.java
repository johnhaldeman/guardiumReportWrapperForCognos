package com.infoinsightsllc.guardrest;

import java.util.HashMap;

public class SimpleTypeMapper 
{
	HashMap<String, XMLTypeFormatter> typeMap;
	
	public SimpleTypeMapper()
	{
		typeMap = new HashMap<String, XMLTypeFormatter>();
		
		typeMap.put("Access Rule Description",new XMLTypeFormatter(XMLTypeFormatter.STRING, 1024));
		typeMap.put("Full Sql", new XMLTypeFormatter(XMLTypeFormatter.STRING, 4096));
		typeMap.put("Full SQL String", new XMLTypeFormatter(XMLTypeFormatter.STRING, 4096));
		typeMap.put("Sql", new XMLTypeFormatter(XMLTypeFormatter.STRING, 4096));
		typeMap.put("SQL string that caused the Exception", new XMLTypeFormatter(XMLTypeFormatter.STRING, 4096));
		typeMap.put("Ack Response Time", new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Average Execution Time",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Avg Execution Ack Time",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Avg Records Affected",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("DATA SET Records Deleted",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("DATA SET Records Inserted",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("DATA SET Records Retrieved",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("DATA SET Records Updated",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Duration (secs)", new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Event Date",new XMLTypeFormatter(XMLTypeFormatter.TIMESTAMP));
		typeMap.put("Event Release Date",new XMLTypeFormatter(XMLTypeFormatter.TIMESTAMP));
		typeMap.put("Exception Timestamp",new XMLTypeFormatter(XMLTypeFormatter.TIMESTAMP));
		typeMap.put("Failed Sqls",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Last Used",new XMLTypeFormatter(XMLTypeFormatter.TIMESTAMP));
		typeMap.put("Returned Data Count",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Session End",new XMLTypeFormatter(XMLTypeFormatter.TIMESTAMP));
		typeMap.put("Session Start",new XMLTypeFormatter(XMLTypeFormatter.TIMESTAMP));
		typeMap.put("Successful Sqls",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Timestamp",new XMLTypeFormatter(XMLTypeFormatter.TIMESTAMP));
		typeMap.put("Total Occurrences",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Total Records Affected",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
		typeMap.put("Records Affected",new XMLTypeFormatter(XMLTypeFormatter.INTEGER));
	}
	
	public XMLTypeFormatter getFormatter(String field)
	{
		XMLTypeFormatter formatter = typeMap.get(field);
		
		System.out.println("Looking up: " + field);
		
		
		if(formatter == null)
		{
			System.out.println("Returning default");
			return new XMLTypeFormatter();
		}
		else
		{
			System.out.println("Found One: " + formatter.getType());
			return formatter;
		}
	}

}

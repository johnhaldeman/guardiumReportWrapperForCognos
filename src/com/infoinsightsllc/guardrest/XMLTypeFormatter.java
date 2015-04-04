package com.infoinsightsllc.guardrest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XMLTypeFormatter 
{
	private int type;
	private int length;
	public static final int STRING = 1;
	public static final int TIMESTAMP = 2;
	public static final int INTEGER = 3;
	
	public XMLTypeFormatter(int type, int length)
	{
		System.out.println("setting type" + type);
		this.type = type;
		this.length = length;
	}
	
	public XMLTypeFormatter(int type)
	{
		this(type, 255);
	}
	
	public XMLTypeFormatter()
	{
		this(STRING, 255);
	}


	public String getType()
	{
		System.out.println("type: " + type);
		if(type == STRING)
		{
			return "type=\"xs:string\" length=\""+ length + "\"";
		}
		else if(type == TIMESTAMP)
		{
			return "type=\"xs:dateTime\"";
		}
		else if(type == INTEGER)
		{
			return "type=\"xs:int\"";
		}
		else
		{
			return "type=\"xs:string\" length=\""+ length + "\"";
		}
	}
	
	public String getFormattedValue(String value)
	{
		if(type == STRING)
		{
			return value;
		}
		else if(type == TIMESTAMP)
		{
			Date date;
			try 
			{
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(value);
				String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String timeString = new SimpleDateFormat("HH:mm:ss").format(date);
				return dateString + "T" + timeString;
			} 
			catch (ParseException e) 
			{
				return value;
			}
		}
		else if(type == INTEGER)
		{
			return  value;
		}
		else
		{
			return  value;
		}
	}
	
}

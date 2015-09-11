package com.citadel.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CitadelDateUtil {
	
	public  static Date convertStringToDate(String userdate){
		Date date=new Date();
		String expectedPattern = "yyyy/MM/dd";
	    SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
	    try
	    {
	      date = formatter.parse(userdate);
	     // System.out.println(date);
	    }
	    catch (ParseException e)
	    {
	      e.printStackTrace();
	    }
	    return date;
		
	}
	
	public  static String convertDateToString(Date userdate){
		String date="";
		String expectedPattern = "yyyy-MM-dd";
	    SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
	    try
	    {
	    date = formatter.format(userdate);
	     // System.out.println(date);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return date;
		
	}
	
	public static void main(String[] args) {
		CitadelDateUtil.convertStringToDate("2015/01/23");
		//CitadelDateUtil.
	}
	

}

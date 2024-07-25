package com.project.test.util;

import java.util.Calendar;
import java.util.Date;

public class AppUtil {
	
	public static String generateRandomTicketNo() throws Exception {
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		
		int min = 0;
	    int max = Integer.MAX_VALUE;
	    int randomInt = (int)Math.floor(Math.random()*(max-min+1)+min);
	    String result = "SA-".concat(year).concat(month).concat(day)+randomInt;
	    return result;
	}
}

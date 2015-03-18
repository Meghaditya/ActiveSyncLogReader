package com.meghaditya.keyvaluereader;

import com.meghaditya.sax.calendar.CalendarParser;

public class Resolver {
	String mKey;
	String mValue;
	
	public Resolver(String key, String value) {
		mKey = key;
		mValue = value;
	}

	public static AbstractParser resolve(String key, String value) {
		AbstractParser parser = null;
		Resolver reolver = new Resolver(key, value);
		
		if (reolver.isContentTypeCalendar()) {
			parser = new CalendarParser();
		} 
		
		return parser;
 	}
	
	private boolean isContentTypeCalendar() {
		return mKey.startsWith("ResponseBody")		// Calendar Rule 1
				&& mValue.contains("<ServerId>1:");	// Calendar Rule 2
	}
}

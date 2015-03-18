package com.meghaditya.sax.calendar;

import com.meghaditya.files.File;

public class CalendarData {
	public static final String	APPLICATION_DATA = "ApplicationData";
	public static final String	TIMEZONE = "TimeZone";
	public static final String	STARTTIME = "StartTime";
	public static final String	ENDTIME = "EndTime";
	public static final String	RECURRENCE = "Recurrence";
	public static final String	RECURRENCE_TYPE = "Type";
	public static final String	RECURRENCE_OCCURRENCES = "Occurrences";
	public static final String	RECURRENCE_INTERVAL = "Interval";
	public static final String	RECURRENCE_WEEKOFMONTH = "WeekOfMonth";
	public static final String	RECURRENCE_DAYOFWEEK = "DayOfWeek";
	public static final String	RECURRENCE_MONTHOFYEAR = "MonthOfYear";
	public static final String	RECURRENCE_UNTIL = "Until";
	public static final String	RECURRENCE_DAYOFMONTH = "DayOfMonth";
	public static final String 	RECURRENCE_CALENDARTYPE = "CalendarType";
	public static final String	RECURRENCE_ISLEAPMONTH = "IsLeapMonth";
	public static final String	RECURRENCE_FIRSTDAYOFWEEK = "FirstDayOfWeek";
	
	private StringBuilder mCalendarData;
	
	public CalendarData() {
		mCalendarData = new StringBuilder();
	}
	
	private void addKeyValuePair(String key, String value) {
		mCalendarData.append(key).append(":").append(value).append('\n');
	}
	
	public void addTimeZone(String timeZone) {
		addKeyValuePair(TIMEZONE, timeZone);
	}
	
	public void addStartTime(String startTime) {
		addKeyValuePair(STARTTIME, startTime);
	}
	
	public void addEndTime(String endTime) {
		addKeyValuePair(ENDTIME, endTime);
	}
	
	public void startRecurrence() {
		addKeyValuePair(RECURRENCE, "starting");
	}

	public void endRecurrence() {
		addKeyValuePair(RECURRENCE, "ending");
	}

	public void addRecurrenceType(String type) {
		addKeyValuePair(RECURRENCE_TYPE, type);
	}
	
	public void addRecurrenceOccurrences(String occurrences) {
		addKeyValuePair(RECURRENCE_OCCURRENCES, occurrences);
	}
	
	public void addRecurrenceInterval(String interval) {
		addKeyValuePair(RECURRENCE_INTERVAL, interval);
	}
	
	public void addRecurrenceWeekOfMonth(String weekOfMonth) {
		addKeyValuePair(RECURRENCE_WEEKOFMONTH, weekOfMonth);
	}
	
	public void addRecurrenceDayOfWeek(String dayOfWeek) {
		addKeyValuePair(RECURRENCE_DAYOFWEEK, dayOfWeek);
	}

	public void addRecurrenceMonthOfYear(String monthOfYear) {
		addKeyValuePair(RECURRENCE_MONTHOFYEAR, monthOfYear);
	}

	public void addRecurrenceUntil(String until) {
		addKeyValuePair(RECURRENCE_UNTIL, until);
	}

	public void addRecurrenceDayOfMonth(String dayOfMonth) {
		addKeyValuePair(RECURRENCE_DAYOFMONTH, dayOfMonth);
	}
	
	public void addRecurrenceCalendarType(String calendarType) {
		addKeyValuePair(RECURRENCE_CALENDARTYPE, calendarType);
	}
	
	public void addRecurrenceIsLeapMonth(String isLeapMonth) {
		addKeyValuePair(RECURRENCE_ISLEAPMONTH, isLeapMonth);
	}
	
	public void addRecurrenceFirstDayOfWeek(String firstDayOfWeek) {
		addKeyValuePair(RECURRENCE_FIRSTDAYOFWEEK, firstDayOfWeek);
	}
	
	public void print() {
		System.out.println(mCalendarData.toString());
	}
	
	public void clear() {
		mCalendarData = File.deleteData(mCalendarData);
	}
}

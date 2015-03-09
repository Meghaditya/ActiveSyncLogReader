package com.citrix.sax.calendar;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class CalendarSaxHandler extends DefaultHandler {
	
	private StringBuilder	mCurrentBuffer = new StringBuilder();
	private CalendarData	mCalendarData = new CalendarData();
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		flushBuffer();
		if (CalendarData.APPLICATION_DATA.equalsIgnoreCase(qName)) {
			mCalendarData.clear();
		} else if (CalendarData.RECURRENCE.equalsIgnoreCase(qName)) {
			mCalendarData.startRecurrence();
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (CalendarData.APPLICATION_DATA.equalsIgnoreCase(qName)) {
			mCalendarData.print();
		} else if (CalendarData.TIMEZONE.equalsIgnoreCase(qName)) {
			mCalendarData.addTimeZone(mCurrentBuffer.toString());
		} else if (CalendarData.STARTTIME.equalsIgnoreCase(qName)) {
			mCalendarData.addStartTime(mCurrentBuffer.toString());
		} else if (CalendarData.ENDTIME.equalsIgnoreCase(qName)) {
			mCalendarData.addEndTime(mCurrentBuffer.toString());			
		} else if (CalendarData.RECURRENCE.equalsIgnoreCase(qName)) {
			mCalendarData.endRecurrence();
		} else if (CalendarData.RECURRENCE_CALENDARTYPE.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceCalendarType(mCurrentBuffer.toString());						
		} else if (CalendarData.RECURRENCE_DAYOFMONTH.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceDayOfMonth(mCurrentBuffer.toString());						
		} else if (CalendarData.RECURRENCE_DAYOFWEEK.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceDayOfWeek(mCurrentBuffer.toString());					
		} else if (CalendarData.RECURRENCE_FIRSTDAYOFWEEK.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceFirstDayOfWeek(mCurrentBuffer.toString());			
		} else if (CalendarData.RECURRENCE_INTERVAL.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceInterval(mCurrentBuffer.toString());			
		} else if (CalendarData.RECURRENCE_ISLEAPMONTH.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceIsLeapMonth(mCurrentBuffer.toString());			
		} else if (CalendarData.RECURRENCE_MONTHOFYEAR.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceMonthOfYear(mCurrentBuffer.toString());			
		} else if (CalendarData.RECURRENCE_OCCURRENCES.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceOccurrences(mCurrentBuffer.toString());			
		} else if (CalendarData.RECURRENCE_TYPE.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceType(mCurrentBuffer.toString());			
		} else if (CalendarData.RECURRENCE_UNTIL.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceUntil(mCurrentBuffer.toString());			
		} else if (CalendarData.RECURRENCE_WEEKOFMONTH.equalsIgnoreCase(qName)) {
			mCalendarData.addRecurrenceWeekOfMonth(mCurrentBuffer.toString());			
		} 
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String string = new String(ch, start, length);
		mCurrentBuffer.append(string);
	}
	
	@Override
	public void error(SAXParseException e) {
		System.out.println("Inside error");
	}
	
	@Override
	public void fatalError(SAXParseException e) {
		System.out.println("Inside fatal error");
	}
	
	private void flushBuffer() {
		int capacity = mCurrentBuffer.capacity();
		if (capacity > 0) {
			mCurrentBuffer.delete(0, capacity);
		}
	}
}

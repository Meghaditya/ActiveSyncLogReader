package com.citrix.sax.calendar;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class CalendarMain {

	public static void main(String args[]) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {

		    InputStream    xmlInput  = new FileInputStream("/Users/meghaditya/Documents/Personal/eclipse_luna_workspace/luna2/ActiveSyncLogReader/logs/testData.txt");
		    SAXParser      saxParser = factory.newSAXParser();

		    CalendarSaxHandler handler   = new CalendarSaxHandler();
		    saxParser.parse(xmlInput, handler);

		} catch (Throwable err) {
		    err.printStackTrace ();
		}
	}
}

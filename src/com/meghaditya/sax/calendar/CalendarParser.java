package com.meghaditya.sax.calendar;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.meghaditya.files.File;

public class CalendarParser {

	public static void parse() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {

		    InputStream    xmlInput  = new FileInputStream(File.getCleanXmlFileName());
		    SAXParser      saxParser = factory.newSAXParser();

		    CalendarSaxHandler handler   = new CalendarSaxHandler();
		    saxParser.parse(xmlInput, handler);

		} catch (Throwable err) {
		    err.printStackTrace ();
		}
	}
}

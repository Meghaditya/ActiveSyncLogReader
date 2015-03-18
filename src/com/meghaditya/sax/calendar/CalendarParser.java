package com.meghaditya.sax.calendar;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.meghaditya.keyvaluereader.AbstractParser;

public class CalendarParser extends AbstractParser {

	@Override
	public void parse(String input) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			InputStream is = new ByteArrayInputStream(input.getBytes());
			SAXParser saxParser = factory.newSAXParser();
			CalendarSaxHandler handler = new CalendarSaxHandler();
			saxParser.parse(is, handler);

		} catch (Throwable err) {
			err.printStackTrace();
		}
	}
}

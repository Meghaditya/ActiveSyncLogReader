package com.meghaditya.files;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XmlStringCleanerTest {

	private final static String input = "Something\n<Body=0 bytes/>\nSomething";
	private final static String expected = "Something\nSomething";
	
	@Test
	public void testCleanXml() {
		assertEquals(expected, XmlStringCleaner.cleanXml(input).trim());
	}

}

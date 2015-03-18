package com.meghaditya.files;

/**
 * @author meghaditya
 * Cleans XML from a given input string
 */
public class XmlStringCleaner {
	private final static String	REMOVE_PREFIX1 = "<Body";
	
	public static String cleanXml(String input) {
		StringBuilder result = new StringBuilder();
		String[] lines = input.split("\n");
		
		for (String line : lines) {
			if (!line.trim().startsWith(REMOVE_PREFIX1)) {
				result.append(line).append('\n');
			}
		}
				
		return result.toString();
	}
}

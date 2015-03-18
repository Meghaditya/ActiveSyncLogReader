package com.meghaditya.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class XmlFileCleaner extends FileModifier{

	private final static String	REMOVE_PREFIX1 = "<Body";
	
	public static String cleanXml(String fileName) throws IOException {
		XmlFileCleaner xc = new XmlFileCleaner(fileName);
		xc.readFileContent();
		return xc.getFileContent();
	}

	private XmlFileCleaner(String fileName) throws IOException {
		super(fileName);
	}

	@Override
	protected void performLogic(BufferedReader br, PrintWriter pw) {
		String str;
		
		try {
			while (null != (str = br.readLine())) {
				if (!str.trim().startsWith(REMOVE_PREFIX1)) {
					mFileContent.append(str).append('\n');
				}
			}
		} catch (IOException ioEx) {
			System.err.println("I/O Error :" + " " + ioEx.getMessage());
		}
	}

	@Override
	protected String getOutFileName() {
		return "cleanActiveSyncLogs.txt";
	}

}

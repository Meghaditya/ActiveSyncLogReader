package com.citrix.aslogreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class XmlCleaner extends FileModifier{

	private final static String	REMOVE_PREFIX1 = "<Body";
	
	public static void cleanXml(String fileName) throws IOException {
		XmlCleaner xc = new XmlCleaner(fileName);
		xc.readFileContent();
	}

	private XmlCleaner(String fileName) throws IOException {
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
		
		pw.write(getFileContent());
	}

	@Override
	protected String getOutFileName() {
		return "cleanActiveSyncLogs.txt";
	}

}

package com.meghaditya.keyvaluereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;

import com.meghaditya.files.XmlStringCleaner;

public class KeyValueReader extends KeyFinder {

	protected KeyValueReader(String fileName) throws IOException {
		super(fileName);
	}

	public static void read(String fileName) throws IOException {
		KeyValueReader kvReader = new KeyValueReader(fileName);
		kvReader.readFileContent();
	}

	@Override
	protected void performLogic(BufferedReader br, PrintWriter pw) {
		String str;
		String key = null;
		String newKey;
		String value;
		Matcher matcher;

		try {
			while (null != (str = br.readLine())) {
				matcher = mPattern.matcher(str);
				if (matcher.find()) {
					newKey = str;
					if (mFileContent.length() != 0 && key != null) {
						value = getFileContent();
						AbstractParser parser = Resolver.resolve(key, value);
						if (null != parser) {
							System.out
									.println(String
											.format("KeyValueReader: Parser found for key = %s",
													key));
							parser.parse(XmlStringCleaner.cleanXml(value));
						}
						clearFileContent();
					}
					key = newKey;
				} else {
					mFileContent.append(str).append('\n');
				}
			}
		} catch (IOException ioEx) {
			System.err.println("I/O Error :" + " " + ioEx.getMessage());
		}
	}

	@Override
	protected String getOutFileName() {
		return "analyzed_log.txt";
	}
}

package com.meghaditya.keyvaluereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.meghaditya.files.FileModifier;

public class KeyFinder extends FileModifier{
	protected static final String REGEX = "[\\w]+[\\s]+:";
	
	public static void findKeys(String fileName) throws IOException {
		KeyFinder kf = new KeyFinder(fileName);
		kf.readFileContent();
	}

	protected final Pattern mPattern ;
	protected LinkedHashSet<String> mUniqueKeyList;

	protected KeyFinder(String fileName) throws IOException {
		super(fileName);
		mPattern = Pattern.compile(REGEX);
		mUniqueKeyList = new LinkedHashSet<String>();
	}
	
	protected void performLogic(BufferedReader br, PrintWriter pw) {
		String str;
		Matcher matcher;

		try {
			while (null != (str = br.readLine())) {
				matcher = mPattern.matcher(str);
				if (matcher.find()) {
					mUniqueKeyList.add(str.trim());
				}
			}
		} catch (IOException ioEx) {
			System.err.println("I/O Error :" + " " + ioEx.getMessage());
		}

		for (String item : mUniqueKeyList) {
			mFileContent.append(item).append('\n');
		}

		pw.write(getFileContent());
	}

	@Override
	protected String getOutFileName() {
		return "keys.txt";
	}
}

package com.citrix.aslogreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileModifier {

	protected Path mPath;
	protected StringBuilder mFileContent = new StringBuilder();

	public FileModifier(String fileName) throws IOException {
		mPath = Paths.get(fileName);
	}

	protected void readFileContent() throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			fis = new FileInputStream(mPath.toFile());
			fos = new FileOutputStream(getOutputAbsoluttePath());
			br = new BufferedReader(new InputStreamReader(fis));
			pw = new PrintWriter(new OutputStreamWriter(fos));
			
			performCoreLogic(br, pw);
	
		} catch (FileNotFoundException fEx) {
			System.err.println("FileNotFound :" + " " + fEx.getMessage());
		} finally {
			if (null != br)
				br.close();
			if (null != fis)
				fis.close();
			if (null != pw)
				pw.close();
			if (null != fos)
				fos.close();
		}
	}

	String getOutputAbsoluttePath() {
		int count = mPath.getNameCount();
		return "/" + mPath.subpath(0, count-1).resolve(getOutFileName()).toString();
	}

	public String getFileContent() {
		return mFileContent.toString();
	}
	
	protected abstract void performCoreLogic(BufferedReader br, PrintWriter pw);
	protected abstract String getOutFileName();
}

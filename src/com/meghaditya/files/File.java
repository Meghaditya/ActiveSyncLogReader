package com.meghaditya.files;

/**
 * @author meghaditya
 * A place to keep path and names of terminal and intermediate files	
 */
public class File {
	static final String REPO_PATH = "/Users/meghaditya/Documents/Personal/eclipse_luna_workspace/luna2/ActiveSyncLogReader/logs/";
	static final String INPUT_FILE_NAME = "as_martin_duursma_02_24_2015.txt";
	static final String CLEAN_XML_NAME = "cleanActiveSyncLogs.txt";
	static final String OUTPUT_FILE_NAME = "calendar_data.txt";
	
	public static String getInputFileName() {
		return new StringBuilder().append(REPO_PATH).append("/")
				.append(INPUT_FILE_NAME).toString();
	}
	
	public static String getCleanXmlFileName() {
		return new StringBuilder().append(REPO_PATH).append("/")
				.append(CLEAN_XML_NAME).toString();
	}

	public static String getOutputFileName() {
		return new StringBuilder().append(REPO_PATH).append("/")
				.append(OUTPUT_FILE_NAME).toString();
	}
}

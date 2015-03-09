package com.citrix.aslogreader;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			// KeyFinder.findKeys("/Users/meghaditya/Documents/Personal/eclipse_luna_workspace/luna2/ActiveSyncLogReader/logs/as_martin_duursma_02_24_2015.txt");
			XmlCleaner.cleanXml("/Users/meghaditya/Documents/Personal/eclipse_luna_workspace/luna2/ActiveSyncLogReader/logs/as_martin_duursma_02_24_2015.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

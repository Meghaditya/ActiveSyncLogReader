package com.meghaditya.aslogreader;

import java.io.IOException;

import com.meghaditya.files.File;
import com.meghaditya.keyvaluereader.KeyValueReader;

public class Main {

	public static void main(String[] args) {
		String inputFileName = File.getInputFileName();
		try {
			System.out.println("KeyValueReader: Starting execution.");
			KeyValueReader.read(inputFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

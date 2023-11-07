package com.gutengmorgen.TimeDial.parsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataManager {
	private static final String DEFAULT = "src/main/resources/";
	public static final String TEMPLATE = "Templates.csv";
	public static final String DATA_TEMPORAL = "dataTemp.csv";
	public static final String BOOKMARK = "bookmarks.csv";
	public static final String DELIMITER_MAJOR = ";";
	public static final String DELIMITER_MINOR = ",";
	public static final String DELIMITER_TEMPLATE = ":";

	public static void main(String[] args) {
		String data = "src/main/resources/Templates.csv";
		Path path = Paths.get(data);
		if (Files.exists(path)) {
			System.out.println("path exists");
		} else {
			System.out.println("path doesnt exists");
		}
	}

	public static List<String> readFile(String fileName){
		try {
			Path path = Paths.get(DEFAULT + fileName);
			return Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package com.gutengmorgen.TimeDial.parsing;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DataManager {
	private static final String DEFAULT = "src/main/resources/";
	public static final String TEMPLATE = "Templates.csv";
	public static final String DATA_TEMPORAL = "dataTemp.csv";
	public static final String BOOKMARK = "bookmarks.csv";
	public static final String DELIMITER_MAJOR = ";";
	public static final String DELIMITER_MINOR = ",";
	public static final String DELIMITER_TEMPLATE = ":";

	public static List<String> readFile(String fileName) {
		try {
			return Files.readAllLines(path(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void appendToFile(String line, String fileName) {
		try {
			BufferedWriter writer = Files.newBufferedWriter(path(fileName), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			writer.write(line);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updateLine(int index, String line, String fileName) {
		try {
			List<String> list = readFile(fileName);
			list.set(index, line);
			Files.write(path(fileName), list, StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Path path(String fileName) {
		return Paths.get(DEFAULT + fileName);
	}
}

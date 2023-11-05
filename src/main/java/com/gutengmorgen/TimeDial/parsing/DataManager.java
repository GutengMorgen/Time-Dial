package com.gutengmorgen.TimeDial.parsing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataManager {
	public static final String TEMPLATE = "Template.csv";
	public static final String SPLITMAJOR = ";";
	public static final String SPLITMINOR = ",";

	public static void main(String[] args) {
		String data = "src/main/resources/Templates.csv";
		Path path = Paths.get(data);
		if (Files.exists(path)) {
			System.out.println("path exists");
		} else {
			System.out.println("path doesnt exists");
		}
	}

	public static List<String> readTemplateLines() {
		try {
			String data = "src/main/resources/Templates.csv";
			Path path = Paths.get(data);
			return Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

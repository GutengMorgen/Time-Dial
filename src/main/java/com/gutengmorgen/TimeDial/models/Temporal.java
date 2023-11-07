package com.gutengmorgen.TimeDial.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Temporal {
	private LocalDateTime dateTime;
	private Tag tag;

	public static List<Temporal> parsingAllLines() {
		List<Temporal> l = new ArrayList<>();
		for (String s : DataManager.readFile(DataManager.DATA_TEMPORAL)) {
			String[] split = s.split(DataManager.DELIMITER_MAJOR);
			String[] splitInter = split[3].split(DataManager.DELIMITER_MINOR);
			l.add(new Temporal(convertDateTime(split[0], split[1]),
					new Tag(split[2], Template.convert(splitInter, true))));
		}
		return l;
	}

	public static LocalDateTime convertDateTime(String date, String time) {
		String text = date + " " + time;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return LocalDateTime.parse(text, formatter);
	}
}

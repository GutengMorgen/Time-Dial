package com.gutengmorgen.TimeDial.models;

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
public class Tag {
	private String name;
	private List<Template> templates;

	public static List<Tag> parsingAllLines() {
		List<Tag> l = new ArrayList<>();
		for (String s : DataManager.readFile(DataManager.TEMPLATE)) {
			String[] split = s.split(DataManager.DELIMITER_MAJOR);
			String[] splitInter = split[1].split(DataManager.DELIMITER_MINOR);
			l.add(new Tag(split[0], Template.convert(splitInter, false)));
		}
		return l;
	}

	@Override
	public String toString() {
		return name;
	}
}

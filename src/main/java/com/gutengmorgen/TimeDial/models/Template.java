package com.gutengmorgen.TimeDial.models;

import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;
import com.gutengmorgen.TimeDial.parsing.DataManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Template {
	private String name;
	private String hold;

	public static List<Template> convert(String text, boolean split) {
		String[] splitMajor = text.split(DataBaseManager.DELIMITER_MINOR);
		List<Template> list = new ArrayList<>();

		for (String txt : splitMajor) {
			if (split) {
				String[] sp = txt.split(DataManager.DELIMITER_TEMPLATE);
				list.add(new Template(sp[0] + ":", sp[1]));
			} else
				list.add(new Template(txt, ""));
		}
		return list;
	}

	@Override
	public String toString() {
		return "Template [name=" + name + ", hold=" + hold + "]";
	}
}

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
public class Template {
	private String name;
	private String hold;

	public static List<Template> convert(String[] names, boolean split) {
		List<Template> list = new ArrayList<>();
		for (String name : names) {
			if (split) {
				String[] sp = name.split(DataManager.DELIMITER_TEMPLATE);
				list.add(new Template(sp[0] + ":", sp[1]));
			} else
				list.add(new Template(name, ""));
		}
		return list;
	}
}

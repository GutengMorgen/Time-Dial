package com.gutengmorgen.TimeDial.extras;

import java.util.ArrayList;
import java.util.List;

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

	public static List<Template> convert(String[] names) {
		List<Template> list = new ArrayList<>();
		for (String name : names) {
			String[] split = name.split(":");
			if (split.length == 2)
				list.add(new Template(split[0] + ":", split[1]));
			else 
				list.add(new Template(name, ""));
		}
		return list;
	}
}

package com.gutengmorgen.TimeDial.extras;

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
public class MyTags {
	private String tagName;
	private List<Template> templates;

	public static List<MyTags> parsingAllLines() {
		List<MyTags> l = new ArrayList<>();
		for (String s : DataManager.readTemplateLines()) {
			String[] split = s.split(DataManager.SPLITMAJOR);
			String[] split2 = split[1].split(DataManager.SPLITMINOR);
			l.add(new MyTags(split[0], Template.convert(split2)));
		}
		return l;
	}

	@Override
	public String toString() {
		return tagName;
	}
}

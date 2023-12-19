package com.gutengmorgen.TimeDial.models;

import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;
import com.gutengmorgen.TimeDial.parsing.DataManager;

public class Template {
	private String name;
	private String hold;

	public Template(String name, String hold){
		this.name = name;
		this.hold = hold;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getHold(){
		return hold;
	}

	public void setHold(String hold){
		this.hold = hold;
	}

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

package com.gutengmorgen.TimeDial.extras;

import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataManager;

public class MyTags {
	private String tagName;
	private String[] templateContent;

	public MyTags() {
	}

	public MyTags(String tagName, String[] templateContent) {
		this.setTagName(tagName);
		this.setTemplateContent(templateContent);
	}

	public static List<MyTags> parsingAllLines(){
		List<MyTags> l = new ArrayList<>();
		for (String s : DataManager.readTemplateLines()) {
			String[] split = s.split(DataManager.SPLITMAJOR);
			String[] split2 = split[1].split(DataManager.SPLITMINOR);
			l.add(new MyTags(split[0], split2));
		}
		return l;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String[] getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String[] templateContent) {
		this.templateContent = templateContent;
	}

	@Override
	public String toString() {
		return tagName;
	}
}

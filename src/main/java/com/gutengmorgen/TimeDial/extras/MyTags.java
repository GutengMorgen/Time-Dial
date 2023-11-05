package com.gutengmorgen.TimeDial.extras;

import java.util.ArrayList;
import java.util.Arrays;
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

	public static List<MyTags> readAllLines() {
		List<MyTags> list = new ArrayList<>();
		for (String string : DataManager.readLines()) {
			String[] split = string.split(DataManager.SPLITBY);

			// Create a new array without the first element (index 0)
			String[] splitWithoutFirstElement = Arrays.copyOfRange(split, 1, split.length);

			list.add(new MyTags(split[0], splitWithoutFirstElement));
		}
		return list;
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

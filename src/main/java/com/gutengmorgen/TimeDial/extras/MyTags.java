package com.gutengmorgen.TimeDial.extras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.ParsingManager;

public class MyTags {
	private String tagName;
	private String[] templateContent;
//	private String[] defaultLine = new String[] { "Study", "resource:", "description:" };

	public MyTags() {
	}

	public MyTags(String tagName, String[] templateContent) {
		this.setTagName(tagName);
		this.setTemplateContent(templateContent);
	}

	public static List<MyTags> readAllLines() {
		List<MyTags> list = new ArrayList<>();
		for (String string : ParsingManager.readLines()) {
			String[] split = string.split(ParsingManager.spliter);

			// Create a new array without the first element (index 0)
			String[] splitWithoutFirstElement = Arrays.copyOfRange(split, 1, split.length);

			list.add(new MyTags(split[0], splitWithoutFirstElement));
		}
		return list;
	}

	public MyTags defaultLine() {
		return new MyTags("Study", new String[] { "Resource:", "Theme:", "Description" });
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

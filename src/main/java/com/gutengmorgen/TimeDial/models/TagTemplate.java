package com.gutengmorgen.TimeDial.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;

public class TagTemplate {
	private int position;
	private String name;
	private List<Template> templates;

	public TagTemplate(int position, String name, List<Template> templates){
		this.position = position;
		this.name = name;
		this.templates = templates;
	}

	public int getPosition(){
		return position;
	}

	public void setPosition(int position){
		this.position = position;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public List<Template> getTemplates(){
		return templates;
	}

	public void setTemplates(List<Template> templates){
		this.templates = templates;
	}

	public static List<TagTemplate> getAll() {
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			List<TagTemplate> list = new ArrayList<>();
			ResultSet rst = cnt.createStatement().executeQuery("SELECT * FROM main");

			while (rst.next()) {
				list.add(new TagTemplate(rst.getInt(2), rst.getString(3), Template.convert(rst.getString(4), false)));
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return name;
	}
}

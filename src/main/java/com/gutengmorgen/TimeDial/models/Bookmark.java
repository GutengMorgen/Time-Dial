package com.gutengmorgen.TimeDial.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
	private int position;
	private String name;
	private List<Template> templates;
	
	public static List<Bookmark> getAll() {
		List<Bookmark> l = new ArrayList<>();
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			ResultSet rst = cnt.createStatement().executeQuery("SELECT * FROM bookmark");

			while (rst.next()) {
				l.add(new Bookmark(rst.getInt(2), rst.getString(3), Template.convert(rst.getString(4), true)));
			}
			return l;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}

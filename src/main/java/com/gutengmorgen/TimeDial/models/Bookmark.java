package com.gutengmorgen.TimeDial.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;
import com.gutengmorgen.TimeDial.parsing.DataManager;

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
	
//	public static List<Bookmark> parsing(){
//		List<Bookmark> l = new ArrayList<>();
//		for (String s : DataManager.readFile(DataManager.BOOKMARK)) {
//			String[] split = s.split(DataManager.DELIMITER_MAJOR);
//			
//			TagTemplate tag = new TagTemplate(split[1], Template.convert(split[2], true));
//			Bookmark bookmark = new Bookmark(Integer.parseInt(split[0]), tag);
//			l.add(bookmark);
//		}
//		return l;
//	}

	public static List<Bookmark> getAll() {
		List<Bookmark> l = new ArrayList<>();
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			ResultSet rst = cnt.createStatement().executeQuery("SELECT * FROM bookmark");

			while (rst.next()) {
				l.add(new Bookmark(rst.getInt(2), rst.getString(3), Template.convert(rst.getString(4), false)));
			}
			return l;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}

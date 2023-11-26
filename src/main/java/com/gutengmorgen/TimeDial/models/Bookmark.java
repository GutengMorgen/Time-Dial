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
public class Bookmark {
	private int position;
	private TagTemplate tag;
	
	public static List<Bookmark> parsing(){
		List<Bookmark> l = new ArrayList<>();
		for (String s : DataManager.readFile(DataManager.BOOKMARK)) {
			String[] split = s.split(DataManager.DELIMITER_MAJOR);
			
			TagTemplate tag = new TagTemplate(split[1], Template.convert(split[2], true));
			Bookmark bookmark = new Bookmark(Integer.parseInt(split[0]), tag);
			l.add(bookmark);
		}
		return l;
	}
}

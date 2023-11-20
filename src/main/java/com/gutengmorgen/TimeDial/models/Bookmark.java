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
	private Tag tag;
	
	public static List<Bookmark> parsing(){
		List<Bookmark> l = new ArrayList<>();
		for (String s : DataManager.readFile(DataManager.BOOKMARK)) {
			String[] split = s.split(DataManager.DELIMITER_MAJOR);
			String[] splitInter = split[2].split(DataManager.DELIMITER_MINOR);
			
			Tag tag = new Tag(split[1], Template.convert(splitInter, true));
			Bookmark bookmark = new Bookmark(Integer.parseInt(split[0]), tag);
			l.add(bookmark);
		}
		return l;
	}
	
//	public static List<Tag> parsingAllLines() {
//		List<Tag> l = new ArrayList<>();
//		for (String s : DataManager.readFile(DataManager.BOOKMARK)) {
//			String[] split = s.split(DataManager.DELIMITER_MAJOR);
//			String[] splitInter = split[1].split(DataManager.DELIMITER_MINOR);
//			l.add(new Tag(split[0], Template.convert(splitInter, true)));
//		}
//		return l;
//	}
}

package com.gutengmorgen.TimeDial;

import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.extras.MyTags;
import com.gutengmorgen.TimeDial.extras.Template;
import com.gutengmorgen.TimeDial.parsing.DataManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataTemp {
	private String date;
	private String time;
	private MyTags tag;

	public static List<DataTemp> parsingAllLines() {
		List<DataTemp> l = new ArrayList<>();
		for (String s : DataManager.readDataTempLines()) {
			String[] split = s.split(DataManager.DELIMITER_MAJOR);
			String[] splitInter = split[3].split(DataManager.DELIMITER_MINOR);
			l.add(new DataTemp(split[0], split[1], new MyTags(split[2], Template.convert(splitInter, true))));
		}
		return l;
	}
}

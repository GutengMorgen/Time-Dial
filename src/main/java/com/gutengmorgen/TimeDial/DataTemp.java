package com.gutengmorgen.TimeDial;

import java.util.ArrayList;
import java.util.List;

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
	private String tagName;
	private List<Template> templates;

	public static List<DataTemp> parsingAllLines() {
		List<DataTemp> l = new ArrayList<>();
		for (String s : DataManager.readDataTempLines()) {
			String[] split = s.split(DataManager.SPLITMAJOR);
			String[] split2 = split[3].split(DataManager.SPLITMINOR);
			l.add(new DataTemp(split[0], split[1], split[2], Template.convert(split2)));
		}
		return l;
	}
}

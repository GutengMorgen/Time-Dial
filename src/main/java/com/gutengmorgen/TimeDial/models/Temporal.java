package com.gutengmorgen.TimeDial.models;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.management.Query;
import javax.swing.JLabel;

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
public class Temporal {
	private LocalDateTime dateTime;
	private Tag tag;

	public static List<Temporal> parsingAllLines() {
		List<Temporal> l = new ArrayList<>();
		for (String s : DataManager.readFile(DataManager.DATA_TEMPORAL)) {
			String[] split = s.split(DataManager.DELIMITER_MAJOR);
			String[] splitInter = split[3].split(DataManager.DELIMITER_MINOR);
			l.add(new Temporal(convertDateTime(split[0], split[1]),
					new Tag(split[2], Template.convert(splitInter, true))));
		}
		return l;
	}

	public static LocalDateTime convertDateTime(String date, String time) {
		String text = date + " " + time;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return LocalDateTime.parse(text, formatter);
	}

	public static void save(String tag, String dcpFmt) {
		String query = "INSERT INTO main (datetime, tag, description) VALUES (DATETIME('now','localtime'),?,?)";
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPORAL_URL);
				PreparedStatement pstm = cnt.prepareStatement(query)) {
			pstm.setString(1, tag);
			pstm.setString(2, dcpFmt); 

			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		save("offline", null);
	}
}

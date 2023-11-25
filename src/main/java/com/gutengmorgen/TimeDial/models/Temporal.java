package com.gutengmorgen.TimeDial.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class Temporal {
	private LocalDateTime dateTime;
	private String tag;
	private List<Template> templates;
	
	public static List<Temporal> getAll(){
		List<Temporal> list = new ArrayList<>();
		try(Connection cnt = DriverManager.getConnection(DataBaseManager.HISTORY_URL)) {
			ResultSet rst = cnt.createStatement().executeQuery("SELECT * FROM main");
			while (rst.next()) {
				String[] split = rst.getString(4).split(DataBaseManager.DELIMITER_MINOR);
				list.add(new Temporal(History.dateTime(rst.getString(2)), rst.getString(3), Template.convert(split, true)));
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
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

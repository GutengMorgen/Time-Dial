package com.gutengmorgen.TimeDial.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;

public class History {
	private LocalDateTime dateTime;
	private String tag;
	private List<Template> templates;

	public History(){
	}

	public History(LocalDateTime dateTime, String tag, List<Template> templates){
		this.dateTime = dateTime;
		this.tag = tag;
		this.templates = templates;
	}

	public void setDateTime(LocalDateTime dateTime){
		this.dateTime = dateTime;
	}

	public LocalDateTime getDateTime(){
		return dateTime;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return tag;
	}

	public List<Template> getTemplates(){
		return templates;
	}

	public void setTemplates(List<Template> templates){
		this.templates = templates;
	}

	public static List<History> getAll() {
		List<History> l = new ArrayList<>();
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.HISTORY_URL)) {
			ResultSet rst = cnt.createStatement().executeQuery("SELECT * FROM main");

			while (rst.next()) {
				l.add(new History(dateTime(rst.getString(2)), rst.getString(3),
						Template.convert(rst.getString(4), true)));
			}
			return l;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static LocalDateTime dateTime(String datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(datetime, formatter);
	}

	public static void save(String tag, String dcpFmt) {
		String query = "INSERT INTO main (datetime, tag, description) VALUES (DATETIME('now','localtime'),?,?)";
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.HISTORY_URL);
				PreparedStatement pstm = cnt.prepareStatement(query)) {
			pstm.setString(1, tag);
			pstm.setString(2, dcpFmt);

			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "History [dateTime=" + dateTime + ", tag=" + tag + ", templates=" + templates + "]";
	}

}

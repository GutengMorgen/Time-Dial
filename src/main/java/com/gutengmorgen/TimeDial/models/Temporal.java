package com.gutengmorgen.TimeDial.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;

public class Temporal {
	public static int LIMIT = 10;
	private LocalDateTime dateTime;
	private String tag;
	private List<Template> templates;

	public Temporal(LocalDateTime dateTime, String tag, List<Template> templates) {
		this.dateTime = dateTime;
		this.tag = tag;
		this.templates = templates;
	}

	public LocalDateTime getDateTime(){
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime){
		this.dateTime = dateTime;
	}

	public String getTag(){
		return tag;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public List<Template> getTemplates(){
		return templates;
	}

	public void setTemplates(List<Template> templates){
		this.templates = templates;
	}

	public static List<Temporal> getAll() {
		List<Temporal> l = new ArrayList<>();
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPORAL_URL)) {
			ResultSet rst = cnt.createStatement().executeQuery("SELECT * FROM main");

			while (rst.next()) {
				l.add(new Temporal(History.dateTime(rst.getString(2)), rst.getString(3),
						Template.convert(rst.getString(4), true)));
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void save(String tag, String dcpFmt) {
		final String COUNT = "SELECT COUNT(*) FROM main";
		final String OLDEST_ID = "SELECT id FROM main ORDER BY datetime ASC LIMIT 1;";
		final String UPDATE = "UPDATE main SET datetime=DATETIME('now','localtime'),tag=?,description=? WHERE id=?;";
		final String INSERT = "INSERT INTO main (datetime, tag, description) VALUES (DATETIME('now','localtime'),?,?);";

		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPORAL_URL);) {
			ResultSet rsl = cnt.createStatement().executeQuery(COUNT);
			PreparedStatement pstm = null;

			if (rsl.next() && rsl.getInt(1) >= LIMIT) {
				// replace record
				ResultSet rslS = cnt.createStatement().executeQuery(OLDEST_ID);
				if (rslS.next()) {
					pstm = cnt.prepareStatement(UPDATE);
					pstm.setString(1, tag);
					pstm.setString(2, dcpFmt);
					pstm.setInt(3, rslS.getInt(1));
				}
			} else {
				// create more records
				pstm = cnt.prepareStatement(INSERT);
				pstm.setString(1, tag);
				pstm.setString(2, dcpFmt);
			}

			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void main(String[] args) {
		save("Offline", "Activity:1,Description:1");
		save("Offline", "Activity:2,Description:2");
		save("Offline", "Activity:3,Description:3");
		save("Offline", "Activity:4,Description:4");
		save("Offline", "Activity:5,Description:5");
		save("Offline", "Activity:6,Description:6");
		save("Offline", "Activity:7,Description:7");
		save("Offline", "Activity:8,Description:8");
		save("Offline", "Activity:9,Description:9");
		save("Offline", "Activity:10,Description:10");
	}
}

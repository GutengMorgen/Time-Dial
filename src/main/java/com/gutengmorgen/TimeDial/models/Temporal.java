package com.gutengmorgen.TimeDial.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Temporal {
	private LocalDateTime dateTime;
	private String tag;
	private List<Template> templates;
	private static int limit = 10;

	public static List<Temporal> getAll() {
		List<Temporal> list = new ArrayList<>();
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.HISTORY_URL)) {
			ResultSet rst = cnt.createStatement().executeQuery("SELECT * FROM main");
			while (rst.next()) {
				String[] split = rst.getString(4).split(DataBaseManager.DELIMITER_MINOR);
				list.add(new Temporal(History.dateTime(rst.getString(2)), rst.getString(3),
						Template.convert(split, true)));
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	// TODO:stop creating if the id is equal to x(5) and replacing starting for the
	// first id
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


	public static void saveV2(String tag, String dcpFmt) {
	final String COUNT = "SELECT COUNT(*) FROM main";
	final String OLDEST_ID = "SELECT id FROM main ORDER BY datetime ASC LIMIT 1;";
	final String UPDATE = "UPDATE main SET datetime=DATETIME('now','localtime'),tag=?,description=? WHERE id=?;";
	final String INSERT = "INSERT INTO main (datetime, tag, description) VALUES (DATETIME('now','localtime'),?,?);";

		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPORAL_URL);) {
			ResultSet rsl = cnt.createStatement().executeQuery(COUNT);
			PreparedStatement pstm = null;

			if (rsl.next() && rsl.getInt(1) >= limit) {
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
		saveV2("1", "1.13");
	}
}

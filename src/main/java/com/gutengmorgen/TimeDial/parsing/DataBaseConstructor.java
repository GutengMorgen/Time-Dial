package com.gutengmorgen.TimeDial.parsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConstructor {

	public static void main(String[] args) {
		try {
//			historydb();
//			temporaldb();
//			templatedb();
			bookmarkdb();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void historydb() throws SQLException {
		Connection conec = DriverManager.getConnection(DataBaseManager.HISTORY_URL);
		Statement stm = conec.createStatement();
		String parms = "datetime TEXT, tag TEXT, description TEXT";
		stm.execute("DROP TABLE IF EXISTS main");
		stm.execute("CREATE TABLE main(id INTEGER PRIMARY KEY, " + parms + ");");

	}

	private static void temporaldb() throws SQLException {
		Connection conec = DriverManager.getConnection(DataBaseManager.TEMPORAL_URL);
		Statement stm = conec.createStatement();
		String parms = "datetime TEXT, tag TEXT, description TEXT";
		stm.execute("DROP TABLE IF EXISTS main");
		stm.execute("CREATE TABLE main(id INTEGER PRIMARY KEY, " + parms + ");");
	}

	private static void templatedb() throws SQLException {
		Connection conec = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL);
		Statement stm = conec.createStatement();
//		los templates tambien pueden tener un orden como los bookmarks
		//create second table for the bookmarks
		String parms = "pos INTEGER, tag TEXT, description TEXT";
		stm.execute("DROP TABLE IF EXISTS main");
		stm.execute("CREATE TABLE main(id INTEGER PRIMARY KEY, " + parms + ");");
		stm.execute(
				"INSERT INTO main (pos, tag, description) VALUES (1,'Studying', 'Theme:,SubTheme:,Resource:,Description:');");
		stm.execute("INSERT INTO main (pos, tag, description) VALUES (2, 'Relax', 'Resource:,Description:');");
		stm.execute("INSERT INTO main (pos, tag, description) VALUES (3, 'Offline', 'Activity:,Description:');");
	}
	
	private static void bookmarkdb() throws SQLException {
		Connection conec = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL);
		Statement stm = conec.createStatement();
		String parms = "pos INTEGER, tag TEXT, description TEXT";
		stm.execute("DROP TABLE IF EXISTS bookmark");
		stm.execute("CREATE TABLE bookmark(id INTEGER PRIMARY KEY, " + parms + ");");
	}
}

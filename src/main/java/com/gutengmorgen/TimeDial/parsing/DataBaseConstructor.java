package com.gutengmorgen.TimeDial.parsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConstructor {

	public static void main(String[] args) {
		try {
			historydb();
			temporaldb();
//			templatedb();
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
		String parms = "pos INTEGER, tag TEXT, template TEXT, hold TEXT";
		stm.execute("DROP TABLE IF EXISTS main");
		stm.execute("CREATE TABLE main(id INTEGER PRIMARY KEY, " + parms + ");");
	}
}

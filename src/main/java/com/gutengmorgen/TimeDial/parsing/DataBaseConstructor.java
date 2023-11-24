package com.gutengmorgen.TimeDial.parsing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConstructor {

	public static void main(String[] args) {

	}

	private static void historydb(Connection conec) throws SQLException {
		Statement sta = conec.createStatement();
		String parms = "date TEXT, time TEXT, tag TEXT, template TEXT";
		sta.execute("CREATE TABLE IF NOT EXISTS history(id INTEGER PRIMARY KEY," + parms + " );");
		
	}
	
	private static void temporaldb(Connection conec) throws SQLException {
		Statement sta = conec.createStatement();
		String parms = "date TEXT, time TEXT, tag TEXT, template TEXT";
		sta.execute("CREATE TABLE IF NOT EXISTS history(id INTEGER PRIMARY KEY," + parms + " );");
	}
	
	private static void templatedb(Connection conec) throws SQLException {
		Statement sta = conec.createStatement();
//		los templates tambien pueden tener un orden como los bookmarks
		String parms = "tag TEXT, template TEXT, hold TEXT";
		sta.execute("CREATE TABLE IF NOT EXISTS history(id INTEGER PRIMARY KEY," + parms + " );");
	}
}

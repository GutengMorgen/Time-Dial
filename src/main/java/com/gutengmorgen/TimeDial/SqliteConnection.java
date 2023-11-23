package com.gutengmorgen.TimeDial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
	public static void main(String[] args) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/hello.db");
			System.out.println("connect to sqlite");

//			createTable(connection);
			getData(connection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
					System.out.println("connection close");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createTable(Connection connection) throws SQLException {
		Statement state = connection.createStatement();
		state.execute("CREATE TABLE IF NOT EXISTS my_table (id INTEGER PRIMARY KEY, name TEXT);");
		state.execute("CREATE TABLE IF NOT EXISTS bookmarks (if INTEGER PRIMART KEY, name TEXT)");
		System.out.println("table create successfuly");

		state.executeUpdate("INSERT INTO my_table (id, name) VALUES (1, 'John');");
		state.executeUpdate("INSERT INTO my_table (id, name) VALUES (2, 'Alice');");
		System.out.println("Data inserted successfully.");
	}

	private static void getData(Connection connection) throws SQLException {
		Statement state = connection.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM my_table");

		while (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			System.out.println("id: " + id + " - " + "name: " + name);
		}
	}
}

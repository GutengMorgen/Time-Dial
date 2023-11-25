package com.gutengmorgen.TimeDial.parsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseManager {
	public static final String JBDC_URL = "jdbc:sqlite:src/main/resources/database/";
	public static final String TEMPLATE_URL = JBDC_URL + "template.db";
	public static final String TEMPORAL_URL = JBDC_URL + "temporal.db";
	public static final String HISTORY_URL = JBDC_URL + "history.db";
	public static final String DELIMITER_MINOR = ",";

	public static ResultSet getAllData(String dbPath) {
		return executeQueryStm(dbPath, "SELECT * FROM main");
	}

	public static void setData(String dbPath, String query) {
		executeUpdateStm(dbPath, "INSERT INTO main " + query + ";");
	}

	public static void updateData(String dbPath, int id) {
		String query = "UPDATE main SET ----- WHERE id = " + id + ";";
		executeUpdateStm(dbPath, query);
	}

	private static ResultSet executeQueryStm(String dbPath, String query) {
		try (Connection conec = DriverManager.getConnection(dbPath)) {
			Statement stm = conec.createStatement();
			return stm.executeQuery(query);
		} catch (Exception e) {
			throw new RuntimeException("Connection failed => " + e.getMessage());
		}
	}

	private static void executeUpdateStm(String dbPath, String query) {
		try (Connection conec = DriverManager.getConnection(dbPath)) {
			Statement stm = conec.createStatement();
			stm.executeUpdate(query);
		} catch (Exception e) {
			throw new RuntimeException("Connection failed => " + e.getMessage());
		}
	}
}

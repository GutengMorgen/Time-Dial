package com.gutengmorgen.TimeDial.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gutengmorgen.TimeDial.parsing.DataBaseManager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
	private int position;
	private String name;
	private List<Template> templates;
	
	public static List<Bookmark> getAll() {
		List<Bookmark> l = new ArrayList<>();
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			ResultSet rst = cnt.createStatement().executeQuery("SELECT * FROM bookmark");

			while (rst.next()) {
				l.add(new Bookmark(rst.getInt(2), rst.getString(3), Template.convert(rst.getString(4), true)));
			}
			return l;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static Bookmark selectByPosAndTag(byte pos, String tName) {
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			Bookmark bookmark = null;

			PreparedStatement pstm = cnt.prepareStatement("SELECT * FROM bookmark WHERE tag=? AND pos=?");
			pstm.setString(1, tName);
			pstm.setInt(2, pos);
			ResultSet rst = pstm.executeQuery();

			while (rst.next()) {
				bookmark = new Bookmark(rst.getInt(2), rst.getString(3), Template.convert(rst.getString(4), true));
			}
			return bookmark;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static int getIdByPosAndTag(byte pos, String tName) {
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			int id = -1;

			PreparedStatement pstm = cnt.prepareStatement("SELECT id FROM bookmark WHERE tag=? AND pos=?");
			pstm.setString(1, tName);
			pstm.setInt(2, pos);
			ResultSet rst = pstm.executeQuery();

			while (rst.next()) {
				id = rst.getInt(1);
			}
			return id;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	public static boolean checkBookmark(byte pos, String tName) {
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			boolean exits = false;

			PreparedStatement pstm = cnt.prepareStatement("SELECT * FROM bookmark WHERE tag=? AND pos=?");
			pstm.setString(1, tName);
			pstm.setInt(2, pos);
			ResultSet rst = pstm.executeQuery();

			if(rst.next()) {
				System.out.println("bookmark exits");
				exits = true;
			}
			
			return exits;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void createBookmark(byte pos, String tag, String description) {
		final String INSERT = "INSERT INTO bookmark (pos, tag, description) VALUES (?,?,?);";
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			PreparedStatement pstm = cnt.prepareStatement(INSERT);
			pstm.setInt(1, pos);
			pstm.setString(2, tag);
			pstm.setString(3, description);
			pstm.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void updateBookmark(byte pos, String tag, String description) {
		final String UPDATE = "UPDATE bookmark SET tag=?, description=? WHERE pos=?;";
		try (Connection cnt = DriverManager.getConnection(DataBaseManager.TEMPLATE_URL)) {
			PreparedStatement pstm = cnt.prepareStatement(UPDATE);
			pstm.setString(1, tag);
			pstm.setString(2, description);
			pstm.setInt(3, pos);
			pstm.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Bookmark [position=" + position + ", name=" + name + ", templates=" + templates + "]";
	}
}

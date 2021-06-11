package com.blas.fish.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
	private final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/OrnamentalFish";
	private final String ID = "root";
	private final String PASS = "";
	private Statement preparedStatement;

	public Connection getConnection() {
		try {
			Class.forName(DRIVER_NAME);
			return DriverManager.getConnection(DB_URL, ID, PASS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public boolean isExistThisId(String id, String password) {
		try {
			Connection conn = getConnection();
			preparedStatement = conn.createStatement();
			ResultSet rs = preparedStatement.executeQuery("SELECT * FROM admin WHERE username='" + id + "' and password='" + password + "'");
			System.out.println("sql: " + preparedStatement.toString());
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}

package de.szut.sqlite_browser.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector {

	private Connection connection;

	public Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connect(String path) throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:" + path);
	}

	public ResultSet getTables() throws SQLException {
		return connection.getMetaData().getTables(null, null, "%", null);
	}

	public ResultSet executeQuery(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}

	public void closeConnection() throws SQLException {
		connection.close();
	}
}

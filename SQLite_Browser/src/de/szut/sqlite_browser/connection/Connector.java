package de.szut.sqlite_browser.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Connector class responsible for administrating the SQL connection
 * @author Steffen Wiﬂmann
 *
 */
public class Connector {

	private Connection connection;

	/**
	 * Loading the SQLite driver
	 */
	public Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
		}
	}

	/**
	 * creates a connection to the database
	 * @param path needs to be an absolute and valid path
	 * @throws SQLException needs to be handled by the instantiating class
	 */
	public void connect(String path) throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:" + path);
	}

	/**
	 * extracts the tablenames of the connected database by using
	 * the connection metadata
	 * @return resultset consisting of tablenames
	 * @throws SQLException needs to be handled by the instantiating class
	 */
	public ResultSet getTables() throws SQLException {
		return connection.getMetaData().getTables(null, null, "%", null);
	}

	/**
	 * executes an SQL query, which is case insensitive
	 * @param query needs to be a valid SQL query, else an SQLException will appear
	 * @return resultset consisting of data, which was returned by the database based on the entered SQL query
	 * @throws SQLException needs to be handled by the instantiating class
	 */
	public ResultSet executeQuery(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}

	/**
	 * closes the connection to the database
	 * @throws SQLException need to be handled by the instantiating class
	 */
	public void closeConnection() throws SQLException {
		connection.close();
	}
}

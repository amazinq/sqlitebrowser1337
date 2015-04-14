package de.szut.sqlite_browser.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import de.szut.sqlite_browser.connection.Connector;
import de.szut.sqlite_browser.gui.Surface;

/**
 * model class responsible for all logic parts
 * @author Steffen Wiﬂmann
 *
 */
public class Model {

	private static final String CONNECTION_ERROR_MESSAGE = "Error while connecting to Database!";
	private static final String INVALID_BOUNDS_ERROR_MESSAGE = "Limit bounds are invalid! Both have to be a number greater than 0 and upper bound must be greater than lower bound!";
	private static final String NO_CONNECTION_ERROR_MESSAGE = "No connection enabled!";
	private static final String INVALID_QUERY_ERROR_MESSAGE = "Query could not be executed! Might be caused by a typing error.";
	private static final String CANT_CLOSE_CONNECTION_ERROR_MESSAGE = "Error while closing the connection!";
	
	private Connector connector;
	private Surface surface;
	private ArrayList<String> tableNames;
	private boolean connectionEnabled;

	public Model(Surface surface) {
		connector = new Connector();
		this.surface = surface;
		tableNames = new ArrayList<String>();
		connectionEnabled = false;
	}

	/**
	 * opens the connection to the database
	 * @param path absolute and valid path
	 */
	public void openConnection(String path) {
			try {
				connector.connect(path);
				ResultSet tables = connector.getTables();
				while(tables.next()) {
					tableNames.add(tables.getString(3));
				}
				connectionEnabled = true;
				surface.updateTree(tableNames);
				surface.setConnectionEnabled(connectionEnabled);
			} catch (SQLException e) {
				surface.setConnectionEnabled(connectionEnabled);
				surface.showErrorMessage(CONNECTION_ERROR_MESSAGE);
			}
	}
	
	/**
	 * executes an SQL query to the connected database. Only works if the connection has been enabled
	 * @param query needs to be a valid SQL query
	 * @param limitEnabled boolean whether the limit flag is true or not
	 * @param lowerBound limit 
	 * @param upperBound limit
	 */
	public void executeQuery(String query, boolean limitEnabled, String lowerBound, String upperBound) {
		Object[][] data;
		String[] columnNames;
		ResultSet queryResult;
		ResultSet numberOfRows;
		ResultSetMetaData queryResultMetaData;
		String tableName = "";
		String limitString;
		query = query.toLowerCase();
		if(connectionEnabled) {
			if(!limitEnabled) {
				lowerBound = null;
				upperBound = null;
			} else {
				if(!isAValidNumber(lowerBound, upperBound)) {
					surface.showErrorMessage(INVALID_BOUNDS_ERROR_MESSAGE);
					return;
				}
			}
		} else {
			surface.showErrorMessage(NO_CONNECTION_ERROR_MESSAGE);
			return;
		}
		// Constructing limitstring which will be appended to the query
		if(lowerBound == null || upperBound == null ||query.contains("limit")) {
			limitString = "";
		} else {
			limitString = " limit " + lowerBound +" , " + (String.valueOf((Integer.parseInt(upperBound) - Integer.parseInt(lowerBound))));
		}
		
		
		try {
			if(query.startsWith("select")) {
				// Execute mainquery
				queryResult = connector.executeSelectQuery(query + limitString);
				String[] queryArray = query.split(" ");
				
				// Extracting the tablename by using the from keyword
				int startOffset = 0;
				int endOffset = 0;
				while(!queryArray[startOffset].contains("from")) {
					startOffset++;
					endOffset++;
				}
				if(queryArray[startOffset +1].startsWith("'") || queryArray[startOffset +1].startsWith(String.valueOf('"'))) {
					while(!queryArray[endOffset +1].endsWith("'") && !queryArray[endOffset +1].endsWith(String.valueOf('"'))) {
						endOffset++;
					}
				}
				for(int iterator = startOffset; iterator <= endOffset; iterator++) {
					tableName = tableName + " " + queryArray[iterator +1];
					
				}
				
				// Subquery to obtain the number of rows in the selected table
				numberOfRows = connector.executeSelectQuery("Select count(*) from " + tableName);
				queryResultMetaData = queryResult.getMetaData();
				numberOfRows.next();
				columnNames = new String[queryResultMetaData.getColumnCount()];
				// Extracting the Columnnames
				for(int i = 1; i <= queryResultMetaData.getColumnCount(); i++) {
					columnNames[i-1] = queryResultMetaData.getColumnName(i);
				}
				data = new Object[numberOfRows.getInt(1)][queryResultMetaData.getColumnCount()];
				int counter = 0;
				// Extracting the tabledata 
				while(queryResult.next()) {
					for(int i = 0; i < queryResultMetaData.getColumnCount(); i++) {
						data[counter][i] = queryResult.getString(i+1);
					}
					counter++;
				}
				queryResult.close();
				numberOfRows.close();
				// Update the GUI
				surface.updateDataList(data, columnNames);
			} else if(query.startsWith("update") || query.startsWith("create")) {
				connector.executeUpdateQuery(query);
				surface.showSuccessfullyUpdatedMessage("Database successfully edited");
			}
		} catch (SQLException e) {
			surface.showErrorMessage(INVALID_QUERY_ERROR_MESSAGE);
		}
	}
	
	/**
	 * Closes the connection and also clears the GUI
	 */
	public void closeConnection() {
		if(connectionEnabled) {
			try {
				connector.closeConnection();
				connectionEnabled = false;
				surface.updateDataList(null, null);
				surface.setConnectionEnabled(false);
				surface.clearTree();
			} catch (SQLException e) {
				surface.showErrorMessage(CANT_CLOSE_CONNECTION_ERROR_MESSAGE);
			}
		} else {
			surface.showErrorMessage(NO_CONNECTION_ERROR_MESSAGE);
		}
	}
	
	/**
	 * Checks whether the bound strings are valid numbers
	 * which can be used in SQL querys
	 * @param lowerBound 
	 * @param upperBound
	 * @return boolean true if the bounds are valid and false if not
	 */
	private boolean isAValidNumber(String lowerBound, String upperBound) {
		try {
			int tempLowerBound = Integer.parseInt(lowerBound);
			int tempUpperBound = Integer.parseInt(upperBound);
			if (tempLowerBound < 0 || tempUpperBound < 0) {
				return false;
			}
			if (tempUpperBound < tempLowerBound) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}

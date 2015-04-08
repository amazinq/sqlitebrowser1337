package de.szut.sqlite_browser.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.szut.sqlite_browser.connection.Connector;

public class Model {

	private Connector connector;

	public Model() {
		connector = new Connector();
	}

	public void openConnection(String path) {
		if (connector.connect(path)) {
			try {
				ResultSet tables = connector.getTables();
				while (tables.next()) {
					System.out.println(tables.getString(3));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}
}

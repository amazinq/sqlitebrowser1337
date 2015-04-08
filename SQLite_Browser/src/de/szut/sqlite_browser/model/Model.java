package de.szut.sqlite_browser.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.szut.sqlite_browser.connection.Connector;
import de.szut.sqlite_browser.gui.Surface;

public class Model {

	private Connector connector;
	private Surface surface;
	private ArrayList<String> tableNames;

	public Model(Surface surface) {
		connector = new Connector();
		this.surface = surface;
		tableNames = new ArrayList<String>();
	}

	public void openConnection(String path) {
		if (connector.connect(path)) {
			try {
				ResultSet tables = connector.getTables();
				while(tables.next()) {
					tableNames.add(tables.getString(3));
				}
				surface.updateTree(tableNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}
}

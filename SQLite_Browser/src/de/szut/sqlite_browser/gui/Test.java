package de.szut.sqlite_browser.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	 public static void main( String args[] )
	  {
	    Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:H:/eclipse/workspace/SQLite_Browser/db/World.db3");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    ResultSet rt;
		try {
			rt = c.getMetaData().getTables(null, null, "%", null);
			while(rt.next()) {
	    	System.out.println(rt.getString(3));
	    }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    System.out.println("Opened database successfully");
	    try {
			ResultSet rs = c.createStatement().executeQuery("Select * from Country");
			while(rs.next()) {
				//System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	  }
}

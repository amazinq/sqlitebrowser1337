package de.szut.sqlite_browser.main;

import de.szut.sqlite_browser.dataOperation.PropertyLoader;
import de.szut.sqlite_browser.gui.Frame;
import de.szut.sqlite_browser.gui.Menu;
import de.szut.sqlite_browser.gui.Surface;
import de.szut.sqlite_browser.gui.SwingSurface;
import de.szut.sqlite_browser.model.Model;

/**
 * main method to execute the application
 * @author Steffen Wiﬂmann
 *
 */
public class Main {

	/**
	 * building all needed objects 
	 * @param args
	 */
	public static void main(String[] args) {
		Surface surface = new SwingSurface();
		Model model = new Model(surface);
		surface.setModel(model);
		new Frame(surface, new Menu(model), new PropertyLoader());

	}

}

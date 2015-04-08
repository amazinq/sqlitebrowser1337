package de.szut.sqlite_browser.main;

import de.szut.sqlite_browser.gui.Frame;
import de.szut.sqlite_browser.gui.Menu;
import de.szut.sqlite_browser.gui.SwingSurface;
import de.szut.sqlite_browser.model.Model;

public class Main {

	public static void main(String[] args) {
		new Frame(new SwingSurface(), new Menu(new Model()));

	}

}

package de.szut.sqlite_browser.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import de.szut.sqlite_browser.dataOperation.PropertyLoader;

/**
 * Main GUI component
 * @author Steffen Wiﬂmann
 *
 */
public class Frame extends JFrame {

	private static final long serialVersionUID = 5836166086363876139L;
	
	/**
	 * Create the frame and defining an actionlistener which saves the properties while closing the window
	 */
	public Frame(Surface panel, JMenuBar menuBar, PropertyLoader propertyLoader) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(propertyLoader.getWindowDimension());
		setVisible(true);
		setContentPane((JPanel)panel);
		setJMenuBar(menuBar);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				propertyLoader.setWindowDimension(getBounds());
			}
		});
	}
}
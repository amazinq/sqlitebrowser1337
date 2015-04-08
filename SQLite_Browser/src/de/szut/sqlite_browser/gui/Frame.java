package de.szut.sqlite_browser.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class Frame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5836166086363876139L;
	
	/**
	 * Create the frame.
	 */
	public Frame(Surface panel, JMenuBar menuBar) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 868, 582);
		setVisible(true);
		setContentPane((JPanel)panel);
		setJMenuBar(menuBar);
	}
}
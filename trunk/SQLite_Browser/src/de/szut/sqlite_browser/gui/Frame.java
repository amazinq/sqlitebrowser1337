package de.szut.sqlite_browser.gui;

import java.awt.EventQueue;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5701910777941753073L;
	private JTextField queryField;
	private JTable contentTable;
	private JTextField limitFrom;
	private JTextField limitTo;
	private JCheckBox limitCheckBox;
	private JTree dataBaseTree;
	private JScrollPane scrollPaneContentTable;
	private JScrollPane scrollPaneDataBaseTree;
	private JLabel connectionLabel;
	private JMenuBar menuBar;
	private JMenu itemMenu;
	private JMenuItem openMenuItem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 620);
		setResizable(false);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		itemMenu = new JMenu("File");
		menuBar.add(itemMenu);

		openMenuItem = new JMenuItem("Open");
		itemMenu.add(openMenuItem);
		getContentPane().setLayout(null);

		scrollPaneDataBaseTree = new JScrollPane();
		scrollPaneDataBaseTree.setBounds(0, 11, 148, 530);
		getContentPane().add(scrollPaneDataBaseTree);

		dataBaseTree = new JTree();
		scrollPaneDataBaseTree.setViewportView(dataBaseTree);

		queryField = new JTextField();
		queryField.setBounds(158, 11, 626, 20);
		getContentPane().add(queryField);
		queryField.setColumns(10);

		scrollPaneContentTable = new JScrollPane();
		scrollPaneContentTable.setBounds(158, 37, 626, 504);
		getContentPane().add(scrollPaneContentTable);

		contentTable = new JTable();
		contentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneContentTable.setViewportView(contentTable);

		connectionLabel = new JLabel("New label");
		connectionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		connectionLabel.setBounds(660, 541, 124, 27);
		getContentPane().add(connectionLabel);

		limitCheckBox = new JCheckBox("Limit");
		limitCheckBox.setBounds(6, 545, 97, 23);
		getContentPane().add(limitCheckBox);

		limitFrom = new JTextField();
		limitFrom.setBounds(158, 546, 100, 20);
		limitFrom.setText("von");
		getContentPane().add(limitFrom);

		limitTo = new JTextField();
		limitTo.setBounds(268, 546, 100, 20);
		limitTo.setText("bis");
		getContentPane().add(limitTo);

	}
}

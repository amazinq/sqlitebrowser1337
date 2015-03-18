package de.szut.sqlite_browser.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Frame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5836166086363876139L;
	private JTextField commandTextField;
	private JTable dataTable;
	private JTextField limitLowerBoundTextField;
	private JTextField limitUpperBoundTextField;
	private JTree dataBaseTree;

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
		setBounds(100, 100, 868, 582);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem openDataBaseMenuItem = new JMenuItem("Open Database");
		fileMenu.add(openDataBaseMenuItem);
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(new BorderLayout(0, 0));
		JPanel statePanel = new JPanel();
		panel.add(statePanel, BorderLayout.SOUTH);
		statePanel.setLayout(new BorderLayout(0, 0));
		JLabel stateLabel = new JLabel("MySQL Connected ");
		stateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statePanel.add(stateLabel, BorderLayout.NORTH);
		JSplitPane splitPane = new JSplitPane();
		panel.add(splitPane, BorderLayout.CENTER);
		JPanel dataPanel = new JPanel();
		splitPane.setRightComponent(dataPanel);
		dataPanel.setLayout(new BorderLayout(0, 0));
		JPanel limitPanel = new JPanel();
		dataPanel.add(limitPanel, BorderLayout.SOUTH);
		limitPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JCheckBox limitCheckBox = new JCheckBox("Limit: ");
		limitPanel.add(limitCheckBox);
		
		limitLowerBoundTextField = new JTextField();
		limitPanel.add(limitLowerBoundTextField);
		limitLowerBoundTextField.setToolTipText("Set Lower Bounds");
		limitLowerBoundTextField.setColumns(10);
		
		limitUpperBoundTextField = new JTextField();
		limitPanel.add(limitUpperBoundTextField);
		limitUpperBoundTextField.setToolTipText("Set Upper Bounds");
		limitUpperBoundTextField.setColumns(10);
		JPanel commandPanel = new JPanel();
		dataPanel.add(commandPanel, BorderLayout.NORTH);
		commandPanel.setLayout(new BorderLayout(0, 0));
		commandTextField = new JTextField();
		commandPanel.add(commandTextField);
		commandTextField.setColumns(10);
		JScrollPane tableScrollPane = new JScrollPane();
		dataPanel.add(tableScrollPane, BorderLayout.CENTER);
		dataTable = new JTable();
		dataTable.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null }, }, new String[] {
				"New column", "New column", "New column", "New column",
				"New column", "New column" }));
		tableScrollPane.setViewportView(dataTable);
		JScrollPane treeScrollPane = new JScrollPane();
		splitPane.setLeftComponent(treeScrollPane);
		dataBaseTree = new JTree();
		treeScrollPane.setViewportView(dataBaseTree);
	}
}
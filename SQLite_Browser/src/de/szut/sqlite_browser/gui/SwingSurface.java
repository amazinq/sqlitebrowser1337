package de.szut.sqlite_browser.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.szut.sqlite_browser.model.Model;

public class SwingSurface extends JPanel implements Surface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1327566355315618207L;
	private static final String CONNECTED_TEXT = "Connected to Database ";
	private static final String DISCONNECTED_TEXT = "Not connected ";

	private JTextField commandTextField;
	private JTable dataTable;
	private JTextField limitLowerBoundTextField;
	private JTextField limitUpperBoundTextField;
	private JTree dataBaseTree;
	private DefaultMutableTreeNode topNode;
	private Model model;
	private JLabel stateLabel;
	private JScrollPane tableScrollPane;
	private JSplitPane splitPane;
	private boolean connectionEnabled;

	public SwingSurface() {
		connectionEnabled = false;

		setLayout(new BorderLayout(0, 0));
		JPanel statePanel = new JPanel();
		add(statePanel, BorderLayout.SOUTH);
		statePanel.setLayout(new BorderLayout(0, 0));

		stateLabel = new JLabel(DISCONNECTED_TEXT);
		stateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statePanel.add(stateLabel, BorderLayout.NORTH);

		splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(150);

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

		JButton executeQueryButton = new JButton("Execute Query");
		commandPanel.add(executeQueryButton, BorderLayout.EAST);
		executeQueryButton.addActionListener(e -> {
			if(connectionEnabled) {
				if (limitCheckBox.isSelected()) {
					String lowerBound = limitLowerBoundTextField.getText();
					String upperBound = limitUpperBoundTextField.getText();
					if (isAValidNumber(lowerBound, upperBound)) {
						model.executeQuery(commandTextField.getText(), lowerBound, upperBound);
					} else {
	//					ERRORMESSAGE!!! invalid number
					}
				} else {
					model.executeQuery(commandTextField.getText(), null, null);
				}
			} else {
//				ERRORMESSAGE!!! no connection
			}
		});

		tableScrollPane = new JScrollPane();
		dataPanel.add(tableScrollPane, BorderLayout.CENTER);
		dataTable = new JTable();
		dataTable.getTableHeader().setReorderingAllowed(false);
		dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableScrollPane.setViewportView(dataTable);

		JScrollPane treeScrollPane = new JScrollPane();
		splitPane.setLeftComponent(treeScrollPane);

		topNode = new DefaultMutableTreeNode("Database");
		dataBaseTree = new JTree(topNode);
		dataBaseTree.addTreeSelectionListener(e -> {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) dataBaseTree
					.getLastSelectedPathComponent();
			if (connectionEnabled) {
				if (limitCheckBox.isSelected()) {
					String lowerBound = limitLowerBoundTextField.getText();
					String upperBound = limitUpperBoundTextField.getText();
					if (isAValidNumber(lowerBound, upperBound)) {
						if (n.getChildCount() == 0) {
							model.executeQuery("Select * from " + (String) n.getUserObject(), lowerBound, upperBound);
						}
					} else {
//						ERRORMESSAGE!! invalid number
					}
				} else {
					if (n.getChildCount() == 0) {
						model.executeQuery("Select * from " + (String) n.getUserObject(), null, null);
					}
				}
			} else {
//				ERRORMESSAGE!!! no connection
			}
		});
		dataBaseTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeScrollPane.setViewportView(dataBaseTree);
	}

	@Override
	public void updateTree(ArrayList<String> tableNames) {
		for (String name : tableNames) {
			topNode.add(new DefaultMutableTreeNode(name));
		}
		repaint();
	}

	@Override
	public void updateDataList(Object[][] data, String[] columnNames) {
		dataTable.setModel(new DefaultTableModel(data, columnNames));
		repaint();
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public void setConnectionEnabled(boolean connectionEnabled) {
		if (connectionEnabled) {
			stateLabel.setText(CONNECTED_TEXT);
		} else {
			stateLabel.setText(DISCONNECTED_TEXT);
		}
		this.connectionEnabled = connectionEnabled;
	}

	@Override
	public void clearTree() {
		int x = topNode.getChildCount();
		for (int i = 0; i < x; i++) {
			topNode.remove(0);
		}
		dataBaseTree.repaint();
		dataBaseTree.collapsePath(new TreePath(topNode.getPath()));
	}

	private boolean isAValidNumber(String lowerBound, String upperBound) {
		try {
			int tempLowerBound = Integer.parseInt(lowerBound);
			int tempUpperBound = Integer.parseInt(upperBound);
			if (tempLowerBound < 0) {
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

package de.szut.sqlite_browser.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
import javax.swing.tree.TreeSelectionModel;

public class SwingSurface extends JPanel implements Surface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1327566355315618207L;
	private JTextField commandTextField;
	private JTable dataTable;
	private JTextField limitLowerBoundTextField;
	private JTextField limitUpperBoundTextField;
	private JTree dataBaseTree;

	public SwingSurface() {
		setLayout(new BorderLayout(0, 0));
		JPanel statePanel = new JPanel();
		add(statePanel, BorderLayout.SOUTH);
		statePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel stateLabel = new JLabel("MySQL Connected ");
		stateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statePanel.add(stateLabel, BorderLayout.NORTH);
		
		JSplitPane splitPane = new JSplitPane();
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
		dataTable.getTableHeader().setReorderingAllowed(false);
		dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableScrollPane.setViewportView(dataTable);
		
		JScrollPane treeScrollPane = new JScrollPane();
		splitPane.setLeftComponent(treeScrollPane);
		
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Database");
		dataBaseTree = new JTree(topNode);
		dataBaseTree.addTreeSelectionListener(e -> {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode)dataBaseTree.getLastSelectedPathComponent();
			if(n.getChildCount() == 0){
				
			}
		});
		dataBaseTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeScrollPane.setViewportView(dataBaseTree);
	}
	
	@Override
	public void updateTree() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDataList() {
		// TODO Auto-generated method stub
		
	}

}

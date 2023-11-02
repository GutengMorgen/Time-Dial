package com.gutengmorgen.TimeDial;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.gutengmorgen.TimeDial.extras.ShortcutManager;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;

public class Popup extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel content = new JPanel();
	private JTextField textField;
	private final GridBagConstraints constraints = new GridBagConstraints();
	private int rowIndex = 0;
	private JPanel center;
	private GridBagLayout gbl_center = new GridBagLayout();
	private List<JComponent> listComp = new ArrayList<>();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Popup dialog = new Popup();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Popup() {
		setAlwaysOnTop(true);
//		setUndecorated(true);
		setBounds(5, 5, 450, 160);
		setContentPane(content);
		GridBagLayout gbl_content = new GridBagLayout();
		gbl_content.columnWidths = new int[] { 434, 0 };
		gbl_content.rowHeights = new int[] { 261, 0 };
		gbl_content.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_content.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		content.setLayout(gbl_content);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		content.add(scrollPane, gbc_scrollPane);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(225, 225, 225));
		scrollPane.setColumnHeaderView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 22, 104, 76, 28, 70, 22 };
		gbl_panel.rowHeights = new int[] { 20 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel.setLayout(gbl_panel);

		JLabel navHistory = new JLabel("v");
		navHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_navHistory = new GridBagConstraints();
		gbc_navHistory.insets = new Insets(0, 0, 0, 5);
		gbc_navHistory.gridx = 0;
		gbc_navHistory.gridy = 0;
		panel.add(navHistory, gbc_navHistory);

		JLabel descriptionlbl = new JLabel("Description:");
		descriptionlbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_descriptionlbl = new GridBagConstraints();
		gbc_descriptionlbl.anchor = GridBagConstraints.WEST;
		gbc_descriptionlbl.insets = new Insets(0, 0, 0, 5);
		gbc_descriptionlbl.gridx = 1;
		gbc_descriptionlbl.gridy = 0;
		panel.add(descriptionlbl, gbc_descriptionlbl);

		JLabel timelbl = new JLabel("(12:01:01)");
		GridBagConstraints gbc_timelbl = new GridBagConstraints();
		gbc_timelbl.insets = new Insets(0, 0, 0, 5);
		gbc_timelbl.gridx = 2;
		gbc_timelbl.gridy = 0;
		panel.add(timelbl, gbc_timelbl);

		JLabel taglbl = new JLabel("Tag:");
		GridBagConstraints gbc_taglbl = new GridBagConstraints();
		gbc_taglbl.anchor = GridBagConstraints.WEST;
		gbc_taglbl.insets = new Insets(0, 0, 0, 5);
		gbc_taglbl.gridx = 3;
		gbc_taglbl.gridy = 0;
		panel.add(taglbl, gbc_taglbl);

		JLabel tagName = new JLabel("Study");
		GridBagConstraints gbc_tagName = new GridBagConstraints();
		gbc_tagName.anchor = GridBagConstraints.WEST;
		gbc_tagName.insets = new Insets(0, 0, 0, 5);
		gbc_tagName.gridx = 4;
		gbc_tagName.gridy = 0;
		panel.add(tagName, gbc_tagName);

		JLabel navTags = new JLabel("v");
		GridBagConstraints gbc_navTags = new GridBagConstraints();
		gbc_navTags.gridx = 5;
		gbc_navTags.gridy = 0;
		panel.add(navTags, gbc_navTags);

		center = new JPanel();
		center.setBorder(null);
		scrollPane.setViewportView(center);
		gbl_center.rowWeights = new double[] { 0.0 };
		gbl_center.columnWeights = new double[] { 0.0, 1.0 };
		gbl_center.rowHeights = new int[] { 0 };
		gbl_center.columnWidths = new int[] { 0, 0 };
		center.setLayout(gbl_center);
								
								JLabel lblNewLabel = new JLabel("Description:");
								lblNewLabel.setRequestFocusEnabled(false);
								lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 11));
								GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
//								gbc_lblNewLabel.weighty = 1.0;
								gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
								gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
								gbc_lblNewLabel.gridx = 0;
								gbc_lblNewLabel.gridy = 0;
								center.add(lblNewLabel, gbc_lblNewLabel);

								textField = new JTextField();
								textField.setFont(new Font("Verdana", Font.PLAIN, 11));
								textField.setBackground(new Color(243, 243, 243));
								textField.setBorder(new EmptyBorder(5, 5, 5, 0));
								GridBagConstraints gbc_textField = new GridBagConstraints();
								gbc_textField.anchor = GridBagConstraints.NORTH;
								gbc_textField.fill = GridBagConstraints.HORIZONTAL;
								gbc_textField.gridx = 1;
								gbc_textField.gridy = 0;
								center.add(textField, gbc_textField);
								textField.setColumns(10);
								textField.requestFocusInWindow();
								ShortcutManager.saveClose(this, textField);
//		autoFill();
//		closeAutoFill();
	}

	public void saveClose() {
		this.dispose();
	}

	private void autoFill() {
		JTextField field0 = new JTextField();

		JTextField field = new JTextField();
		ShortcutManager.saveClose(this, field);
		
		addComponent("source:", field0);
		addComponent("description:", field);

		closeAutoFill();
	}

	private void addComponent(String name, JComponent comp) {
		constraints.gridx = 0;
		constraints.gridy = rowIndex;
		constraints.insets = new Insets(5, 0, 5, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTH;

		JLabel label = new JLabel(name);
//		label.setOpaque(true);
//		label.setBackground(new Color(225, 225, 225));
		center.add(label, constraints);

		constraints.gridx = 1;
		if (comp instanceof JTextField) {
			comp.setBorder(null);
			comp.setPreferredSize(new Dimension(150, comp.getPreferredSize().height));
		}
		center.add(comp, constraints);

		rowIndex++;
	}

	private void closeAutoFill() {
		rowIndex = 0;
//		center.revalidate();
//		pack();
	}
}

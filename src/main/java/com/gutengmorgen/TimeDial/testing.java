package com.gutengmorgen.TimeDial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;

import com.gutengmorgen.TimeDial.extras.ShortcutManager;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class testing {

	private JWindow window;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testing window = new testing();
					window.window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public testing() {
		initialize();
	}

	private void initialize() {
		window = new JWindow();
		window.setFocusTraversalPolicyProvider(true);
//		window.setAlwaysOnTop(true);
		window.setFocusable(true);
		window.setFocusableWindowState(true);
		window.setBounds(10, 10, 450, 225);
		JScrollPane scrollPane = new JScrollPane();
		window.setContentPane(scrollPane);

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

		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 1;
		panel_1.add(textField, gbc_textField);
		textField.requestFocusInWindow();
		textField.setColumns(10);
		ShortcutManager.saveClose(this, textField);
		
		JTextArea textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 5;
		gbc_textArea.gridy = 2;
		panel_1.add(textArea, gbc_textArea);
	}

	public void saveClose() {
		this.window.dispose();
	}

}

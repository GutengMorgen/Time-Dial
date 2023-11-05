package com.gutengmorgen.TimeDial;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.gutengmorgen.TimeDial.extras.MyTags;
import com.gutengmorgen.TimeDial.extras.ShortcutManager;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.border.EmptyBorder;

public class Popup extends JDialog {
	private static final long serialVersionUID = 1L;

	private final JPanel content = new JPanel();
	private final GridBagConstraints cons = new GridBagConstraints();
	private int rowIndex = 0;
	private JPanel center;
	private GridBagLayout gbl_center = new GridBagLayout();
	private List<JTextField> listComp = new ArrayList<>();
	private JLabel tagName;
	private final DefaultListModel<MyTags> modelTag = new DefaultListModel<>();
	private int indexModelTag = 0;
	private final DefaultListModel<MyTags> modelHistory = new DefaultListModel<>();
	private int indexModelHistory = 0;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Popup dialog = new Popup();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Popup() {
		modelTag.addAll(MyTags.parsingAllLines());
		setAlwaysOnTop(true);
		setUndecorated(true);
		setBounds(5, 5, 450, 160);
		setContentPane(content);
		content.setLayout(new BorderLayout(0, 0));

		JPanel bar = new JPanel();
		bar.setBorder(null);
		bar.setBackground(new Color(225, 225, 225));
		content.add(bar, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		//TODO: ajustar estos parametros
		gbl_panel.columnWidths = new int[] { 22, 104, 76, 28, 70 };
		gbl_panel.rowHeights = new int[] { 20 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0 };
		bar.setLayout(gbl_panel);

		JLabel navHistory = new JLabel("v");
		navHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_navHistory = new GridBagConstraints();
		gbc_navHistory.insets = new Insets(0, 0, 0, 5);
		gbc_navHistory.gridx = 0;
		gbc_navHistory.gridy = 0;
		bar.add(navHistory, gbc_navHistory);

		JLabel descriptionlbl = new JLabel("Description:");
		descriptionlbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_descriptionlbl = new GridBagConstraints();
		gbc_descriptionlbl.anchor = GridBagConstraints.WEST;
		gbc_descriptionlbl.insets = new Insets(0, 0, 0, 5);
		gbc_descriptionlbl.gridx = 1;
		gbc_descriptionlbl.gridy = 0;
		bar.add(descriptionlbl, gbc_descriptionlbl);

		JLabel timelbl = new JLabel("(12:01:01)");
		GridBagConstraints gbc_timelbl = new GridBagConstraints();
		gbc_timelbl.insets = new Insets(0, 0, 0, 5);
		gbc_timelbl.gridx = 2;
		gbc_timelbl.gridy = 0;
		bar.add(timelbl, gbc_timelbl);

		JLabel taglbl = new JLabel("Tag:");
		GridBagConstraints gbc_taglbl = new GridBagConstraints();
		gbc_taglbl.anchor = GridBagConstraints.WEST;
		gbc_taglbl.insets = new Insets(0, 0, 0, 5);
		gbc_taglbl.gridx = 3;
		gbc_taglbl.gridy = 0;
		bar.add(taglbl, gbc_taglbl);

		tagName = new JLabel();
		GridBagConstraints gbc_tagName = new GridBagConstraints();
		gbc_tagName.anchor = GridBagConstraints.WEST;
		gbc_tagName.insets = new Insets(0, 0, 0, 5);
		gbc_tagName.gridx = 4;
		gbc_tagName.gridy = 0;
		bar.add(tagName, gbc_tagName);

		center = new JPanel();
		center.setBorder(null);
		content.add(center, BorderLayout.CENTER);
		center.setLayout(gbl_center);

		autoFill(modelTag.get(indexModelTag));
		closeAutoFill();
	}

	public boolean checkText() {
		boolean flag = false;

		for (JTextField comp : listComp) {
			if (comp.getText().isBlank())
				flag = false;
			else
				flag = true;
		}
		return flag;
	}

	public void saveClose() {
//		if (checkText()) {
		this.dispose();
//		}
	}

	public void autoFill(MyTags myTags) {
		if (!listComp.isEmpty()) {
			center.removeAll();
			center.revalidate();
			center.repaint();
			listComp.clear();
		}
		tagName.setText(myTags.getTagName());

		for (String content : myTags.getTemplateContent()) {
			JTextField textField = new JTextField();
			textField.setBackground(new Color(242, 242, 242));
			textField.setBorder(new EmptyBorder(5, 5, 5, 5));
			ShortcutManager.saveClose(this, textField);
			ShortcutManager.nav(this, textField);
			addComponent(content, textField);
			listComp.add(textField);
		}
		closeAutoFill();
		listComp.get(0).requestFocus();
	}

	private void addComponent(String name, JComponent comp) {
		cons.gridx = 0;
		cons.gridy = rowIndex;
		cons.insets = new Insets(2, 2, 2, 2);
		cons.weightx = 0.0;
		cons.fill = GridBagConstraints.VERTICAL;
		cons.anchor = GridBagConstraints.WEST;

		JLabel label = new JLabel(name);
		center.add(label, cons);

		cons.gridx = 1;
		cons.weightx = 1.0;
		cons.fill = GridBagConstraints.BOTH;
		center.add(comp, cons);
		rowIndex++;
	}

	private void closeAutoFill() {
		rowIndex = 0;
		pack();
	}
	
	public void selectedIndexModel(int event) {
		if(indexModelTag == -1)
			return;
		
		if (event == KeyEvent.VK_UP && indexModelTag > 0)
			indexModelTag--;
		else if (event == KeyEvent.VK_DOWN && modelTag.getSize() > indexModelTag + 1)
			indexModelTag++;

		autoFill(modelTag.get(indexModelTag));
	}
}

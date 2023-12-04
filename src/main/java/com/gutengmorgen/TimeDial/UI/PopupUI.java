package com.gutengmorgen.TimeDial.UI;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.gutengmorgen.TimeDial.extras.ShortcutManager;
import com.gutengmorgen.TimeDial.extras.DateHandler;
import com.gutengmorgen.TimeDial.models.Temporal;
import com.gutengmorgen.TimeDial.models.Bookmark;
import com.gutengmorgen.TimeDial.models.History;
import com.gutengmorgen.TimeDial.models.Model;
import com.gutengmorgen.TimeDial.models.TagTemplate;
import com.gutengmorgen.TimeDial.models.Template;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;

public class PopupUI extends JDialog {
	private static final long serialVersionUID = 1L;

	private final JPanel content = new JPanel();
	private final GridBagConstraints cons = new GridBagConstraints();
	private int rowIndex = 0;
	private JPanel center;
	private JLabel tagName;
	private JLabel timelbl;
	private final JLabel footerlbl = new JLabel(" ");
	public Model<TagTemplate> modelTag = new Model<>(TagTemplate.getAll());
	public Model<Temporal> modelTemp = new Model<>(Temporal.getAll());
	private DateHandler timerHandler = new DateHandler();
	private final ShortcutManager shortcuts = new ShortcutManager(this, footerlbl);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
					PopupUI dialog = new PopupUI();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PopupUI() {
		setTitle("Time Dial Popup");
		setAlwaysOnTop(true);
		setUndecorated(true);
		setBounds(5, 5, 450, 160);
		setContentPane(content);
		content.setLayout(new BorderLayout(0, 0));

		JPanel bar = new JPanel();
		bar.setBorder(null);
		bar.setBackground(new Color(225, 225, 225));
		content.add(bar, BorderLayout.NORTH);
		GridBagLayout gbl_bar = new GridBagLayout();
		gbl_bar.columnWidths = new int[] { 76, 35, 58 };
		gbl_bar.rowHeights = new int[] { 20 };
		gbl_bar.columnWeights = new double[] { 1.0, 0.0, 0.0 };
		bar.setLayout(gbl_bar);

		timelbl = new JLabel();
		timerHandler.setClock(timelbl);
		GridBagConstraints gbc_timelbl = new GridBagConstraints();
		gbc_timelbl.anchor = GridBagConstraints.WEST;
		gbc_timelbl.insets = new Insets(0, 2, 0, 2);
		gbc_timelbl.gridx = 0;
		gbc_timelbl.gridy = 0;
		bar.add(timelbl, gbc_timelbl);

		JLabel taglbl = new JLabel("Tag:");
		GridBagConstraints gbc_taglbl = new GridBagConstraints();
		gbc_taglbl.insets = new Insets(0, 0, 0, 2);
		gbc_taglbl.gridx = 1;
		gbc_taglbl.gridy = 0;
		bar.add(taglbl, gbc_taglbl);

		tagName = new JLabel();
		GridBagConstraints gbc_tagName = new GridBagConstraints();
		gbc_tagName.anchor = GridBagConstraints.WEST;
		gbc_tagName.gridx = 2;
		gbc_tagName.gridy = 0;
		bar.add(tagName, gbc_tagName);

		center = new JPanel();
		center.setBorder(null);
		content.add(center, BorderLayout.CENTER);
		center.setLayout(new GridBagLayout());

		footerlbl.setBorder(new EmptyBorder(0, 2, 0, 2));
		footerlbl.setOpaque(true);
		footerlbl.setBackground(new Color(225, 225, 225));
		content.add(footerlbl, BorderLayout.SOUTH);

		autoFill(modelTag.getValue());
	}

	public boolean checkText() {
		for (Component comp : center.getComponents()) {
			if (comp instanceof JTextField) {
				JTextField field = (JTextField) comp;
				if (field.getText().isBlank())
					return false;
			}
		}
		return true;
	}

	public void saveClose() {
//		if (checkText())
//		saveToBookmark();
//		MainFrame.getInstance().timerHandler.restart();
//		saveDescription();
		this.dispose();
	}

	public void autoFill(TagTemplate data) {
		initAutoFill();

		timerHandler.restartClock();
		tagName.setText(data.getName());

		for (Template template : data.getTemplates()) {
			addComponent(template.getName(), new CustomTextField(template.getHold()));
		}
		closeAutoFill();
	}

	public void autoFill(Temporal data) {
		initAutoFill();

		timerHandler.getTimeElapsed(data.getDateTime());
		tagName.setText(data.getTag());

		for (Template template : data.getTemplates()) {
			addComponent(template.getName(), new CustomTextField(template.getHold()));
		}
		closeAutoFill();
	}

	public void autofill(Bookmark data) {
		initAutoFill();

		timerHandler.restartClock();
		tagName.setText(data.getName());

		for (Template template : data.getTemplates()) {
			addComponent(template.getName(), new CustomTextField(template.getHold()));
		}
		closeAutoFill();
	}

	private void addComponent(String name, JComponent comp) {
		cons.gridx = 0;
		cons.gridy = rowIndex;
		cons.insets = new Insets(2, 2, 2, 2);
		cons.weightx = 0.0;
		cons.fill = GridBagConstraints.VERTICAL;
		cons.anchor = GridBagConstraints.WEST;
		center.add(new JLabel(name), cons);

		cons.gridx = 1;
		cons.weightx = 1.0;
		cons.fill = GridBagConstraints.BOTH;
		center.add(comp, cons);
		rowIndex++;
	}

	private void initAutoFill() {
		center.removeAll();
		center.revalidate();
		center.repaint();
	}

	private void closeAutoFill() {
		rowIndex = 0;
		pack();
		shortcuts.nav((JComponent) center.getComponent(1));
		center.getComponent(1).requestFocus();
	}

	public void saveDescription() {
		String tag = this.tagName.getText();
		String dcp = descriptionFormat();

		Temporal.save(tag, dcp);
		History.save(tag, dcp);
	}
	
	
	private String descriptionFormat() {
		StringBuilder fmt = new StringBuilder();

		for (int i = 0; i < center.getComponentCount(); i++) {
			Component comp = center.getComponent(i);
			if (comp instanceof JLabel lb) {
				fmt.append(lb.getText());
			} else if (comp instanceof JTextField fd) {
				fmt.append(fd.getText());
				if (i != center.getComponentCount() - 1)
					fmt.append(",");
			}
		}
		return fmt.toString();
	}
	
	public String bookmarkFormat(int position) {
		String tagName = this.tagName.getText();
		StringBuilder format = new StringBuilder();
		format.append(position + ";");
		format.append(tagName + ";");

		for (int i = 0; i < center.getComponentCount(); i++) {
			Component comp = center.getComponent(i);
			if (comp instanceof JLabel label) {
				format.append(label.getText());
			} else if (comp instanceof JTextField field) {
				format.append(field.getText());
				if (i != center.getComponentCount() - 1)
					format.append(",");
			}
		}
		return format.toString();
	}

	public void selectedIndexBookmark(int position) {
		Bookmark bookmark = Bookmark.getAll().stream()
				.filter(b -> b.getName().equals(tagName.getText()) && b.getPosition() == position).findFirst()
				.orElse(null);
		if (bookmark != null)
			autofill(bookmark);
	}

	public boolean filter(int position) {
		List<Bookmark> list = Bookmark.getAll();
		Bookmark bookmark = list.stream()
				.filter(b -> b.getName().equals(tagName.getText()) && b.getPosition() == position).findFirst()
				.orElse(null);
		list.indexOf(bookmark);
		if (bookmark == null)
			return true;
		else
			return false;
	}

	public int getIndex(int position) {
		List<Bookmark> list = Bookmark.getAll();
		Bookmark bookmark = list.stream()
				.filter(b -> b.getName().equals(tagName.getText()) && b.getPosition() == position).findFirst()
				.orElse(null);
		if (bookmark == null)
			return -1;
		else
			return list.indexOf(bookmark);
	}
}

@SuppressWarnings("serial")
class CustomTextField extends JTextField {
	public CustomTextField(String text) {
		setText(text);
		setBackground(new Color(242, 242, 242));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setPreferredSize(new Dimension(250, getPreferredSize().height));
	}
}
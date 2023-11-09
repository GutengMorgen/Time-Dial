package com.gutengmorgen.TimeDial;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.gutengmorgen.TimeDial.extras.ShortcutManager;
import com.gutengmorgen.TimeDial.extras.TimerHandler;
import com.gutengmorgen.TimeDial.models.Temporal;
import com.gutengmorgen.TimeDial.parsing.DataManager;
import com.gutengmorgen.TimeDial.models.Bookmark;
import com.gutengmorgen.TimeDial.models.Model;
import com.gutengmorgen.TimeDial.models.Tag;
import com.gutengmorgen.TimeDial.models.Template;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.net.SecureCacheResponse;
import java.util.List;
import java.util.StringJoiner;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.border.EmptyBorder;

public class Popup extends JDialog {
	private static final long serialVersionUID = 1L;

	private final JPanel content = new JPanel();
	private final GridBagConstraints cons = new GridBagConstraints();
	private int rowIndex = 0;
	private JPanel center;
	private JLabel tagName;
	private JLabel timelbl;
	public Model<Tag> modelTag = new Model<>(Tag.parsingAllLines());
	public Model<Temporal> modelTemp = new Model<>(Temporal.parsingAllLines());
	private TimerHandler timerHandler = new TimerHandler();
	private final ShortcutManager shortcuts = new ShortcutManager(this);

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
		setUndecorated(true);
		setBounds(5, 5, 450, 160);
		setContentPane(content);
		content.setLayout(new BorderLayout(0, 0));

		JPanel bar = new JPanel();
		bar.setBorder(null);
		bar.setBackground(new Color(225, 225, 225));
		content.add(bar, BorderLayout.NORTH);
		GridBagLayout gbl_bar = new GridBagLayout();
		// TODO: ajustar estos parametros
		gbl_bar.columnWidths = new int[] { 70, 76, 35, 58 };
		gbl_bar.rowHeights = new int[] { 20 };
		gbl_bar.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		gbl_bar.rowWeights = new double[] { 0.0 };
		bar.setLayout(gbl_bar);

		JLabel descriptionlbl = new JLabel("Description:");
		descriptionlbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_descriptionlbl = new GridBagConstraints();
		gbc_descriptionlbl.gridx = 0;
		gbc_descriptionlbl.gridy = 0;
		bar.add(descriptionlbl, gbc_descriptionlbl);

		timelbl = new JLabel();
		timerHandler.setClock(timelbl);
		GridBagConstraints gbc_timelbl = new GridBagConstraints();
		gbc_timelbl.gridx = 1;
		gbc_timelbl.gridy = 0;
		bar.add(timelbl, gbc_timelbl);

		JLabel taglbl = new JLabel("Tag:");
		GridBagConstraints gbc_taglbl = new GridBagConstraints();
		gbc_taglbl.gridx = 2;
		gbc_taglbl.gridy = 0;
		bar.add(taglbl, gbc_taglbl);

		tagName = new JLabel();
		GridBagConstraints gbc_tagName = new GridBagConstraints();
		gbc_tagName.anchor = GridBagConstraints.WEST;
		gbc_tagName.gridx = 3;
		gbc_tagName.gridy = 0;
		bar.add(tagName, gbc_tagName);

		center = new JPanel();
		center.setBorder(null);
		content.add(center, BorderLayout.CENTER);
		center.setLayout(new GridBagLayout());

		autoFill(modelTag.getValue());
	}

	public boolean checkText() {
		boolean flag = false;

		for (Component comp : center.getComponents()) {
			if (comp instanceof JTextField field) {
				if (field.getText().isBlank())
					flag = false;
				else
					flag = true;
			}
		}
		return flag;
	}

	public void saveClose() {
//		if (checkText())
//		saveToBookmark();
		this.dispose();
	}

	public void autoFill(Tag myTags) {
		center.removeAll();
		center.revalidate();
		center.repaint();

		timerHandler.restartClock();
		tagName.setText(myTags.getName());

		for (Template template : myTags.getTemplates()) {
			JTextField field = new JTextField();
			field.setBackground(new Color(242, 242, 242));
			field.setBorder(new EmptyBorder(5, 5, 5, 5));
			field.setText(template.getHold());
			shortcuts.nav(field);
			addComponent(template.getName(), field);
		}
		closeAutoFill();
		center.getComponent(1).requestFocus();
	}

	private void autoFill(Temporal data) {
		center.removeAll();
		center.revalidate();
		center.repaint();

		timerHandler.getTimeElapsed(data.getDateTime());
		tagName.setText(data.getTag().getName());

		for (Template template : data.getTag().getTemplates()) {
			JTextField field = new JTextField();
			field.setBackground(new Color(242, 242, 242));
			field.setBorder(new EmptyBorder(5, 5, 5, 5));
			field.setText(template.getHold());
			shortcuts.nav(field);
			addComponent(template.getName(), field);
		}
		closeAutoFill();
		center.getComponent(1).requestFocus();
	}

	private void autofill(Bookmark bookmark) {
		center.removeAll();
		center.revalidate();
		center.repaint();

		timerHandler.restartClock();
		tagName.setText(bookmark.getTag().getName());

		for (Template template : bookmark.getTag().getTemplates()) {
			JTextField field = new JTextField();
			field.setBackground(new Color(242, 242, 242));
			field.setBorder(new EmptyBorder(5, 5, 5, 5));
			field.setText(template.getHold());
			shortcuts.nav(field);
			addComponent(template.getName(), field);
		}
		closeAutoFill();
		center.getComponent(1).requestFocus();
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

	private void closeAutoFill() {
		rowIndex = 0;
		pack();
	}

	public void selectedIndexModel() {
		autoFill(modelTag.getValue());
	}

	public void selectedIndexTemp() {
		autoFill(modelTemp.getValue());
	}

	@Deprecated
	public void selectedIndex(int event, Model<?> model) {
		if (event == KeyEvent.VK_RIGHT || event == KeyEvent.VK_UP) {
			model.reduceIndex();
		} else if (event == KeyEvent.VK_LEFT || event == KeyEvent.VK_DOWN) {
			model.increaseIndex();
		}
//		autoFill(model.getValue());
	}

	public void selectedIndexBookmark(int position) {
		Bookmark bookmark = Bookmark.parsing().stream()
				.filter(b -> b.getTag().getName().equals(tagName.getText()) && b.getPosition() == position).findFirst()
				.orElse(null);
		if (bookmark != null)
			autofill(bookmark);
	}

	public void saveToBookmark() {
		String tagName = this.tagName.getText();
		StringBuilder format = new StringBuilder();
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

		DataManager.appendToFile(format.toString(), DataManager.BOOKMARK);
	}

	public String formatToBookmark(int position) {
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

	/**
	 * return true == no existe ningun objeto en la posicion dada y se pude
	 * settear/escribir un nuevo objeto return false == existe un objecto en la
	 * posicion dada y no se puede escribir
	 * 
	 * @param position
	 * @return
	 */
	public boolean filter(int position) {
		List<Bookmark> list = Bookmark.parsing();
		Bookmark bookmark = list.stream()
				.filter(b -> b.getTag().getName().equals(tagName.getText()) && b.getPosition() == position).findFirst()
				.orElse(null);
		list.indexOf(bookmark);
		if (bookmark == null)
			return true;
		else
			return false;
	}

	public int getIndex(int position) {
		List<Bookmark> list = Bookmark.parsing();
		Bookmark bookmark = list.stream()
				.filter(b -> b.getTag().getName().equals(tagName.getText()) && b.getPosition() == position).findFirst()
				.orElse(null);
		if (bookmark == null)
			return -1;
		else
			return list.indexOf(bookmark);
	}
}

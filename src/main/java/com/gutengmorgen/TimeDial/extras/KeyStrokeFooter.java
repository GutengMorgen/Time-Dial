package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.gutengmorgen.TimeDial.UI.PopupUI;
import com.gutengmorgen.TimeDial.models.Bookmark;

@SuppressWarnings("serial")
public class KeyStrokeFooter {
	private final PopupUI popup;
	private final JTextField footer;
	private final int altgrCode = KeyEvent.ALT_GRAPH_DOWN_MASK;
	private final int shiftCode = KeyEvent.SHIFT_DOWN_MASK;
	private final int[] keycodes = { KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5,
			KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11,
			KeyEvent.VK_F12 };
	private final String setBm = "sbm";
	private final String getBm = "gbm";
	private final String overrideBm = "obm";

	public KeyStrokeFooter(PopupUI popup, JTextField footer) {
		this.popup = popup;
		this.footer = footer;

		footer.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "save");
		footer.getActionMap().put("save", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.saveClose();
			}
		});

		footer.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "tagUp");
		footer.getActionMap().put("tagUp", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTag.reduceIndex();
				popup.autoFill(popup.modelTag.getValue());
			}
		});

		footer.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "tagDown");
		footer.getActionMap().put("tagDown", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTag.increaseIndex();
				popup.autoFill(popup.modelTag.getValue());
			}
		});

		footer.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "HistoryUp");
		footer.getActionMap().put("HistoryUp", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTemp.reduceIndex();
				popup.autoFill(popup.modelTemp.getValue());
			}
		});

		footer.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "HistoryDown");
		footer.getActionMap().put("HistoryDown", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTemp.increaseIndex();
				popup.autoFill(popup.modelTemp.getValue());
			}
		});

		// set shortcuts for bookmark
		for (byte i = 0; i < keycodes.length; i++) {
			changeKeyStroke(i, getBm);
			changeKeyStroke(i, setBm);
			changeKeyStroke(i, overrideBm);
		}
	}

	private void changeKeyStroke(final byte i, String actionCommand) {
		KeyStroke stroke = null;
		Runnable runnable = null;

		switch (actionCommand) {
		case getBm:
			stroke = KeyStroke.getKeyStroke(keycodes[i], 0);
			runnable = () -> getBookmark(i);
			break;
		case setBm:
			stroke = KeyStroke.getKeyStroke(keycodes[i], altgrCode);
			runnable = () -> setBookmark(i);
			break;
		case overrideBm:
			stroke = KeyStroke.getKeyStroke(keycodes[i], altgrCode | shiftCode);
			runnable = () -> overrideBookmark(i);
			break;
		}

		addAction(stroke, actionCommand + i, runnable);
	}

	private void addAction(KeyStroke stroke, String actionCommand, Runnable action) {
		footer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, actionCommand);
		footer.getActionMap().put(actionCommand, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				action.run();
			}
		});
	}

	private void getBookmark(byte index) {
		System.out.println("getting");
		if (popup.filter(index)) {
			popup.selectBookmark(index);
			footer.setText("Retrieve bookmark at index " + index);
		} else
			footer.setText("No bookmark at index " + index);
	}

	private void setBookmark(byte index) {
		System.out.println("setting");
		if (popup.checkText() && !popup.filter(index)) {
			Bookmark.createBookmark(index, popup.tagName.getText(), popup.descriptionFormat());
			footer.setText("Set new Bookmark at index " + index);
		} else
			footer.setText("Bookmark already exist at index " + index);
	}

	private void overrideBookmark(byte index) {
		System.out.println("override");
		if (popup.checkText()) {
			Bookmark.updateBookmark(index, popup.tagName.getText(), popup.descriptionFormat());
			footer.setText("Override bookmark at index " + index);
		} else
			footer.setText("Cannot override empty bookmark");
	}
}

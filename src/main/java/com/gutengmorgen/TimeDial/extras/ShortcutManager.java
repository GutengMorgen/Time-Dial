package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import com.gutengmorgen.TimeDial.UI.PopupUI;
import com.gutengmorgen.TimeDial.parsing.DataManager;

@SuppressWarnings("serial")
public class ShortcutManager {
	private final PopupUI popup;
	private final JLabel footer;
	private final int ctrlCode = KeyEvent.CTRL_DOWN_MASK;
	private final int shiftCode = KeyEvent.SHIFT_DOWN_MASK;
	private final int[] keycodes = { KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5,
			KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11,
			KeyEvent.VK_F12 };
	private final String setBookmark = "setBkm";
	private final String getBookmark = "getBkm";
	private final String overrideBookmark = "overBkm";

	public ShortcutManager(PopupUI popup, JLabel footer) {
		this.popup = popup;
		this.footer = footer;
	}

	public void nav(JComponent comp) {
		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "save");
		comp.getActionMap().put("save", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.saveClose();
			}
		});

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, ctrlCode), "tagUp");
		comp.getActionMap().put("tagUp", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTag.reduceIndex();
				popup.autoFill(popup.modelTag.getValue());
			}
		});

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, ctrlCode), "tagDown");
		comp.getActionMap().put("tagDown", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTag.increaseIndex();
				popup.autoFill(popup.modelTag.getValue());
			}
		});

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ctrlCode), "HistoryUp");
		comp.getActionMap().put("HistoryUp", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTemp.reduceIndex();
				popup.autoFill(popup.modelTemp.getValue());
			}
		});

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ctrlCode), "HistoryDown");
		comp.getActionMap().put("HistoryDown", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTemp.increaseIndex();
				popup.autoFill(popup.modelTemp.getValue());
			}
		});

		mappingGetBookmark(comp);
		mappingSetBookmark(comp);
		mappingOverrideBookmark(comp);
	}

	private void mappingGetBookmark(JComponent comp) {
		for (int i = 0; i < keycodes.length; i++) {
			final int index = i;
			KeyStroke stroke = KeyStroke.getKeyStroke(keycodes[i], 0);
			comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, getBookmark + i);
			comp.getActionMap().put(getBookmark + i, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!popup.filter(index)) {
						popup.selectedIndexBookmark(index);
						footer.setText("Get bookmark: " + index);
					} else {
						footer.setText("Bookmark in index: " + index + " doesnt exist.");
					}
				}
			});
		}
	}

	private void mappingSetBookmark(JComponent comp) {
		for (int i = 0; i < keycodes.length; i++) {
			final int index = i;
			KeyStroke stroke = KeyStroke.getKeyStroke(keycodes[i], ctrlCode);
			comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, setBookmark + i);
			comp.getActionMap().put(setBookmark + i, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (popup.checkText() && popup.filter(index)) {
						String format = popup.bookmarkFormat(index);
						DataManager.appendToFile(format, DataManager.BOOKMARK);
						footer.setText("Set bookmark in index: " + index);
					} else {
						footer.setText("Bookmark all ready exist in index: " + index);
					}
				}
			});
		}
	}

	private void mappingOverrideBookmark(JComponent comp) {
		for (int i = 0; i < keycodes.length; i++) {
			final int index = i;
			KeyStroke stroke = KeyStroke.getKeyStroke(keycodes[i], ctrlCode | shiftCode);
			comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, overrideBookmark + i);
			comp.getActionMap().put(overrideBookmark + i, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (popup.checkText()) {
						String format = popup.bookmarkFormat(index);
						DataManager.updateLine(popup.getIndex(index), format, DataManager.BOOKMARK);
						footer.setText("Override bookmark in index: " + index);
					} else {
						footer.setText("Can not override bookmark with empty holds");
					}
				}
			});
		}
	}
}

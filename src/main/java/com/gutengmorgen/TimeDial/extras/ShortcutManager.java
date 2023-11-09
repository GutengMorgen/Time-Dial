package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.gutengmorgen.TimeDial.Popup;
import com.gutengmorgen.TimeDial.parsing.DataManager;

@SuppressWarnings("serial")
public class ShortcutManager {
	private final Popup popup;
	private final int ctrlCode = KeyEvent.CTRL_DOWN_MASK;
	private final int shiftCode = KeyEvent.SHIFT_DOWN_MASK;
	private final int[] keycodes = { KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5,
			KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11,
			KeyEvent.VK_F12 };
	private final String setBookmark = "setBkm";
	private final String getBookmark = "getBkm";
	private final String overrideBookmark = "overBkm";

	public ShortcutManager(Popup popup) {
		this.popup = popup;
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
				popup.selectedIndexModel();
			}
		});

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, ctrlCode), "tagDown");
		comp.getActionMap().put("tagDown", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTag.increaseIndex();
				popup.selectedIndexModel();
			}
		});

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ctrlCode), "HistoryUp");
		comp.getActionMap().put("HistoryUp", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTemp.reduceIndex();
				popup.selectedIndexTemp();
			}
		});

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ctrlCode), "HistoryDown");
		comp.getActionMap().put("HistoryDown", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.modelTemp.increaseIndex();
				popup.selectedIndexTemp();
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
					System.out.println("get" + index);
					popup.selectedIndexBookmark(index);
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
						System.out.println("set " + index);
						String format = popup.formatToBookmark(index);
						DataManager.appendToFile(format, DataManager.BOOKMARK);
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
						System.out.println("Override " + index);
						String format = popup.formatToBookmark(index);
						DataManager.updateLine(popup.getIndex(index), format, DataManager.BOOKMARK);
					}
				}
			});
		}
	}
}

package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.gutengmorgen.TimeDial.Popup;

public class ShortcutManager {
	private final Popup popup;
	private final int ctrlCode = KeyEvent.CTRL_DOWN_MASK;
	private final int[] keycodes = { KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5,
			KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11,
			KeyEvent.VK_F12 };
	private final String keyString = "setBookmark";

	public ShortcutManager(Popup popup) {
		this.popup = popup;
	}

	public void nav(JComponent comp) {
		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "save");
		comp.getActionMap().put("save", new AbstractAction() {
			private static final long serialVersionUID = 2265741248768672941L;

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.saveClose();
			}
		});

		comp.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP, KeyEvent.VK_DOWN:
					popup.selectedIndexModel(e.getKeyCode());
					break;
				case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT:
					popup.selectedIndexTemp(e.getKeyCode());
					break;
				case KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6,
						KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11,
						KeyEvent.VK_F12:
							for (int i = 0; i < keycodes.length; i++) {
								if(e.getKeyCode() == keycodes[i]) {
									popup.selectedIndexBookmark(i);
								}
							}
					break;
				default:
					break;
				}
			}
		});

			mappingShortcuts(comp);
	}

	@SuppressWarnings("serial")
	private void mappingShortcuts(JComponent comp) {
		for (int i = 0; i < keycodes.length; i++) {
			final int index = i;
			KeyStroke stroke = KeyStroke.getKeyStroke(keycodes[i], ctrlCode);
			comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(stroke, keyString + i);
			comp.getActionMap().put(keyString + i, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("hello" + index);
					popup.saveToBookmark(index);
				}
			});
		}

	}
}

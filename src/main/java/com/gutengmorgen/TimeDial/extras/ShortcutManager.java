package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.gutengmorgen.TimeDial.Popup;

public class ShortcutManager {
	public static void nav(Popup popup, JComponent comp) {
		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveAction");
		comp.getActionMap().put("saveAction", new AbstractAction() {
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
					popup.selectedIndexBookmark(getNumberByKeyCode(e.getKeyCode()));
					break;
				default:
					break;
				}
			}
		});
	}

	public static int getNumberByKeyCode(int keyCode) {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(KeyEvent.VK_F1, 0);
		map.put(KeyEvent.VK_F2, 1);
		map.put(KeyEvent.VK_F3, 2);
		map.put(KeyEvent.VK_F4, 3);
		map.put(KeyEvent.VK_F5, 4);
		map.put(KeyEvent.VK_F6, 5);
		map.put(KeyEvent.VK_F7, 6);
		map.put(KeyEvent.VK_F8, 7);
		map.put(KeyEvent.VK_F9, 8);
		map.put(KeyEvent.VK_F10, 9);
		map.put(KeyEvent.VK_F11, 10);
		map.put(KeyEvent.VK_F12, 11);
		return map.getOrDefault(keyCode, -1);
	}
}

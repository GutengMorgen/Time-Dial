package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.gutengmorgen.TimeDial.Popup;

public class ShortcutManager {

	public static void saveClose(Popup window, JComponent comp) {
		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveAction");
		comp.getActionMap().put("saveAction", new AbstractAction() {
			private static final long serialVersionUID = 2265741248768672941L;

			@Override
			public void actionPerformed(ActionEvent e) {
				window.saveClose();
			}
		});
	}
	
	public static void nav(Popup popup, JComponent comp ) {
		comp.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP , KeyEvent.VK_DOWN:
					popup.selectedIndexModel(e.getKeyCode());
					break;
				case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT:
					System.out.println("navHistory");
					break;
				default:
					break;
				}
			}
		
		});
	}
}

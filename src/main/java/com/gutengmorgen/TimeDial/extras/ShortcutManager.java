package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
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
	
	public static void navTags(Popup popup, JComponent comp) {
		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_DOWN_MASK), "downTag");
		comp.getActionMap().put("downTag", new AbstractAction() {
			private static final long serialVersionUID = -8397549138049913878L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("down");
				popup.selectedindex(KeyEvent.VK_DOWN);
			}
		});
		
		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK), "upTag");
		comp.getActionMap().put("upTag", new AbstractAction() {
			private static final long serialVersionUID = -6832583136590414379L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("up");
				popup.selectedindex(KeyEvent.VK_UP);
			}
		});
	}
}

package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.gutengmorgen.TimeDial.Popup;
import com.gutengmorgen.TimeDial.testing;

public class ShortcutManager {
	public static void saveClose(testing window, JComponent comp) {
		Action saveAction = new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.saveClose();
			}
		};

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveAction");
		comp.getActionMap().put("saveAction", saveAction);
	}
	
	public static void saveClose(Popup window, JComponent comp) {
		Action saveAction = new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.saveClose();
			}
		};

		comp.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveAction");
		comp.getActionMap().put("saveAction", saveAction);
	}
}

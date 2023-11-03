package com.gutengmorgen.TimeDial.extras;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.gutengmorgen.TimeDial.Popup;

public class MenuOptions {
	private final Popup father;
	private final JComponent ancestor;
	private final JWindow window;

	public MenuOptions(Popup popup, JComponent ancestor) {
		this.father = popup;
		this.ancestor = ancestor;

		DefaultListModel<MyTags> model = new DefaultListModel<>();
		model.addAll(MyTags.readAllLines());
		JList<MyTags> jList = new JList<>(model);
		jList.setBorder(new EmptyBorder(2, 2, 2, 2));

		window = new JWindow(SwingUtilities.getWindowAncestor(father));
		window.setType(Window.Type.POPUP);
		window.setFocusableWindowState(false);
		window.setAlwaysOnTop(true);
		window.add(jList);

		ancestor.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				show();
			}

			@Override
			public void focusLost(FocusEvent e) {
				hide();
			}

		});
		ancestor.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int index = jList.getSelectedIndex();

				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if (index != -1 && index > 0)
						jList.setSelectedIndex(index - 1);
					break;
				case KeyEvent.VK_DOWN:
					if (index != -1 && jList.getModel().getSize() > index + 1)
						jList.setSelectedIndex(index + 1);
					break;
				case KeyEvent.VK_ENTER:
					father.autoFill(jList.getSelectedValue());
					break;
				case KeyEvent.VK_ESCAPE:
					hide();
					break;
				default:
					break;
				}
			}
		});
	}

	public void show() {
		final Point p = ancestor.getLocationOnScreen();
		window.setLocation(p.x, p.y + ancestor.getHeight());
		window.pack();
		window.setVisible(true);
	}

	private void hide() {
		window.setVisible(false);
	}
}

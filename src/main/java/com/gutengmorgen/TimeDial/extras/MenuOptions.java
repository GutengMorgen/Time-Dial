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
	public final JWindow window;
	private final JList<MyTags> list;

	public MenuOptions(Popup popup, JComponent ancestor) {
		this.father = popup;
		this.ancestor = ancestor;

		DefaultListModel<MyTags> model = new DefaultListModel<>();
//		model.addAll(MyTags.readAllLines());
		list = new JList<>(model);
		list.setBorder(new EmptyBorder(2, 2, 2, 2));

		window = new JWindow(SwingUtilities.getWindowAncestor(father));
		window.setType(Window.Type.POPUP);
		window.setFocusableWindowState(false);
		window.setAlwaysOnTop(true);
		window.add(list);

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
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP, KeyEvent.VK_DOWN:
					selectedindex(e.getKeyCode());
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

	public void selectedindex(int event) {
		int index = list.getSelectedIndex();
		System.out.println("index " + index);

		if (index == -1)
			return;

		if (event == KeyEvent.VK_UP && index > 0)
			index--;
		else if (event == KeyEvent.VK_DOWN && list.getModel().getSize() > index + 1)
			index++;

		list.setSelectedIndex(index);
		father.autoFill(list.getSelectedValue());
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

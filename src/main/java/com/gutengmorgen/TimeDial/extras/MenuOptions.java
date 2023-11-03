package com.gutengmorgen.TimeDial.extras;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.gutengmorgen.TimeDial.Popup;

public class MenuOptions {
	private final Popup father;
	private final JComponent ancestor;
//	private final List<MyTags> result;
	private JList<MyTags> jList;
	private JWindow window;
	private DefaultListModel<MyTags> model;

	public MenuOptions(Popup popup, JComponent ancestor) {
		this.father = popup;
		this.ancestor = ancestor;
		DefaultFunctions();
	}

	private void DefaultFunctions() {
		model = new DefaultListModel<>();
		model.addAll(MyTags.readAllLines());
//		model.addElement(new MyTags("working", new String[] {"Company:", "Task:"}));
		jList = new JList<>(model);

		window = new JWindow(SwingUtilities.getWindowAncestor(father));
		window.setType(Window.Type.POPUP);
		window.setFocusableWindowState(false);
		window.setAlwaysOnTop(true);
		window.add(new JScrollPane(jList) {
			private static final long serialVersionUID = -2387472513291367137L;

			@Override
			public Dimension getPreferredSize() {
				final Dimension ps = super.getPreferredSize();
				ps.width = father.getWidth();
				return ps;
			}
		});

		ancestor.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
//				if (result.size() > 0)
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
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					final int index = jList.getSelectedIndex();
					if (index != -1 && index > 0) {
						jList.setSelectedIndex(index - 1);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					final int index = jList.getSelectedIndex();
					if (index != -1 && jList.getModel().getSize() > index + 1) {
						jList.setSelectedIndex(index + 1);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					father.autoFill(jList.getSelectedValue());
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					hide();
				}
			}

		});
	}

	public void show() {
		final Point p = ancestor.getLocationOnScreen();
		window.setLocation(p.x, p.y + ancestor.getHeight());
		window.setSize(50,100);
//		window.pack();
		window.setVisible(true);
//		window.toFront();
	}
	
	private void hide() {
		window.setVisible(false);
	}
}

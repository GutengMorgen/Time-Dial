package com.gutengmorgen.TimeDial;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.gutengmorgen.TimeDial.extras.TimerHandler;
import com.gutengmorgen.TimeDial.parsing.DataManager;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemTray;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static MainFrame instance;
	private JPanel contentPane;
	private JToggleButton toggleBtn;
	private JLabel description = new JLabel(" ");
	public final TimerHandler timerHandler;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static MainFrame getInstance() {
		if(instance == null)
			instance = new MainFrame();
		return instance;
	}
	
	public MainFrame() {
		instance = this;
		timerHandler = new TimerHandler(description, 1);
		setTitle("Time Dial");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/icon.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,420);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowIconified(WindowEvent e) {
				if(SystemTray.isSupported())
					TrayUI.minimizeToTray(MainFrame.this);
				else
					MainFrame.this.dispose();
			}
		
		});
		centerFrameOnScreen(this);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{115, 52, 0};
		gbl_panel.rowHeights = new int[]{45, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		toggleBtn = new JToggleButton("Popup is turn off");
		toggleBtn.setFocusable(false);
		toggleBtn.setFocusPainted(false);
		GridBagConstraints gbc_tglbtnNewToggleButton = new GridBagConstraints();
		gbc_tglbtnNewToggleButton.fill = GridBagConstraints.BOTH;
		gbc_tglbtnNewToggleButton.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnNewToggleButton.gridx = 0;
		gbc_tglbtnNewToggleButton.gridy = 0;
		panel.add(toggleBtn, gbc_tglbtnNewToggleButton);
		toggleBtn.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					timerHandler.runPopup();
					toggleBtn.setText("Popup is turn on");
					description.setText("Popup is running");
				} else {
					toggleBtn.setText("Popup is turn off");
					timerHandler.stop();
					description.setText(" ");
				}
				
			}
		});
		
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(description, gbc_lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{376, 72, 0};
		gbl_panel_2.rowHeights = new int[]{23, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblNewLabel = new JLabel("HISTORY");
		lblNewLabel.setInheritsPopupMenu(false);
		lblNewLabel.setFocusable(false);
		lblNewLabel.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);
		
		JButton btnNewButton = new JButton("refresh");
		btnNewButton.setFocusable(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		panel_2.add(btnNewButton, gbc_btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
	}

	private static void centerFrameOnScreen(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);
	}
}

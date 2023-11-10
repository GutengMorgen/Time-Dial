package com.gutengmorgen.TimeDial;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import javax.swing.JFrame;

public class TrayUI {
    private static PopupMenu popupMenu;
	private static MenuItem restoreItem;
	private static MenuItem exitItem;
	
    private static Image iconImage;
	private static TrayIcon trayIcon;
    private static SystemTray tray;

	public static void minimizeToTray(JFrame frame) {
        try {
            if(tray == null && trayIcon == null) {
            	setMenu(frame);
            	setOnce(frame);
            }
            
            frame.setVisible(false);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }
	
	private static void setMenu(JFrame frame) {
		popupMenu = new PopupMenu();
		restoreItem = new MenuItem("Restore");
		exitItem = new MenuItem("Exit");
		
		popupMenu.add(restoreItem);
        popupMenu.add(exitItem);
        
        restoreItem.addActionListener(e -> {
        	frame.setVisible(true);
        	frame.setState(JFrame.NORMAL);
        });
        exitItem.addActionListener(e -> System.exit(0));
	}
	
	private static void setOnce(JFrame frame) throws AWTException {
    	tray = SystemTray.getSystemTray();

		iconImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/icon.jpg");
    	trayIcon = new TrayIcon(iconImage, "Time Dial", popupMenu);
        trayIcon.setImageAutoSize(true);
        tray.add(trayIcon);
	}
}

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
    private PopupMenu popupMenu;
	private MenuItem restoreItem;
	private MenuItem exitItem;
    private Image iconImage;
	private TrayIcon trayIcon;
    private SystemTray tray;
    private JFrame frame;

    public TrayUI(JFrame frame){
    	if(!SystemTray.isSupported()) {
    		System.out.println("system tray is not support");
    		return;
    	}
    	
    	this.frame = frame;
    	
    	tray = SystemTray.getSystemTray();
    	iconImage = frame.getIconImage();
    	
		popupMenu = new PopupMenu();
		restoreItem = new MenuItem("Restore");
		exitItem = new MenuItem("Exit");
        restoreItem.addActionListener(e -> {
        	frame.setVisible(true);
        	frame.setState(JFrame.NORMAL);
        });
        exitItem.addActionListener(e -> System.exit(0));
		popupMenu.add(restoreItem);
        popupMenu.add(exitItem);
        
        trayIcon = new TrayIcon(iconImage, "System Tray Time Dial", popupMenu);
        trayIcon.setImageAutoSize(true);
    }
    
    public void add() {
    	try {
			tray.add(trayIcon);
			frame.setVisible(false);
		} catch (AWTException e) {
			System.out.println("Unable to add trayIcon to SystemTray");
		}
    }
    
    public void remove() {
    	tray.remove(trayIcon);
    }
    
    public void setIcon(int num) {
    	String imageUrl = "src/main/resources/timer/" + num +".jpg";
    	System.out.println(imageUrl);
    	trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
    }
    /*
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
        exitItem.addActionListener(e -> frame.dispose());
	}
	
	private static void setOnce(JFrame frame) throws AWTException {
    	tray = SystemTray.getSystemTray();

		iconImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/icon.jpg");
    	trayIcon = new TrayIcon(iconImage, "Time Dial", popupMenu);
        trayIcon.setImageAutoSize(true);
        tray.add(trayIcon);
	}*/
}

package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.gutengmorgen.TimeDial.PopupUI;

import lombok.Getter;
import lombok.Setter;

public class TimerHandler {
	@Getter
	@Setter
	private int periodMinutes;
	private int minutes = 0;
	private int seconds = 0;
	private final Timer timer;
	private final static String format = "Time to appear: %02d: %02d";

	public TimerHandler(JLabel out, int periodMinutes) {
		this.periodMinutes = periodMinutes;
		minutes = getPeriodMinutes();
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (minutes == 0 && seconds == 0) {
					stop();
					runPopup();
					out.setText("Popup is running");
				} else {
					if (seconds == 0) {
						minutes--;
						seconds = 59;
					} else {
						seconds--;
					}
					out.setText(String.format(format, minutes, seconds));
				}

			}
		});
	}

	public void runPopup() {
		new PopupUI().setVisible(true);
	}

	public void restart() {
		if (!timer.isRunning()) {
			timer.restart();
		}
	}
	
	public void stop() {
		if(timer != null && timer.isRunning()) {
			timer.stop();
			minutes = getPeriodMinutes();
			seconds = 0;
		}
	}
}

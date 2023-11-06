package com.gutengmorgen.TimeDial.extras;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

import javax.swing.JLabel;
import javax.swing.Timer;

public class TimerHandler {
	private final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
	private Timer clock;
	private JLabel label;

	private LocalDateTime singleDateTime() {
		return LocalDateTime.now();
	}

	public void setClock(JLabel label) {
		this.label = label;
		label.setText(singleDateTime().format(format));

		clock = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(singleDateTime().format(format));
			}
		});

		clock.start();
	}

	public void restartClock() {
		if (!clock.isRunning()) {
			label.setText(singleDateTime().format(format));
			clock.restart();
		}
	}

	public void getTimeElapsed(LocalDateTime oldDate) {
		if (clock.isRunning()) {
			clock.stop();
//			clock = null;
		}
		Duration duration = Duration.between(oldDate, singleDateTime());

		long days = duration.toDays();
		long hours = duration.toHours() % 24;
		long minutes = duration.toMinutes() % 60;

		StringJoiner joiner = new StringJoiner(" ");
		joiner.add("from:");
		if (days > 0)
			joiner.add(days + " days,");
		if (hours > 0)
			joiner.add(hours + " hours,");
		if (minutes > 0)
			joiner.add(minutes + " minutes");

		label.setText(joiner.toString());
	}
}

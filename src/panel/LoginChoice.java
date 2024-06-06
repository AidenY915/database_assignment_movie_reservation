package panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import frame.MovieReservationFrame;

public class LoginChoice extends MovieReservationPanel {
	public LoginChoice() {
		super();
		init();
	}

	@Override
	public void init() {
		setLayout(null);
		JLabel titleLabel = new JLabel("영화 예매 프로그램");

		titleLabel.setFont(new Font(getFont().getName(), Font.BOLD, 25));

		add(titleLabel);

		titleLabel.setBounds(370, 40, 400, 50);

		ToAdminButton toAdminButton = new ToAdminButton();
		ToUserButton toUserButton = new ToUserButton();
		add(toAdminButton);
		add(toUserButton);

		toAdminButton.setBounds(175, 300, 200, 100);
		toUserButton.setBounds(570, 300, 200, 100);

	}

}

class ToAdminButton extends JButton {
	ToAdminButton() {
		super("관리자 로그인");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
				frame.changePanel(frame.getAdminLoginPanel());

			}
		});
	}
}

class ToUserButton extends JButton {
	ToUserButton() {
		super("일반 사용자 로그인");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
				frame.changePanel(frame.getUserLoginPanel());
			}
		});
	}
}

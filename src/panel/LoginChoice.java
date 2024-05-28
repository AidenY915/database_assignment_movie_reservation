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
		JLabel titleLabel = new JLabel("로그인 선택");
		titleLabel.setBounds(440, 20, 300, 50);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 15));
		titleLabel.setForeground(Color.BLUE);

		add(titleLabel);

		titleLabel.setBounds(440, 40, 100, 50);

		ToAdminButton toAdminButton = new ToAdminButton();
		ToUserButton toUserButton = new ToUserButton();
		add(toAdminButton);
		add(toUserButton);

		toAdminButton.setBounds(200, 100, 150, 100);
		toUserButton.setBounds(600, 100, 150, 100);

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

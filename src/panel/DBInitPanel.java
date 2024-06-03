package panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.Service;
import frame.MovieReservationFrame;

public class DBInitPanel extends MovieReservationPanel {
	private Service service = Service.getService();;

	@Override
	public void init() {
		removeAll();
		setLayout(null);

		JLabel titleLabel = new JLabel("데이터베이스 초기화");
		titleLabel.setBounds(400, 50, 200, 50);
		add(titleLabel);

		JButton initButton = new JButton("초기화 실행");
		initButton.setBounds(400, 150, 200, 50);
		initButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean result = service.initializeDatabase();
				if (result) {
					JOptionPane.showMessageDialog(null, "데이터베이스 초기화 성공!");
				} else {
					JOptionPane.showMessageDialog(null, "데이터베이스 초기화 실패.");
				}
				MovieReservationFrame.getMovieReservationFrame().changePanel(new AdminMainPanel());
			}
		});
		add(initButton);
	}
}

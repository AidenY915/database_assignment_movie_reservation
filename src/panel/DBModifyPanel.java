package panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.Service;

public class DBModifyPanel extends MovieReservationPanel {
	private Service service = Service.getService();

	@Override
	public void init() {
		removeAll();
		setLayout(null);

		JLabel titleLabel = new JLabel("SQL 입력");
		titleLabel.setBounds(400, 50, 200, 50);
		add(titleLabel);

		JTextArea sqlInputArea = new JTextArea();
		sqlInputArea.setBounds(100, 150, 800, 200);
		add(sqlInputArea);

		JButton executeButton = new JButton("실행");
		executeButton.setBounds(400, 400, 200, 50);
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql = sqlInputArea.getText();
				boolean result = service.executeSQL(sql);
				if (result) {
					JOptionPane.showMessageDialog(null, "SQL 실행 성공!");
				} else {
					JOptionPane.showMessageDialog(null, "SQL 실행 실패.");
				}
			}
		});
		add(executeButton);
	}
}

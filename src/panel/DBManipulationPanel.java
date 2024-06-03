package panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import service.Service;

public class DBManipulationPanel extends MovieReservationPanel {

	private JTextArea sqlArea;
	private Service service = Service.getService();

	public DBManipulationPanel() {
		super();
		init();
	}

	@Override
	public void init() {
		removeAll();
		setLayout(null);

		JLabel titleLabel = new JLabel("SQL 입력 페이지");
		titleLabel.setBounds(400, 50, 200, 50);
		add(titleLabel);

		sqlArea = new JTextArea();
		sqlArea.setBounds(100, 150, 800, 400);
		add(sqlArea);

		JButton executeButton = new JButton("SQL 실행");
		executeButton.setBounds(400, 600, 200, 50);
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql = sqlArea.getText();
				boolean success = service.executeSQL(sql);
				if (success) {
					JOptionPane.showMessageDialog(null, "SQL이 성공적으로 실행되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "SQL 실행에 실패했습니다.");
				}
			}
		});
		add(executeButton);
	}
}

package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frame.MovieReservationFrame;
import service.Service;

public class DataInputPanel extends MovieReservationPanel {
	private JTextField[] inputFields;
	private String[] currentColumns;
	private JComboBox<String> tableComboBox;
	private JPanel inputPanel;
	private Service service = Service.getService();

	public DataInputPanel() {
		super();
		init();
	}

	@Override
	public void init() {
		removeAll();
		setLayout(null);

		JLabel titleLabel = new JLabel("데이터 입력 페이지");
		titleLabel.setBounds(440, 20, 200, 50);
		add(titleLabel);

		String[] tables = { "movie", "actor", "casting", "screening_schedule", "screening_hall", "seat", "user",
				"booking", "movie_ticket" };
		tableComboBox = new JComboBox<>(tables);
		tableComboBox.setBounds(400, 60, 200, 30);
		add(tableComboBox);

		inputPanel = new JPanel();
		inputPanel.setBounds(300, 100, 480, 300);
		add(inputPanel);

		JButton saveButton = new JButton("저장");
		saveButton.setBounds(400, 410, 100, 30);
		add(saveButton);

		JButton cancelButton = new JButton("취소");
		cancelButton.setBounds(510, 410, 100, 30);
		add(cancelButton);

		tableComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateInputFields((String) tableComboBox.getSelectedItem());
			}
		});

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveData();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				for (JTextField field : inputFields) {
					field.setText("");
				}
			}
		});

		updateInputFields((String) tableComboBox.getSelectedItem());
	}

	private void updateInputFields(String tableName) {
		inputPanel.removeAll();
		switch (tableName) {
		case "movie":
			currentColumns = new String[] { "movie_name (char(45))", "running_time (int)", "age_rating (int)",
					"director_name (char(20))", "Genre (char(45))", "release_date (date)", "movie_info (text)",
					"rating_information (decimal(3,1))" };
			break;
		case "actor":
			currentColumns = new String[] { "actor_name (char(20))" };
			break;
		case "casting":
			currentColumns = new String[] { "actor_no (int)", "movie_no (int)" };
			break;
		case "screening_schedule":
			currentColumns = new String[] { "hall_no (int)", "screening_date (date)", "screening_day (char(3))",
					"screening_session (int)", "screening_start_time (time)", "movie_no (int)" };
			break;
		case "screening_hall":
			currentColumns = new String[] { "standard_price (int)", "hall_name (char(20))" };
			break;
		case "seat":
			currentColumns = new String[] { "hall_no (int)", "seat_no (char(6))" };
			break;
		case "user":
			currentColumns = new String[] { "user_id (char(30))", "user_name (char(30))", "phone_no (char(11))",
					"email (char(100))", "password (char(30))", "is_admin (tinyint)" };
			break;
		case "booking":
			currentColumns = new String[] { "payment_method (char(20))", "payment_status (char(20))",
					"payment_amount (int)", "schedule_no (int)", "seat_no (char(3))", "user_id (char(30))" };
			break;
		case "movie_ticket":
			currentColumns = new String[] { "ticket_no (int)", "booking_no (int)" };
			break;
		default:
			currentColumns = new String[] { "movie_name (char(45))", "running_time (int)", "age_rating (int)",
					"director_name (char(20))", "Genre (char(45))", "release_date (date)", "movie_info (text)",
					"rating_information (decimal(3,1))" };
			break;
		}

		inputPanel.setLayout(new GridLayout(currentColumns.length, 2));
		inputFields = new JTextField[currentColumns.length];
		for (int i = 0; i < currentColumns.length; i++) {
			inputPanel.add(new JLabel(currentColumns[i]));
			inputFields[i] = new JTextField();
			inputPanel.add(inputFields[i]);
		}
		revalidate();
		repaint();
	}

	private void saveData() {
		String[] values = new String[inputFields.length];
		for (int i = 0; i < inputFields.length; i++) {
			values[i] = inputFields[i].getText();
		}
		if (service.insertData((String) tableComboBox.getSelectedItem(), currentColumns, values)) {
			JOptionPane.showMessageDialog(this, "데이터가 저장되었습니다.");
		} else {
			JOptionPane.showMessageDialog(this, "데이터 저장에 실패하였습니다.");
		}
	}
}

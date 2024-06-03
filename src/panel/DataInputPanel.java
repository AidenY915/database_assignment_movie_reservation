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
		titleLabel.setBounds(400, 20, 200, 50);
		add(titleLabel);

		String[] tables = { "선택", "movie", "actor", "casting", "screening_schedule", "screening_hall", "seat", "user",
				"booking", "movie_ticket" };
		tableComboBox = new JComboBox<>(tables);
		tableComboBox.setBounds(400, 50, 200, 30);
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
			currentColumns = new String[] { "movie_name", "running_time", "age_rating", "director_name", "Genre",
					"release_date", "movie_info", "rating_information" };
			break;
		case "actor":
			currentColumns = new String[] { "actor_name" };
			break;
		case "casting":
			currentColumns = new String[] { "actor_no", "movie_no" };
			break;
		case "screening_schedule":
			currentColumns = new String[] { "hall_no", "screening_date", "screening_day", "screening_session",
					"screening_start_time", "movie_no" };
			break;
		case "screening_hall":
			currentColumns = new String[] { "standard_price", "hall_name" };
			break;
		case "seat":
			currentColumns = new String[] { "hall_no", "seat_no" };
			break;
		case "user":
			currentColumns = new String[] { "user_id", "user_name", "phone_no", "email", "password", "is_admin" };
			break;
		case "booking":
			currentColumns = new String[] { "payment_method", "payment_status", "payment_amount", "schedule_no",
					"seat_no", "user_id" };
			break;
		case "movie_ticket":
			currentColumns = new String[] { "ticket_no", "booking_no" };
			break;
		default:
			currentColumns = new String[] { "movie_name", "running_time", "age_rating", "director_name", "Genre",
					"release_date", "movie_info", "rating_information" };
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

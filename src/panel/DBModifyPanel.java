package panel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.Service;

public class DBModifyPanel extends MovieReservationPanel {
	private Service service = Service.getService();
	private JComboBox<String> updateTableComboBox;
	private JComboBox<String> deleteTableComboBox;
	private JTextField setInputField;
	private JTextField whereInputField;
	private JTextField deleteWhereField;

	@Override
	public void init() {
		removeAll();
		setLayout(null);

		JLabel titleLabel = new JLabel("데이터베이스 수정/삭제");
		titleLabel.setBounds(440, 20, 200, 30);
		add(titleLabel);

		JLabel updateSectionLabel = new JLabel(" UPDATE 구역 ");
		updateSectionLabel.setBounds(100, 60, 85, 27);
		updateSectionLabel.setBorder(new LineBorder(Color.BLACK));
		add(updateSectionLabel);

		JLabel updateLabel = new JLabel("UPDATE");
		updateLabel.setBounds(100, 100, 100, 30);
		add(updateLabel);

		updateTableComboBox = new JComboBox<>(new String[] { "movie", "casting", "actor", "screening_schedule",
				"screening_hall", "seat", "booking", "movie_ticket", "user" });
		updateTableComboBox.setBounds(200, 100, 200, 30);
		add(updateTableComboBox);

		JLabel setLabel = new JLabel("SET");
		setLabel.setBounds(100, 140, 50, 30);
		add(setLabel);

		setInputField = new JTextField();
		setInputField.setBounds(200, 140, 540, 30);
		add(setInputField);

		JLabel whereLabel = new JLabel("WHERE");
		whereLabel.setBounds(100, 180, 50, 30);
		add(whereLabel);

		whereInputField = new JTextField();
		whereInputField.setBounds(200, 180, 540, 30);
		add(whereInputField);

		JButton updateButton = new JButton("수정");
		updateButton.setBounds(800, 160, 100, 30);

		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String table = (String) updateTableComboBox.getSelectedItem();
				String set = setInputField.getText();
				String where = whereInputField.getText();
				String sql = "UPDATE " + table + " SET " + set + " WHERE " + where;
				executeSQL(sql);
			}
		});
		add(updateButton);

		JLabel deleteSectionLabel = new JLabel(" DELETE 구역 ");
		deleteSectionLabel.setBounds(100, 240, 85, 27);
		add(deleteSectionLabel);
		deleteSectionLabel.setBorder(new LineBorder(Color.BLACK));
		JLabel deleteLabel = new JLabel("DELETE FROM");
		deleteLabel.setBounds(100, 280, 100, 30);
		add(deleteLabel);

		deleteTableComboBox = new JComboBox<>(new String[] { "movie", "casting", "actor", "screening_schedule",
				"screening_hall", "seat", "booking", "movie_ticket", "user" });
		deleteTableComboBox.setBounds(200, 280, 200, 30);
		add(deleteTableComboBox);

		JLabel deleteWhereLabel = new JLabel("WHERE");
		deleteWhereLabel.setBounds(100, 320, 50, 30);
		add(deleteWhereLabel);

		deleteWhereField = new JTextField();
		deleteWhereField.setBounds(200, 320, 540, 30);
		add(deleteWhereField);

		JButton deleteButton = new JButton("삭제");
		deleteButton.setBounds(800, 320, 100, 30);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String table = (String) deleteTableComboBox.getSelectedItem();
				String where = deleteWhereField.getText();
				String sql = "DELETE FROM " + table + " WHERE " + where;
				executeSQL(sql);
			}
		});
		add(deleteButton);
	}

	private void executeSQL(String sql) {
		boolean result = service.executeSQL(sql);
		if (result) {
			JOptionPane.showMessageDialog(null, "SQL 실행 성공!");
		} else {
			JOptionPane.showMessageDialog(null, "SQL 실행 실패.");
		}
	}
}

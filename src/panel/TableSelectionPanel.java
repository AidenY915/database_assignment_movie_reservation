package panel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import frame.MovieReservationFrame;

public class TableSelectionPanel extends MovieReservationPanel {

    private String[] tables = {"movie", "actor", "casting", "screening_schedule", "screening_hall", "seat", "booking", "movie_ticket", "user"};
    private JComboBox<String> tableComboBox;

    public TableSelectionPanel() {
        super();
        init();
    }

    @Override
    public void init() {
        removeAll();
        setLayout(null);

        JLabel titleLabel = new JLabel("테이블 선택 페이지");
        titleLabel.setBounds(440, 50, 200, 50);
        add(titleLabel);

        tableComboBox = new JComboBox<>(tables);
        tableComboBox.setBounds(400, 150, 200, 30);
        add(tableComboBox);

        JButton viewButton = new JButton("테이블 데이터 보기");
        viewButton.setBounds(400, 200, 200, 50);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTable = (String) tableComboBox.getSelectedItem();
                MovieReservationFrame.getMovieReservationFrame().changePanel(new TableViewPanel(selectedTable));
            }
        });
        add(viewButton);
    }
}

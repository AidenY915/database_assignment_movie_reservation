package panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import frame.MovieReservationFrame;

public class AdminMainPanel extends MovieReservationPanel {

    @Override
    public void init() {
        removeAll();
        setLayout(null);

        JLabel titleLabel = new JLabel("관리자 메인 페이지");
        titleLabel.setBounds(400, 20, 200, 50);
        add(titleLabel);

        JButton dbInitButton = new JButton("데이터베이스 초기화");
        dbInitButton.setBounds(350, 100, 300, 50);
        dbInitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovieReservationFrame.getMovieReservationFrame().changePanel(new DBInitPanel());
            }
        });
        add(dbInitButton);
        JButton dataInputButton = new JButton("데이터 입력");
        dataInputButton.setBounds(350, 200, 300, 50);
        dataInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
                frame.changePanel(new DataInputPanel());
            }
        });
        add(dataInputButton);

        JButton dbModifyButton = new JButton("데이터 삭제/변경");
        dbModifyButton.setBounds(350, 300, 300, 50);
        dbModifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovieReservationFrame.getMovieReservationFrame().changePanel(new DBModifyPanel());
            }
        });
        add(dbModifyButton);

        JButton viewTableButton = new JButton("전체 테이블 보기");
        viewTableButton.setBounds(350, 400, 300, 50);
        viewTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovieReservationFrame.getMovieReservationFrame().changePanel(new TableSelectionPanel());
            }
        });
        add(viewTableButton);
    }
}

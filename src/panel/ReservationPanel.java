package panel;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.BookingDTO;
import dto.MovieDTO;
import dto.ScreeningScheduleDTO;
import frame.MovieReservationFrame;
import service.Service;

public class ReservationPanel extends MovieReservationPanel {

    private Service service = Service.getService();
    private List<MovieDTO> movieList;
    private JComboBox<MovieDTO> comboBox;
    private MovieDTO selectedMovie;
    private SchedulePanel schedulePanel = new SchedulePanel();
    private ScreeningScheduleDTO selectedSchedule;
    private boolean isEditMode = false;
    private boolean isScheduleEdit = false;
    private BookingDTO bookingToEdit;

    @Override
    public void init() {
        removeAll();
        setLayout(null);
        comboBox = new JComboBox<>();
        movieList = service.getAllMovies();
        for (MovieDTO movie : movieList) {
            comboBox.addItem(movie);
        }
        if (selectedMovie != null)
            comboBox.setSelectedItem(selectedMovie);

        SubmitBtn submitBtn = new SubmitBtn(this);
        JLabel movieSelectLabel = new JLabel("영화 선택");
        JLabel scheduleSelectLabel = new JLabel("상영 일정 선택");
        
        add(comboBox);
        add(movieSelectLabel);
        add(schedulePanel);
        add(scheduleSelectLabel);
//        add(submitBtn);

        comboBox.setBounds(300, 200, 400, 50);
        comboBox.addActionListener(new SelectMovieListener(this));
        movieSelectLabel.setBounds(440, 115, 400, 50);
        movieSelectLabel.setFont(new Font(this.getFont().getName(), Font.BOLD, 20));
        scheduleSelectLabel.setBounds(440, 380, 400, 50);
        scheduleSelectLabel.setFont(new Font(this.getFont().getName(), Font.BOLD, 20));
//        submitBtn.setBounds(350, 700, 300, 50);

        if (!isEditMode) {
            selectedMovie = (MovieDTO) comboBox.getSelectedItem();
        }
        addSchedule();
    }

    void addSchedule() {
        if (comboBox == null)
            return; // ComboBox가 초기화되지 않은 경우
        schedulePanel.removeAll();
        selectedMovie = (MovieDTO) comboBox.getSelectedItem();
        List<ScreeningScheduleDTO> scheduleList = service.getScheduleListByMovieNo(selectedMovie);
        scheduleList.sort((o1, o2) -> {
            int rslt = o1.getScreeningDate().compareTo(o2.getScreeningDate());
            if (rslt != 0)
                return rslt;
            rslt = o1.getScreeningStartTime().compareTo(o2.getScreeningStartTime());
            return rslt;
        });
        for (ScreeningScheduleDTO schedule : scheduleList) {
            if (schedule.getScreeningDate().compareTo(MovieReservationFrame.TODAY) < 0)
                continue;
            ScheduleBtn scheduleBtn = new ScheduleBtn(schedule, this);
            schedulePanel.add(scheduleBtn);
        }
        schedulePanel.revalidate();
        schedulePanel.repaint();
    }

    void moveToSeatSelection() {
        MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
        SeatSelectionPanel seatSelectionPanel = (SeatSelectionPanel) frame.getSeatSelectionPanel();
        seatSelectionPanel.setSelectedSchedule(selectedSchedule);
        if (isEditMode) {
            seatSelectionPanel.setBookingToEdit(bookingToEdit);
        }
        frame.changePanel(seatSelectionPanel);
    }

    public void setSelectedMovie(MovieDTO movie) {
        this.selectedMovie = movie;
        if (comboBox != null) {
            comboBox.setSelectedItem(movie);
            addSchedule();
        }
    }

    class SelectMovieListener implements ActionListener {
        private ReservationPanel reservationPanel;

        SelectMovieListener(ReservationPanel reservationPanel) {
            this.reservationPanel = reservationPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            reservationPanel.addSchedule();
        }
    }

    public void setEditMode(BookingDTO booking, boolean isScheduleEdit) {
        this.isEditMode = true;
        this.isScheduleEdit = isScheduleEdit;
        this.bookingToEdit = booking;
        if (comboBox != null) { // comboBox가 초기화된 경우에만 처리
            if (isScheduleEdit) {
                int movieNo = service.getMovieNoByScheduleNo(booking.getScheduleNo());
                selectedMovie = service.getMovieByNo(movieNo);
                comboBox.setSelectedItem(selectedMovie);
                addSchedule();
            } else {
                selectedMovie = service.getMovieByNo(booking.getScheduleNo());
                comboBox.setSelectedItem(selectedMovie);
                addSchedule();
            }
        }
    }

    class SchedulePanel extends JPanel {
        SchedulePanel() {
            setBounds(100,465, 800, 300);
            setLayout(new FlowLayout());
        }
    }

    class ScheduleBtn extends JButton {
        private ScreeningScheduleDTO scheduleDTO;
        private ReservationPanel reservationPanel;

        public ScheduleBtn(ScreeningScheduleDTO scheduleDTO, ReservationPanel reservationPanel) {
        	String[] dateStrArr = scheduleDTO.getScreeningDate().toString().split("-");
        	String dateForm = dateStrArr[0] + "년 " + dateStrArr[1] + "월 " + dateStrArr[2] + "일";
        	String text = "<html><body>" + dateForm + " " + scheduleDTO.getScreeningStartTime().toString().substring(0, 5);
        	text += "<br>상영관: " + scheduleDTO.getHallName() + "</body></html>";
            setText(text);
            this.scheduleDTO = scheduleDTO;
            this.reservationPanel = reservationPanel;
            addActionListener(new SelectScheduleListener());
        }

        class SelectScheduleListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservationPanel.selectedSchedule = scheduleDTO;
                reservationPanel.moveToSeatSelection();	
            }
        }
    }

    class SubmitBtn extends JButton {
        ReservationPanel reservationPanel;

        SubmitBtn(ReservationPanel reservationPanel) {
            super("좌석 선택하기");
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reservationPanel.moveToSeatSelection();
                }
            });
        }
    }
}

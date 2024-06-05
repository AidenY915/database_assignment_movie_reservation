package panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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

		add(comboBox);
		add(schedulePanel);
		add(submitBtn);

		comboBox.setBounds(10, 100, 200, 50);
		comboBox.addActionListener(new SelectMovieListener(this));
		submitBtn.setBounds(350, 700, 300, 50);

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
		frame.changePanel(seatSelectionPanel);
	}

	public void setSelectedMovie(MovieDTO movie) {
		this.selectedMovie = movie;
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
		this.bookingToEdit = booking;
		if (isScheduleEdit) {
			int movieNo = service.getMovieNoByScheduleNo(booking.getScheduleNo());
			selectedMovie = service.getMovieByNo(movieNo);
			comboBox.setSelectedItem(selectedMovie);
			addSchedule();
			System.out.println(bookingToEdit.getBookingNo());
		} else {

		}
	}

	class SchedulePanel extends JPanel {
		SchedulePanel() {
			setBounds(100, 300, 800, 300);
			setLayout(new FlowLayout());
		}
	}

	class ScheduleBtn extends JButton {
		private ScreeningScheduleDTO scheduleDTO;
		private ReservationPanel reservationPanel;

		public ScheduleBtn(ScreeningScheduleDTO scheduleDTO, ReservationPanel reservationPanel) {
			super(scheduleDTO.getScreeningDate().toString() + " "
					+ scheduleDTO.getScreeningStartTime().toString().substring(0, 5));
			this.scheduleDTO = scheduleDTO;
			this.reservationPanel = reservationPanel;
			addActionListener(new SelectScheduleListener());
		}

		class SelectScheduleListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				reservationPanel.selectedSchedule = scheduleDTO;
				if (reservationPanel.isEditMode) {
					boolean success = service.updateBookingSchedule(reservationPanel.bookingToEdit.getBookingNo(),
							scheduleDTO.getScheduleNo());
					if (success) {
						reservationPanel.moveToSeatSelection();
					} else {
						JOptionPane.showMessageDialog(reservationPanel, "상영 일정 변경에 실패했습니다.");
					}
				} else {
					reservationPanel.moveToSeatSelection();
				}
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

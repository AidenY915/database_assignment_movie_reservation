package panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import dto.BookingDTO;
import dto.UserDTO;
import frame.MovieReservationFrame;
import service.Service;

public class MyBookingListPanel extends MovieReservationPanel {
	private Service service = Service.getService();
	private List<BookingDTO> bookingList;
	private BookingListScrollPane currentBookingListScrollPane;

	@Override
	public void init() {
		removeAll();

		setLayout(null);

		JLabel titleLabel = new JLabel("내 예약 목록");
		add(titleLabel);
		titleLabel.setBounds(440, 20, 200, 50);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
		titleLabel.setForeground(Color.BLUE);

		MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
		UserDTO loginUser = frame.getLoginSession();
		if (loginUser != null) {
			loadBookings(loginUser.getId());
		} else {
			// 로그인 안되어 있으면 예약 목록 없음
			JLabel noLoginLabel = new JLabel("로그인이 필요합니다.");
			noLoginLabel.setBounds(440, 100, 200, 50);
			add(noLoginLabel);
		}
	}

	private void loadBookings(String userId) {
		bookingList = service.getBookingByUserId(userId);
		if (currentBookingListScrollPane != null) {
			remove(currentBookingListScrollPane);
		}
		currentBookingListScrollPane = new BookingListScrollPane(bookingList);
		add(currentBookingListScrollPane);
		revalidate();
		repaint();
	}

	class BookingListScrollPane extends JScrollPane {
		List<BookingDTO> bookingList;
		JPanel panel;

		public BookingListScrollPane(List<BookingDTO> bookingList) {
			super();
			this.bookingList = bookingList;
			setSize(980, 480);
			setLocation(4, 100);
			setBackground(Color.BLUE);
			setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

			panel = new JPanel();
			panel.setPreferredSize(new Dimension(960, bookingList.size() * 100));
			panel.setLayout(null);

			setViewportView(panel);

			int i = 0;
			for (BookingDTO booking : bookingList) {
				addBookingPanel(booking, i);
				i++;
			}
		}

		public void addBookingPanel(BookingDTO booking, int index) {
			BookingPanel bookingPanel = new BookingPanel(booking);
			bookingPanel.setBounds(0, index * 100, 960, 100);
			panel.add(bookingPanel);
		}

		public void refreshBookingList() {
			panel.removeAll();
			panel.setPreferredSize(new Dimension(960, bookingList.size() * 100));
			int i = 0;
			for (BookingDTO booking : bookingList) {
				addBookingPanel(booking, i);
				i++;
			}
			panel.revalidate();
			panel.repaint();
		}
	}

	class BookingPanel extends JPanel {
		private BookingDTO booking;
		private Service service = Service.getService();

		public BookingPanel(BookingDTO booking) {
			this.booking = booking;
			setLayout(null);
			setBorder(BorderFactory.createLineBorder(Color.BLACK));

			JLabel bookingNoLabel = new JLabel("예약 번호: " + booking.getBookingNo());
			JLabel movieNameLabel = new JLabel("영화 제목: " + booking.getMovieName());
			JLabel seatNoLabel = new JLabel("좌석 번호: " + booking.getSeatNo());
			JLabel screeningTimeLabel = new JLabel("상영 시간: " + booking.getScreeningStartTime().toString());
			JLabel paymentStatusLabel = new JLabel("결제 상태: " + booking.getPaymentStatus());
			JLabel paymentAmountLabel = new JLabel("결제 금액: " + booking.getPaymentAmount());
			JLabel paymentMethodLabel= new JLabel("결제 방법: "+ booking.getPaymentMethod());

			bookingNoLabel.setBounds(10, 10, 200, 20);
			movieNameLabel.setBounds(10, 30, 200, 20);
			seatNoLabel.setBounds(10, 50, 200, 20);
			screeningTimeLabel.setBounds(10, 70, 200, 20);
			paymentStatusLabel.setBounds(220, 10, 200, 20);
			paymentAmountLabel.setBounds(220, 30, 200, 20);
			paymentMethodLabel.setBounds(220,50,200,20);

			add(bookingNoLabel);
			add(movieNameLabel);
			add(seatNoLabel);
			add(screeningTimeLabel);
			add(paymentStatusLabel);
			add(paymentAmountLabel);
			add(paymentMethodLabel);

			JButton editButton = new JButton("수정");
			editButton.setBounds(760, 50, 80, 30);
			add(editButton);

			editButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String[] options = { "영화 변경", "상영 일정 변경", "취소" };
					int choice = JOptionPane.showOptionDialog(BookingPanel.this, "원하는 작업을 선택하세요.", "예매 수정",
							JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

					if (choice == 0) {
						editMovie();
					} else if (choice == 1) {
						editSchedule();
					}

				}
			});

			JButton deleteButton = new JButton("삭제");
			deleteButton.setBounds(850, 50, 80, 30);
			add(deleteButton);

			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deleteBooking();
				}
			});
			
			JButton issueTicketButton = new JButton("티켓 발급");
			issueTicketButton.setBounds(640, 50, 110, 30);
			add(issueTicketButton);
			
			issueTicketButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					service.issueTicket(booking);
				}
			});
			
			if(booking.getPaymentStatus().equals("미결제")) {
				JButton paymentButton = new JButton("결제하기");
				paymentButton.setBounds(520, 50, 110, 30);
				add(paymentButton);
				paymentButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
						PaymentPanel paymentPanel = (PaymentPanel)frame.getPaymentPanel();
						List<BookingDTO> bookingList = new LinkedList<>();
						bookingList.add(booking);
						paymentPanel.setBookings(bookingList);
						frame.changePanel(paymentPanel);
					}
				});
			}
			
		}

		private void editSchedule() {
		    MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
		    ReservationPanel reservationPanel = (ReservationPanel) frame.getReservationPanel();
		    reservationPanel.setEditMode(booking, true); // 상영 일정 변경
			System.out.println(booking);
		    frame.changePanel(reservationPanel);
		}

		private void editMovie() {
		    MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
		    MovieListPanel movieListPanel = (MovieListPanel) frame.getMovieListPanel();
		    movieListPanel.setEditMode(booking); // 영화 변경
		    System.out.println(booking);
		    frame.changePanel(movieListPanel);
		}


		private void deleteBooking() {
			String[] options = { "삭제", "취소" };
			int deleteCheck = JOptionPane.showOptionDialog(this, "정말 이 예매를 취소하시겠습니까?", "예매 취소",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			if (deleteCheck == 0) {
				boolean success = service.deleteBooking(booking.getBookingNo());
				if (success) {
					JOptionPane.showMessageDialog(this, "예매가 성공적으로 삭제되었습니다.");
					bookingList.remove(booking);
					BookingListScrollPane scrollPane = (BookingListScrollPane) SwingUtilities
							.getAncestorOfClass(BookingListScrollPane.class, this);
					if (scrollPane != null) {
						scrollPane.refreshBookingList();
					}
				} else {
					JOptionPane.showMessageDialog(this, "예매 삭제에 실패했습니다.");
				}
			}
		}
	}
}

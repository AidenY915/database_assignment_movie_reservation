package panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import dto.BookingDTO;
import dto.ScreeningScheduleDTO;
import dto.SeatDTO;
import frame.MovieReservationFrame;
import service.Service;

public class SeatSelectionPanel extends MovieReservationPanel {
	private ScreeningScheduleDTO selectedSchedule;
	List<SeatDTO> selectedSeats;
	private Service service = Service.getService();
	private List<SeatDTO> seatList;

	@Override
	public void init() {
		removeAll();
		setSeatList();
		selectedSeats = new LinkedList<SeatDTO>();

		setLayout(null);

		JButton submitBtn = new SubmitBtn(this);
		JLabel screenLabel = new ScreenLabel();
		JPanel seatPanel = new JPanel();
		seatPanel.setLayout(new FlowLayout());

		add(screenLabel);
		add(submitBtn);
		add(seatPanel);

		submitBtn.setBounds(350, 700, 300, 50);
		seatPanel.setBounds(100, 300, 800, 300);
		screenLabel.setLocation(100, 100);
		
		drawSeat(seatPanel);
	}

	private void setSeatList() {
		seatList = service.getAllSeatsByHallNo(selectedSchedule.getHallNo());
		List<SeatDTO> unBookedSeatList = service.getUnbookedSeatsBySchedule(selectedSchedule);
		seatList.sort(null);
		unBookedSeatList.sort(null);
		System.out.println(seatList);
		System.out.println(unBookedSeatList);
		for (SeatDTO seat : unBookedSeatList) {
			seatList.get(seatList.indexOf(seat)).setBooked(false);
		}
	}

	private void drawSeat(JPanel seatPanel) {
		for (int i = 0; i < seatList.size(); i++) {
			SeatDTO seat = seatList.get(i);
			seatPanel.add(new SeatBtn(seat, this));

		}
	}

	public void setSelectedSchedule(ScreeningScheduleDTO selectedSchedule) {
		this.selectedSchedule = selectedSchedule;
	}

	public void selectSeat(SeatDTO seat) {
		selectedSeats.add(seat);
	}
	public void cancelSeat(SeatDTO seat) {
		selectedSeats.remove(seat);
	}

	public void submit() {
		service.reserve(selectedSchedule ,selectedSeats);
		MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
		List<BookingDTO> bookings = service.getUnpaidBookingList(selectedSchedule ,selectedSeats);
		for(BookingDTO booking : bookings) {
			service.deleteTicket(booking);
		}
		PaymentPanel paymentPanel = (PaymentPanel)frame.getPaymentPanel();
		paymentPanel.setBookings(bookings);
		frame.changePanel(paymentPanel);
//		예약전 부킹 만들어야 함.
//		부킹을 넘겨줘야함.
	}

	class SubmitBtn extends JButton {
		SeatSelectionPanel thisPanel;

		public SubmitBtn(SeatSelectionPanel thisPanel) {
			super("결제하기");
			this.thisPanel = thisPanel;
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					thisPanel.submit();
				}
			});
		}

	}

	class SeatBtn extends JButton {
		private SeatDTO seat;
		private SeatSelectionPanel thisPanel;
		private Color defaultColor;
		SeatBtn(SeatDTO seat ,SeatSelectionPanel thisPanel) {
			super(seat.getSeatNo());
			this.thisPanel = thisPanel;
			this.seat = seat;
			defaultColor = seat.isBooked() ? Color.RED : Color.GRAY;
			setBackground(defaultColor);
			setForeground(Color.WHITE);
			setPreferredSize(new Dimension(75, 45));
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(seat.isBooked()) return;
					if(thisPanel.selectedSeats.contains(seat)) {
						thisPanel.cancelSeat(seat);
						((SeatBtn)e.getSource()).setBackground(defaultColor);
					}else {
						thisPanel.selectSeat(seat);
						((SeatBtn)e.getSource()).setBackground(Color.GREEN);
					}
						
				}
			});

		}
	}
	class ScreenLabel extends JLabel {
		ScreenLabel(){
			super("Screen");
			setSize(800,50);
			setHorizontalAlignment(CENTER);
			setFont(new Font(Font.SERIF ,Font.PLAIN, 24));
			setBorder(new LineBorder(Color.BLACK));
		}
	}
}

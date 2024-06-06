package panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import dto.BookingDTO;
import service.Service;

public class BookingDetailPanel extends MovieReservationPanel {
	private BookingDTO booking;
	private Service service = Service.getService();
	@Override
	public void init() {
		removeAll();
		setLayout(null);
		JPanel contentPanel = new JPanel();
		contentPanel.setBounds(200,50,600,600);
		contentPanel.setBorder(new LineBorder(Color.BLACK));
		contentPanel.setLayout(null);
		add(contentPanel);
		
		
		String[] dateStrArr = booking.getScreeningDate().toString().split("-");
    	String dateForm = dateStrArr[0] + "년 " + dateStrArr[1] + "월 " + dateStrArr[2] + "일";
        String startTimeStr = dateForm + " " + booking.getScreeningStartTime().toString().substring(0, 5);
		
		JLabel title = new JLabel("예매 상세");
		JLabel bookingNoLabel = new JLabel("예약 번호: " + booking.getBookingNo());
		JLabel paymentMethodLabel = new JLabel("결제 수단: " + booking.getPaymentMethod());
		JLabel paymentStatusLabel = new JLabel("결제 상태: " + booking.getPaymentStatus());
		JLabel paymentAmountLabel = new JLabel("결제 금액: " + booking.getPaymentAmount());
		JLabel paymentDateLabel = new JLabel("결제일: " + booking.getPaymentDate());
		JLabel seatNoLabel = new JLabel("좌석 번호: " + booking.getSeatNo());
		JLabel movieNameLabel = new JLabel("영화 제목: " + booking.getMovieName());
		JLabel screeningStartTimeLabel = new JLabel("상영 시작 시간: " + startTimeStr);
		JLabel hallNameLabel = new JLabel("상영관: " + booking.getHallName());
		JLabel ticketIssueLabel = new JLabel("티켓 발급 여부: " + (service.isTicketIssued(booking) ? "발급완료" : "미발급" ));
		
		contentPanel.add(title);
		contentPanel.add(bookingNoLabel);
		contentPanel.add(paymentMethodLabel);
		contentPanel.add(paymentStatusLabel);
		contentPanel.add(paymentAmountLabel);
		contentPanel.add(paymentDateLabel);
		contentPanel.add(seatNoLabel);
		contentPanel.add(movieNameLabel);
		contentPanel.add(screeningStartTimeLabel);
		contentPanel.add(hallNameLabel);
		contentPanel.add(ticketIssueLabel);
		
		for(Component component : contentPanel.getComponents()) {
			component.setFont(new Font(getFont().getName(), Font.PLAIN, 14));
		}
		
		title.setBounds(100,50,400,50);
		title.setFont(new Font(getFont().getName(), Font.BOLD, 20));
		title.setHorizontalAlignment(title.CENTER);
		bookingNoLabel.setBounds(50, 110, 500, 15);
		movieNameLabel.setBounds(50, 130, 500, 15);
		hallNameLabel.setBounds(50, 150, 500, 15);
		screeningStartTimeLabel.setBounds(50, 170, 500, 15);
		paymentAmountLabel.setBounds(50,190,500,15);
		paymentStatusLabel.setBounds(50,210,500,15);
		if(booking.getPaymentStatus().equals("결제완료")) {
			paymentMethodLabel.setBounds(50,230,500,15);
			paymentDateLabel.setBounds(50,250,500,15);
			ticketIssueLabel.setBounds(50,270,500,15);
		}
	}
	public void setBooking(BookingDTO booking) {
		this.booking = booking;
	}
}

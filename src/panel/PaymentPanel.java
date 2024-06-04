package panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dto.BookingDTO;
import frame.MovieReservationFrame;

public class PaymentPanel extends MovieReservationPanel {
	private JTextField paymentMethodField;
	private JButton paymentButton;

	private List<BookingDTO> bookings;

	public PaymentPanel() {
	}

	@Override
	public void init() {
		removeAll();
		setLayout(new GridLayout(3, 2));
		int totalAmount = 0;
		if(bookings == null) {
			MovieReservationFrame.getMovieReservationFrame().goBack();
			return;
		}
		for(BookingDTO booking : bookings) {
			totalAmount += booking.getPaymentAmount();
		}
		JLabel paymentMethodLabel = new JLabel("Payment Method:");
		paymentMethodField = new JTextField();
		add(paymentMethodLabel);
		add(paymentMethodField);

		JLabel paymentAmountLabel = new JLabel("Payment Amount: " + totalAmount);
		add(paymentAmountLabel);

		paymentButton = new JButton("결제 완료");
		paymentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				makePayment();
			}
		});
		add(paymentButton);
	}

	private void makePayment() {
		String paymentMethod = paymentMethodField.getText();

		// 여기서 BookingDTO에 정보 저장
		for(BookingDTO booking : bookings) {
			booking.setPaymentMethod(paymentMethod);
		}
		// 나머지 정보도 저장할 수 있음

	}

	public void setBookings(List<BookingDTO> bookings) {
		this.bookings = bookings;
	}
}

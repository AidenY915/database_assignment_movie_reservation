package panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import dto.BookingDTO;
import frame.MovieReservationFrame;
import service.Service;

public class PaymentPanel extends MovieReservationPanel {
    private JComboBox<String> paymentMethodComboBox;
    private JButton paymentButton;
    private List<BookingDTO> bookings;
    private Service service = Service.getService();

    public PaymentPanel() {
    }

    @Override
    public void init() {
        removeAll();
        setLayout(new GridLayout(4, 2));
        int totalAmount = 0;
        if (bookings == null) {
            MovieReservationFrame.getMovieReservationFrame().goBack();
            return;
        }
        for (BookingDTO booking : bookings) {
            totalAmount += booking.getPaymentAmount();
        }

        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        String[] paymentMethods = {"카드", "현금", "관람권"};
        paymentMethodComboBox = new JComboBox<>(paymentMethods);
        add(paymentMethodLabel);
        add(paymentMethodComboBox);

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
        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        boolean allPaymentsSuccessful = true;
        for (BookingDTO booking : bookings) {
            booking.setPaymentMethod(paymentMethod);
            booking.setPaymentAmount(booking.getPaymentAmount());
            boolean success = service.updatePaymentInfo(booking);
            if (!success) {
                allPaymentsSuccessful = false;
                JOptionPane.showMessageDialog(this, "결제 실패: 예약 번호 " + booking.getBookingNo());
                break;
            }
        }

        if (allPaymentsSuccessful) {
            JOptionPane.showMessageDialog(this, "결제가 완료되었습니다.");
            MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
            frame.changePanel(frame.getMyBookingListPanel());
        }
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }
}

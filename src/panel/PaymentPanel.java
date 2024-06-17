package panel;

import java.awt.Font;
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
        setLayout(null);
        int totalAmount = 0;
        if (bookings == null) {
            MovieReservationFrame.getMovieReservationFrame().goBack();
            return;
        }
        for (BookingDTO booking : bookings) {
            totalAmount += booking.getPaymentAmount();
        }

        JLabel paymentMethodLabel = new JLabel("결제 방법 선택:");
        String[] paymentMethods = {"카드", "현금"};
        paymentMethodComboBox = new JComboBox<>(paymentMethods);
        add(paymentMethodLabel);
        add(paymentMethodComboBox);

        JLabel paymentAmountLabel = new JLabel("총 결제 금액: " + totalAmount + "원");
        add(paymentAmountLabel);

        paymentButton = new JButton("결제 완료");
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makePayment();
            }
        });
        add(paymentButton);
        
        paymentMethodLabel.setBounds(300, 245, 400, 50);
        paymentMethodLabel.setFont(new Font(getFont().getName(), getFont().getStyle(), 14));
        paymentMethodComboBox.setBounds(300, 305, 400, 50);
        paymentMethodComboBox.setFont(new Font(getFont().getName(), getFont().getStyle(), 12));
        paymentAmountLabel.setBounds(300, 360, 400, 50);
        paymentAmountLabel.setFont(new Font(getFont().getName(), getFont().getStyle(), 14));
        paymentButton.setBounds(300, 515, 400, 50);
        
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

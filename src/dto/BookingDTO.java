package dto;

import java.sql.Timestamp;

public class BookingDTO {
	private int bookingNo;
	private String paymentMethod;
	private String paymentStatus;
	private int paymentAmount;
	private Timestamp paymentDate;
	private int scheduleNo;
	private String seatNo;
	private String userId;

	public BookingDTO(int bookingNo, String paymentMethod, String paymentStatus, int paymentAmount,
			Timestamp paymentDate, int scheduleNo, String seatNo, String userId) {
		super();
		this.bookingNo = bookingNo;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
		this.scheduleNo = scheduleNo;
		this.seatNo = seatNo;
		this.userId = userId;
	}

	public int getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(int bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

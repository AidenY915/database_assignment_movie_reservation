package dto;

import java.sql.Date;
import java.sql.Time;

public class BookingDTO {
	private int bookingNo;
	private String paymentMethod;
	private String paymentStatus;
	private int paymentAmount;
	private Date paymentDate;
	private int scheduleNo;
	private String seatNo;
	private String userId;

	private String movieName;
	private Date screeningDate;
	private String hallName;
	private Time screeningStartTime;

	public BookingDTO(int bookingNo, String paymentMethod, String paymentStatus, int paymentAmount, Date paymentDate,
			int scheduleNo, String seatNo, String userId, String movieName, Date screeningDate, String hallName,
			Time screeningStartTime) {
		this.bookingNo = bookingNo;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
		this.scheduleNo = scheduleNo;
		this.seatNo = seatNo;
		this.userId = userId;
		this.movieName = movieName;
		this.screeningDate = screeningDate;
		this.hallName = hallName;
		this.screeningStartTime = screeningStartTime;
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

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
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

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Date getScreeningDate() {
		return screeningDate;
	}

	public void setScreeningDate(Date screeningDate) {
		this.screeningDate = screeningDate;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public Time getScreeningStartTime() {
		return screeningStartTime;
	}

	public void setScreeningStartTime(Time screeningStartTime) {
		this.screeningStartTime = screeningStartTime;
	}
}

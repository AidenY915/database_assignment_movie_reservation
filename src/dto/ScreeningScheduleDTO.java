package dto;

import java.sql.Date;
import java.sql.Time;

public class ScreeningScheduleDTO {
	private int scheduleNo;
	private int hallNo;
	private Date screeningDate;
	private String screeningDay;
	private int screeningSession; // 상영 회차
	private Time screeningStartTime;
	private int movieNo;
	private int standardPrice;
	private String hallName;

	public ScreeningScheduleDTO(int scheduleNo, int hallNo, Date screeningDate, String screeningDay,
			int screeningSession, Time screeningStartTime, int movieNo, int standardPrice, String hallName) {
		super();
		this.scheduleNo = scheduleNo;
		this.hallNo = hallNo;
		this.screeningDate = screeningDate;
		this.screeningDay = screeningDay;
		this.screeningSession = screeningSession;
		this.screeningStartTime = screeningStartTime;
		this.movieNo = movieNo;
		this.standardPrice = standardPrice;
		this.hallName = hallName;
	}

	public int getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public int getHallNo() {
		return hallNo;
	}

	public void setHallNo(int hallNo) {
		this.hallNo = hallNo;
	}

	public Date getScreeningDate() {
		return screeningDate;
	}

	public void setScreeningDate(Date screeningDate) {
		this.screeningDate = screeningDate;
	}

	public String getScreeningDay() {
		return screeningDay;
	}

	public void setScreeningDay(String screeningDay) {
		this.screeningDay = screeningDay;
	}

	public int getScreeningSession() {
		return screeningSession;
	}

	public void setScreeningSession(int screeningSession) {
		this.screeningSession = screeningSession;
	}

	public Time getScreeningStartTime() {
		return screeningStartTime;
	}

	public void setScreeningStartTime(Time screeningStartTime) {
		this.screeningStartTime = screeningStartTime;
	}

	public int getMovieNo() {
		return movieNo;
	}

	public void setMovieNo(int movieNo) {
		this.movieNo = movieNo;
	}

	public int getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(int standardPrice) {
		this.standardPrice = standardPrice;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}
	
}

package dto;

public class SeatDTO implements Comparable<SeatDTO>{
	private int hallNo;
	private String seatNo;
	private boolean isBooked;

	public SeatDTO(int hallNo, String seatNo) {
		super();
		this.hallNo = hallNo;
		this.seatNo = seatNo;
		this.isBooked = true;
	}

	public int getHallNo() {
		return hallNo;
	}

	public void setHallNo(int hallNo) {
		this.hallNo = hallNo;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	@Override
	public boolean equals(Object obj) {
		SeatDTO opSeat = (SeatDTO)obj; 
		return this.hallNo == opSeat.hallNo && this.seatNo.equals(opSeat.seatNo);
	}

	@Override
	public int compareTo(SeatDTO o) {
		int hallDiff = this.hallNo - o.hallNo;
		if (hallDiff != 0) return hallDiff;
		return this.seatNo.compareTo(o.seatNo);
	}
	@Override
	public String toString() {
		return seatNo;
	}
	
}

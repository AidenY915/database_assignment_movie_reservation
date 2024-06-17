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
		int rslt = this.hallNo - o.hallNo;
		if (rslt == 0)
			rslt = this.seatNo.charAt(0) - o.seatNo.charAt(0);
		if (rslt == 0)
			rslt = Integer.valueOf(this.seatNo.substring(1))-Integer.valueOf(o.seatNo.substring(1));;
		return rslt;
	}
	@Override
	public String toString() {
		return seatNo;
	}
	
}

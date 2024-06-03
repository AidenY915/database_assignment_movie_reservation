package panel;

import dto.ScreeningScheduleDTO;

public class SeatSelectionPanel extends MovieReservationPanel {
	private ScreeningScheduleDTO selectedSchedule;
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
	public void setSelectedSchedule(ScreeningScheduleDTO selectedSchedule) {
		this.selectedSchedule = selectedSchedule;
	}
	

}

package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import dto.MovieDTO;
import frame.MovieReservationFrame;

public class MovieDetailPanel extends MovieReservationPanel {
	private MovieDTO movieDTO; //no만 전달받음
	
	public void setMovieDTO(MovieDTO movieDTO) {
		this.movieDTO = movieDTO;
	}


	@Override
	public void init() {
		removeAll();
		
		System.out.println(movieDTO.getMovieNo());
		
		setLayout(null);
		setSize(MovieReservationFrame.WIDTH - 15, MovieReservationFrame.HEIGHT - 45);
		
		JButton moveToReservationBtn = new MoveToReservationBtn();
		
		add(moveToReservationBtn);
		
		//위치 설정
		moveToReservationBtn.setBounds(getWidth()-10-100, getHeight()-10-50, 100, 50);
	}
}

class MoveToReservationBtn extends JButton {
	
	public MoveToReservationBtn() {
		super("예매하기");
		addActionListener(new MoveToResrvationActionListener());
	}
	
	class MoveToResrvationActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
			frame.changePanel(frame.getReservationPanel());
		}
		
	}
}

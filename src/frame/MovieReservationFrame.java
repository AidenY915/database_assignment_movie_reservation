package frame;

import javax.swing.JFrame;
import panel.MovieReservationPanel;
import panel.LoginPanel;

public class MovieReservationFrame extends JFrame {
	final static public int WIDTH = 500, HEIGHT = 800;
	//싱글톤 패턴
	static private MovieReservationFrame movieReservationFrame = new MovieReservationFrame();
	
	private MovieReservationPanel currentPanel = null; 
	private MovieReservationPanel loginPanel = new LoginPanel();
	
	public MovieReservationPanel getCurrentPanel() {
		return currentPanel;
	}
	public MovieReservationPanel getLoginPanel() {
		return loginPanel;
	}
	private MovieReservationFrame(){
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(50,50);
		setSize(WIDTH, HEIGHT);
		changePanel(loginPanel);
		setVisible(true);
	}
	//싱글톤 패턴 생성자 대신 호출
	static public MovieReservationFrame getMovieReservationFrame() {
		return movieReservationFrame;
	}
	public void changePanel(MovieReservationPanel nextPanel) {
		if(currentPanel != null) {
			getContentPane().remove(currentPanel); //왜 안먹지? 왜 중복돼서 아무것도 안나오지?
		}
		getContentPane().add(nextPanel);
		nextPanel.setVisible(true);
		currentPanel = nextPanel;
		revalidate();	// 레이아웃 관리자에게 컴포넌트 변경 사항을 알림
		repaint();
	}
	
}

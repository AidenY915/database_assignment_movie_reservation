package frame;

import javax.swing.JFrame;

import dto.UserDTO;
import panel.AdminLoginPanel;
import panel.LoginChoice;
import panel.MovieReservationPanel;
import panel.RegisterPanel;
import panel.UserLoginPanel;

public class MovieReservationFrame extends JFrame {
	final static public int WIDTH = 1000, HEIGHT = 800;
	//싱글톤 패턴

private UserDTO loginSession = null;

	static private MovieReservationFrame movieReservationFrame;
	
	private MovieReservationPanel currentPanel = null; 
	private MovieReservationPanel loginchoicepanel=new LoginChoice();
	private MovieReservationPanel userloginPanel = new UserLoginPanel();
	private MovieReservationPanel adminloginpanel= new AdminLoginPanel();
	private MovieReservationPanel registerPanel = new RegisterPanel();
	
	public MovieReservationPanel getCurrentPanel() {
		return currentPanel;
	}
	public MovieReservationPanel getUserLoginPanel() {
//		0 넘어감
		
		return userloginPanel;
	}
	public MovieReservationPanel getAdminLoginPanel() {
//		1 
		return adminloginpanel;
	} 
	public MovieReservationPanel getRegisterPanel() {
		return registerPanel;
	}
	public UserDTO getLoginSession() {
		return loginSession;
	}

	public void setLoginSession(UserDTO loginSession) {
		this.loginSession = loginSession;
	}
	
	
	
	
	private MovieReservationFrame(){
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(50, 50);
		setSize(WIDTH, HEIGHT);
		changePanel(loginchoicepanel);
		setVisible(true);
	}

	// 싱글톤 패턴 생성자 대신 호출
	static public MovieReservationFrame getMovieReservationFrame() {
		if(movieReservationFrame == null)
			movieReservationFrame = new MovieReservationFrame();
		return movieReservationFrame;
	}

	public void changePanel(MovieReservationPanel nextPanel) {
		if (currentPanel != null) {
			getContentPane().remove(currentPanel); // 왜 안먹지? 왜 중복돼서 아무것도 안나오지?
		}
		nextPanel.init();
		getContentPane().add(nextPanel);
		nextPanel.setVisible(true);
		currentPanel = nextPanel;
		revalidate(); // 레이아웃 관리자에게 컴포넌트 변경 사항을 알림
		repaint();
	}

}

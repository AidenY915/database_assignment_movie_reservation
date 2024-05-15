package frame;

import javax.swing.JFrame;
import panel.MovieReservationPanel;
import panel.LoginPanel;

public class MovieReservationFrame extends JFrame {
	final static int WIDTH = 500, HEIGHT = 800;
	static MovieReservationFrame movieBookingFrame = new MovieReservationFrame();
	
	private MovieReservationPanel currentPanel = null; 
	private MovieReservationPanel loginPanel = new LoginPanel();
	
	
	
	private MovieReservationFrame(){
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(50,50);
		setSize(WIDTH, HEIGHT);
		changePanel(loginPanel);
		setVisible(true);
	}
	static public MovieReservationFrame getMovieBookingFrame() {
		return movieBookingFrame;
	}
	public void changePanel(MovieReservationPanel nextPanel) {
		if(currentPanel != null)
			remove(currentPanel);
		add(nextPanel);
		currentPanel = nextPanel;
	}
	
}

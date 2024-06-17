package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import dto.UserDTO;
import panel.AdminLoginPanel;
import panel.AdminMainPanel;
import panel.AdminRegisterPanel;
import panel.BookingDetailPanel;
import panel.DBInitPanel;
import panel.DBManipulationPanel;
import panel.DBModifyPanel;
import panel.LoginChoice;
import panel.MovieDetailPanel;
import panel.MovieListPanel;
import panel.MovieReservationPanel;
import panel.MyBookingListPanel;
import panel.PaymentPanel;
import panel.ReservationPanel;
import panel.SeatSelectionPanel;
import panel.TableSelectionPanel;
import panel.UserLoginPanel;
import panel.UserRegisterPanel;

public class MovieReservationFrame extends JFrame {
	final static public int WIDTH = 1000, HEIGHT = 800;
	private UserDTO loginSession = null;
	private static MovieReservationFrame movieReservationFrame;
	private MovieReservationPanel currentPanel = null;
	private MovieReservationPanel loginChoicePanel = new LoginChoice();
	private MovieReservationPanel userLoginPanel = new UserLoginPanel();
	private MovieReservationPanel adminLoginPanel = new AdminLoginPanel();
	private MovieReservationPanel userRegisterPanel = new UserRegisterPanel();
	private MovieReservationPanel adminRegisterPanel = new AdminRegisterPanel();
	private MovieReservationPanel adminMainPanel = new AdminMainPanel();
	private MovieReservationPanel dbInitPanel = new DBInitPanel();
	private MovieReservationPanel dbModifyPanel = new DBModifyPanel();
	private MovieReservationPanel dbManipulationPanel = new DBManipulationPanel();
	private MovieReservationPanel tableSelectionPanel = new TableSelectionPanel();
	private MovieReservationPanel movieListPanel = new MovieListPanel();
	private MovieReservationPanel movieDetailPanel = new MovieDetailPanel();
	private MovieReservationPanel reservationPanel = new ReservationPanel();
	private MovieReservationPanel seatSelectionPanel = new SeatSelectionPanel();
	private MovieReservationPanel paymentPanel = new PaymentPanel();
	private MovieReservationPanel myBookingListPanel = new MyBookingListPanel();
	private MovieReservationPanel bookingDetailPanel = new BookingDetailPanel();
	private Stack<MovieReservationPanel> panelStack = new Stack<>();
	private JButton backButton;
	private JButton toMainButton = new JButton();
	public static final Date TODAY = new Date(2024 - 1900, 5, 7); // 2024년 6월 7일;

	public MovieReservationPanel getCurrentPanel() {
		return currentPanel;
	}

	public MovieReservationPanel getLoginChoicePanel() {
		return loginChoicePanel;
	}

	public MovieReservationPanel getUserLoginPanel() {
		return userLoginPanel;
	}

	public MovieReservationPanel getAdminLoginPanel() {
		return adminLoginPanel;
	}

	public MovieReservationPanel getUserRegisterPanel() {
		return userRegisterPanel;
	}

	public MovieReservationPanel getAdminRegisterPanel() {
		return adminRegisterPanel;
	}

	public MovieReservationPanel getAdminMainPanel() {
		return adminMainPanel;
	}

	public MovieReservationPanel getDbInitPanel() {
		return dbInitPanel;
	}

	public MovieReservationPanel getDbModifyPanel() {
		return dbModifyPanel;
	}

	public MovieReservationPanel getDbManipulationPanel() {
		return dbManipulationPanel;
	}

	public MovieReservationPanel getTableSelectionPanel() {
		return tableSelectionPanel;
	}

	public MovieReservationPanel getMovieListPanel() {
		return movieListPanel;
	}

	public MovieReservationPanel getMovieDetailPanel() {
		return movieDetailPanel;
	}

	public MovieReservationPanel getReservationPanel() {
		return reservationPanel;
	}

	public MovieReservationPanel getMyBookingListPanel() {
		return myBookingListPanel;
	}

	public MovieReservationPanel getSeatSelectionPanel() {
		return seatSelectionPanel;
	}

	public MovieReservationPanel getPaymentPanel() {
		return paymentPanel;
	}

	public MovieReservationPanel getBookingDetailPanel() {
		return bookingDetailPanel;
	}

	public UserDTO getLoginSession() {
		return loginSession;
	}

	public void setLoginSession(UserDTO loginSession) {
		this.loginSession = loginSession;
	}

	private MovieReservationFrame() {
		super("영화 예매 프로그램 ( 19011477 윤종석 & 19011499 한신 )");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocation(50, 50);
		setSize(WIDTH, HEIGHT);

		backButton = new JButton("뒤로 가기");
		backButton.setBounds(10, 10, 100, 30);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});
		add(backButton);

		JLabel todayLabel = new JLabel("오늘: " + TODAY.toString());
		todayLabel.setBounds(WIDTH - 150, 25, 150, 50);
		add(todayLabel);

		toMainButton.setBounds(10, 700, 100, 30);
		add(toMainButton);

		changePanel(loginChoicePanel);
		setVisible(true);

		System.out.println("Today : " + TODAY.toString());

	}

	// 싱글톤 패턴 생성자 대신 호출
	static public MovieReservationFrame getMovieReservationFrame() {
		if (movieReservationFrame == null)
			movieReservationFrame = new MovieReservationFrame();
		return movieReservationFrame;
	}

	public void changePanel(MovieReservationPanel nextPanel) {
		if (currentPanel != null) {
			getContentPane().remove(currentPanel);
			panelStack.push(currentPanel);
		}
		nextPanel.init();
		getContentPane().add(nextPanel);
		nextPanel.setVisible(true);
		currentPanel = nextPanel;
		showToMainButton(currentPanel);
		revalidate();
		repaint();
	}

	public void goBack() {
		if (!panelStack.isEmpty()) {
			MovieReservationPanel previousPanel = panelStack.pop();
			getContentPane().remove(currentPanel);
			getContentPane().add(previousPanel);
			previousPanel.setVisible(true);
			currentPanel = previousPanel;
			showToMainButton(currentPanel);
			revalidate();
			repaint();
		}
	}

	public void showToMainButton(MovieReservationPanel panel) {
		MovieReservationPanel nextPanel;
		toMainButton.setVisible(false);
		toMainButton.setText("메인으로");
		if (panel != null && panel != loginChoicePanel && panel != movieListPanel && panel != adminMainPanel
				&& panel != userLoginPanel && panel != adminLoginPanel) {
			if (loginSession == null)
				return;
			toMainButton.setVisible(true);
			if (loginSession.isAdmin() == 1) {
				nextPanel = adminMainPanel;
			} else {
				nextPanel = movieListPanel;
			}
			for (ActionListener al : toMainButton.getActionListeners())
				toMainButton.removeActionListener(al);
			toMainButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					changePanel(nextPanel);

				}
			});
		} else if (panel == adminMainPanel || panel == movieListPanel) {
			toMainButton.setVisible(true);
			toMainButton.setText("로그아웃");
			nextPanel = loginChoicePanel;
			for (ActionListener al : toMainButton.getActionListeners())
				toMainButton.removeActionListener(al);
			toMainButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					changePanel(nextPanel);
					panelStack.clear();
					loginSession = null;
				}
			});

		}
	}

}

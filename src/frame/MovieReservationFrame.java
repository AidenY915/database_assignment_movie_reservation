package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;

import dto.UserDTO;
import panel.AdminLoginPanel;
import panel.AdminMainPanel;
import panel.AdminRegisterPanel;
import panel.DBInitPanel;
import panel.DBManipulationPanel;
import panel.DBModifyPanel;
import panel.LoginChoice;
import panel.MovieDetailPanel;
import panel.MovieListPanel;
import panel.MovieReservationPanel;
import panel.ReservationPanel;
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
    private Stack<MovieReservationPanel> panelStack = new Stack<>();
    private JButton backButton;

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

	public UserDTO getLoginSession() {
        return loginSession;
    }

	public void setLoginSession(UserDTO loginSession) {
		this.loginSession = loginSession;
	}
	
	
	
	
	private MovieReservationFrame() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        changePanel(loginChoicePanel);
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
            getContentPane().remove(currentPanel);
            panelStack.push(currentPanel);
        }
        nextPanel.init();
        getContentPane().add(nextPanel);
        nextPanel.setVisible(true);
        currentPanel = nextPanel;
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
            revalidate();
            repaint();
        }
    }
}
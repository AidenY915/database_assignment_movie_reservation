package panel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import dto.MovieDTO;
import frame.MovieReservationFrame;

public class MovieDetailPanel extends MovieReservationPanel {
	private MovieDTO movieDTO;

	private JLabel titleLabel = new JLabel();
	private JLabel directorLabel = new JLabel();
	private JLabel genreLabel = new JLabel();
	private JLabel releaseDateLabel = new JLabel();
	private JLabel ratingLabel = new JLabel();

	private JLabel actorLabel = new JLabel("배우");
	private JLabel movieInfoLabel = new JLabel("영화 설명");

	private JTextArea actorTextArea = new JTextArea();
	private JTextArea movieInfoTextArea = new JTextArea();

	private JScrollPane actorScrollPane = new JScrollPane(actorTextArea);
	private JScrollPane movieInfoScrollPane = new JScrollPane(movieInfoTextArea);

	public void setMovieDTO(MovieDTO movieDTO) {
		this.movieDTO = movieDTO;
		updateMovieDetails();
	}

	@Override
	public void init() {
		removeAll();

		setLayout(null);
		setSize(MovieReservationFrame.WIDTH - 15, MovieReservationFrame.HEIGHT - 45);

		add(titleLabel);
		add(directorLabel);
		add(actorScrollPane);
		add(genreLabel);
		add(releaseDateLabel);
		add(movieInfoScrollPane);
		add(ratingLabel);
		add(actorLabel);
		add(movieInfoLabel);
		
		
		JButton moveToReservationBtn = new MoveToReservationBtn();
		add(moveToReservationBtn);
		moveToReservationBtn.setBounds(600, 500, 100, 50);

		titleLabel.setBounds(300, 50, 400, 30);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

		directorLabel.setBounds(300, 90, 400, 30);
		actorLabel.setBounds(300, 130, 400, 20);
		actorScrollPane.setBounds(300, 150, 400, 60);
		genreLabel.setBounds(300, 220, 400, 30);
		releaseDateLabel.setBounds(300, 260, 400, 30);
		movieInfoLabel.setBounds(300, 300, 400, 20);
		movieInfoScrollPane.setBounds(300, 320, 400, 150);
		ratingLabel.setBounds(300, 480, 400, 30);

		actorTextArea.setEditable(false);
		movieInfoTextArea.setEditable(false);

	}

	private void updateMovieDetails() {
		titleLabel.setText("영화 제목: " + movieDTO.getMovieName());
		directorLabel.setText("감독: " + movieDTO.getDirectorName());
		genreLabel.setText("장르: " + movieDTO.getGenre());
		releaseDateLabel.setText("개봉일: " + movieDTO.getReleaseDate().toString());
		movieInfoTextArea.setText(movieDTO.getMovieInfo());
		ratingLabel.setText("평점: " + movieDTO.getRatingInformation());

		String actorNames = String.join(", ", movieDTO.getActorNames());
		actorTextArea.setText(actorNames);
		actorTextArea.setCaretPosition(0);
		movieInfoTextArea.setCaretPosition(0);
	}
}

class MoveToReservationBtn extends JButton {

	public MoveToReservationBtn() {
		super("예매하기");
		addActionListener(new MoveToReservationActionListener());
	}

	class MoveToReservationActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
			frame.changePanel(frame.getReservationPanel());
		}
	}
}

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

		JButton moveToReservationBtn = new MoveToReservationBtn();
		add(moveToReservationBtn);
        moveToReservationBtn.setBounds(790,640,100,50);

		titleLabel.setBounds(50, 50, 400, 30);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

		directorLabel.setBounds(50, 100, 400, 30);
		actorScrollPane.setBounds(50, 150, 400, 60);
		genreLabel.setBounds(50, 230, 400, 30);
		releaseDateLabel.setBounds(50, 280, 400, 30);
		movieInfoScrollPane.setBounds(50, 330, 400, 100);
		ratingLabel.setBounds(50, 450, 400, 30);

		actorTextArea.setEditable(false);
		movieInfoTextArea.setEditable(false);

		moveToReservationBtn.setBounds(getWidth() - 10 - 100, getHeight() - 10 - 50, 100, 50);
	}

	private void updateMovieDetails() {
		titleLabel.setText("영화 제목: " + movieDTO.getMovieName());
		directorLabel.setText("감독: " + movieDTO.getDirectorName());
		genreLabel.setText("장르: " + movieDTO.getGenre());
		releaseDateLabel.setText("개봉일: " + movieDTO.getReleaseDate().toString());
		movieInfoTextArea.setText(movieDTO.getMovieInfo());
		ratingLabel.setText("평점: " + movieDTO.getRatingInformation());

		String actorNames = String.join(" ", movieDTO.getActorNames());
        actorTextArea.setText(actorNames);
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

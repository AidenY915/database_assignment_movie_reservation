package panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

import dto.MovieDTO;
import frame.MovieReservationFrame;
import service.Service;

public class MovieListPanel extends MovieReservationPanel {
	private Service service = Service.getService();
	private List<MovieDTO> movieList;
	private MovieListScrollPane currentMovieListScrollpane;

	public MovieListPanel() {
	}

	@Override
	public void init() {
		removeAll();

		setLayout(null);

		JLabel titleLabel = new JLabel("영화 목록");

		JLabel movieName = new JLabel("영화 제목");
		JLabel directorName = new JLabel("감독 이름");
		JLabel actorName = new JLabel("배우 이름");
		JLabel genre = new JLabel("영화 장르");

		JTextField movieNameField = new JTextField();
		JTextField directorNameField = new JTextField();
		JTextField actorNameField = new JTextField();
		JTextField genreField = new JTextField();

		add(titleLabel);
		add(movieName);
		add(directorName);
		add(actorName);
		add(genre);
		add(movieNameField);
		add(directorNameField);
		add(actorNameField);
		add(genreField);

		titleLabel.setBounds(440, 20, 100, 50);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
		titleLabel.setForeground(Color.BLUE);

		movieName.setBounds(30, 70, 80, 50);
		movieNameField.setBounds(90, 75, 140, 40);

		directorName.setBounds(250, 70, 80, 50);
		directorNameField.setBounds(320, 75, 140, 40);

		actorName.setBounds(500, 70, 80, 50);
		actorNameField.setBounds(560, 75, 140, 40);

		genre.setBounds(750, 70, 80, 50);
		genreField.setBounds(810, 75, 140, 40);

		SearchButton searchButton = new SearchButton(this, movieNameField, directorNameField, actorNameField,
				genreField);
		add(searchButton);
		searchButton.setBounds(440, 150, 100, 50);

		// 검색
		searchMovieList(movieNameField.getText(), directorNameField.getText(), actorNameField.getText(),
				genreField.getText());

	}

	private List<MovieDTO> getMovieList(String title, String director, String actor, String genre) {
		title = title.trim();
		director = director.trim();
		String[] actorArray = actor.trim().split(" ");
		genre = genre.trim();
		if (service == null) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getMovieList(title, director, actor, genre);
		} else {
			return service.getMovieList(title, director, actorArray, genre);
		}
	}

	void searchMovieList(String title, String director, String actor, String genre) {
		movieList = getMovieList(title, director, actor, genre);
		if (currentMovieListScrollpane != null)
			this.remove(currentMovieListScrollpane);
		currentMovieListScrollpane = new MovieListScrollPane(movieList);
		add(currentMovieListScrollpane);
		revalidate();
		repaint();
	}
}

class MovieListScrollPane extends JScrollPane {
	List<MovieDTO> movieList;
	final static int WIDTH = MovieReservationFrame.WIDTH - 20;
	private final static int HEIGHT = MovieReservationFrame.HEIGHT / 7 * 4;
	private final static int Y = MovieReservationFrame.HEIGHT / 7 * 2;

	public MovieListScrollPane(List<MovieDTO> movieList) throws HeadlessException {
		super();
		this.movieList = movieList;
		setSize(WIDTH, HEIGHT);
		setLayout(new ScrollPaneLayout());
		setLocation(4, Y);
		setBackground(Color.BLUE);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH - 20, movieList.size() * MovieDTOPanel.HEIGHT));
		panel.setLayout(null);

		setViewportView(panel);

		int i = 0;
		for (MovieDTO movie : movieList) {
			panel.add(new MovieDTOPanel(movie, 0, i * MovieDTOPanel.HEIGHT));
			System.out.println(movie);
			i++;
		}
	}
}

class MovieDTOPanel extends JPanel {
	private MovieDTO movieDTO;
	final static int WIDTH = MovieListScrollPane.WIDTH - 20;
	final static int HEIGHT = 100;

	public MovieDTOPanel(MovieDTO movieDTO, int x, int y) {
		this.movieDTO = movieDTO;
		setBounds(x, y, WIDTH, HEIGHT);

		JLabel movieNameLabel = new JLabel(movieDTO.getMovieName());

		add(movieNameLabel);
		addMouseListener(new moveToDetailClickListener(movieDTO));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, WIDTH - 10, HEIGHT - 10);
	}

	class moveToDetailClickListener implements MouseListener {
		private MovieDTO movieDTO;

		moveToDetailClickListener(MovieDTO movieDTO) {
			this.movieDTO = movieDTO;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();
			MovieDetailPanel movieDetailPanel = (MovieDetailPanel) frame.getMovieDetailPanel();
			movieDetailPanel.setMovieDTO(movieDTO);
			frame.changePanel(movieDetailPanel);
		}
	}
}

class SearchButton extends JButton {
	private MovieListPanel movieListPanel;
	private JTextField movieNameField;
	private JTextField directorNameField;
	private JTextField actorNameField;
	private JTextField genreField;

	SearchButton(MovieListPanel movieListPanel, JTextField movieNameField, JTextField directorNameField,
			JTextField actorNameField, JTextField genreField) {
		super("영화 검색");
		this.movieListPanel = movieListPanel;
		this.movieNameField = movieNameField;
		this.directorNameField = directorNameField;
		this.actorNameField = actorNameField;
		this.genreField = genreField;

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				movieListPanel.searchMovieList(movieNameField.getText(), directorNameField.getText(),
						actorNameField.getText(), genreField.getText());
			}

		});
	}
}

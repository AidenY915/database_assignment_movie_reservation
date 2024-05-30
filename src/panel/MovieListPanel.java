package panel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import dto.MovieDTO;
import frame.MovieReservationFrame;
import service.Service;

public class MovieListPanel extends MovieReservationPanel {
	public Service service;
	public List<MovieDTO> movieList;

	public MovieListPanel() {
	}

	@Override
	public void init() {
		service = Service.getService();
		setLayout(null);

		movieList = getMovieList("", "", "", "");
		int i = 0;
		for (MovieDTO movie : movieList) {
			add(new MovieDTOPanel(movie, 0, i * MovieDTOPanel.HEIGHT));
			i++;
		}

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
}

class MovieDTOPanel extends JPanel {
	public static final int HEIGHT = 50;
	public static final int WIDTH = MovieReservationFrame.WIDTH - 15;
	private MovieDTO movieDTO;

	public MovieDTOPanel(MovieDTO movieDTO, int x, int y) {
		this.movieDTO = movieDTO;
		setSize(WIDTH, HEIGHT);
		setLocation(x, y);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, WIDTH - 10, HEIGHT - 10);
	}
}

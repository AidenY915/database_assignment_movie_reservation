package panel;

import java.util.List;

import dto.MovieDTO;
import service.Service;

public class MovieListPanel extends MovieReservationPanel {
	public Service service;
	public List<MovieDTO> movieList;
	
	public MovieListPanel() {
	}

	@Override
	public void init() {
		service = Service.getService();
		movieList = getMovieList("", "", "", "");
		System.out.println(movieList);
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

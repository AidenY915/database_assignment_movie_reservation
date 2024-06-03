package panel;

import dto.MovieDTO;

public class MovieDetailPanel extends MovieReservationPanel {
	private MovieDTO movieDTO; //no만 전달받음
	
	public void setMovieDTO(MovieDTO movieDTO) {
		this.movieDTO = movieDTO;
	}


	@Override
	public void init() {

	}
}

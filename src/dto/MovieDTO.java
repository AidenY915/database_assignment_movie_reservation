package dto;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class MovieDTO {
	private int movieNo;
	private String movieName;
	private int runningTime; // 분 단위
	private int ageRating;
	private String directorName;
	private String genre;
	private Date releaseDate;
	private String movieInfo;
	private float ratingInformation;
	private List<String> actorNames;

	public MovieDTO(int movieNo, String movieName, int runningTime, int ageRating, String directorName, String genre,
			Date releaseDate, String movieInfo, float ratingInformation, List<String> actorNames) {
		super();
		if (actorNames == null)
			actorNames = new LinkedList<String>();
		this.movieNo = movieNo;
		this.movieName = movieName;
		this.runningTime = runningTime;
		this.ageRating = ageRating;
		this.directorName = directorName;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.movieInfo = movieInfo;
		this.ratingInformation = ratingInformation;
		this.actorNames = actorNames;
	}

	public MovieDTO(int movieNo, String movieName, int runningTime, int ageRating, String directorName, String genre,
			Date releaseDate, String movieInfo, float ratingInformation, String actorNames) {
		this.movieNo = movieNo;
		this.movieName = movieName;
		this.runningTime = runningTime;
		this.ageRating = ageRating;
		this.directorName = directorName;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.movieInfo = movieInfo;
		this.ratingInformation = ratingInformation;
		this.actorNames = new LinkedList<String>();
		if (actorNames != null) {
			for (String actorName : actorNames.trim().split(" ")) {
				this.actorNames.add(actorName);
			}
		}
	}

	public int getMovieNo() {
		return movieNo;
	}

	public void setMovieNo(int movieNo) {
		this.movieNo = movieNo;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public int getAgeRating() {
		return ageRating;
	}

	public void setAgeRating(int ageRating) {
		this.ageRating = ageRating;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getMovieInfo() {
		return movieInfo;
	}

	public void setMovieInfo(String movieInfo) {
		this.movieInfo = movieInfo;
	}

	public float getRatingInformation() {
		return ratingInformation;
	}

	public void setRatingInformation(float ratingInformation) {
		this.ratingInformation = ratingInformation;
	}

	public void appendActorName(String actorName) {
		this.actorNames.add(actorName);
	}

	@Override
	public String toString() {
		return "movie_no : " + movieNo + ", movie_name" + movieName;
	}

}

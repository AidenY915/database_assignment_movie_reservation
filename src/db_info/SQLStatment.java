package db_info;

public interface SQLStatment extends DbInfo{
	final static String SELECT_USER_BY_ID_AND_PW_QUERY = "SELECT * FROM " + USER_TABLE + " WHERE user_id = ? AND password = ? AND is_admin = ?";
	final static String SELECT_MOVIES_WITH_ACTOR_NAME_QUERY = "SELECT FROM " + MOVIE_TABLE + " JOIN " + CASTING_TABLE + " JOIN " + ACTOR_TABLE 
			+ " WHERE movie_name LIKE ? AND director_name LIKE ? "
			+ "AND actor_name LIKE ? " //배우 이름을 하나만 검색 한 경우에 대한 쿼리
			+ "AND genre LIKE ? "
			+ "GROUP BY movie_no ";
	final static String SELECT_MOVIES_QUERY = "SELECT movie_no, movie_name, running_time, age_rating, director_name, genre, release_date, movie_info, rating_information, STRING_AGG(\"actor_name\", \" \") FROM " + MOVIE_TABLE + " JOIN " + CASTING_TABLE + " JOIN " + ACTOR_TABLE 
			+ " WHERE movie_name LIKE ? AND director_name LIKE ? "
			+ "AND genre LIKE ? "
			+ "GROUP BY movie_no ";
}

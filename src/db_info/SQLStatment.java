package db_info;

public interface SQLStatment extends DbInfo{
	final static String SELECT_USER_BY_ID_AND_PW_QUERY = "SELECT * FROM " + USER_TABLE + " WHERE user_id = ? AND password = ? AND is_admin = ?";
	final static String SELECT_MOVIES_WITH_ACTOR_NAME_QUERY = "SELECT A.*, GROUP_CONCAT(actor_name SEPARATOR ' ') 'actor_names'"
			+ " FROM " + MOVIE_TABLE + " as A NATURAL JOIN " + CASTING_TABLE + " NATURAL JOIN " + ACTOR_TABLE;
	final static String SELECT_MOVIES_QUERY = "SELECT A.*, GROUP_CONCAT(actor_name SEPARATOR ' ') 'actor_names'"
			+ " FROM " + MOVIE_TABLE + " as A LEFT JOIN " + CASTING_TABLE + " as B ON A.movie_no = B.movie_no LEFT JOIN " + ACTOR_TABLE + " AS C ON B.actor_no = C.actor_no" //캐스팅 없어도 영화는 나오게 left 조인 해야함 -> 중복 컬럼 삭제 안됨. 
			+ " WHERE movie_name LIKE ? AND director_name LIKE ? AND genre LIKE ?"
			+ " GROUP BY movie_no";
}

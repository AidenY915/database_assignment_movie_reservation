package db_info;

public interface SQLStatment extends DbInfo {
	final static String selectUserByIdAndPw = "SELECT * FROM " + USER_TABLE
			+ " WHERE user_id = ? AND password = ? AND is_admin = ?";
	String selectUserById = "SELECT * FROM " + USER_TABLE + " WHERE user_id = ?";
	String insertUser = "INSERT INTO " + USER_TABLE
			+ " (user_id, user_name, phone_no, email, password, is_admin) VALUES (?, ?, ?, ?, ?, ?)";

	final static String SELECT_USER_BY_ID_AND_PW_QUERY = "SELECT * FROM " + USER_TABLE + " WHERE user_id = ? AND password = ? AND is_admin = ?";
	
	final static String SELECT_MOVIES_WITH_ACTOR_NAME_QUERY = "SELECT A.*, GROUP_CONCAT(actor_name SEPARATOR ',') 'actor_names'"
			+ " FROM " + MOVIE_TABLE + " as A NATURAL JOIN " + CASTING_TABLE + " NATURAL JOIN " + ACTOR_TABLE;
	
	final static String SELECT_MOVIES_QUERY = "SELECT A.*, GROUP_CONCAT(actor_name SEPARATOR ',') 'actor_names'"
			+ " FROM " + MOVIE_TABLE + " as A LEFT JOIN " + CASTING_TABLE + " as B ON A.movie_no = B.movie_no LEFT JOIN " + ACTOR_TABLE + " AS C ON B.actor_no = C.actor_no" //캐스팅 없어도 영화는 나오게 left 조인 해야함 -> 중복 컬럼 삭제 안됨. 
			+ " WHERE movie_name LIKE ? AND director_name LIKE ? AND genre LIKE ?"
			+ " GROUP BY movie_no";
	
	final static String SELECT_SCREENING_SCHEDUELES_BY_MOVIE_NO = "SELECT A.*, standard_price"
			+ " FROM " + SCREENING_SCHEDULE_TABLE + " AS A INNER JOIN " + SCREENING_HALL_TABLE + " AS B ON A.hall_no = B.hall_no"
			+ " WHERE movie_no = ?"; 
	
	final static String SELECT_UNBOOKED_SEATS_BY_SCHEDULE_NO = "SELECT *"
			+ " FROM " + SEAT_TABLE + " WHERE hall_no = ? AND seat_no NOT IN"
					+ " (SELECT C.seat_no FROM " + BOOKING_TABLE + " AS A INNER JOIN " + SCREENING_SCHEDULE_TABLE + " AS B ON A.schedule_no = B.schedule_no"
							+ " INNER JOIN " + SEAT_TABLE + " AS C ON B.hall_no = C.hall_no AND A.seat_no = C.seat_no"
									+ " WHERE B.schedule_no = ?)";
	
	final static String SELECT_ALL_SEATS_BY_HALL_NO = "SELECT * FROM " + SEAT_TABLE + " WHERE HALL_NO = ?";
	
	final static String INSERT_BOOKING = "INSERT INTO " + BOOKING_TABLE 
			+ "(payment_method, payment_status, payment_amount, schedule_no, seat_no, user_id)"
			+ " VALUES(NULL, '미결제', ?, ?, ?, ?)";
	
	final static String SELECT_UNPAID_BOOKING = "SELECT * FROM " + BOOKING_TABLE
			+ " WHERE payment_method is NULL AND schedule_no = ? AND seat_no = ? AND user_id = ?";
	
	final static String DELETE_TICKET = "DELETE FROM " + TICKET_TABLE + " WHERE booking_no = ?";
	
	final static String INSERT_TICKET = "INSERT INTO " + TICKET_TABLE + "(booking_no) VALUES(?)";
}

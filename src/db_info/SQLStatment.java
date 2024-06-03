package db_info;

public interface SQLStatment extends DbInfo {
	final static String selectUserByIdAndPw = "SELECT * FROM " + USER_TABLE
			+ " WHERE user_id = ? AND password = ? AND is_admin = ?";
	String selectUserById = "SELECT * FROM " + USER_TABLE + " WHERE user_id = ?";
	String insertUser = "INSERT INTO " + USER_TABLE
			+ " (user_id, user_name, phone_no, email, password, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
}

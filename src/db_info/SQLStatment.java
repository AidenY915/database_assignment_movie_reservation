package db_info;

public interface SQLStatment extends DbInfo{
	final static String selectUserByIdAndPw = "SELECT * FROM " + USER_TABLE + " WHERE user_id = ? AND password = ? AND is_admin = ?";
	
}

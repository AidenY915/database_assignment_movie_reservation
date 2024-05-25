package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db_info.DbInfo;
import db_info.SQLStatment;
import dto.UserDTO;
import frame.MovieReservationFrame;

public class DAO implements DbInfo, SQLStatment {
	private MovieReservationFrame frame = MovieReservationFrame.getMovieReservationFrame();

	private DAO() {
	}

	public static DAO dao = new DAO();

	public static DAO getDAO() {
		return dao;
	}

	// 인스턴스 초기화 블럭
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) { // RuntimeException이라 굳이 안해도 되기는 함.
			e.printStackTrace();
		}
	}

	public UserDTO selectUserByIdAndPw(String id, String pw, int isAdmin) {
		String databaseUrl = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";
		String DbId = USER_ID;
		String DbPw = USER_PW;
		if (isAdmin == 1) {
			DbId = ROOT_ID;
			DbPw = ROOT_PW;
		}
		UserDTO rsltUserDTO = null;
		try (Connection conn = DriverManager.getConnection(databaseUrl, DbId, DbPw);
				PreparedStatement selectUserByIdAndPwStmt = conn.prepareStatement(selectUserByIdAndPw);) {
			selectUserByIdAndPwStmt.setString(1, id);
			selectUserByIdAndPwStmt.setString(2, pw);
			selectUserByIdAndPwStmt.setInt(3, isAdmin);
			try (ResultSet rs = selectUserByIdAndPwStmt.executeQuery()) {
				if (rs.next()) {
					rsltUserDTO = new UserDTO(rs.getString("user_id"), rs.getString("user_name"),
							rs.getString("phone_no"), rs.getString("email"), rs.getString("password"),
							rs.getInt("is_admin"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rsltUserDTO;
	}
}

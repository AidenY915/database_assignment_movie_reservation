package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import db_info.DbInfo;
import db_info.SQLStatment;
import dto.MovieDTO;
import dto.UserDTO;

public class DAO implements DbInfo, SQLStatment {

	final static String DATABASE_URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";
	private String DbId = USER_ID;
	private String DbPw = USER_PW;
	
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
		DbId = USER_ID;
		DbPw = USER_PW;
		if (isAdmin == 1) {
			DbId = ROOT_ID;
			DbPw = ROOT_PW;
		}
		UserDTO rsltUserDTO = null;
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectUserByIdAndPwStmt = conn.prepareStatement(SELECT_USER_BY_ID_AND_PW_QUERY);) {
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

	public List<MovieDTO> selectMoviesWithActorNames(String title, String director, String[] actorArray, String genre) {
		List<MovieDTO> rsltMovies = new LinkedList<MovieDTO>();
		StringBuilder actorNameCondition = new StringBuilder(100);
		for (String actorName : actorArray) {
			actorNameCondition.append(" AND actor_name LIKE '%" + actorName + "%'");
		}
		String selectMoviesWithActorNameQuery = SELECT_MOVIES_WITH_ACTOR_NAME_QUERY
				+ " WHERE movie_name LIKE ? AND director_name LIKE ?"
				+ actorNameCondition.toString()
				+ " AND genre LIKE ?"
				+ " GROUP BY movie_no";
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectMoviesStmt = conn.prepareStatement(selectMoviesWithActorNameQuery);) {
				selectMoviesStmt.setString(1, "%" + title + "%");
				selectMoviesStmt.setString(2, "%" + director + "%");
				selectMoviesStmt.setString(3, "%" + genre + "%");
			try (ResultSet rs = selectMoviesStmt.executeQuery()) {
				while (rs.next()) { // 만약 앞에 나온 것 중에 movie_no이 같은게 있다면 actor에 추가, 최초 인 경우 그냥 이름 넣기
//					MovieDTO movie = new MovieDTO();
					rsltMovies.add(null);
				}
				// 마지막에 배우 이름 다 안들어 간 거 삭제
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rsltMovies;
	}

	public List<MovieDTO> selectMovies(String title, String director, String genre) {
		List<MovieDTO> rsltMovies = new LinkedList<MovieDTO>();
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectMoviesStmt = conn.prepareStatement(SELECT_MOVIES_QUERY);) {
			selectMoviesStmt.setString(1, "%" + title + "%");
			selectMoviesStmt.setString(2, "%" + director + "%");
			selectMoviesStmt.setString(3, "%" + genre + "%");
			try (ResultSet rs = selectMoviesStmt.executeQuery()) {
				while (rs.next()) { // 만약 앞에 나온 것 중에 movie_no이 같은게 있다면 actor에 추가, 최초 인 경우 그냥 이름 넣기
					MovieDTO movieDTO = new MovieDTO(rs.getInt("movie_no"), rs.getString("movie_name"),
							rs.getInt("running_time"), rs.getInt("age_rating"), rs.getString("director_name"),
							rs.getString("genre"), rs.getDate("release_date"), rs.getString("movie_info"),
							rs.getFloat("rating_information"), rs.getString("actor_names"));
					rsltMovies.add(movieDTO);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rsltMovies;

	}
}

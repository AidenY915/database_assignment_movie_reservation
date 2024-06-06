package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import db_info.DbInfo;
import db_info.SQLStatment;
import dto.BookingDTO;
import dto.MovieDTO;
import dto.ScreeningScheduleDTO;
import dto.SeatDTO;
import dto.UserDTO;

public class DAO implements DbInfo, SQLStatment {

	final static String DATABASE_URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";
	private String DbId = USER_ID;
	private String DbPw = USER_PW;

	private DAO() {
	}

	private static DAO dao = new DAO();

	public static DAO getDAO() {
		return dao;
	}

	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
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
				+ " WHERE movie_name LIKE ? AND director_name LIKE ?" + actorNameCondition.toString()
				+ " AND genre LIKE ?" + " GROUP BY movie_no";
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectMoviesStmt = conn.prepareStatement(selectMoviesWithActorNameQuery);) {
			selectMoviesStmt.setString(1, "%" + title + "%");
			selectMoviesStmt.setString(2, "%" + director + "%");
			selectMoviesStmt.setString(3, "%" + genre + "%");
			selectMoviesStmt.setString(1, "%" + title + "%");
			selectMoviesStmt.setString(2, "%" + director + "%");
			selectMoviesStmt.setString(3, "%" + genre + "%");
			try (ResultSet rs = selectMoviesStmt.executeQuery()) {
				while (rs.next()) {
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

	public List<MovieDTO> selectMovies(String title, String director, String genre) {
		List<MovieDTO> rsltMovies = new LinkedList<MovieDTO>();
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectMoviesStmt = conn.prepareStatement(SELECT_MOVIES_QUERY);) {
			selectMoviesStmt.setString(1, "%" + title + "%");
			selectMoviesStmt.setString(2, "%" + director + "%");
			selectMoviesStmt.setString(3, "%" + genre + "%");
			try (ResultSet rs = selectMoviesStmt.executeQuery()) {
				while (rs.next()) {
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

	public UserDTO selectUserById(String id) {
		UserDTO rsltUserDTO = null;
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectUserByIdStmt = conn.prepareStatement(selectUserById);) {
			selectUserByIdStmt.setString(1, id);
			try (ResultSet rs = selectUserByIdStmt.executeQuery()) {
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

	public boolean insertUser(UserDTO user) {
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement insertUserStmt = conn.prepareStatement(insertUser);) {
			insertUserStmt.setString(1, user.getId());
			insertUserStmt.setString(2, user.getUserName());
			insertUserStmt.setString(3, user.getphoneNo());
			insertUserStmt.setString(4, user.getEmail());
			insertUserStmt.setString(5, user.getPassword());
			insertUserStmt.setInt(6, user.isAdmin());
			insertUserStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean initializeDatabase() {
		String[] sqlStatements = { "SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;",
				"SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;",
				"SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';",
				"CREATE SCHEMA IF NOT EXISTS `db1` DEFAULT CHARACTER SET utf8;", "USE `db1`;",
				"DROP TABLE IF EXISTS booking;", "DROP TABLE IF EXISTS movie_ticket;", "DROP TABLE IF EXISTS user;",
				"DROP TABLE IF EXISTS seat;", "DROP TABLE IF EXISTS screening_schedule;",
				"DROP TABLE IF EXISTS screening_hall;", "DROP TABLE IF EXISTS actor;", "DROP TABLE IF EXISTS casting;",
				"DROP TABLE IF EXISTS movie;",
				"CREATE TABLE `db1`.`movie` (" + "  `movie_no` INT NOT NULL AUTO_INCREMENT,"
						+ "  `movie_name` CHAR(45) NOT NULL," + "  `running_time` INT NOT NULL,"
						+ "  `age_rating` INT NOT NULL," + "  `director_name` CHAR(20) NOT NULL,"
						+ "  `Genre` CHAR(45) NOT NULL," + "  `release_date` DATE NOT NULL,"
						+ "  `movie_info` TEXT(500) NOT NULL," + "  `rating_information` DECIMAL(3,1) NOT NULL,"
						+ "  PRIMARY KEY (`movie_no`)" + ") ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`screening_hall` (" + "  `hall_no` INT NOT NULL AUTO_INCREMENT,"
						+ "  `standard_price` INT NOT NULL," + "  `hall_name` CHAR(20) NOT NULL,"
						+ "  PRIMARY KEY (`hall_no`)" + ") ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`screening_schedule` (" + "  `schedule_no` INT NOT NULL AUTO_INCREMENT,"
						+ "  `hall_no` INT NOT NULL," + "  `screening_date` DATE NOT NULL,"
						+ "  `screening_day` CHAR(3) NOT NULL," + "  `screening_session` INT NOT NULL,"
						+ "  `screening_start_time` TIME NOT NULL," + "  `movie_no` INT NOT NULL,"
						+ "  PRIMARY KEY (`schedule_no`, `hall_no`, `movie_no`),"
						+ "  INDEX `fk_screening_schedule_screening_hall1_idx` (`hall_no` ASC) VISIBLE,"
						+ "  INDEX `fk_screening_schedule_movie1_idx` (`movie_no` ASC) VISIBLE,"
						+ "  CONSTRAINT `fk_screening_schedule_screening_hall1`" + "    FOREIGN KEY (`hall_no`)"
						+ "    REFERENCES `db1`.`screening_hall` (`hall_no`)" + "    ON DELETE NO ACTION"
						+ "    ON UPDATE NO ACTION," + "  CONSTRAINT `fk_screening_schedule_movie1`"
						+ "    FOREIGN KEY (`movie_no`)" + "    REFERENCES `db1`.`movie` (`movie_no`)"
						+ "    ON DELETE NO ACTION" + "    ON UPDATE NO ACTION" + ") ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`seat` (" + "  `hall_no` INT NOT NULL," + "  `seat_no` CHAR(6) NOT NULL,"
						+ "  PRIMARY KEY (`hall_no`, `seat_no`)," + "  CONSTRAINT `fk_seat_screening_hall1`"
						+ "    FOREIGN KEY (`hall_no`)" + "    REFERENCES `db1`.`screening_hall` (`hall_no`)"
						+ "    ON DELETE NO ACTION" + "    ON UPDATE NO ACTION" + ") ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`user` (" + "  `user_id` CHAR(30) NOT NULL," + "  `user_name` CHAR(30) NOT NULL,"
						+ "  `phone_no` CHAR(11) NOT NULL," + "  `email` CHAR(100) NOT NULL,"
						+ "  `password` CHAR(30) NOT NULL," + "  `is_admin` TINYINT NOT NULL DEFAULT 0,"
						+ "  PRIMARY KEY (`user_id`)" + ") ENGINE = InnoDB;",
				"CREATE INDEX idx_seat_no ON seat(seat_no);",
				"CREATE TABLE `db1`.`booking` (" + "  `booking_no` INT NOT NULL AUTO_INCREMENT,"
						+ "  `payment_method` CHAR(20)," + "  `payment_status` CHAR(20) NOT NULL DEFAULT 'pending',"
						+ "  `payment_amount` INT NOT NULL,"
						+ "  `payment_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
						+ "  `schedule_no` INT NOT NULL," + "  `seat_no` CHAR(3) NOT NULL,"
						+ "  `user_id` CHAR(30) NOT NULL,"
						+ "  PRIMARY KEY (`booking_no`, `schedule_no`, `seat_no`, `user_id`),"
						+ "  INDEX `fk_booking_info_screening_schedule1_idx` (`schedule_no` ASC) VISIBLE,"
						+ "  INDEX `fk_booking_info_seat1_idx` (`seat_no` ASC) VISIBLE,"
						+ "  INDEX `fk_booking_info_user1_idx` (`user_id` ASC) VISIBLE,"
						+ "  CONSTRAINT `fk_booking_info_screening_schedule1`" + "    FOREIGN KEY (`schedule_no`)"
						+ "    REFERENCES `db1`.`screening_schedule` (`schedule_no`)" + "    ON DELETE NO ACTION"
						+ "    ON UPDATE NO ACTION," + "  CONSTRAINT `fk_booking_info_seat1`"
						+ "    FOREIGN KEY (`seat_no`)" + "    REFERENCES `db1`.`seat` (`seat_no`)"
						+ "    ON DELETE NO ACTION" + "    ON UPDATE NO ACTION,"
						+ "  CONSTRAINT `fk_booking_info_user1`" + "    FOREIGN KEY (`user_id`)"
						+ "    REFERENCES `db1`.`user` (`user_id`)" + "    ON DELETE NO ACTION"
						+ "    ON UPDATE NO ACTION" + ") ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`movie_ticket` (" + "  `ticket_no` INT NOT NULL AUTO_INCREMENT,"
						+ "  `booking_no` INT NOT NULL," + "  PRIMARY KEY (`ticket_no`, `booking_no`),"
						+ "  INDEX `fk_movie_ticket_booking_info1_idx` (`booking_no` ASC) VISIBLE,"
						+ "  CONSTRAINT `fk_movie_ticket_booking_info1`" + "    FOREIGN KEY (`booking_no`)"
						+ "    REFERENCES `db1`.`booking` (`booking_no`)" + "    ON DELETE CASCADE"
						+ "    ON UPDATE NO ACTION" + ") ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`actor` (" + "  `actor_no` INT NOT NULL AUTO_INCREMENT,"
						+ "  `actor_name` CHAR(20) NOT NULL," + "  PRIMARY KEY (`actor_no`)" + ") ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`casting` (" + "  `actor_no` INT NOT NULL," + "  `movie_no` INT NOT NULL,"
						+ "  PRIMARY KEY (`actor_no`, `movie_no`),"
						+ "  INDEX `fk_appearance_actor_idx` (`actor_no` ASC) VISIBLE,"
						+ "  INDEX `fk_appearance_movie1_idx` (`movie_no` ASC) VISIBLE,"
						+ "  CONSTRAINT `fk_appearance_actor`" + "    FOREIGN KEY (`actor_no`)"
						+ "    REFERENCES `db1`.`actor` (`actor_no`)" + "    ON DELETE NO ACTION"
						+ "    ON UPDATE NO ACTION," + "  CONSTRAINT `fk_appearance_movie1`"
						+ "    FOREIGN KEY (`movie_no`)" + "    REFERENCES `db1`.`movie` (`movie_no`)"
						+ "    ON DELETE NO ACTION" + "    ON UPDATE NO ACTION" + ") ENGINE = InnoDB;",
				"SET SQL_MODE=@OLD_SQL_MODE;", "SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;",
				"SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;", "USE db1;",
				"INSERT INTO user VALUES('user1', '사용자1', '01000000000', 'user1@naver.com', 'user1', '0');",
				"INSERT INTO user VALUES('root', '관리자', '01011111111', 'root@naver.com', '1234', '1');",
				"INSERT INTO movie (movie_no, movie_name, running_time, age_rating, director_name, Genre, release_date, movie_info, rating_information) VALUES "
						+ "(1, '어벤져스: 엔드게임', 181, 13, '안소니 루소, 조 루소', '액션, 어드벤처, 드라마', '2019-04-26', '남은 어벤져스는 타노스와의 대규모 결전을 위해 사라진 동료들을 되찾는 방법을 찾아야 합니다.', 8.4),"
						+ "(2, '대부', 175, 17, '프랜시스 포드 코폴라', '범죄, 드라마', '1972-03-24', '마피아 가족의 수장 비토 콜레오네와 그의 아들 마이클 콜레오네의 이야기를 중심으로 한 범죄 드라마.', 9.2),"
						+ "(3, '쇼생크 탈출', 142, 17, '프랭크 다라본트', '드라마', '1994-09-23', '억울하게 종신형을 선고받은 앤디 듀프레인이 쇼생크 감옥에서 희망을 잃지 않고 탈출을 계획하는 이야기.', 9.3),"
						+ "(4, '쉰들러 리스트', 195, 17, '스티븐 스필버그', '전기, 드라마, 역사', '1993-12-15', '나치 독일의 사업가 오스카 쉰들러가 유대인 노동자들을 고용해 그들의 목숨을 구한 실화를 바탕으로 한 드라마.', 9.0),"
						+ "(5, '포레스트 검프', 142, 13, '로버트 저메키스', '드라마, 로맨스', '1994-07-06', '지능은 낮지만 순수한 마음을 가진 포레스트 검프가 인생의 중요한 순간들을 경험하며 겪는 이야기.', 8.8),"
						+ "(6, '글래디에이터', 155, 17, '리들리 스콧', '액션, 모험, 드라마', '2000-05-05', '로마 제국의 장군 막시무스가 배신당하고 노예 검투사가 되어 복수를 위해 싸우는 이야기.', 8.5),"
						+ "(7, '다크 나이트', 152, 13, '크리스토퍼 놀란', '액션, 범죄, 드라마', '2008-07-18', '배트맨이 고담시의 범죄를 퇴치하는 과정에서 사이코패스 범죄자 조커와의 치열한 싸움.', 9.0),"
						+ "(8, '타이타닉', 195, 13, '제임스 카메론', '드라마, 로맨스', '1997-12-19', 'RMS 타이타닉 호의 비극적인 침몰 사건을 배경으로, 다른 신분의 두 청춘, 잭과 로즈의 로맨스.', 7.9),"
						+ "(9, '라이언 일병 구하기', 169, 17, '스티븐 스필버그', '드라마, 전쟁', '1998-07-24', '노르망디 상륙작전 후, 잃어버린 라이언 일병을 구하기 위해 특수 임무를 수행하는 미군 부대.', 8.6),"
						+ "(10, '반지의 제왕: 왕의 귀환', 201, 13, '피터 잭슨', '액션, 모험, 드라마', '2003-12-17', '사우론의 군대에 맞서 중간계를 구하기 위해 프로도와 그의 친구들이 마지막 전투를 벌이는 이야기.', 9.0),"
						+ "(11, '인셉션', 148, 13, '크리스토퍼 놀란', '액션, 모험, SF', '2010-07-16', '꿈을 통해 타인의 잠재의식에 들어가 아이디어를 심는 특수 기술을 사용하는 도둑, 코브와 그의 팀.', 8.8),"
						+ "(12, '매트릭스', 136, 17, '워쇼스키 형제', '액션, SF', '1999-03-31', '가상 현실 세계와 인간 세계의 경계에서 살아가는 해커 네오가 진실을 깨닫고, 인류를 구하기 위해 싸우는 이야기.', 8.7);",
				"INSERT INTO actor (actor_name) VALUES " + "('로버트 다우니 주니어')," + "('크리스 에반스')," + "('스칼렛 요한슨'),"
						+ "('말론 브란도')," + "('알 파치노')," + "('제임스 칸')," + "('팀 로빈스')," + "('모건 프리먼')," + "('윌리엄 새들러'),"
						+ "('리암 니슨')," + "('벤 킹슬리')," + "('랄프 파인즈')," + "('톰 행크스')," + "('로빈 라이트')," + "('게리 시니즈'),"
						+ "('러셀 크로우')," + "('호아킨 피닉스')," + "('코니 닐슨')," + "('크리스찬 베일')," + "('히스 레저')," + "('아론 에크하트'),"
						+ "('레오나르도 디카프리오')," + "('케이트 윈슬렛')," + "('빌리 제인')," + "('맷 데이먼')," + "('톰 시즈모어'),"
						+ "('엘리야 우드')," + "('이안 맥켈런')," + "('비고 모텐슨')," + "('조셉 고든 레빗')," + "('엘렌 페이지'),"
						+ "('키아누 리브스')," + "('로런스 피시번')," + "('캐리앤 모스');",
				"INSERT INTO casting (actor_no, movie_no) VALUES " + "(1, 1)," + "(2, 1)," + "(3, 1)," + "(4, 2),"
						+ "(5, 2)," + "(6, 2)," + "(7, 3)," + "(8, 3)," + "(9, 3)," + "(10, 4)," + "(11, 4),"
						+ "(12, 4)," + "(13, 5)," + "(14, 5)," + "(15, 5)," + "(16, 6)," + "(17, 6)," + "(18, 6),"
						+ "(19, 7)," + "(20, 7)," + "(21, 7)," + "(22, 8)," + "(23, 8)," + "(24, 8)," + "(13, 9),"
						+ "(25, 9)," + "(26, 9)," + "(27, 10)," + "(28, 10)," + "(29, 10)," + "(22, 11)," + "(30, 11),"
						+ "(31, 11)," + "(32, 12)," + "(33, 12)," + "(34, 12);",
				"INSERT INTO screening_hall (hall_no, standard_price, hall_name) VALUES "
						+ "(1, 15000, 'IMAX')," + "(2, 12000, '4DX')," + "(3, 10000, 'Standard'),"
						+ "(4, 14000, 'Dolby Cinema')," + "(5, 13000, 'ScreenX')," + "(6, 11000, 'Gold Class'),"
						+ "(7, 12500, 'VIP Theater')," + "(8, 10000, 'Regular')," + "(9, 9500, 'Economy'),"
						+ "(10, 13500, 'Premiere')," + "(11, 15000, 'Super Screen')," + "(12, 16000, 'Luxury Lounge');",
				"CALL loopSeatInsert()",
				"INSERT INTO screening_schedule (hall_no, screening_date, screening_day, screening_session, screening_start_time, movie_no) VALUES "
						+ "(1, '2024-06-06', 'Thu', 1, '12:00:00', 1)," + "(2, '2024-06-07', 'Fri', 2, '14:00:00', 1),"
						+ "(3, '2024-06-08', 'Sat', 3, '16:00:00', 1)," + "(4, '2024-06-09', 'Sun', 4, '18:00:00', 1),"
						+ "(5, '2024-06-06', 'Thu', 1, '20:00:00', 2)," + "(6, '2024-06-07', 'Fri', 2, '22:00:00', 2),"
						+ "(7, '2024-06-08', 'Sat', 3, '10:00:00', 2)," + "(8, '2024-06-09', 'Sun', 4, '12:00:00', 2),"
						+ "(9, '2024-06-06', 'Thu', 1, '14:00:00', 3)," + "(10, '2024-06-07', 'Fri', 2, '16:00:00', 3),"
						+ "(11, '2024-06-08', 'Sat', 3, '18:00:00', 3),"
						+ "(12, '2024-06-09', 'Sun', 4, '20:00:00', 3)," + "(1, '2024-06-06', 'Thu', 1, '22:00:00', 4),"
						+ "(2, '2024-06-07', 'Fri', 2, '10:00:00', 4)," + "(3, '2024-06-08', 'Sat', 3, '12:00:00', 4),"
						+ "(4, '2024-06-09', 'Sun', 4, '14:00:00', 4)," + "(5, '2024-06-06', 'Thu', 1, '16:00:00', 5),"
						+ "(6, '2024-06-07', 'Fri', 2, '18:00:00', 5)," + "(7, '2024-06-08', 'Sat', 3, '20:00:00', 5),"
						+ "(8, '2024-06-09', 'Sun', 4, '22:00:00', 5)," + "(9, '2024-06-06', 'Thu', 1, '10:00:00', 6),"
						+ "(10, '2024-06-07', 'Fri', 2, '12:00:00', 6),"
						+ "(11, '2024-06-08', 'Sat', 3, '14:00:00', 6),"
						+ "(12, '2024-06-09', 'Sun', 4, '16:00:00', 6)," + "(1, '2024-06-06', 'Thu', 1, '18:00:00', 7),"
						+ "(2, '2024-06-07', 'Fri', 2, '20:00:00', 7)," + "(3, '2024-06-08', 'Sat', 3, '22:00:00', 7),"
						+ "(4, '2024-06-09', 'Sun', 4, '10:00:00', 7)," + "(5, '2024-06-06', 'Thu', 1, '12:00:00', 8),"
						+ "(6, '2024-06-07', 'Fri', 2, '14:00:00', 8)," + "(7, '2024-06-08', 'Sat', 3, '16:00:00', 8),"
						+ "(8, '2024-06-09', 'Sun', 4, '18:00:00', 8)," + "(9, '2024-06-06', 'Thu', 1, '20:00:00', 9),"
						+ "(10, '2024-06-07', 'Fri', 2, '22:00:00', 9),"
						+ "(11, '2024-06-08', 'Sat', 3, '10:00:00', 9),"
						+ "(12, '2024-06-09', 'Sun', 4, '12:00:00', 9),"
						+ "(1, '2024-06-06', 'Thu', 1, '14:00:00', 10),"
						+ "(2, '2024-06-07', 'Fri', 2, '16:00:00', 10),"
						+ "(3, '2024-06-08', 'Sat', 3, '18:00:00', 10),"
						+ "(4, '2024-06-09', 'Sun', 4, '20:00:00', 10),"
						+ "(5, '2024-06-06', 'Thu', 1, '22:00:00', 11),"
						+ "(6, '2024-06-07', 'Fri', 2, '10:00:00', 11),"
						+ "(7, '2024-06-08', 'Sat', 3, '12:00:00', 11),"
						+ "(8, '2024-06-09', 'Sun', 4, '14:00:00', 11),"
						+ "(9, '2024-06-06', 'Thu', 1, '16:00:00', 12),"
						+ "(10, '2024-06-07', 'Fri', 2, '18:00:00', 12),"
						+ "(11, '2024-06-08', 'Sat', 3, '20:00:00', 12),"
						+ "(12, '2024-06-09', 'Sun', 4, '22:00:00', 12);",
				"INSERT INTO booking (payment_method, payment_status, payment_amount, payment_date, schedule_no, seat_no, user_id) VALUES "
						+ "('카드', '결제완료', 15000, '2024-06-04', 1, 'A1', 'user1'),"
						+ "('현금', '미결제', 12000, '2024-06-04', 2, 'B3', 'user1'),"
						+ "('카드', '결제완료', 10000, '2024-06-04', 3, 'A3', 'user1'),"
						+ "('카드', '결제완료', 14000, '2024-06-04', 4, 'A4', 'user1'),"
						+ "('현금', '미결제', 13000, '2024-06-04', 5, 'A5', 'user1'),"
						+ "('카드', '결제완료', 11000, '2024-06-05', 6, 'B1', 'user1'),"
						+ "('카드', '결제완료', 12500, '2024-06-05', 7, 'C2', 'user1'),"
						+ "('현금', '미결제', 10000, '2024-06-05', 8, 'D4', 'user1'),"
						+ "('카드', '결제완료', 9500, '2024-06-05', 9, 'E5', 'user1'),"
						+ "('카드', '결제완료', 13500, '2024-06-05', 10, 'A6', 'user1'),"
						+ "('현금', '미결제', 15000, '2024-06-05', 11, 'B7', 'user1'),"
						+ "('카드', '결제완료', 16000, '2024-06-05', 12, 'C8', 'user1');",
				"INSERT INTO movie_ticket (booking_no) VALUES (1), (2), (3), (4), (5), (6);" };

		String[] procedures = { "DROP PROCEDURE IF EXISTS loopSeatInsert;",
				"CREATE PROCEDURE loopSeatInsert() " + "BEGIN " + "    DECLARE i INT DEFAULT 1; "
						+ "    DECLARE j INT; " + "    WHILE i <= 12 DO " + "        SET j = 1; "
						+ "        WHILE j <= 10 DO " + "            INSERT INTO SEAT VALUES (i, concat('A', j)); "
						+ "            INSERT INTO SEAT VALUES (i, concat('B', j)); "
						+ "            INSERT INTO SEAT VALUES (i, concat('C', j)); "
						+ "            INSERT INTO SEAT VALUES (i, concat('D', j)); "
						+ "            INSERT INTO SEAT  VALUES (i, concat('E', j)); " + "            SET j = j + 1; "
						+ "        END WHILE; " + "        SET i = i + 1; " + "    END WHILE; " + "END" };

		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				Statement stmt = conn.createStatement()) {
			for (String procedure : procedures) {
				stmt.execute(procedure);
			}
			for (String sql : sqlStatements) {
				stmt.executeUpdate(sql);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean executeSQL(String sql) {
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String viewTableData(String tableName) {
		StringBuilder result = new StringBuilder();
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName);
				ResultSet rs = stmt.executeQuery()) {

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				result.append(metaData.getColumnName(i)).append("\t");
			}
			result.append("\n");

			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					result.append(rs.getString(i)).append("\t");
				}
				result.append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public boolean insertData(String tableName, String[] columns, String[] values) {
		StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
		for (String column : columns) {
			query.append(column.split(" ")[0]).append(", ");
		}
		query.setLength(query.length() - 2);
		query.append(") VALUES (");
		for (int i = 0; i < values.length; i++) {
			query.append("?, ");
		}
		query.setLength(query.length() - 2);
		query.append(")");

		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement stmt = conn.prepareStatement(query.toString())) {
			for (int i = 0; i < values.length; i++) {
				stmt.setString(i + 1, values[i]);
			}
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<BookingDTO> getBookingByUserId(String userId) {
		List<BookingDTO> bookingList = new LinkedList<>();
		String query = "SELECT b.booking_no, b.payment_method, b.payment_status, b.payment_amount, b.payment_date, "
				+ "b.schedule_no, b.seat_no, b.user_id, m.movie_name, s.screening_date, h.hall_name, "
				+ "s.screening_start_time " + "FROM booking b "
				+ "JOIN screening_schedule s ON b.schedule_no = s.schedule_no "
				+ "JOIN movie m ON s.movie_no = m.movie_no " + "JOIN screening_hall h ON s.hall_no = h.hall_no "
				+ "WHERE b.user_id = ?";

		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					BookingDTO booking = new BookingDTO(rs.getInt("booking_no"), rs.getString("payment_method"),
							rs.getString("payment_status"), rs.getInt("payment_amount"),
							rs.getTimestamp("payment_date"), rs.getInt("schedule_no"), rs.getString("seat_no"),
							rs.getString("user_id"), rs.getString("movie_name"), rs.getDate("screening_date"),
							rs.getString("hall_name"), rs.getTime("screening_start_time"));
					bookingList.add(booking);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookingList;
	}

	public boolean deleteBooking(int bookingNo) {
		String deleteMovieTicketSql = "DELETE FROM movie_ticket WHERE booking_no = ?";
		String deleteBookingSql = "DELETE FROM booking WHERE booking_no = ?";
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement deleteMovieTicketStmt = conn.prepareStatement(deleteMovieTicketSql);
				PreparedStatement deleteBookingStmt = conn.prepareStatement(deleteBookingSql)) {

			conn.setAutoCommit(false);

			deleteMovieTicketStmt.setInt(1, bookingNo);
			deleteMovieTicketStmt.executeUpdate();

			deleteBookingStmt.setInt(1, bookingNo);
			int rowsAffected = deleteBookingStmt.executeUpdate();

			conn.commit();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<ScreeningScheduleDTO> selectSchedulesByMovieNo(int movieNo) {
		List<ScreeningScheduleDTO> rsltSchedules = new LinkedList<>();
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectSchedulesStmt = conn
						.prepareStatement(SELECT_SCREENING_SCHEDUELES_BY_MOVIE_NO);) {
			selectSchedulesStmt.setInt(1, movieNo);
			try (ResultSet rs = selectSchedulesStmt.executeQuery()) {
				while (rs.next()) {
					ScreeningScheduleDTO ScreeningScheduleDTO = new ScreeningScheduleDTO(rs.getInt("schedule_no"),
							rs.getInt("hall_no"), rs.getDate("screening_date"), rs.getString("screening_day"),
							rs.getInt("screening_session"), rs.getTime("screening_start_time"), rs.getInt("movie_no"),
							rs.getInt("standard_price"), rs.getString("hall_name"));
					rsltSchedules.add(ScreeningScheduleDTO);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rsltSchedules;
	}

	public List<SeatDTO> selectUnbookedSeatsBySchedule(int hallNo, int scheduleNo) {
		List<SeatDTO> unbookedSeats = new ArrayList<>(100);
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectSeatsStmt = conn.prepareStatement(SELECT_UNBOOKED_SEATS_BY_SCHEDULE_NO);) {
			selectSeatsStmt.setInt(1, hallNo);
			selectSeatsStmt.setInt(2, scheduleNo);
			try (ResultSet rs = selectSeatsStmt.executeQuery()) {
				while (rs.next()) {
					SeatDTO seatDTO = new SeatDTO(hallNo, rs.getString("seat_no"));
					unbookedSeats.add(seatDTO);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unbookedSeats;
	}

	public List<SeatDTO> selectAllSeatsByHallNo(int hallNo) {
		List<SeatDTO> seats = new ArrayList<>(100);
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectSeatsStmt = conn.prepareStatement(SELECT_ALL_SEATS_BY_HALL_NO);) {
			selectSeatsStmt.setInt(1, hallNo);
			try (ResultSet rs = selectSeatsStmt.executeQuery()) {
				while (rs.next()) {
					SeatDTO seatDTO = new SeatDTO(hallNo, rs.getString("seat_no"));
					seats.add(seatDTO);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seats;
	}

	public int insertBooking(ScreeningScheduleDTO schedule, SeatDTO seat, UserDTO user) {
		int rslt = 0;
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement insertBookingStmt = conn.prepareStatement(INSERT_BOOKING);) {
			insertBookingStmt.setInt(1, schedule.getStandardPrice());
			insertBookingStmt.setInt(2, schedule.getScheduleNo());
			insertBookingStmt.setString(3, seat.getSeatNo());
			insertBookingStmt.setString(4, user.getId());
			rslt = insertBookingStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rslt;
	}

	public List<BookingDTO> selectUnpaidBookings(ScreeningScheduleDTO schedule, List<SeatDTO> seats, UserDTO user) {
		List<BookingDTO> bookings = new LinkedList<>();
		for (SeatDTO seat : seats) {
			try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
					PreparedStatement selectbookingsStmt = conn.prepareStatement(SELECT_UNPAID_BOOKING);) {
				selectbookingsStmt.setInt(1, schedule.getScheduleNo());
				selectbookingsStmt.setString(2, seat.getSeatNo());
				selectbookingsStmt.setString(3, user.getId());
				try (ResultSet rs = selectbookingsStmt.executeQuery()) {
					while (rs.next()) {
						BookingDTO bookingDTO = new BookingDTO(rs.getInt("booking_no"), rs.getString("payment_method"),
								rs.getString("payment_status"), rs.getInt("payment_amount"),
								rs.getTimestamp("payment_date"), rs.getInt("schedule_no"), rs.getString("seat_no"),
								rs.getString("user_id"));
						bookings.add(bookingDTO);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bookings;
	}

	public void insertTicket(BookingDTO booking) {
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement insertTicketStmt = conn.prepareStatement(INSERT_TICKET);) {
			insertTicketStmt.setInt(1, booking.getBookingNo());
			insertTicketStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}

	public void deleteTicket(BookingDTO booking) {
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement deleteTicketStmt = conn.prepareStatement(DELETE_TICKET);) {
			deleteTicketStmt.setInt(1, booking.getBookingNo());
			deleteTicketStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}

	public boolean updateBookingSchedule(int bookingNo, int newScheduleNo) {
		String sql = "UPDATE booking SET schedule_no = ? WHERE booking_no = ?";
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, newScheduleNo);
			stmt.setInt(2, bookingNo);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getMovieNoByScheduleNo(int scheduleNo) {
		String sql = "SELECT movie_no FROM screening_schedule WHERE schedule_no = ?";
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, scheduleNo);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("movie_no");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public MovieDTO getMovieByNo(int movieNo) {
		String sql = "SELECT * FROM movie WHERE movie_no = ?";
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, movieNo);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new MovieDTO(rs.getInt("movie_no"), rs.getString("movie_name"), rs.getInt("running_time"),
							rs.getInt("age_rating"), rs.getString("director_name"), rs.getString("Genre"),
							rs.getDate("release_date"), rs.getString("movie_info"), rs.getFloat("rating_information"),
							(List<String>) null);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updatePaymentInfo(BookingDTO booking) {
		String sql = "UPDATE booking SET payment_method = ?, payment_status = '결제완료', payment_amount = ? WHERE booking_no = ?";
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, booking.getPaymentMethod());
			stmt.setInt(2, booking.getPaymentAmount());
			stmt.setInt(3, booking.getBookingNo());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isTicketIssued(int bookingNo) {
		boolean rslt = false;
		try (Connection conn = DriverManager.getConnection(DATABASE_URL, DbId, DbPw);
				PreparedStatement selectTicketStmt = conn.prepareStatement(SELECT_TICKET);) {
			selectTicketStmt.setInt(1, bookingNo);
			try (ResultSet rs = selectTicketStmt.executeQuery()) {
				if (rs.next()) {
					rslt = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rslt;
	}

}

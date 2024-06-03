package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import db_info.DbInfo;
import db_info.SQLStatment;
import dto.UserDTO;

public class DAO implements DbInfo, SQLStatment {
	private DAO() {
	}

	public static DAO dao = new DAO();

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

	public UserDTO selectUserById(String id) {
		String databaseUrl = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";
		UserDTO rsltUserDTO = null;
		try (Connection conn = DriverManager.getConnection(databaseUrl, USER_ID, USER_PW);
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
		String databaseUrl = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";
		try (Connection conn = DriverManager.getConnection(databaseUrl, USER_ID, USER_PW);
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
		String databaseUrl = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";

		String[] sqlStatements = { "SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;",
				"SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;",
				"SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';",
				"DROP TABLE IF EXISTS booking;", "DROP TABLE IF EXISTS movie_ticket;", "DROP TABLE IF EXISTS user;",
				"DROP TABLE IF EXISTS seat;", "DROP TABLE IF EXISTS screening_schedule;",
				"DROP TABLE IF EXISTS screening_hall;", "DROP TABLE IF EXISTS actor;", "DROP TABLE IF EXISTS casting;",
				"DROP TABLE IF EXISTS movie;",
				"CREATE TABLE `db1`.`movie` (" + "`movie_no` INT NOT NULL AUTO_INCREMENT,"
						+ "`movie_name` CHAR(45) NOT NULL," + "`running_time` INT NOT NULL,"
						+ "`age_rating` INT NOT NULL," + "`director_name` CHAR(20) NOT NULL,"
						+ "`Genre` CHAR(45) NOT NULL," + "`release_date` DATE NOT NULL,"
						+ "`movie_info` TEXT(500) NOT NULL," + "`rating_information` DECIMAL(3,1) NOT NULL,"
						+ "PRIMARY KEY (`movie_no`))" + "ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`screening_hall` (" + "`hall_no` INT NOT NULL AUTO_INCREMENT,"
						+ "`standard_price` INT NOT NULL," + "`hall_name` CHAR(20) NOT NULL,"
						+ "PRIMARY KEY (`hall_no`))" + "ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`screening_schedule` (" + "`schedule_no` INT NOT NULL AUTO_INCREMENT,"
						+ "`hall_no` INT NOT NULL," + "`screening_date` DATE NOT NULL,"
						+ "`screening_day` CHAR(3) NOT NULL," + "`screening_session` INT NOT NULL,"
						+ "`screening_start_time` TIME NOT NULL," + "`movie_no` INT NOT NULL,"
						+ "PRIMARY KEY (`schedule_no`, `hall_no`, `movie_no`),"
						+ "INDEX `fk_screening_schedule_screening_hall1_idx` (`hall_no` ASC) VISIBLE,"
						+ "INDEX `fk_screening_schedule_movie1_idx` (`movie_no` ASC) VISIBLE,"
						+ "CONSTRAINT `fk_screening_schedule_screening_hall1`" + "FOREIGN KEY (`hall_no`)"
						+ "REFERENCES `db1`.`screening_hall` (`hall_no`)" + " ON DELETE NO ACTION"
						+ "  ON UPDATE NO ACTION," + "CONSTRAINT `fk_screening_schedule_movie1`"
						+ "FOREIGN KEY (`movie_no`)" + "REFERENCES `db1`.`movie` (`movie_no`)" + " ON DELETE NO ACTION"
						+ " ON UPDATE NO ACTION)" + "ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`seat` (" + "`hall_no` INT NOT NULL," + "`seat_no` CHAR(3) NOT NULL,"
						+ "PRIMARY KEY (`hall_no`, `seat_no`)," + "CONSTRAINT `fk_seat_screening_hall1`"
						+ "FOREIGN KEY (`hall_no`)" + "REFERENCES `db1`.`screening_hall` (`hall_no`)"
						+ " ON DELETE NO ACTION" + " ON UPDATE NO ACTION)" + "ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`user` (" + "`user_id` CHAR(30) NOT NULL," + "`user_name` CHAR(30) NOT NULL,"
						+ "`phone_no` CHAR(11) NOT NULL," + "`email` CHAR(100) NOT NULL,"
						+ "`password` CHAR(30) NOT NULL," + "`is_admin` TINYINT NOT NULL DEFAULT 0,"
						+ "PRIMARY KEY (`user_id`))" + "ENGINE = InnoDB;",
				"CREATE INDEX idx_seat_no ON seat(seat_no);", // booking의 외래키 설정 떄문
				"CREATE TABLE `db1`.`booking` (" + "`booking_no` INT NOT NULL AUTO_INCREMENT,"
						+ "`payment_method` CHAR(20) NOT NULL,"
						+ "`payment_status` CHAR(20) NOT NULL DEFAULT 'pending'," + "`payment_amount` INT NOT NULL,"
						+ "`payment_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," + "`schedule_no` INT NOT NULL,"
						+ "`seat_no` CHAR(3) NOT NULL," + "`user_id` CHAR(30) NOT NULL,"
						+ "PRIMARY KEY (`booking_no`, `schedule_no`, `seat_no`, `user_id`),"
						+ "INDEX `fk_booking_info_screening_schedule1_idx` (`schedule_no` ASC) VISIBLE,"
						+ "INDEX `fk_booking_info_seat1_idx` (`seat_no` ASC) VISIBLE,"
						+ "INDEX `fk_booking_info_user1_idx` (`user_id` ASC) VISIBLE,"
						+ "CONSTRAINT `fk_booking_info_screening_schedule1`" + "FOREIGN KEY (`schedule_no`)"
						+ "REFERENCES `db1`.`screening_schedule` (`schedule_no`)" + " ON DELETE NO ACTION"
						+ " ON UPDATE NO ACTION," + "CONSTRAINT `fk_booking_info_seat1`" + "FOREIGN KEY (`seat_no`)"
						+ "REFERENCES `db1`.`seat` (`seat_no`)" + " ON DELETE NO ACTION" + " ON UPDATE NO ACTION,"
						+ "CONSTRAINT `fk_booking_info_user1`" + "FOREIGN KEY (`user_id`)"
						+ "REFERENCES `db1`.`user` (`user_id`)" + " ON DELETE NO ACTION" + " ON UPDATE NO ACTION)"
						+ "ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`movie_ticket` (" + "`ticket_no` INT NOT NULL," + "`booking_no` INT NOT NULL,"
						+ "PRIMARY KEY (`ticket_no`, `booking_no`),"
						+ "INDEX `fk_movie_ticket_booking_info1_idx` (`booking_no` ASC) VISIBLE,"
						+ "CONSTRAINT `fk_movie_ticket_booking_info1`" + "FOREIGN KEY (`booking_no`)"
						+ "REFERENCES `db1`.`booking` (`booking_no`)" + " ON DELETE NO ACTION" + " ON UPDATE NO ACTION)"
						+ "ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`actor` (" + "`actor_no` INT NOT NULL AUTO_INCREMENT,"
						+ "`actor_name` CHAR(20) NOT NULL," + "PRIMARY KEY (`actor_no`))" + "ENGINE = InnoDB;",
				"CREATE TABLE `db1`.`casting` (" + "`actor_no` INT NOT NULL," + "`movie_no` INT NOT NULL,"
						+ "PRIMARY KEY (`actor_no`, `movie_no`),"
						+ "INDEX `fk_appearance_actor_idx` (`actor_no` ASC) VISIBLE,"
						+ "INDEX `fk_appearance_movie1_idx` (`movie_no` ASC) VISIBLE,"
						+ "CONSTRAINT `fk_appearance_actor`" + "FOREIGN KEY (`actor_no`)"
						+ "REFERENCES `db1`.`actor` (`actor_no`)" + " ON DELETE NO ACTION" + " ON UPDATE NO ACTION,"
						+ "CONSTRAINT `fk_appearance_movie1`" + "FOREIGN KEY (`movie_no`)"
						+ "REFERENCES `db1`.`movie` (`movie_no`)" + " ON DELETE NO ACTION" + " ON UPDATE NO ACTION)"
						+ "ENGINE = InnoDB;",
				"SET SQL_MODE=@OLD_SQL_MODE;", "SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;",
				"SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;" };

		try (Connection conn = DriverManager.getConnection(databaseUrl, USER_ID, USER_PW);
				Statement stmt = conn.createStatement()) {

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
		String databaseUrl = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";
		try (Connection conn = DriverManager.getConnection(databaseUrl, USER_ID, USER_PW);
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			
			System.out.println(sql);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String viewTableData(String tableName) {
		String databaseUrl = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";
		StringBuilder result = new StringBuilder();

		try (Connection conn = DriverManager.getConnection(databaseUrl, USER_ID, USER_PW);
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
		String databaseUrl = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=Asia/Seoul";
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
        for (String column : columns) {
            query.append(column).append(", ");
        }
        query.setLength(query.length() - 2);
        query.append(") VALUES (");
        for (int i = 0; i < values.length; i++) {
            query.append("?, ");
        }
        query.setLength(query.length() - 2);
        query.append(")");

        try (Connection conn = DriverManager.getConnection(databaseUrl, USER_ID, USER_PW);
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

}

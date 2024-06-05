package service;

import java.util.LinkedList;
import java.util.List;
import dao.DAO;
import dto.BookingDTO;
import dto.MovieDTO;
import dto.ScreeningScheduleDTO;
import dto.SeatDTO;
import dto.UserDTO;
import frame.MovieReservationFrame;

public class Service {
    private DAO dao = DAO.getDAO();
    private MovieReservationFrame frame;

    private static Service service;
    private Service() {
    }
    public static Service getService() {
        if(service == null)
            service = new Service();
        return service;
    }

    public int login(String id, String pw, int isAdmin) {
        if(frame == null) {
            frame = MovieReservationFrame.getMovieReservationFrame();
        }
        UserDTO userDTO = dao.selectUserByIdAndPw(id, pw, isAdmin);
        if(userDTO == null) return -1;
        frame.setLoginSession(userDTO);
        return userDTO.isAdmin();
    }

    public boolean register(String id, String password, String userName, String phoneNo, String email, int isAdmin) {
        if(isIdDuplicated(id)) return false;

        UserDTO newUser = new UserDTO(id, userName, phoneNo, email, password, isAdmin);
        return dao.insertUser(newUser);
    }

    public boolean isIdDuplicated(String id) {
        return dao.selectUserById(id) != null;
    }

    public boolean initializeDatabase() {
        return dao.initializeDatabase();
    }

    public boolean insertData(String tableName, String[] columns, String[] values) {
        return dao.insertData(tableName, columns, values);
    }

    public boolean executeSQL(String sql) {
        return dao.executeSQL(sql);
    }

    public String viewTableData(String tableName) {
        return dao.viewTableData(tableName);
    }

    public List<MovieDTO> getMovieList(String title, String director, String[] actorArray, String genre) {
        if(actorArray.length >= 1 && !actorArray[0].equals(""))
            return dao.selectMoviesWithActorNames(title, director, actorArray, genre);
        return dao.selectMovies(title, director, genre);
    }

    public List<MovieDTO> getAllMovies() {
        return dao.selectMovies("", "", "");
    }

    public List<ScreeningScheduleDTO> getScheduleListByMovieNo(MovieDTO movieDTO) {
        int movieNo = movieDTO.getMovieNo();
        return dao.selectSchedulesByMovieNo(movieNo);
    }

    public List<SeatDTO> getUnbookedSeatsBySchedule(ScreeningScheduleDTO scheduleDTO) {
        return dao.selectUnbookedSeatsBySchedule(scheduleDTO.getHallNo(), scheduleDTO.getScheduleNo());
    }

    public List<SeatDTO> getAllSeatsByHallNo(int hallNo) {
        return dao.selectAllSeatsByHallNo(hallNo);
    }

    public int reserve(ScreeningScheduleDTO selectedSchedule, List<SeatDTO> selectedSeats) {
        int rslt = 0;
        for(SeatDTO seat : selectedSeats) {
            rslt += dao.insertBooking(selectedSchedule, seat, frame.getLoginSession());
        }
        return rslt;
    }

    public List<BookingDTO> getUnpaidBookingList(ScreeningScheduleDTO selectedSchedule, List<SeatDTO> selectedSeats) {
        return dao.selectUnpaidBookings(selectedSchedule, selectedSeats, frame.getLoginSession());
    }

    public List<BookingDTO> getBookingByUserId(String userId) {
        return dao.getBookingByUserId(userId);
    }

    public boolean deleteBooking(int bookingNo) {
        return dao.deleteBooking(bookingNo);
    }

    public boolean updateBookingSchedule(int bookingNo, int newScheduleNo) {
        return dao.updateBookingSchedule(bookingNo, newScheduleNo);
    }

    public int getMovieNoByScheduleNo(int scheduleNo) {
        return dao.getMovieNoByScheduleNo(scheduleNo);
    }

    public MovieDTO getMovieByNo(int movieNo) {
        return dao.getMovieByNo(movieNo);
    }

    public boolean updatePaymentInfo(BookingDTO booking) {
        return dao.updatePaymentInfo(booking);
    }

    public boolean createNewBooking(ScreeningScheduleDTO newSchedule, List<SeatDTO> newSeats, BookingDTO oldBooking) {
        int result = reserve(newSchedule, newSeats);
        if(result == newSeats.size()) {
            return true;
        }
        return false;
    }

    public void deleteOldBooking(BookingDTO oldBooking) {
        deleteBooking(oldBooking.getBookingNo());
    }
    public void deleteTicket(BookingDTO booking) {
		dao.deleteTicket(booking);
	}
	public void issueTicket(BookingDTO booking) {
		dao.deleteTicket(booking);
		dao.insertTicket(booking);
	}
}

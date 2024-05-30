package service;

import java.util.List;

import dao.DAO;
import dto.MovieDTO;
import dto.UserDTO;
import frame.MovieReservationFrame;

public class Service {
	private DAO dao = DAO.getDAO();
	private MovieReservationFrame frame;
	
	private static Service service = new Service();
	private Service() {
	}
	public static Service getService() {return service;}
	
	public int login(String id, String pw, int isAdmin) { // -1이면 로그인 실패, 0이면 사용자 로그인, 1면 관리자 로그인
		if(frame == null) {
			frame = MovieReservationFrame.getMovieReservationFrame();
		}
		UserDTO userDTO = dao.selectUserByIdAndPw(id, pw, isAdmin); 
		if(userDTO == null) return 0;
		frame.setLoginSession(userDTO);
		return frame.getLoginSession().isAdmin();
	}
	public boolean register(String id, String password,String userName,String phoneNo,String email) { 
		if(isIdDuplicated(id)) return false;
		
//		아이디 등록
		return true;
		
		
	}
	public boolean isIdDuplicated(String id) {
//		아이디 중복 확인,
		return false;
	}
	public List<MovieDTO> getMovieList(String title, String director, String[] actorArray, String genre) {
		if(actorArray.length != 0)
			return dao.selectMoviesWithActorNames(title, director, actorArray, genre);
		return null;
	}
}



// login, 영화 목록 조회, 상세조회 ,가로 20 세로 15
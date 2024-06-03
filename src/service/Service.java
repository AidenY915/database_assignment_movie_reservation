package service;

import dao.DAO;
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
	public boolean register(String id, String password,String userName,String phoneNo,String email,int isAdmin) { 
		if(isIdDuplicated(id)) return false;
		
		UserDTO newUser = new UserDTO(id,userName,phoneNo,email,password,isAdmin);
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
}



// login, 영화 목록 조회, 상세조회 ,가로 20 세로 15
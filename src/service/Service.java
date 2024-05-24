package service;

import dao.DAO;

public class Service {
	private static Service service = new Service();
	private Service() {
	}
	public static Service getService() {return service;}
	
	private DAO dao = DAO.getDAO();
	
	public int login(String id, String password) { // 0이면 로그인 실패, 1이면 사용자 로그인, 2면 관리자 로그인
		//데이터베이스와 통신해서 SELECT is_admin FROM USER WHERE id = ? and password = ?
		System.out.println("아이디 : " + id + "\n비밀번호 : " + password);
		return 0;
	}
}



// login, 영화 목록 조회, 상세조회 ,가로 20 세로 15
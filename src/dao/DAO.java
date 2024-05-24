package dao;

import id_info.IdInfo;

public class DAO implements IdInfo{
	private DAO() {}
	public static DAO dao = new DAO();
	public static DAO getDAO() {return dao;}
	
	
	
	
}


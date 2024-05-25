package dto;

public class UserDTO {
	private String id;
	private String userName;
	private String phoneNo;
	private String email;
	private String password;
	private int isAdmin;

	public UserDTO() {
	}

	public UserDTO(String id, String userName, String phoneNo, String email, String pw, int isAdmin) {
		super();
		this.id = id;
		this.userName = userName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getphoneNo() {
		return phoneNo;
	}

	public void setphoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int isAdmin() {
		return isAdmin;
	}

	public void setAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
}

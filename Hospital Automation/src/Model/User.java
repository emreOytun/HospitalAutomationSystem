package Model;

import Helper.DBConnection;

public class User {
	private int id;
	private String identity_number, name, password, type;
	DBConnection conn = new DBConnection();
	
	public User() {}
	
	public User(int id, String identity_number, String name, String password, String type) {
		super();
		this.id = id;
		this.identity_number = identity_number;
		this.name = name;
		this.password = password;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentity_number() {
		return identity_number;
	}

	public void setIdentity_number(String identity_number) {
		this.identity_number = identity_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}

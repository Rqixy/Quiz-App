package model;

import java.io.Serializable;

public class LoginUserBean implements Serializable {
	private int id;
	
	public LoginUserBean() {}

	public LoginUserBean(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

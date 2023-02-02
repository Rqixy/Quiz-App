package bean;

import java.io.Serializable;

public class RegisterUserBean implements Serializable {
	private String name;
	private String pass;

	public RegisterUserBean(String name, String pass){
		this.name = name;
		this.pass = pass;
	}
	
	public String name() {
		return name;
	}
	
	public String pass() {
		return pass;
	}
}

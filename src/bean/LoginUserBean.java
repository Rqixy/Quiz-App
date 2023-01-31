package bean;

import java.io.Serializable;

public class LoginUserBean implements Serializable {
	private final int id;
	private final String name;

	public LoginUserBean(final int id, final String name){
		this.id = id;
		this.name = name;
	}

	public int id() {
		return id;
	}
	
	public String name() {
		return name;
	}
}

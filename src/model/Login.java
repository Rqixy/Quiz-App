package model;

import java.util.regex.Pattern;

import db.LoginDao;

public class Login {
	public int loginCheck(String name, String pass) throws Exception{
		int loggedInUser = 0;
		
		String reqularPattern = "^[a-zA-Z0-9$_]{6,24}$";
		Pattern p = Pattern.compile(reqularPattern);
		
		if(!(p.matcher(name).find())) {
			return loggedInUser;
		}
		
		if(!(p.matcher(pass).find())) {
			return loggedInUser;
		}
		
		LoginDao dao = new LoginDao();
		
		loggedInUser = dao.selectUser(name, pass);
		
		return loggedInUser;
	}
}

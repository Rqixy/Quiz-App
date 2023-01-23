package model;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import db.LoginDao;

public class Login {
	public int loginCheck(String name, String pass) throws Exception{
		int loggedInUser = 0;
		
		String reqularPattern = "^[a-zA-Z0-9$_]{1,24}$";
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
	
	/**
	 * ユーザーがログイン済みかどうかのチェック
	 * @param request
	 * @return loggedInUser
	 */
	public static boolean loggedInUser(HttpServletRequest request) {
		boolean loggedInUser = false;
		if (request.getSession().getAttribute("user") != null) {
			loggedInUser = true;
		}
		
		return loggedInUser;
	}
}

package model;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import bean.LoginUserBean;
import db.LoginDao;

public class Login {
	private Login() {}
	
	public static LoginUserBean check(final String name, final String pass) throws Exception{
		LoginUserBean loginUser = null;
		
		String reqularPattern = "^[a-zA-Z0-9$_]{1,24}$";
		Pattern p = Pattern.compile(reqularPattern);
		
		if(!(p.matcher(name).find())) {
			return loginUser;
		}
		if(!(p.matcher(pass).find())) {
			return loginUser;
		}
		
		loginUser = LoginDao.selectUser(name, pass);
		
		return loginUser;
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

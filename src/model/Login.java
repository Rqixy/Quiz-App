package model;

import javax.servlet.http.HttpServletRequest;

import bean.LoginUserBean;
import db.LoginDao;

public class Login {
	private Login() {}
	
	public static LoginUserBean check(final String name, final String pass) throws Exception{
		LoginUserBean loginUser = LoginDao.selectUser(name, pass);
		
		return loginUser;
	}
	
	/**
	 * ユーザーがログイン済みかどうかのチェック
	 * @param request
	 * @return loggedInUser
	 */
	public static boolean loggedInUser(HttpServletRequest request) {
		boolean loggedInUser = false;
		if (request.getSession().getAttribute("loginUser") != null) {
			loggedInUser = true;
		}
		
		return loggedInUser;
	}
}

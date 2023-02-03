package libs.model;

import javax.servlet.http.HttpServletRequest;

public class Login {
	private Login() {}
	
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

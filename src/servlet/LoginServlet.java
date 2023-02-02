package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginUserBean;
import csrf.Csrf;
import db.LoginDao;
import exception.NoMatchJspFileException;
import transition.Redirect;
import transition.ScreenTransition;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		Csrf.make(request);
			ScreenTransition.forward(request, response, "login.jsp");
		} catch (NoMatchJspFileException e) {
			System.out.println("NoMatchJspFileException : " + e.getMessage());
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードを指定（あとで置き換えてもいい）
		request.setCharacterEncoding("UTF-8");
		if(!Csrf.check(request)) {
			Redirect.login(request, response);
			return;
		}
		
		//セッションスコープの準備
		HttpSession session = request.getSession();

		// ボタンで要件を確認
		String button = request.getParameter("submit");

		if(button.equals("login")) { // ログイン
			// フォームに来た内容を貰う
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
			
			if (name.isEmpty() || pass.isEmpty()) {
				session.setAttribute("name", name);
				session.setAttribute("errorMessage", "ユーザー名とパスワードを入力してください");
				Redirect.login(request, response);
				return;
			}
			
			LoginUserBean loginUser = null;
			// ログイン処理
			try {
				loginUser = LoginDao.selectUser(name, pass);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// ログイン可否で分岐
			if(loginUser == null) { // 失敗時
				session.setAttribute("name", name);
				session.setAttribute("errorMessage", "ユーザー名かパスワードが間違っています");
				Redirect.login(request, response);
				return;
			}
			
			// ログイン成功したら、セッションを再生成
			session.invalidate();
			session = request.getSession(true);
			
			// ユーザーidをスコープに設定(ホームでユーザー判別するのに使う)
			session.setAttribute("loginUser", loginUser);
			// ホーム画面に移動
			Redirect.home(request, response);
		}
		
		if(button.equals("logout")) { // ログアウト
			// ユーザー情報を破棄
			session.invalidate();
			Csrf.make(request);
			// トップに帰す
			Redirect.login(request, response);
		}
	}
}

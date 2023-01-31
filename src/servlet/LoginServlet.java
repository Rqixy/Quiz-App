package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginUserBean;
import csrf.Csrf;
import model.Login;
import transition.Redirect;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Csrf.make(request);
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードを指定（あとで置き換えてもいい）
		request.setCharacterEncoding("UTF-8");
		if(!Csrf.check(request)) {
			Redirect.login(request, response);
			return;
		}

		// ボタンで要件を確認
		String button = request.getParameter("submit");
		
		//セッションスコープの準備
		HttpSession session = request.getSession();

		if(button.equals("login")) { // ログイン
			LoginUserBean loginUser = null;
			
			// フォームに来た内容を貰う
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
			// ログイン処理
			try {
				loginUser = Login.check(name, pass);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// ログイン可否で分岐
			if(loginUser == null) { // 失敗時
				// トップに帰す
				Redirect.login(request, response);
				return;
			}
			
			// ユーザーidをスコープに設定(ホームでユーザー判別するのに使う)
			session.setAttribute("loginUser", loginUser);
			// ホーム画面に移動
			Redirect.home(request, response);
		}else if(button.equals("logout")) { // ログアウト
			// ユーザー情報を破棄
			session.invalidate();
			
			Csrf.make(request);
			// トップに帰す
			Redirect.login(request, response);
		}
	}
}

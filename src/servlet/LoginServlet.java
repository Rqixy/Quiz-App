package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;
import model.LoginUserBean;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードを指定（あとで置き換えてもいい）
		request.setCharacterEncoding("UTF-8");

		// ボタンで要件を確認
		String button = request.getParameter("submit");
		
		//セッションスコープの準備
		LoginUserBean User = new LoginUserBean();
		HttpSession session = request.getSession();

		if(button == "login") { // ログイン
			// フォームに来た内容を貰う
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
					
			// ログイン処理
			Login login = new Login();
			int loggedInUser = 0;
			
			try {
				loggedInUser = login.loginCheck(name, pass);
				User.setId(loggedInUser);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			// ログイン可否で分岐
			if(loggedInUser != 0) { // 成功時
				// ユーザーidをスコープに設定(ホームでユーザー判別するのに使う)
				session.setAttribute("id", User);
				
				// ホーム画面に移動
				RequestDispatcher dispatcher = request.getRequestDispatcher(":ホーム画面");
				dispatcher.forward(request, response);
			}else { // 失敗時
				// トップに帰す
				RequestDispatcher dispatcher = request.getRequestDispatcher(":ログイン画面");
				dispatcher.forward(request, response);
			}		
		}else if(button == "logout") { // ログアウト
			// ユーザー情報を破棄
			session.invalidate();
			
			// トップに帰す
			RequestDispatcher dispatcher = request.getRequestDispatcher(":ログイン画面");
			dispatcher.forward(request, response);
		}
	}

}

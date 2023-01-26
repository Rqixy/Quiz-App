package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import csrf.Csrf;
import model.Login;
import model.LoginUserBean;
import transition.Redirect;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Csrf.make(request);
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
		LoginUserBean User = new LoginUserBean();
		HttpSession session = request.getSession();

		if(button.equals("login")) { // ログイン
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
				session.setAttribute("user", User);
				
				// ホーム画面に移動
				response.sendRedirect(request.getContextPath() + "/home");
			}else { // 失敗時
				// トップに帰す
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
				dispatcher.forward(request, response);
			}		
		}else if(button.equals("logout")) { // ログアウト
			// ユーザー情報を破棄
			session.invalidate();
			
			Csrf.make(request);
			// トップに帰す
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
			dispatcher.forward(request, response);
		}
	}

}

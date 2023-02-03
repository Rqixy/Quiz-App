package servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginUserBean;
import db.LoginDao;
import libs.csrf.Csrf;
import libs.exception.NoMatchJspFileException;
import libs.transition.Redirect;
import libs.transition.ScreenTransition;

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
		try {
			// 文字コードを指定
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
				session.setAttribute("name", name);
				
				// 入力がされていなければエラー
				if (name.isEmpty() || pass.isEmpty()) {
					session.setAttribute("errorMessage", "ユーザー名とパスワードを入力してください");
					Redirect.login(request, response);
					return;
				}
				
				// ログイン処理
				LoginUserBean loginUser = null;
				MessageDigest md = MessageDigest.getInstance("SHA3-256");
				byte[] bytePassword = md.digest(pass.getBytes());
				String encodedPass = String.format("%040x", new BigInteger(1, bytePassword));
				
				loginUser = LoginDao.selectUser(name, encodedPass);
				
				// ログイン可否で分岐
				if(loginUser == null) { // 失敗時
					session.setAttribute("errorMessage", "ユーザー名かパスワードが間違っています");
					Redirect.login(request, response);
					return;
				}
				
				// ログイン成功したら、セッションを再生成
				session.invalidate();
				session = request.getSession(true);
				
				// ユーザー情報をスコープに設定(ホームでユーザー判別するのに使う)
				session.setAttribute("loginUser", loginUser);
				// ホーム画面に移動
				Redirect.home(request, response);
				return;
			}
			
			// ゲストアカウントでログインする処理
			if(button.equals("guest")) {
				LoginUserBean guestUser = new LoginUserBean(1, "ゲスト");
				// ログイン成功したら、セッションを再生成
				session.invalidate();
				session = request.getSession(true);
				
				// ユーザーidをスコープに設定(ホームでユーザー判別するのに使う)
				session.setAttribute("loginUser", guestUser);
				// ホーム画面に移動
				Redirect.home(request, response);
				return;
			}
			
			if(button.equals("logout")) { // ログアウト
				// ユーザー情報を破棄
				session.invalidate();
				Csrf.make(request);
				// トップに帰す
				Redirect.login(request, response);
				return;
			}
			
			// 何かしらあって入力が間違っていたらリダイレクト
			Redirect.login(request, response);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}

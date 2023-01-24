package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;
import model.LoginUserBean;
import model.Quiz;
import model.Result;
import transition.ScreenTransition;

/**
 * 結果時にクリア状況の更新と結果画面表示の準備処理
 */
@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ログインしてなかったら、ログイン画面へリダイレクト
			if (!Login.loggedInUser(request)) {
				ScreenTransition.redirectLogin(request, response);
				return;
			}
			// ログイン済ならホーム画面へリダイレクト
			ScreenTransition.redirectHome(request, response);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			LoginUserBean loginUserBean = (LoginUserBean)session.getAttribute("user");
			Quiz quiz = (Quiz)session.getAttribute("quiz");
			
			Result.clear(loginUserBean, quiz);
			
	        // セッションに目標番号のユニセフサイトのURLを保存
	        session.setAttribute("goalUrl", Result.unicefUrl());

			// 結果画面へ表示
	        ScreenTransition.forward(request, response, "result.jsp");
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
}

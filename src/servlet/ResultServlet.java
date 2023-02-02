package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginUserBean;
import csrf.Csrf;
import exception.NoMatchJspFileException;
import model.Quiz;
import model.Result;
import transition.Redirect;
import transition.ScreenTransition;

/**
 * 結果時にクリア状況の更新と結果画面表示の準備処理
 */
@WebServlet("/result")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ホーム画面へリダイレクト
			Redirect.home(request, response);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			if(!Csrf.check(request)) {
				Redirect.home(request, response);
				return;
			}
			
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			LoginUserBean loginUserBean = (LoginUserBean)session.getAttribute("loginUser");
			Quiz quiz = (Quiz)session.getAttribute("quiz");
			
			boolean isClear = false;
			
			// クリアしていたら、クリア状況を更新するため、現在のセッションを削除する
			if (Result.clear(loginUserBean, quiz)) {
				isClear = true;
				session.removeAttribute("goalList");
			}
			
			session.setAttribute("isClear", isClear);
	        // セッションに目標番号のユニセフサイトのURLを保存
	        session.setAttribute("goalUrl", Result.unicefUrl());

			// 結果画面へ表示
	        ScreenTransition.forward(request, response, "result.jsp");
		} catch (NoMatchJspFileException e) {
			System.out.println("NoMatchJspFileException : " + e.getMessage());
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
}

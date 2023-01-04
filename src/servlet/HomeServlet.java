package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ClearStatusQuery;

/**
 * ホーム画面を表示する際にログインしたユーザーのクリア状況を確認し準備する処理
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			// ログインしてなかったら、ログイン画面へリダイレクト
			if (session.getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/LoginServlet");
				return;
			}
			
			int userId = (int)session.getAttribute("userId");
			// クリア状況DBの処理するクラスの初期化
			ClearStatusQuery clearStatusQuery = new ClearStatusQuery();

			// ユーザーIDからクリア状況のテーブルを参照し、参照したクリア状況を配列に格納
			HashMap<Integer, Integer> clearStatus = clearStatusQuery.select(userId);

			// クリア状況の内容をセッションに保存
			session.setAttribute("clearStatus", clearStatus);

			// Top画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/home.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}

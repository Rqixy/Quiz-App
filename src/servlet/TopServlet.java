package servlet;

import java.io.IOException;
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

@WebServlet("/TopServlet")
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			// クリア状況DBの処理するクラスの初期化
			ClearStatusQuery clearStatusQuery = new ClearStatusQuery();
			
			// XXX 仮のユーザーID(ログイン処理実装後削除)
			int userId = clearStatusQuery.getUserId();
			session.setAttribute("userId", userId);

			// ユーザーIDからクリア状況のテーブルを参照し、参照したクリア状況を配列に格納
			HashMap<Integer, Integer> clearStatus = clearStatusQuery.selectByUserId(userId);

			// クリア状況の内容をセッションに保存
			session.setAttribute("clearStatus", clearStatus);

			// Top画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/top.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.getMessage();
		}
	}
}

package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ClearStatusQuery;
import model.Login;
import model.LoginUserBean;
import model.ScreenTransition;

/**
 * ホーム画面を表示する際にログインしたユーザーのクリア状況を確認し準備する処理
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			
			// ログインしてなかったら、ログイン画面へリダイレクト
			if (!Login.loggedInUser(request)) {
				ScreenTransition.redirectLogin(request, response);
				return;
			}
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			LoginUserBean loginUserBean = (LoginUserBean)session.getAttribute("user");
			// クリア状況DBの処理するクラスの初期化
			ClearStatusQuery clearStatusQuery = new ClearStatusQuery();
			// 送られてきたユーザーIDのクリア状況のデータが作成されているか確認し、クリア状況のデータが作成されていなかったら、新規に作成
			if (!clearStatusQuery.exist(loginUserBean.getId())) {
				clearStatusQuery.insert(loginUserBean.getId());
			}

			// ユーザーIDからクリア状況のテーブルを参照し、参照したクリア状況を配列に格納
			HashMap<Integer, Integer> clearStatus = clearStatusQuery.select(loginUserBean.getId());

			// クリア状況の内容をセッションに保存
			session.setAttribute("clearStatus", clearStatus);

			// Top画面へ表示
			ScreenTransition.forward(request, response, "home.jsp");
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}

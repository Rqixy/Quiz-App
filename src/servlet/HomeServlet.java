package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GoalBean;
import model.Home;
import model.Login;
import model.LoginUserBean;
import transition.ScreenTransition;

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

			ArrayList<GoalBean> goalList = Home.goalList(loginUserBean);
			// クリア状況の内容をセッションに保存
			session.setAttribute("goalList", goalList);

			// Top画面へ表示
			ScreenTransition.forward(request, response, "home.jsp");
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginUserBean;
import libs.csrf.Csrf;
import libs.exception.NoMatchJspFileException;
import libs.model.Home;
import libs.model.Login;
import libs.transition.Redirect;
import libs.transition.ScreenTransition;

/**
 * ホーム画面を表示する際にログインしたユーザーのクリア状況を確認し準備する処理
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			
			// ログインしてなかったら、ログイン画面へリダイレクト
			if (!Login.loggedInUser(request)) {
				Redirect.login(request, response);
				return;
			}
			
			// セッションスコープの準備
			HttpSession session = request.getSession(false);
			if (session == null) {
				Redirect.login(request, response);
				return;
			}
			
			Csrf.make(session);
			
			// クリア状況の内容のセッションが存在していたら、何もせずhome.jspに移動
			if(session.getAttribute("goalList") != null) {
				ScreenTransition.forward(request, response, "home.jsp");
				return;
			}
			
			// クリア状況の内容をセッションに保存
			LoginUserBean loginUserBean = (LoginUserBean)session.getAttribute("loginUser");
			session.setAttribute("goalList", Home.goalList(loginUserBean));

			// Top画面へ表示
			ScreenTransition.forward(request, response, "home.jsp");
		} catch (NoMatchJspFileException e) {
			System.out.println("NoMatchJspFileException : " + e.getMessage());
			e.printStackTrace();
			Redirect.login(request, response);
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
			Redirect.login(request, response);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
			Redirect.login(request, response);
		}
	}
}

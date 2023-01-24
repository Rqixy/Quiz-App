package transition;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 画面遷移やリダイレクト処理をまとめたクラス
 */
public class ScreenTransition {
	/**
	 * jspファイルへフォワードする処理
	 * @param request
	 * @param response
	 * @param jsp
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void forward(final HttpServletRequest request, final HttpServletResponse response, final String jsp) throws IOException, ServletException {
		try {
			if (!jsp.endsWith(".jsp")) {
				throw new IOException("jspファイルを指定してください");
			}
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/" + jsp);
			rd.forward(request, response);
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
	
	/**
	 * ログインへリダイレクトする処理
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void redirectLogin(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		try {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
	
	/**
	 * ホームへリダイレクトする処理
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void redirectHome(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		try {
			response.sendRedirect(request.getContextPath() + "/HomeServlet");
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}

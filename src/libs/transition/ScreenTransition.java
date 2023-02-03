package libs.transition;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import libs.exception.NoMatchJspFileException;

/**
 * 画面遷移処理
 */
public class ScreenTransition {
	private ScreenTransition() {}
	
	/**
	 * jspファイルへフォワードする処理
	 * @param request
	 * @param response
	 * @param jsp
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void forward(final HttpServletRequest request, final HttpServletResponse response, final String jsp) throws IOException, ServletException, NoMatchJspFileException {
		if (!jsp.endsWith(".jsp")) {
			throw new NoMatchJspFileException("jspファイルを指定してください");
		}
		
		ServletContext s = request.getServletContext();
		RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/" + jsp);
		rd.forward(request, response);
	}
}

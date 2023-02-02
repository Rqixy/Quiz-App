package transition;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Redirect {
	private Redirect() {}
	
	/**
	 * ログインへリダイレクトする処理
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void login(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		try {
			response.sendRedirect(request.getContextPath() + "/login");
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
	public static void register(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		try {
			response.sendRedirect(request.getContextPath() + "/register");
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
	public static void home(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		try {
			response.sendRedirect(request.getContextPath() + "/home");
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}

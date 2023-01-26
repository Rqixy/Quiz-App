package csrf;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

public class Csrf {
	private static final int BYTE = 24;
	
	private Csrf() {}
	
	/**
	 * Csrfトークン発行処理
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void make(final HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// トークン発行
		final String token = getToken();
		
		// セッションに保存
		HttpSession session = request.getSession();
		session.setAttribute("csrfToken", token);
	}
	
	/**
	 * CSRFチェック
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static boolean check(final HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// POSTで送られたトークン
		final String postCsrfToken = request.getParameter("csrf_token");
		
		// 空ならfalse
		if (postCsrfToken == null) {
			return false;
		}
		
		// セッションのCSRFトークン
		HttpSession session = request.getSession();
		final String csrfToken = (String)session.getAttribute("csrfToken");
		
		// トークンの値が変わっていたらfalse
		if(!postCsrfToken.equals(csrfToken)) {
			return false;
		}
		
		// トークンのセッション削除
		session.removeAttribute("csrfToken");
		return true;
	}
	
	/**
	 * ランダムな文字列を発行する処理
	 * @return Base64.encodeBase64URLSafeString(buf);
	 */
	private static String getToken() {
		SecureRandom secureRandom;
		byte[] buf = new byte[BYTE];
		
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			byte[] seed = secureRandom.generateSeed(BYTE);
			secureRandom.setSeed(seed);
			
			secureRandom.nextBytes(buf);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException : " + e.getMessage());
		}
		
		return Base64.encodeBase64URLSafeString(buf);
	}
}

package servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.RegistUserBean;
import csrf.Csrf;
import db.RegisterDao;
import exception.NoMatchJspFileException;
import transition.Redirect;
import transition.ScreenTransition;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
    		Csrf.make(request);
			ScreenTransition.forward(request, response, "register.jsp");
		} catch (NoMatchJspFileException e) {
			System.out.println("NoMatchJspFileException : " + e.getMessage());
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//文字コードを設定する。
			request.setCharacterEncoding("UTF-8");
			if(!Csrf.check(request)) {
				Redirect.login(request, response);
				return;
			}
			HttpSession session = request.getSession();
			
			//ボタン判別
			String button = request.getParameter("submit");
			
			if(button.equals("confirm")) {
				//入力された情報を貰う
				String name = request.getParameter("name");
				String pass = request.getParameter("pass");
				String pass2 = request.getParameter("pass2");
				
				if(name.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
					session.setAttribute("name", name);
					session.setAttribute("errorMessage", "ユーザー名とパスワードを入力してください");
					Redirect.register(request, response);
					return;
				}
				
				if(!pass.equals(pass2)) {
					session.setAttribute("name", name);
					session.setAttribute("errorMessage", "パスワードが正しくありません");
					Redirect.register(request, response);
					return;
				}
				
				ArrayList<String> registeredUserList = RegisterDao.selectAll();
				if(registeredUserList.contains(name)) {
					session.setAttribute("name", name);
					session.setAttribute("errorMessage", "そのユーザー名はすでに存在しています");
					Redirect.register(request, response);
					return;
				}
				
				MessageDigest md = MessageDigest.getInstance("SHA3-256");
				byte[] bytePassword = md.digest(pass.getBytes());
				String encodedPass = String.format("%040x", new BigInteger(1, bytePassword));
				
				//登録確認画面に情報を渡す
				RegistUserBean registerUser = new RegistUserBean(name, encodedPass);
				session.setAttribute("registUser", registerUser);
				
				Csrf.make(request);
				ScreenTransition.forward(request, response, "register_confirm.jsp");
				return;
			}
			
			if(button.equals("regist")) {
				RegistUserBean registerUser = (RegistUserBean)session.getAttribute("registUser");
				boolean flag = RegisterDao.userAdd(registerUser);
				
				if(!flag) {
					Redirect.register(request, response);	
					return;
				}
				
				ScreenTransition.forward(request, response, "register_fin.jsp");
			}
		} catch(Exception e) {
			e.getStackTrace();
		}
	}

}

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.RegisterUserBean;
import csrf.Csrf;
import db.SignUpDao;
import transition.Redirect;
import transition.ScreenTransition;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Csrf.make(request);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/register.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//文字コードを設定する。
			request.setCharacterEncoding("UTF-8");
			if(!Csrf.check(request)) {
				Redirect.login(request, response);
				return;
			}
			
			//ボタン判別
			String button = request.getParameter("submit");
			
			//入力された情報を貰う
			String name = request.getParameter("name");
			String pass = request.getParameter("pass");
			
			if(button.equals("confirm")) {
				//登録確認画面に情報を渡す
				RegisterUserBean registerUser = new RegisterUserBean(name, pass);
				request.setAttribute("registerUser", registerUser);
				
				Csrf.make(request);
				ScreenTransition.forward(request, response, "register_confirm.jsp");
			}
			if(button.equals("regist")) {
				RegisterUserBean registerUser = (RegisterUserBean)request.getAttribute("registerUser");
				boolean flag = SignUpDao.userAdd(registerUser);
				
				if(flag) {
					ScreenTransition.forward(request, response, "register_fin.jsp");
				}else {
					ScreenTransition.forward(request, response, "register.jsp");
				}
			}
		} catch(Exception e) {
			
		}
	}

}

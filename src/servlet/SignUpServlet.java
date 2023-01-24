package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import csrf.Csrf;
import db.SignUpDao;
import model.User;
import transition.ScreenTransition;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Csrf.make(request);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/register.jsp");
		dispatcher.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字コードを設定する。
		request.setCharacterEncoding("UTF-8");
		if(!Csrf.check(request)) {
			ScreenTransition.redirectLogin(request, response);
			return;
		}
		
		//遷移準備
		RequestDispatcher dispatcher = null;
		
		//ボタン判別
		String button = request.getParameter("submit");
		
		//入力された情報を貰う
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		
		if(button.equals("confirm")) {
			//登録確認画面に情報を渡す
			User user = new User(name, pass);
			request.setAttribute("user", user);
			
			Csrf.make(request);
			dispatcher = request.getRequestDispatcher("/WEB-INF/view/register_confirm.jsp");
			dispatcher.forward(request, response);
		}
		if(button.equals("regist")) {
			//データベース処理を呼ぶ
			SignUpDao sd = new SignUpDao();
			try {
				boolean flag = sd.UserAdd(name, pass);
				
				if(flag == true) {
					dispatcher = request.getRequestDispatcher("/WEB-INF/view/register_fin.jsp");
				}else {
					dispatcher = request.getRequestDispatcher("/WEB-INF/view/register.jsp");
				}
			}catch(Exception e){
				//例外処理
			}finally {
				//お見送り
				dispatcher.forward(request, response);
			}
		}
	}

}

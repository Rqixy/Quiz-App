package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import csrf.Csrf;
import exception.NoMatchGoalNumberException;
import exception.NoMatchJspFileException;
import model.Quiz;
import transition.Redirect;
import transition.ScreenTransition;

/**
 * クイズを表示する準備処理
 */
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ホーム画面へリダイレクト
			Redirect.home(request, response);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			if(!Csrf.check(request)) {
				Redirect.home(request, response);
				return;
			}
			
			// セッションスコープの準備
			HttpSession session = request.getSession();
			// 目標番号を取得
			String requestGoalNumber = request.getParameter("goalNumber");
			
			if (requestGoalNumber != null) {
				// goalNumberのパラメータが存在していたら、送信された目標番号の問題を準備する
				Quiz quiz = Quiz.prepareQuiz(requestGoalNumber);
				// セッションへ使いまわす情報を保存
				session.setMaxInactiveInterval(600);					// セッションの保存期間の設定(10分)
				session.setAttribute("quiz", quiz);
			} else {
				// goalNumberのパラメータが存在していなかったら、２問目以降なので次の問題の準備する
				Quiz quiz = (Quiz)session.getAttribute("quiz");
				quiz.nextQuiz();
				session.setAttribute("quiz", quiz);
			}
			
			Csrf.make(request);
			// 問題画面へ表示
			ScreenTransition.forward(request, response, "quiz.jsp");
		} catch (NoMatchGoalNumberException e) {
			System.out.println("NoMatchGoalNumberException : " + e.getMessage());
		} catch (NoMatchJspFileException e) {
			System.out.println("NoMatchJspFileException : " + e.getMessage());
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}

package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.QuizInfoEntity;

/**
 * Servlet implementation class AnswerServlet
 */
@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// セッションスコープの準備
			HttpSession session = request.getSession();

			// 問題を取得
			ArrayList<QuizInfoEntity> quizList = (ArrayList<QuizInfoEntity>)session.getAttribute("quizlist");

			// 問題番号を取得
			int quizNum = (int)session.getAttribute("quiznum");
			// 問題リストから出題された問題番号の要素を削除する
			quizList.remove(quizNum);
			// セッションスコープへオブジェクト(quizList)を保存
			session.setAttribute("quizlist", quizList);

			// 問題の答えを取得
			String quizAnswer = (String)session.getAttribute("answer");
			// ユーザーが選択した答えを取得
			String selectedAnswer = request.getParameter("answer");
			// 正解数のセッションを取得
			int answerCount = (int)session.getAttribute("answercount");

			// 問題の答えと選択した答えが一致していたら、
			boolean checkAnswer;
			if(quizAnswer.equals(selectedAnswer)) {
				checkAnswer = true;

				// 正解数をカウントする
				answerCount++;
				session.setAttribute("answercount", answerCount);
			} else {
				checkAnswer = false;
			}

			// セッションにtrueを保存する
			session.setAttribute("correctanswer", checkAnswer);

			// 現在の問題数を取得する
			int currentCount = (int)session.getAttribute("currentcount");

			// 加算した問題数をセッションに保存する
			session.setAttribute("currentcount", currentCount);

			// 転送処理(フォワード)
			// 問題画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/answer.jsp");
			rd.forward(request, response);
		} catch(Exception e) {
			// 例外処理
			e.getStackTrace();
		}
	}
}

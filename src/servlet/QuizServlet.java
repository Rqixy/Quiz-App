package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.QuizQuery;
import model.AnswersBean;
import model.QuizInfoBean;

/**
 * クイズを表示する準備処理
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ログインしてなかったら、ログイン画面へリダイレクト
			if (request.getSession().getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/LoginServlet");
				return;
			}
			// ログイン済ならホーム画面へリダイレクト
			response.sendRedirect(request.getContextPath() + "/HomeServlet");
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			// 目標番号を取得
			String strGoalNumber = request.getParameter("goalNumber");
			
			if (strGoalNumber != null) {
				// goalNumberのパラメータが存在していたら、送信された目標番号の問題を準備する
				int goalNumber = Integer.parseInt(strGoalNumber);
				prepareQuiz(session, goalNumber);
			} else {
				// goalNumberのパラメータが存在していなかったら、２問目以降なので、その処理を行う
				prepareNextQuiz(session);
			}

			// 問題画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	// 送信された目標番号の問題を準備する処理
	private void prepareQuiz(HttpSession session, int goalNumber) {
		try {
			// DB内のクイズ情報と回答情報を操作するクラスの初期化
			QuizQuery quizQuery = new QuizQuery();
			
			// 取得した目標番号からクイズ情報と回答情報をオブジェクトに格納
			ArrayList<QuizInfoBean> quizList = quizQuery.getQuizList(goalNumber);
			ArrayList<AnswersBean> answerList = quizQuery.getAnswerList(goalNumber);
			
			// 問題をランダムに表示する番号
			Random randomNumber = new Random();
			int quizNumber = randomNumber.nextInt(quizList.size());
	
			// セッションへ使いまわす情報を保存
			session.setMaxInactiveInterval(600);					// セッションの保存期間の設定(10分)
			session.setAttribute("goalNumber", goalNumber);			// 目標番号
			session.setAttribute("quizList", quizList);				// クイズ情報
			session.setAttribute("answerList", answerList);			// 回答情報
			session.setAttribute("quizNumber", quizNumber);			// 問題番号
			session.setAttribute("currentQuizCount", 1);			// 問題出題回数
			session.setAttribute("maxQuizCount", quizList.size());	// 最大出題回数
			session.setAttribute("answerCount", 0);					// 正答数
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
	
	// ２問目以降の問題準備処理
	private void prepareNextQuiz(HttpSession session) {
		// セッションから問題リストと回答リストを取得
		ArrayList<QuizInfoBean> quizList = (ArrayList<QuizInfoBean>)session.getAttribute("quizList");
		ArrayList<AnswersBean> answerList = (ArrayList<AnswersBean>)session.getAttribute("answerList");
		
		// 問題番号を取得
		int quizNumber = (int)session.getAttribute("quizNumber");
		// 現在の問題出現回数を取得
		int currentQuizCount = (int)session.getAttribute("currentQuizCount");
		
		// 問題リストと回答リストから出題された問題番号の要素を削除
		quizList.remove(quizNumber);
		answerList.remove(quizNumber);
		
		// 次の出題する問題をランダムで決定
		Random random = new Random();
		quizNumber = random.nextInt(quizList.size());
		
		// 現在の問題番号を１プラス
		currentQuizCount++;
		
		// セッションに更新した情報を再保存
		session.setAttribute("quizNumber", quizNumber);				// 問題番号
		session.setAttribute("currentQuizCount", currentQuizCount);	// 問題出題回数
	}
}

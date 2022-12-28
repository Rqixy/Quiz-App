package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import db.Db;
import model.AnswersBean;
import model.QuizInfoBean;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		/* リストからクイズ問題を取得 */
		try {
			/* POSTで「goalNumber」を取得する */
			String goalNumber = request.getParameter("goalNumber");
			
			if (goalNumber != null) {
				/* goalNumberのパラメータが存在していたら、送信された目標番号の問題を準備する */
				prepareQuiz(request, goalNumber);
			} else {
				/* goalNumberのパラメータが存在していなかったら、２問目以降なので、その処理を行う*/
				prepareNextQuiz(request);
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
	
	/* 送信された目標番号の問題を準備する処理 */
	private void prepareQuiz(HttpServletRequest request, String goalNumber) {
		try {
			/* セッションスコープの準備 */
			HttpSession session = request.getSession();
			
			// DBの接続準備
			Db db = new Db();
			Connection con = db.DbConnection();
			String sql = "";
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			// クイズ問題一覧を取得するSQL文の発行
			sql = "SELECT quiz_id, quiz, answer, commentary FROM quiz_info WHERE goal_number = ?";
			ps = con.prepareStatement(sql);
			// 値をセット
			ps.setString(1, goalNumber);
			// 実行
			rs = ps.executeQuery();
			
			// クイズ情報をオブジェクトに格納する
			ArrayList<QuizInfoBean> quizList = new ArrayList<>();
			while(rs.next()) {
				QuizInfoBean quizInfoObj = new QuizInfoBean();
				// DBから取得したクイズ情報をQuizInfoBeanオブジェクトのクイズ情報へ代入
				quizInfoObj.setQuiz_id(rs.getInt("quiz_id"));
				quizInfoObj.setQuiz(rs.getString("quiz"));
				quizInfoObj.setAnswer(rs.getString("answer"));
				quizInfoObj.setCommentary(rs.getString("commentary"));
				// QuizInfoBeanオブジェクトをクイズリスト(quizList)の要素へ追加
				quizList.add(quizInfoObj);
			}
			
			// 回答一覧を取得するSQL文の発行
			sql = "SELECT a.answer_id, a.correct, a.incorrect_1, a.incorrect_2, a.incorrect_3 "
			    + "FROM answers AS a JOIN quiz_info AS q ON q.quiz_id = answer_id "
			    + "WHERE q.goal_number = ?";
			ps = con.prepareStatement(sql);
			// 値をセット
			ps.setString(1, goalNumber);
			// 実行
			rs = ps.executeQuery();
	
			// 回答情報をオブジェクトに格納する
			ArrayList<AnswersBean> answerList = new ArrayList<>();
			while(rs.next()) {
				AnswersBean answersObj = new AnswersBean();
				// DBから取得したクイズ情報をAnswersBeanオブジェクトのクイズ情報へ代入
				answersObj.setAnswers_id(rs.getInt("answer_id"));
				answersObj.setCorrect(rs.getString("correct"));
				answersObj.setIncorrect_1(rs.getString("incorrect_1"));
				answersObj.setIncorrect_2(rs.getString("incorrect_2"));
				answersObj.setIncorrect_3(rs.getString("incorrect_3"));
				// AnswersBeanオブジェクトを回答リスト(answerList)の要素へ追加
				answerList.add(answersObj);
			}
	
			/* DBへの接続開放(クローズ処理) */
			con.close();
			ps.close();
			rs.close();
			
			// 問題をランダムに表示する番号
			Random randomNumber = new Random();
			int quizNumber = randomNumber.nextInt(quizList.size());
	
			/* セッションスコープへ、クイズ情報と回答一覧を保存する */
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
	
	/* ２問目以降の問題準備処理 */
	private void prepareNextQuiz(HttpServletRequest request) {
		/* セッションスコープの準備 */
		HttpSession session = request.getSession();
		
		/* 問題リストと回答リストを取得 */
		ArrayList<QuizInfoBean> quizList = (ArrayList<QuizInfoBean>)session.getAttribute("quizList");
		ArrayList<AnswersBean> answerList = (ArrayList<AnswersBean>)session.getAttribute("answerList");
		
		/* 問題番号を取得 */
		int quizNumber = (int)session.getAttribute("quizNumber");
		/* 現在の問題出現回数を取得 */
		int currentQuizCount = (int)session.getAttribute("currentQuizCount");
		
		/* 問題リストと回答リストから出題された問題番号の要素を削除する */
		quizList.remove(quizNumber);
		answerList.remove(quizNumber);
		
		// 次の問題をランダムで決定する
		Random random = new Random();
		quizNumber = random.nextInt(quizList.size());
		
		// 現在の問題番号を１プラスする
		currentQuizCount++;
		
		/* セッションに更新した情報を再保存する */
		session.setAttribute("quizNumber", quizNumber);				// 問題番号
		session.setAttribute("currentQuizCount", currentQuizCount);	// 問題出題回数
	}
}

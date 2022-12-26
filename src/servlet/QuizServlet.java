package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		/* セッションスコープの準備 */
		HttpSession session = request.getSession();

		/* リストからクイズ問題を取得 */
		try {
			/* POSTで「targetNumber」を取得する */
			String goalNumber = request.getParameter("goalNumber");

			/* targetNumberのパラメータが存在していなかったら、２問目以降なので、その処理を行う*/
			if (goalNumber == null) {
				/* 問題リストと回答リストを取得 */
				ArrayList<QuizInfoBean> quizList = (ArrayList<QuizInfoBean>)session.getAttribute("quizList");
				ArrayList<AnswersBean> answerList = (ArrayList<AnswersBean>)session.getAttribute("answerList");
				
				/* 問題番号を取得 */
				int quizNumber = (int)session.getAttribute("quizNumber");
				
				/* 問題リストと回答リストから出題された問題番号の要素を削除する */
				quizList.remove(quizNumber);
				answerList.remove(quizNumber);
				
				// 問題をランダムに表示する番号
				Random randomNumber = new Random();
				quizNumber = randomNumber.nextInt(quizList.size());
				// 次の問題番号をセッションに保存
				session.setAttribute("quizNumber", quizNumber);
				// セッションスコープへオブジェクト(quizList)を保存
				session.setAttribute("quizList", quizList);
				
				// 現在の問題出現回数を取得
				int currentQuizCount = (int)session.getAttribute("currentQuizCount");
				// 問題出現回数を１プラスしてセッションに再保存する
				currentQuizCount++;
				session.setAttribute("currentQuizCount", currentQuizCount);

				// 転送処理(フォワード)
				// 問題画面へ表示
				ServletContext s = request.getServletContext();
				RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
				rd.forward(request, response);
				return;
			}

			/* targetNumberのパラメータが存在していたら、送信された目標番号の問題を準備する */
			// DBに接続
			Db db = new Db();
			Connection con = db.DbConnection();

			/* DBから問題一覧と回答一覧を取得する */
			/* SQL文の発行 */
			// クイズ情報
			String sqlQ = "SELECT quiz_id, quiz, answer, commentary FROM quiz_info WHERE goal_number = ?";
			// 回答一覧
			String sqlA = "SELECT a.answer_id, a.correct, a.incorrect_1, a.incorrect_2, a.incorrect_3 "
					    + "FROM answers AS a JOIN quiz_info AS q ON q.quiz_id = answer_id "
					    + "WHERE q.goal_number = ?";

			/* プリペアードステートメント作成 */
			PreparedStatement psQ = con.prepareStatement(sqlQ);
			PreparedStatement psA = con.prepareStatement(sqlA);

			/* 作成したステートメントに目標番号をセットする */
			psQ.setString(1, goalNumber);
			psA.setString(1, goalNumber);

			/* SQLを実行する */
			ResultSet rsQ = psQ.executeQuery();
			ResultSet rsA = psA.executeQuery();

			// コレクションクラス(ArrayList)を初期化し、変数quizListへ代入
			ArrayList<QuizInfoBean> quizList = new ArrayList<>();
			// コレクションクラス(ArrayList)を初期化し、変数answerListへ代入
			ArrayList<AnswersBean> answerList = new ArrayList<>();

			/* DBからクイズ情報をリストに追加する操作 */
			while(rsQ.next()) {
				// QuizInfoEntityをインスタンス化
				QuizInfoBean quizInfoObj = new QuizInfoBean();

				// DBから取得したクイズ情報をQuizInfoEntityオブジェクトのクイズ情報へ代入
				quizInfoObj.setQuiz_id(rsQ.getInt("quiz_id"));
				quizInfoObj.setQuiz(rsQ.getString("quiz"));
				quizInfoObj.setAnswer(rsQ.getString("answer"));
				quizInfoObj.setCommentary(rsQ.getString("commentary"));

				// QuizInfoEntityオブジェクトをクイズリスト(quizList)の要素へ追加
				quizList.add(quizInfoObj);
			}

			/* DBから回答情報をリストに追加する操作 */
			while(rsA.next()) {
				// AnswersEntityをインスタンス化
				AnswersBean answersObj = new AnswersBean();

				answersObj.setAnswers_id(rsA.getInt("answer_id"));
				answersObj.setCorrect(rsA.getString("correct"));
				answersObj.setIncorrect_1(rsA.getString("incorrect_1"));
				answersObj.setIncorrect_2(rsA.getString("incorrect_2"));
				answersObj.setIncorrect_3(rsA.getString("incorrect_3"));

				// AnswersEntityオブジェクトをクイズリスト(answerList)の要素へ追加
				answerList.add(answersObj);
			}

			/* DBへの接続開放(クローズ処理) */
			con.close();
			psQ.close();
			psA.close();
			rsQ.close();
			rsA.close();
			
			// 問題をランダムに表示する番号
			Random randomNumber = new Random();
			int quizNumber = randomNumber.nextInt(quizList.size());

			/* セッションスコープへ、クイズ情報と回答一覧を保存する */
			// セッションの保存期間の設定(10分)
			session.setMaxInactiveInterval(600);
			// 目標番号を保存する
			session.setAttribute("goalNumber", goalNumber);
			// クイズ情報と回答情報を保存する
			session.setAttribute("quizList", quizList);
			session.setAttribute("answerList", answerList);
			// 現在の出題した問題数が何問目かどうかを保存する(初期値：1)
			session.setAttribute("currentQuizCount", 1);
			// 最大何問出題するか保存する
			session.setAttribute("maxQuizCount", quizList.size());
			// 正答数を保存する(初期値：0)
			session.setAttribute("answerCount", 0);
			session.setAttribute("quizNumber", quizNumber);

			// 転送処理(フォワード)
			// 問題画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
			rd.forward(request, response);
		} catch(Exception e) {
			// 例外処理
			e.getStackTrace();
		}
	}
}

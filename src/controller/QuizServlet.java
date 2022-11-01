package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AnswersEntity;
import model.QuizInfoEntity;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public QuizServlet() {
        super();
    }

    // 最初(1問目)のQuizServletが呼ばれた時の処理
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// DBからクイズ問題を取得
		try {
			// JDBCドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");

			// DB接続準備
			String db = "jdbc:mysql://localhost:3306/tech_c_itpj?useSSL=false";
			String user = "root";
			String pass = "SYF02549";

			// DBへの接続
			Connection con = DriverManager.getConnection(db, user, pass);

			// ステートメントの作成
			Statement stmtQuizInfo = con.createStatement();
			Statement stmtAnswers = con.createStatement();

			// SQL文の実行(参照系)
			ResultSet resQuizInfo = stmtQuizInfo.executeQuery("select * from quiz_info");
			ResultSet resAnswers = stmtAnswers.executeQuery("select * from answers");

			// コレクションクラス(ArrayList)を初期化し、変数quizListへ代入
			ArrayList<QuizInfoEntity> quizList = new ArrayList<>();
			// コレクションクラス(ArrayList)を初期化し、変数answersへ代入
			ArrayList<AnswersEntity> answers = new ArrayList<>();

			// DBからクイズ情報をリストに追加する操作
			while(resQuizInfo.next()) {
				// QuizInfoEntityをインスタンス化
				QuizInfoEntity quizInfoObj = new QuizInfoEntity();
				// DBから取得したquiz_idをQuizInfoEntityオブジェクトのquiz_idへ代入
				quizInfoObj.setQuiz_id(resQuizInfo.getInt("quiz_id"));
				// DBから取得したquizをQuizInfoEntityオブジェクトのquizへ代入
				quizInfoObj.setQuiz(resQuizInfo.getString("quiz"));
				// DBから取得したanswerをQuizInfoEntityオブジェクトのanswerへ代入
				quizInfoObj.setAnswer(resQuizInfo.getString("answer"));
				// DBから取得したcommentaryをQuizInfoEntityオブジェクトのcommentaryへ代入
				quizInfoObj.setCommentary(resQuizInfo.getString("commentary"));

				// QuizInfoEntityオブジェクトをクイズリスト(quizList)の要素へ追加
				quizList.add(quizInfoObj);
			}

			// DBから回答情報をリストに追加する操作
			while(resAnswers.next()) {
				// AnswersEntityをインスタンス化
				AnswersEntity answersObj = new AnswersEntity();
				// DBから取得したanswer_idをAnswersEntityオブジェクトのanswer_idへ代入
				answersObj.setAnswers_id(resAnswers.getInt("answer_id"));
				// DBから取得したcorrectをAnswersEntityオブジェクトのcorrectへ代入
				answersObj.setCorrect(resAnswers.getString("correct"));
				// DBから取得したincorrect_1をAnswersEntityオブジェクトのincorrect_1へ代入
				answersObj.setIncorrect_1(resAnswers.getString("incorrect_1"));
				// DBから取得したincorrect_2をAnswersEntityオブジェクトのincorrect_2へ代入
				answersObj.setIncorrect_2(resAnswers.getString("incorrect_2"));
				// DBから取得したincorrect_3をAnswersEntityオブジェクトのincorrect_3へ代入
				answersObj.setIncorrect_3(resAnswers.getString("incorrect_3"));

				// AnswersEntityオブジェクトをクイズリスト(answers)の要素へ追加
				answers.add(answersObj);
			}

			// DBへの接続開放(クローズ処理)
			con.close();
			stmtQuizInfo.close();
			stmtAnswers.close();
			resQuizInfo.close();
			resAnswers.close();

			// セッションスコープへquizListを保存
			// セッションスコープの準備
			HttpSession session = request.getSession();
			// セッションスコープを有効期間(300秒)の設定
			session.setMaxInactiveInterval(300);
			// セッションスコープへオブジェクト(quizList)を保存
			session.setAttribute("quizlist", quizList);
			// セッションスコープへオブジェクト(answers)を保存
			session.setAttribute("answers", answers);
			// 問題出現回数
			session.setAttribute("currentcount", 1);
			// 問題出現上限(0以上)
			session.setAttribute("maxcount", 10);
			// 正解数
			session.setAttribute("answercount", 0);

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

	// 2問目以降にQuizServletが呼ばれた時の処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//  リストからクイズ問題を取得
		try {
			// セッションスコープへquizListを保存
			// セッションスコープの準備
			HttpSession session = request.getSession();
			// 問題出現回数を取得
			int currentCount = (int)session.getAttribute("currentcount");

			// 問題出現回数を１プラスして
			// セッションに保存する
			currentCount++;
			session.setAttribute("currentcount", currentCount);

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

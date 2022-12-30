package db;

import java.sql.SQLException;
import java.util.ArrayList;

import model.AnswersBean;
import model.QuizInfoBean;

/**
 * DB内のクイズ情報と回答情報を取得するクラス
 */
public class QuizQuery extends Db {
	/**
	 * 渡された目標番号のクイズ情報を取得
	 * @param goalNumber	目標番号
	 * @return quizList		クイズ情報
	 * @throws SQLException
	 */
	public ArrayList<QuizInfoBean> getQuizList(String goalNumber) throws SQLException {
		dbInit();
		ArrayList<QuizInfoBean> quizList = new ArrayList<>();
		
		try {
			rs = executeSelect("SELECT quiz, answer FROM quiz_info WHERE goal_number = ?", goalNumber);
			
			while(rs.next()) {
				QuizInfoBean quizInfoObj = new QuizInfoBean(rs.getString("quiz"), rs.getString("answer"));
				// QuizInfoBeanオブジェクトをクイズリスト(quizList)の要素へ追加
				quizList.add(quizInfoObj);
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			try {
				dbClose();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		
		return quizList;
	}
	
	public ArrayList<AnswersBean> getAnswerList(String goalNumber) throws SQLException {
		dbInit();
		ArrayList<AnswersBean> answerList = new ArrayList<>();
		
		try {
			String sql = "SELECT a.answer_id, a.correct, a.incorrect_1, a.incorrect_2, a.incorrect_3 "
				    + "FROM answers AS a JOIN quiz_info AS q ON q.quiz_id = answer_id "
				    + "WHERE q.goal_number = ?";
			rs = executeSelect(sql, goalNumber);
			
			// 回答情報をオブジェクトに格納する
			answerList = new ArrayList<>();
			while(rs.next()) {
				AnswersBean answersObj = new AnswersBean(
						rs.getString("correct"), 
						rs.getString("incorrect_1"), 
						rs.getString("incorrect_2"),	
						rs.getString("incorrect_3"));
				// AnswersBeanオブジェクトを回答リスト(answerList)の要素へ追加
				answerList.add(answersObj);
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			try {
				dbClose();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		
		return answerList;
	}
}

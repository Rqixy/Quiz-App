package db;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.AnswersBean;

/**
 * 回答一覧を取得するクラス
 */
public class AnswersDao extends Db {
	private AnswersDao() {}
	
	/**
	 * 渡された目標番号の回答一覧を取得
	 * @param goalNumber	目標番号
	 * @return answerList		クイズ情報
	 * @throws SQLException
	 */
	public static ArrayList<AnswersBean> answerList(final int goalNumber) throws SQLException {
		dbInit();
		ArrayList<AnswersBean> answerList = new ArrayList<>();
		
		try {
			String sql = "SELECT a.answer_id, a.correct, a.incorrect_1, a.incorrect_2, a.incorrect_3 "
					   + "FROM answers AS a JOIN quiz_info AS q ON q.quiz_id = answer_id "
					   + "WHERE q.goal_number = ?";
			rs = executeSelect(sql, goalNumber);
			// 回答情報をオブジェクトに格納する
			while(rs.next()) {
				AnswersBean answersObj = new AnswersBean(rs.getString("correct"), rs.getString("incorrect_1"), 
														 rs.getString("incorrect_2"), rs.getString("incorrect_3"));
				// AnswersBeanオブジェクトを回答リスト(answerList)の要素へ追加
				answerList.add(answersObj);
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
		}
		
		return answerList;
	}
}

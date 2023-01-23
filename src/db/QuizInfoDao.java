package db;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.QuizInfoBean;

/**
 * DB内の問題一覧を取得するクラス
 */
public class QuizInfoDao extends Db {
	/**
	 * 渡された目標番号の問題一覧を取得
	 * @param goalNumber	目標番号
	 * @return quizList		クイズ情報
	 * @throws SQLException
	 */
	public ArrayList<QuizInfoBean> quizInfoList(int goalNumber) throws SQLException {
		dbInit();
		ArrayList<QuizInfoBean> quizInfoList = new ArrayList<>();
		
		try {
			rs = executeSelect("SELECT quiz, answer FROM quiz_info WHERE goal_number = ?", goalNumber);
			while(rs.next()) {
				QuizInfoBean quizInfoObj = new QuizInfoBean(rs.getString("quiz"), rs.getString("answer"));
				// QuizInfoBeanオブジェクトをクイズリスト(quizList)の要素へ追加
				quizInfoList.add(quizInfoObj);
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
		
		return quizInfoList;
	}
}

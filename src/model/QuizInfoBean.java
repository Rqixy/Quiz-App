package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * クイズ情報管理クラス
 */
public class QuizInfoBean {
	final private String quiz;		// 問題
	final private String answer;	// 回答
	
	QuizInfoBean(final String quiz, final String answer) {
		this.quiz = quiz;
		this.answer = answer;
	}
	
	public static ArrayList<QuizInfoBean> quizList(final ResultSet rs) throws SQLException {
		ArrayList<QuizInfoBean> quizList = new ArrayList<>();
		
		try {
			while(rs.next()) {
				QuizInfoBean quizInfoObj = new QuizInfoBean(rs.getString("quiz"), rs.getString("answer"));
				// QuizInfoBeanオブジェクトをクイズリスト(quizList)の要素へ追加
				quizList.add(quizInfoObj);
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		
		return quizList;
	}
	
	public String quiz() {
		return quiz;
	}

	public String answer() {
		return answer;
	}
}

package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * 回答一覧管理クラス
 */
public class AnswersBean {
	private final String correct;		// 正解
	private final String incorrect1;	// 誤答1
	private final String incorrect2;	// 誤答2
	private final String incorrect3;	// 誤答3

	AnswersBean(final String correct, final String incorrect1, final String incorrect2, final String incorrect3) {
		this.correct = correct;
		this.incorrect1 = incorrect1;
		this.incorrect2 = incorrect2;
		this.incorrect3 = incorrect3;
	}
	
	public static ArrayList<AnswersBean> answersList(final ResultSet rs) throws SQLException {
		ArrayList<AnswersBean> answerList = new ArrayList<>();
		
		try {
			// 回答情報をオブジェクトに格納する
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
				rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		
		return answerList;
	}

	public String correct() {
		return correct;
	}

	public String incorrect1() {
		return incorrect1;
	}

	public String incorrect2() {
		return incorrect2;
	}

	public String incorrect3() {
		return incorrect3;
	}
}

package model;

import java.sql.SQLException;
import java.util.HashMap;

import db.ClearStatusQuery;

/**
 * 問題が終了した時の処理をまとめたクラス
 */
public class Result {
	private final int clearStatusInDb;
	private final float correctAnswerRate;
	private final float allClearRate;
	private final float clearRate;
	private final int allClear;
	private final int clear;
	
	private Result(final int clearStatusInDb, final float correctAnswerRate) {
		this.clearStatusInDb = clearStatusInDb;
		this.correctAnswerRate = correctAnswerRate;
		this.allClearRate = 1f;
		this.clearRate = 0.6f;
		this.allClear = 2;
		this.clear = 1;
	}
	
	/**
	 * 正答率を求め、クリアしたかどうかをチェックする処理
	 * @param sessionInfo
	 * @throws SQLException
	 */
	public static void clear(LoginUserBean loginUserBean, Quiz quiz) throws SQLException {
		try {
			// ユーザーIDと目標番号の取得
			int userId = loginUserBean.getId();
			int goalNumber = quiz.goalNumber();
			float answerCount = quiz.answerCount();
			float maxQuizCount = quiz.maxQuizCount();
			
			// クリア状況の更新処理
			// クリア状況DBの処理するクラスの初期化
			ClearStatusQuery clearStatusQuery = new ClearStatusQuery();
			// DB内のクリア状況と更新するカラム名を取得
			int clearStatusInDb = clearStatusQuery.selectOne(userId, goalNumber);
			// 正答率を求める
			float correctAnswerRate = answerCount / maxQuizCount;
			
			Result result = new Result(clearStatusInDb, correctAnswerRate);
			
			// 正答数を確認して、正答数に応じたクリア状況を更新
			// 1度も全問正解したことがなく、初めて全問正解なら数字を2に更新する
			if (result.isAllClear()) {
				clearStatusQuery.update(goalNumber, result.allClear, userId);
			}
			// 1度もクリアしたことがなく(数字が0)で、初めて正答率が6割超えたら1に更新する
			if (result.isClear()) {
				clearStatusQuery.update(goalNumber, result.clear, userId);
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
	
	// 目標番号からユニセフのURLを取得する処理
	public static String getUnicefUrl(Quiz quiz) {
		// ハッシュ配列で目標番号とSDGsの17の目標のユニセフサイトのURLを格納
		HashMap<Integer, String> goals = new HashMap<Integer, String>();

		// ハッシュ配列に目標番号と17の目標のURLを格納
		goals.put(1, "1-poverty");
        goals.put(2, "2-hunger");
        goals.put(3, "3-health");
        goals.put(4, "4-education");
        goals.put(5, "5-gender");
        goals.put(6, "6-water");
        goals.put(7, "7-energy");
        goals.put(8, "8-economic_growth");
        goals.put(9, "9-industry");
        goals.put(10, "10-inequalities");
        goals.put(11, "11-cities");
        goals.put(12, "12-responsible");
        goals.put(13, "13-climate_action");
        goals.put(14, "14-sea");
        goals.put(15, "15-land");
        goals.put(16, "16-peace");
        goals.put(17, "17-partnerships");
        
        return  "https://www.unicef.or.jp/kodomo/sdgs/17goals/" + goals.get(quiz.goalNumber());
	}
	
	private boolean isAllClear() {
		return clearStatusInDb < allClear && correctAnswerRate == allClearRate;
	}
	
	private boolean isClear() {
		int notClear = 0;
		return clearStatusInDb == notClear && correctAnswerRate >= clearRate;
	}
}

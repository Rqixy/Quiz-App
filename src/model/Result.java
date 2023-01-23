package model;

import java.sql.SQLException;
import java.util.HashMap;

import bean.GoalBean;
import db.ClearStatusDao;

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
	private static String unicefUrl;
	
	private Result(final int clearStatusInDb, final float correctAnswerRate, final String unicefUrl) {
		this.clearStatusInDb = clearStatusInDb;
		this.correctAnswerRate = correctAnswerRate;
		this.allClearRate = 1f;
		this.clearRate = 0.6f;
		this.allClear = 2;
		this.clear = 1;
		Result.unicefUrl = unicefUrl;
	}
	
	/**
	 * 正答率を求め、クリアしたかどうかをチェックする処理
	 * @param sessionInfo
	 * @throws SQLException
	 */
	public static void clear(final LoginUserBean loginUser, final Quiz quiz) throws SQLException {
		try {
			// ユーザーIDと目標番号の取得
			int goalNumber = quiz.goalNumber();
			
			// クリア状況の更新処理
			// クリア状況DBの処理するクラスの初期化
			ClearStatusDao clearStatusDao = new ClearStatusDao();
			// DB内のクリア状況を取得
			GoalBean goal = clearStatusDao.goal(loginUser, quiz);
			int clearStatusInDb = goal.clearStatus();
			// 正答率を求める
			float answerCount = (float)quiz.answerCount();
			float maxQuizCount = (float)quiz.maxQuizCount();
			float correctAnswerRate = answerCount / maxQuizCount;
			
			String unicefUrl = unicefUrlByGoalNumber(goalNumber);
			Result result = new Result(clearStatusInDb, correctAnswerRate, unicefUrl);
			
			// 正答数を確認して、正答数に応じたクリア状況を更新
			// 1度も全問正解したことがなく、初めて全問正解なら数字を2に更新する
			if (result.isAllClear()) {
				clearStatusDao.update(loginUser, quiz, result.allClear);
			}
			// 1度もクリアしたことがなく(数字が0)で、初めて正答率が6割超えたら1に更新する
			if (result.isClear()) {
				clearStatusDao.update(loginUser, quiz, result.clear);
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
	
	public static String unicefUrl() {
		return unicefUrl;
	}
	
	/**
	 * 目標番号からユニセフのURLを取得する処理
	 * @param goalNumber
	 */
	private static String unicefUrlByGoalNumber(final int goalNumber) {
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
        
        return "https://www.unicef.or.jp/kodomo/sdgs/17goals/" + goals.get(goalNumber);
	}
	
	/**
	 * 全問正解判定
	 */
	private boolean isAllClear() {
		return clearStatusInDb < allClear && correctAnswerRate == allClearRate;
	}
	
	/**
	 * 正解判定
	 */
	private boolean isClear() {
		int notClear = 0;
		return clearStatusInDb == notClear && correctAnswerRate >= clearRate;
	}
}

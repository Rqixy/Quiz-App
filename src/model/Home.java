package model;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.GoalBean;
import db.ClearStatusDao;

public class Home {
	public static ArrayList<GoalBean> goalList(LoginUserBean loginUser) {
		ArrayList<GoalBean> goalList = new ArrayList<GoalBean>();
		
		try {
			// クリア状況DBの処理するクラスの初期化
			ClearStatusDao clearStatusDao = new ClearStatusDao();
			// 送られてきたユーザーIDのクリア状況のデータが作成されているか確認し、
			// クリア状況のデータが作成されていなかったら、新規に作成
			if (!clearStatusDao.exist(loginUser)) {
				clearStatusDao.insert(loginUser);
			}

			// ユーザーIDからクリア状況のテーブルを参照し、参照したクリア状況を配列に格納
			goalList = clearStatusDao.goalList(loginUser);
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		
		return goalList;
	}
}

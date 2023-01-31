package model;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.GoalBean;
import bean.LoginUserBean;
import db.ClearStatusDao;

public class Home {
	public static ArrayList<GoalBean> goalList(final LoginUserBean loginUser) {
		ArrayList<GoalBean> goalList = new ArrayList<GoalBean>();
		
		try {
			// 送られてきたユーザーIDのクリア状況のデータが作成されているか確認し、
			// クリア状況のデータが作成されていなかったら、新規に作成
			if (!ClearStatusDao.exist(loginUser)) {
				ClearStatusDao.insert(loginUser);
			}

			// ユーザーIDからクリア状況のテーブルを参照し、参照したクリア状況を配列に格納
			goalList = ClearStatusDao.goalList(loginUser);
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		
		return goalList;
	}
}

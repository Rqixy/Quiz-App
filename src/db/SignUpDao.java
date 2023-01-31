package db;

import java.sql.SQLException;

import bean.RegisterUserBean;

public class SignUpDao extends Db {
	private SignUpDao() {}
	
	//登録処理
	public static boolean userAdd(RegisterUserBean registUser) throws SQLException {
		//初期化
		dbInit();
		boolean flag = false;
		
		try {
			//SQLを実行
			int addCount = executeUpdate("INSERT into users (name, pass) VALUES(?, ?)", registUser.name(), registUser.pass());
			if(addCount == 1) {
				flag = true;
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
		}
		
		return flag;
	}
}

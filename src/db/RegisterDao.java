package db;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.RegistUserBean;

public class RegisterDao extends Db {
	private RegisterDao() {}
	
	public static ArrayList<String> selectAll() throws SQLException {
		dbInit();
		ArrayList<String> registeredNameList = new ArrayList<String>();
		
		try {
			//SQLを実行
			rs = executeSelect("SELECT * FROM users");
			while(rs.next()) {
				registeredNameList.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
		}
		
		return registeredNameList;
	}
	
	//登録処理
	public static boolean userAdd(RegistUserBean registUser) throws SQLException {
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

package db;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.LoginUserBean;
import bean.RegistUserBean;

public class UsersDao extends Db {
	private UsersDao() {}
	
	public static LoginUserBean select(String name, String pass) throws SQLException {
		dbInit();
		LoginUserBean loginUser = null;
		
		try {
			//SQLを実行
			rs = executeSelect("select id, name from users where name = ? and pass = ?", name, pass);
			
			//実行結果を貰う
			if(rs.next()) {
				loginUser = new LoginUserBean(rs.getInt("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return loginUser;
	}
	
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
	public static boolean insert(RegistUserBean registUser) throws SQLException {
		//初期化
		dbInit();
		boolean result = false;
		
		try {
			//SQLを実行
			result = executeUpdate("INSERT into users (name, pass) VALUES(?, ?)", registUser.name(), registUser.pass());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
		}
		
		return result;
	}
	
	public static boolean delete(final LoginUserBean loginUser) throws SQLException {
		dbInit();
		boolean result = false;
		
		try {
			result = executeUpdate("DELETE FROM users WHERE id = ?", loginUser.id());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return result;
	}
}

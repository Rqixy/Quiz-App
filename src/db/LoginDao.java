package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import bean.LoginUserBean;

public class LoginDao extends Db {
	private LoginDao() {}
	
	public static LoginUserBean selectUser(String name, String pass) throws SQLException {
		dbInit();
		LoginUserBean loginUser = null;
		
		try {
			//SQLを実行
			ResultSet res = executeSelect("select id, name from users where name = ? and pass = ?", name, pass);
			
			//実行結果を貰う
			if(res.next()) {
				loginUser = new LoginUserBean(res.getInt("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dbClose();
		}

		return loginUser;
	}
}

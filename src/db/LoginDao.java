package db;

import java.sql.SQLException;

import bean.LoginUserBean;

public class LoginDao extends Db {
	private LoginDao() {}
	
	public static LoginUserBean selectUser(String name, String pass) throws SQLException {
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
}

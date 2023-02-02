package db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import bean.LoginUserBean;

public class LoginDao extends Db {
	private LoginDao() {}
	
	public static LoginUserBean selectUser(String name, String pass) throws SQLException, NoSuchAlgorithmException {
		dbInit();
		LoginUserBean loginUser = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA3-256");
			byte[] bytePassword = md.digest(pass.getBytes());
			String encodedPass = String.format("%040x", new BigInteger(1, bytePassword));
			
			//SQLを実行
			rs = executeSelect("select id, name from users where name = ? and pass = ?", name, encodedPass);
			
			//実行結果を貰う
			if(rs.next()) {
				loginUser = new LoginUserBean(rs.getInt("id"), rs.getString("name"));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return loginUser;
	}
}

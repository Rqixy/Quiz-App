package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
	//DB接続
	Db db = new Db();
	Connection con = db.DbConnection();
	
	//
	int loggedInUser;
	
	public int selectUser(String name, String pass){
		try {
			//SQLを書く
			String sql = "select id, name from users where name = ? and pass = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			//名前を設定
			ps.setString(1, name);
			
			//パスワードを設定
			ps.setString(2, pass);
		
			//SQLを実行
			ResultSet res = ps.executeQuery();
			
			//実行結果を貰う
			if(res.next()) {
				loggedInUser = res.getInt("id");
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally{
			//接続を解除
			try {
				con.close();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		//返す
		return loggedInUser;
	}
}

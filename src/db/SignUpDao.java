package db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpDao {
	//DB接続
	Db db = new Db();
	Connection con = db.DbConnection();
	
	//登録処理
	public boolean UserAdd(String name, String pass) throws Exception {
		//初期化？
		boolean flag = false;
		
		try {
			//SQLを書く
			String sql = "INSERT into users VALUES(?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			//名前を設定
			ps.setString(1, name);
		
			//パスワードを設定
			ps.setString(2, pass);
			
			//SQLを実行
			int addCount = ps.executeUpdate();
			if(addCount == 1) {
				flag = true;
			}
		}catch(Exception e){
			throw new Exception("登録エラー");
		}finally {
			con.close();
		}
		
		return flag;
		
	}
}

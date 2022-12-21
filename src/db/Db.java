package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
	//ドライバー名
	private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	// JDBC接続先情報
	private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/tech_c_itpj?useSSL=false";
	// ユーザー名
	private static final String USER = "root";
	// パスワード
	private static final String PASS = "";

	public Connection DbConnection() {
		Connection connection = null;

		if (connection == null) {
			try {
				// JDBCドライバのロード
				Class.forName(MYSQL_DRIVER);

				// DBへの接続
				connection = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS);
			} catch (ClassNotFoundException e) {
				e.getStackTrace();
			} catch (SQLException e) {
				e.getStackTrace();
			}catch (Exception e) {
				e.getStackTrace();
			}
		}

		return connection;
	}
}

// 使い方
// Db db = new Db();
// Connection con = db.DbConnection();

package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * DB操作関連をまとめたクラス
 * 継承して他のクラスで使う
 */
class Db {
	private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";	//ドライバー名
	private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/tech_c_itpj?useSSL=false";	// JDBC接続先情報
	private static final String USER = "root";	// ユーザー名
	private static final String PASS = "";	// パスワード
	
	private Connection con;
	protected PreparedStatement ps;
	protected ResultSet rs;
	protected ResultSetMetaData rsmd;

	/**
	 * DB接続処理
	 * @return con
	 */
	public Connection DbConnection() {
		Connection con = null;

		if (con == null) {
			try {
				// JDBCドライバのロード
				Class.forName(MYSQL_DRIVER);
				// DBへの接続
				con = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS);
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException : " + e.getMessage());
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}

		return con;
	}
	
	/**
	 * プリペアードステートメントに値をセットする処理
	 * @param ps
	 * @param params
	 * @throws SQLException
	 */
	private void setParams(PreparedStatement ps, Object... params) throws SQLException {
		int paramNum = 1;
		for (Object param : params) {
			ps.setObject(paramNum++, param);
		}
	}
	
	/**
	 * データベース処理するための初期化処理
	 */
	protected void dbInit() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		this.con = DbConnection();
		this.ps = ps;
		this.rs = rs;
		this.rsmd = rsmd;
	}
	
	
	/**
	 * DB接続のクローズ処理
	 * @throws SQLException
	 */
	protected void dbClose() throws SQLException {
		if (con != null || ps != null) {
			con.close();
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
	}
	
	/**
	 * SELECT文の実行
	 * @param sql
	 * @param params
	 * @return rs
	 * @throws SQLException
	 */
	protected ResultSet executeSelect(String sql, Object... params) throws SQLException {
		dbInit();
		
		try {
			ps = con.prepareStatement(sql);
			setParams(ps, params);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		
		return rs;
	}
	
	/**
	 * INSERTやUPDATE文の実行
	 * @param sql
	 * @param params
	 * @return result
	 * @throws SQLException
	 */
	protected int executeUpdate(String sql, Object... params) throws SQLException {
		dbInit();
		int result = 0;
		
		try {
			ps = con.prepareStatement(sql);
			setParams(ps, params);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			try {
				dbClose();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		
		return result;
	}
}

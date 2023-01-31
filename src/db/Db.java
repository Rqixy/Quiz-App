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
 * 
 * // 使い方
 *class TestDao extends Db {
 *	public static TestBean select(final int id) throws SQLException {
 *		// 初期化
 *		dbInit();
 *	    TestBean test = null;
 *	    
 *	   	try {
 *	   		// SELECT文の実行
 *	   		// 引数にSQL文と必要な値を入力する
 *	   		rs = executeSelect("SELECT * FROM test WHERE id = ?", id);
 *	   		// 実行結果
 *	   		if (rs.next()) {
 *	   			test = new Test(rs.getString("name"), rs.getInt("age"));
 *	   		}
 *	   	} catch (SQLException e) {
 *	   		System.out.println("SQLException : " + e.getMessage());
 *		} finally {
 *			// クローズ処理
 *			dbClose();
 *		}
 *			
 *		return test;
 *   }
 *	
 *	public static int insertOrUpdate(final TestBean test) {
 *		// 初期化
 *		dbInit();
 *		int result = 0;
 *		
 *		try {
 *			// INSERT文かUPDATE文の実行
 *	   		// 引数にSQL文と必要な値を入力する
 *	   		result = executeUpdate("INSERT INTO (name, age) VALUES (?, ?)", test.name, test.age);
 *	   	} catch (SQLException e) {
 *	   		System.out.println("SQLException : " + e.getMessage());
 *		} finally {
 *			// クローズ処理ｓ
 *			dbClose();
 *	 	}
 *	 	
 *	 	return result;
 *	 }
 *}
 */

 abstract class Db {
	private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";	//ドライバー名
	private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/tech_c_itpj?useSSL=false";	// JDBC接続先情報
	private static final String USER = "root";	// ユーザー名
	private static final String PASS = "";	// パスワード
	
	private static Connection con;
	protected static PreparedStatement ps;
	protected static ResultSet rs;
	protected static ResultSetMetaData rsmd;
	
	protected Db() {}

	/**
	 * DB接続処理
	 * @return con
	 */
	private static Connection DbConnection() {
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
	 * データベース処理するための初期化処理
	 */
	protected static void dbInit() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		Db.con = DbConnection();
		Db.ps = ps;
		Db.rs = rs;
		Db.rsmd = rsmd;
	}
	
	
	/**
	 * DB接続のクローズ処理
	 * @throws SQLException
	 */
	protected static void dbClose() {
		try {
			if (con != null || ps != null) {
				con.close();
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}	
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
	
	/**
	 * SELECT文の実行
	 * @param sql
	 * @param params
	 * @return ps.executeQuery()
	 * @throws SQLException
	 */
	protected static ResultSet executeSelect(final String sql, final Object... params) throws SQLException {
		dbInit();
		
		ps = con.prepareStatement(sql);
		setParams(ps, params);
		
		return ps.executeQuery();
	}
	
	/**
	 * INSERTやUPDATE文の実行
	 * @param sql
	 * @param params
	 * @return result
	 * @throws SQLException
	 */
	protected static int executeUpdate(final String sql, final Object... params) throws SQLException {
		dbInit();
		con.setAutoCommit(false);
		
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			setParams(ps, params);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			con.commit();
			dbClose();
		}
		
		return result;
	}
	
	/**
	 * プリペアードステートメントに値をセットする処理
	 * @param ps
	 * @param params
	 * @throws SQLException
	 */
	private static void setParams(final PreparedStatement ps, final Object... params) throws SQLException {
		int paramNum = 1;
		for (Object param : params) {
			ps.setObject(paramNum++, param);
		}
	}
}

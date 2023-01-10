package db;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * DBのclear_statusテーブルの処理するクラス
 */
public class ClearStatusQuery extends Db {
	/**
	 * XXX ログイン機能実装後、削除する！！
	 */
	public int getUserId() throws SQLException {
		dbInit();
		int userId = 0;
		
		try {
			rs = executeSelect("SELECT * FROM users WHERE id = 1");
			if (rs.next()) {
				userId = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			try {
				dbClose();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		return userId;
	}
	
	/**
	 * ユーザーIDからクリア状況を取得
	 * @param userId		ユーザーID
	 * @return clearStatus	クリア状況のハッシュ配列(key:目標番号 value:クリア状況)
	 * @throws SQLException
	 */
	public HashMap<Integer, Integer> select(int userId) throws SQLException {
		dbInit();
		// クリア状況を格納するハッシュ配列の初期化
		HashMap<Integer, Integer> clearStatus = new HashMap<Integer, Integer>();
		
		try {
			/* ユーザーIDからクリア状況のテーブルを参照し、参照したクリア状況を配列に格納しておく */
			// カラム名の配列の初期化
			String[] columnNames = new String[17];
			
			rs = executeSelect("SELECT * FROM clear_status WHERE user_id = ?", userId);
			rsmd = rs.getMetaData();
	
			// clear_statusテーブルのカラム数を取得する
			int columnCount = rsmd.getColumnCount();
			// 配列にclear_statusテーブルのカラム名を格納する
			for (int i = 2; i <= columnCount; i++) {
				columnNames[i-2] = rsmd.getColumnName(i);
			}
	
			/* クリア状況のハッシュ配列に目標番号とクリア状況の内容を格納する */
			if (rs.next()) {
				for (int i = 0; i < columnNames.length; i++) {
					clearStatus.put(i+1, rs.getInt(columnNames[i]));
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			try {
				dbClose();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		
		return clearStatus;
	}
	
	/**
   	 * 目標番号のクリア状況を取得する
   	 * @param userId		ユーザーID
   	 * @param goalNumber	目標番号
   	 * @return clearStatus	クリア状況
   	 * @throws SQLException
   	 */
	public int selectOne(int userId, String goalNumber) throws SQLException {
		dbInit();
		int clearStatus = 0;
		
		try {
			HashMap<String, String> columnNames = selectColumnNames(userId);

			rs = executeSelect("SELECT * FROM clear_status WHERE user_id = ?", userId);
			
	
			// DB内のクリア状況の取得
			if (rs.next()) {
				clearStatus = rs.getInt(columnNames.get(goalNumber));
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			try {
				dbClose();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		
		return clearStatus;
	}
	
	/**
	 * 目標番号のクリアステータスのカラム名を取得する
	 * @param userId		ユーザーID
	 * @param goalNumber	目標番号
	 * @return columnName	カラム名
	 * @throws SQLException
	 */
   	public String selectOneColumnName(int userId, String goalNumber) throws SQLException {
   		dbInit();
   		String columnName = "";
   		
		try {
			HashMap<String, String> columnNames = selectColumnNames(userId);
			/* ユーザーが選択した目標番号のクイズのカラム名を取得 */
			columnName = columnNames.get(goalNumber);
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		
		return columnName;
	}
	
   	/**
	 * ユーザーIDからクリア状況のカラム名を取得
	 * @param userId		ユーザーID
	 * @return columnNames	クリア状況のカラム名のハッシュ配列(Key: 目標番号 Value: 目標番号のカラム名)
	 * @throws SQLException
	 */
	private HashMap<String, String> selectColumnNames(int userId) throws SQLException {
		dbInit();
		HashMap<String, String> columnNames = new HashMap<String, String>();
		 
		try {
			rs = executeSelect("SELECT * FROM clear_status WHERE user_id = ?", userId);
			rsmd = ps.getMetaData();
			
			// clear_statusテーブルのカラム数を取得
			int columnCount = rsmd.getColumnCount();
			// ハッシュ配列に目標番号とそのカラム名を格納
			for (int i = 2; i <= columnCount; i++) {
				//clear_statusテーブルの目標番号
				String clearStatusGoalNumber = Integer.valueOf(i-1).toString();
				// ハッシュ配列に格納
				columnNames.put(clearStatusGoalNumber, rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			try {
				dbClose();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		 
		return columnNames;
	}
	
	/**
	 * クリア状況のUpdate文
	 * @param columnName
	 * @param updateStatus
	 * @param userId
	 * @return result
	 * @throws SQLException
	 */
	public int update(String columnName, int updateStatus, int userId) throws SQLException {
		dbInit();
		int result = 0;
		
		try {
			result = executeUpdate("UPDATE clear_status SET " + columnName + " = ? WHERE user_id = ?", updateStatus, userId);
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

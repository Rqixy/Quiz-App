package db;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * DBのclear_statusテーブルの処理するクラス
 */
public class ClearStatusQuery extends Db {
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
	 * 受け取ったユーザーIDのデータが存在するかチェックする
	 * @param userId
	 * @return isExist
	 * @throws SQLException
	 */
	public boolean exist(int userId) throws SQLException {
		dbInit();
		boolean isExist = false;
		try {
			rs = executeSelect("SELECT * FROM clear_status WHERE user_id = ?", userId);
			if (rs.next()) {
				isExist = true;
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
		
		return isExist;
	}
	
   	/**
   	 * 新しいクリアステータスを作成する
   	 * @param userId
   	 * @return result
   	 * @throws SQLException
   	 */
	public int insert(int userId) throws SQLException {
		dbInit();
		int result = 0;
		
		try {
			result = executeUpdate("INSERT into clear_status (user_id) values (?)", userId);
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
	
	/**
	 * クリア状況のUpdate文
	 * @param goalNumber
	 * @param updateStatus
	 * @param userId
	 * @return result
	 * @throws SQLException
	 */
	public int update(String goalNumber, int updateStatus, int userId) throws SQLException {
		dbInit();
		int result = 0;
		
		try {
			result = executeUpdate(clearStatusUpdateStatement(goalNumber), updateStatus, userId);
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
	 * クリア状況の目標番号のUpdate文を取得する
	 * @param goalNumber
	 * @return clearStatusUpdateStatements.get(goalNumber)
	 */
	private String clearStatusUpdateStatement(String goalNumber) {
		HashMap<String, String> clearStatusUpdateStatements = new HashMap<String, String>();
		
		// 17の目標の番号とそのクリア状況のUpdate文を格納
		clearStatusUpdateStatements.put("1", "UPDATE clear_status SET 1_poverty = ? WHERE user_id = ?");
		clearStatusUpdateStatements.put("2", "UPDATE clear_status SET 2_hunger = ? WHERE user_id = ?");
		clearStatusUpdateStatements.put("3", "UPDATE clear_status SET 3_health = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("4", "UPDATE clear_status SET 4_education = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("5", "UPDATE clear_status SET 5_gender = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("6", "UPDATE clear_status SET 6_water = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("7", "UPDATE clear_status SET 7_energy = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("8", "UPDATE clear_status SET 8_economic_growth = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("9", "UPDATE clear_status SET 9_industry = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("10", "UPDATE clear_status SET 10_inequalities = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("11", "UPDATE clear_status SET 11_cities = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("12", "UPDATE clear_status SET 12_responsible = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("13", "UPDATE clear_status SET 13_climate_action = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("14", "UPDATE clear_status SET 14_sea = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("15", "UPDATE clear_status SET 15_land = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("16", "UPDATE clear_status SET 16_peace = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put("17", "UPDATE clear_status SET 17_partnerships = ? WHERE user_id = ?");
        
        return clearStatusUpdateStatements.get(goalNumber);
	}
}

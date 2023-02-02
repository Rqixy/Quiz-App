package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import bean.GoalBean;
import bean.LoginUserBean;
import model.Quiz;

/**
 * DBのclear_statusテーブルの処理するクラス
 */
public class ClearStatusDao extends Db {
	private ClearStatusDao() {}
	
	/**
	 * 17の目情のオブジェクトリストを取得
	 * @param loginUserBean
	 * @return clearStatusList
	 * @throws SQLException
	 */
	public static ArrayList<GoalBean> goalList(final LoginUserBean loginUser) throws SQLException {
		dbInit();
		final ArrayList<GoalBean> goalList = new ArrayList<GoalBean>();
		
		try {
			rs = select(loginUser);
			rsmd = rs.getMetaData();
			
			// clear_statusテーブルのカラム数を取得する
			final int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				for (int goalNumber = 1; goalNumber <= columnCount; goalNumber++) {
					final String columnName = columnName(rs, goalNumber);
					
					final GoalBean clearStatusBean = new GoalBean(goalNumber, rs.getInt(columnName));
					goalList.add(clearStatusBean);
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
		}
		
		return goalList;
	}
	
	/**
	 * 1つの目標のオブジェクトを取得する処理
	 * @param loginUser
	 * @param quiz
	 * @return  goal
	 * @throws SQLException
	 */
	public static GoalBean goal(final LoginUserBean loginUser, final Quiz quiz) throws SQLException {
		dbInit();
		GoalBean goal = null;
		
		final int goalNumber = quiz.goalNumber();
		
		try {
			rs = select(loginUser);
			final String columnName = columnName(rs, goalNumber);
			
			// DB内のクリア状況の取得
			if (rs.next()) {
				goal = new GoalBean(goalNumber, rs.getInt(columnName));
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
		}
		
		return goal;
	}
	
	/**
	 * 受け取ったユーザーIDのデータが存在するかチェックする
	 * @param userId
	 * @return isExist
	 * @throws SQLException
	 */
	public static boolean exist(final LoginUserBean loginUser) throws SQLException {
		dbInit();
		boolean isExist = false;
		try {
			rs = executeSelect("SELECT * FROM clear_status WHERE user_id = ?", loginUser.id());
			if (rs.next()) {
				isExist = true;
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
		}
		
		return isExist;
	}
	
   	/**
   	 * 新しいクリアステータスを作成する
   	 * @param userId
   	 * @return result
   	 * @throws SQLException
   	 */
	public static int insert(final LoginUserBean loginUser) throws SQLException {
		dbInit();
		int result = 0;
		
		try {
			result = executeUpdate("INSERT into clear_status (user_id) values (?)", loginUser.id());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
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
	public static int update(final LoginUserBean loginUser, final Quiz quiz, final int updateStatus) throws SQLException {
		dbInit();
		int result = 0;
		
		try {
			String sql = clearStatusUpdateStatement(quiz.goalNumber());
			result = executeUpdate(sql, updateStatus, loginUser.id());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		} finally {
			dbClose();
		}
		
		return result;
	}
	
	/**
	 * DBからクリア状況のデータを取得する処理
	 * @param loginUserBean
	 * @return executeSelect(sql, userId)
	 * @throws SQLException
	 */
	private static ResultSet select(final LoginUserBean loginUser) throws SQLException {
		dbInit();
		final String sql = "SELECT "
							+ "1_poverty, 2_hunger, 3_health, 4_education, 5_gender, 6_water, "
							+ "7_energy, 8_economic_growth, 9_industry, 10_inequalities, 11_cities, 12_responsible, "
							+ "13_climate_action, 14_sea, 15_land, 16_peace, 17_partnerships "
						 + "FROM clear_status WHERE user_id = ?";
		
		return executeSelect(sql, loginUser.id());
	}
	
	/**
	 * clear_statusテーブルのカラム名を取得する処理
	 * @param rs
	 * @param goalNumber
	 * @return rsmd.getColumnName(goalNumber)
	 * @throws SQLException
	 */
	private static String columnName(final ResultSet rs, final int goalNumber) throws SQLException {
		rsmd = rs.getMetaData();
		return rsmd.getColumnName(goalNumber);
	}
	
	/**
	 * クリア状況の目標番号のUpdate文を取得する
	 * @param goalNumber
	 * @return clearStatusUpdateStatements.get(goalNumber)
	 */
	private static String clearStatusUpdateStatement(final int goalNumber) {
		HashMap<Integer, String> clearStatusUpdateStatements = new HashMap<Integer, String>();
		
		// 17の目標の番号とそのクリア状況のUpdate文を格納
		clearStatusUpdateStatements.put(1, "UPDATE clear_status SET 1_poverty = ? WHERE user_id = ?");
		clearStatusUpdateStatements.put(2, "UPDATE clear_status SET 2_hunger = ? WHERE user_id = ?");
		clearStatusUpdateStatements.put(3, "UPDATE clear_status SET 3_health = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(4, "UPDATE clear_status SET 4_education = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(5, "UPDATE clear_status SET 5_gender = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(6, "UPDATE clear_status SET 6_water = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(7, "UPDATE clear_status SET 7_energy = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(8, "UPDATE clear_status SET 8_economic_growth = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(9, "UPDATE clear_status SET 9_industry = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(10, "UPDATE clear_status SET 10_inequalities = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(11, "UPDATE clear_status SET 11_cities = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(12, "UPDATE clear_status SET 12_responsible = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(13, "UPDATE clear_status SET 13_climate_action = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(14, "UPDATE clear_status SET 14_sea = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(15, "UPDATE clear_status SET 15_land = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(16, "UPDATE clear_status SET 16_peace = ? WHERE user_id = ?");
        clearStatusUpdateStatements.put(17, "UPDATE clear_status SET 17_partnerships = ? WHERE user_id = ?");
        
        return clearStatusUpdateStatements.get(goalNumber);
	}
}

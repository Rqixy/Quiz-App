package db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import bean.GoalBean;
import model.LoginUserBean;
import model.Quiz;

/**
 * DBのclear_statusテーブルの処理するクラス
 */
public class ClearStatusDao extends Db {
	/**
	 * 17の目情のオブジェクトリストを取得
	 * @param loginUserBean
	 * @return clearStatusList
	 * @throws SQLException
	 */
	public ArrayList<GoalBean> goalList(LoginUserBean loginUserBean) throws SQLException {
		dbInit();
		ArrayList<GoalBean> goalList = new ArrayList<GoalBean>();
		
		try {
			int userId = loginUserBean.getId();
			
			rs = executeSelect("SELECT "
								+ "1_poverty, 2_hunger, 3_health, 4_education, 5_gender, 6_water, "
								+ "7_energy, 8_economic_growth, 9_industry, 10_inequalities, 11_cities, 12_responsible, "
								+ "13_climate_action, 14_sea, 15_land, 16_peace, 17_partnerships "
							 + "FROM clear_status WHERE user_id = ?", userId);
			rsmd = rs.getMetaData();
			
			// clear_statusテーブルのカラム数を取得する
			int columnCount = rsmd.getColumnCount();
			
			if (rs.next()) {
				for (int goalNumber = 1; goalNumber <= columnCount; goalNumber++) {
					String columnName = rsmd.getColumnName(goalNumber);
					
					GoalBean clearStatusBean = new GoalBean(userId, columnName, goalNumber, rs.getInt(columnName));
					goalList.add(clearStatusBean);
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
		
		return goalList;
	}
	
	/**
	 * 1つの目標のオブジェクトを取得する処理
	 * @param loginUser
	 * @param quiz
	 * @return  goal
	 */
	public GoalBean goal(final LoginUserBean loginUser, final Quiz quiz) {
		dbInit();
		GoalBean goal = null;
		int userId = loginUser.getId();
		int goalNumber = quiz.goalNumber();
		
		try {
			rs = executeSelect("SELECT "
								+ "1_poverty, 2_hunger, 3_health, 4_education, 5_gender, 6_water, "
								+ "7_energy, 8_economic_growth, 9_industry, 10_inequalities, 11_cities, 12_responsible, "
								+ "13_climate_action, 14_sea, 15_land, 16_peace, 17_partnerships "
							 + "FROM clear_status WHERE user_id = ?", userId);
			rsmd = rs.getMetaData();
			
			String columnName = rsmd.getColumnName(goalNumber);
			
			// DB内のクリア状況の取得
			if (rs.next()) {
				goal = new GoalBean(userId, columnName, goalNumber, rs.getInt(columnName));
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
		
		return goal;
	}
	
	/**
	 * 受け取ったユーザーIDのデータが存在するかチェックする
	 * @param userId
	 * @return isExist
	 * @throws SQLException
	 */
	public boolean exist(LoginUserBean loginUser) throws SQLException {
		dbInit();
		boolean isExist = false;
		try {
			rs = executeSelect("SELECT * FROM clear_status WHERE user_id = ?", loginUser.getId());
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
	public int insert(LoginUserBean loginUser) throws SQLException {
		dbInit();
		int result = 0;
		
		try {
			result = executeUpdate("INSERT into clear_status (user_id) values (?)", loginUser.getId());
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
	public int update(final LoginUserBean loginUser, final Quiz quiz, final int updateStatus) throws SQLException {
		dbInit();
		int result = 0;
		
		try {
			result = executeUpdate(clearStatusUpdateStatement(quiz.goalNumber()), updateStatus, loginUser.getId());
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
	 * クリア状況の目標番号のUpdate文を取得する
	 * @param goalNumber
	 * @return clearStatusUpdateStatements.get(goalNumber)
	 */
	private String clearStatusUpdateStatement(final int goalNumber) {
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
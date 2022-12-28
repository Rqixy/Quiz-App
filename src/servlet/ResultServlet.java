package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.Db;

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			/* セッションスコープの準備 */
			HttpSession session = request.getSession();
			// 目標番号の取得
			String goalNumber = (String)session.getAttribute("goalNumber");
			
			/* クリア状況の更新処理 */
			updateClearStatus(session, goalNumber);
	        /* セッションに目標番号のユニセフサイトのURLを保存 */
	        session.setAttribute("goalUrl", "https://www.unicef.or.jp/kodomo/sdgs/17goals/" + geUnicefUrl(goalNumber));

			// 結果画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/result.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	/* クリア状況を更新する処理 */
	private void updateClearStatus(HttpSession session, String goalNumber) {
		try {
			/* DBに接続 */
			Db db = new Db();
			Connection con = db.DbConnection();
			// 初期化
			String sql = "";
			PreparedStatement ps = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
	
			/* セッションに保存しておいたユーザーIDと目標番号を取得 */
			int userId = (int)session.getAttribute("userId");
	
	        /* ハッシュ配列に目標番号とclear_statusテーブルのカラム名を格納 */
			// SQL文の発行
			sql = "SELECT * FROM clear_status WHERE user_id = ?";
			// プリペアードステートメント作成
			ps = con.prepareStatement(sql);
			// 値をセットする
			ps.setInt(1, userId);
			// 実行
			rs = ps.executeQuery();
			rsmd = ps.getMetaData();
	
			/* ハッシュ配列に目標番号とclear_statusテーブルのカラム名を格納する */
			HashMap<String, String> clearStatusColumns = new HashMap<String, String>();
			// clear_statusテーブルのカラム数を取得
			int clearStatusColumnCount = rsmd.getColumnCount();
			// ハッシュ配列に目標番号とそのカラム名を格納
			for (int i = 2; i <= clearStatusColumnCount; i++) {
				//clear_statusテーブルの目標番号
				String clearStatusGoalNumber = Integer.valueOf(i-1).toString();
				// ハッシュ配列に格納
				clearStatusColumns.put(clearStatusGoalNumber, rsmd.getColumnName(i));
			}
			/* ユーザーが選択した目標番号のクイズのカラム名を取得 */
			String selectedClearStatusColumnName = clearStatusColumns.get(goalNumber);
	
			/* 更新前のクリア状況を取得する */
			// SQL文の発行
			sql = "SELECT " + selectedClearStatusColumnName + " FROM clear_status WHERE user_id = ?";
			// プリペアードステートメント作成
			ps = con.prepareStatement(sql);
			// 値をセットする
			ps.setInt(1, userId);
			// 実行
			rs = ps.executeQuery();
	
			// DB内のクリア状況の取得
			int clearStatusInDb = 0;
			if (rs.next()) {
				clearStatusInDb = rs.getInt(clearStatusColumns.get(goalNumber));
			}
	
			/* 正答数を確認して、正答数に応じたクリア状況を保存 */
			float answerCount = (int)session.getAttribute("answerCount");
			float maxQuizCount = (int)session.getAttribute("maxQuizCount");
	
			// 正答率を求める
			float correctAnswerRate = answerCount / maxQuizCount;
			if (clearStatusInDb < 2 && correctAnswerRate == 1) {
				// 全問正解したことがなく、初めて全問正解なら数字を2に更新する
				executeUpdateClearStatus(selectedClearStatusColumnName, 2, userId);
			} else if (clearStatusInDb == 0 && correctAnswerRate >= 0.6f) {
				// もしまだ6割未満(数字が0)で、今回正答率が6割超えたら1に更新する
				executeUpdateClearStatus(selectedClearStatusColumnName, 1, userId);
			}
	
			/* DBへの接続開放(クローズ処理) */
			con.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
	
	/* DBにクリア状況を更新する処理 */
	private void executeUpdateClearStatus(String clearStatusColumn, int updateStatus, int userId) {
		try {
			/* DBに接続する */
			Db db = new Db();
			Connection con = db.DbConnection();
			
			// 更新処理のSQl文
			String sql = "UPDATE clear_status SET " + clearStatusColumn + " = ? WHERE user_id = ?";
			// プリペアードステートメント作成
			PreparedStatement ps = con.prepareStatement(sql);
			// 数字を2にセットする
			ps.setInt(1, updateStatus);
			// ユーザーIDをセット
			ps.setInt(2, userId);
			// 実行
			ps.executeUpdate();
			
			/* DBへの接続開放(クローズ処理) */
			con.close();
			ps.close();
		} catch(SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		
	}
	
	/* 目標番号からユニセフのURLを取得する処理 */
	private String geUnicefUrl(String goalNumber) {
		/* ハッシュ配列で目標番号とSDGsの17の目標のユニセフサイトのURLを格納する */
		// ハッシュ配列の初期化
		HashMap<String, String> goals = new HashMap<String, String>();

		// ハッシュ配列に目標番号と17の目標のURLを格納する
		goals.put("1", "1-poverty");
        goals.put("2", "2-hunger");
        goals.put("3", "3-health");
        goals.put("4", "4-education");
        goals.put("5", "5-gender");
        goals.put("6", "6-water");
        goals.put("7", "7-energy");
        goals.put("8", "8-economic_growth");
        goals.put("9", "9-industry");
        goals.put("10", "10-inequalities");
        goals.put("11", "11-cities");
        goals.put("12", "12-responsible");
        goals.put("13", "13-climate_action");
        goals.put("14", "14-sea");
        goals.put("15", "15-land");
        goals.put("16", "16-peace");
        goals.put("17", "17-partnerships");
        
        return goals.get(goalNumber);
	}
}

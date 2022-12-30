package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import db.ClearStatusQuery;
import db.Db;

@WebServlet("/TopServlet")
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			// クリア状況DBの処理するクラスの初期化
			ClearStatusQuery clearStatusQuery = new ClearStatusQuery();
			
			// XXX 仮のユーザーID(ログイン処理実装後削除)
			int userId = getUserId();
			session.setAttribute("userId", userId);

			// ユーザーIDからクリア状況のテーブルを参照し、参照したクリア状況を配列に格納
			HashMap<Integer, Integer> clearStatus = clearStatusQuery.selectByUserId(userId);

			// クリア状況の内容をセッションに保存
			session.setAttribute("clearStatus", clearStatus);

			// Top画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/top.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	
	// XXX 仮のユーザーID(ログイン処理実装後削除)
	private int getUserId() {
		/* DB接続 */
		Db db = new Db();
		Connection con = db.DbConnection();
		// 初期化
		String sql ="";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int userId = 0;
		
		try {
			// とりあえず仮でDB内のユーザーの取得する処理(実際はログイン時や会員登録時に取得しておく)
			sql = "SELECT * FROM users WHERE id = 1";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				userId = rs.getInt("id");
				
			}
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			try {
				if (con != null || ps != null || rs != null) {
					con.close();
					ps.close();
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
		return userId;
	}
}

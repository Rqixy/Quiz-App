package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

@WebServlet("/TopServlet")
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			/* セッションスコープの準備 */
			HttpSession session = request.getSession();
			/* DB接続 */
			Db db = new Db();
			Connection con = db.DbConnection();
			// 初期化
			String sql ="";
			PreparedStatement ps = null;
			ResultSet rs = null;
			ResultSetMetaData md = null;

			// とりあえず仮でDB内のユーザーの取得する処理(実際はログイン時や会員登録時に取得しておく)
			sql = "SELECT * FROM users WHERE id = 1";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			int user_id = 0;
			if (rs.next()) {
				user_id = rs.getInt("id");
				session.setAttribute("userId", user_id);
			}

			/* ユーザーIDからクリア状況のテーブルを参照し、参照したクリア状況を配列に格納しておく */
			// カラム名の配列の初期化
			String[] clearStatusColumnNames = new String[17];
			// クリア状況を格納するハッシュ配列の初期化
			HashMap<Integer, Integer> clearStatus = new HashMap<Integer, Integer>();

			sql = "SELECT * FROM clear_status WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			md = rs.getMetaData();

			// clear_statusテーブルのカラム数を取得する
			int columnCount = md.getColumnCount();
			// 配列にclear_statusテーブルのカラム名を格納する
			for (int i = 2; i <= columnCount; i++) {
				clearStatusColumnNames[i-2] = md.getColumnName(i);
			}

			/* クリア状況のハッシュ配列に目標番号とクリア状況の内容を格納する */
			if (rs.next()) {
				for (int i = 0; i < clearStatusColumnNames.length; i++) {
					clearStatus.put(i+1, rs.getInt(clearStatusColumnNames[i]));
				}
			}

			/* DBへの接続開放(クローズ処理) */
			con.close();
			ps.close();
			rs.close();

			/* クリア状況の内容をセッションに保存する */
			session.setAttribute("clearStatus", clearStatus);

			// 転送処理(フォワード)
			// Top画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/top.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.getMessage();
		}
	}
}

package servlet;

import java.io.IOException;
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

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			// ユーザーIDと目標番号の取得
			int userId = (int)session.getAttribute("userId");
			String goalNumber = (String)session.getAttribute("goalNumber");
			
			// クリア状況の更新処理
			// クリア状況DBの処理するクラスの初期化
			ClearStatusQuery clearStatusQuery = new ClearStatusQuery();
	
			// DB内のクリア状況と更新するカラム名を取得
			int clearStatusInDb = clearStatusQuery.selectOneClearStatus(userId, goalNumber);
			String clearStatusColumnName = clearStatusQuery.selectOneColumnName(userId, goalNumber);
			
			// 正答数を確認して、正答数に応じたクリア状況を更新
			float answerCount = (int)session.getAttribute("answerCount");
			float maxQuizCount = (int)session.getAttribute("maxQuizCount");
	
			// 正答率を求める
			float correctAnswerRate = answerCount / maxQuizCount;
			if (clearStatusInDb < 2 && correctAnswerRate == 1) {
				// 全問正解したことがなく、初めて全問正解なら数字を2に更新する
				clearStatusQuery.update(clearStatusColumnName, 2, userId);
			} else if (clearStatusInDb == 0 && correctAnswerRate >= 0.6f) {
				// もしまだ6割未満(数字が0)で、今回正答率が6割超えたら1に更新する
				clearStatusQuery.update(clearStatusColumnName, 1, userId);
			}
			
	        // セッションに目標番号のユニセフサイトのURLを保存
	        session.setAttribute("goalUrl", "https://www.unicef.or.jp/kodomo/sdgs/17goals/" + getUnicefUrl(goalNumber));

			// 結果画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/result.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			System.out.println("ServletException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
	}
	
	// 目標番号からユニセフのURLを取得する処理
	private String getUnicefUrl(String goalNumber) {
		// ハッシュ配列で目標番号とSDGsの17の目標のユニセフサイトのURLを格納
		HashMap<String, String> goals = new HashMap<String, String>();

		// ハッシュ配列に目標番号と17の目標のURLを格納
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

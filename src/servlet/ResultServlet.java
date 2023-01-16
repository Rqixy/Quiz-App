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

/**
 * 結果時にクリア状況の更新と結果画面表示の準備処理
 */
@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ログインしてなかったら、ログイン画面へリダイレクト
			if (request.getSession().getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/LoginServlet");
				return;
			}
			// ログイン済ならホーム画面へリダイレクト
			response.sendRedirect(request.getContextPath() + "/HomeServlet");
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}

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
			int clearStatusInDb = clearStatusQuery.selectOne(userId, goalNumber);
			
			// 正答数を確認して、正答数に応じたクリア状況を更新
			float answerCount = (int)session.getAttribute("answerCount");
			float maxQuizCount = (int)session.getAttribute("maxQuizCount");
			// 正答率を求める
			float correctAnswerRate = answerCount / maxQuizCount;
			// クリアの正答率
			float allClearRate = 1f;	// 100%クリア
			float clearRate = 0.6f;		// 60%クリア
			
			// 1度も全問正解したことがなく、初めて全問正解なら数字を2に更新する
			int allClear = 2;
			if (clearStatusInDb < allClear && correctAnswerRate == allClearRate) {
				clearStatusQuery.update(goalNumber, allClear, userId);
			}
			// もし1度もクリアしたことがなく(数字が0)で、初めて正答率が6割超えたら1に更新する
			int notClear = 0;
			if (clearStatusInDb == notClear && correctAnswerRate >= clearRate) {
				int clear = 1;
				clearStatusQuery.update(goalNumber, clear, userId);
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

package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ResultServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			// セッションから正解数を取得
			int answerCount = (int)session.getAttribute("answercount");

			// 一言画像のURLを初期化
			String resultCommentImage = "";

			// 正解数によって出力する一言画像を変更しセッションに保存する
			if(answerCount <= 0) {
				resultCommentImage = "bubble_incorrect.png";
			} else if(answerCount >  0 && answerCount <= 5) {
				resultCommentImage = "bubble_good_correct.png";
			} else if(answerCount > 5 && answerCount <= 9) {
				resultCommentImage = "bubble_best_correct.png";
			} else {
				resultCommentImage = "bubble_all_correct.png";
			}

			// セッションに一言画像のURLを保存する
			session.setAttribute("resultcommentimage", resultCommentImage);

			// 転送処理(フォワード)
			// 結果画面へ表示
			ServletContext s = request.getServletContext();
			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/result.jsp");
			rd.forward(request, response);
		} catch(Exception e) {
			// 例外処理
			e.getStackTrace();
		}
	}

}
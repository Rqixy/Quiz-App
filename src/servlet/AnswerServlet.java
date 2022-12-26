package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.AnswerCheck;

@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 回答を取得する
			//. JSON テキストを全部取り出す
		    BufferedReader br = new BufferedReader( request.getReader() );
		    String jsonText = br.readLine();
		    jsonText = URLDecoder.decode(jsonText, "UTF-8");

		    ObjectMapper mapper = new ObjectMapper();
		    AnswerCheck answerCheckObj = mapper.readValue(jsonText, AnswerCheck.class);

		    String selectedAnswer = answerCheckObj.getSelectedAnswer();
//		    System.out.println(selectedAnswer);

			// quiz.jspにanswerCheckのデータをjson形式で送信する
			/* セッションスコープの準備 */
			HttpSession session = request.getSession();

			// 現在の問題番号を取得する
			int currentQuizCount = (int)session.getAttribute("currentQuizCount");
			//
			int maxQuizCount = (int)session.getAttribute("maxQuizCount");

			if (currentQuizCount >= maxQuizCount) {
				answerCheckObj.setFinished(true);
			}

			// 問題の答えを取得
			String quizAnswer = (String)session.getAttribute("quizAnswer");
			// ユーザーが選択した答えを取得
			// 正解数のセッションを取得
			int answerCount = (int)session.getAttribute("answerCount");

			/* 問題の答えと選択した答えが一致しているか判定 */
			if(quizAnswer.equals(selectedAnswer)) {
				// 正答数を+1してセッションに再保存する
				answerCount++;
				answerCheckObj.setCheckAnswer(true);
				session.setAttribute("answerCount", answerCount);
			}

			String answerCheckJson = mapper.writeValueAsString(answerCheckObj);
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(answerCheckJson);
			pw.close();
		} catch(Exception e) {
			// 例外処理
			e.getStackTrace();
		}
	}
}

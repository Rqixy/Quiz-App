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

import model.AjaxAnswerInfo;

@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			// 非同期で送られてきたJSONファイルから、ユーザーが選択した答えを取得
		    BufferedReader br = new BufferedReader(request.getReader());
		    ObjectMapper ob = new ObjectMapper();
		    
		    String selectedAnswerJsonText = br.readLine();
		    selectedAnswerJsonText = URLDecoder.decode(selectedAnswerJsonText, "UTF-8");
		    
		    // JSONからオブジェクトに変換する
		    AjaxAnswerInfo ajaxAnswerInfo = ob.readValue(selectedAnswerJsonText, AjaxAnswerInfo.class);
			
			// 現在の問題番号とクイズの最大問題数を取得する
			int currentQuizCount = (int)session.getAttribute("currentQuizCount");
			int maxQuizCount = (int)session.getAttribute("maxQuizCount");
			// 出力した問題数が最大値に達したら、finishedをtrueに変更する
			if (currentQuizCount >= maxQuizCount) {
				ajaxAnswerInfo.setFinished(true);
			}

			// 問題の答えと正解数を取得
			String quizAnswer = (String)session.getAttribute("quizAnswer");
			int answerCount = (int)session.getAttribute("answerCount");

			// 問題の答えと選択した答えが一致しているか判定
			// 回答を取得
			String selectedAnswer = ajaxAnswerInfo.getSelectedAnswer();
			if(quizAnswer.equals(selectedAnswer)) {
				// checkedAnswerをtrueにする
				ajaxAnswerInfo.setCheckedAnswer(true);
				// 正答数を+1してセッションに再保存
				answerCount++;
				session.setAttribute("answerCount", answerCount);
			}
			
			// 回答結果のオブジェクトをJSON形式に書き直して、表に返す
			String ajaxAnswerJson = ob.writeValueAsString(ajaxAnswerInfo);
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(ajaxAnswerJson);
			pw.close();
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
}

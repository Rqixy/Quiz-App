package servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;

import json.Json;

@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			Json json = new Json();
			JsonNode selectedAnswerJson = json.jsonByHttpServletRequest(request);
		    // 選択した答えの取得
		    String selectedAnswer = selectedAnswerJson.get("selectedAnswer").textValue();
		    
		    // 答えの判定と全問終了した判定のハッシュ配列
		    HashMap<String, Boolean> checked = new HashMap<String, Boolean>();
	    	checked.put("isCorrect", false);
	    	checked.put("isFinished", false);

			// 問題の答えと正解数を取得
			String quizAnswer = (String)session.getAttribute("quizAnswer");
			int answerCount = (int)session.getAttribute("answerCount");
			// 問題の答えと選択した答えが一致しているか判定
			if(quizAnswer.equals(selectedAnswer)) {
				// isCorrectをtrueにする
				checked.put("isCorrect", true);
				// 正答数を+1してセッションに再保存
				answerCount++;
				session.setAttribute("answerCount", answerCount);
			}
			
			// 現在の問題番号とクイズの最大問題数を取得する
			int currentQuizCount = (int)session.getAttribute("currentQuizCount");
			int maxQuizCount = (int)session.getAttribute("maxQuizCount");
			// 出力した問題数が最大値に達したら、isFinishedをtrueに変更する
			if (currentQuizCount >= maxQuizCount) {
				checked.put("isFinished", true);
			}
			
			json.checkedMapToStrJsonAndResponse(checked, response);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
}

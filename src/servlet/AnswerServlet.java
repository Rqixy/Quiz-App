package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import json.Json;
import model.Login;
import model.Quiz;
import model.QuizInfoBean;
import model.ScreenTransition;

/**
 * 選択した答えが合っているかどうか確認する処理
 */
@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ログインしてなかったら、ログイン画面へリダイレクト
			if (Login.loggedInUser(request)) {
				ScreenTransition.redirectLogin(request, response);
				return;
			}
			// ログイン済ならホーム画面へリダイレクト
			ScreenTransition.redirectHome(request, response);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession();
			
			Json json = new Json();
			JsonNode selectedAnswerJson = json.jsonByServletRequest(request);
		    // 選択した答えの取得
		    String selectedAnswer = selectedAnswerJson.get("selectedAnswer").textValue();
		    
		    // 答えの判定と全問終了した判定のハッシュ配列
		    HashMap<String, Boolean> flag = new HashMap<String, Boolean>();
		    flag.put("isCorrect", false);
		    flag.put("isFinished", false);

			// 問題の答えと正解数を取得
		    Quiz quiz = (Quiz)session.getAttribute("quiz");
		    QuizInfoBean quizInfoBean = quiz.quizInfoBean();
			String quizAnswer = quizInfoBean.answer();
			
			// 問題の答えと選択した答えが一致しているか判定
			if(quizAnswer.equals(selectedAnswer)) {
				// isCorrectをtrueにする
				flag.put("isCorrect", true);
				// 正答数を+1
				quiz.addAnswerCount();
			}
			
			// 出力した問題数が最大値に達したら、isFinishedをtrueに変更する
			if (quiz.currentQuizCount() >= quiz.maxQuizCount()) {
				flag.put("isFinished", true);
			}
			
			// フロントに答えと全問終了かどうかを返す
			String strFlag = json.flagMapToString(flag);
			json.ResponseStringJson(response, strFlag);
		} catch (JsonMappingException e) {
			System.out.println("JsonMappingException : " + e.getMessage());
		} catch (JsonProcessingException e) {
			System.out.println("JsonProcessingException : " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
}

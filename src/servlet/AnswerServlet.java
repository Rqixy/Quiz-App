package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
import model.Answer;
import model.Login;
import model.Quiz;
import transition.ScreenTransition;

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
			
			Quiz quiz = (Quiz)session.getAttribute("quiz");
			JsonNode selectedAnswerJson = Json.jsonByServletRequest(request);
		    
			// フロントに答えと全問終了かどうかを返す
			String strFlag = Answer.correctAndFinishedFlagToText(quiz, selectedAnswerJson);
			Json.ResponseStringJson(response, strFlag);
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

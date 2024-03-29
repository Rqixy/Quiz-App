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

import libs.json.Json;
import libs.model.Answer;
import libs.model.Quiz;
import libs.transition.Redirect;

/**
 * 選択した答えが合っているかどうか確認する処理
 */
@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ホーム画面へリダイレクト
			Redirect.home(request, response);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// セッションスコープの準備
			HttpSession session = request.getSession(false);
			if (session == null) {
				Redirect.login(request, response);
				return;
			}
			
			Quiz quiz = (Quiz)session.getAttribute("quiz");
			JsonNode selectedAnswerJson = Json.jsonByRequest(request);
		    
			// フロントに答えと全問終了かどうかの結果を返す
			String answerFlag = Answer.flag(quiz, selectedAnswerJson);
			Json.ResponseStringJson(response, answerFlag);
		} catch (JsonMappingException e) {
			System.out.println("JsonMappingException : " + e.getMessage());
			Redirect.home(request, response);
		} catch (JsonProcessingException e) {
			System.out.println("JsonProcessingException : " + e.getMessage());
			Redirect.home(request, response);
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
			Redirect.home(request, response);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
			Redirect.home(request, response);
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			Redirect.home(request, response);
		}
	}
}

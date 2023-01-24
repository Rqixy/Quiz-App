package model;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import json.Json;

public class Answer {
	private static HashMap<String, Boolean> flag;
	private final String quizAnswer;
	private final String selectedAnswer;
	
	private Answer(final String quizAnswer, final String selectedAnswer) {
		Answer.flag = new HashMap<String, Boolean>();
		this.quizAnswer = quizAnswer;
		this.selectedAnswer = selectedAnswer;
	}
	
	/**
	 * 答えの判定と全問終了した判定を行い、その結果を返す処理
	 * @param quiz
	 * @param selectedAnswerJson
	 * @return Json.flagMapToString(flag)
	 * @throws JsonProcessingException
	 */
	public static String correctAndFinishedFlagToText(final Quiz quiz, final JsonNode selectedAnswerJson) throws JsonProcessingException {
	    // 問題の答えと選択した答えを取得
		final String quizAnswer = quiz.quizInfo().answer();
		final String selectedAnswer = selectedAnswerJson.get("selectedAnswer").textValue();
		final Answer answer = new Answer(quizAnswer, selectedAnswer);
	    
	    // 答えの判定と全問終了した判定のハッシュ配列
	    flag.put("isCorrect", false);
	    flag.put("isFinished", false);
		// 問題の答えと選択した答えが一致しているか判定
		if(answer.quizAnswer.equals(answer.selectedAnswer)) {
			// isCorrectをtrueにする
			flag.put("isCorrect", true);
			// 正答数を+1
			quiz.addAnswerCount();
		}
		
		// 出力した問題数が最大値に達したら、isFinishedをtrueに変更する
		if (quiz.currentQuizCount() >= quiz.maxQuizCount()) {
			flag.put("isFinished", true);
		}
		
		return Json.flagMapToString(flag);
	}
}

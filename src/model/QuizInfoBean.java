package model;
/*
 * クイズ情報管理クラス
 */
public class QuizInfoBean {
	private String quiz;		// 問題
	private String answer;		// 回答

	public QuizInfoBean(String quiz, String answer) {
		this.quiz = quiz;
		this.answer = answer;
	}

	public String quiz() {
		return quiz;
	}

	public String answer() {
		return answer;
	}
}

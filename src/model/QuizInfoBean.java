package model;
/*
 * クイズ情報管理クラス
 */
public class QuizInfoBean {

	// フィールド
	private int quiz_id;		// 問題番号
	private String quiz;		// 問題
	private String answer;		// 回答
	private String commentary;	// 解説

	// コンストラクタ
	public QuizInfoBean() {
		this.quiz_id = 0;
		this.quiz = "";
		this.answer = "";
		this.commentary = "";
	}

	// アクセサメソッド
	public int getQuiz_id() {
		return quiz_id;
	}

	public void setQuiz_id(int quiz_id) {
		this.quiz_id = quiz_id;
	}

	public String getQuiz() {
		return quiz;
	}

	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
}

package model;
/*
 * 回答一覧を管理するクラス
 */
public class AnswersEntity {

	// フィールド
	private int answers_id;		// 回答番号
	private String correct;		// 正解
	private String incorrect_1;	// 誤答1
	private String incorrect_2;	// 誤答2
	private String incorrect_3;	// 誤答3

	// コンストラクタ
	public AnswersEntity() {
		this.answers_id = 0;
		this.correct = "";
		this.incorrect_1 = "";
		this.incorrect_2 = "";
		this.incorrect_3 = "";
	}

	// アクセサメソッド
	public int getAnswers_id() {
		return answers_id;
	}

	public void setAnswers_id(int answers_id) {
		this.answers_id = answers_id;
	}

	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public String getIncorrect_1() {
		return incorrect_1;
	}

	public void setIncorrect_1(String incorrect_1) {
		this.incorrect_1 = incorrect_1;
	}

	public String getIncorrect_2() {
		return incorrect_2;
	}

	public void setIncorrect_2(String incorrect_2) {
		this.incorrect_2 = incorrect_2;
	}

	public String getIncorrect_3() {
		return incorrect_3;
	}

	public void setIncorrect_3(String incorrect_3) {
		this.incorrect_3 = incorrect_3;
	}
}

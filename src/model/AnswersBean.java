package model;
/*
 * 回答一覧を管理するクラス
 */
public class AnswersBean {

	// フィールド
	private String correct;		// 正解
	private String incorrect1;	// 誤答1
	private String incorrect2;	// 誤答2
	private String incorrect3;	// 誤答3

	// コンストラクタ
	public AnswersBean(String correct, String incorrect1, String incorrect2, String incorrect3) {
		this.correct = correct;
		this.incorrect1 = incorrect1;
		this.incorrect2 = incorrect2;
		this.incorrect3 = incorrect3;
	}

	// アクセサメソッド
	public String correct() {
		return correct;
	}

	public String incorrect1() {
		return incorrect1;
	}

	public String incorrect2() {
		return incorrect2;
	}

	public String incorrect3() {
		return incorrect3;
	}
}

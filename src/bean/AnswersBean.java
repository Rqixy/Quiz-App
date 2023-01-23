package bean;

/*
 * 回答一覧管理クラス
 */
public class AnswersBean {
	private final String correct;		// 正解
	private final String incorrect1;	// 誤答1
	private final String incorrect2;	// 誤答2
	private final String incorrect3;	// 誤答3

	public AnswersBean(final String correct, final String incorrect1, final String incorrect2, final String incorrect3) {
		this.correct = correct;
		this.incorrect1 = incorrect1;
		this.incorrect2 = incorrect2;
		this.incorrect3 = incorrect3;
	}

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

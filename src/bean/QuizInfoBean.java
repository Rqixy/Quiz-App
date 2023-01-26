package bean;

/*
 * クイズ情報管理クラス
 */
public class QuizInfoBean {
	private final String quiz;		// 問題
	private final String answer;	// 回答
	
	public QuizInfoBean(final String quiz, final String answer) {
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

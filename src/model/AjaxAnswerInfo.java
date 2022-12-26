package model;
/**
 * ユーザーが回答した情報や正解かどうかなどの情報を非同期で送るためのオブジェクト
 */

public class AjaxAnswerInfo {
	private String selectedAnswer;	// ユーザーが選択した答え
	private boolean checkedAnswer;	// 答えが正解かどうか
	private boolean finished;		// 全問終わったかどうか

	public AjaxAnswerInfo() {
		selectedAnswer = "";
		checkedAnswer = false;
		finished = false;
	}

	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	public boolean isCheckedAnswer() {
		return checkedAnswer;
	}

	public void setCheckedAnswer(boolean checkedAnswer) {
		this.checkedAnswer = checkedAnswer;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}

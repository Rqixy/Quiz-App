package model;

public class AnswerCheck {
	private String selectedAnswer;
	private boolean checkAnswer;
	private boolean finished;

	public AnswerCheck() {
		selectedAnswer = "";
		checkAnswer = false;
		finished = false;
	}

	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	public boolean isCheckAnswer() {
		return checkAnswer;
	}

	public void setCheckAnswer(boolean checkAnswer) {
		this.checkAnswer = checkAnswer;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}

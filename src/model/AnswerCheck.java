package model;

public class AnswerCheck {
	private String selectedAnswer;
	private boolean checkAnswer;
	
	public AnswerCheck() {
		selectedAnswer = "";
		checkAnswer = false;
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
	
	
}

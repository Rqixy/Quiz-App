package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.AnswersBean;
import bean.QuizInfoBean;
import db.AnswersDao;
import db.QuizInfoDao;

public class Quiz {
	private ArrayList<QuizInfoBean> quizInfoList;
	private ArrayList<AnswersBean> answerList;
	private QuizInfoBean quizInfo;
	private AnswersBean answers;
	private final int goalNumber;
	private final int maxQuizCount;
	private int askToQuiz;
	private int currentQuizCount;
	private int answerCount;
	
	private Quiz(ArrayList<QuizInfoBean> quizInfoList, ArrayList<AnswersBean> answerList, 
				QuizInfoBean quizInfo, AnswersBean answers, 
				final int goalNumber, final int maxQuizCount, int askToQuiz) {
		this.quizInfoList = quizInfoList;
		this.answerList = answerList;
		this.quizInfo = quizInfo;
		this.answers = answers;
		this.goalNumber = goalNumber;
		this.maxQuizCount = maxQuizCount;
		this.askToQuiz = askToQuiz;
		this.currentQuizCount = 1;
		this.answerCount = 0;
	}
	
	/**
	 * 問題を準備する処理
	 * @param requestGoalNumber
	 * @return new Quiz(quizInfoList, answerList, quizInfo, answers, goalNumber, quizInfoList.size(), askToQuiz)
	 * @throws Exception
	 */
	public static Quiz prepareQuiz(final String requestGoalNumber) throws Exception {
		Pattern goalNumberPattern = Pattern.compile("[0-9]{1,2}");
		Matcher matchGoalNumber = goalNumberPattern.matcher(requestGoalNumber);
		
		if(!matchGoalNumber.find()) {
			throw new Exception("SDGsの目標番号を入力してください");
		}
		
		int goalNumber = Integer.parseInt(requestGoalNumber);
		
		ArrayList<QuizInfoBean> quizInfoList = new ArrayList<QuizInfoBean>();
		ArrayList<AnswersBean> answerList = new ArrayList<AnswersBean>();
		
		try {
			QuizInfoDao quizDao = new QuizInfoDao();
			AnswersDao answerDao = new AnswersDao();
		
			quizInfoList = quizDao.quizInfoList(goalNumber);
			answerList = answerDao.answerList(goalNumber);
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		
		Random random = new Random();
		int askToQuiz = random.nextInt(quizInfoList.size());
		
		QuizInfoBean quizInfo = quizInfoList.get(askToQuiz);
		AnswersBean answers = answerList.get(askToQuiz);
		
		return new Quiz(quizInfoList, answerList, quizInfo, answers, goalNumber, quizInfoList.size(), askToQuiz);
	}
	
	/**
	 * 次の問題へ準備する処理
	 */
	public void nextQuiz() {
		quizInfoList.remove(askToQuiz);
		answerList.remove(askToQuiz);
		
		Random random = new Random();
		askToQuiz = random.nextInt(quizInfoList.size());
		
		quizInfo = quizInfoList.get(askToQuiz);
		answers = answerList.get(askToQuiz);
		
		currentQuizCount++;
	}
	
	/**
	 * カウントをプラスする処理
	 */
	protected void addAnswerCount() {
		answerCount++;
	}
	
	public QuizInfoBean quizInfo() {
		return quizInfo;
	}
	
	public AnswersBean answers() {
		return answers;
	}
	
	public int goalNumber() {
		return goalNumber;
	}
	
	public int maxQuizCount() {
		return maxQuizCount;
	}
	
	public int askToQuiz() {
		return askToQuiz;
	}
	
	public int currentQuizCount() {
		return currentQuizCount;
	}
	
	public int answerCount() {
		return answerCount;
	}
	
	
}

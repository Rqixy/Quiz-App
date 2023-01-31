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
import exception.NoMatchGoalNumberException;

public class Quiz {
	private static final Random RANDOM = new Random();
	
	private ArrayList<QuizInfoBean> quizInfoList;
	private ArrayList<AnswersBean> answerList;
	private QuizInfoBean quizInfo;
	private AnswersBean answers;
	private final int goalNumber;
	private final int maxQuizCount;
	private int selectQuiz;
	private int currentQuizCount;
	private int answerCount;
	
	private Quiz(ArrayList<QuizInfoBean> quizInfoList, ArrayList<AnswersBean> answerList, 
				QuizInfoBean quizInfo, AnswersBean answers, final int goalNumber, final int maxQuizCount, int selectQuiz) {
		this.quizInfoList = quizInfoList;
		this.answerList = answerList;
		this.quizInfo = quizInfo;
		this.answers = answers;
		this.goalNumber = goalNumber;
		this.maxQuizCount = maxQuizCount;
		this.selectQuiz = selectQuiz;
		this.currentQuizCount = 1;
		this.answerCount = 0;
	}
	
	/**
	 * 問題を準備する処理
	 * @param requestGoalNumber
	 * @return new Quiz(quizInfoList, answerList, quizInfo, answers, goalNumber, quizInfoList.size(), askToQuiz)
	 * @throws Exception
	 */
	public static Quiz prepareQuiz(final String requestGoalNumber) throws NoMatchGoalNumberException {
		// 目標番号のみを取得する正規表現
		Pattern goalNumberPattern = Pattern.compile("^[1-9]$|^1[0-7]$");
		Matcher matchGoalNumber = goalNumberPattern.matcher(requestGoalNumber);

		if(!matchGoalNumber.find()) {
			throw new NoMatchGoalNumberException("SDGsの目標番号を入力してください");
		}
		
		int goalNumber = Integer.parseInt(requestGoalNumber);
		
		ArrayList<QuizInfoBean> quizInfoList = new ArrayList<QuizInfoBean>();
		ArrayList<AnswersBean> answerList = new ArrayList<AnswersBean>();
		
		try {
			quizInfoList = QuizInfoDao.quizInfoList(goalNumber);
			answerList = AnswersDao.answerList(goalNumber);
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		
		int maxQuizCount = quizInfoList.size();
		int selectQuiz = RANDOM.nextInt(quizInfoList.size());
		
		QuizInfoBean quizInfo = quizInfoList.get(selectQuiz);
		AnswersBean answers = answerList.get(selectQuiz);
		
		return new Quiz(quizInfoList, answerList, quizInfo, answers, goalNumber, maxQuizCount, selectQuiz);
	}
	
	/**
	 * 次の問題へ準備する処理
	 */
	public void nextQuiz() {
		quizInfoList.remove(selectQuiz);
		answerList.remove(selectQuiz);
		
		selectQuiz = RANDOM.nextInt(quizInfoList.size());
		
		quizInfo = quizInfoList.get(selectQuiz);
		answers = answerList.get(selectQuiz);
		
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
	
	public int currentQuizCount() {
		return currentQuizCount;
	}
	
	public int answerCount() {
		return answerCount;
	}
}

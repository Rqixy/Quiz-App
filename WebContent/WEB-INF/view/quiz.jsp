<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Random" %>
<%@ page import="model.QuizInfoBean" %>
<%@ page import="model.AnswersBean" %>
<%
	// 現在の問題番号を取得する
	int currentQuizCount = (int)session.getAttribute("currentQuizCount");

	// 問題と回答一覧のリストを取得する
	ArrayList<QuizInfoBean> quizList = (ArrayList<QuizInfoBean>)session.getAttribute("quizList");
	ArrayList<AnswersBean> answerList = (ArrayList<AnswersBean>)session.getAttribute("answerList");

	// 問題をランダムに表示する番号
	int quizNumber = (int)session.getAttribute("quizNumber");
	// 問題を取得する
	QuizInfoBean qe = quizList.get(quizNumber);
	AnswersBean ae = answerList.get(quizNumber);

	// 出力された問題をリストから削除するため、問題番号をセッションに保存する
	session.setAttribute("quizNumber", quizNumber);
	// 答えは、AnswerServletでを使うため、セッションに保存しておく
	session.setAttribute("quizAnswer", qe.getAnswer());
%>
<%@ include file="./layouts/header.jsp" %>
<div class="container">
    <div class="wrapper">
        <header>
            <p class="question-num"><%=currentQuizCount %>問目</p>
            <p><a href="TopServlet" class="back">TOPに戻る</a></p>
        </header>
        <main id="question-main">
            <h2 id="question-1">「？？」に入るものを選びなさい</h2>
            <p id="question-2"><%=qe.getQuiz() %></p>
            <div id="answers">
	            <button name="answer" type="submit" value="<%=ae.getCorrect() %>"><%=ae.getCorrect() %></button>
	            <button name="answer" type="submit" value="<%=ae.getIncorrect_1() %>"><%=ae.getIncorrect_1() %></button>
	            <button name="answer" type="submit" value="<%=ae.getIncorrect_2() %>"><%=ae.getIncorrect_2() %></button>
	            <button name="answer" type="submit" value="<%=ae.getIncorrect_3() %>"><%=ae.getIncorrect_3() %></button>
            </div>
        </main>
    </div>
</div>
<%@ include file="./layouts/footer.jsp" %>
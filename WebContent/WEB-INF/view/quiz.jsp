<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Random" %>
<%@ page import="model.QuizInfoEntity" %>
<%@ page import="model.AnswersEntity" %>
<%
	// 現在の問題番号を取得する
	int currentCount = (int)session.getAttribute("currentcount");

	// 問題と回答一覧のリストを取得する
	ArrayList<QuizInfoEntity> quizList = (ArrayList<QuizInfoEntity>)session.getAttribute("quizlist");
	ArrayList<AnswersEntity> answers = (ArrayList<AnswersEntity>)session.getAttribute("answers");

	// 問題をランダムに表示する番号
	Random rand = new Random();
	int quizNum = rand.nextInt(quizList.size());

	QuizInfoEntity qe = quizList.get(quizNum);
	AnswersEntity ae = answers.get(quizNum);
/*
	System.out.println(qe.getQuiz());
	System.out.println(ae.getCorrect()); */

	// 出力された問題をリストから削除するため、
	// 問題番号をセッションに保存する
	session.setAttribute("quiznum", quizNum);

	session.setAttribute("answer", qe.getAnswer());
	session.setAttribute("commentary", qe.getCommentary());
%>
<%@ include file="./layouts/header.jsp" %>
<div class="container">
    <div class="wrapper">
        <header>
            <p class="question-num"><%=currentCount %>問目</p>
            <p><a href="TopServlet" class="back">TOPに戻る</a></p>
        </header>
        <main id="question-main">
            <h2 id="question-1">「？？」に入るものを選びなさい</h2>
            <p id="question-2"><%=qe.getQuiz() %></p>
            <form method="post" action="AnswerServlet" id="answers">
                <button name="answer" type="submit" value="<%=ae.getCorrect() %>"><%=ae.getCorrect() %></button>
                <button name="answer" type="submit" value="<%=ae.getIncorrect_1() %>"><%=ae.getIncorrect_1() %></button>
                <button name="answer" type="submit" value="<%=ae.getIncorrect_2() %>"><%=ae.getIncorrect_2() %></button>
                <button name="answer" type="submit" value="<%=ae.getIncorrect_3() %>"><%=ae.getIncorrect_3() %></button>
            </form>
        </main>
    </div>
</div>
<%@ include file="./layouts/footer.jsp" %>
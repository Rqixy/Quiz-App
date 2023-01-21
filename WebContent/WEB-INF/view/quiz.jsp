<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.QuizInfoBean" %>
<%@ page import="model.AnswersBean" %>
<%
	// 目標番号を取得
	int goalNumber = (int)session.getAttribute("goalNumber");
	// 現在の問題番号を取得
	int currentQuizCount = (int)session.getAttribute("currentQuizCount");
	// 問題と回答一覧のリストを取得
	ArrayList<QuizInfoBean> quizList = (ArrayList<QuizInfoBean>)session.getAttribute("quizList");
	ArrayList<AnswersBean> answerList = (ArrayList<AnswersBean>)session.getAttribute("answerList");
	// 問題をランダムに表示する番号
	int quizNumber = (int)session.getAttribute("quizNumber");
	// 問題を取得
	QuizInfoBean quiz = quizList.get(quizNumber);
	AnswersBean answer = answerList.get(quizNumber);

	// クイズ番号と答えをセッションに保存
	session.setAttribute("quizNumber", quizNumber);
	session.setAttribute("quizAnswer", quiz.answer());
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="クエストなう" />
	<jsp:param name="header">
		<jsp:attribute name="value">
			<header id="quiz-header">
	            <p id="battle-num">BATTLE <%=currentQuizCount %></p>
	            <p class="back"><a href="HomeServlet">あきらめる</a></p>
	        </header>
		</jsp:attribute>
	</jsp:param>
	
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main id="question-main">
	            <div id="field">
	                <img id="enemy" src="./img/enemy/enemy_<%=goalNumber %>.png" alt="">
	            </div>
	            
	            <div id="quiz">
	            	<div id="question-box">
	            		<span id="question-title">Question</span>
	            		<p id="question"><%=quiz.quiz() %></p>
	            	</div>
		            <div id="answers">
			            <button name="answer" type="submit"><%=answer.correct() %></button>
			            <button name="answer" type="submit"><%=answer.incorrect1() %></button>
			            <button name="answer" type="submit"><%=answer.incorrect2() %></button>
			            <button name="answer" type="submit"><%=answer.incorrect3() %></button>
		            </div>
	            </div>
	        </main>
		</jsp:attribute>
	</jsp:param>
	
	<jsp:param name="js">
		<jsp:attribute name="value">
			<script src="<%=request.getContextPath() %>/js/shuffle.js"></script>
			<script src="<%=request.getContextPath() %>/js/answer.js"></script>
		</jsp:attribute>
	</jsp:param>
</jsp:include>
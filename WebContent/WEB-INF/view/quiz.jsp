<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="libs.model.Quiz" %>
<%@ page import="bean.QuizInfoBean" %>
<%@ page import="bean.AnswersBean" %>
<%
	String csrfToken = (String)session.getAttribute("csrfToken");
	Quiz quiz = (Quiz)session.getAttribute("quiz");
	QuizInfoBean quizInfo = quiz.quizInfo();
	AnswersBean answers = quiz.answers();
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="クエストなう" />
	<jsp:param name="header">
		<jsp:attribute name="value">
			<header id="quiz-header">
	            <p id="battle-num">BATTLE <%=quiz.currentQuizCount() %></p>
	            <p class="back"><a href="home" class="button-escape">あきらめる</a></p>
	        </header>
		</jsp:attribute>
	</jsp:param>
	
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main id="question-main">
	            <div id="field">
	                <img id="enemy" src="./img/enemy/enemy_<%=quiz.goalNumber() %>.png" alt="">
	            </div>
	            
	            <div id="quiz">
	            	<div id="question-box">
	            		<span id="question-title">Question</span>
	            		<p id="question"><%=quizInfo.quiz() %></p>
	            	</div>
	            	<input type="hidden" name="csrf_token" value="<%=csrfToken %>">
		            <div id="answers">
			            <button name="answer" type="submit"><%=answers.correct() %></button>
			            <button name="answer" type="submit"><%=answers.incorrect1() %></button>
			            <button name="answer" type="submit"><%=answers.incorrect2() %></button>
			            <button name="answer" type="submit"><%=answers.incorrect3() %></button>
		            </div>
	            </div>
	        </main>
		</jsp:attribute>
	</jsp:param>
	
	<jsp:param name="js">
		<jsp:attribute name="value">
			<script src="js/shuffle.js"></script>
			<script src="js/answer.js"></script>
		</jsp:attribute>
	</jsp:param>
</jsp:include>
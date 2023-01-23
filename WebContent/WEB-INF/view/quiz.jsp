<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Quiz" %>
<%@ page import="model.QuizInfoBean" %>
<%@ page import="model.AnswersBean" %>
<%
	Quiz quiz = (Quiz)session.getAttribute("quiz");
	QuizInfoBean quizInfoBean = quiz.quizInfoBean();
	AnswersBean answersBean = quiz.answersBean();
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="クエストなう" />
	<jsp:param name="header">
		<jsp:attribute name="value">
			<header id="quiz-header">
	            <p id="battle-num">BATTLE <%=quiz.currentQuizCount() %></p>
	            <p class="back"><a href="HomeServlet">あきらめる</a></p>
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
	            		<p id="question"><%=quizInfoBean.quiz() %></p>
	            	</div>
		            <div id="answers">
			            <button name="answer" type="submit"><%=answersBean.correct() %></button>
			            <button name="answer" type="submit"><%=answersBean.incorrect1() %></button>
			            <button name="answer" type="submit"><%=answersBean.incorrect2() %></button>
			            <button name="answer" type="submit"><%=answersBean.incorrect3() %></button>
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
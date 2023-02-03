<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="libs.model.Quiz" %>
<%
	Quiz quiz = (Quiz)session.getAttribute("quiz");
	boolean isClear = (boolean)session.getAttribute("isClear");
	String goalUrl = (String)session.getAttribute("goalUrl");
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="クエスト結果" />
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main id="result-main">
				<% if(isClear) { %>
		            <h1 id="clear-message">CLEAR</h1>
		            
		            <div class="effect clear-effect">
		            	<img class="clear-img" src="./img/small_star.png">
		            	<div class="result">
				            <% if(quiz.answerCount() == quiz.maxQuizCount()) { %>
				            	<h2>全問正解！！</h2>
				            <% } else { %>
				            	<h2><%=quiz.answerCount() %>/<%=quiz.maxQuizCount() %>問正解</h2>
				            <% } %>
			            </div>
			            <img class="clear-img" src="./img/small_star.png">
		            </div>
	            <% } else { %>
	            	<h1 id="fail-message">FAIL</h1>
	            	
		            <div class="effect fail-effect">
		            	<img class="fail-img" src="./img/skull.png">
		            	<div class="result">
				            <h2><%=quiz.answerCount() %>/<%=quiz.maxQuizCount() %>問正解</h2>
			            </div>
			            <img class="fail-img" src="./img/skull.png">
		            </div>
	            <% } %>
	            
	            <div id="back-home">
	                <a href="home" class="btn btn-flat button-stage"><span>ホームに戻る</span></a>
	            </div>
	            
				<a id="unicef-url" class="button-se" href="<%=goalUrl %>" target="_blank">今回の目標について学ぶ</a>
	        </main>
		</jsp:attribute>
	</jsp:param>
</jsp:include>
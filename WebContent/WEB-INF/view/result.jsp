<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String resultCommentImage = (String)session.getAttribute("resultcommentimage");
	int answerCount = (int)session.getAttribute("answercount");
%>
<%@ include file="./unit/header.jsp" %>
<div class="container">
    <div class="wrapper">
        <main id="result-main">
           	<img src="<%=request.getContextPath() %>/img/<%=resultCommentImage %>" alt="" id="bubble">
            <% if(answerCount == 10) { %>
            	<h2 id="result">全問正解！！</h2>
            <% } else { %>
            	<h2 id="result"><%=answerCount %>/10問正解</h2>
            <% } %>
            <div id="menu">
                <a href="QuizServlet">もう一度</a>
                <a href="TopServlet">TOPに戻る</a>
            </div>
        </main>
    </div>
</div>
<%@ include file="./unit/footer.jsp" %>
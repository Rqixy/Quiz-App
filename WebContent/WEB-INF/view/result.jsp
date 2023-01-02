<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int answerCount = (int)session.getAttribute("answerCount");
	int maxQuizCount = (int)session.getAttribute("maxQuizCount");
	String goalUrl = (String)session.getAttribute("goalUrl");
%>
<%@ include file="./layouts/header.jsp" %>
<div class="container">
    <div class="wrapper">
        <main id="result-main">
           	<% if(answerCount == maxQuizCount) { %>
            	<h2 id="result">全問正解！！</h2>
            <% } else { %>
            	<h2 id="result"><%=answerCount %>/<%=maxQuizCount %>問正解</h2>
            <% } %>
            <div id="menu">
                <a href="HomeServlet">ホームに戻る</a>
            </div>

            <a href="<%=goalUrl %>" target="_blank">今回の目標について学ぶ</a>
        </main>
    </div>
</div>
<%@ include file="./layouts/footer.jsp" %>
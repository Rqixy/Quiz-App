<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./unit/header.jsp" %>
<div id="top-container">
    <img src="<%=request.getContextPath() %>/img/SDGsQuiz.png" alt="タイトル" id="img">
    <form method="get" action="QuizServlet" id="start">
        <button type="submit">問題を始める！</button>
    </form>
</div>
<%@ include file="./unit/footer.jsp" %>

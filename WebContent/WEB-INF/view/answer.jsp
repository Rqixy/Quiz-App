<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 正解かどうかの判定するbooleanを取得する
	boolean answerCheck = (boolean)session.getAttribute("answerCheck");
	// 現在の問題番号を取得する
	int currentQuizCount = (int)session.getAttribute("currentQuizCount");
	// 問題の答えを取得する
	String quizAnswer = (String)session.getAttribute("quizAnswer");
	// 問題の解説を取得する
	String commentary = (String)session.getAttribute("commentary");
	//
	int maxQuizCount = (int)session.getAttribute("maxQuizCount");
%>
<%@ include file="./layouts/header.jsp" %>
<div class="container">
    <div class="wrapper">
        <header>
            <p class="question-num"><%=currentQuizCount %>問目</p>
           <p><a href="TopServlet" class="back">TOPに戻る</a></p>
       </header>
       <main id="answer-main">
       	<% if(answerCheck) { %>
           	<img src="<%=request.getContextPath() %>/img/correct.png" alt="正解画像" id="answer-img">
           <% } else { %>
           	<img src="<%=request.getContextPath() %>/img/incorrect.png" alt="不正解画像" id="answer-img">
           <% } %>
           <p id="answer">答え：<%=quizAnswer %></p>
           <p id="commentary"><%=commentary %></p>
           <% if(currentQuizCount >= maxQuizCount) { %>
	           <form method="post" action="ResultServlet" id="next">
	               <button name="next" type="submit" >結果をみる</button>
	           </form>
           <% } else { %>
	           <form method="post" action="QuizServlet" id="next">
	               <button name="next" type="submit" >次へ</button>
	           </form>
           <% } %>
        </main>
    </div>
</div>
<%@ include file="./layouts/footer.jsp" %>
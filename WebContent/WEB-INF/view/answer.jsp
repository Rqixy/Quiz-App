<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 正解かどうかの判定するbooleanを取得する
	boolean correctAnswer = (boolean)session.getAttribute("correctanswer");
	// 現在の問題番号を取得する
	int currentCount = (int)session.getAttribute("currentcount");
	// 問題の答えを取得する
	String answer = (String)session.getAttribute("answer");
	// 問題の解説を取得する
	String commentary = (String)session.getAttribute("commentary");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>クイズアプリ</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css">
</head>
<body>
    <div class="container">
        <div class="wrapper">
            <header>
                <p class="question-num"><%=currentCount %>問目</p>
                <p><a href="TopServlet" class="back">TOPに戻る</a></p>
            </header>
            <main id="answer-main">
            	<% if(correctAnswer) { %>
                	<img src="<%=request.getContextPath() %>/img/correct.png" alt="正解画像" id="answer-img">
                <% } else { %>
                	<img src="<%=request.getContextPath() %>/img/incorrect.png" alt="不正解画像" id="answer-img">
                <% } %>
                <p id="answer">答え：<%=answer %></p>
                <p id="commentary"><%=commentary %></p>
                <% if(currentCount >= 10) { %>
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
</body>
</html>
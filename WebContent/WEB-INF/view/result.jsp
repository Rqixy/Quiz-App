<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String resultCommentImage = (String)session.getAttribute("resultcommentimage");
	int answerCount = (int)session.getAttribute("answercount");
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
</body>
</html>
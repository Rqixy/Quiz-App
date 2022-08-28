<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>クイズアプリ</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
    <div id="top-container">
        <img src="<%=request.getContextPath() %>/img/SDGsQuiz.png" alt="タイトル" id="img">
        <form method="get" action="QuizServlet" id="start">
            <button type="submit">問題を始める！</button>
        </form>
    </div>
</body>

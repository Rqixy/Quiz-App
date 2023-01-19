<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ include file="./layouts/header.jsp" %>
<%
	User user = (User)request.getAttribute("user");
%>
<!DOCTYPE html>
<div class="container">
    <div class="wrapper">
        <main class="register-main" id="register-confirm">
            <h1>ユーザー登録確認</h1>
            <p id="warning">※お間違いなければ登録へお進みください</p>
            <p class="confirm">ユーザー名：<%= user.getName() %></p>
            <p class="confirm">パスワード：・・・・・・・・</p>
            <form class="login" action="SignUpServlet" method="post">
            	<input type="hidden" name="name" value="<%= user.getName() %>" class="name">
                <input type="hidden" name="pass" value="<%= user.getPass() %>" class="">
                <button type="submit" name="submit" value="regist" class="btn btn-flat"><span>登録する‼</span></button>
            </form>
        </main>
    </div>
</div>
<%@ include file="./layouts/footer.jsp" %>

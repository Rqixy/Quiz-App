<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%
	User user = (User)request.getAttribute("user");
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ユーザー登録確認" />
	<jsp:param name="main">
		<jsp:attribute name="value">
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
		</jsp:attribute>
	</jsp:param>
</jsp:include>

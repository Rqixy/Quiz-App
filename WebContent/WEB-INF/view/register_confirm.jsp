<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%
	String csrfToken = (String)session.getAttribute("csrfToken");
	RegisterUserBean registerUser = (RegisterUserBean)request.getAttribute("registerUser");
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ユーザー登録確認" />
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main class="register" id="register-confirm">
	            <h1>ユーザー登録確認</h1>
	            <p id="warning">※お間違いなければ登録へお進みください</p>
	            <p class="confirm">ユーザー名：<%= registerUser.name() %></p>
	            <p class="confirm">パスワード：・・・・・・・・</p>
	            <form class="login" action="signup" method="post">
	            	<input type="hidden" name="csrf_token" value="<%=csrfToken %>"/>
	                <button type="submit" name="submit" value="regist" class="btn btn-flat button-se"><span>登録する‼</span></button>
	            </form>
	        </main>
		</jsp:attribute>
	</jsp:param>
</jsp:include>

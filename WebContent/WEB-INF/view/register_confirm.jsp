<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.RegistUserBean" %>
<%
	String csrfToken = (String)session.getAttribute("csrfToken");
	RegistUserBean registUser = (RegistUserBean)session.getAttribute("registUser");
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ユーザー登録確認" />
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main class="register" id="register-confirm">
	            <h1>ユーザー登録確認</h1>
	            <p id="warning">※お間違いなければ登録へお進みください</p>
	            <p class="confirm">ユーザー名：<%= registUser.name() %></p>
	            <p class="confirm">パスワード：・・・・・・・・</p>
	            <form class="login" action="register" method="post" autocomplete="off">
	            	<input type="hidden" name="csrf_token" value="<%=csrfToken %>"/>
	                <button type="submit" name="submit" value="regist" class="btn btn-flat button-se"><span>登録する!!</span></button>
	            </form>
	            
	            <a class="button-se" href="register">入力画面に戻る</a>
	        </main>
		</jsp:attribute>
	</jsp:param>
</jsp:include>

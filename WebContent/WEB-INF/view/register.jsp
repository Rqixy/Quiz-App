<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String csrfToken = (String)session.getAttribute("csrfToken");
String name = session.getAttribute("name") != null ? (String)session.getAttribute("name") : "";
	String errorMessage = session.getAttribute("errorMessage") != null ? (String)session.getAttribute("errorMessage") : "";
	session.removeAttribute("name");
	session.removeAttribute("errorMessage");
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ユーザー登録" />
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main class="register" id="register-main">
	            <h1>ユーザー登録</h1>
	            <form class="login" action="register" method="post" autocomplete="off">
	            	<input type="hidden" name="csrf_token" value="<%=csrfToken %>"/>
	            	<p class="error"><%=errorMessage %></p>
 	                <input type="text" name="name" placeholder="ユーザー名" value="<%=name %>"><br>
	                <input type="password" name="pass" placeholder="パスワード"><br>
	                <input type="password" name="pass2" placeholder="パスワード確認"><br>
	                <button type="submit" name="submit" value="confirm" class="btn btn-flat button-se"><span>NEXT</span></button>
	            </form>
	            
	            <a class="button-se" href="login">Topへ戻る</a>
	        </main>
		</jsp:attribute>
	</jsp:param>
</jsp:include>
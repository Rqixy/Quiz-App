<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String csrfToken = (String)session.getAttribute("csrfToken"); %>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ユーザー登録" />
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main class="register" id="register-main">
	            <h1>ユーザー登録</h1>
	            <form class="login" action="SignUpServlet" method="post">
	            	<input type="hidden" name="csrf_token" value="<%=csrfToken %>"/>
	                <input type="text" name="name" placeholder="ユーザー名" class="name"><br>
	                <input type="password" name="pass" placeholder="パスワード" class=""><br>
	                <button type="submit" name="submit" value="confirm" class="btn btn-flat"><span>次へ</span></button>
	            </form>
	        </main>
		</jsp:attribute>
	</jsp:param>
</jsp:include>
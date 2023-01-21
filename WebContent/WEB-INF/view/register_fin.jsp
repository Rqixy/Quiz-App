<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ユーザー登録完了" />
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main class="register" id="register-fin">
	            <h1>ユーザー登録完了</h1>
	            <a href="LoginServlet" class="btn btn--orange btn-c">ログインへ</a>
	        </main>
		</jsp:attribute>
	</jsp:param>
</jsp:include>

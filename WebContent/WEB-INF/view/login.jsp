<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String csrfToken = (String)session.getAttribute("csrfToken"); %>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ログイン" />
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main id="login-main">
			    <h1>SDGs Quest</h1>
			    <p>楽しく気軽にSDGｓを学べる教育的Webアプリケーション</p>
			    <form class="login" action="login" method="POST" autocomplete="off">
			    	<input type="hidden" name="csrf_token" value="<%=csrfToken %>"/>
			        <input type="text" name="name" placeholder="ユーザー名" class="name"><br>
			        <input type="password" name="pass" placeholder="パスワード" class=""><br>
			        <button type="submit" name="submit" value="login" class="btn btn-flat button-se" ><span>START!</span></button>
			    </form>
			
			    <a class="button-se" href="signup">新しく始める方はこちら</a>
			</main>
		</jsp:attribute>
	</jsp:param>
</jsp:include>

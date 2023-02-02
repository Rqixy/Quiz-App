<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String csrfToken = (String)session.getAttribute("csrfToken");
	String name = session.getAttribute("name") != null ? (String)session.getAttribute("name") : "";
	String errorMessage = session.getAttribute("errorMessage") != null ? (String)session.getAttribute("errorMessage") : "";
	session.removeAttribute("name");
	session.removeAttribute("errorMessage");
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ログイン" />
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main id="login-main">
			    <h1>SDGs Quest</h1>
			    <p id="sub-title">楽しく気軽にSDGｓを学べる教育的Webアプリケーション</p>
			    <form class="login" action="login" method="POST" autocomplete="off">
			    	<input type="hidden" name="csrf_token" value="<%=csrfToken %>"/>
			    	<p class="error"><%=errorMessage %></p>
			        <input type="text" name="name" placeholder="ユーザー名" value="<%=name %>"><br>
			        <input type="password" name="pass" placeholder="パスワード"><br>
			        <button type="submit" name="submit" value="login" class="btn btn-flat button-se" ><span>START!</span></button><br>
			        <button type="submit" name="submit" value="guest" class="guest button-se" >ゲストアカウントで始める</button><br>
			    </form>
			
			    <a class="button-se" href="register">新しく始める方はこちら</a>
			</main>
		</jsp:attribute>
	</jsp:param>
	
	<jsp:param name="terms">
		<jsp:attribute name="value">
			<div id="terms">
		    	<p>音楽:イワシロ音楽素材 URL:https://iwashiro-sounds.work/</p>
				<p>音楽:seadendenのファミコン風フリーBGMのサイト URL:https://seadenden-8bit.com/</p>
		    </div>
		</jsp:attribute>
	</jsp:param>
</jsp:include>

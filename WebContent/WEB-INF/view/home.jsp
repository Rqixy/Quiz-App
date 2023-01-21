<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%
	HashMap<Integer, Integer> clearStatus = (HashMap<Integer, Integer>)session.getAttribute("clearStatus");
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ホーム画面" />
	<jsp:param name="header">
		<jsp:attribute name="value">
			<header id="home-header">
	    		<form method="post" action="LoginServlet">
	    			<button type="submit" name="submit" value="logout" class="btn btn-flat"><span>ログアウト</span></button>
	    		</form>
	    	</header>
		</jsp:attribute>
	</jsp:param>
	
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main id="home-main">
	            <h1>Stages</h1>
	            <p>挑戦したいテーマを選ぼう‼<br>
	            テーマごとに敵が待ち受けているぞ‼</p>
	            <form id="themes" method="post" action="QuizServlet">
		           <% for(HashMap.Entry<Integer, Integer> cs : clearStatus.entrySet()) { %>
			      		<button class="theme" type="submit" name="goalNumber" value="<%= cs.getKey() %>">
			      			<div class="ribon">
			       			<img alt="" src="<%=request.getContextPath() %>/img/sdgs_icon/sdg_icon_<%= cs.getKey() %>.png" style="width: 200px;">
			      				<% if (cs.getValue() == 2) { %>
			       				<div class="caption"><span class="upper gold"></span></div>
			       				<div class="caption"><span class="under gold"></span></div>
			       			<% } else if (cs.getValue() == 1) { %>
								<div class="caption"><span class="upper silver"></span></div>
			       				<div class="caption"><span class="under silver"></span></div>
			       			<% } %>
			      			</div>
			      		</button>
					<% } %>
	            </form>
	        </main>
		</jsp:attribute>
	</jsp:param>
</jsp:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.GoalBean" %>
<%
	String csrfToken = (String)session.getAttribute("csrfToken");
	ArrayList<GoalBean> goalList = (ArrayList<GoalBean>)session.getAttribute("goalList");
%>
<jsp:include page="./layouts/layout.jsp">
	<jsp:param name="title" value="ホーム画面" />
	<jsp:param name="header">
		<jsp:attribute name="value">
			<header id="home-header">
				<p>ようこそ！testさん！</p>
	    		<form method="post" action="login">
	    			<input type="hidden" name="csrf_token" value="<%=csrfToken %>"/>
	    			<button type="submit" name="submit" value="logout" class="btn btn-flat button-logout"><span>ログアウト</span></button>
	    		</form>
	    	</header>
		</jsp:attribute>
	</jsp:param>
	
	<jsp:param name="main">
		<jsp:attribute name="value">
			<main id="home-main">
	            <h1>Stages</h1>
	            <p>
	            	挑戦したいテーマを選ぼう‼<br>
	            	テーマごとに敵が待ち受けているぞ‼
	            </p>
	            <form id="themes" method="post" action="quiz">
	            	<input type="hidden" name="csrf_token" value="<%=csrfToken %>"/>
		           	<% for (GoalBean goal : goalList) { %>
		      		<button class="theme button-stage" type="submit" name="goalNumber" value="<%= goal.goalNumber() %>">
		      			<div class="ribon">
		       			<img alt="" src="<%=request.getContextPath() %>/img/sdgs_icon/sdg_icon_<%= goal.goalNumber() %>.png">
		      				<% if (goal.clearStatus() == 2) { %>
			       				<div class="caption"><span class="upper gold"></span></div>
			       				<div class="caption"><span class="under gold"></span></div>
			       			<% } else if (goal.clearStatus() == 1) { %>
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
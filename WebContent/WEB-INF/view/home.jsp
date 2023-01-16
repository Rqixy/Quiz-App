<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ include file="./layouts/header.jsp" %>
<%
	HashMap<Integer, Integer> clearStatus = (HashMap<Integer, Integer>)session.getAttribute("clearStatus");
%>
<div class="container">
    <div class="wrapper">
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
    </div>
</div>
<%@ include file="./layouts/footer.jsp" %>

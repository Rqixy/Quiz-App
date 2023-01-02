<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ include file="./layouts/header.jsp" %>
<%
	HashMap<Integer, Integer> clearStatus = (HashMap<Integer, Integer>)session.getAttribute("clearStatus");
%>
<div id="top-container">
    <img src="<%=request.getContextPath() %>/img/SDGsQuiz.png" alt="タイトル" id="img">
    <form method="post" action="QuizServlet" id="start">
		<% for(HashMap.Entry<Integer, Integer> cs : clearStatus.entrySet()) { %>
       		<button type="submit" name="goalNumber" value="<%= cs.getKey() %>">
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

</div>
<%@ include file="./layouts/footer.jsp" %>

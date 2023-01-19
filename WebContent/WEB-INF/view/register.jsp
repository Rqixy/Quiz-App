<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./layouts/header.jsp" %>
<div class="container">
    <div class="wrapper">
        <main class="register-main" id="register-main">
            <h1>ユーザー登録</h1>
            <form class="login" action="SignUpServlet" method="post">
                <input type="text" name="name" placeholder="ユーザー名" class="name"><br>
                <input type="password" name="pass" placeholder="パスワード" class=""><br>
                <button type="submit" name="submit" value="confirm" class="btn btn-flat"><span>次へ</span></button>
            </form>
        
        </main>
    </div>
</div>
<%@ include file="./layouts/footer.jsp" %>
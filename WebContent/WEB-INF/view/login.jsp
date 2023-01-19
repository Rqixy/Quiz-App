<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css">
</head>
<body>
    <div class="container">
        <div class="wrapper">
            <main id="login-main">
                <h1>SDGs Quest</h1>
                <form class="login" action="LoginServlet" method="POST">
                    <input type="text" name="name" placeholder="ユーザー名" class="name"><br>
                    <input type="password" name="pass" placeholder="パスワード" class=""><br>
                    <button type="submit" name="submit" value="login" class="btn btn-flat" ><span>START!</span></button>
                </form>
            
                <a href="SignUpServlet">新しく始める方はこちら</a>
            </main>
        </div>
    </div>
</body>
</html>
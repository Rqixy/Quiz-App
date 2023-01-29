<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${ param.title }</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css">
</head>
<body>
	<div class="container">
	    <div class="wrapper">
	    	${ param.header }
	    	${ param.main }
	    </div>
	</div>
	${ param.terms }
	<script src="<%=request.getContextPath() %>/js/sound.js"></script>
	${ param.js }
</body>
</html>
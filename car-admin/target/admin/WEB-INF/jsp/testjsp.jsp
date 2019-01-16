<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>请核对您输入的页面地址是否正确哦~-</title>
<meta name="description" content="" />
<meta name="Keywords" content="">
<style type="text/css">
.errorbody{
	width: 520px;
	height: 530px;
	position: absolute;
	left: 50%;
	top: 50%;
	margin-left: -260px;
	margin-top: -265px;
	text-align: center;
}
.errorDiv{
}
.backhomeDiv{
	margin-left: 4%;
	margin-top: -30px;
	cursor:pointer;
}
</style>
</head>
<body class="body_bak">
<div class="errorbody">
	<div class="errorDiv">
		<img src="<%=path %>/images/common/error.png"/>
	</div>
	<div class="backhomeDiv">
		<a><img src="<%=path %>/images/common/backhome.png"/></a>
	</div>
</div>
</body>
</html>

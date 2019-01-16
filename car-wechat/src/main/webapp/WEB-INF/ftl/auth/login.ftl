<#include "../commons/header.ftl" />
<title>惠+车服商家端</title>
<link rel="stylesheet" href="${_RESOURCES}/css/com/com.css?v=${_version}">
<link rel="stylesheet" href="${_RESOURCES}/css/login/login.css?v=${_version}">
<script src="${_RESOURCES}/js/login/loginverify.js?v=${_version}"></script>
<script src="${_RESOURCES}/js/login/login.js?v=${_version}"></script>
</head>
<body>
	<div class="header">
		<h1>商家登录</h1>
	</div>
	<form class="forms">
		<!-- 手机号 -->
		<div class="tel">
			<label></label>
			<input type="tel" id="userName" onkeyup="formatPhoneNum(event,this);" placeholder="输入手机号" value=""/>
			<input type="button" class="getcode" id="getcode" value="获取验证码"/>
		</div>
		<!-- 验证码 -->
		<div class="pas">
			<label></label>
			<input type="number" id="code" placeholder="输入验证码"/>
		</div>
		<input type="submit"  id="login" class="action" value="登录" />
	</form>
</body>
</html>
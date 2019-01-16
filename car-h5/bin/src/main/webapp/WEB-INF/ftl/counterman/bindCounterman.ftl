<#include "/commons/header.ftl" />
 	<title>业务员激活</title>
 	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/salesman/salesman-activation.css?v=${_version}"/>
	<script src="${_RESOURCES}/js/counterman/bindCounterman.js?v=${_version}&s=1.0" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header class="header">
		<span class="back"><a href="/" style="display: block;width: 100%;height: 100%;"></a></span>
		<h1>业务员登录</h1>
		<span class="back-home"><a href="/" style="display: block;width: 100%;height: 100%;"></a></span>
	</header>
	<!-- 表单 -->
	<div class="forms">
		<input type="text" name="" id="realName" placeholder="请输姓名"/>
		<input type="text" name="" onkeyup="maxValue(18,this)" id="businessCode" placeholder="请输入工号"/>
	</div>
	<button class="action-btn">激活业务员身份</button>
</body>
</html>

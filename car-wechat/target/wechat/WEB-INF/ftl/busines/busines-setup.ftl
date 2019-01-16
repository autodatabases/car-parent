<#include "../commons/header.ftl" />
	<title>商家设置</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/busines/busines-setup.css?v=${_version}"/>
	</head>
	<body>
		<!-- 头部 -->
		<header class="header">
			<span class="back mui-action-back" onclick="javascript:history.back(-1);"></span>
			<h1>商家信息</h1>
		</header>
		<!-- 信息 -->
        <ul class="forms">
			<li  onclick="location.href='${_ROOT}/busines/busines-one.html'"><label>商家信息</label></li>
			<li onclick="logout()"><label>解绑手机</label></li>
		</ul>
	</body>
	<script>
		function logout(){
			delCookie('CAR_SELLER_TOKEN');
			delCookie('wxopenid');
			location.href = DOMIN.MAIN;
		}
	</script>
</html>
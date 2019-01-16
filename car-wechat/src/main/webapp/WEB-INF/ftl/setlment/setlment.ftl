<#include "../commons/header.ftl" />
	<title>结算管理</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/setlment/setlment.css?v=${_version}"/>
		<script src="${_RESOURCES}/js/setlment/setlment.js?v=${_version}"></script>
	</head>
	<body>
		<!-- 头部 -->
		<header class="header">
			<span class="back mui-action-back" onclick="javascript:history.back(-1);"></span>
			<h1>结算管理</h1>
		</header>
		<section class="top">
			
		</section>
		<section class="list">
			<a href="javascript:location.href='${_ROOT}/setlment/setlment-income.html'">今日收入<span>1299</span></a>
		</section>
		<section class="list">
			<a href="javascript:location.href='${_ROOT}/setlment/setlment-detailed.html'">收入明细</a>
			<!--<a href="">收款设置</a>-->
		</section>
	</body>
</html>
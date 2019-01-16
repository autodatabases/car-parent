<#include "../commons/header.ftl" />
	<title>订单详情</title>
	<link rel="stylesheet" href="${_RESOURCES}/css/order/order-details.css?v=${_version}">
	<script src="${_RESOURCES}/js/order/orderdetail.js?v=${_version}"></script>
</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back" onclick="javascript:history.back(-1);"></span>
		<h1>订单详情</h1>
	</header>
	<!-- 详情 -->
	<h2 class="state">订单状态:待服务</h2>
	<dl class="list">
		<dt>
			<div><img src="${_RESOURCES}/imgs/order/car-logo.png"/></div>
		</dt>
		<dd class="car">
			<span></span>
			<span></span>
			<!--<span>车型：自动三厢 </span>-->
			<span></span>
			<span></span>
			<!--<span>公里数：50000公里</span>-->
		</dd>
		<dd class="goods">
			<img src="${_RESOURCES}/imgs/order/img.png" alt="" />
			<div>
				<p></p>
				<p></p>
			</div>
		</dd>
		<dd class="order">
			<p></p>
			<p></p>
			<p></p>
		</dd>
	</dl>
	<input type="button" class="btn" value="确认服务完毕"/>
	
</body>
</html>
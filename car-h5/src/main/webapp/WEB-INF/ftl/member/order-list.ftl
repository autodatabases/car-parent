	<#include "../commons/header.ftl" />
		<title>我的订单</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/member/order-list.css?v=${_version}"/>
		<script src="${_RESOURCES}/js/member/order.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<!-- 头部 -->
		<header class="header">
			<span class="back"></span>
			<h1>我的订单</h1>
		</header>
		<!--  list -->
		<div class="top_nav">
			<span>全部</span>
			<span>待服务</span>
			<span>服务中</span>
			<span>已完成</span>
			<span>待评价</span>
		</div>
		<ul class="order-list">

		</ul>
		<div class="noData">
			<div class="bgImg">

			</div>
			<div class="tip">
				空空如也，暂无订单，快去下单吧
			</div>
		</div>
	</body>
</html>

<#include "../commons/header.ftl" />
	<title>订单跟踪</title>
	<link rel="stylesheet" type="text/css" href="../../../resources/css/com/com.css?v=${_version}"/>
	<link rel="stylesheet" type="text/css" href="../../../resources/css/repair/order-tracking.css?v=${_version}"/>
	<script src="${_RESOURCES}/js/repair/order-tracking.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body class="mui-android mui-android-5 mui-android-5-0">
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>订单跟踪</h1>
		<span class="back-home"><a href="/" style="display: block;width: 100%;height: 100%;"></a></span>
	</header>
	<!-- 编号 -->
	<div class="bianhao" style="font-size: 16px;">
		<p>订单编号：<span></span></p>
		<p>物流方式：<span>平台物流</span></p>
		<p style="display:none">物流单号：<span></span></p>
	</div>
	<!-- 流程图标 -->
	<div class="tracking-icon">
		 <div class="tracking1">
		 	<b></b>
		 	<i></i>
		 	<span>客户下单</span>
		 </div>
		 <div class="tracking2">
		 	<b></b>
		 	<i></i>
		 	<span>待确认</span>
		 </div>
		 <div class="tracking3">
		 	<b></b>
		 	<i></i>
		 	<span>待服务</span>
		 </div>
		 <div class="tracking4">
		 	<b></b>
		 	<i></i>
		 	<span>服务完成</span>
		 </div>
	</div>
	<ul class="tracking-list">
		<li>
			<i></i>
			<div>
				<p>您提交了订单，请等待系统确认</p>
				<span></span>
			</div>
		</li>
		<li>
			<i></i>
			<div>
				<p>系统已确认，待发货</p>
				<span></span>
			</div>
		</li>
		<li>
			<i></i>
			<div>
				<p>货物已到店，等待服务</p>
				<span></span>
			</div>
		</li>
		<li>
			<i></i>
			<div>
				<p>服务完成</p>
				<span></span>
			</div>
		</li>
	</ul>
</body>
</html>

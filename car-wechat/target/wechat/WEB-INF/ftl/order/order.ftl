<#include "../commons/header.ftl" />
	<title>订单管理</title>
	<link rel="stylesheet" href="${_RESOURCES}/css/order/order.css?v=${_version}">
	<script src="${_RESOURCES}/js/order/order.js?v=${_version}"></script>
</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="back"><a href="${_ROOT}"  style="display: block;width: 100%;height: 100%;"></a></span>
		<h1>订单管理</h1>
	</header>
	<!-- 管理 -->
	<dl class="list">
		<dt>
			<a href="javascript:void(0)"class="on" rel="1"><i></i><span>待服务</span></a>
			<a href="javascript:void(0)"  rel="2"><i></i><span>服务中</span></a>
			<a href="javascript:void(0)"  rel="3"><i></i><span>已服务</span></a>
		</dt>
		<!-- list -->
		<!--<dd>
			<div class="list-top">
				订单编号：12345678901234567890
			</div>
			<div  class="list-main">
				<div>
					<p><label>车牌号</label>：<span>京NEY211</span></p>
					<p><label>客户姓名</label>：<span>张三</span></p>
					<p><label>联系电话</label>：<span>13333333333</span></p>
					<p><label>地 址</label>：<span>朝阳区高和蓝峰大厦B座6层616室</span></p>
				</div>
				<span>待服务</span>
			</div>
			<div class="list-top list-bottom">
				更换电瓶
				<span>2016-9-5 11:15</span>
			</div>
		</dd>-->
	</dl>
</body>
</html>
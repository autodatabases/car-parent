<#include "../commons/header.ftl" />
	<title>订单完成</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/repair/powerorder-ok.css?v=${_version}">
    <script src="${_RESOURCES}/js/repair/order_ok.js?v=${_version}&t=1.1" type="text/javascript" charset="utf-8"></script>
</head>
<body class="mui-android mui-android-5 mui-android-5-0">
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1></h1>
		<span class="back-home"><a href="/" style="display: block;width: 100%;height: 100%;"></a></span>
	</header>
	<!-- 订单跟踪 -->
	<div class="order-tracking" id="order-tracking" onclick="location.href='${_ROOT}/order/orderTrace.html'">
		<a href="javascript:void(0)">
			<span>系统审核中</span>
			订单跟踪
		</a>
	</div>
	<!-- 地址 -->
	<section class="address">
		<div  class="on" id="addressinfo"">
			<p><span id="addr_name"></span>  <span id="addr_phone"></span> </p>
			<p id="addr_detail"></p>
			<a href="javascript:void(0)" id="daohang">开始导航</a>

		</div>

	</section>
	<!-- list -->
	<section class="list">
		<dl>
			<dt>
				<div class="img-warp">
					<img src="${_RESOURCES}/imgs/repair/power.jpg"/>
				</div>
				<div class="text">
					<p></p>
					<p></p>
				</div>
				<span></span>
			</dt>
			<dd>
				<p id="orderNo"><label>订单编号：</label></p>
				<p id="orderTime"><label>订单时间：</label></p>
				<p id="service"><label>配送方式：</label>上门服务</p>

			</dd>
		</dl>
	</section>
	<div id="bottom_button">
		<a href="javascript:void(0)" id="action-code">二维码</a>
		<a href="javascript:void(0)" id="btn-orderTrace" class="order-tracking" onclick="location.href='${_ROOT}/order/orderTrace.html'">订单追踪</a>
		<a href="javascript:void(0)" id="deleteOrder" style="display: none;">取消订单</a>
	</div>
	<!-- 发送券码 -->
	<section class="action-code">
		<div class="code"><img/></div>
		<p>请让商家扫码验证后开始服务</p>
	</section>
	<!--<div id="shade"></div>-->
</body>
</html>

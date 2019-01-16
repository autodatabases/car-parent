<#include "/commons/header.ftl" />
 	<title>定损详情</title>
 	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/salesman/order-details.css?v=${_version}"/>
	<script src="${_RESOURCES}/js/business/order-detail.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>定损详情</h1>
		<span class="back-home"><a href="/user/bussinessIndex.html" style="display: block;width: 100%;height: 100%;"></a></span>
	</header>
	<ul class="case-details">
		<li>
			<label>出险姓名：</label>
			<span class="names"></span>
			<label>出险人手机号：</label>
			<span id="userPhone" class="times"></span>
		</li>
		<li>
			<label>车牌号码：</label>
			<span></span>
		</li>
		<li>
			<label>车辆品牌：</label>
			<span></span>
		</li>
		<li>
			<!--<label>预估价格（元）：</label>
			<span class="money"></span>-->
		</li>
		<li class="img-box">
			<!--<div>
				<img src="../../imgs/salesman/upload-img.png"/>
			</div>-->
		</li>
		<li>
			<label>定损金额（元）：</label>
			<span class="money"></span>
		</li>
		<li>
			<label>定损描述：</label>
			<span></span>
		</li>

		<li style="border-top: 10px solid #eaeaea;">
			<label> 修理厂名称：</label>
			<span></span>
		</li>
		<li>
			<label>修理厂地址：</label>
			<span></span>
		</li>
	</ul>
	<!--<span>提交定损结果</span>-->
</html>

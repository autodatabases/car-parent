	<#include "../commons/header.ftl" />
		<title>商家详情</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/dot/dot-details.css?v=${_version}"/>
		<script src="${_RESOURCES}/js/dot/dot-details.js?v=${_version}&t=1" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<header class="header">
			<span class="back mui-action-back"></span>
			<h1>商家详情</h1>
		</header>
		<!-- img -->
		<section class="img-box">
			<img/>
			<div>
				<span class="tips"></span>
				<span class="times">营业时间：<b></b></span>
			</div>
		</section>
		<!-- 详细信息 -->
		<section class="details">
			<div class="text-warp">
				<div class="text-box">
					<h3 rel="8"></h3>
					<div class="text-t">
						<span><b></b>人已服务</span>
						<p class="on-1"><i></i><i></i><i></i><i></i><i></i><span></span>分</p>
					</div>
				</div>
				<a href="" class="contact-tel">
					<i></i>
					联系商家
				</a>
			</div>
			<!-- 开始导航 -->
			<a class="gps-go">
				<span class="gps">开始导航</span>
				<span></span>
				<p></p>
			</a>
			<div class="car-service">
				<span><i class="xc-2"></i>洗车</span>
				<span><i class="by-2"></i>小保养</span>
				<span id="lacquer"><i class="pq-2"></i>喷漆</span>
				<span><i class="fwfw-2"></i>服务范围</span>
				<span id="install"><i class="hj-2"></i>安装环境</span>

			</div>
		</section>
		<!-- 服务盒子 -->
		<div class="service-box">
			<strong>门店支持的服务</strong>
			<ul>
			</ul>
		</div>
		<input type="button" onclick="hasUnCompletOrder()" class="btn-action" id="" value="立即免费洗车" />
	</body>

</html>

<#include "../commons/header.ftl" />
	<title>订单</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/repair/powerorder.css?v=${_version}"/>
     <script src="${_RESOURCES}/js/repair/powerorder.js?v=${_version}&t=1.0" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1></h1>
	</header>
	<!-- 地址 -->
	<section class="address">
		<a href="${_ROOT}/address/addaddress.html" class="on"><p>添加配送地址</p></a>
		<div id="addressinfo" onclick="location.href='${_ROOT}/address/addaddress.html'">
			<p><span id="addr_name"></span><span id="addr_phone"></span></p>
			<p id="addr_detail"></p>
		</div>
	</section>
	<section class="tyremain">

	</section>
	<!-- list -->
	<section class="list">
		<dl>
			<dd><a href="javascript:void(0);" id="usercarinfo"></a></dd>
			<!--<dd><a href="${_ROOT}/address/chooseCity" id="usercityinfo"></a></dd>-->
			<dt class="list1" id="powerorder_jy">
				<div class="img-warp"><img src="${_RESOURCES}/imgs/repair/power.jpg"/></div>
				<div class="text jy">
					<p>瓦尔塔 蓄电池 电瓶 以旧换新</p>
					<!-- <p style="font-size:12px; color:#bdbdbd;">x1</p> -->
				</div>
				<span></span>
			</dt>
			<dt class="list2">
				<div class="img-warp"><img src="${_RESOURCES}/imgs/repair/gsf-icon.png"/></div>
				<div class="text">
					<p>工时费</p>
					<p></p>
				</div>
				<span></span>
			</dt>
			<!--<dd>服务券</dd>
			<dd><span></span>电瓶服务券<i></i></dd>-->
		</dl>
		<div class="null-box"></div>
	</section>
	<!-- 确认提交 -->
	<div class="action-ok">
		<!--<p class="explain">确认提交表示同意<span>《安装服务说明》</span></p>-->
		<p class="action">
			<input type="button" id="submit" value="确认提交"/>
			<small></small>
			<span>免费专享</span>
		</p>
	</div>
</body>
</html>

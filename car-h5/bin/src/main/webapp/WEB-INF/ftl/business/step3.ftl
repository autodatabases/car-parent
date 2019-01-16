	<#include "../commons/header.ftl" />
		<title>网点</title>	
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/dot/dot.css?v=${_version}"/>
		<script src="${_RESOURCES}/js/common/initAreaSelect.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/business/sellerList.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=C93b5178d7a8ebdb830b9b557abce78b"></script>
	</head>
	<body>
		<header class="header">
			<span></span>
			<h1>周边门店</h1>
			<input type="text" id="sellerName" name="sellerName" placeholder="请输入店铺名称"/>
			<span id="cancle-search" class="search-btn"></span>
		</header>
		<!-- tab -->
		<dl class="tab-list" id="dot_dl">
			<dt>
				<div style="display:none;">
					<!--<select id="province">
						<option value="">选择省</option>
					</select>-->
					<span>选择省</span>
				</div>
				<div>
					<!--<select id="city">
						<option value="">选择市</option>
					</select>-->
					<span  id="selectCity">广州</span>
				</div>
				<div>
					<!--<select id="area">
						<option value="">选择区</option>
					</select>-->
					<span id="selectArea">选择区</span>
				</div>
				<div>
					<!--<select id="sellerType">
						<option value="">所有门店</option>
						<option value="1">快修店</option>
						<option value="2">4s店</option>
						<option value="3">修理厂</option>
					</select>-->
					<span id="selectStore" value="3">修理厂</span>
				</div>
				<div>
					<!--<select id="orderType">
						<option value="score">默认排序</option>
						<option value="score">门店好评</option>
						<option value="">距离最近</option>
					</select>-->
					<span id="selectDefalt" value="">默认排序</span>
				</div>
				<ul class="select-list" id="select-list1"></ul>
				<ul class="select-list" id="select-list2" style="display: none;">
					<li>选择区</li>
				</ul>
				<ul class="select-list" id="select-list3" style="display: none;">
					<li value="3">修理厂 </li>
				</ul>
				<ul class="select-list" id="select-list4" style="display: none;"></ul>
				<div class="bg-box" id="bg-box1"></div>
				<div class="bg-box" id="bg-box2"></div>
				<div class="bg-box" id="bg-box3"></div>
				<div class="bg-box" id="bg-box4"></div>
			</dt>
			<!--<dd>
				<div class="main">
					<div class="img-box">
						<img rel="8" src="http://127.0.0.1:8080/resources/imgs/dot/img1.png/">
						<p class="blue">维修厂</p>
					</div>
					<div class="text-box">
						<h3 rel="8">广州市黄石闪城轮胎</h3>
						<div class="text-t">
							<span><b>10000</b>人已服务</span>
							<p><i></i><i></i><i></i><i></i><i></i><span>5.0</span>分</p>
						</div>
						<div class="text-b">
							<span>4.15km</span>
							<p>广州市白云区柯子岭景云路51号</p>
						</div>
					</div>
				</div>
			</dd>-->
		</dl>
	</body>
</html>

<#include "/commons/header.ftl" />
	<title>出险资料提交</title>
 	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/salesman/data-submit.css?v=${_version}"/>
	<script src="${_RESOURCES}/js/business/step1.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>出险资料提交</h1>
		<span class="back-home"><a href="/user/bussinessIndex.html" style="display: block;width: 100%;height: 100%;"></a></span>
	</header>
	<!-- 步骤  -->
	<div class="step-box">
		<div class="step-one">
			<span>1</span>
			<i></i>
			资料提交
		</div>
		<div class="step-two">
			<span>2</span>
			<i></i>
			照片提交
		</div>
		<div class="step-three">
			<span>3</span>
			<i></i>
			选择修理厂
		</div>
		<div class="step-ok">
			<span>4</span>
			<i></i>
			确认提交
		</div>
	</div>
	<!-- 表单 -->
	<ul class="forms-lists">
		<li style="display: none;">
			<label>出险时间：</label>
			<input type="datetime-local" id="outTime" />
		</li>
		<li>
			<label><b style="color: red;">*&nbsp;</b>车主姓名：</label>
			<input type="text" id="userName" placeholder="请输入出险人姓名"/>
		</li>
		<li>
			<label><b style="color: red;">*&nbsp;</b>联系电话：</label>
			<input type="tel" id="userPhone" onkeyup="formatPhoneNum(event,this);" placeholder="请输入出险人电话"/>
		</li>
		<li>
			<label><b style="color: red;">*&nbsp;</b>车牌号码：</label>
			<input type="text" id="chePai" placeholder="请输入车牌号码"/>
		</li>
		<li>
			<label><b style="color: red;">*&nbsp;</b>车辆品牌：</label>
			<input type="text" id="carBrand" placeholder="例如：奥迪A6,宝马x3"/>
		</li>
		<li>
			<label><b style="color: red;">*&nbsp;</b>预估价格：</label>
			<input type="number" id="money" onkeyup="maxValue(7,this)" placeholder="请输入预估价格"/>
		</li>
		
		<li style="display: none;">
			<label>出险地点：</label>
			<input type="text" id="step_location"/>
		</li>
		<li style="display: none;">
			<label>出险描述：</label>
			<textarea id="userDesc"></textarea>
		</li>
	</ul>
	<!-- 下一步 -->
	<input type="button" class="next-btn" id="" value="下一步" />
</body>
</html>
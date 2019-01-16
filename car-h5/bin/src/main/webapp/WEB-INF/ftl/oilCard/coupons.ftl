<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
   		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	    <title>发送电子券</title>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/coupons.css?v=${_version}&t=1.0"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/oilCard/coupons.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>

		<header class="header">
			<span class="back mui-action-back"></span>
			<h1>发送电子券</h1>
		</header>
		<div class="content">
		<div class="tips">
					<p style="color:red;">温馨提示：该商品为一次性短信消费券，有效期一个月，请您根据需求购买</p>
			</div>
			<div class="coupons">
				<ul>
					<li>
						<p>100元</p>
						<p>电子券</p>
					</li>
					<li>
						<p>200元</p>
						<p>电子券</p>
					</li>
					<li>
						<p>300元</p>
						<p>电子券</p>
					</li>
					<!--  <li>
						<p>400元</p>
						<p>电子券</p>
					</li>
					<li>
						<p>500元</p>
						<p>电子券</p>
					</li>
					<li>
						<p>600元</p>
						<p>电子券</p>
					</li>-->

				</ul>
			</div>
			<div class="number">
				<input type="number" id="number" placeholder="请输入电子券的数量" value="1"/>
				<p>请输入电子券的数量</p>
			</div>
			<div class="send">
				<input type="button" id="send" value="立即发送" />
			</div>

		</div>
		<script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "https://hm.baidu.com/hm.js?494341af2d81649c196093df82511f87";
		  var s = document.getElementsByTagName("script")[0];
		  s.parentNode.insertBefore(hm, s);
		})();
	</script>
	</body>
</html>

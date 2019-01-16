<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>视频会员卡</title>
	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}" />
	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/video/videoVip.css?v=${_version}&t=1.2" />
	<script type="text/javascript">
		var DOMIN = { MAIN: '${_ROOT}' };
	</script>
	<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/oilCard/video/videoVip.js?v=${_version}&t=1.3" type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>爱奇艺</h1>
	</header>
	
	<div class="container">
		<ul id="cont">
			<!-- <li class="coupon_li" sale="19.80">
				<i></i>
				<dl class="coupon">
					<dt>爱奇艺月卡</dt>
					<dd>
						<div class="money">￥<span class="num">191.80</span></div>
						<img src="${_RESOURCES}/imgs/oilCard/video/buy.jpg" alt="" class="buy">
					</dd>
				</dl>
			</li> -->
		</ul>

		<!-- 立即购买按钮 -->
		<input type="button" id="btn_pay" value="立即购买" disabled>
	</div>
	
	<div class="kong"></div>

	<div id="explain">
		<h3 class="title">使用说明</h3>
		<p>1.登陆爱奇艺APP→输入激活码/兑换码→输入验证码，点击立即激活→激活成功；</p>
		<p class="info">注：苹果手机请前往官网进行兑换</p>
		<p>2.购买的会员卡请在三个月内进行兑换；</p>
		<p>3.卡密商品，购买成功后不支持无理由退货，下单前请确认，买错不支持退换。</p>
	</div>
	

	<script>
		var _hmt = _hmt || [];
		(function () {
			var hm = document.createElement("script");
			hm.src = "https://hm.baidu.com/hm.js?494341af2d81649c196093df82511f87";
			var s = document.getElementsByTagName("script")[0];
			s.parentNode.insertBefore(hm, s);
		})();
	</script>
</body>
</html>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
   		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	    <title>账户信息</title>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/oilAccount.css?v=${_version}&t=1"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/oilCard/oilAccount.js?v=${_version}&t=1.8" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>

		<header class="header">
			<h1>账户信息</h1>
		</header>
		<div class="content">
			<div class="info">
				<img class="account_photo" src="${_RESOURCES}/imgs/oilCard/home_touxiang.png"/>
				<div class="account_phone">加载中...</div>
				<div class="balance">账户余额：
					<span class="account_number">加载中...</span> 元
				</div>
			</div>

			<!-- <div class="operate">
				<ul>
					<li class="order"><span>充值记录</span></li>
					<li class="recharge"><span>账户充值</span></li>
					<li class="coupons"><span>发送电子券</span></li>
					<li class="chargecard"><span>油卡充值</span></li>
					<li class="mobile"><span>话费充值</span></li>
					<li class="guide"><span>使用指南</span></li>
					<li class="guide1" onclick="exitOil();"><span>退出登录</span></li>
					<p style="text-align: center;font-size: 16px;padding: 30px;" class="phone">
						客服热线：<a style="color:#ff3e5b;" href="javascript:void(0)">400-867-1993</a></span>
					</p>
				</ul>
			</div> -->
			<div class="operate">
				<ul>
					<li class="order">充值记录</li>
					<li class="recharge">账户充值</li>
					<!-- <li class="coupons">发送电子券</li> -->
					<li class="chargecard">油卡充值</li>
					<li class="daijia">代驾</li>
					<li class="buy">购买洗车</li>
					<li class="mobile">话费充值</li>
					<li class="guide">使用指南</li>
					<li class="exitOil" onclick="exitOil();">退出登录</li>
				</ul>
			</div>
			<div class="phone phone1">
				客服热线Ⅰ：<a style="color:#ff3e5b;" href="javascript:void(0)">400-867-1993</a><span>(服务时间：周一至周五)</span>
			</div>
			<div class="phone phone2">
				客服热线Ⅱ：<a style="color:#ff3e5b;" href="javascript:void(0)">0523-8098-0609</a><span>(服务时间：周六、周日)</span>
			</div>
		</div>
		<div class="tip tip1">
	        <div class="bg"></div>
	        <div class="tipCon">
	            <h3>服务热线Ⅰ</h3>
	            <div class="cont">
	                <p>惠家车服为您提供热线咨询服务，及时解答您的疑问。</p>
					<p>服务时间为周一至周五(节假日除外)，上午9:00-12:00,下午13:00-18:00</p>
	            </div>
	            <div class="btnCon">
	                <div class="btn_close">暂不需要</div>
	                <div><a href="tel:400-867-1993">立即拨打</a></div>
	            </div>
	        </div>
	    </div>
		<div class="tip tip2">
	        <div class="bg"></div>
	        <div class="tipCon">
	            <h3>服务热线Ⅱ</h3>
	            <div class="cont">
	                <p>惠家车服为您提供热线咨询服务，及时解答您的疑问。</p>
					<p>服务时间为周六至周日，9:00-18:00</p>
	            </div>
	            <div class="btnCon">
	                <div class="btn_close">暂不需要</div>
	                <div><a href="tel:052-380980609">立即拨打</a></div>
	            </div>
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

		function exitOil(){
			delCookie('CAR_OIL_TOKEN');
			location.reload();
		}
	</script>
	</body>
</html>

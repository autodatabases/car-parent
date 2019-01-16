<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>油卡充值</title>
	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}" />
	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/chargecard.css?v=${_version}&t=1.2" />
	<script type="text/javascript">
		var DOMIN = { MAIN: '${_ROOT}' };
	</script>
	<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/oilCard/chargecard.js?v=${_version}&t=1.5" type="text/javascript" charset="utf-8"></script>
</head>

<body>

	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>油卡充值</h1>
	</header>
	<div class="content">
		<div class="point" id="point">
			<p>22点50到0点50是系统维护时间，暂不能充值</p>
		</div>
		<!-- <style>
				.point1{
					color: red;
					font-size: 12px;
					padding: 5px;
				}
			</style>
			<div class="point1">
				<p>尊敬的客户，由于系统升级，中石化接口暂时不能充值，由此给您带来的不便，我们深感歉意。感谢您的合作和支持！如有您有任何问题，请致电客服热线400-867-1993</p>
			</div> -->
		<div class="tab">
			<div class="tab_btn">
				<span class="on">中石化</span>
				<span>中石油</span>
			</div>
			<div class="tab_con">
				<p>中石化油卡充值</p>
				<p class="miss">中石油油卡充值</p>
			</div>
		</div>
		<div class="info">
			<div>
				<span>油卡卡号</span>
				<input type="number" id="number" placeholder="请输入油卡卡号" />
			</div>
			<div>
				<span>手机号码</span>
				<input type="number" id="mobile" placeholder="请输入手机号码" />
			</div>
			<p style="color:red;font-size:12px;padding: 0 2rem;">温馨提示：请认真核对充值卡号信息，对个人输入有误产生的售后问题概不受理。充值成功后，请携所充值油卡去油站圈存后方可使用。</p>
		</div>
		<div class="money">
			<div class="money-title">
				<p>请选择充值金额</p>
			</div>
			<div class="coupons">
				<div class="coupons01"></div>
				<div class="coupons02" style="display:none">
					<ul>
						<li>
							<p>50元</p>
						</li>
						<li>
							<p>100元</p>
						</li>
						<li class="miss">
							<p>200元</p>
						</li>
						<li>
							<p>500元</p>
						</li>
						<li>
							<p>1000元</p>
						</li>
					</ul>
				</div>
			</div>
			<div>
				<input type="button" id="btn" value="立即充值" />
			</div>
			<div class="problem">
				<h4>油卡充值常见问题</h4>
				<p>1.充值之后没有到账？</p>
				<p>A.充值有部分时间延迟，请您耐心等待</p>
				<p>2.充值失败？</p>
				<p>A.请核对卡号、油卡类型（中石油、中石化）是否正确，副卡不能直接充值</p>
				<p>3.充值失败，钱少了？</p>
				<p>A.充值会先扣掉余额，充值失败会退还到余额</p>
				<p>4.没有收到短信？</p>
				<p>A.请检查短信是否被屏蔽</p>
				<p>5.充值油卡之后油卡余额为空？</p>
				<p>A.一般圈存业务主要适用于单位副卡用户，具体为主卡再给副卡分配一定的金额后，副卡上的钱还停留在备付账户中，还不能直接用来加油，需要到就近的中石化发卡网点或者有自动圈存机的加油站做下圈存业务操作，对应的副卡才能收到主卡上分配的钱，才能用来加油。</p>
			</div>
		</div>


	</div>
	<div class="tip">
		<div class="bg"></div>
		<div class="tipCon">
			<h3>温馨提示</h3>
			<div class="cont">
				<p>尊敬的用户，为了增加追电科技数据安全，需要对系统数据安全接口进行升级，升级期间无法充值，请各位谅解。</p>
				<p>具体时间为12月16日0：00分-12月18日9：00分</p>
			</div>
		</div>
	</div>
	<div class="tip tip1">
		<div class="bg"></div>
		<div class="tipCon">
			<h3>提示</h3>
			<div class="cont">
				<p>用户你好：</p>
				<p style="text-indent:2em;">系统正在进行安全升级，请您稍后再进行充值服务！感谢您的谅解！</p>
				<p style="padding-top:1rem;">详情请拨打服务电话：</p>
				<p style="text-align:center;color:red;">400-867-1993</p>
			</div>
			<div class="btnCon">
				<div>
					<a href="tel:400-867-1993">立即拨打</a>
				</div>
				<div class="btn_close">取消</div>
			</div>
		</div>
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
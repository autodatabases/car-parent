<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
   		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	    <title>服务登录</title>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/oilLogin.css?v=${_version}&t=1"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/oilCard/loginverify.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/oilCard/oilLogin.js?v=${_version}&t=1.0" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<header class="header">
			<span>用户登录</span>
		</header>
		<div class="content">
			<div class="con_box">
				<div class="phoneNumber">
					<input type="tel"  id="userName" onkeyup="formatPhoneNum(event,this);" placeholder="请输入您的手机号"/>
				</div>
				<div class="code">
					<input type="number" id="code" placeholder="输入验证码"/>
					<input type="button" id="getCode" value="获取验证码" />
				</div>
				<div class="login_btn">
					<input type="button" id="login" value="立即登录" />
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
	</script>
	</body>
</html>

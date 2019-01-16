<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<title>车行网点分布</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/counterman/showmap.css?v=${_version}"/>
	 	<script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=C93b5178d7a8ebdb830b9b557abce78b"></script>
		<script src="${_RESOURCES}/js/counterman/showmap.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	
	</head>
	<body>
		<header class="header">
			<span class="back mui-action-back"></span>
			<h1>车行网点分布</h1>
		</header>
		<div id="l-map"></div>
	</body>
	<script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "https://hm.baidu.com/hm.js?494341af2d81649c196093df82511f87";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
	</script>
</html>

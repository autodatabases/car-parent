<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
   		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	    <title>业务员空间</title>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/counterman/dataInfo.css?v=${_version}"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/counterman/echarts.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/counterman/dataInfo.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>

		<header class="header">
			<span class="back"></span>
			<h1>业务员空间</h1>
		</header>
		<div class="content">
			<!-- <div id="chart" class="chart">

			</div> -->

			<div class="info">
				<div class="title">
					<h2>业务员信息</h3>
				</div>
				<div class="data">
					<div>
						<span>业务员姓名：<b class="name"></b></span>
					</div>
					<div>
						<span>业务员工号：<b class="number"></b></span>
					</div>
					<div>
						<span>手机号：<b class="career"></b></span>
					</div>
				</div>
				<div class="title">
					<h2>本季度积分</h3>
				</div>
				<div class="data">
					<div>
						<span class="area"></span><span class="detail">积分详情 》</span>
					</div>
					<div>
						<p class="point"><b>0</b> 积分</p>
					</div>
				</div>
				<div class="btn">
					<button id="mall">积分商城</button>
					<button id="map">定损中心导航</button>
				</div>
			</div>
		</div>
	</body>
</html>

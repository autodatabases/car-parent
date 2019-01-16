<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
   		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	    <title>代理保单详情</title>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/guoshou/agent_detail.css?v=${_version}"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/guoshou/agent_detail.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>

		<header class="header">
			<span class="back mui-action-back"></span>
			<h1>代理保单详情</h1>
		</header>
		<div class="content">
			<div class="company">
				<ul>
					<li>公司名称：</li>
					<li class="name"></li>
					<li>合作时间：</li>
					<li class="startime"></li>
				</ul>
			</div>
			<div class="baodan">
				<div class="bd_year">
					<div class="select">2017年</div>
					<div class="select_tip" style="display: none">
						<div class="bg"></div>
						<div class="option">
							<!-- <span value="0">2017年</span> -->
						</div>
					</div>
				</div>
				<div class="bd_detail">
					<div class="table_head">
						<span>月份</span>
						<span>保费</span>
						<span>置换产值</span>
					</div>
					<div class="table_body">
						<!-- <ul>
							<li class="item">
								<span>一月</span>
								<span>1222</span>
								<span>3000</span>
							</li>
							<li class="item">
								<span>一月</span>
								<span>1222</span>
								<span>3000</span>
							</li>
							<li class="item">
								<span>一月</span>
								<span>1222</span>
								<span>3000</span>
							</li>
							<li class="item">
								<span>一月</span>
								<span>1222</span>
								<span>3000</span>
							</li>
							<div class="item">
								<span>合计</span>
								<span>1222</span>
								<span>3000</span>
							</div>
							<div class="item foot_item">
								<span>置换率</span>
								<span>1222</span>
							</div>
							<div class="item foot_item">
								<span>赔付率</span>
								<span>1222</span>
							</div>
						</ul> -->
					</div>
				</div>
			</div>
		</div>
	</body>
</html>

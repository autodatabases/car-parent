<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
   		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	    <title>积分商城</title>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/counterman/point-mall.css?v=${_version}"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/counterman/point-mall.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>

		<header class="header">
			<span class="back"></span>
			<h1>积分商城</h1>
		</header>
		<div class="content">
			<!-- <div class="select">
				<select name="city">
					<option value="0">广州</option>
				</select>
				<select name="area" id="selected">
					<option value="0">南岗</option>
					<option value="1">萝岗</option>
					<option value="2">黄埔</option>
				</select>
			</div> -->
			<ul class="list">
				<!--<li>
					<div class="photo">
						<img src="${_RESOURCES}/imgs/ceshi.jpg"/>
						<span class="elected"></span>
					</div>
					<div class="info">
						<p class="name">荣耀 畅玩6X </p>
						<p class="price">
							<span>￥199</span>
							<span>19900 积分</span>
						</p>
						<p class="kucun">
							<span>库存：95 件</span><span class="number"><b>-</b><input type="number" id="number" value="1"/><b>+</b></span>
						</p>
					</div>

				</li>-->

			</ul>
		</div>
		<div class="footer">
			<div>
				合计：
				<!--<span>￥<b class="money">0</b></span> -->
				<span>(<b class="point">0</b> 积分)</span>
			</div>
			<div class="btn">立即结算</div>
		</div>
		<div class="submit">
			<h2>订单列表</h2>
			<ul>
				<!--<li class="items"><span>安踏新版网鞋</span><span>x1</span><span>600积分</span></li>
				<li class="items"><span>安踏新版网鞋</span><span>x1</span><span>600积分</span></li>
				<li class="items"><span>安踏新版网鞋</span><span>x1</span><span>600积分</span></li>
				<li class="items"><span>安踏新版网鞋</span><span>x1</span><span>600积分</span></li>
				<li class="items"><span>安踏新版网鞋</span><span>x1</span><span>600积分</span></li>-->

			</ul>
			<div class="total">总计：<span>0</span> 积分</div>
			<div><button class="sub">确认结算</button><button class="cancel">取消</button></div>
		</div>
		<div class="shade"></div>
		<div class="tip-con">
			<div class="tip-lg"></div>
			<div class="tip-box">
				<div class="box-head">
					<p>商品详情</p>
				</div>
				<div class="box-body">
					<div class="img-box">
						<img src="${_RESOURCES}/imgs/counterman/good-detail.png">
					</div>
					<div class="introduce">
						<p>暂无商品详情</p>
					</div>
				</div>
			</div>

		</div>
	</body>
</html>

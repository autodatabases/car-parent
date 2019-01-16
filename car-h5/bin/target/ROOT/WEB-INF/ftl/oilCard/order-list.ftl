<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
   		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	    <title>账户充值</title>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/order-list.css?v=${_version}&t=1.0"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/oilCard/order-list.js?v=${_version}&t=1.1" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<header class="header">
			<span class="back mui-action-back"></span>
			<h1>订单列表</h1>
			<input type="text" id="sellerName" name="sellerName" placeholder="请输入订单号搜索" style="display: none;">
			<span id="search-btn" class="search-btn"></span>
			<span id="cancle-btn" class="cancle-btn">取消</span>
		</header>
		<div class="content">
            <ul class="order-list">
                <!-- <li>
                    <p>
                        <span>充值金额</span>
                        <span>￥300 元</span>
                    </p>
                    <p>
                        <span>充值状态</span>
                        <span class="status">充值失败</span>
                    </p>
                    <p>
                        <span>充值类型</span>
                        <span>充值失败</span>
                    </p>
                    <p>
                        <span>订单编号</span>
                        <span>充值失败</span>
                    </p>
                    <p>
                        <span>时    间</span>
                        <span>充值失败</span>
                    </p>

                </li> -->

            </ul>
			<div class="tips-word" style="text-align:center;line-height:3rem;display:none">
				暂无数据
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

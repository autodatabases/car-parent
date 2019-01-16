	<#include "../commons/header.ftl" />
		<title>订单完成</title>
		<meta name="format-detection" content="telphone=no" />
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/repair/success.css?v=${_version}"/>
	</head>
	<body>
		<!-- 头部 -->
		<header class="header">
			<h1>订单提交成功</h1><a href="/" class="back-btn"></a>
		</header>
		<div class="text">
			<i></i>
			<p>提交成功</p>
			<p>等待平台审核</p>
		</div>
		<a href="javascript:void(0)"  id="success_ok">查看订单详情</a>

	</body>
	<script>
	    $(function(){
		    var orderNo= $.getUrlParam('orderNo');

			$.tip("提交成功，请您确保电话畅通，稍后我们客服会联系您确认订单。客服电话:400-867-1993");
		    $("#success_ok").on('touchstart',function(){
			    location.href = DOMIN.MAIN+'/order/orderok?orderNo='+orderNo;
			});

		});

	</script>
</html>

		<#include "../commons/header.ftl" />
		<title>我的</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/member/member.css?v=${_version}&t=1"/>
		<script src="${_RESOURCES}/js/member/member.js?v=${_version}&t=1" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<!-- 头部 -->
		<header class="header">

		</header>
		<!-- 我的订单 -->
		<section class="order">
			<div class="order-top">
				<span>我的订单</span>
					<a href="${_ROOT}/user/order.html?type=-1"><i></i>查看全部></a>
			</div>
			<div class="order-list">
				<a href="${_ROOT}/user/order.html?type=1"><i></i>待服务</a>
				<a href="${_ROOT}/user/order.html?type=2"><i></i>服务中</a>
				<a href="${_ROOT}/user/order.html?type=3"><i></i>已完成</a>
				<a href="${_ROOT}/user/order.html?type=-2"><i></i>待评价</a>
			</div>
		</section>
		<!-- 列表 -->
		<ul class="member-list">
			<li onclick="location.href='${_ROOT}/user/garage.html'"><label><i></i>我的车库</label><!--<span>（2）</span>--></li>
			<li><label><i></i>客服热线</label><a href="javascript:void(0)" class="phone">400-101-1506</a></li>
			<li onclick="location.href='${_ROOT}/user/feedback.html'"><label><i></i>用户反馈</label></li>
			<li onclick="location.href='${_ROOT}/user/setup.html'"><label><i></i>用户设置</label></li>
			<li class="exitLogin"><label><i></i>退出登录</label></li>
		</ul>
		<div class="tip-con">
			<div class="tip-lg"></div>
			<div class="tip-box">
				<div class="box-head">
					<p>服务热线</p>
				</div>
				<div class="box-body">
					<div class="introduce">
						<p>惠家车服为您提供热线咨询服务，及时解答您的疑问。服务时间为周一至周五(节假日除外)，上午9:00-12:00,下午13:00-18:00</p>
					</div>
				</div>
				<div class="tip-footer">
					<a href="javascript:void(0)" class="tip-close">暂不需要</a><a href="tel:400-1011506">立即拨打</a>
				</div>
			</div>

		</div>
		<!-- 底部 -->
	<#include "../commons/footer.ftl" />

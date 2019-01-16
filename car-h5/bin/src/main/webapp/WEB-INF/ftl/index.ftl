<#include "/commons/header.ftl" />
	<title>首页</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/index.css?v=${_version}&t=1"/>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/swiper-3.3.1.min.css?v=${_version}"/>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=C93b5178d7a8ebdb830b9b557abce78b"></script>
	<script src="${_RESOURCES}/js/index/position.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/index/index.js?v=${_version}&t=1.2" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<!-- 激活弹层 -->
	<div class="activation">
		<!--<img src="../../resources/imgs/index/superman.png"/>-->
		<!--激活填写-->
		<div class="activation-text">
			<p><b class="on">有车牌号用户</b><b>无车牌号用户</b></p>
			<div>
				<p>
					<input type="text" class="chejia" id="" placeholder="请输入您要激活的发动机号"/>
				</p>
				<p>
					<span id="car-address">粤</span>
					<input class="chepai" type="text" id="" placeholder="请输入您要激活的车牌号"/>
				</p>
				<p>
					<input type="number" id="password"  onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'') "  placeholder="请输入您保单上的刮刮卡号"/>
				</p>
			</div>
			<div class="btn-box">
				<button class="action">确认提交</button>
				<button class="close">关闭</button>
			</div>
			<span>激活后领取免费小保养等服务礼包</span>
			<div class="xiaci">
				<input type="checkbox" name="" id="" value="" />
				下次不再提醒
			</div>
		</div>
		<div class="activation-ok"><i></i><p>车险服务包已激活</p></div>
		<div class="select-box" id="add-box">
			<span>粤</span>
		    <span>京</span>
		    <span>津</span>
			<span>沪</span>
			<span>渝</span>
			<span>冀</span>
			<span>晋</span>
			<span>辽</span>
			<span>吉</span>
			<span>黑</span>
			<span>苏</span>
			<span>浙</span>
			<span>皖</span>
			<span>闽</span>
			<span>赣</span>
			<span>鲁</span>
			<span>豫</span>
			<span>鄂</span>
			<span>湘</span>
			<span>琼</span>
			<span>川</span>
			<span>贵</span>
			<span>云</span>
			<span>陕</span>
			<span>甘</span>
			<span>青</span>
			<span>台</span>
			<span>蒙</span>
			<span>桂</span>
			<span>藏</span>
			<span>宁</span>
			<span>新</span>
			<span>港</span>
			<span>澳</span>
		</div>
	</div>

	<!-- 头部 -->
	<header class="header">
		<span class="gps"><a href="${_ROOT}/address/chooseCity"><i id="cityname"></i></a></span>
		惠+车服
		<a class="info fr" href="${_ROOT}/msg.html"><i></i></a>
	</header>

	<!-- 车辆信息 -->
	<section class="news">
		<div class="models-warp" id="mycar">
			<div class="models-img">
				<img/>
			</div>
			<div class="models-details">
				<p id="license"></p>
				<p style="" id="car-info"></p>
			</div>
		</div>
		<a id="addcarinfo" href="${_ROOT}/brand/car1.html" style="display:none"> + 请先添加我的车型</a>
	</section>
	<!-- banner -->
	<style>
		#banner-swiper img{
			width: 100%;
			height: 100%;
		}
	</style>
	<div class="swiper-container" id="banner-swiper" style="height: 180px;">
	    <ul class="swiper-wrapper">
	        <li class="swiper-slide"><a href="javascript:void(0)"><img class="bind_user_car"  src="${_RESOURCES}/imgs/index/banner5.png"/></a></li>
			<!-- <li class="swiper-slide"><a href="javascript:void(0)"><img class="points-mall" src="${_RESOURCES}/imgs/index/banner2.jpg"/></a></li> -->
			<li class="swiper-slide"><a href="/gsbranch/showmap.html?status=1&citySet=1"><img src="${_RESOURCES}/imgs/index/banner6.png"/></a></li>
	    	<li class="swiper-slide"><a href="/gsbranch/showmap.html?status=1&citySet=2"><img src="${_RESOURCES}/imgs/index/banner2.jpg"/></a></li>

	    </ul>
	    <div class="swiper-pagination"></div>
	</div>
	<script>
		var mySwiper_0 = new Swiper ('#banner-swiper', {
		    loop: true,
		    autoplayDisableOnInteraction : false,
		    autoplay: 5000,
		    pagination: '.swiper-pagination',
		    paginationClickable: true
	  	});
	</script>


	<!-- 维修保养 -->
	<section class="service-warp" style="margin-top: 0px;">
		<div class="top-tip">
			|<span>救援服务</span>
		</div>
		<div class="service">
			<a class="xc-btn"><i></i>洗 车</a>
			<a class="by-btn"><i></i>小保养</a>
			<a class="dp-btn"><i></i>电 瓶</a>
			<a class="pq-btn"><i></i>喷 漆</a>
			<a class="dd-btn"><i></i>搭 电</a>
			<a href="${_ROOT}/didi/mydidi.html" class="di-btn"><i></i>滴滴代驾</a>
			<a class="tc-btn"><i></i>拖 车</a>
		</div>
	</section>

	<!-- 积分商城 -->
	<section class="points-mall" >
		<img src="${_RESOURCES}/imgs/index/daolujiuyuan.png">
	</section>

	<!-- 快讯 -->
	<section class="news-main">
	  	<strong>惠<sup>+</sup>快讯</strong>
		<div class="news-list fr">
			<div class="swiper-container top-silde">
			    <ul class="swiper-wrapper" id="news-list">
			        <!-- <li class="swiper-slide"><a href="#">自2月11日起所有商家已经恢复正常营业</a></li> -->
			        <!-- <li class="swiper-slide"><a href="${_ROOT}/user/news.html">保养后再出发？自驾游出行车辆保养检查</a></li> -->
					<li class="swiper-slide"><a href="${_ROOT}/user/news.html">叫车还便宜么？网约车发布新规细则</a></li>
			    </ul>
			</div>
		</div>
	</section>

	<!-- 国寿i购商城 -->
	<section class="product">
		<div class="status01">
			<a href="https://www.emateglobal.com/">
				<img src="${_RESOURCES}/imgs/index/igou01.jpg"/>
			</a>
		</div>
		<div class="status02" style="display:none;">
			<div class="igou">
				<a href="https://www.emateglobal.com/">
					<img src="${_RESOURCES}/imgs/index/igou.jpg"/>
				</a>
			</div>
			<!-- 业务员空间 -->
			<div class="salesman-box">
				<a href="javascript:void(0)">
					<img src="${_RESOURCES}/imgs/index/salesman01.png"/>
				</a>
				<!-- <a href="/static/maintain.html">
					<img src="${_RESOURCES}/imgs/index/instruction.jpg"/>
				</a> -->
			</div>
		</div>

	</section>

	<!-- 合作品牌 -->
	<section class="partner">
		<div class="top-tip">
			|<span>品牌区</span>
		</div>
		<ul class="partner-list">
			<li><img src="${_RESOURCES}/imgs/index/img1.png"/></li>
			<li><img src="${_RESOURCES}/imgs/index/img2.png"/></li>
			<li><img src="${_RESOURCES}/imgs/index/img3.png"/></li>
			<li><img src="${_RESOURCES}/imgs/index/img4.png"/></li>
			<li><img src="${_RESOURCES}/imgs/index/img5.png"/></li>
			<li><img src="${_RESOURCES}/imgs/index/img6.png"/></li>
		</ul>
	</section>

	<!-- 热销商品 -->
	<section class="goods">
		<div class="top-tip">
			|<span>热销商品</span>
		</div>
		<ul class="goods-list">
			<li>
				<a href="https://www.emateglobal.com/">
					<img src="${_RESOURCES}/imgs/index/img7.jpg"/>
				</a>
			</li>
			<li>
				<a href="https://www.emateglobal.com/">
					<img src="${_RESOURCES}/imgs/index/img8.jpg"/>
				</a>
			</li>
			<li>
				<a href="https://www.emateglobal.com/">
					<img src="${_RESOURCES}/imgs/index/img9.jpg"/>
				</a>
			</li>
			<li>
				<a href="https://www.emateglobal.com/">
					<img src="${_RESOURCES}/imgs/index/img10.jpg"/>
				</a>
			</li>
		</ul>
	</section>

	<!-- 客服热线 -->
	<section class="phone-number">
		<div class="phone">
			客服热线：<a style="color:#ff3e5b;" href="javascript:void(0)">400-101-1506</a><a style="color:#ff3e5b;display: inline-block;right: 2rem;position: absolute;" href="javascript:void(0)">立即拨打</a>
		</div>
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
	</section>
<#include "commons/footer.ftl" />

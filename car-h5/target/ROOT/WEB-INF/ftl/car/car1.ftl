 <#include "/commons/header.ftl" />
 	<title>汽车匹配</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/car/car1.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/car/car1.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>添加车辆</h1>
	</header>
	<div class="warp">
		<div class="list-main">
			<!-- 搜索 -->
			<section class="search"><input type="search" id="search" placeholder="请输入您要查找的车辆"/></section>
			<!-- 热门品牌 -->
			<section class="hot">
				<h2>热门品牌</h2>
				<div>
					<span class="dazhong">大众</span>
					<span class="fute">福特</span>
					<span class="bentian">本田</span>
					<span class="fengtian">丰田</span>
					<span class="bieke">别克</span>
				</div>
				<div>
					<span class="aodi">奥迪</span>
					<span class="xiandai">现代</span>
					<span class="xuefolan">雪佛兰</span>
					<span class="benchi">奔驰</span>
					<span class="baoma">宝马</span>
				</div>
			</section>
			<!-- 汽车列表 -->
			<section class="list" id="carpinyinlist">
				<dl>
					<dt>A</dt>
					<dt>B</dt>
					<dt>C</dt>
					<dt>D</dt>
					<dt>F</dt>
					<dt>G</dt>
					<dt>H</dt>
					<dt>J</dt>
					<dt>K</dt>
					<dt>L</dt>
					<dt>M</dt>
					<dt>N</dt>
					<dt>O</dt>
					<dt>P</dt>
					<dt>Q</dt>
					<dt>R</dt>
					<dt>S</dt>
					<dt>T</dt>
					<dt>W</dt>
					<dt>X</dt>
					<dt>Y</dt>
					
				</dl>
			</section>
		</div>
		<div class="bg"></div>
		
		<div class="sidebar">
			<h3><img id="selectBrandTitle"/><span></span></h3>
			<dl id="factorylist">

			</dl>
		</div>
	</div>
</body>
</html>
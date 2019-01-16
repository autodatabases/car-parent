 <#include "/commons/header.ftl" />
 <title>城市定位</title>
 <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/city-GPS.css?v=${_version}"/>
 <script src="${_RESOURCES}/js/oilCard/daijia/city-GPS.js?v=${_version}" type="text/javascript" charset="utf-8"></script>

</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>城市选择</h1>
	</header>
	<!-- 搜索 -->
	<section class="search"><input type="search" name="search_keyword" id="search_keyword" value="" placeholder="请输入您要查找的城市"/></section>
	<div class="city" id="city1">
		<span>当前定位城市</span>
		<p><span id="positioncity"></span></p>
	</div>
	<div class="city" id="city2">
		<span>最近访问城市</span>
		<p id="historycity"></p>
	</div>
	<ul class="search-list" id="allcity">
		<!--  <li>佛山市</li>-->
	</ul>
</body>
</html>

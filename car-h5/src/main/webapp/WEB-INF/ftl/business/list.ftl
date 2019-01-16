	<#include "/commons/header.ftl" />
 	<title>案件查询</title>
 	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/salesman/case-search.css?v=${_version}"/>
	<script src="${_RESOURCES}/js/business/list.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header class="header">
		<span class="back"><a href="/" style="display: block;width: 100%;height: 100%;"></a></span>
		<h1>案件查询</h1>
		<span class="back-home"><a href="/" style="display: block;width: 100%;height: 100%;"></a></span>
	</header>
	<!-- 搜索 -->
	<div class="search-box">
		<a href="javascript:void(0);"><i>+</i>添加新的案件</a>
		<span>工号：${(businessCode)!""}</span>
		<!--<input type="number" id="" placeholder=""/>-->
	</div>
	<!-- 案件列表 -->
	<ul class="case-lists">
		<!--<li>
			<div>
				<a>案件号：<span>123456789012345678</span></a>
				<p class="p-1"><span class="chepai">京NQ4369</span><span class="times">2016-11-24</span></p>
				<p class="p-2">出险地点：北京市 朝阳区 高和蓝峰大厦B座右边出口606室...</p>
			</div>
			<span>信息不完善请继续提交</span>
		</li>-->
	</ul>
</body>
</html>
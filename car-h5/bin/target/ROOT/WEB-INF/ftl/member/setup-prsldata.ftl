	<#include "../commons/header.ftl" />
		<title>用户设置-个人资料</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/member/setup-prsldata.css?v=${_version}"/>
		<script src="${_RESOURCES}/js/member/prsldata.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<!-- 头部 -->
		<header class="header">
			<span class="back mui-action-back"></span>
			<h1>用户设置</h1>
		</header>
		<!-- 资料列表 -->
		<section class="data-list">
			<ul>
				<li></li>
				<li><label>姓名</label><input type="text" /></li>
				<li>
				    <label>性别</label>
				    <select class="gender">
						<option value="0">保密</option>
						<option value="1">男</option>
						<option value="2">女</option>
					</select>
				</li>
				<li>
					<label>省份</label>
					<select name="" disabled="disabled">
						<option value="">广东省</option>
					</select>
					<select name="" disabled="disabled">
						<option value="">广州市</option>
					</select>
				</li>
			</ul>
			<input type="button" id="" value="提交" />
		</section>
	</body>
</html>

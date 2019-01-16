<#include "../commons/header.ftl" />
	<title>收货地址</title>
	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/repair/add-address.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/common/initAreaSelect.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
    <script src="${_RESOURCES}/js/repair/add-address.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>新增收货地址</h1>
	</header>
	<!-- 填写地址 -->
	<section class="info-list">
		<ul>
			<li>
				<label>收货人姓名</label>
				<input type="text" id="userName" placeholder="请输入姓名"/>
			</li>
			<li>
				<label>手机号码</label>
				<input type="tel" id="userTel" placeholder="请输入手机号"/>
			</li>
			<li>
				<label>省/市/区</label>
				<select id="province">
				</select>
				<select id="city">
				</select>
				<select id="area">
				</select>
			</li>
			<li>
				<label>详细地址</label>
				<input type="text" id="useradrs" placeholder="请输入详细地址"/>
			</li>
		</ul>
		<input type="button" id="action-btn" value="保存" />
	</section>
</body>
</html>
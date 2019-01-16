<#include "../commons/header.ftl" />
	<title>轮胎规格</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/com/com.css?v=${_version}"/>
	<link rel="stylesheet" type="text/css" href="/resources/css/repair/tyre-select.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/repair/tyre-select.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
   
</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>选择轮胎规格</h1>
	</header>
	<!-- 填写地址 -->
	<section class="info-list">
		<h2></h2>
		<ul>
			<li>
				<label>其他规格：</label>
				<div>
					<select id="">
					   <option></option>
					</select>
				</div>
			</li>
			<li>
				<strong>如何确定您爱车的轮胎规格</strong>
				<p>
					您的爱车适配过多规格的轮胎，请查看您爱车轮胎胎侧的规格信息选择轮胎规格（如图示）
				</p>
				<img src="${_RESOURCES}/imgs/repair/tyre-bg.png"/>
			</li>
		</ul>
		<input type="button" id="action-btn" value="下一步" />
	</section>
</body>
</html>
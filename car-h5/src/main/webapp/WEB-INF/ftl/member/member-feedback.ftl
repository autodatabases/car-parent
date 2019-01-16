	<#include "../commons/header.ftl" />
		<title>用户反馈</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/member/member-feedback.css?v=${_version}"/>
		<script src="${_RESOURCES}/js/member/feedback.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<!-- 头部 -->
		<header class="header">
			<span class="back mui-action-back"></span>
			<h1>用户反馈</h1>
		</header>
		<!-- 用户反馈 -->
		<section class="text">
			<textarea placeholder="亲，请您留下宝贵的吐槽，您的建议是我们成长的动力..." maxlength="4000"></textarea>
			<p>用户：<span></span></p>
			<input type="button" id="feedback_btn" value="提交" />
		</section>
	</body>
</html>

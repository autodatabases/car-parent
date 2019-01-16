<#include "../commons/header.ftl" />
	<title>商家信息-上传资质文件</title>
		<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/busines/busines-two.css?v=${_version}"/>
		<script src="${_RESOURCES}/js/busines/busines_two.js?v=${_version}"></script>
	</head>
	<body onload="busines_two()">
		<!-- 头部 -->
		<header class="header">
			<span class="back mui-action-back" onclick="javascript:history.back(-1);"></span>
			<h1>商家信息</h1>
		</header>
		<!-- 步骤 -->
		<section class="step">
			<span class="one">填写资质信息</span>
			<span class="two">上传资质文件</span>
			<span class="three">等待审核</span>
			<div></div>
			<i></i>
		</section>
		<!-- 上传图片 -->
		<section class="upload-warp">
			<p>营业执照<span class="up_span_y">未上传</span></p>
			<form id="submit_form" method="post" action="${_ROOT}/uploadImg?imgtype=sellerinfo" target="exec_target" enctype="multipart/form-data">
			<div class="upload_y upload">
				<span>点击上传</span>
				<input type="file" name="file_upload" />
				<img style="display:none"/>
			</div>
			</form>
			<span class="tip">上传文件需加盖公章，每张图片不能超过10MB</span>
		</section>
		<iframe id="exec_target" name="exec_target" style="display:none;"></iframe>
        <iframe id="exec_targets" name="exec_targets" style="display:none;"></iframe>  		
		<section class="upload-warp">
			<p>税务登记证副本<span class="up_span_s">未上传</span></p>
			<form id="submit_forms" method="post" action="${_ROOT}/uploadImg?imgtype=sellerinfo" target="exec_targets" enctype="multipart/form-data">
			<div class="upload_s upload">
				<span>点击上传</span>
				<input type="file" name="file_upload" />
				<img style="display:none"/>
			</div>
			</form>
		</section>
		<input type="button" class="btn two_btn" value="确认提交" />
	</body>
</html>
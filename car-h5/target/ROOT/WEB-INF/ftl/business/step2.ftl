<#include "../commons/header.ftl" />
 	<title>现场拍照上传</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
 	<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/salesman/img-submit.css?v=${_version}"/>
	<script src="${_RESOURCES}/js/business/step2.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header class="header">
		<h1>现场拍照上传</h1>
		<span class="back-home"><a href="${_ROOT}/user/bussinessIndex.html" style="display: block;width: 100%;height: 100%;"></a></span>
	</header>
	<!-- 图片上传 -->
	<div class="upload-img">
		<h2 style=""><a href="javascript:void(0)" class="edit">编辑</a>请上传定损所需的图片</h2>
		<p style="font-size: 0.75rem;padding: 0.75rem 1.3rem;color:#999999;display: none;">(提示：上传照片不少于6张，包括<span style="color: red;">车辆四个角各一张，车牌照一张，车辆损坏处至少一张.)
		已上传：<span id="remain-count">0</span>/6张</span>
		</p>
		<style>
			.delpic{
				display: none;
			}
		</style>
		<dl id="uploadImglist">
			<dd>
				<img src="${_RESOURCES}/imgs/fixorder/fix_demo_01.png"/>
				<div class="delpic"><span>×</span></div>
			</dd>
			<dd>
				<img src="${_RESOURCES}/imgs/fixorder/fix_demo_02.png"/>
				<div class="delpic"><span>×</span></div>
			</dd>
			<dd>
				<img src="${_RESOURCES}/imgs/fixorder/fix_demo_03.png"/>
				<div class="delpic"><span>×</span></div>
			</dd>
			<dd>
				<img src="${_RESOURCES}/imgs/fixorder/fix_demo_04.png"/>
				<div class="delpic"><span>×</span></div>
			</dd>
			<dd>
				<img src="${_RESOURCES}/imgs/fixorder/fix_demo_05.png"/>
				<div class="delpic"><span>×</span></div>
			</dd>
			<dd>
				<img src="${_RESOURCES}/imgs/fixorder/fix_demo_06.png"/>
				<div class="delpic"><span>×</span></div>
			</dd>
			<iframe id="exec_target" name="exec_target" style="display:none;"></iframe>
			<dt>
				<form id="submit_form" method="post" action="" target="exec_target" enctype="multipart/form-data">
					<span>+</span>
					点击上传
					<input type="file" name="file_upload" id=""/>
				</form>
			</dt>
		</dl>
	</div>
	<!-- 下一步 -->
	<input type="button" class="next-btn" id="" value="下一步" />
</body>
</html>
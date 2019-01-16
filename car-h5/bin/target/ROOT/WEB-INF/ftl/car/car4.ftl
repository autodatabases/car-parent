 <#include "/commons/header.ftl" />
 	<title>汽车匹配-确认</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/car/car4.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/car/car4.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>添加车辆</h1>
	</header>
	<div class="forms">
		<!-- 基本信息 -->
		<dl>
			<dd><label>品牌</label><img id="selectBrandTitle"/></dd>
			<dd><label>车牌号</label><input type="text" id="license" placeholder="请输入车牌号"/></dd>
			<dt>基本信息</dt>
			<dd><a href="${_ROOT}/brand/car2.html"><label id="disp_time">排量年份</label></a></dd>
			<dd><a href="${_ROOT}/brand/car3.html"><label id="time_type">销售年款</label></a></dd>
			<dd><label>行驶里程</label><input type="number"  id="mileage" type="text" value="" placeholder="请输入车辆行驶公里数"/></dd>
			
		</dl>
		<dl class="xt-list">
			<dt>车辆信息<small>（选填）</small></dt>
			<dd><label>车架号</label><input type="number" name="" id="carCode" value="" placeholder="请输入车架号"/></dd>
			<!-- 图片上传 -->
			<form id="chejiahao" action="${_ROOT}/uploadImg" method="post" enctype="multipart/form-data" target="result">
				<dd class="imgUploat">
					<input id="uploadfile" name="file_upload" type="file"></input>
					<span>+点击上传</span>
				</dd>
			</form>
		</dl>
		<p class="tip"><i></i>为了更好的匹配您的车型请上传一张清晰车架号照片</p>
		<p id="imgView"></p>
		<!-- 车辆信息 -->
		
		<input type="submit" class="btn" value="确认提交" id="submit"/>
	</div>
	<iframe name="result" id="result"  style="width:0px;height: 0px;"></iframe>
</body>
</html>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
   		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	    <title>查勘录入</title>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
		<link rel="stylesheet" href="${_RESOURCES}/css/takenode/mobiscroll-2.13.2.full.min.css">
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/takenode/takenode.css?v=${_version}"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/takenode/mobiscroll-2.13.2.full.min.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/takenode/takenode.js?v=${_version}" type="text/javascript" charset="utf-8"></script>

	</head>
	<body>
		<header class="header">
			<h1>查勘录入</h1>
		</header>
		<div class="content">
			<div class="item name">
				姓名: <input type="text" placeholder="请输入车主的姓名">
			</div>
			<div class="item phone">
				手机号: <input type="tel" onkeyup="formatPhoneNum(event,this);" placeholder="请输入车主的手机号码">
			</div>
			<div class="item dates">
				保险到期日: <input type="text" id="dates" placeholder="请选择车主保险到期日"><span></span>
			</div>
			<div class="item proposer name">
				申请人: <input type="text" placeholder="请输入申请人">
			</div>
			<div class="item1 upload">
				<h3>上传行驶证</h3>
				<form id="submit_form" method="post" action="/uploadImg?type=survey" target="exec_target" enctype="multipart/form-data">
					<div class="img-warp">
						<div class="img-box" style="display:none;"><img id="goodImg"/></div>
						<div class="upload-btn"><input type="file" name="file_upload" id="" value="" /></div>
					</div>
				</form>
				<iframe id="exec_target" name="exec_target" style="display:none;"></iframe>
			</div>
			<div class="item1">
				<button class="btn" id="suBtn">立即录入</button>
			</div>

		</div>
	</body>
</html>

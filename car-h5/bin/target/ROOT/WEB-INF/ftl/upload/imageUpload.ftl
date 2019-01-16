<%@page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<span style="color: #ff0000;"><base target="_self"></span>
<title>图片上传</title>
</head>
<body>
	<h5>图片上传</h5>
	<hr/>
	<p style="color: red"><%=request.getAttribute("errorMsg")%></p>
	<form id="form1" name="form1"
		action="<%=request.getContextPath()%>/uploadImg" method="post"
		enctype="multipart/form-data" target="result">
		<div>注:图片大小最大不能超过3M!</div>
		<div>
			<input type="file" name="file" />
		</div>
		<div>
			<input type="submit" value="上传" />
		</div>
	</form>
			<button id="viewBtn" onclick="view(200);">200x200</button>
			<button id="viewBtn" onclick="view(400);">400x400</button>
			<button id="viewBtn" onclick="view(600);">600x600</button>
			<button id="viewBtn" onclick="view(800);">800x800</button>
			<button id="viewBtn" onclick="view();">原图</button>
	<iframe name="result" id="result"></iframe><br>
	<img alt="xxx" src="" id="imgView">
</body>
</html>
<script type="text/javascript">
	function view(length) {
		var val = eval("(" + document.getElementById("result").contentWindow.document.body.innerText + ")");
	  if (val.url) {
		  if (length) {
			  document.getElementById("imgView").src = val.url + "?" + length + "x" + length;
		  } else {
			  document.getElementById("imgView").src = val.url;
		  }
	  }
	}
</script>

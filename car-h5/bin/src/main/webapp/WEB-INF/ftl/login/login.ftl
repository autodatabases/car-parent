    <#include "../commons/header.ftl" />
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/login/login.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/login/login.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${_RESOURCES}/js/login/loginverify.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
<body>
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>快速登录</h1>
		<!--<a class="register_a" href="/user/register.html">注册</a>-->
	</header>
	<form class="forms">
		<!-- 手机号 -->
		<div class="tel">
			<label></label>
			<input type="tel" id="userName" onkeyup="formatPhoneNum(event,this);" placeholder="输入手机号" value=""/>
			<input type="button" class="getcode" id="getcode"  value="获取验证码"/>
		</div>
		<!-- 验证码 -->
		<div class="pas">
			<label></label>
			<input type="number" id="code" placeholder="输入验证码"  onafterpaste="this.value=this.value.replace(/[^\d]/g,'') "/>
		</div>
	</form>
    <!-- <div class="changeNum">
        <span id="changeNum">更改手机号？</span>
    </div> -->
		<input style="margin-top: 3rem;" type="submit"  id="login" class="action" value="登录" />
		<p class="reg-agment" style="margin-top: 0rem;">登录即表示同意 <a href="${_ROOT}/reg-agrment.html">《用户服务注册协议》</a></p>
		<p class="reg-agment" style="background: url(${_RESOURCES}/imgs/login/login-phone.png) no-repeat 28%;background-size:18px 18px;margin-top: 15rem;">客服热线：<a style="color:#7b7b7b;" href="tel:400-101-1506">400-101-1506</a></p>
	<script>
		function formatPhoneNum(e,obj){
			if(e.keyCode==8){
				return;
			}
			obj.value = obj.value.replace(/[^\d\s]/g,'');
			var _this = obj;
			//_this.value=_this.value.replace(/[^(\d\s)]/g,'');
			if(_this.value.length==3){
				_this.value = _this.value+' ';
			}else if(_this.value.length==8){
				_this.value = _this.value+' ';
			}
			if(_this.value.length>13){
				_this.value = _this.value.substr(0,13);
			}
			$('#userName').change();
		}
		$("#code").on("input",function() {
			if($(this).val().length >4) {
				$(this).val($(this).val().substring(0,4)) ;
			}
		})
	</script>
</body>
</html>

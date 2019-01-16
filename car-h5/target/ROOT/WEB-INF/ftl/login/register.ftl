    <#include "../commons/header.ftl" />
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/login/login.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/login/register.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
    <script src="${_RESOURCES}/js/login/loginverify.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
<body>
	<header class="header">
		<span class="back mui-action-back"></span>
		<h1>用户注册</h1>
	</header>
	<!-- 用户选择 -->
	<div class="user-tab">
		<span class="on">车险用户</span>
		<span>普通用户</span>
	</div>
	<!-- 表单 -->
	<form class="forms">
		<!-- 手机号 -->
		<div class="tel">
			<label></label>
			<input type="tel" id="userName" onkeyup="formatPhoneNum(event,this);" placeholder="输入手机号" value=""/>
			<input type="button" class="getcode" id="getcode" value="获取验证码"/>
		</div>
		<!-- 验证码 -->
		<div class="pas">
			<label></label>
			<input type="number" id="code" maxlength="9999" onkeyup="maxValue(4,this)" placeholder="输入验证码"/>
		</div>
		<div class="car">
			<label></label>
			<select name="">
				<option value="粤">粤</option>
			    <option value="京">京</option>
			    <option value="津">津</option>
				<option value="沪">沪</option>
				<option value="渝">渝</option>
				<option value="冀">冀</option>
				<option value="晋">晋</option>
				<option value="辽">辽</option>
				<option value="吉">吉</option>
				<option value="黑">黑</option>
				<option value="苏">苏</option>
				<option value="浙">浙</option>
				<option value="皖">皖</option>
				<option value="闽">闽</option>
				<option value="赣">赣</option>
				<option value="鲁">鲁</option>
				<option value="豫">豫</option>
				<option value="鄂">鄂</option>
				<option value="湘">湘</option>
				<option value="琼">琼</option>
				<option value="川">川</option>
				<option value="黔">黔</option>
				<option value="滇">滇</option>
				<option value="陕">陕</option>
				<option value="甘">甘</option>
				<option value="青">青</option>
				<option value="台">台</option>
				<option value="蒙">蒙</option>
				<option value="桂">桂</option>
				<option value="藏">藏</option>
				<option value="宁">宁</option>
				<option value="新">新</option>
				<option value="港">港</option>
				<option value="澳">澳</option>
			</select>
			<input type="text" id="login-car0" placeholder="输入车牌号" maxlength="6" style="text-indent:5px;"/>
			<span class="car-tip">没有车牌号？</span>
			<input type="text" id="login-car1" placeholder="输入车架号" style="text-indent:5px;"/>
		</div>
		<div class="card">
			<label></label>
			<input type="number" id="login-scratch" onkeyup="maxValue(8,this)"placeholder="输入刮刮卡号"/>
		</div>
	</form>
	<p class="reg-agment">激活即表示同意 <a href="${_ROOT}/reg-agrment.html">《用户服务注册协议》</a></p>
	<input type="submit"  id="login" class="action" value="注册" />
	<div class="notes">
		<span>注册须知：</span>
		<p>1、车险用户可以享受免费服务包和商品购买</p>
		<p>2、普通用户仅可购买商品不享受车险服务包</p>
	</div>
	<script>
		function maxValue(length,obj){
			obj.value = obj.value.replace(/[^\d]/g,'');
			if(obj.value.length>length){
				obj.value = obj.value.substr(0,length);
			}
		}
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
	</script>
</body>
</html>
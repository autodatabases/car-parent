<#include "../commons/header.ftl" />
<title>验证信息</title>
<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/login/login.css?v=${_version}"/>
<script src="${_RESOURCES}/js/login/validateInfo.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
<script src="${_RESOURCES}/js/login/loginverify.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
<body>
<header class="header">
    <span class="back mui-action-back"></span>
    <h1>验证信息</h1>
    <!--<a class="register_a" href="/user/register.html">注册</a>-->
</header>
<form class="forms">
    <!-- 手机号 -->
    <div class="card">
        <label></label>
        <input type="text" id="card" placeholder="请输入已激活的车牌号"/>
    </div>
    <!-- 验证码 -->
    <div class="car">
        <label></label>
        <input type="number" id="car" placeholder="输入保单号" />
    </div>
    <div class="tel">
        <label></label>
        <input type="tel" id="userName" onkeyup="formatPhoneNum(event,this);"  placeholder="输入之前绑定手机号" value=""/>
    </div>
</form>
    <input style="margin-top: 3rem;" type="submit"  id="login" class="action" value="确定" />
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
</script>
</body>
</html>

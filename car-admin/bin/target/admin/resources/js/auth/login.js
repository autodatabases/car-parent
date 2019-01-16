$(function() {
	bindEvent();
})
// 绑定事件
function bindEvent() {
	$(document).keydown(function(event) {
		if (event.keyCode === 13) {
			login();
		}
	});
	$container = $(".warp");
	$container.on("click",'#login_sub',login);
}
var isLoading = false;
var login = function() {
	var userName = $.trim($("#userName").val());
	var password = $.trim($("#password").val());
	if (userName == '' || password == '') {
		$.tip('请输入用户名或密码！');
		return;
	}
	if(isLoading){
		$.tip('请不要频繁点击!');
		return;
	}
	isLoading = true;
	$("#processText").html('正在登录...请稍后');
	$.ajax({
		url : DOMIN.MAIN+"/auth/login",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			userName:userName,
			password:password
		},
		traditional: true,
		success : function(data, textStatus){
			isLoading = false;
			if(data.success){
				$("#processText").html('登录成功,即将跳转...');
				addCookie("CAR_ADMIN_TOKEN",data.data);
				setTimeout(function(){
					location.href = DOMIN.MAIN;
				},200)
			}else{
				$.tip(data.message);
				$("#processText").html('登录失败！'+data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			isloading = false;
			$("#processText").html('登录异常！');
		}
	});
}
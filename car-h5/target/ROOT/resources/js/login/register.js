String.prototype.trim = function() {
  return this.replace(/\s+|\s+/g, '');
}
$(document).ready(function(e){
		$(".car-tip").on('click',function(){
			if($("#login-car1").css("display")=="none"){
				$("#login-car1").css("display","block");
				$(".car select").css("display","none");
				$("#login-car0").css("display","none");
				$(".car-tip").html("没有车架号？");
			}else{
				$("#login-car1").css("display","none");
				$(".car select").css("display","block");
				$("#login-car0").css("display","block");
				$(".car-tip").html("没有车牌号？");
			}
		});
	    var user_tab=1;
		$.mode = '';
		//tab切换
		$(".user-tab span").each(function(i,e){
			$(e).on('click',function(){
				$(e).addClass("on").siblings().removeClass("on");
				if(i==0){
					$(".car").css("display","block");
					$(".card").css("display","block");
					user_tab=1;
				}else{
					$(".car").css("display","none");
					$(".card").css("display","none");
					user_tab=0;
				}
			});
		})
   		//手机号验证
		$('#userName').change(function(){
			var username = $.trim($(this).val()).trim();
			var error = JSVerify.Phone(username,false);
			if(error){
				//$.tip('请输入正确的手机号码！');
				$('#userName').focus();
				$.mode = '';
				return;
			}else{
				$.mode = 'mobile';
			}
		});
		
 		//普通注册
		var isload = false;
 		$("#login").on('click',function(){
			if(isload){
				$.tip('请不要频繁操作！');
				return;
			}
 			var _this = $(this);
 			//手机号
			var userName = $.trim($('#userName').val()).trim();
			
			if($.mode == ''){
				//请输入正确的手机和邮箱
				$.tip('请输入正确的手机号！');
				//$('#userName').focus();
				return;
			}
			if(userName==''){
				$.tip('请输入正确的手机号！');
				return;
			}
			var vCode = $('#code').val($.trim($('#code').val())).val();
			if(vCode == null || vCode == '' || vCode == undefined){
				$.tip('验证码不能为空！');
				return false;
			}
			if(getCookie('code')==null){
				$.tip('验证码不正确！');
				return false;
			}
			var login_car ="";
			var login_scratch ="";
			if(user_tab==1){
				if($("#login-car1").css("display")=="none"){
					login_car = $(".car select").val()+$.trim($('#login-car0').val()).trim();
				}else{
					login_car = $.trim($('#login-car1').val()).trim();
				}
				if(login_car == null || login_car == '' || login_car == undefined){
					$.tip('车牌号或车架号不能为空！');
					return false;
				}
				login_car=login_car.toUpperCase();
				login_scratch = $('#login-scratch').val($.trim($('#login-scratch').val())).val();
				if(login_scratch == null || login_scratch == '' || login_scratch == undefined){
					$.tip('刮刮码不能为空！');
					return false;
				}
			}
			$('#login').val('正在登录...');
			_this.prop('disabled',true);
			var load = $.loading();
			isload = true;
			$.ajax({
				url:DOMIN.MAIN+'/user/register',
				type: 'POST',
				dataType: 'json',
				cache:false,
				asycn:false,
				data:{
						userName : userName,
						level:2,
						smsCode : $("#code").val(),
						tokenCode : getCookie('code'),
						chepai:login_car,
						password:login_scratch,
						regType:user_tab,
				}, 
				error:function(data){
					isload=false;
					load.remove();
					_this.prop('disabled',false);
					$.tip("链接服务器失败！");
					$('#login').val('注册');
				},success: function(data){
					isload=false;
					load.remove();
					_this.prop('disabled',false);
					if(data.success){
						addCookie('CAR_H5_TOKEN',data.data,360);
						window.location.href = DOMIN.MAIN;
					}else{
						$.tip(data.message);
						$('#login').val('注册');
					}
				}
			});
			
 		});
 		
 	//发送验证码
	$('#getcode').on('click',function(){
		if($.mode == ''){
             $.tip('请输入正确的手机号！');
             return false;
        }
		var self = this;
		if($(self).data('issending'))return;
		$(self).data('issending',true);
		$(self).prop('disabled',true).val('正在发送...');
		//$('#js-phone-code-tip').show();
		var callback  = function(data,error){
			if(error==null && data.success){
				var smsResult = JSON.parse(data.data);
				if(smsResult.code == 200){
					//保存验证码
					addCookie('code',smsResult.obj);
					var time = 60;
					$(self).val(time+'S');
					$(self).data('sendcode-setInterval',setInterval(function(){
						if(time==0){
							clearInterval($(self).data('sendcode-setInterval'));
							$(self).prop('disabled',false).val('发送验证码').data('issending',false);
							return;
						}
						$(self).val((--time)+'S');
					},1000));
				}else{
					$.tip(smsResult.msg);
					$(self).prop('disabled',false).val('发送验证码').data('issending',false);
				}
			}else{
				$.tip(error);
				$(self).prop('disabled',false).val('发送验证码').data('issending',false);
			}
		};
		var error = null;
		var userName =$.trim($('#userName').val()).trim();
		error = JSVerify.SendPhoneCode(userName,true,callback);
		if(error){
			$.tip(error);
			$(self).prop('disabled',false).val('发送验证码').data('issending',false);
		}
	});
});

/**
 * 验证码的cookie,精确到分钟
 * @param name
 * @param value
 * @param minutes
 * @returns
 */
function addCookieForSmsCode(name,value,minutes){
	if(!minutes || isNaN(minutes)){ //默认5分钟
		minutes = 5;
	}
	var exp = new Date();
    exp.setTime(exp.getTime() + minutes*60*1000);
    document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
}

String.prototype.trim = function() {
  return this.replace(/\s+|\s+/g, '');
}
$(document).ready(function(e){
		$.mode = '';
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

			$("#userName").trigger('change');
			if($.mode == ''){
				//请输入正确的手机和邮箱
				$.tip('请输入正确的手机号！');
				//$('#userName').focus();
				return;
			}
			if(userName==""){
				$.tip('请输入正确的手机号码！');
				$('#userName').focus();
				return;
			}
			var vCode = $('#code').val($.trim($('#code').val())).val();
			if(vCode == null || vCode == '' || vCode == undefined){
				$.tip('验证码不能为空！');
				return false;
			}

			if(getCookie('send_sms_encryption_code')==null){
				$.tip('验证码不正确！');
				return false;
			}
			$('#login').val('正在登录...');
			_this.prop('disabled',true);
			var load = $.loading();
			isload = true;
			$.ajax({
				url:DOMIN.MAIN+'/user/login',
				type: 'POST',
				dataType: 'json',
				cache:false,
				asycn:false,
				data:{
						userName : userName,
						level:2,
						smsCode : $("#code").val(),
						tokenCode : getCookie('send_sms_encryption_code'),
				},
				error:function(data){
					isload=false;
					load.remove();
					_this.prop('disabled',false);
					$.tip("链接服务器失败！");
					$('#login').val('登录');
				},success: function(data){
					isload=false;
					load.remove();
					_this.prop('disabled',false);
					if(data.success){
						addCookie('CAR_H5_TOKEN',data.data,360);
						window.location.href = DOMIN.MAIN;
					}else{
						$.tip(data.message);
						$('#login').val('登录');
					}
				}
			});

 		});

 	//发送验证码
		$('#getcode').on('click',function(){
			//var userName = $('#userName').val($.trim($('#userName').val())).val(); || userName==''
			$("#userName").trigger('change');
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
						addCookieForSmsCode('send_sms_encryption_code',smsResult.obj);
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
	// $("#changeNum").on("click",function() {
	// 	window.location.href = DOMIN.MAIN + "/user/changeNum.html";
	// })
});


String.prototype.trim = function() {
  return this.replace(/\s+|\s+/g, '');
}
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
//手机号格式调整和验证手机号
	function formatPhoneNum(e,obj){
		//调整格式
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
	}
$(document).ready(function() {
	// $.mode = '';
   	// 	//手机号验证
	// 	$('#userName').change(function(){
    //         alert("**" + $.trim($(this).val()).trim() + "**");
	// 		var username = $.trim($(this).val()).trim();
	// 		var error = JSVerify.Phone(username,false);
	// 		if(error){
	// 			//$.tip('请输入正确的手机号码！');
	// 			$('#userName').focus();
	// 			$.mode = '';
	// 			return;
	// 		}else{
	// 			$.mode = 'mobile';
	// 		}
	// 	});
	//验证码长度控制
	$("#code").on("keyup",function() {
		if($(this).val().length >4) {
			var lCode = $(this).val().substring(0,4);
			$(this).val(lCode) ;
		}
	});
	//获取验证码
	$("#getCode").on('click',function() {
        var userName =$.trim($('#userName').val()).trim();
		if(JSVerify.Phone(userName,false) != null){
             $.tip(JSVerify.Phone(userName,false));
             $('#userName').focus();
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
					addCookieForSmsCode('send_sms_oilcard_code',smsResult.obj);
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
		error = JSVerify.SendPhoneCode(userName,true,callback);
		if(error){
			$.tip(error);
			$(self).prop('disabled',false).val('发送验证码').data('issending',false);
		}
	});
	//登录验证
	$("#login").on('click',function () {
			var _this = $(this);
			//手机号
            var userName = $.trim($('#userName').val()).trim();
            if(JSVerify.Phone(userName,false) != null){
                 $.tip(JSVerify.Phone(userName,false));
                 $('#userName').focus();
                 return false;
            }
			var vCode = $.trim($('#code').val());
			if(vCode == null || vCode == '' || vCode == undefined){
				$.tip('验证码不能为空！');
				return false;
			}

			if(getCookie('send_sms_oilcard_code')==null){
				$.tip('验证码不正确！');
				return false;
			}
			$('#login').val('正在登录...');
			_this.prop('disabled',true);
			//var load = $.loading();
			isload = true;
			$.ajax({
				url:DOMIN.MAIN+'/oilUser/login',
				type: 'POST',
				dataType: 'json',
				cache:false,
				asycn:true,
				data:{
						userName : userName,
						smsCode : $.trim($('#code').val()),
						tokenCode : getCookie('send_sms_oilcard_code'),
				},
				error:function(data){
					isload=false;
					//load.remove();
					_this.prop('disabled',false);
					$.tip("链接服务器失败！");
					$('#login').val('登录');
				},success: function(data){
					isload=false;
					//load.remove();
					_this.prop('disabled',false);
					if(data.success){
						addCookie('CAR_OIL_TOKEN',data.data,360);
						window.location.href = DOMIN.MAIN + "/oilUser/oilAccount.html?time="+new Date().getTime();
					}else{
						$.tip(data.message);
						$('#login').val('登录');
					}
				}
			});
	});

})

var wxopenid=getCookie('wxopenid');
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
function checkOpenId(){
	var access_code = $.getUrlParam('code');
    if (wxopenid==null){
        if (access_code==null || access_code == '' || !access_code)
        {   
        	getWxAppId();
        }
        else
        {   
            $.ajax({
                type:'post',
                url:DOMIN.MAIN+'/user/getOpenId', 
                async:false,
                cache:false,
                data:{code:access_code},
                dataType:'json',
                success:function(result){
                        if (result!=null && result.hasOwnProperty('openid') && result.openid!=""){
                     	   wxopenid = result.openid;
                           //addCookie('wxopenid',result.openid,360*2);
                           //alert('wxopenid获取成功！openid='+result.openid+"&result="+result);             
                        }else{
                        	  alert('微信身份识别失败 \n '+result);
                        }
                        
                    }
                });    
        }
    }
}
function getWxAppId(){
	$.ajax({
		url:DOMIN.MAIN+'/getWxAppId',
		type: 'POST',
		dataType: 'json',
		cache:false,
		asycn:false,
		data:{}, 
		error:function(data){
		},success: function(data){
			if(data.success){
		        var fromurl=location.href;
	            var url='https://open.weixin.qq.com/connect/oauth2/authorize?appid='+data.data
	            +'&redirect_uri='+encodeURIComponent(fromurl)+'&response_type=code&scope=snsapi_base&state=123#wechat_redirect';
	            location.href=url;
			}
		}
	});
}
checkOpenId();
$(document).ready(function(e){
		$.mode = '';
   		//手机号验证
		$('#userName').change(function(){
			var username = $.trim($(this).val()).trim();
			var error = JSVerify.Phone(username,false);
			if(error){
				//alert('请输入正确的手机号码！');
				$('#userName').focus();
				$.mode = '';
				return;
			}else{
				$.mode = 'mobile';
			}
		});
		//验证码长度控制
 		$("#code").on("keyup",function() {
			if($(this).val().length >4) {
				var lCode = $(this).val().substring(0,4);
				$(this).val(lCode) ;
			}
		});
 		//普通注册
 		$("#login").click(function(){
 			var _this = $(this);
 			//手机号
			var userName =$.trim($('#userName').val()).trim();
			
			if($.mode == ''){
				//请输入正确的手机和邮箱
				alert('请输入正确的手机号！');
				//$('#userName').focus();
				return;
			}
			var vCode = $('#code').val($.trim($('#code').val())).val();
			if(vCode == null || vCode == '' || vCode == undefined){
				alert('验证码不能为空！');
				return false;
			}
			
			$('#login').val('正在登录...');
			_this.prop('disabled',true);
			$.ajax({
				url:DOMIN.MAIN+'/user/bind',
				type: 'POST',
				dataType: 'json',
				cache:false,
				asycn:false,
				data:{
						mobile : userName,
						openId : wxopenid,
						code : $("#code").val(),
						tokenCode : getCookie('send_sms_encryption_code'),
				}, 
				error:function(data){
					_this.prop('disabled',false);
					alert("链接服务器失败！");
					$('#login').val('登录');
				},success: function(data){
					_this.prop('disabled',false);
					if(data.success){
						addCookie('wxopenid',wxopenid,360*2);
						addCookie('CAR_SELLER_TOKEN',data.data,360*2);
						window.location.href = DOMIN.MAIN;
					}else{
						alert(data.message);
						$('#login').val('登录');
					}
				}
			});
			
 		});
 		
 		
 		
 	//发送验证码
	$('#getcode').click(function(){
		if($.mode == ''){
             alert('请输入正确的手机号！');
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
					alert(smsResult.msg);
					$(self).prop('disabled',false).val('发送验证码').data('issending',false);
				}
			}else{
				alert(error);
				$(self).prop('disabled',false).val('发送验证码').data('issending',false);
			}
		};
		var error = null;
		var userName =$.trim($('#userName').val()).trim();
		error = JSVerify.SendPhoneCode(userName,true,callback);
		if(error){
			alert(error);
			$(self).prop('disabled',false).val('发送验证码').data('issending',false);
		}
	});
});
//手机验证，手机号码格式控制
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
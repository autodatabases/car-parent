$(document).ready(function(){
	var isload = false;
	$(".action-btn").on('click',function(){
		if(isload){
			$.tip("请不要频繁点击~~~");
			return;
		}
		var _this = $(this);
		isload = true;
		var businessCode=$("#businessCode").val();
		var realName=$("#realName").val();
		if(realName==""){
			$.tip("姓名不能为空");
			return;
		}
		if(businessCode==""){
			$.tip("工号不能为空");
			return;
		}
		_this.prop('disabled',true);
		$.ajax({
			url : DOMIN.MAIN+"/counterman/bindCounterman",
			type : "post",
			cache : false,
			async : true,
			dataType : "json",
			data: {
				countermanCode:$.trim(businessCode),
				realName:$.trim(realName)
			},
			traditional: true,
			success : function(data, textStatus){
				isload = false;
				_this.prop('disabled',false);
				if(data.success){
					//setTimeout(function () {
						location.href = DOMIN.MAIN+'/counterman/dataInfo.html';
					//}, 2000);
				}else{
					$.tip(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				isload = false;
				_this.prop('disabled',false);
				$.tip("服务器错误！");
			}
		});
	})
})
function maxValue(length,obj){
	// obj.value = obj.value.replace(/[^\d]/g,'');
	if(obj.value.length>length){
		obj.value = obj.value.substr(0,length);
	}
}

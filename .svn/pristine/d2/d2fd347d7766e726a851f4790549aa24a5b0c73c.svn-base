$(document).ready(function(){
	$(".action-btn").on('click',function(){
		bindBusiness();
	})
})
function bindBusiness(){
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
	$.ajax({
		url : DOMIN.MAIN+"/user/bindBusiness",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			businessCode:$.trim(businessCode),
			realName:$.trim(realName)
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				setTimeout(function () {
					location.href = DOMIN.MAIN+'/user/bussinessIndex.html';
				}, 2000);
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});
}
function maxValue(length,obj){
	obj.value = obj.value.replace(/[^\d]/g,'');
	if(obj.value.length>length){
		obj.value = obj.value.substr(0,length);
	}
}
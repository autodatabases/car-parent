var businessInfo_id=getCookie('businessInfoId');
$(function(){
	$("#businessInfo_button").click(function(){
		businessInfo_button();
	});
});
function businessInfo_button(){//提交业务员信息
	if($.trim($("#businessCode").val())==""){
		$.tip("请填写工号！");
		$("#businessInfo_button").prop("disabled",false);
		return;
	}
	if($.trim($("#businessName").val())==""){
		$.tip("请填写员工姓名！");
		$("#businessInfo_button").prop("disabled",false);
		return;
	}
	$("#businessInfo_button").prop("disabled",true);
    $.ajax({
		url : DOMIN.MAIN + "/businessInfo/addBusinessInfo",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			businessCode:$.trim($("#businessCode").val()),
			businessName:$.trim($("#businessName").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				setTimeout(function () {
					$('.tables').load('member/business-info.html?t=1.0');
				}, 2000);
			}else{
				$.tip(data.message);
				$("#businessInfo_button").prop("disabled",false);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
			$("#businessInfo_button").prop("disabled",false);
		}
	}); 	
}


















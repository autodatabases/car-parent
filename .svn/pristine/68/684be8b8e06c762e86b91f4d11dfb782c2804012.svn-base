var auto_id=getCookie('autoInfoId');
$(function(){
	$("#auto_button").click(function(){
		auto_button();
	});
	if(auto_id){
		editAutoInfo();
	}else{
		busines_id=null;
		initSearchParam();
	}
});
function auto_button(){//提交
	if($.trim($("#brand").val())==""){
		$.tip("品牌不能为空！");
		$("#auto_button").prop("disabled",false);
		return;
	}
	if($.trim($("#autolineName").val())==""){
		$.tip("车系不能为空！");
		$("#auto_button").prop("disabled",false);
		return;
	}
	if(!(/^[0-9]+$/.test($("#oilAmount").val()))){
		$.tip("请填写正确的机油量");
		$("#auto_button").prop("disabled",false);
		return;
	}
	if($.trim($("#productTime").val())==""){
		$.tip("请填写正确年份！");
		$("#auto_button").prop("disabled",false);
		return;
	}
	if($.trim($("#engineDisp").val())==""){
		$.tip("请填写正确排量！");
		$("#auto_button").prop("disabled",false);
		return;
	}
	$("#auto_button").prop("disabled",true);
    $.ajax({
		url : DOMIN.MAIN + "/autoinfo/addAutoInfo",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			id:auto_id,
			brand:$.trim($("#brand").val()),
			autolineName:$.trim($("#autolineName").val()),
			productTime:$.trim($("#productTime").val()),
			engineDisp:$.trim($("#engineDisp").val()),
			oilAmount:$.trim($("#oilAmount").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				setTimeout(function () {
					$('.tables').load('autoinfo/auto-list.html?t=1');
				}, 2000);
			}else{
				$.tip(data.message);
				$("#auto_button").prop("disabled",false);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
			$("#auto_button").prop("disabled",false);
		}
	}); 	
}
function editAutoInfo(){//车型修改
	$.ajax({
		url : DOMIN.MAIN + "/autoinfo/findAutoInfo",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			autoInfoId:auto_id
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				delCookie('autoInfoId');	
				$("#brand").val(data.data.brand);
				$("#autolineName").val(data.data.autolineName);
				$("#productTime").val(data.data.productTime);
				$("#engineDisp").val(data.data.engineDisp);
				$("#oilAmount").val(data.data.oilAmount);
			}else{
				$.tip("查询车型信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function initSearchParam(){
	if(sessionStorage.getItem('autoinfo.edit.brand')){
		$("#brand").val(sessionStorage.getItem('autoinfo.edit.brand'));
		sessionStorage.removeItem('autoinfo.edit.brand');
	}
	if(sessionStorage.getItem('autoinfo.edit.autolineName')){
		$("#autolineName").val(sessionStorage.getItem('autoinfo.edit.autolineName'));
		sessionStorage.removeItem('autoinfo.edit.autolineName');
	}
	if(sessionStorage.getItem('autoinfo.edit.productTime')){
		$("#productTime").val(sessionStorage.getItem('autoinfo.edit.productTime'));
		sessionStorage.removeItem('autoinfo.edit.productTime');
	}
	if(sessionStorage.getItem('autoinfo.edit.engineDisp')){
		$("#engineDisp").val(sessionStorage.getItem('autoinfo.edit.engineDisp'));
		sessionStorage.removeItem('autoinfo.edit.engineDisp');
	}
}


















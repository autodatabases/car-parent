var auto_id=getCookie('autoInfoId');
$(function(){
	$("#autopart_button").click(function(){
		autopart_button();
	});
	$("#partsTypeId").on('change',function(){
		var html = '';
		if($("#partsTypeId").val()==2){
			html += '<option value="MAHLE">马勒</option>';
			html += '<option value="MANNFILTER">曼牌</option>';
			html += '<option value="BOSCH">博世</option>';
		}else{
			html += '<option value="Castrol">嘉实多</option>';
		}
		$("#brand").empty().html(html);
	});
	$("#partsTypeId").trigger('change');
});
function autopart_button(){//提交
	if(!(/^[0-9]+$/.test($("#salePrice").val()))){
		$.tip("请填写正确的销售价格");
		$("#autopart_button").prop("disabled",false);
		return;
	}
	if($.trim($("#productCode").val())==""){
		$.tip("请填写产品编码！");
		$("#autopart_button").prop("disabled",false);
		return;
	}
	$("#autopart_button").prop("disabled",true);
    $.ajax({
		url : DOMIN.MAIN + "/autopart/addAutoPart",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			partsTypeId:$("#partsTypeId").val(),
			brand:$("#brand").val(),
			productCode:$.trim($("#productCode").val()),
			salePrice:$.trim($("#salePrice").val()),
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				setTimeout(function () {
					$('.tables').load('autoinfo/part-list.html?t=1');
				}, 2000);
			}else{
				$.tip(data.message);
				$("#autopart_button").prop("disabled",false);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
			$("#autopart_button").prop("disabled",false);
		}
	}); 	
}


















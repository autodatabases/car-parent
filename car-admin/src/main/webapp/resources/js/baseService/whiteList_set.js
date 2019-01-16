//白名单设置
$('#oilcard_button').click(function(){
	cai_dot_list.dataList()
})

var cai_dot_list = {};

cai_dot_list.dataList = function() {
	$.ajax({
		url : DOMIN.MAIN + "/oilrecordwhitelist/addOilRecordWhiteList",
		type : "post",
		async : true,
		dataType : "json",
		data :{
			userAccount:$('#userAccount').val(),
			openQuota:$('#openQuota option:selected').val(),
			remark:$("#remark").val()
		},
		success : function(data, textStatus) {
			// console.log(data)
			var serviceTelephone=$('#userAccount').val();
			var openQuota=$('#openQuota option:selected').val();
			if(serviceTelephone==''){
				$.tip('请输入用户账号')
				return;
			}
			if(!(/^1[34578]\d{9}$/.test(serviceTelephone))){ 
				$.tip("请输入正确的用户账号"); 
				$("#busines_button").prop("disabled",false);		
				return; 
			} 
			if(openQuota=="0"){
					$.tip("请选择额度");
					$("#oilcard_button").prop("disabled",false);
					return;
			}
			// console.log(data);
			if(data.message){
				$.tip(data.message);
			}else{
				$.tip('开通白名单成功！！！')
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}


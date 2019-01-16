var member_id=getCookie('memberId');
$(function(){
	initProvince("province");
	initCity("province","city");
	initArea("province","city","area");
	if(member_id){
		editbusines();
	}else{
		member_id=null;
		$("#member_fanhui").css("display","none");
	}
	
	$("#member_button").click(function(){
		member_button();
	});
});
function editbusines(){//商家修改
	$.ajax({
		url : DOMIN.MAIN + "/user/queryUserForEdit",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			userId:member_id
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				delCookie('memberId');	
				$("#nickName").val(data.data.nickName);
				$("#name").val(data.data.name);
				if(data.data.province){
					var province_clearInterval=setInterval(function showTime(){
						if($("#province option").size()>2){
							clearInterval(province_clearInterval);
							$("#province option").each(function(i,e){
								if($(e).html()==data.data.province){
									$(e).prop("selected","selected");
									$("#province").change();
									var city_clearInterval=setInterval(function showTime(){
										if($("#city option").size()>2){
											clearInterval(city_clearInterval);
											$("#city option").each(function(j,a){
												if($(a).html()==data.data.city){
													$(a).prop("selected","selected");
													$("#city").change();
													var area_clearInterval=setInterval(function showTime(){
														if($("#area option").size()>2){
															clearInterval(area_clearInterval);
															$("#area option").each(function(y,k){
																if($(k).html()==data.data.area){
																	$(k).prop("selected","selected");
																	//$("#area").change();
																	return true;
																}
															})
														}
													},300);
													return true;
												}
											})
										}
									},300);
									return true;
								}
							})
						}
					}, 300);
				}
				$("#autoBrand").val(data.data.autoBrand);
				$("#autoType").val(data.data.autoType);
				$("#autoLine").val(data.data.autoLine);
				$("#productYear").val(data.data.productYear);
				$("#engineDisp").val(data.data.engineDisp);
				$("#license").val(data.data.license).prop("disabled","disabled");
				$("#xiecheTimes").val(data.data.xiecheTimes);
				$("#dianpingTimes").val(data.data.dianpingTimes);
				$("#baoyangTimes").val(data.data.baoyangTimes);
				$("#chejia").val(data.data.cheJia);
				if(!data.data.license){
					$("#autoBrand").prop("disabled","disabled");
					$("#autoType").prop("disabled","disabled");
					$("#autoLine").prop("disabled","disabled");
					$("#productYear").prop("disabled","disabled");
					$("#engineDisp").prop("disabled","disabled");
					$("#xiecheTimes").prop("disabled","disabled");
					$("#dianpingTimes").prop("disabled","disabled");
					$("#baoyangTimes").prop("disabled","disabled");
					$("#chejia").prop("disabled","disabled");
				}
				
			}else{
				$.tip("查询信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function member_button(){//提交
	$("#member_button").prop("disabled",true);
	var province= $("#province option:selected").text();
	var city= $("#city option:selected").text();
	var area= $("#area option:selected").text();
	if(province=="选择省份"){
		 $.tip("请选择省份");
		 $("#member_button").prop("disabled",false);
		 return;
	}
	if(city=="选择城市"){
		 $.tip("请选择城市");
		 $("#member_button").prop("disabled",false);
		 return;
	}
	if(area=="选择市区"){
		 $.tip("请选择市区");
		 $("#member_button").prop("disabled",false);
		 return;
	}
    $.ajax({
		url : DOMIN.MAIN + "/user/createOrUpdateUserInfo",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			id:member_id,
			nickName:$("#nickName").val(),
			name:$("#name").val(),
			autoBrand:$("#autoBrand").val(),
			autoType:$("#autoType").val(),
			autoLine:$("#autoLine").val(),
			productYear:$("#productYear").val(),
			engineDisp:$("#engineDisp").val(),
			license:$("#license").val(),
			xiecheTimes:$("#xiecheTimes").val(),
			dianpingTimes:$("#dianpingTimes").val(),
			baoyangTimes:$("#baoyangTimes").val(),
			province:province,
			city:city,
			area:area,
			cheJia:$("#chejia").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				setTimeout(function () {
					$('.tables').load('member/member-list.html');
				}, 2000);
			}else{
				$.tip(data.message);
				$("#member_button").prop("disabled",false);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
			$("#member_button").prop("disabled",false);
		}
	}); 

	
}


















$(document).ready(function(){
	$('#action-btn').on('click',submit);
	initProvince("province");
	initCity("province","city");
	initArea("province","city","area");
	var province_clearInterval=setInterval(function showTime(){
		if($("#province option").length>0){
			clearInterval(province_clearInterval);
			$("#province option").each(function(i,e){
				if($(e).html()=="广东"){
					$(e).attr("selected",true);
					$("#province").change();
					return;
				}
			});
		}
	}, 300);
}); 
// 提交验证 
 function submit(){
 	var username = $.trim($('#userName').val());
 	var userTel = $.trim($('#userTel').val());
 	var useradrs = $.trim($('#useradrs').val());
 	
 	var provinceId = $('#province').val();
 	var cityId = $('#city').val();
 	var areaId = $('#area').val();
 	
 	var protext =$('#province').find('option:selected').text();
 	var citytext =$('#city').find('option:selected').text();
 	var areatext =$('#area').find('option:selected').text();
 	var otext = protext+citytext+areatext;
 	if(protext=="选择省"){
		$.tip('请选择省份');
		return;
	}
	if(citytext=="选择市"){
		$.tip('请选择市');
		return;
	}
	if(areatext=="选择区"){
		$.tip('请选择区域');
		return;
	}
 	if(username ==""){
		$.tip('请输入收货人姓名');
		return;
	};
	
	if (!/^\d{11}$/.test(userTel)) {
		$.tip('请输入正确的手机号');
		return ;
	};
	
	if(provinceId==''||city==''||area==''){
		$.tip('请选择地址');
		return;
	};
	
	if(useradrs ==""){
		$.tip('请输入详细地址');
		return;
	};
	$.ajax({
		url : DOMIN.MAIN+"/address/addAddress",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			userName:username,
			provinceId:provinceId,
			cityId:cityId,
			areaId:areaId,
			proCityArea:encodeURI(otext),
			mobile:userTel,
			addressContent:encodeURI(useradrs),
			status:0
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				$.tip('保存成功');
				setTimeout(function(){location.href=DOMIN.MAIN+"/powerorder.html?type=0"},1000);
				//setTimeout(function(){window.history.go(-1)},1000)
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});
 }
		




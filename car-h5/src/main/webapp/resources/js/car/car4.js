var autoId;
$(function(){
	$("#selectBrandTitle").prop('src',sessionStorage.getItem('carlogo'));
	$("#selectBrandTitle").after(sessionStorage.getItem('carname'));
	var car4 = JSON.parse(sessionStorage.getItem('car4'));
	if(car4.length>0){
		var car = car4[0];
		autoId = car.id;
		$("#disp_time").after(car.enginedisp+' '+car.firstproducttime+'年');
		var desc = '';
		if(car.transdesc == 'AMT'){
			desc = '自动';
		}else if(car.transdesc == 'MT'){
			desc = '手动';
		}else if(car.transdesc == 'DCT'){
			desc = '双离合';
		}else if(car.transdesc == 'CVT'){
			desc = '无极变速';
		}
		$("#time_type").after(car.enginedisp+' '+desc+car.drivingtype);
	}
	
	$("#uploadfile").change(function(){
		if(isloading){
			$.tip('正在提交...');
			return;
		}
		load = $.loading();
		isloading = true;
		$("#chejiahao").submit();
		setTimeout(function(){
			var val = eval("(" + document.getElementById("result").contentWindow.document.body.innerText + ")");
			var img = $('<img src="'+ val.url+'"/>').appendTo($("#imgView").empty());
			load.remove();
			isloading = false;
		},2000);
	});
	$("#submit").on('click',submit);
})
var isloading = false;
//正在加载的对象
var load;
//提交车辆信息
function submit(){
	if(isloading){
		$.tip('正在提交...');
		return;
	}
	if($.trim($("#license").val())==''){
		$.tip('车牌号必填！');
		$("#license").focus();
		return;
	}
	load = $.loading();
	isloading = true;
	$.ajax({
		url : DOMIN.MAIN+"/user/setCarInfo",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			autoId:autoId,
			pic:$("#imgView").find('img').attr('src'),
			carCode:$("#carCode").val(),
			mileage:$("#mileage").val(),
			license:$("#license").val()
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				load.remove();
				isloading = false;
				$.tip('提交成功！');
				setTimeout(function(){
					location.href = DOMIN.MAIN;
				},1000);
				
			}else{
				$.tip(data.message);
				load.remove();
				isloading = false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
			load.remove();
			isloading = false;
		}
	});
}

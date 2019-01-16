String.prototype.trim = function() {
	  return this.replace(/\s+|\s+/g, '');
	}   
$(document).ready(function(){
	$(".next-btn").on('click',function(){
		step1();
	});
	var timeval = getNowTime();
	timeval = timeval.substr(0,10)+'T'+timeval.substr(11,5)+':00';
	$("#outTime").val(timeval);
})
function maxValue(length,obj){
	obj.value = obj.value.replace(/[^\d]/g,'');
	if(obj.value.length>length){
		obj.value = obj.value.substr(0,length);
	}
}
function getNowTime(){
	var d = new Date();
	var month = d.getMonth()+1;
	var day = d.getDate();
	var hour = d.getHours();
	var minute = d.getMinutes();
	if(month < 10){
		month = "0"+month;
	}
	if(day < 10){
		day = "0"+day;
	}
	if(hour < 10){
		hour = "0"+hour;
	}
	if(minute < 10){
		minute = "0"+minute;
	}
	return d.getFullYear()+'-'+month+'-'+day + ' ' + hour + ':'+minute;
}
function step1(){
	var outTime=$("#outTime").val();
	if(outTime==""){//获取系统当前时间为默认时间
		outTime = getNowTime();
	}
	outTime=outTime.replace("T"," ");
	outTime=outTime.replace("年","-");
	outTime=outTime.replace("月","-");
	var reg=/[\u4E00-\u9FA5]/g;
	outTime=outTime.replace(reg,'');
	if(outTime.length>16){
		outTime = outTime.substr(0,16);
	}
	var userName=$.trim($("#userName").val());
	if(userName==""){
		$.tip("请填写车主姓名");
		return;
	}
	var userPhone=$("#userPhone").val().trim();
	if(!/^[0-9]{11}$/.test(userPhone)){
		$.tip("请填写正确的手机号");
		return;
	}
	var chePai=$.trim($("#chePai").val());
	if(chePai==""){
		$.tip("请填写出险车牌号");
		return;
	}
	var carBrand=$.trim($("#carBrand").val());
	if(carBrand==""){
		$.tip("请填写汽车品牌！");
		return;
	}
	var money=$.trim($("#money").val());
	if(money==""){
		$.tip("请填写预估价格！");
		return;
	}
	if(money.length>7){
		$.tip("预估价格过高！");
		return;
	}
	$.ajax({
		url : DOMIN.MAIN+"/order/createFixOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data:{
			step:1,
			userName:userName,
			userPhone:userPhone,
			chePai:chePai,
			carBrand:carBrand,
			money:money
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				location.href=DOMIN.MAIN+'/user/bussinessStep2.html?id='+data.data.id;
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});
}
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
}
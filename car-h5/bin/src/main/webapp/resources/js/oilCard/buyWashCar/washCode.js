var orderNo = $.getUrlParam("orderNo");
$(document).ready(function() {
	$("#status").html("");
	getData();
	$(".btn").on('click',function() {
		location.href = "http://sd.auto1768.com:8078/ShengDaPointQuery/point/city.jhtm?source=GDGSCX";
	})
});
function getData() {
	$.ajax({
		type:"post",
		url: DOMIN.MAIN + '/washcharge/getCarWashByOrder',
		cache:false,
		async:true,
		dataType: "json",
		data : {
			orderNo: $.trim(orderNo)
		},
		success: function(data) {
			if(data.success){
				setData(data.data.carWash);
			}else{
				$.tip(data.message);
			}
		},
		error: function(data) {
			$.tip("链接服务器失败!");
		}
	});
}
//设置核销码以及过期时间
function setData(data){
	$(".codeImg img").attr('src', DOMIN.MAIN + '/carwashsheng/getQrCode?barCode=' + encodeURIComponent(data.barCode));
	$(".code").html(data.orderCode);
	//日期转换
	var newDate = new Date();
	newDate.setTime(data.couponExpirDate);
	var showDate = newDate.getFullYear()+"-"+(newDate.getMonth()-0 + 1)+"-"+newDate.getDate();
	$(".data").html(showDate);
}

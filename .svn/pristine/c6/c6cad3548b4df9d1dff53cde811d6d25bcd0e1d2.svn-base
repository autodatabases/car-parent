$(function(){
	var orderNo=sessionStorage.getItem('order_trackingid');
	$.ajax({
		url : DOMIN.MAIN+"/order/getOrderTrace",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			orderNo:orderNo
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
			    $(".bianhao p").eq(0).find("span").html(data.data.orderNo);
				// data.data.expressNo = "123456789";
				if(data.data.expressNo !== null && data.data.expressNo !== "") {
					$(".bianhao p").eq(2).show().find("span").html(data.data.expressNo);
				}
				if(data.data.newTime){
					$(".tracking-icon").addClass("tracking-icon0");
					$(".tracking-list li").eq(0).css("display","block").find("span").html($.formatDate(data.data.newTime));

				}else{
					$(".tracking-icon").removeClass("tracking-icon0");
				}
				if(data.data.confirmTime){
					$(".tracking-icon").addClass("tracking-icon2");
					$(".tracking-list li").eq(1).css("display","block").find("span").html($.formatDate(data.data.confirmTime));
				}else{
					$(".tracking-icon").removeClass("tracking-icon2");
				}
				if(data.data.deliverTime){
					$(".tracking-icon").addClass("tracking-icon3");
					$(".tracking-list li").eq(2).css("display","block").find("span").html($.formatDate(data.data.deliverTime));
				}else{
					$(".tracking-icon").removeClass("tracking-icon3");
				}
				if(data.data.completTime){
					$(".tracking-icon").addClass("tracking-icon4");
					$(".tracking-list li").eq(3).css("display","block").find("span").html($.formatDate(data.data.completTime));
				}else{
					$(".tracking-icon").removeClass("tracking-icon4");
				}
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			load.remove();
		}
	});
})

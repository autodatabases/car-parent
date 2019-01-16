$(document).ready(function(){
	order_detail();
})
function order_detail(){
	var orderNo = $.getUrlParam('orderNo');
	$.ajax({
		url : DOMIN.MAIN+"/order/fiexOrderDetail",
		type : "post",
		cache : false,
		async : true,
		data : {orderNo:orderNo},
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				$(".names").html(data.data.userName);
				$("#userPhone").html(data.data.userPhone);
				$(".case-details li").eq(1).find("span").html(data.data.chePai);
				$(".case-details li").eq(2).find("span").html(data.data.carBrand);
				/*$(".case-details li").eq(3).find("span").html(data.data.money);*/
/*				var outTime=$.formatDate(data.data.outTime)
				outTime=outTime.substring(0,outTime.length-8);
				$(".times").html(outTime);*/
				var userPic=data.data.userPic;
				$(".img-box").html("没有图片！");
				if(userPic && userPic !=null && userPic !=''){
					userPic=userPic.split(",");
					var html="";
					for(var i=0;i<userPic.length;i++){
						html+='<div><img src="'+DOMIN.MAIN+userPic[i]+'"/></div>';
					}
					$(".img-box").html(html);
				}
				if(data.data.money){
					$(".money").html(data.data.money);
				}else{
					$(".money").html('0.0');
				}
				if(data.data.sellerRemark){
					$(".case-details li").eq(6).find("span").html(data.data.sellerRemark);
				}else{
					$(".case-details li").eq(6).find("span").html('无');
				}
				$(".case-details li").eq(7).find("span").html(data.data.sellerName);
				$(".case-details li").eq(8).find("span").html(data.data.sellerAddress);
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});
}



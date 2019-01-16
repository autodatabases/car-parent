$(document).ready(function() {
	$("#status").html("");
	var type = $.getUrlParam("type");
	var orderNo = $.getUrlParam("orderNo");
	if(type==1){//订单列表页跳转过来
		$.ajax({
			type:"post",
			url: DOMIN.MAIN + '/carwashsheng/getCarWashByOrder',
			cache:false,
			async:true,
			dataType: "json",
			data : {
				orderNo: $.trim(orderNo)
			},
			success: function(data) {
				if(data.success){
					setData(data.data.carWash);
					if(data.data.carWash.finishTime!=null){
						$("#status").html("已使用");
					}else if(data.data.isExpire){
						$("#status").html("已过期");
					}else{
						$("#status").html("未使用");
					}
				}else{
					$.tip(data.message);
				}
			},
			error: function(data) {
				$.tip("链接服务器失败!");
			}
		});
		$(".back").addClass("mui-action-back");
	}else{
		//$.tip('系统维护中...请稍后来试,如有问题，请联系客服：400-867-1993');
		//return;
		$.ajax({
			type:"post",
			url: DOMIN.MAIN + '/carwashsheng/sendCarWashSheng',
			cache:false,
			async:true,
			dataType: "json",
			success: function(data) {
				if(data.success){
					setData(data.data);
				}else{
					$.tip(data.message);
				}
			},
			error: function(data) {
				$.tip("链接服务器失败!");
			}
		});
		$(".back").click(function(){
			window.location.href=DOMIN.MAIN+"/carwashsheng/carWash.html";
		});
		/*var u = navigator.userAgent;
		if(!!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)){
			setBack("/carwash/carWash.html");
		}*/
		$(".myTip").css("display","block");
		$(".myMsg").css("display","block");
	}
});
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

function setBack(url){
	 pushHistory();
     var bool=false; //控制微信 进页面就触发 popstate事件
     setTimeout(function(){
           bool=true;
     },100);
     window.addEventListener("popstate", function(e) {
       if(bool){
    	   	window.location.href=DOMIN.MAIN + url;
       }
     }, false);
}

function pushHistory() {
    var state = {
        title: "title",
        url: "#"
    };
    window.history.pushState(state, "title", "#22");
}

$(document).ready(function(){
	order_list();
	$(".exitLogin").on('click',function(){
		delCookie("CAR_H5_TOKEN");
		location.href=DOMIN.MAIN+"/user/login.html";
	});
	$(".phone").on("click",function() {
		$(".tip-con").show();
	});
	$(".tip-close").on("click",function() {
		$(".tip-con").hide();
	})
});
function order_list(){
	$.ajax({
		url : DOMIN.MAIN+"/user/getUserInfo",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				$(".header").html("");
				var html="";
					html+='<div></div>';
					html+='<p>'+data.data.name+'</p>';
					html+='<a href="'+DOMIN.MAIN+'/msg.html" class="msg-btn"><i></i></a>';
					sessionStorage.setItem('memberName',data.data.name);
				$(".header").html(html);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){

		}
	})
};

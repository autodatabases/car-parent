$(document).ready(function(){
	order_list();
});
function order_list(){
	$.ajax({
		url : DOMIN.MAIN+"/user/myCars",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			$(".garage-list").html("");
			var html="";
			if(data.success){
				if(data.list.length>0){
					data = data.list[0];
					html+='<li class="on">';
					html+='<i>默认</i>';
					html+='<div class="img-box"><img src="'+DOMIN.MAIN+'/resources/imgs/logo/'+data.logo+'.png"/></div>';
					html+='<div class="text-box">';
					html+='<span>'+data.chePai+'</span>';
					var autoType="";
					if(data.autoType){
						autoType=data.autoType;
					}
					var engineDisp="";
					if(data.engineDisp){
						engineDisp=data.engineDisp;
					}
					var productYear="";
					if(data.productYear){
						productYear=data.productYear+'年产';
					}
					html+='<p>'+data.autoBrand+' '+autoType+' '+engineDisp+' '+productYear+'</p>';
					html+='</div>';
					//html+='<a href="'+DOMIN.MAIN+'/brand/car1.html">编辑</a>';
					html+='</li>';
					$(".garage-list").html(html);
				}else{
					//$(".garage-list").html('<center style="line-height:100px;">您还没有添加车型。</center>');
					$(".garage-list").html('<div style="font-size:0.75rem;text-align:center;clear:both;margin:1rem 0;padding:0;height:19.3rem;">没有查询到数据。</div>');
				}
			}else{
				$.tip(data.message);
			}
	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	})
};
  
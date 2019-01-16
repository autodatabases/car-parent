$(function(){
	$(".info-list h2").html(sessionStorage.getItem('usercarinfo'));
	var tyre_select="";
	$.ajax({
		url : DOMIN.MAIN+"/order/matchTyreProduct",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
		},
		traditional: true,
		success : function(data, textStatus){
			var html="";
			var htmls="";
			if(data.success){
			    for(var i=0;i<data.list.length;i++){
					html+="<li class='tyre_li' rel="+data.list[i].price+">"+data.list[i].product+"</li>"
				}
				for(var i=0;i<data.datasetLists.allTyre.length;i++){
					htmls+="<option>"+data.datasetLists.allTyre[i].frontSize+"</option>"
				}
				$(".info-list li").eq(0).before(html);
				$(".info-list select").append(htmls);
				$(".tyre_li").each(function(i,e){
					$(e).on('click',function(){
						$(e).addClass("on").siblings().removeClass("on");
						tyre_select=$(e).html();
						sessionStorage.setItem('price',$(e).attr("rel"));
					});
				});
				$(".info-list select").change(function(){
					$(".tyre_li").removeClass("on");
					tyre_select="";
				});
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	})
	$("#action-btn").on('click',function(){
          tyreselect(tyre_select);
	})
})

function tyreselect(tyre_select){
	var sellerId=$.getUrlParam('sellerId');
	var tyre_selects=$(".info-list select").val();
	if(tyre_select){
		sessionStorage.setItem('tyre_select',tyre_select);
	}else if(tyre_selects){
		sessionStorage.setItem('tyre_select',tyre_selects);
	}else{
		$.tip("请选择轮胎规格！");
		return;
	}
	sessionStorage.setItem("sellerId",sellerId);
	if(sellerId){
		location.href=DOMIN.MAIN+"/powerorder.html?type=2";
	}else{
		location.href=DOMIN.MAIN+"/dot.html?type=1";
	}
}










var pageNo = 1;
var pageSize = 10;
var hasNext = true;
var orderStatus=1;
$(function(){
	$(".list dt").find("a").each(function(i,e){//4种状态切换默认为待定损
		$(e).click(function(){
		   orderStatus=$(e).attr("rel");
		   $(e).addClass("on").siblings().removeClass("on"); 
		   pageNo = 1;
		   order_repair(orderStatus);
		   fixOrderSummary();
		});
	});
	order_repair(orderStatus);
	fixOrderSummary()
	$(window).scroll(function(){
		var scrollHeight = $(document).height();
	　	var scrollTop = $(document).scrollTop();
	　　	var windowHeight = document.body.clientHeight;
	　　	if(scrollTop + windowHeight >= scrollHeight){
	　　　　	nextPage();
	　　	}
	});
})
function fixOrderSummary(){//查询订单个数
	$.ajax({
		type:'post',
		url:DOMIN.MAIN+'/order/fixOrderSummary', 
		async:false,
		cache:false,
		dataType:'json',
		success:function(result){
			if (result.success){
				$(".list dt").find("a").eq(0).find("i").html(result.data.waiteForFix);
				$(".list dt").find("a").eq(1).find("i").html(result.data.waiteForCheck);
				$(".list dt").find("a").eq(2).find("i").html(result.data.checkOk);
				$(".list dt").find("a").eq(3).find("i").html(result.data.complet);
			}else{
				$.tip(result.message);
			}
		}
    }); 
}
function nextPage(){
	if(hasNext){
		pageNo ++;
		orderStatus=$(".on").attr("rel");
		order_repair(orderStatus);
	}else{
		$.tip('没有更多了！');
	}
}
function order_repair(orderStatus){//查询列表
	$.ajax({
		type:'post',
		url:DOMIN.MAIN+'/order/fixOrderList', 
		async:false,
		cache:false,
		dataType:'json',
		data : {
			pageNo:pageNo,
			pageSize:pageSize,
			orderStatus:orderStatus
		},
		success:function(result){
			if (result.success){
				var html="";
				if(pageNo==1){
					$(".list dd").remove();
					$(".list p").remove();
				}
				if(result.pageInfo.pageCount == result.pageInfo.pageIndex){
					hasNext = false;
				}else{
					hasNext = true;
				}
				if(result.list.length>0){
					for(var i=0;i<result.list.length;i++){
						if(result.list[i].carBrand==null){
							result.list[i].carBrand = '未知';
						}
						html+='<dd onclick=order_detail("'+result.list[i].orderNo+'","'+orderStatus+'")>'
						html+='<div class="list-top">案件编号：'+result.list[i].orderNo+'</div>';
						html+='<div  class="list-main"><div>';
						html+='<p><label>汽车品牌</label>：<span>'+result.list[i].carBrand+'</span></p>';
						html+='<p><label>车牌号</label>：<span>'+result.list[i].chepai+'</span></p>';
						html+='<p><label>车主姓名</label>：<span>'+result.list[i].userName+'</span></p>';
						html+='<p><label>车主电话</label>：<span>'+result.list[i].userPhone+'</span></p></div>';
						if(result.list[i].status==1){
							html+='<span>待入场</span>';
						}else if(result.list[i].status==2){
							html+='<span>已入场</span>';
						}else if(result.list[i].status==3){
							html+='<span>审核通过</span>';
						}else if(result.list[i].status==4){
							html+='<span>已结案</span>';
						}
						
						html+='</div>';
						html+='<div class="list-top list-bottom">维修<span>'+$.formatDate(result.list[i].createTime)+'</span></div>';
						html+='</dd>';
					}
				}else{
					html+='<p style="text-align:center;font-size:14px;color:red;padding-top:20px;">暂时没有数据</p>';
				}
				$(".list").append(html);
			}else{
				$.tip(result.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
    }); 
}
function order_detail(orderNo,orderStatus){//跳转详情页
    location.href=DOMIN.MAIN+"/repair/order-detail.html?orderNo="+orderNo;
}
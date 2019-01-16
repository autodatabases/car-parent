checkLogin();
var pageNo = 1;
var pageSize = 10;
var hasNext = true;
var orderStatus = $.getUrlParam('orderStatus');
function orders_Explain(){//接单页面是否阅读服务说明
	var orders_Explain_img=$("#orders_Explain_img");
	var num=orders_Explain_img.attr("rel");
	if(num==1){
	    orders_Explain_img.css("background","url('"+DOMIN.MAIN+"/resources/imgs/xuanzhong.png')");
		orders_Explain_img.attr("rel","2");
	}else{
		orders_Explain_img.css("background","url('"+DOMIN.MAIN+"/resources/imgs/weixuan.png')");
		orders_Explain_img.attr("rel","1");
		}
};
$(function () {
	sellerGetOrderSummary();
	queryOrder(orderStatus);
	$(".list dt").find("a").each(function(i,e){
		$(e).click(function(){
			$(e).addClass("on").siblings("a").removeClass("on");
			pageNo=1;
			orderStatus=$(e).attr("rel");
			queryOrder(orderStatus);
			sellerGetOrderSummary();
		});
	});
	$(window).scroll(function(){
		var scrollHeight = $(document).height();
	　	var scrollTop = $(document).scrollTop();
	　　	var windowHeight = document.body.clientHeight;
	　　	if(scrollTop + windowHeight >= scrollHeight){
	　　　　	nextPage();
	　　	}
	});
});
function sellerGetOrderSummary(){//查询订单个数
	$.ajax({
		type:'post',
		url:DOMIN.MAIN+'/order/sellerGetOrderSummary', 
		async:false,
		cache:false,
		dataType:'json',
		success:function(result){
			if (result.success){
				$(".list dt").find("a").eq(0).find("i").html(result.data.waiteService);
				$(".list dt").find("a").eq(1).find("i").html(result.data.servicing);
				$(".list dt").find("a").eq(2).find("i").html(result.data.complate);
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
		queryOrder(orderStatus);
	}else{
		$.tip('没有更多了！');
	}
}
function queryOrder(orderStatus){
    $.ajax({
        type:'post',
        url:DOMIN.MAIN+'/order/queryOrderList', 
        async:false,
        cache:false,
        data:{
        	orderStatus:orderStatus,
			pageNo : pageNo,
			pageSize : pageSize
        },
        dataType:'json',
        success:function(result){
                if (result.success){
					if(pageNo==1 || result.list.length<=0){
						$(".list dd").each(function(i,e){
							$(e).remove();
						})
						$(".list p").remove();
					}
					if(result.pageInfo.pageCount == result.pageInfo.pageIndex){
						hasNext = false;
					}else{
						hasNext = true;
					}
					if(result.list.length>0){
						renderHtml(result.list);
					}else{
						$(".list").append("<p style=text-align:center;font-size:14px;color:red;padding-top:20px;>暂时没有数据</p>");
					}
                }else{
                	$.tip(result.message);
                }
                
            }
       }); 
}

function renderHtml(obj){
	var html="";
	for(var i=0;i<obj.length;i++){
		html+='<dd onclick="location.href=\''+DOMIN.MAIN+'/order/order-details.html?orderNo='+obj[i].orderNo+'\'">';
		html+='<div class="list-top">';
		html+='订单编号：'+obj[i].orderNo+'';
		html+='</div>';
		html+='<div  class="list-main">';
		html+='<div>';
		html+='<p><label>车牌号</label>：<span>'+obj[i].license+'</span></p>';
		html+='<p><label>客户姓名</label>：<span>'+obj[i].userName+'</span></p>';
		html+='<p><label>联系电话</label>：<span>'+obj[i].phone+'</span></p>';
		if(obj[i].addressInfo=="--"){
			html+='<p><label>服务方式</label>：<span>到店服务</span></p>';
		}else{
			html+='<p><label>地 址</label>：<span>'+obj[i].addressInfo+'</span></p>';
		}
		html+='</div>';
		if(obj[i].status==1){
			html+='<span>待服务</span>';
		}else if(obj[i].status==2){
			html+='<span>服务中</span>';
		}else if(obj[i].status==3){
			html+='<span>已服务</span>';
		}
		html+='</div>';
		html+='<div class="list-top list-bottom">';
		if(obj[i].orderType==0){
			html+='更换电瓶';
		}else if(obj[i].orderType==1){
			html+='更换轮胎';
		}else if(obj[i].orderType==2){
			html+='小保养';
		}else if(obj[i].orderType==3){
			html+='洗车';
		}else if(obj[i].orderType==4){
			html+='喷漆';
		}
		html+='<span>'+$.formatDate(obj[i].createtime)+'</span>';
		html+='</div>';
		html+='</dd>';
	}
	$(".list").append(html);
}

function viewDetail(orderid){
	location.href = DOMIN.MAIN + '/order/order.html?id='+orderid;
}
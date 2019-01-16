checkLogin();
var code = $.getUrlParam('code');
var isScan = $.getUrlParam('isScan');
$(function () {
	querydetail();
	$(".btn").click(function(){
		var url="";
		if($(".btn").val()=="确认收货"){
			url=DOMIN.MAIN+'/order/confirmOrder';
		}else if($(".btn").val()=="确认服务完成"){
			url=DOMIN.MAIN+'/order/completeOrder';
		}else if($(".btn").val()=="去验券"){
			location.href=DOMIN.MAIN;
			return;
		}else{
			return;
		}
		var orderNo=$(".btn").attr("rel");
		$.ajax({
			type:'post',
			url:url, 
			async:false,
			cache:false,
			data:{
				orderNo:orderNo,
				code:code
			},
			dataType:'json',
			error:function(data){
				$.tip(data);
			}, 
			success:function(result){
				if (result.success){
					$.tip("操作成功");
					setTimeout(function(){
						location.href=DOMIN.MAIN+"/order/order.html?orderStatus=1";
					},2000);
				}else{
					$.tip(result.message);
				}
			}
		}); 
	})
});

function querydetail(){
	var isByOrderNo = 'no';
	var orderNo =  $.getUrlParam('orderNo');
	if(orderNo!=null && /^\d+$/.test(orderNo)){
		isByOrderNo = 'yes';
	}
    $.ajax({
        type:'post',
        url:DOMIN.MAIN+'/order/queryOrderDetail', 
        async:false,
        cache:false,
        data:{
			code:code,
			isByOrderNo:isByOrderNo,
			orderNo:orderNo
        },
        dataType:'json',
		error:function(data){
			$.tip(data);
		}, 
        success:function(result){
                if (result.success){
					renderHtml(result.data);
                }else{
                	$.tip(result.message);
                }
            }
       }); 
}

function renderHtml(obj){
	if(obj.status=="0"){
		$.tip('非法状态！');
		$(".list").html('<p style="text-align:center;color:red;">非法状态!</p>');	
		$(".btn").remove();
		return;
	}
	if(obj.status=="1"){
		$(".btn").val("确认收货");
	}else if(obj.status=="2"){
		if(isScan=='1' && code!=null && /^\d+$/.test(code)){
			$(".btn").val("确认服务完成");
		}else{
			$(".btn").val("去验券");
		}
	}else if(obj.status=="0" || obj.status=="3" || obj.status=="4"){
		$(".btn").remove();
	}
	$(".btn").attr("rel",obj.orderNo);
	if(obj.orderType=="0"){
		$(".state").html("服务项目：电瓶更换");
		$(".btn").remove();
	}else if(obj.orderType=="1"){
		$(".state").html("服务项目：轮胎更换");
	}else if(obj.orderType=="2"){
		$(".state").html("服务项目：小保养");
	}else if(obj.orderType=="3"){
		$(".state").html("服务项目：洗车");
	}else if(obj.orderType=="4"){
		$(".state").html("服务项目：喷漆");
	}
	
	$(".list").html("");
	var html="";
        html+='<dt>';
		html+='<img src="../resources/imgs/logo/'+obj.carlogo+'.png"/>';
		html+=obj.carinfo.chePai;
		html+='</dt>';
		html+='<dd class="car">';
		html+='<span>客户姓名：'+obj.userName+'</span>';
		html+='<span>客户电话：'+obj.userPhone+'</span>';
		html+='<span>品牌：'+obj.carinfo.autoBrand+'</span>';
		html+='<span>车系：'+obj.carinfo.autoType+'</span>';
		html+='<span>年份：'+obj.carinfo.productYear+'</span>';
		html+='<span>排量：'+obj.carinfo.engineDisp+'</span>';
		html+='</dd>';
		if(obj.orderType=="0"){
			html+='<dd class="goods">';
			html+='<img src="../resources/imgs/order/img.png" alt="" />';
			html+='<div>';
			html+='<p>瓦尔塔 蓄电池 电瓶 以旧换新</p>';
			var zimu=obj.product;
			zimu=zimu.substr(zimu.length-1,1);
			if(zimu=="M")
				html+='<p>'+obj.product+' 【蓝标】</p>';
			else if(zimu=="H")
				html+='<p>'+obj.product+' 【银标】</p>';
			else if(zimu=="A")
				html+='<p>'+obj.product+' 【AGM】</p>';
			html+='</div>';
			html+='</dd>';
		}else if(obj.orderType=="1"){
			html+='<dd class="goods">';
			html+='<img src="../resources/imgs/order/lt-img.png" alt="" />';
			html+='<div>';
			html+='<p>马牌轮胎</p>';
			html+='<p>'+obj.product+'</p>';
			html+='</div>';
			html+='</dd>';
		}else if(obj.orderType=="2"){
			var product=obj.product;
			product=product.split(",");
			var jyproduct=product[0].split("|");
			var jy4L=jyproduct[0].split("_");
			html+='<dd class="car">';
			if(obj.supplyId !="" || obj.supplyId !="0") {//判断是商家代销还是店家直结
				html+='<span>机油机滤 : '+ obj.jilvPrice +' 元</span>';
			}
			html+='</dd>';
			html+='<dd class="goods">';
			html+='<img src="../resources/imgs/order/jy-img.jpg" alt="" />';
			html+='<div>';
			html+='<p>机油</p>';
			html+='<p>'+jy4L[0]+'*'+jy4L[1]+'</p>';
			html+='</div>';
			html+='</dd>';
			if(jyproduct[1]!=undefined){
				var jy1L=jyproduct[1].split("_");
				html+='<dd class="goods">';
				html+='<img src="../resources/imgs/order/jy-img.jpg" alt="" />';
				html+='<div>';
				html+='<p>机油</p>';
				html+='<p>'+jy1L[0]+'*'+jy1L[1]+'</p>';
				html+='</div>';
				html+='</dd>';
			}
			if(product[1] && product[1].length>5){
				html+='<dd class="goods">';
				html+='<img src="../resources/imgs/order/jl-img.png" alt="" />';
				html+='<div>';
				html+='<p>机滤</p>';
				html+='<p>'+product[1].substr(0,product[1].length-5)+'&nbsp;*1</p>';
				html+='</div>';
				html+='</dd>';
			}
			
		}else if(obj.orderType=="4"){
			html+='<dd class="goods">';
			html+='<img src="../resources/imgs/index/penqi-shouye.png" alt="" />';
			html+='<div>';
			html+='<p> 喷漆</p>';
			html+='<p> x' + obj.goodsNum + ' 幅</p>';
			html+='</div>';
			html+='</dd>';
		}
		html+='<dd class="order">';
		html+='<p>订单编号：'+obj.orderNo+'</p>';
		html+='<p>下单时间：'+$.formatDate(obj.createTime)+'</p>';
		html+='<p>支付方式：服务包结算</p>';
		html+='</dd>';	
	$(".list").html(html);	 
}

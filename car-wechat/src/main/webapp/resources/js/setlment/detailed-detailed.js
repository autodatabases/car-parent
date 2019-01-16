checkLogin();
var orderNo = $.getUrlParam('orderNo');
$(function(){
	setlment();
})
function setlment(){
	$.ajax({
			type:'post',
			url:DOMIN.MAIN+'/order/sellerIncomeDetail',
			async:false,
			cache:false,
			data:{orderNo:orderNo},
			dataType:'json',
			error:function(data){
				$.tip(data);
			},
			success:function(result){
				if (result.success){
					$(".lists").html("");
					var html="";
					html+='<li>';
					var finishTime=$.formatDate(result.data.finishTime);
					finishTime=finishTime.split(" ");
					html+='<label>订单时间</label>：'+finishTime[0]+'';
					html+='</li>';
					html+='<li>';
					html+='<label>订单编号</label>：'+result.data.orderNo+'';
					html+='</li>';
					html+='<li>';
					if(result.data.orderType==1){
						html+='<label>服务类型</label>：更换轮胎';
					}else if(result.data.orderType==2){
						html+='<label>服务类型</label>：小保养';
					}else if(result.data.orderType==0){
						html+='<label>服务类型</label>：电瓶';
					}else if(result.data.orderType==3){
						html+='<label>服务类型</label>：洗车';
					}
					html+='</li>';
					html+='<li>';
					var moneyAmount="";
					if(result.data.supplyId=="0"){
						html+='<label>结算类型</label>：商家代销';
						moneyAmount="0";
					}else{
						html+='<label>结算类型</label>：门店直结';
						moneyAmount=result.data.moneyAmount;
					}
					html+='</li>';
					if(result.data.orderType==1){
						html+='<li><label>轮胎价格</label>：'+result.data.moneyAmount+ '' + '</li>';
					}else if(result.data.orderType==2){
						if(result.data.supplyId!="0"){
							html+='<li><label>机油机滤</label>：'+result.data.jilvPrice+ '' +'</li>';
						}
					}

					html+='<li>';
					html+='<label>工时费</label>：'+result.data.serviceMoney+'';
					html+='</li>';
					$(".lists").html(html);
					if(result.data.supplyId=="0"){
						$(".btn-box b").html(parseInt(result.data.serviceMoney));
					}else{
						if(result.data.orderType==2){//小保养结算
							$(".btn-box b").html(parseInt(result.data.serviceMoney) + parseInt(result.data.jilvPrice));
						} else if (result.data.orderType==3) {
							$(".btn-box b").html(parseInt(result.data.serviceMoney));
						} else {
							$(".btn-box b").html(parseInt(result.data.serviceMoney) +parseInt(result.data.moneyAmount));
						}

					}
					$(".btn-box a").attr("href",'javascript:location.href=\''+DOMIN.MAIN+'/order/order-details.html?t=1&code='+result.data.code+'\'');
				}else{
					$.tip(result.message);
				}
			}
		});
};

var type= $.getUrlParam('type');
$(document).ready(function(){
	if(type==-1){
		$(".top_nav span:eq(0)").addClass('selected');
	}else if(type==1){
		$(".top_nav span:eq(1)").addClass('selected');
		type="0,1";
	}else if(type==2){
		$(".top_nav span:eq(2)").addClass('selected');
	}else if(type==3){
		$(".top_nav span:eq(3)").addClass('selected');
	}else if(type==-2){
		$(".top_nav span:eq(4)").addClass('selected');
	}
	order_list(type);
	$(".top_nav span").on("click",function() {
		if($(this).attr('class') == "selected") {
			return;
		}
		$(".top_nav span").removeClass('selected');
		$(this).addClass('selected');
		switch ($(this).index()) {
			case 0:
				type = -1;
				break;
			case 1:
				type = "0,1";
				break;
			case 2:
				type = 2;
				break;
			case 3:
				type = 3;
				break;
			case 4:
				type = -2;
				break;
		}
		order_list(type);
	})
	$(".back").on("click",function() {
		window.location.href = DOMIN.MAIN + "/user/infocenter.html";
	})
});
function order_list(num){
	$.ajax({
		url : DOMIN.MAIN+"/order/queryOrderList?status="+num,
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				if(data.list && data.list.length>0){
					$(".order-list").html("");
					$(".noData").hide();
					var html="";
					for(var i=0;i<data.list.length;i++){
						var carPrice = parseInt(data.list[i].carPrice||0);
					    html+='<li>';
				        html+='<div class="list-top">';
						var createtime=$.formatDate(data.list[i].createtime);
						createtime=createtime.split(" ");
						html+='<span class="oType">';

						if(data.list[i].status==0)
						html+='待确认';
						else if(data.list[i].status==1)
						html+='待服务';
						else if(data.list[i].status==2)
						html+='服务中';
						else if(data.list[i].status==3)
						html+='已完成';
						else if(data.list[i].status==4)
						html+='已取消';

					    html+='</span>订单编号：<b>'+data.list[i].orderNo;
						html+='</b></div>';
						html+='';
						var orderType=data.list[i].orderType;
						var productName=data.list[i].productName;
						productName=productName.split(",");
						if(orderType==0){
							html+='<div class="list-center"  onclick=details("'+data.list[i].orderNo+','+data.list[i].orderType+','+data.list[i].washType+'")><div class="img-box"><img src="'+DOMIN.MAIN+'/resources/imgs/member/img.png"/></div>';
							html+='<div class="text-box">';
							//html+='<p>瓦尔塔 蓄电池 电瓶 以旧换新</p>';
							if(data.list[i].productName){
								var zimu=data.list[i].productName;
								zimu=zimu.substr(zimu.length-1,1);
								if(zimu=="M")
									html+='<span>'+data.list[i].productName+' 【蓝标】x1</span>';
								else if(zimu=="H")
									html+='<span>'+data.list[i].productName+' 【银标】x1</span>';
								else if(zimu=="A")
									html+='<span>'+data.list[i].productName+' 【AGM】x1</span>';
								html+='</div></div>';
							}
						}else if(orderType==1){
							html+='<div class="list-center" onclick=details("'+data.list[i].orderNo+','+data.list[i].orderType+','+data.list[i].washType+'")><div class="img-box"><img src="'+DOMIN.MAIN+'/resources/imgs/repair/lt-img.png"/></div>';
							html+='<div class="text-box">';
							html+='<p>马牌轮胎</p>';
							html+='<span>'+data.list[i].productName+'</span>';
							html+='</div></div>';
						}else if(orderType==2){
							html+='<div class="list-center" onclick=details("'+data.list[i].orderNo+','+data.list[i].orderType+','+data.list[i].washType+'")><div class="img-box"><img src="'+DOMIN.MAIN+'/resources/imgs/index/by-new.png"/></div>';
							html+='<div class="text-box">';
							html+='<p>服务：<b>小保养</b></p>';
							html+='<span></span>';
							html+='</div></div>';
							// //如果是比亚迪或宝马用户则不再向后台获取机油、机滤数据，在前台页面写定
							// if ( productName[0] == "比亚迪专用机油_1_160") {
							// 	html+='<div class="center-goods"><p>提供产品</p>'
							// 	html+='<div class="list-center"><div class="img-box"><img src= "' + DOMIN.MAIN + '/resources/imgs/repair/jy-img.jpg"/></div>';
							// 	html+='<div class="text-box">';
							// 	html+='<p>比亚迪专用机油</p>';
							// 	html+='<span>x1</span>';
							// 	html+='</div></div>';
							// 	html+='<div class="list-center"><div class="img-box"><img src="' + DOMIN.MAIN + '/resources/imgs/repair/jl-img.png"/></div>';
							// 	html+='<div class="text-box">';
							// 	html+='<p>比亚迪专用机油滤清器</p>';
							// 	html+='<span>x1</span>';
							// 	html+='</div></div></div>';
							// } else {
							// 	var remakRet={};
							// 	var productName=data.list[i].productName;
							// 	// var	jilvPic = (data.list[i].jilvPic)[0],
							// 	// 	jiyouPic = (data.list[i].jiyouPic)[0];
							// 	productName=productName.split(",");
							// 	function getReamrk(res){
							// 		remakRet= res;
							// 	}
							// 	getCarType(carPrice,getReamrk);
							// 	var paramsList = getStringRemark(remakRet,productName[0]);
							// 	html+='<div class="center-goods"><p>提供产品</p>'
							// 	html+='<div class="list-center"><div class="img-box" style="border: 1px solid #e0e0e0;"><img src="'+DOMIN.MAIN+'/resources/imgs/peijian_image/jiyou/JIASHIDUOCastrol_5W-30.jpg"/></div>';
							// 	html+='<div class="text-box">';
							// 	html+='<p><b>机油</b></p>';
							// 	html+='<p>'+paramsList+'</p>';
							// 	html+='</div></div>';
							// 	html+='<div class="list-center"><div class="img-box" style="border: 1px solid #e0e0e0;"><img src="'+DOMIN.MAIN+'/resources/imgs/peijian_image/jilv/MANPAIMANNFILTER_W719-45.jpg"/></div>';
							// 	html+='<div class="text-box">';
							// 	html+='<p><b>机滤</b></p>';
							// 	html+='<p>'+remakRet.filterText+'</p>';
							// 	html+='</div></div></div>';
							// }

						}else if(orderType==3){
							html+='<div class="list-center" onclick=details("'+data.list[i].orderNo+','+data.list[i].orderType+','+data.list[i].washType+'")><div class="img-box"><img src="'+DOMIN.MAIN+'/resources/imgs/index/xc-new.png"/></div>';
							html+='<div class="text-box">';
							html+='<p>服务：<b>洗车</b></p>';
							html+='<span></span>';
							html+='</div></div>';
						}else if(orderType==4){
							html+='<div class="list-center" onclick=details("'+data.list[i].orderNo+','+data.list[i].orderType+','+data.list[i].washType+'")><div class="img-box"><img src="'+DOMIN.MAIN+'/resources/imgs/index/pq-new.png"/></div>';
							html+='<div class="text-box">';
							html+='<p>服务：<b>喷漆</b></p>';
							html+='<span></span>';
							html+='</div></div>';
						}
						html+='</div>';
						html+='<div class="list-bottom">';
						html+='<div>下单时间：<strong>' + createtime[0] + '</strong></div>';
						html+='<div><span onclick=details("'+data.list[i].orderNo+','+data.list[i].orderType+','+data.list[i].washType+','+data.list[i].orderCode+'")>订单详情</span>';
						if (data.list[i].comment==0) {
							html+='<span onclick=pingjia("'+data.list[i].orderNo+','+data.list[i].orderType+','+num+'")>评 价</span>';
						}
						html+='</div></div></li>';
			           // html+='</strong><span>支付方式：服务券</span></div></li>';
					}
					$(".order-list").html(html);
				}else{
					$(".order-list").html("");
					$(".noData").show();
					//$(".order-list").html(getNoDataHtml());
				}
			}else{
				$(".order-list").html("");
				$(".noData").show();
				//$(".order-list").html(getNoDataHtml());
				$.tip(data.message);
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown){

		}
	})
};
function details(dataNumber){
	var datas = dataNumber.split(",");
	if (datas[1] == 3) {
		if (datas[2] == 1) {
			if (datas[3].length == 12) {
				location.href = DOMIN.MAIN+'/carwashsheng/washCode.html?type=1&orderNo='+datas[0];
			} else {
				location.href = DOMIN.MAIN+'/carwash/washCode.html?type=1&orderNo='+datas[0];
			}
			return;
		}

	}
	location.href = DOMIN.MAIN+'/order/orderok?orderNo='+datas[0];
}
function pingjia(pdata) {
	var pdata = pdata.split(",");
	var ordernum = encodeURI(encodeURI(pdata[0]));
	var ptype = encodeURI(encodeURI(pdata[1]));
	var otype = encodeURI(encodeURI(pdata[2]));
	location.href = DOMIN.MAIN+'/ordercomment/evaluate.html?ordernum=' + ordernum + '&ptype=' + ptype + '&otype=' + otype;
}
//获取机油，机滤显示列表
function getCarType(carPrice,callBackFn){
	var priceList = [
		{minPrice:0,maxPrice:200000,totalAmount:600, oilText:"机油 10W-40 (嘉实多 金嘉护超净)",filterText:"机滤 (曼牌、马勒、博世)"},
		//{minPrice:150000,maxPrice:200000,totalAmount:800, oilText:"机油 5W-30 (嘉实多 金嘉护超净)",filterText:"机滤 (曼牌、马勒、博世)"},
		{minPrice:200000,maxPrice:300000,totalAmount:1000,oilText:"机油 5W-40 (嘉实多 新磁护)",filterText:"机滤 (曼牌、马勒、博世)"},
		{minPrice:300000,maxPrice:30000000,totalAmount:1500,oilText:"机油 5W-40 (嘉实多 极护)",filterText:"机滤 (曼牌、马勒、博世)"}
	];
	$.each(priceList,function(i,e){
		if(carPrice>=e.minPrice&&carPrice<e.maxPrice){
			callBackFn(e);
			return;
		}
	});
}
function getStringRemark(targetString,origalString) {
 	var arrayList = origalString.split("|");
	var ret =[];
	for(var key in arrayList){
		if(arrayList[key].indexOf("4L")!=-1){
			ret.push(targetString.oilText+' 4L');
		}
		if(arrayList[key].indexOf("1L")!=-1){
			ret.push(targetString.oilText+' 1L');
		}
	}
	return (ret.join());
}

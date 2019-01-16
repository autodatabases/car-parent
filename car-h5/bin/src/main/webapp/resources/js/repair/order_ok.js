var load,carPrice=0;
$(document).ready(function(){
	queryOrderInfo();
});


function queryOrderInfo(){
	load = $.loading();
	var orderNo = $.getUrlParam('orderNo');
	$.ajax({
		url : DOMIN.MAIN+"/order/queryOrderByOrderNo",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {orderNo:orderNo},
		traditional: true,
		success : function(data, textStatus){
			load.remove();
			if(data.success){
				carPrice = parseInt(data.data.carPrice||0);
				$("#deleteOrder").attr("rel",data.data.orderNo);
				$('#addr_name').html(data.data.userName);
				$('#addr_phone').html(data.data.phone);
				if(data.data.orderType==0){
					$('#addr_detail').html(data.data.province+data.data.city+data.data.area+' '+data.data.addressDetail);
				}else{
					$('#addr_detail').html(data.data.addressDetail);
				}
				$('#orderNo').append(data.data.orderNo);
				$('#orderTime').append($.formatDate(parseInt(data.data.createTime)));
				var data_status="";
				if(data.data.status==0){
					data_status="待确认";
					$("#deleteOrder").show();
				}else if(data.data.status==1){
					data_status="待服务";
				}else if(data.data.status==2){
					data_status="服务中";
				}else if(data.data.status==3){
					data_status="已完成";
					$("#action-code").hide();
				}else if(data.data.status==4){
					data_status="已取消";
					$("#action-code").hide();
					$(".order-tracking").attr('onclick','');
					$("#btn-orderTrace").hide();
					$("#order-tracking").css('background','#fff');
				}
				$(".order-tracking span").html(data_status);
				sessionStorage.setItem('order_trackingid',data.data.orderNo);
				if(data.data.orderType==0){
					$(".order-tracking").attr('onclick','');
					$("#btn-orderTrace").hide();
					$("#order-tracking").css('background','#fff');
					$(".header h1").html("更换电瓶");
					var zimu=data.data.orderRemark;
					zimu=zimu.substr(zimu.length-1,1);
					if(zimu=="M")
						$(".list div").find("p").eq(1).html(data.data.orderRemark+' 【蓝标】');
					else if(zimu=="H")
						$(".list div").find("p").eq(1).html(data.data.orderRemark+' 【银标】');
					else if(zimu=="A")
						$(".list div").find("p").eq(1).html(data.data.orderRemark+' 【AGM】');
				}else if(data.data.orderType==1){
					$(".order-tracking").attr('onclick','');
					$("#btn-orderTrace").hide();
					$("#order-tracking").css('background','#fff');
					$(".header h1").html("更换轮胎");
					$(".text p").eq(0).html("马牌轮胎 "+data.data.orderRemark);
					$(".text p").eq(1).html(data.data.phone);
					$("#service").html("<label>配送方式：</label>到店服务");
					$(".img-warp img").attr("src","/resources/imgs/repair/lt-img.png");
				}else if(data.data.orderType==2){
					$(".header h1").html("小保养");
					var remakRet={};
					function getReamrk(res){
						remakRet= res;
					}
					getCarType(carPrice,getReamrk);
					var orderRemark=data.data.orderRemark;
					orderRemark=orderRemark.split(",");

					$("#service").html("<label>配送方式：</label>到店服务");
					//如果是比亚迪或宝马用户则不再向后台获取机油、机滤数据，在前台页面写定
					if (orderRemark[0] == "比亚迪专用机油_1_160") {
						$(".list dl dt").remove();
						var strHtml = '';
						strHtml = '<dt>'
								+ '<div class="img-warp"><img src= "' + DOMIN.MAIN + '/resources/imgs/repair/jy-img.jpg"/></div>'
								+ '<div class="text jy">'
								+ '<p>比亚迪专用机油</p>'
								+ '</div>'
								+ '<span>x1</span>'
								+ '</dt>'
								+ '<dt>'
								+ '<div class="img-warp"><img src="' + DOMIN.MAIN + '/resources/imgs/repair/jl-img.png"/></div>'
								+ '<div class="text jy">'
								+ '<p>比亚迪专用机油滤清器</p>'
								+ '</div>'
								+ '<span>x1</span>'
								+ '</dt>'
						$(".list dd").before(strHtml);
					} else {
						var jyRemark=orderRemark[0].split("|");
						var jyRemark_0=jyRemark[0].split("_");
						$(".text p").eq(0).html("机油");
						$(".list dt").find("span").eq(0).html("x"+jyRemark_0[1]);
						$(".text p").eq(1).html(jyRemark_0[0]);
						
						var pic = getPic(jyRemark[0]);

						$(".img-warp img").attr("src",DOMIN.MAIN+'/resources/imgs/'+pic);
						var html="";
						if(jyRemark[1]!=undefined){
							pic = getPic(jyRemark[1]);
							var jyRemark_1=jyRemark[1].split("_");
							html+='<dt>';
							html+='<div class="img-warp">';
							html+='<img src='+DOMIN.MAIN+'/resources/imgs/'+pic+'/>';
							html+='</div>';
							html+='<div class="text">';
							html+='<p>机油</p>';
							html+='<p>'+jyRemark_1[0]+'</p>';
							html+='</div>';
							html+='<span>x'+jyRemark_1[1]+'</span>';
							//html+='<span>￥'+jyRemark_1[2]+'</span>';
							html+='</dt>';
						}
						var jlRemark=orderRemark[1].split("_");
						html+='<dt>';
						html+='<div class="img-warp">';
						html+='<img src='+DOMIN.MAIN+'/resources/imgs/'+data.datasetLists.jilv[0]+'/>';
						html+='</div>';
						html+='<div class="text">';
						html+='<p>机滤</p>';
						html+='<p>'+remakRet.filterText+'</p>';
						html+='</div>';
						html+='<span>x'+jlRemark[1]+'</span>';
						//html+='<span>￥'+jlRemark[2]+'</span>';
						html+='</dt>';
						$(".list dt").after(html);
					}

				}else if(data.data.orderType==3){
					$(".order-tracking").attr('onclick','');
					$("#btn-orderTrace").hide();
					$("#order-tracking").css('background','#fff');
					$(".header h1").html("洗车");
					$("#service").html("<label>配送方式：</label>到店服务");
					$(".list dt").remove();
				}else if(data.data.orderType==4){
					$(".header h1").html("喷漆");
					$(".text p").eq(0).html("喷漆");
					$(".text p").eq(1).html("x" + data.data.goodsNum + " 幅");
					$("#service").html("<label>配送方式：</label>到店服务");
					$(".img-warp img").attr("src","/resources/imgs/index/pq-new.png");
				}

				$('.action-code .code img').prop('src',DOMIN.MAIN + '/order/getQrCode?orderNo='+data.data.code);
				$('.action-code').append("<p class='code-text'>"+data.data.code+"</p>");
				$("#deleteOrder").on('click',function(){//取消订单
					var orderNo=$("#deleteOrder").attr("rel");
					mui.confirm("是否确定取消订单！","提交订单",['取消','确定'],function(e){
						if (e.index == 1) {
							$.ajax({
								url : DOMIN.MAIN+"/order/deleteOrder",
								type : "post",
								cache : false,
								async : true,
								dataType : "json",
								data: {orderNo:orderNo},
								traditional: true,
								success : function(data, textStatus){
									if(data.success){
										$("#deleteOrder").css("display","none");
										mui.alert("取消订单成功","提示","确定",function(){
											setTimeout(function(){
												location.href=DOMIN.MAIN;
											},1500);
										});
									}else{
										$.tip(data.message);
									}
								},
								error : function(XMLHttpRequest, textStatus, errorThrown){
									load.remove();
								}
							});
						} else {

						}
					});
				});
				var sellerName,sellerPostion,sellerPhone;
				//获取参数，进行导航页面的跳转
				sellerName = data.data.userName;
				sellerAddress = data.data.addressDetail;
				sellerPosition = data.datasetLists.sellerPosition[0];
				sellerPhone = data.data.phone;
				$("#daohang").on('click',function(){
					sellerName = encodeURI(encodeURI(sellerName));
					sellerPosition = encodeURI(encodeURI(sellerPosition));
					sellerPhone = encodeURI(encodeURI(sellerPhone));
					sellerAddress = encodeURI(encodeURI(sellerAddress));
					location.href=DOMIN.MAIN+"/static/gps.html?sellerName="+sellerName+"&sellerPosition="+sellerPosition+"&sellerPhone="+sellerPhone+"&sellerAddress="+sellerAddress;
				});
				//点击出现二维码遮罩层
				$("#action-code").on("click",function() {
					$("#action-code").toggleClass("action_button");
					$(".action-code").toggle();
					//$("#shade").toggle();
				})

			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			load.remove();
		}
	});
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
//获取图片地址
function getPic(jyRemark) {
	if(jyRemark.indexOf("磁护") !== -1) {
		if(jyRemark.indexOf("4L") !== -1) {
			return "repair/jiyou/ci4.jpg";
		} else if(jyRemark.indexOf("1L") !== -1) {
			return "repair/jiyou/ci1.jpg";
		}
	}
	if(jyRemark.indexOf("极护") !== -1) {
		if(jyRemark.indexOf("4L") !== -1) {
			return "repair/jiyou/ji4.jpg";
		} else if(jyRemark.indexOf("1L") !== -1) {
			return "repair/jiyou/ji1.jpg";
		}
	}
	if(jyRemark.indexOf("金嘉护") !== -1) {
		if(jyRemark.indexOf("4L") !== -1) {
			return "repair/jiyou/jin4.jpg";
		} else if(jyRemark.indexOf("1L") !== -1) {
			return "repair/jiyou/jin1.jpg";
		}
	}
}

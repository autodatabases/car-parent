var sellerId= $.getUrlParam('sellerId');
var type=$.getUrlParam('type');
var sellerName,sellerPosition,sellerPhone,sellerAddress;
$(document).ready(function(){
	//判断是否是江门市，有出现喷漆服务
	if(getCookie("selcity")=="江门市" || getCookie("city")=="江门市" || getCookie("selcity")=="中山市" || getCookie("city")=="中山市"|| getCookie("selcity")=="广州市" || getCookie("city")=="广州市"){
		//$("#lacquer").css("display","inline-block");//喷漆选项
		//$(".fwfw-2").parent().css("border-right","none");
		//$("#install").hide();//安装环境选项
	}
	if(type=="2"){
		$(".car-service span").eq(1).find("i").removeClass("by-2").addClass("by-1");
		$(".btn-action").val("立即免费小保养");
	}else if(type=="4"){
		$(".car-service span").eq(2).find("i").removeClass("pq-2").addClass("pq-1");
		$(".btn-action").val("立即免费喷漆");
	}else if(type=="3"){
		$(".car-service span").eq(0).find("i").removeClass("xc-2").addClass("xc-1");
		$(".btn-action").val("立即免费洗车");
	}
	$(".gps-go span").eq(1).html("距你"+$.getUrlParam('dotKm')+"km");
	getLtDot();

	//跳转至导航页面
	$(".gps-go").on('click',function(){
		sellerName = encodeURI(encodeURI(sellerName));
		sellerPosition = encodeURI(encodeURI(sellerPosition));
		sellerAddress = encodeURI(encodeURI(sellerAddress));
		sellerPhone = encodeURI(encodeURI(sellerPhone));
		location.href=DOMIN.MAIN+"/static/gps.html?sellerName="+sellerName+"&sellerPosition="+sellerPosition+"&sellerPhone="+sellerPhone+"&sellerAddress="+sellerAddress;
	});

	//各个汽车服务类型的页面交互
	$(".car-service span").eq(0).on('click',function(){//洗车
		if($(".car-service span").eq(0).find("i").attr("class")!="xc-3"){//根据class名判断按钮的状态
			$(".car-service span").eq(0).find("i").removeClass("xc-2").addClass("xc-1");
			type=3;
			if($(".car-service span").eq(1).find("i").attr("class")!="by-3"){
				$(".car-service span").eq(1).find("i").removeClass("by-1").addClass("by-2");
			}
			if($(".car-service span").eq(2).find("i").attr("class")!="pq-3"){
				$(".car-service span").eq(2).find("i").removeClass("pq-1").addClass("pq-2");
			}
			$(".car-service span").eq(3).find("i").removeClass("fwfw-1").addClass("fwfw-2");
			$(".car-service span").eq(4).find("i").removeClass("hj-1").addClass("hj-2");
			$(".btn-action").val("立即免费洗车");
			$(".btn-action").css("display","block");
			$(".service-box").css("display","none");
		}
	});
	$(".car-service span").eq(1).on('click',function(){//小保养
		if($(".car-service span").eq(1).find("i").attr("class")!="by-3"){
			if($(".car-service span").eq(0).find("i").attr("class")!="xc-3"){
				$(".car-service span").eq(0).find("i").removeClass("xc-1").addClass("xc-2");
			}
			if($(".car-service span").eq(2).find("i").attr("class")!="pq-3"){
				$(".car-service span").eq(2).find("i").removeClass("pq-1").addClass("pq-2");
			}
			$(".car-service span").eq(1).find("i").removeClass("by-2").addClass("by-1");
			type=2;
			$(".btn-action").css("display","block");
			$(".btn-action").val("立即免费小保养");
			$(".service-box").css("display","none");
			$(".car-service span").eq(3).find("i").removeClass("fwfw-1").addClass("fwfw-2");
			$(".car-service span").eq(4).find("i").removeClass("hj-1").addClass("hj-2");
		}
	});
	$(".car-service span").eq(2).on('click',function(){//喷漆
		if($(".car-service span").eq(2).find("i").attr("class")!="pq-3"){
			if($(".car-service span").eq(0).find("i").attr("class")!="xc-3"){
				$(".car-service span").eq(0).find("i").removeClass("xc-1").addClass("xc-2");
			}
			if($(".car-service span").eq(1).find("i").attr("class")!="by-3"){
				$(".car-service span").eq(1).find("i").removeClass("by-1").addClass("by-2");
			}
			$(".car-service span").eq(2).find("i").removeClass("pq-2").addClass("pq-1");
			type=4;
			$(".btn-action").css("display","block");
			$(".btn-action").val("立即免费喷漆");
			$(".service-box").css("display","none");
			$(".car-service span").eq(3).find("i").removeClass("fwfw-1").addClass("fwfw-2");
			$(".car-service span").eq(4).find("i").removeClass("hj-1").addClass("hj-2");
		}
	});
	$(".car-service span").eq(3).on('click',function(){//服务范围
		if($(".car-service span").eq(0).find("i").attr("class")!="xc-3"){//根据class名判断按钮的状态
			$(".car-service span").eq(0).find("i").removeClass("xc-1").addClass("xc-2");
		}
		if($(".car-service span").eq(1).find("i").attr("class")!="by-3"){
			$(".car-service span").eq(1).find("i").removeClass("by-1").addClass("by-2");
		}
		if($(".car-service span").eq(2).find("i").attr("class")!="pq-3"){
			$(".car-service span").eq(2).find("i").removeClass("pq-1").addClass("pq-2");
		}
		$(".btn-action").css("display","none");
		$(".service-box").css("display","block");
		$(".service-box img").css("display","none");
		$(".service-box strong").css("display","block");
		$(".service-box ul").css("display","block");
		$(".car-service span").eq(3).find("i").removeClass("fwfw-2").addClass("fwfw-1");
		$(".car-service span").eq(4).find("i").removeClass("hj-1").addClass("hj-2");
	});
	$(".car-service span").eq(4).on('click',function(){//安装环境
		//if($(".car-service span").eq(0).find("i").attr("class")!="xc-3"){
		//	$(".car-service span").eq(0).find("i").removeClass("xc-1").addClass("xc-2");
		//}
		//if($(".car-service span").eq(1).find("i").attr("class")!="by-3"){
		//	$(".car-service span").eq(1).find("i").removeClass("by-1").addClass("by-2");
		//}
		//$(".btn-action").css("display","none");
		//$(".service-box").css("display","block");
		//$(".service-box strong").css("display","none");
		////$(".service-box ul").css("display","none");
		//$(".service-box img").css("display","block");
		//$(".car-service span").eq(2).find("i").removeClass("fwfw-1").addClass("fwfw-2");
		//$(".car-service span").eq(3).find("i").removeClass("hj-2").addClass("hj-1");
	});
});

function getLtDot(t){
	$.ajax({
		url : DOMIN.MAIN+"/seller/getSeller",
		type : "post",
		cache : false,
		async : true,
		data:{sellerId:sellerId},
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				if(data.data.shopPic && data.data.shopPic!=null){
					$(".img-box").find('img').prop('src',DOMIN.MAIN+'/imgget/getimg?uri='+data.data.shopPic+'&828X400');
				}else{
					$(".img-box").find('img').prop('src',DOMIN.MAIN+'/resources/imgs/dot/img.png');
				}
				$(".times b").html(data.data.openTime);
				$(".text-box h3").html(data.data.sellerName);
				$(".text-t b").html(data.data.completOrder);
				$(".text-t p").removeClass("on-1").addClass("on-"+data.data.score);
				$(".text-t p").find("span").html(data.data.score+".0");
				$(".text-warp a").attr("href","tel:"+data.data.sellerPhone);
				$(".gps-go p").html(data.data.addressDetail);
				if(data.data.sellerGrade=="1"){
					$(".img-box .tips").html("快修店").addClass("green");
				}else if(data.data.sellerGrade=="2"){
					$(".img-box .tips").html("4s店").addClass("blue");
				}else if(data.data.sellerGrade=="3"){
					$(".img-box .tips").html("修理厂").addClass("blue");
				}
				var properties=data.data.properties;
				if(parseInt(properties&2)==0){
					$(".car-service span").eq(0).find("i").removeClass("xc-2").removeClass("xc-1").addClass("xc-3");
				}
				if(parseInt(properties&1)==0){
					$(".car-service span").eq(1).find("i").removeClass("by-2").removeClass("by-1").addClass("by-3");
				}
				if(parseInt(properties&16)==0){
					$(".car-service span").eq(2).find("i").removeClass("pq-2").removeClass("pq-1").addClass("pq-3");
				}
				var html="";
				$(".service-box ul").html("");
				if(data.data.shopService){
					var shopService=data.data.shopService;
					shopService=shopService.split("，");
					for(var i=0;i<shopService.length;i++){
						html+='<li>'+shopService[i]+'</li>';
					}
					$(".service-box ul").append(html);
					//$(".service-box ul").html("<li>"+data.data.shopService+"</li>");
				}
				sellerPosition = data.data.sellerPosition;
				sellerName = data.data.sellerName;
				//sellerPostion=data.data.province+data.data.city+data.data.area+data.data.addressDetail;
				sellerAddress = data.data.addressDetail;
				sellerPhone = data.data.sellerPhone;
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
		}
	});
}
function hasUnCompletOrder(){//预约
    $.ajax({
		url : DOMIN.MAIN+"/user/hasUnCompletOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data:{orderType:type},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				if(type!="3"){
					sessionStorage.setItem('sellerId',sellerId);
					location.href = DOMIN.MAIN + '/powerorder.html?type='+type;
				}else{
					data= {
						orderType:3,
						selectedSellerId:sellerId,
						goodsName:"",
						goodsId:'',
						goodsNum:'',
						moneyAmount:'',
						province:"",
						city:"",
						area:"",
						addressDetail:"",
						userName:"",
						phone:""
					}
					//submit(data);
					mui.confirm("是否确定提交订单！","提交订单",['取消','确定'],function(e){
						if (e.index == 1) {
							submit(data);
						} else {

						}
					});
				}
			}else{
				$.tip("有未完成的订单！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
    });
}

var load,isloading=false;
function submit(data){
	if(isloading){
		$.tip('正在加载，请稍后...');
		return;
	}
	load = $.loading();
	isloading = true;
	$.ajax({
		url : DOMIN.MAIN+"/order/createOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data:data,
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				location.href = DOMIN.MAIN+'/order/success.html?orderNo='+data.data.orderNo;
			}else{
				$.tip(data.message);
			}
			isloading = false;
			load.remove();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
			isloading = false;
			load.remove();
		}
	});
}

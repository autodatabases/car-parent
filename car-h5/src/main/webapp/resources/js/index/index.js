$(document).ready(function(){
	startSwiper();
	getLocation();
	addSource();
	if(getCookie('CAR_H5_TOKEN')==null){//根据cookie判断是否登录(没登录跳转登录页面)
		mui('body').on('tap', 'div,section,a', function() {
			location.href=DOMIN.MAIN+"/user/login.html";
			return false;
		});
	}else{
		getCarInfo();
		showPenQi();
		$(".bind_user_car").on('click',function(e){//打开绑定车牌弹窗
			if(!hasCar){
				$(".activation").css("display","block");
				$(".nav").css("display","none");
				return;
			}else{
				$.tip("您已经绑定过车型");
			}

		});
		$(".activation .close").on('click',function(e){//关闭绑定车牌弹窗
			$(".activation").hide();
			$(".nav").show();
			if($(".xiaci input").prop("checked")){
				addCookie("indexBinding",1,720);
			}
			 e.preventDefault();
		});
		$(".xc-btn").on('click',carWash);
		$(".by-btn").on('click',smallMaintenance);
		$(".dp-btn").on('click',toPower);
		$(".pq-btn").on('click',sprayPaint);
		$(".dd-btn").on('click',zanshi);
		$(".tc-btn").on('click',zanshi);

		//更改车牌地址
		$("#car-address").on('touchstart',function() {
			$(".select-box").addClass("select-animate");
		});
		$("#add-box").on('touchstart','span',function() {
			$("#car-address").html($(this).html());
			$(".select-box").attr("class","select-box");
		})

		$(".activation .action").on('click',function(e){//确认提交绑定车牌
			if($(".xiaci input").prop("checked")){
				addCookie("indexBinding",1,720);
			}
			indexsubmit();
			e.preventDefault();
		});
		$(".activation p").eq(0).find("b").eq(0).on('click',function(e){//绑定车牌号切换
			$(this).addClass("on").siblings("b").removeClass("on");
			$(".activation p").eq(1).css("display","none");
			$(".activation p").eq(2).css("display","block");
			 e.preventDefault();
		});
		$(".activation p").eq(0).find("b").eq(1).on('click',function(e){//绑定车架号切换
			$(this).addClass("on").siblings("b").removeClass("on");
			$(".activation p").eq(2).css("display","none");
			$(".activation p").eq(1).css("display","block");
			 e.preventDefault();
		});

		//积分商城界面
		$(".points-mall").on('click',goMall);

		//业务员跳转界面
		$(".salesman-box a").eq(0).on('click',function(){//跳转业务员绑定
		    var id=$(".salesman-box a").eq(0).attr("rel");
			if(id=="1"){
				location.href = DOMIN.MAIN+'/counterman/tobindCounterman.html';
			}else if(id=="2"){
				location.href = DOMIN.MAIN+'/counterman/dataInfo.html';
			}
		});
		$("#password").on("keyup",function() {
			if($(this).val().length >8) {
				$(this).val($(this).val().substring(0,8)) ;
			}
		})

		//服务热线提示
		$(".phone").on("click",function() {
			$(".tip-con").show();
		});
		$(".tip-close").on("click",function() {
			$(".tip-con").hide();
		})
	}
})
function showPenQi(){
	var city_clearInterval=setInterval(function showTime(){
		if($("#cityname").html()!=""){
			clearInterval(city_clearInterval);
			if($("#cityname").html()=="广州市"){
				$(".status02").show();
				$(".status01").hide();
			}else{
				$(".status01").show();
				$(".status02").hide();
			}
			if(getCookie('CAR_H5_TOKEN')==null || penqiTimes <=0){
				$(".pq-btn").hide();
				return;
			}

			 if($("#cityname").html()=="中山市"){//中山市有维修其他市没有
			 	if ($("#cityname").html()==(userAddress+"市") || userAddress == null) {
			 		$(".pq-btn").show();//显示喷漆模块
			 	}

			 }else if($("#cityname").html()=="江门市" && ($("#cityname").html()==(userAddress+"市") || userAddress == null)){//江门市才有的喷漆业务
			 	$(".pq-btn").show();//显示喷漆模块

			 }else if($("#cityname").html()=="广州市" && ($("#cityname").html()==(userAddress+"市") || userAddress == null)){//广州才有的喷漆业务
			 	$(".pq-btn").show();//显示喷漆模块

			 }else {
			 	$(".pq-btn").hide();
			 }
		}
	},1000);
}
function indexsubmit(){//绑定车牌或者车架号
	var chepai="";
	if($(".activation p").eq(2).css("display")=="none"){
		if($(".activation p").eq(1).find("input").val()){
			chepai=$.trim($(".activation p").eq(1).find("input").val());
		}else{
			$.tip("车架号不能为空");
			return;
		}
	}else{
		if($(".activation p").eq(2).find("input").val()){
			chepai=$(".activation p").eq(2).find("span").html()+$(".activation p").eq(2).find("input").val();
		}else{
			$.tip("车牌号不能为空");
			return;
		}
	}
	var password=$("#password").val();
	if(password==""){
		$.tip("刮刮卡不能为空");
		return;
	}
	$.ajax({
		url : DOMIN.MAIN+"/user/bind",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			chepai:chepai,
			password:password,
			source:getCookie('source_phone')
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				$(".activation .activation-text").hide();
				$(".activation .activation-ok").show();
				setTimeout(function () { location.reload();}, 2000);
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});
}
var hasCar = false;
var sellerId;//商家id
var orderInfolist;//订单信息
var washType;//区分是宽途洗车 还是车服洗车
var agent,userAddress;//区分4s店
var dianpingTimes=0,luntaiTimes=0,baoyangTimes=0,xicheTimes=-1,penqiTimes=0;//服务次数
// 获取车辆信息
function getCarInfo(){
	$.ajax({
		url : DOMIN.MAIN+"/user/getCarInfo",
		type : "post",
		cache : false,
		async : false,
		dataType : "json",
		data: {
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				hasCar = true;
				sellerId=data.data.seller;//客户ID
				dianpingTimes=data.data.dianpingTimes;//订单次数
				//luntaiTimes=data.data.luntaiTimes;//轮胎次数
				baoyangTimes=data.data.baoyangTimes;//保养次数
				xicheTimes=data.data.xiecheTimes;//洗车次数
				penqiTimes=data.data.penqiTimes;//喷漆次数
				orderInfolist=data.datasets.orderInfo.list;
				agent=data.data.agent;
				userAddress = data.data.address;
				washType = data.datasets.washType.data;

				if(xicheTimes > 0){
					$(".xc-btn").show();
				}
				if(baoyangTimes > 0){
					$(".by-btn").show();
				}
				if(dianpingTimes > 0){
					$(".dp-btn").show();
				}



				//判断是否有服务次数
				if(dianpingTimes>0){
					$(".dp-btn").append("<b>"+dianpingTimes+"</b>");
				}
				//$(".lt-btn").append("<b>"+luntaiTimes+"</b>");
				if(baoyangTimes>0){
					$(".by-btn").append("<b>"+baoyangTimes+"</b>");
				}
				if(xicheTimes>0){
					$(".xc-btn").append("<b>"+xicheTimes+"</b>");
				}
				if(penqiTimes>0){
					$(".pq-btn").append("<b>"+penqiTimes+"</b>");
				}
				$(".salesman-box a").eq(0).attr("rel",data.data.id);
				$("#addcarinfo").hide();
				if(data.data.chePai!=null){
					$(".di-btn").show();
					$("#mycar").show();
					$("#mycar").find('img').prop('src',DOMIN.MAIN + '/resources/imgs/logo/big/'+data.data.autoLine+'.png');
					//$("#license").html(data.data.brandname);
					$("#license").html(data.data.chePai);
					if(data.data.engineDisp != null && data.data.engineDisp.length==1){
						data.data.engineDisp = data.data.engineDisp+'.0L'
					}
					var carinfo;
					if(data.data.autoType == null
							|| data.data.engineDisp == null
							|| data.data.productYear == null){
						carinfo = data.data.autoBrand;
					}else{
						carinfo = data.data.autoBrand+" "+data.data.autoType + " " + data.data.engineDisp + " " + data.data.productYear+'款';
					}
					sessionStorage.setItem('usercarinfo',carinfo);
					$("#car-info").html(carinfo);
					//下面代码用来处理服务到期提醒
					if(data.datasets&&data.datasets.expireTip&&data.datasets.expireTip.success){
						var tipStatus =data.datasets.expireTip.data||0;
						var tiptipStatusLabel={
							4:"您的旧保单已过期，旧保单免费次数已作废!",
							5:"您已续保，免费次数已叠加!",
							6: "您已续保，免费次数已叠加!",
							8:"您的保单即将在"+$.formatDate(data.data.endTime).substr(0,10)+"到期"
						}
						if(tipStatus==4||tipStatus==5||tipStatus==6||tipStatus==8){
							$("#news-list").find('li:eq(0)').before('<li class="swiper-slide"><a href="#" style="color:red;">'+tiptipStatusLabel[tipStatus]+'</a></li>');
							startSwiper();
						}
					}
					/*if(data.datasets.endTime && /^\d+$/.test(data.data.endTime)){
						if(baoyangTimes>0 && data.data.endTime - new Date().getTime() > 0 && data.data.endTime - new Date().getTime() < 2 * 30 * 24 * 60 * 60 * 1000){
							$("#news-list").find('li:eq(0)').before('<li class="swiper-slide"><a href="#" style="color:red;">您的免费保养将在  '+$.formatDate(data.data.endTime).substr(0,10)+' 到期</a></li>');
							startSwiper();
						}
					}*/
				}else{
					hasCar = false;
					if(getCookie("indexBinding")==null && data.data.id==1){
						$(".activation").show();
						$(".nav").hide();
					}
				}
			}else{
				hasCar = false;

			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});
}
function toTyre(){//d
	//if(!hasCar){
		//$.tip('请先添加我的车型！');
		//window.location.href=DOMIN.MAIN+"/brand/car1.html";
		//return;
	//}fd
	if(orderInfolist){
		for(var i=0;i<orderInfolist.length;i++){
			if(orderInfolist[i].orderType==1){
				location.href = DOMIN.MAIN+'/order/orderok?orderNo='+orderInfolist[i].orderNo;
				return;
			}
		}
	}
	if(luntaiTimes>0){
		mui.openWindow({
			url:DOMIN.MAIN+"/order/tyre-select.html?sellerId="+sellerId
		});
	}else{
		$.tip("对不起，您的服务次数不足。");
	}
}

function carWash(){//洗车
	// if(getTime()) {
	// 	$.tip("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因春节期间法定假日放假需求，惠加车服由2018年2月1日至2018年3月7日止将暂停所有服务，停止服务期间将不接待各项汽车服务要求，3月8日起恢复正常。");
	// 	return;
	// }
	if(!hasCar){
		$(".activation").css("display","block");
		$(".nav").css("display","none");
		$.tip("请先绑定车牌！");
		return;
	}
	if(xicheTimes < 0 ){
		$.tip("对不起，您的服务次数不足。");
		return;
	}
	if (washType == 1){//判断是盛大洗车
		location.href = DOMIN.MAIN+'/carwashsheng/carWash.html';
		return;
	}
	// if (washType == 0){//判断是盛大洗车
	// 	location.href = DOMIN.MAIN+'/carwashsheng/carWash.html';
	// 	return;
	// }
	if(orderInfolist){//判断是否有未完成订单（有则跳转订单详情）
		for(var i=0;i<orderInfolist.length;i++){
			if(orderInfolist[i].orderType==3){
				location.href = DOMIN.MAIN+'/order/orderok?orderNo='+orderInfolist[i].orderNo;
				return;
			}
		}
	}
	if(xicheTimes > 0){//判断是否有服务次数
		//判断是宽途洗车还是车服洗车
			location.href = DOMIN.MAIN + '/dot.html?type=3&userAddress=' + encodeURI(encodeURI(userAddress));
/*		else if(washType == -1) {
			$.tip("对不起，该城市尚无洗车渠道规则");
		}*/
		//location.href = 'http://m.kuanter.com/shop/v3/init?cityCode=4401';
	}else{//-1   表示未绑定车牌
		$.tip("对不起，您的服务次数不足。");
	}
}
function smallMaintenance(){//小保养
	// if(getTime()) {
	// 	$.tip("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因春节期间法定假日放假需求，惠加车服由2018年2月1日至2018年3月7日止将暂停所有服务，停止服务期间将不接待各项汽车服务要求，3月8日起恢复正常。");
	// 	return;
	// }
	if(!hasCar){
		$(".activation").css("display","block");
		$(".nav").css("display","none");
		$.tip("请先绑定车牌！");
		return;
	}
	if(orderInfolist){//判断是否有未完成订单（有则跳转订单详情）
		for(var i=0;i<orderInfolist.length;i++){
			if(orderInfolist[i].orderType==2){
				location.href = DOMIN.MAIN+'/order/orderok?orderNo='+orderInfolist[i].orderNo;
				return;
			}
		}
	}
	if(baoyangTimes>0){//判断是否有服务次数
		if(/^\d+$/.test(sellerId)){//判断是否有绑定门店（有则跳转提交订单页，否则跳转选择门店列表）
			sessionStorage.setItem('sellerId',sellerId);
			location.href = DOMIN.MAIN + '/powerorder.html?type=2&agent='+encodeURI(encodeURI(agent));
		}else{
			mui.openWindow({
				url:DOMIN.MAIN+'/dot.html?type=2&userAddress='+encodeURI(encodeURI(userAddress)) + '&agent=' + encodeURI(encodeURI(agent))
			});
		}
	}else{
		$.tip("对不起，您的服务次数不足。");
	}
}

function toPower(){//跟换电瓶
	// if(getTime()) {
	// 	$.tip("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因春节期间法定假日放假需求，惠加车服由2018年2月1日至2018年3月7日止将暂停所有服务，停止服务期间将不接待各项汽车服务要求，3月8日起恢复正常。");
	// 	return;
	// }
	if(orderInfolist){//判断是否有未完成订单（有则跳转订单详情）
		for(var i=0;i<orderInfolist.length;i++){
			if(orderInfolist[i].orderType==0){
				location.href = DOMIN.MAIN+'/order/orderok?orderNo='+orderInfolist[i].orderNo;
				return;
			}
		}
	}
	if(dianpingTimes>0){//判断是否有服务次数
		location.href = DOMIN.MAIN + '/powerorder.html?type=0';
		return;
	}else{
		$.tip("对不起，您的服务次数不足。");
	}
}
function sprayPaint() {//喷漆服务
	// if(getTime()) {
	// 	$.tip("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因春节期间法定假日放假需求，惠加车服由2018年2月1日至2018年3月7日止将暂停所有服务，停止服务期间将不接待各项汽车服务要求，3月8日起恢复正常。");
	// 	return;
	// }
	if(!hasCar){
		$(".activation").css("display","block");
		$(".nav").css("display","none");
		$.tip("请先绑定车牌！");
		return;
	}
	if(orderInfolist){//判断是否有未完成订单（有则跳转订单详情）
		for(var i=0;i<orderInfolist.length;i++){
			if(orderInfolist[i].orderType==4){
				location.href = DOMIN.MAIN+'/order/orderok?orderNo='+orderInfolist[i].orderNo;
				return;
			}
		}
	}
	if(penqiTimes>0){//判断是否有服务次数
		sessionStorage.setItem('sellerId',sellerId);
		if(/\d+/.test(sellerId)){//判断是否有绑定门店（有则跳转提交订单页，否则跳转选择门店列表）
			location.href = DOMIN.MAIN + '/powerorder.html?type=4&agent='+encodeURI(encodeURI(agent));
		}else{
			location.href = DOMIN.MAIN+"/dot.html?type=4&userAddress="+encodeURI(encodeURI(userAddress));
		}
		return;
	}else{
		$.tip("对不起，您的服务次数不足。");
	}
}

//滚动新闻
function startSwiper(){
	var silde = $(".top-silde");
	var parent = silde.parent();
	silde.remove().appendTo(parent);
	var mySwiper = new Swiper ('.top-silde', {
    	direction: 'vertical',
	    loop: true,
	    autoplayDisableOnInteraction : false,
	    autoplay: 4000
  	});
}

//处理业务员推荐过来的人员绑定记录
function addSource(){
	if($.getUrlParam('source') && /^\d+$/.test($.getUrlParam('source'))){
		addCookie('source_phone',$.getUrlParam('source'),2);//2天之内都可以登录
	}
}

//积分商城
function goMall() {
	$.tip("敬请期待");
}
function zanshi() {
	// if(getTime()) {
	// 	$.tip("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因春节期间法定假日放假需求，惠加车服由2018年2月1日至2018年3月7日止将暂停所有服务，停止服务期间将不接待各项汽车服务要求，3月8日起恢复正常。");
	// 	return;
	// }
	$.tip("对不起，您的服务次数不足。");
	return;
}
//时间比较 "2018/2/1 0:0:0"
// function getTime() {
// 	var curDate = new Date();
// 	var startDate = new Date("2018/2/1 0:0:0");
// 	var endDate = new Date("2018/3/8 0:0:0");
// 	var t1 = curDate.getTime();
// 	var s1 = startDate.getTime();
// 	var s2 = endDate.getTime();
// 	if(t1 > s1 && t1 < s2) {
// 		return true;
// 	} else {
// 		return false;
// 	}
// }

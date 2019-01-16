var pageNo = 1;
var pageSize = 15;//暂时定分页为15
var hasNext = true;
var x,y;
var isLoad = false,load;
var province_list="";
var type= $.getUrlParam('type');
var powerorder_by= $.getUrlParam('powerorder_by');
var cityId = null;
var userAddress= $.getUrlParam('userAddress');
var agent= $.getUrlParam('agent');
$(document).ready(function(){
	getDefaultCity();//获取默认市区
	//获取市区
	$("#selectCity").on("click",function() {
		if(userAddress){
			$.tip('系统检测到你的保单地址为'+userAddress+',目前只能在该地区享受服务，请见谅。');
			return;
		}
		$("#selectCity").toggleClass("on");
		$("#select-list1").toggle();
		$("#bg-box1").toggle();
		$("#select-list2").hide();
		$("#select-list3").hide();
		$("#select-list4").hide();
		$("#bg-box2").hide();
		$("#bg-box3").hide();
		$("#bg-box4").hide();
		$("#selectArea").removeClass("on");
		$("#selectStore").removeClass("on");
		$("#selectDefalt").removeClass("on");
		$.ajax({
			url : DOMIN.MAIN+"/address/queryCityByProvince",
			type : "post",
			cache : false,
			async : true,
			dataType : "json",
			data: {
				provinceId: 20
			},
			traditional: true,
			success : function(data, textStatus){
				if(data.success){
					var obj = data.list;
					//处理返回结果
					var html = '';
					for(var i=0;i<obj.length;i++){
						html += '<li city="' + obj[i].id + '">'+obj[i].regionname+'</li>';
					}
					$("#select-list1").empty().append(html);
				}else{
					$.tip(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.tip(errorThrown);
			}
		});
	})
	//选择市
	$("#select-list1").on("click","li",function() {
		cityId = $(this).attr("city")
		var cityHtml = $(this).html().trim();
		$("#selectCity").html(cityHtml);
		$("#selectCity").removeClass("on");
		$("#select-list1").hide();
		$("#bg-box1").hide();
		$("#selectArea").html("选择区");
		pageNo = 1;
		getLtDot();
	})

	//选择区
	$("#selectArea").on("click",function() {
		$.ajax({
			url : DOMIN.MAIN+"/address/queryAllAreaByCity",
			type : "post",
			cache : false,
			async : true,
			dataType : "json",
			data: {
				cityId: cityId
			},
			traditional: true,
			success : function(data, textStatus){
				if(data.success){
					var obj = data.list;
					//处理返回结果
					var html = '<li>选择区</li>';
					for(var i=0;i<obj.length;i++){
						html += '<li>'+obj[i].regionname+'</li>';
					}
					$("#select-list2").empty().append(html);
				}else{
					$.tip(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				$.tip(errorThrown);
			}
		});
		$("#selectArea").toggleClass("on");
		$("#select-list2").toggle();
		$("#bg-box2").toggle();
		$("#select-list1").hide();
		$("#select-list4").hide();
		$("#select-list3").hide();
		$("#bg-box1").hide();
		$("#bg-box4").hide();
		$("#bg-box3").hide();
		$("#selectCity").removeClass("on");
		$("#selectStore").removeClass("on");
		$("#selectDefalt").removeClass("on");
	});
	//选择区
	$("#select-list2").on("click","li",function() {
		$("#selectArea").html($(this).html())
		$("#selectArea").removeClass("on");
		$("#select-list2").hide();
		$("#bg-box2").hide();
		pageNo = 1;
		getLtDot();
	});

	//加载门店
	$("#selectStore").on("click",function() {
		var html = "";
		html = '<li value="">所有门店 </li>'
			+ '<li value="1">快修店 </li>'
			+ '<li value="2">4s店</li>'
			+ '<li value="3">修理厂 </li>'
			+ '<li value="0">其他 </li>'
		$("#selectStore").toggleClass("on");
		$("#select-list3").html(html).toggle();
		$("#bg-box3").toggle();
		$("#select-list1").hide();
		$("#select-list2").hide();
		$("#select-list4").hide();
		$("#bg-box1").hide();
		$("#bg-box2").hide();
		$("#bg-box4").hide();
		$("#selectCity").removeClass("on");
		$("#selectArea").removeClass("on");
		$("#selectDefalt").removeClass("on");


	});
	//选择门店
	$("#select-list3").on("click","li",function() {
			$("#selectStore").html($(this).html())
			$("#selectStore").removeClass("on");
			$("#select-list3").hide();
			$("#bg-box3").hide();
			$("#selectStore").attr("value",$(this).attr("value"))
			pageNo = 1;
			getLtDot();
		});
	//加载排序
	$("#selectDefalt").on("click",function() {
		var html = "";
		html = '<li value="score">默认排序 </li>'
			+ '<li value="score">门店好评 </li>'
			+ '<li value="distance">距离最近</li>'
		$("#selectDefalt").toggleClass("on");
		$("#select-list4").html(html).toggle();
		$("#bg-box4").toggle();
		$("#select-list1").hide();
		$("#select-list2").hide();
		$("#select-list3").hide();
		$("#bg-box1").hide();
		$("#bg-box2").hide();
		$("#bg-box3").hide();
		$("#selectCity").removeClass("on");
		$("#selectArea").removeClass("on");
		$("#selectStore").removeClass("on");
	});
	//选择排序
	$("#select-list4").on("click","li",function() {
		$("#selectDefalt").html($(this).html())
		$("#selectDefalt").removeClass("on");
		$("#select-list4").hide();
		$("#bg-box4").hide();
		$("#selectDefalt").attr("value",$(this).attr("value"));
		pageNo = 1;
		getLtDot();
	});
    getLocation();
	$(window).scroll(function(){
		var scrollHeight = $(document).height();
	　	var scrollTop = $(document).scrollTop();
	　　	var windowHeight = document.body.clientHeight;
	　　	if(scrollTop + windowHeight >= scrollHeight){
	　　　　	nextPage();
	　　	}
	});
	//点击隐藏层可以将下拉列表隐藏
	$(".bg-box").on("touchstart",function() {
		$(".select-list").hide();
		$(".bg-box").hide();
		$(".tab-list span").removeClass('on');
	});
	// 点击（显示/取消)搜索栏
	$("#cancle-search").click(function(){
		if(!$(this).hasClass('on')){
			$(this).addClass('on');
			$("#sellerName").show();
		}else{
			$(this).removeClass('on');
			$("#sellerName").hide();
		};
		$("#sellerName").val("");
		pageNo = 1;
		getLtDot();
	});
	// 搜索
	$("#sellerName").on('keyup',function(){
		pageNo = 1;
		getLtDot();
	});
});

function nextPage(){
	if(hasNext){
		pageNo ++;
		getLtDot();
	}else{
		if($('.mui-popup').length==0){
			$.tip('没有更多了！');
		}
	}
}
function getLocation() {//定位
	if(getCookie("dotlng_x")!=null){
		x = getCookie("dotlng_x");
		y = getCookie("dotlat_y");

		//江门4s店和修理厂
		agent = decodeURI(agent);
		if (agent == "江门4s") {
			$("#selectStore").html("4s店");
			$("#selectStore").attr("value","2");
		} else if (agent == "江门修理厂") {
			$("#selectStore").html("修理厂");
			$("#selectStore").attr("value","3");
		}
		getLtDot();
		return;
	}
	//检查浏览器是否支持地理位置获取
	if (navigator.geolocation) {
	//若支持地理位置获取,成功调用showPosition(),失败调用showPositionError
	//var config = { enableHighAccuracy: true, timeout: 3000, maximumAge: 30000 };
	//navigator.geolocation.getCurrentPosition(showPosition,showPositionError,config);
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r) {
			if (this.getStatus() == BMAP_STATUS_SUCCESS) {
				x = r.point.lng;
				y = r.point.lat;
				addCookies("dotlng_x",x,30);
				addCookies("dotlat_y",y,30);

				//江门4s店和修理厂
				agent = decodeURI(agent);
				if (agent == "江门4s") {
					$("#selectStore").html("4s店");
					$("#selectStore").attr("value","2");
				} else if (agent == "江门修理厂") {
					$("#selectStore").html("修理厂");
					$("#selectStore").attr("value","3");
				}

				getLtDot();
			} else {
				console.log('failed' + this.getStatus());
			}
		}, {
			enableHighAccuracy : true
		})
	} else {
		x = "";
		y = "";
		console.log("Geolocation is not supported by this browser.");
	}
}


function getLtDot(){//查询数据
	var type_dot="";
	if(type=="2"){
		type_dot=1;//小保养传参
	}else if(type=="3"){
		type_dot=2;//洗车传参
	}else if(type=="4") {
		type_dot=16;//喷漆传参
	}
	if(isLoad){
		return;
	}
	var selectArea= $("#selectArea").html();
	if(selectArea=="选择区"){
		selectArea="";
	}
	var data="";
	isLoad = true;
	load = $.loading();
	data={
			latStr:x,
			lngStr:y,
			province:"",
			city:$("#selectCity").html(),
			area:selectArea,
			type:type_dot,
			sellerType:$("#selectStore").attr("value"),
			orderType:$("#selectDefalt").attr("value"),
			sellerName:$.trim($("#sellerName").val())
	}
	data.pageNo = pageNo;
	data.pageSize = pageSize;
	$.ajax({
		url : DOMIN.MAIN+"/seller/getList",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: data,
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				if(pageNo==1){
					$("#dot_dl dd").remove();
				}
				if(data.pageInfo.pageCount == data.pageInfo.pageIndex){
					hasNext = false;
				}else{
					hasNext = true;
				}
				if(data.list.length>0){
					for(var i=0;i<data.list.length;i++){
						var html="";
							html+="<dd><div class='main' rel='"+data.list[i].id+"' sellerDes='"+data.list[i].sellerDes+"'>"
							html+='<div class="img-box">';
							if(data.list[i].sellerLogo && data.list[i].sellerLogo!=null){
								html+='<img rel="8" src="'+DOMIN.MAIN+'/imgget/getimg?uri='+data.list[i].sellerLogo+'&150X120">';
							}else{
								html+='<img rel="8" src="'+DOMIN.MAIN+'/resources/imgs/dot/img1.png/">';
							}
							if(data.list[i].sellerGrade=="1"){
								html+='<p class="green">快修店</p>';
							}else if(data.list[i].sellerGrade=="2"){
								html+='<p class="blue">4s店</p>';
							}else if(data.list[i].sellerGrade=="3"){
								html+='<p class="blue">修理厂</p>';
							}else if(data.list[i].sellerGrade=="0"){
								html+='<p class="blue">其他</p>';
							}
							html+='</div>';
							html+='<div class="text-box">';
							html+='<h3 rel="8">'+data.list[i].sellerName+'</h3>';
							html+='<div class="text-t">';
							html+='<span><b>'+data.list[i].orderCountOver+'</b>人已服务</span>';
							if(data.list[i].scoreService=="1"){
								html+='<p class="on-1"><i></i><i></i><i></i><i></i><i></i><span>1.0</span>分</p>';
							}else if(data.list[i].scoreService=="2"){
								html+='<p class="on-2"><i></i><i></i><i></i><i></i><i></i><span>2.0</span>分</p>';
							}else if(data.list[i].scoreService=="3"){
								html+='<p class="on-3"><i></i><i></i><i></i><i></i><i></i><span>3.0</span>分</p>';
							}else if(data.list[i].scoreService=="4"){
								html+='<p class="on-4"><i></i><i></i><i></i><i></i><i></i><span>4.0</span>分</p>';
							}else if(data.list[i].scoreService=="5"){
								html+='<p class="on-5"><i></i><i></i><i></i><i></i><i></i><span>5.0</span>分</p>';
							}
							html+='</div>';
							html+='<div class="text-b">';
							html+='<span>'+data.list[i].sellerDes+'km</span>';
							html+='<p>'+data.list[i].addressDetail+'</p>';
							if(powerorder_by){
								html+='<a style="color:red;" href="javascript:void(o)" onclick=appointment("'+data.list[i].id+'")>选择门店</a>';
							}
							html+='</div>';
							html+='</div></div></dd>';
							$("#dot_dl").append(html);
					}
				}else{
					hasNext = false;
					$("#dot_dl").append("<dd><p style='text-align: center;font-size: 14px;'>暂无数据</p></dd>");
				}
				mui(".main ").on("tap","img,span,p,h3",function(){
					var main_sellerId=$(this).parents(".main").attr('rel');
					var dotKm=$(this).parents(".main").attr('sellerDes');
					window.location.href=DOMIN.MAIN+"/seller/dot-detail.html?sellerId="+main_sellerId+"&type="+type+"&dotKm="+dotKm;
				})
			}else{
				$.tip(data.message);
			}
			isLoad = false;
			load.remove();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			isLoad = false;
			load.remove();
		}
	});
}
function appointment(sellerId){//预约
    sessionStorage.setItem('sellerId',sellerId);
    var packagetype;
	if(type==3){//洗车
		 data= {
			orderType:3,
			selectedSellerId:sellerId,
			goodsName:"洗车",
			goodsId:'',
			goodsNum:1,
			moneyAmount:30,
			//province:$("#province").val(),
			//city:$("#city").val(),
			//area:$("#area").val(),
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
		return;
	}
	location.href = DOMIN.MAIN + '/powerorder.html?type='+type;
	//$.ajax({
	//	url : DOMIN.MAIN+"/user/getPackageCode",
	//	type : "post",
	//	cache : false,
	//	async : true,
	//	dataType : "json",
	//	data: {
	//		packageType:packagetype
	//	},
	//	traditional: true,
	//	success : function(data, textStatus){
	//		if(data.success){
	//			userCode = data.data.packageCode;
	//			if(data.data.status!=0){//已经使用过直接跳转到订单页面
	//				location.href = DOMIN.MAIN + '/order/orderok?code='+userCode;
	//				return;
	//			}
	//			sessionStorage.setItem('packagecode',userCode);
	//			location.href = DOMIN.MAIN + '/powerorder.html?type='+packagetype;
	//		}else{
	//			$.tip(data.message);
	//		}
	//	},
	//	error : function(XMLHttpRequest, textStatus, errorThrown){
	//	}
	//});

}
var isloading=false;
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
function addCookies(name,value,time)
{
	var Days = 1;
	if(/^\d+$/.test(time)){
		Days = time;
	}
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*60*1000);
    document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
};
//获取城市参数
function getDefaultCity() {
	var cityLength = null;
	var defaultCity = "广州";
	if (getCookie("selcity") != null) {
		cityLength = getCookie("selcity").length;
		defaultCity = getCookie("selcity").substring(0,cityLength-1);
	}else if(getCookie("city") != null){
		cityLength = getCookie("city").length;
		defaultCity = getCookie("city").substring(0,cityLength-1);
	}
	if(userAddress){
		userAddress= decodeURI(userAddress);
		defaultCity = userAddress;
		sessionStorage.setItem('userAddress',userAddress);
	}
	if(sessionStorage.getItem('userAddress')){
		userAddress = sessionStorage.getItem('userAddress');
		defaultCity = userAddress;
	}
	$("#selectCity").text(defaultCity);
	//获取默认城市的cityId
	$.ajax({
		url : DOMIN.MAIN+"/address/searchByKeyWord",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			keyword: defaultCity
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success && data.list.length>0){
				cityId = data.list[0].id;
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});

}

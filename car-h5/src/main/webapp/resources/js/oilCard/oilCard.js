var pageNo = 1;
var pageSize = 10;
var hasNext = true;
var isLoad = false,load;
var cityId = null;
$(document).ready(function(){
	getLocation();
	getDefaultCity();//获取默认市区
	getLtDot();
	//获取市区
	$("#selectCity").on("click",function() {
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
		cityId = $(this).attr("city");
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
		html = '<li value="0">油品供应</li>'
			+  '<li value="1">商场超市</li>'
			+ '<li value="2">饭店服务 </li>'
			+ '<li value="3">其他采购 </li>'
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
			$("#storeName").show();
		}else{
			$(this).removeClass('on');
			$("#storeName").hide();
		};
		$("#storeName").val("");
		pageNo = 1;
		getLtDot();
	});
	// 搜索 
	$("#storeName").on('keyup',function(){
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
			if($('.mui-popup').length==0){
				$.tip('没有更多了！');
			}
		}
	}
}

function getLtDot(){//查询数据
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
	var seller_keyword="轮胎";
	data={
			city:$("#selectCity").html(),
			area:selectArea,
			merchantType:$("#selectStore").attr("value"),
			storeName:$.trim($("#storeName").val())
	}
	data.pageNo = pageNo;
	data.pageSize = pageSize;
	$.ajax({
		url : DOMIN.MAIN+"/merchant/getMerchantList",
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
							html+="<dd><div class='main'>";
							html+='<div class="img-box">';
							if(data.list[i].merchantType=="0"){
								html+='<img rel="8" src="'+DOMIN.MAIN+'/resources/imgs/dot/jiayouzhan.jpg/">';
								html+='<p class="green">油品供应</p>';
							}else if(data.list[i].merchantType=="1"){
								html+='<img rel="8" src="'+DOMIN.MAIN+'/resources/imgs/oilCard/shangchang.jpg/">';
								html+='<p class="blue">商场超市</p>';
							}else if(data.list[i].merchantType=="2"){
								html+='<img rel="8" src="'+DOMIN.MAIN+'/resources/imgs/oilCard/fandian.jpg/">';
								html+='<p class="blue">饭店服务</p>';
							}else if(data.list[i].merchantType=="3"){
								html+='<img rel="8" src="'+DOMIN.MAIN+'/resources/imgs/oilCard/qita.jpg/">';
								html+='<p class="blue">其他采购</p>';
							}
							html+='</div>';
							html+='<div class="text-box">';
							html+='<h3 rel="8">'+data.list[i].storeName+'</h3>';
							html+='<div class="text-b">';
							html+='<p>'+data.list[i].address+'</p>';
							html+='</div>';
							html+='<div class="text-c">';
							html+='<p>'+data.list[i].shortName+'</p>';
							html+='</div>';
							html+='</div></div></dd>';
							var obj = $(html);
							obj.appendTo($("#dot_dl"));
							geocodeSearch(data.list[i].address,obj);
					}
				}else{
					hasNext = false;
					$("#dot_dl").append("<dd><p style='text-align: center;font-size: 14px;'>暂无数据</p></dd>");
				}
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

var isloading=false;
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
var baiduMap ={};
$(function(){
	// 百度地图API功能
	//var map = new BMap.Map("l-map");
	//map.centerAndZoom(new BMap.Point(117.269945,31.86713), 13);
	//map.enableScrollWheelZoom(true);
	baiduMap.myGeo = new BMap.Geocoder();
	// 编写自定义函数,创建标注
	function addMarker(point,label){
		var marker = new BMap.Marker(point);
		map.addOverlay(marker);
		marker.setLabel(label);
	}
});
function geocodeSearch(add,obj){
	if(!x || !y){
		setTimeout(function(){
			geocodeSearch(add,obj);
		},200);
		return;
	}
	baiduMap.myGeo.getPoint(add, function(point){
		if (point) {
			//document.getElementById("result").innerHTML +=  index + "、" + add + ":" + point.lng + "," + point.lat + "</br>";
			var address = new BMap.Point(point.lng, point.lat);
			var myPoint = new BMap.Point(x, y);
			//addMarker(address,new BMap.Label(index+":"+add,{offset:new BMap.Size(20,-10)}));
			obj.find('.text-c p').append('&nbsp;&nbsp;<font style="color:red;display:inline-block;text-align:right;width:70%;float:right;">距离：'
					+getDistance(myPoint,address).toFixed(2)+'km&nbsp;&nbsp;<a style="color:red;" onclick="nav('+address.lat+','+address.lng+','+myPoint.lat+','+myPoint.lng+')">导航</a></font>');
		}
	}, "合肥市");
}
function nav(x1,y1,x2,y2){
	location.href="http://api.map.baidu.com/direction?origin="+x2+","+y2+"&destination="+x1+","+y1+"&mode=driving&region=广东&output=html";
}
function getDistance(point1,point2){
	var pk = 180 / 3.14169;    
	var a1 = point1.lat / pk; // jwd:116.397928,39.92925;pmzb:12957499.06,4827983.94;level:16  
	var a2 = point1.lng / pk;    
	var b1 = point2.lat / pk;  // jwd:116.408456,39.929637;pmzb:12958671.05,4828039.9;level:16  
	var b2 = point2.lng / pk;    
	var t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);    
	var t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
	var t3 = Math.sin(a1) * Math.sin(b1);    
	var tt = Math.acos(t1 + t2 + t3);
	return (6366000 * tt)/1000;  
}
var x,y;
function getLocation() {//定位
	if(getCookie("dotlng_x")!=null){
		x = getCookie("dotlng_x");
		y = getCookie("dotlat_y");
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
			} else {
				console.log('failed' + this.getStatus());
			}
		}, {
			enableHighAccuracy : true
		})
	} else {
		console.log("Geolocation is not supported by this browser."); 
	}	
}

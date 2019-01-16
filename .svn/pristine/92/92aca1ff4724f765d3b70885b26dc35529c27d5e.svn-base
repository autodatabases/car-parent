var status = 0;
if ($.getUrlParam("status") != null) {
	status = 1;
}
var citySet=[
	{citySet:1,"cityName":"广州市","pointSet":"113.2708136740,23.1351666766","searchData":"广州"},
	{citySet:2,"cityName":"惠州市","pointSet":"114.4233539430,23.1164256927","searchData":"惠州"}
]

var citySetNum = $.getUrlParam("citySet");
var targetSet =$.map(citySet,function(e,i){
	if(e.citySet==citySetNum) return e;
});
$(document).ready(function() {
	// 百度地图API功能
	var map = new BMap.Map("l-map");
	var cityPoint = targetSet[0].pointSet.split(",");
	map.centerAndZoom(new BMap.Point(cityPoint[0],cityPoint[1]), 13);
	//map.centerAndZoom(new BMap.Point(113.30765,23.120049), 13);广州
	//map.centerAndZoom(new BMap.Point(114.4233539430,23.1164256927), 13);惠州
	map.enableScrollWheelZoom(true);
	var myGeo = new BMap.Geocoder();

	var index = 0;
	var add = "";//地址
	var adds = [];//数据列表
	//信息窗口初始化
	var opts = {
				width : 260,     // 信息窗口宽度
				height: 0,     // 信息窗口高度
				enableMessage:true//设置允许信息窗发送短息
			   };
	//地址解析
	function addressResolve() {
		//标签内容
		var content = "";
		content = "<div style='font-size:12px;position:relative;margin-top:50px;'><p class='icon'></p><p style='color:#ff3e5b; font-size: 14px'>" + adds[index].name + "</p>";
		content += "<p> 地址：" + adds[index].address+ "</p>";
		content += "<p> 电话：</p>";
		if (adds[index].type == 2) {
			content += "<p><span style='flaot:right;color:blue;font-size:14px' surveyid='" + adds[index].id + "' class='detail'>公司详情>></span></p>";
		}
		content += "<p style='width:50%;margin: 0 auto'><button class='btn'>导航</button></p></div>";

		//图标颜色变换
		var url = "";
		if (adds[index].type == 2) {
			if (adds[index].cooperation == 0) {
				url = "../../../resources/imgs/counterman/mapIconl.png";
			} else {
				url = "../../../resources/imgs/counterman/mapIconr.png";
			}
		} else if (adds[index].type == 0) {
			url = "../../../resources/imgs/counterman/guoshou01.png";
		} else if (adds[index].type == 1) {
			url = "../../../resources/imgs/counterman/guoshou02.png";
		}

		var myIcon = new BMap.Icon(url, new BMap.Size(20, 32), {
		    anchor: new BMap.Size(10, 32)
		});
		if(adds[index].position!=""&&adds[index].position!=null){
			var points = adds[index].position.split(",");
			var point = new BMap.Point(points[0],points[1]);
			addMarker(point,content,myIcon);
		}else{//position未定义
			add = adds[index].address;
			myGeo.getPoint(add, function(point){
				if (point) {
					var address = new BMap.Point(point.lng, point.lat);
					addMarker(address,content,myIcon);
				}
			}, targetSet[0].cityName);
		}


		//解析地址
//		add = adds[index].address;
//		myGeo.getPoint(add, function(point){
//			if (point) {
//				var address = new BMap.Point(point.lng, point.lat);
//				addMarker(address,content,myIcon);
//			}
//		}, "广州市");
	}

	// 编写自定义函数,创建标注
	function addMarker(point,content,myIcon){
		var marker = new BMap.Marker(point, {icon: myIcon});
		//var marker = new BMap.Marker(point);
		map.addOverlay(marker);
		addClickHandler(content,marker);
	}
	function addClickHandler(content,marker){
		marker.addEventListener("click",function(e){
			openInfo(content,e)}
		);
	}
	function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
		map.openInfoWindow(infoWindow,point); //开启信息窗口
		setTimeout(navigation(point),1000)
	}

	getData();
	//获取数据
	function getData() {
		$.ajax({
			url: DOMIN.MAIN + "/gsbranch/getgsdata",
			type: "get",
			dataType: "json",
			data:{city:targetSet[0].searchData},
			async: true,
			success: function(data) {
				if (data.success) {
					adds = data.list;
					for(var i = 0; i < adds.length; i++) {
						addressResolve();
						index ++;
					}
				} else {
					$.tip(data.message);
				}
			},
			error: function(data) {
				$.tip("服务器错误");
			}
		});
	}
	//导航
	function navigation(address) {
		return function() {
			$(".btn").on('click',function() {
				var aimLat = address.lat;
				var aimLng = address.lng;
				var geolocation = new BMap.Geolocation();
				geolocation.getCurrentPosition(function(r){
			        if(this.getStatus() == BMAP_STATUS_SUCCESS){
			            var mk = new BMap.Marker(r.point);
			            map.addOverlay(mk);
			            var latCurrent = r.point.lat;
			            var lngCurrent = r.point.lng;

			            location.href="http://api.map.baidu.com/direction?origin="+latCurrent+","+lngCurrent+"&destination="+aimLat+","+aimLng+"&mode=driving&region="+targetSet[0].searchData+"&output=html";
			        }
			        else {
			            alert('failed'+this.getStatus());
			        }
			    },{enableHighAccuracy: true})
			})
			$(".detail").on('click',function() {
			    var surveyid = $(this).attr('surveyid');
			    window.location.href = DOMIN.MAIN + "/gsbranch/carshop-detail.html?surveyid=" + surveyid + "&status=" + status+ "&citySet=" + targetSet[0].citySet;
			})
		}


	}

})

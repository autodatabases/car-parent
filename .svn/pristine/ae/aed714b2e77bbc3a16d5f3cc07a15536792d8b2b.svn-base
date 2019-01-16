var id = $.getUrlParam('surveyid');
var status = $.getUrlParam('status');
var citySet=[
	{citySet:1,"cityName":"广州市","pointSet":"113.2708136740,23.1351666766","searchData":"广州"},
	{citySet:2,"cityName":"惠州市","pointSet":"114.4233539430,23.1164256927","searchData":"惠州"}
]

var citySetNum = $.getUrlParam("citySet");
var targetSet =$.map(citySet,function(e,i){
	if(e.citySet==citySetNum) return e;
});
$(document).ready(function() {
	getData();
	//获取网点详情
	function getData() {
		$.ajax({
			type: "get",
			url: DOMIN.MAIN+"/gsbranch/getSurveyCenter",
			async: true,
			dataType: "json",
			data: {
				id: id
			},
			success: function(data) {
				if(data.success) {
					renderPage(data);
				} else {
					$.tip(data.message);
				}
			},
			error: function(data) {
				$.tip("服务器错误");
			}
		});
	}
	function renderPage(records) {
		for (var key in records.data) {
			if (records.data[key] == null) {
				records.data[key] = "";
			}
		}
		var cooperationTime = $.formatDate(records.data.cooperationTime);
		if (records.data.cooperationTime == null || records.data.cooperationTime == "") {
			cooperationTime = "";
		}
		var cooperation = "未知";
		if(records.data.cooperation == 0) {
			cooperation = "是";
		} else if(records.data.cooperation == 1) {
			cooperation = "否";
		}
		var html = "",optionHtml="",yearHtml="";
		html += "<h2>" + records.data.name + "</h2>";
		html += "<ul class='list'>";
		//html += "<li><span>归属集团：</span>" + records.data.belongGroup + "</li>";
		html += "<li><span>主营车型：</span>" + records.data.mainCab + "</li>";
		html += "<li><span>是否合作：</span>" + cooperation + "</li>";
		html += "<li><span>合作时间：</span>" + cooperationTime + "</li>";
		html += "<li><span>车商地址：</span>" + records.data.address + "</li></ul>";
		html += "<div class='yearContainer' ><select class='yearSet'>";
		var yearRecords =records.data.surveyCenterBranch||[];
		for(var i=0; i<yearRecords.length; i++) {
			if(new Date().getFullYear()==yearRecords[i].year){
				optionHtml+="<option selected value="+yearRecords[i].year+">"+yearRecords[i].year+"年</option>";
			}else{
				optionHtml+="<option value="+yearRecords[i].year+">"+yearRecords[i].year+"年</option>";
			}
			yearHtml += "<ul class='yearDisplay year_"+yearRecords[i].year+"'>";
			var lossRationHtml =yearRecords[i].lossRation!=null?(yearRecords[i].lossRation* 100).toFixed(2) + "%":"---";
			var qiandanHtml =yearRecords[i].qiandanleiji!=null?yearRecords[i].qiandanleiji:"---";
			var premiumHtml =yearRecords[i].premium!=null?yearRecords[i].premium + "万":"---";
			var weixiuHtml =yearRecords[i].weixiuchanzhi!=null?yearRecords[i].weixiuchanzhi + "万":"---";
			var replaceRateHtml =yearRecords[i].replaceRate!=null?(yearRecords[i].replaceRate* 100).toFixed(2) + "%":"---";

			yearHtml += "<li><span>年度赔付率：</span>" + lossRationHtml + "</li>";
			yearHtml += "<li><span>年度签单累计：</span>" + qiandanHtml+ "</li>";
			yearHtml += "<li><span>累计保费收入：</span>" + premiumHtml + "</li>";
			yearHtml += "<li><span>维修产值：</span>" + weixiuHtml+ "</li>";
			yearHtml += "<li><span>置换率：</span>" + replaceRateHtml + "</li>";
			//yearHtml += "<li><span>同比增长：</span>" + records.data.premium + "万</li>";
			yearHtml += "</ul>";
		}
		html+=optionHtml+"</select></div>"+yearHtml,
		html += "<div class='localDirect'><button class='btn'>导航</button></li></div>";
		$(".content").html(html);
		var className=".year_"+$(".yearSet").val();
		$(className).show();
		navigation(records);
	}
	//导航
	function navigation(records) {
		$(".btn").on('click',function() {
			var points="",
				aimLng="",
				aimLat="";
			if(records.data.centerPosition!=null&&records.data.centerPosition!=''){
				points = records.data.centerPosition.split(",");
				aimLng = points[0];
				aimLat = points[1];
			}else{
				add = records.data.address;
				var myGeo = new BMap.Geocoder();
				myGeo.getPoint(add, function(point){
					if (point) {
						aimLng = point.lng;
						aimLat = point.lat;
					}else{
						$.tip("您选择地址没有解析到结果!");
					}
				}, targetSet[0].cityName);
			}
			
//			// 创建地址解析器实例
//			var myGeo = new BMap.Geocoder();
//			// 将地址解析结果显示在地图上,并调整地图视野
//			myGeo.getPoint(records.data.address, function(point){
//				if (point) {
//					aimLat = point.lat;
//					aimLng = point.lng;
//				}else{
//					alert("您选择地址没有解析到结果!");
//				}
//			}, "广州市");

			var geolocation = new BMap.Geolocation();
			geolocation.getCurrentPosition(function(r){
		        if(this.getStatus() == BMAP_STATUS_SUCCESS){
		            var latCurrent = r.point.lat;
		            var lngCurrent = r.point.lng;
		            location.href="http://api.map.baidu.com/direction?origin="+latCurrent+","+lngCurrent+"&destination="+aimLat+","+aimLng+"&mode=driving&region="+targetSet[0].searchData+"&output=html";
		        }
		        else {
		            alert('failed'+this.getStatus());
		        }
		    },{enableHighAccuracy: true})
		})
	}
	//年的选择事件
	$(document).on('change', '.yearSet', function(){
		var className=".year_"+$(this).val();
		var secectName =".yearDisplay:not("+className+")";
		$(secectName).hide();
		$(className).show();
	})
})

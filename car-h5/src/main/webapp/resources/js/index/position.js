/**
* 获取地址位置成功
*/
function showPosition(x,y) {
	//获得经度纬度

	//配置Baidu Geocoding API
	var url = "http://api.map.baidu.com/geocoder/v2/?ak=C93b5178d7a8ebdb830b9b557abce78b" +
			  "&callback=renderReverse" +
			  "&location=" + x + "," + y +
			  "&output=json" +
			  "&pois=0";

	$.ajax({
		type: "GET",
		dataType: "jsonp",
		url: url,
		success: function (json) {
			if (json == null || typeof (json) == "undefined") {
				return;
			}
			if (json.status != "0") {
				return;
			}
			try{
				var city = json.result.addressComponent.city;
				// console.log(json.result.addressComponent.province)
				// addCookie('province',json.result.addressComponent.province,360);
         		$('#cityname').text(city);
         		addCookie('city',city,360);
         		addHisttory(city);
         		/*$.ajax({
         			url:DOMIN.MAIN+'/user/setAddressInfo',
						type: 'POST',
						dataType: 'json',
						cache:false,
						asycn:false,
						data:{
							address:city
						},
						success:function(data){
							if(data.success){
							}else{
								alert(data.message);
							}

						},error:function(data){

						}
         		})*/
			}catch(exception){
				alert(exception);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			$.tip("[x:" + x + ",y:" + y + "]地址位置获取失败,请手动选择地址");
		}
	});
}

function getLocation() {
	if(getCookie('selcity')!=null){
		$('#cityname').text(getCookie('selcity'));
		return;
	}
	//检查浏览器是否支持地理位置获取
	if (navigator.geolocation) {
		//若支持地理位置获取,成功调用showPosition(),失败调用showPositionError
		//var config = { enableHighAccuracy: true, timeout: 3000, maximumAge: 30000 };
		//navigator.geolocation.getCurrentPosition(showPosition,showPositionError,config);
			var BMap_clearInterval=setInterval(function showTime(){
				 if(typeof BMap!="undefined"){
					clearInterval(BMap_clearInterval);
					var geolocation = new BMap.Geolocation();
					geolocation.getCurrentPosition(function(r) {
						if (this.getStatus() == BMAP_STATUS_SUCCESS) {
							showPosition(r.point.lat,r.point.lng);

						} else {
							console.log('failed' + this.getStatus());
						}
					}, {
						enableHighAccuracy : true
					})
				}
			},300);
		} else {
			console.log("Geolocation is not supported by this browser.");
		}
}

function addHisttory(city){
	addCookie('history_citys',city,360);
	return;
//	var addresslist = getCookie('history_citys');
//	//alert(addresslist);
//	if(addresslist==null){
//		addCookie('history_citys',city,180);
//	}else{
//		if(addresslist.indexOf(city)!=-1){
//			//alert('已经有了，不在添加.');
//			return;
//		}
//		var cityArry = addresslist.split(",");
//		if(cityArry.length>15){
//			for(var i=cityArry.length-1;i>=1;i--){
//				cityArry[i] = cityArry[i-1];
//			}
//			cityArry[0]=city;
//			addCookie('history_citys',cityArry.join(","),360);
//		}else{
//			addCookie('history_citys',city+','+addresslist,360);
//		}
//
//	}
}

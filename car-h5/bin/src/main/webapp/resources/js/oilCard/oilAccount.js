//根据cookie判断是否登录(没登录跳转登录页面)
if (getCookie('CAR_OIL_TOKEN') == null) {
	window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html?time=" + new Date().getTime();
}
$(document).ready(function () {
	//	渲染页面
	rendering();
	$(".phone1").on("click", function () {
		$(".tip1").show();
	});
	$(".phone2").on("click", function () {
		$(".tip2").show();
	});
	$(".btn_close").on("click", function () {
		$(".tip").hide();
	})
	//操作选项
	$(".operate ul li").on('click', function () {
		var operate = $(this).attr('class');
		// if (getTime("2018/3/28 18:0:0","2018/4/2 9:0:0")) {
		// 	$.tip("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因追电科技系统升级维护，追电科技的油卡充值和话费充值由2018年3月30日16点起至4月2日9点将暂时停止，敬请谅解。");
		// 	return;
		// }
		switch (operate) {
			case 'order':
				window.location.href = DOMIN.MAIN + "/oilUser/order-list.html";
				break;
			case 'recharge':
				window.location.href = DOMIN.MAIN + "/oilUser/recharge.html";
				break;
			// case 'coupons':
			// 	window.location.href = DOMIN.MAIN + "/oilUser/coupons.html";
			// 	break;
			case 'daijia':
				window.location.href = DOMIN.MAIN + "/driver/order.html";
				break;
			case 'chargecard':
				window.location.href = DOMIN.MAIN + "/oilUser/chargecard.html";
				break;
			case 'mobile':
				window.location.href = DOMIN.MAIN + "/oilUser/mobile.html";
				break;
			case 'didi':
				window.location.href = DOMIN.MAIN + "/oilUser/didi.html";
				break;
			case 'buy':
				window.location.href = DOMIN.MAIN + "/washcharge/washhome.html";
				break;
			case 'guide':
				window.location.href = DOMIN.MAIN + "/static/guide.html";
				break;
			case 'cateyes':
				window.location.href = DOMIN.MAIN + "/oilUser/cateyes.html";
				break;
			case 'videoCard':
				window.location.href = DOMIN.MAIN + "/oilUser/tovideo.html";
				break;
		}
	});
})
function rendering() {
	$.ajax({
		type: "get",
		url: DOMIN.MAIN + "/oilUser/infocenter",
		async: false,
		cache: false,
		dataType: "json",
		success: function (data) {
			if (data.success) {
				$(".account_phone").html(data.data.phone);
				$(".account_number").html(data.data.money);
				if (data.datasets) {
					addCookie("gaoyang", data.datasets.oilConfig.data.supplier, 360);
				} else {
					addCookie("gaoyang", 1, 360);
				}

			} else {
				$.tip(data.message)
			}
		}
	});
}

//时间比较 "2018/2/1 0:0:0"
function getTime(times01, times02) {
	var curDate = new Date();
	var startDate = new Date(times01);
	var endDate = new Date(times02)
	var t1 = curDate.getTime();
	var s1 = startDate.getTime();
	var s2 = endDate.getTime();
	if (t1 > s1 && t1 < s2) {
		return true;
	} else {
		return false;
	}
}

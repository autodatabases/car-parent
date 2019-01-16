//根据cookie判断是否登录(没登录跳转登录页面)
if (getCookie('CAR_OIL_TOKEN') == null) {
	window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
var index = 0;
var money = 0;
var goodId = "";
var arrHtml01 = "";
var arrHtml02 = "";
var flag = getCookie("gaoyang");
$(document).ready(function () {
	$(".bg").height($(document).height());
	setInterval(function () {
		var mydate = new Date();
		var date01 = new Date("2017/12/16 0:0:0");
		var date02 = new Date("2017/12/18 9:0:0");
		var my = mydate.getTime();
		var t1 = date01.getTime();
		var t2 = date02.getTime();
		if (my > t1 && my < t2) {
			$(".tip").show();
		} else {
			$(".tip").hide();
		}
	}, 10000)

	//$("#btn").prop('disabled','disabled').css({'background-color':'grey'});
	//	维护时间段提示
	setInterval(function () {
		var mydate = new Date();
		var myhours = mydate.getHours(); //获取当前小时数(0-23)
		var myminutes = mydate.getMinutes(); //获取当前分钟数(0-59)
		var myseconds = mydate.getSeconds(); //获取当前秒数(0-59)
		var t1 = myhours * 60 * 60 + myminutes * 60 + myseconds;
		var t2 = 22 * 60 * 60 + 50 * 60;//22点50
		var t3 = 50 * 60; //0点50
		var t4 = 23 * 60 * 60 + 59 * 60 + 59;
		if ((t1 > t2 && t1 <= t4) || (t1 >= 0 && t1 <= t3)) {
			$("#point").show();
		} else {
			$("#point").hide();
		}
	}, 3000);
	getData(0);
	$(".btn_close").on("click", function () {
		$(".tip").hide();
	})
})

function getData(goodTypeAll) {
	$.ajax({
		url: DOMIN.MAIN + '/oilUser/queryoilgood',
		type: 'POST',
		dataType: 'json',
		data: {
			goodType: goodTypeAll
		},
		success: function (data) {
			if (data.success) {
				renderHtml(data, goodTypeAll);
			} else {
				$.tip(data.message);
			}
		},
		error: function () {
			$.tip("服务器连接失败！");
		}
	})
}

function renderHtml(data, rechageType) {
	var strArray01 = [];
	var strArray02 = [];
	var strList = data.list;
	for (var i = 0; i < strList.length; i++) {
		if (rechageType != 1) {
			// 当商品的 status为2时候 货品不展示
			if (strList[i].status != 2) {
				strArray01.push(strList[i]);
			}
		} else {
			if (strList[i].status != 2) {
				strArray02.push(strList[i])
			}
		}
	}
	if (rechageType != 1) {
		strArray01 = arrSort(strArray01);
		arrHtml01 = getHtml(strArray01);
		$(".coupons01").html(arrHtml01);
	} else {
		strArray02 = arrSort(strArray02);
		arrHtml02 = getHtml(strArray02);
		$(".coupons02").html(arrHtml02);
	}
	operation()
}
function getHtml(strArr) {
	var html = "<ul>";
	for (var i = 0; i < strArr.length; i++) {
		html += "<li data-onstatus=" + strArr[i].oneStatus + " denomination=" + strArr[i].denomination + " sale=" + strArr[i].sale + " status=" + strArr[i].status + " goodid=" + strArr[i].id + "><p>" + strArr[i].denomination + "元</p></li>"
	}
	html += "</ul>";
	return html;
}
function arrSort(strArray) {
	for (var i = 0; i < strArray.length; i++) {
		for (var j = i + 1; j < strArray.length; j++) {
			if (parseInt(strArray[i].denomination) > parseInt(strArray[j].denomination)) {
				var temp = strArray[i];
				strArray[i] = strArray[j];
				strArray[j] = temp;
			}
		}
	}
	return strArray;
}
function operation() {
	//	tab切换
	$(".tab_btn span").on('click', function () {
		money = 0;
		goodId = "";
		index = $(this).index();
		if ($(this).prop('class') == "on") {
			return;
		}
		$(this).addClass('on').siblings().removeClass('on');
		$(".tab_con p").eq(index).removeClass('miss').siblings().addClass('miss');
		$(".coupons ul li").css({ "border-color": "#ccc", "color": "#333" });
		if (index == 0) {
			$(".coupons01").show();
			$(".coupons02").hide();
			getData(0)
		} else {
			$(".coupons02").show();
			$(".coupons01").hide();
			getData(1)
		}

	})
	//	选择面额
	var sale;
	$(".coupons ul li").on('click', function () {
		if ($(this).attr('data-onstatus') != 1) {
			$.tip("追电商品系统维护，请稍后重试");
			return;
		}
		if ($(this).attr('status') == 3) {
			// $.tip("抱歉，由于系统升级，中石化" + $(this).attr('denomination') + "元面额暂时不能充值！");
			$.tip("该商品库存不足，请选择其它商品！");
			return;
		} else if ($(this).attr('status') == 4) {
			$.tip("商品系统维护中，请选择其他商品！");
			return;
		}
		money = $(this).attr('denomination');
		// 售价
		sale = $(this).attr('sale');
		if ($(this).attr('goodId') === "" || $(this).attr('goodId') === "null") {
			goodId = "";
		} else {
			goodId = $(this).attr('goodId');
		}
		$(this).css({ "border-color": "#2aaff4", "color": "#2aaff4" }).siblings().css({ "border-color": "#ccc", "color": "#333" });
	})
	//充值
	$("#btn").off('click').on('click', function () {
		var url = DOMIN.MAIN + '/oilUser/chargeoil';
		var dataAll;
		var oilCardNo = $.trim($("#number").val());
		var mobile = $.trim($("#mobile").val());
		if (oilCardNo == "" || money == 0 || mobile == "") {//非空验证
			$.tip("油卡卡号、手机号或充值金额不能为空！");
			return;
		} else {
			//手机号码的格式的验证
			if (!/^\d{11}$/.test(mobile)) {
				$.tip("手机号码格式错误！");
				return;
			}
		}
		if (index == 0) {
			if (oilCardNo.length != 19 || oilCardNo.charAt(0) != "1") {
				$.tip("中石化加油卡是“100011”开头的19位卡号");
				return;
			}
			// 参数
			dataAll = {
				goodId: goodId,
				money: sale,
				oilCardNo: oilCardNo,
				mobile: mobile,
				orderType: 0
			}
			//$.tip("抱歉，由于系统升级，中石化暂时不能充值！");
			//return;
		} else if (index == 1) {
			if (oilCardNo.length != 16 || oilCardNo.charAt(0) != "9") {
				$.tip("中石油加油卡是“9”开头的16位卡号");
				return;
			}
			dataAll = {
				goodId: goodId,
				money: sale,
				oilCardNo: oilCardNo,
				mobile: mobile,
				orderType: 1
			}
			//$.tip("抱歉，由于系统升级，中石油暂时不能充值！");
			//return;
		}
		$("#btn").val("正在充值...");
		$("#btn").attr("disabled", true);
		$("#btn").css("background", "#ccc");
		$.ajax({
			type: "post",
			url: url,
			cache: false,
			async: true,
			dataType: "json",
			data: dataAll,
			success: function (data) {
				if (data.success) {
					$.tip("订单已提交，请耐心等待充值结果!");
				} else {
					if (data.message.indexOf("1007_该面额库存不足,正紧急调货,请稍后充值 ...") !== -1 || data.message.indexOf("301_该面额库存不足,正紧急调货,请稍后充值 ...") !== -1) {
						$(".tip1").show();
					} else {
						$.tip(data.message);
					}
				}
				$("#btn").val("立即充值");
				$("#btn").attr("disabled", false);
				$("#btn").css("background", "#2aaff4");
			},
			error: function (data) {
				$.tip("链接服务器失败!");
				$("#btn").val("立即充值");
				$("#btn").attr("disabled", false);
				$("#btn").css("background", "#2aaff4");
			}

		});

	})
}

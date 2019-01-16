//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
var supplier = getCookie("gaoyang");
$(document).ready(function() {
	$(".bg").height($(document).height());

	$(".back").on('click',function() {
		window.location.href = DOMIN.MAIN + "/oilUser/oilAccount.html";
	});
	$(".btn_close").on("click",function() {
		$(".tip").hide();
	})
	getData();
})
function getData() {
	$.ajax({
		url: DOMIN.MAIN + '/oilUser/queryall',
		type: 'get',
		dataType: 'json',
		data: {
			pageNo: 1,
			pageSize: 5000,
			supplier: supplier
		},
		success: function(data) {
			if (data.success) {
				renderHtml(data);
			} else {
				$.tip(data.message);
			}
		},
		error: function() {
			$.tip("服务器连接失败！");
		}
	})
}

function renderHtml(data) {
	var strArray = [];
	var strList = data.list;
	for (var i = 0; i < strList.length; i++) {
		if (strList[i].status !== 2) {
			if (strList[i].cardType === 2) {
				strArray.push(strList[i]);
			}
		}
	}
	for (var i=0; i < strArray.length; i++) {
		for (var j=i+1; j < strArray.length; j++) {
			if(parseInt(strArray[i].content) > parseInt(strArray[j].content)) {
				var temp = strArray[i];
				strArray[i] = strArray[j];
				strArray[j] = temp;
			}
		}
	}
	var html = "<ul>";
	for (var i = 0; i < strArray.length; i++) {
		html += "<li content=" + strArray[i].content + " status=" + strArray[i].status + " cardType=" + strArray[i].cardType + "><p>" + strArray[i].content + "元</p></li>"
	}
	html += "</ul>";
	$(".coupons ul").html(html);
	operate(strArray);
}
function operate(strArray) {
	if (supplier === '1') {
		oufei();//欧飞
	} else if (supplier === '2') {
		gaoyang(strArray);//高阳
	}
}
function oufei() {
	var money = "";
    //	选择面额
	$(".coupons ul li").on('click',function() {
		if ($(this).attr('status') == 1) {
			$.tip("抱歉，由于系统升级，话费" + $(this).attr("content") + "元面额暂时不能充值！");
			return;
		}
		money = $(this).attr("content");
		$(this).css({"border-color":"#2aaff4","color":"#2aaff4"}).siblings().css({"border-color":"#ccc","color":"#333"});
	})
	//充值
	$("#btn").on('click',function() {
		recharge(money,"");
	})
}
function gaoyang(strArray) {
	var prodId = "";
	var money = "";
	$("#mobile").on('input',function() {
		if($(this).val().length > 11) {
			$(this).val($(this).val().substr(0,11));
		}
		if($(this).val().length == 11) {
			$.ajax({
				url: DOMIN.MAIN + '/oilUser/queryGyProduct',
				type: 'POST',
				dataType: 'json',
				data: {
					phone: $(this).val()
				},
				success: function(data) {
					if(data.success) {
						for(var i = 0; i<data.list.length; i++) {
							for(var j = i + 1; j<data.list.length; j++) {
								if(parseInt(data.list[i].prodContent) > parseInt(data.list[j].prodContent)) {
									var temp = data.list[i];
									data.list[i] = data.list[j];
									data.list[j] = temp;
								}
							}
						}
						html = "";
						for (var i = 0; i < data.list.length; i++) {
							for(var j = 0; j < strArray.length; j++) {
								if(data.list[i].prodContent === strArray[j].content) {
									html += "<li content=" + strArray[j].content + " status=" + strArray[j].status + "><p pid='" + data.list[i].prodId + "'>" + data.list[i].prodContent + "元</p></li>";
								}
							}
						}
						$(".coupons ul").html(html);
						//	选择面额
						$(".coupons ul li").on('click',function() {
							if ($(this).attr('status') == 1) {
								$.tip("抱歉，由于系统升级，话费" + $(this).attr("content") + "元面额暂时不能充值！");
								return;
							}
							prodId = $(this).children('p').attr('pid');
							money = $(this).attr('content');
							$(this).css({"border-color":"#2aaff4","color":"#2aaff4"}).siblings().css({"border-color":"#ccc","color":"#333"});
						})
					} else {
						$(".coupons ul").html("");
						$.tip(data.message);
					}
				},
				error: function(data) {
					$.tip("链接服务器失败!");
				}
			})
		}
	});
	//充值
	$("#btn").on('click',function() {
		recharge(money,prodId);
	})
}
function recharge(money,prodId) {//充值
	var url = '';


	var mobile = $.trim($("#mobile").val());
	if (!/^\d{11}$/.test(mobile)) {
		$.tip("请输入正确格式的手机号！");
		return ;
	}
	if ( prodId === "" && money === "") {//非空验证
		$.tip("请选择充值金额！");
		return;
	}
	url = DOMIN.MAIN + '/oilUser/phoneRecharge';
	$("#btn").val("正在充值...");
	$("#btn").attr("disabled",true);
	$("#btn").css("background","#ccc");
	$.ajax({
		type:"post",
		url: url,
		cache:false,
		async:true,
		dataType: "json",
		data : {
			phone: mobile,
			proId: prodId,
			supplier: supplier,
			money: money
		},
		success: function(data) {
			if(data.success) {
				$.tip("<p>提交成功！</p><p style='line-height:2rem;'>订单号：<span style='color:#2aaff4'>" + data.data + "</span></p> <p>请耐心等待充值结果!</p>");
			} else {
				if(data.message.indexOf("1007_该面额库存不足,正紧急调货,请稍后充值 ...") !== -1 || data.message.indexOf("下单失败,1007_该面额库存不足,请稍后充值") !== -1) {
					$(".tip1").show();
				} else {
					$.tip(data.message);
				}
			}
			$("#btn").val("立即充值");
			$("#btn").attr("disabled",false);
			$("#btn").css("background","#2aaff4");
		},
		error: function(data) {
			$.tip("链接服务器失败!");
			$("#btn").val("立即充值");
			$("#btn").attr("disabled",false);
			$("#btn").css("background","#2aaff4");
		}

	});
}

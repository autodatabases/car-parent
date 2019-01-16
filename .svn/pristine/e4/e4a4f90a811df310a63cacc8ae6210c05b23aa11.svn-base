//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
$("document").ready(function(){
	var orderNo = getParam("orderNo");
	if(orderNo == null){
		mui.alert("无订单详情，请重试","提示",function(){
			window.location.href = DOMIN.MAIN + "/oilUser/oilAccount.html";
		});
	};
	//后台获取优惠券详情
	$.ajax({
		url:DOMIN.MAIN+'/oilUser/queryvideocardinfo',
		async : false,
		type:'POST',
		dataType:'json',
		data:{
			orderNo:orderNo
		},
		error:function(data){
			$.tip("链接服务器失败！");
		},
		success:function(data){
			var result = data.data;
			var coupon = {
				type : result.chargeType,     //类别
				finishMoney : result.finishMoney,  //充值金额
				cardNo : result.cardNo,   //卡号
				cardSecret : result.cardSecret,   //兑换码
				name : result.goodIntroduce,   //商品名称
				orderNo : result.orderNo,   //订单号
				finishTime : result.finishTime,   //下单时间
				info : result.usefulLife,   //过期时间提示语
				src : ""   //logo图片地址
			};
			//通过优惠券类别更改LOGO图片
			switch (coupon.type) {
				case 1 || 2 : coupon.src = "../resources/imgs/oilCard/home/home_dianziquan.png";
					break;
				case 3 || 4 : coupon.src = "../resources/imgs/oilCard/home/home_huafeichongzhi.png";
					break;
				case 5 : coupon.src = "../resources/imgs/oilCard/home/home_cateyes.png";
					break;
				case 6 : coupon.src = "../resources/imgs/oilCard/home/home_didi.png";
					break;
				case 7 : coupon.src = "../resources/imgs/oilCard/video/youku.png";
					break;
				case 8 : coupon.src = "../resources/imgs/oilCard/video/aiqiyi.png";
					break;
				case 9 : coupon.src = "../resources/imgs/oilCard/video/tx.png";
					break;
				case 10 : coupon.src = "../resources/imgs/oilCard/video/souhu.png";
					break;
				default:
					break;
			};



			// 字符串拼接生成页面
			var html = '<div class="cont_top">';
				html += '<img src="'+coupon.src+'" alt="" class="coupon_logo">';
				html += '<p class="money">-'+coupon.finishMoney.toFixed(2)+'</p>';
				html += '<p>订单支付成功</p>';
				html += '<p class="cardSecret"><span>卡&nbsp;&nbsp;&nbsp;&nbsp;号</span>：'+coupon.cardNo+'</p>'
				html += '<p class="cardSecret"><span>兑换码</span>：'+coupon.cardSecret+'</p>';
				html += '</div>';
				html += '<div class="cont_bottom">';
				html += '<p>商品名称：'+coupon.name+'</p>';
				html += '<p>订单号：'+coupon.orderNo+'</p>';
				html += '<p>下单时间：'+$.formatDate(coupon.finishTime)+'</p>';
				html += '<p class="info">使用有效期：'+coupon.info+'</p>';
				html += '</div></div>';
			
			$("#content").html(html);
		}
	});
})

// 获取url后制定参数值
function getParam(paramName) { 
    paramValue = "", isFound = !1; 
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) { 
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0; 
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++ 
    } 
    return paramValue == "" && (paramValue = null), paramValue 
}
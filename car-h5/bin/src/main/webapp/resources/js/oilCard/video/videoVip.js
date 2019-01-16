//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
};
$("document").ready(function(){
	//判断会员卡种类
	var goodType = getParam("goodType");
	if(goodType == null){
		mui.alert("获取会员卡失败，请重试","提示",function(){
			window.location.href = DOMIN.MAIN + "/oilUser/oilAccount.html";
		});
	};

	//后台获取会员卡
	$.ajax({
		url:DOMIN.MAIN+'/oilUser/queryoilgood',
		async : false,
		type:'POST',
		dataType:'json',
		data:{
			goodType : goodType
		},
		error:function(data){
			$.tip("链接服务器失败！");
		},
		success:function(data){
			var result = data.list;
			if (data.success) {
                renderHtml(result);
            } else {
                $.tip(data.message);
            }
		}
	});


	var coupon_id = "";	//会员卡ID
	var coupon_price = "";	//会员卡价格
	var goodsName = ""; //弹出框题头描述
	var orderType = getParam("orderType");   //会员卡种类


	//点击会员卡事件
	var coupon_li = $("#cont .coupon_li");
	coupon_li.on("click",function(){
		if ($(this).attr('data-onstatus') != 1) {
            $.tip("追电商品系统维护，请稍后重试");
            return;
        }
		//判断会员卡库存  states值
		if($(this).attr("data-status") == 3){
			$.tip("该商品库存不足，请选择其它商品！");
			return;
		}else if($(this).attr("data-status") == 4){
			$.tip("商品系统维护中，请选择其它商品！");
			return;
		}

		//选择的唯一性  去除选择的其它会员卡
		$(this).addClass("on").siblings().removeClass("on");

		//点击事件获取会员卡的参数
		coupon_id = $(this).attr("data-id");
		coupon_price = $(this).attr("sale");
		goodsName = $(this).attr("data-goodsName");

		//改变按钮的金额
		$("#btn_pay").val("立即支付："+ coupon_price).removeAttr("disabled");
	});



	//支付按钮点击事件
	$("#btn_pay").on("click",function(){
		var btnArray = ['取消', '确定'];
        mui.confirm('<div style="margin:1rem 0">需支付：<span style="font-size: 18px;color: #2996EA;">'+coupon_price+'</span></div>', goodsName, btnArray, function (e) {
            if (e.index == 1) {
                require_coupon(coupon_id,coupon_price,orderType);
            }
        })
	})
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


function renderHtml(data){
	//console.log(data);
	$(data).each(function(index,element){
		var html = '';
		var sale = element.sale.toString().indexOf('.') != -1 ? sale = element.sale :sale = element.sale + '.00';
		html += '<li class="coupon_li status'+element.status+'" sale="'+sale+'" data-status="'+element.status+'" data-id="'+element.id+'"  data-goodsName="'+element.goodsName+'" data-onstatus="'+element.oneStatus+'">';
		html += '<i></i>';
		html += '<dl class="coupon">';
		html += '<dt>'+element.goodsName+'</dt>';
		html += '<dd>';
		html += '<div class="money">￥<span class="num">'+sale+'</span></div>';
		html += '<img src="../../resources/imgs/oilCard/video/buy.jpg" alt="" class="buy">';
		html += '</dd>';
		html += '</dl>';
		html += '</li>';

		$("#cont").append(html);
	});
}


//后台获取优惠券
function require_coupon(goodId,money,orderType){
	$.ajax({
		url:DOMIN.MAIN+'/oilUser/getcardsecret',
		async : true,
		type:'POST',
		data:{
			goodId:goodId,
			money:money,
			orderType:orderType
		},
		dataType:'json',
		error:function(data){
			$.tip("链接服务器失败！");
		},
		success:function(data){
			var require_data = data;
			console.log(data);
			if(require_data.success == false){
				$.tip(require_data.message);
			}else{
				var btnArray = ['查看详情', '确定'];
		        mui.confirm('<div  style="line-height:2rem;">兑换卡密：'+require_data.data.cardSecret+'</div>', '<p style="color:#2aaff4">支付成功</p>', btnArray, function (e) {
		            if (e.index == 0) {
						window.location.href = DOMIN.MAIN + "/oilUser/oilCardDetail.html?orderNo="+require_data.data.orderNo;
		            }
		        })
			}
		}
	});
}
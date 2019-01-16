//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
$("document").ready(function(){
	//后台获取优惠券
	$.ajax({
		url:DOMIN.MAIN+'/oilUser/querydidi',
		async : false,
		type:'POST',
		dataType:'json',
		error:function(data){
			$.tip("链接服务器失败！");
		},
		success:function(data){
			//console.log(data);
			var list = data.list;
			if(data.success == false || list.length <= 0){  //判断是否成功以及是否有优惠券
				mui.alert("暂无优惠券","确定",function(){
					window.location.href = DOMIN.MAIN + "/oilUser/oilAccount.html";
				});
			}else{
				$(list).each(function(index,element){
					joinHtml(this);		//各类别优惠券生成html
				})
			}
		}
	});


	
	var coupon_id = "";	//优惠券ID
	var coupon_type = "";   //优惠券类别
	var coupon_price = "";	//优惠券价格
	var goodsName = ""; //弹出框题头描述

	//点击优惠券事件
	var coupon_li = $(".cont .coupon_li");
	coupon_li.on("click",function(){
		if ($(this).attr('data-onstatus') != 1) {
            $.tip("追电商品系统维护，请稍后重试");
            return;
        }
		//判断优惠券库存  states值
		if($(this).attr("data-status") == 3){
			$.tip("该商品库存不足，请选择其它商品！");
			return;
		}else if($(this).attr("data-status") == 4){
			$.tip("商品系统维护中，请选择其它商品！");
			return;
		}

		//选择的唯一性  去除选择的其它券
		coupon_li.each(function(){
			$(this).removeClass("on");
		});
		$(this).addClass("on");

		//点击事件获取优惠券的参数
		coupon_id = $(this).attr("data-id");
		coupon_type = $(this).find(".cate").html();
		coupon_price = $(this).attr("data-money");
		coupon_price = coupon_price.toString().indexOf('.') != -1 ? coupon_price : coupon_price + '.00'
		
		goodsName = $(this).attr("data-goodsName");

		//改变按钮的金额
		$("#btn_pay").val("立即支付："+ coupon_price).removeAttr("disabled");
	})


	

	//支付按钮点击事件
	$("#btn_pay").on("click",function(){
		var btnArray = ['取消', '确定'];
        mui.confirm('<div style="line-height:2rem;">需支付：<span style="color:#2996EA;font-size: 18px;">'+coupon_price+'</span></div>', goodsName, btnArray, function (e) {
            if (e.index == 1) {
                require_coupon();
            }
        })
	})

	//后台获取优惠券
	function require_coupon(){
		$.ajax({
			url:DOMIN.MAIN+'/oilUser/getcardsecret',
			async : true,
			type:'POST',
			data:{
				goodId:coupon_id,
				money:coupon_price,
				orderType:6
			},
			dataType:'json',
			error:function(data){
				$.tip("链接服务器失败！");
			},
			success:function(data){
				var require_data = data;
				//console.log(require_data);
				if(require_data.success == false){
					$.tip(require_data.message);
				}else{
					var btnArray = ['查看详情', '确定'];
			        mui.confirm('<div style="line-height:2rem;">兑换卡密：'+require_data.data.cardSecret+'</div>', '<p style="color:#2aaff4">支付成功</p>', btnArray, function (e) {
			            if (e.index == 0) {
							window.location.href = DOMIN.MAIN + "/oilUser/oilCardDetail.html?orderNo="+require_data.data.orderNo;
			            }
			        })
				}
			}
		});
	}

})


// 初始页面优惠券字符串拼接
function joinHtml(data){
	var didi_money = "";   //优惠券金额
	var didi_type = "";   //种类  优惠券||出行卡||电子券
	//console.log(data);
	var html = '<div class="cont">';
		html += '<h3 class="title">滴滴'+data.typeName+'</h3>';
		html += '<ul class="coupon_ul">';
		

		$(data.oilGoods).each(function(index,element){
			didi_money = element.denomination.split("元")[0];
			didi_type = element.denomination.split("元")[1];


			html += '<li class="coupon_li status'+element.status+'" data-money="'+element.sale+'" data-id="'+element.id+'" data-status="'+element.status+'" data-goodsName="'+element.goodsName+'" data-onStatus="'+element.oneStatus+'">';
			html += '<i></i>';
			html += '<div class="coupon">';
			html += '<div class="intr">';
			html += '<dl>';
			html += '<dt>';
			html += '<span class="num">'+didi_money+'</span>元';
			html += '</dt>';
			html += '<dd>'+didi_type+'</dd>';
			html += '</dl>';
			html += '<hr>';
			html += '<p class="cate">滴滴'+data.typeName+'</p>';
			html += '</div>';
			html += '<div class="purchase">点击<br>购买</div>';
			html += '</div>';
			html += '</li>';
		})	
		html += '</ul></div>';
		$("#cont").append(html);		
}
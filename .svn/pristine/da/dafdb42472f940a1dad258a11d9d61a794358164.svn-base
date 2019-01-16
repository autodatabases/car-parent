$("document").ready(function(){

	//后台获取兑换优惠券数目
	$.ajax({
		url:DOMIN.MAIN+'/didi/getcouponnum',
		async : false,
		type:'POST',
		dataType:'json',
		error:function(data){
			$.tip("链接服务器失败！");
		},
		success:function(data){
			if(data.success){
				if(data.data.unusedCoupon20 != 0 || data.data.unusedCoupon50 != 0){
					renderHtml(20,data.data.unusedCoupon20);
					renderHtml(50,data.data.unusedCoupon50);
				}else{
					$(".warp").hide();
					$(".no_have").show();
				}
			}else{
				$.tip(data.message);
			}
		}
	});

	// 生成html模板
	function renderHtml(couponMoney,couponNum){
		var html = '';
		for(var i = 0;i < couponNum;i++){
			html += '<li data-money="'+couponMoney+'">' 
				 + '<i class="logo"></i>' 
				 + '<div class="content">' 
				 + '<span>'+couponMoney+'</span>元优惠券' 
				 + '</div>'
				 + '<div class="right">'
				 + '立即<br>兑换'
				 + '<i></i>'
				 + '</div>'
				 + '</li>';
		}
		$('#coupon').append(html);

	}
	//点击优惠券事件
	var coupon_li = $("#coupon li");
	coupon_li.on("click",function(){
		var money = $(this).attr("data-money");
		var btnArray = ['取消', '确定'];
        mui.confirm('是否兑换滴滴代驾'+money+'元优惠券','',btnArray, function (e) {
            if (e.index == 1) {
                couponorders(money);
            }
        })
	})


	// 兑换接口
	function couponorders(money){
		$.ajax({
			url:DOMIN.MAIN+'/didi/addcouponorders',
			async : false,
			type:'POST',
			data:{
				content:money
			},
			dataType:'json',
			error:function(data){
				error();
			},
			success:function(data){
				if(data.success){
					success(money,data.data.cardSecret);
				}else{
					error();
				}
			}
		});
	}

	// 兑换成功弹窗
	function success(money,cardSecret){
		var btnArray = ['确定'];
		var content =  '<div class="mui_content"><p>滴滴代驾'+money+'元优惠券</p>' +
					   '<p>兑换码：'+cardSecret+'</p>' +
					   '<p style="color:#ff3e59">请在三个月内使用</p></div>';
        mui.confirm(content,'兑换成功',btnArray, function (e) {
            if (e.index == 0) {
                window.location.reload();
            }
        })
	}

	// 兑换失败
	function error(){
		var btnArray = ['确定'];
        mui.confirm('系统维护中，暂时无法出货<br/>请稍后重试','提示',btnArray)
	}
})
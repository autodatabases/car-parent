//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html?time="+new Date().getTime();
}
$(document).ready(function(){
	getData();//渲染页面

    $(".washRecord").on('click',function() {
        location.href = DOMIN.MAIN + "/washcharge/washrecord.html"
    });
	$(".purchase").on('click',function() {
        location.href = DOMIN.MAIN + "/washcharge/purchaserecord.html"
    });
	$(".wangdian").on('click',function() {
        location.href = "http://sd.auto1768.com:8078/ShengDaPointQuery/point/city.jhtm?source=GDGSCX";
    });
	$(".bg").height($(document).height());
});
function getData() {
	$.ajax({
		url: DOMIN.MAIN + '/washcharge/carWashData',
		type: 'get',
		dataType: 'json',
		success: function(data) {
			if(data.success) {
				rendering(data);
			} else {
				$.tip(data.message);
			}
		},
		error: function() {
			$.tip("服务器有点累了");
		}
	})
}
function rendering(data) {
	$(".washTimes").html((data.data.carWashTime != "null")?data.data.carWashTime:0);
	$(".ticket").html((data.data.unUsed.list.length != 0)?1:0);
	if(data.data.unUsed.list.length != 0) {
		$(".code").attr('orderNo', data.data.unUsed.list[0].orderNo);
	}
	if (data.data.products.list) {
		var html = "";
		$.each(data.data.products.list,function(index, el) {
			var n = "";
			switch (index) {
				case 0:
					n = "一";
					break;
				case 1:
					n = "二";
					break;
				case 2:
					n = "三";
					break;
			}
			if (el.presentNumber) {
				html += '<li pid="' + el.id + '" price="' + el.price + '"><p>套餐' + n + '</p><p>购买洗车服务<span>' + el.buyNumber + '</span>次</p><p>(额外赠送洗车服务<span>' + el.presentNumber + '</span>次)</p></li>';
			} else {
				html += '<li pid="' + el.id + '" price="' + el.price + '"><p>套餐' + n + '</p><p>购买洗车服务<span>' + el.buyNumber + '</span>次</p></li>';
			}
		});
		$(".setmale ul").html(html);
		$(".setmale ul li:first-child").addClass('on');
		$(".btncon .btn").html('立即购买（￥<span>20</span>）')
		$(".btn span").html($(".setmale ul li:first-child").attr("price"));
		$(".btn").attr("pid",$(".setmale ul li").attr("pid"));
		$("#tip02 h3").html($(".setmale ul li:first-child p:first-child").html());
		$("#tip02 .cont").html($(".setmale ul li:first-child").html());
		$("#tip02 .cont p:nth-child(2)").append('<p style="display:inline-block">；&nbsp&nbsp价格：<span>￥' + $(".setmale ul li:first-child").attr("price") + '</span></p>');
		$("#btnOk02").attr("pid",$(".setmale ul li").attr("pid"));
	}
	operate();
}
function operate() {
	$(".setmale ul li").off('click').on('click',function() {
		$(this).addClass('on');
		$(this).siblings().removeClass("on");
		$(".btn span").html($(this).attr("price"));
		$(".btn").attr("pid",$(this).attr("pid"));
		$("#tip02 h3").html($(this).children('p:first-child').html());
		$("#tip02 .cont").html($(this).html());
		$("#tip02 .cont p:nth-child(2)").append('<p style="display:inline-block">；&nbsp&nbsp价格：<span>￥' + $(this).attr("price") + '</span></p>');
		$("#btnOk02").attr('pid', $(this).attr("pid"));
	});
	$(".code").off('click').on('click',function() {
		//location.href = DOMIN.MAIN + "/washcharge/washcode.html?orderNo=" + $(this).attr("orderNo");
		if($(this).attr("orderNo")) {
			location.href = DOMIN.MAIN + "/washcharge/washcode.html?orderNo=" + $(this).attr("orderNo");
		} else {
			$("#tip01").show();
		}
    });
	$(".exchage").off('click').on('click', function() {
		if($(".code").attr("orderNo")) {
			location.href = "http://sd.auto1768.com:8078/ShengDaPointQuery/point/city.jhtm?source=GDGSCX";
		} else {
			$("#tip01").show();
		}
	});
	$("#btnOk01").off('click').on('click',function() {
		$("#tip01").hide();
		$.ajax({
			type:"post",
			url: DOMIN.MAIN + '/washcharge/sendCarWashSheng',
			cache:false,
			async:true,
			dataType: "json",
			success: function(data) {
				if(data.success){
					getData();
					$.tip("兑换洗车券码成功!\n请前往“洗车券码”查看洗车券码\n或点击“立即洗车”进行洗车。");
				}else{
					$.tip(data.message);
				}
			},
			error: function(data) {
				$.tip("链接服务器失败!");
			}
		});
	});
	$(".btn").off('click').on('click', function() {
		$("#tip02").show();
	});
	$("#btnOk02").off('click').on('click', function() {
		$("#tip02").hide();
		var productId = $(this).attr('pid');
		$.ajax({
		   type:"post",
		   url: DOMIN.MAIN + "/washcharge/addpay",
		   cache:false,
		   async:true,
		   dataType: "json",
		   data: {
			   productId: productId
		   },
		   success: function(data) {
			   if(data.success){
				   getData();
				   $(".btn").attr('disabled', false);
				   $.tip("套餐购买成功");
			   }else{
				   $.tip(data.message);
				   getData();
				   $(".btn").attr('disabled', false);
			   }
		   },
		   error: function(data) {
			   $.tip("链接服务器失败!");
			   getData();
			   $(".btn").attr('disabled', false);
		   }
		});
	});
	$(".btn_close").off('click').on('click', function() {
		$(".tip").hide();
	});
}

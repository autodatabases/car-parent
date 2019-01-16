//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
$(document).ready(function() {
	var money = 0;
	var count = 1;
	//选取电子券面额
	$(".coupons ul li").on('click',function() {
		$(this).css({"border-color":"#0ab519","color":"#0ab519"}).siblings().css({"border-color":"#ccc","color":"#333"});
		money = ($(this).index() + 1) * 100;
		money = String(money);
	})
	$("#send").on('click',function() {
		//$.tip('抱歉，短信电子券将于2-3工作日内开通');
		//return;
		if(money == 0) {
			$.tip("请选择电子券！");
			return ;
		}
		if ($.trim($("#number").val()) != "") {
			count = $.trim($("#number").val());
			var r = /^\+?[1-9][0-9]*$/;
			if (!(r.test(count))) {
				$.tip("请输入正确格式的数量!");
				return;
			} else if (count > 9) {
				$.tip("单次电子券购买数量最多为9张!");
				return;
			}
		} else {
			$.tip("请输入正确格式的数量!");
			return;
		}
		//电子券失效性提示
		var btnArray = ['确定','取消'];
		mui.confirm("该商品为一次性短信消费券，有效期一个月，请您根据需求购买","温馨提示",btnArray,function(e) {
			if(e.index == 1) {
				return;
			} else {
				$("#send").val("正在发送");
				$("#send").attr("disabled","disabled");
				$.ajax({
					url : DOMIN.MAIN+"/oilUser/sendSmSOilCode",
					type: 'POST',
					dataType: 'json',
					cache:false,
					asycn:true,
					data:{
						money: money,
						count: count
					},
					success : function(data){
						if(data.success){
							$.tip("发送成功！");
						} else {
							$.tip(data.message);
						}
						$("#send").val("立即发送");
						$("#send").attr("disabled",false);
					},
					error : function(data){
						$.tip("链接服务器失败！");
						$("#send").val("立即发送");
						$("#send").attr("disabled",false);
					}
				});
			}
		});


	})

})

//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
$("document").ready(function() {
	var rechargeCode = 0;
	
	//	充值
	$("#recharge").on('click',function() {
		if ($.trim($("#number").val()) != "") {
			if ($.trim($("#number").val()).length != 18) {
				$.tip("请输入正确的18位充值码！");
				return;
			}
			rechargeCode = $.trim($("#number").val());
		}else{
			$.tip("请输入正确充值码！");
			return ;
		}
		if(rechargeCode == 0) {
			$.tip("请输入正确充值码！");
			return ;
		}
		$("#send").val("正在充值");
		$("#send").attr("disabled","disabled");
		$.ajax({
			url : DOMIN.MAIN+"/oilUser/recharge",
			type: 'POST',
			dataType: 'json',
			cache:false,
			asycn:true,
			data:{
				rechargeCode: rechargeCode,
			}, 
			success : function(data){
				if(data.success){
	                mui.alert('充值成功！', '提示信息', function() {
	                    window.location.href = DOMIN.MAIN + "/oilUser/oilAccount.html";
	                });
				} else {
					$.tip(data.message);
				}
				$("#send").val("立即充值");
				$("#send").attr("disabled",false);
			},
			error : function(data){
				$.tip("链接服务器失败！");
				$("#send").val("立即充值");
				$("#send").attr("disabled",false);
			}
		})
	})
})

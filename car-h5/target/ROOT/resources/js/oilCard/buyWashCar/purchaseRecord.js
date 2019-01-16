//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html?time="+new Date().getTime();
}
$(document).ready(function(){
	getData();//渲染页面
});
function getData() {
	$.ajax({
		url: DOMIN.MAIN + '/washcharge/paylist',
		type: 'get',
		dataType: 'json',
		data: {
			pageNo: 1,
			pageSize: 300
		},
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
    var html = "";
	if (data.list.length != 0) {
		$.each(data.list,function(index, el) {
	        html += "<div class='item'><ul>";
	        html += "<li>订单编号：<span>" + el.orderNo + "</span></li>";
			html += "<li>支付金额：<span class='colorb'>" + el.totalPay + "元</span></li>";
            if(el.accountPay && el.actualPay) {
                html += "<li>支付方式：<span>余额支付(<span class='colorb'>" + el.accountPay + "元</span>)+微信支付(<span class='colorb'>" + el.actualPay + "元</span>)</span></li>";
            } else if(el.accountPay) {
                html += "<li>支付方式：<span>余额支付(<span class='colorb'>" + el.accountPay + "元</span>)</span></li>";
            } else if(el.actualPay) {
                html += "<li>支付方式：<span>微信支付(<span class='colorb'>" + el.actualPay + "元</span>)</span></li>";
            }
	        html += "<li>购买次数：<span class='colorb'>" + el.washNumber + "次</span></li>";
            html += "<li>支付时间：<span>" + $.formatDate(el.createTime) + "</span></li>";
	        html +=  "</ul></div>";
	    });
	    $(".content").html(html);
	} else {
		$(".nullRecord").show();
	}

}

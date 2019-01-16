//根据cookie判断是否登录(没登录跳转登录页面)
if(getCookie('CAR_OIL_TOKEN')==null){
		window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html?time="+new Date().getTime();
}
$(document).ready(function(){
	getData();//渲染页面
});
function getData() {
	$.ajax({
		url: DOMIN.MAIN + '/washcharge/shenglist',
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
	var flag = false;
	if (data.list.length != 0) {
		$.each(data.list,function(index, el) {
			if(el.status == 1) {
				html += "<div class='item'><ul>";
				html += "<li>订单编号：<span>" + el.orderNo + "</span></li>";
				html += "<li>门店名称：<span>" + el.shopName + "</span></li>";
				html += "<li>门店地址：<span>" + el.shopAddress + "</span></li>";
				html += "<li>联系方式：<span>" + el.shopPhone + "</span></li>";
				html += "<li>服务时间：<span>" + $.formatDate(el.finishTime) + "</span></li>";
				html +=  "</ul></div>";

				flag = true;
			}
	    });
	    $(".content").html(html);
	} else {
		$(".nullRecord").show();
	}
	if (flag == false) {
		$(".nullRecord").show();
	}

}

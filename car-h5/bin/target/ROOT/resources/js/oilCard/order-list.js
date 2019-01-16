//根据cookie判断是否登录(没登陆跳转登录页面)
if (getCookie('CAR_OIL_TOKEN') == null) {
    window.location.herf = DOMIN.MAIN + '/oilUser/oilLogin.html';
}
var orderNo = "";
$(document).ready(function() {
    getData();
    $("#search-btn").on('click',function() {
        if($(this).attr("class") === "search-btn") {
            $("#sellerName").val("");
            $("#sellerName").show();
            $(this).addClass('on');
            $(".cancle-btn").show();
        } else {
            orderNo = $.trim($("#sellerName").val());
            getData();
        }

    })
    $("#cancle-btn").on('click',function() {
        $("#search-btn").removeClass('on');
        $("#sellerName").hide();
        $(".cancle-btn").hide();
        orderNo = ""
        getData();
    })
})
function getData() {
    $.ajax({
        url: DOMIN.MAIN + '/oilUser/queryLogList',
        type: 'post',
        dataType: 'json',
        data: {
            orderNo: orderNo
        },
        success: function(data) {
            if (data.success) {
                if (data.list == "") {
                    $(".order-list").html("");
                    $(".tips-word").show();
                    return;
                }
                renderHtml(data)
            } else {
                $.tip(data.message);
            }
        },
        error: function(data) {
            $.tip('连接服务器失败！');
        }

    })
}
function renderHtml(data) {
	//0：处理中1充值成功2充值失败3部分成功
	var orderStatusLabel = {
		"0": "处理中",
		"1": "充值成功",
		"2": "充值失败",
		"3": "部分成功"
	}
	//1中石化2中石油3话费4串码充值
	var orderTypeLabel = {
		"1": "中石化油卡充值",
		"2": "中石油油卡充值",
		"3": "手机话费充值",
		"4": "串码充值"
	}
    $.each(data.list,function(i,e) {
    	if(e.orderStatus){
    		e.orderStatusText = orderStatusLabel[e.orderStatus];
    	}else{
    		e.orderStatusText = '--';
    	}
    	if(e.orderType){
    		e.orderTypeText = orderTypeLabel[e.orderType];
    	}else{
    		e.orderTypeText = '--';
    	}
    });
    var html = "";
    var createtime = "";
    var orderNum = "";
    var color = "";
    var orderNumber = "";
    $.each(data.list, function(i, val) {
        createtime = $.formatDate(data.list[i].createTime);
        if (data.list[i].orderType == 4) {
            orderNum = "充值码";
            orderNumber = data.list[i].cardNo;
            color = '#2aaff4';
        } else {
            orderNum = "订单编号";
            orderNumber = data.list[i].orderNo;
            switch (data.list[i].orderStatus) {
                case '0':
                    color = '#2aaff4';
                    break;
                case '1':
                    color = '#F0882C';
                    break;
                case '2':
                    color = '#ff0000';
                    break;
                case '3':
                    color = '#2aaff4';
                    break;
            }
        }
        html += '<li>';
        html += '<p><span>充值金额</span><span class="money">' + data.list[i].tardeMoney + '</span></p>';
        html += '<p><span>充值状态</span><span style="font-weight:bold;font-size:15px;color:' + color + '">' + data.list[i].orderStatusText+ '</span></p>';
        html += '<p><span>充值类型</span><span>' +  data.list[i].orderTypeText + '</span></p>';
        html += '<p><span>充值卡号</span><span>' + data.list[i].phone + '</span></p>';
        html += '<p><span>' + orderNum + '</span><span>' + orderNumber + '</span></p>';
        html += '<p><span>时&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp间</span><span>' + createtime + '</span></p>';
        html += '</li>';
    });
    $(".order-list").html(html);
}

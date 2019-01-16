//根据cookie判断是否登录(没登陆跳转登录页面)
if (getCookie('CAR_OIL_TOKEN') == null) {
    window.location.herf = DOMIN.MAIN + '/oilUser/oilLogin.html';
}
var orderNo = "";
$(document).ready(function() {
    getData();
})
function getData() {
    $.ajax({
        url: DOMIN.MAIN + '/driver/querydriverorder',
        type: 'post',
        dataType: 'json',
        data: {
            pageNo: 1,
            pageSize: 100

        },
        success: function(data) {
            if (data.success) {
                if (data.list == "") {
                    $(".content").html("");
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
    var html = "";
    var createtime = "";
    $.each(data.list, function(i, val) {
        createtime = $.formatDate(val.createTime);
        html += "<section class='items' orderno=" + val.orderNo + ">"
        html += "<p><span>起点：</span> <span>" + val.startAddress + "</span></p>"
        html += "<p><span>终点：</span> <span>" + val.endAddress + "</span></p>"
        html += "<p><span>订单编号：</span> <span>" + val.orderNo + "</span></p>"
        html += "<p><span>下单时间：</span> <span>" + createtime + "</span></p>"
        switch (val.orderStatus) {
            case 0:
                html += "<p> <span>" + val.city + "内</span>  <span>等待接单中</span> </p>"
                break;
            case 1:
                html += "<p> <span>" + val.city + "内</span>  <span>司机已接单</span> </p>"
                $(".bottom-btn").hide();
                break;
            case 2:
                html += "<p> <span>" + val.city + "内</span>  <span>司机已到达</span> </p>"
                $(".bottom-btn").hide();
                break;
            case 3:
                html += "<p> <span>" + val.city + "内</span>  <span>服务已完成</span> </p>"
                $(".bottom-btn").hide();
                break;
            case 4:
                html += "<p> <span>" + val.city + "内</span>  <span>订单已取消</span> </p>"
                $(".bottom-btn").hide();
                break;
        }
        html += "</section>"

    });
    $(".content").html(html);
    operate();
}
function operate() {
    $(".items").off("click").on('click',function() {
        window.location.href = DOMIN.MAIN + "/driver/success.html?orderNo=" + $(this).attr("orderno");
    })
}

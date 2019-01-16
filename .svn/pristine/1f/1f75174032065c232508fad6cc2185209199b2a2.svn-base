//根据cookie判断是否登录(没登陆跳转登录页面)
if (getCookie('CAR_OIL_TOKEN') == null) {
    window.location.herf = DOMIN.MAIN + '/oilUser/oilLogin.html';
}
var isClick = true;//判断是否点击过
var orderNumB = '';//查询input内容
var orderType = '';//订单类型
$(document).ready(function () {
    getData();
    // 查询按钮
    $('#searchBtn').on('click', function () {
        if (orderType == 0 || orderType == 200) {
            $('.tableAll').hide();
        }
        orderNumB = $.trim($("#sellerName").val());
        getData(orderNumB);
    })
    getCateData();
    // 下拉事件
    $('h1,.xiala').on('click', function () {
        $('.tableAll li').removeClass('tactive').first().addClass('tactive');
        if (isClick) {
            $('.popBot').show();
            isClick = false;
        } else {
            $('.popBot').hide();
            isClick = true;
        }
    })
    // tab切换
    var statusT;
    $('.tableAll li').on('click', function () {
        statusT = $(this).attr('data-status');
        getData('', orderType, statusT);
        $(this).addClass('tactive').siblings().removeClass('tactive');
    })

})
// 获取筛选类目
function getCateData() {
    $.ajax({
        url: DOMIN.MAIN + '/oilUser/queryOilGoodsTypeList',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                var htmlName = '';
                data.list.map(function (el) {
                    htmlName += '<li data-t=' + el.orderType + '>' + el.oilGoodsType.typeName + '</li>'
                })
                $('.popBot .pop ul').append(htmlName)
                // 筛选分类
                $('.popBot .pop li').on('click', function () {
                    orderType = $(this).attr('data-t');
                    if (orderType == 0 || orderType == 200) {
                        $('.popBot').hide();
                        $('.tableAll').show();
                        isClick = true;
                    } else {
                        $('.popBot').hide();
                        $('.tableAll').hide();
                        isClick = true;
                    }
                    getData('', orderType);
                })
            } else {

            }
        },
        error: function (data) {
            $.tip('连接服务器失败！');
        }

    })
}
// 获取所有订单
function getData(orderNo, orderType, status) {
    $.ajax({
        url: DOMIN.MAIN + '/oilUser/queryLogList',
        type: 'post',
        dataType: 'json',
        data: {
            orderNo: orderNo,
            orderType: orderType
        },
        success: function (data) {
            if (data.success) {
                if (data.list == "" || data.list == null) {
                    $('body').addClass('activeBody');
                    $(".order-list").html("");
                    $(".tips-word").show();
                    return;
                }
                $('body').removeClass('activeBody');
                $(".tips-word").hide();
                if (orderType == 0 || orderType == 200) {
                    if (data.list == null) {
                        $('body').addClass('activeBody');
                        $(".order-list").html("");
                        $(".tips-word").show();
                        return
                    }
                    if (status == 0) { renderHtml(data.list[0].list0); } else if (status == 2) {
                        renderHtml(data.list[0].list2)
                    } else if (status == 1) {
                        renderHtml(data.list[0].list1)
                    } else {
                        renderHtml(data.list[0].list)
                    }
                    return
                }
                renderHtml(data.list)
            } else {
                $.tip(data.message);
            }
        },
        error: function (data) {
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
        "4": "手机流量充值",
        "5": "猫眼电影",
        "6": "滴滴出行",
        "7": "优酷",
        "8": "爱奇艺",
        "9": "腾讯视频",
        "10": "搜狐",
        "11": "串码充值"
    }
    $.each(data, function (i, e) {
        if (e.orderStatus) {
            e.orderStatusText = orderStatusLabel[e.orderStatus];
        } else {
            e.orderStatusText = '--';
        }
        if (e.orderType) {
            e.orderTypeText = orderTypeLabel[e.orderType];
        } else {
            e.orderTypeText = '--';
        }
    });
    var html = "";
    var createtime = "";
    var orderNum = "";
    var color = "";
    var orderNumber = "";
    $.each(data, function (i, val) {
        createtime = $.formatDate(data[i].createTime);
        if (data[i].orderType == 11) {
            orderNum = "充值码";
            orderNumber = data[i].cardNo;
            color = '#2aaff4';
        } else {
            orderNum = "订单编号";
            orderNumber = data[i].orderNo;
            switch (data[i].orderStatus) {
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
        html += '<p><span>充值金额</span><span class="money">￥' + data[i].tardeMoney + '</span></p>';
        html += '<p><span>充值状态</span><span style="font-weight:bold;font-size:15px;color:' + color + '">' + data[i].orderStatusText + '</span></p>';
        // 订单号  商品名称 兑换码 使用有效期 时间
        if (data[i].orderType < 5 || data[i].orderType == 11) {
            html += '<p><span>充值类型</span><span>' + data[i].orderTypeText + '</span></p>';
        }
        if (data[i].orderType == 3 || data[i].orderType == 4) {
            html += '<p><span>充值面额</span><span>' + data[i].denomination + '</span></p>';
        }
        // 当充值类型为 猫眼 到 搜狐 的时候
        if (data[i].orderType > 4 && data[i].orderType < 11) {
            html += '<p><span>' + orderNum + '</span><span>' + orderNumber + '</span></p>';
            html += '<p><span>商品名称</span><span>' + data[i].goodsName + '</span></p>';
            html += '<p><span>卡&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp号</span><span>' + data[i].cardNo + '</span></p>';
            html += '<p><span>兑换码</span><span>' + data[i].cardSecret + '</span></p>';
            html += '<p><span>使用有效期</span><span>' + data[i].indate + '</span></p>';
        } else {
            html += '<p><span>充值卡号</span><span>' + data[i].phone + '</span></p>';
            html += '<p><span>' + orderNum + '</span><span>' + orderNumber + '</span></p>';
        }
        html += '<p><span>时&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp间</span><span>' + createtime + '</span></p>';
        html += '</li>';
    });
    $(".order-list").html(html);
}

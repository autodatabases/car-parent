//根据cookie判断是否登录(没登录跳转登录页面)
if (getCookie('CAR_OIL_TOKEN') == null) {
    window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
var supplier = getCookie("gaoyang");
var shopType, orderTall;
$(document).ready(function () {
    window.location.href.indexOf('youku') != -1 ? (shopType = 6, orderTall = 7) : (shopType = 4, orderTall = 9);
    // 获取数据
    funcAll.getDataAll(shopType);
    // 立即购买
    $('#buyNow').on('click', function () {
        var btnArray = ['取消', '确定'];
        var moneyB;
        moneyT.indexOf('.') != -1 ? moneyB = moneyT : moneyB = moneyT + '.00';
        if ($(this).hasClass('canTap')) {
            mui.confirm('<p style="margin:1rem 0">需支付:<span style="font-size: 18px;color: #2996EA;">' + moneyB + '</span></p>', denominaShow, btnArray, function (e) {
                if (e.index == 1) {
                    rechargeAll(goodIdT, moneyT, orderTall);
                }
            })
        }
    })
})
var funcAll = {
    getDataAll: function (goodType) {
        $.ajax({
            url: DOMIN.MAIN + '/oilUser/queryoilgood',
            type: 'post',
            dataType: 'json',
            data: { goodType: goodType },
            success: function (data) {
                if (data.success) {
                    renderHtml(data)
                    // 切换规格
                    choosCate()
                } else {
                    $.tip(data.message);
                }
            },
            error: function () {
                $.tip("服务器连接失败！");
            }
        })
    }
};
// 渲染规格
function renderHtml(data) {
    var strList = data.list;
    var html = '';
    var salezero;
    $.map(data.list, function (el) {
        if (el.status != 2) {
            el.sale.toString().indexOf('.') != -1 ? salezero = el.sale : salezero = el.sale + '.00';
            html += '<li data-status=' + el.status + ' data-onStatus=' + el.oneStatus + ' data-moeny=' + el.sale + ' data-goodsId=' + el.id + ' data-goodsName=' + el.goodsName + '>'
                + '<i></i>'
                + '<div class="vouerch">'
                + ' <div class="title">' + el.goodsName + '</div>'
                + '<div class="connect"></div>'
                + '<div class="moenyTitle">'
                + '<div class="mon">￥'
                + '<span>' + salezero + '</span>'
                + '</div>'
                + '<div class="buyPic"></div>'
                + '</div>'
                + '</div>'
                + '</div>'
                + '</li>'
        }
    })
    $('.topM ul').append(html)
}
// 选择规格
// 提交参数
var goodIdT, moneyT, denominaShow;
function choosCate() {
    $('.topM ul li').off('click').on('click', function () {
        goodIdT = $(this).attr('data-goodsid');
        moneyT = $(this).attr('data-moeny');
        denominaShow = $(this).attr('data-goodsName');
        if ($(this).attr('data-onstatus') != 1) {
            $.tip("追电商品系统维护，请稍后重试");
            return;
        }
        if ($(this).attr('data-status') == 3) {
            $.tip("该商品库存不足，请选择其它商品！");
            return;
        } else if ($(this).attr('data-status') == 4) {
            $.tip("商品系统维护中，请选择其他商品！");
            return;
        }
        $(this).children('i').addClass('iActive').parent().siblings().children('i').removeClass('iActive');
        $('#buyNow').addClass('canTap');
        var moneyA;
        moneyT.indexOf('.') != -1 ? moneyA = moneyT : moneyA = moneyT + '.00';
        $('#buyNow').html('立即支付：' + moneyA)
    })
}

// 充值
function rechargeAll(goodId, money, orderType) {
    var dataRech = {
        goodId: goodId,
        money: money,
        orderType: orderType
    };
    $("#btn").attr("disabled", true);
    $("#btn").css("background", "#ccc");
    $.ajax({
        url: DOMIN.MAIN + '/oilUser/getcardsecret',
        type: 'POST',
        dataType: 'json',
        data: dataRech,
        success: function (data) {
            if (data.success) {
                var btnArray = ['查看详情', '确定']
                mui.confirm("<p style='line-height:2rem;'>兑换卡密：<span>" + data.data.cardSecret + "</span></p>", '<span style="color:#2aaff4">支付成功</span>', btnArray, function (e) {
                    if (e.index == 0) {
                        window.location.href = DOMIN.MAIN + "/oilUser/oilCardDetail.html?orderNo=" + data.data.orderNo;
                    }
                })
                btnStatus();
            } else {
                $.tip(data.message);
                btnStatus();
                return false;
            }
        },
        error: function () {
            $.tip("链接服务器失败!");
            btnStatus();
        }
    })
}
// 充值按钮状态
function btnStatus() {
    $("#buyNow").attr("disabled", false);
    $("#buyNow").css("background", "#2aaff4");
}
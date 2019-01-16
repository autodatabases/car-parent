//根据cookie判断是否登录(没登录跳转登录页面)
if (getCookie('CAR_OIL_TOKEN') == null) {
    window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
var supplier = getCookie("gaoyang");
$(document).ready(function () {
    $(".bg").height($(document).height());

    $(".back").on('click', function () {
        window.location.href = DOMIN.MAIN + "/oilUser/oilAccount.html";
    });
    $(".btn_close").on("click", function () {
        $(".tip").hide();
    })
    // tab切换
    $('.allToal div').on('tap', function () {
        $(this).addClass('headActive').siblings().removeClass('headActive')
    })
    // 话费油卡规格切换
    $('.phoneRe').on('tap', function () {
        $('.money').show();
        $('.flowMoney').hide();
        $('#mobile').attr('data-type', '1');
        if ($('#mobile').val().length == 11) {
            getData(mobileNum, 7);
            $("#flowBtn").removeClass('canTap');
            $("#flowBtn").val('立即充值');
        }
        // 流量充值说明
        $('.rechargeTg').hide();
    })
    $('.flowRe').on('tap', function () {
        $('.money').hide();
        $('.flowMoney').show();
        $('#mobile').attr('data-type', '0')
        if ($('#mobile').val().length == 11) {
            getData(mobileNum, 8);
            $("#btn").removeClass('canTap');
            $("#btn").val('立即充值');
        }
        // 流量充值说明
        $('.rechargeTg').show();
    })
    // 输入手机号码后显示规格
    var mobileNum;//存储手机号
    $("#mobile").on('input', function () {
        // 当input为11位的时候,进行规格操作
        if ($(this).val().length == 11) {
            mobileNum = $(this).val().trim();
            // 流量0 话费1
            if ($(this).attr('data-type') == '1') {
                getData(mobileNum, 7);
            } else {
                getData(mobileNum, 8);
            }
        } else {
            $("#btn").removeClass('canTap');
            $("#flowBtn").removeClass('canTap');
            $(".coupons li").removeClass('fontactive');
            $(".coupons li").css({ "border-color": "#ccc", "color": "#ccc" });
            $(".flowCoupons li").removeClass('fontactive');
            $(".flowCoupons li").css({ "border-color": "#ccc", "color": "#ccc" });
            $('#area').hide();
            $('#btn').val('立即充值');
            $('#flowBtn').val('立即充值');
        }
    })

})
// 获取规格
function getData(mobile, goodType) {
    var dataList = {
        mobile: mobile,
        goodType: goodType
    }
    $.ajax({
        url: DOMIN.MAIN + '/oilUser/queryoilgood',
        type: 'post',
        dataType: 'json',
        data: dataList,
        success: function (data) {
            if (data.success) {
                $('#area').html('(' + data.datasets.operator.data + ')');
                $('#area').show();
                renderHtml(data, goodType);
            } else {
                $.tip(data.message);
            }
        },
        error: function () {
            $.tip("服务器连接失败！");
        }
    })
}
// 输入手机号后,展示规格
function renderHtml(data, goodType) {
    var strList = data.list;
    var html = "<ul>";
    for (var i = 0; i < strList.length; i++) {
        if (goodType == '7') {
            if (strList[i].status != 2) {
                html += "<li data-onstatus=" + strList[i].oneStatus + " data-money=" + strList[i].sale + " data-status=" + strList[i].status + "  data-goodId=" + strList[i].id + "><p>" + strList[i].denomination + "元</p></li>"
            }
        } else if (goodType == '8') {
            if (strList[i].status != 2) {
                html += "<li data-onstatus=" + strList[i].oneStatus + " data-money=" + strList[i].sale + " data-status=" + strList[i].status + " data-goodId=" + strList[i].id + "><p>" + strList[i].denomination + "</p></li>"
            }
        }
    }
    html += "</ul>";
    if (goodType == '7') {
        $(".coupons").html(html);
        $(".coupons li").addClass('fontactive');
        choosCate('coupons');
    } else if (goodType == '8') {
        $(".flowCoupons").html(html);
        $(".flowCoupons li").addClass('fontactive');
        choosCate('flowCoupons');
    }
}
// 选择规格
var goodsId, moneyId, mianE;
function choosCate(Name) {
    $('.' + Name + ' li').off('click').on('click', function () {
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
        if ($(this).hasClass('fontactive')) {
            goodsId = $(this).attr('data-goodId');
            moneyId = $(this).attr('data-money');
            mianE = $(this).children('p').html();
            $(this).css({ "border-color": "#2aaff4", "color": "#2aaff4" }).siblings().css({ "border-color": "#ccc", "color": "#333" });
            if (Name == 'coupons') {
                $('#btn').val('立即支付(' + moneyId + '元)');
                $('#btn').addClass('canTap');
            } else {
                $('#flowBtn').val('立即支付(' + moneyId + '元)');
                $('#flowBtn').addClass('canTap');
            }
        }
    })
    // 给充值按钮添加点击事件
    if (Name == 'coupons') {
        $('#btn').off('click').on('click', function () {
            if ($(this).hasClass('canTap')) {
                checkCate(goodsId, $('#mobile').val(), moneyId, 0)
            }
        })
    } else {
        $('#flowBtn').off('click').on('click', function () {
            if ($(this).hasClass('canTap')) {
                checkCate(goodsId, $('#mobile').val(), moneyId, 1)
            }
        })
    }
}
// 判断是否有库存
function checkCate(goodId, phone, perValue, orderType) {
    var dataL = {
        goodId: goodId,
        phone: phone,
        perValue: perValue,
        orderType: orderType
    }
    $.ajax({
        url: DOMIN.MAIN + '/oilUser/checkoilgood',
        type: 'POST',
        dataType: 'json',
        data: dataL,
        success: function (data) {
            if (data.success) {
                var dataTitle;
                var btnArray = ['取消', '确定'];
                orderType == 0 ? dataTitle = $("#area").html().replace("(", "").replace(")", "") + mianE + '话费' : dataTitle = $("#area").html().replace("(", "").replace(")", "") + mianE + '流量';
                mui.confirm('<p style="margin:1rem 0">需支付:<span style="font-size: 18px;color: #2996EA;">' + perValue + '元</span></p>', dataTitle, btnArray, function (e) { if (e.index == 1) { rechargeAll(orderType) } })
            } else {
                $.tip(data.message);
                return false;
            }
        },
        error: function () {
            $.tip("服务器连接失败！");
        }
    })
}
// 充值
function rechargeAll(orderType) {
    var dataRech, urlRe;
    if (orderType == '0') {
        dataRech = {
            goodId: goodsId,
            phone: $('#mobile').val(),
            money: moneyId,
        }
        urlRe = DOMIN.MAIN + '/oilUser/chargemobile'
    } else {
        dataRech = {
            goodId: goodsId,
            phone: $('#mobile').val(),
            perValue: moneyId,
        }
        urlRe = DOMIN.MAIN + '/oilUser/chargeflow'
    }
    $("#btn").attr("disabled", true);
    $("#btn").css("background", "#ccc");
    $("#flowBtn").attr("disabled", true);
    $("#flowBtn").css("background", "#ccc");
    $.ajax({
        url: urlRe,
        type: 'POST',
        dataType: 'json',
        data: dataRech,
        success: function (data) {
            if (data.success) {
                mui.alert("<p style='line-height:2rem;'>订单号：<span>" + data.data + "</span></p> <p  style='color:#AAAAAA'>请耐心等待充值结果!</p>", '<span style="color:#2aaff4">提交成功</span>', '确定')
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
    $("#btn").attr("disabled", false);
    $("#btn").css("background", "#2aaff4");
    $("#flowBtn").attr("disabled", false);
    $("#flowBtn").css("background", "#2aaff4");
}
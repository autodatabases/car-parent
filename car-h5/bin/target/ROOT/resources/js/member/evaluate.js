var type = decodeURI($.getUrlParam("ptype"));
var ordernum = decodeURI($.getUrlParam("ordernum"));
var otype = $.getUrlParam("otype");
var sudu = 0;
var taidu = 0;
// var comment = "";
$(document).ready(function() {
    switch (type) {
        case "0":
            $(".img-box img").attr('src',DOMIN.MAIN+'/resources/imgs/member/img.png');
            $(".type").html("更换电瓶服务");
            break;
        case "1":
            $(".img-box img").attr('src',DOMIN.MAIN+'/resources/imgs/repair/lt-img.png');
            $(".type").html("更换轮胎服务");
            break;
        case "2":
            $(".img-box img").attr('src',DOMIN.MAIN+'/resources/imgs/index/by-new.png');
            $(".type").html("小保养服务");
            break;
        case "3":
            $(".img-box img").attr('src',DOMIN.MAIN+'/resources/imgs/index/xc-new.png');
            $(".type").html("洗车服务");
            break;
        case "4":
            $(".img-box img").attr('src',DOMIN.MAIN+'/resources/imgs/index/pq-new.png');
            $(".type").html("喷漆服务");
            break;
    }
    $("#message").on("input",function() {
        // var param = $(this).val();
        // var regRule = /\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g;
        // if(param.match(regRule)) {
        //     param = param.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "??");
        // }
        // $("#message").val(param);
        if ($("#message").val().length > 300) {
            $("#message").val($("#message").val().substr(0,300));
        }
    })
    $("#starts1 li").on("click",function() {
        if ($(this).next("li").attr('class') == "on") {
            $(this).nextAll('li').removeClass('on');
        }else {
            $(this).addClass('on');
            $(this).prevAll("li").addClass('on');
        }
        taidu = $(this).index() + 1;
        $("#mark1").html(taidu + " 分");
        if ($(this).index() == 4) {
            $("#mark1").addClass("manfen");
        } else {
            $("#mark1").removeClass("manfen");
        }
    })
    $("#starts2 li").on("click",function() {
        if ($(this).next("li").attr('class') == "on") {
            $(this).nextAll('li').removeClass('on');
        }else {
            $(this).addClass('on');
            $(this).prevAll("li").addClass('on');
        }
        sudu = $(this).index() + 1;
        $("#mark2").html(sudu + " 分");
        if ($(this).index() == 4) {
            $("#mark2").addClass("manfen");
        } else {
            $("#mark2").removeClass("manfen");
        }
    })
    $(".release").on("click",function() {
        var comment = $("#message").val();
        if($("#message").val() == "") {
            comment = "默认好评";
        }
        comment = escape(comment);
        if (taidu == 0) {
            $.tip("请对商家态度做出评分");
            return;
        } else if (sudu == 0) {
            $.tip("请对物流速度做出评分");
            return;
        }
        $.ajax({
            url: DOMIN.MAIN + '/ordercomment/addcomment',
            type: 'POST',
            dataType: 'json',
            data: {
                orderNo: ordernum,
                sellerStar: taidu,
                expressStar: sudu,
                comment: comment
            },
            success: function(data) {
                if (data.success) {
                    mui.alert('评价成功！','提示', '确定', function() {
                        window.location.href = DOMIN.MAIN + "/user/order.html?type=" + otype;
                    });
                } else {
                    $.tip(data.message);
                }
            },
            error: function() {
                $.tip("服务器错误！")
            }
        })

    })
})

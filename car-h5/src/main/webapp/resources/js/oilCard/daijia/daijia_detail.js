var orderNo = $.getUrlParam("orderNo");
$(document).ready(function() {
    getData();
    //获取时间
    getAppointTime();
    $(".bg,.btn").on('click',function() {
        $(".popup").hide();
    })
    $(".btn_close").on('click',function() {
        $(".tip").hide();
    })
})

//mobiScroll插件初始化和调用，选取预约时间
function getAppointTime() {
    var now = new Date();
    var currYear = now.getFullYear();
    var currMonth = now.getMonth() + 1;
    var currDay = now.getDate();
    var currhou = now.getHours();
    var currmin = now.getMinutes();

    //mobiScroll插件选项
    var opt1 = {
        preset : 'datetime', //日期，可选：date\datetime\time\tree_list\image_text\select
        theme : 'android-ics light', //皮肤样式，可选：default\android\android-ics light\android-ics\ios\jqm\sense-ui\wp light\wp
        display : 'bottom', //显示方式 ，可选modal\inline\bubble\top\bottom
        mode : 'scroller', //日期选择模式，可选：scroller\clickpick\mixed
        lang : 'zh',
        dateFormat : 'yyyy-mm-dd', // 面板日期格式
        setText : '确认', //确认按钮名称
        cancelText : '取消', //取消按钮名籍我
        dateOrder : 'dd', //面板中日期排列格式
        dayText : '日',
        monthText : '月',
        yearText : '年', //面板中年月日文字
        showNow : false,
        nowText : "今",
        endYear: currYear +100, //结束年份
        minDate: new Date(currYear, currMonth - 1, currDay, currhou, currmin + 30),
        maxDate: new Date(currYear, currMonth - 1, currDay + 2, currhou, currmin),
        // onBeforeShow: function (inst) { inst.settings.wheels[0].length>2?inst.settings.wheels[0].splice(0,1):null; }, //去掉年月滚轮
        onSelect:function(textVale,inst){ //选中时触发事件
            //console.log("我被选中了.....");
        },
        onClose:function(textVale,inst){ //插件效果退出时执行  inst:表示点击的状态反馈：set/cancel
            //console.log("textVale--"+textVale);
            //console.log(this.id);//this表示调用该插件的对象
        }
    };
    $("#time").mobiscroll().datetime(opt1);
}
function getData() {
    if(orderNo) {
        $.ajax({
            url: DOMIN.MAIN + "/driver/orderdetail",
            type: "POST",
            dataType: "json",
            data: {
                orderNo: orderNo
            },
            success: function(data) {
                if(data.success) {
                    renderHtml(data);
                } else {
                    $.tip(data.message)
                }
            },
            error: function() {
                $.tip('连接服务器失败！');
            }
        })
    }
}
function renderHtml(data) {
    $(".startAddress span").html(data.data.startAddress);
    $(".endAddress span").html(data.data.endAddress);
    $(".appointmentTime span").html($.formatDate(data.data.appointmentTime));
    $(".orderNo span").html(data.data.orderNo);
    if(data.data.orderStatus === 0) {
        $(".bottom-btn").show();
    } else {
        $(".bottom-btn").hide();
    }
    if(data.datasetLists.orderDetail != "") {
        var startArr = data.datasetLists.orderDetail.reverse();
        var html01 = "";
        var html02 = "";
        $.each(startArr,function(index, el) {
            html01 += "<li></li>"
            html02 += '<li><span class="nowTimes">' + $.formatDate(el.createTime) + '</span>'
            switch (el.status) {
                case 0:
                    html02 += '<span>已收到你的订单,等待代驾司机接单中</span></li>';
                    break;
                case 1:
                    if(data.data.driverName) {
                        html02 += '<span>代驾司机（<i style="color:#2aaff4;font-style:normal">' + data.data.driverName + ' ' + data.data.driverPhone + '</i>）已接单，正在赶来的路上</span></li>';
                    } else {
                        html02 += '<span>代驾司机已接单，正在赶来的路上</span></li>';
                    }
                    break;
                case 2:
                    html02 += '<span>代驾司机已到达预定地点</span></li>';
                    break;
                case 3:
                    html02 += '<span>已到达目的地，服务完成！</span></li>';
                    break;
                case 4:
                    html02 += '<span>您已取消订单</span></li>';
                    break;
                case 5:
                    html02 += '<span>修改预约时间成功</span></li>'
                    break;
            }
        });
        $(".pro-left ul").html(html01);
        $(".pro-right ul").html(html02);

    }
    operate(data);
}
function operate(data) {
    $(".change").off('click').on('click',function() {
        if(data.data.appointmentNum < 1) {
            $(".popCon-top").html("您已更改过一次预约时间，无法再次更改。您可以选择取消订单，再次预约！");
            $(".tel").hide();
            $(".btn").show();
            $(".popup").show();
            return;
        }
        var appointmentTime = $.formatDate(data.data.appointmentTime);
        var createTime = $.formatDate(data.data.createTime);
        var result02 = compareTime(createTime,-48*60*60*1000);
        if(result02 === false) {
            $(".popCon-top").html("距离下单时间已超过48小时不能再进行更改预约时间的操作！");
            $(".tel").show();
            $(".btn").hide();
            $(".popup").show();
            return;
        }
        var result01 = compareTime(appointmentTime,30*60*1000);
        if(result01 === false) {
            $(".popCon-top").html("距离预约时间开始前30分钟内不能进行更改预约时间的操作！");
            $(".tel").show();
            $(".btn").hide();
            $(".popup").show();
            return;
        }

        $("#time").trigger("click");
    })
    $(".cancel").off('click').on('click',function() {
        var appointmentTime = $.formatDate(data.data.appointmentTime);
        var result = compareTime(appointmentTime,30*60*1000);
        if(result !== true) {
            $(".popCon-top").html("距离预约时间开始前30分钟内不能进行取消订单的操作！");
            $(".tel").show();
            $(".btn").hide();
            $(".popup").show();
            return;
        }
        $(".tip").show();
    })
    $("#time").off('change').on('change',function() {
        var appointmentTime = $(this).val() + ":00";
        $.ajax({
            url: DOMIN.MAIN + "/driver/updateappointmenttime",
            type: "POST",
            dataType: "json",
            data: {
                orderNo: data.data.orderNo,
                appointmentTime: appointmentTime
            },
            success: function(data) {
                if(data.success) {
                    if(data.data === "success") {
                        $.tip("更改预约时间成功！");
                        getData();
                    } else if(data.data === "1") {
                        $(".popCon-top").html("您已更改过一次预约时间，无法再次更改。您可以选择取消订单，再次预约！");
                        $(".tel").hide();
                        $(".btn").show();
                        $(".popup").show();
                    } else if(data.data === "0") {
                        $(".popCon-top").html("距离预约时间开始前30分钟内不能进行更改预约时间的操作！");
                        $(".tel").show();
                        $(".btn").hide();
                        $(".popup").show();
                    } else if(data.data === "2") {
                        $(".popCon-top").html("更改的预约时间需在距离下单时间开始后的48小时之内！");
                        $(".tel").show();
                        $(".btn").hide();
                        $(".popup").show();
                    } else if(data.data === "3") {
                        $(".popCon-top").html("更改的预约时间需在更改操作时间开始后的30分钟之后！");
                        $(".tel").show();
                        $(".btn").hide();
                        $(".popup").show();
                    }

                } else {
                    $.tip(data.message);
                }
            },
            error: function() {
                $.tip("连接服务器失败！")
            }
        })
    })
    var isSending = false;
    $(".btn_cancel").off('click').on('click',function() {
    	if(isSending){
    		return;
    	}
    	isSending=true;
        $.ajax({
            url: DOMIN.MAIN + "/driver/canceldriverorder",
            type: "POST",
            dataType: "json",
            data: {
                orderNo: data.data.orderNo
            },
            success: function(data) {
                if(data.success) {
                    $(".tip").hide();
                    if(data.data === "success") {
                        $.tip("取消订单成功！");
                        getData();
                    } else if(data.data === "0") {
                        $(".popCon-top").html("代驾司机已出发，您已无法取消订单！");
                        $(".tel").show();
                        $(".btn").hide();
                        $(".popup").show();
                    }
                } else {
                    $.tip(data.message)
                }
                isSending=false;
            },
            error: function() {
                $.tip("连接服务器失败！");
                isSending=false;
            }
        })
    })
}
function compareTime(times,m) {
	var curDate = new Date();
	var startDate = new Date(times);
	var t1 = curDate.getTime();
	var s1 = startDate.getTime();
    s1 = s1 - m;
	if(t1 < s1) {
		return true;
	} else {
		return false;
	}
}

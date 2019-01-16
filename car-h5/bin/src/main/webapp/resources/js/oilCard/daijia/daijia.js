var dataArr = {};//代驾接口参数
var pointFlag = true;
$(document).ready(function() {
    //代驾协议
    if(!getCookie("daijia_service")) {
        $(".tip").show();
    }
    $(".btn_ok").on('click',function() {
        addCookie("daijia_service",1,360);
        $(".tip").hide();
    })
    $(".btn_no").on('click',function() {
        location.href = DOMIN.MAIN + "/oilUser/oilAccount.html"
    })


    //获取定位
    getLocation();
    //获取时间
    getAppointTime();
    $("#time").on("change",function() {
        getAppointTime();
    })

    $(".back").on('click',function() {
        location.href = DOMIN.MAIN + "/oilUser/oilAccount.html"
    })
    $(".bg").height($(document).height());

    //根据获取的城市 改变代驾的价格
    if($.trim($("#cityname").html()) != "") {
        if($.trim($("#cityname").html()) === "广州市" || $.trim($("#cityname").html()) === "深圳市" || $.trim($("#cityname").html()) === "东莞市") {
            $("#money").html("130元");
            $("#money").attr("money","130");
            $("#paymoney").html("￥130.00");
            $("#paymoney").attr("money","130");
        }
    }

    //立即预约
    $("#yuyue").on('click', function() {
        yuyue();
    });
    //关闭填写信息弹窗
    $("#pop-close").on('click',function() {
        $(".popup").hide();
        $(".info-pop").removeClass('pop-active');
    })
    $("input[name='radio']").on("change",function() {
        if($.trim($("#userName").val()) !== "" && $("input[name='radio']:checked").val() !== undefined && $.trim($("#userPhone").val()) !== "") {
            $("#next").addClass('button-on');
        }
    })
    $("#userName,#userPhone").on("input",function() {
        if($.trim($("#userName").val()) !== "" && $("input[name='radio']:checked").val() !== undefined && $.trim($("#userPhone").val()) !== "") {
            $("#next").addClass('button-on');
        } else {
            $("#next").removeClass('button-on');
        }
    })
    //下一步
    $("#next").on('click',function() {
        next();
    })
    //上一步
    $("#prev").on('click',function() {
        $(".info-pop").addClass('pop-active');
        $(".pay-pop").removeClass('payPop-active');
    })
    //立即付款
    $("#pay").on('click',function() {
        payMoney();
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

//立即预约
function yuyue() {
    if(getCookie('sel_province') !== null) {
        if(!(getCookie('sel_province') === "广东省" || getCookie('sel_province') === "20")) {
            $.tip("本代驾服务于广东省内地区！请重新选择城市");
            return;
        }
    } else {
        if(!(getCookie('province') === "广东省")) {
            $.tip("本代驾服务于广东省内地区！请重新选择城市");
            return;
        }
    }
    if($.trim($("#startAdd").val()) === "") {
        $.tip("请填写起点的详细地址！");
        return;
    } else if($.trim($("#endAdd").val()) === ""){
        $.tip("请填写终点的详细地址！");
        return;
    } else if($.trim($("#time").val()) === "") {
        $.tip("请选择预约时间！");
        return;
    }
    //getPoint("广州市海珠区江南大道中161之6-7",startAdd);
    //getPoint("海珠区江南大道北69号",startAdd);
    getPoint($.trim($("#startAdd").val()),startAdd);
    //获取缓存的用户信息
    getDaijiaUser();

    $(".popup").show();
    $(".info-pop").addClass('pop-active');
}
//下一步
function next() {
    if($("#next").attr('class') === "next") {
        return;
    }
    if($.trim($("#userName").val()).length > 5) {
        $.tip("请输入五个字以内的名字");
        return;
    }
    if(!/^\d{11}$/.test($.trim($("#userPhone").val()))) {
        $.tip("请填写正确格式的手机号！");
        return;
    }
    dataArr.userName = $.trim($("#userName").val());
    dataArr.userPhone = $.trim($("#userPhone").val());
    dataArr.userSex = $("input[name='radio']:checked").val();
    addCookie("daijiaUser",dataArr.userName + "," + dataArr.userPhone + "," + dataArr.userSex);
    $(".info-pop").removeClass('pop-active');
    $(".pay-pop").addClass('payPop-active');
    getMoney();
    // $(".balance").html("（账户余额：" + getCookie("account_number") + "元）");
}
//地址解析成经纬度
function getPoint(addressName,cb) {
    // 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上,并调整地图视野
    myGeo.getPoint(addressName, function(point){//异步执行，有坑
		if (point) {
            cb(point);
		}else{
            cb(point);
		}
	}, "广东省");
}
//地址解析回调函数  起点
function startAdd(point) {
    if(point == null) {
        $.tip("您填写的起始地址无法解析！请填写准确地址")
        return;
    }
    dataArr.longitude = point.lng;
    dataArr.latitude = point.lat;
    dataArr.startAddress = $.trim($("#startAdd").val());
    getPoint($.trim($("#endAdd").val()),endAdd);
}
//地址解析回调函数 终点
function endAdd(point) {
    if(point == null) {
        $.tip("您填写的终止地址无法解析！请填写准确地址")
        return;
    }
    dataArr.deLongitude = point.lng;
    dataArr.deLatitude = point.lat;
    dataArr.endAddress = $.trim($("#endAdd").val());
    dataArr.appointmentTime = $.trim($("#time").val()) + ":00";
    dataArr.city = $.trim($("#cityname").html());

    $(".popup").show();
    $(".info-pop").addClass('pop-active');
}

//支付函数
function payMoney() {
    if(!(compareTime(dataArr.appointmentTime) === true)) {
        $.tip(compareTime(dataArr.appointmentTime));
        return;
    }
    dataArr.money = $("#paymoney").attr("money");
    $("#pay").attr("disabled",true);
    $("#pay").css("background","#ccc");
    $.ajax({
        url: DOMIN.MAIN + '/driver/createOrder',
        type: 'POST',
        dataType: 'json',
        data: dataArr,
        success: function(data) {
            if (data.success) {
                window.location.href = DOMIN.MAIN + "/driver/success.html?orderNo=" + data.data.orderNo;
            } else {
                $.tip(data.message)
                $("#pay").attr("disabled",false);
                $("#pay").css("background","#2aaff4");
            }
        },
        error: function() {
            $.tip("下单失败！请重新尝试下单");
            $("#pay").attr("disabled",false);
            $("#pay").css("background","#2aaff4");
        }
    })
}
//缓存的用户信息
function getDaijiaUser() {
    if(getCookie("daijiaUser")) {
        var user = getCookie("daijiaUser").split(",");
        $("#userName").val(user[0]);
        $("#userPhone").val(user[1]);
        $("input[name='radio']:eq(" + (user[2] - 1) + ")").prop("checked",true);
    }
    //填写信息弹窗 下一步按钮 高亮显示
    if($.trim($("#userName").val()) !== "" && $("input[name='radio']:checked").val() !== undefined && $.trim($("#userPhone").val()) !== "") {
        $("#next").addClass('button-on');
    }
}
function getMoney() {
    $.ajax({
		type: "get",
		url: DOMIN.MAIN + "/oilUser/infocenter",
		async: false,
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.success) {
                $(".balance").html("（账户余额：" + data.data.money + "元）");
			} else {
				$.tip(data.message)
			}
		}
	});
}
function compareTime(times01) {
	var curDate = new Date();
	var startDate = new Date(times01);
	var t1 = curDate.getTime();
	var s1 = startDate.getTime();
    s1 = s1 - 30*60*1000;
	if(t1 < s1) {
		return true;
	} else {
		return "下单时间距离预约时间小于30分钟时不能进行下单！";
	}
}

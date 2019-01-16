String.prototype.trim = function() {
  return this.replace(/\s+|\s+/g, '');
}
$(function () {
    timeInit();

    //上传图片
    $(".upload-btn input[type=file]").change(function() { //商品上传图片
        if ($(".upload-btn input[type=file]").val() != '') $("#submit_form").submit();
    });
    //重新覆盖上传
    $(".img-box").on("click",function() {
        $(".upload-btn input").click();
    });
    //iframe加载响应，初始页面时也有一次，此时data为null。
    $("#exec_target").load(function() {
      var data = eval("(" + document.getElementById("exec_target").contentWindow.document.body.innerText + ")");
      if (data.url) {
          $(".img-box").css("display", "block");
          $(".img-box img").attr("src", data.url);
          $(".upload-btn").hide();
      } else {
          $.tip(decodeURI(data.message));
      }
    });

    //提交
    $("#suBtn").on('click', function() {
        submit();
    });

});
//初始化时间选择器
function timeInit() {
    var opt = {
        theme: "android-ics light",
        display: 'modal', //显示方式
        lang: "zh",
        setText: '确定', //确认按钮名称
        cancelText: "取消",
        dateFormat: 'yyyy-mm-dd', //返回结果格式化为年月格式
        dateOrder: 'yyyymmdd', //面板中日期排列格式
        startYear: (new Date()).getFullYear() - 10, //开始年份
        endYear: (new Date()).getFullYear() + 10, //结束年份
        onBeforeShow: function (inst) {
        //  console.info( inst.settings.wheels);
          },
        headerText: function (valueText) { //自定义弹出框头部格式
        //  console.info(valueText);
            array = valueText.split('-');
            return array[0] + "年" + array[1] + "月" + array[2] + "日";
        }
    };
    $("#dates").mobiscroll().date(opt);
}
//手机号格式调整和验证手机号
function formatPhoneNum(e,obj){
	//调整格式
	if(e.keyCode==8){
		return;
	}
	obj.value = obj.value.replace(/[^\d\s]/g,'');
	var _this = obj;
	//_this.value=_this.value.replace(/[^(\d\s)]/g,'');
	if(_this.value.length==3){
		_this.value = _this.value+' ';
	}else if(_this.value.length==8){
		_this.value = _this.value+' ';
	}
	if(_this.value.length>13){
		_this.value = _this.value.substr(0,13);
	}
}
//提交录入信息
function submit() {
    var name = $.trim($(".name input").val());
    var proposer = $.trim($(".proposer input").val());   
    var phone = ($(".phone input").val()).trim();
    var date = $.trim($(".dates input").val());
    var dateNew = date.replace(/-/g, '/');
    var imgUrl = $("#goodImg").attr('src');
    if(name === "" || name === null) {
        $.tip("请输入车主姓名！");
        return;
    } else if(name.length >30){
        $.tip("您输入的姓名过长！");
        return;
    }
    if(proposer === "" || proposer === null) {
        $.tip("请输入申请人！");
        return;
    } else if(proposer.length >6){
        $.tip("请输入五个字之内,包含五个字,(必须填)");
        return;
    }
    if(phone === "" || phone === null) {
        $.tip("请输入车主手机号！");
        return;
    } else if(!IsTel(phone)) {
        $.tip("请输入合法的手机号！");
        return;
    }
    if(date === "" || date === null) {
        $.tip("请输入保险到期日！");
        return;
    }
    if(imgUrl === "" || imgUrl === null || imgUrl === undefined) {
        $.tip("请上传驾驶证！");
        return;
    }
    $("#suBtn").html("正在录入");
    $("#suBtn").attr('disabled', true);
    $.ajax({
        url: DOMIN.MAIN + "/surveyrecord/addrecord",
        type: "post",
        async: false,
        dataType: "json",
        data: {
            userName: name,
            userPhone: phone,
            licensePicture: imgUrl,
            expiredTime: dateNew,
            proposer:proposer
        },
        success: function(data, textStatus) {
            console.log(proposer)
            if (data.success) {
                mui.alert('录入成功', '提示', function() {
                    location.reload();
                });
            } else {
                $.tip(data.message);
                $("#suBtn").html("立即录入");
                $("#suBtn").attr('disabled', false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip("连接服务器失败！");
            $("#suBtn").html("立即录入");
            $("#suBtn").attr('disabled', false);
        }
    });
}
//三大运营商号段
// (134)|(135)|(136)|(137)|(138)|(139)|(147)|(148)|(150)|(151)|(152)|(157)|(158)|(159)|(172)|(178)|(182)|(183)|(184)|(187)|(188)|(198) //移动
// (130)|(131)|(132)|(145)|(146)|(155)|(156)|(166)|(171)|(175)|(176)|(185)|(186) //联通
// (133)|(149)|(153)|(173)|(174)|(177)|(180)|(181)|(189)|(199) //电信
function IsTel(Tel){
    var myreg = /^(((134)|(135)|(136)|(137)|(138)|(139)|(147)|(148)|(150)|(151)|(152)|(157)|(158)|(159)|(172)|(178)|(182)|(183)|(184)|(187)|(188)|(198)|(130)|(131)|(132)|(145)|(146)|(155)|(156)|(166)|(171)|(175)|(176)|(185)|(186)|(133)|(149)|(153)|(173)|(177)|(180)|(181)|(189)|(199)|(170))+\d{8})$/;
    if(!myreg.test(Tel)){
      return false;
  } else {
      return true;
  }
}

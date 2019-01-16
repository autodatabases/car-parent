$(function() {
    $("#oilcard_button").click(function() {
    	oilcard_button();
    });
    $("#cancel_button").click(function() {
    	$(".tables").load('oilcard/zhizuoyouka/zhizuoyouka.html');
    });
});
function oilcard_button() { //提交
    $(".oilcard_button").prop("disabled", true);
    $("#oilcard_button").val("提交中. . .");

    //判断面额必须填写
	if($.trim($("#money").val())==""){
		$.tip("请选择油卡面额");
		$(".oilcard_button").prop("disabled", false);
        $("#oilcard_button").val("确认生成");
		return;
	}
    //判断数量必须填写
    if($.trim($("#num").val())=="") {
        $.tip("请选择油卡数量");
        $(".oilcard_button").prop("disabled", false);
        $("#oilcard_button").val("确认生成");
		return;
    }
	//判断失效日期必须填写
	if($.trim($("#deadTime").val())==""){
		$.tip("请选择失效时间");
        $(".oilcard_button").prop("disabled", false);
        $("#oilcard_button").val("确认生成");
		return;
	}
    var deadTime = $("#deadTime").val() + " 00:00:00";
    $.ajax({
        url: DOMIN.MAIN + "/oilmake/addRecord",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
            money: $.trim($("#money").val()),
            num: $.trim($("#num").val()),
            deadTime: deadTime,
            remark: $.trim($("#remark").val())
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                $.tip("操作成功！");
                setTimeout(function() {
                    $('.tables').load('oilcard/zhizuoyouka/zhizuoyouka.html');
                }, 1000);
            } else {
                $.tip(data.message);
                $(".oilcard_button").prop("disabled", false);
                $("#oilcard_button").val("确认生成");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
            $(".oilcard_button").prop("disabled", false);
            $("#oilcard_button").val("确认生成");
        }
    });
}

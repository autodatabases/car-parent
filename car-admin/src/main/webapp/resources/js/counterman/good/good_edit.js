var countermanGood_id = getCookie('countermanGoodId');
$(function() {
    getPoints();
    if (countermanGood_id) {
        if (countermanGood_id == "-100") {
			batchIncrease();
			delCookie('countermanGoodId');
			return;
		}
        editCountermanGood();
    } else {
        countermanGood_id = null;
    }
    $("#countermanGood_button").click(function() {
        countermanGood_button();
    });

    $(".goodPhoto input[type=file]").change(function() { //商品上传图片
        if ($(".goodPhoto input[type=file]").val() != '') $("#submit_form").submit();
    });
    //iframe加载响应，初始页面时也有一次，此时data为null。
    $("#exec_target").load(function() {
        var data = eval("(" + document.getElementById("exec_target").contentWindow.document.body.innerText + ")");
        if (data.url) {
            $(".goodPhoto .img-box").css("display", "block");
            $(".goodPhoto img").attr("src", data.url);
            $(".upload-btn").hide();
            // $(".goodPhoto_p").html("重新上传");
        } else {
        	$.tip(data.message);
            //$.tip("图片长宽必须在180px和220px之间");
        }

    });
    $(".goodPhoto .img-box").on("click",function() {
        $(".goodPhoto input").click();
    })
    // $("#goodPrice").on('keyup', function() {
    //     var goodPrice = $.trim($("#goodPrice").val());
    //     if (goodPrice == "") {
    //         return;
    //     }
    //     if (!(/^\d+(\.{0,1}\d+){0,1}$/.test(goodPrice))) {
    //         $.tip("商品价格格式不对,请正确填写");
    //         return;
    //     }
    //     if (parseFloat(goodPrice) > 100000) {
    //         $.tip("你输入的价格过大！");
    //         $("#goodPrice").val(0);
    //         return;
    //     }
    // })
    // $("#goodScore").on('keyup', function() {
    //     var goodScore = $.trim($(this).val());
    //     if (goodScore == "") {
    //         return;
    //     }
    //     if (!(/^[0-9]*[1-9][0-9]*$/.test(goodScore))) {
    //         $.tip("商品积分格式不对,请正确填写");
    //         return;
    //     }
    //     if (parseFloat(goodScore) > 100000000) {
    //         $.tip("你输入的积分过大！");
    //         $(this).val(0);
    //         return;
    //     }
    // })
    // $("#guoPrice").on('keyup', function() {
    //     var guoPrice = $.trim($("#guoPrice").val());
    //     if (guoPrice == "") {
    //         return;
    //     }
    //     if (!(/^\d+(\.{0,1}\d+){0,1}$/.test(guoPrice))) {
    //         $.tip("商品价格格式不对,请正确填写");
    //         return;
    //     }
    //     if (parseFloat(guoPrice) > 100000) {
    //         $.tip("你输入的价格过大！");
    //         $("#guoPrice").val(0);
    //         return;
    //     }
    // })
    // $("#goodNumber").on('keyup', function() {
    //     var goodNumber = $.trim($(this).val());
    //     if (goodNumber == "") {
    //         return;
    //     }
    //     if (!(/^(0|[1-9]\d*)$/.test(goodNumber))) {
    //         $.tip("商品库存格式不对,请正确填写！");
    //         return;
    //     }
    //     if (goodNumber.length >= 6) {
    //         $.tip("你输入的库存数量过大！");
    //         goodNumber = goodNumber.substring(0,6);
    //         $(this).val(goodNumber);
    //         return;
    //     }
    // })
});

function getPoints() {
    $.ajax({
        url: DOMIN.MAIN + "/countermanCaiDot/queryCaiDotList",
        type: "post",
        processData: true,
        cache: false,
        async: false,
        dataType: "json",
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                var html = "<option value='0'>请选择网点</option>";
                for (var i = 0; i < data.list.length; i++) {
                    html += "<option value='" + data.list[i].id + "' scale='" + data.list[i].ratio + "'>" + data.list[i].dotName + "</option>";
                }
                $("#points").html(html);
            } else {
                $.tip("查询信息失败！");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip("连接服务器失败！");
        }
    });
}

function editCountermanGood() { //商品修改
    $.ajax({
        url: DOMIN.MAIN + "/countermanGood/getCountermanGood",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
            countermanGoodId: countermanGood_id
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                delCookie('countermanGoodId');
                $("#goodName").val(data.data.goodName);
                $("#goodPrice").val(data.data.goodPrice);
                $("#guoPrice").val(data.data.guoPrice);
                $("#goodNumber").val(data.data.goodNumber);
                $("#goodScore").val(data.data.goodScore);
                $("#points").val(data.data.caiDotId);
                if (data.data.goodImg) {
                    $("#goodImg").parent("div").css("display", "block");
                    $("#goodImg").attr("src", data.data.goodImg);
                    $(".upload-btn").hide();
                }
                $("#goodStatus").val(data.data.goodStatus);
                $("#introduce").val(data.data.remark);
            } else {
                $.tip("查询信息失败");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
        }
    });
}

function countermanGood_button() { //提交
    $("#countermanGood_button").prop("disabled", true);
    var goodPrice = $.trim($("#goodPrice").val());
    var goodName = $.trim($("#goodName").val());
    var goodScore = $.trim($("#goodScore").val());
    var guoPrice = $.trim($("#guoPrice").val());
    var remark = $.trim($("#introduce").val());
    var goodNumber = $.trim($("#goodNumber").val());
    if (goodName == "") {
        $.tip("商品名称不能为空！");
        $("#countermanGood_button").prop("disabled", false);
        $("#goodName").focus();
        return;
    } else {
        if (goodName.length > 28) {
            $.tip("商品名称不能超过28个字符!");
            $("#countermanGood_button").prop("disabled", false);
            $("#goodName").focus();
            return;
        }
    }
    if ($("#points").val() == 0) {
        $.tip("请先选择所属财险网点！");
        $("#countermanGood_button").prop("disabled", false);
        $("#points").focus();
        return;
    }
    if (goodPrice == "") {
        $.tip("商品价格不能为空！");
        $("#countermanGood_button").prop("disabled", false);
        $("#goodPrice").focus();
        return;
    } else {
        if (!(/^\d+(\.{0,1}\d+){0,1}$/.test(goodPrice))) {
            $.tip("商品价格格式不对,请正确填写");
            $("#countermanGood_button").prop("disabled", false);
            $("#goodPrice").focus();
            return;
        }
        if (parseFloat(goodPrice) > 100000) {
            $.tip("你输入的价格过大！");
            $("#countermanGood_button").prop("disabled", false);
            $("#goodPrice").focus();
            return;
        }
    }
    if (goodScore == "") {
        $.tip("商品积分不能为空！");
        $("#countermanGood_button").prop("disabled", false);
        $("#goodScore").focus();
        return;
    }  else {
        if (!(/^[0-9]*[1-9][0-9]*$/.test(goodScore))) {
            $.tip("商品积分格式不对,请正确填写");
            $("#countermanGood_button").prop("disabled", false);
            $("#goodScore").focus();
            return;
        }
    }
    if (guoPrice == "") {
        $.tip("结算价格不能为空！");
        $("#countermanGood_button").prop("disabled", false);
        $("#guoPrice").focus();
        return;
    } else {
        if (!(/^\d+(\.{0,1}\d+){0,1}$/.test(guoPrice))) {
            $.tip("结算价格格式不对,请正确填写");
            $("#countermanGood_button").prop("disabled", false);
            $("#guoPrice").focus();
            return;
        }
        if (parseFloat(guoPrice) > 100000) {
            $.tip("你输入的价格过大！");
            $("#countermanGood_button").prop("disabled", false);
            $("#guoPrice").focus();
            return;
        }
    }
    if (goodNumber == "") {
        $.tip("商品库存不能为空！");
        $("#countermanGood_button").prop("disabled", false);
        $("#goodNumber").focus();
        return;
    } else {
        if (!(/^[0-9]+$/.test(goodNumber))) {
            $.tip("商品库存格式不对,请正确填写");
            $("#countermanGood_button").prop("disabled", false);
            $("#goodNumber").focus();
            return;
        }
        if (goodNumber.length >= 6) {
            $.tip("你输入的库存数量过大！");
            $("#countermanGood_button").prop("disabled", false);
            $("#goodNumber").focus();
            return;
        }
    }
    $.ajax({
        url: DOMIN.MAIN + "/countermanGood/addOrUpdateCountermanGood",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
            id: countermanGood_id,
            goodName: goodName,
            goodPrice: goodPrice,
            goodScore: goodScore,
            guoPrice: guoPrice,
            goodNumber: goodNumber,
            caiDotId: $.trim($("#points").val()),
            goodImg: $.trim($("#goodImg").attr("src")),
            goodStatus: $.trim($("#goodStatus").val()),
            remark: $.trim(remark)
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                $.tip("操作成功");
                setTimeout(function() {
                    $('.tables').load('counterman/good/good_list.html');
                }, 1000);
            } else {
                $.tip(data.message);
                $("#countermanGood_button").prop("disabled", false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
            $("#countermanGood_button").prop("disabled", false);
        }
    });
}
function batchIncrease() {
	$(".table01").hide();
	$(".table02").show();
	$("#piliang_button").on('click', function() {
		$("#piliang_button").attr("disabled",true);
		if($("#uploadfile").val()!=""){
			$("#daoru").submit();
		}else{
			$.tip('请选择导入文件！');
			$("#piliang_button").attr("disabled",false);
			return;
		}
		$("#import-model").load(function(){
			if(document.getElementById("import-model").contentWindow.document.body.innerText!=""){
				var val = eval("(" + document.getElementById("import-model").contentWindow.document.body.innerText + ")");
				if(val.success){
					$.tip("提交成功！共计提交" + val.data.success + "条");
					$("#piliang_button").attr("disabled",false);
				}else{
					$.tip(val.message);
					$("#piliang_button").attr("disabled",false);
				}
			}
	    });
	});
}

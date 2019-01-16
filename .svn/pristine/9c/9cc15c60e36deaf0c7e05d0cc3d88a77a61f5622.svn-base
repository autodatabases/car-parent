var denominationId = getCookie("denominationId");
$(function() {
    if (denominationId) {
        editDenomination();
        delCookie("denominationId");
    }
    $("#denomination_button").on('click', function() {
        if (denominationId) {
            editSubmit();
            return;
        }
        submitDenomination();
    });
    $("#suppli,#mfrs").change(function(event) {
        if($("#suppli").val() === "1" &&  ($("#mfrs").val() === "0" || $("#mfrs").val() === "1")) {
            $("#identifier").parent().show();
        } else {
            $("#identifier").parent().hide();
        }
    });

})
function submitDenomination () {
    var reg = new RegExp('^\\+?[1-9][0-9]*$');
    if ($("#suppli").val() == "") {
        $.tip("请选择服务商！");
        return;
    }
    if ($("#mfrs").val() == "") {
        $.tip("请选择油卡厂商！");
        return;
    }
    if($("#suppli").val() === "1" &&  ($("#mfrs").val() === "0" || $("#mfrs").val() === "1")) {
        if($("#identifier").val() === "" || $("#identifier").val().length > 30) {
            $.tip("请填写正确的产品编号！");
            return;
        }
    }
    if(!reg.test($("#number").val())) {
        $.tip("请填写正整数的面额值！");
        return;
    }
    if ($("#status").val() == "") {
        $.tip("请选择面额状态！");
        return;
    }

    $.ajax({
        url: DOMIN.MAIN + '/oilcardconfig/addorupdate',
        type: 'post',
        dataType: 'json',
        data: {
            id: denominationId,
            supplier: $("#suppli").val(),
            cardType: $("#mfrs").val(),
            goodId: $.trim($("#identifier").val()),
            content: $.trim($("#number").val()),
            status: $("#status").val()
        },
        success: function(data) {
            if (data.success) {
                $.tip("操作成功！");
                var cData = [$("#suppli").val(),$("#mfrs").val()];
                addCookie("cData",cData,"360");
                $('.tables').load('oilcard/denomination_config.html');
            } else {
                $.tip(data.message);
            }
        },
        error: function() {
            $.tip("服务器错误！")
        }
    });
}
function editDenomination() {
    $.ajax({
        url: DOMIN.MAIN + '/oilcardconfig/querybyid',
        type: 'get',
        dataType: 'json',
        data: {
            id: denominationId,
        },
        success: function(data) {
            if (data.success) {
                if(data.data.cardType === "2") {
                    var html = '<option value="">请选择油卡厂商</option><option value="0">中石化</option><option value="1">中石油</option><option value="2">话费</option>';
                    $("#mfrs").html(html);
                }
                $("#suppli").val(data.data.supplier);
                $("#mfrs").val(data.data.cardType);
                $("#number").val(data.data.content);
                $("#status").val(data.data.status);
                $("#identifier").val(data.data.goodId);

                $("#suppli").attr('disabled', true);
                $("#suppli").css("background","#ccc");
                $("#mfrs").attr('disabled', true);
                $("#mfrs").css("background","#ccc");
                $("#number").attr('disabled', true);
                $("#number").css("background","#ccc");
                $("#identifier").attr('disabled', true);
                $("#identifier").css("background","#ccc");

                $(".winfo").html("修改面额状态");
                $(".table-title").html("修改面额状态");
                if($("#suppli").val() === "1") {
                    $("#identifier").parent().show();
                } else {
                    $("#identifier").parent().hide();
                }
            } else {
                $.tip(data.message);
            }
        },
        error: function() {
            $.tip("服务器错误！")
        }
    });
}
function editSubmit() {
    if ($("#status").val() == "") {
        $.tip("请选择面额状态！");
        return;
    }
    $.ajax({
        url: DOMIN.MAIN + '/oilcardconfig/updatestatus',
        type: 'post',
        dataType: 'json',
        data: {
            id: denominationId,
            status: $("#status").val()
        },
        success: function(data) {
            if (data.success) {
                $.tip("操作成功！");
                var cData = [$("#suppli").val(),$("#mfrs").val()];
                addCookie("cData",cData,"360");
                $('.tables').load('oilcard/denomination_config.html');
            } else {
                $.tip(data.message);
            }
        },
        error: function() {
            $.tip("服务器错误！")
        }
    });
}

var area_id = getCookie('areaId');
$(function() {
    getPoints();
    if (area_id) {
        editArea();
    } else {
        area_id = null;
    }
    $("#area_button").click(function() {
        area_button();
    });
});

function editArea() { //业务员修改
    $.ajax({
        url: DOMIN.MAIN + "/countermanCareer/getCountermanCareer",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
            countermanCareerId: area_id
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                delCookie('areaId');
                $("#careerName").val(data.data.careerName);
                $("#careerCode").val(data.data.careerCode);
                $("#branchCompany").val(data.data.branchCompany);
				$("#points").val(data.data.caiDotId);
            } else {
                $.tip("查询信息失败");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
        }
    });
}

function getPoints() {
    $.ajax({
        url: DOMIN.MAIN + "/countermanCaiDot/queryCaiDotList",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                var html = "<option value='0'>请选择网点</option>";
                for (var i = 0; i < data.list.length; i++) {
                    html += "<option value='" + data.list[i].id + "'>" + data.list[i].dotName + "</option>";
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

function area_button() { //提交
    $("#area_button").prop("disabled", true);
    if($("#points").val() == "0") {
        $.tip("请选择所属网点！");
        $("#area_button").prop("disabled", false);
        return;
    }
    $.ajax({
        url: DOMIN.MAIN + "/countermanCareer/addOrUpdateCountermanCareer",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
            id: area_id,
            careerName: $.trim($("#careerName").val()),
            careerCode: $.trim($("#careerCode").val()),
            caiDotId: $("#points").val(),
            branchCompany: $.trim($("#branchCompany").val())
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                $.tip("操作成功");
                setTimeout(function() {
                    $('.tables').load('counterman/area_list.html');
                }, 1000);
            } else {
                $.tip(data.message);
                $("#area_button").prop("disabled", false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
            $("#area_button").prop("disabled", false);
        }
    });
}

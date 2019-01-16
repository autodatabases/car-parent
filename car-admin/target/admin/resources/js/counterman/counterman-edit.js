
var counterman_id = getCookie('countermanId');
$(function() {
	getPoints();
    if (counterman_id) {
		if (counterman_id == "-100") {
			batchIncrease();
			delCookie('countermanId');
			return;
		} else if (counterman_id == "-200") {
			batchEdit();
			delCookie('countermanId');
			return;
		}
        editCounterman();
    } else {
        counterman_id = null;
    }
    $("#counterman_button").click(function() {
        counterman_button();
    });
});

function editCounterman() { //业务员修改
	$(".table-title").html("业务员编辑");
    $.ajax({
        url: DOMIN.MAIN + "/counterman/getCounterman",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
            countermanId: counterman_id
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                delCookie('countermanId');
                $("#name").val(data.data.name);
                $("#phone").val(data.data.phone);
                $("#countermanCode").val(data.data.countermanCode);
                //$("#post").val(data.data.post);
                $("#score").val(data.data.score);
                $("#status").val(data.data.status);
				$("#points").val(data.data.caiDotId);
				//$("#career").val(data.data.lifeCareerId);
                $("#countermanCode").attr("disabled", true);
                $("#countermanCode").css("background-color", "#e6e3e3");
				$("#points").attr("disabled", true);
                $("#points").css("background-color", "#e6e3e3");
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
        async: false,
        dataType: "json",
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
					var html = "<option value='0'>请选择网点</option>";
					for(var i=0; i<data.list.length; i++) {
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
	$.ajax({
        url: DOMIN.MAIN + "/countermanLifeCareer/queryAllCountermanLifeCareer",
        type: "post",
        processData: true,
        cache: false,
        async: false,
        dataType: "json",
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
					var html = "<option value='0'>请选择职场</option>";
					for(var i=0; i<data.list.length; i++) {
						html += "<option value='" + data.list[i].id + "'>" + data.list[i].careerName + "</option>";
					}
            		$("#career").html(html);
            } else {
                $.tip("查询信息失败！");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip("连接服务器失败！");
        }
    });
}

function counterman_button() { //提交
    $("#counterman_button").prop("disabled", true);
    if($("#points").val() == "0") {
        $.tip("请选择所属网点！");
        $("#counterman_button").prop("disabled", false);
        return;
    }
    var phone = $("#phone").val();
    if (!(/^1[34578]\d{9}$/.test(phone))) {
        $.tip("手机号码有误，请重填");
        $("#counterman_button").prop("disabled", false);
        return;
    }
    var countermanCode = $("#countermanCode").val();
    if (countermanCode == "" || countermanCode == null) {
        $.tip("工号不能为空，请填写工号");
        $("#counterman_button").prop("disabled", false);
        return;
    }
    var score = $("#score").val();
	if (!(/^(0|[1-9]\d*)$/.test(score))) {
		$.tip("请填写正确格式的积分数字！");
		$("#counterman_button").prop("disabled", false);
		return;
	}
    $.ajax({
        url: DOMIN.MAIN + "/counterman/addOrUpdateCounterman",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
            id: counterman_id,
            name: $.trim($("#name").val()),
            phone: $.trim(phone),
           // post: $.trim($("#post").val()),
            countermanCode: $.trim($("#countermanCode").val()),
            status: $.trim($("#status").val()),
            score: $.trim($("#score").val()),
			caiDotId: $.trim($("#points").val()),
			//lifeCareerId: $.trim($("#career").val())
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                $.tip("操作成功");
                setTimeout(function() {
                    $('.tables').load('counterman/counterman-list.html');
                }, 1000);
            } else {
                $.tip(data.message);
                $("#counterman_button").prop("disabled", false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
            $("#counterman_button").prop("disabled", false);
        }
    });
}
function batchIncrease() {
	$(".table1").hide();
	$(".table2").show();
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
function batchEdit() {
	$(".table1").hide();
	$(".table3").show();
	$("#pedit_button").on('click', function() {
		$("#pedit_button").attr("disabled",true);
		if($("#uploadfiles").val()!=""){
			$("#xiugai").submit();
		}else{
			$.tip('请选择导入文件！');
			$("#pedit_button").attr("disabled",false);
			return;
		}
		$("#import-model-edit").load(function(){
			if(document.getElementById("import-model-edit").contentWindow.document.body.innerText!=""){
				var val = eval("(" + document.getElementById("import-model-edit").contentWindow.document.body.innerText + ")");
				if(val.success){
					$.tip("提交成功！共计提交" + val.data.success + "条数据");
					$("#pedit_button").attr("disabled",false);
				}else{
					$.tip(val.message);
					$("#pedit_button").attr("disabled",false);
				}
			}
	    });
	});
}

$(function() {
	init();
    $("#config_button").click(function() {
    	config_button();
    });
});
function init() {
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/queryconfig",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				if(data.data){
					$("#supplier").val(data.data.supplier);
					$("#id").val(data.data.id);
				}else{
					$.tip("没有选择服务商");
				}
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function config_button() { //提交
    $("#config_button").prop("disabled", true);
	//判断区域必须填写
	if($.trim($("#supplier").val())==""){
		$.tip("请选择服务商");
		$("#config_button").prop("disabled", false);
		return;
	}
    $.ajax({
        url: DOMIN.MAIN + "/oilCard/updateconfig",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
        	id: $.trim($("#id").val()),
            supplier: $.trim($("#supplier option:selected").val())
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                $.tip("操作成功");
                setTimeout(function() {
                    $('.tables').load('oilcard/oilcard_config.html');
                }, 1000);
            } else {
                $.tip(data.message);
                $("#config_button").prop("disabled", false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
            $("#config_button").prop("disabled", false);
        }
    });
}

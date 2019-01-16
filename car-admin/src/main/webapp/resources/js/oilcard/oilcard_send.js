$(function() {
    $("#oilcard_button").click(function() {
    	oilcard_button();
    });
    getCity();
});

function getCity(){
	$.ajax({
		url : DOMIN.MAIN+"/address/queryCityByProvince",
		type : "post",
		cache : false,
		async : false,
		dataType : "json",
		data: {
			provinceId: 20
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				var obj = data.list;
					//处理返回结果
				$("#city").empty();
				$("#city").append('<option value="">选择城市</option>');
				for(var i=0;i<obj.length;i++){
					$("#city").append('<option value="'+obj[i].id+'">'+obj[i].regionname+'</option>');
				}
			}else{
				alert(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
		}
	});
}

function oilcard_button() { //提交
    $("#oilcard_button").prop("disabled", true);
    //判断手机号格式正确与否
    var phone=$.trim($("#phone").val());
    if (!(/^1[34578]\d{9}$/.test(phone))) {
        $.tip("手机号码有误，请重填");
        $("#oilcard_button").prop("disabled", false);
        return;
    }
    //判断金额正确与否
	var price=$.trim($("#price").val());
	if(!(/^[0-9]+$/.test(price))){
		$.tip("金额格式不对,请正确填写");
		$("#oilcard_button").prop("disabled",false);
		return;
	}else if(price-0<=0){
		$.tip("金额必须大于0,请正确填写");
		$("#oilcard_button").prop("disabled",false);
		return;
	}
	//判断区域必须填写
	if($.trim($("#city").val())==""){
		$.tip("请选择地址");
		$("#oilcard_button").prop("disabled", false);
		return;
	}
    $.ajax({
        url: DOMIN.MAIN + "/oilCard/sendOilCard",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
        	userName: $.trim($("#userName").val()),
            phone: phone,
            chePai: $.trim($("#chePai").val()),
            money: price,
            address: $.trim($("#city option:selected").text())
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                $.tip("操作成功");
                $(".sidebar ul").find("li").eq(0).removeClass("on");
            	$(".sidebar ul").find("li").eq(1).addClass("on");
                setTimeout(function() {
                    $('.tables').load('oilcard/oilcard_list.html');
                }, 1000);
            } else {
                $.tip(data.message);
                $("#oilcard_button").prop("disabled", false);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
            $("#oilcard_button").prop("disabled", false);
        }
    });
}

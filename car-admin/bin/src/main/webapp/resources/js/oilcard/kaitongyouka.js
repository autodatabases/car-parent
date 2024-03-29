$(function() {
    $("#oilcard_button").click(function() {
    	oilcard_button();
    });
    getCity();
    $(".searchBtn").click(function(){
        //判断序列号格式正确与否
        var reg = /^\d{9}$/;
        if ($.trim($("#startCode").val()) !== "") {
            if(!reg.test($.trim($("#startCode").val()))) {
                $.tip("请输入9位数字的开始序列号");
                $("#oilcard_button").prop("disabled",false);
        		return;
            }
        } else {
            $.tip("请输入开始序列号");
            $("#oilcard_button").prop("disabled",false);
    		return;
        }
        if ($.trim($("#endCode").val()) !== "") {
            if(!reg.test($.trim($("#endCode").val()))) {
                $.tip("请输入9位数字的结束序列号");
                $("#oilcard_button").prop("disabled",false);
        		return;
            }
        } else {
            $.tip("请输入结束序列号");
            $("#oilcard_button").prop("disabled",false);
    		return;
        }
        if($.trim($("#startCode").val()) - $.trim($("#endCode").val()) > 0) {
            $.tip("结束序列号必须大于或等于开始序列号");
            $("#oilcard_button").prop("disabled",false);
    		return;
        }
		initPaginator("child_paga", getChildData,1,localStorage.getItem('globalPageSize'));
        getChildData();
	})
	$(".tips-bg").click(function(){
		$(".child_html").hide();
	})
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
				$("#address").empty();
				$("#address").append('<option value="">选择城市</option>');
				for(var i=0;i<obj.length;i++){
					console.log(obj[i])
					$("#address").append('<option value="'+obj[i].id+'">'+obj[i].regionname+'</option>');
				}
				$("#address").append('<option value="赠送">赠送</option>');
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

    //判断序列号格式正确与否
    var reg = /^\d{9}$/;
    if ($.trim($("#startCode").val()) !== "") {
        if(!reg.test($.trim($("#startCode").val()))) {
            $.tip("请输入9位数字的开始序列号");
            $("#oilcard_button").prop("disabled",false);
    		return;
        }
    } else {
        $.tip("请输入开始序列号");
        $("#oilcard_button").prop("disabled",false);
		return;
    }
    if ($.trim($("#endCode").val()) !== "") {
        if(!reg.test($.trim($("#endCode").val()))) {
            $.tip("请输入9位数字的结束序列号");
            $("#oilcard_button").prop("disabled",false);
    		return;
        }
    } else {
        $.tip("请输入结束序列号");
        $("#oilcard_button").prop("disabled",false);
		return;
    }
    if($.trim($("#startCode").val()) - $.trim($("#endCode").val()) > 0) {
        $.tip("结束序列号必须大于或等于开始序列号");
        $("#oilcard_button").prop("disabled",false);
		return;
    }
    //判断供应商必须填写
	if($.trim($("#supplier").val())==""){
		$.tip("请选择供应商");
		$("#oilcard_button").prop("disabled", false);
		return;
	}
	//判断城市必须填写
	if($.trim($("#address").val())==""){
		$.tip("请选择油卡城市");
		$("#oilcard_button").prop("disabled", false);
		return;
	}
    //判断申请人必须填写
	if(!$.trim($("#proposer").val())==""){
        if($.trim($("#proposer").val()).length > 5) {
            $.tip("请输入5个字符以内申请人");
            $("#oilcard_button").prop("disabled", false);
            return;
        }
	} else {
        $.tip("请填写申请人");
		$("#oilcard_button").prop("disabled", false);
		return;
    }
    //判断购买方必须填写
	if(!$.trim($("#buyer").val())==""){
        if($.trim($("#buyer").val()).length > 20) {
            $.tip("请输入20个字符以内购买方");
            $("#oilcard_button").prop("disabled", false);
            return;
        }
	} else {
        $.tip("请填写购买方");
		$("#oilcard_button").prop("disabled", false);
		return;
    }
    $.ajax({
        url: DOMIN.MAIN + "/oilCard/openOil",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
        	startCode: $.trim($("#startCode").val()),
            endCode: $.trim($("#endCode").val()),
            proposer: $.trim($("#proposer").val()),
            address: $.trim($("#address option:selected").text()),
            buyer: $.trim($("#buyer").val()),
            supplier: $.trim($("#supplier").val()),
            remark: $.trim($("#remark").val())
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                $.tip("已开通面额为 " + data.data.money + " 的油卡 " + data.data.number + " 张");
                setTimeout(function() {
                    $('.tables').load('oilcard/kaitongyouka.html');
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

//获取详情子页面数据
var isload_child = false;
function getChildData() {
	if(isload_child){
		return;
	}
	var load = $.loading();
	isload_child = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/findoilcard",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["child_paga"].pageNo,
			pageSize:_allPages["child_paga"].pageSize,
            startCode: $.trim($("#startCode").val()),
            endCode: $.trim($("#endCode").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload_child=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
                $(".code_number span").html($.trim($("#startCode").val()) + "--" + $.trim($("#endCode").val()) + " , 共" + data.datasets.num.data + "张" )
				$("#child-list tbody").empty();
				if(returnObj.list.length>0){
                    renderHtmlChild(returnObj.list);
				}else{
					$("#child-list tbody").append('<tr><td colspan="4" style="color:#e91e63;">没有油卡数据~~</td></tr>');
				}
				// 分页
				setPaginator("child_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
						returnObj.pageInfo.recordCount);
			}else{
				isload_child=false;
				load.remove();
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			isload_child=false;
			load.remove();
			$.tip(errorThrown);
		}
	});
}
function renderHtmlChild(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j] === null || datalist[i][j] === ""){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].phone+"</td>";
		html+="<td>"+datalist[i].money+"</td>";
        html+="<td>"+$.formatDate(datalist[i].createTime) +"</td>";
        if(datalist[i].deadTime == "--"){
        	html+="<td>--</td>";
        }else{
        	html+="<td>"+$.formatDate(datalist[i].deadTime).slice(0,10) +"</td>";
        }
        
		html+="</tr>"
	}
	$("#child-list tbody").append(html);
    $(".child_html").show();
    $(".code_number").show();
}

var oilcardList = {};
var recordId = sessionStorage.getItem("recordId");
var orderId = null;
var startNum = "";
var endNum = "";
oilcardList.refresh = function(){
	//	_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
}
var isload = false;
oilcardList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilmake/queryallorder",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["oilcard_paga"].pageNo,
			pageSize:_allPages["oilcard_paga"].pageSize,
            recordId: recordId,
			startNum: startNum,
			endNum: endNum
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$("#tab-list tbody").empty();
				if(returnObj.list.length>0){
					oilcardList.renderHtml(returnObj.list);
				}else{
					$("#tab-list tbody").append('<tr><td colspan="10" style="color:#e91e63;">没有油卡数据~~</td></tr>');
				}
				// 分页
				setPaginator("oilcard_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
						returnObj.pageInfo.recordCount);
			}else{
				isload=false;
				load.remove();
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			isload=false;
			load.remove();
			$.tip(errorThrown);
		}
	});
}
oilcardList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j] === null || datalist[i][j] === ""){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].id+"</td>";
		html+="<td>"+datalist[i].startCode+"</td>";
		html+="<td>"+datalist[i].endCode+"</td>";
        html+="<td>500</td>";
        html+="<td>"+datalist[i].money+"</td>";
        if(datalist[i].status === 0) {
            html+="<td>待处理</td>";
        } else if(datalist[i].status === 1) {
            html+="<td>已处理</td>";
        } else {
            html+="<td>"+datalist[i].status+"</td>";
        }
        html+="<td>"+$.formatDate(datalist[i].createTime) +"</td>";
        html+="<td>"+$.formatDate(datalist[i].deadTime).slice(0,10) +"</td>";
		html+="<td>";
		html+='<a href="javascript:void(0)" class="chakan" orderId="'+datalist[i].id+'" style="color:#fc9338;">查看&nbsp;';
		html+="</td>";
        html+="<td>";
		html+='<a href="javascript:void(0)" class="daochu" orderId="'+datalist[i].id+'" style="color:#fc9338;">导出&nbsp;';
		html+="</td>";
		html+="</tr>"
	}
	$("#tab-list tbody").append(html);
    oilcardList.operate(datalist);
}
oilcardList.operate = function(datalist) {
    $(".chakan").click(function(){
        orderId = $(this).attr("orderId");
		initPaginator("child_paga", oilcardList.dataList_child,1,localStorage.getItem('globalPageSize'));
        oilcardList.dataList_child();
		$(".child_html").show();
	})
	$(".daochu").click(function(){
        orderId = $(this).attr("orderId");
		window.open('oilmake/exportoilrechargecode?orderId=' + orderId);
	})
}
$(function() {
	initPaginator("oilcard_paga", oilcardList.dataList,1,localStorage.getItem('globalPageSize'));
	oilcardList.dataList();
	$(".tips-bg").click(function(){
		$(".child_html").hide();
	})
    $("#zhizuo_list,#back_btn").on("click",function() {
        $(".tables").load('oilcard/zhizuoyouka/zhizuoyouka.html');
    })
	$("#search-btn").click(function(){//根据查询条件查询

		//判断序列号格式正确与否
	    var reg = /^\d{9}$/;
		if($.trim($("#startNum").val()) !== "" || $.trim($("#endNum").val()) !== "") {
			if ($.trim($("#startNum").val()) !== "") {
		        if(!reg.test($.trim($("#startNum").val()))) {
		            $.tip("请输入9位数字的开始序列号");
		    		return;
		        }
		    } else {
		        $.tip("请输入开始序列号");
				return;
		    }
		    if ($.trim($("#endNum").val()) !== "") {
		        if(!reg.test($.trim($("#endNum").val()))) {
		            $.tip("请输入9位数字的结束序列号");
		    		return;
		        }
		    } else {
		        $.tip("请输入结束序列号");
				return;
		    }
		    if($.trim($("#startNum").val()) - $.trim($("#endNum").val()) > 0) {
		        $.tip("结束序列号必须大于或等于开始序列号");
				return;
		    }
		}
		startNum = $.trim($("#startNum").val());
		endNum = $.trim($("#endNum").val());
		_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
	});
	$("#exportSelectBtn").click(function(){
		//判断序列号格式正确与否
	    var reg = /^\d{9}$/;
		if($.trim($("#startNum").val()) !== "" || $.trim($("#endNum").val()) !== "") {
			if ($.trim($("#startNum").val()) !== "") {
		        if(!reg.test($.trim($("#startNum").val()))) {
		            $.tip("请输入9位数字的开始序列号");
		    		return;
		        }
		    } else {
		        $.tip("请输入开始序列号");
				return;
		    }
		    if ($.trim($("#endNum").val()) !== "") {
		        if(!reg.test($.trim($("#endNum").val()))) {
		            $.tip("请输入9位数字的结束序列号");
		    		return;
		        }
		    } else {
		        $.tip("请输入结束序列号");
				return;
		    }
		    if($.trim($("#startNum").val()) - $.trim($("#endNum").val()) > 0) {
		        $.tip("结束序列号必须大于或等于开始序列号");
				return;
		    }
		}
		window.open('oilmake/exportoilrechargecodetwo?startCode=' + $("#startNum").val() + "&endCode=" + $("#endNum").val()  + "&recordId=" + recordId);
	})
});

//获取详情子页面数据
var isload_child = false;
oilcardList.dataList_child = function() {
	if(isload_child){
		return;
	}
	var load = $.loading();
	isload_child = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilmake/queryallcode",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["child_paga"].pageNo,
			pageSize:_allPages["child_paga"].pageSize,
            orderId: orderId
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload_child=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$("#child-list tbody").empty();
				if(returnObj.list.length>0){
					oilcardList.renderHtml_child(returnObj.list);
				}else{
					$("#child-list tbody").append('<tr><td colspan="7" style="color:#e91e63;">没有油卡数据~~</td></tr>');
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
oilcardList.renderHtml_child = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j] === null || datalist[i][j] === ""){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].id+"</td>";
		html+="<td>"+datalist[i].phone+"</td>";
		html+="<td>"+datalist[i].money+"</td>";
        html+="<td>"+datalist[i].rechargeCode+"</td>";
		if (datalist[i].status == 0) {
			html+="<td>未使用</td>";
		} else if(datalist[i].status == 1){
			html+="<td>已使用</td>";
		} else if(datalist[i].status == 2){
			html+="<td>已冻结</td>";
		} else if(datalist[i].status == 3){
			html+="<td>已过期</td>";
		} else if(datalist[i].status == 9){
			html+="<td>未开通</td>";
		} else{
			html+="<td>--</td>";
		}
        html+="<td>"+$.formatDate(datalist[i].createTime) +"</td>";
        html+="<td>"+$.formatDate(datalist[i].deadTime).slice(0,10) +"</td>";
		html+="</tr>"
	}
	$("#child-list tbody").append(html);
}

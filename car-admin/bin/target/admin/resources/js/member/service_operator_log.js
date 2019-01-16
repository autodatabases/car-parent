var serviceLogList = {};
serviceLogList.refresh = function(){
		// _allPages["servicelog_paga"].pageNo = 1;
		serviceLogList.dataList();
}
var isload = false;
serviceLogList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/servicelog/queryServiceLogList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["servicelog_paga"].pageNo,
			pageSize:_allPages["servicelog_paga"].pageSize,
			serviceType:$.trim($("#serviceType").val()),
			verifyStatus:$.trim($("#verifyStatus").val()),
			num:$.trim($("#num").val()),
			chePai:$.trim($("#chePai").val()),
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					serviceLogList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:#3f51b5;">没有发送服务次数~</td></tr>');
				}
				// 分页
				setPaginator("servicelog_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
serviceLogList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j] === null || datalist[i][j] === ""){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].id+'</td>';
		html+='<td>'+datalist[i].operator+'</td>';
		html+='<td>'+datalist[i].serviceValue+'</td>';
		if (datalist[i].serviceType == 0) {
			html+='<td>电瓶</td>';
		} else if (datalist[i].serviceType == 1) {
			html+='<td>轮胎</td>';
		} else if (datalist[i].serviceType == 2) {
			html+='<td>小保养</td>';
		} else if (datalist[i].serviceType == 3) {
			html+='<td>洗车</td>';
		} else if (datalist[i].serviceType == 4) {
			html+='<td>喷漆</td>';
		} else{
			html+='<td>--</td>';
		}
		html+='<td>'+datalist[i].chePai+'</td>';
		if(datalist[i].createTime !== "--") {
			html+='<td>'+$.formatDate(datalist[i].createTime)+'</td>';
		} else {
			html+='<td>--</td>';
		}
		if (datalist[i].verifyStatus === 0) {
			html+='<td>待审核</td>';
		} else if (datalist[i].verifyStatus === 1) {
			html+='<td>审核通过</td>';
		} else if (datalist[i].verifyStatus === 2) {
			html+='<td>审核不通过</td>';
		} else{
			html+='<td>--</td>';
		}
		html+="<td style='cursor:pointer'>"+datalist[i].remark +"</td>";
		if (datalist[i].verifyStatus === 0) {
			html+="<td>";
			html+='<a href="javascript:void(0)" class="shenhe" orderid="'+datalist[i].id+'" style="color:#fc9338;">审核&nbsp;</a>';
			html+='<a href="javascript:void(0)" class="beizhu" orderid="'+datalist[i].id+'" style="color:#fc9338;">备注&nbsp;';
			html+='<p style="display:none">'+datalist[i].remark+'</p></a>';
			html+="</td>";
		} else{
			html+="<td>";
			html+='<a href="javascript:void(0)" class="shenhe" orderid="">&nbsp;&nbsp;--&nbsp;&nbsp;&nbsp;</a>';
			html+='<a href="javascript:void(0)" class="beizhu" orderid="'+datalist[i].id+'" style="color:#fc9338;">备注&nbsp;';
			html+='<p style="display:none">'+datalist[i].remark+'</p></a>';
			html+="</td>";
		}
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	//备注超过30字截取
	$(".tab-list tbody").find("tr").each(function(){
		var remakTem = $.trim($(this).find("td").eq(7).html());
		if(remakTem.length-0>12){
			$(this).find("td").eq(7).html(remakTem.substring(0,12)+"...");
			$(this).find("td").eq(7).attr("title",remakTem);
		}
	});
	serviceLogList.operate(datalist);
}
serviceLogList.operate = function(datalist) {
    $(".shenhe").click(function(){
        var orderNo = $(this).attr('orderid');
		if(orderNo === "") {
			return;
		}
		$(".shenhe_btn").attr("orderid",orderNo);
		$(".tips-warp").show();
	})
	$(".beizhu").click(function(){
		var remark = $(this).children('p').html();
		var orderNo = $(this).attr('orderid');
		$("#orderOperRemark-orderNo").val(orderNo);
		if(remark === "--") {
			remark = "";
		}
		if(remark!='null' && remark!='undefined'){
			$("#orderOperRemarkContent").val(remark);
		}
		$("#orderOperRemark").show();
	})
}
//提交备注
serviceLogList.submitRemak = function(){
	$.ajax({
		url : DOMIN.MAIN + "/servicelog/updateremark",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			id:$("#orderOperRemark-orderNo").val(),
			remark:$("#orderOperRemarkContent").val()
		},
		success : function(data, textStatus) {
			$("#orderOperRemark-orderNo").val('');
			$("#orderOperRemarkContent").val('');
			if (data.success) {
				$.tip("操作成功");
				$("#orderOperRemark").hide();
				serviceLogList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
serviceLogList.checklog  = function(id,verifyStatus){
	$.ajax({
		url : DOMIN.MAIN + "/servicelog/checklog ",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data : {
			id: id,
			verifyStatus: verifyStatus
		},
		success : function(data, textStatus) {
			if (data.success) {
				if(data.data === "SUCCESS") {
					$.tip("操作成功");
					$(".tips-warp").hide();
					serviceLogList.refresh();
				}

			}else{
				$.tip(data.message);
				$(".tips-warp").hide();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
			$(".tips-warp").hide();
		}
	})
}
$(function() {
	initPaginator("servicelog_paga", serviceLogList.dataList,1,localStorage.getItem('globalPageSize'));
	serviceLogList.dataList();

	$("#search").on("click",function(){
		var rel = /^\+?[1-9][0-9]*$/;
		if($.trim($("#num").val()) !== "") {
			if(!rel.test($("#num").val())) {
				$.tip("请输入正确格式的发送次数");
				return;
			}
		}
		_allPages["servicelog_paga"].pageNo = 1;
		serviceLogList.dataList();
	});
	$(".shenhe_btn").on("click",function(){
		var id = $(this).attr("orderid");
		var verifyStatus = $(this).attr("status");
		serviceLogList.checklog(id,verifyStatus);
	});
	$(".close_btn").on("click",function(){
		$(".tips-warp").hide();
	});
	$(".close_btns").on("click",function(){
		$("#orderOperRemark").hide();
	});
	$(".submit_btn").on("click",function(){
		serviceLogList.submitRemak();
	});

});

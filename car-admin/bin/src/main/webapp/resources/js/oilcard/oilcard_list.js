var oilcardList = {};
oilcardList.refresh = function(){
	//	_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
}
oilcardList.summary = function(){
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/summary",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			$("#money_one").html(data.data.one);
			$("#money_two").html(data.data.two);
			$("#money_three").html(data.data.three);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
var isload = false;
oilcardList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/oilCardList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["oilcard_paga"].pageNo,
			pageSize:_allPages["oilcard_paga"].pageSize,
			userPhone:$.trim($("#userPhone").val()),
			startNum:$.trim($("#startNum").val()),
			endNum:$.trim($("#endNum").val()),
			status:$("#status").val(),
			phone:$("#phone").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list-send tbody").empty();
				if(returnObj.list.length>0){
					oilcardList.renderHtml(returnObj.list);
				}else{
					$(".tab-list-send tbody").append('<tr><td colspan="12" style="color:#e91e63;">没有发送的油卡数据~~</td></tr>');
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
		html+="<td>"+datalist[i].userName+"</td>";
		html+="<td>"+datalist[i].phone+"</td>";
		if (datalist[i].chePai == "system") {
			html+="<td>油卡发放</td>";
		} else {
			html+="<td>短信发放</td>";
		}
		html+="<td>"+datalist[i].address+"</td>";
		if(datalist[i].openTime === '--') {
			html+="<td>--</td>";
		} else {
			html+="<td>"+$.formatDate(datalist[i].openTime)+"</td>";
		}
		html+="<td>"+datalist[i].money+"</td>";
		html+="<td>"+datalist[i].recharge +"</td>";
		// html+="<td>"+datalist[i].address+"</td>";
		if(datalist[i].smsStatus==0){
			html+="<td>未发送</td>";
		}else if(datalist[i].smsStatus==1){
			html+="<td>发送成功</td>";
		}else if(datalist[i].smsStatus==2){
			html+="<td>发送失败</td>";
		} else {
			html+="<td>--</td>";
		}
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

		if(datalist[i].createTime === '--') {
			html+="<td>--</td>";
		} else {
			html+="<td>"+$.formatDate(datalist[i].createTime)+"</td>";
		}
		html+="<td style='cursor:pointer'>"+datalist[i].remark+"</td>";
		html+="<td>";
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338">备注&nbsp;';
		html+='<p style="display:none">'+datalist[i].remark+'</p></a>';
		html+="</td>";
		html+="</tr>"
	}
	$(".tab-list-send tbody").append(html);
	//备注超过30字截取
	$(".tab-list-send tbody").find("tr").each(function(){
		var remakTem = $.trim($(this).find("td").eq(10).html());
		if(remakTem.length-0>12){
			$(this).find("td").eq(10).html(remakTem.substring(0,12)+"...");
			$(this).find("td").eq(10).attr("title",remakTem);
		}
	});
	$(".beizhu").click(function(){
		var remark = $(this).children('p').html();
		var orderNo = $(this).attr('ono');;
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
$(function() {
	initPaginator("oilcard_paga", oilcardList.dataList,1,localStorage.getItem('globalPageSize'));
	oilcardList.dataList();
	//oilcardList.summary();
	$("#export").click(function(){
		$(".search-box .success_span").remove();
		if($("#uploadfile").val()!=""){
			$("#sendOilCard").submit();
		}else{
			$.tip('请选择文件！');
		}
	});
	$("#import-oilcard-result").load(function(){
		if(document.getElementById("import-oilcard-result").contentWindow.document.body.innerText!=""){
			var val = eval("(" + document.getElementById("import-oilcard-result").contentWindow.document.body.innerText + ")");
			if(val.success){
				$("#uploadfile").val("");
				var html="<span class='success_span' style='margin-left:30px;'>成功："+val.data.success+"</span>";
				$(".search-box .imgUploat").append(html);
			}else{
				$.tip(val.message);
			}
			oilcardList.refresh();
		}
    });
	$(".search-box button").click(function(){//根据查询条件查询

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
		var reg01 = /^\d{11}$/;
		if ($.trim($("#phone").val()) !== "") {
			if(!reg01.test($.trim($("#phone").val()))) {
				$.tip("请输入正确格式的手机号");
				return;
			}
		}
		if ($.trim($("#userPhone").val()) !== "") {
			if(!reg01.test($.trim($("#userPhone").val()))) {
				$.tip("请输入正确格式的账户手机号");
				return;
			}
		}

		_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
	});
	$("#add_oilcard").click(function(){
		$('.tables').load('oilcard/oilcard_send.html');
	});
	$("#exportSelectBtn").click(function(){
		var reg = /^\d{9}$/;
		if(!reg.test($.trim($("#startCode").val())) || !reg.test($.trim($("#endCode").val()))) {
			$.tip("请输入9位数字的序列号");
			return ;
		}
		//判断序列号格式正确与否
	    var reg = /^\d{9}$/;
	    if ($.trim($("#startCode").val()) !== "") {
	        if(!reg.test($.trim($("#startCode").val()))) {
	            $.tip("请输入9位数字的开始序列号");
	    		return;
	        }
	    } else {
	        $.tip("请输入开始序列号");
			return;
	    }
	    if ($.trim($("#endCode").val()) !== "") {
	        if(!reg.test($.trim($("#endCode").val()))) {
	            $.tip("请输入9位数字的结束序列号");
	    		return;
	        }
	    } else {
	        $.tip("请输入结束序列号");
			return;
	    }
	    if($.trim($("#startCode").val()) - $.trim($("#endCode").val()) > 0) {
	        $.tip("结束序列号必须大于或等于开始序列号");
			return;
	    }
		window.open('oilCard/exportoilcodeexcel?startCode=' + $("#startCode").val() + "&endCode=" + $("#endCode").val());
	})
});
//打开备注弹出框，并显示原有备注
function orderRemark(orderNo,remark){
	$("#orderOperRemark-orderNo").val(orderNo);
	if(remark!='null' && remark!='undefined'){
		$("#orderOperRemarkContent").val(remark);
	}
	$("#orderOperRemark").show();
}
//提交备注
function submitRemak(){
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/updateRemak",
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
				oilcardList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
//关闭备注弹出框
function closeRemak(){
	$("#orderOperRemark-orderNo").val('');
	$("#orderOperRemarkContent").val('');
	$("#orderOperRemark").hide();
}

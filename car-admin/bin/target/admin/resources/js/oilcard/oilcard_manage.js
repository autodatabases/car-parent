var oilcardList = {};
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
	var data =$.parseForm($(".olicard-form"));
	var postData = $.extend(data,
			{pageNo:_allPages["oilcard_paga"].pageNo,pageSize:_allPages["oilcard_paga"].pageSize});
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/oilBatchList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :postData,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					oilcardList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="11" style="color:#e91e63;">没有油卡数据~~</td></tr>');
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
	var supplierLabel = {
		"0": "北京亿特诺美",
		"1": "上海腾霜",
		"2": "上海腾姆",
		"3": "上海泰奎",
		"4": "上海泰钭"
	}
    $.each(datalist,function(i,e) {
    	if(e.supplier!=null){
    		e.supplierText = supplierLabel[e.supplier];
    	}else{
    		e.supplierText = '--';
    	}
    });
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null || datalist[i][j]==""){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].id+"</td>";
		if (datalist[i].type === 0) {
			html+="<td>油卡</td>";
		} else {
            html+="<td>--</td>";
        }
		html+="<td>"+datalist[i].startNumber+"</td>";
		html+="<td>"+datalist[i].endNumber+"</td>";
		html+="<td>"+datalist[i].count +"</td>";
        html+="<td>"+datalist[i].city +"</td>";
        html+="<td>"+datalist[i].batchNumber +"</td>";
        html+="<td>"+datalist[i].supplierText+"</td>";
        html+="<td>"+datalist[i].buyer +"</td>";
        html+="<td>"+$.formatDate(datalist[i].openTime).slice(0,7) +"</td>";
        html+="<td>"+datalist[i].money +"</td>";
		html+="<td style='cursor:pointer'>"+datalist[i].remark +"</td>";
        // html+="<td>"+$.formatDate(datalist[i].createTime) +"</td>";
		html+="<td>";
		html+='<a href="javascript:void(0)" class="mingxi" index="' + i + '" style="color:#fc9338;">明细&nbsp;</a>';
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338;">备注&nbsp;';
		html+='<p style="display:none">'+datalist[i].remark+'</p></a>';
		html+="</td>";
		html+="</tr>"
	}
	$(".tab-list tbody").append(html);
	$(".tab-list tbody").find("tr").each(function(){
		var remakTem = $.trim($(this).find("td").eq(9).html());
		if(remakTem.length-0>12){
			$(this).find("td").eq(9).html(remakTem.substring(0,12)+"...");
			$(this).find("td").eq(9).attr("title",remakTem);
		}
	});
    oilcardList.operate(datalist);
}
oilcardList.operate = function(datalist) {
    $(".mingxi").click(function(){
        var i = $(this).attr("index");
        addCookie('oilcardXuliehao',datalist[i].startNumber + "," + datalist[i].endNumber);
    	$('.tables').load('oilcard/manage_mingxi.html');
	})
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
oilcardList.initExportSelect = function(){
	var mydate = new Date();
	var year=mydate.getFullYear(); //获取完整的年份(4位,1970-????)
	var month = mydate.getMonth()+1; //获取当前月份(0-11,0代表1月)
    var html="<option value=''>请选择时间</option>";
	for(i=0;i<12;i++){
		var times = "";
		if(month-i<=0){
			if (12 + month - i < 10) {
				times = (year-1)+'-0'+(12 + month - i);
			} else {
				times = (year-1)+'-'+(12 + month - i);
			}

		}else{
			if(month - i < 10) {
				times = year + '-0' + (month - i);
			} else {
				times = year + '-' + (month - i);
			}
		}
		html+="<option value='" + times + "'>" + times + "</option>";
	}
	$("#exportSelect").append(html);

}
$(function() {
	initPaginator("oilcard_paga", oilcardList.dataList,1,localStorage.getItem('globalPageSize'));
	oilcardList.dataList();
    oilcardList.initExportSelect();
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
		_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
	});
});
//提交备注
function submitRemak(){
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/updatebatchremark",
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

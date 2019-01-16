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
		url : DOMIN.MAIN + "/oilCard/oilcardorderlist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["oilcard_paga"].pageNo,
			pageSize:_allPages["oilcard_paga"].pageSize,
			cardNo:getCookie('cardNo')
		},
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
					$(".tab-list tbody").append('<tr><td colspan="6" style="color:#e91e63;">没有订单数据~~</td></tr>');
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
	var statusLabel = {
		"0": "充值成功",
		"1": "充值失败",
		"2": "处理中",
	}
    $.each(datalist,function(i,e) {
    	if(e.status!=null){
    		e.statusText = statusLabel[e.status];
    	}else{
    		e.statusText = '--';
    	}
    });
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].orderNo+"</td>";
		if (datalist[i].orderType == 0) {
			html+='<td>油卡</td>';
		} else if (datalist[i].orderType == 1) {
			html+='<td>话费</td>';
		} else if (datalist[i].orderType == 2) {
			html+='<td>洗车</td>';
		} else if (datalist[i].orderType == 3) {
			html+='<td>代驾</td>';
		}else{
			html+='<td>--</td>';
		}
		html+="<td>"+datalist[i].money +"</td>";
        if(datalist[i].createTime !== '--') {
        	html+="<td>"+$.formatDate(datalist[i].createTime) +"</td>";
        } else {
            html+="<td>--</td>";
        }
       	html+="<td>"+datalist[i].statusText +"</td>";
		html+="</tr>"
	}
	$(".tab-list tbody").append(html);
}
$(function() {
	initPaginator("oilcard_paga", oilcardList.dataList,1,localStorage.getItem('globalPageSize'));
	oilcardList.dataList();

	$("#oilcard_check").click(function(){
		$('.tables').load('oilcard/oilcard_check.html');
	});
});

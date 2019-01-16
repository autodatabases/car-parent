var cai_dot_list = {};
cai_dot_list.refresh = function(){
		cai_dot_list.dataList();
}
var isload = false;
cai_dot_list.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/washproduct/shenglist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo: _allPages["taocan_paga"].pageNo,
			pageSize: _allPages["taocan_paga"].pageSize,
			userPhone: $.trim($("#userPhone").val()),
            orderNo: $.trim($("#orderNo").val()),
            shopName:$.trim($("#shopName").val()),
            orderCode:$.trim($("#orderCode").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
                    taocanNms = returnObj.list.length;
					cai_dot_list.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="7" style="color:red;">没有洗车套餐数据~~</td></tr>');
				}
				// 分页
				setPaginator("taocan_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

cai_dot_list.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
        html+='<tr>';
        html+='<td>'+datalist[i].phone+'</td>';
        html += "<td>" + datalist[i].orderNo + "</td>";
        html += "<td>" + datalist[i].orderCode + "</td>";
        html += "<td>" + datalist[i].shopName + "</td>";
        html += "<td>" + datalist[i].shopAddress + "</td>";
        html += "<td>" + datalist[i].shopPhone + "</td>";
		if(datalist[i].status == 0) {
			html += "<td>处理中</td>";
		} else if(datalist[i].status == 1){
			html += "<td>已完成</td>";
		} else if(datalist[i].status == 2){
			html += "<td>失败</td>";
		}
		html += "<td>" + datalist[i].remark + "</td>";
		if(datalist[i].createTime == '--') {
			html += "<td>--</td>";
		} else {
			html += "<td>" + $.formatDate(datalist[i].createTime) + "</td>";
		}
		if(datalist[i].finishTime == '--') {
			html += "<td>--</td>";
		} else {
			html += "<td>" + $.formatDate(datalist[i].finishTime) + "</td>";
		}
        html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("taocan_paga", cai_dot_list.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("taocan_paga", cai_dot_list.dataList,1,localStorage.getItem('globalPageSize'));
	}
	cai_dot_list.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["taocan_paga"].pageNo = 1;
		cai_dot_list.dataList();
	});
});

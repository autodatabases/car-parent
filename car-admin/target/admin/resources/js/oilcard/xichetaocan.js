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
		url : DOMIN.MAIN + "/washproduct/paylist",
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
            status:$("#orderStatus :selected").attr("value")
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
		html+='<td>'+datalist[i].orderNo+'</td>';
		html+='<td>'+datalist[i].totalPay+'</td>';
        if(datalist[i].accountPay != "--" && datalist[i].actualPay != "--") {
            html += "<td>余额支付(" + datalist[i].accountPay + "元)+微信支付(" + datalist[i].actualPay + "元)</td>";
        } else if(datalist[i].accountPay != "--") {
            html += "<td>余额支付(" + datalist[i].accountPay + "元)</td>";
        } else if(datalist[i].actualPay != "--") {
            html += "<td>微信支付(" + datalist[i].accountPay + "元)</td>";
        }
		html+='<td>'+datalist[i].washNumber+'</td>';
        if(datalist[i].status == 0) {
            html+="<td>待支付</td>";
        } else if(datalist[i].status == 1) {
            html+="<td>支付中</td>"
        } else if(datalist[i].status == 2) {
            html+="<td>支付完成</td>"
        } else if(datalist[i].status == 3) {
            html+="<td>支付失败</td>"
        }
		html+="<td>"+$.formatDate(datalist[i].createTime)+"</td>";
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

//验证码记录
var verification = {};
verification.refresh = function(){
		verification.dataList();
}
var isload = false;
verification.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/authCode/check",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["verification_paga"].pageNo,
			pageSize:_allPages["verification_paga"].pageSize,
			userPhone:$.trim($('#userAccounts').val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			// console.log(data)
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
                    taocanNms = returnObj.list.length;
					verification.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:red;">没有数据~~</td></tr>');
				}
				// 分页
				setPaginator("verification_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

verification.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td>'+datalist[i].id+'</td>'//序号
        html+='<td>'+datalist[i].userphone+'</td>';//手机号
		html+='<td>'+datalist[i].platform+'</td>';  //所属平台
		html+='<td>'+datalist[i].createTime+'</td>';//发送时间
		html+='<td>'+datalist[i].code+'</td>';//验证码		
		html+='<td>'+datalist[i].effectiveTime+'</td>';  //有效时间    
		html+='<td>'+datalist[i].status+'</td>';  //接收状态    
        html+="<td style='cursor:pointer'>"+datalist[i].failureFeedback +"</td>";//失败反馈            
		html+='</tr>';
		
	}
	$(".tab-list tbody").append(html);
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("verification_paga", verification.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("verification_paga", verification.dataList,1,localStorage.getItem('globalPageSize'));
	}
	verification.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["verification_paga"].pageNo = 1;
		verification.dataList();
	});
});

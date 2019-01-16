//验证码记录
var logRecord = {};
logRecord.refresh = function(){
		logRecord.dataList();
}
var isload = false;
logRecord.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oillog/oilloglist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["busines_paga"].pageNo,
			pageSize:_allPages["busines_paga"].pageSize,
			orderNo:$.trim($('#userAcc').val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			console.log(data)
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
                    // taocanNms = returnObj.list.length;
					logRecord.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="6" style="color:red;">没有数据~~</td></tr>');
				}
				// 分页
				setPaginator("busines_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

logRecord.renderHtml = function(datalist){
	var html="";
	console.log(datalist)
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td>'+datalist[i].orderNo+'</td>';
		if(datalist[i].orderType=="0"){
			html+='<td>中石化</td>'
		}else if(datalist[i].orderType=='1'){
			html+='<td>中石油</td>'
		}else if(datalist[i].orderType=='2'){
			html+='<td>话费</td>'
		}else if(datalist[i].orderType=='3'){
			html+='<td>洗车</td>'
		}else if(datalist[i].orderType=='4'){
			html+='<td>代驾</td>'
		}else if(datalist[i].orderType=='5'){
			html+='<td>流量</td>'
		}else if(datalist[i].orderType=='6'){
			html+='<td>卡密</td>'
		}else if(datalist[i].orderType==null){
			html+='<td>未知类型</td>'
		}

		if(datalist[i].interfaceType=='0'){
			html+='<td>下单接口</td>';
		}else if(datalist[i].interfaceType=='1'){
			html+='<td>回调接口</td>';
		}else if(datalist[i].interfaceType=='2'){
			html+='<td>查询接口</td>';
		}
		
		html+='<td>'+datalist[i].requestStr+'</td>';
		html+='<td>'+datalist[i].returnResult+'</td>';
		html+='<td>'+$.formatDate(datalist[i].createTime)+'</td>';
		html+='<td>'+$.formatDate(datalist[i].updateTime)+'</td>';		
		html+='</tr>';
		
	}
	$(".tab-list tbody").append(html);
	//备注超过12个字
	$(".tab-list tbody").find("tr").each(function(){
		var remakTem = $.trim($(this).find("td").eq(3).html());
		// console.log(remakTem)
		if(remakTem.length-0>12){
			$(this).find("td").eq(3).html(remakTem.substring(0,12)+"...");
			$(this).find("td").eq(3).attr("title",remakTem);
		}
		
		var remakTem = $.trim($(this).find("td").eq(4).html());
		// console.log(remakTem)
		if(remakTem.length-0>150){
			$(this).find("td").eq(4).html(remakTem.substring(0,150)+"...");
			$(this).find("td").eq(4).attr("title",remakTem);
		}
	});
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("busines_paga", logRecord.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("busines_paga", logRecord.dataList,1,localStorage.getItem('globalPageSize'));
	}
	logRecord.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["busines_paga"].pageNo = 1;
		logRecord.dataList();
	});
});

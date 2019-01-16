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
		url : DOMIN.MAIN + "/oilCard/querynewchildorder",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["oilcard_paga"].pageNo,
			pageSize:_allPages["oilcard_paga"].pageSize,
			parentOrderNo:getCookie('orderNo'),
			orderType:getCookie('orderType')
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
				if(returnObj.pageInfo){
					setPaginator("oilcard_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
						returnObj.pageInfo.recordCount);
				}
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
	var orderStatusLabel = {
		"0": "待处理",
		"1": "处理中",
		"2": "充值成功",
		"3": "充值失败"
	}
	var  providerLabel = {
		0: "北京亿特诺美",
		1: "上海腾霜",
		2: "上海腾姆",
	    3: "上海泰奎",
		4: "上海泰钭"
	}
    $.each(datalist,function(i,e) {
    	if(e.orderStatus!=null){
    		e.orderStatusText = orderStatusLabel[e.orderStatus];
    	}else{
    		e.orderStatusText = '--';
    	}
    	if(e.provider!=null){
    		e.providerText = providerLabel[e.provider];
    	}else{
    		e.providerText = '北京亿特诺美';  
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
		html+="<td>"+datalist[i].providerText+"</td>";
		var cardMoneyItem ="",
			moneyItem ="",
			supplierItem ="";
		for(var k=0;k<(datalist[i].cardInfo).length;k++){
			var cardInfos = datalist[i].cardInfo;
			$.each(cardInfos,function(c,e) {
				if(e.supplier!=null){
					e.supplierText = providerLabel[e.supplier];
				}else{
					e.supplierText = '北京亿特诺美';   
				}
			});
			cardMoneyItem+="<p>"+datalist[i].cardInfo[k].cardMoney +"</p>";
			moneyItem+="<p>"+datalist[i].cardInfo[k].money +"</p>";
			supplierItem+="<p>"+datalist[i].cardInfo[k].supplierText +"</p>";
		}
		html+="<td>"+supplierItem+"</td>";
		html+="<td>"+moneyItem+"</td>";
		html+="<td>"+cardMoneyItem+"</td>";
        html+="<td>"+datalist[i].orderMoney+"</td>";
       	html+="<td>"+datalist[i].orderStatusText +"</td>";
		html+="</tr>"
	}
	$(".tab-list tbody").append(html);
}
$(function() {
	initPaginator("oilcard_paga", oilcardList.dataList,1,localStorage.getItem('globalPageSize'));
	oilcardList.dataList();

	$("#oilOrder_New").click(function(){
		$('.tables').load('oilcard/oilOrder_New.html');
	});
});

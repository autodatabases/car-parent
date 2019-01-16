var oilOrder = {};
var oilCompType = 2;
var transType = 0;
var o = oilCompType;
var t = transType;
var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
oilOrder.refresh = function(){
	//	_allPages["oilcard_paga"].pageNo = 1;
		oilOrder.dataList();
}
var isload = false;
oilOrder.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	var data =$.parseForm($(".oilcard-new"));
	var postData = $.extend(data,{pageNo:_allPages["oilcard_paga"].pageNo,pageSize:_allPages["oilcard_paga"].pageSize});
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/queryneworder",
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
					oilOrder.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="10" style="color:#e91e63;">没有油卡订单数据~~</td></tr>');
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
oilOrder.renderHtml = function(datalist){
	var chargeTypeLabel = {
		1: "中石化",
		2: "中石油",
		3: "手机充值",
		4: "手机流量",
		5: "猫眼电影",
		6:"滴滴出行",
		7: "优酷",
		8: "爱奇艺",
		9: "腾讯视频",
		10: "搜狐"
	}
	var orderStatusLabel = {
		0: "待处理",
		1: "处理中",
		2: "充值成功",
		3: "部分成功",
		4: "充值失败",
	}
    $.each(datalist,function(i,e) {
    	if(e.orderStatus!=null){
    		e.orderStatusText = orderStatusLabel[e.orderStatus];
    	}else{
    		e.orderStatusText = '--';
    	}
    	if(e.chargeType!=null){
    		e.chargeTypeText = chargeTypeLabel[e.chargeType];
    	}else{
    		e.chargeTypeText = '--';
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
		html+="<td>"+datalist[i].chargeTypeText+"</td>";
		html+="<td>"+datalist[i].orderNo+"</td>";
		html+="<td>"+datalist[i].provider+"</td>";
		html+="<td>"+datalist[i].userPhone+"</td>";
		html+="<td>"+datalist[i].cardNo+"</td>";
		datalist[i].cardSecret? html+="<td>"+datalist[i].cardSecret+"</td>":html+="<td>--</td>";
		html+="<td>"+datalist[i].goodIntroduce+"</td>";
		html+="<td>"+datalist[i].fillMoney+"</td>";
		html+="<td>"+datalist[i].surplusMoney+"</td>";
		//html+="<td>"+datalist[i].userMoney+"</td>";
		html+="<td>"+datalist[i].orderStatusText+"</td>";
		if(datalist[i].createTime !== '--') {
        	html+="<td>"+$.formatDate(datalist[i].createTime) +"</td>";
        } else {
            html+="<td>--</td>";
        }
		html+="<td>";
		if(datalist[i].provider=='--'){
			html+='<a href="javascript:void(0)" class="searchChildOrder" data-orderNo="'+datalist[i].orderNo+'" data-orderType="'+datalist[i].chargeType+'" style="color:#fc9338;">查看&nbsp;</a>';
		}else{
			html+="--";
		}
	    html+="</td>";
	    /*if (datalist[i].remark==1) {
			html+="<td style='color:#fc9338;'>特殊订单</td>";
		}else{
			html+='<td>--</td>';
		}*/
		html+="</tr>";
	}
	$(".tab-list tbody").append(html);
	oilcardList.operate();
}
oilcardList.operate = function() {
    $(".searchChildOrder").on("click",function() {
        var dataNo = $(this).attr("data-orderNo");
         var orderType = $(this).attr("data-orderType");
        addCookie('orderNo',dataNo);
        addCookie('orderType',orderType);
    	$('.tables').load('oilcard/oilOrder_childLink.html');

    })
}
function initExportSelect() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var html = "";
	for (var i=0; i<12; i++) {
		if(month - i <= 0) {
			html += "<option>" + (year - 1) + "-" + (month - i + 12) + "</option>";
		} else {
			html += "<option>" + year + "-" + (month - i) + "</option>";
		}
	}
	$("#eMonth").html(html);
	$("#exportSelectBtn").click(function(){
		window.open('oilCard/exportoilexcelTwo?date='+$("#eMonth").val());
	});

	// 单个账号导出功能
	$("#exportPhoneBtn").click(function(){
		var exportPhone = $("#phone").val();
		if(exportPhone == null || exportPhone == ""){
			$.tip("账户手机号不可为空");
		}else if (!myreg.test(exportPhone)) {  
            $.tip("请输入正确的账户手机号");
        } else {  
            window.open('oilCard/exportOilExcelWithPhone?phone='+exportPhone);
        }
	})
}
$(function() {
	initPaginator("oilcard_paga", oilOrder.dataList,1,localStorage.getItem('globalPageSize'));
	oilOrder.dataList();
	initExportSelect();
	$("#searchBtn").click(function(){//根据查询条件查询
		if(oilCompType === 3) {
			oilCompType = o;
			transType = t;
		}
		_allPages["oilcard_paga"].pageNo = 1;
		oilOrder.dataList();
	});
});

var order = {};
var oilCompType = 2;
var transType = 0;
var o = oilCompType;
var t = transType;
var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
order.refresh = function(){
	//	_allPages["oilcard_paga"].pageNo = 1;
		order.dataList();
}
var isload = false;
order.dataList = function() {
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
					order.renderHtml(returnObj.list);
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
order.renderHtml = function(datalist){
	var html = '';

	$(".tab-list tbody").append(html);

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
	$(".eMonth").html(html);
	$("#exportSelectBtn").click(function(){
		window.open('oilCard/exportoilexcelTwo?date='+$("#eMonth").val());
	});
}
$(function() {
	initPaginator("oilcard_paga", order.dataList,1,localStorage.getItem('globalPageSize'));
	order.dataList();
	initExportSelect();
	$("#searchBtn").click(function(){//根据查询条件查询
		if(oilCompType === 3) {
			oilCompType = o;
			transType = t;
		}
		_allPages["oilcard_paga"].pageNo = 1;
		order.dataList();
	});
});

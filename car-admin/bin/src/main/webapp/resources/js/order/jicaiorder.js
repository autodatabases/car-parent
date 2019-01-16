﻿var jicaiStatus="";
var jicaiorderList = {};
jicaiorderList.refresh = function(){
		_allPages["jicaiorder_paga"].pageNo = 1;
		jicaiorderList.dataList();
}
var isload = false;
jicaiorderList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/jicaiorder/jicaiOrderList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["jicaiorder_paga"].pageNo,
			pageSize:_allPages["jicaiorder_paga"].pageSize,
			orderNo:$(".search-box input").eq(0).val(),
			status:jicaiStatus
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					jicaiorderList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:red;">没有集彩订单数据！</td></tr>');
				}
				// 分页
				setPaginator("jicaiorder_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
jicaiorderList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td>'+datalist[i].orderNo+'</td>';
		html+='<td>'+datalist[i].customerName+'</td>';
		html+='<td>'+datalist[i].customerPhone+'</td>';
		html+='<td>'+datalist[i].goodName+'</td>';
		html+='<td>'+datalist[i].goodNum+'</td>';
		if(datalist[i].status==0)
			html+='<td >未发货</td>';
		else if(datalist[i].status==1)
			html+='<td >已发货</td>';
		html+='<td>'+$.formatDate(datalist[i].createTime)+'</td>';
		if(datalist[i].status==0)
			html+='<td><a href="javascript:void(0)" onclick=audit("'+datalist[i].orderNo+'")>确认发货</a>  <a href="javascript:void(0)" onclick=details("'+datalist[i].orderNo+'");>详情</a></td>';
		else
			html+='<td><a href="javascript:void(0)" onclick=details("'+datalist[i].orderNo+'");>详情</a></td>';
	}
	$(".tab-list tbody").append(html);
}
function details(orderNo_num){//查看详情
	$.ajax({
		url : DOMIN.MAIN + "/jicaiorder/jicaiOrderDetail",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:orderNo_num,
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$(".jicaiorder-tip").show();
				$(".jicaiorder-tip span").html("");
				$(".order-tip li").eq(0).find("span").html(data.data.orderNo);
				$(".order-tip li").eq(1).find("span").html(data.data.customerName);
				$(".order-tip li").eq(2).find("span").html(data.data.customerPhone);
				$(".order-tip li").eq(3).find("span").html(data.data.goodName);
				$(".order-tip li").eq(4).find("span").html(data.data.goodNum);
				if(data.data.status==0)
					$(".order-tip li").eq(5).find("span").html('未发货');
				else if(data.data.status==1)
					$(".order-tip li").eq(5).find("span").html('已发货');
				$(".order-tip li").eq(6).find("span").html($.formatDate(data.data.createTime));
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
};
function audit(num){//弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}

function audit_button(){
	var orderNo=$(".tips-warp .ok_btn").attr("rel");
	$.ajax({
		url : DOMIN.MAIN + "/jicaiorder/updatejicaiOrder",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:orderNo
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("确认成功");
				$(".tips-warp").css("display","none");
				jicaiorderList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
$(function(){
	initPaginator("jicaiorder_paga", jicaiorderList.dataList,1,localStorage.getItem('globalPageSize'));
	$("#order_span span").each(function(i,e){
		$(e).click(function(){
			$(e).addClass("on").siblings("span").removeClass("on");
			jicaiStatus=$(e).attr("rel");
			_allPages["jicaiorder_paga"].pageNo = 1;
			jicaiorderList.dataList();
		});
	});
	$(".order-tip-btn button").click(function(){
		$(".order-tip").hide();
	});
	$(".order-tips-top span").click(function(){
		$(".order-tip").hide();
	});
	$(".search-box button").click(function(){
		_allPages["jicaiorder_paga"].pageNo = 1;
		jicaiorderList.dataList();
	});
	$("#order_span span").eq(1).click();
})

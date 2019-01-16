﻿var orderStatus=1;
var countermanOrdersList = {};
countermanOrdersList.refresh = function(){
	countermanOrdersList.dataList();
}
var isload = false;
countermanOrdersList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/countermanOrders/queryCountermanOrdersList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["order_paga"].pageNo,
			pageSize:_allPages["order_paga"].pageSize,
			countermanPhone:$("#countermanPhone").val(),
			orderNo:$("#orderNo").val(),
			orderStatus:orderStatus,
			startTimes:$("#startTimes").val(),
			endTimes:$("#endTimes").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					countermanOrdersList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="6" style="text-indent:600px;color:red;">没有订单数据！</td></tr>');
				}
				// 分页
				setPaginator("order_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
countermanOrdersList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		if(datalist[i].orderRemark!=null && datalist[i].orderRemark.length>50){
			datalist[i].orderRemarkshort = datalist[i].orderRemark.substr(0,50);
		}else{
			datalist[i].orderRemarkshort = datalist[i].orderRemark;
		}
		html+='<tr>';
		html+='<td class="bh">'+(i+1)+'</td>';
		html+='<td class="ddxq">';
		html+='<p>订单编号：'+datalist[i].orderNo+'</p>';
		html+='<p>订单金额：'+datalist[i].orderPrice+'分</p>';
		var statusStr = "待收货";
		/*if (datalist[i].orderStatus == 0) {
			statusStr = "待审核";
		} else*/ if (datalist[i].orderStatus == 1) {
			statusStr = '待收货';
		} else if (datalist[i].orderStatus == 2) {
			statusStr = '已完成';
		} /*else if (datalist[i].orderStatus == 3) {
			statusStr = '已取消';
		}*/
		html+='<p>订单状态：<span class="order-status">'+statusStr+'</span></p>';
		html+='<p class="create-time">下单时间：'+$.formatDate(datalist[i].orderCreateTime)+'</p>';
		html+='</td>';
		html+='<td class="khxx">';
		html+='<p>业务员姓名：'+datalist[i].countermanName+'</p>';
		html+='<p>业务员代码：'+datalist[i].countermanCode+'</p>';
		html+='<p>业务员电话：'+datalist[i].countermanphone+'</p>';
		html+='</td>';
		html+='<td class="sjxx">'
			var goods = datalist[i].good
			for(var j=0;j<goods.length;j++){
				html+='<p>商品名称：'+goods[j].goodName+'</p>';
				html+='<p>积分：'+goods[j].goodprice+'分</p>';
				html+='<p>商品数量：'+goods[j].goodNumber+'</p>';
			}
		html+='</td>';
		html+='<td class="jsxx" style="width:20%;">';
		html+='<p title='+datalist[i].orderRemark+'>备注：'+(datalist[i].orderRemark==null?'无':datalist[i].orderRemarkshort)+'</p>';
		html+='</td>';
		html+='<td class="cz">';
		//html+='<a href="javascript:void(0)" onclick="orderRemark(\''+datalist[i].orderNo+'\',\''+datalist[i].orderRemark+'\');">备注&nbsp;</a>';
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].orderNo+'">备注&nbsp;';
		html+='<p style="display:none">'+datalist[i].orderRemark+'</p></a>';
/*		if(datalist[i].orderStatus==0){
			html+='<a href="javascript:void(0);" onclick="check(\''+datalist[i].orderNo+'\')">审核&nbsp;</a>';
		}else */if(datalist[i].orderStatus==1){
			html+='<a href="javascript:void(0);" onclick="finish(\''+datalist[i].orderNo+'\')">确认完成&nbsp;</a>';
		}
		html+='</td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	$(".beizhu").click(function(){
		var remark = $(this).children('p').html();
		var orderNo = $(this).attr('ono');;
		$("#orderOperRemark-orderNo").val(orderNo);
		if(remark!='null' && remark!='undefined'){
			$("#orderOperRemarkContent").val(remark);
		}
		$("#orderOperRemark").show();
	})
}
// function orderRemark(orderNo,remark){
// 	$("#orderOperRemark-orderNo").val(orderNo);
// 	if(remark!='null' && remark!='undefined'){
// 		$("#orderOperRemarkContent").val(remark);
// 	}
// 	$("#orderOperRemark").show();
// }
function submitRemak(){
	if($.trim($("#orderOperRemarkContent").val())==''){
		$.tip('订单备注信息为空！');
		return;
	}
	$.ajax({
		url : DOMIN.MAIN + "/countermanOrders/addOrderRemark",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:$("#orderOperRemark-orderNo").val(),
			remark:$("#orderOperRemarkContent").val()
		},
		success : function(data, textStatus) {
			$("#orderOperRemark-orderNo").val('');
			$("#orderOperRemarkContent").val('');
			if (data.success) {
				$.tip("操作成功");
				$("#orderOperRemark").hide();
				countermanOrdersList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
function closeRemak(){
	$("#orderOperRemark-orderNo").val('');
	$("#orderOperRemarkContent").val('');
	$("#orderOperRemark").hide();
}
/*function check(num){//审核弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("请选择是否审核通过");
	$(".tips-warp .ok_btn").attr("rel",num);
}*/
function finish(num){//确认完成弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("请选择是否确认完成");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}

function audit_button(){
	var orderNo=$(".tips-warp .ok_btn").attr("rel");
	var num="";
	var data="";
	var url="";
	var Prompt="";
/*	if($(".tips-top .wenzi").html()=="请选择是否审核通过"){
		data={
			orderNo:orderNo,
			status:1
		}
		url=DOMIN.MAIN +"/countermanOrders/updateOrdersStatus";
		Prompt="审核成功";
	}else*/ if($(".tips-top .wenzi").html()=="请选择是否确认完成"){
		url=DOMIN.MAIN +"/countermanOrders/updateOrdersStatus";
		data={
				orderNo:orderNo,
				status:2
			}
		Prompt="订单已完成";
	}
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :data,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip(Prompt);
				$(".tips-warp").css("display","none");
				countermanOrdersList.refresh();
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
	initPaginator("order_paga", countermanOrdersList.dataList,1,localStorage.getItem('globalPageSize'));
	$("#order_span span").each(function(i,e){
		$(e).click(function(){
			$(e).addClass("on").siblings("span").removeClass("on");
			orderStatus=$(e).attr("rel");
			_allPages["order_paga"].pageNo = 1;
			countermanOrdersList.dataList();
		});
	});
	$(".search-box button").click(function(){
		_allPages["order_paga"].pageNo = 1;
		countermanOrdersList.dataList();
	});
	$("#order_span span").eq(1).click();
})

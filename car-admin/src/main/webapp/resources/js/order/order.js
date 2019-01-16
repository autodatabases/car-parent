﻿var orderStatus=-1;
var orderList = {};
orderList.refresh = function(){
		//_allPages["order_paga"].pageNo = 1;
		orderList.dataList();
}
var isload = false;
orderList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/order/queryOrderList.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["order_paga"].pageNo,
			pageSize:_allPages["order_paga"].pageSize,
			phone:$.trim($("#phone").val()),
			orderNo:$.trim($("#orderNo").val()),
			orderStatus:orderStatus,
			startTimes:$("#startTimes").val(),
			endTimes:$("#endTimes").val(),
			license:$.trim($("#license").val()),
			orderType:$.trim($("#orderType").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					orderList.renderHtml(returnObj.list);
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
orderList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		if(datalist[i].operateRemark!=null && datalist[i].operateRemark.length>50){
			datalist[i].operateRemarkshort = datalist[i].operateRemark.substr(0,50);
		}else{
			datalist[i].operateRemarkshort = datalist[i].operateRemark;
		}
		html+='<tr>';
		html+='<td class="bh">'+(i+1)+'</td>';
		html+='<td class="ddxq">';
		if(datalist[i].orderType==0){
			html+='<strong>电瓶服务</strong>';
		}else if(datalist[i].orderType==1){
			html+='<strong>轮胎服务</strong>';
		}else if(datalist[i].orderType==2){
			html+='<strong>小保养服务</strong>';
		}else if(datalist[i].orderType==3){
			html+='<strong>洗车服务</strong>';
		}else if(datalist[i].orderType==4){
			html+='<strong>喷漆服务</strong>';
		}
		var statusStr = "待审核";
		if (datalist[i].status == 0) {
			statusStr = "待审核";
		} else if (datalist[i].status == 1) {
			statusStr = '待服务';
		} else if (datalist[i].status == 2) {
			statusStr = '服务中';
		} else if (datalist[i].status == 3) {
			statusStr = '已完成';
		} else if (datalist[i].status == 4) {
			statusStr = '已取消';
		} else if (datalist[i].status == 5) {
			statusStr = '已结算';
		}
		html+='  订单状态：<span class="order-status">'+statusStr+'</span>';
		html+='<p class="create-time">下单时间：'+$.formatDate(datalist[i].createtime)+'</p>';
		if(datalist[i].status == 3){
			if(datalist[i].finishtime){
				html+='<p class="finish-time">订单完成时间：'+$.formatDate(datalist[i].finishtime)+'</p>';
			}else{
				html+='<p class="finish-time">订单完成时间:</p>';
			}
		}
		html+='<p>订单编号：'+datalist[i].orderNo+'</p>';
		html+='</td>';
		html+='<td class="khxx">';
		html+='<p>姓名：'+datalist[i].userName+'</p>';
		html+='<p class="user-phone">电话：'+datalist[i].userPhone+'</p>';
		html+='<p>车牌：'+datalist[i].license+'</p>';
		html+='</td>';
		if(datalist[i].orderType==0){
			html+='<td class="cz"><span>无</span></td>';
		}else{
			html+='<td class="sjxx">'
			if(datalist[i].sellerName){
				html+='<p>名称：'+datalist[i].sellerName+'</p>';
			}else{
				html+='<p>名称：</p>';
			}
			if(datalist[i].sellerPhone){
				html+='<p class="seller-phone">联系方式：'+datalist[i].sellerPhone+'</p>';

			}else{
				html+='<p class="seller-phone">联系方式：</p>';
			}
			if(datalist[i].sellerAddress){
				html+='<p>地址：'+datalist[i].sellerAddress+'</p>';
			}else{
				html+='<p>地址：</p>';
			}
			html+='</td>';
		}
		html+='<td class="jsxx" style="width:20%;">';
		html+='<p title='+datalist[i].operateRemark+'>备注：'+(datalist[i].operateRemark==null?'无':datalist[i].operateRemarkshort)+'</p>';
		html+='</td>';
		html+='<td class="cz">';
		html+='<a href="javascript:void(0)" onclick="details(\''+datalist[i].orderNo+'\');">查看&nbsp;</a>';
		html+='<a href="javascript:void(0)" onclick=addToPrint(\''+JSON.stringify(datalist[i])+'\',this);>打印&nbsp;</a>';
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].orderNo+'">备注&nbsp;';
		html+='<p style="display:none">'+datalist[i].operateRemark+'</p></a>';
		if(datalist[i].status==0){
			html+='<a href="javascript:void(0);" onclick="audit(\''+datalist[i].orderNo+'\',\''+datalist[i].sellerName+'\')">审核&nbsp;</a>';
		}
		if(datalist[i].status!=0&&datalist[i].expressStatus==null&&datalist[i].orderType==2){
			html+='<a href="javascript:void(0);" orderNo="'+datalist[i].orderNo+'" onclick="javascript:showArrivalWin(this);">发货&nbsp;</a>';
		}
		if(datalist[i].expressStatus!=null){
			html+='<a id="findExpress" href="javascript:void(0);" orderNo="'+datalist[i].orderNo+'" onclick="javascript:viewExpress(this);">物流信息&nbsp;</a>';
		}
		//if(datalist[i].expressStatus!=null && datalist[i].deliverTime ==null){
			//html+='<a href="javascript:void(0);" expressStatus="'+datalist[i].expressStatus+'" orderNo="'+datalist[i].orderNo+'" mobile="'+datalist[i].userPhone+'" sellerPhone="'+datalist[i].sellerPhone+'" onclick="javascript:arrival(this);">确认到货&nbsp;</a>';
		//}
		if(datalist[i].status == 1 || datalist[i].status == 2){//强制完成订单
			html+='<a  href="javascript:void(0);" orderNo="'+datalist[i].orderNo+'" operate="1" onclick="javascript:makeSure(this);">强制完成&nbsp;</a>';
			// if(new Date().getTime() - datalist[i].createtime > 1000 * 60 * 60 * 24 * 20){//超过20天
			// 	html+='<a  href="javascript:void(0);" orderNo="'+datalist[i].orderNo+'" onclick="javascript:complateOrder(this);">强制完成&nbsp;</a>';
			// }
		}
		if(datalist[i].status == 0 || datalist[i].status == 1 || datalist[i].status == 2){//强制完成订单
			html+='<a  href="javascript:void(0);" orderNo="'+datalist[i].orderNo+'" operate="2" onclick="javascript:makeSure(this);">强制取消&nbsp;</a>';
			// if(new Date().getTime() - datalist[i].createtime > 1000 * 60 * 60 * 24 * 20){//超过20天
			// 	html+='<a  href="javascript:void(0);" orderNo="'+datalist[i].orderNo+'" onclick="javascript:complateOrder(this);">强制完成&nbsp;</a>';
			// }
		}
		html+='</td>';
		html+='</tr>';

	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
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
function makeSure(obj) {
	if($(obj).attr("operate") === "1") {
		$(".tips-warp02 .tips-center p").eq(0).html("确认强制完成订单号为" );
	} else if($(obj).attr("operate") === "2"){
		$(".tips-warp02 .tips-center p").eq(0).html("确认强制取消订单号为");
	}
	$(".tips-warp02 .tips-center p").eq(1).html($(obj).attr("orderNo"));
	$(".sub_btn").attr("operate",$(obj).attr("operate"));
	$(".sub_btn").attr("orderNo",$(obj).attr('orderNo'));
	$(".tips-warp02").css("display","block");
}
function complateOrder(orderNo){
	$.ajax({
		url : DOMIN.MAIN + "/order/completeOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:orderNo
		},
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				$(".tips-warp02").css("display","none");
				orderList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
function cancelOrder(orderNo){
	$.ajax({
		url : DOMIN.MAIN + "/order/cancelOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:orderNo
		},
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				$(".tips-warp02").css("display","none");
				orderList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
// function operatorRemark(orderNo,remark){
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
		url : DOMIN.MAIN + "/order/addOrderRemark",
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
				orderList.refresh();
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

function showArrivalWin(obj){
	window.currentArrivalOrderNo = $(obj).attr("orderNo");
	$("#expressNo").val('');
	$("#logistics").show();
}
function arrivalOrder(){
	var expressId = $("#expressList").val();
	var expressNo = $.trim($("#expressNo").val());
	if(expressNo==''){
		alert('请输入快递单号！');
		return;
	}
	$.ajax({
		url : DOMIN.MAIN + "/order/deliveOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:window.currentArrivalOrderNo,
			expressId:expressId,
			expressNo:expressNo
		},
		success : function(data, textStatus) {
			if (data.success) {
				$("#logistics").hide();
				$.tip("操作成功");
				orderList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
};
var exStatus;
function viewExpress(obj){
	$.ajax({
		url : DOMIN.MAIN + "/order/queryExpressInfo",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:$(obj).attr('orderNo')
		},
		success : function(data, textStatus) {
			if (data.success) {
				data = data.data;
				$("#logisticsInfo").show();
				$("#expressName").html(data.expressName);
				$("#expressNumber").val(data.expressNo);
				$("#orderNo").attr('currentNo',data.orderNo);
				exStatus=data.expressStatus;
				if(data.expressStatus==0)
					$("#expressStatus").html("在途中");
				else if(data.expressStatus==1)
					$("#expressStatus").html("已揽收");
				else if(data.expressStatus==2)
					$("#expressStatus").html("疑难");
				else if(data.expressStatus==3)
					$("#expressStatus").html("已签收");
				else if(data.expressStatus==4)
					$("#expressStatus").html("退签");
				else if(data.expressStatus==5)
					$("#expressStatus").html("同城派送中");
				else if(data.expressStatus==6)
					$("#expressStatus").html("退回");
				else if(data.expressStatus==7){
					$("#expressStatus").html("转单");
				}
				$("#expressInfoList").empty();
				if(data.responseJson==null || data.responseJson==''){
					return;
				}
				var expressInfo = JSON.parse(data.responseJson);
				for(var i in expressInfo){
					var html = '<li>'+
							'<label>'+expressInfo[i].time+'</label>：'+
							'<span>'+expressInfo[i].context+'</span>'+
						'</li>';
					$("#expressInfoList").append(html);
				}
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
//修改快递物流
function saveExpressNumber(){
	if(exStatus==3){
		viewExpress($("#findExpress"))
		return;
	}
	$.ajax({
		url : DOMIN.MAIN + "/order/saveExpressStatus",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:$("#orderNo").attr('currentNo'),
			expressNo:$("#expressNumber").val()
		},
		success : function(data, textStatus) {
			if (data.success) {
				alert("修改运单号码成功");
				viewExpress($("#findExpress"))
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
/**
 * 查询物流信息列表
 * @returns
 */
function queryExpressList(){
	$.ajax({
		url : DOMIN.MAIN + "/order/queryExpressList",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
		},
		success : function(data, textStatus) {
			if (data.success) {
				var html= '';
				for(var i in data.list){
					html  += '<option value="'+data.list[i].id+'">'+data.list[i].expressName+'</option>';
				}
				$("#expressList").append(html);
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
};
function arrival(obj){
	if($(obj).attr("expressStatus")!='3'){
		if(!window.confirm('该快递单号没有签收，是否确认到货')){
			return;
		}
	}
	$.ajax({
		url : DOMIN.MAIN + "/order/deliverComplet",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:$(obj).attr("orderNo"),
			mobile:$(obj).attr("mobile"),
			sellerPhone:$(obj).attr("sellerPhone")
		},
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				orderList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
};
function details(orderNo_num){
	//查看详情
	$(".order-tip span").html("");
	$.ajax({
		url : DOMIN.MAIN + "/order/queryOrderDetail",
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
				$(".order-tip").show();
				if(data.data.status==0)
					$(".order-tip li").eq(0).find("span").html("待审核");
				else if(data.data.status==1)
					$(".order-tip li").eq(0).find("span").html("待服务");
				else if(data.data.status==2)
					$(".order-tip li").eq(0).find("span").html("服务中");
				else if(data.data.status==3)
					$(".order-tip li").eq(0).find("span").html("已完成");
				else if(data.data.status==4)
					$(".order-tip li").eq(0).find("span").html("已取消");
				else if(data.data.status==5)
					$(".order-tip li").eq(0).find("span").html("已结算");

				if(data.data.orderType==0)
					$(".order-tip li").eq(1).find("span").html("电瓶服务");
				else if(data.data.orderType==1)
					$(".order-tip li").eq(1).find("span").html("轮胎服务");
				else if(data.data.orderType==2)
					$(".order-tip li").eq(1).find("span").html("小保养服务");
				else if(data.data.orderType==3)
					$(".order-tip li").eq(1).find("span").html("洗车服务");
				else if(data.data.orderType==4)
					$(".order-tip li").eq(1).find("span").html("喷漆服务");

				var obj = data.data;
				if (obj.orderType == 2) {
					if(obj.product){
						if(obj.product.split(',')[1].lastIndexOf('（') !== -1) {
							obj.product = obj.product.split(',')[0]+","+obj.product.split(',')[1].substr(0,obj.product.split(',')[1].lastIndexOf('（'))+" x 1";
						}
						var jiyous = obj.product.split(',')[0].split('|');
						var jiyou1 = jiyous[0];
						var jiyou1Detail = jiyou1.split('_');
						jiyou1 = jiyou1Detail[0]+" x " + jiyou1Detail[1];
						if(jiyous.length>1){
							var jiyou2 = jiyous[1];
							var jiyou2Detail = jiyou2.split('_');
							jiyou1 =  jiyou1 + " " + jiyou2Detail[0]+" x " + jiyou2Detail[1];
						}
						obj.product = '<i style="font-size:12px;color:blue;">'+jiyou1 + '</i>&nbsp;<i style="font-size:12px;color:blue;">'+obj.product.split(',')[1]+'</i>';
					}
				}
				if (!("carinfo" in data.data)) {
					data.data.carinfo = {};
					data.data.carinfo.chePai = "--";
					data.data.carinfo.autoBrand = "--";
					data.data.carinfo.autoType = "--";
					data.data.carinfo.productYear= "--";
					data.data.carinfo.engineDisp = "--";
				}
				$(".order-tip li").eq(2).find("span").html(data.data.product);
				$(".order-tip li").eq(3).find("span").html(data.data.userName);
				$(".order-tip li").eq(4).find("span").html(data.data.userPhone);
				$(".order-tip li").eq(5).find("span").html(data.data.carinfo.chePai);
				$(".order-tip li").eq(6).find("span").html(data.data.carinfo.autoBrand);
				$(".order-tip li").eq(7).find("span").html(data.data.carinfo.autoType);
				$(".order-tip li").eq(8).find("span").html(data.data.carinfo.productYear);
				$(".order-tip li").eq(9).find("span").html(data.data.carinfo.engineDisp);
				if(data.data.mileage!=null){
					$(".order-tip li").eq(10).find("span").html(data.data.mileage);
				}else{
					$(".order-tip li").eq(10).find("span").html("--");
				}
				if(data.data.carPrice!=null){
					$(".order-tip li").eq(11).find("span").html(data.data.carPrice);
				}else{
					$(".order-tip li").eq(11).find("span").html("--");
				}
				$(".order-tip li").eq(12).find("span").html(data.data.orderNo);
				$(".order-tip li").eq(13).find("span").html($.formatDate(data.data.createTime));
				$(".order-tip li").eq(14).find("span").html("服务包结算");
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
};
function audit(num,sellerName){//弹窗
    $(".tips-warp01").css("display","block");
//	if(sellerName=="比亚迪广州饰和销售服务店"){
//		$(".check_no").prop("checked", true);
//		$(".check_ok").prop("disabled",true);
//		$(".check_no").prop("disabled",true);
//	}else{
//		$(".check_ok").prop("disabled",false);
//		$(".check_no").prop("disabled",false);
//	}
	$(".tips-warp01 .ok_btn").attr("rel",num);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}

function audit_button(){
	var supplierId=$("input[name=check_radio]:checked").val();
	var orderNo=$(".tips-warp01 .ok_btn").attr("rel");
//	if(!supplierId){
//		$.tip("请选择是否是门店代销！！");
//		return;
//	}
	$.ajax({
		url : DOMIN.MAIN + "/order/confirmOrder",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:orderNo,
//			supplierId:supplierId,
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("审核成功");
				$(".tips-warp01").css("display","none");
				orderList.refresh();
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
	initPaginator("order_paga", orderList.dataList,1,localStorage.getItem('globalPageSize'));
	//orderList.dataList();
	$("#order_span span").each(function(i,e){
		$(e).click(function(){
			$(e).addClass("on").siblings("span").removeClass("on");
			orderStatus=$(e).attr("rel");
			if(orderStatus=="3"){
				$("#orderSta").html("订单完成时间:");
			}else{
				$("#orderSta").html("下单时间:");
			}
			_allPages["order_paga"].pageNo = 1;
			orderList.dataList();
			if(i==4 || i==0){
				$("#exportSelect").show();
				$("#exportSelectBtn").show();
			}else{
				$("#exportSelect").hide();
				$("#exportSelectBtn").hide();
			}
			if(i==1){
				$("#exportSelectBtnUnCheck").show();
			}else{
				$("#exportSelectBtnUnCheck").hide();
			}
		});
	});
	$(".order-tip-btn button").click(function(){
		$(".order-tip").hide();
	});
	$(".order-tips-top span").click(function(){
		$(".order-tip").hide();
	});
	$(".search-box button").click(function(){
		_allPages["order_paga"].pageNo = 1;
		orderList.dataList();
	});
	$("#order_span span").eq(1).click();
	initExportSelect();
	queryExpressList();
	$(".tips-bg").on("click",function(){
		$(".order-tip span").html("");
		$(".order-tip").hide("");

	})
	$(".sub_btn").on("click",function() {
		var orderNo = $(this).attr("orderno");
		if($(this).attr("operate") == "1") {
			complateOrder(orderNo);
		} else if($(this).attr("operate") == "2") {
			cancelOrder(orderNo);
		}
	})
})


function initExportSelect(){
	var mydate = new Date();
	var year=mydate.getFullYear(); //获取完整的年份(4位,1970-????)
	var month = mydate.getMonth()+1; //获取当前月份(0-11,0代表1月)
	var html="<option>"+year+'-'+month+"</option>";
	for(i=1;i<6;i++){
		if(month-i<=0){
			html+="<option>"+(year-1)+'-'+(12+month-i)+"</option>";
		}else{
			html+="<option>"+year+'-'+(month-i)+"</option>";
		}
	}
	$("#exportSelect").append(html);
	$("#exportSelectBtn").click(function(){
		var url = 'order/exportChinaLifeExcelTwo?date='+$("#exportSelect").val()+'&status='+orderStatus;
		window.open(url);
	});

	$("#exportSelectBtnUnCheck").click(function(){
		window.open('order/exportUnCheckOrderToExcel');
	});
}

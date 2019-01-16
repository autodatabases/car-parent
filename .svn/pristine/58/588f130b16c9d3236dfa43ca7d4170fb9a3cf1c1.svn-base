﻿var auditStatus="";
var fixorderList = {};
fixorderList.refresh = function(){
		_allPages["fixorder_paga"].pageNo = 1;
		fixorderList.dataList();
}
var isload = false;
fixorderList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/order/fiexOrderList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["fixorder_paga"].pageNo,
			pageSize:_allPages["fixorder_paga"].pageSize,
			orderNo:$(".search-box input").eq(0).val(),
			auditStatus:auditStatus
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					fixorderList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="9" style="color:red;">没有维修订单数据！</td></tr>');
				}
				// 分页
				setPaginator("fixorder_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
fixorderList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td>'+datalist[i].orderNo+'</td>';
		html+='<td>'+datalist[i].businessCode+'</td>';
		html+='<td>'+datalist[i].businessName+'</td>';
		if(datalist[i].status==1)
			html+='<td style="color:red;">待入场</td>';
		else if(datalist[i].status==2)
			html+='<td style="color:red;">待审核</td>';
		else if(datalist[i].status==3)
			html+='<td style="color:red;">审核通过</td>';
		else if(datalist[i].status==4)
			html+='<td style="color:red;">已结案</td>';
		else
			html+='<td style="color:red;">信息不完善</td>';
		html+='<td>'+datalist[i].userName+'</td>';
		html+='<td>'+datalist[i].userPhone+'</td>';
		html+='<td>'+datalist[i].chepai+'</td>';
		html+='<td>'+datalist[i].money+'</td>';
		if(datalist[i].status==2)
			html+='<td><a href="javascript:void(0)" onclick=audit("'+datalist[i].orderNo+'")>审核</a>  <a href="javascript:void(0)" onclick=details("'+datalist[i].orderNo+'");>详情</a></td>';
		else
			html+='<td><a href="javascript:void(0)" onclick=details("'+datalist[i].orderNo+'");>详情</a></td>';
	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
}

function details(orderNo_num){//查看详情
	$.ajax({
		url : DOMIN.MAIN + "/order/fiexOrderDetail",
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
				$(".fixorder-tip").show();
				//$(".fixorder-tip span").html("");
				$(".order-tip li").eq(0).find("span").html(data.data.orderNo);
				$(".order-tip li").eq(1).find("span").html(data.data.businessCode);
				$(".order-tip li").eq(2).find("span").html(data.data.businessName);
				if(data.data.auditStatus==1)
					$(".order-tip li").eq(3).find("span").html('待入场');
				else if(data.data.auditStatus==2)
					$(".order-tip li").eq(3).find("span").html('待审核');
				else if(data.data.auditStatus==3)
					$(".order-tip li").eq(3).find("span").html('审核通过');
				else if(data.data.auditStatus==4)
					$(".order-tip li").eq(3).find("span").html('已结案');
				else
					$(".order-tip li").eq(3).find("span").html('信息不完善');
				$(".order-tip li").eq(4).find("span").html(data.data.userName);
				$(".order-tip li").eq(5).find("span").html(data.data.userPhone);
				$(".order-tip li").eq(6).find("span").html(data.data.chePai);
				$(".order-tip li").eq(7).find("span").html(data.data.carBrand);
				//$(".order-tip li").eq(8).find("span").html(data.data.location);
				//$(".order-tip li").eq(9).find("span").html(data.data.userDesc);
				var userPic=data.data.userPic;
				$(".order-tip li").eq(8).find("span").html("没有图片！");
				if(userPic && userPic !=null && userPic !=''){
					userPic=userPic.split(",");
					var html="";
					for(var i=0;i<userPic.length;i++){
						html+="<img src=\""+userPic[i]+"\"/>";
					}
					$(".order-tip li").eq(8).find("span").html(html);
				}
				$("#fixorderMoney").val(data.data.money);
				$("#sellerRemark").val(data.data.sellerRemark);
				$("#sellerName").html(data.data.sellerName);
				$("#sellerAddress").html(data.data.sellerAddress);
				
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
		url : DOMIN.MAIN + "/order/checkFixOrder",
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
				$.tip("审核成功");
				$(".tips-warp").css("display","none");
				fixorderList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}

function commitFixRemark(){
	$.ajax({
		url : DOMIN.MAIN + "/order/commitFixRemark",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:$(".order-tip li").eq(0).find("span").html(),
			price:$("#fixorderMoney").val(),
			remark:$("#sellerRemark").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功！");
				fixorderList.refresh();
			}else{
				$.tip(data.message);
			}
			$(".order-tip").hide();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}


$(function(){
	initPaginator("fixorder_paga", fixorderList.dataList,1,localStorage.getItem('globalPageSize'));
	$("#order_span span").each(function(i,e){
		$(e).click(function(){
			$(e).addClass("on").siblings("span").removeClass("on");
			auditStatus=$(e).attr("rel");
			_allPages["fixorder_paga"].pageNo = 1;
			fixorderList.dataList();
		});
	});
	$("#fix-order-commit").click(function(){
		commitFixRemark();
	});
	$(".order-tips-top span").click(function(){
		$(".order-tip").hide();
	});
	$(".search-box button").click(function(){
		_allPages["fixorder_paga"].pageNo = 1;
		fixorderList.dataList();
	});
	$("#order_span span").eq(1).click();
})

var businessInfoList = {};
businessInfoList.refresh = function(){
		_allPages["businessInfo_paginator"].pageNo = 1;
		businessInfoList.dataList();
}
var isload = false;
businessInfoList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/businessInfo/queryAll.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["businessInfo_paginator"].pageNo,
			pageSize:_allPages["businessInfo_paginator"].pageSize,
			businessCode:$.trim($("#businessCode").val()),
			businessName:$.trim($("#businessName").val()),
			phone:$.trim($("#phone").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					businessInfoList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="5" style="color:#3f51b5;">没有业务员数据~~</td></tr>');
				}
				// 分页
				setPaginator("businessInfo_paginator",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
						returnObj.pageInfo.recordCount);
			}else{
				isload=false;
				load.remove();
				$.tip("暂无数据");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			isload=false;
			load.remove();
			$.tip(errorThrown);
		}
	});
}
businessInfoList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].businessCode+'</td>';
		html+='<td>'+datalist[i].businessName+'</td>';
		html+='<td>'+datalist[i].phone+'</td>';
		if(datalist[i].status=="0"){
			html+='<td>已绑定</td>';
		}else if(datalist[i].status=="1"){
			html+='<td>未绑定</td>';
		}else{
			html+='<td>--</td>';
		}
		html+='<td>'
		html+='<a href="javascript:void(0)" onclick=deleteBusinessInfo('+datalist[i].id+')>删除</a>';
		html+='</td></tr>';
		
	}
	$(".tab-list tbody").append(html);
}
//点击删除按钮跳出弹窗
function deleteBusinessInfo(num){//删除弹窗
	$(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("是否删除该业务员");
	$(".tips-warp .ok_btn").attr("rel",num);
}
//执行删除操作
function businessInfo_button(){
	var businessInfoId=$(".tips-warp .ok_btn").attr("rel");
	businessInfo_delete(businessInfoId);
}
//关闭弹窗
function businessInfo_close(){
    $(".tips-warp").css("display","none");
}
function businessInfo_delete(id){//删除业务员
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/businessInfo/deleteBusinessInfo.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			businessInfoId:id
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			if (data.success) {
				$(".tips-warp .ok_btn").attr("rel","");
				$(".tips-warp").css("display","none");
				$.tip("删除成功");
				businessInfoList.refresh();
			}else{
				$.tip("删除失败"+data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			isload=false;
			load.remove();
			$.tip(errorThrown);
		}
	});
}
$(function() {
	initPaginator("businessInfo_paginator", businessInfoList.dataList,1,localStorage.getItem('globalPageSize'));
	businessInfoList.dataList();
	//点击新增业务员事件
	$("#add_businessInfo").click(function(){
		$('.tables').load('member/businessInfo-add.html?t=1.0');
	});	
	$(".search-box button").click(function(){
		_allPages["businessInfo_paginator"].pageNo = 1;
		businessInfoList.dataList();
	});
});
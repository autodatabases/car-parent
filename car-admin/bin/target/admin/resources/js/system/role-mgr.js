var rolemessage = {};
rolemessage.refresh = function(){
		_allPages["role_paginator"].pageNo = 1;
		rolemessage.dataList();
}
var isload = false;
rolemessage.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/permission/roleManager.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["role_paginator"].pageNo,
			pageSize:_allPages["role_paginator"].pageSize
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				rolemessage.renderHtml(returnObj.list);
				// 分页
				setPaginator("role_paginator",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
rolemessage.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td class="role-left">'+datalist[i].name+'</td>';
		html+='<td class="role-left">';
		html+='<a href="javascript:void(0)" onclick="edit_role('+datalist[i].id+')">编辑</a>';
		html+='<a href="javascript:void(0)" onclick="delete_role('+datalist[i].id+')">删除</a>';
		html+='</td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
}
$(function() {
	initPaginator("role_paginator", rolemessage.dataList,1,localStorage.getItem('globalPageSize'));
	rolemessage.dataList();
});
function edit_role(num){//编辑
	addCookie("edit_roleID",num);
	$('.tables').load('system/audit-mgr.html');
}
function delete_role(id){//删除
	$.ajax({
		url : DOMIN.MAIN + "/permission/deleteRole.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{roleId:id},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("删除成功！！");
				rolemessage.refresh();
			}else{
				$.tip("删除失败！！"+data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}












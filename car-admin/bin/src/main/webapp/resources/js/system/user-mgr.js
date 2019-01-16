$(function(){
	$("#add_user").click(function(){
		$('.tables').load('system/add-user.html');
	});
	
	
})
var rolemessage = {};
rolemessage.refresh = function(){
		_allPages["user_paginator"].pageNo = 1;
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
		url : DOMIN.MAIN + "/permission/userManager.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["user_paginator"].pageNo,
			pageSize:_allPages["user_paginator"].pageSize
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
				setPaginator("user_paginator",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
		html+='<td>'+datalist[i].userName+'</td>';
		if(datalist[i].lastLoginTime){
			html+='<td>'+$.formatDate(datalist[i].lastLoginTime)+'</td>';
		}else{
			html+='<td>--</td>';
		}
		html+='<td>'+datalist[i].loginTime+'</td>';
		html+='<td>'+datalist[i].creator+'</td>';
		html+='<td>';
		html+='<a href="javascript:void(0)" onclick=usermgr_edit('+datalist[i].creatorId+',"'+datalist[i].userName+'",'+datalist[i].id+')>编辑</a>';
		html+='<a href="javascript:void(0)" onclick=usermgr_delete('+datalist[i].id+')>删除</a>';
		html+='</td></tr>';
		
	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
}
$(function() {
	initPaginator("user_paginator", rolemessage.dataList,1,localStorage.getItem('globalPageSize'));
	rolemessage.dataList();
});
function usermgr_delete(id){//删除用户
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/permission/deleteUser.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			userId:id
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			if (data.success) {
				$.tip("删除成功");
				rolemessage.refresh();
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
function usermgr_edit(id,name,uid){//编辑用户
    addCookie("user_namemagr",name);
	addCookie("role_idmagr",id);
	addCookie("user_idmagr",uid);
	$('.tables').load('system/add-user.html');
}




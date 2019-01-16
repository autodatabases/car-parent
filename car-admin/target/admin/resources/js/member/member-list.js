var sellerName="";
var memberList = {};
memberList.refresh = function(){
		_allPages["member_paga"].pageNo = 1;
		memberList.dataList();
}
var isload = false;
memberList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/user/queryUserList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["member_paga"].pageNo,
			pageSize:_allPages["member_paga"].pageSize,
			name:$("#phone").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					memberList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="9" style="color:#3f51b5;">没有会员数据~~</td></tr>');
				}
				// 分页
				setPaginator("member_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
memberList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td>'+datalist[i].userName+'</td>';
		html+='<td>'+datalist[i].phoneNum+'</td>';
		if(datalist[i].autoBrand){
			html+='<td>'+datalist[i].autoBrand+'</td>';
			html+='<td>'+datalist[i].autoType+'</td>';
			html+='<td>'+datalist[i].productYear+'</td>';
			html+='<td>'+datalist[i].engineDisp+'</td>';
			html+='<td>'+datalist[i].chejia+'</td>';
			html+='<td>'+datalist[i].chepai+'</td>';
		}else{
			html+='<td>--</td>';
			html+='<td>--</td>';
			html+='<td>--</td>';
			html+='<td>--</td>';
			html+='<td>--</td>';
			html+='<td>--</td>';
		}
		html+='<td><a onclick=edit_member("'+datalist[i].id+'")>编辑</a></td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
}
function edit_member(num){//编辑
	addCookie('memberId',num);
	$('.tables').load('member/member-edit.html');
}

$(function() {
	initPaginator("member_paga", memberList.dataList,1,localStorage.getItem('globalPageSize'));
	memberList.dataList();
	$(".search-box button").click(function(){//根据手机号查询
		memberList.refresh();
	});
});


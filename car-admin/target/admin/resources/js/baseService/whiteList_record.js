//恢复额度
function restoreLines(abc){
	$.ajax({
		url : DOMIN.MAIN + "/oilrecordwhitelist/changeOpenQuota",
		type : "post",
		async : true,
		dataType : "json",
		data :{
			id:abc
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			$(".tables").load('baseService/whiteList_record.html');
			// $('.tables').load('fixseller/seller-edit.html?t=111');
			// console.log(data)
			$.tip('恢复额度成功！！！')
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}

//验证码记录
var whiteList = {};
whiteList.refresh = function(){
		whiteList.dataList();
}
var isload = false;
whiteList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilrecordwhitelist/queryOilRecordWhitelist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["busines_paga"].pageNo,
			pageSize:_allPages["busines_paga"].pageSize,
			userPhone:$.trim($('#userAccounts').val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			// console.log(data)
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
                    taocanNms = returnObj.list.length;
					whiteList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="6" style="color:red;">没有数据~~</td></tr>');
				}
				// 分页
				setPaginator("busines_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

whiteList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td>'+datalist[i].id+'</td>'
		html+='<td>'+datalist[i].userAccount+'</td>';
		if(datalist[i].openQuota==999){
			html+='<td class="restore">不限额度</td>';
		}else{
			html+='<td class="restore">'+datalist[i].openQuota+'万</td>';
		}
		
		html+='<td>'+$.formatDate(datalist[i].createTime)+'</td>';
		
		if(datalist[i].remark==''){
			html+="<td style='cursor:pointer'>--</td>";
		}else{
			html+="<td style='cursor:pointer'>"+datalist[i].remark +"</td>";
		}
		html+='<td><a onclick=restoreLines("'+datalist[i].id+'")>恢复额度</a>';
		html+='</tr>';
		
	}
	$(".tab-list tbody").append(html);
	//备注超过12个字
	$(".tab-list tbody").find("tr").each(function(){
		var remakTem = $.trim($(this).find("td").eq(4).html());
		// console.log(remakTem)
		if(remakTem.length-0>12){
			$(this).find("td").eq(4).html(remakTem.substring(0,12)+"...");
			$(this).find("td").eq(4).attr("title",remakTem);
		}
	});
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("busines_paga", whiteList.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("busines_paga", whiteList.dataList,1,localStorage.getItem('globalPageSize'));
	}
	whiteList.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		var phone=$.trim($("#userAccounts").val());
		if(phone==''){
			$.tip('用户账号不可为空！')
			return;
		}
		if (!(/^1[34578]\d{9}$/.test(phone))) {
			$.tip("请输入正确的用户账号！");
			$("#oilcard_button").prop("disabled", false);
			return;
		}
		_allPages["busines_paga"].pageNo = 1;
		whiteList.dataList();
	});
});

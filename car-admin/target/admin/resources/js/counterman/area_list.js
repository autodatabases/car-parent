var area_list = {};
area_list.refresh = function(){
		//_allPages["counterman_paga"].pageNo = 1;
		area_list.dataList();
}
var isload = false;
area_list.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/countermanCareer/queryCountermanCareerList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo: _allPages["counterman_paga"].pageNo,
			pageSize: _allPages["counterman_paga"].pageSize,
			careerName: $.trim($("#careerName").val()),
			careerCode: $.trim($("#careerCode").val()),
			caiDotName: $.trim($("#caiDotName").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					area_list.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="5" style="color:red;">没有财险职场数据~</td></tr>');
				}
				// 分页
				setPaginator("counterman_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

area_list.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].careerName+'</td>';
		html+='<td>'+datalist[i].careerCode+'</td>';
		html+='<td>'+datalist[i].caiDotName+'</td>';
		html+='<td>'+datalist[i].branchCompany+'</td>';
		html+='<td><a onclick=edit_area("'+datalist[i].id+'")>编辑</a>';
		html+='<a href="javascript:void(0)" onclick=deleted("'+datalist[i].id+'");>删除</a>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
function edit_area(num){//编辑
	sessionStorage.setItem("counterman_session",_allPages["counterman_paga"].pageNo);
	addCookie('areaId',num);
	$('.tables').load('counterman/area_edit.html?t=1.0');
}
function deleted(num){//删除弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
	var areaId=$(".tips-warp .ok_btn").attr("rel");
	var url=DOMIN.MAIN +"/countermanCareer/deleteCountermanCareer";
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data : {
			countermanCareerId: areaId
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("删除成功");
				$(".tips-warp").css("display","none");
				area_list.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function add_area(){//新增职场
	$('.tables').load('counterman/area_edit.html?t=1.0');
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("counterman_paga", area_list.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("counterman_paga", area_list.dataList,1,localStorage.getItem('globalPageSize'));
	}
	area_list.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["counterman_paga"].pageNo = 1;
		area_list.dataList();
	});
	$("#add_area").click(function(){
		add_area();
	});
});

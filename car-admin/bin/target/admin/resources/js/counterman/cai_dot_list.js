var cai_dot_list = {};
cai_dot_list.refresh = function(){
		cai_dot_list.dataList();
}
var isload = false;
cai_dot_list.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/countermanCaiDot/queryCountermanCaiDotList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo: _allPages["caiDot_paga"].pageNo,
			pageSize: _allPages["caiDot_paga"].pageSize,
			dotName: $.trim($("#dotName").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					cai_dot_list.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="5" style="color:red;">没有财险网点数据~~</td></tr>');
				}
				// 分页
				setPaginator("caiDot_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

cai_dot_list.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].id+'</td>';
		html+='<td>'+datalist[i].dotName+'</td>';
		html+='<td>'+datalist[i].pinYin+'</td>';
		/*html+='<td>1 : '+datalist[i].ratio+'</td>';*/
		html+="<td>"+$.formatDate(datalist[i].createTime)+"</td>";
		html+="<td>"+$.formatDate(datalist[i].updateTime)+"</td>";
		html+='<td><a href="javascript:void(0)" onclick=edit_caiDot("'+datalist[i].id+'")>编辑</a>';
		html+='<a href="javascript:void(0)" onclick=del_caiDot("'+datalist[i].id+'")>删除</a></td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
var caiDotId = null;
function add_caiDot(){//新增财险网点弹窗
	caiDotId = null;
	$("#dotName1").val("");
	$("#pinYin1").val("");
	$(".tips-warp").css("display","block");
	$("#idIndex").css("display","none");
}
function edit_caiDot(num){//修改财险网点弹窗
    $(".tips-warp").css("display","block");
    $("#idIndex").css("display","block");
    caiDotId=num
    $.ajax({
		url : DOMIN.MAIN + "/countermanCaiDot/getCountermanCaiDot",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			countermanCaiDotId:num
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success){
				$("#id").html(data.data.id);
				$("#dotName1").val(data.data.dotName);
				$("#pinYin1").val(data.data.pinYin);
				$("#pinYin1").attr("disabled", true);
                $("#pinYin1").css("background-color", "#e6e3e3");
				/*$("#scale").val(data.data.ratio);*/
			}else{
				$.tip("查询信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function del_caiDot(num){//修改财险网点弹窗
    $.ajax({
		url : DOMIN.MAIN + "/countermanCaiDot/deleteCountermanCaiDot",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			countermanCaiDotId:num
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success){
				$.tip("删除财险网点成功");
				cai_dot_list.refresh();
			}else{
				$.tip("查询信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
/*	var scale = $.trim($("#scale").val());
	var reg = /^\+?[1-9][0-9]*$/;
	if (!reg.test(scale)) {
		$.tip("请输入正确格式的比例（不能为小数）");
		return;
	}*/
	var url=DOMIN.MAIN +"/countermanCaiDot/addOrUpdateCountermanCaiDot";
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data : {
			id: caiDotId,
			dotName: $.trim($("#dotName1").val()),
			pinYin: $.trim($("#pinYin1").val())
			/*ratio: $.trim($("#scale").val())*/
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				caiDotId = null;
				$("#dotName1").val("");
				$("#pinYin1").val("");
				$.tip("添加或修改财险网点成功");
				$(".tips-warp").css("display","none");
				cai_dot_list.refresh();
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
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("caiDot_paga", cai_dot_list.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("caiDot_paga", cai_dot_list.dataList,1,localStorage.getItem('globalPageSize'));
	}
	cai_dot_list.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["caiDot_paga"].pageNo = 1;
		cai_dot_list.dataList();
	});
	$("#add_caiDot").click(function(){
		add_caiDot();
	});
});

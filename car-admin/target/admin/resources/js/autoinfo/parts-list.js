var partsList = {};
partsList.refresh = function(){
		//_allPages["busines_paga"].pageNo = 1;
		partsList.dataList();
}
var isload = false;
partsList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	var brand = $("#brand").val();
	if($("#partsTypeId").val() == 2){
		brand = "";
	}
	$.ajax({
		url : DOMIN.MAIN + "/autopart/autoPartList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["partsList_paga"].pageNo,
			pageSize:_allPages["partsList_paga"].pageSize,
			partsTypeId:$("#partsTypeId").val(),
			productCode:$.trim($("#productCode").val()),
			brand:brand
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					partsList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="7" style="color:#9c27b0;">没有匹配的车型配件~~</td></tr>');
				}
				// 分页
				setPaginator("partsList_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

partsList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		if(datalist[i].detailName!=null && datalist[i].detailName.length>25){
			datalist[i].detailNameshort = datalist[i].detailName.substr(0,25);
		}else{
			datalist[i].detailNameshort = datalist[i].detailName;
		}
		html+='<tr>';
		//车型id
		html+='<td>'+datalist[i].id+'</td>';
		//配件类型
		html+='<td>'+datalist[i].partsType+'</td>';
		//产品编码
		html+='<td>'+datalist[i].productCode+'</td>';
		//详细名称
		html+='<td title="'+datalist[i].detailName+'">'+datalist[i].detailNameshort+'</td>';
		//价格
		html+='<td>'+datalist[i].salePrice+'</td>';
		//图片
		html+='<td>'+datalist[i].pic+'</td>';
		html+='<td><a onclick="add_autopart();">新增</a>';
		html+='<a href="javascript:void(0)" onclick="deleteParts(\''+datalist[i].id+'\');">删除</a></td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
function deleteParts(id){
	if(!window.confirm('您确认要删除该配件？')){
		return;
	}
	$.ajax({
		url : DOMIN.MAIN + "/autopart/deleteAutoPart",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			autoPartId:id
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("删除成功！");
				partsList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
$(function() {
	initPaginator("partsList_paga", partsList.dataList,1,localStorage.getItem('globalPageSize'));
	partsList.dataList();
	$("#search").click(function(){//根据条件查询
		_allPages["partsList_paga"].pageNo = 1;
		partsList.dataList();
	});
});

function add_autopart(){//
	$('.tables').load('autoinfo/parts_add.html?t=1.0');
}

var productList = {};
productList.refresh = function(){
	productList.dataList();
}
var isload = false;
//获取数据
productList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	var data =$.parseForm($(".productList-form"));
	var postData = $.extend(data,
			{pageNo:_allPages["oilcard_paga"].pageNo,pageSize:_allPages["oilcard_paga"].pageSize});

	$.ajax({
		url : DOMIN.MAIN + "/virtualtopup/queryVirtualTopupList",
		type : "GET",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :postData,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list-send tbody").empty();
				if(returnObj.list.length>0){
					productList.renderHtml(returnObj.list);
				}else{
					$(".tab-list-send tbody").append('<tr><td colspan="9" style="color:#e91e63;">暂无商品数据~~</td></tr>');
				}
				// 分页
				setPaginator("oilcard_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
//数据处理
productList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j] === null || datalist[i][j] === ""){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].productId+"</td>";
		html+="<td>"+datalist[i].typeName+"</td>";
		html+="<td>"+datalist[i].goodsName+"</td>";
		html+="<td>"+datalist[i].sale+"</td>";
		html+="<td>"+datalist[i].status+"</td>";
		if(datalist[i].createTime === '--') {
			html+="<td>--</td>";
		} else {
			html+="<td>"+$.formatDate(datalist[i].createTime)+"</td>";
		}
		if(datalist[i].updateTime === '--') {
			html+="<td>--</td>";
		} else {
			html+="<td>"+$.formatDate(datalist[i].updateTime)+"</td>";
		}
		html+="<td>";
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338">更改信息&nbsp;</a>';
		html+="</td>";
		html+="<td>";
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338">上架&nbsp;</a>';
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338">下架&nbsp;</a>';
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338">库存不足&nbsp;</a>';
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338">系统维护&nbsp;</a>';
		html+="</td>";
		html+="</tr>"
	}
	$(".tab-list-send tbody").append(html);
	$(".beizhu").click(function(){
		var remark = $(this).children('p').html();
		var orderNo = $(this).attr('ono');;
		$("#orderOperRemark-orderNo").val(orderNo);
		if(remark === "--") {
			remark = "";
		}
		if(remark!='null' && remark!='undefined'){
			$("#orderOperRemarkContent").val(remark);
		}
		$("#orderOperRemark").show();
	})
}
$(function() {
	initPaginator("oilcard_paga", productList.dataList,1,localStorage.getItem('globalPageSize'));
	productList.dataList();
    $(".search-box #productList-search").off("click").on("click",function(){//根据查询条件查询
		_allPages["oilcard_paga"].pageNo = 1;
		productList.dataList();
	});

	$("#add_product").off("click").on("click",function(){
		$('.tables').load('product/product-add.html');
	});
    $("#category_Manager").off("click").on("click",function(){
		$('.tables').load('product/categoryManager.html');
	});
});
//打开备注弹出框，并显示原有备注
function orderRemark(orderNo,remark){
	$("#orderOperRemark-orderNo").val(orderNo);
	if(remark!='null' && remark!='undefined'){
		$("#orderOperRemarkContent").val(remark);
	}
	$("#orderOperRemark").show();
}
//提交备注
function submitRemak(){
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/updateRemak",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			id:$("#orderOperRemark-orderNo").val(),
			remark:$("#orderOperRemarkContent").val()
		},
		success : function(data, textStatus) {
			$("#orderOperRemark-orderNo").val('');
			$("#orderOperRemarkContent").val('');
			if (data.success) {
				$.tip("操作成功");
				$("#orderOperRemark").hide();
				productList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
//关闭备注弹出框
function closeRemak(){
	$("#orderOperRemark-orderNo").val('');
	$("#orderOperRemarkContent").val('');
	$("#orderOperRemark").hide();
}

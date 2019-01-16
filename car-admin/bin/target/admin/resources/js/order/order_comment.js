var order_comment = {};
order_comment.refresh = function(){
		order_comment.dataList();
}
var isload = false;
order_comment.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/ordercomment/adminquery",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			sellerName:$.trim($("#sellerName").val()),
			orderNo:$.trim($("#orderNo").val()),
			userPhone:$.trim($("#userphone").val()),
			pageNo: _allPages["comment_paga"].pageNo,
			pageSize: _allPages["comment_paga"].pageSize
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					order_comment.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="6" style="color:red;">没有订单评语~~</td></tr>');
				}
				// 分页
				setPaginator("comment_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

order_comment.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		if(datalist[i].comment!=null && datalist[i].comment.length>20){
			datalist[i].commentshort = datalist[i].comment.substr(0,20)+"...";
		}else{
			datalist[i].commentshort = datalist[i].comment;
		}
		html+='<tr>';
		html+='<td>'+datalist[i].orderNo+'</td>';
		html+='<td>'+datalist[i].sellerPhone+'</td>';
		html+='<td>'+datalist[i].sellerName+'</td>';
		html+='<td>'+datalist[i].sellerStar+'星</td>';
		html+='<td>'+datalist[i].expressStar+'星</td>';
		var info = unescape(datalist[i].comment);
		html+='<td class="comment"><span>'+info+'</span></td>';
		html+="<td style='cursor:pointer'>"+datalist[i].remark+"</td>";
		html+="<td>";
		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338">编辑回访记录&nbsp;';
		html+='<p style="display:none">'+datalist[i].remark+'</p></a>';
		html+="</td>";
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	//备注超过30字截取
	$(".tab-list tbody").find("tr").each(function(){
		var remakTem = $.trim($(this).find("td").eq(6).html());
		if(remakTem.length-0>15){
			$(this).find("td").eq(6).html(remakTem.substring(0,15)+"...");
			$(this).find("td").eq(6).attr("title",remakTem);
		}
	});
	$(".comment").on("click",function() {
		var comment = $(this).children('span').html();
		$("#orderComment").html(comment);
		$(".tips-warp").css("display","block");
	});
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

function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
$(function(){
	var comment_session= sessionStorage.getItem("comment_session");
	if(comment_session){
		initPaginator("comment_paga", order_comment.dataList,comment_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("comment_session");
	}else{
		initPaginator("comment_paga", order_comment.dataList,1,localStorage.getItem('globalPageSize'));
	}
	order_comment.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["comment_paga"].pageNo = 1;
		order_comment.dataList();
	});
});
function submitRemak(){
	$.ajax({
		url : DOMIN.MAIN + "/ordercomment/updateordercomment",
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
				order_comment.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}

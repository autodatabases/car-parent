var feedbackList = {};
feedbackList.refresh = function(){
		_allPages["feedback_paga"].pageNo = 1;
		feedbackList.dataList();
}
var isload = false;
feedbackList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/user/userBackList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["feedback_paga"].pageNo,
			pageSize:_allPages["feedback_paga"].pageSize,
			phone:$.trim($("#phone").val()),
			startTimes:$.trim($("#startTimes").val()),
			endTimes:$.trim($("#endTimes").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					feedbackList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="4" style="color:#3f51b5;">没有反馈数据~~</td></tr>');
				}
				// 分页
				setPaginator("feedback_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
feedbackList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		if(datalist[i].message!=null && datalist[i].message.length>80){
			datalist[i].messageshort = datalist[i].message.substr(0,80);
		}else{
			datalist[i].messageshort = datalist[i].message;
		}
		html+='<tr>';
		html+='<td>'+datalist[i].name+'</td>';
		html+='<td>'+datalist[i].phone+'</td>';
		html+='<td style="width:500px;"title="'+datalist[i].message+'">'+datalist[i].messageshort+'</td>';
		html+='<td>'+$.formatDate(datalist[i].createTime)+'</td>';
		html+='<td style="width:300px;">'+datalist[i].remark+'</td>';
		html+='<td class="beizhu" style="cursor:pointer;" did="'+datalist[i].id+'">备注';
		html+='<p style="display:none">'+datalist[i].remark+'</p></td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	$(".beizhu").click(function(){
		var remark = $(this).children('p').html();
		var bId = $(this).attr('did');;
		if (remark != "--") {
			$("#remark").val(remark);
		} else {
			$("#remark").val("");
		}
		$("#remark").attr('bid', bId);
		$(".tips-warp").show();
	})
}

// function beizhu() {
// 	var remark = $(this).children('p').html();
// 	var bId = $(this).attr('did');;
// 	if (remark != "--") {
// 		$("#remark").val(remark);
// 	} else {
// 		$("#remark").val("");
// 	}
// 	$("#remark").attr('bid', bId);
// 	$(".tips-warp").show();
// }
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_submit() {
	if($.trim($("#remark").val()) == "") {
		$.tip("备注不能为空!");
		return;
	}
	$.ajax({
		url: DOMIN.MAIN + '/user/updatefeedback',
		type: 'POST',
		dataType: 'json',
		data: 	{
			id: $("#remark").attr('bid'),
			remark: $.trim($("#remark").val())
		},
		success: function(data) {
			if(data.success) {
				$(".tips-warp").css("display","none");
				$.tip("添加备注成功");
				feedbackList.dataList();
			} else {
				$.tip(data.message);
			}
		},
		error: function() {
			$.tip("服务器错误");
		}
	})
}
$(function() {
	initPaginator("feedback_paga", feedbackList.dataList,1,localStorage.getItem('globalPageSize'));
	feedbackList.dataList();
	$(".search-box button").click(function(){//根据手机号查询
		feedbackList.refresh();
	});
});

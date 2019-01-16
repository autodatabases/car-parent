var task = {};
task.refresh = function(){
		_allPages["task_paginator"].pageNo = 1;
		task.dataList();
}
task.dataList = function() {
	var load = $.loading();
	$.ajax({
		url : DOMIN.MAIN + "/taskinterface/tasklist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["task_paginator"].pageNo,
			pageSize:_allPages["task_paginator"].pageSize,
			title:$("#title").val(),
			status:$("#status").val(),
			catalog:$("#catalog").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			load.remove();
			var returnObj = data.returnObj;
			if (data.statusCode == 200) {
				$("#taskgrid").empty();
				task.renderHtml(returnObj.result);
				// 分页
				setPaginator("task_paginator",returnObj.pageNo, returnObj.totalPages,
						returnObj.totalCount);
			} else {
				$.tip("查询失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			load.remove();
			$.tip(errorThrown);
		}
	});
}
task.renderHtml = function(datalist){
	$.each(datalist,function(index,value){
		var operHtml = '';
		var dataS = new Date(value.startTime);
		var dataE = new Date(value.endTime);
		var dataNow = new Date();
		//考试时间未到 
		if(dataNow.getTime() < dataS.getTime()){
			operHtml = '<a class="modity" sid="'
				+value.taskId+'">修改</a><a onclick="task.deleteQuestion('
				+value.taskId+');">删除</a>'
		
		//已经结束 可以修改 或者删除 查看结果
		}else if(dataNow.getTime() > dataE.getTime()){
			operHtml = '<a class="modity" sid="'
				+value.taskId+'">修改</a><a onclick="task.deleteQuestion(\''
				+value.taskId+'\');">删除</a> <a onclick="task.viewExamination(\''
				+value.taskId+'\',\''+value.taskName+'\');">考试结果</a>'
		}else{
			operHtml = '<a onclick="task.viewExamination(\''
				+value.taskId+'\',\''+value.taskName+'\');">考试结果</a>'
		}
		//$.truncLongText(value);
		var html = 	'<tr>'+
				'<td>'+value.taskId+'</td>'+
				'<td>'+value.deptName+'</td>'+
				'<td>'+value.taskName+'</td>'+
				'<td>'+value.questionCount+'</td>'+
				'<td>'+value.startTime+'</td>'+
				'<td>'+value.endTime+'</td>'+
				'<td>'+value.costTime+'</td>'+
				'<td>'+operHtml+'</td>'+
			'</tr>';
		$("#taskgrid").append(html);
	});
	$.addTitle('taskgrid');
}
$(function() {
	initPaginator("task_paginator", task.dataList,1,4);
	task.dataList();
});
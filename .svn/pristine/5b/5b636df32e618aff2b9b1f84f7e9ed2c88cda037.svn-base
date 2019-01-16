var importCountemanList = {};
importCountemanList.refresh = function(){
		_allPages["importCounterman_paga"].pageNo = 1;
		importCountemanList.dataList();
}
var isload = false;
importCountemanList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/counterman/queryCountermanInfoList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["importCounterman_paga"].pageNo,
			pageSize:_allPages["importCounterman_paga"].pageSize,
			policyNumber:$.trim($("#policyNumber").val()),
			countermanName:$.trim($("#countermanName").val()),
			careerName:$.trim($("#careerName").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					importCountemanList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="9" style="color:red;">没有导入保单~~</td></tr>');
				}
				// 分页
				setPaginator("importCounterman_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
importCountemanList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].policyNumber+"</td>";
		html+="<td>"+$.formatDate(datalist[i].paymentTime).split(' ')[0]+"</td>";
		html+="<td>"+datalist[i].orderPrice+"</td>";
		if(datalist[i].serviceType == 0) {
			html+="<td>续保</td>";
		} else if(datalist[i].serviceType == 1) {
			html+="<td>新保单</td>";
		} else if(datalist[i].serviceType == 2){
			html+="<td>转保</td>";
		}else if(datalist[i].serviceType == 3){
			html+="<td>未知</td>";
		}else{
			html+="<td>--</td>";
		}
		if(datalist[i].orderType == 0) {
			html+="<td>国寿天财出单</td>";
		} else if(datalist[i].orderType == 1) {
			html+="<td>人工出单</td>";
		}else {
			html+="<td>--</td>";
		}
		html+="<td>"+datalist[i].countermanName+"</td>";
/*		html+="<td>"+datalist[i].countermanCode+"</td>";
		html+="<td>"+datalist[i].careerCode+"</td>";*/
		html+="<td>"+datalist[i].careerName+"</td>";
		html+="<td>"+$.formatDate(datalist[i].createTime)+"</td>";
		html+="<td>"+$.formatDate(datalist[i].updateTime)+"</td>";
		html+="</tr>"
	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
}
$(function() {
	initPaginator("importCounterman_paga", importCountemanList.dataList,1,localStorage.getItem('globalPageSize'));
	importCountemanList.dataList();
	$("#export").click(function(){
		$(".search-box .success_span").remove();
		if($("#uploadfile").val()!=""){
			$("#importCounterman").submit();
		}else{
			$.tip('请选择导入文件！');
		}
	});
	$("#import-counterman-result").load(function(){
		if(document.getElementById("import-counterman-result").contentWindow.document.body.innerText!=""){
			var val = eval("(" + document.getElementById("import-counterman-result").contentWindow.document.body.innerText + ")");
			if(val.success){
				var html="<span class='success_span' style='margin-left:30px;'>成功："+val.data.success+"</span>";
				$(".search-box .imgUploat").append(html);
			}else{
				$.tip(val.message);
			}
			importCountemanList.refresh();
		}
    });

	$("#search").on("click",function(){
		_allPages["importCounterman_paga"].pageNo = 1;
		importCountemanList.dataList();
	});
/*	$("#match").on("click",function(){
		if(!window.confirm('您确认要验证数据，可能耗时比较长？')){
			return;
		}
		$.ajax({
			url : DOMIN.MAIN +"/user/getMatchChe",
			type : "post",
			processData: true,
			cache : false,
			async : true,
			dataType : "json",
			traditional : true,// 使用传统方式序列化
			success : function(data, textStatus) {
				if (data.success) {
					importCountemanList.refresh();
					$.tip("验证已发送,请稍等!!");
				}else{
					$.tip("验证发送失败");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.tip(errorThrown);
			}
		});
	});*/
});

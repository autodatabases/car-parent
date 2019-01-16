var zhizuoyouka = {};
zhizuoyouka.refresh = function(){
	//	_allPages["zhizuo_paga"].pageNo = 1;
		zhizuoyouka.dataList();
}
var isload = false;
zhizuoyouka.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilmake/queryallrecord",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["zhizuo_paga"].pageNo,
			pageSize:_allPages["zhizuo_paga"].pageSize
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					zhizuoyouka.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:#e91e63;">没有油卡数据~~</td></tr>');
				}
				// 分页
				setPaginator("zhizuo_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
zhizuoyouka.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j] === null || datalist[i][j] === ""){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].id+"</td>";
		html+="<td>"+datalist[i].money+"</td>";
		html+="<td>"+datalist[i].num+"</td>";
		if(datalist[i].status === 0) {
            html+="<td>待处理</td>";
        } else if(datalist[i].status === 1) {
            html+="<td>处理中</td>";
        } else if(datalist[i].status === 2) {
            html+="<td>已处理</td>";
        } else {
            html+="<td>"+datalist[i].status+"</td>";
        }
        html+="<td>"+$.formatDate(datalist[i].createTime) +"</td>";
        html+="<td>"+$.formatDate(datalist[i].deadTime).slice(0,10) +"</td>";
		html+="<td style='cursor:pointer'>"+datalist[i].remark +"</td>";
		html+="<td>";
		html+='<a href="javascript:void(0)" class="chakan" recordId="'+datalist[i].id+'" style="color:#fc9338;">查看&nbsp;';
		html+="</td>";
		html+="</tr>"
	}
	$(".tab-list tbody").append(html);
	$(".tab-list tbody").find("tr").each(function(){
		var remakTem = $.trim($(this).find("td").eq(6).html());
		if(remakTem.length-0>12){
			$(this).find("td").eq(6).html(remakTem.substring(0,12)+"...");
			$(this).find("td").eq(6).attr("title",remakTem);
		}
	});
    zhizuoyouka.operate(datalist);
}
zhizuoyouka.operate = function(datalist) {
	$(".chakan").click(function(){
		sessionStorage.setItem("recordId", $(this).attr("recordId"));
		$(".tables").load('oilcard/zhizuoyouka/zhizuo_detail.html');
	})
}
$(function() {
	initPaginator("zhizuo_paga", zhizuoyouka.dataList,1,localStorage.getItem('globalPageSize'));
	zhizuoyouka.dataList();
    $("#zhizuoyouka").on("click",function() {
        $(".tables").load('oilcard/zhizuoyouka/zhizuo_operate.html');
    })
});

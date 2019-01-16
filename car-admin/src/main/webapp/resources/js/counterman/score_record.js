var score_list = {};
var countermanId = getCookie('countermanId');
// delCookie('countermanId');
score_list.refresh = function(){
		_allPages["counterman_paga"].pageNo = 1;
		score_list.dataList();
}
var isload = false;
score_list.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/counterman/queryscorerecord",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo: _allPages["counterman_paga"].pageNo,
			pageSize: _allPages["counterman_paga"].pageSize,
            id: countermanId
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list){
					score_list.renderHtml(data);
				}else{
					$.tip("暂无数据!!");
					return;
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

score_list.renderHtml = function(data){
	var html="";
    var pointType = "";
    var operator = "";
	for(var i=0;i<data.list.length;i++){
		for(var j in data.list[i]){
			if(data.list[i][j]==null){
				data.list[i][j] = '--';
			}
		}
        switch (data.list[i].operationType) {
            case 1:
                if (data.list[i].score >= 0) {
                    pointType = "积分赠送";
                    operator = "+";
                } else {
                    pointType = "积分扣除";
                    operator = "";
                }
                break;
            case 2:
                pointType = "积分兑换";
                operator = "";
                break;
            default:
                break;
        }
		html+='<tr>';
		html+='<td>'+ $.formatDate(data.list[i].createTime) +'</td>';
		html+='<td>'+ pointType +'</td>';
		html+='<td>' + operator + data.list[i].score +'</td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	$(".name").html(data.list[0].countermanName);
	$(".totalScore").html(data.list[0].countermanScore);
}

$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("counterman_paga", score_list.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("counterman_paga", score_list.dataList,1,localStorage.getItem('globalPageSize'));
	}
	_allPages["counterman_paga"].pageNo = 1;
	score_list.dataList();
});

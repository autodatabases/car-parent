var score_list = {};
score_list.refresh = function(){
		//_allPages["counterman_paga"].pageNo = 1;
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
		url : DOMIN.MAIN + "/countermanScore/queryCountermanScoreList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			countermanName:$.trim($("#countermanName").val()),
			countermanCode:$.trim($("#countermanCode").val()),
			pageNo: _allPages["counterman_paga"].pageNo,
			pageSize: _allPages["counterman_paga"].pageSize
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					score_list.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="9" style="color:red;">没有积分数据~~</td></tr>');
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

score_list.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].countermanName+'</td>';
		html+='<td>'+datalist[i].countermanCode+'</td>';
		html+='<td>'+datalist[i].belongArea+'</td>';
		html+='<td>'+datalist[i].changScore+'</td>';
		html+='<td>'+datalist[i].caiScore+'</td>';
		html+='<td>'+datalist[i].score+'</td>';
		html+='<td>'+datalist[i].number+'</td>';
		html+='<td>'+datalist[i].totalPrice+'</td>';
		html+='<td><a href="javascript:void(0)" onclick=edit_score("'+datalist[i].id+'")>编辑</a>';
		html+='<a href="javascript:void(0)" onclick=detail_score("'+datalist[i].countermanCode+'")>明细</a></td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
var scoreId = 0;
function edit_score(num){//删除弹窗
    $(".tips-warp").css("display","block");
    scoreId = num;
    $.ajax({
		url : DOMIN.MAIN + "/countermanScore/getCountermanScore",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			countermanScoreId:scoreId
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$("#name").html(data.data.countermanName);
				$("#code").html(data.data.countermanCode);
				$("#area").html(data.data.belongArea);
				$("#score").val(data.data.score);
				$("#remark").val("");
			}else{
				$.tip("查询信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function detail_score(code){//积分明细
	//$.tip("敬请期待")
	addCookie('countermanCode',code);
	$('.tables').load('counterman/score_record.html?t=1.0');
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
	var score = $("#score").val();
	if (!(/^(0|[1-9]\d*)$/.test(score))) {
		$.tip("积分数字的格式不对,请正确填写！");
		return;
	}
	var url=DOMIN.MAIN +"/countermanScore/addOrUpdateCountermanScore";
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data : {
			id: scoreId,
			score: $.trim($("#score").val()),
			remark:$.trim($("#remark").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("修改积分成功");
				$(".tips-warp").css("display","none");
				score_list.refresh();
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
		initPaginator("counterman_paga", score_list.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("counterman_paga", score_list.dataList,1,localStorage.getItem('globalPageSize'));
	}
	score_list.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["counterman_paga"].pageNo = 1;
		score_list.dataList();
	});
	$("#score").on('input', function() {
        var score = $(this).val();
        if (!(/^(0|[1-9]\d*)$/.test(score))) {
            $.tip("积分数字的格式不对,请正确填写！");
            return;
        }
        if (score.length >= 10) {
            $.tip("你输入的积分数字过大！");
            score = score.substring(0,10);
            $(this).val(score);
            return;
        }
    })
});

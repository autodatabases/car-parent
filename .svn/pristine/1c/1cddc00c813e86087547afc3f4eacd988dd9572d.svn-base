var countermanList = {};
countermanList.refresh = function(){
		//_allPages["counterman_paga"].pageNo = 1;
		countermanList.dataList();
}
var isload = false;
countermanList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/counterman/queryCountermanList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["counterman_paga"].pageNo,
			pageSize:_allPages["counterman_paga"].pageSize,
			name:$.trim($("#name").val()),
			phone:$.trim($("#phone").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					countermanList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:red;">没有业务员数据~~</td></tr>');
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

countermanList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].name+'</td>';
		html+='<td>'+datalist[i].phone+'</td>';
		html+='<td>'+datalist[i].countermanCode+'</td>';
		html+='<td>'+datalist[i].score+'</td>';
/*		html+='<td>'+datalist[i].careerCode+'</td>';
		html+='<td>'+datalist[i].careerName+'</td>';*/
		html+='<td>'+datalist[i].caiDotName+'</td>';
		if(datalist[i].status==0)
			html+='<td>在职</td>';
		else if(datalist[i].status==1)
			html+='<td>离职</td>';
	    /*if(datalist[i].status==2)
			html+='<td>其他</td>';*/
		if(datalist[i].status==0){
			html+='<td><a onclick=edit_counterman("'+datalist[i].id+'")>编辑</a>';
			html+='<a href="javascript:void(0)" onclick=disable("'+datalist[i].id+'");>冻结</a>';
			html+='<a href="javascript:void(0)" onclick=record("'+datalist[i].id+'");>积分记录</a>';
	    }/*else if(datalist[i].status==1){
			html+='<td><a onclick=edit_counterman("'+datalist[i].id+'")>编辑</a>';
		    html+='<a href="javascript:void(0)" onclick=enable("'+datalist[i].id+'");>启用</a>';
		}*/else{
			html+='<td><a onclick=edit_counterman("'+datalist[i].id+'")>编辑</a>';
			html+='<a href="javascript:void(0)" onclick=record("'+datalist[i].id+'");>积分记录</a>';
		}
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
function edit_counterman(num){//编辑
	sessionStorage.setItem("counterman_session",_allPages["counterman_paga"].pageNo);
	addCookie('countermanId',num);
	$('.tables').load('counterman/counterman-edit.html?t=1.3');
}
function disable(num){//停用弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("请选择是否冻结该业务员");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function record(num){//停用弹窗
	sessionStorage.setItem("counterman_session",_allPages["counterman_paga"].pageNo);
	addCookie('countermanId',num);
	$('.tables').load('counterman/score_record.html');
}
/*function enable(num){//起用弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("请选择是否启用该业务员");
	$(".tips-warp .ok_btn").attr("rel",num);
}*/
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
	var countermanId=$(".tips-warp .ok_btn").attr("rel");
	var num="";
	var data="";
	var url=DOMIN.MAIN +"/counterman/updateCountermanStatus";
	var Prompt="";
	if($(".tips-top .wenzi").html()=="请选择是否冻结该业务员"){
		data={
			countermanId:countermanId,
			status:1
		}
		Prompt="冻结成功";
	}/*else if($(".tips-top .wenzi").html()=="请选择是否启用该业务员"){
		data={
			countermanId:countermanId,
			status:0
		}
		Prompt="启用成功";
	}*/
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :data,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip(Prompt);
				$(".tips-warp").css("display","none");
				countermanList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function add_counterman(){//新增业务员
	$('.tables').load('counterman/counterman-edit.html?t=1.3');
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("counterman_paga", countermanList.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("counterman_paga", countermanList.dataList,1,localStorage.getItem('globalPageSize'));
	}
	countermanList.dataList();
	$(".search-box button").click(function(){//根据查询条件查询
		_allPages["counterman_paga"].pageNo = 1;
		countermanList.dataList();
	});
	$("#add_counterman").click(function(){
		add_counterman();
	});
	$("#batch_increase").click(function(){
		addCookie('countermanId',-100);
		$('.tables').load('counterman/counterman-edit.html?t=1.3');
	});
	$("#batch_edit").click(function(){
		addCookie('countermanId',-200);
		$('.tables').load('counterman/counterman-edit.html?t=1.3');
	});
	$(".export-btn").click(function(){
		window.open("counterman/exportCountermanExcel");
	});
});

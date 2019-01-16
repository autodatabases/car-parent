var countermanGoodList = {};
countermanGoodList.refresh = function(){
		//_allPages["countermanGood_paga"].pageNo = 1;
		countermanGoodList.dataList();
}
var isload = false;
countermanGoodList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/countermanGood/queryCountermanGoodList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["countermanGood_paga"].pageNo,
			pageSize:_allPages["countermanGood_paga"].pageSize,
			goodName:$("#goodName").val(),
			goodStatus:$("#goodStatus :selected").attr("value")
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					countermanGoodList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="7" style="color:red;">没有商品数据~~</td></tr>');
				}
				// 分页
				setPaginator("countermanGood_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

countermanGoodList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].goodName+'</td>';
		html+='<td>'+datalist[i].goodPrice+'</td>';
		html+='<td>'+datalist[i].goodScore+'</td>';
		html+='<td>'+datalist[i].guoPrice+'</td>';
		html+='<td>'+datalist[i].goodNumber+'</td>';
		html+='<td>'+datalist[i].caiDotName+'</td>';
		if(datalist[i].goodStatus==0){
			html+='<td>上架</td>';
		}else if(datalist[i].goodStatus==1){
			html+='<td>下架</td>';
		}else{
			html+='<td>其他</td>';
		}
		if(datalist[i].goodStatus==0){
			html+='<td><a onclick=edit_countermanGood("'+datalist[i].id+'")>编辑</a>';
			html+='<a href="javascript:void(0)" onclick=disable("'+datalist[i].id+'");>下架</a>';
	    }else if(datalist[i].goodStatus==1){
			html+='<td><a onclick=edit_countermanGood("'+datalist[i].id+'")>编辑</a>';
		    html+='<a href="javascript:void(0)" onclick=enable("'+datalist[i].id+'");>上架</a>';
		}else{
			html+='<td><a onclick=edit_countermanGood("'+datalist[i].id+'")>编辑</a>';
		}
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
function edit_countermanGood(num){//编辑
	sessionStorage.setItem("countermanGood_session",_allPages["countermanGood_paga"].pageNo);
	addCookie('countermanGoodId',num);
	$('.tables').load('counterman/good/good_edit.html?t=1.1');
}
function disable(num){//停用弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("请选择是否下架该商品");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function enable(num){//起用弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("请选择是否上架该商品");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
	var countermanGoodId=$(".tips-warp .ok_btn").attr("rel");
	var num="";
	var data="";
	var url=DOMIN.MAIN +"/countermanGood/updateCountermanGoodStatus";
	var Prompt="";
	if($(".tips-top .wenzi").html()=="请选择是否下架该商品"){
		data={
			countermanGoodId:countermanGoodId,
			goodStatus:1
		}
		Prompt="下架成功";
	}else if($(".tips-top .wenzi").html()=="请选择是否上架该商品"){
		data={
			countermanGoodId:countermanGoodId,
			goodStatus:0
		}
		Prompt="上架成功";
	}
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
				countermanGoodList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function add_countermanGood(){//新增业务员
	$('.tables').load('counterman/good/good_edit.html?t=1.1');
}
function getPoints() {
    $.ajax({
        url: DOMIN.MAIN + "/countermanCaiDot/queryCaiDotList",
        type: "post",
        processData: true,
        cache: false,
        async: false,
        dataType: "json",
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
								var html = "<option value='0'>请选择网点</option>";
								for(var i=0; i<data.list.length; i++) {
									html += "<option value='" + data.list[i].id + "'>" + data.list[i].dotName + "</option>";
								}
            		$("#points").html(html);
            } else {
                $.tip("查询信息失败！");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip("连接服务器失败！");
        }
    });
}
$(function(){
	getPoints();
	var countermanGood_session= sessionStorage.getItem("countermanGood_session");
	if(countermanGood_session){
		initPaginator("countermanGood_paga", countermanGoodList.dataList,countermanGood_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("countermanGood_session");
	}else{
		initPaginator("countermanGood_paga", countermanGoodList.dataList,1,localStorage.getItem('globalPageSize'));
	}
	countermanGoodList.dataList();
	$(".search-box button").click(function(){//根据查询条件查询
		_allPages["countermanGood_paga"].pageNo = 1;
		countermanGoodList.dataList();
	});
	$("#add_countermanGood").click(function(){
		add_countermanGood();
	});
	$("#batch_increase").click(function(){
		addCookie('countermanGoodId',-100);
		$('.tables').load('counterman/good/good_edit.html?t=1.1');
	});
});

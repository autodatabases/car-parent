var autoList = {};
autoList.refresh = function(){
		//_allPages["busines_paga"].pageNo = 1;
		autoList.dataList();
}
var isload = false;
autoList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/autoinfo/queryAutoInfoList.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["autoInfo_paga"].pageNo,
			pageSize:_allPages["autoInfo_paga"].pageSize,
			brand:$.trim($("#brand").val()),
			autolineName:$.trim($("#autolineName").val()),
			productTime:$.trim($("#productTime").val()),
			engineDisp:$.trim($("#engineDisp").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					autoList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="9" style="color:#9c27b0;">没有车型了~~</td></tr>');
				}
				// 分页
				setPaginator("autoInfo_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

autoList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		if(datalist[i].jilv!=null && datalist[i].jilv.length>10){
			datalist[i].jilvshort = datalist[i].jilv.substr(0,10);
		}else{
			datalist[i].jilvshort = datalist[i].jilv;
		}
		if(datalist[i].jiyou!=null && datalist[i].jiyou.length>10){
			datalist[i].jiyoushort = datalist[i].jiyou.substr(0,10);
		}else{
			datalist[i].jiyoushort = datalist[i].jiyou;
		}
		html+='<tr>';
		//车型id
		html+='<td>'+datalist[i].id+'</td>';
		//车型品牌
		html+='<td>'+datalist[i].brand+'</td>';
		//车系
		html+='<td>'+datalist[i].autolineName+'</td>';
		//生产年份
		html+='<td>'+datalist[i].productTime+'</td>';
		//排量
		html+='<td>'+datalist[i].engineDisp+'</td>';
		//机油用量
		html+='<td>'+datalist[i].oilAmount+'</td>';
		//机滤Id
		html+='<td title="'+datalist[i].jilv+'">'+datalist[i].jilvshort+'</td>';
		//机油Id
		html+='<td title="'+datalist[i].jiyou+'">'+datalist[i].jiyoushort+'</td>';
		html+='<td><a onclick="adit_autoInfo(\''+datalist[i].id+'\');">编辑</a>';
		html+='<a href="javascript:void(0)" onclick="deleteAuto(\''+datalist[i].id+'\');">删除</a>';
		html+='<a href="javascript:void(0)" onclick="relation(\''+datalist[i].id+'\');">关联</a></td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
function add_autoInfo(){//新增
	$('.tables').load('autoinfo/autoinfo_add.html?t=1.0');
}

function addAuto(){//添加车型
	//处理返回的时候要带参数
	sessionStorage.setItem('autoinfo.edit.brand',$.trim($("#brand").val()));
	sessionStorage.setItem('autoinfo.edit.autolineName',$.trim($("#autolineName").val()));
	sessionStorage.setItem('autoinfo.edit.productTime',$.trim($("#productTime").val()));
	sessionStorage.setItem('autoinfo.edit.engineDisp',$.trim($("#engineDisp").val()));
	$('.tables').load('autoinfo/autoinfo_add.html?t=1.0');
}
function adit_autoInfo(num){//编辑
	sessionStorage.setItem("autoInfo_session",_allPages["autoInfo_paga"].pageNo);
	addCookie('autoInfoId',num);
	//处理返回的时候要带参数
	sessionStorage.setItem('autoinfo.edit.brand',$.trim($("#brand").val()));
	sessionStorage.setItem('autoinfo.edit.autolineName',$.trim($("#autolineName").val()));
	sessionStorage.setItem('autoinfo.edit.productTime',$.trim($("#productTime").val()));
	sessionStorage.setItem('autoinfo.edit.engineDisp',$.trim($("#engineDisp").val()));
	
	$('.tables').load('autoinfo/autoinfo_add.html?t=1.0');
}
function deleteAuto(num){//删除弹窗
	$(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("是否删除该车型");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function auto_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function auto_button(){
	var autoinfoId=$(".tips-warp .ok_btn").attr("rel");
	var num="";
	var data="";
	var url="";
	var Prompt="";
	if($(".tips-top .wenzi").html()=="是否删除该车型"){
		data={
			autoinfoId:autoinfoId
		}
		url=DOMIN.MAIN +"/autoinfo/deleteAutoInfo";
		Prompt="删除成功";
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
				autoList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function details(orderNo_num){
	//查看详情
	$(".order-tip span").html("");
};
function relation(autoId){
	$("#autoId").val(autoId);
	$("#rela").show();
}
function fanhui(){
	$("#rela").hide();
	autoList.refresh
};
function matchPart(obj){
	if(!/^\d+$/.test($.trim($("#autoPartId").val()))){
		$.tip('配件id格式不正确！');
		return;
	}
	$.ajax({
		url : DOMIN.MAIN +"/autopart/matchAutoParts",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			autoId:$("#autoId").val(),
			autoPartId:$.trim($("#autoPartId").val()),
			type:$("#type").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("关联成功！");
				$("#rela").hide();
				$("#autoId").val('');
				autoList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
$(function() {
	var autoInfo_session= sessionStorage.getItem("autoInfo_session");
	if(autoInfo_session){
		initPaginator("autoInfo_paga", autoList.dataList,autoInfo_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("autoInfo_session");
	}else{
		initPaginator("autoInfo_paga", autoList.dataList,1,localStorage.getItem('globalPageSize'));
	}
	initSearchParam();
	autoList.dataList();
	$("#search").click(function(){//根据条件查询
		_allPages["autoInfo_paga"].pageNo = 1;
		autoList.dataList();
	});
	$("#submitRelation").click(function(){
		matchPart($(this));
	});
	$("#add_autoInfo").click(function(){
		add_autoInfo();
	});
	$("#addAuto").click(function(){
		addAuto();
	});	
});

function initSearchParam(){
	if(sessionStorage.getItem('autoinfo.edit.brand')){
		$("#brand").val(sessionStorage.getItem('autoinfo.edit.brand'));
		sessionStorage.removeItem('autoinfo.edit.brand');
	}
	if(sessionStorage.getItem('autoinfo.edit.autolineName')){
		$("#autolineName").val(sessionStorage.getItem('autoinfo.edit.autolineName'));
		sessionStorage.removeItem('autoinfo.edit.autolineName');
	}
	if(sessionStorage.getItem('autoinfo.edit.productTime')){
		$("#productTime").val(sessionStorage.getItem('autoinfo.edit.productTime'));
		sessionStorage.removeItem('autoinfo.edit.productTime');
	}
	if(sessionStorage.getItem('autoinfo.edit.engineDisp')){
		$("#engineDisp").val(sessionStorage.getItem('autoinfo.edit.engineDisp'));
		sessionStorage.removeItem('autoinfo.edit.engineDisp');
	}
}


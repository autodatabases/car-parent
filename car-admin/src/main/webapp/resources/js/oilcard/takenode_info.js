var cai_dot_list = {};
cai_dot_list.refresh = function(){
		cai_dot_list.dataList();
}
var isload = false;
cai_dot_list.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/survey/queryrecord",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo: _allPages["taocan_paga"].pageNo,
			pageSize: _allPages["taocan_paga"].pageSize,
            userName:$.trim($("#userName").val()),
			userPhone: $.trim($("#userPhone").val()),
			proposer: $.trim($("#proposer").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			console.log(data)
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
                    taocanNms = returnObj.list.length;
					cai_dot_list.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="7" style="color:red;">没有洗车套餐数据~~</td></tr>');
				}
				// 分页
				setPaginator("taocan_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

cai_dot_list.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
        html+='<tr>';
        html+='<td>'+datalist[i].userName+'</td>';
        html += "<td>" + datalist[i].userPhone + "</td>";
        if(datalist[i].expiredTime == '--') {
			html += "<td>--</td>";
		} else {
			html += "<td>" + $.formatDate(datalist[i].expiredTime).split(" ")[0] + "</td>";
		}
        html += "<td><span class='checkPhoto' style='color:#fc9338;cursor:pointer;' onclick='checkPhoto(this)'>点击查看照片</span><div class='img_box'><span style='cursor:pointer;' onclick='closePhoto(this)'>X</span><img src='" + datalist[i].licensePicture + "' /></div></td>";
		if(datalist[i].proposer == '--') {
			html += "<td>--</td>";
		} else {
			html += "<td>" + datalist[i].proposer + "</td>";
		}
		if(datalist[i].createTime == '--') {
			html += "<td>--</td>";
		} else {
			html += "<td>" + $.formatDate(datalist[i].createTime) + "</td>";
		}
        html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("taocan_paga", cai_dot_list.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("taocan_paga", cai_dot_list.dataList,1,localStorage.getItem('globalPageSize'));
	}
	cai_dot_list.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["taocan_paga"].pageNo = 1;
		cai_dot_list.dataList();
	});
	initExportSelect();
});
function checkPhoto(that) {
	// $(".img_box").css("display","none");
	$(".img_box").removeClass('img_on');
	// $(that).siblings("div").css("display","block");
	$(that).siblings("div").addClass('img_on');
}
function closePhoto(that) {
	$(that).parent(".img_box").removeClass('img_on');
	// $(that).parent(".img_box").css("display","none");
}
function initExportSelect() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var html = "";
	for (var i=0; i<12; i++) {
		if(month - i <= 0) {
			html += "<option>" + (year - 1) + "-" + (month - i + 12) + "</option>";
		} else {
			html += "<option>" + year + "-" + (month - i) + "</option>";
		}
	}
	$("#eMonth").html(html);
	$("#exportSelectBtn").click(function(){
		window.open('survey/exportSurveyRecordExcel?date='+$("#eMonth").val());
	});
}

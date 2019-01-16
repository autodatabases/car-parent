var channelList = {};
channelList.refresh = function(){
	//	_allPages["oilcard_paga"].pageNo = 1;
		channelList.dataList();
}
var isload = false;
channelList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/jilvconfig/adminpage",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["channel_page"].pageNo,
			pageSize:_allPages["channel_page"].pageSize,
			cityName:$.trim($("#cityName").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					channelList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="11" style="color:red;">没有机滤渠道规则!!</td></tr>');
				}
				// 分页
				setPaginator("channel_page",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
channelList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].id+"</td>";
		html+="<td>"+datalist[i].cityName+"</td>";
		html+="<td>"+datalist[i].min+"</td>";
		html+="<td>"+datalist[i].max+"</td>";
		html+="<td>"+datalist[i].jilvPrice+"</td>";
		html+="<td>";
		html+="<a href='javascript:void(0)' onclick='edit(\""+datalist[i].cityName+"\");'>编辑&nbsp;</a> &nbsp;";
		html+="<a href='javascript:void(0)' onclick='toDel(\""+datalist[i].cityName+"\");'>删除&nbsp;</a></td>";
		html+="</tr>"
	}
	$(".tab-list tbody").append(html);
}

$(document).ready(function(){
	initPaginator("channel_page", channelList.dataList,1,localStorage.getItem('globalPageSize'));
	channelList.dataList();

	$("#new").click(function(){
		$('.tables').load("/admin/shop/jilv_add.html");
	});

	$(".search-box button").click(function(){//根据查询条件查询
		_allPages["channel_page"].pageNo = 1;
		channelList.dataList();
	});
});

function edit(cityName){
	sessionStorage.setItem('jilv_config_cityName',cityName);
	$('.tables').load("/admin/shop/jilv_add.html?t=1.0");
}
function toDel(cityName){
	$(".tips-warp").css("display","block");
	$(".tips-warp .ok_btn").attr("cityName",cityName);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
	var cityName=$(".tips-warp .ok_btn").attr("cityName");
	$.ajax({
        url: DOMIN.MAIN + "/jilvconfig/delconfig",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
        	cityName: cityName
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
            	$.tip("操作成功");
				$(".tips-warp").css("display","none");
				channelList.refresh();
            } else {
                $.tip("查询信息失败");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
        }
    });
}

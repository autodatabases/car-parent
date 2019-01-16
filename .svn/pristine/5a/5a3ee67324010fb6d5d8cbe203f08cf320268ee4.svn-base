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
		url : DOMIN.MAIN + "/scoreChannel/queryScoreChannelList",
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
					$(".tab-list tbody").append('<tr><td colspan="14" style="color:red;">没有城市渠道规则!!</td></tr>');
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
var jiyouListData=["","金嘉护10W-40","新磁护5W-40","极护5W-40"];
channelList.renderHtml = function(datalist){
	console.log(datalist)
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '';
			}
		}
		html+="<tr>";
		html+="<td>"+(i-0+1)+"</td>";
		html+="<td>"+datalist[i].cityName+"</td>";
		html+="<td>"+datalist[i].channel+"</td>";
		if(datalist[i].score!=""){
			html+="<td>1:"+datalist[i].score+"</td>";
		}else{
			html+="<td>无</td>";
		}
		if(datalist[i].min!=""||datalist[i].max!=""){
			html+="<td>"+datalist[i].min+"≤保单金额＜"+datalist[i].max+"</td>";
		}else{
			html+="<td>无</td>";
		}
		html+="<td>"+datalist[i].baoyangtimes+"</td>";
		html+="<td>"+datalist[i].carType+"</td>";
		if(datalist[i].jiyou.length>0){
			var jiyouData =datalist[i].jiyou;
			var jy = "";
			for (var mk=0;mk<jiyouData.length;mk++) {
				jy+="<span>"+jiyouData[mk].minimum+"-"+jiyouData[mk].maximum+" &nbsp;"+jiyouListData[jiyouData[mk].ruleValue]+"</span>";
			}
			html+="<td>"+jy+"</td>";
		}else{
			html+="<td>无</td>";
		}
		html+="<td>"+datalist[i].xichetimes+"</td>";
		html+="<td>"+datalist[i].juan20+"</td>";
		html+="<td>"+datalist[i].juan50+"</td>";
		html+="<td>"+datalist[i].penqitimes+"</td>";
		html+="<td>"+datalist[i].dianpingtimes+"</td>";

		html+="<td>";
		html+="<a href='javascript:void(0)' onclick='edit(\""+datalist[i].channel+"\",\""+datalist[i].cityName+"\");'>编辑&nbsp;</a> &nbsp;";
		html+="<a href='javascript:void(0)' onclick='toDel(\""+datalist[i].channel+"\",\""+datalist[i].cityName+"\");'>删除&nbsp;</a></td>";
		html+="</tr>"
	}
	$(".tab-list tbody").append(html);
	//备注超过30字截取
	/*$(".tab-list tbody").find("tr").each(function(){
		var remakTem = $.trim($(this).find("td").eq(5).html());
		if(remakTem.length-0>30){
			$(this).find("td").eq(5).html(remakTem.substring(0,30)+"...");
			$(this).find("td").eq(5).attr("title",remakTem);
		}
	});*/
}

$(document).ready(function(){
	initPaginator("channel_page", channelList.dataList,1,localStorage.getItem('globalPageSize'));
	channelList.dataList();

	$("#new").click(function(){
		$('.tables').load("/admin/shop/channel_add.html");
	});

	$(".search-box button").click(function(){//根据查询条件查询
		_allPages["channel_page"].pageNo = 1;
		channelList.dataList();
	});
});

function edit(channel,cityName){
	addCookie('channel',channel);
	addCookie('cityName',cityName);
	$('.tables').load("/admin/shop/channel_add.html?t=1.0");
}
function toDel(channel,cityName){
	$(".tips-warp").css("display","block");
	$(".tips-warp .ok_btn").attr("channel",channel);
	$(".tips-warp .ok_btn").attr("cityName",cityName);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
	var channel=$(".tips-warp .ok_btn").attr("channel");
	var cityName=$(".tips-warp .ok_btn").attr("cityName");
	$.ajax({
        url: DOMIN.MAIN + "/scoreChannel/delScoreChannel",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
        	channel: channel,
        	cityName:cityName
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

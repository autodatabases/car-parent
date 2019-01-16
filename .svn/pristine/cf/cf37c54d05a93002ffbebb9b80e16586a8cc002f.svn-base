var cityList = {};
cityList.refresh = function(){
	cityList.dataList();
}
var isload = false;
//获取数据
cityList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	var data ={"goodId":sessionStorage.getItem('goodId')};
	var postData = $.extend(data,
			{pageNo:_allPages["oilcard_paga"].pageNo,pageSize:_allPages["oilcard_paga"].pageSize});

	$.ajax({
		url : DOMIN.MAIN + "/virtualtopup/queryoiladdress",
		type : "POST",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :postData,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list-send tbody").empty();
				if(returnObj.list.length>0){
					cityList.renderHtml(returnObj);
				}else{
					$(".tab-list-send tbody").append('<tr><td colspan="3" style="color:#e91e63;">暂无数据~~</td></tr>');
				}
				// 分页
				setPaginator("oilcard_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
//数据处理
cityList.renderHtml = function(records){
	var datalist = records.list;
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j] === null || datalist[i][j] === ""){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].address+"</td>";
		html+="<td>"+datalist[i].oilCardCode+"</td>";
		html+=datalist[i].status==0?"<td>正常</td>":"<td>系统维护</td>";
		html+="<td>";
		if(records.datasets.status.data==false){
			html+='<a href="javascript:void(0)" class="operate" data-type="citySystemUpdate"  data-id="'+datalist[i].id+'">系统维护</a>';
			html+='<a href="javascript:void(0)" class="operate" data-type="citySystemUpdate" data-status="'+datalist[i].status+'" data-id="'+datalist[i].id+'">恢复正常</a>';
		}else{
			//油卡卡号所属城市 "status": 0,正常；1维护；
			if(datalist[i].status==0){
				html+='<a href="javascript:void(0)" class="operate actived" data-type="citySystemUpdate" data-status="'+datalist[i].status+'" data-id="'+datalist[i].id+'">系统维护</a>';
				html+='<a href="javascript:void(0)" class="operate" data-type="citySystemUpdate" data-status="'+datalist[i].status+'" data-id="'+datalist[i].id+'">恢复正常</a>';
			}else{
				html+='<a href="javascript:void(0)" class="operate" data-type="citySystemUpdate" data-status="'+datalist[i].status+'" data-id="'+datalist[i].id+'">系统维护</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="citySystemUpdate"  data-status="'+datalist[i].status+'"  data-id="'+datalist[i].id+'">恢复正常</a>';
			}
		}
		html+="</td>";
		html+="</tr>"
	}
	$(".tab-list-send tbody").append(html);
	bindlistEvent();
}
$(function() {
	initPaginator("oilcard_paga", cityList.dataList,1,localStorage.getItem('globalPageSize'));
	cityList.dataList();
	bindlistEvent();
});
//绑定事件
function bindlistEvent(){
	//弹出框事件
	$("#product-list-pop .cancel_btn").off("click").on("click",function(e){
		$("#product-list-pop").hide();
	});
	$('#product-list-pop .close').off("click").on("click",function(e){
		$("#product-list-pop").hide();
	});
	$('#product-list-pop .ok_btn').off("click").on("click",function(e){
		$("#product-list-pop").hide();
		var $operateTarget = $("#product-list-pop .hideData a");
		var operateType = $(".hideData a") .attr("data-type");
		if($operateTarget.length!=0){
			var Id =  $operateTarget.attr("data-id");
			var cityStatus =  $operateTarget.attr("data-status")=="0"?1:0;
			switch(operateType){
				case "citySystemUpdate":
					var postData ={id:Id,status:cityStatus};
					updateproductCityStatus(postData);
				break;
			}
		}

	});
	//列表操作
	$(".operate.actived").off("click").on("click",function(e){
		var operateType = $(e.target).attr("data-type");
			titleSet = $(e.target).text();
		switch(operateType){
			//更新商品
			case "citySystemUpdate":
				var str ='是否将商品城市状态改为'+titleSet;
				$("#product-list-pop .tips-center").hide();
				$("#product-list-pop .tips-center-text").show();
			    $("#product-list-pop .tips-center-text p").empty().html(str);
			    $("#product-list-pop .tips-center-text .hideData").empty().html($(e.target).clone(true))
				$("#product-list-pop").show();
			break;
		}
	});
}
//更新商品城市信息
function updateproductCityStatus(postData){
    var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/virtualtopup/updateoiladdress",
		type : "POST",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :postData,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			if (data.success) {
				//操作的
				$('.tables').load('product/cityLocation.html');
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
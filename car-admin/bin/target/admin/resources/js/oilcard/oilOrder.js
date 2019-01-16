var oilOrder = {};
var oilCompType = 2;
var transType = 0;
var o = oilCompType;
var t = transType;
oilOrder.refresh = function(){
	//	_allPages["oilcard_paga"].pageNo = 1;
		oilOrder.dataList();
}
var isload = false;
oilOrder.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/queryoilorder",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["oilcard_paga"].pageNo,
			pageSize:_allPages["oilcard_paga"].pageSize,
			userPhone:$.trim($("#phone").val()),
			orderSn:$.trim($("#dingdan").val()),
			oilCardNumber:$.trim($("#oilCardNumber").val()),
			oilCompType:oilCompType,
			transType:transType
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					oilOrder.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="10" style="color:#e91e63;">没有油卡订单数据~~</td></tr>');
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
oilOrder.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		if(datalist[i].supplier == 0) {
			html+="<td>追电科技</td>";
		} else if(datalist[i].supplier == 1) {
			html+="<td>高阳迅捷</td>";
		}else if(datalist[i].supplier == 2) {
			html+="<td>欧飞</td>";
		} else {
			html+="<td>--</td>";
		}
        if(datalist[i].transType==0){
			html+="<td>串码充值</td>";
		}else if(datalist[i].transType==1){
			html+="<td>卷码充值</td>";
		}else if(datalist[i].transType==2){
			html+="<td>中石油油卡充值</td>";
		}else if(datalist[i].transType==3){
			html+="<td>中石化油卡充值</td>";
		}else if(datalist[i].transType==4){
			html+="<td>话费充值</td>";
		}else if(datalist[i].transType==5){
			html+="<td>流量充值</td>";
		}else if(datalist[i].transType==6){
			html+="<td>金额修改失败</td>";
		} else {
            html+="<td>--</td>";
        }
		html+="<td>"+datalist[i].orderSn+"</td>";
		html+="<td>"+datalist[i].userPhone+"</td>";
		html+="<td>"+datalist[i].cardNo+"</td>";
		html+="<td>"+datalist[i].fillMoney+"</td>";
		html+="<td>"+datalist[i].surplusMoney+"</td>";
		html+="<td>"+datalist[i].userMoney+"</td>";
		html+="<td>"+datalist[i].respMsg+"</td>";
		html+="<td>"+datalist[i].createTime+"</td>";
		html+="</tr>";
	}
	$(".tab-list tbody").append(html);
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
		if(oilCompType === 3) {
			oilCompType = o;
		}
		window.open('oilCard/exportoilexcel?date='+$("#eMonth").val()+'&oilCompType=' + oilCompType);
	});
}
$(function() {
	initPaginator("oilcard_paga", oilOrder.dataList,1,localStorage.getItem('globalPageSize'));
	oilOrder.dataList();
	initExportSelect();
	$("#searchBtn").click(function(){//根据查询条件查询
		if(oilCompType === 3) {
			oilCompType = o;
			transType = t;
		}
		_allPages["oilcard_paga"].pageNo = 1;
		oilOrder.dataList();
	});
	$("#allSearch").click(function(){//根据查询条件查询全部订单
		o = oilCompType;
		t = transType;
		if($.trim($("#phone").val()) == "" && $.trim($("#dingdan").val()) == "") {
			$.tip("请填写手机号或者订单号！")
		}
		_allPages["oilcard_paga"].pageNo = 1;
		oilCompType = 3;
		transType = 7;
		oilOrder.dataList();
	});
	$(".tab-top span").on('mouseenter', function() {
	  	$(this).siblings('span').children('ul').hide();
	  	$(this).children('ul').show('slow');
	});
	$(".tab-top span").on('mouseleave', function() {
	  $(".tab-top span ul").hide();
	});
	$(".tab-top span li").on('click', function() {
	  _allPages["oilcard_paga"].pageNo = 1;
	  oilCompType = $(this).attr('rel');
	  transType = $(this).attr('als');
	  $(this).parents("span").addClass('on');
	  $(this).parents("span").siblings('span').removeClass('on');
	  if(oilCompType === "4" && transType === "4") {
		  $(".oilCardNumber").show();
	  } else {
		  $("#oilCardNumber").val("");
		  $(".oilCardNumber").hide();
	  }
	  oilOrder.refresh();
	});
	$("#allExportSelectBtn").click(function(){
		if($.trim($("#phone").val()) === "" && $.trim($("#dingdan").val()) === "") {
			$.tip("请输入订单号或账户手机号！");
			return;
		}
		window.open('oilCard/exportuseroilexcel?userPhone=' + $.trim($("#phone").val()) + '&orderNo=' + $.trim($("#dingdan").val()));
	});
});

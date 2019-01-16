var sellerReportList = {};
sellerReportList.refresh = function(){
	_allPages["businesStatement_paga"].pageNo = 1;
	sellerReportList.dataList();
}
var isload = false;
sellerReportList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/seller/querySellerReportList.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["businesStatement_paga"].pageNo,
			pageSize:_allPages["businesStatement_paga"].pageSize,
			sellerName:$("#sellerName").val(),
			date:$("#exportSelect").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					sellerReportList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="9" style="color:#cddc39;font-size:20px">没有商家结算信息~~</td></tr>');
				}

				// 分页
				setPaginator("businesStatement_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
sellerReportList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td>'+datalist[i].sellerName+'</td>';
		html+='<td>'+datalist[i].companyName+'</td>';
//		html+='<td>'+datalist[i].year+'-'+datalist[i].month+'</td>';
//		html+='<td>'+datalist[i].num+'</td>';
		html+='<td>'+datalist[i].totalMoney+'</td>';
		html+='<td>'+datalist[i].accountName+'</td>';
		html+='<td>'+datalist[i].accountNumber+'</td>';
		html+='<td>'+datalist[i].account+'</td>';
		if(datalist[i].invoice == 1) {
			html+='<td>开</td>';
		} else {
			html+='<td>不开</td>';
		}
		if(datalist[i].status==0)
			html+='<td>商家未结算</td>';
		if(datalist[i].status==1)
			html+='<td>商家已结算</td>';
		if(datalist[i].status==2)
			html+='<td>问题订单</td>';
		if(datalist[i].status==3)
			html+='<td>已完成</td>';
		if(datalist[i].status==1){
			html+='<td><a onclick=exportExcel("'+datalist[i].sellerId+'","'+datalist[i].year+'","'+datalist[i].month+'")>导出</a>';
			html+='<a href="javascript:void(0)" onclick=editStatus("'+datalist[i].id+'");>确认完成</a></td>';
	    }else{
			html+='<td><a onclick=exportExcel("'+datalist[i].sellerId+'","'+datalist[i].year+'","'+datalist[i].month+'")>导出</a></td>';
/*		    html+='<a href="javascript:void(0)" onclick=enable("'+datalist[i].id+'");>起用</a>';
		    html+='<a href="javascript:void(0)" onclick=deleteBus("'+datalist[i].id+'");>删除</a></td>';*/
		}
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
}

function editStatus(num){//确定修改状态弹窗
	$(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("是否确定已跟商家结算");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function exportExcel(sellerId,year,month){
	console.log(year)
	var date = year+'-'+month;
	window.open('order/exportOrderExcel?date='+date+'&sellerId='+sellerId);
}
function audit_button(){
	var obusinesNo=$(".tips-warp .ok_btn").attr("rel");
	var num="";
	var data="";
	var url="";
	var Prompt="";
	if($(".tips-top .wenzi").html()=="是否确定已跟商家结算"){
		data={
			reportId:obusinesNo,
			status:3
		}
		url=DOMIN.MAIN +"/seller/updateSellerReportStatus";
		Prompt="修改成功";
	}else{
		return;
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
				sellerReportList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}

function jiesuan_button(){
	$.ajax({
		url : DOMIN.MAIN +"/order/resetSellerReport",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			date:$.trim($("#exportSelect").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				sellerReportList.refresh();
				$.tip("更新成功");
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function initExportSelect(){
	var mydate = new Date();
	var year=mydate.getFullYear(); //获取完整的年份(4位,1970-????)
	var month = mydate.getMonth()+1;  //获取当前月份(0-11,0代表1月)
	var html="<option value=''>请选择年份</option>";
	for(i=1;i<13;i++){
		if(month-i<=0){
			html+="<option value='"+(year-1)+'-'+(12+month-i)+"' >"+(year-1)+'-'+(12+month-i)+"</option>";
		}else{
			html+="<option value='"+year+'-'+(month-i)+"' >"+year+'-'+(month-i)+"</option>";
		}
	}
	$("#exportSelect").append(html);
}
function initExportSelect01(){
	var mydate = new Date();
	var year=mydate.getFullYear(); //获取完整的年份(4位,1970-????)
	var month = mydate.getMonth()+1;  //获取当前月份(0-11,0代表1月)
	var html="<option value=''>请选择年份</option>";
	html +="<option value='2017年'>2017年</option>";
	for(i=1;i<13;i++){
		if(month-i<=0){
			html+="<option value='"+(year-1)+'-'+(12+month-i)+"' >"+(year-1)+'-'+(12+month-i)+"</option>";
		}else{
			html+="<option value='"+year+'-'+(month-i)+"' >"+year+'-'+(month-i)+"</option>";
		}
	}
	$("#exportSelect01").append(html);
}
$(function() {
	initExportSelect();
	initExportSelect01();
	initPaginator("businesStatement_paga", sellerReportList.dataList,1,localStorage.getItem('globalPageSize'));
	sellerReportList.dataList();
	$(".search-box button.search-btn").click(function(){//根据店铺查询
		_allPages["businesStatement_paga"].pageNo = 1;
		sellerReportList.dataList();
	});
	$(".search-box button.export-btn").on("click",function() {
		var date = $.trim($("#exportSelect01").val());
		if(date === "") {
			$.tip("请选择导出时间!");
			return;
		}
		window.open('order/exportAllOrderExcel?date='+date);
	})
	$(".search-box button.jiesuan-btn").click(function(){
		jiesuan_button();
	});
})

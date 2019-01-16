var orderStatus=-1;
var orderList = {};
orderList.refresh = function(){
		//_allPages["order_paga"].pageNo = 1;
		orderList.dataList();
}
var isload = false;
orderList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/driverorder/driverlist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["order_paga"].pageNo,
			pageSize:_allPages["order_paga"].pageSize,
			orderNo:$.trim($("#orderNo").val()),
			orderStatus:orderStatus
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					orderList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="6" style="text-indent:600px;color:red;">没有订单数据！</td></tr>');
				}
				// 分页
				setPaginator("order_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
orderList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		if(datalist[i].remark!=null && datalist[i].remark.length>50){
			datalist[i].remarkshort = datalist[i].remark.substr(0,50);
		}else{
			datalist[i].remarkshort = datalist[i].remark;
		}
		html+='<tr>';
		html+='<td class="bh">'+(i+1)+'</td>';
		html+='<td class="ddxq">';
    		var statusStr = "待服务";
    		if (datalist[i].orderStatus == 0) {
    			statusStr = "待服务";
    		} else if (datalist[i].orderStatus == 1 || datalist[i].orderStatus == 2) {
    			statusStr = '服务中';
    		} else if (datalist[i].orderStatus == 3) {
    			statusStr = '已完成';
    		} else if (datalist[i].orderStatus == 4) {
    			statusStr = '已取消';
    		}
    		html+='  订单状态：<span class="order-status">'+statusStr+'</span>';
            html+='<p>订单编号：'+datalist[i].orderNo+'</p>';
    		html+='<p class="create-time">下单时间：'+$.formatDate(datalist[i].createTime)+'</p>';
            html+='<p class="appoint-time">预约服务时间：'+$.formatDate(datalist[i].appointmentTime)+'</p>';
    		if(datalist[i].orderStatus == 3){
    			if(datalist[i].finishTime){
    				html+='<p class="finish-time">订单完成时间：'+$.formatDate(datalist[i].finishTime)+'</p>';
    			}else{
    				html+='<p class="finish-time">订单完成时间:</p>';
    			}
    		}
            if(datalist[i].orderStatus == 4){
    			if(datalist[i].updateTime){
    				html+='<p class="cancel-time">订单取消时间：'+$.formatDate(datalist[i].updateTime)+'</p>';
    			}else{
    				html+='<p class="cancel-time">订单取消时间:</p>';
    			}
    		}
            html+='<p class="start-address">起点：'+datalist[i].startAddress+'</p>';
            html+='<p class="end-address">终点：'+datalist[i].endAddress+'</p>';
		html+='</td>';
		html+='<td class="khxx">';
            if(datalist[i].passengerName) {
                html+='<p>姓名：'+datalist[i].passengerName+'</p>';
                html+='<p class="seller-phone">电话：'+datalist[i].passengerPhone+'</p>';
            } else {
                html+='<p>无</p>';
            }
		html+='</td>';
		html+='<td class="sjxx">'
            if(datalist[i].driverName) {
                html+='<p>姓名：'+datalist[i].driverName+'</p>';
                html+='<p class="seller-phone">电话：'+datalist[i].driverPhone+'</p>';
            } else {
                html+='<p>无</p>';
            }
		html+='</td>';
		html+='<td class="jsxx" style="width:20%;">';
    		html+='<p title='+datalist[i].remark+'>备注：'+(datalist[i].remark==null?'无':datalist[i].remarkshort)+'</p>';
		html+='</td>';
		html+='<td class="cz">';
    		html+='<a href="javascript:void(0)" onclick="details(\''+datalist[i].orderNo+'\');">查看&nbsp;</a>';
    		html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].orderNo+'">备注&nbsp;';
    		html+='<p style="display:none">'+datalist[i].remark+'</p></a>';
		html+='</td>';
		html+='</tr>';

	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
	$(".beizhu").click(function(){
		var remark = $(this).children('p').html();
		var orderNo = $(this).attr('ono');;
		$("#orderOperRemark-orderNo").val(orderNo);
		if(remark!='null' && remark!='undefined'){
			$("#orderOperRemarkContent").val(remark);
		}
		$("#orderOperRemark").show();
	})
}

function submitRemak(){
	if($.trim($("#orderOperRemarkContent").val())==''){
		$.tip('订单备注信息为空！');
		return;
	}
	$.ajax({
		url : DOMIN.MAIN + "/driverorder/driverremark",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			orderNo:$("#orderOperRemark-orderNo").val(),
			remark:$("#orderOperRemarkContent").val()
		},
		success : function(data, textStatus) {
			$("#orderOperRemark-orderNo").val('');
			$("#orderOperRemarkContent").val('');
			if (data.success) {
				$.tip("操作成功");
				$("#orderOperRemark").hide();
				orderList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}

function closeRemak(){
	$("#orderOperRemark-orderNo").val('');
	$("#orderOperRemarkContent").val('');
	$("#orderOperRemark").hide();
}
function details(orderNo_num){
	//查看详情
	$(".order-tip span").html("");
	$.ajax({
		url : DOMIN.MAIN + "/driverorder/driverlist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
            pageNo:_allPages["order_paga"].pageNo,
			pageSize:_allPages["order_paga"].pageSize,
			orderNo:orderNo_num
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
                var obj = data.list[0];
                $.each(obj, function(key, val) {
                    if(obj[key] === null || obj[key] === "") {
                        obj[key] = "--"
                    }
                });
                var html = "";
                html += "<li><label>订单编号</label><span>" + obj.orderNo + "</span></li>";

				if(obj.orderStatus==0)
					html += "<li><label>订单状态</label><span>待服务</span></li>";
				else if(obj.orderStatus==1 || obj.orderStatus==2)
					html += "<li><label>订单状态</label><span>服务中</span></li>";
				else if(obj.orderStatus==3)
					html += "<li><label>订单状态</label><span>已完成</span></li>";
				else if(obj.orderStatus==4)
					html += "<li><label>订单状态</label><span>已取消</span></li>";

                html += "<li><label>客户姓名</label><span>" + obj.passengerName + "</span></li>";
                html += "<li><label>客户电话</label><span>" + obj.passengerPhone + "</span></li>";
                if(obj.createTime != "--") {
                    html += "<li><label>下单时间</label><span>" + $.formatDate(obj.createTime) + "</span></li>";
                } else {
                    html += "<li><label>下单时间</label><span>--</span></li>";
                }
                if(obj.appointmentTime != "--") {
                    html += "<li><label>预约时间</label><span>" + $.formatDate(obj.appointmentTime) + "</span></li>";
                } else {
                    html += "<li><label>预约时间</label><span>--</span></li>";
                }
                if(obj.orderStatus == 3){
        			if(obj.finishTime){
        				html += "<li><label>订单完成时间</label><span>" + $.formatDate(obj.finishTime) + "</span></li>";
        			}else{
        				html += "<li><label>订单完成时间</label><span>--</span></li>";
        			}
        		}
                if(obj.orderStatus == 4){
        			if(obj.updateTime){
        				html += '<li><label>订单取消时间</label><span>' + $.formatDate(obj.updateTime) + '</span></li>';
        			}else{
        				html += "<li><label>订单完成时间</label><span>--</span></li>";
        			}
        		}
                html += "<li><label>起点</label><span>" + obj.startAddress + "</span></li>";
                html += "<li><label>终点</label><span>" + obj.endAddress + "</span></li>";
                html += "<li><label>司机姓名</label><span>" + obj.driverName + "</span></li>";
                html += "<li><label>司机电话</label><span>" + obj.driverPhone + "</span></li>";

                $(".order-tips-list ul").html(html)
                $(".order-tip").show();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
};
$(function(){
	initPaginator("order_paga", orderList.dataList,1,localStorage.getItem('globalPageSize'));
	orderList.dataList();
	$("#order_span span").each(function(i,e){
		$(e).click(function(){
            $("#orderNo").val("");
			$(e).addClass("on").siblings("span").removeClass("on");
			orderStatus=$(e).attr("rel");
			_allPages["order_paga"].pageNo = 1;
			orderList.dataList();
		});
	});
	$(".order-tip-btn button").click(function(){
		$(".order-tip").hide();
	});
	$(".order-tips-top span").click(function(){
		$(".order-tip").hide();
	});
	$(".search-box button").click(function(){
		_allPages["order_paga"].pageNo = 1;
		orderList.dataList();
	});
	$(".tips-bg").on("click",function(){
		$(".order-tip span").html("");
		$(".order-tip").hide("");

	})
})

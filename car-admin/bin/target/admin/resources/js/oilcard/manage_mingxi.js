var startNum = "";
var endNum = "";
oilcardList.refresh = function(){
	//	_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
}
var isload = false;
oilcardList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	if(startNum === "" || endNum === "") {
		startNum = getCookie('oilcardXuliehao').split(",")[0];
		endNum = getCookie('oilcardXuliehao').split(",")[1];
	}
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/oilDetailList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["oilcard_paga"].pageNo,
			pageSize:_allPages["oilcard_paga"].pageSize,
			startNum:startNum,
			endNum:endNum,
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					oilcardList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:#e91e63;">没有油卡数据~~</td></tr>');
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
oilcardList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+="<tr>";
		html+="<td>"+datalist[i].id+"</td>";
		html+="<td>"+datalist[i].phone+"</td>";
		html+="<td>"+datalist[i].rechargeCode+"</td>";
        if(datalist[i].openTime !== '--') {
        	html+="<td>"+$.formatDate(datalist[i].openTime) +"</td>";
        } else {
            html+="<td>--</td>";
        }
        html+="<td>"+datalist[i].address +"</td>";
        html+="<td>"+datalist[i].money +"</td>";
        if (datalist[i].status == 0) {
			html+="<td>未使用</td>";
		} else if(datalist[i].status == 1){
			html+="<td>已使用</td>";
		} else if(datalist[i].status == 2){
			html+="<td>已冻结</td>";
		} else if(datalist[i].status == 3){
			html+="<td>已过期</td>";
		} else if(datalist[i].status == 9){
			html+="<td>未开通</td>";
		} else{
			html+="<td>--</td>";
		}
		html+="<td>";
		if(datalist[i].status == 2){
			html+='<a href="javascript:void(0)" class="dongjie" num="'+datalist[i].phone+'" style="color:#fc9338;" status="1">解冻&nbsp;</a>';
		} else {
			html+='<a href="javascript:void(0)" class="dongjie" num="'+datalist[i].phone+'" style="color:#fc9338;" status="0">冻结&nbsp;</a>';
		}
		html+="</td>";
		html+="</tr>"
	}
	$(".tab-list tbody").append(html);
	$(".dongjie").on('click',function(){
		$(".ok_btn").attr('status',$(this).attr('status'));
		$(".ok_btn").attr('num',$(this).attr('num'));
		if($(this).attr('status') === "0") {
			$(".tips-center p").html("是否确认冻结此油卡");
		} else if($(this).attr('status') === "1") {
			$(".tips-center p").html("是否确认解冻此油卡");
		}
		$(".tips-warp").css("display","block");
	})
}
oilcardList.initExportSelect = function(){
	var mydate = new Date();
	var year=mydate.getFullYear(); //获取完整的年份(4位,1970-????)
	var month = mydate.getMonth()+1; //获取当前月份(0-11,0代表1月)
	var html="<option>"+year+'-'+month+"</option>";
	for(i=1;i<12;i++){
		if(month-i<=0){
			html+="<option>"+(year-1)+'-'+(12+month-i)+"</option>";
		}else{
			html+="<option>"+year+'-'+(month-i)+"</option>";
		}
	}
	$("#exportSelect").append(html);
}
oilcardList.dongjie = function(dataObj){
	var startNum = dataObj.startNum;
	var endNum = dataObj.endNum;
	var status = dataObj.status;
	var num = dataObj.num;
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/freezeoil",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data :{
			startNum: startNum,
			endNum: endNum,
			status: status,
			num: num
		},
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				oilcardList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	})
}
$(function() {
	initPaginator("oilcard_paga", oilcardList.dataList,1,localStorage.getItem('globalPageSize'));
	oilcardList.dataList();
    oilcardList.initExportSelect();
	$(".search-btn").click(function(){//根据查询条件查询
		//判断序列号格式正确与否
	    var reg = /^\d{9}$/;
		if($.trim($("#startNum").val()) !== "" || $.trim($("#endNum").val()) !== "") {
			if ($.trim($("#startNum").val()) !== "") {
		        if(!reg.test($.trim($("#startNum").val()))) {
		            $.tip("请输入9位数字的开始序列号");
		    		return;
		        }
		    } else {
		        $.tip("请输入开始序列号");
				return;
		    }
		    if ($.trim($("#endNum").val()) !== "") {
		        if(!reg.test($.trim($("#endNum").val()))) {
		            $.tip("请输入9位数字的结束序列号");
		    		return;
		        }
		    } else {
		        $.tip("请输入结束序列号");
				return;
		    }
		    if($.trim($("#startNum").val()) - $.trim($("#endNum").val()) > 0) {
		        $.tip("结束序列号必须大于或等于开始序列号");
				return;
		    }
		}
		startNum = $.trim($("#startNum").val());
		endNum = $.trim($("#endNum").val());
		_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
	});
	$(".pDongjie").on("click",function(){
		//判断序列号格式正确与否
	    var reg = /^\d{9}$/;
	    if ($.trim($("#startNum").val()) !== "") {
	        if(!reg.test($.trim($("#startNum").val()))) {
	            $.tip("请输入9位数字的开始序列号");
	    		return;
	        }
	    } else {
	        $.tip("请输入开始序列号");
			return;
	    }
	    if ($.trim($("#endNum").val()) !== "") {
	        if(!reg.test($.trim($("#endNum").val()))) {
	            $.tip("请输入9位数字的结束序列号");
	    		return;
	        }
	    } else {
	        $.tip("请输入结束序列号");
			return;
	    }
	    if($.trim($("#startNum").val()) - $.trim($("#endNum").val()) > 0) {
	        $.tip("结束序列号必须大于或等于开始序列号");
			return;
	    }

		if($(this).attr('status') === "0") {
			$(".tips-center p").html("是否确认冻结这批油卡");
		} else if($(this).attr('status') === "1") {
			$(".tips-center p").html("是否确认解冻这批油卡");
		}
		$(".ok_btn").attr('status',$(this).attr('status'));
		$(".tips-warp").css("display","block");
	})
	$("#oilcard_manage").click(function(){
		$('.tables').load('oilcard/oilcard_manage.html');
	});
	$(".ok_btn").click(function(){
		var status = $(this).attr('status');
		if($(this).attr("num")) {
			var num = $(this).attr('num');
			var dataObj = {
				startNum: "",
				endNum: "",
				status: status,
				num: num
			}
		} else {
			var startNum = $.trim($("#startNum").val());
			var endNum = $.trim($("#endNum").val());
			var dataObj = {
				startNum: startNum,
				endNum: endNum,
				status: status,
				num: ""
			}
		}
		$(this).attr('num',"");
		$(".tips-warp").css("display","none");
		oilcardList.dongjie(dataObj)

	});
});
function deletes(num){//删除面额
    $(".tips-warp").css("display","block");
  	$(".tips-warp .ok_btn").attr("rel",num);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}

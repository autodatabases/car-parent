var oilcardList = {};
oilcardList.refresh = function () {
	//	_allPages["oilcard_paga"].pageNo = 1;
	oilcardList.dataList();
}
var isload = false;
oilcardList.dataList = function () {
	if (isload) {
		return;
	}
	var load = $.loading();
	isload = true;
	var data = $.parseForm($(".oil-checkForm"));
	var postData = $.extend(data, { pageNo: _allPages["oilcard_paga"].pageNo, pageSize: _allPages["oilcard_paga"].pageSize, status: $(".statusTab").attr("status") });
	$.ajax({
		url: DOMIN.MAIN + "/oilCard/oilCheckList",
		type: "post",
		processData: true,
		cache: false,
		async: true,
		dataType: "json",
		data: postData,
		traditional: true,// 使用传统方式序列化
		success: function (data, textStatus) {
			isload = false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list-check tbody").empty();
				if (returnObj.list.length > 0) {
					oilcardList.renderHtml(returnObj);
				} else {
					$(".tab-list-check tbody").append('<tr><td colspan="9" style="color:#e91e63;">没有油卡数据~~</td></tr>');
				}
				// 分页
				setPaginator("oilcard_paga", returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
					returnObj.pageInfo.recordCount);
			} else {
				isload = false;
				load.remove();
				$.tip(data.message);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			isload = false;
			load.remove();
			$.tip(errorThrown);
		}
	});
}
oilcardList.renderHtml = function (returnObj) {
	var datalist = returnObj.list;
	var html = "";
	var supplierLabel = {
		"0": "北京亿特诺美",
		"1": "上海腾霜",
		"2": "上海腾姆",
		"3": "上海泰奎",
		"4": "上海泰钭"
	};
	var statusLabel = {
		"0": "未使用",
		"1": "已使用",
		"2": "已冻结",
		"3": "已过期",
		"9": "未开通"
	}
	$.each(datalist, function (i, e) {
		if (e.supplier != null) {
			e.supplierText = supplierLabel[e.supplier];
		} else {
			e.supplierText = '--';
		}
		if (e.status != null) {
			e.statusText = statusLabel[e.status];
		} else {
			e.statusText = '--';
		}
	});
	for (var i = 0; i < datalist.length; i++) {
		for (var j in datalist[i]) {
			if (datalist[i][j] == null) {
				datalist[i][j] = '--';
			}
		}
		html += "<tr>";
		html += "<td>" + datalist[i].phone + "</td>";
		html += "<td>" + datalist[i].batchCode + "</td>";
		html += "<td>" + datalist[i].supplierText + "</td>";
		html += "<td>" + datalist[i].buyer + "</td>";
		html += "<td>" + datalist[i].userPhone + "</td>";
		html += "<td>" + datalist[i].address + "</td>";
		html += "<td>" + datalist[i].moeny + "</td>";
		html += "<td>" + datalist[i].statusText + "</td>";
		if (datalist[i].date === '--') {
			html += "<td>--</td>";
		} else {
			html += "<td>" + $.formatDate(datalist[i].date) + "</td>";
		}
		if (datalist[i].status == 1) {
			html += "<td>";
			html += '<a href="javascript:void(0)" class="searchOrder" data-CardNo="' + datalist[i].phone + '" style="color:#fc9338;">查看&nbsp;</a>';
			html += "</td>";
		} else {
			html += "<td>--</td>";
		}
		// html+="<td>"+datalist[i].remark+"</td>";
		// html+="<td>";
		// html+='<a href="javascript:void(0)" class="beizhu" ono="'+datalist[i].id+'" style="color:#fc9338;">编辑备注&nbsp;';
		// html+='<p style="display:none">'+datalist[i].remark+'</p></a>';
		// html+="</td>";
		html += "</tr>"
	}
	$(".tab-list-check tbody").append(html);
	var dataNum = returnObj.datasets.total.data
	if (dataNum) {
		$("#totalNum").html(dataNum[-1]);
		$("#usedNum").html(dataNum[1]);
		$("#openNum").html(dataNum[9]);
		$("#nuseNum").html(dataNum[0]);
		$("#overNum").html(dataNum[3]);
		$("#freedNum").html(dataNum[2]);
		$("#moneyNum").html(dataNum.money);
	}
	// //备注超过30字截取
	// $(".tab-list-check tbody").find("tr").each(function(){
	// 	var remakTem = $.trim($(this).find("td").eq(5).html());
	// 	if(remakTem.length-0>30){
	// 		$(this).find("td").eq(5).html(remakTem.substring(0,30)+"...");
	// 		$(this).find("td").eq(5).attr("title",remakTem);
	// 	}
	// });
	// $(".beizhu").click(function(){
	// 	var remark = $(this).children('p').html();
	// 	var orderNo = $(this).attr('ono');;
	// 	$("#orderOperRemark-orderNo").val(orderNo);
	// 	if(remark!='null' && remark!='undefined'){
	// 		$("#orderOperRemarkContent").val(remark);
	// 	}
	// 	$("#orderOperRemark").show();
	// })
	oilcardList.operate();
}
oilcardList.operate = function () {
	$(".searchOrder").on("click", function () {
		var dataNo = $(this).attr("data-CardNo");
		addCookie('cardNo', dataNo);
		$('.tables').load('oilcard/oilcard_linkOrder.html');

	})
}
$(function () {
	initPaginator("oilcard_paga", oilcardList.dataList, 1, localStorage.getItem('globalPageSize'));
	oilcardList.dataList();
	initDate();
	// 切换省份
	$('#province').on('change', function () {
		if ($(this).val() == '') {
			$("#address").empty();
			$("#address").append('<option value="">选择城市</option>');
		} else if ($(this).val() == '广东省') {
			getCity(20)
		} else if ($(this).val() == '湖南省') {
			getCity(19)
		}
	})
	// oilcardList.summary();
	$(".search-box button").click(function () {//根据查询条件查询
		//判断序列号格式正确与否
		var reg = /^\d{9}$/;
		if ($.trim($("#startNum").val()) !== "" || $.trim($("#endNum").val()) !== "") {
			if ($.trim($("#startNum").val()) !== "") {
				if (!reg.test($.trim($("#startNum").val()))) {
					$.tip("请输入9位数字的开始序列号");
					return;
				}
			} else {
				$.tip("请输入开始序列号");
				return;
			}

			if ($.trim($("#endNum").val()) !== "") {
				if (!reg.test($.trim($("#endNum").val()))) {
					$.tip("请输入9位数字的结束序列号");
					return;
				}
			} else {
				$.tip("请输入结束序列号");
				return;
			}
			if ($.trim($("#startNum").val()) - $.trim($("#endNum").val()) > 0) {
				$.tip("结束序列号必须大于或等于开始序列号");
				return;
			}
		}
		if ($.trim($("#num").val()) !== "") {
			if (!reg.test($.trim($("#num").val()))) {
				$.tip("请输入9位数字的序列号");
				return;
			}
		}
		// 限制城市
		if ($('#province').val() != '') {
			if ($('#address').val() == '') {
				$.tip('请选择城市');
				return;
			}
		}
		_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
	});
	$("#exportSelectBtn").click(function () {
		if ($("#batchDate").val() !== "") {
			window.open("oilCard/exportoilchecklist?startNum=" + $("#startNum").val() + "&endNum=" + $("#endNum").val() + "&status=" + $(".statusTab").attr("status") + "&num=" + $("#num").val() + "&address=" + $("#address").val() + "&date=" + $("#date").val() + "&batchDate=" + $("#batchDate").val() + "&batchNum=" + $("#batchNum").val());
			return;
		}
		if ($("#startNum").val() === "" && $("#endNum").val() === "" && $(".statusTab").attr("status") === "-1" && $("#num").val() === "" && $("#address").val() === "" && $("#date").val() === "" && $("#batchDate").val() === "" && $("#batchNum").val() === "") {
			$.tip("请输入至少一个导出条件！");
			return;
		}
		//判断序列号格式正确与否
		var reg = /^\d{9}$/;
		if ($.trim($("#startNum").val()) !== "" || $.trim($("#endNum").val()) !== "") {
			if ($.trim($("#startNum").val()) !== "") {
				if (!reg.test($.trim($("#startNum").val()))) {
					$.tip("请输入9位数字的开始序列号");
					return;
				}
			} else {
				$.tip("请输入开始序列号");
				return;
			}
			if ($.trim($("#endNum").val()) !== "") {
				if (!reg.test($.trim($("#endNum").val()))) {
					$.tip("请输入9位数字的结束序列号");
					return;
				}
			} else {
				$.tip("请输入结束序列号");
				return;
			}
			if ($.trim($("#startNum").val()) - $.trim($("#endNum").val()) > 0) {
				$.tip("结束序列号必须大于或等于开始序列号");
				return;
			}
		}
		if ($.trim($("#num").val()) !== "") {
			if (!reg.test($.trim($("#num").val()))) {
				$.tip("请输入9位数字的序列号");
				return;
			}
		}
		// 限制城市
		if ($('#province').val() != '') {
			if ($('#address').val() == '') {
				$.tip('请选择城市');
				return;
			}
		}
		window.open("oilCard/exportoilchecklist?startNum=" + $("#startNum").val() + "&endNum=" + $("#endNum").val() + "&status=" + $(".statusTab").attr("status") + "&num=" + $("#num").val() + "&address=" + $("#address").val() + "&date=" + $("#date").val() + "&batchNum=" + $("#batchNum").val());
	})
	$(".statusTab li").on('click', function () {
		$(this).addClass('on').siblings().removeClass("on");
		$(".statusTab").attr("status", $(this).attr("rel"));
		_allPages["oilcard_paga"].pageNo = 1;
		oilcardList.dataList();
	})
	$("#batchDate").on("change", function () {
		if ($("#batchDate").val() !== "") {
			getBatchnum();
		} else {
			$("#batchNum").html('<option value="">请选择批次</option>');
		}
	})
	$("#batchNum").on("click", function () {
		if ($("#batchDate").val() === "") {
			$.tip("请先选择开通时间");
			return;
		}
		// getBatchnum();
	})
});
//打开备注弹出框，并显示原有备注
function orderRemark(orderNo, remark) {
	$("#orderOperRemark-orderNo").val(orderNo);
	if (remark != 'null' && remark != 'undefined') {
		$("#orderOperRemarkContent").val(remark);
	}
	$("#orderOperRemark").show();
}
// //提交备注
// function submitRemak(){
// 	if($.trim($("#orderOperRemarkContent").val())==''){
// 		$.tip('订单备注信息为空！');
// 		return;
// 	}
// 	$.ajax({
// 		url : DOMIN.MAIN + "/oilCard/updateRemak",
// 		type : "post",
// 		cache : false,
// 		async : true,
// 		dataType : "json",
// 		data :{
// 			id:$("#orderOperRemark-orderNo").val(),
// 			remark:$("#orderOperRemarkContent").val()
// 		},
// 		success : function(data, textStatus) {
// 			$("#orderOperRemark-orderNo").val('');
// 			$("#orderOperRemarkContent").val('');
// 			if (data.success) {
// 				$.tip("操作成功");
// 				$("#orderOperRemark").hide();
// 				oilcardList.refresh();
// 			}else{
// 				$.tip(data.message);
// 			}
// 		},
// 		error : function(XMLHttpRequest, textStatus, errorThrown) {
// 			$.tip(errorThrown);
// 		}
// 	})
// }
// //关闭备注弹出框
// function closeRemak(){
// 	$("#orderOperRemark-orderNo").val('');
// 	$("#orderOperRemarkContent").val('');
// 	$("#orderOperRemark").hide();
// }
//获取城市
function getCity(provinceId) {
	$.ajax({
		url: DOMIN.MAIN + "/address/queryCityByProvince",
		type: "post",
		cache: false,
		async: false,
		dataType: "json",
		data: {
			provinceId: provinceId
		},
		traditional: true,
		success: function (data, textStatus) {
			if (data.success) {
				var obj = data.list;
				//处理返回结果
				$("#address").empty();
				$("#address").append('<option value="">选择城市</option>');
				for (var i = 0; i < obj.length; i++) {
					$("#address").append('<option value="' + obj[i].regionname + '">' + obj[i].regionname + '</option>');
				}
				if (provinceId == 19) {
					$("#address").append('<option value="湖南-其他">湖南-其他</option>');
					$("#address").append('<option value="湖南-赠送">湖南-赠送</option>');
				} else if (provinceId == 20) {
					$("#address").append('<option value="广东-其他">广东-其他</option>');
					$("#address").append('<option value="广东-赠送">广东-赠送</option>');
				}
			} else {
				$.tip(data.message);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
//组织日期
function initDate() {
	var mydate = new Date();
	var year = mydate.getFullYear(); //获取完整的年份(4位,1970-????)
	var month = mydate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
	var html = "<option value=''>请选择时间</option>";
	for (i = 0; i < 12; i++) {
		var times = "";
		if (month - i <= 0) {
			if (12 + month - i < 10) {
				times = (year - 1) + '-0' + (12 + month - i);
			} else {
				times = (year - 1) + '-' + (12 + month - i);
			}

		} else {
			if (month - i < 10) {
				times = year + '-0' + (month - i);
			} else {
				times = year + '-' + (month - i);
			}
		}
		html += "<option value='" + times + "'>" + times + "</option>";
	}
	$("#date").append(html);
	$("#batchDate").append(html);
}
//根据开通时间获取批次号
function getBatchnum() {
	$.ajax({
		url: DOMIN.MAIN + "/oilCard/batchlinkage",
		type: "post",
		cache: false,
		async: false,
		dataType: "json",
		data: {
			openTime: $("#batchDate").val()
		},
		traditional: true,
		success: function (data) {
			if (data.success) {
				var obj = data.list;
				if (obj.length < 1) {
					$.tip("该月份无开通油卡批次");
					return;
				}
				//处理返回结果
				var html = '<option value="">请选择批次</option>';
				for (var i = 0; i < obj.length; i++) {
					var item = obj[i];
					var key = Object.keys(item);
					html += '<option value="' + key + '">' + item[key] + '</option>';
				}
				$("#batchNum").html(html);
			} else {
				$.tip(data.message);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}

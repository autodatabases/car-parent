var importUserErrList = {};
importUserErrList.refresh = function(){
		_allPages["importUser_paga"].pageNo = 1;
		importUserErrList.dataList();
}
var isload = false;
importUserErrList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/user/queryImportErrList",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["importUser_paga"].pageNo,
			pageSize:_allPages["importUser_paga"].pageSize,
			flag:$("#flag").val(),
			chePai:$.trim($("#chePai").val()),
			realName:$.trim($("#realName").val()),
			phone:$.trim($("#phone").val()),
			baoDan:$.trim($("#baoDan").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					importUserErrList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="15" style="color:#3f51b5;">没有导入保单数据~~</td></tr>');
				}
				// 分页
				setPaginator("importUser_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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
importUserErrList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null||datalist[i][j]=="null"||datalist[i][j]==""){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].realName+'</td>';
		html+='<td>'+datalist[i].phone+'</td>';
		html+='<td>'+datalist[i].chePai+'</td>';
		html+='<td>'+datalist[i].autoBrand+'</td>';
		html+='<td>'+datalist[i].autoType+'</td>';
		html+='<td>'+datalist[i].engineDisp+'</td>';
		html+='<td>'+datalist[i].productYear+'</td>';
		html+='<td>'+datalist[i].cheJia+'</td>';
		if(datalist[i].baoDan=="--"){
			html+='<td>--</td>';
		}else{
			html+='<td style="cursor:pointer;text-decoration:underline" class="orderDetail" index="' + i + '">'+datalist[i].baoDan+'</td>';
		}
		html+='<td>'+datalist[i].baoyangTimes+'</td>';
		html+='<td>'+datalist[i].xiecheTimes+'</td>';
		html+='<td>'+datalist[i].penqiTimes+'</td>';
		if(datalist[i].juan20==0){
			html+="<td>--</td>";
		 }else{
			html+="<td>"+datalist[i].juan20+"</td>";
		 }
		 if(datalist[i].juan50==0){
			 html+="<td>--</td>";
		  }else{
			 html+="<td>"+datalist[i].juan50+"</td>";
		  }
		html+='<td style="color:#F0882C;cursor:pointer" class="editOrder" index="' + i + '">编辑</td>';
		html+='</tr>'
	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
	importUserErrList.operate(datalist);
}
importUserErrList.operate = function(datalist) {
	$(".orderDetail").on("click",function() {
		var i = $(this).attr("index");
		importUserErrList.orderDetail(datalist[i].orderPrice,datalist[i].signTime,datalist[i].startTime,datalist[i].endTime,datalist[i].paymentTime,datalist[i].address,datalist[i].baoDan,datalist[i].source,datalist[i].id);
	})
	$(".editOrder").on("click",function() {
		var i = $(this).attr("index");
		importUserErrList.editOrder(datalist[i].realName,datalist[i].phone,datalist[i].chePai,datalist[i].autoBrand,datalist[i].autoType,datalist[i].engineDisp,datalist[i].productYear,datalist[i].id);
	})
}
importUserErrList.orderDetail = function(orderPrice,signTime,startTime,endTime,paymentTime,address,baoDan,source,infoid) {
	$(".tips-top p").html("保单详情");
	$("#submit").attr('infoid', infoid);
	$("#submit").attr('edittype', 2);
	if (signTime != "--") {
		signTime = $.formatDate(parseInt(signTime)).split(" ")[0];
	}
	if (startTime != "--") {
		startTime = $.formatDate(parseInt(startTime)).split(" ")[0];
	}
	if (endTime != "--") {
		endTime = $.formatDate(parseInt(endTime)).split(" ")[0];
	}
	if (paymentTime != "--") {
		paymentTime = $.formatDate(parseInt(paymentTime)).split(" ")[0];
	}
	var html = "";
	    html += '<p>保单号：<span>' + baoDan + '</span></p>';
	    html += '<p>保单地址：<span>' + address + '</span></p>';
	    html += '<p>保单渠道：<span>' + source + '</span></p>';
		html += '<p>保费收入：<input type="text" value= "' + orderPrice + '" id="dOrderPrice" ></p>';
		html += '<p>签单日期：<input type="text" value= "' + signTime + '" id="dSignTime" onfocus="WdatePicker()"></p>';
		html += '<p>起保日期：<input type="text" value= "' + startTime + '" id="dStartTime" onfocus="WdatePicker()"></p>';
		html += '<p>终止日期：<input type="text" value= "' + endTime + '" id="dEndTime" onfocus="WdatePicker()"></p>';
		html += '<p>收付日期：<input type="text" value= "' + paymentTime + '" id="dPaymentTime" onfocus="WdatePicker()"></p>';
		$("#tips-data").html(html);
	$(".tips-warp").show();
}
importUserErrList.editOrder = function(realName,phone,chePai,autoBrand,autoType,engineDisp,productYear,infoid) {
	$(".tips-top p").html("修改保单信息");
	$("#submit").attr('infoid', infoid);
	$("#submit").attr('edittype', 1);
	var html = "";
	    html += '<p>客户姓名：&nbsp;&nbsp;&nbsp;<input type="text" value= "' + realName + '" id="dRealName"> </p>';
	    html += '<p>绑定手机号：<input type="text" value= "' + phone + '" id="dPhone"> </p>';
	    html += '<p>车牌号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" value= "' + chePai + '" id="dChepai"> </p>';
		html += '<p>汽车品牌：&nbsp;&nbsp;&nbsp;<input type="text" value= "' + autoBrand + '" id="dAutoBrand"> </p>';
		html += '<p>车型：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" value= "' + autoType + '" id="dAutoType"> </p>';
		html += '<p>排量：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" value= "' + engineDisp + '" id="dEngineDisp"> </p>';
		html += '<p>年份：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" value= "' + productYear + ' "id="dProductYear"> </p>';
		$("#tips-data").html(html);
	$(".tips-warp").show();
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
importUserErrList.submitEdit01 = function() {
	if($("#dRealName").val() === "") {
		$.tip("客户姓名不能为空！")
		return;
	}
	if($("#dPhone").val() === "") {
		$.tip("绑定手机号不能为空！")
		return;
	}
	if($("#dChepai").val() === "") {
		$.tip("车牌号不能为空！")
		return;
	}
	if($("#dAutoBrand").val() === "") {
		$.tip("汽车品牌不能为空！")
		return;
	}
	if($("#dAutoType").val() === "") {
		$.tip("车型不能为空！")
		return;
	}
	if($("#dEngineDisp").val() === "") {
		$.tip("排量不能为空！")
		return;
	}
	if($("#dProductYear").val() === "") {
		$.tip("年份不能为空！")
		return;
	}
	var id = $("#submit").attr("infoid");
	var chePai = $.trim($("#dChepai").val());
	var phone = $.trim($("#dPhone").val());
	var realName = $.trim($("#dRealName").val());
	var autoBrand = $.trim($("#dAutoBrand").val());
	var autoType = $.trim($("#dAutoType").val());
	var productYear = $.trim($("#dProductYear").val());
	var engineDisp = $.trim($("#dEngineDisp").val());
	if($("#dRealName").val() === "--") {
		chePai = "";
	}
	var re = /^1\d{10}$/;
	if($("#dPhone").val() === "--") {
		phone = "";
	} else if(!re.test($("#dPhone").val())){
		$.tip("请输入正确格式的手机号码！")
		return;
	}
	if($("#dChepai").val() === "--") {
		realName = "";
	}
	if($("#dAutoBrand").val() === "--") {
		autoBrand = "";
	}
	if($("#dAutoType").val() === "--") {
		autoType = "";
	}
	if($("#dEngineDisp").val() === "--") {
		productYear = "";
	}
	if($("#dProductYear").val() === "--") {
		engineDisp = "";
	}
	$.ajax({
		url: DOMIN.MAIN + "/user/updateCarInfo",
		type: "POST",
		dataType: "json",
		data: {
			id: id,
			chePai: chePai,
			phone: phone,
			realName: realName,
			autoBrand: autoBrand,
			autoType: autoType,
			productYear: productYear,
			engineDisp: engineDisp
		},
		success: function(data) {
			if(data.success) {
				importUserErrList.dataList();
				$.tip("修改成功");
				audit_close();
			} else {
				$.tip(data.message);
			}
		}
	})
}
importUserErrList.submitEdit02 = function() {
	var orderPrice = $("#dOrderPrice").val();
	if($("#dOrderPrice").val() === "") {
		$.tip("保费收入不能为空！")
		return;
	}
	var re = /^[0-9]\d*(\.\d+)?$/;
	if($("#dOrderPrice").val() === "--") {
		orderPrice = "";
	} else if(!re.test($("#dOrderPrice").val())) {
		$.tip("请输入正确格式的保费！")
		return;
	}
	if($("#dSignTime").val() === "" || $("#dSignTime").val() === "--") {
		$.tip("签单日期不能为空！")
		return;
	}
	if($("#dStartTime").val() === "" || $("#dStartTime").val() === "--") {
		$.tip("起保日期不能为空！")
		return;
	}
	if($("#dEndTime").val() === "" || $("#dEndTime").val() === "--") {
		$.tip("终止日期不能为空！")
		return;
	}

	var date01 = new Date($("#dStartTime").val());
	var date02 = new Date($("#dEndTime").val());
	if(date01.getTime() > date02.getTime()-24*60*60*1000) {
		$.tip("终止日期要大于起保日期！！！")
		return;
	}
	if($("#dPaymentTime").val() === "" || $("#dPaymentTime").val() === "--") {
		$.tip("收付日期不能为空！")
		return;
	}
	$.ajax({
		url: DOMIN.MAIN + "/user/updateCarInfoTwo",
		type: "POST",
		dataType: "json",
		data: {
			infoId: $("#submit").attr("infoid"),
			orderPrice: orderPrice,
			signTime: $.trim($("#dSignTime").val()),
			startTime: $.trim($("#dStartTime").val()),
			endTime: $.trim($("#dEndTime").val()),
			paymentTime: $.trim($("#dPaymentTime").val())
		},
		success: function(data) {
			if(data.success) {
				importUserErrList.dataList();
				$.tip("修改成功");
				audit_close();
			} else {
				$.tip(data.message);
			}
		}
	})
}
$(function() {
	initPaginator("importUser_paga", importUserErrList.dataList,1,localStorage.getItem('globalPageSize'));
	importUserErrList.dataList();
	$(".imgUploat span").click(function(){
		$(".search-box .success_span").remove();
		if($("#uploadfile").val()!=""){
			$("#chejiahao").submit();
		}else{
			$.tip('请选择导入文件！');
		}
	});
	$("#import-user-result").load(function(){
		if(document.getElementById("import-user-result").contentWindow.document.body.innerText!=""){
			var val = eval("(" + document.getElementById("import-user-result").contentWindow.document.body.innerText + ")");
			if(val.success){
				var html="<span class='success_span' style='margin-left:30px;'>成功："+val.data.success+"</span>  <span class='success_span'>失败："+val.data.fail+"</span>";
				$(".search-box .imgUploat").append(html);
			}else{
				$.tip(val.message);
			}
			importUserErrList.refresh();
		}
    });

	$("#search").on("click",function(){
		_allPages["importUser_paga"].pageNo = 1;
		importUserErrList.dataList();
	});

/*	$("#match").on("click",function(){
		if(!window.confirm('您确认要验证数据，可能耗时比较长？')){
			return;
		}
		$.ajax({
			url : DOMIN.MAIN +"/user/getMatchChe",
			type : "post",
			processData: true,
			cache : false,
			async : true,
			dataType : "json",
			traditional : true,// 使用传统方式序列化
			success : function(data, textStatus) {
				if (data.success) {
					importUserErrList.refresh();
					$.tip("验证已发送,请稍等!!");
				}else{
					$.tip("验证发送失败");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.tip(errorThrown);
			}
		});
	});*/
	$("#submit").on('click',function() {
		if($(this).attr('edittype') === "1") {
			importUserErrList.submitEdit01();
		} else if($(this).attr('edittype') === "2"){
			importUserErrList.submitEdit02();
		}
	})
});

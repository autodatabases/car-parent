
var carwash_config_cityName = sessionStorage.getItem('carwash_config_cityName');
var priceList = [];
$(document).ready(function(){
	getCity();
	$("#type").change(function(){
		if($(this).val()==1){//宽途洗车
			 $("input[name=countType]").prop('disabled','disabled');
			 $("input[name=countType]:eq(1)").prop('checked','checked');
			 $("#oneTime").prev().html("每年洗车次数：");
			//  $("#totalCount").show();
			//  $("#orderLevel").hide();
		}else{
			 $("input[name=countType]").prop('disabled',false);
			 $("input[name=countType]:eq(0)").prop('checked','checked');
			 $("#oneTime").prev().html("每月洗车次数：");
			//  $("#totalCount").hide();
			//  $("#orderLevel").show();
		}
	});
	$("#type").trigger('change');
	/*
	 * 编辑
	 */
	if (carwash_config_cityName) {
        editChannel();
    }
	//点击渠道列表跳转
	$("#list").click(function(){
		$('.tables').load("/admin/shop/car_wash.html");
	});


	$("input[name=countType]").click(function(){
		if($(this).index() == 1){
			$("#oneTime").prev().html("每月洗车次数：");
			// $("#totalCount").hide();
			// $("#orderLevel").show();
		}else{
			$("#oneTime").prev().html("每年洗车次数：");
			// $("#totalCount").show();
			// $("#orderLevel").hide();
		}
	});

	$("#addPrice").click(function(){
		var min = $("#minPrice").val();
		var max = $("#maxPrice").val();
		var count = $("#oneTime").val();
		if(count == ''){
			$.tip('赠送洗车次数不能为空~~');
			return;
		}
		if(min =='' || max == ''
			|| parseFloat(min)<0 || parseFloat(max) <=0 || parseFloat(min) >= parseFloat(max)){
			$.tip('无效值！');
			return;
		}
		var flag = false;
		if (priceList.length == 0) {
			flag = true;
		}
		for(var i in priceList){//区间重叠判断
			if ((i == 0) && parseFloat(max) <= priceList[i].min) {
				flag = true;
			} else if (i == priceList.length-1 && parseFloat(min) >= priceList[i].max) {
				flag = true;
			}else if (i != priceList.length-1){
				var a = parseInt(i) + 1;
				if (parseFloat(min) >= priceList[i].max && parseFloat(max) <= priceList[a].min){
					flag = true;
				}
			}
		}
		if (flag == false) {
			$.tip("区间重叠");
			return;
		}
		var newPirce = {};
		newPirce.min = parseFloat(min);
		newPirce.max = parseFloat(max);
		newPirce.count = $("#oneTime").val();
		for(var i in priceList) {//数组排序
			if(parseFloat(max) <= priceList[i].min) {
				priceList.splice(i-1,0,newPirce);
				sortAndAppend();
				return;
			}
		}
		priceList.push(newPirce);
		sortAndAppend();
	});



	$("#sub_button").click(function(){
		$("#sub_button").prop("disabled", true);
		//判断渠道、城市
		if($("#city").val()==""){
			$.tip("请选择城市");
			$("#sub_button").prop("disabled", false);
			return;
		}else if($("#type").val()==""){
			$.tip("请选择洗车类型");
			$("#sub_button").prop("disabled", false);
			return;
		}else if(!(/^\d+(\.\d+)?$/.test($("#cPrice").val()))){
			$.tip("请正确填写采购价格");
			$("#sub_button").prop("disabled", false);
			return;
		}else if(!(/^\d+(\.\d+)?$/.test($("#xPrice").val()))){
			$.tip("请正确填写洗车价格");
			$("#sub_button").prop("disabled", false);
			return;
		}
		var countType = $("input[name=countType]:checked").val();
		var dataParam = [];
//		if(countType == 1){
			if(priceList.length<=0){
				$.tip("请正确填写保单区间配置！");
				$("#sub_button").prop("disabled", false);
				return;
			}
			for(var i in priceList){
				var obj = {};
				obj.cityName = $("#city :selected").html();
				obj.washType = $("#type").val();
				obj.carTimes = $.trim($("#XcNum").val());
				obj.purchasePrice = $.trim($("#cPrice").val());
				obj.washPrice = $.trim($("#xPrice").val());
				obj.countType = countType;
				obj.carTimes = priceList[i].count;
				obj.minimum = priceList[i].min;
				obj.maximum = priceList[i].max;
				dataParam.push(obj);
			}
//		}
/*		else{
			if(!(/^\+?[1-9][0-9]*$/.test($("#XcNum").val())) || parseInt($("#XcNum").val()) > 9999){
				$.tip("请正确填写洗车次数");
				$("#sub_button").prop("disabled", false);
				return;
			}
			var obj = {};
			obj.cityName = $("#city :selected").html();
			obj.washType = $("#type").val();
			obj.carTimes = $.trim($("#XcNum").val());
			obj.purchasePrice = $.trim($("#cPrice").val());
			obj.washPrice = $.trim($("#xPrice").val());
			obj.countType = countType;
			obj.carTimes = $.trim($("#XcNum").val());
			obj.minimum = 0;
			obj.maximum = 0;
			dataParam.push(obj);
		}*/

		$.ajax({
			url : DOMIN.MAIN + "/carwashConfig/addOrUpdateWashConfig",
            type: "POST",
            contentType : 'application/json;charset=utf-8', //设置请求头信息
            dataType: "json",
            data: JSON.stringify(dataParam),//将Json对象序列化成Json字符串，JSON.stringify()原生态方法,
            success: function(data){
            	if(data.success){
            		$.tip("操作成功");
            		$('.tables').load("/admin/shop/car_wash.html");
            	}else{
            		$("#sub_button").prop("disabled", false);
            		$.tip(data.message);
            	}
            },
            error: function(res){
            	$.tip("链接服务器失败");
            }
        });
	});
});

function getCity(){
	$.ajax({
		url : DOMIN.MAIN+"/address/queryCityByProvince",
		type : "post",
		cache : false,
		async : false,
		dataType : "json",
		data: {
			provinceId: 20
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				var obj = data.list;
					//处理返回结果
				$("#city").empty();
				$("#city").append('<option value="">选择城市</option>');
				for(var i=0;i<obj.length;i++){
					$("#city").append('<option value="'+obj[i].id+'" text="'+obj[i].regionname+'">'+obj[i].regionname+'</option>');
				}
			}else{
				alert(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
		}
	});
}

function editChannel(){
	$.ajax({
        url: DOMIN.MAIN + "/carwashConfig/getWashConfig",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
        	cityName: carwash_config_cityName
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
            	sessionStorage.removeItem('carwash_config_cityName');
            	if(data.list.length>=0){
            		data.data = data.list[0];
            		 $("#city option[text = '" + data.data.cityName + "']").attr('selected', 'selected');
            		 $("#city").prop('disabled','disabled');
                     $("#type").val(data.data.washType);
                     $("#XcNum").val(data.data.carTimes);
                     $("#cPrice").val(data.data.purchasePrice);
                     $("#xPrice").val(data.data.washPrice);
                     $("input[name=countType]").prop('disabled','disabled');
            		if(data.list[0].countType == 2){
                         $("input[name=countType]:eq(1)").prop('checked','checked');
						 $("#oneTime").prev().html("每年洗车次数：");
                        //  $("#totalCount").show();
            			//  $("#orderLevel").hide();
            		}else{
            			 $("input[name=countType]:eq(0)").prop('checked','checked');
						 $("#oneTime").prev().html("每月洗车次数：");
                        //  $("#totalCount").hide();
            			//  $("#orderLevel").show();
            		}
					 for(var i in data.list){
        				 var one = {};
        				 one.min = data.list[i].minimum;
        				 one.max = data.list[i].maximum;
        				 one.count = data.list[i].carTimes;
        				 priceList.push(one);
        			 }
        			 sortAndAppend();
            	}else{
            		 $.tip("查询信息失败");
            	}

            } else {
                $.tip("查询信息失败");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
        }
    });
}

function sortAndAppend(){
	if(priceList.length==0){
		$("#fwbList").empty();
		return;
	}
	var resultArray = [];
	while(priceList.length>0){
		var min = priceList[0];
		var index = 0;
		for(var i=1;i<priceList.length;i++){
			if(min.min>priceList[i].min){
				min = priceList[i];
				index = i;
			}
		}
		priceList.splice(index,1);
		resultArray.push(min);
	}
	var html = '';
	for(var i in resultArray){
		html += '<tr><td>'+resultArray[i].min+'≤保单金额<'+resultArray[i].max+'</td>'+
				'<td>'+resultArray[i].count+'</td>'+
				'<td onclick="javascript:priceList.splice('+i+',1);sortAndAppend();">删除</td></tr>';
	}
	$("#fwbList").html(html);
	priceList = resultArray;
}

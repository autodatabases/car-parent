
var jilv_config_cityName = sessionStorage.getItem('jilv_config_cityName');
var priceList = [];
$(document).ready(function(){
	getCity();
	/*
	 * 编辑
	 */
	if (jilv_config_cityName) {
        editChannel();
    }
	//点击渠道列表跳转
	$("#list").click(function(){
		$('.tables').load("/admin/shop/jilv_config.html");
	});
	$("#addPrice").click(function(){
		var min = $("#minPrice").val();
		var max = $("#maxPrice").val();
		var count = $("#oneTime").val();
		if(count == ''){
			$.tip('机滤价格不能为空~~');
			return;
		}
		if(min =='' || max == ''
			|| parseFloat(min)<0 || parseFloat(max) <=0 || parseFloat(min) >= parseFloat(max)){
			$.tip('无效值！');
			return;
		}
		// for(var i in priceList){
		// 	if((parseFloat(min) >= priceList[i].min && parseFloat(min) < priceList[i].max) ||
		// 			(parseFloat(max) > priceList[i].min && parseFloat(max) <= priceList[i].max)){
		// 		$.tip('区间重叠');
		// 		return;
		// 	}
		// }
		var flag = false;
		if (priceList.length == 0) {
			flag = true;
		}
		for(var i in priceList){
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
		for(var i in priceList) {
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
		}
		var dataParam = [];
		if(priceList.length<=0){
			$.tip("请正确填写机滤区间配置！");
			$("#sub_button").prop("disabled", false);
			return;
		}
		for(var i in priceList){
			var obj = {};
			obj.cityName = $("#city :selected").html();
			obj.jilvPrice = priceList[i].count;
			obj.min = priceList[i].min;
			obj.max = priceList[i].max;
			dataParam.push(obj);
		}
		$.ajax({
			url : DOMIN.MAIN + "/jilvconfig/addconfig",
            type: "POST",
            contentType : 'application/json;charset=utf-8', //设置请求头信息
            dataType: "json",
            data: JSON.stringify(dataParam),//将Json对象序列化成Json字符串，JSON.stringify()原生态方法,
            success: function(data){
            	if(data.success){
            		$.tip("操作成功");
            		$('.tables').load("/admin/shop/jilv_config.html");
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
        url: DOMIN.MAIN + "/jilvconfig/querybycity",
        type: "post",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: {
        	cityName: jilv_config_cityName
        },
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
            	sessionStorage.removeItem('jilv_config_cityName');
            	if(data.list.length>=0){
            		data.data = data.list[0];
            		 $("#city option[text = '" + data.data.cityName + "']").attr('selected', 'selected');
            		 $("#city").prop('disabled','disabled');
					 for(var i in data.list){
        				 var one = {};
        				 one.min = data.list[i].min;
        				 one.max = data.list[i].max;
        				 one.count = data.list[i].jilvPrice;
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
		html += '<tr><td>'+resultArray[i].min+'≤购置价<'+resultArray[i].max+'</td>'+
				'<td>'+resultArray[i].count+'</td>'+
				'<td onclick="javascript:priceList.splice('+i+',1);sortAndAppend();">删除</td></tr>';
	}
	$("#fwbList").html(html);
	priceList = resultArray;
}

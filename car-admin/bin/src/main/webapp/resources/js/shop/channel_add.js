var minVals = new Array();// 创建数组 jy
var allVals = new Array();// 创建数组

var minValsBY = new Array();// 创建数组 fwb
var allValsBY = new Array();// 创建数组

var finallyData = new Array();// 创建数组

var carTypeArr = new Array();// 车型数组
/**
 * 编辑
 * @returns
 */
var channel = getCookie('channel');
var cityName = getCookie('cityName');


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

function showCarType(carTypeArr){
	if(carTypeArr.length>0){
		$(".carType1").show();
		var html="<ul>";
		for(var i=0;i<carTypeArr.length;i++){
			html += "<li><span>"+carTypeArr[i]+"</span>";
			html += "<span class='del'>X</span></li>";

			if((i+1)%3 == 0){
				html += "</ul><ul>";
			}
		}
		html +="</ul>"
		$("#carTypeList").html(html);

		$(".del").click(function(){
			var value = $(this).prev().text();
			carTypeArr.splice($.inArray(value, carTypeArr), 1);
			showCarType(carTypeArr);
		});
	}else{
		$(".carType1").hide();
		$("#carTypeList").empty();
	}
}
$(document).ready(function(){
	getCity();
	/*
	 * 编辑
	 */
	if (channel!=""&&cityName!="") {
        editChannel();
    }
	//点击渠道列表跳转
	$("#list").click(function(){
		$('.tables').load("/admin/shop/channel_list.html");
	});

	$("#jifen").change(function(){
		if($(this).is(':checked')){
			$("#scoreDd").css("display","block");
		}else{
			$("#scoreDd").css("display","none");
		}
	});

	$("#fwb").change(function(){
		if($(this).is(':checked')){
			$("#fwBaoDl").css("display","block");
		}else{
			$("#fwBaoDl").css("display","none");
		}
	});

	$("#jiyouAdd").click(function(){
		$("#minVal").val("");
		$("#maxVal").val("");
		$("#jiyou_type").val("");
		$(".tips-warp").css("display","block");
	});

	$("#addFwb").click(function(){
		var carType="";
		for(var i=0;i<carTypeArr.length;i++){
			carType+=carTypeArr[i]+",";
		}
		carType=carType.substring(0,carType.length-1);
		new addDataBY($("#minValFwb").val(),$("#maxValFwb").val(),
				$("#ByNum").val(),$("#XcNum").val(),$("#PqNum").val(),
				$("#DpNum").val(),carType);
	});
	$("#ByNum").blur(function(){
		var num = $.trim($("#ByNum").val());
		if(Number(num)>0){
			$(".carType").show();
		}else{
			$(".carType").hide();
		}
	});
	$("#addCarType").click(function(){
		var carType = $.trim($("#ByCarType").val());
		if("全部" == carType){
			$("#carTypeList").empty();
			carTypeArr.splice(0,carTypeArr.length);
			carTypeArr.push("全部");
			showCarType(carTypeArr);
			return;
		}
		if(!/^[\u4E00-\u9FA5]+\-[\u4E00-\u9FA5]+$/.test(carType)){
			$.tip("车型填写格式不对~");
			return;
		};
		$.ajax({
			url : DOMIN.MAIN + "/autoinfo/queryAutoInfoByCondition",
			type : "post",
			processData: true,
			cache : false,
			async : true,
			dataType : "json",
			data :{
				carType:carType
			},
			traditional : true,// 使用传统方式序列化
			success : function(data, textStatus) {
				if (data.success) {
					if(data.data){
						$("#carTypeList").empty();
						if(carTypeArr.indexOf("全部") !=-1 ){
							$.tip("请移除全部品牌");
							showCarType(carTypeArr);
							return;
						}
						if(carTypeArr.indexOf(carType)==-1){
							carTypeArr.push(carType);
						}
						showCarType(carTypeArr);
						$("#ByCarType").val("");
						$("#ByCarType").focus();
					}else{
						$.tip("查不到该车型!!");
					}
				}else{
					$.tip(data.message);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
	            $.tip(errorThrown);
	        }
		});
	});
	$("#sub_button").click(function(){
		$("#sub_button").prop("disabled", true);
		//判断渠道、城市
		if($("#channel").val()==""){
			$.tip("请填写渠道名称");
			$("#sub_button").prop("disabled", false);
			return;
		}else if($("#city").val()==""){
			$.tip("请选择城市");
			$("#sub_button").prop("disabled", false);
			return;
		}else if($("#jifen").is(':checked')&&!(/^\d+(\.\d{1})?$/.test($("#score").val()))){
			$.tip("积分规则格式不正确");
			$("#sub_button").prop("disabled", false);
			return;
		}else if($("#fwb").is(':checked')&&finallyData==""){
			$.tip("请填写服务包赠送规则");
			$("#sub_button").prop("disabled", false);
			return;
		}

		if(!$("#jifen").is(':checked')){
			$("#score").val("");
		}
		if(!$("#fwb").is(':checked')){
			finallyData=[{cityName:$("#city").find("option:selected").text()
							,channel:$("#channel").val(),score:$("#score").val()
							,ruleType:"-1",minimum:""
							,maximum:"",ruleValue:""}];
		}else{
			//选中服务包规则时 重组数据  校验是否有服务包规则
			var sign = 0;
			for (x in finallyData){
				if(finallyData[x].ruleType==0){
					sign = 1;//有一个规则即可
				}
				finallyData[x]["cityName"]=$("#city").find("option:selected").text();
				finallyData[x]["channel"]=$("#channel").val();
				finallyData[x]["score"]=$("#score").val();
			}
			if(sign==0){
				$.tip("请填写服务包赠送规则");
				$("#sub_button").prop("disabled", false);
				return;
			}
		}
		//排序
		finallyData.sort(function(a,b){
            return a.minimum-b.minimum});
		$.ajax({
			url : DOMIN.MAIN + "/scoreChannel/updateScoreChannel",
            type: "POST",
            contentType : 'application/json;charset=utf-8', //设置请求头信息
            dataType:"json",
            data:JSON.stringify(finallyData),//将Json对象序列化成Json字符串，JSON.stringify()原生态方法
            success: function(data){
            	if(data.success){
            		$.tip("操作成功");
            		$('.tables').load("/admin/shop/channel_list.html");
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

function audit_close_del(){//关闭弹窗
    $(".tips-warp-del").css("display","none");
}

function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}

function audit_button(){
	new addData($("#minVal").val(),$("#maxVal").val(),$("#jiyou_type").val(),$("#jiyou_type").find("option:selected").text());
}


function addData(minValue, maxValue, jiyouId,jiyou) {
	this.data;
	this.type=1;
	this.minValue = minValue;
	this.maxValue = maxValue;
	this.jiyou = jiyou;
	this.jiyouId = jiyouId;
	this.minVals=minVals;
	this.allVals = allVals;

	if (this.checkData()) {
		this.initElements();
		this.initEvents();
		this.addData();
		audit_close();// 对弹出框操作。。。
	}
}

addData.prototype = {
	constructor : addData,
	/*
	 * 校验整体数据
	 */
	checkData : function() {
		if (!(/^[0-9]+$/.test(this.minValue))
				|| !(/^[0-9]+$/.test(this.maxValue))) {// 数字都是数字
			$.tip("金额格式不对,请正确填写");
			return false;
		} else if (this.minValue - 0 >= this.maxValue - 0) {// 小的小于大的
			$.tip("金额区间不对,请正确填写");
			return false;
		} else if (this.jiyouId == "") {
			$.tip("请选择机油类型");
			return false;
		} else if (!this.checkRegion(this.minValue - 0, this.maxValue - 0)) {
			$.tip("金额区间有重叠,请正确填写");
			return false;
		}
		return true;
	},
	/*
	 * 校验区间
	 */
	checkRegion : function(minNum, maxNum) {// allVals: {10,20, 25,30, 35,40}
		if (this.allVals.length > 0) {// 判断小值
			this.allVals.sort(sortNumber);
			var minIndex = $.inArray(minNum + "", this.allVals);// 小值是否在数组内
			if (minIndex - 0 >= 0) {// 包含
				if (minIndex % 2 == 0) {// 落在偶数位
					return false;
				} else if (this.allVals[minIndex + 1] - 0 < maxNum - 0) {// 落在奇数位，判断区间长度
																	// 剩余长度小于预期
					return false;
				}
				return true;
			} else {// 不包含 minIndex=-1;
				for (x in this.allVals) {
					if (minNum - 0 < this.allVals[x] - 0) {
						minIndex = x;
						break;
					}
				}
				if (minIndex == -1) {// 最小值未落在当前已有区间，最小值大于当前最大值
					return true;
				} else if (!(minIndex % 2 == 0)) {// 落在奇数位
					return false;
				} else if (this.allVals[minIndex] - 0 < maxNum - 0) {// 落在偶数位，判断区间长度  剩余长度小于预期
					return false;
				}
				return true;
			}
		}
		return true;
	},
	/**
	 * 初始化所有元素
	 */
	initElements : function() {
		this.father=$("#jiyouList");
		this.tr = $("<tr/>");
		this.td1 = $("<td/>");
		this.td1.text(this.minValue + "-" + this.maxValue);
		this.td2 = $("<td/>");
		this.td2.text(this.jiyou);
		this.td3 = $("<td/>");
		this.delBtn = $("<span>x</span>");
		this.td3.append(this.delBtn);
		this.tr.append(this.td1).append(this.td2).append(this.td3);
	},
	/*
	 * 初始化事件绑定
	 */
	initEvents : function() {
		var that = this;
		this.delBtn.on("click", function(event) {
			that.delData();
		});
	},
	/*
	 * 添加数据
	 */
	addData : function() {
		var index = this.getIndex(this.minValue);
		if (index == 0) {
			this.father.prepend(this.tr);
		} else {
			this.father.find("tr").eq(index - 1).after(this.tr);
		}
		this.minVals.push(this.minValue);
		this.allVals.push(this.minValue);
		this.allVals.push(this.maxValue);
		//组装数据
		if(this.type==1){//机油规则
			this.data={ruleType:this.type,minimum:this.minValue,maximum:this.maxValue,ruleValue:this.jiyouId};
			//finallyData.push({type:this.type,minVal:this.minValue,maxVal:this.maxValue,ruleValue:this.jiyou});
		}else if(this.type==0){//小保养规则
			this.data={ruleType:this.type,minimum:this.minValue,maximum:this.maxValue,ruleValue:this.by+","+this.pq+","+this.xc+","+this.dp,carType:this.carType};
			//finallyData.push({type:this.type,minVal:this.minValue,maxVal:this.maxValue,ruleValue:this.by+","+this.pq+","+this.xc+","+this.dp});
		}
		finallyData.push(this.data);
	},
	/*
	 * 确定新加数据位置
	 */
	getIndex : function(val) {
		this.minVals.sort(sortNumber);
		if (this.minVals.length > 0) {
			for (x in this.minVals) {
				if (val - 0 < this.minVals[x]) {
					return x;
				}
			}
			return this.minVals.length;
		}
		return 0;
	},
	/*
	 * 定义删除操作
	 */
	delData : function() {
		this.tr.remove();
		this.minVals.splice($.inArray(this.minValue, this.minVals), 1);
		this.allVals.splice($.inArray(this.minValue, this.allVals), 1);
		this.allVals.splice($.inArray(this.maxValue, this.allVals), 1);

		//alert(JSON.stringify(this.data));
		finallyData.splice($.inArray(this.data, finallyData), 1);

		/*$(".tips-warp-del").css("display","block");
		var t = this;
		$("#audit_button_del").click(function(){
			audit_button_del(t,minVals,allVals);
		});*/
	}
}

/*function audit_button_del(obj,minVals,allVals){
	obj.tr.remove();
	minVals.splice($.inArray(obj.minValue, minVals), 1);
	allVals.splice($.inArray(obj.minValue, allVals), 1);
	allVals.splice($.inArray(obj.maxValue, allVals), 1);
	$(".tips-warp-del").css("display","none");
}*/
//按大小 排序
function sortNumber(a, b) {
	return a - b
}





function extend(subClass, superClass) {
	var F = function() {
	};
	F.prototype = superClass.prototype;
	//子类的prototype指向F的_proto_ ， _proto_又指向父类的prototype
	subClass.prototype = new F();
	//在子类上存储一个指向父类的prototype的属性，便于子类的构造方法中与父类的名称解耦 使用subClass.superClass.constructor.call代替superClass.call
	subClass.superClass = superClass.prototype;
}

/*
 * 添加小保养服务包添加方法   继承机油规则 （区间验证，获取插入下标，添加删除方法）
 */
function addDataBY(minValue, maxValue, by, xc, pq, dp,carType) {
	this.data;
	this.type=0;
	this.minValue = minValue;
	this.maxValue = maxValue;
	this.by = by;
	this.xc = xc;
	this.pq = pq;
	this.dp = dp;
	this.dp = dp;
	this.carType = carType;
	this.minVals = minValsBY;
	this.allVals = allValsBY;
	if (this.checkData()) {
		this.initElements();
		this.initEvents();
		this.addData();
		clearFwb();//
	}
}
extend(addDataBY, addData);
/*
 * 重写  数据校验、 初始化对象
 */
addDataBY.prototype.checkData = function() {
	if (!(/^[0-9]+$/.test(this.minValue)) || !(/^[0-9]+$/.test(this.maxValue))) {//数字都是数字
		$.tip("金额格式不对,请正确填写");
		return false;
	} else if (this.minValue - 0 >= this.maxValue - 0) {//小的小于大的
		$.tip("金额区间不对,请正确填写");
		return false;
	} else if (!this.checkRegion(this.minValue - 0, this.maxValue - 0)) {
		$.tip("金额区间有重叠,请正确填写");
		return false;
	}else if(!(/^[0-9]+$/.test(this.by))){
		$.tip("小保养次数格式不对,请正确填写");
		return false;
	}else if(!(/^[0-9]+$/.test(this.xc))){
		$.tip("洗车次数格式不对,请正确填写");
		return false;
	}else if(!(/^[0-9]+$/.test(this.pq))){
		$.tip("喷漆次数格式不对,请正确填写");
		return false;
	}else if(!(/^[0-9]+$/.test(this.dp))){
		$.tip("更换电瓶次数格式不对,请正确填写");
		return false;
	} else if (this.by > 0) {
		if (this.carType == "") {
			$.tip("请填写指定车型");
			return false;
		}
	}
	return true;
}
addDataBY.prototype.initElements = function() {
	this.father = $("#fwbList");
	this.tr = $("<tr/>");
	this.td1 = $("<td/>");
	this.td1.text(this.minValue+"≤保单金额<"+this.maxValue);
	this.td2 = $("<td/>");
	this.td2.text(this.by);
	this.td3 = $("<td/>");
	this.td3.text(this.carType);
	this.td4 = $("<td/>");
	this.td4.text(this.xc);
	this.td5 = $("<td/>");
	this.td5.text(this.pq);
	this.td6 = $("<td/>");
	this.td6.text(this.dp);
	this.td7 = $("<td/>");
	this.delBtn = $("<span>x</span>");
	this.td7.append(this.delBtn);
	this.tr.append(this.td1).append(this.td2).append(this.td3)
	.append(this.td4).append(this.td5).append(this.td6).append(this.td7);
}

function clearFwb(){
	$("#minValFwb").val("");
	$("#maxValFwb").val("");
	$("#ByNum").val("");
	$("#XcNum").val("");
	$("#PqNum").val("");
	$("#DpNum").val("");
	$("#ByCarType").val("");
	carTypeArr.splice(0,carTypeArr.length);
	$(".carType").hide();
	$(".carType1").hide();
	$("#carTypeList").empty();
}

var jiyouListData=["","金嘉护10W-40","新磁护5W-40","极护5W-40"];
function editChannel(){
	$.ajax({
        url: DOMIN.MAIN + "/scoreChannel/queryScoreChannel",
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
            	delCookie('channel');
                delCookie('cityName');
            	if(data.list!=null&&data.list.length>0){
            		$("#channel").val(data.list[0].channel);
            		//设置城市的值
            		$("#city option[text='"+data.list[0].cityName+"']").attr("selected","selected");
            		//设置渠道城市不能编辑
            		$("#channel").attr("disabled","disabled");
            		$("#city").attr("disabled","disabled");
            		if(data.list[0].score!=""){
            			//设置积分规则选中
            			$("#jifen").attr("checked", true);
            			$("#scoreDd").css("display","block");
            			//设置积分的值
            			$("#score").val(data.list[0].score);
            		}
            		//先设置服务包规则选中
            		$("#fwb").attr("checked", true);
            		$("#fwBaoDl").css("display","block");
            		for (var i = 0; i < data.list.length; i++) {
						if(data.list[i].ruleType==1){
							new addData(data.list[i].minimum, data.list[i].maximum, data.list[i].ruleValue,jiyouListData[data.list[i].ruleValue])
						}else if(data.list[i].ruleType==0){
							var val = data.list[i].ruleValue.split(",");
							new addDataBY(data.list[i].minimum, data.list[i].maximum, val[0],val[2],val[1],val[3], data.list[i].carType);
						}else{
							//取消选中
							$("#fwb").attr("checked", false);
							$("#fwBaoDl").css("display","none");
						}
					}
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

var productList = {};
productList.refresh = function(){
	productList.dataList();
}
var isload = false;
//获取数据
productList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	var data =$.parseForm($(".productList-form"));
	var postData = $.extend(data,
			{pageNo:_allPages["oilcard_paga"].pageNo,pageSize:_allPages["oilcard_paga"].pageSize});

	$.ajax({
		url : DOMIN.MAIN + "/virtualtopup/queryVirtualTopupList",
		type : "GET",
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
					productList.renderHtml(returnObj.list);
				}else{
					$(".tab-list-send tbody").append('<tr><td colspan="9" style="color:#e91e63;">暂无商品数据~~</td></tr>');
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
productList.renderHtml = function(datalist){
	var statusLabel = {
		1: "上架",
		2: "下架",
		3: "库存不足",
		4: "系统维护"
	}
	var orderStatusLabel = {
		0: "待处理",
		1: "处理中",
		2: "充值成功",
		3: "部分成功",
		4: "充值失败",
	}
    $.each(datalist,function(i,e) {
    	if(e.status!=null){
    		e.statusLabelText = statusLabel[e.status];
    	}else{
    		e.statusLabelText = '--';
    	}
    });
	var html="";
	var isOneStatus = 1;
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j] === null || datalist[i][j] === ""){
				datalist[i][j] = '--';
			}
		}
		isOneStatus = datalist[i].oneStatus;
		html+="<tr>";
		html+="<td>"+datalist[i].productId+"</td>";
		html+="<td>"+datalist[i].typeName+"</td>";
		html+="<td>"+datalist[i].goodsName+"</td>";
		html+="<td>"+datalist[i].sale+"</td>";
		html+="<td>"+datalist[i].denomination+"</td>";
		if(datalist[i].oneStatus==2){
			html+="<td>下架</td>";
		}else{
			html+="<td>"+datalist[i].statusLabelText+"</td>";
		}
		if(datalist[i].createTime === '--') {
			html+="<td>--</td>";
		} else {
			html+="<td>"+$.formatDate(datalist[i].createTime)+"</td>";
		}
		if(datalist[i].updateTime === '--') {
			html+="<td>--</td>";
		} else {
			html+="<td>"+$.formatDate(datalist[i].updateTime)+"</td>";
		}
			html+="<td>";
		if(datalist[i].oneStatus==2){
			html+='<a href="javascript:void(0)" class="operate" data-type="updateProduct" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >更改信息</a>';
			html+="</td>";
			html+="<td>";
			html+='<a href="javascript:void(0)" class="operate" data-type="onShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >上架</a>';
			html+='<a href="javascript:void(0)" class="operate" data-type="downShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >下架</a>';
			html+='<a href="javascript:void(0)" class="operate" data-type="stockUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >库存不足</a>';
			html+='<a href="javascript:void(0)" class="operate" data-type="systemUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'">系统维护</a>';
		}else{
			html+= datalist[i].status==1?'<a href="javascript:void(0)" class="operate" data-type="updateProduct" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >更改信息</a>':'<a href="javascript:void(0)" class="operate actived" data-type="updateProduct" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >更改信息</a>';
			html+="</td>";
			html+="<td>";
			//商品状态：1，上架，2，下架，3，库存不足，4，系统维护
			if(datalist[i].status==1){
				html+='<a href="javascript:void(0)" class="operate" data-type="onShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >上架</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="downShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >下架</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="stockUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >库存不足</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="systemUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'">系统维护</a>';
			}else if(datalist[i].status==2){
				html+='<a href="javascript:void(0)" class="operate actived" data-type="onShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >上架</a>';
				html+='<a href="javascript:void(0)" class="operate" data-type="downShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >下架</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="stockUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >库存不足</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="systemUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'">系统维护</a>';
			}else if(datalist[i].status==3){
				html+='<a href="javascript:void(0)" class="operate actived" data-type="onShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >上架</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="downShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >下架</a>';
				html+='<a href="javascript:void(0)" class="operate" data-type="stockUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >库存不足</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="systemUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'">系统维护</a>';
			}else if(datalist[i].status==4){
				html+='<a href="javascript:void(0)" class="operate actived" data-type="onShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >上架</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="downShelf" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >下架</a>';
				html+='<a href="javascript:void(0)" class="operate actived" data-type="stockUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >库存不足</a>';
				html+='<a href="javascript:void(0)" class="operate" data-type="systemUpdate" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'">系统维护</a>';
			}			
		}

		html+="</td>";
		html+="<td>";
		if(datalist[i].flag==true){
			html+='<a href="javascript:void(0)" class="operate actived" data-type="cityLocation" data-productId="'+datalist[i].productId+'" data-id="'+datalist[i].id+'" >查看</a>';
		}
		html+="</td>";
		html+="</tr>"
	}
	if(isOneStatus==1){
		$(".productList-form .shelves-btn span").attr("data-oneStatus",isOneStatus).text("一键下架");
	}else{
		$(".productList-form .shelves-btn span").attr("data-oneStatus",isOneStatus).text("一键恢复");
	}
	$(".tab-list-send tbody").append(html);
	bindlistEvent();
}
$(function() {
	initPaginator("oilcard_paga", productList.dataList,1,localStorage.getItem('globalPageSize'));
	productList.dataList();
	bindlistEvent();
});
//绑定事件
function bindlistEvent(){
	$("#add_product").off("click").on("click",function(){
		$('.tables').load('product/product-add.html');
	});
    $("#category_Manager").off("click").on("click",function(){
		$('.tables').load('product/categoryManager.html');
	});
	$(".search-box #productList-search").off("click").on("click",function(){//根据查询条件查询
	    _allPages["oilcard_paga"].pageNo = 1;
		productList.dataList();
	});
	//一键上下架
	$(".search-box .shelves-btn").off("click").on("click",function(e){
		console.log($(e.target))
		var onestatusLabel = $(e.target).attr("data-onestatus")=="1"?"下架":"恢复";
		var str ='是否将所有商品都'+onestatusLabel;
			$("#product-list-pop .tips-center").hide();
			$("#product-list-pop .tips-center-text").show();
		    $("#product-list-pop .tips-center-text p").empty().html(str);
		    $("#product-list-pop .tips-center-text .hideData").empty().html($(e.target).clone(true))
			$("#product-list-pop").show();
	});
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
			var pid =  $operateTarget.attr("data-productid");
			switch(operateType){
				case "onShelf":
					var postData ={id:Id,productId:pid,status:1};
					updateproductStatus(postData);
				break;
				case "downShelf":
					var postData ={id:Id,productId:pid,status:2};
					updateproductStatus(postData);
				break;
				case "stockUpdate":
					var postData ={id:Id,productId:pid,status:3};
					updateproductStatus(postData);
				break;
				case "systemUpdate":
					var postData ={id:Id,productId:pid,status:4};
					updateproductStatus(postData);
				break;
				case "updateProduct":
					var postData ={id:Id,productId:pid};
					updateProduct(postData);
				break;
			}
		}else{
			var allstatusSet = $("#product-list-pop .hideData span").attr("data-onestatus")=="1"?2:1;
			updateproductStatus({oneStatus:allstatusSet});
		}

	});
	//列表操作
	$(".operate.actived").off("click").on("click",function(e){
		var operateType = $(e.target).attr("data-type");
		switch(operateType){
			//更新商品
			case "updateProduct":
	            var str = '<div class="formTable">'
		                + '<label>商品名称：</label>'
		                + '<input type="text" class="goodsName" data-valide="true" name="goodsName">'
		                + '</div>'
		                + '<div class="formTable">'
		                + '<label>商品实际价格：</label>'
		                + '<input type="number" class="sale"  data-valide="true" name="sale">'
		                + '</div>'
		                + '<div class="formTable">'
		                + '<label>商品规格：</label>'
		                + '<input type="text" class="denomination"  data-valide="true" name="denomination">'
		                + '</div>';
	            $("#product-list-pop .tips-center").show();
	            $("#product-list-pop .tips-center-text").hide();
	            $('#product-list-pop .tips-center form').empty().html(str);
	            $("#product-list-pop .tips-center-text .hideData").empty().html($(e.target).clone(true))
           		$("#product-list-pop .tips-center form input[name='goodsName']").val($(e.target).parents("tr").find("td:nth-child(3)").text());
           		$("#product-list-pop .tips-center form input[name='sale']").val($(e.target).parents("tr").find("td:nth-child(4)").text());
           		$("#product-list-pop .tips-center form input[name='denomination']").val($(e.target).parents("tr").find("td:nth-child(5)").text());
           		$("#product-list-pop").show();
			break;
			case "onShelf":
				var str ='是否确定上架该商品';
				$("#product-list-pop .tips-center").hide();
				$("#product-list-pop .tips-center-text").show();
			    $("#product-list-pop .tips-center-text p").empty().html(str);
			    $("#product-list-pop .tips-center-text .hideData").empty().html($(e.target).clone(true))
				$("#product-list-pop").show();
			break;
			case "downShelf":
				var str ='是否确定下架该商品';
				$("#product-list-pop .tips-center").hide();
				$("#product-list-pop .tips-center-text").show();
			    $("#product-list-pop .tips-center-text p").empty().html(str);
			    $("#product-list-pop .tips-center-text .hideData").empty().html($(e.target).clone(true))
				$("#product-list-pop").show();
			break;
			case "stockUpdate":
				var str ='是否将商品状态改为库存不足';
				$("#product-list-pop .tips-center").hide();
				$("#product-list-pop .tips-center-text").show();
			    $("#product-list-pop .tips-center-text p").empty().html(str);
			    $("#product-list-pop .tips-center-text .hideData").empty().html($(e.target).clone(true))
				$("#product-list-pop").show();
			break;
			case "systemUpdate":
				var str ='是否将商品状态改为系统维护';
				 $("#product-list-pop .tips-center").hide();
				$("#product-list-pop .tips-center-text").show();
			    $("#product-list-pop .tips-center-text p").empty().html(str);
			    $("#product-list-pop .tips-center-text .hideData").empty().html($(e.target).clone(true))
				$("#product-list-pop").show();
			break;
			case "cityLocation":
				var dId = $(e.target).attr("data-id");
				sessionStorage.setItem('goodId',dId);
				$('.tables').load('product/cityLocation.html');
			break;
		}
	});
}
//更新商品信息
function updateProduct(postData){
	var RegExpString = /^((?:[1-9]\d{0,5}(?:\.\d{1,2})?)|(0\.(0[1-9]|[1-9][0-9]{0,1}))  )$/,
    	RegExpSku = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/;
    var inputs = $("#product-list-pop form").find("input"),
        isUpdateValid = true;
    $.each(inputs, function (index, input) {
    	var toastMessage = $(input).prev().text();
    	if($(input).attr("data-valide")&&$(input).val()==""){
    		$.tip((toastMessage+"不能为空！").replace(/：/g, ""));
            isUpdateValid = false;
            return false;
    	}else{
    		var valideName = $(input).attr("name");
    		switch(valideName){
    			case "sale":
	    			if(!RegExpString.test($(input).val())){
				     	$.tip(("请输入正确的"+toastMessage).replace(/：/g, ""));
				     	isUpdateValid = false;
	        			return false;
				    }
    			break;
    			case "denomination":
	    			if(!RegExpSku.test($(input).val())){
	    				$.tip(("请输入正确的"+toastMessage).replace(/：/g, ""));
				     	isValid = false;
	        			return false;
				    }
    			break;
    		}
    	}
    });
    if(!isUpdateValid) return;
    var load = $.loading();
	isload = true;
    var data =$.parseForm($("#product-list-pop form"));
	var postparams = $.extend(data,postData);
	$.ajax({
		url : DOMIN.MAIN + "/virtualtopup/updateOilGoods",
		type : "POST",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :postparams,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			if (data.success) {
				//操作的
				$('.tables').load('product/product-list.html');
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
//更新商品状态
function updateproductStatus(data){
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/virtualtopup/updateOilGoodsStatus",
		type : "POST",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :data,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			if (data.success) {
				$('.tables').load('product/product-list.html');
				$.tip("操作成功！");
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
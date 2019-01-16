$(function() {
	//获取数据
	getType(1,{parentId:""});
	$("#productAdd_button").off("click").on("click",function(){
    	productAdd();
    });
    $("#product_list").off("click").on("click",function(){
		$('.tables').load('product/product-list.html');
	});
    $("#category_Manager").off("click").on("click",function(){
		$('.tables').load('product/categoryManager.html');
	});
});
//获取分类
function getType(typeLevel,postData){
	$.ajax({
		url : DOMIN.MAIN + "/virtualtopup/queryOilGoodsType",
		type : "GET",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :postData,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				var returnObj = data.list||[];
				if(returnObj.length>0){
					$(".productAdd-form select").removeClass("typeEnd");
					renderTypeHtml(typeLevel,returnObj);
				}else{
					$.tip("暂无类目数据，请先添加类目！");
				}
			}else{
				if((data.message).indexOf("没有下一级商品类目信息了")!=-1){
					$(".productAdd-form #typeName"+typeLevel).empty().hide();
					var isHasNext = typeLevel-1;
					$(".productAdd-form #typeName"+isHasNext).addClass("typeEnd").siblings().removeClass("typeEnd");
				}else{
					$.tip(data.message);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
//分类的选择
function renderTypeHtml(typeLevel,record){
	if(typeLevel==1){
		var html="";
		for(var i=0;i<record.length;i++){
			html+="<option value=''>--请选择--</option>";
			html+="<option data-parent='"+record[i].parentId+"' data-typeGrade='"+record[i].typeGrade+"' value='"+record[i].id+"'>"+record[i].typeName+"</option>";
		}
		$(".productAdd-form #typeName1").empty().append(html);
		$(".productAdd-form #typeName2").empty().hide();
		$(".productAdd-form #typeName3").empty().hide();
	}
	if(typeLevel==2){
		var html="";
			html+="<option value=''>--请选择--</option>";
		for(var i=0;i<record.length;i++){
			html+="<option data-parent='"+record[i].parentId+"' data-typeGrade='"+record[i].typeGrade+"' value='"+record[i].id+"'>"+record[i].typeName+"</option>";
		}
		$(".productAdd-form #typeName2").empty().append(html).show();
		$(".productAdd-form #typeName3").empty().hide();
	}
	if(typeLevel==3){
		var html="";
			html+="<option value=''>--请选择--</option>";
		for(var i=0;i<record.length;i++){
			html+="<option data-parent='"+record[i].parentId+"' data-typeGrade='"+record[i].typeGrade+"' value='"+record[i].id+"'>"+record[i].typeName+"</option>";
		}
		$(".productAdd-form #typeName3").empty().append(html).show();
	}
	bindEvent()
}
function bindEvent(){
	$("#typeName1").off("change").on("change",function(e){
		if($(e.target).val()!=""){
			getType(2,{parentId:$(e.target).val()});
		}else{
			$.tip("请选择类目！");
		}
	});
	$("#typeName2").off("change").on("change",function(e){
		if($(e.target).val()!=""){
			getType(3,{parentId:$(e.target).val()});
		}else{
			$.tip("请选择类目！");
		}
	});
	$("#typeName3").off("change").on("change",function(e){
		if($(e.target).val()!=""){
			getType(4,{parentId:$(e.target).val()});
		}else{
			$.tip("请选择类目！");
		}
	});
}
//添加商品
function productAdd() {
    $("#productAdd_button").prop("disabled", true).text("提交中...");
	var data = $.parseForm($(".productAdd-form"));
    //类目必须填写
	if($(".typeEnd").length==0){
		$.tip("请选择商品类目！");
		 $(".productAdd_button").prop("disabled", false).text("确定");
		return;
	}
	var RegExpString = /^((?:[1-9]\d{0,5}(?:\.\d{1,2})?)|(0\.(0[1-9]|[1-9][0-9]{0,1}))  )$/;
    var inputs = $(".productAdd-form").find("input"),
    	isValid = true;
    $.each(inputs, function (index, input) {
    	var toastMessage = $(input).prev().text();
    	if($(input).attr("data-valide")&&$(input).val()==""){
    		mui.toast(toastMessage+"不能为空！");
    		$(".productAdd_button").prop("disabled", false).text("确定");
            return false;
    	}else{
    		var valideName = $(input).attr("name");
    		switch(valideName){
    			case "denomination":
    			case "sale":
	    			if(!RegExpString.test($(input).val())){
				     	mui.toast("请输入正确的"+toastMessage);
				     	isValid = false;
	        			return false;
				    }
    			break;
    		}
    	}
    });
    var typesNames =[],
		selectItems = $(".productAdd-form select");
 	$.each(selectItems, function (index, e) {
 		typesNames.push($(this).find("option:selected").text());
 	})
	var postData = $.extend(data,
			{typeName:typesNames.join(">"),parentId:$(".typeEnd option:selected").attr("data-parent")});
    $.ajax({
        url: DOMIN.MAIN + "/virtualtopup/addOilGoods",
        type: "PUT",
        processData: true,
        cache: false,
        async: true,
        dataType: "json",
        data: postData,
        traditional: true, // 使用传统方式序列化
        success: function(data, textStatus) {
            if (data.success) {
                $.tip("操作成功！");
                setTimeout(function() {
                	$('.tables').load('product/product-list.html');
                }, 1000);
            } else {
                $.tip(data.message);
                $(".productAdd_button").prop("disabled", false).text("确定");
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $.tip(errorThrown);
            $(".productAdd_button").prop("disabled", false).text("确定");
        }
    });
}
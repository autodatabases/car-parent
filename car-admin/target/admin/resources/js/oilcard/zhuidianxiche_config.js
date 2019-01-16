var cai_dot_list = {};
cai_dot_list.refresh = function(){
		cai_dot_list.dataList();
}
var isload = false;
var taocanNms = 0;
cai_dot_list.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/washproduct/productlist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo: _allPages["taocan_paga"].pageNo,
			pageSize: _allPages["taocan_paga"].pageSize,
			//dotName: $.trim($("#dotName").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
                    taocanNms = returnObj.list.length;
					cai_dot_list.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="5" style="color:red;">没有洗车套餐数据~~</td></tr>');
				}
				// 分页
				setPaginator("taocan_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

cai_dot_list.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]==null){
				datalist[i][j] = '--';
			}
		}
		html+='<tr>';
		html+='<td>'+datalist[i].id+'</td>';
		html+='<td>'+datalist[i].buyNumber+'次</td>';
		html+='<td>'+datalist[i].presentNumber+'次</td>';
        html+='<td>'+datalist[i].price+'元</td>';
		html+="<td>"+$.formatDate(datalist[i].createTime)+"</td>";
		html+="<td>"+$.formatDate(datalist[i].updateTime)+"</td>";
		html+='<td><a href="javascript:void(0)" onclick=edit_button("'+datalist[i].id+'")>编辑</a>';
		html+='<a href="javascript:void(0)" onclick=del_button("'+datalist[i].id+'")>删除</a></td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
var taocanId = null;
var tips = "";
function add_button(){//新增财险网点弹窗
	if (taocanNms >= 2) {
		$.tip("最多能添加两个套餐！");
		return;
	}
	tips = "新增套餐成功！";
	$(".tips-top p").html("添加或修改洗车套餐");
	$(".ok_btn").attr("class", "ok_btn");
	taocanId = null;
    $("#buyNm").val("");
    $("#preNm").val("");
    $("#price").val("");
	$(".tips-warp").css("display","block");
	$("#idIndex").css("display","none");
	$("#buyNm").attr('disabled', false);
	$("#buyNm").css('background', '#fff');
	$("#preNm").attr('disabled', false);
	$("#preNm").css('background', '#fff');
	$("#buyNm").attr('disabled', false);
	$("#price").css('background', '#fff');
}
function edit_button(num){//修改套餐弹窗
	tips = "修改套餐成功！";
	$(".tips-top p").html("添加或修改洗车套餐");
	$(".ok_btn").attr("class", "ok_btn");
    $(".tips-warp").css("display","block");
    taocanId=num;
    get_taocan();
	$("#buyNm").attr('disabled', true);
	$("#buyNm").css('background', '#ccc');
	$("#preNm").attr('disabled', false);
	$("#preNm").css('background', '#fff');
	$("#buyNm").attr('disabled', false);
	$("#price").css('background', '#fff');
}
function del_button(num){//删除财险网点弹窗
	$(".tips-top p").html("是否删除此套餐 ?");
	$(".ok_btn").attr("class", "ok_btn del");
	$(".tips-warp").css("display","block");
	taocanId=num;
	get_taocan();
	$("#buyNm").attr('disabled', true);
	$("#buyNm").css('background', '#ccc');
	$("#preNm").attr('disabled', true);
	$("#preNm").css('background', '#ccc');
	$("#buyNm").attr('disabled', true);
	$("#price").css('background', '#ccc');
}
function get_taocan() {
	$.ajax({
		url : DOMIN.MAIN + "/washproduct/getproduct",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			id:taocanId
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success){
				$("#buyNm").val(data.data.buyNumber);
				$("#buyNm").attr('disabled', true);
				$("#buyNm").css('background', '#ccc');
				$("#preNm").val(data.data.presentNumber);
                $("#price").val(data.data.price);
			}else{
				$.tip("查询信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(that){
	if (that.getAttribute('class') == "ok_btn") {
		add_edit();
	} else {
		del_taocan();
	}

}
function add_edit() {
	var buyNm = $.trim($("#buyNm").val());
	var reg = /^\+?[1-9][0-9]*$/;
	if (!reg.test(buyNm)) {
		$.tip("请输入正确格式的套餐洗车次数");
		return;
	}
	var reg1 = /^\d+$/;
    var preNm = $.trim($("#preNm").val());
	if (!reg1.test(preNm)) {
		$.tip("请输入正确格式的赠送洗车次数");
		return;
	}
    var price = $.trim($("#price").val());
	var reg2 = /^[1-9]\d*(\.\d+)?$/;
	if (!reg1.test(price)) {
		$.tip("请输入正确格式的套餐价格");
		return;
	}
	var url=DOMIN.MAIN +"/washproduct/editproduct";
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data : {
			id: taocanId,
            buyNumber: $.trim($("#buyNm").val()),
            presentNumber: $.trim($("#preNm").val()),
            price: $.trim($("#price").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				taocanId = null;
                $("#buyNm").val("");
                $("#preNm").val("");
                $("#price").val("");
				$.tip(tips);
				$(".tips-warp").css("display","none");
				cai_dot_list.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function del_taocan(){//删除财险网点弹窗
    $.ajax({
		url : DOMIN.MAIN + "/washproduct/delproduct",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			id:taocanId
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success){
				$.tip("删除套餐成功");
				$(".tips-warp").css("display","none");
				cai_dot_list.refresh();
			}else{
				$.tip("查询信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("taocan_paga", cai_dot_list.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("taocan_paga", cai_dot_list.dataList,1,localStorage.getItem('globalPageSize'));
	}
	cai_dot_list.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["taocan_paga"].pageNo = 1;
		cai_dot_list.dataList();
	});
	$("#add_taocan").click(function(){
		add_button();
	});
});

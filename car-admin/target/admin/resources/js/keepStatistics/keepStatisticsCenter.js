//点击跳转
$("#list").click(function(){
	$('.tables').load("/admin/keepStatistics/keepStatistics.html");
});
//验证码记录
var verification = {};
verification.refresh = function(){
		verification.dataList();
}

$('#exportSelectBtn').click(function(){
	window.open('oilCard/exportoilcardliucun?date=2018-04');
})

var isload = false;
verification.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/oilcardliucun",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["verification_paga"].pageNo,
			pageSize:_allPages["verification_paga"].pageSize,
			date:$.trim($('#userAccounts').val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			console.log(data)
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
                    taocanNms = returnObj.list.length;
					verification.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:red;">没有数据~~</td></tr>');
				}
				// 分页
				setPaginator("verification_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

verification.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td>'+datalist[i].id+'</td>'//序号
        html+='<td>'+datalist[i].userphone+'</td>';//手机号
        html+='<td>'+datalist[i].userphone+'</td>';//手机号
        html+='<td>'+datalist[i].userphone+'</td>';//手机号        
		html+='<td>'+datalist[i].platform+'</td>';  //所属平台
		html+='<td>'+datalist[i].createTime+'</td>';//发送时间
		html+='<td><a href="javascript:void(0)" onclick=edit_button("'+datalist[i].id+'")>修改密码</a></td>';        
		html+='</tr>';
		
	}
	$(".tab-list tbody").append(html);
}
$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("verification_paga", verification.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("verification_paga", verification.dataList,1,localStorage.getItem('globalPageSize'));
	}
	verification.dataList();
	$(".search-box button.search-btn").click(function(){//根据查询条件查询
		_allPages["verification_paga"].pageNo = 1;
		verification.dataList();
	});
});
function edit_button(num){//修改套餐弹窗
	tips = "修改套餐成功！";
	$(".tips-top p").html("添加账号");
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
function audit_button(that){
	if (that.getAttribute('class') == "ok_btn") {
		add_edit();
	} else {
		del_taocan();
	}
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}

function add_edit() {
    var preNm = $.trim($("#preNm").val());
	if (preNm=='') {
		$.tip("请输入修改后的密码！！！");
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



$('#account_button').click(function(){
	$('.tips-warps').css({'display':'block'})
})
function audit_closes(){//关闭弹窗
    $(".tips-warps").css("display","none");
}
function audit_buttonn(that){
	if (that.getAttribute('class') == "ok_btn") {
		add_edity();
	}
}
function add_edity() {
	var buyNm = $.trim($("#buyNm").val());
    var userName = $.trim($("#userName").val());	
	var passWord = $.trim($("#passWord").val());	
	if(userName==''){
		$.tip('用户账号不可为空！')
		return;
	}
	if (!(/^1[34578]\d{9}$/.test(userName))) {
		$.tip("请输入正确的用户账号！");
		return;
	}
	if (buyNm=='') {
		$.tip("请输入公司名称！！！");
		return;
	}
	if (passWord=='') {
		$.tip("请输用户密码！！！");
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
			// id: taocanId,
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
				$(".tips-warps").css("display","none");
				cai_dot_list.refresh();
			}else{
				$.tip(data.message);
			}
			$(".tips-warps").css("display","none");
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
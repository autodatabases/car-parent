//点击跳转
$("#list").click(function(){
	$('.tables').load("/admin/accountSwitches/accountSwitches.html");
});
//验证码记录
var accountNumberAndPassword = {};
accountNumberAndPassword.refresh = function(){
		accountNumberAndPassword.dataList();
}
var isload = false;
accountNumberAndPassword.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilCard/ofuserlist",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["accountNumberAndPassword_paga"].pageNo,
			pageSize:_allPages["accountNumberAndPassword_paga"].pageSize
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			// console.log(data)
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					accountNumberAndPassword.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:red;">没有数据~~</td></tr>');
				}
				// 分页
				setPaginator("accountNumberAndPassword_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

accountNumberAndPassword.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		html+='<tr>';
		html+='<td class="addId">'+datalist[i].id+'</td>';
        html+='<td class="company">'+datalist[i].company+'</td>';;
		html+='<td>'+datalist[i].userName+'</td>';
		html+='<td>'+datalist[i].userPassword+'</td>';
		html+='<td><a href="javascript:void(0)" onclick=add_edite("'+datalist[i].id+'")>修改密码</a></td>';        
		html+='</tr>';
		
	}
	$(".tab-list tbody").append(html);
}



$(function(){
	var counterman_session= sessionStorage.getItem("counterman_session");
	if(counterman_session){
		initPaginator("accountNumberAndPassword_paga", accountNumberAndPassword.dataList,counterman_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("counterman_session");
	}else{
		initPaginator("accountNumberAndPassword_paga", accountNumberAndPassword.dataList,1,localStorage.getItem('globalPageSize'));
	}
	accountNumberAndPassword.dataList();
});

function audit_close(){//关闭弹窗
	$(".tips-warp").css("display","none");
	$("#buyNm").val("");
	$("#userName").val("");
	$("#passWord").val("");
	$("#preNm").val("");	
}
function audit_closes(){//关闭弹窗
	$(".tips-warp").css("display","none");
	$("#buyNm").val("");
	$("#userName").val("");
	$("#passWord").val("");
	$("#preNm").val("");	
}
function audit_buttons(that){
	if (that.getAttribute('class') == "ok_btn") {
		add_edit(that);
	}
}
//修改密码
function add_edite(asd) {
	$(".tips-warp").css("display","block");
	$('.idView').html(asd)
	// console.log($('.idView').html())
}
function add_edit(){
	var preNm = $.trim($("#preNm").val());
	if (preNm=='') {
		$.tip("请输入修改后的密码！！！");
		return;
	}
	var url=DOMIN.MAIN +"/oilCard/updateOilProvider";
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data : {
			providerId: $('.idView').html(),
            userPassword: $.trim($("#preNm").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				taocanId = null;
				// if(!(/[a-zA-Z0-9]{5}/.test($("#preNm").val()))){
				// 	$.tip('请输入正确的密码！！')
				// 	return;
				// }
				if(/\s/.test($("#preNm").val())){
					$.tip('请输入正确的密码！！')
					return;
				}
                $("#buyNm").val("");
                $("#preNm").val("");
				$("#price").val("");
				$.tip('修改成功！！')
				$(".tips-warp").css("display","none");
				accountNumberAndPassword.dataList();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}




//添加账号
$('#account_button').click(function(){
	$('.tips-warps').css({'display':'block'})
})
function audit_closes(){//关闭弹窗
	$(".tips-warps").css("display","none");
	$("#buyNm").val("");
	$("#userName").val("");
	$("#passWord").val("");
}
function audit_button(that){
	if (that.getAttribute('class') == "ok_btn") {
		add_edity();
	}
}
//添加账户
function add_edity() {
	var buyNm = $.trim($("#buyNm").val());
    var userNames = $.trim($("#userName").val());	
	var passWord = $.trim($("#passWord").val());	
	if(userNames==''){
		$.tip('用户账号不可为空！')
		return;
	}
	if(!(/^[A]\w{7}$/.test(userNames))){
		$.tip("请输入正确的用户账号！");
		return
	}
	if (buyNm=='') {
		$.tip("请输入公司名称！！！");
		return;
	}
	if(!(/[\u4e00-\u9fa5]/.test(buyNm))){
		$.tip('请输入正确的公司名称！！')
		return;
	}
	if(/\s/.test(buyNm)){
		$.tip('请输入正确的公司名！！')
		return;
	}
	if(/\s/.test(userNames)){
		$.tip('请输入正确的用户账号！！')
		return;
	}
	if(/\s/.test(passWord)){
		$.tip('请输入正确的密码！！')
		return;
	}
	if (passWord=='') {
		$.tip("请输用户密码！！！");
		return;
	}
	if (passWord.length>21) {
		$.tip("请输入正确的密码！！");
		return;
	}
	if (passWord.length<21) {
		$.tip("请输入正确的密码！！");
		return;
	}
	if(!(/[a-zA-Z0-9]{20}/.test(passWord))){
		$.tip("请输正确的密码！！！");
		return;
	}
	var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
    	regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
	if(regEn.test(buyNm) || regCn.test(buyNm)) {
		$.tip("名称不能包含特殊字符.");
		return;
	}
	$.ajax({
		url : DOMIN.MAIN +"/oilCard/addoilprovider",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data : {
            userName: $.trim($("#userName").val()),
            userPassword: $.trim($("#passWord").val()),
            company: $.trim($("#buyNm").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			// console.log(data)
			if (data.success) {
				taocanId = null;
                $("#buyNm").val("");
                $("#userName").val("");
                $("#passWord").val("");
				$.tip('添加成功！！');
				$(".tips-warps").css("display","none");
				accountNumberAndPassword.dataList();
			}else{
				$.tip(data.message);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
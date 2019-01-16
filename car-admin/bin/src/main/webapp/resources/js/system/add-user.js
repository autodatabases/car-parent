$(function(){
	var user_roleid="";
	var user_name="";
	var user_userid="";
	$.ajax({
		url : DOMIN.MAIN + "/permission/roleManager.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:1,
			pageSize:1000000
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if(data.success){
				$("select[name=roleid]").empty();
				var html="";
				for(var i=0;i<data.list.length;i++){
					html+='<option value='+data.list[i].id+'>'+data.list[i].name+'</option>';
				}
				$("select[name=roleid]").html(html);
			}
			if(getCookie("user_idmagr")!=null){
				$("input[name=name]").val(getCookie("user_namemagr"));
				$("select[name=roleid]").find("option").each(function(i,e){
					if($(e).val()==getCookie("role_idmagr")){
						$(e).attr("selected","selected");
					}
				})
				user_userid=getCookie("user_idmagr");
				delCookie("user_namemagr");
				delCookie("user_idmagr");
				delCookie("role_idmagr");
				$("input[type=button]").val("确认修改");
			}else{
				$("input[type=button]").val("确认提交");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
	
	$("input[type=button]").click(function(){
	      add_usermgr(user_userid);
		 
	})
	
})
var isload = false;
function add_usermgr(id){
	if(isload){
		return;
	}
	var url="";
	var data="";
	var name=$("input[name=name]").val();
	//var reg = /^(?!\D+$)(?![^a-zA-Z]+$)\S{6,20}$/; 
	var password=$("input[name=password]").val();
	var password_tow=$("input[name=password_tow]").val();
	var roleId=$("select[name=roleid]").val();
	if(!/^\w{3,20}|\d{3,20}$/.test(name)){
		$.tip("用户名必须是3-20位的数字或字母组成!");
		return false;
	}
	if(!/^\w{6,15}|\d{6,15}$/.test(password)){
		$.tip("密码必须是6-20位的数字或字母组成!");
		return false;
	}
	if(password!=password_tow){
		$.tip("两次密码必须一致!");
		return false;
	}
	if($("input[type=button]").val()=="确认提交"){
		url=DOMIN.MAIN + "/permission/addUser.json";
		var content_user="新增";
		data={userName:name,
			password:password,
			roleId:roleId
		};
	}else{
		url=DOMIN.MAIN + "/permission/updateUser.json";
		var content_user="修改";
		data={userName:name,
			password:password,
			roleId:roleId,
			id:id
		};
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :data,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			load.remove();
			isload=false;
			if(data.success){
				$.tip(content_user+"成功!");
				$('.tables').load('system/user-mgr.html');
			}else{
				$.tip(content_user+"失败!"+data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			isload=false;
			load.remove();
			$.tip(errorThrown);
		}
	});
	
}











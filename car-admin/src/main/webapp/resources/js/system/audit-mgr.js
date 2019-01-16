$(function(){
	var roleId=0;
	if(getCookie('edit_roleID')!=null){
		roleId=getCookie('edit_roleID');
	}
	audit_checked(roleId);
	$(".audit_whole").click(function(){//全部权限全选与取消全选
		if(this.checked){
			$(".create-list :checkbox").prop("checked", true); 
		}else{
			$(".create-list :checkbox").prop("checked", false); 
		}
	});
	$("#audit_button").click(function(){
		var audit_buttons=$("#audit_button").html();
		var id=0;
		var url="";
		if(audit_buttons=="确认提交"){
		    url=DOMIN.MAIN + "/permission/addRole.json";
			audit_button(url,id);//新增角色
		}else{
			url=DOMIN.MAIN + "/permission/updateRole.json";
			id=$("#audit_button").attr("ids");
			audit_button(url,id);//修改角色
		}
	});
})
var isload = false;
function audit_button(url,id){//新增角色
    if(isload){
		return;
	}
    var userIds=$(".create input").val();
	if(userIds==""){
		$.tip("角色名不能为空");
		return false;
	}
	var pageIds="";
	$(".create-list input[type=checkbox]").each(function(i,e){
		if($(e).prop("checked")){
			if($(e).attr("pagid")){
				pageIds+=$(e).attr("pagid")+',';
			}
		}
	});
	pageIds=pageIds.substring(0,pageIds.length-1);
	var data="";
	if(id!=0){
		data={name:userIds,pageIds:pageIds,actionIds:"",id:id};
		var audit_content="修改";
	}else{
		data={name:userIds,pageIds:pageIds,actionIds:""};
		var audit_content="新增";
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
		data:data,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			load.remove();
			isload=false;
			if(data.success){
				$.tip(audit_content+"成功！！");
			}else{
				$.tip(audit_content+"失败"+data.message);
			}
			$("#audit_button").attr("ids","");
			if(audit_content=="修改"){
				$('.tables').load('system/role-mgr.html');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			load.remove();
			isload=false;
			$.tip(errorThrown);
		}
	});
}
function audit_checked(roleId){//加载所有权限模块
	$.ajax({
		url : DOMIN.MAIN + "/permission/queryRoleById.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data:{roleId:0},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			var data_sets=data.datasets.ztreeData4Role.list;
			$(".create-list dd").remove();
			var html="";
			for(var i=0;i<data_sets.length;i++){
				html+='<dd>';
				html+='<span><input rel='+data_sets[i].entity.id+' type="checkbox"/>'+data_sets[i].entity.name+'</span>';
				if(data_sets[i].children){
					for(var j=0;j<data_sets[i].children.length;j++){
						html+='<span><input pagid='+data_sets[i].children[j].entity.id+' type="checkbox"/>'+data_sets[i].children[j].entity.name+'</span>';
					}
				}
				html+='</dd>';
			}
			$(".create-list dl").append(html);
			$(".create-list dd").each(function(i,e){//
				$(e).find("span").eq(0).find("input").click(function(){
					if(this.checked){
						$(e).find("input").prop("checked", true); 
					}else{
						$(e).find("input").prop("checked", false); 
					}
				})
			});
			if(roleId!=0){
				$.ajax({
					url : DOMIN.MAIN + "/permission/queryRoleById.json",
					type : "post",
					processData: true,
					cache : false,
					async : true,
					dataType : "json",
					data:{roleId:roleId},
					traditional : true,// 使用传统方式序列化
					success : function(data, textStatus) {
						$(".create input").val(data.data.name);
						var data_rolelist=data.datasets.relaPages.list;
						for(var i=0;i<data_rolelist.length;i++){
							$(".create-list span").each(function(j,e){
								if($(e).text()==data_rolelist[i].name){
									$(e).find("input[type=checkbox]").prop("checked", true);
								}
							})
						} 
						$("#audit_button").html("确认修改");
						$("#audit_button").attr("ids",roleId);
						delCookie("edit_roleID");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						$.tip(errorThrown);
					}
				});	
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
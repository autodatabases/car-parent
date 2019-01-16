$(document).ready(function(){
	order_list();
	mui(".data-list").on("tap","input[type=button]",function(){
		prsldata();
	});
});
function order_list(){
	$.ajax({
		url : DOMIN.MAIN+"/user/getUserInfo",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				$(".data-list ul").find("li").eq(0).html("<label>账号</label><span>"+data.data.name+"</span>");
				$(".data-list ul").find("li").eq(1).find("input").val(data.data.nickName);
				mui(".gender option").each(function(i,e){
					if($(e).val()==data.data.gender){
						$(e).attr("selected","selected");
					}
				})
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			
		}
	})
};
function prsldata(){
	var gender=$(".gender").val();
	var nickName=$(".data-list ul").find("li").eq(1).find("input").val();
	if($.trim(nickName)==""){
		$.tip("姓名不能为空");
		return;
	}
	$.ajax({
		url : DOMIN.MAIN+"/user/updateUserInfo",
		type : "post",
		cache : false,
		async : true,
		data:{nickName:nickName,gender:gender},
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				$.tip("修改成功");
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			
		}
	})
}  
  
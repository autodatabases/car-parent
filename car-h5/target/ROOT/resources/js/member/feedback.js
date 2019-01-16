$(document).ready(function(){
	var memberName=sessionStorage.getItem("memberName");
	$(".text p").find("span").html(memberName);feedback_btn
	mui(".text").on("tap","#feedback_btn",function(){
		feedback_btn(memberName);
	});
	
});
function feedback_btn(memberName){
	var content=$(".text textarea").val();
	if($.trim(content)==""){
		$.tip("请填写反馈后提交");
		return;
	}
	$.ajax({
		url : DOMIN.MAIN+"/user/addFeedBack",
		type : "post",
		cache : false,
		async : true,
		data :{content:content,userName:memberName},
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.data){
				$.tip('反馈成功！');
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	})
};
  
$(document).ready(function(){
	var step=1;
	$(".next-btn").click(function(){
		if(step==1){
			step2();
		}
	})
	$("#submit_form").attr("action",DOMIN.MAIN+"/uploadImg?type=fixOrder");
	$(".upload-img input[type=file]").change(function(){//营业执照上传图片
         if($(".upload-img input[type=file]").val() != ''){
			 $("#submit_form").submit();
			 $(".edit").show();
		 } 
    });
	$("#uploadImglist").delegate('.delpic','click',function(){
		$("#remain-count").text((parseInt($("#remain-count").text())-1)+"");
		if($(this).parent().index()>=6){
			$(this).parent().remove();
		}else{
			$(this).hide();
			$(this).prev().attr('src',DOMIN.MAIN+'/resources/imgs/fixorder/fix_demo_0'+($(this).parent().index()+1)+'.png');	
		}
	});
    //iframe加载响应，初始页面时也有一次，此时data为null。
    $("#exec_target").load(function(){
		if(document.getElementById("exec_target").contentWindow.document.body.innerText){
			var count = parseInt($("#remain-count").text());
			$("#remain-count").text((count+1)+"");
		    var data =eval("(" + document.getElementById("exec_target").contentWindow.document.body.innerText + ")");
		    var isReplace = false;
		    $("#uploadImglist dd").each(function(i,e){
		    	if($(this).find('img').attr('src').indexOf('fix_demo')>0){
		    		isReplace = true;
		    	}
		    });
		    if(isReplace){//表示是前5张
		    	var hasAppend = false;
		    	$("#uploadImglist dd").each(function(i,e){
		    		if(hasAppend){
		    			return;
		    		}
		    		if($(this).find('img').attr('src').indexOf('fix_demo')>0){
		    			$(this).find('img').attr('src',data.url);
		    			hasAppend = true;
		    		}
		    	});
		    }else{
		    	 $(".upload-img dt").before('<dd><img src="'+data.url+'"/><div class="delpic"><span>×</span></div></dd>');
		    }
			$(".upload-img input[type=file]").val("");
			
		}
    });
	$(".edit").on('click',function(){
		if($(".edit").html()=="编辑"){
			$(".upload-img dd").find(".delpic").each(function(i,e){
				if($(e).prev().attr('src').indexOf("fix_demo")<0){
					$(e).show();
				}
			});
			$(".edit").html("确认");
			$("input").prop("disabled",true);
			step=2;
		}else{
			$(".upload-img dd").find("div").hide();
			$(".edit").html("编辑");
			$("input").prop("disabled",false);
			step=1;
		}
		
	})
	
})
function step2(){
		var id=$.getUrlParam('id');
		var count = parseInt($("#remain-count").text());
		/*if(count<6){
			$.tip("图片数量不能少于6张！");
			return;
		}*/
		var userPic="";
		$("#uploadImglist dd").each(function(i,e){
			var picUrl = $(e).find("img").attr("src");
			if(picUrl.indexOf('fix_demo')<0){
				if(i==0){
					userPic+=picUrl;
				}else{
				    userPic+=","+picUrl;
				}
			}
		});
		$.ajax({
		url : DOMIN.MAIN+"/order/createFixOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data:{
			step:2,
			id:id,
			userPic:userPic
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				location.href=DOMIN.MAIN+'/user/bussinessStep3.html?id='+data.data.id;
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});
}

checkLogin();
function busines_two(){//如果有商家 填写营业执照和税务登记副本
		$.ajax({
				url : DOMIN.MAIN+"/user/getSellerInfo",
				type : "post",
				cache : false,
				async : true,
				dataType : "json",
				traditional: true,
				success : function(data, textStatus){
					if(data.success){
						 $(".upload_y img").css("display","block");
						 $(".upload_y span").css("display","none");
						 $(".upload_y input").css("display","none");
						 $(".upload_y img").attr("src",data.data.businessLicence);
						 $(".up_span_y").html("重新上传");
						 $(".upload_s img").css("display","block");
						 $(".upload_s span").css("display","none");
						 $(".upload_s input").css("display","none");
						 $(".upload_s img").attr("src",data.data.taxRegistration);
						 $(".up_span_s").html("重新上传");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
				}
		});
	};	
$(function(){
	$(".two_btn").click(function(){//确定提交信息
		var up_span_y=$(".up_span_y").html();
		if(up_span_y=="未上传"){
			$.tip("请上传营业执照!");
			return false;
		}
		var up_span_s=$(".up_span_s").html();
		if(up_span_s=="未上传"){
			$.tip("请上传税务登记证副本!");
			return false;
		}
		$.ajax({
			url : DOMIN.MAIN+"/user/setSellerInfo",
			type : "post",
			cache : false,
			async : true,
			dataType : "json",
			data: {
				"sellerName":sessionStorage.getItem("sellerName"),
				"taxesCode":sessionStorage.getItem("taxesCode"),
				"registerAddr":sessionStorage.getItem("registerAddr"),
				"registerPhone":sessionStorage.getItem("registerPhone"),
				"account":sessionStorage.getItem("account"),
				"accountNumber":sessionStorage.getItem("accountNumber"),
				"businessLicence":$(".upload_y img").attr("src"),
				"taxRegistration":$(".upload_s img").attr("src"),
			},
			traditional: true,
			success : function(data, textStatus){
				if(data.success){
					location.href='busines-three.html';
				}else{
					$.tip(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
			}
		});
	});
	
	$(".upload_y input[type=file]").change(function(){//营业执照上传图片
         if($(".upload_y input[type=file]").val() != '') $("#submit_form").submit();
    });
    //iframe加载响应，初始页面时也有一次，此时data为null。
    $("#exec_target").load(function(){
		 var data =eval("(" + document.getElementById("exec_target").contentWindow.document.body.innerText + ")");
		 $(".upload_y img").css("display","block");
		 $(".upload_y span").css("display","none");
		 $(".upload_y input").css("display","none");
		 $(".upload_y img").attr("src",data.url);
		 $(".up_span_y").html("重新上传");
    });
	$(".up_span_y").click(function(){//营业执照重新上传
	    if($(".up_span_y").html()=="重新上传"){
			$(".upload_y img").css("display","none");
		    $(".upload_y span").css("display","block");
		    $(".upload_y input").css("display","block");
		    $(".upload_y img").attr("src","");
			$(".up_span_y").html("未上传");
		}
	});
	$(".up_span_s").click(function(){//税务登记证副本重新上传
	    if($(".up_span_s").html()=="重新上传"){
			$(".upload_s img").css("display","none");
		    $(".upload_s span").css("display","block");
		    $(".upload_s input").css("display","block");
		    $(".upload_s img").attr("src","");
			$(".up_span_s").html("未上传");
		}
	});
	$(".upload_s input[type=file]").change(function(){//税务登记证副本上传图片
         if($(".upload_s input[type=file]").val() != '') $("#submit_forms").submit();
    });
    //iframe加载响应，初始页面时也有一次，此时data为null。
    $("#exec_targets").load(function(){
		 var data =eval("(" + document.getElementById("exec_targets").contentWindow.document.body.innerText + ")");
		 $(".upload_s img").css("display","block");
		 $(".upload_s span").css("display","none");
		 $(".upload_s input").css("display","none");
		 $(".upload_s img").attr("src",data.url);
		 $(".up_span_s").html("重新上传");
         //若iframe携带返回数据，则显示在feedback中
     });
	
	
})
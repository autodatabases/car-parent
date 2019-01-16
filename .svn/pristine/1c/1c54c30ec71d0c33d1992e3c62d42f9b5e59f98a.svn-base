$(function(){
	orderdetail_repair();
	$(".next-btn").eq(0).click(function(){//提交定损结果
		commitFixRemark();
	});
	$(".next-btn").eq(1).click(function(){//提交定损结果
		completFixOrder();
	});
});
function orderdetail_repair(){//查询详情
	var orderNo = $.getUrlParam('orderNo');
	$.ajax({
		type:'post',
		url:DOMIN.MAIN+'/order/fixOrderDetail', 
		async:false,
		cache:false,
		dataType:'json',
		data : {orderNo:orderNo},
		success:function(data){
			if (data.success){
				$(".names").html(data.data.userName);
				$(".case-details li").eq(1).find("span").html(data.data.chePai);
				$(".case-details li").eq(2).find("span").html(data.data.userPhone);
				$(".case-details li").eq(3).find("span").html(data.data.carBrand==null?'未知':data.data.carBrand);
				var outTime=$.formatDate(data.data.outTime)
				outTime=outTime.substring(0,outTime.length-8);
				$(".times").html(outTime);
				$(".img-box").html("没有图片！");
				var userPic=data.data.userPic;
				if(userPic && userPic!='' &&　userPic!=null){
					userPic=userPic.split(",");
					var html="";
					for(var i=0;i<userPic.length;i++){
						html+='<div><img src="'+userPic[i]+'"/></div>';
					}
					$(".img-box").html(html);
				}
				if(data.data.money){
					$(".case-details li").eq(5).find("span").html(data.data.money);
				}else{
					$(".case-details li").eq(5).find("span").html('0.00');
				}
				if(data.data.sellerRemark){
					$("textarea").html(data.data.sellerRemark);
				}else{
					$("textarea").html("无");
				}
				if(data.data.auditStatus=="1"){//判断是否有提交
					$(".next-btn").eq(0).show();
				}else if(data.data.auditStatus=="3"){
					$(".next-btn").eq(1).show();
				}
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
    }); 
}
function commitFixRemark(){//提交定损结果
	var orderNo = $.getUrlParam('orderNo');
	/*var price=$(".money").val();
	if(price==""){
		$.tip("请填写定损金额");
		return;
	}
	var remark=$("textarea").val();
	if(remark==""){
		$.tip("请填写定损描述");
		return;
	}*/
	$.ajax({
		type:'post',
		url:DOMIN.MAIN+'/order/commitFixRemark', 
		async:false,
		cache:false,
		dataType:'json',
		data : {
			orderNo:orderNo
			//price:price,
			//remark:remark
		},
		success:function(data){
			if (data.success){
				$.tip("操作成功");
				setTimeout(function(){
					location.href=DOMIN.MAIN+"/repair/ok.html?v=1.0";
				},2000);
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
    }); 
}
function completFixOrder(){//维修完成
	var orderNo = $.getUrlParam('orderNo');
	$.ajax({
		type:'post',
		url:DOMIN.MAIN+'/order/completFixOrder', 
		async:false,
		cache:false,
		dataType:'json',
		data : {
			orderNo:orderNo
		},
		success:function(data){
			if (data.success){
				$.tip("操作成功");
				setTimeout(function(){
					location.href=DOMIN.MAIN+"/repair/order.html";
				},2000);
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
    }); 
}
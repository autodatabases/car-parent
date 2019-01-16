var pageNo = 1;
var pageSize = 10;
var hasNext = true;
$(document).ready(function(){
	list();
	$(".search-box a").on('click',function(){
		location.href = DOMIN.MAIN+'/user/bussinessStep1.html';
	});
	$(window).scroll(function(){
		var scrollHeight = $(document).height();
	　	var scrollTop = $(document).scrollTop();
	　　	var windowHeight = document.body.clientHeight;
	　　	if(scrollTop + windowHeight >= scrollHeight){
	　　　　	nextPage();
	　　	}
	});
})
function nextPage(){
	if(hasNext){
		pageNo ++;
		list();
	}else{
		if($('.mui-popup').length==0){
			$.tip('没有更多了！');
		}
	}
}
function list(){
	$.ajax({
		url : DOMIN.MAIN+"/order/fiexOrderList",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data:{
			pageNo:pageNo,
			pageSize:pageSize,
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				var html="";
				if(pageNo==1){
					$(".case-lists").html("");
				}
				if(data.pageInfo.pageCount == data.pageInfo.pageIndex){
					hasNext = false;
				}else{
					hasNext = true;
				}
				if(data.list.length>0){
					for(var i=0;i<data.list.length;i++){
						if(data.list[i].step==3){
							html+='<li onclick=details_step("'+data.list[i].orderNo+'")>';
						}else{
							html+='<li onclick=supplement_step("'+data.list[i].step+'","'+data.list[i].id+'")>';
						}
						
						html+='<div>';
						html+='<a>案件号：<span>'+data.list[i].orderNo+'</span></a>';
						html+='<p class="p-1"><span class="chepai">车牌号：'+data.list[i].chepai+'</span><span class="times">'+$.formatDate(data.list[i].createTime)+'</span></p>';
						html+='<p class="p-2">出险人手机号：'+data.list[i].userPhone+'</p>';
						html+='</div>';
						if(data.list[i].step==3){
							if(data.list[i].status==1){
								html+='<span>待入场</span>';
							}else if(data.list[i].status==2){
								html+='<span>已入场</span>';
							}else if(data.list[i].status==3){
								html+='<span>审核通过</span>';
							}else if(data.list[i].status==4){
								html+='<span>案件已完结</span>';
							}
						}else{
							html+='<span>信息不完善请继续提交</span>';
						}
						html+='</li>';
					}
					$(".case-lists").append(html);
				}else{
					$(".case-lists").html("<li><p style='text-align:center; color:red; margin:5px 0;'>暂无数据</p></li>");
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
function supplement_step(step,id){//补充信息跳转
    if(step=="2"){
		location.href=DOMIN.MAIN+'/user/bussinessStep3.html?id='+id;
	} else{
		location.href=DOMIN.MAIN+'/user/bussinessStep2.html?id='+id;
	}
	
};
function details_step(orderNo){
	location.href=DOMIN.MAIN+'/user/bussinessOrderDetail.html?orderNo='+orderNo;
};



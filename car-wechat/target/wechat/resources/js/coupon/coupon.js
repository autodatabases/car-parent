checkLogin();
var pageNo = 1;
var pageSize =14;
var hasNext = true;
$(function(){
	setlment();
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
		setlment();
	}else{
		$.tip('没有更多了！');
	}
}
function setlment(){
	$.ajax({
			type:'post',
			url:DOMIN.MAIN+'/order/sellerCompleteList', 
			async:false,
			cache:false,
			data:{
				pageNo:pageNo,
				pageSize:pageSize
			},
			dataType:'json',
			error:function(data){
				$.tip(data);
			}, 
			success:function(result){
				if (result.success){
					$(".lists dt").find("span").html(result.pageInfo.recordCount);
					if(pageNo==1){
						$(".lists dd").each(function(i,e){
							$(e).remove();
						})
					}
					if(result.pageInfo.pageCount == result.pageInfo.pageIndex){
						hasNext = false;
					}else{
						hasNext = true;
					}
					var html="";
					if(result.list.length>0){
						for(var i=0;i<result.list.length;i++){
							html+='<dd>';
							html+='<span class="ddbh">订单编号：'+result.list[i].orderNo+'</span>';
							html+='<span>券码：'+result.list[i].code+' </span>';
							html+='<a href=javascript:location.href="'+DOMIN.MAIN+'/order/order-details.html?code='+result.list[i].code+'">查看</a>';
							html+='</dd>';
						}
					}else{
						html+='<dd><p style="text-align:center;color:red;">暂无数据</p></dd>';
					}
					$(".lists").append(html);
				}else{
					$.tip(result.message);
				}
			}
		}); 
};
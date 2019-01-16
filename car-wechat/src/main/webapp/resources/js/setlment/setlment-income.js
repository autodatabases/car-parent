checkLogin();
$(function(){
	setlment();
})
function setlment(){
	$.ajax({
			type:'post',
			url:DOMIN.MAIN+'/order/sellerTodayIncome', 
			async:false,
			cache:false,
			dataType:'json',
			error:function(data){
				$.tip(data);
			}, 
			success:function(result){
				if (result.success){
					$(".list").html("");
					var html="";
					if(result.list.length>0){
						for(var i=0;i<result.list.length;i++){
							html+='<li>';
							html+='<strong>订单入账</strong>';
							html+='<span>'+$.formatDate(result.list[i].finishTime)+'</span>';
							html+='<i>+'+result.list[i].money+'</i>';
							html+='</li>';
						}
					}else{
						html+='<li><p style="text-align:center; color:red; font-size:14px;">暂无数据</p></li>';
					}
					$(".list").html(html);
				}else{
					$.tip(result.message);
				}
			}
		}); 
};
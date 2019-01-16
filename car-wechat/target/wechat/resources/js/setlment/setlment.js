checkLogin();
$(function(){
	setlment();
})
function setlment(){
	$.ajax({
			type:'post',
			url:DOMIN.MAIN+'/order/sellerGetMoneySummary', 
			async:false,
			cache:false,
			dataType:'json',
			error:function(data){
				$.tip(data);
			}, 
			success:function(result){
				if (result.success){
					$(".top").html("<span>本月应结算</span>￥"+result.data.monthMoney+"");
					$(".list a").find("span").html(result.data.todayMoney);
				}else{
					$.tip(result.message);
				}
			}
		}); 
};
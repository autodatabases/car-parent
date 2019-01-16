$(function(){
	var num=3;
	var busines=setInterval(
	    function(){
		    num=num-1;
			if(num<0){
				clearInterval(busines);
				location.href="/wechat";
			}else{
				$(".wait span").html(num);
			}
	}, 1000); 
})
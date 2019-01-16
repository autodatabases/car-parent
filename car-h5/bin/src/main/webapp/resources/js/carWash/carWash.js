var bool=false;
$(document).ready(function() {
	$.ajax({
		type:"post",
		url: DOMIN.MAIN + '/carwashsheng/carWashData',
		cache:false,
		async:true,
		dataType: "json",
		success: function(data) {
			if(data.success){
				var carWashTime = data.data.carWashTime==null?0: data.data.carWashTime;
				$(".washNumber").html(carWashTime);

				if(data.data.unUsed!=null&&data.data.unUsed.success){
					var codeNumber = data.data.unUsed.list.length;
					$(".codeNumber").html(codeNumber);
					setFunction(codeNumber,carWashTime);
				}else if(data.data.unUsed!=null&&!data.data.unUsed.success){
					$.tip(data.data.unUsed.message);
				}
			}else{
				setFunction(0,0);
				$.tip(data.message);
			}
		},
		error: function(data) {
			$.tip("链接服务器失败!");
		}
	});
	$(".back").click(function(){
		window.location.href=DOMIN.MAIN;
	});
});


//根据洗车券个数和洗车次数绑定事件
function setFunction(codeNumber,carWashTime){
	//洗车券个数大于0
	if(codeNumber-0>0){
		$(".codeNumber").click(function(){
			window.location.href=DOMIN.MAIN + "/carwashsheng/washCode.html";
		});
		$("#toCarWash").click(function(){
			window.location.href=DOMIN.MAIN + "/carwashsheng/washCode.html";
		});
	}else{//洗车券个数不大于0
		$(".codeNumber").click(function(){
			$.tip("您的洗车券个数为0!");
		});
		if(carWashTime-0<=0){
			$("#toCarWash").click(function(){
				$.tip("您的洗车次数不足!");
			});
		}else{
			$("#toCarWash").click(function(){
				window.location.href=DOMIN.MAIN + "/carwashsheng/washCode.html";
			});
		}
	}
}



function setBack(url){
	pushHistory();

	bool=false;//控制微信  点击返回   进页面就触发 popstate事件
	setTimeout(function(){
        bool=true;
	},100);
    window.addEventListener("popstate", function(e) {//只有返回时会调用
    	setTimeout(function(){
            bool=true;
    	},100);
       if(bool){
    	   	window.location.href=DOMIN.MAIN+url ;
       }
     }, false);
}
function pushHistory() {
    var state = {
        title: "title",
        url: "#"
    };
    window.history.pushState(state, "title", "#11");
}

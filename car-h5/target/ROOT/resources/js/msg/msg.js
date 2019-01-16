$(document).ready(function(){
     getmsg();
});
function getmsg(){
	$(".list").html("");
	$.ajax({
		url : DOMIN.MAIN+"/user/getMsgList",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				if(data.list.length==0){
					$(".list").html(getNoDataHtml());
					return;
				}
				var html=""; 
				for(var i=0;i<data.list.length;i++){
					html+='<a href='+DOMIN.MAIN+'/msgdetail.html?id='+data.list[i].id+'>';
					html+='<p>'+data.list[i].msgTitle+'</p>';
					html+='<span>'+(new Date(data.list[i].updateTime).Format("yyyy.MM.dd"))+'</span>';
					html+='</a>';
				}
				$(".list").html(html);
			}else{
				$.tip(data.message);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	})
	
}
Date.prototype.Format = function (fmt) { //author: meizz  
            var o = {  
                "M+": this.getMonth() + 1, //�·�  
                "d+": this.getDate(), //��  
                "h+": this.getHours(), //Сʱ  
                "m+": this.getMinutes(), //��  
                "s+": this.getSeconds(), //��  
                "q+": Math.floor((this.getMonth() + 3) / 3), //����  
                "S": this.getMilliseconds() //����  
            };  
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
            for (var k in o)  
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));  
            return fmt;  
        }; 
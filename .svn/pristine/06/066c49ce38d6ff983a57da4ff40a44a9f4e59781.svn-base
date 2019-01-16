$("document").ready(function(){
	//后台获取优惠券详情
	$.ajax({
		url:DOMIN.MAIN+'/didi/getcouponrecord',
		async : false,
		type:'POST',
		dataType:'json',
		data:{
			pageNo:1,
			pageSize:500
		},
		error:function(data){
			$.tip("链接服务器失败！");
		},
		success:function(data){
			if(data.success){
				if(data.list.length > 0){
					renderHtml(data.list);
				}else{
					$(".warp").hide();
					$(".no_have").show();
				}
			}else{
				$.tip(data.message);
			}
		}
	});


	// 生成html模板
	function renderHtml(data){
		var html = '';
		for(var i = 0;i < data.length;i++){
			html += '<li>'
				 + '<p>名称：'+data[i].goodName+'</p>'
				 + '<p>兑换码：'+data[i].goodSecret+'</p>'
				 + '<p>兑换日期：'+$.formatDate(data[i].createTime)+'</p>'
				 + '<p>使用说明：<span class="red">请在三个月内进行使用</span></p>'
				 + '</li>'
		}
		$('#coupon').append(html);

	}
})
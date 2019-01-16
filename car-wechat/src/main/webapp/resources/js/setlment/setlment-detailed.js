checkLogin();
var pageNo = 1;
var pageSize = 10;
var hasNext = true;
//读取cookies
function getCookieNoEncode(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return (arr[2]);
    else
        return null;
};
$(function(){
	var mydate = new Date();
	var year=mydate.getFullYear(); //获取完整的年份(4位,1970-????)
	var month = mydate.getMonth()+1; //获取当前月份(0-11,0代表1月)
	var html="<option>"+year+'-'+month+"</option>";
	for(i=1;i<12;i++){
		if(month-i<=0){
			html+="<option>"+(year-1)+'-'+(12+month-i)+"</option>";
		}else{
			html+="<option>"+year+'-'+(month-i)+"</option>";
		}
	}
	$("select").append(html);
	var mydate_month=year+"-"+month;
	setlment(mydate_month);
	$("select").change(function(){
		pageNo=1;
		var year_month=$("select").val();
		setlment(year_month);
	});
	$("#Export_span").click(function(){//导出
		var year_month=$("select").val();
		location.href = DOMIN.MAIN+'/order/exportSellerExcel?date='+year_month+'&token='+getCookieNoEncode('CAR_SELLER_TOKEN');
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
		var year_month=$("select").val();
		setlment(year_month);
	}else{
		//$.tip('没有更多了！');
	}
}
function setlment(date){//根据月份刷新当前收入明细
	$.ajax({
			type:'post',
			url:DOMIN.MAIN+'/order/sellerAllIncome',
			async:false,
			cache:false,
			dataType:'json',
			data:{
				date:date,
				pageNo : pageNo,
				pageSize : pageSize
			},
			error:function(data){
				$.tip(data);
			},
			success:function(result){
				if (result.success){
					var year_month=$("select").val();
					if(result.datasets.isComplet.data=="false"){
						$(".list dt").html("<span onclick=settlement_confirm('"+year_month+"')>确认结算</span>"+year_month);
					}else{
						$(".list dt").html("<span style='background-color:#ccc';>已结算</span>"+year_month);
					}
					if(pageNo==1){
						$(".list dd").remove();
					}
					if(result.pageInfo.pageCount == result.pageInfo.pageIndex){
						hasNext = false;
					}else{
						hasNext = true;
					}
                    if (result.pageInfo.pageCount == 0) {
                        hasNext = false;
                    }
					var html="";
					if(result.list.length>0){
						$(".list dt").css("display","block");
						for(var i=0;i<result.list.length;i++){
							var shijian=$.formatDate(result.list[i].finishTime);
							shijian=shijian.split(" ");
							var yuefen=shijian[0].split("-");
							var shifen=shijian[1].split(":")
							html+='<dd>';
							html+='<div class="datas">';
							html+='<span>'+yuefen[1]+'-'+yuefen[2]+'</span>';
							html+=shifen[0]+':'+shifen[1];
							html+='</div>';
							html+='<div class="imgs">';
							if(result.list[i].orderType==0){
								html+='<img src="../resources/imgs/setlment/dp-icon.png"/>';
							}else if(result.list[i].orderType==1){
								html+='<img src="../resources/imgs/setlment/lt-icon.png"/>';
							}else if(result.list[i].orderType==2){
								html+='<img src="../resources/imgs/setlment/by-icon.png"/>';
							}else if(result.list[i].orderType==3){
								html+='<img src="../resources/imgs/setlment/xc.png"/>';
							}else if(result.list[i].orderType==4){
								html+='<img src="../resources/imgs/index/penqi-shouye.png"/>';
							}
							html+='</div>';
							html+='<div class="detals">';
							html+='<strong>+'+result.list[i].money+'</strong>';
							html+='</div>';
							html+='<a href="javascript:location.href=\''+DOMIN.MAIN+'/setlment/detailed-details.html?t=1.1&orderNo='+result.list[i].orderNo+'\'">明细</a>';
							html+='</dd>';
						}
					}else{
						$(".list dt").css("display","none");
						html+='<dd><p style="text-align:center; color:red; font-size:14px;">暂无数据</p></dd>';
					}
					$(".list").append(html);
				}else{
					$.tip(result.message);
				}
			}
		});
};
function settlement_confirm(date){//确认结算
	var invoice = 0;
	mui.confirm("是否开发票！","开发票据",['不开','开'],function(e){
		if (e.index == 1) {
			invoice = 1;
		} else {

		}
		$.ajax({
			type:'post',
			url:DOMIN.MAIN+'/order/sellerComfirmReport',
			async:false,
			cache:false,
			data:{
				date:date,
				invoice:invoice
			},
			dataType:'json',
			error:function(data){
				$.tip(data);
			},
			success:function(result){
				if (result.success){
					$.tip("确认结算成功！");
					setlment(date);
				}else{
					$.tip(result.message);
				}
			}
		});
	});

}

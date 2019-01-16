var pageNo = 1;
var pageSize = 20;
var hasNext = true;
var flag = true;
$(document).ready(function() {
	$(".back").on('click',function() {
		window.location.href = DOMIN.MAIN + "/counterman/dataInfo.html";
	})
	getData();
	//获取数据
	function getData() {
		$.ajax({
			url: DOMIN.MAIN + "/counterman/getGoodList",
			type: "post",
			dataType: "json",
			data: {
				pageNo: pageNo,
				pageSize: pageSize
			},
			success: function(data) {
				if (data.success) {
					if(data.pageInfo.pageCount == data.pageInfo.pageIndex){
						hasNext = false;
					}else{
						hasNext = true;
					}
					renderPage(data);
				} else {
					$.tip(data.message);
				}
			},
			error: function(data) {
				$.tip("服务器错误");
			}
		});
	}
	//渲染页面
    function renderPage(data) {
    	var html = "";
    	for(var i=0; i<data.list.length; i++) {
    		html += "<li goodid = '" + data.list[i].id + "' goodScore='" + data.list[i].goodScore + "' stock='" + data.list[i].goodNumber + "' goodname='" + data.list[i].goodName + "'>";
    		html += "<div class='photo'>";
		    html += "<img src='" + data.list[i].goodImg + "'/>";
			//html += "<img src='../../resources/imgs/counterman/ceshi2.jpg'/>"
			html += "<span class='elected'></span>";
			html += "</div>";
			html += "<div class='info'>";
			html += "<p class='remark' style='display:none'>" + data.list[i].remark + "</p>";
			html += "<p class='name'>" + data.list[i].goodName + "</p>";
			html += "<p class='price'>";
			html += "<span>￥" + data.list[i].goodPrice + "</span> ";
			html += "<span>" + data.list[i].goodScore + " 积分</span>";
			html += "</p>"	;
			html += "<p class='kucun'>";
			html += "<span>库存：" + data.list[i].goodNumber + " 件</span>";
			html += "<span class='number'><b class='reduce'>-</b><input type='number' class='num' value='1'/><b class='add'>+</b></span>";
			html += "</p>";
			html += "</div>";
			html += "</li>"	;
    	}
		if (flag == false) {
			$(".list").append(html);
		} else {
			$(".list").html(html);
		}

    	$(".point").html(0);
    	 //购买操作
  		operation();

    }
    //购买操作
    function operation() {
    	var goodObj = [];
    	var totalMoney = 0;
    	var numberValue = 1;
		//点击照片查看商品详情
		$(".name,.price").on("click",function() {
			var imgSrc = $(this).parent().prev().children('img').attr('src');
			var detail = $(this).parent().children('.remark').html();
			if(detail == "null" || detail == "") {
				detail = "暂无商品详情";
			}
			if(imgSrc == "null" || imgSrc == "") {
				imgSrc = "${_RESOURCES}/imgs/counterman/good-detail.png";
			}
			$(".img-box img").attr('src', imgSrc);
			$(".introduce p").html(detail);
			$(".tip-con").show();
		});
		$(".tip-lg,.box-head,.img-box").on("click",function() {
			$(".tip-con").hide();
		});
    	//点击改变商品数量
	    $(".number b").on('click',function() {
	    	numberValue =  parseInt($(this).siblings("input").val());
	    	var goodScore = parseInt($(this).parents("li").attr("goodScore"));
	    	var stock = parseInt($(this).parents("li").attr("stock"));
	    	var id = $(this).parents("li").attr("goodid");
	    	var flag = -1;
	    	for(var i=0; i<goodObj.length; i++) {
	    		if(goodObj[i].id == id) {
	    			flag = i;
	    		}
	    	}
	    	if (flag == -1) {
	    		$(".num").blur();
	    		$.tip("请先选定商品");
	    		return;
	    	}
		  	if ($(this).attr("class") == "reduce") {
		  		if (numberValue == 1) {
		  			$.tip("最低购买一件商品！");
		  			numberValue = 1;
		  		} else {
		  			numberValue = numberValue - 1;
		  		}
		  	} else if ($(this).attr("class") == "add") {
		  		if (numberValue >= stock) {
		  			$.tip("购买数量不能超过库存！");
		  			numberValue = stock;
		  		} else {
		  			numberValue = numberValue + 1;
		  		}
		  	}
		  	$(this).siblings("input").val(numberValue);
		  	numberValue = numberValue - goodObj[flag].goodNumber;
		  	goodObj[flag].goodNumber = parseInt($(this).siblings("input").val());
		  	countPrice(goodScore);
	    })
	    //输入改变商品的数量
	    $(".num").on('click',function() {
	    	var id = $(this).parents("li").attr("goodid");
	    	var flag = -1;
	    	for(var i=0; i<goodObj.length; i++) {
	    		if(goodObj[i].id == id) {
	    			flag = i;
	    		}
	    	}
	    	if (flag == -1) {
	    		$(this).blur();
	    		$(this).trigger('input');
	    	}
	    })
	    $(".num").on('input',function() {
	    	var stock = parseInt($(this).parents("li").attr("stock"));
	    	var goodScore = parseInt($(this).parents("li").attr("goodScore"));
	    	var id = $(this).parents("li").attr("goodid");
	    	var flag = -1;
	    	for(var i=0; i<goodObj.length; i++) {
	    		if(goodObj[i].id == id) {
	    			flag = i;
	    		}
	    	}
	    	if (flag == -1) {
	    		$(this).val(1);
	    		$.tip("请先选定商品");
	    		return;
	    	}
	    	var r = /^\+?[1-9][0-9]*$/;
	    	var count = $.trim($(this).val());
	    	if(count == "") {
	    		return;
	    	}
			if (!(r.test(count))) {
				$.tip("请输入整数的数量!");
				$(this).val(1);
			}
			if(count > stock) {
				$.tip("购买数量不能超过库存！");
	  			$(this).val(1);
			}
			numberValue = parseInt($(this).val());
			numberValue = numberValue - goodObj[flag].goodNumber;
			goodObj[flag].goodNumber = parseInt($(this).val());
			countPrice(goodScore);
	    })
	    $(".num").on('blur',function() {
	    	var count = $.trim($(this).val());
	    	if(count == "") {
	    		$(this).val(1);
	    		$(this).trigger('input');
	    	}
	    })
	    //选择商品
	    $(".photo").on('click',function() {
	    	var id = $(this).parent().attr("goodid");
	    	var goodsClass = $(this).children("span").attr("class");
	    	var goodScore = parseInt($(this).parent().attr("goodScore"));
	    	var goodName = $(this).parent().attr("goodname");
	    	var stock = parseInt($(this).parent().attr("stock"));
			var goodNumber = parseInt($(this).parent().find("input").val());
	    	if(stock <= 0) {
	    		$.tip("此商品暂时没有库存！");
	    		return;
	    	}
	    	if (goodsClass == "elected on") {
	    		goodsClass = "elected";
	    		for(var i=0; i<goodObj.length; i++) {
		    		if(goodObj[i].id == id) {
		    			numberValue = -goodObj[i].goodNumber;
		    			goodObj.splice(i,1);
		    		}
		    	}
	    	} else {
	    		goodsClass = "elected on";
	    		var obj = {
	    			id: id,
	    			goodName: goodName,
	    			goodScore: goodScore,
	    			goodNumber: goodNumber
	    		}
	    		goodObj.push(obj);
	    		numberValue = goodNumber;
	    	}
	    	$(this).children("span").attr("class",goodsClass);
	    	countPrice(goodScore);
	    })
	    //计算总价格
	    function countPrice(goodScore) {
	    	totalMoney = totalMoney + goodScore * numberValue;
	    	$(".point").html(totalMoney);
//	    	$(".money").html(totalMoney / 100);
	    }
	    //结算查看
	    $(".btn").off("click").on('click',function() {
	       	if(goodObj.length >= 1) {
	    		var html = "";
		    	for(var i=0; i<goodObj.length; i++) {
		    		html += "<li class='items'><span>" + goodObj[i].goodName + "</span>";
		    		html += "<span>x " + goodObj[i].goodNumber + "</span>";
		    		html += "<span>" + goodObj[i].goodScore * goodObj[i].goodNumber + " 积分</span></li>"
		    	}
		    	$(".submit ul").html(html);
		    	$(".total span").html($(".point").html());
		    	$(".submit").show();
		    	$(".shade").show();
	    	} else {
	    		$.tip("请选择商品！");
	    	}
	    })
	    //取消结算
	    $(".cancel").on('click',function() {
	    	$(".submit").hide();
	    	$(".shade").hide();
	    })
	    //结算
	    $(".sub").off("click").on('click',function() {
	    	var goodId = ""
	    	for(var i=0; i<goodObj.length; i++) {
	    		goodId += goodObj[i].id +  "_" + goodObj[i].goodNumber;
	    		if (i < goodObj.length - 1) {
	    			goodId += ","
	    		}
	    	}
	    	$.ajax({
				url: DOMIN.MAIN + "/counterman/addCountermanOrder",
				type: "post",
				dataType: "json",
				data: {
					orderPrice: $.trim($(".total span").html()),
					goodId: goodId
				},
				success: function(data) {
					if (data.success) {
						$(".submit").hide();
						var btnArray = ['是', '否'];
						mui.confirm('是否继续购物？', '结算成功！', btnArray, function(e) {
							flag = true ;
		                    if (e.index == 1) {
		                        $(".shade").hide();
		                        window.location.href = DOMIN.MAIN + "/counterman/dataInfo.html";
	    						getData();
		                    } else {
								$(".shade").hide();
	    						getData();
		                    }
		                })

					} else {
						$.tip(data.message);
						$(".submit").hide();
	    				$(".shade").hide();
					}
				},
				error: function(data) {
					$.tip("服务器错误");
				}
			});
	    })

    }
    //分页
    $(window).scroll(function(){
		var scrollHeight = $(document).height();
	　	var scrollTop = $(document).scrollTop();
	　　	var windowHeight = document.body.clientHeight;
		if(scrollTop + windowHeight >= scrollHeight){
	　　　　	nextPage();
	　　	}
	});
	function nextPage(){
		if(hasNext){
			pageNo ++;
		    flag = false ;
			getData();
		}else{
			// if($('.mui-popup').length==0){
			// 	$.tip('没有更多了！');
			// }
//			var isFocus = $(".num").is(":focus");
//			if(true==isFocus){
//			    return;
//			}else{
//			  	$.tip("没有更多了!");
//			}
		}
	}
})

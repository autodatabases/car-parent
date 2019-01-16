var types= $.getUrlParam('type');
var goodsNum="";
var moneyAmount="";
var agent= $.getUrlParam('agent');
var servicePrice="";
agent= decodeURI(agent);
function clearSession(){
	sessionStorage.removeItem('id_jl');
	sessionStorage.removeItem('id_jy');
	sessionStorage.removeItem('id_jy1');
	sessionStorage.removeItem('product_jl');
	sessionStorage.removeItem('product_jy');
	sessionStorage.removeItem('product_jy1');
}
$(document).ready(function(){
	//如果是宝马或比亚迪用户，则以下参数返回空
	if(agent != "null"){

	} else {
		$(".tyremain").click(function(){
			location.href= DOMIN.MAIN + '/dot.html?type='+types+'&powerorder_by=1';
		});
	}
	clearSession();
	//$('#action-btn').on('click',submit);
	var data="";
	$("#usercarinfo").html(sessionStorage.getItem('usercarinfo'));
	$("#usercityinfo").html(getCookie('selcity')==null?getCookie('city'):getCookie('selcity'));
	if(types==0){
		$(".header h1").html("更换电瓶");
		$(".tyremain").remove();
		var xy = "<dd class='xy'>电瓶更换完成后旧电瓶将会回收</dd>";
		$(".list dl").append(xy);
		queryUserAddress();
		$("#submit").on('click',function(){
			if(!hasAddress){
				$.tip('请选择收货地址！');
				return;
			}
			data= {
				orderType:0,
				//selectedSellerId:selectedSellerId,
				goodsName:goodsName,
				moneyAmount:'',
				goodsNum:1,
				goodsId:'',
				province:sessionStorage.getItem('province'),
				city:sessionStorage.getItem('city'),
				area:sessionStorage.getItem('area'),
				addressDetail:sessionStorage.getItem('addr_detail'),
				userName:sessionStorage.getItem('addr_name'),
				phone:sessionStorage.getItem('addr_phone')
			}
			mui.confirm("是否确定提交订单！","提交订单",['取消','确定'],function(e){
				if (e.index == 1) {
					submit(data);
				} else {

				}
			});
		});
    }else if(types==1){
		$(".address").remove();
		$(".header h1").html("更换轮胎");
		getStore();
		queryluntai();
		$("#submit").on('click',function(){
			data= {
				orderType:1,
				selectedSellerId:sessionStorage.getItem('sellerId'),
				goodsName:sessionStorage.getItem('tyre_select'),
				moneyAmount:'',
				goodsNum:1,
				goodsId:'',
				province:"",
				city:"",
				area:"",
				addressDetail:"",
				userName:"",
				phone:""
			}
			mui.confirm("是否确定提交订单！","提交订单",['取消','确定'],function(e){
				if (e.index == 1) {
					submit(data);
				} else {

				}
			});
		});
	}else if(types==2){
		$(".address").remove();
		$(".header h1").html("小保养");
		$(".list dd").after("<dd><span>公里数（公里）</span><input type='number' id='kilometre' placeholder='点击输入公里数'/></dd>");
		getStore();
		querybaoyang();
		$("#submit").on('click',function(){
			var kilometre=$("#kilometre").val();
			if(!/^[0-9]*[1-9][0-9]*$/.test(kilometre)){
				$.tip("请填写正确公里数！");
				return;
			}else if(kilometre>1000000){
				$.tip("公里数只能1-1000000");
				return;
			}
			var goodsName="";
			var goodsId="";
			if(sessionStorage.getItem("product_jy1")){
				goodsName=sessionStorage.getItem("product_jy")+"|"+sessionStorage.getItem("product_jy1")+","+sessionStorage.getItem("product_jl")
			    goodsId=sessionStorage.getItem("id_jy")+"|"+sessionStorage.getItem("id_jy1")+","+sessionStorage.getItem("id_jl");
			}else{
				goodsName=sessionStorage.getItem("product_jy")+","+sessionStorage.getItem("product_jl")
			    goodsId=sessionStorage.getItem("id_jy")+","+sessionStorage.getItem("id_jl");
			}
			//如果是宝马或比亚迪用户，则以下参数返回空
			if(agent == "比亚迪广州饰和销售服务店"){
				data= {
					orderType:2,
					selectedSellerId:sessionStorage.getItem('sellerId'),
					goodsName:agent,
					mileage:kilometre,
					province:"",
					city:"",
					area:"",
					addressDetail:"",
					userName:"",
					phone:""
				}

			}else {
				data= {
					orderType:2,
					selectedSellerId:sessionStorage.getItem('sellerId'),
					goodsName:goodsName,
					moneyAmount:moneyAmount,
					goodsId:goodsId,
					goodsNum:goodsNum,
					mileage:kilometre,
					province:"",
					city:"",
					area:"",
					addressDetail:"",
					userName:"",
					phone:""
				}
			}

			mui.confirm("是否确定提交订单！","提交订单",['取消','确定'],function(e){
				if (e.index == 1) {
					submit(data);
				} else {

				}
			});
		});
	} else if(types==4){
		$(".address").remove();
		$("#powerorder_jy").remove();
		$(".header h1").html("喷漆");
		$(".list dd").after('<dd><span>幅数 (单位 幅)</span><select id="fushu"><option value="1">1幅</option><option value="2">2幅</option></select></dd>');
		getStore();
		$("#submit").on('click',function(){
			var goodsNum = $("#fushu").val();
			data= {
				orderType:4,
				selectedSellerId:sessionStorage.getItem('sellerId'),
				goodsName:"喷漆服务",
				goodsNum:goodsNum,
			}
			mui.confirm("是否确定提交订单！","提交订单",['取消','确定'],function(e){
				if (e.index == 1) {
					submit(data);
				} else {

				}
			});
		});
		$('.action span').addClass('pc');
	}
});
var hasAddress = false;
var goodsName="";
var selectedSellerId="";

function queryUserAddress(){//跟换电瓶
	$.ajax({
		url : DOMIN.MAIN+"/user/getUserAddress",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				hasAddress = true;
				$('.address').find('a').hide();
				$('#addressinfo').show();
				$('#addr_name').html(data.data.userName);
				$('#addr_phone').html(data.data.mobile);
				$('#addr_detail').html(data.data.proCityArea+data.data.addressContent);
				sessionStorage.setItem('addr_name',data.data.userName);
				sessionStorage.setItem('addr_phone',data.data.mobile);
				sessionStorage.setItem('addr_info',data.data.proCityArea);
				sessionStorage.setItem('addr_detail',data.data.addressContent);
				sessionStorage.setItem('province',data.data.provinceId);
				sessionStorage.setItem('city',data.data.cityId);
				sessionStorage.setItem('area',data.data.areaId);
			}else{
				//$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
		}
	});
	$.ajax({//查看匹配电源
		url : DOMIN.MAIN+"/order/matchPowerProduct",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			//carid:sessionStorage.getItem("usercarid")
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
			    //$(".list dt").find("p").eq(1).html(data.data.productName);
				if(data.data.productName){
					var zimu=data.data.productName;
					//selectedSellerId=data.data.id;
					goodsName=data.data.productName;
					zimu=zimu.substr(zimu.length-1,1);
					if(zimu=="M")
						$(".list dt").find("p").eq(0).html(data.data.productName+' 【蓝标】');
					else if(zimu=="H")
						$(".list dt").find("p").eq(0).html(data.data.productName+' 【银标】');
					else if(zimu=="A")
						$(".list dt").find("p").eq(0).html(data.data.productName+' 【AGM】');
				}
			    $(".list dt").find("span").eq(0).html("￥"+data.data.productPrice);
				$(".list dd").find("span").html(data.data.productPrice);
				$(".action-ok small").html("市场价￥"+data.data.productPrice);
			}else{
				//$.tip(data.message);
				$(".list dt").find("p").eq(0).html('抱歉，无法匹配您的电瓶');
				$(".list dt").find("p").eq(1).html('请联系官方客服  <a href="tel:4008671993">4008671993</a>');
				$("#submit").prop("disabled",true).css("background","#ccc");
				//location.href = DOMIN.MAIN+'/order/ordererr.html';
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	});
}
function queryluntai(){//跟换轮胎
	var price="";
	$.ajax({
		url : DOMIN.MAIN+"/seller/getSeller",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			sellerId:sessionStorage.getItem('sellerId')
		},
		traditional: true,
		success : function(data, textStatus){
			var html="";
			if(data.success){
				html+='<h2>'+data.data.sellerName+'</h2>';
				html+='<img src="/resources/imgs/dot/img1.png"/>';
				html+='<div>';
				html+='<p>'+data.data.addressDetail+'</p>';
				html+='<span>'+data.data.sellerPhone+'</span>';
				html+='</div>';
				html+='<i></i>';
				$(".tyremain").append(html);
				servicePrice=data.data.servicePrice;
				$(".list2 span").html("￥"+data.data.servicePrice);
				if(sessionStorage.getItem('price')){
					price=sessionStorage.getItem('price');
					$(".list1 span").html("￥"+price);
				}else{
					price="500";
					$(".list1 span").html("￥500");
				}
				var zongjia=parseInt(price)+parseInt(servicePrice);
				$(".action-ok small").html("市场价￥"+zongjia);
			}else{
				$.tip(data.message);
			};
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	})
	$(".list1 div").find("p").eq(0).html("马牌轮胎 "+sessionStorage.getItem("tyre_select"));
	$(".list1 img").attr("src","/resources/imgs/repair/lt-img.png")
}
var price="";
function jy_baoyang(data,num,otherRet,pic){//机油4L和1L追加元素公用
	//$(".list1 span").eq(0).html("￥"+data.salePrice*num);
	var pics = "";
	if(pic === 1) {
		pics = otherRet.pic1
	} else {
		pics = otherRet.pic4
	}
	$(".list1 span").eq(0).html("x"+num);
	$(".list1 img").eq(0).attr("src",DOMIN.MAIN+'/resources/imgs/'+pics);
	$(".list1 .jy").find("p").eq(0).html(otherRet.oilText+data.size);
	//$(".list1 .jy").find("p").eq(1).html(data.properties+'*'+num);
	var product=data.product;
	price=data.salePrice*num;
	sessionStorage.setItem("product_jy",data.detailName+'_'+num+'_'+data.salePrice*num);
	sessionStorage.setItem("id_jy",data.id);
}
function querybaoyang(){//小保养
	var price1= "",priceJy1="";
	var carPrice = 0,oilRet={};
	//如果是比亚迪或宝马用户则不再向后台获取机油、机滤数据，在前台页面写定
	if (agent == "比亚迪广州饰和销售服务店") {
		$(".list1").remove();
		$(".list2").remove();
		var strHtml = '';
		strHtml = '<dt class="list1" id="powerorder_jy">'
				+ '<div class="img-warp"><img src= "' + DOMIN.MAIN + '/resources/imgs/repair/jy-img.jpg"/></div>'
				+ '<div class="text jy">'
				+ '<p>比亚迪专用机油</p>'
				+ '</div>'
				+ '<span>x1</span>'
				+ '</dt>'
				+ '<dt class="list1">'
				+ '<div class="img-warp"><img src="' + DOMIN.MAIN + '/resources/imgs/repair/jl-img.png"/></div>'
				+ '<div class="text jy">'
				+ '<p>比亚迪专用机油滤清器</p>'
				+ '</div>'
				+ '<span>x1</span>'
				+ '</dt>'
				+ '<dt class="list2">'
				+ '<div class="img-warp"><img src="' + DOMIN.MAIN + '/resources/imgs/repair/gsf-icon.png"/></div>'
				+ '<div class="text">'
				+ '<p>工时费</p>'
				+ '<p></p>'
				+ '</div>'
				+ '<span></span>'
				+ '</dt>'
		$(".list dl").append(strHtml);
		$(".action-ok small").html("市场价￥577元");
		return
	}
	$.ajax({//查询机油
		url : DOMIN.MAIN+"/order/matchOilProduct",
		type : "post",
		cache : false,
		async : false,
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				if(data.list.length!=0){
					function textCallBack(res){
						oilRet = res;
					}
					carPrice =parseInt(data.list[0].price);
					if(data.list[0].hasOwnProperty('isOrderPriece') &&
							data.list[0].isOrderPriece=='1'){
						getOilRemarkByOrderPrice(data.list[0].oilRule,textCallBack,data.list[0].address);
					}else{
						getCarType(carPrice,textCallBack);
					}

					goodsNum = data.list[0].amount;
					var oilRequireAmount = data.list[0].amount;

					//机油的匹配有的可能没有4L或者1L机油，那么就需要手动处理
					var shouldProcess = false;
					var has4 = false;
					var has1 = false;
					for(var i=0;i<data.list[0].product.length;i++){
						if($.trim(data.list[0].product[i].size) == '4L'){
							has4 = true;
						}else if($.trim(data.list[0].product[i].size) == '1L'){
							has1 = true;
						}
					}
					if(!has4 || !has1){
						shouldProcess = true;
					}
					//这里处理后台手动关联的时候没有关联4L机油
					if(shouldProcess){
						var oil = data.list[0].product[0];
						var oilClone = new Object();
						for(var i in oil){
							oilClone[i] = oil[i];
						}
						if(oil.size=='1L'){
							oilClone.size = '4L';
							oilClone.detailName = oilClone.detailName.replace('1L','4L');
						}else if(oil.size=='4L'){
							oilClone.size = '1L';
							oilClone.detailName = oilClone.detailName.replace('4L','1L');
						}
						data.list[0].product.push(oilClone);
					}

					if(oilRequireAmount>=4){
						if(oilRequireAmount%4==0){
							for(var i=0;i<data.list[0].product.length;i++){
								if($.trim(data.list[0].product[i].size)=="4L"){
									jy_baoyang(data.list[0].product[i],oilRequireAmount/4,oilRet,4);
									break;
								}
							}
						}else{
							for(var i=0;i<data.list[0].product.length;i++){
								if($.trim(data.list[0].product[i].size)=="4L"){
									jy_baoyang(data.list[0].product[i],parseInt(oilRequireAmount/4),oilRet,4);
									break;
								}
							}
							for(var i=0;i<data.list[0].product.length;i++){
								var html='';
								if($.trim(data.list[0].product[i].size)=="1L"){
									html+='<dt class="list1">';
									html+='<div class="img-warp"><img src="'+DOMIN.MAIN+'/resources/imgs/'+oilRet.pic1+'"/></div>';
									html+='<div class="text">';
									html+='<p>'+oilRet.oilText+$.trim(data.list[0].product[i].size)+'</p>';
									// html+='<p>'+data.list[0].product[i].detailName+'</p>';
									//html+='<p style="font-size:12px; color:#bdbdbd;">'+data.list[0].product[i].productCode+'*'+oilRequireAmount%4+'</p>';
									html+='</div>';
									// html+='<span>￥'+data.list[0].product[i].salePrice*(oilRequireAmount%4)+'</span>';
									html+='<span>x'+oilRequireAmount%4+'</span>';
									html+='</dt>';
									$("#powerorder_jy").after(html);
									priceJy1=data.list[0].product[i].salePrice*(oilRequireAmount%4);
									sessionStorage.setItem("product_jy1",data.list[0].product[i].detailName+'_'+oilRequireAmount%4+'_'+data.list[0].product[i].salePrice*(oilRequireAmount%4));
									sessionStorage.setItem("id_jy1",data.list[0].product[i].id);
									break;
								}
							}

						}
					}else{
						for(var i=0;i<data.list[0].product.length;i++){
							if($.trim(data.list[0].product[i].size)=="1L"){
								jy_baoyang(data.list[0].product[i],oilRequireAmount,oilRet,1);
								break;
							}
						}
					}
				}else{
					$(".list1 span").eq(0).html("");
					$(".list1 img").eq(0).attr("src",DOMIN.MAIN+'/resources/imgs/repair/jy-img.jpg');
					$(".list1 .jy").find("p").eq(0).html("抱歉，无法匹配您的机油");
					$(".list1 .jy").find("p").eq(1).html('请联系官方客服  <a href="tel:4008671993">4008671993</a>');
					$("#submit").prop("disabled",true).css("background","#ccc");
					//location.href = DOMIN.MAIN+'/order/ordererr.html';
				}
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	})
	$.ajax({//查询机滤
		url : DOMIN.MAIN+"/order/matchJilvProduct",
		type : "post",
		cache : false,
		async : false,
		dataType : "json",
		traditional: true,
		success : function(data, textStatus){
			var html="",jvRet={};
			if(data.success){
				if(data.list.length!=0){
					function textCallBack(res){
						jvRet = res;
					}
					getCarType(carPrice,textCallBack);
					html+='<dt class="list1">';
					html+='<div class="img-warp"><img src="'+DOMIN.MAIN+'/resources/imgs/'+data.list[0].product.pic+'"/></div>';
					html+='<div class="text">';
		 			html+='<p>'+jvRet.filterText+'</p>';
		 			//html+='<p>'+data.list[0].product.detailName+'</p>';
					//html+='<p style="font-size:12px; color:#bdbdbd;">'+data.list[0].product.productCode+'*'+data.list[0].product.size+'</p>';
					html+='</div>';
					//html+='<span>￥'+data.list[0].product.salePrice+'</span>';
					html+='<span>x1</span>';
					html+='</dt>';
					$(".list2").before(html);
					sessionStorage.setItem("product_jl",data.list[0].product.detailName+'_1_'+data.list[0].product.salePrice);
					sessionStorage.setItem("id_jl",data.list[0].product.id);
					price1=data.list[0].product.salePrice;
				}else{
					html+='<dt class="list1">';
					html+='<div class="img-warp"><img src="'+DOMIN.MAIN+'/resources/imgs/repair/jl-img.png"/></div>';
					html+='<div class="text">';
		 			html+='<p>抱歉，无法匹配您的机滤</p>';
					html+='<p style="font-size:12px; color:#bdbdbd;">请联系官方客服  <a href="tel:4008671993">4008671993</a></p>';
					html+='</div>';
					html+='<span></span>';
					html+='</dt>';
					$(".list2").before(html);
					$("#submit").prop("disabled",true).css("background","#ccc");
					//location.href = DOMIN.MAIN+'/order/ordererr.html';
				}
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	})
	$(".action-ok small").hide();
	/*var province_clearInterval = setInterval(function showTime(){
		if(price!=""&&servicePrice!=""&&price1!=""){
			clearInterval(province_clearInterval);
			var zongjia;
			if(priceJy1!=""){
				zongjia = parseFloat(price)+parseFloat(servicePrice)+parseFloat(price1)+parseFloat(priceJy1);
			}else{
				zongjia = parseFloat(price)+parseFloat(servicePrice)+parseFloat(price1);
			}
			//$(".action-ok small").html("市场价￥"+zongjia.toFixed(1));
			moneyAmount=zongjia.toFixed(1);
			function priceCallBack(res){
				var disParams = res.totalAmount||0;
				$(".action-ok small").html("市场价￥"+disParams.toFixed(1));
			}
			getCarType(carPrice,priceCallBack);
		}
	},300);*/
}
//获取机油，机滤显示列表
function getCarType(carPrice,callBackFn){
	var priceList = [
		{minPrice:0,maxPrice:200000,totalAmount:600, oilText:"机油 10W-40 (嘉实多 金嘉护超净)",filterText:"机滤 (曼牌、马勒、博世)"},
		//{minPrice:150000,maxPrice:200000,totalAmount:800, oilText:"机油 5W-30 (嘉实多 金嘉护超净)",filterText:"机滤 (曼牌、马勒、博世)"},
		{minPrice:200000,maxPrice:300000,totalAmount:1000,oilText:"机油 5W-40 (嘉实多 新磁护)",filterText:"机滤 (曼牌、马勒、博世)"},
		{minPrice:300000,maxPrice:30000000,totalAmount:1500,oilText:"机油 5W-40 (嘉实多 极护)",filterText:"机滤 (曼牌、马勒、博世)"}
	];
	$.each(priceList,function(i,e){
		if(carPrice>=e.minPrice&&carPrice<e.maxPrice){
			callBackFn(e);
			return;
		}
	});
}

//获取机油，机滤显示列表
function getOilRemarkByOrderPrice(orderPrice,callBackFn,city){
	var priceList = [
		{oilText:"机油 10W-40 (嘉实多 金嘉护超净)",filterText:"机滤 (曼牌、马勒、博世)",pic1:"repair/jiyou/jin1.jpg",pic4:"repair/jiyou/jin4.jpg"},
		//{minPrice:150000,maxPrice:200000,totalAmount:800, oilText:"机油 5W-30 (嘉实多 金嘉护超净)",filterText:"机滤 (曼牌、马勒、博世)"},
		{oilText:"机油 5W-40 (嘉实多 新磁护)",filterText:"机滤 (曼牌、马勒、博世)",pic1:"repair/jiyou/ci1.jpg",pic4:"repair/jiyou/ci4.jpg"},
		{oilText:"机油 5W-40 (嘉实多 极护)",filterText:"机滤 (曼牌、马勒、博世)",pic1:"repair/jiyou/ji1.jpg",pic4:"repair/jiyou/ji4.jpg"}
	];
	switch (orderPrice) {
		case "1":
			callBackFn(priceList[0]);
			break;
		case "2":
			callBackFn(priceList[1]);
			break;
		case "3":
			callBackFn(priceList[2]);
			break;
	}
}
var load,isloading=false;
function submit(data){
	if(isloading){
		$.tip('正在加载，请稍后...');
		return;
	}
	load = $.loading();
	isloading = true;
	$.ajax({
		url : DOMIN.MAIN+"/order/createOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data:data,
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				location.href = DOMIN.MAIN+'/order/success.html?orderNo='+data.data.orderNo;
			}else{
				$.tip(data.message);
			}
			isloading = false;
			load.remove();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
			isloading = false;
			load.remove();
		}
	});
}
//获取商家
function getStore() {
	$.ajax({//查询匹配店铺
		url : DOMIN.MAIN+"/seller/getSeller",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			sellerId:sessionStorage.getItem('sellerId')
		},
		traditional: true,
		success : function(data, textStatus){
			var html="";
			if(data.success){
				html+='<h2>'+data.data.sellerName+'</h2>';
				if(data.data.shopPic!=null && data.data.shopPic!=''){
					html+='<img src="'+DOMIN.MAIN+'/imgget/getimg?uri='+data.data.shopPic+'&180X140"/>';
				}else{
					html+='<img src="/resources/imgs/dot/img1.png"/>';
				}
				html+='<div>';
				html+='<p>'+data.data.addressDetail+'</p>';
				html+='<span>'+data.data.sellerPhone+'</span>';
				html+='</div>';
				if (agent != "比亚迪广州饰和销售服务店")
					html+='<i></i>';
				$(".tyremain").append(html);
				servicePrice=data.data.servicePrice;
				// $(".list2 span").html("￥"+data.data.servicePrice);
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			$.tip(errorThrown);
		}
	})
}

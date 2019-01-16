<#include "/commons/header.ftl" />
<title>惠+车服商家端</title>
<link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/index.css?v=${_version}"/>
<script src="${_RESOURCES}/js/index.js?v=${_version}"></script>
</head>
<body>
	<!-- 头部 -->
	<header class="header">
		<span class="name"></span>
		<div class="btn-box">
			<input type="tel" class="btn-text" id="scancoderesult" value="" placeholder="扫描二维码或输入券码"/>
			<input type="button" class="btn-bon"  id="" value="立即验证" />
		</div>
		<div class="test">
			<span class="scancode" onclick="scan();">扫码验证</span>
			<span class="recode" onclick="javascript:location.href='${_ROOT}/coupon/coupon.html'">验证记录</span>
		</div>
	</header>
	<!-- 公告 -->
	<section class="notice">
		<label>公告：</label><span>惠+车服商家端上线</span>
	</section>
	<!-- 服务列表 -->
	<section class="list">
		<div>
			<a href="${_ROOT}/order/order.html?orderStatus=1" class="yfw"><b></b>订单管理</a>
			<a href="tel:400-101-1506" class="shfw"><b></b>售后服务</a>
		</div>
		<div>
			<a href="${_ROOT}/setlment/setlment.html" class="yjs"><b></b>结算管理</a>
			<a href="${_ROOT}/busines/busines-setup.html" class="yusz"><b></b>用户设置</a>
		</div>
		<div style="display:none;">
			<a href="${_ROOT}/repair/order.html" class="wxdd"><b></b>维修订单</a>
			<a style="background: #fcfcfc;"><b></b></a>
		</div>
	</section>
	<!-- 服务热线 -->
	<section class="hot-tel">
		<i></i>客服热线：<a href="tel:400-101-1506">400-101-1506</a>
	</section>
		<script type="text/javascript">
    	  //获取服务端的秘钥
    	  wx.config({
      		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
      		    appId: '${appId}', // 必填，公众号的唯一标识
      		    timestamp: '${timestamp}', // 必填，生成签名的时间戳
      		    nonceStr: '${noncestr}', // 必填，生成签名的随机串
      		    signature: '${signature}',// 必填，签名，见附录1
      		    jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
      		});


    	  wx.ready(function(){
    		  //scan();
    	  });
		  wx.error(function(res){
			 // alert('${url}'');
    		  //alert("微信js接口失败！"+JSON.stringify(res));
    		});

		  function scan(){
			  wx.scanQRCode({
				    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
				    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
				    success: function (res) {
				    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				    //location.href = 'http://smart.emateglobal.com/car-wechat/orderlist.html';
				    $("#scancoderesult").val(result);
				}
			});
		  }
		  $(function(){
		      $(".btn-bon").click(function(){
			  toOrderDetail();
			  });
			  $.ajax({
				type:'post',
				url:DOMIN.MAIN+'/user/getSeller',
				async:false,
				cache:false,
				dataType:'json',
				success:function(result){
					if (result.success){
					   $(".name").html(result.data.sellerName);
					   if(result.data.sellerCity=="中山"){
					       $(".list div").eq(2).show();
					   }
					}else{
						$.tip(result.message);
					}
				}
			   });
		  })
		  function toOrderDetail(){
			  var code = $("#scancoderesult").val($.trim($("#scancoderesult").val())).val();
			  if(code == ""){
				  $.tip('未扫码！');
				  return;
			  }
			  if(!/^\d{8}$/.test(code)){
				$.tip('请输入正确的券码！');
				  return;
			  }
				$.ajax({
					url : DOMIN.MAIN+"/order/queryOrderDetail",
					type : "post",
					cache : false,
					async : true,
					dataType : "json",
					data: {
						code:code
					},
					traditional: true,
					success : function(data, textStatus){
						if(data.success){
						    if(data.data.status==2){
								location.href = DOMIN.MAIN + '/order/order-details.html?code='+code+"&isScan=1";
							}else  if(data.data.status==1){
							    $.tip("请先确认收货！");
							}else{
								 $.tip("订单状态不正确！");
							}
						}else{
							$.tip(data.message);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
					}
				});
		  }

    </script>
</body>
</html>

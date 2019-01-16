<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
        <title>代驾首页</title>
        <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/daijia/daijia.css?v=${_version}"/>
        <link rel="stylesheet" type="text/css" href="${_RESOURCES}/js/common/mobiscroll/mobiscroll-2.13.2.full.min.css"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=C93b5178d7a8ebdb830b9b557abce78b"></script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="${_RESOURCES}/js/common/mobiscroll/mobiscroll-2.13.2.full.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="${_RESOURCES}/js/index/position.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${_RESOURCES}/js/oilCard/daijia/daijia.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
        <style>
            .dwhl:nth-child(1) .dwfl:nth-child(1) .dw-i:not(:empty):after {
                content: "日";
            }
            /* .dwhl:nth-child(1) .dwfl:nth-child(2) .dw-i:not(:empty):after {
                content: "日";
            } */
            .dwhl:nth-child(2) .dwfl:nth-child(1) .dw-i:not(:empty):after {
                content: "时";
            }
            .dwhl:nth-child(2) .dwfl:nth-child(2) .dw-i:not(:empty):after {
                content: "分";
            }
        </style>
    </head>
    <body>
        <header class="header">
            <span class="back mui-action-back"></span>
            <h1>代驾</h1>
        </header>
        <div class="con-top">
            <div class="address">
                <a href="${_ROOT}/driver/citygps.html"><b></b><span id="cityname"></span><i></i></a>
            </div>
            <div class="standards"><a href="${_ROOT}/driver/services.html"></a></div>
        </div>
        <div class="con-center">
            <section class="part01">
                <div>服务金额</div>
                <div class="money" id="money" money="120">120元</div>
            </section>
            <section class="part02">
                <p>服务承诺：</p>
                <ul>
                    <li><i>1</i> 24小时之内</li>
                    <li><i>2</i> 规定市区之内</li>
                    <li><i>3</i> 不限制时间</li>
                    <li><i>4</i> 不限制地点</li>
                </ul>
            </section>
            <section class="part03">
                <ul>
                    <li> <label><i></i>起点</label> <input type="text" placeholder="请输入详细地址" id="startAdd"> </li>
                    <li> <label><i></i>终点</label> <input type="text" placeholder="请输入详细地址" id="endAdd"> </li>
                    <li> <label><i></i>预约时间</label> <input type="text" placeholder="请选择时间" id="time"  name="time"> </li>
                </ul>
                <div>
                    <i></i> <p>距离预约时间30分钟以内时，不支持预约订单、取消订单和更改预约时间！</p>
                </div>
            </section>
            <section class="part04">
                <div>
                    <!-- <a href="${_ROOT}/driver/success.html">代驾记录 》</a> -->
                    <a href="${_ROOT}/driver/record.html">代驾记录 》</a>
                </div>
                <div>
                    <button id="yuyue">立即预约</button>
                </div>
            </section>
        </div>
        <div class="popup">
            <div class="bg"></div>
            <div class="info-pop">
                <ul>
                    <li><span id="pop-close">X</span> 填写信息</li>
                    <li><span>姓名：</span> <input type="text" placeholder="请填写你的姓名" id="userName" /> </li>
                    <li><span>性别：</span> <input type="radio" name="radio" value="1" class="sex01"/> 女士
                        <input type="radio" name="radio" value="2" class="sex02"/> 男士
                    </li>
                    <li><span>电话：</span> <input type="text" placeholder="请填写你的电话号码" id="userPhone"> </li>
                    <li><button class="next" id="next">下一步</button> </li>
                </ul>
            </div>
            <div class="pay-pop">
                <ul>
                    <li><span id="prev">上一步</span>代驾费用</li>
                    <li><p id="paymoney" money="120">￥120.00</p></li>
                    <li><span>支付内容</span> <span>代驾购买</span> </li>
                    <li><span>支付方式</span> <span>余额支付</span> </li>
                    <li><button class="next" id="pay"> <p>立即支付</p> <p class="balance"></p></button></li>
                </ul>
            </div>
        </div>
        <div class="tip">
	        <div class="bg"></div>
	        <div class="tipCon">
	            <h3>提示</h3>
	            <div class="cont">
	                <p>是否同意追电科技 <a href="${_ROOT}/driver/services.html">《代驾协议》</a> </p>
	            </div>
	            <div class="btnCon">
					<div class="btn_no">不同意</div>
	                <div class="btn_ok">同意</div>
	            </div>
	        </div>
	    </div>
    </body>
</html>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
        <title>代驾详情</title>
        <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/com/com.css?v=${_version}"/>
	    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/daijia/daijia_detail.css?v=${_version}"/>
        <link rel="stylesheet" type="text/css" href="${_RESOURCES}/js/common/mobiscroll/mobiscroll-2.13.2.full.min.css"/>
	    <script type="text/javascript">
			var DOMIN = {MAIN:'${_ROOT}'};
		</script>
		<script src="${_RESOURCES}/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/common/common.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
	    <script src="${_RESOURCES}/js/mui/mui.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="${_RESOURCES}/js/common/mobiscroll/mobiscroll-2.13.2.full.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="${_RESOURCES}/js/oilCard/daijia/daijia_detail.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
    </head>
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
    <body>
        <header class="header">
            <span class="back mui-action-back"></span>
            <h1>订单详情</h1>
        </header>
        <div class="content">
            <section class="con-top">
                <p>恭喜你</p>
                <p>预约成功！</p>
            </section>
            <section class="detail">
                <ul>
                    <li class="startAddress">起点： <span>加载中...</span> </li>
                    <li class="endAddress">终点： <span>加载中...</span> </li>
                    <li class="appointmentTime">预约时间： <span>加载中...</span> </li>
                    <li class="orderNo">订单编号： <span>加载中...</span> </li>
                </ul>
            </section>
            <section class="process">
                <div class="pro-left">
                    <ul>
                        <!-- <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li> -->
                    </ul>
                </div>
                <div class="pro-right">
                    <ul>
                        <!-- <li><span class="nowTimes">2018-2-10 12:22:00</span><span>等待代驾司机接单中</span></li>
                        <li><span class="nowTimes">2018-2-10 12:22:00</span><span>您已成功下单，司机正在赶来的路上</span></li>
                        <li><span class="nowTimes">2018-2-10 12:22:00</span><span>代驾司机已到达预定地点</span></li>
                        <li><span class="nowTimes">2018-2-10 12:22:00</span><span>已到达目的地，服务完成！</span></li>
                        <li><span class="nowTimes">2018-2-10 12:22:00</span><span>您已取消订单</span></li>
                        <li><span class="nowTimes">2018-2-10 12:22:00</span><span>修改预约时间成功</span></li> -->
                    </ul>
                </div>
            </section>
            <section class="bottom-btn" style="display:none">
                <input type="text" id="time" style="display:none" />
                <div class="cancel">
                    取消订单
                </div>
                <div class="change">
                    更改预约时间
                </div>
            </section>
        </div>
        <div class="popup">
            <div class="bg"></div>
            <div class="pop-con">
                <div class="popCon-top">
                    <!-- 代驾司机已出发，您无法申请取消订单！ -->
                </div>
                <a class="popCon-bottom tel" href="tel:400-880-1768" style="display:none">联系客服</a>
                <p class="popCon-bottom btn" >确定</p>
            </div>
        </div>
        <div class="tip">
	        <div class="bg"></div>
	        <div class="tipCon">
	            <h3>提示</h3>
	            <div class="cont">
	                <p>确认是否取消此次订单？</p>
	            </div>
	            <div class="btnCon">
	                <div class="btn_close">否</div>
	                <div class="btn_cancel">是</div>
	            </div>
	        </div>
	    </div>
    </body>
</html>

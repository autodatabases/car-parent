<#include "../../commons/header.ftl" />
    <title>洗车</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/oilCard/buyWashCar/washHome.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/oilCard/buyWashCar/washHome.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
    <header class="header">
        <span class="back mui-action-back"></span>
        <h1>洗车</h1>
    </header>
    <div class="content">
        <div class="washCode">
            <ul>
                <li>
                    <p>剩余洗车次数</p>
                    <p class="washTimes">0</p>
                </li>
                <li>
                    <p>剩余洗车券</p>
                    <p class="ticket">0</p>
                </li>
            </ul>
            <div class="xicheBtn">
                <button class="washRecord">洗车记录</button>
                <button class="wangdian">网点查询</button>
                <button class="code">洗车券码</button>
                <button class="exchage">立即洗车</button>
            </div>
        </div>
        <div class="setmale">
            <h3>购买套餐</h3>
            <ul>
                <li><p>套餐一</p><p>购买洗车服务1次</p></li>
                <li><p>套餐二</p><p>购买洗车服务5次</p><p>(额外赠送服务1次)</p></li>
                <li><p>套餐三</p><p>购买洗车服务10次</p><p>(额外赠送服务5次)</p></li>
            </ul>
            <div class="btncon">
                <button class="purchase">购买记录</button>
                <button class="btn">结算（￥<span>20</span>）</button>
            </div>

        </div>
        <div class="liucheng">
            <h3>服务流程</h3>
            <ul>
                <li></li>
                <li>兑换洗车</li>
                <li></li>
                <li>到店洗车</li>
                <li></li>
            </ul>
            <ul>
                <li style="background: #2cc7ff;">1</li>
                <li style="background: #ffae07;">2</li>
                <li style="background: #45ccab;">3</li>
                <li style="background: #ff696b;">4</li>
                <li style="background: #39b89a;">5</li>
            </ul>
            <ul>
                <li>购买套餐</li>
                <li></li>
                <li>网点查询</li>
                <li></li>
                <li>服务完成</li>
            </ul>
        </div>
    </div>
    <div class="tip" id="tip01">
        <div class="bg"></div>
        <div class="tipCon">
            <h3>提示</h3>
            <div class="cont">
                <p>您暂无洗车券，</p>
                <p>是否立即兑换洗车券?</p>
            </div>
            <div class="btnCon">
                <div class="btn_close">取消</div>
                <div class="btn_ok" id="btnOk01">立即兑换</div>
            </div>
        </div>
    </div>
    <div class="tip" id="tip02">
        <div class="bg"></div>
        <div class="tipCon">
            <h3>确认购买套餐</h3>
            <div class="cont"></div>
            <div class="btnCon">
                <div class="btn_close">取消</div>
                <div class="btn_ok" id="btnOk02">确认购买</div>
            </div>
        </div>
    </div>
</body>
</html>

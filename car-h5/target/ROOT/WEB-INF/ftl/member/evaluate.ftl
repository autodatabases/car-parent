<#include "../commons/header.ftl" />
    <title>订单评价</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/member/evaluate.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/member/evaluate.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
    <!-- 头部 -->
    <header class="header">
        <span class="back mui-action-back"></span>
        <h1>发表评价</h1>
        <span class="release">发布</span>
    </header>
    <!--  list -->
    <div class="info">
        <div class="service">
            <div class="img-box">
                <img src="${_RESOURCES}/imgs/index/xc-new.png">
            </div>
            <div class="type">
                洗车服务
            </div>
            <div class="status">
                服务状态：<span>已完成</span>
            </div>
        </div>
        <div class="message">
            <textarea id="message" placeholder="本次洗车你是否满意？我们仍在完善服务和提升服务品质中，乐于倾听你的意见以及建议，感谢您的分享"></textarea>
        </div>
    </div>
    <div class="code">
        <div class="shop"><span>门店评分</span></div>
        <div class="score">
            <span>商家态度</span>
            <ul class="starts" id="starts1">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <span class="mark1" id="mark1">0 分</span>
        </div>
        <div class="score">
            <span>物流速度</span>
            <ul class="starts" id="starts2">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <span class="mark2" id="mark2">0 分</span>
        </div>
    </div>
</body>
</html>

<#include "../commons/header.ftl" />
    <title>洗车</title>
    <link rel="stylesheet" type="text/css" href="${_RESOURCES}/css/carWash/kwashCode.css?v=${_version}"/>
    <script src="${_RESOURCES}/js/carWash/kwashCode.js?v=${_version}" type="text/javascript" charset="utf-8"></script>
</head>
<body>
    <header class="header">
        <span class="back"></span>
        <h1>洗车</h1>
    </header>
    <div class="content">
        <div class="washCode">
            <p>请将10位核销码告知服务商</p>
            <p class="code"></p>
            <p>此服务由宽途洗车提供</p>
            <p>券码有效期至：<span class="data"></span></p>
        </div>
        <div class="status"><span id="status"></span></div>
        <div class="myTip" style="display: none;"><span>温馨提示</span></div>
        <div class="myMsg" style="display: none;">
        	<span>请将10位核销码告知洗车网点服务商家，商家验证后将为您提供免费洗车服务。如有问题请拨打客服热线：400-867-1993</span>
        </div>
    </div>
</body>
</html>

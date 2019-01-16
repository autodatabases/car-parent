//根据cookie判断是否登录(没登录跳转登录页面)
if (getCookie('CAR_OIL_TOKEN') == null) {
    window.location.href = DOMIN.MAIN + "/oilUser/oilLogin.html";
}
var supplier = getCookie("gaoyang");
$(document).ready(function () {

    var className;
    $('.navTab li').on('click', function () {
        className = $(this).attr('class');
        switch (className) {
            case 'aiqiyi':
                window.location.href = DOMIN.MAIN + "/oilUser/aiqiyi.html?goodType=3&orderType=8";
                break;
            case 'txvideo':
                window.location.href = DOMIN.MAIN + "/oilUser/txvideo.html";
                break;
            case 'sohu':
                window.location.href = DOMIN.MAIN + "/oilUser/souhu.html?goodType=5&orderType=10";
                break;
            case 'youku':
                window.location.href = DOMIN.MAIN + "/oilUser/youku.html";
                break;
        }
    })
})
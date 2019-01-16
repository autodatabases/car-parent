String.prototype.trim = function() {
  return this.replace(/\s+|\s+/g, '');
}
var newphone = $.getUrlParam("tel");
$(document).ready(function() {
    $("#login").on("click",function() {
        if($.trim($("#card").val()) === "") {
            $.tip("请输入已绑定的车牌号！");
            return;
        }
        if(!/^\d{21}$/.test($.trim($("#car").val()))) {
            $.tip("请输入格式为21位数字的保单号！");
            return;
        }
        if (!/^\d{11}$/.test($("#userName").val().trim())) {
            $.tip("请输入格式为11位数字的手机号！");
            return;
		};
        submit();
    })
})
function submit() {
    $("#login").prop('disabled',true);
    $.ajax({
        url: DOMIN.MAIN + "/user/updateuserphone",
        type: "POST",
        dataType: "json",
        data: {
            newphone: newphone,
            license: $.trim($("#card").val()),
            baoDan: $.trim($("#car").val()),
            oldPhone: $.trim($("#userName").val()).trim()
        },
        success: function(data) {
            if(data.success) {
                addCookie('CAR_H5_TOKEN',data.data,360);
                window.location.href = DOMIN.MAIN;
            } else {
                $.tip(data.message);
                $("#login").prop('disabled',false);
            }
        },
        error: function() {
            $("#login").prop('disabled',false);
            $.tip("链接服务器失败！");
        }
    })
}

var DOMIN = {MAIN:'/admin'};
$.noDataFond = '没有查询到数据。';

function getNoDataHtml(){
	var html = '<div style="font-size:0.75rem;text-align:center;clear:both;margin:1rem 0;padding:0;height:19.3rem;">'+$.noDataFond+'</div>';
	return html;
}

function windowScroll(){
		var fixed_display_element = $("#fixed_display_element");
		if(!fixed_display_element || fixed_display_element == null || fixed_display_element == undefined){
			return;
		}

		//显示"回到顶部"
		if(getScroll().t>=50){
			$("#fixed_display_element").fadeIn(1000);
			//$('body').css('padding-bottom',fixed_display_element.show().height());
		}else{
			$("#fixed_display_element").fadeOut(1000);
			//fixed_display_element.hide();
			//$('body').css('padding-bottom',0);
		}
};

/**设置window窗口滚动函数*/
$(window).scroll(function(){
	windowScroll();
});


/**获取当前窗口滚动位置*/
function getScroll()
{
    var t, l, w, h;

    if (document.documentElement && document.documentElement.scrollTop) {
        t = document.documentElement.scrollTop;
        l = document.documentElement.scrollLeft;
        w = document.documentElement.scrollWidth;
        h = document.documentElement.scrollHeight;
    } else if (document.body) {
        t = document.body.scrollTop;
        l = document.body.scrollLeft;
        w = document.body.scrollWidth;
        h = document.body.scrollHeight;
    }
    return { t: t, l: l, w: w, h: h };
}

/**物理设备像素和屏幕分辨率比例*/
window['mobileDevicePixelRatio'] = window['devicePixelRatio'] || 1;
if (window['mobileDevicePixelRatio'] > 1) {
    window['mobileDevicePixelRatio'] = 2;
} else {
    window['mobileDevicePixelRatio'] = 1;
}

/**定义dom对象方法函数*/
(function($) {
    //显示tips
    $.tip = function(msg){
    	// var settings = {
    	// 	html:'tip'
    	// };
    	// var self = $('<div class="mobileui-tip"><div>'+msg+'</div></div>').appendTo('body');
    	// var parentWidth = self.outerWidth();
    	// var child = self.children();
    	// child.css('margin-left',(parentWidth - child.outerWidth())/2);
    	// self.hide().fadeIn(500);
    	// setTimeout(function(){
    	// 	self.animate({top:'0'},200).fadeOut(1000,function(){
    	// 		self.remove();
    	// 	});
    	// },2000);
		var tipHtml = '';
		    tipHtml = '<div class="tips-content">';
	    	tipHtml += '<div class="tips-bg"></div>';
	    	tipHtml += '<div class="tips-box">';
	    	tipHtml +=	'<div class="tips-top">';
	    	tipHtml +=	'<p style="float: left;">提示</p>';
	    	tipHtml +=	'<span style="float: right;" class="close">x</span>';
	    	tipHtml +=	'</div>';
	    	tipHtml +=	'<div class="tips-center">';
	        tipHtml += '<p class="tips-icon"></p>';
	    	tipHtml +=	'<p class="tips-info">'+ msg +'</p>';
	    	tipHtml +=	'</div>';
	    	tipHtml +=	'<div class="tips-bottom">';
	    	tipHtml +=	'<input type="button" class="tips_btn close" value="确认" />';
	    	tipHtml +=	'</div>';
	        tipHtml +=	'</div>';
	        tipHtml += '</div>';
			$('body').append(tipHtml);
			$(".close").on('click',function() {
				$(".tips-content").remove();
			})
    }

    /** 加载的loading图片**/
	$.loading = function(){
		var self = $('<div class="carui-loading"><div><img src="'+DOMIN.MAIN+'/resources/imgs/public/loading.gif"/></div></div>').appendTo('body').hide().fadeIn(500);

		var returnObj = {
			remove:function(){
				self.remove();
			}
		};
		return returnObj;
	}
})($);

// 设置cookie
function addCookie(name,value,time)
{
	var Days = 1;
	if(/^\d+$/.test(time)){
		Days = time;
	}
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();


//    var strsec = getsec(time);
//    var exp = new Date();
//    exp.setTime(exp.getTime() + strsec*1);
//    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
};

//读取cookies
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return (unescape(arr[2]));
    else
        return null;
};


//删除cookie
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";path=/;expires="+exp.toGMTString();
};


/*未登录跳转*/
function checkLogin(){
		var token =getCookie("CAR_ADMIN_TOKEN");
		if(token == null || token == undefined || token == "" || token == "-1"){
			//$.t
			location.href = DOMIN.MAIN + '/auth/login.html';
		}
};

$.getDateStr = function(type){
	var dataStr;
 	var d = new Date();
 	if(type == 2){
 		dataStr = d.getFullYear()+'年'+(d.getMonth()+1)+'月'+d.getDate()+'日' +d.getHours()+'时'+d.getMinutes()+'分'+d.getSeconds()+'秒';
		return dataStr;
 	}
	dataStr = d.getFullYear()+'年'+(d.getMonth()+1)+'月'+d.getDate()+'日';
	return dataStr;
}
$.formatDate = function(time){
	var dataStr;
 	var d = new Date(time);
	var hours=d.getHours();
	if(parseInt(hours)<10){
		hours="0"+hours;
	}
	var minutes=d.getMinutes();
	if(parseInt(minutes)<10){
		minutes="0"+minutes;
	}
	var seconds=d.getSeconds();
	if(parseInt(seconds)<10){
		seconds="0"+seconds;
	}
	var getDate=d.getDate();
	if(parseInt(getDate)<10){
		getDate="0"+getDate;
	}
	var getMonth=d.getMonth()+1;
	if(parseInt(getMonth)<10){
		getMonth="0"+getMonth;
	}
 	dataStr = d.getFullYear()+'-'+getMonth+'-'+getDate+' ' +hours+':'+minutes+':'+seconds+'';
	return dataStr;
}
$.getUrlParam = function(name) {
     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
     var r = window.location.search.substr(1).match(reg);  //匹配目标参数
     if (r != null) return unescape(r[2]); return null; //返回参数值
 }
$.parseForm = function(form) {
    var $form = $(form);
    if (!$form[0]) return null;

    var fields = $form.serializeArray();
    if (!fields.length) return null;

    var result = {};
    $.each(fields, function(i, o) { result[o.name] =$.trim(o.value); });

    return result;
};

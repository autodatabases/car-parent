checkLogin();
var musicAlterTime=5*60*1000;
$(function(){
	if(getCookie('CAR_ADMIN_TOKEN')==null){
		return;
	}
	if(getCookie('car-admin-unid')==null){
		addCookie('car-admin-unid',new Date().getTime()+"",720);
	}
	detectOS();
    getMenuList();
    //initAutoMusicPlay();
	getName();
    bindEvent();
	if(localStorage.getItem('globalPageSize') == null){
		localStorage.setItem('globalPageSize',5);
	}else{
		$("#globalPageSize").val(localStorage.getItem('globalPageSize'));
	}

	$("#globalPageSize").on('change',function(){
		localStorage.setItem('globalPageSize',$("#globalPageSize").val());
	});
	//显示当前系统时间
	setInterval(function(){
			$("#nowTime").html($.getDateStr(2));
	},1000);
});

var backgroundMusic,mp3_clearInterval;
function initAutoMusicPlay(){
	backgroundMusic = document.createElement('AUDIO');
	backgroundMusic.setAttribute('autoplay',false);
	backgroundMusic.setAttribute('src','member/2478.mp3');
	document.body.appendChild(backgroundMusic);
	audioPause();
	$(".info-box span").click(function(){//手动关闭
		deleteNewOrderAlter();
		$(".info-box").hide();
		audioPause();//关闭音乐提醒
		//mp3_clearInterval=setInterval(function(){showTime()},musicAlterTime);//重启定时器
	});
	$(".info-box b").click(function(){//手动关闭
		$(".info-box span").click();
	});
}
function showTime(){
	$.ajax({
		url : DOMIN.MAIN+"/order/hasNewOrder",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			unid:getCookie('car-admin-unid'),
			userName:$("#adminName").text(),
			userAgent:navigator.userAgent
		},
		traditional: true,
		success : function(res, textStatus){
			if(res.success){
				clearInterval(mp3_clearInterval);//关闭定时器
				$(".info-box").css("display","block");//显示弹窗
				audioPlay();//播放音乐
				setTimeout(function(){
					if($(".info-box").css("display")=="block"){//如果没有手动关闭一分钟自动关闭
						audioPause();//关闭音乐提醒
						mp3_clearInterval=setInterval(function(){showTime()},musicAlterTime);//重启定时器
					}
				},musicAlterTime);
			}
		}
	});
};
//播放音乐
function audioPlay(){
	backgroundMusic.play();
}
//从头开始
function goToFirst(){
	backgroundMusic.currentTime=0;
	backgroundMusic.play();
}
//暂停
function audioPause(){
	backgroundMusic.pause();
}
function deleteNewOrderAlter(){//删除查询出来的订单
	$.ajax({
		url : DOMIN.MAIN+"/order/deleteNewOrderAlter",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {},
		traditional: true,
		success : function(res, textStatus){
		}
	});
};

var allMenu;
function detectOS() {//获取当前操作系统
    var sUserAgent = navigator.userAgent;
    var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
    var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
    if (isMac) return "Mac";
    var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
    if (isUnix) return "Unix";
    var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
    if (isLinux) return "Linux";
    if (isWin) {
        var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
        if (isWin2K) return "Win2000";
        var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
        if (isWinXP) return "WinXP";
        var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
        if (isWin2003) return "Win2003";
        var isWinVista= sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
        if (isWinVista) return "WinVista";
        var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
        if (isWin7) return "Win7";
		var isWin10=sUserAgent.indexOf("Windows NT 10.0") > -1 || sUserAgent.indexOf("Windows NT 10.0") > -1;
		if (isWin10) return "Win10";
    }
    return "other";
}
function getName(){//获取首页管理员信息
    $(".user-right li").eq(0).html("<label>当前操作系统：</label>"+detectOS());
	$.ajax({
        url : DOMIN.MAIN+"/index/adminHomeSummary",
        type : "post",
        cache : false,
        async : true,
        dataType : "json",
        data: {},
        traditional: true,
        success : function(res, textStatus){
            if(res.success){
            	$("#adminName").html(res.data.adminName);
            	if(res.data.adminName == 'admin' || (res.data.roleName && (res.data.roleName.indexOf('运营')!=-1 || res.data.roleName.indexOf('超级管理员')!=-1 || res.data.roleName.indexOf('车服客服')!=-1))){
            		//mp3_clearInterval=setInterval(function(){showTime()},musicAlterTime);//订单提示定时器
					$('.tables>div').show();
            	}else{
            		  $('.tables').empty().html('<p style="margin:20px;width:100%;">欢迎登陆: '+res.data.adminName+'</p>');
            		  return;
            	}
                $(".user-left li").eq(0).html("<label>管理员账号：</label>"+res.data.adminName);
				$(".user-left li").eq(1).html("<label>管理员组：</label>"+res.data.roleName);
				$(".user-left li").eq(2).html("<label>最后登录时间：</label>"+$.formatDate(res.data.lastLoginTime));
				$(".user-left li").eq(3).html("<label>最后登录IP/地址：</label>"+res.data.lastLoginIp);
				$(".user-left li").eq(4).html("<label>登录次数：</label>"+res.data.loginTime);
				$("#finishOrder").html(res.data.finishOrder);
				$("#importPolicy").html(res.data.importPolicy);
				$("#seller").html(res.data.seller);
				$("#member").html(res.data.member);
				$("#bindmember").html(res.data.boundMember);
				//$("#yesterdayOrder").html(res.data.yesterdayOrder);
				$("#yesterdayOrder").html("洗车:<label>"+res.data.yesterdayXiOrder+"</label>&nbsp保养:<label>"+res.data.yesterdayByOrder+"</label>");
				//$("#todayOrder").html(res.data.todayOrder);
				$("#todayOrder").html("洗车:<label>"+res.data.todayXiOrder+"</label>&nbsp保养:<label>"+res.data.todayByOrder+"</label>");
				$("#yesterdaymember").html(res.data.yesterdayMember);
				$("#todaymember").html(res.data.todayMember);
				$("#yesterdayPolicy").html(res.data.yesterdayPolicy);
				$("#todayPolicy").html(res.data.todayPolicy);
				$("#allMoney").html(res.data.allMoney);
				$("#allOrders").html(res.data.allOrders);
				//$("#yesterdaySeller").html("0");
				//$("#todaySeller").html("0")
            }else{
                $.tip(res.message);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert(errorThrown);
        }
    });
};
//获取菜单列表
function getMenuList() {
    $.ajax({
        url : DOMIN.MAIN+"/permission/queryUserResourcs.json",
        type : "post",
        cache : false,
        async : true,
        dataType : "json",
        data: {},
        traditional: true,
        success : function(res, textStatus){
            if(res.success){
                var menuList = res.data.authTree||[];
                allMenu = menuList;
                renderPackages(menuList);
            }else{
                $.tip(res.message);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert(errorThrown);
        }
    });
}
function renderPackages(records) {
    var $menuContainer = $(".nav-warp"),$sidebarContainer = $(".sidebar ul");
    var headHtml = [],
        menuClz = [
            {clz : "index", label : "首页"},
            {clz : "statics", label : "汽车"},
            {clz : "busines", label : "商家"},
            {clz : "order", label : "订单"},
            {clz : "report", label : "统计"},
            {clz : "member", label : "会员"},
            {clz : "system", label : "系统"},
            {clz : "article", label : "文章"},
            {clz : "counterman", label : "业务员"},
            {clz : "oilcard", label : "增值服务"},
            {clz : "electricbasic", label : "追电基础服务"},
            {clz : "shop", label : "积分商城"}
        ];
    $.each(records, function (k, v) {
       /*var contentHtml = [];
       var sidebarList  = v.systemPages||[];
       if(sidebarList.length>0){
           $.each(sidebarList, function (i, f) {
               var str = "$('.tables').load('"+(f.pageUrl).substring(1)+"')";
               contentHtml.push('<li><a href="javascript:void(0)" onclick="'+str+'">'+f.name+'</a></li>');
           });
           $sidebarContainer.append(contentHtml.join(''));
       }*/
        var clz = menuClz.filter(function(e) {
          return e.label==v.name;
        });
        headHtml.push('<a href="javascript:void(0)" class="'+clz[0].clz+'"><i></i>' +v.name+'</a>');
    });
    $menuContainer.append(headHtml.join(''));
}
function bindEvent() {
    //时间戳
    var getTimestamp=new Date().getTime();
    var $menuContainer = $(".nav-warp");
    var $sidebarContainer = $(".sidebar ul");
    $menuContainer.delegate('a','click',function(){
        $(this).children('i').addClass('on');
        $(this).addClass('on').siblings('a').children('i').removeClass('on');
        $(this).siblings('a').removeClass('on');
        var text = $(this).text();
        $.each(allMenu,function(i,value){
            if(value.name == text){
                var subMenu = value.systemPages;
                $sidebarContainer.empty()
                $.each(subMenu, function (i, f) {
                    var data = f.pageUrl.substring(1);
                    $sidebarContainer.append('<li><a href="javascript:void(0)" data-url="'+data+'?timestamp='+getTimestamp+'">'+f.name+'</a></li>');
                });
            }
        });
		$(".sidebar li").eq(0).find("a").click();
    });

    $sidebarContainer.delegate('a','click',function(){
        $(this).parent().addClass('on');
        $(this).parent().siblings().removeClass('on');
        $('.tables').load($(this).attr('data-url'));
    });

    $(".exit_login").click(function(){//退出登录
		delCookie("CAR_ADMIN_TOKEN");
		location.href=DOMIN.MAIN;
	});

}

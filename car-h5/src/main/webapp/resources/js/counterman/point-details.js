var pageNo = 1;
var pageSize = 20;
var hasNext = true;
$(document).ready(function() {
    getData();

    function getData() {
        $.ajax({
            type: "get",
            url: DOMIN.MAIN + "/counterman/getScoreRecordList",
            async: true,
            dataType: "json",
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            success: function(data) {
                if (data.success) {
                    if (data.pageInfo.pageCount == data.pageInfo.pageIndex) {
                        hasNext = false;
                    } else {
                        hasNext = true;
                    }
					if(data.list.length < 1) {
						$(".nullTips").show();
					}
                    renderPage(data);
                } else {
                    $.tip(data.message);
                }
            },
            error: function(data) {
                $.tip("服务器错误");
            }
        });
    }

    function renderPage(data) {
        var html = "";
        var pointType = "";
        var operator = "";
        for (var i = 0; i < data.list.length; i++) {
            switch (data.list[i].operationType) {
                case 0:
                    pointType = "保单收入";
                    operator = "+";
                    break;
                case 1:
                    if (data.list[i].score >= 0) {
                        pointType = "积分赠送";
                        operator = "+";
                    } else {
                        pointType = "积分扣除";
                        operator = "";
                    }
                    break;
                case 2:
                    pointType = "积分兑换";
                    operator = "";
                    break;
                default:
                    break;
            }
            html += "<li><div><span class='detail'><p class='pointType'>" + pointType + "</p>"
            html += "<p class='pointData'>" + $.formatDate(data.list[i].updateTime) + "</p></span>"
            html += "<span class='number'>" + operator + data.list[i].score + "</span></div></li>"
        }
        $(".list").append(html);
    }

    //分页
    $(window).scroll(function() {
        var scrollHeight = $(document).height();　
        var scrollTop = $(document).scrollTop();　　
        var windowHeight = document.body.clientHeight;　　
        if (scrollTop + windowHeight >= scrollHeight) {　　　　
            nextPage();　　
        }
    });

    function nextPage() {
        if (hasNext) {
            pageNo++;
            getData();
        } else {
        	if($('.mui-popup').length==0){
    			$.tip('没有更多了！');
    		}
        }
    }
})

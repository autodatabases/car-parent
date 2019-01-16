var year = "";
$(document).ready(function() {
	var agentid = $.getUrlParam("agentid")
	agentid = decodeURI(agentid);
	getdata();
	function getdata() {
		$.ajax({
			url: DOMIN.MAIN + "/gsagent/queryagentdata",
			type: "get",
			dataType: "json",
			cache: false,
			asycn: true,
			data: {
				agentId: agentid,
				year: year
			},
			success: function(data) {
				if (data.success == true) {
					renderHtml(data);
				} else {
					$.tip(data.message);
				}
			},
			error: function(data) {
				$.tip("服务器异常");
			}
		})
	}
	function renderHtml(data) {
		$(".name").html(data.datasets.gsAgent.data.agency);
		var cooperateTime = $.formatDate(data.datasets.gsAgent.data.cooperateTime).split(" ")[0];
		$(".startime").html(cooperateTime);
		if (data.list.length == 0) {
			$.tip("暂无数据");
			$(".table_body").html("");
		} else {
			var html01 = "<ul>";
			$.each(data.list,function(i, n) {
				html01 += '<li class="item"><span>' + n.month + '</span>';
				html01 += '<span>' + n.premium + '</span>';
				html01 += '<span>' + n.replaceValue + '</span></li>';
			});
			html01 += '<li class="item foot_item"><span>置换率</span>';
			html01 += '<span>' + data.list[1].replaceRate + '</span>';
			html01 += '<li class="item foot_item"><span>赔付率</span>';
			html01 += '<span>' + data.list[1].lossRate + '</span></li></ul>';
			$(".table_body").html(html01);
		}
		operate(data);
	}
	function operate(data) {
		var d1 = new Date();
		var fullYear1 = d1.getFullYear();
		var d2 = new Date(data.datasets.gsAgent.data.cooperateTime);
		var fullYear2 = d2.getFullYear();
		var ynumber = fullYear1 - fullYear2;
		var html02 = "";
		var fullYear = fullYear1;
		for (var i = 0; i <= ynumber; i++) {
			html02 += '<span value="' + i + '">' + fullYear + '年</span>';
			fullYear = fullYear - 1;
		}
		$(".option").html(html02);
		$(".select").on("click",function() {
			$(".select_tip").show();
		});
		$(".option span").on('click',function() {
			$(".select").html($(this).html());
			year = fullYear1 - $(this).attr("value");
			getdata();
			$(".select_tip").hide();
		})
	}
})

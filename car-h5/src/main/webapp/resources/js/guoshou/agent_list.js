$(document).ready(function() {
	var dname = $.getUrlParam("dname");
	dname = decodeURI(dname);
	$("#search_input").val("");
	getdata();
	$(".search").on('click',function() {
		$(".search_tip").hide();
		$("#search_input").focus();
	});
	$("#search_input").on('blur',function() {
		$("#search_input").val("");
		$(".search_tip").show();
	});
	$("#search_input").on('input',function() {
		getdata();
	});
	function getdata() {
		$.ajax({
			url: DOMIN.MAIN + "/gsagent/agentlist",
			type: "get",
			dataType: "json",
			cache: false,
			asycn: true,
			data: {
				dotName: dname,
				agent: $.trim($("#search_input").val())
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
		var html = ""
		$.each(data.list, function(i,n) {
			html += '<li class="item" agentid="' + n.id + '">' + n.agency + '</li>';
		});
		$(".dot_list ul").html(html);
		operate();
	}
	function operate() {
		$(".item").on('click',function() {
			var agentid = $(this).attr("agentid");
			agentid = encodeURI(encodeURI(agentid));
			window.location.href = DOMIN.MAIN + "/gsagent/agentdetail.html?agentid=" + agentid;
		})
	}
})

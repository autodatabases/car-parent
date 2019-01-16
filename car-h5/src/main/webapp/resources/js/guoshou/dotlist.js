$(document).ready(function() {
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
			url: DOMIN.MAIN + "/gsagent/dotlist",
			type: "get",
			dataType: "json",
			cache: false,
			asycn: true,
			data: {
				dotName: $.trim($("#search_input").val())
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
			html += '<li class="item">' + n + '</li>';
		});
		$(".dot_list ul").html(html);
		operate();
	}
	function operate() {
		$(".item").on('click',function() {
			var dname = $.trim($(this).html());
			dname = encodeURI(encodeURI(dname));
			window.location.href = DOMIN.MAIN + "/gsagent/agentlist.html?dname=" + dname;
		})
	}
})

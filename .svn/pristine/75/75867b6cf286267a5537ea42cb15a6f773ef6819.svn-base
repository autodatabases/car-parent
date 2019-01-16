$(document).ready(function() {
	$("#oilcard_button").on('click',function() {
		var type = $("#service_type").val();
		var number = $.trim($("#send_number").val());
		$("#oilcard_button").attr("disabled",true);
		if (type == "-1") {
			$.tip("请选择服务类型");
			$("#oilcard_button").attr("disabled",false);
			return;
		}
		if (!(/^-?\d+$/.test(number))) {
			$.tip("请填写正确的服务次数");
			$("#oilcard_button").attr("disabled",false);
			return;
		}
		if($("#uploadfile").val()!=""){
			$("#chejiahao").submit();
		}else{
			$.tip('请选择导入文件！');
			$("#oilcard_button").attr("disabled",false);
			return;
		}
		$("#import-model").load(function(){
			if(document.getElementById("import-model").contentWindow.document.body.innerText!=""){
				var val = eval("(" + document.getElementById("import-model").contentWindow.document.body.innerText + ")");
				console.log(val)
				if(val.success){
					$.tip("发送成功");
					$("#oilcard_button").attr("disabled",false);
				}else{
					$.tip(val.message);
					console.log(val.message)
					$("#oilcard_button").attr("disabled",false);
				}
			}
	    });
	})
})
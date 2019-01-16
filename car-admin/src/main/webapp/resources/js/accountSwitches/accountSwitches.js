//用户切换
$('#account_button').click(function(){
	cai_dot_list.dataList()
})

var cai_dot_list = {};

cai_dot_list.dataList = function() {
	$.ajax({
		url : DOMIN.MAIN + "/accountSwitching/update",
		type : "post",
		async : true,
		dataType : "json",
		data:{
			id:$('#openQuota option:selected').val()
		},
		success : function(data, textStatus) {
			$.tip('切换成功!!')	
			$("#openQuota").append();	
			$(".tables").load('accountSwitches/accountSwitches.html');			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
$("#new").click(function(){
	$('.tables').load("/admin/accountSwitches/accountNumberAndPassword.html?t=1.1");
});
$.ajax({
	url : DOMIN.MAIN + "/accountSwitching/query",
	type : "post",
	async : true,
	dataType : "json",
	success : function(data, textStatus) {
		// console.log(data)
		var compan=data.datasetLists.oilProviderList
		// console.log(compan)
		var html="";
		for(var i=0;i<compan.length;i++){
			// console.log(compan[i].id)
			html+='<option value="'+compan[i].id+'" id="'+compan[i].id+'">'+compan[i].company+'</option>'
			// console.log(html)
		}
		$("#openQuota").append(html);					
		$('.accounts').html(data.data.company)
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		$.tip(errorThrown);
	}
});
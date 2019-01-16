$(document).ready(function(){
	$("#positioncity").html(getCookie('city'));
	if(getCookie('history_citys')!=null){
		$("#historycity").html('<span>'+getCookie('history_citys')+'</span>');
	}
	searchByKeyWord();
	$('#search_keyword').on('change',function(){
		if($(this).val()!=''){
			$("#city1").hide();
			$("#city1").hide();
		}else{
			$("#city1").show();
			$("#city1").show();
		}
		searchByKeyWord();
	});

	$(".city").find('span').on('click',function(){
		addCookie('selcity',$(this).text(),360);
		addCookie('history_citys',$(this).text(),360);
		location.href = DOMIN.MAIN;
	});

	$("#allcity").delegate('li','click',function(){
		addCookie('selcity',$(this).text(),360);
		addCookie('history_citys',$(this).text(),360);
		location.href = DOMIN.MAIN;
	});
});

//搜索城市
function searchByKeyWord(){
	var provinceId = null;
	var cityName = $.trim($('#search_keyword').val());
	if(cityName!=''){
		provinceId = null;
		if(cityName.indexOf('市')>0){
			cityName = cityName.replace(/市/g,'');
		}
	}else{
		provinceId = '20';
	}

	$.ajax({
		url:DOMIN.MAIN+'/address/searchByKeyWord',
		type: 'POST',
		dataType: 'json',
		cache:false,
		asycn:false,
		data:{
			provinceId:provinceId,
			keyword:cityName
		},
		success:function(data){
			if(data.success && data.list.length>0){
				$("#allcity").empty();
				console.log(data)
				for(var i in data.list){
					$("#allcity").append('<li>'+data.list[i].regionname+'市</li>');
				}
			}
		},error:function(data){

		}
	});
}

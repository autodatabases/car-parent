$(function(){
	$("#selectBrandTitle").prop('src',sessionStorage.getItem('carlogo'));
	$("#selectBrandTitle").after(sessionStorage.getItem('carname'));
	var car2 = JSON.parse(sessionStorage.getItem('car2'));
//	var time = {};//{2015,2013}
//	for(var i in car2){
//		if(car2[i].lastproducttime != '—' && !isNaN(car2[i].lastproducttime)){
//			time[car2[i].firstproducttime+'-'+car2[i].lastproducttime] = '';
//		}else{
//			time[car2[i].firstproducttime] = '';
//		}
//	}
	var disp  = {};//{1.5L,1.6L}
	for(var i in car2){
		disp[car2[i].enginedisp] = '';
	}
	var html = '';
	for(var i in disp){
		html += '<dd>'+i+'</dd>';
	}
	$(".list").append(html);
	$(".list").delegate('dd','click',function(){
		var car3 = new Array();
		for(var i in car2){
			if(car2[i].enginedisp == $(this).text()){
				car3.push(car2[i]);
			}
		}
		sessionStorage.setItem('car3',JSON.stringify(car3));
		location.href = DOMIN.MAIN + '/brand/car3.html';
	});
})

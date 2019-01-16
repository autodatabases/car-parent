$(function(){
	$("#selectBrandTitle").prop('src',sessionStorage.getItem('carlogo'));
	$("#selectBrandTitle").after(sessionStorage.getItem('carname'));
	var car3 = JSON.parse(sessionStorage.getItem('car3'));
	var time = {};//{2015,2013}
	for(var i in car3){
		if(car3[i].lastproducttime != '—' && !isNaN(car3[i].lastproducttime)){
			time[car3[i].firstproducttime+'-'+car3[i].lastproducttime] = '';
		}else{
			time[car3[i].firstproducttime] = '';
		}
	}
	var html = '';
	for(var i in time){
		html += '<dd>'+i+'</dd>';
	}
	$(".list").append(html);
	$(".list").delegate('dd','click',function(){
		var car4 = new Array();
		for(var i in car3){
			if(car3[i].lastproducttime != '—' && !isNaN(car3[i].lastproducttime)){
				if((car3[i].firstproducttime +'-'+car3[i].lastproducttime) == $(this).text()){
					car4.push(car3[i]);
				}
			}else{
				if(car3[i].firstproducttime == $(this).text()){
					car4.push(car3[i]);
				}
			}
			
		}
		sessionStorage.setItem('car4',JSON.stringify(car4));
		location.href = DOMIN.MAIN + '/brand/car4.html';
	});
})

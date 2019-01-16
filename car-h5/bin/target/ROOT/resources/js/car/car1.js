$(function(){	
	$('.hot').on("click",'span',function(){
		$('.bg').show();
		$('.sidebar').addClass('on');
		$("#selectBrandTitle").prop('src',DOMIN.MAIN+'/resources/imgs/logo/'+$(this).attr("class")+'.png');
		sessionStorage.setItem('carlogo',$("#selectBrandTitle").prop('src'));
		sessionStorage.setItem('carname',$(this).text());
		queryAutoByBrand($(this).text());
		return false;
	});
	$('.list').on("click",'dd',function(){
		$('.bg').show();
		$('.sidebar').addClass('on');
		$("#selectBrandTitle").prop('src',$(this).find('img').prop('src'));
		sessionStorage.setItem('carlogo',$("#selectBrandTitle").prop('src'));
		sessionStorage.setItem('carname',$(this).text());
		queryAutoByBrand($(this).text());
		return false;
	});
	$('.bg').on("click",function(){
		$(this).hide();
		$('.sidebar').removeClass('on');
		return false;
	});
	queryAllBrand();
	
	$("#factorylist").delegate('dd','click',function(){
		var factoryName = $(this).parent().find('dt:eq('+$(this).attr('data-id')+')').text();
		var autoLineName = $(this).text();
		var factoryList = brandMap[factoryName];
		autoListBrandType = new Array();
		for(var i in factoryList){
			if(factoryList[i].autolinename == autoLineName){
				autoListBrandType.push(factoryList[i]);
			}
		}
		if($("#selectBrandTitle").prop('src')==''){
			$("#selectBrandTitle").prop('src',DOMIN.MAIN + '/resources/imgs/logo/'+autoListBrandType[0].logo+'.png');
		}
		sessionStorage.setItem('carlogo',$("#selectBrandTitle").prop('src'));
		sessionStorage.setItem('carname',factoryName);
		sessionStorage.setItem('car2',JSON.stringify(autoListBrandType));
		location.href = DOMIN.MAIN + '/brand/car2.html';
	});
	
	$("#search").blur(function(){
		$('.bg').show();
		$('.sidebar').addClass('on');
		//$("#selectBrandTitle").prop('src',$(this).find('img').prop('src'));
		queryAutoByKeyword($(this).val());
	});
})
//保存所有车型字母map
var brandPinyinMap = new Object();
//当前汽车列表 依次为 A3列表 品牌类型日期列表  品牌类型日期排量列表
var autoListBrandType,autoListTime,autoListdisplacement;
//生产厂家的品牌 汽车列表
var brandMap;//一汽大众 ====>list
//是否正在加载数据
var isloading = false;
//正在加载的对象
var load;
function queryAllBrand(){
	if(isloading){
		$.tip('正在加载...请稍后');
		return;
	}
	var load = $.loading();
	isloading = true;
	$.ajax({
		url : DOMIN.MAIN+"/brand/queryBrandList",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				var datalist = data.list;
				//处理返回结果
				for(var i=0;i<datalist.length;i++){
					if(undefined != brandPinyinMap[datalist[i].pinyin]){
						brandPinyinMap[datalist[i].pinyin].push(datalist[i]);
					}else{
						var arrayData = new Array();
						arrayData.push(datalist[i]);
						brandPinyinMap[datalist[i].pinyin] = arrayData;
					}
				}
				load.remove();
				isloading = false;
				setPinyinList();
			}else{
				alert(data.message);
				load.remove();
				isloading = false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			load.remove();
			isloading = false;
		}
	});
}

function setPinyinList(){
	$("#carpinyinlist").find('dt').each(function(){
		var pinyin = $(this).text();
		var html = '';
		for(var i in brandPinyinMap[pinyin]){
			html += '<dd><img src="'+DOMIN.MAIN+'/resources/imgs/logo/'+brandPinyinMap[pinyin][i].logo+'.png"/>'+brandPinyinMap[pinyin][i].brandname+'</dd>';
		}
		$(this).after(html);
	});
}

//根据点击显示选中品牌的车型列表  第一级
function queryAutoByBrand(brandName){
	if(isloading){
		return;
	}
	load = $.loading();
	isloading = true;
	$("#selectBrandTitle").next().html(brandName);
	$.ajax({
		url : DOMIN.MAIN+"/brand/queryAutoByBrand",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			brandName:brandName
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				var autoListBrand = data.list;
				if(autoListBrand != null){
					brandMap = new Object();
					for(var i in autoListBrand){
						if(brandMap[autoListBrand[i].factoryname] != undefined){
							brandMap[autoListBrand[i].factoryname].push(autoListBrand[i]);
						}else{
							var dataList = new Array();
							dataList.push(autoListBrand[i]);
							brandMap[autoListBrand[i].factoryname]=dataList;
						}
					}
					
					//显示选中车型的汽车列表
					setBrandList(brandMap);
				}
			}else{
				alert(data.message);
				load.remove();
				isloading = false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			load.remove();
			isloading = false;
		}
	});
}
//选中一个品牌 显示厂家品牌列表 例如 奥迪 ： a3 a4 a6 a8
function setBrandList(brandMap){
	$("#factorylist").empty();
	var index = 0;
	for(var i in brandMap){
		var brandDiv = '<dt>'+i+'</dt>';
		var html = '';
		var autoLine = {};
		for(var j in brandMap[i]){
			autoLine[brandMap[i][j].autolinename] = '';
		}
		for(var k in autoLine){
			html += '<dd data-id="'+index+'">'+k+'</dd>';
		}
		$("#factorylist").append(brandDiv).append(html);
		index++;
	}
	load.remove();
	isloading = false;
}


//根据搜索选择
function queryAutoByKeyword(keyword){
	if(isloading){
		return;
	}
	load = $.loading();
	isloading = true;
	$("#selectBrandTitle").next().html(keyword);
	$.ajax({
		url : DOMIN.MAIN+"/brand/queryAutoByKeyword",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		data: {
			keyword:keyword
		},
		traditional: true,
		success : function(data, textStatus){
			if(data.success){
				var autoListBrand = data.list;
				if(autoListBrand != null){
					brandMap = new Object();
					for(var i in autoListBrand){
						if(brandMap[autoListBrand[i].factoryname] != undefined){
							brandMap[autoListBrand[i].factoryname].push(autoListBrand[i]);
						}else{
							var dataList = new Array();
							dataList.push(autoListBrand[i]);
							brandMap[autoListBrand[i].factoryname]=dataList;
						}
					}
					
					//显示选中车型的汽车列表
					setBrandList(brandMap);
				}
			}else{
				alert(data.message);
				load.remove();
				isloading = false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			load.remove();
			isloading = false;
		}
	});
}
var denominationList = {};
var supplier = 1;
var cardType = 0;
if (getCookie("cData")) {
	supplier = getCookie("cData").split(",")[0];
	if(supplier == 2) {
		$(".tab-top span:nth-child(2)").addClass('on');
		$(".tab-top span:nth-child(2)").siblings('span').removeClass('on');
	}
	cardType = getCookie("cData").split(",")[1];
}
denominationList.refresh = function(){
		denominationList.dataList();
}
var isload = false;
denominationList.dataList = function() {
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/oilcardconfig/queryall",
		type : "post",
		dataType : "json",
		data :{
			pageNo:_allPages["denomination_paga"].pageNo,
			pageSize:_allPages["denomination_paga"].pageSize,
			supplier: supplier,
			cardType: cardType
		},
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					denominationList.renderHtml(returnObj.list);
					delCookie("cData");
				}else{
					$(".tab-list tbody").append('<tr><td colspan="6" style="color:red;">没有油卡面额数据~~</td></tr>');
				}
				// 分页
				setPaginator("denomination_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
						returnObj.pageInfo.recordCount);
			}else{
				isload=false;
				load.remove();
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			isload=false;
			load.remove();
			$.tip(errorThrown);
		}
	});
}

denominationList.renderHtml = function(datalist){
	var html="";
	var cardType = "";
	var status = "";
	var supplier = "";
	for(var i=0;i<datalist.length;i++){
		for(var j in datalist[i]){
			if(datalist[i][j]===null || datalist[i][j]=== ""){
				datalist[i][j] = '--';
			}
		}
	switch (datalist[i].supplier) {
		case 1:
			supplier = "欧飞";
			break;
		case 2:
			supplier = "高阳迅捷";
			break;
		case "--":
			supplier = "--";
			break;
	}
    switch (datalist[i].cardType) {
		case 0:
			cardType = "中石化";
			break;
		case 1:
			cardType = "中石油";
			break;
		case 2:
			cardType = "话费";
			break;
		case "--":
			cardType = "--";
			break;
    }
    switch (datalist[i].status) {
      case 0:
        status = "正常显示";
        break;
      case 1:
        status = "暂停服务";
        break;
      case 2:
        status = "禁用";
        break;
      case "--":
        status = "--";
        break;
    }

		html+='<tr>';
		html+='<td>'+supplier+'</td>';
		html+='<td>'+cardType+'</td>';
		html+='<td>'+datalist[i].goodId+'</td>';
		html+='<td>'+datalist[i].content+'</td>';
		html+='<td>'+status+'</td>';
	    html+='<td><a href="javascript:void(0)" onclick=edit_denomination("'+datalist[i].id+'")>修改面额状态</a>';
	    html+='<a href="javascript:void(0)" onclick=deletes("'+datalist[i].id+'");>删除面额</a></td>';
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
}
function edit_denomination(num){//编辑
	sessionStorage.setItem("denomination_session",_allPages["denomination_paga"].pageNo);
	addCookie('denominationId',num);
	$('.tables').load('oilcard/denomination_edit.html?t=1.3');
}
function deletes(num){//删除面额
    $(".tips-warp").css("display","block");
  	$(".tips-warp .ok_btn").attr("rel",num);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
	var id = $(".tips-warp .ok_btn").attr("rel");
	var url=DOMIN.MAIN +"/oilcardconfig/delconfig";
	$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		data : {
			id: id
		},
		success : function(data) {
			if (data.success) {
				$.tip("删除成功！");
				$(".tips-warp").css("display","none");
				denominationList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
$(function(){
	var denomination_session= sessionStorage.getItem("denomination_session");
	if(denomination_session){
		initPaginator("denomination_paga", denominationList.dataList,denomination_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("denomination_session");
	}else{
		initPaginator("denomination_paga", denominationList.dataList,1,localStorage.getItem('globalPageSize'));
	}
	denominationList.dataList();
	$("#add_denomination").click(function(){
		$('.tables').load('oilcard/denomination_edit.html');
	});
	$(".tab-top span").on('mouseenter', function() {
	  	$(this).siblings('span').children('ul').hide();
	  	$(this).children('ul').show('slow');
	});
	$(".tab-top span").on('mouseleave', function() {
	  $(".tab-top span ul").hide();
	});
	$(".tab-top span li").on('click', function() {
	  supplier = $(this).attr('rel');
	  cardType = $(this).attr('als');
	  $(this).parents("span").addClass('on');
	  $(this).parents("span").siblings('span').removeClass('on');
	  denominationList.dataList();
	});
});

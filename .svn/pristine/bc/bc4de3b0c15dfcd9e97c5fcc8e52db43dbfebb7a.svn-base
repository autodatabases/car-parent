var sellerList = {};
sellerList.refresh = function(){
		//_allPages["busines_paga"].pageNo = 1;
		sellerList.dataList();
}
var isload = false;
sellerList.dataList = function() {
	var province = $("#province").find("option:selected").text();
	var city = $("#city").find("option:selected").text();
	var area = $("#area").find("option:selected").text();
	if(province=="选择省份"){
		province="";
	}
	if(city=="选择城市"){
		city="";
	}
	if(area=="选择市区"){
		area="";
	}
	if(isload){
		return;
	}
	var load = $.loading();
	isload = true;
	$.ajax({
		url : DOMIN.MAIN + "/seller/querySellerList.json",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			pageNo:_allPages["busines_paga"].pageNo,
			pageSize:_allPages["busines_paga"].pageSize,
			sellerName:$("#sellerName").val(),
			joinType:$("#joinType").val(),
			sellerGrade:$("#sellerGrade").val(),
			province:province,
			city:city,
			area:area,
			auditStatus:$("#auditStatus").val(),
			properties:$("#properties").val()
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			isload=false;
			load.remove();
			var returnObj = data;
			if (data.success) {
				$(".tab-list tbody").empty();
				if(returnObj.list.length>0){
					sellerList.renderHtml(returnObj.list);
				}else{
					$(".tab-list tbody").append('<tr><td colspan="8" style="color:#cddc39;font-size:20px">没有商家了~~</td></tr>');
				}
				
				// 分页
				setPaginator("busines_paga",returnObj.pageInfo.pageIndex, returnObj.pageInfo.pageCount,
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

sellerList.renderHtml = function(datalist){
	var html="";
	for(var i=0;i<datalist.length;i++){
		var address="";
		if(datalist[i].province){
			address+=datalist[i].province+"省";
		}
		if(datalist[i].city){
			address+=datalist[i].city+"市";
		}
		if(datalist[i].area){
			address+=datalist[i].area;
		}
		html+='<tr>';
		html+='<td>'+datalist[i].sellerName+'</td>';
		/*html+='<td>马牌门店</td>';*/
		if(datalist[i].joinType==0){
			html+='<td>马牌门店</td>';
		}else if(datalist[i].joinType==1){
			html+='<td>国寿</td>';
		}else if(datalist[i].joinType==2){
			html+='<td>陌拜用户</td>';
		}
		if(datalist[i].sellerGrade==0)
			html+='<td>其它</td>';
		if(datalist[i].sellerGrade==1)
			html+='<td>快修店</td>';
	    if(datalist[i].sellerGrade==2)
			html+='<td>4s店</td>';
		if(datalist[i].sellerGrade==3)
			html+='<td>修理厂</td>';
		if(datalist[i].auditStatus==1)
			html+='<td>待审核</td>';
		if(datalist[i].auditStatus==2)
			html+='<td>审核通过</td>';
		if(datalist[i].auditStatus==3)
			html+='<td>冻结</td>';
		html+='<td>'+datalist[i].name+'</td>';
		if(datalist[i].sellerPhone){
			html+='<td>'+datalist[i].sellerPhone+'</td>';
		}else{
			html+='<td></td>';
		}
		if(datalist[i].addressDetail){
			html+='<td>'+datalist[i].addressDetail+'</td>';
		}else if(address) {
			html+='<td>'+address+'</td>';
		}else{
			html+='<td>------</td>';
		}
		if(datalist[i].auditStatus==2){
			html+='<td><a onclick=edit_busines("'+datalist[i].id+'")>编辑</a>';
			html+='<a href="javascript:void(0)" onclick=disable("'+datalist[i].id+'");>停用</a>';
			html+='<a href="javascript:void(0)" onclick=deleteBus("'+datalist[i].id+'");>删除</a></td>';
	    }else{
			html+='<td><a onclick=edit_busines("'+datalist[i].id+'")>编辑</a>';
		    html+='<a href="javascript:void(0)" onclick=enable("'+datalist[i].id+'");>起用</a>';
		    html+='<a href="javascript:void(0)" onclick=deleteBus("'+datalist[i].id+'");>删除</a></td>';
		}
		html+='</tr>';
	}
	$(".tab-list tbody").append(html);
	//$.addTitle('taskgrid');
}
function edit_busines(num){//编辑
	sessionStorage.setItem("busines_session",_allPages["busines_paga"].pageNo);
	addCookie('businesId',num);
	$('.tables').load('busines/busines-audie.html?t=2.0');
}
function deleteBus(num){//删除弹窗
	$(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("是否删除商家");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function disable(num){//停用弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("请选择是否停用该店铺");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function enable(num){//起用弹窗
    $(".tips-warp").css("display","block");
	$(".tips-warp .wenzi").html("请选择是否起用该店铺");
	$(".tips-warp .ok_btn").attr("rel",num);
}
function audit_close(){//关闭弹窗
    $(".tips-warp").css("display","none");
}
function audit_button(){
	var obusinesNo=$(".tips-warp .ok_btn").attr("rel");
	var num="";
	var data="";
	var url="";
	var Prompt="";
	if($(".tips-top .wenzi").html()=="请选择是否停用该店铺"){
		data={
			sellerId:obusinesNo,
			status:3
		}
		url=DOMIN.MAIN +"/seller/updateSellerStatus";
		Prompt="停用成功";
	}else if($(".tips-top .wenzi").html()=="是否删除商家"){
		url=DOMIN.MAIN +"/seller/deleteSeller";
		data={
			sellerId:obusinesNo,
		}
		Prompt="删除成功";
	}else{
		data={
			sellerId:obusinesNo,
			status:2
		}
		url=DOMIN.MAIN +"/seller/updateSellerStatus";
		Prompt="起用成功";
	}
	$.ajax({
		url : url,
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :data,
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip(Prompt);
				$(".tips-warp").css("display","none");
				sellerList.refresh();
			}else{
				$.tip(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
$(function() {
	initProvince("province");
	initCity("province","city");
	initArea("province","city","area");
	$('#province').val(20);
	$('#province').trigger('change');
	var busines_session= sessionStorage.getItem("busines_session");
	if(busines_session){
		initPaginator("busines_paga", sellerList.dataList,busines_session,localStorage.getItem('globalPageSize'));
		sessionStorage.removeItem("busines_session");
	}else{
		initPaginator("busines_paga", sellerList.dataList,1,localStorage.getItem('globalPageSize'));
	}
	sellerList.dataList();
	$(".search-box button").click(function(){//根据店铺查询
		_allPages["busines_paga"].pageNo = 1;
		sellerList.dataList();
	});
});


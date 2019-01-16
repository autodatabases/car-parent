var busines_id=getCookie('businesId');
$(function(){
	initProvince("province");
	initCity("province","city");
	initArea("province","city","area");
	if(busines_id){
		editbusines();
	}else{
		busines_id=null;
	}
	$(".dotPhoto input[type=file]").change(function(){//网点上传图片
         if($(".dotPhoto input[type=file]").val() != '') $("#submit_form").submit();
    });
    //iframe加载响应，初始页面时也有一次，此时data为null。
    $("#exec_target").load(function(){
		 var data =eval("(" + document.getElementById("exec_target").contentWindow.document.body.innerText + ")");
		 $(".dotPhoto .img-box").css("display","block");
		 $(".dotPhoto img").attr("src",data.url);
		 $(".dotPhoto_p").html("重新上传");
    });
	$(".businessLicense input[type=file]").change(function(){//营业执照上传图片
         if($(".businessLicense input[type=file]").val() != '') $("#submit_forms").submit();
    });
    //iframe加载响应，初始页面时也有一次，此时data为null。
    $("#exec_targets").load(function(){
		 var data =eval("(" + document.getElementById("exec_targets").contentWindow.document.body.innerText + ")");
		 $(".businessLicense .img-box").css("display","block");
		 $(".businessLicense img").attr("src",data.url);
		 $(".businessLicense_p").html("重新上传");
    });
	$("#busines_button").click(function(){
		busines_button();
	});
	
	$("#seller_order").change(function(){
		if($(this).val()<0){
			alert('排序必须大于0!');
			$(this).val(0);
		}
	});
});
function editbusines(){//商家修改
	$.ajax({
		url : DOMIN.MAIN + "/seller/getSellerInfo",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			sellerId:busines_id
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				delCookie('businesId');	
				var dataseller=data.datasets.seller.data;
				$("#selectName").val(dataseller.sellerName);
				$("#corporateName").val(dataseller.name);
				$("#serviceNetwork").val(dataseller.addressDetail);
				$("#serviceTelephone").val(dataseller.sellerPhone);
				$("#openTime").val(dataseller.openTime);
				$("#servicePrice").val(dataseller.servicePrice);
				if(dataseller.sellerPostion){
					var sellerPostion=dataseller.sellerPostion;
					sellerPostion=sellerPostion.split("_");
					$("#sellerPostion_x").val(sellerPostion[0]);
					$("#sellerPostion_y").val(sellerPostion[1]);
				}
				$("#sellerGrade").val(dataseller.sellerGrade);
				$("#settleType").val(dataseller.settleType);
				$("#joinType").val(dataseller.joinType);
				$("#scoreService").val(dataseller.scoreService);
/*				$("#scoreService option").each(function(i,e){
					if($(e).val()==dataseller.scoreService){
					    $(e).prop("selected","selected");	
					}
				});*/
				var properties=dataseller.properties;
				if(parseInt(properties&1)==0){
					$("#properties_xi option").eq(0).prop("selected","selected");
				}else{
					$("#properties_xi option").eq(1).prop("selected","selected");
				}
				if(parseInt(properties&2)==0){
					$("#properties_xiao option").eq(0).prop("selected","selected");
				}else{
					$("#properties_xiao option").eq(1).prop("selected","selected");
				}
				if(parseInt(properties&4)==0){
					$("#properties_dian option").eq(0).prop("selected","selected");
				}else{
					$("#properties_dian option").eq(1).prop("selected","selected");
				}
				if(parseInt(properties&8)==0){
					$("#properties_ban option").eq(0).prop("selected","selected");
				}else{
					$("#properties_ban option").eq(1).prop("selected","selected");
				}
				if(parseInt(properties&16)==0){
					$("#properties_pen option").eq(0).prop("selected","selected");
				}else{
					$("#properties_pen option").eq(1).prop("selected","selected");
				}
				$("#seller_order").val(dataseller.sellerOrder);
			    if(data.data){
					$("#businessLicense_id").val(data.data.yinyyeCode);
					$("#shouhuo").val(data.data.shouhuo);
					$("#jiesuan").val(data.data.jiesuan);
					$("#duijie").val(data.data.duijie);
					$("#baoxian").val(data.data.baoxian);
					$("#accountName").val(data.data.accountName);
					$("#accountNumber").val(data.data.accountNumber);
					$("#account").val(data.data.account);
					$("#weixin").val(data.data.weixin);
					$("#fullName").val(data.data.fullName);
					$("#wxZizhi").val(data.data.wxZizhi);
					$("#sellerArea").val(data.data.sellerArea);
					$("#employee").val(data.data.employee);
					$("#areaManager").val(data.data.areaManager);
					$("#workbay").val(data.data.workbay);
					if(data.data.shopPic){
						$("#shopPic").parent("div").css("display","block");
						$("#shopPic").attr("src",data.data.shopPic);
					}
					if(data.data.businessLicence){
						$("#businessLicence").parent("div").css("display","block");
						$("#businessLicence").attr("src",data.data.businessLicence);
					}
					if(data.data.hasNetwork==true){
						$("#hasNetwork option").eq(1).attr("selected",true);
					}else{
						$("#hasNetwork option").eq(0).attr("selected",true);
					}
					$("#channel").val(data.data.channel);
					$("#service").val(data.data.service);
					var province_clearInterval=setInterval(function showTime(){
						if($("#province option").size()>=2){
							clearInterval(province_clearInterval);
							$("#province option").each(function(i,e){
								if($(e).html()==dataseller.province){
									$(e).prop("selected","selected");
									$("#province").change();
									var city_clearInterval=setInterval(function showTime(){
										if($("#city option").size()>=2){
											clearInterval(city_clearInterval);
											$("#city option").each(function(j,a){
												if($(a).html()==dataseller.city){
													$(a).prop("selected","selected");
													$("#city").change();
													var area_clearInterval=setInterval(function showTime(){
														if($("#area option").size()>=2){
															clearInterval(area_clearInterval);
															$("#area option").each(function(y,k){
																if($(k).html()==dataseller.area){
																	$(k).prop("selected","selected");
																	//$("#area").change();
																	return true;
																}
															})
														}
													},300);
													return true;
												}
											})
										}
									},300);
									return true;
								}
							})
						}
					}, 300);
				}
			}else{
				$.tip("查询信息失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
		}
	});
}
function busines_button(){//提交
	$("#busines_button").prop("disabled",true);
	var province= $("#province option:selected").text();
	var serviceTelephone=$("#serviceTelephone").val();
	var city= $("#city option:selected").text();
	var area= $("#area option:selected").text();
	var properties_shuzu=[];
	properties_shuzu.push(parseInt($("#properties_xi").val()));
	properties_shuzu.push(parseInt($("#properties_xiao").val()));
	properties_shuzu.push(parseInt($("#properties_dian").val()));
	properties_shuzu.push(parseInt($("#properties_ban").val()));
	properties_shuzu.push(parseInt($("#properties_pen").val()));
	var properties=0;
	for(var i=0;i<properties_shuzu.length;i++){
		properties = properties | properties_shuzu[i];
	}
	if(!(/^1[34578]\d{9}$/.test(serviceTelephone))){ 
        $.tip("手机号码有误，请重填"); 
		$("#busines_button").prop("disabled",false);		
        return; 
    } 
	if(province=="选择省份"){
		 $.tip("请选择省份");
		 $("#busines_button").prop("disabled",false);
		 return;
	}
	if(city=="选择城市"){
		 $.tip("请选择城市");
		 $("#busines_button").prop("disabled",false);
		 return;
	}
	if(area=="选择市区"){
		 $.tip("请选择市区");
		 $("#busines_button").prop("disabled",false);
		 return;
	}
	var sellerPostion_x=$("#sellerPostion_x").val();
	if(!(/^[0-9]*\.[0-9]*$/.test(sellerPostion_x))){
		$.tip("请填写正确的经度");
		$("#busines_button").prop("disabled",false);
		return;
	}
	var sellerPostion_y=$("#sellerPostion_y").val();
	if(!(/^[0-9]*\.[0-9]*$/.test(sellerPostion_y))){
		$.tip("请填写正确的纬度");
		$("#busines_button").prop("disabled",false);
		return;
	}
	var sellerPostion=sellerPostion_x+'_'+sellerPostion_y;
    $.ajax({
		url : DOMIN.MAIN + "/seller/setSellerInfo",
		type : "post",
		processData: true,
		cache : false,
		async : true,
		dataType : "json",
		data :{
			id:busines_id,
			sellerName:$.trim($("#selectName").val()),
			joinType:$.trim($("#joinType").val()),
			sellerPhone:$.trim(serviceTelephone),
			yinyyeCode:$.trim($("#businessLicense_id").val()),
			accountName:$.trim($("#accountName").val()),
			account:$.trim($("#account").val()),
			accountNumber:$.trim($("#accountNumber").val()),
			businessLicence:$.trim($("#businessLicence").attr("src")),
			shouhuo:$.trim($("#shouhuo").val()),
			jiesuan:$.trim($("#jiesuan").val()),
			duijie:$.trim($("#duijie").val()),
			baoxian:$.trim($("#baoxian").val()),
			weixin:$.trim($("#weixin").val()),
			fullName:$.trim($("#fullName").val()),
			wxZizhi:$.trim($("#wxZizhi").val()),
			sellerArea:$.trim($("#sellerArea").val()),
			employee:$.trim($("#employee").val()),
			openTime:$.trim($("#openTime").val()),
			areaManager:$.trim($("#areaManager").val()),
			shopPic:$.trim($("#shopPic").attr("src")),
			hasNetwork:$.trim($("#hasNetwork").val()),
			channel:$.trim($("#channel").val()),
			service:$.trim($("#service").val()),
			name:$.trim($("#corporateName").val()),
			addressDetail:$.trim($("#serviceNetwork").val()),
			province:province,
			city:city,
			area:area,
			workbay:$.trim($("#workbay").val()),
			properties:$.trim(properties),
			servicePrice:$.trim($("#servicePrice").val()),
			sellerGrade:$.trim($("#sellerGrade").val()),
			settleType:$.trim($("#settleType").val()),
			scoreService:$.trim($("#scoreService").val()),
			sellerPostion:$.trim(sellerPostion),
			sellerOrder:$.trim($("#seller_order").val())
		},
		traditional : true,// 使用传统方式序列化
		success : function(data, textStatus) {
			if (data.success) {
				$.tip("操作成功");
				setTimeout(function () {
					$('.tables').load('fixseller/seller-list.html');
				}, 1000);
			}else{
				$.tip(data.message);
				$("#busines_button").prop("disabled",false);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.tip(errorThrown);
			$("#busines_button").prop("disabled",false);
		}
	}); 

	
}


















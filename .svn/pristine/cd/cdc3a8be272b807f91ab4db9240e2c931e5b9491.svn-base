checkLogin();
function busines_one(){//如果有商家 填写商家信息
		$.ajax({
				url : DOMIN.MAIN+"/user/getSellerInfo",
				type : "post",
				cache : false,
				async : true,
				dataType : "json",
				traditional: true,
				success : function(data, textStatus){
					if(data.success){
						var busines_ulone=$(".busines_ulone");
						busines_ulone.find("li").eq(0).find("input").val(data.data.sellerName);
						busines_ulone.find("li").eq(1).find("input").val(data.data.taxesCode);
						busines_ulone.find("li").eq(2).find("input").val(data.data.registerAddr);
						busines_ulone.find("li").eq(3).find("input").val(data.data.registerPhone);
						busines_ulone.find("li").eq(4).find("input").val(data.data.account);
						busines_ulone.find("li").eq(5).find("input").val(data.data.accountNumber);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
				}
		});
};
$(function(){
	$(".one_btn").click(function(){//商家信息填写
	    var busines_ulone=$(".busines_ulone");
	    var sellerName=busines_ulone.find("li").eq(0).find("input").val();
		var taxesCode=busines_ulone.find("li").eq(1).find("input").val();
		var registerAddr=busines_ulone.find("li").eq(2).find("input").val();
		var registerPhone=busines_ulone.find("li").eq(3).find("input").val();
		var account=busines_ulone.find("li").eq(4).find("input").val();
		var accountNumber=busines_ulone.find("li").eq(5).find("input").val();
		if(sellerName==""){
			$.tip("请填写店铺信息!");
			return false;
		}
		if(taxesCode==""){
			$.tip("请填写纳税人识别码!");
			return false;
		}
		if(registerAddr==""){
			$.tip("请填写注册地址!");
			return false;
		}
		if(!/^\d{11}$/.test(registerPhone)){
			$.tip(error);
			return false;
		}
		if(account==""){
			$.tip("请填写开户行!");
			return false;
		}
		var reg = /^\d{19}$/g; // 以19位数字开头，以19位数字结尾
		if(accountNumber==""){
			$.tip("请填写银行账号!");
			return false;
		}else if( !reg.test(accountNumber) )
		{
			$.tip("格式错误，应该是19位数字！");
			return false;
		} 
		sessionStorage.setItem("sellerName",sellerName);
		sessionStorage.setItem("taxesCode",taxesCode);
		sessionStorage.setItem("registerAddr",registerAddr);
		sessionStorage.setItem("registerPhone",registerPhone);
		sessionStorage.setItem("account",account);
		sessionStorage.setItem("accountNumber",accountNumber);
		location.href='busines-two.html';
	});
})
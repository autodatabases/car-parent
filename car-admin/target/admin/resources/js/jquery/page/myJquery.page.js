/*修改分页方法实现多页面同时分页*/
var _allPages = {};
/**  
* 描述：翻页初始方法，只要为了加载每个页面中的查询方法名称
* 创建人：likk
*/ 
function initPaginator(id, method,pageNo,pageSize){
	_allPages[id] = {};
	_allPages[id].pageNo = pageNo;
	_allPages[id].pageSize = pageSize;
	_allPages[id].methodName = method;
//	$("#"+id).css("width","100%");
	_allPages[id]._divId = id + "_";
	_createPaginator(method,id);
	//前一页
	_getJqueryRealId(id,"pageLeft").click(function(){
		var id = $(this).parent().parent().parent().attr('id');
		if(_allPages[id].pageNo > 1){
			_allPages[id].pageNo -= 1;
			_allPages[id].methodName();
		}
	});
	//后一页
	_getJqueryRealId(id,"pageRight").click(function(){
		var id = $(this).parent().parent().parent().attr('id');
		if(_allPages[id].pageNo < _allPages[id].totalPages){
			_allPages[id].pageNo += 1;
			_allPages[id].methodName();
		}
	});
}
/**  
* 描述：设置分页查询信息后分页信息
* 创建人：wuzb3
*/ 
function setPaginator(id,pageNo,totalPages,totalCount){
	_allPages[id].pageNo = parseInt(pageNo);
	_allPages[id].totalPages = parseInt(totalPages);
	_setPageNoStyle(id,totalCount);
	_getJqueryRealId(id,"pageNo").val(pageNo);
	_getJqueryRealId(id,"totalPages").text(totalPages);
	
}

//一下都是内部方法
function _createPaginator(method,id){
	var str = "<div id=\""+ _allPages[id]._divId +"pageDiv\" class=\"pageDiv\">"
//			+ "<div id=\""+ _allPages[id]._divId +"pageBox\" class=\"pageBox\" style=\"font-size: 16px;\"><br />" 
			+ "<div id=\""+ _allPages[id]._divId +"pageBox\" class=\"pageBox\"><br />" 
			+ "<a id=\""+ _allPages[id]._divId +"pageLeft\" class=\"page_a\">上页</a>" 
			+ "<a id=\""+ _allPages[id]._divId +"firstPages\" onclick=\"_exchangePage('"+id+"',1);\" class=\"page_a\" style=\"display:none;\">1</a>" 
			+ "<span id=\""+ _allPages[id]._divId +"points_min\" style=\"display:none;\">...</span>" 
			+ "<font id=\""+ _allPages[id]._divId +"page_nums\">"
			+ "</font>"
			+ "<span id=\""+ _allPages[id]._divId +"points_max\">...</span>" 
			+ "<a id=\""+ _allPages[id]._divId +"totalPages\" onclick=\"_exchangePage('"+id+"',-1);\" class=\"page_a\">0</a>" 
			+ "<a id=\""+ _allPages[id]._divId +"pageRight\" class=\"page_a\">下页</a>"
			+ "到<input id=\""+ _allPages[id]._divId +"pageNo\" class=\"pageselect\" type=\"text\" onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\\D/g,'')}\""  
			+ "  onafterpaste=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\\D/g,'')}\"/>页"
			+ "<a id=\""+ _allPages[id]._divId +"confimPage\" onclick=\"_jumpPage('"+id+"')\" class=\"page_a confimBut\">确定</a>"
			+ "<font style=\"margin-left: 10px;\" id=\""+ _allPages[id]._divId +"showTotalNum\">共0个<font> </div>"
			+ "</div>";
	$("#"+id).html(str);
}
function _exchangePage(id,value){
	if(value==-1){
		value = _allPages[id].totalPages;
	}
	if(value<=_allPages[id].totalPages){
		_allPages[id].pageNo = value;
		_allPages[id].methodName();
	}
}
/** 跳转页数 */
function _jumpPage(id){
	var value = _getJqueryRealId(id,"pageNo").val();
	if(value != "" && value<=_allPages[id].totalPages){
		_allPages[id].pageNo = value;
		_allPages[id].methodName();
	}else{
		alert("页数为空或超出最大页数");
	}
}
/** 设置分页样式 */
function _setPageNoStyle(id,totalCount){
	if(_allPages[id].totalPages != 0){
		_getJqueryRealId(id,"showTotalNum").html("共"+totalCount+"个");
		if(_allPages[id].pageNo==_allPages[id].totalPages){
			_getJqueryRealId(id,"pageRight").addClass("pageDisabled");
		}else{
			_getJqueryRealId(id,"pageRight").removeClass("pageDisabled");
		}
		if(_allPages[id].pageNo==1){
			_getJqueryRealId(id,"pageLeft").addClass("pageDisabled");
		}else{
			_getJqueryRealId(id,"pageLeft").removeClass("pageDisabled");
		}
		_getJqueryRealId(id,"pageDiv").css("display","inline-block");
		_getJqueryRealId(id,"pageBox").find("a").removeClass("pageDown");
		_getJqueryRealId(id,"firstPages").css("display","none");
		_getJqueryRealId(id,"points_min").css("display","none");
		_getJqueryRealId(id,"points_max").css("display","none");
		_getJqueryRealId(id,"totalPages").css("display","none");
		if(_allPages[id].totalPages>5){
			if(_allPages[id].pageNo>=4){
				_getJqueryRealId(id,"firstPages").css("display","inline-block");
				_getJqueryRealId(id,"points_min").css("display","inline-block");
			}
			if(_allPages[id].pageNo+2>=_allPages[id].totalPages){
				_getJqueryRealId(id,"points_max").css("display","none");
				_getJqueryRealId(id,"totalPages").css("display","none");
			}else{
				_getJqueryRealId(id,"points_max").css("display","inline-block");
				_getJqueryRealId(id,"totalPages").css("display","inline-block");
			}
		}
		var str = "";
		if(_allPages[id].totalPages<=5){
			for(var i=1;i<=_allPages[id].totalPages;i++){
				str = str + "<a id='"+ _allPages[id]._divId +"page_"+i+"' onclick='_exchangePage(\""+id+"\","+i+");' class='page_a'>"+i+"</a>";
			}
		}else{
			if(_allPages[id].pageNo<4 || _allPages[id].totalPages<=5){
				str = "<a id='"+ _allPages[id]._divId +"page_1' onclick='_exchangePage(\""+id+"\",1);' class='page_a'>1</a>" 
						+ "<a id='"+ _allPages[id]._divId +"page_2' onclick='_exchangePage(\""+id+"\",2);' class='page_a'>2</a>" 
						+ "<a id='"+ _allPages[id]._divId +"page_3' onclick='_exchangePage(\""+id+"\",3);' class='page_a'>3</a>" 
						+ "<a id='"+ _allPages[id]._divId +"page_4' onclick='_exchangePage(\""+id+"\",4);' class='page_a'>4</a>" 
						+ "<a id='"+ _allPages[id]._divId +"page_5' onclick='_exchangePage(\""+id+"\",5);' class='page_a'>5</a>";
			}else if(_allPages[id].pageNo<=_allPages[id].totalPages-2){
				str = "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].pageNo-2) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].pageNo-2) + ");' class='page_a'>" + (_allPages[id].pageNo-2) + "</a>"
						+ "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].pageNo-1) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].pageNo-1) + ");' class='page_a'>" + (_allPages[id].pageNo-1) + "</a>"
						+ "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].pageNo) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].pageNo) + ");' class='page_a'>" + (_allPages[id].pageNo) + "</a>"
						+ "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].pageNo+1) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].pageNo+1) + ");' class='page_a'>" + (_allPages[id].pageNo+1) + "</a>"
						+ "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].pageNo+2) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].pageNo+2) + ");' class='page_a'>" + (_allPages[id].pageNo+2) + "</a>";
			}else if(_allPages[id].pageNo > _allPages[id].totalPages-2){
				str ="<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].totalPages-4) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].totalPages-4) + ");' class='page_a'>" + (_allPages[id].totalPages-4) + "</a>"
						+ "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].totalPages-3) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].totalPages-3) + ");' class='page_a'>" + (_allPages[id].totalPages-3) + "</a>"
						+ "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].totalPages-2) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].totalPages-2) + ");' class='page_a'>" + (_allPages[id].totalPages-2) + "</a>"
						+ "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].totalPages-1) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].totalPages-1) + ");' class='page_a'>" + (_allPages[id].totalPages-1) + "</a>"
						+ "<a id='"+ _allPages[id]._divId +"page_" + (_allPages[id].totalPages) + "' onclick='_exchangePage(\""+id+"\"," + (_allPages[id].totalPages) + ");' class='page_a'>" + (_allPages[id].totalPages) + "</a>";
			}
		}
		_getJqueryRealId(id,"page_nums").html(str);
		_getJqueryRealId(id,"page_" + _allPages[id].pageNo).addClass("pageDown");
	}else{
		_getJqueryRealId(id,"pageDiv").css("display","none");
	}
	
}
function _getJqueryRealId(pageId,id){
	return $("#"+_allPages[pageId]._divId + id);
}

$(document).ready(function() {
	// var myChart = echarts.init(document.getElementById('chart'));
	// option = {
	// 	 title : {
	//         text: '职场销售额汇总',
	//         right: "auto"
	//     },
	//     tooltip: {
	//         trigger: 'axis'
	//     },
	//     xAxis: [
	//         {
	//             type: 'category',
	//             data: []
	//         }
	//     ],
	//     yAxis: [
	//         {
	//             type: 'value',
	//             name: '销售额(万元)'
	//         }
	//     ],
	//     dataZoom: [
	//     	{
	//     		type: 'slider',
	//     		show: true,
	//     		xAxisIndex: [0],
	//     		start: 1,
	//     		end: 35
	//     	},
	//     	{
	//     		type: 'inside',
	//     		show: true,
	//     		xAxisIndex: [0],
	//     		start: 1,
	//     		end: 35
	//     	}
	//     ],
	//     series: [
	//         {
	//             name:'销售额',
	//             type:'bar',
	//             data:[],
	//        }
	//     ]
	// };
	// myChart.setOption(option);
	//
	// //获取数据
	// getData();
	// function getData() {
	// 	$.ajax({
	// 		url : DOMIN.MAIN+"/counterman/getCountermanInfo",
	// 		type : "post",
	// 		cache : false,
	// 		async : true,
	// 		dataType : "json",
	// 		success : function(data, textStatus){
	// 			if(data.success){
	// 				var money = [];
	// 				var areas = [];
	// 				for(var i=0; i<data.list.length; i++) {
	// 					if(data.list[i].careerName != null) {
	// 		                areas.push(data.list[i].careerName);
	// 		            }
	// 					if(data.list[i].yesterdayPrice != null) {
	// 						var price = data.list[i].yesterdayPrice / 10000;
	// 		                money.push(price);
	// 		            }
	// 				}
	// 				// 填入数据
	// 			    myChart.setOption({
	// 			    	xAxis: [
	// 				        {
	// 				            data: areas
	// 				        }
	// 				    ],
	// 			        series: [
	// 				        {
	// 				        	name: "销售额",
	// 				        	data: money
	// 				        }
	// 				    ],
	//
	// 			    });
	// 			}else{
	// 				$.tip(data.message);
	// 			}
	//
	// 		},
	// 		error : function(XMLHttpRequest, textStatus, errorThrown){
	// 			$.tip(errorThrown);
	// 		}
	// 	});
	// 	$.ajax({
	// 		url : DOMIN.MAIN+"/counterman/getCountermanScore",
	// 		type : "post",
	// 		cache : false,
	// 		async : true,
	// 		dataType : "json",
	// 		success : function(data, textStatus){
	// 			if(data.success){
	// 				for(var i=0; i<data.list.length; i++) {
	// 					switch (data.list[i].belongArea){
	// 						case "黄埔_南岗":
	// 							$(".nangang").html(data.list[i].score);
	// 							break;
	// 						case "黄埔_萝岗":
	// 							$(".luogang").html(data.list[i].score);
	// 							break;
	// 						case "黄埔":
	// 							$(".huangpu").html(data.list[i].score);
	// 							break;
	// 						default:
	// 							break;
	// 					}
	// 				}
	// 			}else{
	// 				$.tip(data.message);
	// 			}
	//
	// 		},
	// 		error : function(XMLHttpRequest, textStatus, errorThrown){
	// 			$.tip(errorThrown);
	// 		}
	// 	});
	// }
	getData();
	function getData() {
		$.ajax({
			url: DOMIN.MAIN+"/counterman/getCounterman",
			type: 'post',
			dataType: 'json',
			data: {param1: 'value1'},
			success: function(data) {
					if(data.success) {
							$(".name").html(data.data.countermanName);
							$(".number").html(data.data.countermanCode);
							$(".career").html(data.data.countermanPhone);
							$(".area").html(data.data.dotName);
							$(".point b").html(data.data.score);
					} else {
						//$.tip(data.message);
						location.href = DOMIN.MAIN + "/counterman/tobindCounterman.html";
					}
			},
			error: function(data) {
					$.tip("服务器连接失败！");
			}
		})
	}
	//定损中心导航
	$("#map").on('click',function() {
		location.href = DOMIN.MAIN + "/gsbranch/showmap.html?status=1&citySet=1";
	})
	$("#mall").on('click',function() {
		location.href = DOMIN.MAIN + "/counterman/point-mall.html";
	})
	$(".detail").on('click',function() {
		window.location.href = DOMIN.MAIN + "/counterman/point-details.html?";
	})
	//返回主页按钮
	$(".back").on('click',function() {
		window.location.href = DOMIN.MAIN;
	})
})

var win=$(window)[0];
win.addEventListener('touchmove',up_move,false);
var clentH=$(window).height();
var documentH=$('.jump').height();
function up_move(){
	var flag=false;
	var scrollTop=$(document).scrollTop();
	var top=$('.jump').offset().top-documentH||0;
	var touch;
	var space;
	if(parseInt(top) < clentH+scrollTop){
		flag=true;	
		touch=$('.up_touch')[0];
		space=-150;
		if(parseInt(window.screen.availHeight)>750){
			space=-350;
		}
	}
	if(flag){
		var fingerY;
		var	startY;
		touch.addEventListener('touchstart',function(e){
			startY=e.touches[0].clientY;
			touch.addEventListener('touchmove',function(event){
				fingerY=event.touches[0].clientY;
				fingerY-=startY;
				if(fingerY<0){
					$('.content').attr('style','top:'+fingerY+'px');
				}
				if(fingerY<space){
					window.location.href='www.hao123.com';
				}
			},false);
		},false);
		touch.addEventListener('touchend',function(){
			if(fingerY<10){
				$('.content').stop().animate({top:0},500,function(){
					fingerY=null,startY=null;
				});
			}
		},false);
	}
}

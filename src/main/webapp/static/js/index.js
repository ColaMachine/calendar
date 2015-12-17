/**
 * 
 */
var App={
		init:function(){
//			this.menuInit();
		},
		menuInit: function(){
			var _this = this;
			$('.menu ul a').click(function(event){alert(1);
				event.preventDefault();
				
				
				
				if(isNull($(this).attr("href"))){
					if($(this).parent().hasClass('open')){
						$(this).parent().removeClass('open');
						$(this).parent().find("ul").slideUp(500);
					}else{
						$(this).parent().parent().find('li').removeClass('open');
						
						$(this).parent().find("ul").slideDown(500);
						$(this).parent().addClass('open');
					}
				}else{
					$(this).parent().parent().find('a').removeClass('active');
					$(this).addClass('active');
					_this.loadPage($(this).attr("href"));
				}
			});
			$('#logout').click(function(){
				jDialog.confirm('退出系统', '确实要退出吗？', function(){
					_this.logout();
				});
			});
			$('#userprofile').click(function(){
				_this.userprofile();
			});
			$('#userpasswd').click(function(){
				_this.userpasswd();
			});
		},
		
		loadPage: function(url, fun){
		//	jLoading.start();
			$.ajax({
				type: 'GET',
				url: url,
				dataType: 'html',
				success: function(data){
					//jLoading.close();
					
					$('#main').html(data);
					if(typeof fun == 'function') fun();
				},
				error: function(){
					//jLoading.close();
					//jDialog.alert('加载页面失败', '系统错误')
				}
			});
		},
}



$(function(){
	App.init();
	//AwifiJoint.loadPage('/account/overview.htm');
	//App.loadPage('/account/accounthomepage.htm');
//App.loadPage('/calendar/index2');
});



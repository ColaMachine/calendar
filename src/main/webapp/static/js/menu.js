/**
 * 
 */

var zMenu={
		autoCollapse:true,
		animation:true,
		 idName:"",
		 urlName :"",
		 pidName:"",
		 menuName:"",
		init:function(id,data,option){
			 var ele=document.getElementById(id);
			 if(ele){
				
			var html="<ul class='nav'>";
//			document.getElementById(id).appendChild()
			 this.idName=option.id;
			 this.urlName =option.url;
			 this.pidName=option.pid;
			 this.menuName =option.name;
			 
			for(var i=0;i<data.length;i++){
				if(StringUtil.isBlank(data[i][this.pidName])||data[i][this.pidName]==0  ){
					html+=this.createLi(data,data[i]);
				}
			}
			 html+="</ul>";
			 ele.innerHTML=html;
			 this.addEvent(id);
			 }
		},
		addEvent:function(id){
			var _this = this;
			var menuWrap = document.getElementById(id);
			$("#"+id).find('ul a').click(function(event){
				
				event.preventDefault();
				//console.log($(".menu-wrap")[0].offsetWidth);
				if($(".menu-wrap")[0].offsetWidth<100){
					//return;
				}
				
				if(isNull($(this).attr("href"))){
					var id=$(this).attr("id");
					var ul="m_u_"+id.substr(4);
					//console.log($(this).parent().length);
					//查找当前所属的li状态是否是打开状态的
					if($(this).parent().hasClass('open')){//console.log("rm open");
						$(this).parent().removeClass('open');
						$("#"+ul).slideUp(200,function(){});
						//$(this).parent().find("ul").eq(0).slideUp(200,function(){});
					}else{//console.log("add open");
					//$(".mark").removeClass("mark");
					//$(this).addClass("mark");
						//$(this).parent().parent().find('li').removeClass('open');
						//$(this).parent().parent().find('.open').find("ul").eq(0).slideUp(200);
						//打开当前节点,关闭同级节点里的open元素.
						
						
						//$(this).parent().siblings().filter('.open').removeClass('open');
						//$(this).parent().find("ul").eq(0).slideDown(100);
						$("#"+ul).slideDown(100,function(){});
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
			window.data={};
			//截取参数
			var position=url.indexOf("?");
			if(position>0){
				var paramsStr= url.substring(position+1);
				console.log("paramsStr:"+paramsStr);
				var arr= paramsStr.split("&");
				
				for(var i=0;i<arr.length;i++){
					var keyVal=arr[i].split("=");
					var key=keyVal[0];
					var val=keyVal[1];
					console.log(keyVal[0]+":"+keyVal[1]);
					window.data[key]=val;
				}
		}
			//	jLoading.start();
				$.ajax({
					type: 'GET',
					url: url,
					dataType: 'html',
					success: function(data){
						//jLoading.close();
						$('.main-content').html(data);
						if(typeof fun == 'function') fun();
					},
					error: function(){
						//jLoading.close();
						//jDialog.alert('加载页面失败', '系统错误')
					}
				});
			},
		createIcon:function(){
			
			return  	"<i class=\"material-icons\"><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"48\" height=\"48\" viewBox=\"0 0 48 48\">"+
			"<rect x=\"20\" y=\"32\" width=\"8\" height=\"8\" fill=\"#0cc2aa\"></rect>"+
			"<rect x=\"8\" y=\"20\" width=\"8\" height=\"8\" fill=\"#0cc2aa\"></rect>"+
			"<rect x=\"20\" y=\"8\" width=\"8\" height=\"8\" fill=\"#0cc2aa\"></rect>"+
			"<rect x=\"32\" y=\"20\" width=\"8\" height=\"8\" fill=\"#0cc2aa\"></rect>"+
		"</svg></i>";
		},
		createLi:function(data,row){
			var html=
			"<li ><a id=\"m_a_"+row["id"]+"\" href=\""+row[this.urlName]+"\" ><span class='nav-icon'><i class='"+row["icon"]+"'></i></span><span class='nav-text'>"+row[this.menuName]+"</span>"+(isNull(row[this.urlName])?"<span class='nav-caret'><i class=\"fa fa-caret-down\"></i></span>":"")+"</a><ul id=\"m_u_"+row["id"]+"\">";
			for(var i=0;i<data.length;i++){
				if(typeof data[i][this.pidName] != 'undefined' && data[i][this.pidName] != null && data[i][this.pidName]==row[this.idName]){//说明有子项目
					html+=this.createLi(data,data[i]);
				}
			}
			html+="</ul></li>";
			return html;
		}
}
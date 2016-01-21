<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name=”viewport” content=”width=device-width, initial-scale=1, maximum-scale=1″>
<title>Insert title here</title>
 <link href="${path}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />  
<link href="${path}/static/css/font-awesome.css" rel="stylesheet" type="text/css" />
 <link href="${path}/static/css/main.css" rel="stylesheet" type="text/css" /> 
<link href="${path}/static/css/menu.css" rel="stylesheet" type="text/css" />

<link href="${path}/static/css/collapse.css" rel="stylesheet" type="text/css" />


<link href="${path}/static/css/form.css" rel="stylesheet" type="text/css" />
<link href="${path}/static/css/col.css" rel="stylesheet" type="text/css" />
<link href="${path}/static/css/font.css" rel="stylesheet" type="text/css" />
<link href="${path}/static/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${path}/static/css/head.css" rel="stylesheet" type="text/css" />
 <link href="${path}/static/css/global.css" rel="stylesheet" type="text/css" /> 
 <link href="${path}/static/css/widget.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" >
var WEBCONTEXT="${path}";
var PATH="${path}";
</script>
<script type="text/javascript" src="${path}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${path}/static/js/common.js"></script>
<script type="text/javascript" src="${path}/static/js/menu.js"></script>
<script type="text/javascript" src="${path}/static/js/validmsg.js"></script>
<script type="text/javascript" src="${path}/static/js/DateUtils.js"></script>
<script type="text/javascript" src="${path}/static/js/grid.js"></script>
<script type="text/javascript" src="${path}/static/js/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="${path}/static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${path}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${path}/static/js/additional-methods.js"></script>


</head>
<body>

<div id="page" class="page" style="">
	   <div class=" menu-wrap clearfix dark ">
        <div class="logo">
            <!-- <span class="logo-head">aWiFi</span> -->
            
            <%-- <img src="${path}/statics/img/logo.png"></img>
 --%>           
             <div class="logo-desc" ><span class="logo-desc-text"'>后台管理系统</span><span class="nav-icon"><i onclick="$('#page').toggleClass('collapse1')" style="" class="fa fa-reorder">&nbsp;</i></span></div>
        </div>
        <div id="menu" class="menu">
        </div>
    </div>
	<div class="main-wrap">
		<div  class="head-wrap navbar white">
		
		<div class="navbar-item pull-left h5 ng-binding"  ng-bind="$state.current.data.title" id="pageTitle">Widgets</div>
<!-- 				<div class="dropdown pull-left navbar-item "  style="vertical-align:middle">
  <button class="btn btn-default dropdown-toggle" type="button" 
  id="dropdownMenu1" data-toggle="dropdown" 
  aria-haspopup="true" aria-expanded="true">
    Dropdown
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
    <li><a href="#">Action</a></li>
    <li><a href="#">Another action</a></li>
    <li><a href="#">Something else here</a></li>
    <li><a href="#">Separated link</a></li>
  </ul>
</div> -->
				<ul class="login-info navbak navbar-nav pull-right">
					<li class="nav-item dropdown pos-stc-xs"><a class="nav-link"
						href="" data-toggle="dropdown">
						<i class="fa fa-bell-o"></i>
							<span class="label label-sm up warn">3</span></a>
					</li>

					<li class="nav-item dropdown">
                    <a aria-expanded="false"
						class="nav-link clear" href="" data-toggle="dropdown">&nbsp;<span
							class="avatar w-32"><img src="img/a0.jpg"
								alt="..."> <i class="on b-white bottom"></i></span></a>
								<ul id="menu1" class="pull-right dropdown-menu" aria-labelledby="drop4">
          <li><a href="/calendar/logout.htm">登出</a></li>
          <li role="separator" class="divider"></li>
     <li><a href="#">修改资料</a></li>
        </ul>
					</li>
					
					<!-- <li><a><img style="width:50px ;height:50px;" alt="头像" src="img/a0.jpg" /></a>  
			</li>-->
		
			</ul>
			<!-- <div class="collapse navbar-toggleable-sm"   id="collapse">
                    <form
                        class="navbar-form form-inline pull-right pull-none-sm navbar-item v-m ng-pristine ng-valid ng-scope"
                        role="search">

                        <div class="form-group l-h m-a-0">
                            <div class="input-group input-group-sm">
                                <input class="form-control p-x b-a rounded"
                                    placeholder="Search projects..." type="text"> <span
                                    class="input-group-btn"><button type="submit"
                                        class="btn white b-a rounded no-b-l no-shadow">
                                        <i class="fa fa-search"></i>
                                    </button></span>
                            </div>
                        </div>
                    </form>
                    <ul class="nav navbar-nav" >
                        <li class="nav-item dropdown"><a aria-expanded="false"
                            class="nav-link" href="" data-toggle="dropdown"><i
                                class="fa fa-fw fa-plus text-muted"></i> <span>New</span></a>
                        <div class="dropdown-menu dropdown-menu-scale ng-scope">
                                <a href="#/app/inbox/compose" class="dropdown-item"
                                    ui-sref="app.inbox.compose"><span>Inbox</span></a> <a
                                    href="#/app/todo" class="dropdown-item" ui-sref="app.todo"><span>Todo</span></a>
                                <a href="#/app/note/list" class="dropdown-item"
                                    ui-sref="app.note.list"><span>Note</span> <span
                                    class="label primary m-l-xs">3</span></a>
                                <div class="dropdown-divider"></div>
                                <a href="#/app/contact" class="dropdown-item"
                                    ui-sref="app.contact">Contact</a>
                            </div></li>
                    </ul>
                </div> -->
		</div>
    <div class="footer-wrap"></div>
    <div id="main " class="body-wrapper " >
		<div id="main " class="main-content  container " >
        
		<div class="row white" style="color:black;margin-top:15px;">
		  <div class="col-xs-4 ">
		  hello
		  </div>
		    <div class="col-xs-8 ">
         <div class="tranform-demo" onclick="$('.tranform-demo').toggleClass('tranform-demo2')" >asdasdfasdfaaaaaaaaaaaa</div>
          </div>
		</div>
		<h1></h1>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<!-- 	<div id="modal" class="modal" >
				<div class="modal-footer">
					<button type="button" class="btn btn-red">提交</button>
					<button type="button" data-dismiss='modal' class="btn btn-red">取消</button>
				</div>
			</div> -->
			
			
		</div><!--  main content 结束 -->
	</div>
	
</div>
    

     
</div>
<div class="widget"></div>
    <div class="mask" ></div>
    
</body>
<script type="text/javascript" src="${path}/static/js/index.js"></script>

<script type="text/javascript" >
var menuList=[
              {id:1,name:"日志管理",url:"",pid:0,icon:"fa fa-bank"},
              {id:2,name:"访问日志",url:"",pid:1},
              {id:3,name:"异常日志",url:"${path}/log/listRequestLog",pid:1},
              {id:21,name:"访问日志A",url:"${path}/log/listRequestLog",pid:2},
              {id:22,name:"访问日志B",url:"${path}/log/listRequestLog",pid:2},
              {id:5,name:"用户管理",url:"",pid:0,icon:"fa fa-diamond"},
              {id:6,name:"角色管理",url:"${path}/auth/role/list.htm",pid:5}, 
              {id:7,name:"日历",url:"${path}/static/html/CalendarView.html",icon:"fa fa-calendar",pid:0},
               {id:8,name:"组件库",url:"",icon:"fa fa-bug",pid:0},
              {id:9,name:"手机登录页面",url:"${path}/static/phone/login.html",icon:"fa fa-spinner",pid:8},
              {id:10,name:"上传图片",url:"${path}/static/html/imageCompress.html",icon:"fa fa-bank",pid:8},
              {id:11,name:"列表",url:"${path}/static/html/example/table.html",icon:"fa fa-spinner",pid:8},
              {id:12,name:"alert",url:"${path}/static/html/example/alert.html",icon:"fa fa-spinner",pid:8},
              {id:13,name:"短信",url:"${path}/smsBatch/list.htm",icon:"fa fa-spinner",pid:0}, 
              ]
zMenu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});
</script>
<script src="${path}/static/js/bootstrap.min.js" type="text/javascript"></script>
</html>
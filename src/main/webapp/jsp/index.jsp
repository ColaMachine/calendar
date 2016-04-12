<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name=”viewport” content=”width=device-width, initial-scale=1, maximum-scale=1″>
<title>Insert title here</title>
<script type="text/javascript" src="${path}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${path}/static/js/common.js"></script>
<script type="text/javascript" >
var WEBCONTEXT="${path}";
var PATH="${path}"; 
includeCSS(["/static/css/bootstrap.min.css",
"/static/css/font-awesome.css",
"/static/css/main.css",
"/static/css/menu.css",
"/static/css/collapse.css",
"/static/css/form.css",
"/static/css/col.css",
"/static/css/font.css",
 "/static/css/grid.css",
  "/static/css/jqgrid.css",
  "/static/css/ui.jqgrid.css",
  "/static/css/head.css",
   "/static/css/global.css",
    "/static/css/widget.css",
    "/static/css/window.css",
    "/static/css/zTreeStyle.css",
     "/static/css/layer.css"
  
]);


includeJS(["/static/js/menu.js" ,
           "/static/js/validmsg.js",
           "/static/js/DateUtils.js",
           "/static/js/jquery-ui.min.js",
            /*"/static/js/grid.js",*/
            "/static/js/jquery.jqGrid.js",
            "/static/js/jquery.form.js",
            "/static/js/grid.locale-en.js",
            "/static/js/My97DatePicker/WdatePicker.js",
            "/static/js/jquery.validate.js",
            "/static/js/additional-methods.js",
            "/static/js/index.js",
            "/static/js/window.js",
            "/static/js/bootstrap.min.js",
            "/static/js/drag.js",
            "/static/js/dialog.js",
            "/static/js/jquery.ztree.core-3.5.js",
            "/static/js/jquery.ztree.excheck-3.5.js",
              "/static/js/layer.js",

          ]);
</script>


</head>
<body>

<div id="page" class="page" style="">
	   <div class=" menu-wrap clearfix dark ">
        <div class="logo">
            <!-- <span class="logo-head">aWiFi</span> -->
            
            <%-- <img src="${path}/statics/img/logo.png"></img>
 --%>           
             <div class="logo-desc" ><span class="nav-icon"><i onclick="$('#page').toggleClass('collapse1')" style="" class="fa fa-reorder">&nbsp;</i></span><span class="logo-desc-text"'>后台管理系统</span></div>
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
          <li><a href="javascript:void(0)" id="logout">登出</a></li>
          <li role="separator" class="divider"></li>
     <li><a href="#">修改资料</a></li>
        </ul>
					</li>
					
					<!-- <li><a><img style="width:50px ;height:50px;" alt="头像" src="img/a0.jpg" /></a>  
			</li>-->
		
			</ul>
			<div id="dialog-zone"></div>
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
		 <div id="SysUserList" class="rgt_body">
             <div class="body_title">| 用户</div>
                 <div class="body_top" >
                     <form class="form-inline app-search">

                 <label for="id">主键</label>
                     <input type="number" id="id" name="id"  class="form-control input-sm"  maxlength="15" onkeyup="chkInt(this,8)" onafterpaste="chkInt(this,8)" placeholder="主键"></input>
                 <label for="username">用户名</label>
                     <input type="text" id="usernameLike" name="usernameLike"  class="form-control input-sm"   maxlength="20" placeholder="用户名" ></input>
                 <label for="password">密码</label>
                     <input type="text" id="passwordLike" name="passwordLike"  class="form-control input-sm"   maxlength="50" placeholder="密码" ></input>
                 <label for="nkname">昵称</label>
                     <input type="text" id="nknameLike" name="nknameLike"  class="form-control input-sm"   maxlength="20" placeholder="昵称" ></input>
                 <label for="type">类型</label>
                     <input type="number" id="type" name="type"  class="form-control input-sm"  maxlength="4" onkeyup="chkInt(this,8)" onafterpaste="chkInt(this,8)" placeholder="类型"></input>
                 <label for="status">状态</label>
                     <select id="status" name="status"  class="form-control input-sm"  >
                         <option value=''>-请选择-</option>
                         <option value=1>正常</option>
                         <option value=2>禁用</option>
                         <option value=3>未激活</option>
                     </select>
                 <label for="email">邮箱地址</label>
                     <input type="text" id="emailLike" name="emailLike"  class="form-control input-sm"   maxlength="50" placeholder="邮箱地址" ></input>
                 <label for="telno">手机号码</label>
                     <input type="text" id="telnoLike" name="telnoLike"  class="form-control input-sm"   maxlength="11" placeholder="手机号码" ></input>
                 <label for="idcard">身份证号码</label>
                     <input type="text" id="idcardLike" name="idcardLike"  class="form-control input-sm"   maxlength="18" placeholder="身份证号码" ></input>
                 <label for="sex">性别</label>
                     <input type="number" id="sex" name="sex"  class="form-control input-sm"  maxlength="1" onkeyup="chkInt(this,8)" onafterpaste="chkInt(this,8)" placeholder="性别"></input>
                 <label for="birth">出生年月</label>
                 <div class="input-group">
                     <input type="text" id="birth" name="birth"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  datatype="date" format="yyyy-MM-dd"  placeholder="出生年月" ></input>
                     <label class="input-group-addon" for="birth" ><i class="fa fa-calendar"></i></label>        </div>
                 <label for="birthBegin">出生年月开始</label>
                 <div class="input-group">
                     <input type="text" id="birthBegin" name="birthBegin"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  datatype="date" format="yyyy-MM-dd"  placeholder="出生年月开始" ></input>
                     <label class="input-group-addon" for="birthBegin" ><i class="fa fa-calendar"></i></label>        </div>
                 <label for="birthEnd">出生年月结束</label>
                 <div class="input-group">
                     <input type="text" id="birthEnd" name="birthEnd"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  datatype="date" format="yyyy-MM-dd"  placeholder="出生年月结束" ></input>
                     <label class="input-group-addon" for="birthEnd" ><i class="fa fa-calendar"></i></label>        </div>
                 <label for="integral">积分</label>
                     <input type="number" id="integral" name="integral"  class="form-control input-sm"  maxlength="11" onkeyup="chkInt(this,8)" onafterpaste="chkInt(this,8)" placeholder="积分"></input>
                 <label for="address">地址</label>
                     <input type="text" id="addressLike" name="addressLike"  class="form-control input-sm"   maxlength="50" placeholder="地址" ></input>
                 <label for="weichat">微信</label>
                     <input type="text" id="weichatLike" name="weichatLike"  class="form-control input-sm"   maxlength="20" placeholder="微信" ></input>
                 <label for="qq">qq</label>
                     <input type="number" id="qq" name="qq"  class="form-control input-sm"  maxlength="11" onkeyup="chkInt(this,8)" onafterpaste="chkInt(this,8)" placeholder="qq"></input>
                 <label for="face">头像</label>
                     <input type="text" id="faceLike" name="faceLike"  class="form-control input-sm"   maxlength="100" placeholder="头像" ></input>
                 <label for="remark">备注</label>
                     <input type="text" id="remarkLike" name="remarkLike"  class="form-control input-sm"   maxlength="200" placeholder="备注" ></input>
                 <label for="createtime">创建时间</label>
                 <div class="input-group">
                     <input type="text" id="createtime" name="createtime"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  datatype="date" format="yyyy-MM-dd HH:mm:ss"  placeholder="创建时间" ></input>
                     <label class="input-group-addon" for="createtime" ><i class="fa fa-calendar"></i></label>        </div>
                 <label for="createtimeBegin">创建时间开始</label>
                 <div class="input-group">
                     <input type="text" id="createtimeBegin" name="createtimeBegin"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  datatype="date" format="yyyy-MM-dd HH:mm:ss"  placeholder="创建时间开始" ></input>
                     <label class="input-group-addon" for="createtimeBegin" ><i class="fa fa-calendar"></i></label>        </div>
                 <label for="createtimeEnd">创建时间结束</label>
                 <div class="input-group">
                     <input type="text" id="createtimeEnd" name="createtimeEnd"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  datatype="date" format="yyyy-MM-dd HH:mm:ss"  placeholder="创建时间结束" ></input>
                     <label class="input-group-addon" for="createtimeEnd" ><i class="fa fa-calendar"></i></label>        </div>
                 <label for="updatetime">更新时间</label>
                 <div class="input-group">
                     <input type="text" id="updatetime" name="updatetime"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  datatype="date" format="yyyy-MM-dd HH:mm:ss"  placeholder="更新时间" ></input>
                     <label class="input-group-addon" for="updatetime" ><i class="fa fa-calendar"></i></label>        </div>
                 <label for="updatetimeBegin">更新时间开始</label>
                 <div class="input-group">
                     <input type="text" id="updatetimeBegin" name="updatetimeBegin"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  datatype="date" format="yyyy-MM-dd HH:mm:ss"  placeholder="更新时间开始" ></input>
                     <label class="input-group-addon" for="updatetimeBegin" ><i class="fa fa-calendar"></i></label>        </div>
                 <label for="updatetimeEnd">更新时间结束</label>
                 <div class="input-group">
                     <input type="text" id="updatetimeEnd" name="updatetimeEnd"  class="form-control input-sm"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  datatype="date" format="yyyy-MM-dd HH:mm:ss"  placeholder="更新时间结束" ></input>
                     <label class="input-group-addon" for="updatetimeEnd" ><i class="fa fa-calendar"></i></label>        </div>


                         <button type="button" onclick="search()" class="btn btn-default">查询</button>
                     </form>
                 <div >
                     <button class="btn addBtn" >新增</button>
                     <button class="btn deleteBtn">删除</button>
                     <button class="btn exportBtn" >导出</button>
                 </div>
             </div>
             <table id="SysUserGrid" class="grid"></table>
             <div id="pagetest" class="pager"></div>
             <div class="modal" id="mymodal">
                 <div class="modal-dialog">
                     <div class="modal-content">
                         <div class="modal-header">
                             <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                             <h4 class="modal-title">模态弹出窗标题</h4>
                         </div>
                         <div class="modal-body">
                             <p>模态弹出窗主体内容</p>
                         </div>
                         <div class="modal-footer">
                             <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                             <button type="button" class="btn btn-primary">保存</button>
                         </div>
                     </div><!-- /.modal-content -->
                 </div><!-- /.modal-dialog -->
             </div><!-- /.modal -->

         </div>

		  </div>

		</div>
		<h1></h1>

			
			
		</div><!--  main content 结束 -->
	</div>
	
</div>
    

     
</div>
<div class="widget"></div>
    <div class="mask" ></div>
   
    
</body>


<script type="text/javascript" >
var menuList=[
              {id:1,name:"日志管理",url:"",pid:0,icon:"fa fa-bank"},
              {id:2,name:"访问日志",url:"",pid:1},
              {id:3,name:"异常日志",url:PATH+"/log/listRequestLog",pid:1},
              {id:21,name:"访问日志A",url:PATH+"/log/listRequestLog",pid:2},
              {id:22,name:"访问日志B",url:PATH+"/log/listRequestLog",pid:2},
              {id:5,name:"用户管理",url:"",pid:0,icon:"fa fa-diamond"},
              {id:51,name:"用户管理",url:PATH+"/user/list.htm",icon:"fa fa-spinner",pid:5},
              {id:6,name:"角色管理",url:PATH+"/auth/role/list.htm",pid:5}, 
              {id:7,name:"日历",url:PATH+"/static/html/CalendarView.html",icon:"fa fa-calendar",pid:0},
               {id:8,name:"组件库",url:"",icon:"fa fa-bug",pid:0},
              {id:9,name:"手机登录页面",url:PATH+"/login/login.htm",icon:"fa fa-spinner",pid:8},
              {id:10,name:"上传图片",url:PATH+"/static/html/imageCompress.html",icon:"fa fa-bank",pid:8},
              {id:11,name:"列表",url:PATH+"/static/html/example/table.html",icon:"fa fa-spinner",pid:8},
              {id:12,name:"alert",url:PATH+"/static/html/example/alert.html",icon:"fa fa-spinner",pid:8},
              {id:13,name:"短信",url:PATH+"/smsBatch/list.htm",icon:"fa fa-spinner",pid:0}, 
              {id:14,name:"合作伙伴",url:PATH+"/PartnerUserlist.htm",icon:"fa fa-spinner",pid:0}, 
              {id:15,name:"用户",url:PATH+"/sysUser/list.htm",icon:"fa fa-spinner",pid:5},
              {id:16,name:"角色",url:PATH+"/sysRole/list.htm",icon:"fa fa-spinner",pid:5},
              {id:17,name:"资源",url:PATH+"/sysResource/list.htm",icon:"fa fa-spinner",pid:5},
              {id:18,name:"用户角色",url:PATH+"/sysUserRole/list.htm",icon:"fa fa-spinner",pid:5},
              {id:19,name:"角色资源",url:PATH+"/sysRoleResource/list.htm",icon:"fa fa-spinner",pid:5},
              {id:20,name:"用户资源",url:PATH+"/sysUserResource/list.htm",icon:"fa fa-spinner",pid:5},
               {id:21,name:"用户角色关联",url:PATH+"/sysUserRole/listMapper.htm",icon:"fa fa-spinner",pid:5},
               {id:22,name:"角色资源关联",url:PATH+"/sysRoleResource/listMapper.htm",icon:"fa fa-spinner",pid:5},
               {id:23,name:"用户资源关联",url:PATH+"/sysUserResource/listMapper.htm",icon:"fa fa-spinner",pid:5},
              {id:24,name:"短信验证码",url:PATH+"/smsRecord/list.htm",icon:"fa fa-spinner",pid:0},
              ]
zMenu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});


var sysUserList={
    modal:false,
    mygrid:null,
    treeObj:null,
    root:$("#SysUserList"),
    init:function(){
        this.mygrid =this.root.find("#SysUserGrid").jqGrid(this.gridParam);

        this.addEventListener();

    },
    addEventListener:function(){
        $(this.root).find(".addBtn").click(this.addInfo.Apply(this));
        $(this.root).find(".editBtn").click(this.editInfo.Apply(this));
        $(this.root).find(".deleteBtn").click(this.deleteInfo.Apply(this));
        $(this.root).find(".searchBtn").click(this.searchInfo.Apply(this));
    },
    gridParam:{
                  datatype: "json",
                  viewrecords: true, sortorder: "desc", caption:"JSON Example",
                  rowNum:10,
                  rowList:[10,20,30],
                  multiselect : true,
                  url : PATH+'/sysUser/list.json',
                  pager:"#pagetest",
                  jsonReader:jsonReader,
                  colNames : [
                   "主键","用户名","密码","昵称","类型","状态","邮箱地址","手机号码","身份证号码","性别","出生年月","积分","地址","微信","qq","头像","备注","创建时间","更新时间" , '操作' ],
                  colModel : [
                          {
                              name : 'id',
                              width : 80,
                          } ,
                          {
                              name : 'username',
                              width : 80,
                          } ,
                          {
                              name : 'password',
                              width : 80,
                          } ,
                          {
                              name : 'nkname',
                              width : 80,
                          } ,
                          {
                              name : 'type',
                              width : 80,
                          } ,
                          {
                              name : 'status',
                              width : 80,
                              formatter : function(value, grid, rows) {
                                  var map ={'1':'正常','2':'禁用','3':'未激活',};
                                  return map[value];
                              }
                          } ,
                          {
                              name : 'email',
                              width : 80,
                          } ,
                          {
                              name : 'telno',
                              width : 80,
                          } ,
                          {
                              name : 'idcard',
                              width : 80,
                          } ,
                          {
                              name : 'sex',
                              width : 80,
                          } ,
                          {
                              name : 'birth',
                              width : 80,
                          } ,
                          {
                              name : 'integral',
                              width : 80,
                          } ,
                          {
                              name : 'address',
                              width : 80,
                          } ,
                          {
                              name : 'weichat',
                              width : 80,
                          } ,
                          {
                              name : 'qq',
                              width : 80,
                          } ,
                          {
                              name : 'face',
                              width : 80,
                          } ,
                          {
                              name : 'remark',
                              width : 80,
                          } ,
                          {
                              name : 'createtime',
                              width : 80,
                               formatter : function(value, grid, rows) {
                                  return new Date(value).format("yyyy-MM-dd");
                              }
                          } ,
                          {
                              name : 'updatetime',
                              width : 80,
                               formatter : function(value, grid, rows) {
                                  return new Date(value).format("yyyy-MM-dd");
                              }
                          }
                          ,
                          {
                              name : 'id',
                              width : 150,
                              formatter : function(value, grid, rows) {
                                  return getViewHtml(value,"sysUserList")+getEditHtml(value,"sysUserList")+getDelHtml(value,"sysUserList");
                              }
                          }
                    ],

                    onSelectRow: function(id){ //alert("单击选中"+id);

                    },
    },
    saveInfo:function(){
    },
    addInfo:function (){
        dialog.window('/sysUser/edit.htm',this.modal);
    },
    editInfo:function (id){
        dialog.window("/sysUser/edit.htm?id="+id,this.modal);
    },
    searchInfo:function (){
        var jso = changeForm2Jso(".app-search");
        this.mygrid.jqGrid("search",jso);
    },
    deleteInfo:function (id){
         //弹窗
         zconfirm("确定删除数据:"+id,"删除",function(){
            Ajax.post(PATH+"/sysUser/del.json?id="+id,function(result){
                result=ajaxResultHandler(result);
                if(result.r==AJAX_SUCC){
                    dialog.alert("删除成功，数据："+id,function(index){
                    $("#grid").jqGrid("reloadGrid");
                    dialog.close(index);
                });
                }else {
                    dialog.error(result.msg,"提醒");
                }
            });
        });
    },
    viewInfo:function (id){
        dialog.window("/sysUser/view.htm?id="+id,this.modal);
    },
    search:function (){
        var jso= changeForm2Jso(".app-search");
        console.log(jso);
        this.mygrid.search(jso);
    },
    exportInfo:function (){
        var jso= changeForm2Jso(".app-search");
        Ajax.getJSON(PATH+"/sysUser/export.json",jso,function(data){
            if(data.r==AJAX_SUCC){
                window.location=PATH+"/"+data.data;
            }else{
                dialog.error(data.msg,"导出失败",null);
            }
        })
    },
    multiDelete:function (){
        //获取ids字符串
        var ids=this.mygrid.jqGrid("getGridParam","selarrrow");
        if(ids.length==0){
            dialog.alert("请勾选数据");
            return;
        }
        var data= this.mygrid.jqGrid("getGridParam","data");
        for(var i=0;i<ids.length;i++){
            ids[i]=data[ids[i]]["id"];
        }
        //弹窗
        dialog.confirm("确定删除数据:"+ids.join(","),function(){
            Ajax.post(PATH+"/sysUser/mdel.json?ids="+ids.join(","),function(result){
                result=ajaxResultHandler(result);
                if(result.r==AJAX_SUCC){
                    dialog.alert("删除成功，数据："+ids.join(","),function(index){
                    this.mygrid.jqGrid("reloadGrid");
                    dialog.close(index);
                });
                }else {
                    dialog.error(result.msg);
                }
            });
        });
    }
};
sysUserList.init()
</script>
</html>
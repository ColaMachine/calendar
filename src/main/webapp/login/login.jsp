<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<html>
<head>
  <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
    
<script src="<%=path %>/js/jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="<%=path %>/js/jquery.validate.js" type="text/javascript"
	charset="utf-8"></script>
	<script src="<%=path %>/js/additional-methods.js" type="text/javascript"
	charset="utf-8"></script>
<style type="text/css">
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

</style>
</head>
<body>

    <div  class="container">
      <form id="login_form" class="form-signin" action="<%=path %>/loginPost" method="post">
        <h3 class="form-signin-heading"><span id="form_title">登录</span>  <a style="float:right" id="form_type"  onclick="changeForm()">注册</a></h3>  
	      
        <label for="email" class="sr-only">Email address</label>
        <input type="email" name="email" maxlength="50" class="form-control" placeholder="邮件地址" required  autofocus>
        <label for="pwd" class="sr-only">Password</label>
        <input type="password" name="pwd"  maxlength="50"   class="form-control" placeholder="密码" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <a style="float:right">忘记密码</a>
        </div>
        <div class="failure"  ><ul>
     
        </ul></div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
      </form>
      <form id="register_form" style="display:none"   class="form-signin"  action="<%=path %>/registerPost" method="post">
        <h3 class="form-signin-heading"><span id="form_title">注册账号</span>  <a style="float:right" id="form_type"  onclick="changeForm()">登录</a></h3>  
	        <label for="username" class="sr-only">姓名</label>
	        <input   name="username" maxlength="50" class="form-control" placeholder="姓名" required autofocus>
        <label for="email" class="sr-only">邮件地址</label>
        <input type="email"   name="email" maxlength="50" class="form-control" placeholder="邮件地址" required autofocus>
        <label for="pwd" class="sr-only">Password</label>
        <input type="password"  name="pwd"  maxlength="50"   class="form-control" placeholder="密码" required>
        <div class="failure"  ><ul>
     
        </ul></div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
      </form>
    </div>
</body>
<script  type="text/javascript">

var MyValidator = function() {
    var handleSubmit = function() {
        $('#login_form').validate({
        	errorElement: 'div',
			errorClass: 'help-block',
			focusInvalid: false,
            rules : {
            	 email : {
            		 email:true,
            		 required : true,
                     rangelength:[1,50]
                 },
                 pwd : {
                	 stringCheck:true,
            		 required : true,
                     rangelength:[6,15]
                 },
                 
            },
            messages : {
            	email : {
            		email:"请填写真实的邮箱",
                     required : "邮箱未填写",
                     rangelength:"邮箱长度应在50字符以内"
                 },
                 pwd:{
                	 required : "密码未填写",
                	 rangelength:"密码应由6~20个的数字或字母组成"
                 }
            },

            highlight : function(element) {
              $(element).closest('.form-signin').removeClass('has-info').addClass('has-error');
            },

        	success: function (e) {
				$(e).closest('.form-signin').removeClass('has-error').addClass('has-info');
				$(e).remove();
			},
	
			errorPlacement: function (error, element) {
				 error.insertAfter(element); 
			},
	
			submitHandler: function (form) {
				login();
				
			},
			invalidHandler: function (form) {
			}
        });

        $('#form-signin input').keypress(function(e) {
            if (e.which == 13) {
                if ($(e).closest('.form-signin').validate().form()) {
                	$(e).closest('.form-horizontal').submit();
                }
                return false;
            }
        });
    };
    return {
        init : function() {
            handleSubmit();
        }
    };

}();

MyValidator.init(); 


	var form_type="login";
	function changeForm(){
		if(form_type=="login"){
			$("#register_form").show();
			$("#login_form").hide();
		 	form_type="register"
		 
		}else{
			form_type="login";
			$("#register_form").hide();
			$("#login_form").show();
		}
	}
	
	

	var MyValidator1 = function() {
	    var handleSubmit = function() {
	        $('#register_form').validate({
	        	errorElement: 'div',
				errorClass: 'help-block',
				focusInvalid: false,
	            rules : {
	                 username:{
	                	 required : true,
	                	  rangelength:[3,15]
	                 },
	            	 email : {
	            		 email:true,
	            		 required : true,
	                     rangelength:[1,50]
	                 },
	                 pwd : {
	                	 stringCheck:true,
	            		 required : true,
	                     rangelength:[6,15]
	                 }
	            },
	            messages : {
	                 username:{
	                	 required : "请填写真实的姓名",
	                	  rangelength:"姓名长度应在5~15个字符"
	                 },
	            	email : {
	            		email:"请填写真实的邮箱",
	                     required : "邮箱未填写",
	                     rangelength:"邮箱长度应在50字符以内"
	                 },
	                 pwd:{
	                	 required : "密码未填写",
	                	 rangelength:"密码应由6~20个的数字或字母组成"
	                 }
	            },

	            highlight : function(element) {
	              $(element).closest('.form-signin').removeClass('has-info').addClass('has-error');
	            },

	        	success: function (e) {
					$(e).closest('.form-signin').removeClass('has-error').addClass('has-info');
					$(e).remove();
				},
		
				errorPlacement: function (error, element) {
					 error.insertAfter(element); 
				},
		
				submitHandler: function (form) {
				register();
					
				},
				invalidHandler: function (form) {
				}
	        });

	        $('#form-signin input').keypress(function(e) {
	            if (e.which == 13) {
	                if ($(e).closest('.form-signin').validate().form()) {
	                	$(e).closest('.form-horizontal').submit();
	                }
	                return false;
	            }
	        });
	    };
	    return {
	        init : function() {
	            handleSubmit();
	        }
	    };

	}();

	MyValidator1.init(); 
	
	
	function getCookie(c_name)
	{
	if (document.cookie.length>0)
	  {
	  c_start=document.cookie.indexOf(c_name + "=")
	  if (c_start!=-1)
	    { 
	    c_start=c_start + c_name.length+1 
	    c_end=document.cookie.indexOf(";",c_start)
	    if (c_end==-1) c_end=document.cookie.length
	    return unescape(document.cookie.substring(c_start,c_end))
	    } 
	  }
	return ""
	}

	function setCookie(c_name,value,expiredays)
	{
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
	}

	function checkCookie()
	{
	username=getCookie('username')
	if (username!=null && username!="")
	  {alert('Welcome again '+username+'!')}
	else 
	  {
	  username=prompt('Please enter your name:',"")
	  if (username!=null && username!="")
	    {
	    setCookie('username',username,365)
	    }
	  }
	}
	
	function login(){
		var jso = changeForm2Jso("#login_form");
		$.post("<%=path%>/loginPost",jso,function(data){
			if(data.result=="1"){
				window.location="<%=path%>/index";
			}else{
				var ul = 	$("#login_form").find(".failure").find("ul");
				ul.empty();
				ul.append("<li>"+data.msg+"</li>");
				if(data.errors && data.errors.length>0){
					for(var i in data.errors){
						ul.append("<li>"+data.errors[i]+"</li>");
					}
				}
				
			}
		});
	}
	function register(){
		var jso = changeForm2Jso("#register_form");
		$.post("<%=path%>/registerPost",jso,function(data){
			if(data.result=="1"){
				window.location="<%=path%>/index";
			}else{

				var ul = 	$("#register_form").find(".failure").find("ul");
				ul.empty();
				ul.append("<li>"+data.msg+"</li>");
				if(data.errors){
					
					for(var key in data.errors){  alert(data.errors[key])
						ul.append("<li>"+data.errors[key]+"</li>");
					}
				}
			}
		});
	}
	

	function changeForm2Jso(formId) {
		var jso = {};
		var arr = $( formId).serializeArray();
		for (var i = 0; i < arr.length; i++) {
			jso["" + arr[i].name] = arr[i].value;
		}

		return jso;
	}

  </script>
</html>

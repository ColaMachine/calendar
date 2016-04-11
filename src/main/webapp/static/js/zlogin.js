/**
 *
 *
 * Author:
 *	zzw <zzw1986@gmail.com>
 *
 *
 * File: td_view.js
 * Create Date: 2013-9-23 8:29
 *
 *
 */
var loginForm={
    ids:{
    root:null,
        form:null,
        picCaptchaInput:null,
        picCaptchaImg:null,
        userName:null,
        pwd:null,
        rememberMe:null,
        forgetLink:null,
        submitBtn:null,
    },
    doms:{

    },
    init:function(){
        var cfg={
        root:"#login",
            form:"#login_form",
            userName:"#email",
            pwd:"#loginpwd",
            picCaptchaInput:"#loginPicCaptchaInput",
            picCaptchaImg:"#loginPicCaptchaImg",
            rememberMe:"#rememberme",
            forgetLink:"#forgetLink",
            submitBtn:"#loginBtn",
        };

        $.extend(this.ids,cfg);
        for(var i in this.ids){
            this.doms[i]=$(this.ids[i]);
            if(this.doms[i].length==0){
                console.log(this.ids[i] +"doesn't find ");
            }
        }

        $.extend(this.valid,BaseValidator);
        this.doms.form.validate(this.valid);
        this.doms.submitBtn.removeAttr("disabled");
        this.addEventListener();
    },
    addEventListener:function(){
        //注册按钮
        this.doms.submitBtn.click(this.submit.Apply(this) );
        this.doms.picCaptchaImg.click(this.getPicCaptcha.Apply(this));
    },
    //登录按扭提交
    submit:function(){
        if(!this.doms.form.valid()){
            return;
        }
        var jso = changeForm2Jso("#login_form");
        //jso.password=$.md5(jso.password);
        //先禁用按钮
        $("#loginBtn").attr("disabled", "disabled");
        //alert($("#rememberme").attr("checked"));
        //判断是否使用记住功能
        if ($("#rememberme").attr("checked") == 'checked') {//alert("选中了记住我");
        /*	console.log("选中了记住我" );*/
            console.log("username:" + jso.email);
            setCookie('username', jso.email, 365);
            setCookie('password', jso.password, 365);
        }
        $.post(PATH + "/loginPost.json", jso, function(data) {
            if (data[AJAX_RESULT] == AJAX_SUCC) {
                window.location = PATH + "/index.htm";
            } else {
            dialog.alert(data.msg);
              /*  var ul = $("#login_form").find(".failure").find("ul");
                ul.empty();
                ul.append("<li>" + data[AJAX_MSG] + "</li>");
                if (data[AJAX_ERRORS] && data[AJAX_ERRORS].length > 0) {
                    for ( var i in data[AJAX_ERRORS]) {
                        ul.append("<li style='color:red'>" + data[AJAX_ERRORS][i]
                                + "</li>");
                    }
                }*/

            }
            $("#loginBtn").removeAttr("disabled", "");
        });
    },
    //获取验证码图片点击事件
    getPicCaptcha:function(){
        that =this;
        Ajax.getJSON(PATH+"/code/img/request.json",null,function(result){
            if(result.r==AJAX_SUCC){
               that.doms.picCaptchaImg.prop("src","data:image/png;base64,"+result.data.imgdata);
            }else{
                dialog.error(result.msg);
            }
        });
    },
    valid:
    {
        rules : {
            email : {
                required : true,
              /*  email : true,*/
                rangelength : [ 1, 50 ],
                isemailorphone : true
            },
            pwd : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 15 ],
            },
            picCaptcha:{
                 required : true,
            }
        },
        messages : {
            email : {
                required : "邮箱/手机号未填写",
              /*  isemailorphone:true,*/
                rangelength : "邮箱/手机号长度应在50字符以内",
            },
            pwd : {
                required : "密码未填写",//TODO 增加判断
                rangelength : "密码应由6~20个的数字或字母组成"
            },
            picCaptcha:{
                             required : "请输入验证码",
            }
        },

    },

}
var registerForm={
    ids:{
            root:null,//根节点
            form:null,//表单
            picCaptchaInput:null,//图片输入框
            picCaptchaImg:null,//图片验证码img
            userName:null,//
            realName:null,
            pwd:null,
            pwdrepeat:null,
            submitBtn:null,
        },
    doms:{

    },

    init:function(){
         var cfg={
                    root:"#register",
                    form:"#registerForm",
                    userName:"#email",
                    pwd:"#pwd",
                    pwdrepeat:"#pwdrepeat",
                    realName:"#username",
                    picCaptchaInput:"#regPicCaptchaInput",
                    picCaptchaImg:"#regPicCaptchaImg",
                    submitBtn:"#registerBtn",
                };

                $.extend(this.ids,cfg);
                for(var i in this.ids){
                    this.doms[i]=$(this.ids[i]);
                    if(this.doms[i].length==0){
                        console.log(this.ids[i] +"doesn't find ");
                    }
                }
                $.extend(this.valid,BaseValidator);
                this.doms.form.validate(this.valid);
                this.doms.submitBtn.removeAttr("disabled");
                this.addEventListener();

        //this.doms.form=$("#registerForm");
        // this.modal=$("#registerEnterModal");
         //this.registerEnterForm=$("#registerEnterForm");

       // this.registerBtn=this.doms.form.find("#registerBtn");

        //this.registerEnterBtn=this.modal.find("#registerEnterBtn");
         //this.registerBtn.removeAttr("disabled");
       // this.registerEnterBtn.removeAttr("disabled");
       // this.addEventListener();
         //  $.extend(this.valid,BaseValidator);
           //     this.doms.form.validate(this.valid);

    },
    addEventListener:function(){

         this.doms.submitBtn.click(this.submit.Apply(this) );
        this.doms.picCaptchaImg.click(this.getPicCaptcha.Apply(this));
        //注册按钮




    },
       //获取验证码图片点击事件
    getPicCaptcha:function(){
        that =this;
        Ajax.getJSON(PATH+"/code/img/request.json",null,function(result){
            if(result.r==AJAX_SUCC){
               that.doms.picCaptchaImg.prop("src","data:image/png;base64,"+result.data.imgdata);
            }else{
                dialog.error(result.msg);
            }
        });
    },

    submit:function () {
    	if (!this.doms.form.valid()) {
    		return;
    	}
    	var _this=this;
    	this.doms.submitBtn.attr("disabled", "disabled");
        var jso=changeForm2Jso(this.ids.form);
        $.post(PATH + "/registerPost.json", jso, function(data) {
             _this.doms.submitBtn.removeAttr("disabled");
           if (data[AJAX_RESULT] == AJAX_SUCC) {
                //如果是用手机注册的就弹出手机验证码 发送窗口
                if(StringUtil.isPhone(jso.email)){
                //手机号复制

                    smsValidForm.setPhone(jso.email);
                    smsValidForm.show();
                    //registerForm.registerEnterForm.find("#phone").text(jso.email);
                    //registerForm.modal.modal("show");
                }else
                if(StringUtil.isEmail(jso.email)){
                //手机号复制
                    emailValidForm.setEmail(jso.email);
                     emailValidForm.show();
                    /*registerForm.registerEnterForm.find("#phone").text(jso.email);
                    registerForm.modal.modal("show");*/
                }


           } else {
            _this.alert(data[AJAX_MSG],data[AJAX_ERRORS]);

           }

        });

    	//如果是邮箱注册的就弹出邮箱验证码 发送窗口
    },
    alert:function(msg,arr){
       var ul = this.doms.root.find(".failure").find("ul");
       ul.empty();
       ul.append("<li>" + msg + "</li>");
       if (arr) {
           for ( var key in arr) {
               ul.append("<li>" + arr[key] + "</li>");
           }
       }
    },
    valid:{
        rules : {
            username : {
                required : true,
                rangelength : [ 3, 15 ]
            },
            email : {
            /*	email : true,*/
                required : true,
                isemailorphone:true,
                rangelength : [ 1, 50 ],
            /*	isemail : true*/
            },
            pwd : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 15 ]
            },
            pwdrepeat : {
                stringCheck : true,
                required : true,
                rangelength : [ 6, 15 ],
                equalTo : "#pwd"
            }
        },
        messages : {
            username : {
                required : "请填写真实的姓名",
                rangelength : "姓名长度应在5~15个字符"
            },
            email : {
                email : "请填写真实的邮箱",
                required : "邮箱未填写",
                rangelength : "邮箱长度应在50字符以内"
            },
            pwd : {
                required : "密码未填写",
                rangelength : "密码应由6~20个的数字或字母组成"
            },
            pwdrepeat : {
                required : "密码未填写",
                rangelength : "密码应由6~20个的数字或字母组成",
                equalTo : "两次输入密码不同"
            }
        }
    },


}
var smsValidForm={
 ids:{
            form:null,//form id
            smsCaptchaInput:null,//短信验证码输入框id
            smsCaptchaBtn:null,//短信验证码获取按钮
            phone:null,//手机span
            submitBtn:null,//提交按钮
        },
    doms:{

    },
    valid:{
        rules : {
            smsCaptcha : {
                required : true,
                rangelength : [ 3, 6 ]
            },

        },
        messages : {
            smsCaptcha : {
                required : "请填写短信验证码",
                rangelength : "长度应在3~6个字符"
            },

        }
    },
    init:function(){
        var cfg={
            form:"#smsValidForm",
            smsCaptchaInput:"#smsCaptcha",
            smsCaptchaBtn:"#smsCaptchaBtn",
            phone:"#phone",
            submitBtn:"#smsValidBtn",
        };

        $.extend(this.ids,cfg);
        for(var i in this.ids){
            this.doms[i]=$(this.ids[i]);
            if(this.doms[i].length==0){
                console.log(this.ids[i] +"doesn't find ");
            }
        }
        $.extend(this.valid,BaseValidator);
        this.doms.form.validate(this.valid);
        this.doms.submitBtn.removeAttr("disabled");
        this.addEventListener();

    },

    captchaCutdown:function(smsBtn){
        var self =$(smsBtn);
        self.attr('disabled', true);
        self.text('发送中');
        var time =  60;
        var sI = setInterval(function() {
            time = time - 1;
            if (time > 0) {
                self.text(time + '秒后重试');
            } else {
                window.clearInterval(sI);
                self.text('重新获取');
                self.removeAttr("disabled");
            }
        }, 1000);
    },
   submit:function(){

    	var jso={};
    	jso.smsCaptcha=this.doms.smsCaptchaInput.val();
    	jso.phone=this.doms.phone.text();
    	if(StringUtil.isBlank(jso.smsCaptcha)|| jso.smsCaptcha.length<4){
    	    dialog.alert("请输入验证码");
    	    return;
    	}
        Ajax.post(PATH+"/validPhone.json",jso,function(result){
            if(result.r==AJAX_SUCC){
                 window.location=PATH+"/index.htm";
            }else{
                dialog.alert(result.msg);
            }

        });



    },
    setPhone:function(phone){
        this.doms.phone.text(phone);
    },
    show:function(){
        this.doms.form.modal("show");
    },
    addEventListener:function(){

        var _this=this;
        // this.registerEnterBtn.removeAttr("disabled");
        this.doms.submitBtn.click(this.submit.Apply(this));
        this.doms.smsCaptchaBtn.click(function(){
            _this.captchaCutdown(this);
            //发送短信
            Ajax.getJSON(PATH+"/code/sms/request.json",{"phone":_this.doms.phone.text()},function(result){
                if(result.r==AJAX_SUCC){
                   dialog.alert("发送成功");
                }else{
                    dialog.error(result.msg);
                }
            });
        });
    }
};




function doRegister() {
	document.location = "register.html";
}


var form_type = "login";
//切换登录表单和注册表单
/*function changeForm() {
	$("#registerBtn").removeAttr("disabled");
	$("#loginBtn").removeAttr("disabled");
	if (form_type == "login") {
		$("#register_form").show();
		$("#login_form").hide();
		form_type = "register"
	} else {
		form_type = "login";
		$("#register_form").hide();
		$("#login_form").show();
	}
}*/


/***
 ** 取cookie值
 *
 */
function getCookie(c_name) {
	console.log("cookie:"+document.cookie);
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
//设置cookie值
function setCookie(c_name, value, expiredays) {console.log("expiredays:"+expiredays);
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	console.log(c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString()));
	document.cookie = c_name + "=" + escape(value)
			+ ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

function checkCookie() {
	var username = getCookie('username')
	$("#login-email").val(username);
	//alert(username+$("#login-email").val());
	//return;
	if (username != null && username != "") {//alert('Welcome again '+username+'!')
		document.getElementById("login-email").value = username;

		$("#login-email").val(username);
	}

	else {
		//username = prompt('Please enter your name:', "")
		if (username != null && username != "") {
			setCookie('username', username, 7)
		}
	}
}
/**
 *登录
 *
 **/


$(document).ready(function() {
	//$("#loginBtn").bind('click',function(){alert('tt!')});
	//登录表单验证器初始化
	loginForm.init();
	//loginValidator.init();
	//注册表单初始化
	//registerValidator.init();
	registerForm.init();
	smsValidForm.init();
//在这里面输入任何合法的js语句
//页面层-自定义
/*layer.open({
  type: 1,
  title: false,
  closeBtn: 1,
  shadeClose: false,
  skin: 'yourclass',
  content: '自定义HTML内容'
});*/

	checkCookie();




	//show user name and password

});




/**
 *
 *
 * Author:
 *	zzw <zzw1986@gmail.com>
 *
 *
 * File: td_view.js
 * Create Date: 2013-9-23 8:29
 *
 *
 */



(function() {
	var Awifi_UI = function() {
		this.name = 'Awifi_UI';
		this.version = '1.0';
	}
	this.Awifi_UI = new Awifi_UI();
	Awifi_UI = this.Awifi_UI;

	Awifi_UI.captcha = {
		systemNo:'',
		host: '',
		//包含组件的div
		mainContain: '',
		//模板
		captchaTpl: '<div class="ui-row-90 awui-text-center" style="min-height: 28px"><span class="awui-red" id="error"></span></div><div class=awui-row-90><input class=awui-main-input id=username placeholder="请输入手机号" maxlength=11></div><div class=awui-row-90 id=captchaDiv><div class="awui-row-47 awui-left"><input class="awui-row-47 awui-main-input" placeholder=请输入验证码 id=picCaptcha maxlength=4></div><div class="awui-row-47 awui-right"><button type="button" class="awui-back-gray awui-gray awui-half-btn awui-text-center" id=smsCaptchaGet>获取验证码</button></div></div><div class="awui-row-90 awui-login-btngroup"><button class="awui-back-orange awui-white awui-half-btn awui-text-center" id=sure>确认</button></div>',
		captchaPicTpl: '<div class="ui-row-90 awui-text-center" style="min-height: 28px"><span class="awui-red" id="error"></span></div><div class=awui-row-90><input class=awui-main-input id=username placeholder="请输入手机号" maxlength=11></div><div class=awui-row-90 id=captchaDiv><div class="awui-row-47 awui-left"><input class="awui-row-47 awui-main-input" placeholder=请输入验证码 id=picCaptcha maxlength=4></div><div class="awui-row-47 awui-right awui-text-center"><img src=img/forget.png id=picCaptchaGet class="awui-captchaPic"></div></div><div class="awui-row-90 awui-login-btngroup"><button class="awui-back-orange awui-white awui-half-btn awui-text-center" id=sure>确认</button></div>',
		//请求地址
		url: {
			smsCaptchaUrl: '',
			picCaptchaUrl: '',
			sureUrl: ''
		},
		//计数
		captchaCutdownTime: 60,
		//参数
		params: {
			appid: '',
			timestamp: '',
			token: ''
		},
		captchaType: '',
		$main:null ,
		$username:null,
		$smsCaptcha:null,
		$picCaptcha:null,
		$loginBtn:null,
		$error:null,
		/**
		 * 初始化
		 * @param {Object} config
		 */
		init: function(config) {
			if (!config) {
				console.log("配置缺失");
				return false;
			}
			//JQuery对象
			if (config.mainContain.length>0) {//alert($("#login_form").length)
				this.mainContain = config.mainContain;
				console.log("mainContain:" + this.mainContain);
			} else {
				console.log("配置参数缺失,请指定mainContain（组件dom容器）");
				return false;
			}

			//portal参数
			if (config.params) {
				this.params = config.params;
			}

			if (config.systemNo) {
				this.systemNo = config.systemNo;
			}


			//请求host
			if (config.host) {
				this.host = config.host;
			}
			//url
			if (config.url) {
				this.url = config.url;
			}
			//验证码类型
			if (config.captchaType) {
				this.captchaType = config.captchaType;
			}
			//验证码倒计时
			this.captchaCutdownTime = typeof(config.captchaCutdownTime) == 'undefined' ? this.captchaCutdownTime : config.captchaCutdownTime;

			if (this.captchaType == 'pic') {
				//this.mainContain.html(this.captchaPicTpl);
				//this.picCaptchaClick();
				//this.getCpatchaClick();
			} else {
				//this.mainContain.html(this.captchaTpl);
				//this.getCaptcha();
			}

			this.$main = this.mainContain;
			if(this.mainContain.length==0){
				console.log("mainContain not exist");
			}
			var $main=this.$main;
			if($main.length==0){
				console.log("$main not exist");
			}
			this. $username = $main.find('.username');
			this.$smsCaptcha = $main.find('#smsCaptcha');
			this.$picCaptcha = $main.find('#picCaptcha');
			this.$loginBtn = $main.find('#loginBtn');
			this. $error = $main.find("#error");
			this.$picCaptchaGet=$main.find("#picCaptchaGet");
			this.$smsCaptchaGet=$main.find("#smsCaptchaGet");
			this.addEventListener();
		},

		initValid:function(){

		},
		/**
		 * 获取验证码
		 */
		initSmsCaptcha: function() {
			var that = this;
			var checker = that.checker;
			var $username =this.$username;
			var $error =this.$error;

			/*this.$smsCaptchaGet.on('click', function() {
				if ($(this).attr("disabled")) {
					return;
				}
				var username = $username.val() || '';
				if (checker.checkUserName(username)) {
					$error.text("请输入正确的帐号");
					return false;
				}

				var appid = params.appid || '';
				var timestamp = params.timestamp || '';
				var token = params.token || '';
				var data = {
					phone: username,
					appid: appid,
					timestamp: timestamp,
					token: token
				};
				that.getCaptchaAjax(data);
			});*/
		},
		picCaptchaClick: function() {
			console.log("picCaptchaClick");
			//TODO
			var that = this;
			var params = that.params;
			var phone =  new Date().getTime();
			var appid = params.appid || '';
			var timestamp = params.timestamp || '';
			var token = params.token || '';
			var data = {
				systemno: this.systemNo,
				/*appid: appid,
				timestamp: timestamp,*/
				sessionid: that.$main.find("#sessionid").val()
			};
			that.getPicCaptchaAjax(data);
		},
		smsCaptchaClick: function() {//TODO
			var that = this;
			var params = that.params;
			var phone = this.$username.val();
			var appid = params.appid || '';
			var timestamp = params.timestamp || '';
			var token = params.token || '';
			var data = {
				phone: phone,
				appid: appid,
				timestamp: timestamp,
				token: token,
				systemno:this.systemNo

			};
			if(this.checker.isBlank(phone)){
				this.alert("请先填写手机号");
				return;
			}
			that.captchaCutdown(this.$smsCaptchaGet);
			that.getSmsCaptchaAjax(data);
		},
		alert:function(str){
			this.$error.text(str);
		},
		/**
		 * 绑定事件
		 */
		addEventListener: function() {
			var that = this;
			var checker = this.checker;
			var params = this.params;
			if(this.$picCaptchaGet.length>0){
				this.$picCaptchaGet.on('click', function() {
					that.picCaptchaClick();
				});
				//显示验证码
				this.$picCaptchaGet.trigger("click");
			}else{
				console.log("$pciCaptchaGet not exist");
			}
			if(this.$smsCaptchaGet.length>0){
				this.$smsCaptchaGet.on('click', function() {
					that.smsCaptchaClick();
				});
			}else{
				console.log("$smsCaptchaGet not exist");
			}

			/*this.$loginBtn.on('click', function() {

				var username, captcha;
				username = that.$username.val() || '';
				captcha = that.$smsCaptcha.val() || '';

				//6位验证码验证

				if(that.$smsCaptcha.length>0){
					if(that.checker.checkCaptcha(captcha)){

					}

				}

				if (that.checker.checkCaptcha(captcha) || that.checker.checkUserName(username)) {
					that.alert("请输入正确的帐号或验证码");
					return false;
				}

				var data = {
					phone: username,
					code: captcha,
					appid: appid,
					timestamp: timestamp,
					token: token
				};
				that.sureAjax(data);
			});*/
		},

		/**
		 * 短信验证码请求
		 * @param {Object} data
		 */
		getPicCaptchaAjax: function(data) {
			var that = this;
			var type = this.captchaType;
			var url;
			url = this.url.picCaptchaUrl || '';
			url = this.host + url;
			$.ajax({
				url: url,
				data: data,
				type:'GET',
				dataType: 'JSONP',
				jsonp: 'callback',
				jsonpCallback:'getName',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				header: {
					'cache-control': 'no-cache'
				},
				success: function(data, textStatus, jqXHR) {
					if (data.r == 0 && that.$picCaptcha) {
						//that.$picCaptchaGet.prop("src",that.host+"/"+data.data.img);
						//that.$picCaptchaGet.find("img").prop("src",that.host+"/"+data.data.img+"?r="+Math.random());
						that.$picCaptchaGet.find("img").prop("src","data:image/png;base64,"+data.data.imgdata);
						//暂存token
						var $sessionid= that.$main.find("#sessionid");
						if($sessionid.length==0){
							$sessionid=$("<input type='hidden' id='sessionid' name='sessionid' />");
							that.$main.append($sessionid);

						}
						$sessionid.val(data.data.sessionid);
					}
				},
				error: function(XHR, textStatus, errorThrown) {}
			});
		},
		/**
		 * 验证码请求
		 * @param {Object} data
		 */
		getSmsCaptchaAjax: function(data) {
			var that = this;
			var type = this.captchaType;
			var url;
			url = this.url.smsCaptchaUrl || '';
			url = this.host + url;
			$.ajax({
				url: url,
				data: data,
				dataType: 'JSONP',
				type:'GET',
				jsonp: 'callback',
				jsonpCallback:'getName',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				header: {
					'cache-control': 'no-cache'
				},
				success: function(data, textStatus, jqXHR) {
					if (data.r == 0 ) {

					}else{
						that.alert(data.msg);
						console.log(data);
					}

				},
				error: function(XHR, textStatus, errorThrown) {}
			});
		},
		/*sureAjax: function(data) {
			var that = this;
			that.awui_loading.show();
			var url = this.url.sureUrl || '';
			url = this.host + url;
			$.ajax({
				url: url,
				data: data,
				dataType: 'JSONP',
				jsonp: 'callback',
				header: {
					'cache-control': 'no-cache'
				},
				success: function(datas) {
					console.log(datas);
				},
				error: function(XHR, textStatus, errorThrown) {},
				complete: function(XHR, textStatus) {
					that.awui_loading.hide();
				}
			});
		},*/
		/**
		 * 倒计时 传入按钮
		 * @param {Object} self
		 */
		captchaCutdown: function(self) {
			self.attr('disabled', true);
			self.text('发送中');
			var time = this.captchaCutdownTime || 60;
			var sI = setInterval(function() {
				time = time - 1;
				if (time > 0) {
					self.text(time + '秒后重试');
				} else {
					window.clearInterval(sI);
					self.text('重新获取');
					self.removeAttr("disabled");
				}
			}, 1000);
		},

		/**
		 * 参数验证
		 */
		checker: {
			isBlank:function(it){
				if(it==null || typeof it=='undefinded' || it==''){
					return true;
				}
				return null;
			},
			checkUserName: function(str) {
				var re = /^1\d{10}$/
				if (!re.test(str)) {
					return true;
				}
			},
			checkPassword: function(str) {
				var re = /^[0-9 | A-Z | a-z]{6,20}$/;
				if (!re.test(str)) {
					return true;
				}
			},
			checkCaptcha: function(str) {
				var re = /^[0-9]{4}$/;
				if (!re.test(str)) {
					return true;
				}
			},
			checkAuthCaptcha: function(str) {
				var re = /^[0-9]{6}$/;
				if (!re.test(str)) {
					return true;
				}
			}
		},
		/**
		 * 获取uri参数
		 * @param {Object} name
		 */
		getParam: function(name) {
			if (!name) {
				return '';
			}
			var search = document.location.search;
			var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
			var matcher = pattern.exec(search);
			var items = null;
			if (null != matcher) {
				try {
					items = decodeURIComponent(decodeURIComponent(matcher[1]));
				} catch (e) {
					try {
						items = decodeURIComponent(matcher[1]);
					} catch (e) {
						items = matcher[1];
					}
				}
			}
			return items;
		},
		/**
		 * 加载状态
		 */
		awui_loading: {
			loadingHtml: '<div class="loading" id="awui_loading" style="{{height}}">' +
				'<div class="loading-warp" style="{{style}}">' +
				'<div class="loading-content"><span class="loading-circle loading-circle-one"></span></div>' +
				'<div class="loading-content"><span class="loading-circle loading-circle-two"></span></div>' +
				'<div class="loading-content"><span class="loading-circle loading-circle-three"></span></div>' +
				'</div>' +
				'</div>',
			topPx: 0,
			heightPx: 0,
			getHeight: function() {
				return document.body.clientHeight + 60;
			},
			init: function() {
				var self = this;
				self.topPx = window.screen.availHeight / 2 + window.scrollY;
				self.heightPx = self.getHeight();
			},
			show: function(height) {
				var self = this;
				self.init();
				height = height || self.heightPx;
				var leftPx = document.body.scrollWidth / 2 - 70 / 2;
				var html = this.loadingHtml.replace('{{style}}', 'margin-top:' + self.topPx + 'px;left:' + leftPx + 'px').replace('{{height}}', 'height:' + self.heightPx + 'px');
				$('body').css({
					'overflow': 'hidden'
				}).append(html);
			},
			hide: function() {
				$('#awui_loading').remove();
				$('body').css({
					'overflow': ''
				})
			}
		}
	}

})();
$(document).ready(function(){return;
Awifi_UI.captcha.init({
	/**
	 * 必选
	 */
	systemNo:'calendar',
	mainContain: $("#login_form"), //容器 JQuery对象
	/**
	 * 以下可选
	 */
	/**
	 * 请求地址
	 */
	url: {
		smsCaptchaUrl: '/code/sms/get.json',
		picCaptchaUrl:'/code/img/get.json',
		sureUrl: '/code/valid.json'
	},
	/**
	 * host
	 */
	host:'http://127.0.0.1:8080/',
	/**
	 *  参数
	 */
	params: {

		appid: '1111',
		timestamp: '1111111111111',
		token: 'adcfgvftgf'
	},
	captchaType:'pic',// 'pic'表示图片验证码
	captchaCutdownTime: 60 //验证码倒计时时间 默认60秒
});
})

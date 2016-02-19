(function() {
	var Awifi_UI = function() {
		this.name = 'Awifi_UI';
		this.version = '1.0';
	}
	this.Awifi_UI = new Awifi_UI();
	Awifi_UI = this.Awifi_UI;

	Awifi_UI.captcha = {
		host: '',
		//包含组件的div
		mainContain: '',
		//模板
		captchaTpl: '<div class="ui-row-90 awui-text-center" style="min-height: 28px"><span class=awui-red id=error></span></div><div class=awui-row-90><input class=awui-main-input id=username placeholder=请输入手机号 maxlength=11></div><div class=awui-row-90 id=captchaDiv><div class="awui-row-47 awui-left"><input class="awui-row-47 awui-main-input" placeholder=请输入验证码 id=captcha maxlength=4></div><div class="awui-row-47 awui-right"><button class="awui-back-gray awui-gray awui-half-btn awui-text-center" id=getCaptcha>获取验证码</button></div></div><div class="awui-row-90 awui-login-btngroup"><button class="awui-back-orange awui-white awui-half-btn awui-text-center" id=sure>确认</button></div>',
		captchaPicTpl: '<div class="ui-row-90 awui-text-center" style="min-height: 28px"><span class=awui-red id=error></span></div><div class=awui-row-90><input class=awui-main-input id=username placeholder=请输入手机号 maxlength=11></div><div class=awui-row-90 id=captchaDiv><div class="awui-row-47 awui-left"><input class="awui-row-47 awui-main-input" placeholder=请输入验证码 id=captcha maxlength=4></div><div class="awui-row-47 awui-right awui-text-center"><img src=img/forget.png id=getCaptcha class="awui-captchaPic"></div></div><div class="awui-row-90 awui-login-btngroup"><button class="awui-back-orange awui-white awui-half-btn awui-text-center" id=sure>确认</button></div>',
		//请求地址
		url: {
			captchaUrl: '',
			captchaPicUrl: '',
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
			if (config.mainContain) {
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
				this.mainContain.html(this.captchaPicTpl);
				this.getPicCaptcha();
				this.getCpatchaClick();
			} else {
				this.mainContain.html(this.captchaTpl);
				this.getCaptcha();
			}
			this.sure();
		},
		addEventListener:function(){

		},
		initValid:function(){

		},
		/**
		 * 获取验证码 
		 */
		getCaptcha: function() {
			var that = this;
			var checker = that.checker;
			var $main = that.mainContain;
			var $username = $main.find('#username');
			var $getCaptcha = $main.find('#getCaptcha');
			var $error = $main.find("#error");
			var params = that.params;
			$getCaptcha.on('click', function() {
				if ($(this).attr("disabled")) {
					return;
				}
				var username = $username.val() || '';
				if (checker.checkUserName(username)) {
					$error.text("请输入正确的帐号");
					return false;
				}
				that.captchaCutdown($(this));
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
			});
		},
		getPicCaptcha: function() {
			var that = this;
			var $main = that.mainContain;
			var $getCaptcha = $main.find('#getCaptcha');
			$getCaptcha.on('click', function() {
				that.getCpatchaClick();
			});
		},
		getCpatchaClick: function() {
			var that = this;
			var params = that.params;
			var phone = returnCitySN.cip + new Date().getTime();
			var appid = params.appid || '';
			var timestamp = params.timestamp || '';
			var token = params.token || '';
			var data = {
				phone: phone,
				appid: appid,
				timestamp: timestamp,
				token: token
			};
			that.getCaptchaAjax(data);
		},
		/**
		 * 提交
		 */
		sure: function() {
			var that = this;
			var checker = that.checker;
			var $main = that.mainContain;
			var $username = $main.find('#username');
			var $captcha = $main.find('#captcha');
			var $sure = $main.find('#sure');
			var $error = $main.find("#error");
			var params = that.params;
			$sure.on('click', function() {
				var username, captcha, biaozhi;
				username = $username.val() || '';
				captcha = $captcha.val() || '';
				biaozhi = that.params.biaozhi || '';
				//6位验证码验证
				if (checker.checkCaptcha(captcha) || checker.checkUserName(username)) {
					$error.text("请输入正确的帐号或验证码");
					return false;
				}
				var appid = params.appid || '';
				var timestamp = params.timestamp || '';
				var token = params.token || '';
				var data = {
					phone: username,
					code: captcha,
					appid: appid,
					timestamp: timestamp,
					token: token
				};
				that.sureAjax(data);
			});
		},

		/**
		 * 验证码请求
		 * @param {Object} data
		 */
		getCaptchaAjax: function(data) {
			var that = this;
			var type = this.captchaType;
			var url;
			if (type == 'pic') {
				url = this.url.captchaPicUrl || '';
			} else {
				url = this.url.captchaUrl || '';
			}
			url = this.host + url;
			$.ajax({
				url: url,
				data: data,
				dataType: 'JSONP',
				jsonp: 'callback',
				header: {
					'cache-control': 'no-cache'
				},
				success: function(data, textStatus, jqXHR) {
				
					if (data.r == 0 && that.captchaType == 'pic') {
						var a = $(".awui-main").find("#getCaptcha");
						$(".awui-main").find("#getCaptcha").prop("src",that.host+"/"+data.data.img);
					}

				},
				error: function(XHR, textStatus, errorThrown) {}
			});
		},

		sureAjax: function(data) {
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
		},
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
				if (time != 0) {
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
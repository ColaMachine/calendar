var _Entity_=function(state,divId){function mobileStyle(str){var re=/^1\d{10}$/;if(!re.test(str))return!0}function captchaStyle(str){var re=/^[0-9]{4}$/;if(!re.test(str))return!0}function tip(msg){console.log(msg)}function success_jsonpCallback(){}function imgLoad2(){var img=document.createElement("img");img.style="display:none;",img.src=REQUEST_IMG+"?"+(new Date).getTime(),EventUtil.addHandler(img,"error",function(){console.log("你没连网"),img=null,imgLoad2()}),EventUtil.addHandler(img,"load",function(){$("#linkMsg").text("已联网"),$("#showCompleteFrame").attr("src","https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/css/super_min_9e70e43e.css")})}function render(){return ReactDOM.render(React.createElement(EntityReact,null),document.getElementById(divId))}!function($){$.fn.yxMobileSlider=function(settings){var defaultSettings={width:"100%",height:"239px",during:3e3,speed:20};return settings=$.extend(!0,{},defaultSettings,settings),this.each(function(){function autoMove(){timer=setInterval(doMove,s.during)}function stopMove(){clearInterval(timer)}function doMove(){iCurr=iCurr>=num-1?0:iCurr+1;var a=oLi.width()*iCurr;doAnimate(-a),oFocus.eq(iCurr).addClass("current").siblings().removeClass("current")}function bindTochuEvent(){oMover.get(0).addEventListener("touchstart",touchStartFunc,!1),oMover.get(0).addEventListener("touchmove",touchMoveFunc,!1),oMover.get(0).addEventListener("touchend",touchEndFunc,!1)}function touchPos(e){for(var touch,tagX,tagY,touches=e.changedTouches,l=touches.length,i=0;i<l;i++)touch=touches[i],tagX=touch.clientX,tagY=touch.clientY;return oPosition.x=tagX,oPosition.y=tagY,oPosition}function touchStartFunc(e){stopMove(),touchPos(e),startX=oPosition.x,startY=oPosition.y,temPos=oMover.position().left}function touchMoveFunc(e){touchPos(e);var moveX=oPosition.x-startX,moveY=oPosition.y-startY;Math.abs(moveY)<Math.abs(moveX)&&(e.preventDefault(),oMover.css({left:temPos+moveX}))}function touchEndFunc(e){touchPos(e);var moveX=oPosition.x-startX,moveY=oPosition.y-startY;if(Math.abs(moveY)<Math.abs(moveX)){if(moveX>0)if(iCurr--,iCurr>=0){var moveX=iCurr*moveWidth;doAnimate(-moveX,autoMove)}else doAnimate(0,autoMove),iCurr=0;else if(iCurr++,iCurr<num&&iCurr>=0){var moveX=iCurr*moveWidth;doAnimate(-moveX,autoMove)}else iCurr=num-1,doAnimate(-(num-1)*moveWidth,autoMove);oFocus.eq(iCurr).addClass("current").siblings().removeClass("current")}}function mobileSettings(){moveWidth=$(window).width();var iScale=$(window).width()/s.width;_this.height(s.height*iScale).width($(window).width()),oMover.css({left:-iCurr*moveWidth})}function doAnimate(iTarget,fn){oMover.stop().animate({left:iTarget},_this.speed,function(){fn&&fn()})}function isMobile(){return!(!navigator.userAgent.match(/Android/i)&&navigator.userAgent.indexOf("iPhone")==-1&&navigator.userAgent.indexOf("iPod")==-1&&navigator.userAgent.indexOf("iPad")==-1)}var temPos,_this=$(this),s=settings,startX=0,startY=0,iCurr=0,timer=null,oMover=$("ul",_this),oLi=$("li",oMover),num=oLi.length,oPosition={},moveWidth=s.width;_this.width(s.width).height(s.height).css({position:"relative",overflow:"hidden",margin:"0 auto"}),oMover.css({position:"absolute",left:0}),oLi.css({"float":"left",display:"inline"}),$("img",oLi).css({width:document.body.clientWidth+"px",height:.56*document.body.clientWidth+"px"}),console.log("slider start"),_this.append('<div class="focus"><div></div></div>');for(var oFocusContainer=$(".focus"),i=0;i<num;i++)$("div",oFocusContainer).append("<span></span>");var oFocus=$("span",oFocusContainer);oFocusContainer.css({minHeight:2*_this.find("span").height(),position:"absolute",bottom:0}),$("span",oFocusContainer).css({display:"block","float":"left",cursor:"pointer"}),$("div",oFocusContainer).width(oFocus.outerWidth(!0)*num).css({position:"absolute",right:_this.width()/2-10*num-10,top:"50%",marginTop:-$(this).find("span").width()/2}),oFocus.first().addClass("current"),$(window).on("resize load",function(){isMobile()&&(mobileSettings(),bindTochuEvent()),oLi.width(_this.width()).height(_this.height()),oMover.width(num*oLi.width()),oFocusContainer.width(_this.width()).height(.15*_this.height()).css({zIndex:2}),_this.fadeIn(300)}),autoMove(),isMobile()||oFocus.hover(function(){iCurr=$(this).index()-1,stopMove(),doMove()},function(){autoMove()})})}}(jQuery);var REQUEST_IMG="http://www.baidu.com/img/bdlogo.gif",STATION_TYPE="beta",PATH="",JSCSS_PATH="",UPLOAD_PATH="",IMG_PATH="";"beta"==STATION_TYPE?(JSCSS_PATH="http://beta-msp-static.51awifi.com/V1/",UPLOAD_PATH="http://beta-msp-img.51awifi.com/upload/",IMG_PATH="http://beta-msp-img.51awifi.com/V1/"):"pre"==STATION_TYPE?(JSCSS_PATH="http://pre-msp-static.51awifi.com/V1/",UPLOAD_PATH="http://pre-msp-img.51awifi.com/upload/",IMG_PATH="http://pre-msp-img.51awifi.com/V1/"):"pro"==STATION_TYPE?(JSCSS_PATH="http://msp-static.51awifi.com/V1/",UPLOAD_PATH="http://msp-img.51awifi.com/upload/",IMG_PATH="http://msp-img.51awifi.com/V1/"):(UPLOAD_PATH="/statics/upload/",JSCSS_PATH="/statics/static/V1/",IMG_PATH="/statics/static/V1/");var PATH="http://beta-msp.51awifi.com",testMerhcantId="5590";!function(window){var loadingHtml='<div class="loading" id="loading" style="{{height}}"><div class="loading-warp" style="{{style}}"><div class="loading-content"><span class="loading-circle loading-circle-one"></span></div><div class="loading-content"><span class="loading-circle loading-circle-two"></span></div><div class="loading-content"><span class="loading-circle loading-circle-three"></span></div></div></div>',loading={topPx:0,heightPx:0,getHeight:function(){return document.body.clientHeight+60},init:function(){var self=this;self.topPx=window.screen.availHeight/2+window.scrollY,self.heightPx=self.getHeight()},show:function(height){var self=this;self.init(),height=height||self.heightPx;var leftPx=document.body.scrollWidth/2-35,html=loadingHtml.replace("{{style}}","margin-top:"+self.topPx+"px;left:"+leftPx+"px").replace("{{height}}","height:"+self.heightPx+"px");$("body").css({overflow:"hidden"}).append(html)},hide:function(){$("#loading").remove(),$("body").css({overflow:""})}};window.loading=window.loading||loading;var Dialogue={modal:function(html,top,height,fn){var mask_html='<div class="mask" > </div>',modal_html='<div class="modal" style="z-index:30000"><div class="content row-10 " ></div><div class="row-6 center-text"><button class="back-orange" >确定</button></div></div>',msg_html='<div class="row-9 center-text" style="min-height:50px"></div>',m={};return m.showMask=function(){0==$(".mask").length&&$("body").append(mask_html),$(".mask").show()},m.hideMask=function(){$(".mask").hide()},m.showModal=function(){this.showMask(),0==$(".modal").length&&$("body").append(modal_html),top&&""!=top&&$(".modal").css("top",top),height&&""!=height&&$(".modal").css("height",height),$(".modal").find(".content").html(html||msg_html),$(".modal").show(),this.distroy()},m.hideModal=function(){this.hideMask(),$(".modal").find(".content").html(""),$(".modal").hide()},m.distroy=function(){var that=this;$(".modal").find("button").on("click",function(){that.hideModal(),"function"==typeof fn&&(fn(),fn=null)})},m},tip:function(msg){}};window.Dialogue=window.Dialogue||Dialogue}(window);var Ajax={get:function(url,data,callback){this.AjaxFun(url,data,callback,{type:"GET"})},getJSON:function(url,data,callback){this.AjaxFun(url,data,callback,{type:"GET",dataType:"JSON"})},getJSONP:function(url,data,callback){this.AjaxFun(url,data,callback,{type:"GET",dataType:"jsonp",jsonp:"callback",jsonpCallback:"success_jsonpCallback"})},post:function(url,data,callback){this.AjaxFun(url,data,callback,{type:"POST"})},AjaxFun:function(url,inputData,callback,options,callbackOnError){window.location.href;options=options||{},url+=url.indexOf("?")!=-1?"&r="+Math.floor(1001*Math.random()):"?r="+Math.floor(1001*Math.random()),options.url=url,options.type=options.type||"POST",options.data=inputData,options.contentType="application/x-www-form-urlencoded; charset=utf-8","undifined"==typeof options.async&&(options.async=!1);options.inputData;options.success=function(outputData){"504"==outputData.r&&(window.location=PATH+"/login.htm"),"function"==typeof callback&&callback(outputData)},options.error=function(jqXHR,textStatus,errorThrown){var responseText=jqXHR.responseText||"";return(500==jqXHR.status||1e3==jqXHR.status)&&responseText.indexOf("dwr.engine.http.1000")>=0?void top.location.replace(acwsContext+"/acwsui/pages/logout.htm"):void("function"==typeof callbackOnError?callbackOnError(errorThrown):alert("参数不是function"))},delete options.inputData,$.ajax(options)}};!function(window){var htmlDecode=function(value){return value&&("&nbsp;"===value||"&#160;"===value||1===value.length&&160===value.charCodeAt(0))?"":value?String(value).replace(/&gt;/g,">").replace(/&lt;/g,"<").replace(/&quot;/g,'"').replace(/&amp;/g,"&"):value},htmlEncode=function(value){return value?String(value).replace(/&/g,"&amp;").replace(/\"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;"):value},setCookie=function(name,value,hours,path){var name=escape(name),value=escape(value),expires=new Date;expires.setTime(expires.getTime()+36e5*hours),path=""==path?"":";path="+path;var _expires="string"==typeof hours?"":";expires="+expires.toUTCString();document.cookie=name+"="+value+_expires+path},getCookieValue=function(name){var value="";switch(name){case"MS_merchantId":value=globalParam.merId;break;case"merId":value=globalParam.merId;break;case"user_phone":value=globalParam.user_phone;break;case"dev_id":value=globalParam.dev_id||globalParam.devId;break;case"user_mac":value=globalParam.user_mac;break;case"url":value=globalParam.url;break;case"login_type":value=globalParam.login_type;break;case"region":value=globalParam.region;break;case"belongTo":value=globalParam.belongTo;break;case"model":value=globalParam.model;break;case"trafficLimit":value=globalParam.trafficLimit;break;case"ssid":value=globalParam.ssid;break;case"devName":value=globalParam.devName;break;case"corp":value=globalParam.corp;break;case"MS_deviceId":value=globalParam.devId||globalParam.dev_id;break;case"ap_mac":value=globalParam.mac;break;case"MS_terMac":value=globalParam.user_mac;break;case"MS_terMac":value=globalParam.user_mac;break;case"gw_address":value=globalParam.gw_address;break;case"gw_url":value=globalParam.url;break;case"host":value=globalParam.host;break;case"gw_port":value=globalParam.gw_port;break;default:value=globalParam[name]}var name=escape(name),allcookies=document.cookie;name+="=";var pos=allcookies.indexOf(name);if(pos!=-1){var start=pos+name.length,end=allcookies.indexOf(";",start);end==-1&&(end=allcookies.length);var value=allcookies.substring(start,end);return unescape(value)||value}return value};window.setCookie=window.setCookie||setCookie,window.getCookieValue=window.getCookieValue||getCookieValue,window.htmlDecode=window.htmlDecode||htmlDecode,window.htmlEncode=window.htmlEncode||htmlEncode}(window),Function.prototype.Apply=function(thisObj){var _method=this;return function(data){return _method.apply(thisObj,[data])}};var EventUtil={addHandler:function(element,type,handler){element.addEventListener?element.addEventListener(type,handler,!1):element.attachEvent?element.attachEvent("on"+type,handler):element["on"+type]=handler},removeHandler:function(element,type,handler){element.removeEventListener?element.removeEventListener(type,handler,!1):element.detachEvent?element.detachEvent("on"+type,handler):element["on"+type]=null}},_state=state||{login_frame_show:"none",fast_login_show:"none",get_free_pkg_show:"none",left_time_show:"none"},HeanderReact=React.createClass({displayName:"HeanderReact",render:function(){return React.createElement("header",{className:"indexWrap header back-blue"},"         ",React.createElement("nav",null,React.createElement("a",{id:"login",style:{cursor:"pointer"}},React.createElement("div",{id:"loginBtn",className:"header-left center-text row-3 left small-text"},React.createElement("img",{className:"header-left-img",src:"http://msp-img.51awifi.com/V1/img/header/wifi.png"}),React.createElement("span",{className:"header-left-span right-text",id:"linkMsg"},"请连网")),"             "),React.createElement("div",{className:"center-text row-4 left normal-text",style:{whiteSpace:"nowrap",textOverflow:"ellipsis",overflow:"hidden"},id:"merchantname"},"Yxy"),React.createElement("a",{href:"#/userCenter",id:"userCenter"},"                 ",React.createElement("div",{className:"header-right center-text row-3 left small-text"},React.createElement("img",{className:"header-right-img right",src:"http://msp-img.51awifi.com/V1/img/header/information.png"})))))}}),LogoReact=React.createClass({displayName:"LogoReact",render:function(){return React.createElement("header",{className:"loginWrap",style:{display:"none"}},React.createElement("a",{href:"#/"},React.createElement("div",{className:"loginlogo row-10 back-blue center-text"},React.createElement("img",{"class":"row-5-2 loginlogo-icon",src:"http://msp-img.51awifi.com/V1/img/header/wifiLogo.png",alt:"logo"}),React.createElement("span",{className:"logoWord"},"爱WiFi欢迎您"))))}}),LoginReact=React.createClass({displayName:"LoginReact",render:function(){return React.createElement("div",{className:"loginWrap container regcontainer",style:{display:"none"}},React.createElement("div",{className:"row-9 loginErr"},React.createElement("span",{className:"errorMsg"})),React.createElement("div",{className:"row-9"},React.createElement("input",{type:"text",className:"logininput",name:"username",placeholder:"输入手机号",maxLength:"11"})),React.createElement("div",{className:"row-9 captcha"},React.createElement("input",{className:"row-4-7",type:"text",placeholder:"输入验证码",name:"captcha",maxLength:"4"}),React.createElement("button",{className:"row-4-7 right clr-fix back-gray gray-text",onClick:this.captchaClick},"获取验证码")),React.createElement("div",{className:"row-9 loginbutton"},React.createElement("button",{className:"back-orange",id:"login",onClick:this.login},"登录")))}}),RollingMsgReact=React.createClass({displayName:"RollingMsgReact",render:function(){return React.createElement("div",{className:"row-10 activeDiv",style:{backgroundImage:" url(http://msp-img.51awifi.com/V1/img/index/activeBack.png)"}},React.createElement("div",{className:" text-indent row-8 left small-text",style:{height:"30px",lineHeight:"30px"}},React.createElement("ul",{id:"activeInfo",style:{margin:0,padding:0,listStyle:"none"}},React.createElement("li",{id:"activeInfoLi"},React.createElement("marquee",null,"动态: ",React.createElement("span",null,"恭喜AWIFI园区开通 !"),"    动态: ",React.createElement("span",null,"全场免费！"),"   动态:",React.createElement("span",null,"免费是假的！"),"   动态: ",React.createElement("span",null,"当然要收费！"),"    动态:",React.createElement("span",null,"其实真不贵！"))))),React.createElement("div",{className:"right-text row-2 left small-text",style:{height:"30px",lineHeight:"30px"},id:"closeActive"},React.createElement("div",{style:{height:"20px",lineHeight:"20px",marginTop:"3px"}},React.createElement("img",{src:"http://msp-img.51awifi.com/V1/img/index/activeClose.png",style:{width:"20px",height:"20px",paddingRight:"1rem",verticalAlign:"middle"}}))))}}),RollingImageReact=React.createClass({displayName:"RollingImageReact",render:function(){return React.createElement("div",{className:"carousel clr-fix"},React.createElement("ul",{className:"carousel-ul",id:"carousel-ul"}))}}),AppsReact=React.createClass({displayName:"AppsReact",render:function(){return React.createElement("div",{className:"app clr-fix"},React.createElement("div",{className:"row-2 left center-text"},React.createElement("div",{className:"app-icon row-8"},React.createElement("a",{href:"javascript:void(0)"},React.createElement("img",{className:"row-9",src:"http://msp-img.51awifi.com/V1/img/app/lunch.png"})),React.createElement("div",{className:"small-text row-10"},"点餐"))),React.createElement("div",{className:"row-2 left center-text"},React.createElement("div",{className:"app-icon row-8"},React.createElement("a",{href:"javascript:void(0)"},React.createElement("img",{"class":"row-9",src:"http://msp-img.51awifi.com/V1/img/app/services.png"})),React.createElement("div",{className:"small-text row-10"},"服务"))),React.createElement("div",{className:"row-2 left center-text"},React.createElement("div",{className:"app-icon row-8"},React.createElement("a",{href:"javascript:void(0)"},React.createElement("img",{"class":"row-9",src:"http://msp-img.51awifi.com/V1/img/app/order.png"})),React.createElement("div",{className:"small-text row-10"},"买单"))),React.createElement("div",{className:"row-2 left center-text"},React.createElement("div",{className:"app-icon row-8"},React.createElement("a",{href:"javascript:void(0)"},React.createElement("img",{"class":"row-9",src:"http://msp-img.51awifi.com/V1/img/app/cup.png"})),React.createElement("div",{className:"small-text row-10"},"续杯"))),React.createElement("div",{className:"row-2 left center-text"},React.createElement("div",{className:"app-icon row-8"},React.createElement("a",{href:"javascript:void(0)"},React.createElement("img",{"class":"row-9",src:"http://msp-img.51awifi.com/V1/img/app/share.png"})),React.createElement("div",{className:"small-text row-10"},"分享"))))}}),fastLoginReact=React.createClass({displayName:"fastLoginReact",render:function(){return React.createElement("div",{className:"row-10",id:"fastLogin",style:{display:"none"}},React.createElement("div",{className:"row-9 "},React.createElement("div",{className:"row-2 left"},React.createElement("img",{className:"avatar",src:"http://msp-img.51awifi.com/V1/img/index/shalouon.png"})),React.createElement("div",{className:"row-4 left small-text "},React.createElement("div",{className:"text-left"},"当前账号"),React.createElement("div",null,React.createElement("span",{id:"fastLoginAccount"}),React.createElement("a",{className:"link",id:"switch_account"},"切换"))),React.createElement("div",{className:"row-4 left right-text"},React.createElement("div",{className:"row-9"},React.createElement("button",{type:"button",id:"fastLoginBtn"},"一键登录")))))}}),getFreePkg=React.createClass({displayName:"getFreePkg",render:function(){return React.createElement("div",{className:"row-10",id:"getFreePkgWrap",style:{display:"none"}},React.createElement("div",{className:"row-9 "},React.createElement("div",{className:"row-9"},React.createElement("button",{id:"getFreeBtn",type:"button",className:"back-orange"},"领取免费包"))))}}),BuyReact=React.createClass({displayName:"BuyReact",render:function(){return React.createElement("div",{className:"row-10",id:"buydivwrap",style:{display:"none"}},React.createElement("div",{className:"row-9 "},React.createElement("div",{className:"row-2 left",id:"shalou"},React.createElement("img",{className:"avatar",src:"http://msp-img.51awifi.com/V1/img/index/shalouon.png"})),React.createElement("div",{className:"row-4 left small-text",id:"leftDiv"},React.createElement("div",{id:"leftTime"},"您暂时无法上网"),React.createElement("div",{className:"",id:"leftTimeTitle"},"点击请连网登录")),React.createElement("div",{className:"row-4 left right-text",id:"buyDiv"},React.createElement("div",{className:"row-9"},React.createElement("button",{id:"buy",type:"button",className:"back-orange"},"时长购买")))))}}),EntityReact=React.createClass({displayName:"EntityReact",getInitialState:function(){return _state},render:function(){return React.createElement("div",{style:{position:"relative"}},React.createElement(LogoReact,null),React.createElement(LoginReact,null),React.createElement(HeanderReact,null),React.createElement("div",{className:"indexWrap container"},React.createElement(RollingMsgReact,null),React.createElement(RollingImageReact,null),React.createElement(AppsReact,null),React.createElement("fastLoginReact",null),React.createElement("getFreePkg",null),React.createElement(BuyReact,null)))},componentDidMount:function(){this.request(),this.addEventListener()},getInitialState:function(){return _state},afterLoginFlag:!1,request:function(){window.indexSI=window.indexSI||[],window.globalParam={};var msp_url=window.location.search;if(msp_url&&msp_url.length>0){msp_url=msp_url.substr(1);for(var ary=msp_url.split("&"),i=0;i<ary.length;i++){var str=ary[i],ele=str.split("=");ele.length>=2&&(globalParam[ele[0]]=ele[1])}}var dev_info=globalParam.dev_info,devInfo=eval("("+decodeURI(dev_info)+")");for(var i in devInfo)globalParam[i]=devInfo[i],console.log(i+":"+globalParam[i]);var StringUtil={};StringUtil.isNull=function(it){return null==it||"undefinded"==typeof it||""==it||null},StringUtil.isBlank=function(it){return null==it||"undefinded"==typeof it||""==it||null},window.StringUtil=StringUtil,window.afterLoginFlag=!1,window.loading={show:function(){},hide:function(){}}},dqurl:"",noTime:"",getDqUrlRequest:function(result){var dqurl,that=this;if(console.log("getDqUrlRequest:++"),1==result.r){var data=result.data;dqurl=data.deviceurl||"",dqurl=""==dqurl||"error"==dqurl?"":dqurl+"&link_phone="+data.deviceusername+"&code_number="+data.deviceinfo+"&yxgh="+data.devicemerid,console.log(dqurl),that.dqurl=dqurl,globalParam.dqUrl=dqurl;var noTime=$("#buy").attr("name");if($("#buy").attr("disabled"))return;""!=dqurl?"noTime"==noTime?that.buy(dqurl):window.location.href=dqurl:(alert("未获取到宽带账号"),that.goLogin())}else{console.log("未登陆");var html='<div class="row-9 center-text" >支付服务失败，请重新登录！</div>';Dialogue.modal(html,"25%","180px",function(){that.goLogin()}).showModal()}},getFreeRequest:function(result){var that=this;if(1==result.r){var html='<div class="row-9 center-text" >领取成功</div>';Dialogue.modal(html,"25%","180px",function(){var authData={merchantId:getCookieValue("MS_merchantId"),deviceId:getCookieValue("MS_deviceId"),apMac:getCookieValue("ap_mac"),terMac:getCookieValue("MS_terMac"),userMac:getCookieValue("MS_terMac"),callback:"callback",username:getCookieValue("MS_telephone"),password:getCookieValue("MS_pcode")};afterLoginFlag=!0,that.auther(authData)}).showModal(),$("#getFreeBtn").hide(),$("#getFreePkgWrap").hide(),$("#buy").show(),this.setState({login_frame_show:"block",get_free_pkg_show:"none",fast_login_show:"none",left_time_show:"none"})}else if(result.msg){var html='<div class="row-9 center-text">'+result.msg+"</div>";Dialogue.modal(html,"25%","180px").showModal()}},showLogin:function(){this.setState({login_frame_show:"block"})},hideLogin:function(){this.setState({login_frame_show:"none"})},showFreePkg:function(){this.setState({get_free_pkg_show:"block"})},hideFreePkg:function(){this.setState({get_free_pkg_show:"none"})},leftTimeShow:function(user_cutoff){var now_time,cutoffdate=0;cutoffdate=user_cutoff?user_cutoff.cutoffdate||0:0,now_time=(new Date).getTime();var leftTime=cutoffdate-now_time>0?cutoffdate-now_time:0;if(leftTime<=36e5)if(leftTime<=3e3){$("#buy").attr("name","noTime");var html='<div class="row-9 center-text" >您已无上网时长</div>';Dialogue.modal(html,"25%","200px","").showModal()}else{var html='<div class="row-9 center-text "  >您的上网时长已不足一小时</div><div class="row-9 center-text " >请尽快购买时长</div>';Ajax.getJSONP(PATH+"/ms/sms/send",{mobile:getCookieValue("MS_telephone"),type:"ms_warn"},function(){}),Dialogue.modal(html,"25%","200px","").showModal()}if(window.indexSI.length)for(var i in window.indexSI)clearInterval(window.indexSI[i]);var indexSI=setInterval(function(){var _html="";if(leftTime<=0){if(window.indexSI.length)for(var i in window.indexSI)clearInterval(window.indexSI[i]);_html="<span>您暂时无法上网</span>",$("#leftTimeTitle").html("请购买上网时长")}else{$("#leftTimeTitle").html("剩余上网时间");var d=Math.floor(leftTime/1e3/60/60/24),h=Math.floor(leftTime/1e3/60/60%24),m=Math.floor(leftTime/1e3/60%60),s=Math.floor(leftTime/1e3%60);h=h<10?"0"+h:h,m=m<10?"0"+m:m,s=s<10?"0"+s:s,_html="<span>"+d+"天"+h+":"+m+":"+s+"</span>"}$("#leftTime").html(_html),leftTime-=1e3},1e3);window.indexSI.push(indexSI)},indexRequest:function(result){loading.hide();var that=this,cutoffdate,now_time,leftTime,topPicList,merchant,accUname,notice,picHTML="";if(1==result.r&&result.data){if(topPicList=result.data.topPicList,merchant=result.data.merchant,accUname=result.data.account,notice=result.data.notice,merchant){if(merchant.merchantname){var merchantname=merchant.merchantname;$("#merchantname").html(merchantname)}if(merchant.remarks){var info=htmlDecode(merchant.remarks);info=htmlEncode(info),$("#remarks").html(info)}}var html="<div>客服热线：10000 </div><div>报障请提供宽带账号："+(accUname||"")+"</div>";if($("#accUname").html(html),topPicList&&topPicList.length<1)picHTML+='<li class="carousel-ul-li" ><a href="javascript:void(0)"><img class="carousel-img row-10" src="'+IMG_PATH+'img/carousel/ad1.png"  ></a></li>';else for(var i=0;i<topPicList.length;i++){var img=topPicList[i].picpath||"";""!=img&&(img=UPLOAD_PATH+""+img),picHTML+='<li class="carousel-ul-li" ><a href="javascript:void(0)"><img  class="carousel-img row-10" src="'+img+'"  /></a></li>'}if(notice){var notices=eval("("+notice+")"),noticeHtml="<marquee direction=left>";for(var i in notices){var noticeTitleInfo=notices[i].noticeTitle||"";noticeTitleInfo=htmlDecode(noticeTitleInfo),noticeTitleInfo=htmlEncode(noticeTitleInfo);var noticeTitle=noticeTitleInfo,noticeTextInfo=notices[i].noticeText||"";noticeTextInfo=htmlDecode(noticeTextInfo),noticeTextInfo=htmlEncode(noticeTextInfo);var noticeText=noticeTextInfo;noticeHtml+="&nbsp; &nbsp;&nbsp;<span>"+noticeTitle+"</span> &nbsp;<span>"+noticeText+"</span> "}noticeHtml+="</marquee>",$("#activeInfoLi").html(noticeHtml)}}else picHTML='<li class="carousel-ul-li" ><a href="javascript:void(0)"><img class="carousel-img row-10" src="'+IMG_PATH+'img/carousel/ad1.png"  ></a></li>';$("#carousel-ul").html(picHTML);var width=document.body.offsetWidth,height=9*width/16;$(".carousel").show().yxMobileSlider({height:height+"px"}),$(window).resize()},initUserCallBack:function(result){loading.hide(),console.log("initUserCallBack");var cutoffdate,now_time,leftTime,that=this;if(1==result.r&&result.data&&afterLoginFlag){if(result.data.user)if(console.log("已登录"),$("#linkMsg").text("已登录"),this.setState({fast_login_show:"none",left_time_show:"block"}),cutoffdate=result.data.user_cutoff?result.data.user_cutoff.cutoffdate||0:0,now_time=result.data.now_date||0,leftTime=cutoffdate-now_time>0?cutoffdate-now_time:0,(leftTime>0||result.data.isVipUser)&&(console.log("发现有时间"),afterLoginFlag&&console.log("不是刚登陆过，不触发联网")),result.data.isVipUser)$("#buy").attr("disabled","disabled"),$("#buy").removeClass("back-orange"),$("#buy").addClass("back-gray"),$("#shalou").hide(),$("#leftDiv").addClass("row-5"),$("#buyDiv").addClass("row-5"),$("#leftTime").html("<span style='font-size:1.7rem;font-weight:500'>电信尊贵客户</span>"),$("#leftTimeTitle").html("<span  >无需购买时长</span>");else{var free_pkg=result.data.free_pkg_get||"";if("ok"==free_pkg&&this.setState({get_free_pkg_show:"block"}),result.data.user){if(leftTime<=36e5)if(leftTime<=3e3){$("#buy").attr("name","noTime");var html='<div class="row-9 center-text" >您已无上网时长</div>';Dialogue.modal(html,"25%","200px","").showModal()}else{var html='<div class="row-9 center-text "  >您的上网时长已不足一小时</div><div class="row-9 center-text " >请尽快购买时长</div>';Ajax.getJSONP(PATH+"/ms/sms/send",{mobile:getCookieValue("MS_telephone"),type:"ms_warn"},function(){}),Dialogue.modal(html,"25%","200px","").showModal()}var indexSI=setInterval(function(){var _html="";if(leftTime<=0){if(window.indexSI.length)for(var i in window.indexSI)clearInterval(window.indexSI[i]);_html="<span>您暂时无法上网</span>",$("#leftTimeTitle").html("请购买上网时长")}else{$("#leftTimeTitle").html("剩余上网时间");var d=Math.floor(leftTime/1e3/60/60/24),h=Math.floor(leftTime/1e3/60/60%24),m=Math.floor(leftTime/1e3/60%60),s=Math.floor(leftTime/1e3%60);h=h<10?"0"+h:h,m=m<10?"0"+m:m,s=s<10?"0"+s:s,_html="<span>"+d+"天"+h+":"+m+":"+s+"</span>"}$("#leftTime").html(_html),leftTime-=1e3},1e3);window.indexSI.push(indexSI)}}}else{console.log("未登录");var user_phone=getCookieValue("user_phone"),login_type=getCookieValue("login_type");if(console.log("user_phone:"+user_phone+"login_type:"+login_type),login_type&&"authed"==login_type){console.log("authed");var re=/^1\d{10}$/;user_phone&&re.test(user_phone)&&($("#fastLoginAccount").text(user_phone),this.setState({get_free_pkg_show:"none",fast_login_show:"block",left_time_show:"none"}),$("#switch_account").click(function(){that.goLogin()}),$("#fastLoginBtn").click(function(){var username=($(this),user_phone);setCookie("MS_telephone",username,24,"/"),loading.show(),console.log("开始一键登录"),Ajax.getJSONP(PATH+"/ms/login/save",{username:getCookieValue("MS_telephone"),captcha:"1234",deviceId:getCookieValue("MS_deviceId"),apMac:getCookieValue("ap_mac"),merchantId:getCookieValue("MS_merchantId"),terMac:getCookieValue("MS_terMac"),userMac:getCookieValue("MS_terMac"),authed:!0,callback:"callback"},that.afterLogin.Apply(that))}))}else $("#fastLogin").hide(),$("#buydivwrap").show();console.log("未登陆"),console.log("获取用户信息失败/或者没有用户登录")}},addEventListener:function(){if(window.indexSI.length)for(var i in window.indexSI)clearInterval(window.indexSI[i]);var that=this;$("#loginBtn").click(function(){that.goLogin()});var devInfo=globalParam.dev_info||"{}";devInfo=decodeURI(devInfo),devInfo=eval("("+devInfo+")");var host=globalParam.host||"";""!=host&&setCookie("host",host,2376,"/");var deviceId=globalParam.dev_id||"";""!=deviceId&&setCookie("MS_deviceId",deviceId,2376,"/");var gw_address=globalParam.gw_address||"";""!=gw_address&&setCookie("gw_address",gw_address,2376,"/");var gw_url=globalParam.url||"";""!=gw_url&&setCookie("gw_url",gw_url,2376,"/");var gw_port=globalParam.gw_port||"";""!=gw_port&&setCookie("gw_port",gw_port,2376,"/");var terMac=globalParam.user_mac||"";""!=terMac&&setCookie("MS_terMac",terMac,2376,"/");var apmacTHREE=globalParam.ap_mac||"",apmacFOUR=devInfo.mac||"",ap_mac=""!=apmacFOUR?apmacFOUR:apmacTHREE;""!=ap_mac&&setCookie("ap_mac",ap_mac,2376,"/");var meridTHREE=globalParam.merchantId||"",meridFOUR=devInfo.merId||"",merchantId=""!=meridFOUR?meridFOUR:meridTHREE;""!=merchantId&&setCookie("MS_merchantId",merchantId,2376,"/");var portal_type;portal_type=globalParam.portal_type&""!=globalParam.portal_type?globalParam.portal_type:"authFatAP",console.log("判断设备为:authFatAP"),""!=portal_type&&setCookie("portal_type",portal_type,2376,"/");var ac_name=globalParam.ac_name||"";if(""!=ac_name&&setCookie("ac_name",ac_name,2376,"/"),afterLoginFlag){console.log("登录后跳转首页开始认证");var authData={merchantId:getCookieValue("MS_merchantId"),deviceId:getCookieValue("MS_deviceId"),apMac:getCookieValue("ap_mac"),terMac:getCookieValue("MS_terMac"),userMac:getCookieValue("MS_terMac"),callback:"callback",username:getCookieValue("MS_telephone"),password:getCookieValue("MS_pcode")};that.auther(authData)}that.index(),this.addBuyEvent();var roleType=getCookieValue("MS_roleType");2==roleType&&$("#mngPower").show(),$("#getFreeBtn").on("click",function(){loading.show(),Ajax.getJSONP(PATH+"/pkg/get",{},that.getFreeRequest.Apply(that))}),$("#closeActive").on("click",function(){$("#activeInfo").parent().parent().hide()}),console.log("addEventOfLogin"),this.addEventOfLogin()},addBuyEvent:function(){console.log("添加购买按钮事件:"+$("#buy").length),$("#buy").on("click",this.buyHandler.Apply(this))},buyHandler:function(){console.log("触发购买按钮事件");var that=this;Ajax.getJSONP(PATH+"/ms/pay/url/get",{},that.getDqUrlRequest.Apply(that))},afterLogin:function(data){loading.hide(),1==data.r?(afterLoginFlag=!0,tip("登录成功"),console.log("登录成功"),this.goIndex(),this.initUserCallBack(data),this.autherCallBack(data)):(tip("登录失败"),console.log("登录失败"),alert(data.msg))},index:function(){var that=this;loading.show(),Ajax.getJSONP(PATH+"/ms/member/merchant/index/init",{deviceId:getCookieValue("MS_deviceId"),merchantId:getCookieValue("MS_merchantId"),terMac:getCookieValue("MS_terMac")},this.indexRequest.Apply(that)),this.initUser()},initUser:function(){console.log("开始initUser");var that=this;Ajax.getJSONP(PATH+"/ms/member/merchant/index/initUser",{deviceId:getCookieValue("MS_deviceId"),merchantId:getCookieValue("MS_merchantId"),terMac:getCookieValue("MS_terMac")},this.initUserCallBack.Apply(that))},buy:function(dqurl){var authData={merchantId:getCookieValue("MS_merchantId"),
deviceId:getCookieValue("MS_deviceId"),apMac:getCookieValue("ap_mac"),terMac:getCookieValue("MS_terMac"),userMac:getCookieValue("MS_terMac"),callback:"callback",username:getCookieValue("MS_telephone"),password:getCookieValue("MS_pcode"),paytype:1};loading.show(),this.auther(authData,"1")},autherCallBack:function(data){var that=this,paytype=data.data.paytype;if(console.log("auther结果"),console.log(data),console.log("请求认证："),console.log(data),tip("正在请求上网"),loading&&loading.hide(),data.data&&data.data.msg)return console.log("认证报错:"+data.data.msg),tip(data.data.msg),void("已超过当日认证次数"==data.data.msg&&(window.location="/statics/buyTran.html?dqUrl="+encodeURIComponent(globalParam.dqUrl)));var auth;if(data.data&&data.data.access_auth){var str=data.data.access_auth.auth||"";if(""!=str&&(str=str.substring(8,str.length),auth=eval(str)),data.data.access_auth.kickoff&&1==data.data.access_auth.kickoff){console.log("之前的账号被踢下线");var html='<div class="row-9 center-text" >完成登录,其他设备的账号已经下线！</div>';Dialogue.modal(html,"25%","180px",function(){}).showModal()}}else console.log("即不是vip 也没有时长");if(data.data.isVipUser||data.data.user_cutoff&&that.leftTimeShow(data.data.user_cutoff),auth&&"0"==auth.resultCode){$("#linkMsg").text("已连网"),console.log("认证登陆成功:"+auth.resultCode);var token=auth.data,authUrl,host=encodeURIComponent(getCookieValue("host")||""),dev_id=getCookieValue("MS_deviceId"),gw_address=getCookieValue("gw_address"),gw_url=encodeURIComponent(getCookieValue("gw_url")||""),gw_port=getCookieValue("gw_port"),user_mac=getCookieValue("MS_terMac"),ap_mac=getCookieValue("ap_mac"),portal_type=getCookieValue("portal_type"),ac_name=getCookieValue("ac_name");gw_port&&""!=gw_port&&(gw_address+=":"+gw_port),authUrl="authFatAP"==portal_type?"http://"+gw_address+"/smartwifi/auth?url="+gw_url+"&user_mac="+user_mac+"&token="+token+"&ac_name="+ac_name:host+"?dev_id="+dev_id+"&dev_mac="+ap_mac+"&site_id=1&user_mac="+user_mac+"&ac_name="+ac_name,console.log("请求"+authUrl),$.ajax({url:authUrl,dataType:"JSONP",jsonp:"callback",async:!1,header:{"cache-control":"no-cache"},success:function(data,textStatus,jqXHR){tip("上网成功"),console.log("请求sb上网成功"),imgLoad2(),console.log(data)},error:function(xhr,status,error){console.log("请求sb上网失败"),imgLoad2(),console.log(xhr)},complete:function(xhr){console.log("complete")}}),setTimeout(function(){if("1"==paytype)if(""!=that.dqurl)console.log(that.dqurl),window.location="/statics/buyTran.html?dqUrl="+encodeURIComponent(globalParam.dqUrl);else{var html='<div class="row-9 center-text" >支付服务失败，请重新登录！</div>';Dialogue.modal(html,"25%","180px",function(){that.goLogin()}).showModal()}},1e3)}else console.log("认证登陆失败:")},auther:function(authData,paytype){afterLoginFlag=!1,tip("请稍等，正在网络放行"),console.log("请稍等，正在网络放行");var that=this;if(authData){var merchantId=getCookieValue("MS_merchantId"),deviceId=getCookieValue("MS_deviceId"),apMac=getCookieValue("ap_mac"),terMac=getCookieValue("terMac");if(""==merchantId||""==deviceId||""==apMac||""==terMac)return void alert("认证失败，请重新连接设备！");var authIndexUrl="/ms/access/auth";$.ajax({url:authIndexUrl,data:authData,dataType:"JSON",success:this.autherCallBack.Apply(that),error:function(xhr,status,error){}})}},login:function(){var that=($(this),this),deviceId=getCookieValue("MS_deviceId"),terMac=getCookieValue("MS_terMac"),merchantId=getCookieValue("MS_merchantId"),$username=$("input[name=username]"),$captcha=$("input[name=captcha]"),$loginmsg=$(".errorMsg"),username=$username.val()||"",captcha=$captcha.val()||"";return""==username||""==captcha||mobileStyle(username)||captchaStyle(captcha)?($loginmsg.text("请输入正确的帐号或验证码"),!1):(setCookie("MS_telephone",username,24,"/"),loading.show(),void Ajax.getJSONP(PATH+"/ms/login/save",{username:username,captcha:captcha,deviceId:deviceId,apMac:getCookieValue("ap_mac"),merchantId:merchantId,terMac:terMac,userMac:terMac,callback:"callback"},that.afterLogin.Apply(that)))},captchaClick:function(){var $username=(getCookieValue("MS_deviceId"),getCookieValue("MS_terMac"),getCookieValue("MS_merchantId"),$("input[name=username]")),$loginmsg=($("input[name=captcha]"),$(".errorMsg")),cutdownTime=60;console.log("点击了获取验证码"),$(".errorMsg").text("");var self=$(this);if(!self.attr("disabled")){var username=$username.val()||"";if(""==username||mobileStyle(username))return $loginmsg.text("请输入正确的手机号"),!1;self.prop("disabled",!0),self.text("发送中");var time=cutdownTime,sI=setInterval(function(){time-=1,0!==time?self.text(time+"秒后重试"):(window.clearInterval(sI),time=cutdownTime,self.text("重新获取"),self.removeAttr("disabled"))},1e3);Ajax.getJSONP(PATH+"/ms/sms/send",{mobile:username,type:"login"},function(result){1!=result.r&&result.msg&&$(".errorMsg").text(result.msg)})}},addEventOfLogin:function(){console.log("addEventOfLogin");var $username=(getCookieValue("MS_deviceId"),getCookieValue("MS_terMac"),getCookieValue("MS_merchantId"),$("input[name=username]")),$captcha=$("input[name=captcha]"),$loginmsg=$(".errorMsg");$("#login").on("click",this.login.Apply(this)),$(".captcha").find("button").on("click",this.captchaClick.Apply(this)),$username.on("change",function(){$loginmsg.text("")}),$captcha.on("change",function(){$loginmsg.text("")})},goIndex:function(){$(".loginWrap").hide(),$(".indexWrap").show()},goLogin:function(){$(".indexWrap").hide(),$(".loginWrap").show();var $username=$("input[name=username]"),$captcha=$("input[name=captcha]");$username.val(""),$captcha.val("")}});return{render:render}};
/*---------------------------------------------------------------------------*\
|  Subject: JavaScript Framework
|  Author:  cola.machine
|  Created: 2014-4-25 17:20:30
|  Version: v1.0
|-----------------------------------
|  QQ:371452875
|  Copyright (c) cola.machine   MIT-style license
|  The above copyright notice and this permission notice shall be
|  included in all copies or substantial portions of the Software
\*---------------------------------------------------------------------------*/
/**
 * @author colamachine
 * 日期格式化
 */
Date.prototype.Format = function(format)
   {
    var o = {
	    "M+" : this.getMonth()+1, // month
	    "d+" : this.getDate(),    // day
	    "h+" : this.getHours(),   // hour
	    "m+" : this.getMinutes(), // minute
	    "s+" : this.getSeconds(), // second
	    "q+" : Math.floor((this.getMonth()+3)/3),  // quarter
	    "S" : this.getMilliseconds() // millisecond
    }
    if(/(y+)/.test(format)) //用正则表达式去解析格式字符串 如果
    	format=format.replace(RegExp.$1,    (this.getFullYear()+"").substr(4 - RegExp.$1.length));//向后截取 如果fromt 是yy 就截取 2015 的15部分
    for(var k in o)
    	if(new RegExp("("+ k +")").test(format))
    		format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));// 很妙的设计
    return format;
   }

/*function parseDate(dateStr,format){
	var date =new Date();
	if(/(y+)/.test(format)){
		var year =  dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length);
	}
}*/
/**
 * @author colamachine
 * 根据指定日期字符串 和 日期格式转化成 date 对象返回
 */
function parseDate(dateStr,format){
	var year ,month,day,hour=0,minute=0,seconds=0;

	var date =new Date();
	if(/(y+)/.test(format)){
		 year=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
		 date.setFullYear(year);
	}
	if(/(M+)/.test(format)){
		 month=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
		 date.setMonth(month-1);
	}
	if(/(d+)/.test(format)){
		 day=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
		 date.setDate(day);
	}
	if(/(h+)/.test(format)){
		 hour=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
		//date+=hour*60*60000;

	}date.setHours(hour);
	if(/(m+)/.test(format)){
		 minute=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
//		date+=minute*60000;

	} date.setMinutes(minute);
	if(/(s+)/.test(format)){
		 seconds=parseInt(dateStr.substr(format.indexOf(RegExp.$1),RegExp.$1.length));
//		date+=seconds*1000;

	}
	date.setSeconds(seconds);

	return new Date(date);
}



/**
 * 增加天数 建议挪入 dateUtils 2015-05-29 11:31:21
 * @param day
 * @param increment
 * @returns {Date}
 */
function DateAdd(day,increment){//date int

	var d=day.getDate();
	var m=day.getMonth();
	var y=day.getFullYear();
	var myDay=new Date();
	myDay.setYear(y);
	myDay.setMonth(m);
	myDay.setDate(d+increment);
	return myDay;

}


/**
 * 根据指定日期得到 当前星期的数组
 * @param day
 * @returns {Array} 星期天~星期六
 */
function getDateOfWeek( day)//date
	{
		var myDay=day;
		var mon;
		var tues;
		var wed;
		var thurs;
		var fri;
		var sat;
		var sun;
		var mydate = CaculateDaysWeekNum(myDay.getFullYear(),myDay.getMonth()+1,myDay.getDate());
		//1 代表 日 2 代表1 7代表6 8
			mon=DateAdd(myDay,1-mydate);
			tues=DateAdd(myDay,2-mydate);
			wed=DateAdd(myDay,3-mydate);
			thurs=DateAdd(myDay,4-mydate);
			fri=DateAdd(myDay,5-mydate);
			sat=DateAdd(myDay,6-mydate);
			sun=DateAdd(myDay,7-mydate);
		var arr=[mon,tues,wed,thurs,fri,sat,sun];
		return arr;
	}

/**
 * 根据日期算出星期几
 * @param y
 * @param m
 * @param d
 * @returns {Number} 星期几 星期一是1 星期天是7
 */
function CaculateDaysWeekNum( y, m,d){
	y=new Number(y);
	m=new Number(m);
	d=new Number(d);
	if(m==1||m==2) {
	m+=12;
	y--;
	}
	var week=(d+2*m+Math.floor(3*(m+1)/5)+y+Math.floor(y/4)-Math.floor(y/100)+Math.floor(y/400))%7;
	return week+1;
};


//get the amount of day in appointed month

/**
 * 计算这个年月有多少天
 * @param y
 * @param m
 * @returns 天数
 */
function CaculateMonthDays(y,m){
	y=new Number(y);
	m=new Number(m);
	var MonthDays=[31,28,31,30,31,30,31,31,30,31,30,31];
	if((y%4==0 && y%100!=0) || y%400==0  )
	MonthDays[1]=29;
	return MonthDays[m-1];
}

/*function getAllDaysOfThisWeek(y,m,d){
	var week;
	var weeknum=CaculateDaysWeekNum(y,m,d);

	week[0]=;

}*/


/**
 * 日期转成yyyyMMdd格式字符串
 * @param date
 * @returns {String}
 */
function getdayStrFromDate(date){
	//其实可以使用 format 的
	return date.getFullYear()
				+ "-"
				+ (date.getMonth() < 9 ? ("0" + (date.getMonth() + 1))
						: (date.getMonth() + 1))
				+ "-"
				+ (date.getDate() < 10 ? ("0" + date.getDate())
						: date.getDate()) ;
}
/**
 * 根据时间得到他在 日历格子中的格数 如果flat 为1 代表 第一格的序号是1
 * @param time
 * @param flag
 * @returns {Number}
 */
function getTimeEndIndex(time,flag){
	var hour=parseInt(time.substr(0,2));
//	var min=parseInt(time.substr(3,2));
	var index=hour*2;
	/*if(min<30){

	}else{
		index++;
	}*/
	if(flag==1){
		index++;
	}
	return index;

}

/**
 *把当前日期 转成date time  转成分钟数
 * @param date日期 保留日期的年月日
 * @param time 0~23 代表小时数目
 * @returns 日期和小时代表的分钟数
 */
function getTimes(date,time){
	//var dateTime = parseInt( Date.parse(date+"T"+time+":00")/60000);
//	alert(date+"T"+time+":00");
//	alert( new Date(Date.parse(date+"T"+time+":00")));

	var date =
	parseDate(date+"T"+time,"yyyy-MM-ddThh:mm");
	return date.getTime()/60000;
}

var YMD= {
		year:0,
		month:0,
		day:0
};

/**
 * 到前年
 */
function getPreYear (day){
	//var day = this.dummyDay;
	var y = day.getFullYear();
	var m = day.getMonth();
	var d = day.getDate();
	y--;

	if (d > 28) {
		var endDay = CaculateMonthDays(y, m);
		d = d > endDay ? endDay : d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	return day;
}

function getNextYear (day) {
	//var day = this.dummyDay;
	var y = day.getYear();
	var m = day.getMonth();
	var d = day.getDate();
	y++;

	if (d > 28) {
		var endDay = CaculateMonthDays(y, m);
		d = d > endDay ? endDay : d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	return day;
}



/**
 * 到上月
 */
function getPreMonth  (day) {
	//var day = this.dummyDay;
	var m = day.getMonth();
	var y = day.getFullYear();
	var d = day.getDate();
	if (m == 0) {
		y--;
		m = 11;
	} else {
		m--;
	}
	if (d > 28) {
		var endDay = CaculateMonthDays(y, m);
		d = d > endDay ? endDay : d;
	}
	day.setYear(y);
	day.setMonth(m);
	day.setDate(d);
	return day;
}
/**
 * 到下月
 */
function getNextMonth(day) {
	//var day = this.dummyDay;
	var m = day.getMonth();
	var y = day.getFullYear();
	var d = day.getDate();
	if (m == 11) {
		y++;
		m = 0;
	} else {
		m++;
	}
	if (d > 28) {
		var endDay = CaculateMonthDays(y, m + 1);
		d = (d > endDay ? endDay : d);
	}
	day.setYear(y);
	day.setMonth(m, d);
	//this.dummyDay = day;
	return day
}


/**
 * 得到指定日期后一天日期
 * @param day
 * @returns {Date}
 */
function getNextDay(day){
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();
	/*if(d<CaculateMonthDays(y,m))
		d++;
	else
	{
		if(m<11){
			m++;

		}else{
			y++;
			m=0;

		}
		d=1;
	}*/
	var myDate=new Date();
	myDate.setYear(y);
	myDate.setMonth(m);
	myDate.setDate(day.getDate()+1)
	return myDate;
}

/**
 * 获取指定日期的前一天
 * @param day
 * @returns {Date}
 */
function getPreDay(day){//date
	var m=day.getMonth();
	var y=day.getFullYear();
	var d=day.getDate();
	/*if(d>1)
		d--;
	else
	{
		if(m>0){
			m--;

		}else{
			y--;
			m=11;

		}
		d=CaculateMonthDays(y,m);
	}*/
	var myDate=new Date();
	myDate.setYear(y);
	myDate.setMonth(m);
	myDate.setDate(day.getDate()-1);
	return myDate;
}

function eqToday(date){
	var now =new Date();
	if(date.getFullYear()==now.getFullYear() &&
			date.getMonth()==now.getMonth() &&
			date.getDate()==now.getDate()
	)
		return true;
	return false;
}
/**
 * 比较开始日期是否小于结束日期
 * @param startDate yyyy-MM-dd
 * @param endDate yyyy-MM-dd
 */
function compareYMD(startDate,endDate){
	var startDateInt = YMD2Int(startDate);
	var endDateInt=YMD2Int(endDate);
	if(startDateInt<endDateInt)
		return 1;
	if(startDateInt==endDateInt)
		return 0;
	if(startDateInt>endDateInt)
		return -1;
}
function YMD2Int(str){
	if(str.length!=10){
		logger.log("日期格式不正确");
		return;
	}
	var year= getYFromYMD(str);
	var month =getMFromYMD(str);
	var date = getDFromYMD(str);
	return year*10000+month*100+date;
}
function getYFromYMD(str){
	return parseInt(str.substr(0,4));
}
function getMFromYMD(str){
	return parseInt(str.substr(5,2));
}
function getDFromYMD(str){
	return parseInt(str.substr(8,2));
}(function(window) {
	var loadingHtml =
		'<div class="loading" id="loading" style="{{height}}">' +
		'<div class="loading-warp" style="{{style}}">' +
		'<div class="loading-content"><span class="loading-circle loading-circle-one"></span></div>' +
		'<div class="loading-content"><span class="loading-circle loading-circle-two"></span></div>' +
		'<div class="loading-content"><span class="loading-circle loading-circle-three"></span></div>' +
		'</div>' +
		'</div>';

	var loading = {
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
			var html = loadingHtml.replace('{{style}}', 'margin-top:' + self.topPx + 'px;left:' + leftPx + 'px').replace('{{height}}', 'height:' + self.heightPx + 'px');
			$('body').css({
				'overflow': 'hidden'
			}).append(html);
		},

		hide: function() {
			$('#loading').remove();
			$('body').css({
				'overflow': ''
			})
		}
	};

	window.loading = window.loading || loading;



	var Dialogue = {
		modal: function(html, top, height, fn) {
			var mask_html = '<div class="mask" > </div>';
			var modal_html = '<div class="modal" style="z-index:30000"><div class="content row-10 " ></div>' +
				'<div class="row-6 center-text"><button class="back-orange" >确定</button>' +
				'</div></div>';
			var msg_html = '<div class="row-9 center-text" style="min-height:50px"></div>';
			var m = {};
			m.showMask = function() {
				if ($(".mask").length == 0) {
					$("body").append(mask_html);
				}
				$(".mask").show();
			};
			m.hideMask = function() {
				$(".mask").hide();
			};
			m.showModal = function() {
				this.showMask();
				if ($(".modal").length == 0) {
					$("body").append(modal_html);
				}
				if (top && top != '') {
					$(".modal").css('top', top);
				}
				if (height && height != '') {
					$(".modal").css('height', height);
				}
				$(".modal").find(".content").html(html || msg_html);
				$(".modal").show();
				this.distroy();
			};
			m.hideModal = function() {
				this.hideMask();
				$(".modal").find(".content").html('');
				$(".modal").hide();
			};
			m.distroy = function() {
				var that = this;
				$(".modal").find("button").on('click', function() {
					that.hideModal();
					if (typeof(fn) == 'function') {
						fn();
						fn=null;
					}
				});
			}

			return m;
		},
		tip: function(msg) {

		}
	};
	window.Dialogue = window.Dialogue || Dialogue;
})(window);/*! jQuery v1.11.3 | (c) 2005, 2015 jQuery Foundation, Inc. | jquery.org/license */
!function(a,b){"object"==typeof module&&"object"==typeof module.exports?module.exports=a.document?b(a,!0):function(a){if(!a.document)throw new Error("jQuery requires a window with a document");return b(a)}:b(a)}("undefined"!=typeof window?window:this,function(a,b){var c=[],d=c.slice,e=c.concat,f=c.push,g=c.indexOf,h={},i=h.toString,j=h.hasOwnProperty,k={},l="1.11.3",m=function(a,b){return new m.fn.init(a,b)},n=/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g,o=/^-ms-/,p=/-([\da-z])/gi,q=function(a,b){return b.toUpperCase()};m.fn=m.prototype={jquery:l,constructor:m,selector:"",length:0,toArray:function(){return d.call(this)},get:function(a){return null!=a?0>a?this[a+this.length]:this[a]:d.call(this)},pushStack:function(a){var b=m.merge(this.constructor(),a);return b.prevObject=this,b.context=this.context,b},each:function(a,b){return m.each(this,a,b)},map:function(a){return this.pushStack(m.map(this,function(b,c){return a.call(b,c,b)}))},slice:function(){return this.pushStack(d.apply(this,arguments))},first:function(){return this.eq(0)},last:function(){return this.eq(-1)},eq:function(a){var b=this.length,c=+a+(0>a?b:0);return this.pushStack(c>=0&&b>c?[this[c]]:[])},end:function(){return this.prevObject||this.constructor(null)},push:f,sort:c.sort,splice:c.splice},m.extend=m.fn.extend=function(){var a,b,c,d,e,f,g=arguments[0]||{},h=1,i=arguments.length,j=!1;for("boolean"==typeof g&&(j=g,g=arguments[h]||{},h++),"object"==typeof g||m.isFunction(g)||(g={}),h===i&&(g=this,h--);i>h;h++)if(null!=(e=arguments[h]))for(d in e)a=g[d],c=e[d],g!==c&&(j&&c&&(m.isPlainObject(c)||(b=m.isArray(c)))?(b?(b=!1,f=a&&m.isArray(a)?a:[]):f=a&&m.isPlainObject(a)?a:{},g[d]=m.extend(j,f,c)):void 0!==c&&(g[d]=c));return g},m.extend({expando:"jQuery"+(l+Math.random()).replace(/\D/g,""),isReady:!0,error:function(a){throw new Error(a)},noop:function(){},isFunction:function(a){return"function"===m.type(a)},isArray:Array.isArray||function(a){return"array"===m.type(a)},isWindow:function(a){return null!=a&&a==a.window},isNumeric:function(a){return!m.isArray(a)&&a-parseFloat(a)+1>=0},isEmptyObject:function(a){var b;for(b in a)return!1;return!0},isPlainObject:function(a){var b;if(!a||"object"!==m.type(a)||a.nodeType||m.isWindow(a))return!1;try{if(a.constructor&&!j.call(a,"constructor")&&!j.call(a.constructor.prototype,"isPrototypeOf"))return!1}catch(c){return!1}if(k.ownLast)for(b in a)return j.call(a,b);for(b in a);return void 0===b||j.call(a,b)},type:function(a){return null==a?a+"":"object"==typeof a||"function"==typeof a?h[i.call(a)]||"object":typeof a},globalEval:function(b){b&&m.trim(b)&&(a.execScript||function(b){a.eval.call(a,b)})(b)},camelCase:function(a){return a.replace(o,"ms-").replace(p,q)},nodeName:function(a,b){return a.nodeName&&a.nodeName.toLowerCase()===b.toLowerCase()},each:function(a,b,c){var d,e=0,f=a.length,g=r(a);if(c){if(g){for(;f>e;e++)if(d=b.apply(a[e],c),d===!1)break}else for(e in a)if(d=b.apply(a[e],c),d===!1)break}else if(g){for(;f>e;e++)if(d=b.call(a[e],e,a[e]),d===!1)break}else for(e in a)if(d=b.call(a[e],e,a[e]),d===!1)break;return a},trim:function(a){return null==a?"":(a+"").replace(n,"")},makeArray:function(a,b){var c=b||[];return null!=a&&(r(Object(a))?m.merge(c,"string"==typeof a?[a]:a):f.call(c,a)),c},inArray:function(a,b,c){var d;if(b){if(g)return g.call(b,a,c);for(d=b.length,c=c?0>c?Math.max(0,d+c):c:0;d>c;c++)if(c in b&&b[c]===a)return c}return-1},merge:function(a,b){var c=+b.length,d=0,e=a.length;while(c>d)a[e++]=b[d++];if(c!==c)while(void 0!==b[d])a[e++]=b[d++];return a.length=e,a},grep:function(a,b,c){for(var d,e=[],f=0,g=a.length,h=!c;g>f;f++)d=!b(a[f],f),d!==h&&e.push(a[f]);return e},map:function(a,b,c){var d,f=0,g=a.length,h=r(a),i=[];if(h)for(;g>f;f++)d=b(a[f],f,c),null!=d&&i.push(d);else for(f in a)d=b(a[f],f,c),null!=d&&i.push(d);return e.apply([],i)},guid:1,proxy:function(a,b){var c,e,f;return"string"==typeof b&&(f=a[b],b=a,a=f),m.isFunction(a)?(c=d.call(arguments,2),e=function(){return a.apply(b||this,c.concat(d.call(arguments)))},e.guid=a.guid=a.guid||m.guid++,e):void 0},now:function(){return+new Date},support:k}),m.each("Boolean Number String Function Array Date RegExp Object Error".split(" "),function(a,b){h["[object "+b+"]"]=b.toLowerCase()});function r(a){var b="length"in a&&a.length,c=m.type(a);return"function"===c||m.isWindow(a)?!1:1===a.nodeType&&b?!0:"array"===c||0===b||"number"==typeof b&&b>0&&b-1 in a}var s=function(a){var b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u="sizzle"+1*new Date,v=a.document,w=0,x=0,y=ha(),z=ha(),A=ha(),B=function(a,b){return a===b&&(l=!0),0},C=1<<31,D={}.hasOwnProperty,E=[],F=E.pop,G=E.push,H=E.push,I=E.slice,J=function(a,b){for(var c=0,d=a.length;d>c;c++)if(a[c]===b)return c;return-1},K="checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",L="[\\x20\\t\\r\\n\\f]",M="(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+",N=M.replace("w","w#"),O="\\["+L+"*("+M+")(?:"+L+"*([*^$|!~]?=)"+L+"*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|("+N+"))|)"+L+"*\\]",P=":("+M+")(?:\\((('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|((?:\\\\.|[^\\\\()[\\]]|"+O+")*)|.*)\\)|)",Q=new RegExp(L+"+","g"),R=new RegExp("^"+L+"+|((?:^|[^\\\\])(?:\\\\.)*)"+L+"+$","g"),S=new RegExp("^"+L+"*,"+L+"*"),T=new RegExp("^"+L+"*([>+~]|"+L+")"+L+"*"),U=new RegExp("="+L+"*([^\\]'\"]*?)"+L+"*\\]","g"),V=new RegExp(P),W=new RegExp("^"+N+"$"),X={ID:new RegExp("^#("+M+")"),CLASS:new RegExp("^\\.("+M+")"),TAG:new RegExp("^("+M.replace("w","w*")+")"),ATTR:new RegExp("^"+O),PSEUDO:new RegExp("^"+P),CHILD:new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\("+L+"*(even|odd|(([+-]|)(\\d*)n|)"+L+"*(?:([+-]|)"+L+"*(\\d+)|))"+L+"*\\)|)","i"),bool:new RegExp("^(?:"+K+")$","i"),needsContext:new RegExp("^"+L+"*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\("+L+"*((?:-\\d)?\\d*)"+L+"*\\)|)(?=[^-]|$)","i")},Y=/^(?:input|select|textarea|button)$/i,Z=/^h\d$/i,$=/^[^{]+\{\s*\[native \w/,_=/^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/,aa=/[+~]/,ba=/'|\\/g,ca=new RegExp("\\\\([\\da-f]{1,6}"+L+"?|("+L+")|.)","ig"),da=function(a,b,c){var d="0x"+b-65536;return d!==d||c?b:0>d?String.fromCharCode(d+65536):String.fromCharCode(d>>10|55296,1023&d|56320)},ea=function(){m()};try{H.apply(E=I.call(v.childNodes),v.childNodes),E[v.childNodes.length].nodeType}catch(fa){H={apply:E.length?function(a,b){G.apply(a,I.call(b))}:function(a,b){var c=a.length,d=0;while(a[c++]=b[d++]);a.length=c-1}}}function ga(a,b,d,e){var f,h,j,k,l,o,r,s,w,x;if((b?b.ownerDocument||b:v)!==n&&m(b),b=b||n,d=d||[],k=b.nodeType,"string"!=typeof a||!a||1!==k&&9!==k&&11!==k)return d;if(!e&&p){if(11!==k&&(f=_.exec(a)))if(j=f[1]){if(9===k){if(h=b.getElementById(j),!h||!h.parentNode)return d;if(h.id===j)return d.push(h),d}else if(b.ownerDocument&&(h=b.ownerDocument.getElementById(j))&&t(b,h)&&h.id===j)return d.push(h),d}else{if(f[2])return H.apply(d,b.getElementsByTagName(a)),d;if((j=f[3])&&c.getElementsByClassName)return H.apply(d,b.getElementsByClassName(j)),d}if(c.qsa&&(!q||!q.test(a))){if(s=r=u,w=b,x=1!==k&&a,1===k&&"object"!==b.nodeName.toLowerCase()){o=g(a),(r=b.getAttribute("id"))?s=r.replace(ba,"\\$&"):b.setAttribute("id",s),s="[id='"+s+"'] ",l=o.length;while(l--)o[l]=s+ra(o[l]);w=aa.test(a)&&pa(b.parentNode)||b,x=o.join(",")}if(x)try{return H.apply(d,w.querySelectorAll(x)),d}catch(y){}finally{r||b.removeAttribute("id")}}}return i(a.replace(R,"$1"),b,d,e)}function ha(){var a=[];function b(c,e){return a.push(c+" ")>d.cacheLength&&delete b[a.shift()],b[c+" "]=e}return b}function ia(a){return a[u]=!0,a}function ja(a){var b=n.createElement("div");try{return!!a(b)}catch(c){return!1}finally{b.parentNode&&b.parentNode.removeChild(b),b=null}}function ka(a,b){var c=a.split("|"),e=a.length;while(e--)d.attrHandle[c[e]]=b}function la(a,b){var c=b&&a,d=c&&1===a.nodeType&&1===b.nodeType&&(~b.sourceIndex||C)-(~a.sourceIndex||C);if(d)return d;if(c)while(c=c.nextSibling)if(c===b)return-1;return a?1:-1}function ma(a){return function(b){var c=b.nodeName.toLowerCase();return"input"===c&&b.type===a}}function na(a){return function(b){var c=b.nodeName.toLowerCase();return("input"===c||"button"===c)&&b.type===a}}function oa(a){return ia(function(b){return b=+b,ia(function(c,d){var e,f=a([],c.length,b),g=f.length;while(g--)c[e=f[g]]&&(c[e]=!(d[e]=c[e]))})})}function pa(a){return a&&"undefined"!=typeof a.getElementsByTagName&&a}c=ga.support={},f=ga.isXML=function(a){var b=a&&(a.ownerDocument||a).documentElement;return b?"HTML"!==b.nodeName:!1},m=ga.setDocument=function(a){var b,e,g=a?a.ownerDocument||a:v;return g!==n&&9===g.nodeType&&g.documentElement?(n=g,o=g.documentElement,e=g.defaultView,e&&e!==e.top&&(e.addEventListener?e.addEventListener("unload",ea,!1):e.attachEvent&&e.attachEvent("onunload",ea)),p=!f(g),c.attributes=ja(function(a){return a.className="i",!a.getAttribute("className")}),c.getElementsByTagName=ja(function(a){return a.appendChild(g.createComment("")),!a.getElementsByTagName("*").length}),c.getElementsByClassName=$.test(g.getElementsByClassName),c.getById=ja(function(a){return o.appendChild(a).id=u,!g.getElementsByName||!g.getElementsByName(u).length}),c.getById?(d.find.ID=function(a,b){if("undefined"!=typeof b.getElementById&&p){var c=b.getElementById(a);return c&&c.parentNode?[c]:[]}},d.filter.ID=function(a){var b=a.replace(ca,da);return function(a){return a.getAttribute("id")===b}}):(delete d.find.ID,d.filter.ID=function(a){var b=a.replace(ca,da);return function(a){var c="undefined"!=typeof a.getAttributeNode&&a.getAttributeNode("id");return c&&c.value===b}}),d.find.TAG=c.getElementsByTagName?function(a,b){return"undefined"!=typeof b.getElementsByTagName?b.getElementsByTagName(a):c.qsa?b.querySelectorAll(a):void 0}:function(a,b){var c,d=[],e=0,f=b.getElementsByTagName(a);if("*"===a){while(c=f[e++])1===c.nodeType&&d.push(c);return d}return f},d.find.CLASS=c.getElementsByClassName&&function(a,b){return p?b.getElementsByClassName(a):void 0},r=[],q=[],(c.qsa=$.test(g.querySelectorAll))&&(ja(function(a){o.appendChild(a).innerHTML="<a id='"+u+"'></a><select id='"+u+"-\f]' msallowcapture=''><option selected=''></option></select>",a.querySelectorAll("[msallowcapture^='']").length&&q.push("[*^$]="+L+"*(?:''|\"\")"),a.querySelectorAll("[selected]").length||q.push("\\["+L+"*(?:value|"+K+")"),a.querySelectorAll("[id~="+u+"-]").length||q.push("~="),a.querySelectorAll(":checked").length||q.push(":checked"),a.querySelectorAll("a#"+u+"+*").length||q.push(".#.+[+~]")}),ja(function(a){var b=g.createElement("input");b.setAttribute("type","hidden"),a.appendChild(b).setAttribute("name","D"),a.querySelectorAll("[name=d]").length&&q.push("name"+L+"*[*^$|!~]?="),a.querySelectorAll(":enabled").length||q.push(":enabled",":disabled"),a.querySelectorAll("*,:x"),q.push(",.*:")})),(c.matchesSelector=$.test(s=o.matches||o.webkitMatchesSelector||o.mozMatchesSelector||o.oMatchesSelector||o.msMatchesSelector))&&ja(function(a){c.disconnectedMatch=s.call(a,"div"),s.call(a,"[s!='']:x"),r.push("!=",P)}),q=q.length&&new RegExp(q.join("|")),r=r.length&&new RegExp(r.join("|")),b=$.test(o.compareDocumentPosition),t=b||$.test(o.contains)?function(a,b){var c=9===a.nodeType?a.documentElement:a,d=b&&b.parentNode;return a===d||!(!d||1!==d.nodeType||!(c.contains?c.contains(d):a.compareDocumentPosition&&16&a.compareDocumentPosition(d)))}:function(a,b){if(b)while(b=b.parentNode)if(b===a)return!0;return!1},B=b?function(a,b){if(a===b)return l=!0,0;var d=!a.compareDocumentPosition-!b.compareDocumentPosition;return d?d:(d=(a.ownerDocument||a)===(b.ownerDocument||b)?a.compareDocumentPosition(b):1,1&d||!c.sortDetached&&b.compareDocumentPosition(a)===d?a===g||a.ownerDocument===v&&t(v,a)?-1:b===g||b.ownerDocument===v&&t(v,b)?1:k?J(k,a)-J(k,b):0:4&d?-1:1)}:function(a,b){if(a===b)return l=!0,0;var c,d=0,e=a.parentNode,f=b.parentNode,h=[a],i=[b];if(!e||!f)return a===g?-1:b===g?1:e?-1:f?1:k?J(k,a)-J(k,b):0;if(e===f)return la(a,b);c=a;while(c=c.parentNode)h.unshift(c);c=b;while(c=c.parentNode)i.unshift(c);while(h[d]===i[d])d++;return d?la(h[d],i[d]):h[d]===v?-1:i[d]===v?1:0},g):n},ga.matches=function(a,b){return ga(a,null,null,b)},ga.matchesSelector=function(a,b){if((a.ownerDocument||a)!==n&&m(a),b=b.replace(U,"='$1']"),!(!c.matchesSelector||!p||r&&r.test(b)||q&&q.test(b)))try{var d=s.call(a,b);if(d||c.disconnectedMatch||a.document&&11!==a.document.nodeType)return d}catch(e){}return ga(b,n,null,[a]).length>0},ga.contains=function(a,b){return(a.ownerDocument||a)!==n&&m(a),t(a,b)},ga.attr=function(a,b){(a.ownerDocument||a)!==n&&m(a);var e=d.attrHandle[b.toLowerCase()],f=e&&D.call(d.attrHandle,b.toLowerCase())?e(a,b,!p):void 0;return void 0!==f?f:c.attributes||!p?a.getAttribute(b):(f=a.getAttributeNode(b))&&f.specified?f.value:null},ga.error=function(a){throw new Error("Syntax error, unrecognized expression: "+a)},ga.uniqueSort=function(a){var b,d=[],e=0,f=0;if(l=!c.detectDuplicates,k=!c.sortStable&&a.slice(0),a.sort(B),l){while(b=a[f++])b===a[f]&&(e=d.push(f));while(e--)a.splice(d[e],1)}return k=null,a},e=ga.getText=function(a){var b,c="",d=0,f=a.nodeType;if(f){if(1===f||9===f||11===f){if("string"==typeof a.textContent)return a.textContent;for(a=a.firstChild;a;a=a.nextSibling)c+=e(a)}else if(3===f||4===f)return a.nodeValue}else while(b=a[d++])c+=e(b);return c},d=ga.selectors={cacheLength:50,createPseudo:ia,match:X,attrHandle:{},find:{},relative:{">":{dir:"parentNode",first:!0}," ":{dir:"parentNode"},"+":{dir:"previousSibling",first:!0},"~":{dir:"previousSibling"}},preFilter:{ATTR:function(a){return a[1]=a[1].replace(ca,da),a[3]=(a[3]||a[4]||a[5]||"").replace(ca,da),"~="===a[2]&&(a[3]=" "+a[3]+" "),a.slice(0,4)},CHILD:function(a){return a[1]=a[1].toLowerCase(),"nth"===a[1].slice(0,3)?(a[3]||ga.error(a[0]),a[4]=+(a[4]?a[5]+(a[6]||1):2*("even"===a[3]||"odd"===a[3])),a[5]=+(a[7]+a[8]||"odd"===a[3])):a[3]&&ga.error(a[0]),a},PSEUDO:function(a){var b,c=!a[6]&&a[2];return X.CHILD.test(a[0])?null:(a[3]?a[2]=a[4]||a[5]||"":c&&V.test(c)&&(b=g(c,!0))&&(b=c.indexOf(")",c.length-b)-c.length)&&(a[0]=a[0].slice(0,b),a[2]=c.slice(0,b)),a.slice(0,3))}},filter:{TAG:function(a){var b=a.replace(ca,da).toLowerCase();return"*"===a?function(){return!0}:function(a){return a.nodeName&&a.nodeName.toLowerCase()===b}},CLASS:function(a){var b=y[a+" "];return b||(b=new RegExp("(^|"+L+")"+a+"("+L+"|$)"))&&y(a,function(a){return b.test("string"==typeof a.className&&a.className||"undefined"!=typeof a.getAttribute&&a.getAttribute("class")||"")})},ATTR:function(a,b,c){return function(d){var e=ga.attr(d,a);return null==e?"!="===b:b?(e+="","="===b?e===c:"!="===b?e!==c:"^="===b?c&&0===e.indexOf(c):"*="===b?c&&e.indexOf(c)>-1:"$="===b?c&&e.slice(-c.length)===c:"~="===b?(" "+e.replace(Q," ")+" ").indexOf(c)>-1:"|="===b?e===c||e.slice(0,c.length+1)===c+"-":!1):!0}},CHILD:function(a,b,c,d,e){var f="nth"!==a.slice(0,3),g="last"!==a.slice(-4),h="of-type"===b;return 1===d&&0===e?function(a){return!!a.parentNode}:function(b,c,i){var j,k,l,m,n,o,p=f!==g?"nextSibling":"previousSibling",q=b.parentNode,r=h&&b.nodeName.toLowerCase(),s=!i&&!h;if(q){if(f){while(p){l=b;while(l=l[p])if(h?l.nodeName.toLowerCase()===r:1===l.nodeType)return!1;o=p="only"===a&&!o&&"nextSibling"}return!0}if(o=[g?q.firstChild:q.lastChild],g&&s){k=q[u]||(q[u]={}),j=k[a]||[],n=j[0]===w&&j[1],m=j[0]===w&&j[2],l=n&&q.childNodes[n];while(l=++n&&l&&l[p]||(m=n=0)||o.pop())if(1===l.nodeType&&++m&&l===b){k[a]=[w,n,m];break}}else if(s&&(j=(b[u]||(b[u]={}))[a])&&j[0]===w)m=j[1];else while(l=++n&&l&&l[p]||(m=n=0)||o.pop())if((h?l.nodeName.toLowerCase()===r:1===l.nodeType)&&++m&&(s&&((l[u]||(l[u]={}))[a]=[w,m]),l===b))break;return m-=e,m===d||m%d===0&&m/d>=0}}},PSEUDO:function(a,b){var c,e=d.pseudos[a]||d.setFilters[a.toLowerCase()]||ga.error("unsupported pseudo: "+a);return e[u]?e(b):e.length>1?(c=[a,a,"",b],d.setFilters.hasOwnProperty(a.toLowerCase())?ia(function(a,c){var d,f=e(a,b),g=f.length;while(g--)d=J(a,f[g]),a[d]=!(c[d]=f[g])}):function(a){return e(a,0,c)}):e}},pseudos:{not:ia(function(a){var b=[],c=[],d=h(a.replace(R,"$1"));return d[u]?ia(function(a,b,c,e){var f,g=d(a,null,e,[]),h=a.length;while(h--)(f=g[h])&&(a[h]=!(b[h]=f))}):function(a,e,f){return b[0]=a,d(b,null,f,c),b[0]=null,!c.pop()}}),has:ia(function(a){return function(b){return ga(a,b).length>0}}),contains:ia(function(a){return a=a.replace(ca,da),function(b){return(b.textContent||b.innerText||e(b)).indexOf(a)>-1}}),lang:ia(function(a){return W.test(a||"")||ga.error("unsupported lang: "+a),a=a.replace(ca,da).toLowerCase(),function(b){var c;do if(c=p?b.lang:b.getAttribute("xml:lang")||b.getAttribute("lang"))return c=c.toLowerCase(),c===a||0===c.indexOf(a+"-");while((b=b.parentNode)&&1===b.nodeType);return!1}}),target:function(b){var c=a.location&&a.location.hash;return c&&c.slice(1)===b.id},root:function(a){return a===o},focus:function(a){return a===n.activeElement&&(!n.hasFocus||n.hasFocus())&&!!(a.type||a.href||~a.tabIndex)},enabled:function(a){return a.disabled===!1},disabled:function(a){return a.disabled===!0},checked:function(a){var b=a.nodeName.toLowerCase();return"input"===b&&!!a.checked||"option"===b&&!!a.selected},selected:function(a){return a.parentNode&&a.parentNode.selectedIndex,a.selected===!0},empty:function(a){for(a=a.firstChild;a;a=a.nextSibling)if(a.nodeType<6)return!1;return!0},parent:function(a){return!d.pseudos.empty(a)},header:function(a){return Z.test(a.nodeName)},input:function(a){return Y.test(a.nodeName)},button:function(a){var b=a.nodeName.toLowerCase();return"input"===b&&"button"===a.type||"button"===b},text:function(a){var b;return"input"===a.nodeName.toLowerCase()&&"text"===a.type&&(null==(b=a.getAttribute("type"))||"text"===b.toLowerCase())},first:oa(function(){return[0]}),last:oa(function(a,b){return[b-1]}),eq:oa(function(a,b,c){return[0>c?c+b:c]}),even:oa(function(a,b){for(var c=0;b>c;c+=2)a.push(c);return a}),odd:oa(function(a,b){for(var c=1;b>c;c+=2)a.push(c);return a}),lt:oa(function(a,b,c){for(var d=0>c?c+b:c;--d>=0;)a.push(d);return a}),gt:oa(function(a,b,c){for(var d=0>c?c+b:c;++d<b;)a.push(d);return a})}},d.pseudos.nth=d.pseudos.eq;for(b in{radio:!0,checkbox:!0,file:!0,password:!0,image:!0})d.pseudos[b]=ma(b);for(b in{submit:!0,reset:!0})d.pseudos[b]=na(b);function qa(){}qa.prototype=d.filters=d.pseudos,d.setFilters=new qa,g=ga.tokenize=function(a,b){var c,e,f,g,h,i,j,k=z[a+" "];if(k)return b?0:k.slice(0);h=a,i=[],j=d.preFilter;while(h){(!c||(e=S.exec(h)))&&(e&&(h=h.slice(e[0].length)||h),i.push(f=[])),c=!1,(e=T.exec(h))&&(c=e.shift(),f.push({value:c,type:e[0].replace(R," ")}),h=h.slice(c.length));for(g in d.filter)!(e=X[g].exec(h))||j[g]&&!(e=j[g](e))||(c=e.shift(),f.push({value:c,type:g,matches:e}),h=h.slice(c.length));if(!c)break}return b?h.length:h?ga.error(a):z(a,i).slice(0)};function ra(a){for(var b=0,c=a.length,d="";c>b;b++)d+=a[b].value;return d}function sa(a,b,c){var d=b.dir,e=c&&"parentNode"===d,f=x++;return b.first?function(b,c,f){while(b=b[d])if(1===b.nodeType||e)return a(b,c,f)}:function(b,c,g){var h,i,j=[w,f];if(g){while(b=b[d])if((1===b.nodeType||e)&&a(b,c,g))return!0}else while(b=b[d])if(1===b.nodeType||e){if(i=b[u]||(b[u]={}),(h=i[d])&&h[0]===w&&h[1]===f)return j[2]=h[2];if(i[d]=j,j[2]=a(b,c,g))return!0}}}function ta(a){return a.length>1?function(b,c,d){var e=a.length;while(e--)if(!a[e](b,c,d))return!1;return!0}:a[0]}function ua(a,b,c){for(var d=0,e=b.length;e>d;d++)ga(a,b[d],c);return c}function va(a,b,c,d,e){for(var f,g=[],h=0,i=a.length,j=null!=b;i>h;h++)(f=a[h])&&(!c||c(f,d,e))&&(g.push(f),j&&b.push(h));return g}function wa(a,b,c,d,e,f){return d&&!d[u]&&(d=wa(d)),e&&!e[u]&&(e=wa(e,f)),ia(function(f,g,h,i){var j,k,l,m=[],n=[],o=g.length,p=f||ua(b||"*",h.nodeType?[h]:h,[]),q=!a||!f&&b?p:va(p,m,a,h,i),r=c?e||(f?a:o||d)?[]:g:q;if(c&&c(q,r,h,i),d){j=va(r,n),d(j,[],h,i),k=j.length;while(k--)(l=j[k])&&(r[n[k]]=!(q[n[k]]=l))}if(f){if(e||a){if(e){j=[],k=r.length;while(k--)(l=r[k])&&j.push(q[k]=l);e(null,r=[],j,i)}k=r.length;while(k--)(l=r[k])&&(j=e?J(f,l):m[k])>-1&&(f[j]=!(g[j]=l))}}else r=va(r===g?r.splice(o,r.length):r),e?e(null,g,r,i):H.apply(g,r)})}function xa(a){for(var b,c,e,f=a.length,g=d.relative[a[0].type],h=g||d.relative[" "],i=g?1:0,k=sa(function(a){return a===b},h,!0),l=sa(function(a){return J(b,a)>-1},h,!0),m=[function(a,c,d){var e=!g&&(d||c!==j)||((b=c).nodeType?k(a,c,d):l(a,c,d));return b=null,e}];f>i;i++)if(c=d.relative[a[i].type])m=[sa(ta(m),c)];else{if(c=d.filter[a[i].type].apply(null,a[i].matches),c[u]){for(e=++i;f>e;e++)if(d.relative[a[e].type])break;return wa(i>1&&ta(m),i>1&&ra(a.slice(0,i-1).concat({value:" "===a[i-2].type?"*":""})).replace(R,"$1"),c,e>i&&xa(a.slice(i,e)),f>e&&xa(a=a.slice(e)),f>e&&ra(a))}m.push(c)}return ta(m)}function ya(a,b){var c=b.length>0,e=a.length>0,f=function(f,g,h,i,k){var l,m,o,p=0,q="0",r=f&&[],s=[],t=j,u=f||e&&d.find.TAG("*",k),v=w+=null==t?1:Math.random()||.1,x=u.length;for(k&&(j=g!==n&&g);q!==x&&null!=(l=u[q]);q++){if(e&&l){m=0;while(o=a[m++])if(o(l,g,h)){i.push(l);break}k&&(w=v)}c&&((l=!o&&l)&&p--,f&&r.push(l))}if(p+=q,c&&q!==p){m=0;while(o=b[m++])o(r,s,g,h);if(f){if(p>0)while(q--)r[q]||s[q]||(s[q]=F.call(i));s=va(s)}H.apply(i,s),k&&!f&&s.length>0&&p+b.length>1&&ga.uniqueSort(i)}return k&&(w=v,j=t),r};return c?ia(f):f}return h=ga.compile=function(a,b){var c,d=[],e=[],f=A[a+" "];if(!f){b||(b=g(a)),c=b.length;while(c--)f=xa(b[c]),f[u]?d.push(f):e.push(f);f=A(a,ya(e,d)),f.selector=a}return f},i=ga.select=function(a,b,e,f){var i,j,k,l,m,n="function"==typeof a&&a,o=!f&&g(a=n.selector||a);if(e=e||[],1===o.length){if(j=o[0]=o[0].slice(0),j.length>2&&"ID"===(k=j[0]).type&&c.getById&&9===b.nodeType&&p&&d.relative[j[1].type]){if(b=(d.find.ID(k.matches[0].replace(ca,da),b)||[])[0],!b)return e;n&&(b=b.parentNode),a=a.slice(j.shift().value.length)}i=X.needsContext.test(a)?0:j.length;while(i--){if(k=j[i],d.relative[l=k.type])break;if((m=d.find[l])&&(f=m(k.matches[0].replace(ca,da),aa.test(j[0].type)&&pa(b.parentNode)||b))){if(j.splice(i,1),a=f.length&&ra(j),!a)return H.apply(e,f),e;break}}}return(n||h(a,o))(f,b,!p,e,aa.test(a)&&pa(b.parentNode)||b),e},c.sortStable=u.split("").sort(B).join("")===u,c.detectDuplicates=!!l,m(),c.sortDetached=ja(function(a){return 1&a.compareDocumentPosition(n.createElement("div"))}),ja(function(a){return a.innerHTML="<a href='#'></a>","#"===a.firstChild.getAttribute("href")})||ka("type|href|height|width",function(a,b,c){return c?void 0:a.getAttribute(b,"type"===b.toLowerCase()?1:2)}),c.attributes&&ja(function(a){return a.innerHTML="<input/>",a.firstChild.setAttribute("value",""),""===a.firstChild.getAttribute("value")})||ka("value",function(a,b,c){return c||"input"!==a.nodeName.toLowerCase()?void 0:a.defaultValue}),ja(function(a){return null==a.getAttribute("disabled")})||ka(K,function(a,b,c){var d;return c?void 0:a[b]===!0?b.toLowerCase():(d=a.getAttributeNode(b))&&d.specified?d.value:null}),ga}(a);m.find=s,m.expr=s.selectors,m.expr[":"]=m.expr.pseudos,m.unique=s.uniqueSort,m.text=s.getText,m.isXMLDoc=s.isXML,m.contains=s.contains;var t=m.expr.match.needsContext,u=/^<(\w+)\s*\/?>(?:<\/\1>|)$/,v=/^.[^:#\[\.,]*$/;function w(a,b,c){if(m.isFunction(b))return m.grep(a,function(a,d){return!!b.call(a,d,a)!==c});if(b.nodeType)return m.grep(a,function(a){return a===b!==c});if("string"==typeof b){if(v.test(b))return m.filter(b,a,c);b=m.filter(b,a)}return m.grep(a,function(a){return m.inArray(a,b)>=0!==c})}m.filter=function(a,b,c){var d=b[0];return c&&(a=":not("+a+")"),1===b.length&&1===d.nodeType?m.find.matchesSelector(d,a)?[d]:[]:m.find.matches(a,m.grep(b,function(a){return 1===a.nodeType}))},m.fn.extend({find:function(a){var b,c=[],d=this,e=d.length;if("string"!=typeof a)return this.pushStack(m(a).filter(function(){for(b=0;e>b;b++)if(m.contains(d[b],this))return!0}));for(b=0;e>b;b++)m.find(a,d[b],c);return c=this.pushStack(e>1?m.unique(c):c),c.selector=this.selector?this.selector+" "+a:a,c},filter:function(a){return this.pushStack(w(this,a||[],!1))},not:function(a){return this.pushStack(w(this,a||[],!0))},is:function(a){return!!w(this,"string"==typeof a&&t.test(a)?m(a):a||[],!1).length}});var x,y=a.document,z=/^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/,A=m.fn.init=function(a,b){var c,d;if(!a)return this;if("string"==typeof a){if(c="<"===a.charAt(0)&&">"===a.charAt(a.length-1)&&a.length>=3?[null,a,null]:z.exec(a),!c||!c[1]&&b)return!b||b.jquery?(b||x).find(a):this.constructor(b).find(a);if(c[1]){if(b=b instanceof m?b[0]:b,m.merge(this,m.parseHTML(c[1],b&&b.nodeType?b.ownerDocument||b:y,!0)),u.test(c[1])&&m.isPlainObject(b))for(c in b)m.isFunction(this[c])?this[c](b[c]):this.attr(c,b[c]);return this}if(d=y.getElementById(c[2]),d&&d.parentNode){if(d.id!==c[2])return x.find(a);this.length=1,this[0]=d}return this.context=y,this.selector=a,this}return a.nodeType?(this.context=this[0]=a,this.length=1,this):m.isFunction(a)?"undefined"!=typeof x.ready?x.ready(a):a(m):(void 0!==a.selector&&(this.selector=a.selector,this.context=a.context),m.makeArray(a,this))};A.prototype=m.fn,x=m(y);var B=/^(?:parents|prev(?:Until|All))/,C={children:!0,contents:!0,next:!0,prev:!0};m.extend({dir:function(a,b,c){var d=[],e=a[b];while(e&&9!==e.nodeType&&(void 0===c||1!==e.nodeType||!m(e).is(c)))1===e.nodeType&&d.push(e),e=e[b];return d},sibling:function(a,b){for(var c=[];a;a=a.nextSibling)1===a.nodeType&&a!==b&&c.push(a);return c}}),m.fn.extend({has:function(a){var b,c=m(a,this),d=c.length;return this.filter(function(){for(b=0;d>b;b++)if(m.contains(this,c[b]))return!0})},closest:function(a,b){for(var c,d=0,e=this.length,f=[],g=t.test(a)||"string"!=typeof a?m(a,b||this.context):0;e>d;d++)for(c=this[d];c&&c!==b;c=c.parentNode)if(c.nodeType<11&&(g?g.index(c)>-1:1===c.nodeType&&m.find.matchesSelector(c,a))){f.push(c);break}return this.pushStack(f.length>1?m.unique(f):f)},index:function(a){return a?"string"==typeof a?m.inArray(this[0],m(a)):m.inArray(a.jquery?a[0]:a,this):this[0]&&this[0].parentNode?this.first().prevAll().length:-1},add:function(a,b){return this.pushStack(m.unique(m.merge(this.get(),m(a,b))))},addBack:function(a){return this.add(null==a?this.prevObject:this.prevObject.filter(a))}});function D(a,b){do a=a[b];while(a&&1!==a.nodeType);return a}m.each({parent:function(a){var b=a.parentNode;return b&&11!==b.nodeType?b:null},parents:function(a){return m.dir(a,"parentNode")},parentsUntil:function(a,b,c){return m.dir(a,"parentNode",c)},next:function(a){return D(a,"nextSibling")},prev:function(a){return D(a,"previousSibling")},nextAll:function(a){return m.dir(a,"nextSibling")},prevAll:function(a){return m.dir(a,"previousSibling")},nextUntil:function(a,b,c){return m.dir(a,"nextSibling",c)},prevUntil:function(a,b,c){return m.dir(a,"previousSibling",c)},siblings:function(a){return m.sibling((a.parentNode||{}).firstChild,a)},children:function(a){return m.sibling(a.firstChild)},contents:function(a){return m.nodeName(a,"iframe")?a.contentDocument||a.contentWindow.document:m.merge([],a.childNodes)}},function(a,b){m.fn[a]=function(c,d){var e=m.map(this,b,c);return"Until"!==a.slice(-5)&&(d=c),d&&"string"==typeof d&&(e=m.filter(d,e)),this.length>1&&(C[a]||(e=m.unique(e)),B.test(a)&&(e=e.reverse())),this.pushStack(e)}});var E=/\S+/g,F={};function G(a){var b=F[a]={};return m.each(a.match(E)||[],function(a,c){b[c]=!0}),b}m.Callbacks=function(a){a="string"==typeof a?F[a]||G(a):m.extend({},a);var b,c,d,e,f,g,h=[],i=!a.once&&[],j=function(l){for(c=a.memory&&l,d=!0,f=g||0,g=0,e=h.length,b=!0;h&&e>f;f++)if(h[f].apply(l[0],l[1])===!1&&a.stopOnFalse){c=!1;break}b=!1,h&&(i?i.length&&j(i.shift()):c?h=[]:k.disable())},k={add:function(){if(h){var d=h.length;!function f(b){m.each(b,function(b,c){var d=m.type(c);"function"===d?a.unique&&k.has(c)||h.push(c):c&&c.length&&"string"!==d&&f(c)})}(arguments),b?e=h.length:c&&(g=d,j(c))}return this},remove:function(){return h&&m.each(arguments,function(a,c){var d;while((d=m.inArray(c,h,d))>-1)h.splice(d,1),b&&(e>=d&&e--,f>=d&&f--)}),this},has:function(a){return a?m.inArray(a,h)>-1:!(!h||!h.length)},empty:function(){return h=[],e=0,this},disable:function(){return h=i=c=void 0,this},disabled:function(){return!h},lock:function(){return i=void 0,c||k.disable(),this},locked:function(){return!i},fireWith:function(a,c){return!h||d&&!i||(c=c||[],c=[a,c.slice?c.slice():c],b?i.push(c):j(c)),this},fire:function(){return k.fireWith(this,arguments),this},fired:function(){return!!d}};return k},m.extend({Deferred:function(a){var b=[["resolve","done",m.Callbacks("once memory"),"resolved"],["reject","fail",m.Callbacks("once memory"),"rejected"],["notify","progress",m.Callbacks("memory")]],c="pending",d={state:function(){return c},always:function(){return e.done(arguments).fail(arguments),this},then:function(){var a=arguments;return m.Deferred(function(c){m.each(b,function(b,f){var g=m.isFunction(a[b])&&a[b];e[f[1]](function(){var a=g&&g.apply(this,arguments);a&&m.isFunction(a.promise)?a.promise().done(c.resolve).fail(c.reject).progress(c.notify):c[f[0]+"With"](this===d?c.promise():this,g?[a]:arguments)})}),a=null}).promise()},promise:function(a){return null!=a?m.extend(a,d):d}},e={};return d.pipe=d.then,m.each(b,function(a,f){var g=f[2],h=f[3];d[f[1]]=g.add,h&&g.add(function(){c=h},b[1^a][2].disable,b[2][2].lock),e[f[0]]=function(){return e[f[0]+"With"](this===e?d:this,arguments),this},e[f[0]+"With"]=g.fireWith}),d.promise(e),a&&a.call(e,e),e},when:function(a){var b=0,c=d.call(arguments),e=c.length,f=1!==e||a&&m.isFunction(a.promise)?e:0,g=1===f?a:m.Deferred(),h=function(a,b,c){return function(e){b[a]=this,c[a]=arguments.length>1?d.call(arguments):e,c===i?g.notifyWith(b,c):--f||g.resolveWith(b,c)}},i,j,k;if(e>1)for(i=new Array(e),j=new Array(e),k=new Array(e);e>b;b++)c[b]&&m.isFunction(c[b].promise)?c[b].promise().done(h(b,k,c)).fail(g.reject).progress(h(b,j,i)):--f;return f||g.resolveWith(k,c),g.promise()}});var H;m.fn.ready=function(a){return m.ready.promise().done(a),this},m.extend({isReady:!1,readyWait:1,holdReady:function(a){a?m.readyWait++:m.ready(!0)},ready:function(a){if(a===!0?!--m.readyWait:!m.isReady){if(!y.body)return setTimeout(m.ready);m.isReady=!0,a!==!0&&--m.readyWait>0||(H.resolveWith(y,[m]),m.fn.triggerHandler&&(m(y).triggerHandler("ready"),m(y).off("ready")))}}});function I(){y.addEventListener?(y.removeEventListener("DOMContentLoaded",J,!1),a.removeEventListener("load",J,!1)):(y.detachEvent("onreadystatechange",J),a.detachEvent("onload",J))}function J(){(y.addEventListener||"load"===event.type||"complete"===y.readyState)&&(I(),m.ready())}m.ready.promise=function(b){if(!H)if(H=m.Deferred(),"complete"===y.readyState)setTimeout(m.ready);else if(y.addEventListener)y.addEventListener("DOMContentLoaded",J,!1),a.addEventListener("load",J,!1);else{y.attachEvent("onreadystatechange",J),a.attachEvent("onload",J);var c=!1;try{c=null==a.frameElement&&y.documentElement}catch(d){}c&&c.doScroll&&!function e(){if(!m.isReady){try{c.doScroll("left")}catch(a){return setTimeout(e,50)}I(),m.ready()}}()}return H.promise(b)};var K="undefined",L;for(L in m(k))break;k.ownLast="0"!==L,k.inlineBlockNeedsLayout=!1,m(function(){var a,b,c,d;c=y.getElementsByTagName("body")[0],c&&c.style&&(b=y.createElement("div"),d=y.createElement("div"),d.style.cssText="position:absolute;border:0;width:0;height:0;top:0;left:-9999px",c.appendChild(d).appendChild(b),typeof b.style.zoom!==K&&(b.style.cssText="display:inline;margin:0;border:0;padding:1px;width:1px;zoom:1",k.inlineBlockNeedsLayout=a=3===b.offsetWidth,a&&(c.style.zoom=1)),c.removeChild(d))}),function(){var a=y.createElement("div");if(null==k.deleteExpando){k.deleteExpando=!0;try{delete a.test}catch(b){k.deleteExpando=!1}}a=null}(),m.acceptData=function(a){var b=m.noData[(a.nodeName+" ").toLowerCase()],c=+a.nodeType||1;return 1!==c&&9!==c?!1:!b||b!==!0&&a.getAttribute("classid")===b};var M=/^(?:\{[\w\W]*\}|\[[\w\W]*\])$/,N=/([A-Z])/g;function O(a,b,c){if(void 0===c&&1===a.nodeType){var d="data-"+b.replace(N,"-$1").toLowerCase();if(c=a.getAttribute(d),"string"==typeof c){try{c="true"===c?!0:"false"===c?!1:"null"===c?null:+c+""===c?+c:M.test(c)?m.parseJSON(c):c}catch(e){}m.data(a,b,c)}else c=void 0}return c}function P(a){var b;for(b in a)if(("data"!==b||!m.isEmptyObject(a[b]))&&"toJSON"!==b)return!1;

return!0}function Q(a,b,d,e){if(m.acceptData(a)){var f,g,h=m.expando,i=a.nodeType,j=i?m.cache:a,k=i?a[h]:a[h]&&h;if(k&&j[k]&&(e||j[k].data)||void 0!==d||"string"!=typeof b)return k||(k=i?a[h]=c.pop()||m.guid++:h),j[k]||(j[k]=i?{}:{toJSON:m.noop}),("object"==typeof b||"function"==typeof b)&&(e?j[k]=m.extend(j[k],b):j[k].data=m.extend(j[k].data,b)),g=j[k],e||(g.data||(g.data={}),g=g.data),void 0!==d&&(g[m.camelCase(b)]=d),"string"==typeof b?(f=g[b],null==f&&(f=g[m.camelCase(b)])):f=g,f}}function R(a,b,c){if(m.acceptData(a)){var d,e,f=a.nodeType,g=f?m.cache:a,h=f?a[m.expando]:m.expando;if(g[h]){if(b&&(d=c?g[h]:g[h].data)){m.isArray(b)?b=b.concat(m.map(b,m.camelCase)):b in d?b=[b]:(b=m.camelCase(b),b=b in d?[b]:b.split(" ")),e=b.length;while(e--)delete d[b[e]];if(c?!P(d):!m.isEmptyObject(d))return}(c||(delete g[h].data,P(g[h])))&&(f?m.cleanData([a],!0):k.deleteExpando||g!=g.window?delete g[h]:g[h]=null)}}}m.extend({cache:{},noData:{"applet ":!0,"embed ":!0,"object ":"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"},hasData:function(a){return a=a.nodeType?m.cache[a[m.expando]]:a[m.expando],!!a&&!P(a)},data:function(a,b,c){return Q(a,b,c)},removeData:function(a,b){return R(a,b)},_data:function(a,b,c){return Q(a,b,c,!0)},_removeData:function(a,b){return R(a,b,!0)}}),m.fn.extend({data:function(a,b){var c,d,e,f=this[0],g=f&&f.attributes;if(void 0===a){if(this.length&&(e=m.data(f),1===f.nodeType&&!m._data(f,"parsedAttrs"))){c=g.length;while(c--)g[c]&&(d=g[c].name,0===d.indexOf("data-")&&(d=m.camelCase(d.slice(5)),O(f,d,e[d])));m._data(f,"parsedAttrs",!0)}return e}return"object"==typeof a?this.each(function(){m.data(this,a)}):arguments.length>1?this.each(function(){m.data(this,a,b)}):f?O(f,a,m.data(f,a)):void 0},removeData:function(a){return this.each(function(){m.removeData(this,a)})}}),m.extend({queue:function(a,b,c){var d;return a?(b=(b||"fx")+"queue",d=m._data(a,b),c&&(!d||m.isArray(c)?d=m._data(a,b,m.makeArray(c)):d.push(c)),d||[]):void 0},dequeue:function(a,b){b=b||"fx";var c=m.queue(a,b),d=c.length,e=c.shift(),f=m._queueHooks(a,b),g=function(){m.dequeue(a,b)};"inprogress"===e&&(e=c.shift(),d--),e&&("fx"===b&&c.unshift("inprogress"),delete f.stop,e.call(a,g,f)),!d&&f&&f.empty.fire()},_queueHooks:function(a,b){var c=b+"queueHooks";return m._data(a,c)||m._data(a,c,{empty:m.Callbacks("once memory").add(function(){m._removeData(a,b+"queue"),m._removeData(a,c)})})}}),m.fn.extend({queue:function(a,b){var c=2;return"string"!=typeof a&&(b=a,a="fx",c--),arguments.length<c?m.queue(this[0],a):void 0===b?this:this.each(function(){var c=m.queue(this,a,b);m._queueHooks(this,a),"fx"===a&&"inprogress"!==c[0]&&m.dequeue(this,a)})},dequeue:function(a){return this.each(function(){m.dequeue(this,a)})},clearQueue:function(a){return this.queue(a||"fx",[])},promise:function(a,b){var c,d=1,e=m.Deferred(),f=this,g=this.length,h=function(){--d||e.resolveWith(f,[f])};"string"!=typeof a&&(b=a,a=void 0),a=a||"fx";while(g--)c=m._data(f[g],a+"queueHooks"),c&&c.empty&&(d++,c.empty.add(h));return h(),e.promise(b)}});var S=/[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source,T=["Top","Right","Bottom","Left"],U=function(a,b){return a=b||a,"none"===m.css(a,"display")||!m.contains(a.ownerDocument,a)},V=m.access=function(a,b,c,d,e,f,g){var h=0,i=a.length,j=null==c;if("object"===m.type(c)){e=!0;for(h in c)m.access(a,b,h,c[h],!0,f,g)}else if(void 0!==d&&(e=!0,m.isFunction(d)||(g=!0),j&&(g?(b.call(a,d),b=null):(j=b,b=function(a,b,c){return j.call(m(a),c)})),b))for(;i>h;h++)b(a[h],c,g?d:d.call(a[h],h,b(a[h],c)));return e?a:j?b.call(a):i?b(a[0],c):f},W=/^(?:checkbox|radio)$/i;!function(){var a=y.createElement("input"),b=y.createElement("div"),c=y.createDocumentFragment();if(b.innerHTML="  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>",k.leadingWhitespace=3===b.firstChild.nodeType,k.tbody=!b.getElementsByTagName("tbody").length,k.htmlSerialize=!!b.getElementsByTagName("link").length,k.html5Clone="<:nav></:nav>"!==y.createElement("nav").cloneNode(!0).outerHTML,a.type="checkbox",a.checked=!0,c.appendChild(a),k.appendChecked=a.checked,b.innerHTML="<textarea>x</textarea>",k.noCloneChecked=!!b.cloneNode(!0).lastChild.defaultValue,c.appendChild(b),b.innerHTML="<input type='radio' checked='checked' name='t'/>",k.checkClone=b.cloneNode(!0).cloneNode(!0).lastChild.checked,k.noCloneEvent=!0,b.attachEvent&&(b.attachEvent("onclick",function(){k.noCloneEvent=!1}),b.cloneNode(!0).click()),null==k.deleteExpando){k.deleteExpando=!0;try{delete b.test}catch(d){k.deleteExpando=!1}}}(),function(){var b,c,d=y.createElement("div");for(b in{submit:!0,change:!0,focusin:!0})c="on"+b,(k[b+"Bubbles"]=c in a)||(d.setAttribute(c,"t"),k[b+"Bubbles"]=d.attributes[c].expando===!1);d=null}();var X=/^(?:input|select|textarea)$/i,Y=/^key/,Z=/^(?:mouse|pointer|contextmenu)|click/,$=/^(?:focusinfocus|focusoutblur)$/,_=/^([^.]*)(?:\.(.+)|)$/;function aa(){return!0}function ba(){return!1}function ca(){try{return y.activeElement}catch(a){}}m.event={global:{},add:function(a,b,c,d,e){var f,g,h,i,j,k,l,n,o,p,q,r=m._data(a);if(r){c.handler&&(i=c,c=i.handler,e=i.selector),c.guid||(c.guid=m.guid++),(g=r.events)||(g=r.events={}),(k=r.handle)||(k=r.handle=function(a){return typeof m===K||a&&m.event.triggered===a.type?void 0:m.event.dispatch.apply(k.elem,arguments)},k.elem=a),b=(b||"").match(E)||[""],h=b.length;while(h--)f=_.exec(b[h])||[],o=q=f[1],p=(f[2]||"").split(".").sort(),o&&(j=m.event.special[o]||{},o=(e?j.delegateType:j.bindType)||o,j=m.event.special[o]||{},l=m.extend({type:o,origType:q,data:d,handler:c,guid:c.guid,selector:e,needsContext:e&&m.expr.match.needsContext.test(e),namespace:p.join(".")},i),(n=g[o])||(n=g[o]=[],n.delegateCount=0,j.setup&&j.setup.call(a,d,p,k)!==!1||(a.addEventListener?a.addEventListener(o,k,!1):a.attachEvent&&a.attachEvent("on"+o,k))),j.add&&(j.add.call(a,l),l.handler.guid||(l.handler.guid=c.guid)),e?n.splice(n.delegateCount++,0,l):n.push(l),m.event.global[o]=!0);a=null}},remove:function(a,b,c,d,e){var f,g,h,i,j,k,l,n,o,p,q,r=m.hasData(a)&&m._data(a);if(r&&(k=r.events)){b=(b||"").match(E)||[""],j=b.length;while(j--)if(h=_.exec(b[j])||[],o=q=h[1],p=(h[2]||"").split(".").sort(),o){l=m.event.special[o]||{},o=(d?l.delegateType:l.bindType)||o,n=k[o]||[],h=h[2]&&new RegExp("(^|\\.)"+p.join("\\.(?:.*\\.|)")+"(\\.|$)"),i=f=n.length;while(f--)g=n[f],!e&&q!==g.origType||c&&c.guid!==g.guid||h&&!h.test(g.namespace)||d&&d!==g.selector&&("**"!==d||!g.selector)||(n.splice(f,1),g.selector&&n.delegateCount--,l.remove&&l.remove.call(a,g));i&&!n.length&&(l.teardown&&l.teardown.call(a,p,r.handle)!==!1||m.removeEvent(a,o,r.handle),delete k[o])}else for(o in k)m.event.remove(a,o+b[j],c,d,!0);m.isEmptyObject(k)&&(delete r.handle,m._removeData(a,"events"))}},trigger:function(b,c,d,e){var f,g,h,i,k,l,n,o=[d||y],p=j.call(b,"type")?b.type:b,q=j.call(b,"namespace")?b.namespace.split("."):[];if(h=l=d=d||y,3!==d.nodeType&&8!==d.nodeType&&!$.test(p+m.event.triggered)&&(p.indexOf(".")>=0&&(q=p.split("."),p=q.shift(),q.sort()),g=p.indexOf(":")<0&&"on"+p,b=b[m.expando]?b:new m.Event(p,"object"==typeof b&&b),b.isTrigger=e?2:3,b.namespace=q.join("."),b.namespace_re=b.namespace?new RegExp("(^|\\.)"+q.join("\\.(?:.*\\.|)")+"(\\.|$)"):null,b.result=void 0,b.target||(b.target=d),c=null==c?[b]:m.makeArray(c,[b]),k=m.event.special[p]||{},e||!k.trigger||k.trigger.apply(d,c)!==!1)){if(!e&&!k.noBubble&&!m.isWindow(d)){for(i=k.delegateType||p,$.test(i+p)||(h=h.parentNode);h;h=h.parentNode)o.push(h),l=h;l===(d.ownerDocument||y)&&o.push(l.defaultView||l.parentWindow||a)}n=0;while((h=o[n++])&&!b.isPropagationStopped())b.type=n>1?i:k.bindType||p,f=(m._data(h,"events")||{})[b.type]&&m._data(h,"handle"),f&&f.apply(h,c),f=g&&h[g],f&&f.apply&&m.acceptData(h)&&(b.result=f.apply(h,c),b.result===!1&&b.preventDefault());if(b.type=p,!e&&!b.isDefaultPrevented()&&(!k._default||k._default.apply(o.pop(),c)===!1)&&m.acceptData(d)&&g&&d[p]&&!m.isWindow(d)){l=d[g],l&&(d[g]=null),m.event.triggered=p;try{d[p]()}catch(r){}m.event.triggered=void 0,l&&(d[g]=l)}return b.result}},dispatch:function(a){a=m.event.fix(a);var b,c,e,f,g,h=[],i=d.call(arguments),j=(m._data(this,"events")||{})[a.type]||[],k=m.event.special[a.type]||{};if(i[0]=a,a.delegateTarget=this,!k.preDispatch||k.preDispatch.call(this,a)!==!1){h=m.event.handlers.call(this,a,j),b=0;while((f=h[b++])&&!a.isPropagationStopped()){a.currentTarget=f.elem,g=0;while((e=f.handlers[g++])&&!a.isImmediatePropagationStopped())(!a.namespace_re||a.namespace_re.test(e.namespace))&&(a.handleObj=e,a.data=e.data,c=((m.event.special[e.origType]||{}).handle||e.handler).apply(f.elem,i),void 0!==c&&(a.result=c)===!1&&(a.preventDefault(),a.stopPropagation()))}return k.postDispatch&&k.postDispatch.call(this,a),a.result}},handlers:function(a,b){var c,d,e,f,g=[],h=b.delegateCount,i=a.target;if(h&&i.nodeType&&(!a.button||"click"!==a.type))for(;i!=this;i=i.parentNode||this)if(1===i.nodeType&&(i.disabled!==!0||"click"!==a.type)){for(e=[],f=0;h>f;f++)d=b[f],c=d.selector+" ",void 0===e[c]&&(e[c]=d.needsContext?m(c,this).index(i)>=0:m.find(c,this,null,[i]).length),e[c]&&e.push(d);e.length&&g.push({elem:i,handlers:e})}return h<b.length&&g.push({elem:this,handlers:b.slice(h)}),g},fix:function(a){if(a[m.expando])return a;var b,c,d,e=a.type,f=a,g=this.fixHooks[e];g||(this.fixHooks[e]=g=Z.test(e)?this.mouseHooks:Y.test(e)?this.keyHooks:{}),d=g.props?this.props.concat(g.props):this.props,a=new m.Event(f),b=d.length;while(b--)c=d[b],a[c]=f[c];return a.target||(a.target=f.srcElement||y),3===a.target.nodeType&&(a.target=a.target.parentNode),a.metaKey=!!a.metaKey,g.filter?g.filter(a,f):a},props:"altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),fixHooks:{},keyHooks:{props:"char charCode key keyCode".split(" "),filter:function(a,b){return null==a.which&&(a.which=null!=b.charCode?b.charCode:b.keyCode),a}},mouseHooks:{props:"button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),filter:function(a,b){var c,d,e,f=b.button,g=b.fromElement;return null==a.pageX&&null!=b.clientX&&(d=a.target.ownerDocument||y,e=d.documentElement,c=d.body,a.pageX=b.clientX+(e&&e.scrollLeft||c&&c.scrollLeft||0)-(e&&e.clientLeft||c&&c.clientLeft||0),a.pageY=b.clientY+(e&&e.scrollTop||c&&c.scrollTop||0)-(e&&e.clientTop||c&&c.clientTop||0)),!a.relatedTarget&&g&&(a.relatedTarget=g===a.target?b.toElement:g),a.which||void 0===f||(a.which=1&f?1:2&f?3:4&f?2:0),a}},special:{load:{noBubble:!0},focus:{trigger:function(){if(this!==ca()&&this.focus)try{return this.focus(),!1}catch(a){}},delegateType:"focusin"},blur:{trigger:function(){return this===ca()&&this.blur?(this.blur(),!1):void 0},delegateType:"focusout"},click:{trigger:function(){return m.nodeName(this,"input")&&"checkbox"===this.type&&this.click?(this.click(),!1):void 0},_default:function(a){return m.nodeName(a.target,"a")}},beforeunload:{postDispatch:function(a){void 0!==a.result&&a.originalEvent&&(a.originalEvent.returnValue=a.result)}}},simulate:function(a,b,c,d){var e=m.extend(new m.Event,c,{type:a,isSimulated:!0,originalEvent:{}});d?m.event.trigger(e,null,b):m.event.dispatch.call(b,e),e.isDefaultPrevented()&&c.preventDefault()}},m.removeEvent=y.removeEventListener?function(a,b,c){a.removeEventListener&&a.removeEventListener(b,c,!1)}:function(a,b,c){var d="on"+b;a.detachEvent&&(typeof a[d]===K&&(a[d]=null),a.detachEvent(d,c))},m.Event=function(a,b){return this instanceof m.Event?(a&&a.type?(this.originalEvent=a,this.type=a.type,this.isDefaultPrevented=a.defaultPrevented||void 0===a.defaultPrevented&&a.returnValue===!1?aa:ba):this.type=a,b&&m.extend(this,b),this.timeStamp=a&&a.timeStamp||m.now(),void(this[m.expando]=!0)):new m.Event(a,b)},m.Event.prototype={isDefaultPrevented:ba,isPropagationStopped:ba,isImmediatePropagationStopped:ba,preventDefault:function(){var a=this.originalEvent;this.isDefaultPrevented=aa,a&&(a.preventDefault?a.preventDefault():a.returnValue=!1)},stopPropagation:function(){var a=this.originalEvent;this.isPropagationStopped=aa,a&&(a.stopPropagation&&a.stopPropagation(),a.cancelBubble=!0)},stopImmediatePropagation:function(){var a=this.originalEvent;this.isImmediatePropagationStopped=aa,a&&a.stopImmediatePropagation&&a.stopImmediatePropagation(),this.stopPropagation()}},m.each({mouseenter:"mouseover",mouseleave:"mouseout",pointerenter:"pointerover",pointerleave:"pointerout"},function(a,b){m.event.special[a]={delegateType:b,bindType:b,handle:function(a){var c,d=this,e=a.relatedTarget,f=a.handleObj;return(!e||e!==d&&!m.contains(d,e))&&(a.type=f.origType,c=f.handler.apply(this,arguments),a.type=b),c}}}),k.submitBubbles||(m.event.special.submit={setup:function(){return m.nodeName(this,"form")?!1:void m.event.add(this,"click._submit keypress._submit",function(a){var b=a.target,c=m.nodeName(b,"input")||m.nodeName(b,"button")?b.form:void 0;c&&!m._data(c,"submitBubbles")&&(m.event.add(c,"submit._submit",function(a){a._submit_bubble=!0}),m._data(c,"submitBubbles",!0))})},postDispatch:function(a){a._submit_bubble&&(delete a._submit_bubble,this.parentNode&&!a.isTrigger&&m.event.simulate("submit",this.parentNode,a,!0))},teardown:function(){return m.nodeName(this,"form")?!1:void m.event.remove(this,"._submit")}}),k.changeBubbles||(m.event.special.change={setup:function(){return X.test(this.nodeName)?(("checkbox"===this.type||"radio"===this.type)&&(m.event.add(this,"propertychange._change",function(a){"checked"===a.originalEvent.propertyName&&(this._just_changed=!0)}),m.event.add(this,"click._change",function(a){this._just_changed&&!a.isTrigger&&(this._just_changed=!1),m.event.simulate("change",this,a,!0)})),!1):void m.event.add(this,"beforeactivate._change",function(a){var b=a.target;X.test(b.nodeName)&&!m._data(b,"changeBubbles")&&(m.event.add(b,"change._change",function(a){!this.parentNode||a.isSimulated||a.isTrigger||m.event.simulate("change",this.parentNode,a,!0)}),m._data(b,"changeBubbles",!0))})},handle:function(a){var b=a.target;return this!==b||a.isSimulated||a.isTrigger||"radio"!==b.type&&"checkbox"!==b.type?a.handleObj.handler.apply(this,arguments):void 0},teardown:function(){return m.event.remove(this,"._change"),!X.test(this.nodeName)}}),k.focusinBubbles||m.each({focus:"focusin",blur:"focusout"},function(a,b){var c=function(a){m.event.simulate(b,a.target,m.event.fix(a),!0)};m.event.special[b]={setup:function(){var d=this.ownerDocument||this,e=m._data(d,b);e||d.addEventListener(a,c,!0),m._data(d,b,(e||0)+1)},teardown:function(){var d=this.ownerDocument||this,e=m._data(d,b)-1;e?m._data(d,b,e):(d.removeEventListener(a,c,!0),m._removeData(d,b))}}}),m.fn.extend({on:function(a,b,c,d,e){var f,g;if("object"==typeof a){"string"!=typeof b&&(c=c||b,b=void 0);for(f in a)this.on(f,b,c,a[f],e);return this}if(null==c&&null==d?(d=b,c=b=void 0):null==d&&("string"==typeof b?(d=c,c=void 0):(d=c,c=b,b=void 0)),d===!1)d=ba;else if(!d)return this;return 1===e&&(g=d,d=function(a){return m().off(a),g.apply(this,arguments)},d.guid=g.guid||(g.guid=m.guid++)),this.each(function(){m.event.add(this,a,d,c,b)})},one:function(a,b,c,d){return this.on(a,b,c,d,1)},off:function(a,b,c){var d,e;if(a&&a.preventDefault&&a.handleObj)return d=a.handleObj,m(a.delegateTarget).off(d.namespace?d.origType+"."+d.namespace:d.origType,d.selector,d.handler),this;if("object"==typeof a){for(e in a)this.off(e,b,a[e]);return this}return(b===!1||"function"==typeof b)&&(c=b,b=void 0),c===!1&&(c=ba),this.each(function(){m.event.remove(this,a,c,b)})},trigger:function(a,b){return this.each(function(){m.event.trigger(a,b,this)})},triggerHandler:function(a,b){var c=this[0];return c?m.event.trigger(a,b,c,!0):void 0}});function da(a){var b=ea.split("|"),c=a.createDocumentFragment();if(c.createElement)while(b.length)c.createElement(b.pop());return c}var ea="abbr|article|aside|audio|bdi|canvas|data|datalist|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video",fa=/ jQuery\d+="(?:null|\d+)"/g,ga=new RegExp("<(?:"+ea+")[\\s/>]","i"),ha=/^\s+/,ia=/<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi,ja=/<([\w:]+)/,ka=/<tbody/i,la=/<|&#?\w+;/,ma=/<(?:script|style|link)/i,na=/checked\s*(?:[^=]|=\s*.checked.)/i,oa=/^$|\/(?:java|ecma)script/i,pa=/^true\/(.*)/,qa=/^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g,ra={option:[1,"<select multiple='multiple'>","</select>"],legend:[1,"<fieldset>","</fieldset>"],area:[1,"<map>","</map>"],param:[1,"<object>","</object>"],thead:[1,"<table>","</table>"],tr:[2,"<table><tbody>","</tbody></table>"],col:[2,"<table><tbody></tbody><colgroup>","</colgroup></table>"],td:[3,"<table><tbody><tr>","</tr></tbody></table>"],_default:k.htmlSerialize?[0,"",""]:[1,"X<div>","</div>"]},sa=da(y),ta=sa.appendChild(y.createElement("div"));ra.optgroup=ra.option,ra.tbody=ra.tfoot=ra.colgroup=ra.caption=ra.thead,ra.th=ra.td;function ua(a,b){var c,d,e=0,f=typeof a.getElementsByTagName!==K?a.getElementsByTagName(b||"*"):typeof a.querySelectorAll!==K?a.querySelectorAll(b||"*"):void 0;if(!f)for(f=[],c=a.childNodes||a;null!=(d=c[e]);e++)!b||m.nodeName(d,b)?f.push(d):m.merge(f,ua(d,b));return void 0===b||b&&m.nodeName(a,b)?m.merge([a],f):f}function va(a){W.test(a.type)&&(a.defaultChecked=a.checked)}function wa(a,b){return m.nodeName(a,"table")&&m.nodeName(11!==b.nodeType?b:b.firstChild,"tr")?a.getElementsByTagName("tbody")[0]||a.appendChild(a.ownerDocument.createElement("tbody")):a}function xa(a){return a.type=(null!==m.find.attr(a,"type"))+"/"+a.type,a}function ya(a){var b=pa.exec(a.type);return b?a.type=b[1]:a.removeAttribute("type"),a}function za(a,b){for(var c,d=0;null!=(c=a[d]);d++)m._data(c,"globalEval",!b||m._data(b[d],"globalEval"))}function Aa(a,b){if(1===b.nodeType&&m.hasData(a)){var c,d,e,f=m._data(a),g=m._data(b,f),h=f.events;if(h){delete g.handle,g.events={};for(c in h)for(d=0,e=h[c].length;e>d;d++)m.event.add(b,c,h[c][d])}g.data&&(g.data=m.extend({},g.data))}}function Ba(a,b){var c,d,e;if(1===b.nodeType){if(c=b.nodeName.toLowerCase(),!k.noCloneEvent&&b[m.expando]){e=m._data(b);for(d in e.events)m.removeEvent(b,d,e.handle);b.removeAttribute(m.expando)}"script"===c&&b.text!==a.text?(xa(b).text=a.text,ya(b)):"object"===c?(b.parentNode&&(b.outerHTML=a.outerHTML),k.html5Clone&&a.innerHTML&&!m.trim(b.innerHTML)&&(b.innerHTML=a.innerHTML)):"input"===c&&W.test(a.type)?(b.defaultChecked=b.checked=a.checked,b.value!==a.value&&(b.value=a.value)):"option"===c?b.defaultSelected=b.selected=a.defaultSelected:("input"===c||"textarea"===c)&&(b.defaultValue=a.defaultValue)}}m.extend({clone:function(a,b,c){var d,e,f,g,h,i=m.contains(a.ownerDocument,a);if(k.html5Clone||m.isXMLDoc(a)||!ga.test("<"+a.nodeName+">")?f=a.cloneNode(!0):(ta.innerHTML=a.outerHTML,ta.removeChild(f=ta.firstChild)),!(k.noCloneEvent&&k.noCloneChecked||1!==a.nodeType&&11!==a.nodeType||m.isXMLDoc(a)))for(d=ua(f),h=ua(a),g=0;null!=(e=h[g]);++g)d[g]&&Ba(e,d[g]);if(b)if(c)for(h=h||ua(a),d=d||ua(f),g=0;null!=(e=h[g]);g++)Aa(e,d[g]);else Aa(a,f);return d=ua(f,"script"),d.length>0&&za(d,!i&&ua(a,"script")),d=h=e=null,f},buildFragment:function(a,b,c,d){for(var e,f,g,h,i,j,l,n=a.length,o=da(b),p=[],q=0;n>q;q++)if(f=a[q],f||0===f)if("object"===m.type(f))m.merge(p,f.nodeType?[f]:f);else if(la.test(f)){h=h||o.appendChild(b.createElement("div")),i=(ja.exec(f)||["",""])[1].toLowerCase(),l=ra[i]||ra._default,h.innerHTML=l[1]+f.replace(ia,"<$1></$2>")+l[2],e=l[0];while(e--)h=h.lastChild;if(!k.leadingWhitespace&&ha.test(f)&&p.push(b.createTextNode(ha.exec(f)[0])),!k.tbody){f="table"!==i||ka.test(f)?"<table>"!==l[1]||ka.test(f)?0:h:h.firstChild,e=f&&f.childNodes.length;while(e--)m.nodeName(j=f.childNodes[e],"tbody")&&!j.childNodes.length&&f.removeChild(j)}m.merge(p,h.childNodes),h.textContent="";while(h.firstChild)h.removeChild(h.firstChild);h=o.lastChild}else p.push(b.createTextNode(f));h&&o.removeChild(h),k.appendChecked||m.grep(ua(p,"input"),va),q=0;while(f=p[q++])if((!d||-1===m.inArray(f,d))&&(g=m.contains(f.ownerDocument,f),h=ua(o.appendChild(f),"script"),g&&za(h),c)){e=0;while(f=h[e++])oa.test(f.type||"")&&c.push(f)}return h=null,o},cleanData:function(a,b){for(var d,e,f,g,h=0,i=m.expando,j=m.cache,l=k.deleteExpando,n=m.event.special;null!=(d=a[h]);h++)if((b||m.acceptData(d))&&(f=d[i],g=f&&j[f])){if(g.events)for(e in g.events)n[e]?m.event.remove(d,e):m.removeEvent(d,e,g.handle);j[f]&&(delete j[f],l?delete d[i]:typeof d.removeAttribute!==K?d.removeAttribute(i):d[i]=null,c.push(f))}}}),m.fn.extend({text:function(a){return V(this,function(a){return void 0===a?m.text(this):this.empty().append((this[0]&&this[0].ownerDocument||y).createTextNode(a))},null,a,arguments.length)},append:function(){return this.domManip(arguments,function(a){if(1===this.nodeType||11===this.nodeType||9===this.nodeType){var b=wa(this,a);b.appendChild(a)}})},prepend:function(){return this.domManip(arguments,function(a){if(1===this.nodeType||11===this.nodeType||9===this.nodeType){var b=wa(this,a);b.insertBefore(a,b.firstChild)}})},before:function(){return this.domManip(arguments,function(a){this.parentNode&&this.parentNode.insertBefore(a,this)})},after:function(){return this.domManip(arguments,function(a){this.parentNode&&this.parentNode.insertBefore(a,this.nextSibling)})},remove:function(a,b){for(var c,d=a?m.filter(a,this):this,e=0;null!=(c=d[e]);e++)b||1!==c.nodeType||m.cleanData(ua(c)),c.parentNode&&(b&&m.contains(c.ownerDocument,c)&&za(ua(c,"script")),c.parentNode.removeChild(c));return this},empty:function(){for(var a,b=0;null!=(a=this[b]);b++){1===a.nodeType&&m.cleanData(ua(a,!1));while(a.firstChild)a.removeChild(a.firstChild);a.options&&m.nodeName(a,"select")&&(a.options.length=0)}return this},clone:function(a,b){return a=null==a?!1:a,b=null==b?a:b,this.map(function(){return m.clone(this,a,b)})},html:function(a){return V(this,function(a){var b=this[0]||{},c=0,d=this.length;if(void 0===a)return 1===b.nodeType?b.innerHTML.replace(fa,""):void 0;if(!("string"!=typeof a||ma.test(a)||!k.htmlSerialize&&ga.test(a)||!k.leadingWhitespace&&ha.test(a)||ra[(ja.exec(a)||["",""])[1].toLowerCase()])){a=a.replace(ia,"<$1></$2>");try{for(;d>c;c++)b=this[c]||{},1===b.nodeType&&(m.cleanData(ua(b,!1)),b.innerHTML=a);b=0}catch(e){}}b&&this.empty().append(a)},null,a,arguments.length)},replaceWith:function(){var a=arguments[0];return this.domManip(arguments,function(b){a=this.parentNode,m.cleanData(ua(this)),a&&a.replaceChild(b,this)}),a&&(a.length||a.nodeType)?this:this.remove()},detach:function(a){return this.remove(a,!0)},domManip:function(a,b){a=e.apply([],a);var c,d,f,g,h,i,j=0,l=this.length,n=this,o=l-1,p=a[0],q=m.isFunction(p);if(q||l>1&&"string"==typeof p&&!k.checkClone&&na.test(p))return this.each(function(c){var d=n.eq(c);q&&(a[0]=p.call(this,c,d.html())),d.domManip(a,b)});if(l&&(i=m.buildFragment(a,this[0].ownerDocument,!1,this),c=i.firstChild,1===i.childNodes.length&&(i=c),c)){for(g=m.map(ua(i,"script"),xa),f=g.length;l>j;j++)d=i,j!==o&&(d=m.clone(d,!0,!0),f&&m.merge(g,ua(d,"script"))),b.call(this[j],d,j);if(f)for(h=g[g.length-1].ownerDocument,m.map(g,ya),j=0;f>j;j++)d=g[j],oa.test(d.type||"")&&!m._data(d,"globalEval")&&m.contains(h,d)&&(d.src?m._evalUrl&&m._evalUrl(d.src):m.globalEval((d.text||d.textContent||d.innerHTML||"").replace(qa,"")));i=c=null}return this}}),m.each({appendTo:"append",prependTo:"prepend",insertBefore:"before",insertAfter:"after",replaceAll:"replaceWith"},function(a,b){m.fn[a]=function(a){for(var c,d=0,e=[],g=m(a),h=g.length-1;h>=d;d++)c=d===h?this:this.clone(!0),m(g[d])[b](c),f.apply(e,c.get());return this.pushStack(e)}});var Ca,Da={};function Ea(b,c){var d,e=m(c.createElement(b)).appendTo(c.body),f=a.getDefaultComputedStyle&&(d=a.getDefaultComputedStyle(e[0]))?d.display:m.css(e[0],"display");return e.detach(),f}function Fa(a){var b=y,c=Da[a];return c||(c=Ea(a,b),"none"!==c&&c||(Ca=(Ca||m("<iframe frameborder='0' width='0' height='0'/>")).appendTo(b.documentElement),b=(Ca[0].contentWindow||Ca[0].contentDocument).document,b.write(),b.close(),c=Ea(a,b),Ca.detach()),Da[a]=c),c}!function(){var a;k.shrinkWrapBlocks=function(){if(null!=a)return a;a=!1;var b,c,d;return c=y.getElementsByTagName("body")[0],c&&c.style?(b=y.createElement("div"),d=y.createElement("div"),d.style.cssText="position:absolute;border:0;width:0;height:0;top:0;left:-9999px",c.appendChild(d).appendChild(b),typeof b.style.zoom!==K&&(b.style.cssText="-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:1px;width:1px;zoom:1",b.appendChild(y.createElement("div")).style.width="5px",a=3!==b.offsetWidth),c.removeChild(d),a):void 0}}();var Ga=/^margin/,Ha=new RegExp("^("+S+")(?!px)[a-z%]+$","i"),Ia,Ja,Ka=/^(top|right|bottom|left)$/;a.getComputedStyle?(Ia=function(b){return b.ownerDocument.defaultView.opener?b.ownerDocument.defaultView.getComputedStyle(b,null):a.getComputedStyle(b,null)},Ja=function(a,b,c){var d,e,f,g,h=a.style;return c=c||Ia(a),g=c?c.getPropertyValue(b)||c[b]:void 0,c&&(""!==g||m.contains(a.ownerDocument,a)||(g=m.style(a,b)),Ha.test(g)&&Ga.test(b)&&(d=h.width,e=h.minWidth,f=h.maxWidth,h.minWidth=h.maxWidth=h.width=g,g=c.width,h.width=d,h.minWidth=e,h.maxWidth=f)),void 0===g?g:g+""}):y.documentElement.currentStyle&&(Ia=function(a){return a.currentStyle},Ja=function(a,b,c){var d,e,f,g,h=a.style;return c=c||Ia(a),g=c?c[b]:void 0,null==g&&h&&h[b]&&(g=h[b]),Ha.test(g)&&!Ka.test(b)&&(d=h.left,e=a.runtimeStyle,f=e&&e.left,f&&(e.left=a.currentStyle.left),h.left="fontSize"===b?"1em":g,g=h.pixelLeft+"px",h.left=d,f&&(e.left=f)),void 0===g?g:g+""||"auto"});function La(a,b){return{get:function(){var c=a();if(null!=c)return c?void delete this.get:(this.get=b).apply(this,arguments)}}}!function(){var b,c,d,e,f,g,h;if(b=y.createElement("div"),b.innerHTML="  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>",d=b.getElementsByTagName("a")[0],c=d&&d.style){c.cssText="float:left;opacity:.5",k.opacity="0.5"===c.opacity,k.cssFloat=!!c.cssFloat,b.style.backgroundClip="content-box",b.cloneNode(!0).style.backgroundClip="",k.clearCloneStyle="content-box"===b.style.backgroundClip,k.boxSizing=""===c.boxSizing||""===c.MozBoxSizing||""===c.WebkitBoxSizing,m.extend(k,{reliableHiddenOffsets:function(){return null==g&&i(),g},boxSizingReliable:function(){return null==f&&i(),f},pixelPosition:function(){return null==e&&i(),e},reliableMarginRight:function(){return null==h&&i(),h}});function i(){var b,c,d,i;c=y.getElementsByTagName("body")[0],c&&c.style&&(b=y.createElement("div"),d=y.createElement("div"),d.style.cssText="position:absolute;border:0;width:0;height:0;top:0;left:-9999px",c.appendChild(d).appendChild(b),b.style.cssText="-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;display:block;margin-top:1%;top:1%;border:1px;padding:1px;width:4px;position:absolute",e=f=!1,h=!0,a.getComputedStyle&&(e="1%"!==(a.getComputedStyle(b,null)||{}).top,f="4px"===(a.getComputedStyle(b,null)||{width:"4px"}).width,i=b.appendChild(y.createElement("div")),i.style.cssText=b.style.cssText="-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:0",i.style.marginRight=i.style.width="0",b.style.width="1px",h=!parseFloat((a.getComputedStyle(i,null)||{}).marginRight),b.removeChild(i)),b.innerHTML="<table><tr><td></td><td>t</td></tr></table>",i=b.getElementsByTagName("td"),i[0].style.cssText="margin:0;border:0;padding:0;display:none",g=0===i[0].offsetHeight,g&&(i[0].style.display="",i[1].style.display="none",g=0===i[0].offsetHeight),c.removeChild(d))}}}(),m.swap=function(a,b,c,d){var e,f,g={};for(f in b)g[f]=a.style[f],a.style[f]=b[f];e=c.apply(a,d||[]);for(f in b)a.style[f]=g[f];return e};var Ma=/alpha\([^)]*\)/i,Na=/opacity\s*=\s*([^)]*)/,Oa=/^(none|table(?!-c[ea]).+)/,Pa=new RegExp("^("+S+")(.*)$","i"),Qa=new RegExp("^([+-])=("+S+")","i"),Ra={position:"absolute",visibility:"hidden",display:"block"},Sa={letterSpacing:"0",fontWeight:"400"},Ta=["Webkit","O","Moz","ms"];function Ua(a,b){if(b in a)return b;var c=b.charAt(0).toUpperCase()+b.slice(1),d=b,e=Ta.length;while(e--)if(b=Ta[e]+c,b in a)return b;return d}function Va(a,b){for(var c,d,e,f=[],g=0,h=a.length;h>g;g++)d=a[g],d.style&&(f[g]=m._data(d,"olddisplay"),c=d.style.display,b?(f[g]||"none"!==c||(d.style.display=""),""===d.style.display&&U(d)&&(f[g]=m._data(d,"olddisplay",Fa(d.nodeName)))):(e=U(d),(c&&"none"!==c||!e)&&m._data(d,"olddisplay",e?c:m.css(d,"display"))));for(g=0;h>g;g++)d=a[g],d.style&&(b&&"none"!==d.style.display&&""!==d.style.display||(d.style.display=b?f[g]||"":"none"));return a}function Wa(a,b,c){var d=Pa.exec(b);return d?Math.max(0,d[1]-(c||0))+(d[2]||"px"):b}function Xa(a,b,c,d,e){for(var f=c===(d?"border":"content")?4:"width"===b?1:0,g=0;4>f;f+=2)"margin"===c&&(g+=m.css(a,c+T[f],!0,e)),d?("content"===c&&(g-=m.css(a,"padding"+T[f],!0,e)),"margin"!==c&&(g-=m.css(a,"border"+T[f]+"Width",!0,e))):(g+=m.css(a,"padding"+T[f],!0,e),"padding"!==c&&(g+=m.css(a,"border"+T[f]+"Width",!0,e)));return g}function Ya(a,b,c){var d=!0,e="width"===b?a.offsetWidth:a.offsetHeight,f=Ia(a),g=k.boxSizing&&"border-box"===m.css(a,"boxSizing",!1,f);if(0>=e||null==e){if(e=Ja(a,b,f),(0>e||null==e)&&(e=a.style[b]),Ha.test(e))return e;d=g&&(k.boxSizingReliable()||e===a.style[b]),e=parseFloat(e)||0}return e+Xa(a,b,c||(g?"border":"content"),d,f)+"px"}m.extend({cssHooks:{opacity:{get:function(a,b){if(b){var c=Ja(a,"opacity");return""===c?"1":c}}}},cssNumber:{columnCount:!0,fillOpacity:!0,flexGrow:!0,flexShrink:!0,fontWeight:!0,lineHeight:!0,opacity:!0,order:!0,orphans:!0,widows:!0,zIndex:!0,zoom:!0},cssProps:{"float":k.cssFloat?"cssFloat":"styleFloat"},style:function(a,b,c,d){if(a&&3!==a.nodeType&&8!==a.nodeType&&a.style){var e,f,g,h=m.camelCase(b),i=a.style;if(b=m.cssProps[h]||(m.cssProps[h]=Ua(i,h)),g=m.cssHooks[b]||m.cssHooks[h],void 0===c)return g&&"get"in g&&void 0!==(e=g.get(a,!1,d))?e:i[b];if(f=typeof c,"string"===f&&(e=Qa.exec(c))&&(c=(e[1]+1)*e[2]+parseFloat(m.css(a,b)),f="number"),null!=c&&c===c&&("number"!==f||m.cssNumber[h]||(c+="px"),k.clearCloneStyle||""!==c||0!==b.indexOf("background")||(i[b]="inherit"),!(g&&"set"in g&&void 0===(c=g.set(a,c,d)))))try{i[b]=c}catch(j){}}},css:function(a,b,c,d){var e,f,g,h=m.camelCase(b);return b=m.cssProps[h]||(m.cssProps[h]=Ua(a.style,h)),g=m.cssHooks[b]||m.cssHooks[h],g&&"get"in g&&(f=g.get(a,!0,c)),void 0===f&&(f=Ja(a,b,d)),"normal"===f&&b in Sa&&(f=Sa[b]),""===c||c?(e=parseFloat(f),c===!0||m.isNumeric(e)?e||0:f):f}}),m.each(["height","width"],function(a,b){m.cssHooks[b]={get:function(a,c,d){return c?Oa.test(m.css(a,"display"))&&0===a.offsetWidth?m.swap(a,Ra,function(){return Ya(a,b,d)}):Ya(a,b,d):void 0},set:function(a,c,d){var e=d&&Ia(a);return Wa(a,c,d?Xa(a,b,d,k.boxSizing&&"border-box"===m.css(a,"boxSizing",!1,e),e):0)}}}),k.opacity||(m.cssHooks.opacity={get:function(a,b){return Na.test((b&&a.currentStyle?a.currentStyle.filter:a.style.filter)||"")?.01*parseFloat(RegExp.$1)+"":b?"1":""},set:function(a,b){var c=a.style,d=a.currentStyle,e=m.isNumeric(b)?"alpha(opacity="+100*b+")":"",f=d&&d.filter||c.filter||"";c.zoom=1,(b>=1||""===b)&&""===m.trim(f.replace(Ma,""))&&c.removeAttribute&&(c.removeAttribute("filter"),""===b||d&&!d.filter)||(c.filter=Ma.test(f)?f.replace(Ma,e):f+" "+e)}}),m.cssHooks.marginRight=La(k.reliableMarginRight,function(a,b){return b?m.swap(a,{display:"inline-block"},Ja,[a,"marginRight"]):void 0}),m.each({margin:"",padding:"",border:"Width"},function(a,b){m.cssHooks[a+b]={expand:function(c){for(var d=0,e={},f="string"==typeof c?c.split(" "):[c];4>d;d++)e[a+T[d]+b]=f[d]||f[d-2]||f[0];return e}},Ga.test(a)||(m.cssHooks[a+b].set=Wa)}),m.fn.extend({css:function(a,b){return V(this,function(a,b,c){var d,e,f={},g=0;if(m.isArray(b)){for(d=Ia(a),e=b.length;e>g;g++)f[b[g]]=m.css(a,b[g],!1,d);return f}return void 0!==c?m.style(a,b,c):m.css(a,b)},a,b,arguments.length>1)},show:function(){return Va(this,!0)},hide:function(){return Va(this)},toggle:function(a){return"boolean"==typeof a?a?this.show():this.hide():this.each(function(){U(this)?m(this).show():m(this).hide()})}});function Za(a,b,c,d,e){
return new Za.prototype.init(a,b,c,d,e)}m.Tween=Za,Za.prototype={constructor:Za,init:function(a,b,c,d,e,f){this.elem=a,this.prop=c,this.easing=e||"swing",this.options=b,this.start=this.now=this.cur(),this.end=d,this.unit=f||(m.cssNumber[c]?"":"px")},cur:function(){var a=Za.propHooks[this.prop];return a&&a.get?a.get(this):Za.propHooks._default.get(this)},run:function(a){var b,c=Za.propHooks[this.prop];return this.options.duration?this.pos=b=m.easing[this.easing](a,this.options.duration*a,0,1,this.options.duration):this.pos=b=a,this.now=(this.end-this.start)*b+this.start,this.options.step&&this.options.step.call(this.elem,this.now,this),c&&c.set?c.set(this):Za.propHooks._default.set(this),this}},Za.prototype.init.prototype=Za.prototype,Za.propHooks={_default:{get:function(a){var b;return null==a.elem[a.prop]||a.elem.style&&null!=a.elem.style[a.prop]?(b=m.css(a.elem,a.prop,""),b&&"auto"!==b?b:0):a.elem[a.prop]},set:function(a){m.fx.step[a.prop]?m.fx.step[a.prop](a):a.elem.style&&(null!=a.elem.style[m.cssProps[a.prop]]||m.cssHooks[a.prop])?m.style(a.elem,a.prop,a.now+a.unit):a.elem[a.prop]=a.now}}},Za.propHooks.scrollTop=Za.propHooks.scrollLeft={set:function(a){a.elem.nodeType&&a.elem.parentNode&&(a.elem[a.prop]=a.now)}},m.easing={linear:function(a){return a},swing:function(a){return.5-Math.cos(a*Math.PI)/2}},m.fx=Za.prototype.init,m.fx.step={};var $a,_a,ab=/^(?:toggle|show|hide)$/,bb=new RegExp("^(?:([+-])=|)("+S+")([a-z%]*)$","i"),cb=/queueHooks$/,db=[ib],eb={"*":[function(a,b){var c=this.createTween(a,b),d=c.cur(),e=bb.exec(b),f=e&&e[3]||(m.cssNumber[a]?"":"px"),g=(m.cssNumber[a]||"px"!==f&&+d)&&bb.exec(m.css(c.elem,a)),h=1,i=20;if(g&&g[3]!==f){f=f||g[3],e=e||[],g=+d||1;do h=h||".5",g/=h,m.style(c.elem,a,g+f);while(h!==(h=c.cur()/d)&&1!==h&&--i)}return e&&(g=c.start=+g||+d||0,c.unit=f,c.end=e[1]?g+(e[1]+1)*e[2]:+e[2]),c}]};function fb(){return setTimeout(function(){$a=void 0}),$a=m.now()}function gb(a,b){var c,d={height:a},e=0;for(b=b?1:0;4>e;e+=2-b)c=T[e],d["margin"+c]=d["padding"+c]=a;return b&&(d.opacity=d.width=a),d}function hb(a,b,c){for(var d,e=(eb[b]||[]).concat(eb["*"]),f=0,g=e.length;g>f;f++)if(d=e[f].call(c,b,a))return d}function ib(a,b,c){var d,e,f,g,h,i,j,l,n=this,o={},p=a.style,q=a.nodeType&&U(a),r=m._data(a,"fxshow");c.queue||(h=m._queueHooks(a,"fx"),null==h.unqueued&&(h.unqueued=0,i=h.empty.fire,h.empty.fire=function(){h.unqueued||i()}),h.unqueued++,n.always(function(){n.always(function(){h.unqueued--,m.queue(a,"fx").length||h.empty.fire()})})),1===a.nodeType&&("height"in b||"width"in b)&&(c.overflow=[p.overflow,p.overflowX,p.overflowY],j=m.css(a,"display"),l="none"===j?m._data(a,"olddisplay")||Fa(a.nodeName):j,"inline"===l&&"none"===m.css(a,"float")&&(k.inlineBlockNeedsLayout&&"inline"!==Fa(a.nodeName)?p.zoom=1:p.display="inline-block")),c.overflow&&(p.overflow="hidden",k.shrinkWrapBlocks()||n.always(function(){p.overflow=c.overflow[0],p.overflowX=c.overflow[1],p.overflowY=c.overflow[2]}));for(d in b)if(e=b[d],ab.exec(e)){if(delete b[d],f=f||"toggle"===e,e===(q?"hide":"show")){if("show"!==e||!r||void 0===r[d])continue;q=!0}o[d]=r&&r[d]||m.style(a,d)}else j=void 0;if(m.isEmptyObject(o))"inline"===("none"===j?Fa(a.nodeName):j)&&(p.display=j);else{r?"hidden"in r&&(q=r.hidden):r=m._data(a,"fxshow",{}),f&&(r.hidden=!q),q?m(a).show():n.done(function(){m(a).hide()}),n.done(function(){var b;m._removeData(a,"fxshow");for(b in o)m.style(a,b,o[b])});for(d in o)g=hb(q?r[d]:0,d,n),d in r||(r[d]=g.start,q&&(g.end=g.start,g.start="width"===d||"height"===d?1:0))}}function jb(a,b){var c,d,e,f,g;for(c in a)if(d=m.camelCase(c),e=b[d],f=a[c],m.isArray(f)&&(e=f[1],f=a[c]=f[0]),c!==d&&(a[d]=f,delete a[c]),g=m.cssHooks[d],g&&"expand"in g){f=g.expand(f),delete a[d];for(c in f)c in a||(a[c]=f[c],b[c]=e)}else b[d]=e}function kb(a,b,c){var d,e,f=0,g=db.length,h=m.Deferred().always(function(){delete i.elem}),i=function(){if(e)return!1;for(var b=$a||fb(),c=Math.max(0,j.startTime+j.duration-b),d=c/j.duration||0,f=1-d,g=0,i=j.tweens.length;i>g;g++)j.tweens[g].run(f);return h.notifyWith(a,[j,f,c]),1>f&&i?c:(h.resolveWith(a,[j]),!1)},j=h.promise({elem:a,props:m.extend({},b),opts:m.extend(!0,{specialEasing:{}},c),originalProperties:b,originalOptions:c,startTime:$a||fb(),duration:c.duration,tweens:[],createTween:function(b,c){var d=m.Tween(a,j.opts,b,c,j.opts.specialEasing[b]||j.opts.easing);return j.tweens.push(d),d},stop:function(b){var c=0,d=b?j.tweens.length:0;if(e)return this;for(e=!0;d>c;c++)j.tweens[c].run(1);return b?h.resolveWith(a,[j,b]):h.rejectWith(a,[j,b]),this}}),k=j.props;for(jb(k,j.opts.specialEasing);g>f;f++)if(d=db[f].call(j,a,k,j.opts))return d;return m.map(k,hb,j),m.isFunction(j.opts.start)&&j.opts.start.call(a,j),m.fx.timer(m.extend(i,{elem:a,anim:j,queue:j.opts.queue})),j.progress(j.opts.progress).done(j.opts.done,j.opts.complete).fail(j.opts.fail).always(j.opts.always)}m.Animation=m.extend(kb,{tweener:function(a,b){m.isFunction(a)?(b=a,a=["*"]):a=a.split(" ");for(var c,d=0,e=a.length;e>d;d++)c=a[d],eb[c]=eb[c]||[],eb[c].unshift(b)},prefilter:function(a,b){b?db.unshift(a):db.push(a)}}),m.speed=function(a,b,c){var d=a&&"object"==typeof a?m.extend({},a):{complete:c||!c&&b||m.isFunction(a)&&a,duration:a,easing:c&&b||b&&!m.isFunction(b)&&b};return d.duration=m.fx.off?0:"number"==typeof d.duration?d.duration:d.duration in m.fx.speeds?m.fx.speeds[d.duration]:m.fx.speeds._default,(null==d.queue||d.queue===!0)&&(d.queue="fx"),d.old=d.complete,d.complete=function(){m.isFunction(d.old)&&d.old.call(this),d.queue&&m.dequeue(this,d.queue)},d},m.fn.extend({fadeTo:function(a,b,c,d){return this.filter(U).css("opacity",0).show().end().animate({opacity:b},a,c,d)},animate:function(a,b,c,d){var e=m.isEmptyObject(a),f=m.speed(b,c,d),g=function(){var b=kb(this,m.extend({},a),f);(e||m._data(this,"finish"))&&b.stop(!0)};return g.finish=g,e||f.queue===!1?this.each(g):this.queue(f.queue,g)},stop:function(a,b,c){var d=function(a){var b=a.stop;delete a.stop,b(c)};return"string"!=typeof a&&(c=b,b=a,a=void 0),b&&a!==!1&&this.queue(a||"fx",[]),this.each(function(){var b=!0,e=null!=a&&a+"queueHooks",f=m.timers,g=m._data(this);if(e)g[e]&&g[e].stop&&d(g[e]);else for(e in g)g[e]&&g[e].stop&&cb.test(e)&&d(g[e]);for(e=f.length;e--;)f[e].elem!==this||null!=a&&f[e].queue!==a||(f[e].anim.stop(c),b=!1,f.splice(e,1));(b||!c)&&m.dequeue(this,a)})},finish:function(a){return a!==!1&&(a=a||"fx"),this.each(function(){var b,c=m._data(this),d=c[a+"queue"],e=c[a+"queueHooks"],f=m.timers,g=d?d.length:0;for(c.finish=!0,m.queue(this,a,[]),e&&e.stop&&e.stop.call(this,!0),b=f.length;b--;)f[b].elem===this&&f[b].queue===a&&(f[b].anim.stop(!0),f.splice(b,1));for(b=0;g>b;b++)d[b]&&d[b].finish&&d[b].finish.call(this);delete c.finish})}}),m.each(["toggle","show","hide"],function(a,b){var c=m.fn[b];m.fn[b]=function(a,d,e){return null==a||"boolean"==typeof a?c.apply(this,arguments):this.animate(gb(b,!0),a,d,e)}}),m.each({slideDown:gb("show"),slideUp:gb("hide"),slideToggle:gb("toggle"),fadeIn:{opacity:"show"},fadeOut:{opacity:"hide"},fadeToggle:{opacity:"toggle"}},function(a,b){m.fn[a]=function(a,c,d){return this.animate(b,a,c,d)}}),m.timers=[],m.fx.tick=function(){var a,b=m.timers,c=0;for($a=m.now();c<b.length;c++)a=b[c],a()||b[c]!==a||b.splice(c--,1);b.length||m.fx.stop(),$a=void 0},m.fx.timer=function(a){m.timers.push(a),a()?m.fx.start():m.timers.pop()},m.fx.interval=13,m.fx.start=function(){_a||(_a=setInterval(m.fx.tick,m.fx.interval))},m.fx.stop=function(){clearInterval(_a),_a=null},m.fx.speeds={slow:600,fast:200,_default:400},m.fn.delay=function(a,b){return a=m.fx?m.fx.speeds[a]||a:a,b=b||"fx",this.queue(b,function(b,c){var d=setTimeout(b,a);c.stop=function(){clearTimeout(d)}})},function(){var a,b,c,d,e;b=y.createElement("div"),b.setAttribute("className","t"),b.innerHTML="  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>",d=b.getElementsByTagName("a")[0],c=y.createElement("select"),e=c.appendChild(y.createElement("option")),a=b.getElementsByTagName("input")[0],d.style.cssText="top:1px",k.getSetAttribute="t"!==b.className,k.style=/top/.test(d.getAttribute("style")),k.hrefNormalized="/a"===d.getAttribute("href"),k.checkOn=!!a.value,k.optSelected=e.selected,k.enctype=!!y.createElement("form").enctype,c.disabled=!0,k.optDisabled=!e.disabled,a=y.createElement("input"),a.setAttribute("value",""),k.input=""===a.getAttribute("value"),a.value="t",a.setAttribute("type","radio"),k.radioValue="t"===a.value}();var lb=/\r/g;m.fn.extend({val:function(a){var b,c,d,e=this[0];{if(arguments.length)return d=m.isFunction(a),this.each(function(c){var e;1===this.nodeType&&(e=d?a.call(this,c,m(this).val()):a,null==e?e="":"number"==typeof e?e+="":m.isArray(e)&&(e=m.map(e,function(a){return null==a?"":a+""})),b=m.valHooks[this.type]||m.valHooks[this.nodeName.toLowerCase()],b&&"set"in b&&void 0!==b.set(this,e,"value")||(this.value=e))});if(e)return b=m.valHooks[e.type]||m.valHooks[e.nodeName.toLowerCase()],b&&"get"in b&&void 0!==(c=b.get(e,"value"))?c:(c=e.value,"string"==typeof c?c.replace(lb,""):null==c?"":c)}}}),m.extend({valHooks:{option:{get:function(a){var b=m.find.attr(a,"value");return null!=b?b:m.trim(m.text(a))}},select:{get:function(a){for(var b,c,d=a.options,e=a.selectedIndex,f="select-one"===a.type||0>e,g=f?null:[],h=f?e+1:d.length,i=0>e?h:f?e:0;h>i;i++)if(c=d[i],!(!c.selected&&i!==e||(k.optDisabled?c.disabled:null!==c.getAttribute("disabled"))||c.parentNode.disabled&&m.nodeName(c.parentNode,"optgroup"))){if(b=m(c).val(),f)return b;g.push(b)}return g},set:function(a,b){var c,d,e=a.options,f=m.makeArray(b),g=e.length;while(g--)if(d=e[g],m.inArray(m.valHooks.option.get(d),f)>=0)try{d.selected=c=!0}catch(h){d.scrollHeight}else d.selected=!1;return c||(a.selectedIndex=-1),e}}}}),m.each(["radio","checkbox"],function(){m.valHooks[this]={set:function(a,b){return m.isArray(b)?a.checked=m.inArray(m(a).val(),b)>=0:void 0}},k.checkOn||(m.valHooks[this].get=function(a){return null===a.getAttribute("value")?"on":a.value})});var mb,nb,ob=m.expr.attrHandle,pb=/^(?:checked|selected)$/i,qb=k.getSetAttribute,rb=k.input;m.fn.extend({attr:function(a,b){return V(this,m.attr,a,b,arguments.length>1)},removeAttr:function(a){return this.each(function(){m.removeAttr(this,a)})}}),m.extend({attr:function(a,b,c){var d,e,f=a.nodeType;if(a&&3!==f&&8!==f&&2!==f)return typeof a.getAttribute===K?m.prop(a,b,c):(1===f&&m.isXMLDoc(a)||(b=b.toLowerCase(),d=m.attrHooks[b]||(m.expr.match.bool.test(b)?nb:mb)),void 0===c?d&&"get"in d&&null!==(e=d.get(a,b))?e:(e=m.find.attr(a,b),null==e?void 0:e):null!==c?d&&"set"in d&&void 0!==(e=d.set(a,c,b))?e:(a.setAttribute(b,c+""),c):void m.removeAttr(a,b))},removeAttr:function(a,b){var c,d,e=0,f=b&&b.match(E);if(f&&1===a.nodeType)while(c=f[e++])d=m.propFix[c]||c,m.expr.match.bool.test(c)?rb&&qb||!pb.test(c)?a[d]=!1:a[m.camelCase("default-"+c)]=a[d]=!1:m.attr(a,c,""),a.removeAttribute(qb?c:d)},attrHooks:{type:{set:function(a,b){if(!k.radioValue&&"radio"===b&&m.nodeName(a,"input")){var c=a.value;return a.setAttribute("type",b),c&&(a.value=c),b}}}}}),nb={set:function(a,b,c){return b===!1?m.removeAttr(a,c):rb&&qb||!pb.test(c)?a.setAttribute(!qb&&m.propFix[c]||c,c):a[m.camelCase("default-"+c)]=a[c]=!0,c}},m.each(m.expr.match.bool.source.match(/\w+/g),function(a,b){var c=ob[b]||m.find.attr;ob[b]=rb&&qb||!pb.test(b)?function(a,b,d){var e,f;return d||(f=ob[b],ob[b]=e,e=null!=c(a,b,d)?b.toLowerCase():null,ob[b]=f),e}:function(a,b,c){return c?void 0:a[m.camelCase("default-"+b)]?b.toLowerCase():null}}),rb&&qb||(m.attrHooks.value={set:function(a,b,c){return m.nodeName(a,"input")?void(a.defaultValue=b):mb&&mb.set(a,b,c)}}),qb||(mb={set:function(a,b,c){var d=a.getAttributeNode(c);return d||a.setAttributeNode(d=a.ownerDocument.createAttribute(c)),d.value=b+="","value"===c||b===a.getAttribute(c)?b:void 0}},ob.id=ob.name=ob.coords=function(a,b,c){var d;return c?void 0:(d=a.getAttributeNode(b))&&""!==d.value?d.value:null},m.valHooks.button={get:function(a,b){var c=a.getAttributeNode(b);return c&&c.specified?c.value:void 0},set:mb.set},m.attrHooks.contenteditable={set:function(a,b,c){mb.set(a,""===b?!1:b,c)}},m.each(["width","height"],function(a,b){m.attrHooks[b]={set:function(a,c){return""===c?(a.setAttribute(b,"auto"),c):void 0}}})),k.style||(m.attrHooks.style={get:function(a){return a.style.cssText||void 0},set:function(a,b){return a.style.cssText=b+""}});var sb=/^(?:input|select|textarea|button|object)$/i,tb=/^(?:a|area)$/i;m.fn.extend({prop:function(a,b){return V(this,m.prop,a,b,arguments.length>1)},removeProp:function(a){return a=m.propFix[a]||a,this.each(function(){try{this[a]=void 0,delete this[a]}catch(b){}})}}),m.extend({propFix:{"for":"htmlFor","class":"className"},prop:function(a,b,c){var d,e,f,g=a.nodeType;if(a&&3!==g&&8!==g&&2!==g)return f=1!==g||!m.isXMLDoc(a),f&&(b=m.propFix[b]||b,e=m.propHooks[b]),void 0!==c?e&&"set"in e&&void 0!==(d=e.set(a,c,b))?d:a[b]=c:e&&"get"in e&&null!==(d=e.get(a,b))?d:a[b]},propHooks:{tabIndex:{get:function(a){var b=m.find.attr(a,"tabindex");return b?parseInt(b,10):sb.test(a.nodeName)||tb.test(a.nodeName)&&a.href?0:-1}}}}),k.hrefNormalized||m.each(["href","src"],function(a,b){m.propHooks[b]={get:function(a){return a.getAttribute(b,4)}}}),k.optSelected||(m.propHooks.selected={get:function(a){var b=a.parentNode;return b&&(b.selectedIndex,b.parentNode&&b.parentNode.selectedIndex),null}}),m.each(["tabIndex","readOnly","maxLength","cellSpacing","cellPadding","rowSpan","colSpan","useMap","frameBorder","contentEditable"],function(){m.propFix[this.toLowerCase()]=this}),k.enctype||(m.propFix.enctype="encoding");var ub=/[\t\r\n\f]/g;m.fn.extend({addClass:function(a){var b,c,d,e,f,g,h=0,i=this.length,j="string"==typeof a&&a;if(m.isFunction(a))return this.each(function(b){m(this).addClass(a.call(this,b,this.className))});if(j)for(b=(a||"").match(E)||[];i>h;h++)if(c=this[h],d=1===c.nodeType&&(c.className?(" "+c.className+" ").replace(ub," "):" ")){f=0;while(e=b[f++])d.indexOf(" "+e+" ")<0&&(d+=e+" ");g=m.trim(d),c.className!==g&&(c.className=g)}return this},removeClass:function(a){var b,c,d,e,f,g,h=0,i=this.length,j=0===arguments.length||"string"==typeof a&&a;if(m.isFunction(a))return this.each(function(b){m(this).removeClass(a.call(this,b,this.className))});if(j)for(b=(a||"").match(E)||[];i>h;h++)if(c=this[h],d=1===c.nodeType&&(c.className?(" "+c.className+" ").replace(ub," "):"")){f=0;while(e=b[f++])while(d.indexOf(" "+e+" ")>=0)d=d.replace(" "+e+" "," ");g=a?m.trim(d):"",c.className!==g&&(c.className=g)}return this},toggleClass:function(a,b){var c=typeof a;return"boolean"==typeof b&&"string"===c?b?this.addClass(a):this.removeClass(a):this.each(m.isFunction(a)?function(c){m(this).toggleClass(a.call(this,c,this.className,b),b)}:function(){if("string"===c){var b,d=0,e=m(this),f=a.match(E)||[];while(b=f[d++])e.hasClass(b)?e.removeClass(b):e.addClass(b)}else(c===K||"boolean"===c)&&(this.className&&m._data(this,"__className__",this.className),this.className=this.className||a===!1?"":m._data(this,"__className__")||"")})},hasClass:function(a){for(var b=" "+a+" ",c=0,d=this.length;d>c;c++)if(1===this[c].nodeType&&(" "+this[c].className+" ").replace(ub," ").indexOf(b)>=0)return!0;return!1}}),m.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "),function(a,b){m.fn[b]=function(a,c){return arguments.length>0?this.on(b,null,a,c):this.trigger(b)}}),m.fn.extend({hover:function(a,b){return this.mouseenter(a).mouseleave(b||a)},bind:function(a,b,c){return this.on(a,null,b,c)},unbind:function(a,b){return this.off(a,null,b)},delegate:function(a,b,c,d){return this.on(b,a,c,d)},undelegate:function(a,b,c){return 1===arguments.length?this.off(a,"**"):this.off(b,a||"**",c)}});var vb=m.now(),wb=/\?/,xb=/(,)|(\[|{)|(}|])|"(?:[^"\\\r\n]|\\["\\\/bfnrt]|\\u[\da-fA-F]{4})*"\s*:?|true|false|null|-?(?!0\d)\d+(?:\.\d+|)(?:[eE][+-]?\d+|)/g;m.parseJSON=function(b){if(a.JSON&&a.JSON.parse)return a.JSON.parse(b+"");var c,d=null,e=m.trim(b+"");return e&&!m.trim(e.replace(xb,function(a,b,e,f){return c&&b&&(d=0),0===d?a:(c=e||b,d+=!f-!e,"")}))?Function("return "+e)():m.error("Invalid JSON: "+b)},m.parseXML=function(b){var c,d;if(!b||"string"!=typeof b)return null;try{a.DOMParser?(d=new DOMParser,c=d.parseFromString(b,"text/xml")):(c=new ActiveXObject("Microsoft.XMLDOM"),c.async="false",c.loadXML(b))}catch(e){c=void 0}return c&&c.documentElement&&!c.getElementsByTagName("parsererror").length||m.error("Invalid XML: "+b),c};var yb,zb,Ab=/#.*$/,Bb=/([?&])_=[^&]*/,Cb=/^(.*?):[ \t]*([^\r\n]*)\r?$/gm,Db=/^(?:about|app|app-storage|.+-extension|file|res|widget):$/,Eb=/^(?:GET|HEAD)$/,Fb=/^\/\//,Gb=/^([\w.+-]+:)(?:\/\/(?:[^\/?#]*@|)([^\/?#:]*)(?::(\d+)|)|)/,Hb={},Ib={},Jb="*/".concat("*");try{zb=location.href}catch(Kb){zb=y.createElement("a"),zb.href="",zb=zb.href}yb=Gb.exec(zb.toLowerCase())||[];function Lb(a){return function(b,c){"string"!=typeof b&&(c=b,b="*");var d,e=0,f=b.toLowerCase().match(E)||[];if(m.isFunction(c))while(d=f[e++])"+"===d.charAt(0)?(d=d.slice(1)||"*",(a[d]=a[d]||[]).unshift(c)):(a[d]=a[d]||[]).push(c)}}function Mb(a,b,c,d){var e={},f=a===Ib;function g(h){var i;return e[h]=!0,m.each(a[h]||[],function(a,h){var j=h(b,c,d);return"string"!=typeof j||f||e[j]?f?!(i=j):void 0:(b.dataTypes.unshift(j),g(j),!1)}),i}return g(b.dataTypes[0])||!e["*"]&&g("*")}function Nb(a,b){var c,d,e=m.ajaxSettings.flatOptions||{};for(d in b)void 0!==b[d]&&((e[d]?a:c||(c={}))[d]=b[d]);return c&&m.extend(!0,a,c),a}function Ob(a,b,c){var d,e,f,g,h=a.contents,i=a.dataTypes;while("*"===i[0])i.shift(),void 0===e&&(e=a.mimeType||b.getResponseHeader("Content-Type"));if(e)for(g in h)if(h[g]&&h[g].test(e)){i.unshift(g);break}if(i[0]in c)f=i[0];else{for(g in c){if(!i[0]||a.converters[g+" "+i[0]]){f=g;break}d||(d=g)}f=f||d}return f?(f!==i[0]&&i.unshift(f),c[f]):void 0}function Pb(a,b,c,d){var e,f,g,h,i,j={},k=a.dataTypes.slice();if(k[1])for(g in a.converters)j[g.toLowerCase()]=a.converters[g];f=k.shift();while(f)if(a.responseFields[f]&&(c[a.responseFields[f]]=b),!i&&d&&a.dataFilter&&(b=a.dataFilter(b,a.dataType)),i=f,f=k.shift())if("*"===f)f=i;else if("*"!==i&&i!==f){if(g=j[i+" "+f]||j["* "+f],!g)for(e in j)if(h=e.split(" "),h[1]===f&&(g=j[i+" "+h[0]]||j["* "+h[0]])){g===!0?g=j[e]:j[e]!==!0&&(f=h[0],k.unshift(h[1]));break}if(g!==!0)if(g&&a["throws"])b=g(b);else try{b=g(b)}catch(l){return{state:"parsererror",error:g?l:"No conversion from "+i+" to "+f}}}return{state:"success",data:b}}m.extend({active:0,lastModified:{},etag:{},ajaxSettings:{url:zb,type:"GET",isLocal:Db.test(yb[1]),global:!0,processData:!0,async:!0,contentType:"application/x-www-form-urlencoded; charset=UTF-8",accepts:{"*":Jb,text:"text/plain",html:"text/html",xml:"application/xml, text/xml",json:"application/json, text/javascript"},contents:{xml:/xml/,html:/html/,json:/json/},responseFields:{xml:"responseXML",text:"responseText",json:"responseJSON"},converters:{"* text":String,"text html":!0,"text json":m.parseJSON,"text xml":m.parseXML},flatOptions:{url:!0,context:!0}},ajaxSetup:function(a,b){return b?Nb(Nb(a,m.ajaxSettings),b):Nb(m.ajaxSettings,a)},ajaxPrefilter:Lb(Hb),ajaxTransport:Lb(Ib),ajax:function(a,b){"object"==typeof a&&(b=a,a=void 0),b=b||{};var c,d,e,f,g,h,i,j,k=m.ajaxSetup({},b),l=k.context||k,n=k.context&&(l.nodeType||l.jquery)?m(l):m.event,o=m.Deferred(),p=m.Callbacks("once memory"),q=k.statusCode||{},r={},s={},t=0,u="canceled",v={readyState:0,getResponseHeader:function(a){var b;if(2===t){if(!j){j={};while(b=Cb.exec(f))j[b[1].toLowerCase()]=b[2]}b=j[a.toLowerCase()]}return null==b?null:b},getAllResponseHeaders:function(){return 2===t?f:null},setRequestHeader:function(a,b){var c=a.toLowerCase();return t||(a=s[c]=s[c]||a,r[a]=b),this},overrideMimeType:function(a){return t||(k.mimeType=a),this},statusCode:function(a){var b;if(a)if(2>t)for(b in a)q[b]=[q[b],a[b]];else v.always(a[v.status]);return this},abort:function(a){var b=a||u;return i&&i.abort(b),x(0,b),this}};if(o.promise(v).complete=p.add,v.success=v.done,v.error=v.fail,k.url=((a||k.url||zb)+"").replace(Ab,"").replace(Fb,yb[1]+"//"),k.type=b.method||b.type||k.method||k.type,k.dataTypes=m.trim(k.dataType||"*").toLowerCase().match(E)||[""],null==k.crossDomain&&(c=Gb.exec(k.url.toLowerCase()),k.crossDomain=!(!c||c[1]===yb[1]&&c[2]===yb[2]&&(c[3]||("http:"===c[1]?"80":"443"))===(yb[3]||("http:"===yb[1]?"80":"443")))),k.data&&k.processData&&"string"!=typeof k.data&&(k.data=m.param(k.data,k.traditional)),Mb(Hb,k,b,v),2===t)return v;h=m.event&&k.global,h&&0===m.active++&&m.event.trigger("ajaxStart"),k.type=k.type.toUpperCase(),k.hasContent=!Eb.test(k.type),e=k.url,k.hasContent||(k.data&&(e=k.url+=(wb.test(e)?"&":"?")+k.data,delete k.data),k.cache===!1&&(k.url=Bb.test(e)?e.replace(Bb,"$1_="+vb++):e+(wb.test(e)?"&":"?")+"_="+vb++)),k.ifModified&&(m.lastModified[e]&&v.setRequestHeader("If-Modified-Since",m.lastModified[e]),m.etag[e]&&v.setRequestHeader("If-None-Match",m.etag[e])),(k.data&&k.hasContent&&k.contentType!==!1||b.contentType)&&v.setRequestHeader("Content-Type",k.contentType),v.setRequestHeader("Accept",k.dataTypes[0]&&k.accepts[k.dataTypes[0]]?k.accepts[k.dataTypes[0]]+("*"!==k.dataTypes[0]?", "+Jb+"; q=0.01":""):k.accepts["*"]);for(d in k.headers)v.setRequestHeader(d,k.headers[d]);if(k.beforeSend&&(k.beforeSend.call(l,v,k)===!1||2===t))return v.abort();u="abort";for(d in{success:1,error:1,complete:1})v[d](k[d]);if(i=Mb(Ib,k,b,v)){v.readyState=1,h&&n.trigger("ajaxSend",[v,k]),k.async&&k.timeout>0&&(g=setTimeout(function(){v.abort("timeout")},k.timeout));try{t=1,i.send(r,x)}catch(w){if(!(2>t))throw w;x(-1,w)}}else x(-1,"No Transport");function x(a,b,c,d){var j,r,s,u,w,x=b;2!==t&&(t=2,g&&clearTimeout(g),i=void 0,f=d||"",v.readyState=a>0?4:0,j=a>=200&&300>a||304===a,c&&(u=Ob(k,v,c)),u=Pb(k,u,v,j),j?(k.ifModified&&(w=v.getResponseHeader("Last-Modified"),w&&(m.lastModified[e]=w),w=v.getResponseHeader("etag"),w&&(m.etag[e]=w)),204===a||"HEAD"===k.type?x="nocontent":304===a?x="notmodified":(x=u.state,r=u.data,s=u.error,j=!s)):(s=x,(a||!x)&&(x="error",0>a&&(a=0))),v.status=a,v.statusText=(b||x)+"",j?o.resolveWith(l,[r,x,v]):o.rejectWith(l,[v,x,s]),v.statusCode(q),q=void 0,h&&n.trigger(j?"ajaxSuccess":"ajaxError",[v,k,j?r:s]),p.fireWith(l,[v,x]),h&&(n.trigger("ajaxComplete",[v,k]),--m.active||m.event.trigger("ajaxStop")))}return v},getJSON:function(a,b,c){return m.get(a,b,c,"json")},getScript:function(a,b){return m.get(a,void 0,b,"script")}}),m.each(["get","post"],function(a,b){m[b]=function(a,c,d,e){return m.isFunction(c)&&(e=e||d,d=c,c=void 0),m.ajax({url:a,type:b,dataType:e,data:c,success:d})}}),m._evalUrl=function(a){return m.ajax({url:a,type:"GET",dataType:"script",async:!1,global:!1,"throws":!0})},m.fn.extend({wrapAll:function(a){if(m.isFunction(a))return this.each(function(b){m(this).wrapAll(a.call(this,b))});if(this[0]){var b=m(a,this[0].ownerDocument).eq(0).clone(!0);this[0].parentNode&&b.insertBefore(this[0]),b.map(function(){var a=this;while(a.firstChild&&1===a.firstChild.nodeType)a=a.firstChild;return a}).append(this)}return this},wrapInner:function(a){return this.each(m.isFunction(a)?function(b){m(this).wrapInner(a.call(this,b))}:function(){var b=m(this),c=b.contents();c.length?c.wrapAll(a):b.append(a)})},wrap:function(a){var b=m.isFunction(a);return this.each(function(c){m(this).wrapAll(b?a.call(this,c):a)})},unwrap:function(){return this.parent().each(function(){m.nodeName(this,"body")||m(this).replaceWith(this.childNodes)}).end()}}),m.expr.filters.hidden=function(a){return a.offsetWidth<=0&&a.offsetHeight<=0||!k.reliableHiddenOffsets()&&"none"===(a.style&&a.style.display||m.css(a,"display"))},m.expr.filters.visible=function(a){return!m.expr.filters.hidden(a)};var Qb=/%20/g,Rb=/\[\]$/,Sb=/\r?\n/g,Tb=/^(?:submit|button|image|reset|file)$/i,Ub=/^(?:input|select|textarea|keygen)/i;function Vb(a,b,c,d){var e;if(m.isArray(b))m.each(b,function(b,e){c||Rb.test(a)?d(a,e):Vb(a+"["+("object"==typeof e?b:"")+"]",e,c,d)});else if(c||"object"!==m.type(b))d(a,b);else for(e in b)Vb(a+"["+e+"]",b[e],c,d)}m.param=function(a,b){var c,d=[],e=function(a,b){b=m.isFunction(b)?b():null==b?"":b,d[d.length]=encodeURIComponent(a)+"="+encodeURIComponent(b)};if(void 0===b&&(b=m.ajaxSettings&&m.ajaxSettings.traditional),m.isArray(a)||a.jquery&&!m.isPlainObject(a))m.each(a,function(){e(this.name,this.value)});else for(c in a)Vb(c,a[c],b,e);return d.join("&").replace(Qb,"+")},m.fn.extend({serialize:function(){return m.param(this.serializeArray())},serializeArray:function(){return this.map(function(){var a=m.prop(this,"elements");return a?m.makeArray(a):this}).filter(function(){var a=this.type;return this.name&&!m(this).is(":disabled")&&Ub.test(this.nodeName)&&!Tb.test(a)&&(this.checked||!W.test(a))}).map(function(a,b){var c=m(this).val();return null==c?null:m.isArray(c)?m.map(c,function(a){return{name:b.name,value:a.replace(Sb,"\r\n")}}):{name:b.name,value:c.replace(Sb,"\r\n")}}).get()}}),m.ajaxSettings.xhr=void 0!==a.ActiveXObject?function(){return!this.isLocal&&/^(get|post|head|put|delete|options)$/i.test(this.type)&&Zb()||$b()}:Zb;var Wb=0,Xb={},Yb=m.ajaxSettings.xhr();a.attachEvent&&a.attachEvent("onunload",function(){for(var a in Xb)Xb[a](void 0,!0)}),k.cors=!!Yb&&"withCredentials"in Yb,Yb=k.ajax=!!Yb,Yb&&m.ajaxTransport(function(a){if(!a.crossDomain||k.cors){var b;return{send:function(c,d){var e,f=a.xhr(),g=++Wb;if(f.open(a.type,a.url,a.async,a.username,a.password),a.xhrFields)for(e in a.xhrFields)f[e]=a.xhrFields[e];a.mimeType&&f.overrideMimeType&&f.overrideMimeType(a.mimeType),a.crossDomain||c["X-Requested-With"]||(c["X-Requested-With"]="XMLHttpRequest");for(e in c)void 0!==c[e]&&f.setRequestHeader(e,c[e]+"");f.send(a.hasContent&&a.data||null),b=function(c,e){var h,i,j;if(b&&(e||4===f.readyState))if(delete Xb[g],b=void 0,f.onreadystatechange=m.noop,e)4!==f.readyState&&f.abort();else{j={},h=f.status,"string"==typeof f.responseText&&(j.text=f.responseText);try{i=f.statusText}catch(k){i=""}h||!a.isLocal||a.crossDomain?1223===h&&(h=204):h=j.text?200:404}j&&d(h,i,j,f.getAllResponseHeaders())},a.async?4===f.readyState?setTimeout(b):f.onreadystatechange=Xb[g]=b:b()},abort:function(){b&&b(void 0,!0)}}}});function Zb(){try{return new a.XMLHttpRequest}catch(b){}}function $b(){try{return new a.ActiveXObject("Microsoft.XMLHTTP")}catch(b){}}m.ajaxSetup({accepts:{script:"text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},contents:{script:/(?:java|ecma)script/},converters:{"text script":function(a){return m.globalEval(a),a}}}),m.ajaxPrefilter("script",function(a){void 0===a.cache&&(a.cache=!1),a.crossDomain&&(a.type="GET",a.global=!1)}),m.ajaxTransport("script",function(a){if(a.crossDomain){var b,c=y.head||m("head")[0]||y.documentElement;return{send:function(d,e){b=y.createElement("script"),b.async=!0,a.scriptCharset&&(b.charset=a.scriptCharset),b.src=a.url,b.onload=b.onreadystatechange=function(a,c){(c||!b.readyState||/loaded|complete/.test(b.readyState))&&(b.onload=b.onreadystatechange=null,b.parentNode&&b.parentNode.removeChild(b),b=null,c||e(200,"success"))},c.insertBefore(b,c.firstChild)},abort:function(){b&&b.onload(void 0,!0)}}}});var _b=[],ac=/(=)\?(?=&|$)|\?\?/;m.ajaxSetup({jsonp:"callback",jsonpCallback:function(){var a=_b.pop()||m.expando+"_"+vb++;return this[a]=!0,a}}),m.ajaxPrefilter("json jsonp",function(b,c,d){var e,f,g,h=b.jsonp!==!1&&(ac.test(b.url)?"url":"string"==typeof b.data&&!(b.contentType||"").indexOf("application/x-www-form-urlencoded")&&ac.test(b.data)&&"data");return h||"jsonp"===b.dataTypes[0]?(e=b.jsonpCallback=m.isFunction(b.jsonpCallback)?b.jsonpCallback():b.jsonpCallback,h?b[h]=b[h].replace(ac,"$1"+e):b.jsonp!==!1&&(b.url+=(wb.test(b.url)?"&":"?")+b.jsonp+"="+e),b.converters["script json"]=function(){return g||m.error(e+" was not called"),g[0]},b.dataTypes[0]="json",f=a[e],a[e]=function(){g=arguments},d.always(function(){a[e]=f,b[e]&&(b.jsonpCallback=c.jsonpCallback,_b.push(e)),g&&m.isFunction(f)&&f(g[0]),g=f=void 0}),"script"):void 0}),m.parseHTML=function(a,b,c){if(!a||"string"!=typeof a)return null;"boolean"==typeof b&&(c=b,b=!1),b=b||y;var d=u.exec(a),e=!c&&[];return d?[b.createElement(d[1])]:(d=m.buildFragment([a],b,e),e&&e.length&&m(e).remove(),m.merge([],d.childNodes))};var bc=m.fn.load;m.fn.load=function(a,b,c){if("string"!=typeof a&&bc)return bc.apply(this,arguments);var d,e,f,g=this,h=a.indexOf(" ");return h>=0&&(d=m.trim(a.slice(h,a.length)),a=a.slice(0,h)),m.isFunction(b)?(c=b,b=void 0):b&&"object"==typeof b&&(f="POST"),g.length>0&&m.ajax({url:a,type:f,dataType:"html",data:b}).done(function(a){e=arguments,g.html(d?m("<div>").append(m.parseHTML(a)).find(d):a)}).complete(c&&function(a,b){g.each(c,e||[a.responseText,b,a])}),this},m.each(["ajaxStart","ajaxStop","ajaxComplete","ajaxError","ajaxSuccess","ajaxSend"],function(a,b){m.fn[b]=function(a){return this.on(b,a)}}),m.expr.filters.animated=function(a){return m.grep(m.timers,function(b){return a===b.elem}).length};var cc=a.document.documentElement;function dc(a){return m.isWindow(a)?a:9===a.nodeType?a.defaultView||a.parentWindow:!1}m.offset={setOffset:function(a,b,c){var d,e,f,g,h,i,j,k=m.css(a,"position"),l=m(a),n={};"static"===k&&(a.style.position="relative"),h=l.offset(),f=m.css(a,"top"),i=m.css(a,"left"),j=("absolute"===k||"fixed"===k)&&m.inArray("auto",[f,i])>-1,j?(d=l.position(),g=d.top,e=d.left):(g=parseFloat(f)||0,e=parseFloat(i)||0),m.isFunction(b)&&(b=b.call(a,c,h)),null!=b.top&&(n.top=b.top-h.top+g),null!=b.left&&(n.left=b.left-h.left+e),"using"in b?b.using.call(a,n):l.css(n)}},m.fn.extend({offset:function(a){if(arguments.length)return void 0===a?this:this.each(function(b){m.offset.setOffset(this,a,b)});var b,c,d={top:0,left:0},e=this[0],f=e&&e.ownerDocument;if(f)return b=f.documentElement,m.contains(b,e)?(typeof e.getBoundingClientRect!==K&&(d=e.getBoundingClientRect()),c=dc(f),{top:d.top+(c.pageYOffset||b.scrollTop)-(b.clientTop||0),left:d.left+(c.pageXOffset||b.scrollLeft)-(b.clientLeft||0)}):d},position:function(){if(this[0]){var a,b,c={top:0,left:0},d=this[0];return"fixed"===m.css(d,"position")?b=d.getBoundingClientRect():(a=this.offsetParent(),b=this.offset(),m.nodeName(a[0],"html")||(c=a.offset()),c.top+=m.css(a[0],"borderTopWidth",!0),c.left+=m.css(a[0],"borderLeftWidth",!0)),{top:b.top-c.top-m.css(d,"marginTop",!0),left:b.left-c.left-m.css(d,"marginLeft",!0)}}},offsetParent:function(){return this.map(function(){var a=this.offsetParent||cc;while(a&&!m.nodeName(a,"html")&&"static"===m.css(a,"position"))a=a.offsetParent;return a||cc})}}),m.each({scrollLeft:"pageXOffset",scrollTop:"pageYOffset"},function(a,b){var c=/Y/.test(b);m.fn[a]=function(d){return V(this,function(a,d,e){var f=dc(a);return void 0===e?f?b in f?f[b]:f.document.documentElement[d]:a[d]:void(f?f.scrollTo(c?m(f).scrollLeft():e,c?e:m(f).scrollTop()):a[d]=e)},a,d,arguments.length,null)}}),m.each(["top","left"],function(a,b){m.cssHooks[b]=La(k.pixelPosition,function(a,c){return c?(c=Ja(a,b),Ha.test(c)?m(a).position()[b]+"px":c):void 0})}),m.each({Height:"height",Width:"width"},function(a,b){m.each({padding:"inner"+a,content:b,"":"outer"+a},function(c,d){m.fn[d]=function(d,e){var f=arguments.length&&(c||"boolean"!=typeof d),g=c||(d===!0||e===!0?"margin":"border");return V(this,function(b,c,d){var e;return m.isWindow(b)?b.document.documentElement["client"+a]:9===b.nodeType?(e=b.documentElement,Math.max(b.body["scroll"+a],e["scroll"+a],b.body["offset"+a],e["offset"+a],e["client"+a])):void 0===d?m.css(b,c,g):m.style(b,c,d,g)},b,f?d:void 0,f,null)}})}),m.fn.size=function(){return this.length},m.fn.andSelf=m.fn.addBack,"function"==typeof define&&define.amd&&define("jquery",[],function(){return m});var ec=a.jQuery,fc=a.$;return m.noConflict=function(b){return a.$===m&&(a.$=fc),b&&a.jQuery===m&&(a.jQuery=ec),m},typeof b===K&&(a.jQuery=a.$=m),m});
/*jslint eqeq: true, plusplus: true, undef: true, sloppy: true, vars: true, forin: true */
/*!
 * jQuery MobiScroll v2.5.1
 * http://mobiscroll.com
 *
 * Copyright 2010-2013, Acid Media
 * Licensed under the MIT license.
 *
 */
(function ($) {

    function Scroller(elem, settings) {
        var m,
            hi,
            v,
            dw,
            ww, // Window width
            wh, // Window height
            rwh,
            mw, // Modal width
            mh, // Modal height
            anim,
            debounce,
            that = this,
            ms = $.mobiscroll,
            e = elem,
            elm = $(e),
            theme,
            lang,
            s = extend({}, defaults),
            pres = {},
            warr = [],
            iv = {},
            pixels = {},
            input = elm.is('input'),
            visible = false;

        // Private functions

        function isReadOnly(wh) {
            if ($.isArray(s.readonly)) {
                var i = $('.dwwl', dw).index(wh);
                return s.readonly[i];
            }
            return s.readonly;
        }

        function generateWheelItems(i) {
            var html = '<div class="dw-bf">',
                l = 1,
                j;

            for (j in warr[i]) {
                if (l % 20 == 0) {
                    html += '</div><div class="dw-bf">';
                }
                html += '<div class="dw-li dw-v" data-val="' + j + '" style="height:' + hi + 'px;line-height:' + hi + 'px;"><div class="dw-i">' + warr[i][j] + '</div></div>';
                l++;
            }
            html += '</div>';
            return html;
        }

        function setGlobals(t) {
            min = $('.dw-li', t).index($('.dw-v', t).eq(0));
            max = $('.dw-li', t).index($('.dw-v', t).eq(-1));
            index = $('.dw-ul', dw).index(t);
            h = hi;
            inst = that;
        }

        function formatHeader(v) {
            var t = s.headerText;
            return t ? (typeof t === 'function' ? t.call(e, v) : t.replace(/\{value\}/i, v)) : '';
        }

        function read() {
            that.temp = ((input && that.val !== null && that.val != elm.val()) || that.values === null) ? s.parseValue(elm.val() || '', that) : that.values.slice(0);
            that.setValue(true);
        }

        function scrollToPos(time, index, manual, dir, orig) {

            // Call validation event
            if (event('validate', [dw, index, time]) !== false) {

                // Set scrollers to position
                $('.dw-ul', dw).each(function (i) {
                    var t = $(this),
                        cell = $('.dw-li[data-val="' + that.temp[i] + '"]', t),
                        cells = $('.dw-li', t),
                        v = cells.index(cell),
                        l = cells.length,
                        sc = i == index || index === undefined;

                    // Scroll to a valid cell
                    if (!cell.hasClass('dw-v')) {
                        var cell1 = cell,
                            cell2 = cell,
                            dist1 = 0,
                            dist2 = 0;

                        while (v - dist1 >= 0 && !cell1.hasClass('dw-v')) {
                            dist1++;
                            cell1 = cells.eq(v - dist1);
                        }

                        while (v + dist2 < l && !cell2.hasClass('dw-v')) {
                            dist2++;
                            cell2 = cells.eq(v + dist2);
                        }

                        // If we have direction (+/- or mouse wheel), the distance does not count
                        if (((dist2 < dist1 && dist2 && dir !== 2) || !dist1 || (v - dist1 < 0) || dir == 1) && cell2.hasClass('dw-v')) {
                            cell = cell2;
                            v = v + dist2;
                        } else {
                            cell = cell1;
                            v = v - dist1;
                        }
                    }

                    if (!(cell.hasClass('dw-sel')) || sc) {
                        // Set valid value
                        that.temp[i] = cell.attr('data-val');

                        // Add selected class to cell
                        $('.dw-sel', t).removeClass('dw-sel');
                        cell.addClass('dw-sel');

                        // Scroll to position
                        //that.scroll(t, i, v, time);
                        that.scroll(t, i, v, sc ? time : 0.1, sc ? orig : undefined);
                    }
                });

                // Reformat value if validation changed something
                that.change(manual);
            }

        }

        function position(check) {

            if (s.display == 'inline' || (ww === $(window).width() && rwh === $(window).height() && check)) {
                return;
            }

            var w,
                l,
                t,
                aw, // anchor width
                ah, // anchor height
                ap, // anchor position
                at, // anchor top
                al, // anchor left
                arr, // arrow
                arrw, // arrow width
                arrl, // arrow left
                scroll,
                totalw = 0,
                minw = 0,
                st = $(window).scrollTop(),
                wr = $('.dwwr', dw),
                d = $('.dw', dw),
                css = {},
                anchor = s.anchor === undefined ? elm : s.anchor;

            ww = $(window).width();
            rwh = $(window).height();
            wh = window.innerHeight; // on iOS we need innerHeight
            wh = wh || rwh;

            if (/modal|bubble/.test(s.display)) {
                $('.dwc', dw).each(function () {
                    w = $(this).outerWidth(true);
                    totalw += w;
                    minw = (w > minw) ? w : minw;
                });
                w = totalw > ww ? minw : totalw;
                wr.width(w);
            }

            mw = d.outerWidth();
            mh = d.outerHeight(true);

            if (s.display == 'modal') {
                l = (ww - mw) / 2;
                t = st + (wh - mh) / 2;
            } else if (s.display == 'bubble') {
                scroll = true;
                arr = $('.dw-arrw-i', dw);
                ap = anchor.offset();
                at = ap.top;
                al = ap.left;

                // horizontal positioning
                aw = anchor.outerWidth();
                ah = anchor.outerHeight();
                l = al - (d.outerWidth(true) - aw) / 2;
                l = l > (ww - mw) ? (ww - (mw + 20)) : l;
                l = l >= 0 ? l : 20;

                // vertical positioning
                t = at - mh; //(mh + 3); // above the input
                if ((t < st) || (at > st + wh)) { // if doesn't fit above or the input is out of the screen
                    d.removeClass('dw-bubble-top').addClass('dw-bubble-bottom');
                    t = at + ah;// + 3; // below the input
                } else {
                    d.removeClass('dw-bubble-bottom').addClass('dw-bubble-top');
                }

                //t = t >= st ? t : st;

                // Calculate Arrow position
                arrw = arr.outerWidth();
                arrl = al + aw / 2 - (l + (mw - arrw) / 2);

                // Limit Arrow position to [0, pocw.width] intervall
                $('.dw-arr', dw).css({ left: arrl > arrw ? arrw : arrl });
            } else {
                css.width = '100%';
                if (s.display == 'top') {
                    t = st;
                } else if (s.display == 'bottom') {
                    t = st + wh - mh;
                }
            }

            css.top = t < 0 ? 0 : t;
            css.left = l;
            d.css(css);

            // If top + modal height > doc height, increase doc height
            $('.dw-persp', dw).height(0).height(t + mh > $(document).height() ? t + mh : $(document).height());

            // Scroll needed
            if (scroll && ((t + mh > st + wh) || (at > st + wh))) {
                $(window).scrollTop(t + mh - wh);
            }
        }

        function testTouch(e) {
            if (e.type === 'touchstart') {
                touch = true;
                setTimeout(function () {
                    touch = false; // Reset if mouse event was not fired
                }, 500);
            } else if (touch) {
                touch = false;
                return false;
            }
            return true;
        }

        function event(name, args) {
            var ret;
            args.push(that);
            $.each([theme.defaults, pres, settings], function (i, v) {
                if (v[name]) { // Call preset event
                    ret = v[name].apply(e, args);
                }
            });
            return ret;
        }

        function plus(t) {
            var p = +t.data('pos'),
                val = p + 1;
            calc(t, val > max ? min : val, 1, true);
        }

        function minus(t) {
            var p = +t.data('pos'),
                val = p - 1;
            calc(t, val < min ? max : val, 2, true);
        }

        // Public functions

        /**
        * Enables the scroller and the associated input.
        */
        that.enable = function () {
            s.disabled = false;
            if (input) {
                elm.prop('disabled', false);
            }
        };

        /**
        * Disables the scroller and the associated input.
        */
        that.disable = function () {
            s.disabled = true;
            if (input) {
                elm.prop('disabled', true);
            }
        };

        /**
        * Scrolls target to the specified position
        * @param {Object} t - Target wheel jQuery object.
        * @param {Number} index - Index of the changed wheel.
        * @param {Number} val - Value.
        * @param {Number} time - Duration of the animation, optional.
        * @param {Number} orig - Original value.
        */
        that.scroll = function (t, index, val, time, orig) {

            function getVal(t, b, c, d) {
                return c * Math.sin(t / d * (Math.PI / 2)) + b;
            }

            function ready() {
                clearInterval(iv[index]);
                delete iv[index];
                t.data('pos', val).closest('.dwwl').removeClass('dwa');
            }

            var px = (m - val) * hi,
                i;

            if (px == pixels[index] && iv[index]) {
                return;
            }

            if (time && px != pixels[index]) {
                // Trigger animation start event
                event('onAnimStart', [dw, index, time]);
            }

            pixels[index] = px;

            t.attr('style', (prefix + '-transition:all ' + (time ? time.toFixed(3) : 0) + 's ease-out;') + (has3d ? (prefix + '-transform:translate3d(0,' + px + 'px,0);') : ('top:' + px + 'px;')));

            if (iv[index]) {
                ready();
            }

            if (time && orig !== undefined) {
                i = 0;
                t.closest('.dwwl').addClass('dwa');
                iv[index] = setInterval(function () {
                    i += 0.1;
                    t.data('pos', Math.round(getVal(i, orig, val - orig, time)));
                    if (i >= time) {
                        ready();
                    }
                }, 100);
            } else {

                t.data('pos', val);
            }
        };

        /**
        * Gets the selected wheel values, formats it, and set the value of the scroller instance.
        * If input parameter is true, populates the associated input element.
        * @param {Boolean} sc - Scroll the wheel in position.
        * @param {Boolean} fill - Also set the value of the associated input element. Default is true.
        * @param {Number} time - Animation time
        * @param {Boolean} temp - If true, then only set the temporary value.(only scroll there but not set the value)
        */
        that.setValue = function (sc, fill, time, temp) {
            if (!$.isArray(that.temp)) {
                that.temp = s.parseValue(that.temp + '', that);
            }

            if (visible && sc) {
                scrollToPos(time);
            }

            v = s.formatResult(that.temp);

            if (!temp) {
                that.values = that.temp.slice(0);
                that.val = v;
            }

            if (fill) {
                if (input) {
                    elm.val(v).trigger('change');
                }
            }
        };

        that.getValues = function () {
            var ret = [],
                i;

            for (i in that._selectedValues) {
                ret.push(that._selectedValues[i]);
            }
            return ret;
        };

        /**
        * Checks if the current selected values are valid together.
        * In case of date presets it checks the number of days in a month.
        * @param {Number} time - Animation time
        * @param {Number} orig - Original value
        * @param {Number} i - Currently changed wheel index, -1 if initial validation.
        * @param {Number} dir - Scroll direction
        */
        that.validate = function (i, dir, time, orig) {
            scrollToPos(time, i, true, dir, orig);
        };

        /**
        *
        */
        that.change = function (manual) {
            v = s.formatResult(that.temp);
            if (s.display == 'inline') {
                that.setValue(false, manual);
            } else {
                $('.dwv', dw).html(formatHeader(v));
            }

            if (manual) {
                event('onChange', [v]);
            }
        };

        /**
        * Changes the values of a wheel, and scrolls to the correct position
        */
        that.changeWheel = function (idx, time) {
            if (dw) {
                var i = 0,
                    j,
                    k,
                    nr = idx.length;

                for (j in s.wheels) {
                    for (k in s.wheels[j]) {
                        if ($.inArray(i, idx) > -1) {
                            warr[i] = s.wheels[j][k];
                            $('.dw-ul', dw).eq(i).html(generateWheelItems(i));
                            nr--;
                            if (!nr) {
                                position();
                                scrollToPos(time, undefined, true);
                                return;
                            }
                        }
                        i++;
                    }
                }
            }
        };

        /**
        * Return true if the scroller is currently visible.
        */
        that.isVisible = function () {
            return visible;
        };

        /**
        *
        */
        that.tap = function (el, handler) {
            var startX,
                startY;

            if (s.tap) {
                el.bind('touchstart', function (e) {
                    e.preventDefault();
                    startX = getCoord(e, 'X');
                    startY = getCoord(e, 'Y');
                }).bind('touchend', function (e) {
                    // If movement is less than 20px, fire the click event handler
                    if (Math.abs(getCoord(e, 'X') - startX) < 20 && Math.abs(getCoord(e, 'Y') - startY) < 20) {
                        handler.call(this, e);
                    }
                    tap = true;
                    setTimeout(function () {
                        tap = false;
                    }, 300);
                });
            }

            el.bind('click', function (e) {
                if (!tap) {
                    // If handler was not called on touchend, call it on click;
                    handler.call(this, e);
                }
            });

        };

        /**
        * Shows the scroller instance.
        * @param {Boolean} prevAnim - Prevent animation if true
        */
        that.show = function (prevAnim) {
            if (s.disabled || visible) {
                return false;
            }

            if (s.display == 'top') {
                anim = 'slidedown';
            }

            if (s.display == 'bottom') {
                anim = 'slideup';
            }

            // Parse value from input
            read();

            event('onBeforeShow', [dw]);

            // Create wheels
            var l = 0,
                i,
                label,
                mAnim = '';

            if (anim && !prevAnim) {
                mAnim = 'dw-' + anim + ' dw-in';
            }
            // Create wheels containers
            var html = '<div class="dw-trans ' + s.theme + ' dw-' + s.display + '">' + (s.display == 'inline' ? '<div class="dw dwbg dwi"><div class="dwwr">' : '<div class="dw-persp">' + '<div class="dwo"></div><div class="dw dwbg ' + mAnim + '"><div class="dw-arrw"><div class="dw-arrw-i"><div class="dw-arr"></div></div></div><div class="dwwr">' + (s.headerText ? '<div class="dwv"></div>' : ''));

            for (i = 0; i < s.wheels.length; i++) {
                html += '<div class="dwc' + (s.mode != 'scroller' ? ' dwpm' : ' dwsc') + (s.showLabel ? '' : ' dwhl') + '"><div class="dwwc dwrc"><table cellpadding="0" cellspacing="0"><tr>';
                // Create wheels
                for (label in s.wheels[i]) {
                    warr[l] = s.wheels[i][label];
                    html += '<td><div class="dwwl dwrc dwwl' + l + '">' + (s.mode != 'scroller' ? '<div class="dwwb dwwbp" style="height:' + hi + 'px;line-height:' + hi + 'px;"><span>+</span></div><div class="dwwb dwwbm" style="height:' + hi + 'px;line-height:' + hi + 'px;"><span>&ndash;</span></div>' : '') + '<div class="dwl">' + label + '</div><div class="dww" style="height:' + (s.rows * hi) + 'px;min-width:' + s.width + 'px;"><div class="dw-ul">';
                    // Create wheel values
                    html += generateWheelItems(l);
                    html += '</div><div class="dwwo"></div></div><div class="dwwol"></div></div></td>';
                    l++;
                }
                html += '</tr></table></div></div>';
            }
            html += (s.display != 'inline' ? '<div class="dwbc' + (s.button3 ? ' dwbc-p' : '') + '"><span class="dwbw dwb-s"><span class="dwb">' + s.setText + '</span></span>' + (s.button3 ? '<span class="dwbw dwb-n"><span class="dwb">' + s.button3Text + '</span></span>' : '') + '<span class="dwbw dwb-c"><span class="dwb">' + s.cancelText + '</span></span></div></div>' : '<div class="dwcc"></div>') + '</div></div></div>';
            dw = $(html);

            scrollToPos();

            event('onMarkupReady', [dw]);

            // Show
            if (s.display != 'inline') {
                dw.appendTo('body');
                // Remove animation class
                setTimeout(function () {
                    dw.removeClass('dw-trans').find('.dw').removeClass(mAnim);
                }, 350);
            } else if (elm.is('div')) {
                elm.html(dw);
            } else {
                dw.insertAfter(elm);
            }

            event('onMarkupInserted', [dw]);

            visible = true;

            // Theme init
            theme.init(dw, that);

            if (s.display != 'inline') {
                // Init buttons
                that.tap($('.dwb-s span', dw), function () {
                    if (that.hide(false, 'set') !== false) {
                        that.setValue(false, true);
                        event('onSelect', [that.val]);
                    }
                });

                that.tap($('.dwb-c span', dw), function () {
                    that.cancel();
                });

                if (s.button3) {
                    that.tap($('.dwb-n span', dw), s.button3);
                }

                // prevent scrolling if not specified otherwise
                if (s.scrollLock) {
                    dw.bind('touchmove', function (e) {
                        if (mh <= wh && mw <= ww) {
                            e.preventDefault();
                        }
                    });
                }

                // Disable inputs to prevent bleed through (Android bug)
                $('input,select,button').each(function () {
                    if (!$(this).prop('disabled')) {
                        $(this).addClass('dwtd').prop('disabled', true);
                    }
                });

                // Set position
                position();
                $(window).bind('resize.dw', function () {
                    // Sometimes scrollTop is not correctly set, so we wait a little
                    clearTimeout(debounce);
                    debounce = setTimeout(function () {
                        position(true);
                    }, 100);
                });
            }

            // Events
            dw.delegate('.dwwl', 'DOMMouseScroll mousewheel', function (e) {
                if (!isReadOnly(this)) {
                    e.preventDefault();
                    e = e.originalEvent;
                    var delta = e.wheelDelta ? (e.wheelDelta / 120) : (e.detail ? (-e.detail / 3) : 0),
                        t = $('.dw-ul', this),
                        p = +t.data('pos'),
                        val = Math.round(p - delta);
                    setGlobals(t);
                    calc(t, val, delta < 0 ? 1 : 2);
                }
            }).delegate('.dwb, .dwwb', START_EVENT, function (e) {
                // Active button
                $(this).addClass('dwb-a');
            }).delegate('.dwwb', START_EVENT, function (e) {
                e.stopPropagation();
                e.preventDefault();
                var w = $(this).closest('.dwwl');
                if (testTouch(e) && !isReadOnly(w) && !w.hasClass('dwa')) {
                    click = true;
                    // + Button
                    var t = w.find('.dw-ul'),
                        func = $(this).hasClass('dwwbp') ? plus : minus;

                    setGlobals(t);
                    clearInterval(timer);
                    timer = setInterval(function () { func(t); }, s.delay);
                    func(t);
                }
            }).delegate('.dwwl', START_EVENT, function (e) {
                // Prevent scroll
                e.preventDefault();
                // Scroll start
                if (testTouch(e) && !move && !isReadOnly(this) && !click) {
                    move = true;
                    $(document).bind(MOVE_EVENT, onMove);
                    target = $('.dw-ul', this);
                    scrollable = s.mode != 'clickpick';
                    pos = +target.data('pos');
                    setGlobals(target);
                    moved = iv[index] !== undefined; // Don't allow tap, if still moving
                    start = getCoord(e, 'Y');
                    startTime = new Date();
                    stop = start;
                    that.scroll(target, index, pos, 0.001);
                    if (scrollable) {
                        target.closest('.dwwl').addClass('dwa');
                    }
                }
            });

            event('onShow', [dw, v]);
        };

        /**
        * Hides the scroller instance.
        */
        that.hide = function (prevAnim, btn) {
            // If onClose handler returns false, prevent hide
            if (!visible || event('onClose', [v, btn]) === false) {
                return false;
            }

            // Re-enable temporary disabled fields
            $('.dwtd').prop('disabled', false).removeClass('dwtd');
            elm.blur();

            // Hide wheels and overlay
            if (dw) {
                if (s.display != 'inline' && anim && !prevAnim) {
                    dw.addClass('dw-trans').find('.dw').addClass('dw-' + anim + ' dw-out');
                    setTimeout(function () {
                        dw.remove();
                        dw = null;
                    }, 350);
                } else {
                    dw.remove();
                    dw = null;
                }
                visible = false;
                pixels = {};
                // Stop positioning on window resize
                $(window).unbind('.dw');
            }
        };

        /**
        * Cancel and hide the scroller instance.
        */
        that.cancel = function () {
            if (that.hide(false, 'cancel') !== false) {
                event('onCancel', [that.val]);
            }
        };

        /**
        * Scroller initialization.
        */
        that.init = function (ss) {
            // Get theme defaults
            theme = extend({ defaults: {}, init: empty }, ms.themes[ss.theme || s.theme]);

            // Get language defaults
            lang = ms.i18n[ss.lang || s.lang];

            extend(settings, ss); // Update original user settings
            extend(s, theme.defaults, lang, settings);

            that.settings = s;

            // Unbind all events (if re-init)
            elm.unbind('.dw');

            var preset = ms.presets[s.preset];

            if (preset) {
                pres = preset.call(e, that);
                extend(s, pres, settings); // Load preset settings
                extend(methods, pres.methods); // Extend core methods
            }

            // Set private members
            m = Math.floor(s.rows / 2);
            hi = s.height;
            anim = s.animate;

            if (elm.data('dwro') !== undefined) {
                e.readOnly = bool(elm.data('dwro'));
            }

            if (visible) {
                that.hide();
            }

            if (s.display == 'inline') {
                that.show();
            } else {
                read();
                if (input && s.showOnFocus) {
                    // Set element readonly, save original state
                    elm.data('dwro', e.readOnly);
                    e.readOnly = true;
                    // Init show datewheel
                    elm.bind('focus.dw', function () { that.show(); });
                }
            }
        };

        that.trigger = function (name, params) {
            return event(name, params);
        };

        that.values = null;
        that.val = null;
        that.temp = null;
        that._selectedValues = {}; // [];

        that.init(settings);
    }

    function testProps(props) {
        var i;
        for (i in props) {
            if (mod[props[i]] !== undefined) {
                return true;
            }
        }
        return false;
    }

    function testPrefix() {
        var prefixes = ['Webkit', 'Moz', 'O', 'ms'],
            p;

        for (p in prefixes) {
            if (testProps([prefixes[p] + 'Transform'])) {
                return '-' + prefixes[p].toLowerCase();
            }
        }
        return '';
    }

    function getInst(e) {
        return scrollers[e.id];
    }

    function getCoord(e, c) {
        var org = e.originalEvent,
            ct = e.changedTouches;
        return ct || (org && org.changedTouches) ? (org ? org.changedTouches[0]['page' + c] : ct[0]['page' + c]) : e['page' + c];

    }

    function bool(v) {
        return (v === true || v == 'true');
    }

    function constrain(val, min, max) {
        val = val > max ? max : val;
        val = val < min ? min : val;
        return val;
    }

    function calc(t, val, dir, anim, orig) {
        val = constrain(val, min, max);

        var cell = $('.dw-li', t).eq(val),
            o = orig === undefined ? val : orig,
            idx = index,
            time = anim ? (val == o ? 0.1 : Math.abs((val - o) * 0.1)) : 0;

        // Set selected scroller value
        inst.temp[idx] = cell.attr('data-val');

        inst.scroll(t, idx, val, time, orig);

        setTimeout(function () {
            // Validate
            inst.validate(idx, dir, time, orig);
        }, 10);
    }

    function init(that, method, args) {
        if (methods[method]) {
            return methods[method].apply(that, Array.prototype.slice.call(args, 1));
        }
        if (typeof method === 'object') {
            return methods.init.call(that, method);
        }
        return that;
    }

    var scrollers = {},
        timer,
        empty = function () { },
        h,
        min,
        max,
        inst, // Current instance
        date = new Date(),
        uuid = date.getTime(),
        move,
        click,
        target,
        index,
        start,
        stop,
        startTime,
        pos,
        moved,
        scrollable,
        mod = document.createElement('modernizr').style,
        has3d = testProps(['perspectiveProperty', 'WebkitPerspective', 'MozPerspective', 'OPerspective', 'msPerspective']),
        prefix = testPrefix(),
        extend = $.extend,
        tap,
        touch,
        START_EVENT = 'touchstart mousedown',
        MOVE_EVENT = 'touchmove mousemove',
        END_EVENT = 'touchend mouseup',
        onMove = function (e) {
            if (scrollable) {
                e.preventDefault();
                stop = getCoord(e, 'Y');
                inst.scroll(target, index, constrain(pos + (start - stop) / h, min - 1, max + 1));
            }
            moved = true;
        },
        defaults = {
            // Options
            width: 70,
            height: 40,
            rows: 3,
            delay: 300,
            disabled: false,
            readonly: false,
            showOnFocus: true,
            showLabel: true,
            wheels: [],
            theme: '',
            headerText: '{value}',
            display: 'modal',
            mode: 'scroller',
            preset: '',
            lang: 'en-US',
            setText: 'Set',
            cancelText: 'Cancel',
            scrollLock: true,
            tap: true,
            formatResult: function (d) {
                return d.join(' ');
            },
            parseValue: function (value, inst) {
                var w = inst.settings.wheels,
                    val = value.split(' '),
                    ret = [],
                    j = 0,
                    i,
                    l,
                    v;

                for (i = 0; i < w.length; i++) {
                    for (l in w[i]) {
                        if (w[i][l][val[j]] !== undefined) {
                            ret.push(val[j]);
                        } else {
                            for (v in w[i][l]) { // Select first value from wheel
                                ret.push(v);
                                break;
                            }
                        }
                        j++;
                    }
                }
                return ret;
            }
        },

        methods = {
            init: function (options) {
                if (options === undefined) {
                    options = {};
                }

                return this.each(function () {
                    if (!this.id) {
                        uuid += 1;
                        this.id = 'scoller' + uuid;
                    }
                    scrollers[this.id] = new Scroller(this, options);
                });
            },
            enable: function () {
                return this.each(function () {
                    var inst = getInst(this);
                    if (inst) {
                        inst.enable();
                    }
                });
            },
            disable: function () {
                return this.each(function () {
                    var inst = getInst(this);
                    if (inst) {
                        inst.disable();
                    }
                });
            },
            isDisabled: function () {
                var inst = getInst(this[0]);
                if (inst) {
                    return inst.settings.disabled;
                }
            },
            isVisible: function () {
                var inst = getInst(this[0]);
                if (inst) {
                    return inst.isVisible();
                }
            },
            option: function (option, value) {
                return this.each(function () {
                    var inst = getInst(this);
                    if (inst) {
                        var obj = {};
                        if (typeof option === 'object') {
                            obj = option;
                        } else {
                            obj[option] = value;
                        }
                        inst.init(obj);
                    }
                });
            },
            setValue: function (d, fill, time, temp) {
                return this.each(function () {
                    var inst = getInst(this);
                    if (inst) {
                        inst.temp = d;
                        inst.setValue(true, fill, time, temp);
                    }
                });
            },
            getInst: function () {
                return getInst(this[0]);
            },
            getValue: function () {
                var inst = getInst(this[0]);
                if (inst) {
                    return inst.values;
                }
            },
            getValues: function () {
                var inst = getInst(this[0]);
                if (inst) {
                    return inst.getValues();
                }
            },
            show: function () {
                var inst = getInst(this[0]);
                if (inst) {
                    return inst.show();
                }
            },
            hide: function () {
                return this.each(function () {
                    var inst = getInst(this);
                    if (inst) {
                        inst.hide();
                    }
                });
            },
            destroy: function () {
                return this.each(function () {
                    var inst = getInst(this);
                    if (inst) {
                        inst.hide();
                        $(this).unbind('.dw');
                        delete scrollers[this.id];
                        if ($(this).is('input')) {
                            this.readOnly = bool($(this).data('dwro'));
                        }
                    }
                });
            }
        };

    $(document).bind(END_EVENT, function (e) {
        if (move) {
            var time = new Date() - startTime,
                val = constrain(pos + (start - stop) / h, min - 1, max + 1),
                speed,
                dist,
                tindex,
                ttop = target.offset().top;

            if (time < 300) {
                speed = (stop - start) / time;
                dist = (speed * speed) / (2 * 0.0006);
                if (stop - start < 0) {
                    dist = -dist;
                }
            } else {
                dist = stop - start;
            }

            tindex = Math.round(pos - dist / h);

            if (!dist && !moved) { // this is a "tap"
                var idx = Math.floor((stop - ttop) / h),
                    li = $('.dw-li', target).eq(idx),
                    hl = scrollable;

                if (inst.trigger('onValueTap', [li]) !== false) {
                    tindex = idx;
                } else {
                    hl = true;
                }

                if (hl) {
                    li.addClass('dw-hl'); // Highlight
                    setTimeout(function () {
                        li.removeClass('dw-hl');
                    }, 200);
                }
            }

            if (scrollable) {
                calc(target, tindex, 0, true, Math.round(val));
            }

            move = false;
            target = null;

            $(document).unbind(MOVE_EVENT, onMove);
        }

        if (click) {
            clearInterval(timer);
            click = false;
        }

        $('.dwb-a').removeClass('dwb-a');

    }).bind('mouseover mouseup mousedown click', function (e) { // Prevent standard behaviour on body click
        if (tap) {
            e.stopPropagation();
            e.preventDefault();
            return false;
        }
    });

    $.fn.mobiscroll = function (method) {
        extend(this, $.mobiscroll.shorts);
        return init(this, method, arguments);
    };

    $.mobiscroll = $.mobiscroll || {
        /**
        * Set settings for all instances.
        * @param {Object} o - New default settings.
        */
        setDefaults: function (o) {
            extend(defaults, o);
        },
        presetShort: function (name) {
            this.shorts[name] = function (method) {
                return init(this, extend(method, { preset: name }), arguments);
            };
        },
        shorts: {},
        presets: {},
        themes: {},
        i18n: {}
    };

    $.scroller = $.scroller || $.mobiscroll;
    $.fn.scroller = $.fn.scroller || $.fn.mobiscroll;
	$.mobiscroll.i18n.zh = $.extend($.mobiscroll.i18n.zh, {
			dateFormat: 'yyyy-mm-dd',
			dateOrder: 'yymmdd',
			dayNames: ['周日', '周一;', '周二;', '周三', '周四', '周五', '周六'],
			dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
			dayText: '日',
			hourText: '时',
			minuteText: '分',
			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			monthNamesShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
			monthText: '月',
			secText: '秒',
			timeFormat: 'HH:ii',
			timeWheels: 'HHii',
			yearText: '年'
		});
		$.mobiscroll.i18n.zh = $.extend($.mobiscroll.i18n.zh, {
			setText: '确定',
			cancelText: '取消'
		});
		var theme = {
			defaults: {
				dateOrder: 'Mddyy',
				mode: 'mixed',
				rows: 5,
				width: 70,
				height: 36,
				showLabel: true,
				useShortLabels: true
			}
		}

		$.mobiscroll.themes['android-ics'] = theme;
		$.mobiscroll.themes['android-ics light'] = theme;

})(jQuery);
/*jslint eqeq: true, plusplus: true, undef: true, sloppy: true, vars: true, forin: true */
(function ($) {

    var ms = $.mobiscroll,
        date = new Date(),
        defaults = {
            dateFormat: 'mm/dd/yy',
            dateOrder: 'mmddy',
            timeWheels: 'hhiiA',
            timeFormat: 'hh:ii A',
            startYear: date.getFullYear() - 100,
            endYear: date.getFullYear() + 1,
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
            dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            shortYearCutoff: '+10',
            monthText: 'Month',
            dayText: 'Day',
            yearText: 'Year',
            hourText: 'Hours',
            minuteText: 'Minutes',
            secText: 'Seconds',
            ampmText: '&nbsp;',
            nowText: 'Now',
            showNow: false,
            stepHour: 1,
            stepMinute: 1,
            stepSecond: 1,
            separator: ' '
        },
        preset = function (inst) {
            var that = $(this),
                html5def = {},
                format;
            // Force format for html5 date inputs (experimental)
            if (that.is('input')) {
                switch (that.attr('type')) {
                case 'date':
                    format = 'yy-mm-dd';
                    break;
                case 'datetime':
                    format = 'yy-mm-ddTHH:ii:ssZ';
                    break;
                case 'datetime-local':
                    format = 'yy-mm-ddTHH:ii:ss';
                    break;
                case 'month':
                    format = 'yy-mm';
                    html5def.dateOrder = 'mmyy';
                    break;
                case 'time':
                    format = 'HH:ii:ss';
                    break;
                }
                // Check for min/max attributes
                var min = that.attr('min'),
                    max = that.attr('max');
                if (min) {
                    html5def.minDate = ms.parseDate(format, min);
                }
                if (max) {
                    html5def.maxDate = ms.parseDate(format, max);
                }
            }

            // Set year-month-day order
            var s = $.extend({}, defaults, html5def, inst.settings),
                offset = 0,
                wheels = [],
                ord = [],
                o = {},
                i,
                k,
                f = { y: 'getFullYear', m: 'getMonth', d: 'getDate', h: getHour, i: getMinute, s: getSecond, a: getAmPm },
                p = s.preset,
                dord = s.dateOrder,
                tord = s.timeWheels,
                regen = dord.match(/D/),
                ampm = tord.match(/a/i),
                hampm = tord.match(/h/),
                hformat = p == 'datetime' ? s.dateFormat + s.separator + s.timeFormat : p == 'time' ? s.timeFormat : s.dateFormat,
                defd = new Date(),
                stepH = s.stepHour,
                stepM = s.stepMinute,
                stepS = s.stepSecond,
                mind = s.minDate || new Date(s.startYear, 0, 1),
                maxd = s.maxDate || new Date(s.endYear, 11, 31, 23, 59, 59);

            inst.settings = s;

            format = format || hformat;

            if (p.match(/date/i)) {

                // Determine the order of year, month, day wheels
                $.each(['y', 'm', 'd'], function (j, v) {
                    i = dord.search(new RegExp(v, 'i'));
                    if (i > -1) {
                        ord.push({ o: i, v: v });
                    }
                });
                ord.sort(function (a, b) { return a.o > b.o ? 1 : -1; });
                $.each(ord, function (i, v) {
                    o[v.v] = i;
                });

                var w = {};
                for (k = 0; k < 3; k++) {
                    if (k == o.y) {
                        offset++;
                        w[s.yearText] = {};
                        var start = mind.getFullYear(),
                            end = maxd.getFullYear();
                        for (i = start; i <= end; i++) {
                            w[s.yearText][i] = dord.match(/yy/i) ? i : (i + '').substr(2, 2);
                        }
                    } else if (k == o.m) {
                        offset++;
                        w[s.monthText] = {};
                        for (i = 0; i < 12; i++) {
                            var str = dord.replace(/[dy]/gi, '').replace(/mm/, i < 9 ? '0' + (i + 1) : i + 1).replace(/m/, (i + 1));
                            w[s.monthText][i] = str.match(/MM/) ? str.replace(/MM/, '<span class="dw-mon">' + s.monthNames[i] + '</span>') : str.replace(/M/, '<span class="dw-mon">' + s.monthNamesShort[i] + '</span>');
                        }
                    } else if (k == o.d) {
                        offset++;
                        w[s.dayText] = {};
                        for (i = 1; i < 32; i++) {
                            w[s.dayText][i] = dord.match(/dd/i) && i < 10 ? '0' + i : i;
                        }
                    }
                }
                wheels.push(w);
            }

            if (p.match(/time/i)) {

                // Determine the order of hours, minutes, seconds wheels
                ord = [];
                $.each(['h', 'i', 's', 'a'], function (i, v) {
                    i = tord.search(new RegExp(v, 'i'));
                    if (i > -1) {
                        ord.push({ o: i, v: v });
                    }
                });
                ord.sort(function (a, b) {
                    return a.o > b.o ? 1 : -1;
                });
                $.each(ord, function (i, v) {
                    o[v.v] = offset + i;
                });

                w = {};
                for (k = offset; k < offset + 4; k++) {
                    if (k == o.h) {
                        offset++;
                        w[s.hourText] = {};
                        for (i = 0; i < (hampm ? 12 : 24); i += stepH) {
                            w[s.hourText][i] = hampm && i == 0 ? 12 : tord.match(/hh/i) && i < 10 ? '0' + i : i;
                        }
                    } else if (k == o.i) {
                        offset++;
                        w[s.minuteText] = {};
                        for (i = 0; i < 60; i += stepM) {
                            w[s.minuteText][i] = tord.match(/ii/) && i < 10 ? '0' + i : i;
                        }
                    } else if (k == o.s) {
                        offset++;
                        w[s.secText] = {};
                        for (i = 0; i < 60; i += stepS) {
                            w[s.secText][i] = tord.match(/ss/) && i < 10 ? '0' + i : i;
                        }
                    } else if (k == o.a) {
                        offset++;
                        var upper = tord.match(/A/);
                        w[s.ampmText] = { 0: upper ? 'AM' : 'am', 1: upper ? 'PM' : 'pm' };
                    }

                }

                wheels.push(w);
            }

            function get(d, i, def) {
                if (o[i] !== undefined) {
                    return +d[o[i]];
                }
                if (def !== undefined) {
                    return def;
                }
                return defd[f[i]] ? defd[f[i]]() : f[i](defd);
            }

            function step(v, st) {
                return Math.floor(v / st) * st;
            }

            function getHour(d) {
                var hour = d.getHours();
                hour = hampm && hour >= 12 ? hour - 12 : hour;
                return step(hour, stepH);
            }

            function getMinute(d) {
                return step(d.getMinutes(), stepM);
            }

            function getSecond(d) {
                return step(d.getSeconds(), stepS);
            }

            function getAmPm(d) {
                return ampm && d.getHours() > 11 ? 1 : 0;
            }

            function getDate(d) {
                var hour = get(d, 'h', 0);
                return new Date(get(d, 'y'), get(d, 'm'), get(d, 'd', 1), get(d, 'a') ? hour + 12 : hour, get(d, 'i', 0), get(d, 's', 0));
            }

            inst.setDate = function (d, fill, time, temp) {
                var i;
                // Set wheels
                for (i in o) {
                    this.temp[o[i]] = d[f[i]] ? d[f[i]]() : f[i](d);
                }
                this.setValue(true, fill, time, temp);
            };

            inst.getDate = function (d) {
                return getDate(d);
            };

            return {
                button3Text: s.showNow ? s.nowText : undefined,
                button3: s.showNow ? function () { inst.setDate(new Date(), false, 0.3, true); } : undefined,
                wheels: wheels,
                headerText: function (v) {
                    return ms.formatDate(hformat, getDate(inst.temp), s);
                },
                /**
                * Builds a date object from the wheel selections and formats it to the given date/time format
                * @param {Array} d - An array containing the selected wheel values
                * @return {String} - The formatted date string
                */
                formatResult: function (d) {
                    return ms.formatDate(format, getDate(d), s);
                },
                /**
                * Builds a date object from the input value and returns an array to set wheel values
                * @return {Array} - An array containing the wheel values to set
                */
                parseValue: function (val) {
                    var d = new Date(),
                        i,
                        result = [];
                    try {
                        d = ms.parseDate(format, val, s);
                    } catch (e) {
                    }
                    // Set wheels
                    for (i in o) {
                        result[o[i]] = d[f[i]] ? d[f[i]]() : f[i](d);
                    }
                    return result;
                },
                /**
                * Validates the selected date to be in the minDate / maxDate range and sets unselectable values to disabled
                * @param {Object} dw - jQuery object containing the generated html
                * @param {Integer} [i] - Index of the changed wheel, not set for initial validation
                */
                validate: function (dw, i) {
                    var temp = inst.temp, //.slice(0),
                        mins = { y: mind.getFullYear(), m: 0, d: 1, h: 0, i: 0, s: 0, a: 0 },
                        maxs = { y: maxd.getFullYear(), m: 11, d: 31, h: step(hampm ? 11 : 23, stepH), i: step(59, stepM), s: step(59, stepS), a: 1 },
                        minprop = true,
                        maxprop = true;
                    $.each(['y', 'm', 'd', 'a', 'h', 'i', 's'], function (x, i) {
                        if (o[i] !== undefined) {
                            var min = mins[i],
                                max = maxs[i],
                                maxdays = 31,
                                val = get(temp, i),
                                t = $('.dw-ul', dw).eq(o[i]),
                                y,
                                m;
                            if (i == 'd') {
                                y = get(temp, 'y');
                                m = get(temp, 'm');
                                maxdays = 32 - new Date(y, m, 32).getDate();
                                max = maxdays;
                                if (regen) {
                                    $('.dw-li', t).each(function () {
                                        var that = $(this),
                                            d = that.data('val'),
                                            w = new Date(y, m, d).getDay(),
                                            str = dord.replace(/[my]/gi, '').replace(/dd/, d < 10 ? '0' + d : d).replace(/d/, d);
                                        $('.dw-i', that).html(str.match(/DD/) ? str.replace(/DD/, '<span class="dw-day">' + s.dayNames[w] + '</span>') : str.replace(/D/, '<span class="dw-day">' + s.dayNamesShort[w] + '</span>'));
                                    });
                                }
                            }
                            if (minprop && mind) {
                                min = mind[f[i]] ? mind[f[i]]() : f[i](mind);
                            }
                            if (maxprop && maxd) {
                                max = maxd[f[i]] ? maxd[f[i]]() : f[i](maxd);
                            }
                            if (i != 'y') {
                                var i1 = $('.dw-li', t).index($('.dw-li[data-val="' + min + '"]', t)),
                                    i2 = $('.dw-li', t).index($('.dw-li[data-val="' + max + '"]', t));
                                $('.dw-li', t).removeClass('dw-v').slice(i1, i2 + 1).addClass('dw-v');
                                if (i == 'd') { // Hide days not in month
                                    $('.dw-li', t).removeClass('dw-h').slice(maxdays).addClass('dw-h');
                                }
                            }
                            if (val < min) {
                                val = min;
                            }
                            if (val > max) {
                                val = max;
                            }
                            if (minprop) {
                                minprop = val == min;
                            }
                            if (maxprop) {
                                maxprop = val == max;
                            }
                            // Disable some days
                            if (s.invalid && i == 'd') {
                                var idx = [];
                                // Disable exact dates
                                if (s.invalid.dates) {
                                    $.each(s.invalid.dates, function (i, v) {
                                        if (v.getFullYear() == y && v.getMonth() == m) {
                                            idx.push(v.getDate() - 1);
                                        }
                                    });
                                }
                                // Disable days of week
                                if (s.invalid.daysOfWeek) {
                                    var first = new Date(y, m, 1).getDay(),
                                        j;
                                    $.each(s.invalid.daysOfWeek, function (i, v) {
                                        for (j = v - first; j < maxdays; j += 7) {
                                            if (j >= 0) {
                                                idx.push(j);
                                            }
                                        }
                                    });
                                }
                                // Disable days of month
                                if (s.invalid.daysOfMonth) {
                                    $.each(s.invalid.daysOfMonth, function (i, v) {
                                        v = (v + '').split('/');
                                        if (v[1]) {
                                            if (v[0] - 1 == m) {
                                                idx.push(v[1] - 1);
                                            }
                                        } else {
                                            idx.push(v[0] - 1);
                                        }
                                    });
                                }
                                $.each(idx, function (i, v) {
                                    $('.dw-li', t).eq(v).removeClass('dw-v');
                                });
                            }

                            // Set modified value
                            temp[o[i]] = val;
                        }
                    });
                },
                methods: {
                    /**
                    * Returns the currently selected date.
                    * @param {Boolean} temp - If true, return the currently shown date on the picker, otherwise the last selected one
                    * @return {Date}
                    */
                    getDate: function (temp) {
                        var inst = $(this).mobiscroll('getInst');
                        if (inst) {
                            return inst.getDate(temp ? inst.temp : inst.values);
                        }
                    },
                    /**
                    * Sets the selected date
                    * @param {Date} d - Date to select.
                    * @param {Boolean} [fill] - Also set the value of the associated input element. Default is true.
                    * @return {Object} - jQuery object to maintain chainability
                    */
                    setDate: function (d, fill, time, temp) {
                        if (fill == undefined) {
                            fill = false;
                        }
                        return this.each(function () {
                            var inst = $(this).mobiscroll('getInst');
                            if (inst) {
                                inst.setDate(d, fill, time, temp);
                            }
                        });
                    }
                }
            };
        };

    $.each(['date', 'time', 'datetime'], function (i, v) {
        ms.presets[v] = preset;
        ms.presetShort(v);
    });

    /**
    * Format a date into a string value with a specified format.
    * @param {String} format - Output format.
    * @param {Date} date - Date to format.
    * @param {Object} settings - Settings.
    * @return {String} - Returns the formatted date string.
    */
    ms.formatDate = function (format, date, settings) {
        if (!date) {
            return null;
        }
        var s = $.extend({}, defaults, settings),
            look = function (m) { // Check whether a format character is doubled
                var n = 0;
                while (i + 1 < format.length && format.charAt(i + 1) == m) {
                    n++;
                    i++;
                }
                return n;
            },
            f1 = function (m, val, len) { // Format a number, with leading zero if necessary
                var n = '' + val;
                if (look(m)) {
                    while (n.length < len) {
                        n = '0' + n;
                    }
                }
                return n;
            },
            f2 = function (m, val, s, l) { // Format a name, short or long as requested
                return (look(m) ? l[val] : s[val]);
            },
            i,
            output = '',
            literal = false;

        for (i = 0; i < format.length; i++) {
            if (literal) {
                if (format.charAt(i) == "'" && !look("'")) {
                    literal = false;
                } else {
                    output += format.charAt(i);
                }
            } else {
                switch (format.charAt(i)) {
                case 'd':
                    output += f1('d', date.getDate(), 2);
                    break;
                case 'D':
                    output += f2('D', date.getDay(), s.dayNamesShort, s.dayNames);
                    break;
                case 'o':
                    output += f1('o', (date.getTime() - new Date(date.getFullYear(), 0, 0).getTime()) / 86400000, 3);
                    break;
                case 'm':
                    output += f1('m', date.getMonth() + 1, 2);
                    break;
                case 'M':
                    output += f2('M', date.getMonth(), s.monthNamesShort, s.monthNames);
                    break;
                case 'y':
                    output += (look('y') ? date.getFullYear() : (date.getYear() % 100 < 10 ? '0' : '') + date.getYear() % 100);
                    break;
                case 'h':
                    var h = date.getHours();
                    output += f1('h', (h > 12 ? (h - 12) : (h == 0 ? 12 : h)), 2);
                    break;
                case 'H':
                    output += f1('H', date.getHours(), 2);
                    break;
                case 'i':
                    output += f1('i', date.getMinutes(), 2);
                    break;
                case 's':
                    output += f1('s', date.getSeconds(), 2);
                    break;
                case 'a':
                    output += date.getHours() > 11 ? 'pm' : 'am';
                    break;
                case 'A':
                    output += date.getHours() > 11 ? 'PM' : 'AM';
                    break;
                case "'":
                    if (look("'")) {
                        output += "'";
                    } else {
                        literal = true;
                    }
                    break;
                default:
                    output += format.charAt(i);
                }
            }
        }
        return output;
    };

    /**
    * Extract a date from a string value with a specified format.
    * @param {String} format - Input format.
    * @param {String} value - String to parse.
    * @param {Object} settings - Settings.
    * @return {Date} - Returns the extracted date.
    */
    ms.parseDate = function (format, value, settings) {
        var def = new Date();

        if (!format || !value) {
            return def;
        }

        value = (typeof value == 'object' ? value.toString() : value + '');

        var s = $.extend({}, defaults, settings),
            shortYearCutoff = s.shortYearCutoff,
            year = def.getFullYear(),
            month = def.getMonth() + 1,
            day = def.getDate(),
            doy = -1,
            hours = def.getHours(),
            minutes = def.getMinutes(),
            seconds = 0, //def.getSeconds(),
            ampm = -1,
            literal = false, // Check whether a format character is doubled
            lookAhead = function (match) {
                var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) == match);
                if (matches) {
                    iFormat++;
                }
                return matches;
            },
            getNumber = function (match) { // Extract a number from the string value
                lookAhead(match);
                var size = (match == '@' ? 14 : (match == '!' ? 20 : (match == 'y' ? 4 : (match == 'o' ? 3 : 2)))),
                    digits = new RegExp('^\\d{1,' + size + '}'),
                    num = value.substr(iValue).match(digits);

                if (!num) {
                    return 0;
                }
                //throw 'Missing number at position ' + iValue;
                iValue += num[0].length;
                return parseInt(num[0], 10);
            },
            getName = function (match, s, l) { // Extract a name from the string value and convert to an index
                var names = (lookAhead(match) ? l : s),
                    i;

                for (i = 0; i < names.length; i++) {
                    if (value.substr(iValue, names[i].length).toLowerCase() == names[i].toLowerCase()) {
                        iValue += names[i].length;
                        return i + 1;
                    }
                }
                return 0;
                //throw 'Unknown name at position ' + iValue;
            },
            checkLiteral = function () {
                //if (value.charAt(iValue) != format.charAt(iFormat))
                //throw 'Unexpected literal at position ' + iValue;
                iValue++;
            },
            iValue = 0,
            iFormat;

        for (iFormat = 0; iFormat < format.length; iFormat++) {
            if (literal) {
                if (format.charAt(iFormat) == "'" && !lookAhead("'")) {
                    literal = false;
                } else {
                    checkLiteral();
                }
            } else {
                switch (format.charAt(iFormat)) {
                case 'd':
                    day = getNumber('d');
                    break;
                case 'D':
                    getName('D', s.dayNamesShort, s.dayNames);
                    break;
                case 'o':
                    doy = getNumber('o');
                    break;
                case 'm':
                    month = getNumber('m');
                    break;
                case 'M':
                    month = getName('M', s.monthNamesShort, s.monthNames);
                    break;
                case 'y':
                    year = getNumber('y');
                    break;
                case 'H':
                    hours = getNumber('H');
                    break;
                case 'h':
                    hours = getNumber('h');
                    break;
                case 'i':
                    minutes = getNumber('i');
                    break;
                case 's':
                    seconds = getNumber('s');
                    break;
                case 'a':
                    ampm = getName('a', ['am', 'pm'], ['am', 'pm']) - 1;
                    break;
                case 'A':
                    ampm = getName('A', ['am', 'pm'], ['am', 'pm']) - 1;
                    break;
                case "'":
                    if (lookAhead("'")) {
                        checkLiteral();
                    } else {
                        literal = true;
                    }
                    break;
                default:
                    checkLiteral();
                }
            }
        }
        if (year < 100) {
            year += new Date().getFullYear() - new Date().getFullYear() % 100 +
                (year <= (typeof shortYearCutoff != 'string' ? shortYearCutoff : new Date().getFullYear() % 100 + parseInt(shortYearCutoff, 10)) ? 0 : -100);
        }
        if (doy > -1) {
            month = 1;
            day = doy;
            do {
                var dim = 32 - new Date(year, month - 1, 32).getDate();
                if (day <= dim) {
                    break;
                }
                month++;
                day -= dim;
            } while (true);
        }
        hours = (ampm == -1) ? hours : ((ampm && hours < 12) ? (hours + 12) : (!ampm && hours == 12 ? 0 : hours));
        var date = new Date(year, month - 1, day, hours, minutes, seconds);
        if (date.getFullYear() != year || date.getMonth() + 1 != month || date.getDate() != day) {
            throw 'Invalid date';
        }
        return date;
    };

})(jQuery);
(function($) {
	$.attrFn = $.attrFn || {};

	// navigator.userAgent.toLowerCase() isn't reliable for Chrome installs
	// on mobile devices. As such, we will create a boolean isChromeDesktop
	// The reason that we need to do this is because Chrome annoyingly
	// purports support for touch events even if the underlying hardware
	// does not!
	var agent = navigator.userAgent.toLowerCase(),
		isChromeDesktop = (agent.indexOf('chrome') > -1 && ((agent.indexOf('windows') > -1) || (agent.indexOf('macintosh') > -1) || (agent.indexOf('linux') > -1)) && agent.indexOf('mobile') < 0 && agent.indexOf('android') < 0),

		settings = {
			tap_pixel_range: 5,
			swipe_h_threshold: 50,
			swipe_v_threshold: 50,
			taphold_threshold: 750,
			doubletap_int: 500,

			touch_capable: (window.navigator.msPointerEnabled) ? false : ('ontouchstart' in window && !isChromeDesktop),
			orientation_support: ('orientation' in window && 'onorientationchange' in window),

			startevent: (window.navigator.msPointerEnabled) ? 'MSPointerDown' : (('ontouchstart' in window && !isChromeDesktop) ? 'touchstart' : 'mousedown'),
			endevent: (window.navigator.msPointerEnabled) ? 'MSPointerUp' : (('ontouchstart' in window && !isChromeDesktop) ? 'touchend' : 'mouseup'),
			moveevent: (window.navigator.msPointerEnabled) ? 'MSPointerMove' : (('ontouchstart' in window && !isChromeDesktop) ? 'touchmove' : 'mousemove'),
			tapevent: ('ontouchstart' in window && !isChromeDesktop) ? 'tap' : 'click',
			scrollevent: ('ontouchstart' in window && !isChromeDesktop) ? 'touchmove' : 'scroll',

			hold_timer: null,
			tap_timer: null
		};

	// Convenience functions:
	$.isTouchCapable = function() {
		return settings.touch_capable;
	};
	$.getStartEvent = function() {
		return settings.startevent;
	};
	$.getEndEvent = function() {
		return settings.endevent;
	};
	$.getMoveEvent = function() {
		return settings.moveevent;
	};
	$.getTapEvent = function() {
		return settings.tapevent;
	};
	$.getScrollEvent = function() {
		return settings.scrollevent;
	};

	// Add Event shortcuts:
	$.each(['tapstart', 'tapend', 'tapmove', 'tap', 'tap2', 'tap3', 'tap4', 'singletap', 'doubletap', 'taphold', 'swipe', 'swipeup', 'swiperight', 'swipedown', 'swipeleft', 'swipeend', 'scrollstart', 'scrollend', 'orientationchange'], function(i, name) {
		$.fn[name] = function(fn) {
			return fn ? this.on(name, fn) : this.trigger(name);
		};

		$.attrFn[name] = true;
	});

	// tapstart Event:
	$.event.special.tapstart = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject);

			$this.on(settings.startevent, function(e) {
				$this.data('callee', arguments.callee);
				if (e.which && e.which !== 1) {
					return false;
				}

				var origEvent = e.originalEvent,
					touchData = {
						'position': {
							'x': ((settings.touch_capable) ? origEvent.touches[0].screenX : e.screenX),
							'y': (settings.touch_capable) ? origEvent.touches[0].screenY : e.screenY
						},
						'offset': {
							'x': (settings.touch_capable) ? origEvent.touches[0].pageX - origEvent.touches[0].target.offsetLeft : e.offsetX,
							'y': (settings.touch_capable) ? origEvent.touches[0].pageY - origEvent.touches[0].target.offsetTop : e.offsetY
						},
						'time': Date.now(),
						'target': e.target
					};

				triggerCustomEvent(thisObject, 'tapstart', e, touchData);
				return true;
			});
		},

		remove: function() {
			$(this).off(settings.startevent, $(this).data.callee);
		}
	};

	// tapmove Event:
	$.event.special.tapmove = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject);

			$this.on(settings.moveevent, function(e) {
				$this.data('callee', arguments.callee);

				var origEvent = e.originalEvent,
					touchData = {
						'position': {
							'x': ((settings.touch_capable) ? origEvent.touches[0].screenX : e.screenX),
							'y': (settings.touch_capable) ? origEvent.touches[0].screenY : e.screenY
						},
						'offset': {
							'x': (settings.touch_capable) ? origEvent.touches[0].pageX - origEvent.touches[0].target.offsetLeft : e.offsetX,
							'y': (settings.touch_capable) ? origEvent.touches[0].pageY - origEvent.touches[0].target.offsetTop : e.offsetY
						},
						'time': Date.now(),
						'target': e.target
					};

				triggerCustomEvent(thisObject, 'tapmove', e, touchData);
				return true;
			});
		},
		remove: function() {
			$(this).off(settings.moveevent, $(this).data.callee);
		}
	}

	// tapend Event:
	$.event.special.tapend = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject);

			$this.on(settings.endevent, function(e) {
				// Touch event data:
				$this.data('callee', arguments.callee);

				var origEvent = e.originalEvent;
				var touchData = {
					'position': {
						'x': (settings.touch_capable) ? origEvent.changedTouches[0].screenX : e.screenX,
						'y': (settings.touch_capable) ? origEvent.changedTouches[0].screenY : e.screenY
					},
					'offset': {
						'x': (settings.touch_capable) ? origEvent.changedTouches[0].pageX - origEvent.changedTouches[0].target.offsetLeft : e.offsetX,
						'y': (settings.touch_capable) ? origEvent.changedTouches[0].pageY - origEvent.changedTouches[0].target.offsetTop : e.offsetY
					},
					'time': Date.now(),
					'target': e.target
				};
				triggerCustomEvent(thisObject, 'tapend', e, touchData);
				return true;
			});
		},
		remove: function() {
			$(this).off(settings.endevent, $(this).data.callee);
		}
	};

	// taphold Event:
	$.event.special.taphold = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject),
				origTarget,
				timer,
				start_pos = {
					x: 0,
					y: 0
				},
				end_x = 0,
				end_y = 0;

			$this.on(settings.startevent, function(e) {
					if (e.which && e.which !== 1) {
						return false;
					} else {
						$this.data('tapheld', false);
						origTarget = e.target;

						var origEvent = e.originalEvent;
						var start_time = Date.now(),
							startPosition = {
								'x': (settings.touch_capable) ? origEvent.touches[0].screenX : e.screenX,
								'y': (settings.touch_capable) ? origEvent.touches[0].screenY : e.screenY
							},
							startOffset = {
								'x': (settings.touch_capable) ? origEvent.touches[0].pageX - origEvent.touches[0].target.offsetLeft : e.offsetX,
								'y': (settings.touch_capable) ? origEvent.touches[0].pageY - origEvent.touches[0].target.offsetTop : e.offsetY
							};

						start_pos.x = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageX : e.pageX;
						start_pos.y = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageY : e.pageY;

						end_x = start_pos.x;
						end_y = start_pos.y;

						settings.hold_timer = window.setTimeout(function() {

							var diff_x = (start_pos.x - end_x),
								diff_y = (start_pos.y - end_y);

							if (e.target == origTarget && ((start_pos.x == end_x && start_pos.y == end_y) || (diff_x >= -(settings.tap_pixel_range) && diff_x <= settings.tap_pixel_range && diff_y >= -(settings.tap_pixel_range) && diff_y <= settings.tap_pixel_range))) {
								$this.data('tapheld', true);

								var end_time = Date.now(),
									endPosition = {
										'x': (settings.touch_capable) ? origEvent.touches[0].screenX : e.screenX,
										'y': (settings.touch_capable) ? origEvent.touches[0].screenY : e.screenY
									},
									endOffset = {
										'x': (settings.touch_capable) ? origEvent.touches[0].pageX - origEvent.touches[0].target.offsetLeft : e.offsetX,
										'y': (settings.touch_capable) ? origEvent.touches[0].pageY - origEvent.touches[0].target.offsetTop : e.offsetY
									};
								duration = end_time - start_time;

								// Build the touch data:
								var touchData = {
									'startTime': start_time,
									'endTime': end_time,
									'startPosition': startPosition,
									'startOffset': startOffset,
									'endPosition': endPosition,
									'endOffset': endOffset,
									'duration': duration,
									'target': e.target
								}
								$this.data('callee1', arguments.callee);
								triggerCustomEvent(thisObject, 'taphold', e, touchData);
							}
						}, settings.taphold_threshold);

						return true;
					}
				}).on(settings.endevent, function() {
					$this.data('callee2', arguments.callee);
					$this.data('tapheld', false);
					window.clearTimeout(settings.hold_timer);
				})
				.on(settings.moveevent, function(e) {
					$this.data('callee3', arguments.callee);

					end_x = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageX : e.pageX;
					end_y = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageY : e.pageY;
				});
		},

		remove: function() {
			$(this).off(settings.startevent, $(this).data.callee1).off(settings.endevent, $(this).data.callee2).off(settings.moveevent, $(this).data.callee3);
		}
	};

	// doubletap Event:
	$.event.special.doubletap = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject),
				origTarget,
				action,
				firstTap,
				origEvent,
				cooloff,
				cooling = false;

			$this.on(settings.startevent, function(e) {
				if (e.which && e.which !== 1) {
					return false;
				}
				$this.data('doubletapped', false);
				origTarget = e.target;
				$this.data('callee1', arguments.callee);

				origEvent = e.originalEvent;
				firstTap = {
					'position': {
						'x': (settings.touch_capable) ? origEvent.touches[0].screenX : e.screenX,
						'y': (settings.touch_capable) ? origEvent.touches[0].screenY : e.screenY
					},
					'offset': {
						'x': (settings.touch_capable) ? origEvent.touches[0].pageX - origEvent.touches[0].target.offsetLeft : e.offsetX,
						'y': (settings.touch_capable) ? origEvent.touches[0].pageY - origEvent.touches[0].target.offsetTop : e.offsetY
					},
					'time': Date.now(),
					'target': e.target
				};

				return true;
			}).on(settings.endevent, function(e) {

				var now = Date.now();
				var lastTouch = $this.data('lastTouch') || now + 1;
				var delta = now - lastTouch;
				window.clearTimeout(action);
				$this.data('callee2', arguments.callee);

				if (delta < settings.doubletap_int && (e.target == origTarget) && delta > 100) {
					$this.data('doubletapped', true);
					window.clearTimeout(settings.tap_timer);

					// Now get the current event:
					var lastTap = {
						'position': {
							'x': (settings.touch_capable) ? e.originalEvent.changedTouches[0].screenX : e.screenX,
							'y': (settings.touch_capable) ? e.originalEvent.changedTouches[0].screenY : e.screenY
						},
						'offset': {
							'x': (settings.touch_capable) ? e.originalEvent.changedTouches[0].pageX - e.originalEvent.changedTouches[0].target.offsetLeft : e.offsetX,
							'y': (settings.touch_capable) ? e.originalEvent.changedTouches[0].pageY - e.originalEvent.changedTouches[0].target.offsetTop : e.offsetY
						},
						'time': Date.now(),
						'target': e.target
					};

					var touchData = {
						'firstTap': firstTap,
						'secondTap': lastTap,
						'interval': lastTap.time - firstTap.time
					};

					if (!cooling) {
						triggerCustomEvent(thisObject, 'doubletap', e, touchData);
					}

					cooling = true;

					cooloff = window.setTimeout(function(e) {
						cooling = false;
					}, settings.doubletap_int);

				} else {
					$this.data('lastTouch', now);
					action = window.setTimeout(function(e) {
						window.clearTimeout(action);
					}, settings.doubletap_int, [e]);
				}
				$this.data('lastTouch', now);
			});
		},
		remove: function() {
			$(this).off(settings.startevent, $(this).data.callee1).off(settings.endevent, $(this).data.callee2);
		}
	};

	// singletap Event:
	// This is used in conjuction with doubletap when both events are needed on the same element
	$.event.special.singletap = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject),
				origTarget = null,
				startTime = null,
				start_pos = {
					x: 0,
					y: 0
				};

			$this.on(settings.startevent, function(e) {
				if (e.which && e.which !== 1) {
					return false;
				} else {
					startTime = Date.now();
					origTarget = e.target;
					$this.data('callee1', arguments.callee);

					// Get the start x and y position:
					start_pos.x = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageX : e.pageX;
					start_pos.y = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageY : e.pageY;
					return true;
				}
			}).on(settings.endevent, function(e) {
				$this.data('callee2', arguments.callee);
				if (e.target == origTarget) {
					// Get the end point:
					end_pos_x = (e.originalEvent.changedTouches) ? e.originalEvent.changedTouches[0].pageX : e.pageX;
					end_pos_y = (e.originalEvent.changedTouches) ? e.originalEvent.changedTouches[0].pageY : e.pageY;

					// We need to check if it was a taphold:

					settings.tap_timer = window.setTimeout(function() {
						if (!$this.data('doubletapped') && !$this.data('tapheld') && (start_pos.x == end_pos_x) && (start_pos.y == end_pos_y)) {
							var origEvent = e.originalEvent;
							var touchData = {
								'position': {
									'x': (settings.touch_capable) ? origEvent.changedTouches[0].screenX : e.screenX,
									'y': (settings.touch_capable) ? origEvent.changedTouches[0].screenY : e.screenY
								},
								'offset': {
									'x': (settings.touch_capable) ? origEvent.changedTouches[0].pageX - origEvent.changedTouches[0].target.offsetLeft : e.offsetX,
									'y': (settings.touch_capable) ? origEvent.changedTouches[0].pageY - origEvent.changedTouches[0].target.offsetTop : e.offsetY
								},
								'time': Date.now(),
								'target': e.target
							};

							// Was it a taphold?
							if ((touchData.time - startTime) < settings.taphold_threshold) {
								triggerCustomEvent(thisObject, 'singletap', e, touchData);
							}
						}
					}, settings.doubletap_int);
				}
			});
		},

		remove: function() {
			$(this).off(settings.startevent, $(this).data.callee1).off(settings.endevent, $(this).data.callee2);
		}
	};

	// tap Event:
	$.event.special.tap = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject),
				started = false,
				origTarget = null,
				start_time,
				start_pos = {
					x: 0,
					y: 0
				},
				touches;

			$this.on(settings.startevent, function(e) {
				$this.data('callee1', arguments.callee);

				if (e.which && e.which !== 1) {
					return false;
				} else {
					started = true;
					start_pos.x = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageX : e.pageX;
					start_pos.y = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageY : e.pageY;
					start_time = Date.now();
					origTarget = e.target;

					touches = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches : [e];
					return true;
				}
			}).on(settings.endevent, function(e) {
				$this.data('callee2', arguments.callee);

				// Only trigger if they've started, and the target matches:
				var end_x = (e.originalEvent.targetTouches) ? e.originalEvent.changedTouches[0].pageX : e.pageX,
					end_y = (e.originalEvent.targetTouches) ? e.originalEvent.changedTouches[0].pageY : e.pageY,
					diff_x = (start_pos.x - end_x),
					diff_y = (start_pos.y - end_y),
					eventName;

				if (origTarget == e.target && started && ((Date.now() - start_time) < settings.taphold_threshold) && ((start_pos.x == end_x && start_pos.y == end_y) || (diff_x >= -(settings.tap_pixel_range) && diff_x <= settings.tap_pixel_range && diff_y >= -(settings.tap_pixel_range) && diff_y <= settings.tap_pixel_range))) {
					var origEvent = e.originalEvent;
					var touchData = [];

					for (var i = 0; i < touches.length; i++) {
						var touch = {
							'position': {
								'x': (settings.touch_capable) ? origEvent.changedTouches[i].screenX : e.screenX,
								'y': (settings.touch_capable) ? origEvent.changedTouches[i].screenY : e.screenY
							},
							'offset': {
								'x': (settings.touch_capable) ? origEvent.changedTouches[i].pageX - origEvent.changedTouches[i].target.offsetLeft : e.offsetX,
								'y': (settings.touch_capable) ? origEvent.changedTouches[i].pageY - origEvent.changedTouches[i].target.offsetTop : e.offsetY
							},
							'time': Date.now(),
							'target': e.target
						};

						touchData.push(touch);
					}

					switch (touches.length) {
						case 1:
							eventName = 'tap';
							break;

						case 2:
							eventName = 'tap2';
							break;

						case 3:
							eventName = 'tap3';
							break;

						case 4:
							eventName = 'tap4';
							break;
					}

					triggerCustomEvent(thisObject, eventName, e, touchData);
				}
			});
		},

		remove: function() {
			$(this).off(settings.startevent, $(this).data.callee1).off(settings.endevent, $(this).data.callee2);
		}
	};

	// swipe Event (also handles swipeup, swiperight, swipedown and swipeleft):
	$.event.special.swipe = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject),
				started = false,
				hasSwiped = false,
				originalCoord = {
					x: 0,
					y: 0
				},
				finalCoord = {
					x: 0,
					y: 0
				},
				startEvnt;

			// Screen touched, store the original coordinate

			function touchStart(e) {
				$this = $(e.currentTarget);
				$this.data('callee1', arguments.callee);
				originalCoord.x = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageX : e.pageX;
				originalCoord.y = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageY : e.pageY;
				finalCoord.x = originalCoord.x;
				finalCoord.y = originalCoord.y;
				started = true;
				var origEvent = e.originalEvent;
				// Read event data into our startEvt:
				startEvnt = {
					'position': {
						'x': (settings.touch_capable) ? origEvent.touches[0].screenX : e.screenX,
						'y': (settings.touch_capable) ? origEvent.touches[0].screenY : e.screenY
					},
					'offset': {
						'x': (settings.touch_capable) ? origEvent.touches[0].pageX - origEvent.touches[0].target.offsetLeft : e.offsetX,
						'y': (settings.touch_capable) ? origEvent.touches[0].pageY - origEvent.touches[0].target.offsetTop : e.offsetY
					},
					'time': Date.now(),
					'target': e.target
				};
			}

			// Store coordinates as finger is swiping

			function touchMove(e) {
				$this = $(e.currentTarget);
				$this.data('callee2', arguments.callee);
				finalCoord.x = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageX : e.pageX;
				finalCoord.y = (e.originalEvent.targetTouches) ? e.originalEvent.targetTouches[0].pageY : e.pageY;

				var swipedir;

				// We need to check if the element to which the event was bound contains a data-xthreshold | data-vthreshold:
				var ele_x_threshold = ($this.parent().data('xthreshold')) ? $this.parent().data('xthreshold') : $this.data('xthreshold'),
					ele_y_threshold = ($this.parent().data('ythreshold')) ? $this.parent().data('ythreshold') : $this.data('ythreshold'),
					h_threshold = (typeof ele_x_threshold !== 'undefined' && ele_x_threshold !== false && parseInt(ele_x_threshold)) ? parseInt(ele_x_threshold) : settings.swipe_h_threshold,
					v_threshold = (typeof ele_y_threshold !== 'undefined' && ele_y_threshold !== false && parseInt(ele_y_threshold)) ? parseInt(ele_y_threshold) : settings.swipe_v_threshold;

				if (originalCoord.y > finalCoord.y && (originalCoord.y - finalCoord.y > v_threshold)) {
					swipedir = 'swipeup';
				}
				if (originalCoord.x < finalCoord.x && (finalCoord.x - originalCoord.x > h_threshold)) {
					swipedir = 'swiperight';
				}
				if (originalCoord.y < finalCoord.y && (finalCoord.y - originalCoord.y > v_threshold)) {
					swipedir = 'swipedown';
				}
				if (originalCoord.x > finalCoord.x && (originalCoord.x - finalCoord.x > h_threshold)) {
					swipedir = 'swipeleft';
				}
				if (swipedir != undefined && started) {
					originalCoord.x = 0;
					originalCoord.y = 0;
					finalCoord.x = 0;
					finalCoord.y = 0;
					started = false;

					// Read event data into our endEvnt:
					var origEvent = e.originalEvent;
					endEvnt = {
						'position': {
							'x': (settings.touch_capable) ? origEvent.touches[0].screenX : e.screenX,
							'y': (settings.touch_capable) ? origEvent.touches[0].screenY : e.screenY
						},
						'offset': {
							'x': (settings.touch_capable) ? origEvent.touches[0].pageX - origEvent.touches[0].target.offsetLeft : e.offsetX,
							'y': (settings.touch_capable) ? origEvent.touches[0].pageY - origEvent.touches[0].target.offsetTop : e.offsetY
						},
						'time': Date.now(),
						'target': e.target
					};

					// Calculate the swipe amount (normalized):
					var xAmount = Math.abs(startEvnt.position.x - endEvnt.position.x),
						yAmount = Math.abs(startEvnt.position.y - endEvnt.position.y);

					var touchData = {
						'startEvnt': startEvnt,
						'endEvnt': endEvnt,
						'direction': swipedir.replace('swipe', ''),
						'xAmount': xAmount,
						'yAmount': yAmount,
						'duration': endEvnt.time - startEvnt.time
					}
					hasSwiped = true;
					$this.trigger('swipe', touchData).trigger(swipedir, touchData);
				}
			}

			function touchEnd(e) {
				$this = $(e.currentTarget);
				var swipedir = "";
				$this.data('callee3', arguments.callee);
				if (hasSwiped) {
					// We need to check if the element to which the event was bound contains a data-xthreshold | data-vthreshold:
					var ele_x_threshold = $this.data('xthreshold'),
						ele_y_threshold = $this.data('ythreshold'),
						h_threshold = (typeof ele_x_threshold !== 'undefined' && ele_x_threshold !== false && parseInt(ele_x_threshold)) ? parseInt(ele_x_threshold) : settings.swipe_h_threshold,
						v_threshold = (typeof ele_y_threshold !== 'undefined' && ele_y_threshold !== false && parseInt(ele_y_threshold)) ? parseInt(ele_y_threshold) : settings.swipe_v_threshold;

					var origEvent = e.originalEvent;
					endEvnt = {
						'position': {
							'x': (settings.touch_capable) ? origEvent.changedTouches[0].screenX : e.screenX,
							'y': (settings.touch_capable) ? origEvent.changedTouches[0].screenY : e.screenY
						},
						'offset': {
							'x': (settings.touch_capable) ? origEvent.changedTouches[0].pageX - origEvent.changedTouches[0].target.offsetLeft : e.offsetX,
							'y': (settings.touch_capable) ? origEvent.changedTouches[0].pageY - origEvent.changedTouches[0].target.offsetTop : e.offsetY
						},
						'time': Date.now(),
						'target': e.target
					};

					// Read event data into our endEvnt:
					if (startEvnt.position.y > endEvnt.position.y && (startEvnt.position.y - endEvnt.position.y > v_threshold)) {
						swipedir = 'swipeup';
					}
					if (startEvnt.position.x < endEvnt.position.x && (endEvnt.position.x - startEvnt.position.x > h_threshold)) {
						swipedir = 'swiperight';
					}
					if (startEvnt.position.y < endEvnt.position.y && (endEvnt.position.y - startEvnt.position.y > v_threshold)) {
						swipedir = 'swipedown';
					}
					if (startEvnt.position.x > endEvnt.position.x && (startEvnt.position.x - endEvnt.position.x > h_threshold)) {
						swipedir = 'swipeleft';
					}

					// Calculate the swipe amount (normalized):
					var xAmount = Math.abs(startEvnt.position.x - endEvnt.position.x),
						yAmount = Math.abs(startEvnt.position.y - endEvnt.position.y);

					var touchData = {
						'startEvnt': startEvnt,
						'endEvnt': endEvnt,
						'direction': swipedir.replace('swipe', ''),
						'xAmount': xAmount,
						'yAmount': yAmount,
						'duration': endEvnt.time - startEvnt.time
					}
					$this.trigger('swipeend', touchData);
				}

				started = false;
				hasSwiped = false;
			}

			$this.on(settings.startevent, touchStart);
			$this.on(settings.moveevent, touchMove);
			$this.on(settings.endevent, touchEnd);
		},

		remove: function() {
			$(this).off(settings.startevent, $(this).data.callee1).off(settings.moveevent, $(this).data.callee2).off(settings.endevent, $(this).data.callee3);
		}
	};

	// scrollstart Event (also handles scrollend):
	$.event.special.scrollstart = {
		setup: function() {
			var thisObject = this,
				$this = $(thisObject),
				scrolling,
				timer;

			function trigger(event, state) {
				scrolling = state;
				triggerCustomEvent(thisObject, scrolling ? 'scrollstart' : 'scrollend', event);
			}

			// iPhone triggers scroll after a small delay; use touchmove instead
			$this.on(settings.scrollevent, function(event) {
				$this.data('callee', arguments.callee);

				if (!scrolling) {
					trigger(event, true);
				}

				clearTimeout(timer);
				timer = setTimeout(function() {
					trigger(event, false);
				}, 50);
			});
		},

		remove: function() {
			$(this).off(settings.scrollevent, $(this).data.callee);
		}
	};

	// This is the orientation change (largely borrowed from jQuery Mobile):
	var win = $(window),
		special_event,
		get_orientation,
		last_orientation,
		initial_orientation_is_landscape,
		initial_orientation_is_default,
		portrait_map = {
			'0': true,
			'180': true
		};

	if (settings.orientation_support) {
		var ww = window.innerWidth || win.width(),
			wh = window.innerHeight || win.height(),
			landscape_threshold = 50;

		initial_orientation_is_landscape = ww > wh && (ww - wh) > landscape_threshold;
		initial_orientation_is_default = portrait_map[window.orientation];

		if ((initial_orientation_is_landscape && initial_orientation_is_default) || (!initial_orientation_is_landscape && !initial_orientation_is_default)) {
			portrait_map = {
				'-90': true,
				'90': true
			};
		}
	}

	$.event.special.orientationchange = special_event = {
		setup: function() {
			// If the event is supported natively, return false so that jQuery
			// will on to the event using DOM methods.
			if (settings.orientation_support) {
				return false;
			}

			// Get the current orientation to avoid initial double-triggering.
			last_orientation = get_orientation();

			win.on('throttledresize', handler);
			return true;
		},
		teardown: function() {
			if (settings.orientation_support) {
				return false;
			}

			win.off('throttledresize', handler);
			return true;
		},
		add: function(handleObj) {
			// Save a reference to the bound event handler.
			var old_handler = handleObj.handler;

			handleObj.handler = function(event) {
				event.orientation = get_orientation();
				return old_handler.apply(this, arguments);
			};
		}
	};

	// If the event is not supported natively, this handler will be bound to
	// the window resize event to simulate the orientationchange event.

	function handler() {
		// Get the current orientation.
		var orientation = get_orientation();

		if (orientation !== last_orientation) {
			// The orientation has changed, so trigger the orientationchange event.
			last_orientation = orientation;
			win.trigger("orientationchange");
		}
	}

	$.event.special.orientationchange.orientation = get_orientation = function() {
		var isPortrait = true,
			elem = document.documentElement;

		if (settings.orientation_support) {
			isPortrait = portrait_map[window.orientation];
		} else {
			isPortrait = elem && elem.clientWidth / elem.clientHeight < 1.1;
		}

		return isPortrait ? 'portrait' : 'landscape';
	};

	// throttle Handler:
	$.event.special.throttledresize = {
		setup: function() {
			$(this).on('resize', throttle_handler);
		},
		teardown: function() {
			$(this).off('resize', throttle_handler);
		}
	};

	var throttle = 250,
		throttle_handler = function() {
			curr = Date.now();
			diff = curr - lastCall;

			if (diff >= throttle) {
				lastCall = curr;
				$(this).trigger('throttledresize');

			} else {
				if (heldCall) {
					window.clearTimeout(heldCall);
				}

				// Promise a held call will still execute
				heldCall = window.setTimeout(handler, throttle - diff);
			}
		},
		lastCall = 0,
		heldCall,
		curr,
		diff;

	// Trigger a custom event:

	function triggerCustomEvent(obj, eventType, event, touchData) {
		var originalType = event.type;
		event.type = eventType;

		$.event.dispatch.call(obj, event, touchData);
		event.type = originalType;
	}

	// Correctly on anything we've overloaded:
	$.each({
		scrollend: 'scrollstart',
		swipeup: 'swipe',
		swiperight: 'swipe',
		swipedown: 'swipe',
		swipeleft: 'swipe',
		swipeend: 'swipe',
		tap2: 'tap'
	}, function(e, srcE, touchData) {
		$.event.special[e] = {
			setup: function() {
				$(this).on(srcE, $.noop);
			}
		};
	});

})(jQuery);/**
 * $.yxMobileSlider
 * @charset utf-8
 * @extends jquery.1.9.1
 * @fileOverview 创建一个焦点轮播插件，兼容PC端和移动端，若引用请保留出处，谢谢！
 * @author 李玉玺
 * @version 1.0
 * @date 2013-11-12
 * @example
 * $(".container").yxMobileSlider();
 */
(function($) {
	$.fn.yxMobileSlider = function(settings) {
		var defaultSettings = {
			width: '100%', //容器宽度
			height: '239px', //容器高度
			during: 3000, //间隔时间
			speed: 20 //滑动速度
		};
		settings = $.extend(true, {}, defaultSettings, settings);
		return this.each(function() {
			var _this = $(this),
				s = settings;
			var startX = 0,
				startY = 0; //触摸开始时手势横纵坐标
			var temPos; //滚动元素当前位置
			var iCurr = 0; //当前滚动屏幕数
			var timer = null; //计时器
			var oMover = $("ul", _this); //滚动元素
			var oLi = $("li", oMover); //滚动单元
			var num = oLi.length; //滚动屏幕数
			var oPosition = {}; //触点位置
			var moveWidth = s.width; //滚动宽度
			//初始化主体样式
			_this.width(s.width).height(s.height).css({
				position: 'relative',
				overflow: 'hidden',
				margin: '0 auto'
			});
			//设定容器宽高及样式
			oMover.css({
				position: 'absolute',
				left: 0
			});
			oLi.css({
				float: 'left',
				display: 'inline'
			});
			$("img", oLi).css({
				width: document.body.clientWidth+'px',
				height: (document.body.clientWidth*0.56)+'px'
			});
			//初始化焦点容器及按钮
			console.log('slider start');
			_this.append('<div class="focus"><div></div></div>');
			var oFocusContainer = $(".focus");
			for (var i = 0; i < num; i++) {
				$("div", oFocusContainer).append("<span></span>");
			}
			var oFocus = $("span", oFocusContainer);
			oFocusContainer.css({
				minHeight: _this.find('span').height() * 2,
				position: 'absolute',
				bottom: 0
					// background: 'rgba(0,0,0,0.5)'
			})
			$("span", oFocusContainer).css({
				display: 'block',
				float: 'left',
				cursor: 'pointer'
			})
			$("div", oFocusContainer).width(oFocus.outerWidth(true) * num).css({
				position: 'absolute',
				right: _this.width() / 2 - num * 10 - 10,
				top: '50%',
				marginTop: -$(this).find('span').width() / 2
			});
			oFocus.first().addClass("current");
			//页面加载或发生改变
			$(window).on('resize load', function() {
				if (isMobile()) {
					mobileSettings();
					bindTochuEvent();
				}
				oLi.width(_this.width()).height(_this.height()); //设定滚动单元宽高
				oMover.width(num * oLi.width());
				oFocusContainer.width(_this.width()).height(_this.height() * 0.15).css({
					zIndex: 2
				}); //设定焦点容器宽高样式
				_this.fadeIn(300);
			});
			//页面加载完毕BANNER自动滚动
			autoMove();
			//PC机下焦点切换
			if (!isMobile()) {
				oFocus.hover(function() {
					iCurr = $(this).index() - 1;
					stopMove();
					doMove();
				}, function() {
					autoMove();
				})
			}
			//自动运动
			function autoMove() {
				timer = setInterval(doMove, s.during);
			}
			//停止自动运动
			function stopMove() {
				clearInterval(timer);
			}
			//运动效果
			function doMove() {
				iCurr = iCurr >= num - 1 ? 0 : iCurr + 1;
				var a=oLi.width()*iCurr;
//				if (moveWidth == "100%") {
//					a = document.body.clientWidth * iCurr;
//				} else {
//					a = iCurr * moveWidth;
//				}
				doAnimate(-a);
				oFocus.eq(iCurr).addClass("current").siblings().removeClass("current");
			}
			//绑定触摸事件
			function bindTochuEvent() {
				oMover.get(0).addEventListener('touchstart', touchStartFunc, false);
				oMover.get(0).addEventListener('touchmove', touchMoveFunc, false);
				oMover.get(0).addEventListener('touchend', touchEndFunc, false);
			}
			//获取触点位置
			function touchPos(e) {
				var touches = e.changedTouches,
					l = touches.length,
					touch, tagX, tagY;
				for (var i = 0; i < l; i++) {
					touch = touches[i];
					tagX = touch.clientX;
					tagY = touch.clientY;
				}
				oPosition.x = tagX;
				oPosition.y = tagY;
				return oPosition;
			}
			//触摸开始
			function touchStartFunc(e) {
				stopMove();
				// clearInterval(timer);
				touchPos(e);
				startX = oPosition.x;
				startY = oPosition.y;
				temPos = oMover.position().left;
			}
			//触摸移动
			function touchMoveFunc(e) {
				touchPos(e);
				var moveX = oPosition.x - startX;
				var moveY = oPosition.y - startY;
				if (Math.abs(moveY) < Math.abs(moveX)) {
					e.preventDefault();
					oMover.css({
						left: temPos + moveX
					});
				}
			}
			//触摸结束
			function touchEndFunc(e) {
				touchPos(e);
				var moveX = oPosition.x - startX;
				var moveY = oPosition.y - startY;
				if (Math.abs(moveY) < Math.abs(moveX)) {
					if (moveX > 0) {
						iCurr--;
						if (iCurr >= 0) {
							var moveX = iCurr * moveWidth;
							doAnimate(-moveX, autoMove);
						} else {
							doAnimate(0, autoMove);
							iCurr = 0;
						}
					} else {
						iCurr++;
						if (iCurr < num && iCurr >= 0) {
							var moveX = iCurr * moveWidth;
							doAnimate(-moveX, autoMove);
						} else {
							iCurr = num - 1;
							doAnimate(-(num - 1) * moveWidth, autoMove);
						}
					}
					oFocus.eq(iCurr).addClass("current").siblings().removeClass("current");
				}
			}
			//移动设备基于屏幕宽度设置容器宽高
			function mobileSettings() {
				moveWidth = $(window).width();
				var iScale = $(window).width() / s.width;
				_this.height(s.height * iScale).width($(window).width());
				oMover.css({
					left: -iCurr * moveWidth
				});
			}
			//动画效果
			function doAnimate(iTarget, fn) {
				oMover.stop().animate({
					left: iTarget
				}, _this.speed, function() {
					if (fn)
						fn();
				});
			}
			//判断是否是移动设备
			function isMobile() {
				if (navigator.userAgent.match(/Android/i) || navigator.userAgent.indexOf('iPhone') != -1 || navigator.userAgent.indexOf('iPod') != -1 || navigator.userAgent.indexOf('iPad') != -1) {
					return true;
				} else {
					return false;
				}
			}
		});
	}
})(jQuery);/*! layer mobile-v1.7 弹层组件移动版 License LGPL http://layer.layui.com/mobile By 贤心  getAttribute("merge")||document.head*/
;!function(a){"use strict";var b=document,c="querySelectorAll",d="getElementsByClassName",e=function(a){return b[c](a)},f={type:0,shade:!0,shadeClose:!0,fixed:!0,anim:!0},g={extend:function(a){var b=JSON.parse(JSON.stringify(f));for(var c in a)b[c]=a[c];return b},timer:{},end:{}};g.touch=function(a,b){var c;return/Android|iPhone|SymbianOS|Windows Phone|iPad|iPod/.test(navigator.userAgent)?(a.addEventListener("touchmove",function(){c=!0},!1),void a.addEventListener("touchend",function(a){a.preventDefault(),c||b.call(this,a),c=!1},!1)):a.addEventListener("click",function(a){b.call(this,a)},!1)};var h=0,i=["layermbox"],j=function(a){var b=this;b.config=g.extend(a),b.view()};j.prototype.view=function(){var a=this,c=a.config,f=b.createElement("div");a.id=f.id=i[0]+h,f.setAttribute("class",i[0]+" "+i[0]+(c.type||0)),f.setAttribute("index",h);var g=function(){var a="object"==typeof c.title;return c.title?'<h3 style="'+(a?c.title[1]:"")+'">'+(a?c.title[0]:c.title)+'</h3><button class="layermend"></button>':""}(),j=function(){var a,b=(c.btn||[]).length;return 0!==b&&c.btn?(a='<span type="1">'+c.btn[0]+"</span>",2===b&&(a='<span type="0">'+c.btn[1]+"</span>"+a),'<div class="layermbtn">'+a+"</div>"):""}();if(c.fixed||(c.top=c.hasOwnProperty("top")?c.top:100,c.style=c.style||"",c.style+=" top:"+(b.body.scrollTop+c.top)+"px"),2===c.type&&(c.content='<i></i><i class="laymloadtwo"></i><i></i>'),f.innerHTML=(c.shade?"<div "+("string"==typeof c.shade?'style="'+c.shade+'"':"")+' class="laymshade"></div>':"")+'<div class="layermmain" '+(c.fixed?"":'style="position:static;"')+'><div class="section"><div class="layermchild '+(c.className?c.className:"")+" "+(c.type||c.shade?"":"layermborder ")+(c.anim?"layermanim":"")+'" '+(c.style?'style="'+c.style+'"':"")+">"+g+'<div class="layermcont">'+c.content+"</div>"+j+"</div></div></div>",!c.type||2===c.type){var k=b[d](i[0]+c.type),l=k.length;l>=1&&layer.close(k[0].getAttribute("index"))}document.body.appendChild(f);var m=a.elem=e("#"+a.id)[0];c.success&&c.success(m),a.index=h++,a.action(c,m)},j.prototype.action=function(a,b){var c=this;if(a.time&&(g.timer[c.index]=setTimeout(function(){layer.close(c.index)},1e3*a.time)),a.title){var e=b[d]("layermend")[0],f=function(){a.cancel&&a.cancel(),layer.close(c.index)};g.touch(e,f)}var h=function(){var b=this.getAttribute("type");0==b?(a.no&&a.no(),layer.close(c.index)):a.yes?a.yes(c.index):layer.close(c.index)};if(a.btn)for(var i=b[d]("layermbtn")[0].children,j=i.length,k=0;j>k;k++)g.touch(i[k],h);if(a.shade&&a.shadeClose){var l=b[d]("laymshade")[0];g.touch(l,function(){layer.close(c.index,a.end)})}a.end&&(g.end[c.index]=a.end)},a.layer={v:"1.7",index:h,open:function(a){var b=new j(a||{});return b.index},close:function(a){var c=e("#"+i[0]+a)[0];c&&(c.innerHTML="",b.body.removeChild(c),clearTimeout(g.timer[a]),delete g.timer[a],"function"==typeof g.end[a]&&g.end[a](),delete g.end[a])},closeAll:function(){for(var a=b[d](i[0]),c=0,e=a.length;e>c;c++)layer.close(0|a[0].getAttribute("index"))}},"function"==typeof define?define(function(){return layer}):function(){var a=document.scripts,c=a[a.length-1],d=c.src,e=d.substring(0,d.lastIndexOf("/")+1);c.getAttribute("merge")||document.head.appendChild(function(){var a=b.createElement("link");return a.href=e+"need/layer.css",a.type="text/css",a.rel="styleSheet",a.id="layermcss",a}())}()}(window);/**
 * uri解析
 * @authors pingyf
 * @date    2015-10-30 10:48:32
 * @version $Id$
 */

(function(window, undefined){
    var URI = {};
    URI.query = function(search){
        var s = search || location.search,
            reg = /([?&])?([^=]+?)(?=(=|&|$))(([^&$]*))?/g,
            r = {},
            match = null,
            total = 0;
        var _remove = function(key) {
            // r[key] = undefined;
            delete r[key];
            total--;
        };
        while(match = reg.exec(s)){
            var val = decodeURIComponent(match[4]).replace(/^=/, "");
            if (match[2].indexOf('[]') !== -1) {
                var k = match[2].replace('[]', '');
                if (typeof r[k] === 'undefined') {
                    r[k] = [val];
                    total++;
                } else {
                    r[k].push(val);
                }
            } else {
                r[match[2]] = val;
                total++;
            }
        }
        return {
            get: function(key) {
                return r[key];
            },
            keys: function() {
                var keys = [];
                if ('keys' in Object) {
                    keys = Object.keys(r);
                } else {
                    for (var key in r) {
                        keys.push(key);
                    }
                }
                return keys;
            },
            remove: _remove,
            count: function() {
                return total;
            }
        };
    };
    window.Uri = window.Uri || URI;
})(window);/**
 * @comment : 请求参数提供类
 * @author   : liuhualuo@163.com
 * @create   : 2012-7-18
 */
(function() {

	var URLParam = function(){
		this.name = 'URLParam';
		this.version = '2.0.1';
	};
	this.URLParam = new URLParam();
	URLParam = this.URLParam;

	//包含所有的参数的列表。
	var paramList = {};

	/**
	 * @Comments : 将参数添加到全局的paramList变量中。
	 * @param    :  prmObj 为参数名, 为ParamInf的一个实现类
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-8-10
	 */
	var addParamList = function(prmObj){
		var oName = prmObj.getName();
		// alert(oName);
		var objParams = prmObj.paramList;
		var defaultParam = objParams["defaultParam"] || {};

		//处理获取参数规则
		var specFunc = function(paramName){
			return objParams[paramName];
		};
		if( !$.isEmptyObject(prmObj) && $.isFunction(prmObj.getSpecificParam) ){
			specFunc = prmObj.getSpecificParam;
		}

		//遍历所有params 将param放入到paramList全局变量中。
		$.each(objParams, function(key, value){
			var extendParam = {};
			var specParam = specFunc(key);
			if( specParam["extend"] ){
				extendParam = specFunc(specParam["extend"]);
			}

			specParam = $.extend(true, {}, defaultParam, extendParam, specParam);
			paramList[oName + "." + key] = specParam;

		});
	};

	URLParam.addParamObj = function(prmObj){
		addParamList(prmObj);
	}

	/**
	 * @Comments : 获得特定的请求参数。
	 * @param    :  paramName 为参数名, paramType为ParamType对象中属性。
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-23
	 */
	URLParam.getUrlParam = function(paramName){
		/*如果Param类已经提供了 getSpecificParam 方法则直接调用该方法。
		if( !$.isEmptyObject(paramType) && $.isFunction(paramType.getSpecificParam) ){
			return paramType.getSpecificParam(paramName);
		}
		var specParam = $.extend(true, {}, paramType["defaultParam"], paramType[paramName]);
		return specParam;
		*/
		var specParam = $.extend(true, {}, paramList[paramName]);
		return specParam;
	}

    URLParam.getParamList = function(){
        return paramList;
    }

})();
;/**
 * @comment : 请求提供类
 * @author   : liuhualuo@163.com
 * @create   : 2012-7-18
 */
(function() {

	var Protocol = function(){
		this.name = 'Protocol';
		this.version = '2.0.1';
	};
	this.Protocol = new Protocol();
	Protocol = this.Protocol;

	window.logger = (typeof logger != "object") ?
					{log:function(){}, error:function(){}} : logger;

	//请求缓存
	Protocol.Cache = (function() {
		return {
			SetUp : false,

			putCache : function(opts, data) {

			},
			getCache : function(urlParam, key) {
				return null;
			}
		}
	})();

	var requestCache = Protocol.Cache.SetUp;		//默认全局缓存的开启。

	/*缓存请求类型,
	 格式	ajax : {
    		type : "ajax",
    		getOptions : getAjaxOptions,
    		validateOptions : validateOptions,
    		request : ajaxRequest,
    		registerEvent : ajaxRegisterEvent
    	}
    */
	var typeRequest = {

	};

	Protocol.registerRequest = function(type, request){
		typeRequest[type] = request;
	},

	/**
	 * @Comments : 获得特定的RequestWay参数。
	 * 参数处理顺序->  getAjaxOptions -> putOptions -> ajaxValidateOptions
	 * @param    : urlParam 为在URLParam中返回的urlParam。
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-18
	 */
	Protocol.getSpecificUrlParam = function(urlParam){
		var opts = {};
		urlParam = $.extend(true, {}, Protocol.defaultParam, urlParam);
		var rqType = urlParam.requestType;
		// var Type = Protocol.RequestType;
		var getOpts = typeRequest[rqType]["getOptions"];
		if( typeof getOpts == "function" ){
			opts = getOpts(urlParam);
		}else{
			opts = getDefaultOptions(urlParam);
		}
		//放入所有请求必须要有的参数。
		opts = putOptions(opts, urlParam);

		//把urlParam放入到请求项中。
		opts.urlParam = urlParam;

		var validateOpts = typeRequest[rqType]["validateOptions"];
		if( typeof validateOpts == "function" ){
			validateOpts(opts);
		}else{
			defaultValidateOptions(opts);
		}
		return opts;
	},

    /**
	 * @Comments : request请求数据接口。
	 * @param    : urlParam ==> {
	 *      parameter    : 传递的参数
	 *      success      : 请求成功的回调函数
	 *      complete     : 请求完成后的回调函数
	 *      failure      : 请求失败的回调函数
	 *		url			 : 请求url
	 *      ........
	 * }
	 * 		params 中的参数将会覆盖urlParam中参数。
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-19
	 */
    Protocol.request = function(urlParam, params) {
    	urlParam = getUrlParam(urlParam, params);
	//	alert('merid:'+urlParam.parameter.merchantId||'')
		var options = {};
		options = Protocol.getSpecificUrlParam(urlParam);

		//如果不是强制刷新，则先从缓存中取。
		if( !urlParam.forceRefresh){
			//缓存中存在，则直接获取缓存中的数据。
			var store = Protocol.Cache.getCache(options, "result");
			if( !$.isEmptyObject(store) ){
				if ( $.isFunction(options.success) ) {
					options.success.call(options.scope, options, store, "cache");
				}
				if ( $.isFunction(options.complete) ) {
					options.complete.call(options.scope, options, "cache");
				}
				return store;
			}
		}

		//成功之后首先执行的方法。
		options.afterSuccess = afterSuccess;

        var rqType = options.requestType;

		var sendRequest = typeRequest[rqType]["request"];
		//执行请求
		return sendRequest(options);
    },

     /**
	 * @Comments : 事件注册
	 * @param    : options ==> {
	 *      parameter    : 传递的参数
	 *      success      : 请求成功的回调函数
	 *      complete     : 请求完成后的回调函数
	 *      failure      : 请求失败的回调函数
	 *		url			 : 请求url
	 *      ........
	 * }
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-10-8
	 */
    Protocol.registerEvent = function(urlParam, params){
		urlParam = getUrlParam(urlParam, params);
		var options = {};
		options = Protocol.getSpecificUrlParam(urlParam);

    	var rqType = options.requestType;

		var registerEvent = typeRequest[rqType]["registerEvent"];
		registerEvent(options);
    }

    /**
	 * @Comments : 默认请求校验方法。
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-18
	 */
	var defaultValidateOptions = function(options){

	};

	/**
	 * @Comments : 默认请求参数转换方法。
	 * @author   : liuhualuo@163.com
	 */
	var getDefaultOptions = function(options){
		return options;
	};

    //urlParam 转换.
    var getUrlParam = function(urlParam, params){
    	//如果urlParam为字符串，则将先转化成UrlParam对象。
    	if( typeof urlParam === "string"){
    		urlParam = URLParam.getUrlParam(urlParam);
    	}
		if( $.isEmptyObject(urlParam) ){
			return {};
		}
		if(params){
			urlParam = $.extend(true, {}, urlParam, params);
		}
		return urlParam;
    }

    /**
	 * @Comments : options选项必定有的参数。
		options ==> {
	 *      model		 : 模型对象。
	 *      storeTime    : 启用缓存有效时间。
	 *      success      : 加载成功的回调函数
	 *      forceRefresh : 是否强制刷新对象。
	 *      parameter    : 请求参数
	 * 		external	 : 客户端对象
	 *		url			 : 请求url
	 * @author   : liuhualuo@163.com
	 */
	var putOptions = function(opts, urlParam){
		opts.requestType = urlParam.requestType;
		opts.external = urlParam.external;
		opts.url = (typeof urlParam.url == "function") ? urlParam.url() : urlParam.url;
		opts.parameter = urlParam.parameter;
		opts.success = urlParam.success;
		opts.model = urlParam.model;
		opts.forceRefresh = urlParam.forceRefresh;
		opts.storeTime = urlParam.storeTime;
		return opts;
	}

	/**
	 * @Comments : 请求成功后第一个执行的方法。
	 * @param    : options ajax请求中的参数。
				   data为请求成功后返回的数据。
				   textStatus="success", "notmodified", "error", "timeout", "abort", or "parsererror"。
				   jqXHR=XMLHTTPRequest。
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-18
	 */
	var afterSuccess = function(options, result, textStatus, jqXHR){
		//将返回数据封装成模型对象。
		if ( !$.isEmptyObject(options.model) ) {
			var modelArr = options.model.batData(result.data);
			result.data = {};
			result.modelValue = modelArr;
		}
		options.result = result;
		//开启缓存
		if( requestCache ){
			var store = Protocol.Cache.putCache(options, result);
		}

		return options;
	}

})();

// create by John Resig(Jquery 创始人)
// Inspired by base2 and Prototype
(function(){
  var initializing = false, fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

  // The base Class implementation (does nothing)
  this.Class = function(){};

  // Create a new Class that inherits from this class
  Class.extend = function(prop) {
    var _super = this.prototype;

    // Instantiate a base class (but only create the instance,
    // don't run the init constructor)
    initializing = true;
    var prototype = new this();
    initializing = false;

    // Copy the properties over onto the new prototype
    for (var name in prop) {
      // Check if we're overwriting an existing function
      prototype[name] = typeof prop[name] == "function" &&
        typeof _super[name] == "function" && fnTest.test(prop[name]) ?
        (function(name, fn){
          return function() {
            var tmp = this._super;

            // Add a new ._super() method that is the same method
            // but on the super-class
            this._super = _super[name];

            // The method only need to be bound temporarily, so we
            // remove it when we're done executing
            var ret = fn.apply(this, arguments);
            this._super = tmp;

            return ret;
          };
        })(name, prop[name]) :
        prop[name];
    }

    // The dummy class constructor
    function Class() {
      // All construction is actually done in the init method
      if ( !initializing && this.init )
        this.init.apply(this, arguments);
    }

    // Populate our constructed prototype object
    Class.prototype = prototype;

    // Enforce the constructor to be what we expect
    Class.constructor = Class;

    // And make this class extendable
    Class.extend = arguments.callee;

    return Class;
  };
})();;/**
 * @comment : uri配置规范实现类
 * @author   : liuhualuo@163.com
 * @create   : 2012-7-18
 */
(function($) {

	var URI = function(){
		this.name = 'URI';
		this.version = '2.0.1';
	};
	this.URI = new URI();
	URI = this.URI;

	var homeURI = '#/home';
	var templateSuffix = "tpl";
	var uriList = {};

	//this.URI = (function(){

	//	return {
	/**
	 * uriList是否包含uri路径
	 * @params {Sting} "#/im/home"
	 */
	URI.hasURI = function(str) {
		if(typeof str != 'string' ||  str.length == 0){
			return false;
		}
		var uri = '';
		try{
			uri = str.substr(2).replace('/', '_') + '_uri'; // #/im/home -> im_home_uri
		}catch(e){}
		if( typeof uriList[uri] == "object" ){
			return true;
		}
		return false;
	}

	URI.setHomeURI = function(uri) {
		if(this.hasURI(uri)){
			uriList.context.homeURI = uri;
		}
	}

	URI.getHomeURI = function(){
		var context = uriList.context || {};
		return context.homeURI || homeURI;
	}

	URI.getTemplateSuffix = function(){
		var context = uriList.context || {};
		return context.templateSuffix || templateSuffix;
	}

	//获取BeforeController
	URI.getBeforeController = function(){
		var context = uriList.context || {};
		return context.before_controller;
	}

	//获取afterController
	URI.getAfterController = function(){
		var context = uriList.context || {};
		return context.after_controller;
	}
	//获取url的参数
	URI.getQueryString = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null){
			return unescape(r[2]);
		}
		return null;
	}

	URI.isRefresh = function(refreshFlag){
		var context = uriList.context || {};
		if( context.refresh && context.refreshFlag != refreshFlag ){
			return true;//刷新
		}
		return false;//不刷新
	}

	/**
	 * @Comments : 获得特定的请求参数。
	 * @param    :  paramName 为参数名, paramType为ParamType对象中属性。
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-23
	 */
	URI.getURIS = function(){
		return uriList;
	}

	URI.getURI = function(uri){
		if(uri && uri != ""){
			return uriList[uri];
		}else{
			return URI.getLocationURI();
		}
	}
	/**
	 * @Comments : 通过地址栏uri，获取对应的item。
	 * @author   : liuhualuo@163.com
	 * @create   : 2015-7-14
	 */
	URI.getLocationURI = function(){
		//url中#后面的一段参数
		var hash = window.location.hash;
		if(hash && hash != ""){
			return uriList[hash];
		}
		//window.location.pathname 为url 不带?的url
		return uriList[window.location.pathname];
	}

	URI.addURI = function(uris){
		$.each(uris, function(key, value){
			parentValue = {};
			//如果有继承父类，获取父类继承。
			if( !$.isEmptyObject(value.extend) ){
				parentValue = uris[value.extend];
			}
			if(key == "context"){
				uriList[key] = $.extend(true, parentValue, value);
			}else{
				uriList[value.uri] = $.extend(true, parentValue, value);
			}
		});
	}
	//	}
	//})();
})(jQuery);;/**
 * @comment : 基础控制器类
 * @author   : liuhualuo@163.com
 * @create   : 2012-7-18
 */
(function($) {

    window.logger = (typeof logger != "object") ?
					{log:function(){}, error:function(){}} : logger;

	var EasyController = Class.extend({
        params : null,
		append : null,
		selector : null,
		template : null,
		_data : null,
		requestCount : 0,

		init : function(config) {
	        $.extend(this, config);
	        //this.addEventListener();
	    },
		request : function(){

		},

		addEventListener : function(){

		},

		sendRequest : function(urlParam, params){
			//如果urlParam为字符串，则将先转化成UrlParam对象。
			if($.type(urlParam) === "string"){
				urlParam = URLParam.getUrlParam(urlParam);
			}
			if( $.isEmptyObject(urlParam) ){
				//请求未发出
				return {};
			}
			if(params){
				urlParam = $.extend(true, {}, urlParam, params);
			}
			var that = this;
			var succFuc = urlParam.success;
			if( $.isFunction(urlParam.complete) ){
				that.requestCount++;
				urlParam.complete = function(avg1, avg2, avg3, avg4, avg5){
					urlParam.scope.complete(avg1, avg2, avg3, avg4, avg5);//调用的是对象的complete方法
					that.requestCount--;
					if( that.requestCount === 0 ){//只有请求都完成了再调用render渲染模板
						that.render();
					}
				}
			}else{
				if( $.isFunction(urlParam.success) ){
					that.requestCount++;
					urlParam.success = function(options, result, textStatus, avg4, avg5){
						succFuc.call(options.scope, options, result, textStatus, avg4, avg5);
						that.requestCount--;
						if( that.requestCount === 0 ){
							that.render();
						}
					}
				}
			}
			return Protocol.request(urlParam);
		},

		server : function(){
			this.request();//先执行request
			if( this.requestCount < 1 ){
				this.render();//不一定渲染成功
			}
		},

		render : function(){
			var that = this;
			if( this.template && this.selector ){
				//渲染模版
                var tmpl = $(mvc.template).find("#" + this.template).text();
				if(this.append){
					$(this.selector).append(mvc.Tmpl(tmpl, this._data));
				}else{console.log(this.selector);
					$(this.selector).html(mvc.Tmpl(tmpl, this._data));
				}
			}

			this.addEventListener();
		},
		afterRender:function(){

		},
		destroy : function(){
            this.params = null;
			this.append = null;
			this.selector = null;
			this.template = null;
			this._data = null;
			this.requestCount = 0;
		}
	});

	window.EasyController = EasyController;
})(jQuery);;/**
 * @comment : 简单的框架实现类
 * @author   : liuhualuo@163.com
 * @create   : 2012-7-18
 */
(function(){

	window.logger = (typeof logger != "object") ?
					{log:function(){}, error:function(){}} : logger;

	var mvc = function(){
		this.name = 'mvc';
		this.version = '1.0.2';
	};
	this.mvc = new mvc();
	mvc = this.mvc;
    //所有模版缓存的内容
	mvc.template = "";

	var Clone = function(object) {
        var clone = {};
        var cloneOf = function(item) {
            switch ( typeof item ) {
              case "array":
                return Clone(item);

              case "object":
                return Clone(item);

              default:
                return item;
            }
        };
        for (var key in object) clone[key] = cloneOf(object[key]);
        return clone;
    };

	mvc.URI = URI;
	var tempCache = {};

    var reg = new RegExp("([^&]*)=([^&]*)(&|$)", "g");
    var parseUri = function(uri){
        //var r = uri.match(reg)
        var param = {};
        while( (arr = reg.exec(uri)) != null){
            if(arr.length >2){
                param[arr[1]] = arr[2];
            }
        }
        return param;
    }

    /**
	 * @Comments : uri跳转
     * @param uri 跳转uri
     * @param refresh 不传默认为刷新页面.
	 * @author   : liuhualuo@163.com
	 * @create   : 2015-7-23
	 */
    mvc.href = function(uri, refresh){
        if(uri && typeof uri == "string"){
           if(!refresh){
                //增加时间戳
                var t = new Date().getTime();
                uri = uri.indexOf("?") > -1 ? uri + "&t=" + t
                    : uri + "?t=" + t;
            }
            if(uri.indexOf("#") == 0){
                window.location.hash = uri;
            }else{
                window.location.href = uri;
            }
        }
    }

    /**
	 * @Comments : mvc 路由
	 * @author   : liuhualuo@163.com
	 * @create   : 2015-6-23
	 */
	mvc.routeRun = function(uri){

        var params = {};
        if(uri && typeof uri == "string"){
            //去除uri上的参数
            if(uri.indexOf("?") > -1){
                //解析url中的参数
                params = parseUri(uri.substring(uri.indexOf("?") + 1, uri.length));
                uri = uri.substring(0, uri.indexOf("?"));
            }
        }else{
            logger.error("uri is " + uri);
            return ;
        }

		var uriItem = mvc.URI.getURI(uri);
		if(typeof uriItem != "object"){
			//未找到匹配的uri路由
			logger.error(uri + " is not match route.");
			return ;
		}
		var _self = this;

		var layout = uriItem.layout;
		for( var i in layout ){
			var lItem = layout[i];
			lItem.id = lItem.id || getItemId();

			//保存上次请求的div的页面模版。
			var uuid = $(lItem.selector).attr("uuid");
			if(uuid){
				tempCache[uuid] = $(lItem.selector).html();
			}

			if( !mvc.URI.isRefresh(URI.getQueryString("refresh"))
				&& uriItem.request ){
                logger.log("render cache page.");
				//不刷新页面
				var tempHtml = tempCache[lItem.id];
				if(tempHtml && lItem.selector){
					$(lItem.selector).html(tempHtml);
				}
			}else{
				//预先将模版放入到sammy缓存中 seajs 模式添加

				var controller = lItem.controller;
				controller = new controller({
					params : params || {},
					append : lItem.append,
					selector : lItem.selector,
					template : lItem.template
				});

				try{
					controller.server();
				}catch(e){
                    controller.destroy();
                    controller = null;
					logger.error([e, lItem]);
				}
			}

			$(lItem.selector).attr("uuid", lItem.id);

		}
		//标记该uri 已经请求过
		uriItem.request = true;
	}

	var idArr = ["a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9",
				"b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9",
				"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9",
				"d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9",
				"e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9",
				"f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9",
				"g0", "g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8", "g9",
				"h0", "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9"];

	var getItemId = function(){
		return idArr.pop();
	}

	var cache = {};
	mvc.Tmpl = function tmpl(str, data) {
		// Figure out if we're getting a template, or if we need to
		// load the template - and be sure to cache the result.
		var fn = !/\W/.test(str) ? cache[str] = cache[str] || mvc.Tmpl(document.getElementById(str).innerHTML) :

		// Generate a reusable function that will serve as a template
		// generator (and which will be cached).
		new Function("obj", "var p=[],print=function(){p.push.apply(p,arguments);};" +

		// Introduce the data as local variables using with(){}
		"with(obj){p.push('" +

		// Convert the template into pure JavaScript
		str.replace(/[\r\t\n]/g, " ").split("<%").join("\t").replace(/((^|%>)[^\t]*)'/g, "$1\r").replace(/\t=(.*?)%>/g, "',$1,'").split("\t").join("');").split("%>").join("p.push('").split("\r").join("\\'") + "');}return p.join('');");

		// Provide some basic currying to the user
		return data ? fn(data) : fn;
	};

	window.mvc = mvc;

    window.onhashchange = function(){
        mvc.routeRun(window.location.hash);
    }
})(jQuery);/**
 * @Comments : 系统通讯对象。
 * @author   : liuhualuo@163.com
 * @create   : 2012-7-18
 */
AjaxProtocol = (function() {

	/**
	 * @Comments : 校验传入的ajax请求的options参数，设置默认值。
	 * @param    : {Object}    options 参数选项对象
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-18
	 */
    var ajaxValidateOptions   = function(options) {
        if ($.isEmptyObject(options.type)) {
            options.type = 'GET';
        }
        if ($.isEmptyObject(options.dataType)) {
            options.dataType = 'json';
        }
        if ($.isEmptyObject(options.async)) {
            options.async = true;
        }
        if ($.isEmptyObject(options.cache)) {
            options.cache = false;
        }
        if ($.isEmptyObject(options.timeout)) {
            options.timeout = 18000;
        }
        if ($.isEmptyObject(options.showErr)) {
            options.showErr = false;
        }
    };

	/**
	 * @Comments : 将urlParam参数，转化为ajax请求的options参数形式。
	 * @param    : urlParam ==> { 为在URLParam中返回的urlParam。
	 *		askType
	 *		parameter	: 请求参数
	 *		resultWrapper
	 *		forceRefresh
	 *		dataType
	 *		scope		 : 请求作用域
	 *      data         : 传递的参数
	 *      success      : 加载成功的回调函数
	 *      failure      : 加载失败的回调函数
	 *      complete     : 请求结束后回调函数
	 *		url			 : 请求url
	 * }
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-18
	 */
	var getAjaxOptions = function(urlParam){
		var opts = {};
		opts.askType = urlParam.askType;
		opts.dataType = urlParam.dataType;
		opts.type = urlParam.type;
		opts.refreshTime = urlParam.refreshTime

		opts.data = urlParam.parameter;
		opts.failure = urlParam.failure;
		opts.complete = urlParam.complete;
		opts.resultWrapper = urlParam.resultWrapper;

		opts.scope = urlParam.scope;
		if( !$.isFunction(opts.resultWrapper) ){
			opts.resultWrapper = function(result){
				//console.log('P.resultWrapper');
				/*
				result = $.xml2json(result);
				if( $.isEmptyObject(result) ){
					return {};
				}
				result.data = result.result;
				result.result = {};
				*/
				return result;
			};
		}
		return opts;
	};

	/**
	 * @Comments : ajax请求数据接口。
	 * @param    : options ==> {
	 *      data         : 传递的参数
	 *      success      : 加载成功的回调函数
	 *      failure      : 加载失败的回调函数
	 *      complete     : 请求结束后回调函数
	 *		url			 : 请求url
	 *      ........
	 * }
	 * @author   : liuhualuo@163.com
	 * @create   : 2012-7-19
	 */
    var ajaxRequest = function(options) {
        var result = $.ajax({
			accepts		 : options.accepts,
			async		 : options.async,			//默认为true，请求以同步的形式发送。 该选项不支持 跨域请求和jsonp请求。jQuery 1.8, the use of async: false is deprecated.
            beforeSend   : options.beforeSend,		//在请求之前调用方法。参数（jqXHR, settings）。如果方法返回false，请求将不会被发送。
			cache        : options.cache,			//如设置false，则强制刷新，在url上添加参数"_=[TIMESTAMP]"。
			contents	 : options.contents,			//一个map参数，作为请求相应的content type。
			contentType  : options.contentType,		//默认值'application/x-www-form-urlencoded'，ata will always be transmitted to the server using UTF-8 charset;
			context		 : options.context,
			converters	 : options.converters,		//默认值{"* text": window.String, "text html": true, "text json": jQuery.parseJSON, "text xml": jQuery.parseXML}。每个转换值对应一个转换方法。
			crossDomain  : options.crossDomain,		//当设置为true时，强制跨域请求。
			data         : options.data,				//发送请求参数。
			dataFilter   : options.dataFilter,		//返回数据过滤方法。参数（data,type),data为从服务器返回过来的数据，type为数据类型。
			dataType     : options.dataType, //'jsonp',	//默认是系统智能猜测(xml, json, script, or html)。
			global		 : options.global,			//默认值为true
			headers		 : options.headers,			//默认值为{}, 一个map参数，在beforeSend方法调用前设置header。
			ifModified	 : options.ifModified,		//默认值为false，
			isLocal		 : options.isLocal,			//默认依赖当前本地协议。
			jsonp		 : options.jsonp,			//jsonp请求
			jsonpCallback: options.jsonpCallback,
			mimeType	 : options.mimeTyp,
			username	 : options.username,
			password	 : options.password,
			processData  : options.processData,		//默认值为true，
			scriptCharset: options.scriptCharset,
			statusCode	 : options.statusCode,		//默认值为{} statusCode: {	404: function() {	alert("page not found");	}	}
			timeout		 : options.timeout,
			traditional	 : options.traditional,
			url          : options.url,
            type         : options.type,
            error        : function(jqXHR, textStatus, errorThrown) {
				if ( $.isFunction(options.failure) ) {
					options.failure.call(options.scope,null,jqXHR, textStatus,errorThrown);
				}
            },
            success : function(result, textStatus, jqXHR) {
				if ( $.isFunction(options.resultWrapper) ) {
					result = options.resultWrapper(result);
				}
				if( $.isFunction(options.afterSuccess) ){
					options.afterSuccess(options, result, textStatus);
				}
				if ( $.isFunction(options.success) ){
					options.success.call(options.scope, options, result, textStatus);
				}
            },
			complete : function(jqXHR, textStatus){		//在请求完后，调用的方法（无论请求是否成功都会调用）。参数（jqXHR=XMLHTTPRequest, textStatus="success", "notmodified", "error", "timeout", "abort", or "parsererror"）。
				if ( $.isFunction(options.complete) ) {
					options.complete.call(options.scope, options, textStatus);
				}
			}
        });
		return result;
    };

	var ajaxRegisterEvent = function(options){
		ajaxRequest(options);
		setTimeout(function(){
			ajaxRegisterEvent(options);
		}, options.refreshTime || 2000);
	};

	Protocol.registerRequest("ajax", {
		type : "ajax",
		getOptions : getAjaxOptions,
		validateOptions : ajaxValidateOptions,
		request : ajaxRequest,
		registerEvent : ajaxRegisterEvent
	});

})();

(function(window) {


	var htmlDecode = function(value) {
		if (value && (value === '&nbsp;' || value === '&#160;' || (value.length === 1 && value
				.charCodeAt(0) === 160))) {
			return "";
		}
		return !value ? value : String(value).replace(
				/&gt;/g, ">").replace(/&lt;/g, "<")
			.replace(/&quot;/g, '"').replace(/&amp;/g,
				"&");
	};
	var htmlEncode = function(value) {
		return !value ? value : String(value).replace(/&/g,
			"&amp;").replace(/\"/g, "&quot;").replace(
			/</g, "&lt;").replace(/>/g, "&gt;");
	};


	var setCookie = function(name, value, hours, path) {
		var name = escape(name);
		var value = escape(value);
		var expires = new Date();
		expires.setTime(expires.getTime() + hours * 3600000);
		path = path == "" ? "" : ";path=" + path;
		_expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
		document.cookie = name + "=" + value + _expires + path;
		//		store.set(name, value);
	};
	function getCookieValue(name)
	{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
	return unescape(arr[2]);
	else
	return null;
	}
	var getCookieValue123 = function(name) {
		if(name=="MS_merchantId"){

		}
		var value="";
		switch(name)
		{
		case "MS_merchantId":
		  value=globalParam["merId"];
		  break;
		case "merId":
		  value=globalParam["merId"];
		  break;
		case "user_phone":
		  value=globalParam["user_phone"];
		  break;

		case "dev_id":
		  value=globalParam["dev_id"]||globalParam["devId"];

		  break;
		case "user_mac":
		  value=globalParam["user_mac"];
		  break;
		case "url":
		  value=globalParam["url"];
		  break;
		case "login_type":
		  value=globalParam["login_type"];
		  break;
		 case "region":
		  value=globalParam["region"];
		  break;
		 case "belongTo":
		  value=globalParam["belongTo"];
		  break;
		 case "model":
		  value=globalParam["model"];
		  break;
		  case "trafficLimit":
		  value=globalParam["trafficLimit"];
		  break;
		  case "ssid":
		  value=globalParam["ssid"];
		  break;
		  case "devName":
		  value=globalParam["devName"];
		  break;
		  case "corp":
		  value=globalParam["corp"];
		  break;
		case "MS_deviceId":
		  value=globalParam["devId"]||globalParam["dev_id"];

		  break;
		case "ap_mac":
		  value=globalParam["mac"];
		  break;
		case "MS_terMac":
		  value=globalParam["user_mac"];
		  break;
		case "MS_terMac":
		  value=globalParam["user_mac"];
		  break;
		case "gw_address":
		  value=globalParam["gw_address"];
		  break;
		case "gw_url":
		  value=globalParam["url"];
		  break;
		case "host":
		  value=globalParam["host"];
		  break;
		case "gw_port":
		  value=globalParam["gw_port"];
		  break;


		default:
			value=globalParam[name];
		}
		var name = escape(name);
		var allcookies = document.cookie;
		name += "=";
		var pos = allcookies.indexOf(name);
		if (pos != -1) {
			var start = pos + name.length;
			var end = allcookies.indexOf(";", start);
			if (end == -1) end = allcookies.length;
			var value = allcookies.substring(start, end);
			return unescape(value)||value;
		} else return value;
		//		return (store.get(name) || '');
	};
	window.setCookie = window.setCookie || setCookie;
	window.getCookieValue = window.getCookieValue || getCookieValue;
	window.htmlDecode = window.htmlDecode || htmlDecode;
	window.htmlEncode = window.htmlEncode || htmlEncode;

})(window);



function zImageUtil(config) {
	var o = {
		imgDom: null, //回显的image的id
		maxHeight: null, //预设的最大高度
		maxWidth: null, //预设的最大宽度
		postUrl: null, //提交的url"/calendar/image/upload.json"
		preShow: true,
		callback: null,

		fileChange: function(e) {
			var f = e.files[0]; //一次只上传1个文件，其实可以上传多个的
			var FR = new FileReader();
			var _this = this;

			FR.onload = function(f) {

				_this.compressImg(this.result, 300, function(data) { //压缩完成后执行的callback
					console.log("压缩完成后执行的callback");
					//document.getElementById('imgData').value = data;//写到form元素待提交服务器
					//document.getElementById('myImg').src = data;//压缩结果验证
					if (_this.preShow) {
						console.log("img pre show");
						_this.imgDom.src = data;
						console.log(_this.imgDom);

					}
					console.log("begin send img");
					var json = {};
					// json.imageName= "myImage.png";
					data = data.substring(22);
					// alert(data)
					json.imageData = encodeURIComponent(data);
					console.log("begin post");

					$.post(_this.postUrl,
						json,
						function(data) {
							if (data.r == 1) {
								_this.imgDom.src = UPLOAD_PATH+"/" + data.data;
								$(_this).parent().find("#picurl")
								console.log("imgUrl:" + data.data);
							} else {
								//	                        		zalert(data.msg);
							}
							if (_this.callback != null)
								_this.callback(data);
						}
					);
				});
			};
			FR.readAsDataURL(f); //先注册onload，再读取文件内容，否则读取内容是空的
		},
		compressImg: function(imgData, maxHeight, onCompress) {
			var _this = this;
			if (!imgData)
				return;
			onCompress = onCompress || function() {};
			maxHeight = maxHeight || this.maxHeight; //默认最大高度200px
			var canvas = document.createElement('canvas');
			var img = new Image();
			console.log("maxHeight:" + maxHeight);
			img.onload = function() {
				if (img.height > maxHeight) { //按最大高度等比缩放
					img.width *= maxHeight / img.height;
					img.height = maxHeight;
				}
				canvas.width = img.width;
				canvas.height = img.height;
				var ctx = canvas.getContext("2d");

				ctx.clearRect(0, 0, canvas.width, canvas.height); // canvas清屏

				//重置canvans宽高 canvas.width = img.width; canvas.height = img.height;
				console.log("width:" + img.width + "height:" + img.height);
				ctx.drawImage(img, 0, 0, img.width, img.height); // 将图像绘制到canvas上
				console.log("begin compress img");
				onCompress(canvas.toDataURL("image/png")); //必须等压缩完才读取canvas值，否则canvas内容是黑帆布
			};
			// 记住必须先绑定事件，才能设置src属性，否则img没内容可以画到canvas
			console.log("begin origin data load:");
			img.src = imgData;

		},
		init: function(jso) {
			this.imgDom = jso.imgDom;
			this.maxHeight = jso.maxHeight;
			this.maxWidth = jso.maxWidth;
			this.postUrl = jso.postUrl;
			this.callback = jso.callback;
		},
	};
	o.init(config);
	return o;
}

function formAjax() {
	var ajax_option = {

		success: function(data) {
			//				zalert(data.r+data.msg+data.data,"提示");
		}
	}
	$("#testForm").ajaxSubmit(ajax_option);
}/**
 * 购买跳转
 */
var BuyTranController = EasyController.extend({
	selector: {},
	request: function() {},
	addEventListener: function() {


		//		var dqUrl = "http://122.224.205.11/wap/market/iwifisale/iwifidetail.whtml?name=iwifi&goodsType=L&code_number=hzhz171434&link_phone=13325719856&yxgh=";
		var dqUrl = window.globalParam["dqUrl"];//this.getParam('url');
		$("#dqurllink").attr("href",window.globalParam["dqUrl"]);
		console.log(dqUrl);
		var EventUtil = {
			addHandler: function(element, type, handler) {
				if (element.addEventListener) {
					element.addEventListener(type, handler, false);
				} else if (element.attachEvent) {
					element.attachEvent("on" + type, handler);
				} else {
					element["on" + type] = handler;
				}
			},
			removeHandler: function(element, type, handler) {
				if (element.removeEventListener) {
					element.removeEventListener(type, handler, false);
				} else if (element.detachEvent) {
					element.detachEvent("on" + type, handler);
				} else {
					element["on" + type] = null;
				}
			}
		};

		function imgLoad() {
			var img = document.createElement('img');
			img.style = 'display:none;';
			img.src = REQUEST_IMG + '?' + new Date().getTime();
			EventUtil.addHandler(img, 'error', function() {
				console.log('你没连网');
				img = null;
				imgLoad();
			});
			EventUtil.addHandler(img, 'load', function() {
				console.log('你连网了');
				setTimeout(function() {
					window.location.href = dqUrl;
				}, 500)

			});
			//			document.body.appendChild(img);
		}
		imgLoad();

	},
	/**
	 * 获取uri参数
	 */
	getParam: function(name) {
		var search = document.location.href;
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
	}


});;/**
 * 主页控制
 */


var layerIndex;

// 问卷提交返回数据类型
function changeForm2Jso(formId) {

	var jso = {};
	var arr = formId.serializeArray();
	for (var i = 0; i < arr.length; i++) {
		jso["" + arr[i].name] = arr[i].value;
	}
	return jso;
}
// alert(JSON.stringify(changeForm2Jso($("#survey-form"))));

//红包
function redpacket() {
	var jso = JSON
			.stringify(changeForm2Jso($("#survey-form")));
	$.ajax({
		type : "POST",
		url : "/questionnaire/redPackage",
		data : jso,
		dataType : 'json',
		success : function(data) {
			var index = layer.open({
				content : '恭喜您获得'+data.data+'天免费时长',
				btn : [ '确定' ],
				style:' font-size: 15px; font-weight: 600;',
				yes:function(){
					layer.close(index);
					layer.close(layerIndex);
					reload();
				}
			});
		}
	});

}

function reload(){
	window.location.reload();
}
var initFlag = true;

function init(){
	if(initFlag){
		$(".optiondiv").each(function(){
   		$.each($(this).children("p").find("input"),function(){
   			 $(this).on('click',function(){
   			 	if("input:checked"){
   			 		$(this).parent().parent().prev().find(".wrong-d").remove();
   			 	}
   			 });
   		});
   	});
	}
	initFlag =false;
}

// 问卷提交验证
function formsubmit() {

	init();
	var indexcount = 0;
	var index = '';
	$(".wrong-d").remove();
	var wrongDiv=null;
	$(".optiondiv").each(function() {
		indexcount++;
		if($(this).children("p").find("input:checked").length==0){
			//alert("index"+index);
			if(index ==''){
				index="question"+indexcount;
			}
			$(this).prev().children().append("<span class='wrong-d' style='color:red;' >*请先选择再提交</span>");
			return;
		}
	});

	if(index !=''){
		$("input[name="+index+"]").focus();

		return false;

	}
	$("#x").prop("disabled",true);
	//document.getElementById("#x").disabled=true;
     $("#x").css("opacity","0.6");
	// 请求后台判断问卷提交成功
	var jso = changeForm2Jso($("#survey-form"));
	$
			.ajax({
				type : "POST",
				url : "/questionnaire/submitQuest",
				data : jso,
				dataType : 'json',
				success : function(data) {
					layer.closeAll();
					if (data.r == 1) {
							layer
									.open({
										content : '提交成功，感谢您的参与 </br> </br><img type="image" src="img/red packet.png" style="width:98px;" onclick="redpacket()"></img></br></br>拆开红包获取免费上网时长'
									,style:' font-size: 15px; font-weight: 600;'
									})
					}else {
						layer
									.open({
									content : '提交失败',
									btn : [ '确定' ]
									 ,style:' font-size: 15px; font-weight: 600;',
									yes:function(){
										layer.closeAll();
										//reload();
									}
									})

					}

				}
			});

}

function outquestion(){

	var index =	layer
			.open({
				content : '  确定要离开吗    ',
				btn : [ '取消', '确定' ],
				style:' font-size: 15px; font-weight: 600;',
				yes : function(){
					layer.close(index);

					},
			    no : function(){
			    	layer.closeAll();
			    }
				})
}

var param = {};
var IndexController = EasyController
		.extend({
			request : function() {
				window.indexSI = window.indexSI || [];
			},

			dqurl : '',
			noTime : '',

			/**
			 * 发起请求去后台获取电渠的购买地址 获得电渠地址请求成功回调
			 *
			 * @param {Object}
			 *            options
			 * @param {Object}
			 *            result
			 * @param {Object}
			 *            textStatus
			 * @param {Object}
			 *            jqXHR
			 */
			getDqUrlRequest : function(options, result, textStatus, jqXHR) {
				var that = this;
				var dqurl;
				if (result.r == AJAX_SUCC) {
					// 获取成功 处理地址
					/*
					 * var data = result.msg || '{}'; if (data == 'null') { data =
					 * '{}'; }
					 */
					data = result.data; // eval("(" + data + ")");
					dqurl = data.deviceurl || '';
					if (dqurl == '' || dqurl == 'error') {
						dqurl = '';
					} else {
						dqurl = dqurl + "&link_phone=" + data.deviceusername
								+ "&code_number=" + data.deviceinfo + "&yxgh="
								+ data.devicemerid;
					}
					console.log(dqurl);
					that.dqurl = dqurl;
					globalParam["dqUrl"] = dqurl;
					var noTime = $("#buy").attr("name");
					if ($("#buy").attr("disabled")) {
						return;
					} else if (dqurl != '') {
						// 没时间临时放行
						if (noTime == 'noTime') {
							that.buy(dqurl);
						} else {
							// 有时间直接访问
							window.location.href = dqurl;
						}
					} else {
						// 获取地址不符合期望的情况
						alert("未获取到宽带账号");
						that.showLoginPanel();
					}
				} else {
					if(result.r==504){
						var html = '<div class="row-9 center-text" >支付服务失败，请重新登录！</div>';
						Dialogue.modal(html, '25%', '180px', function() {
							that.showLoginPanel();
							/*
							 * mvc.href("#/login"); $("#MainContent").hide();
							 * $("#LoginWrap").show();
							 */
						}).showModal();
					}else{
						tip(result.msg);
					}
					// 获取失败 确认后跳转登录页

				}

			},
			/**
			 * 领取免费流量包请求成功回调 左上角要显示已联网 要有倒计时 能上网
			 *
			 * @param {Object}
			 *            options
			 * @param {Object}
			 *            result
			 * @param {Object}
			 *            textStatus
			 * @param {Object}
			 *            jqXHR
			 */
			getFreeRequest : function(options, result, textStatus, jqXHR) {
				var that = this;
				if (result.r == AJAX_SUCC) {
					var html = '<div class="row-9 center-text" >领取成功</div>';
					Dialogue.modal(html, '25%', '180px', function() {
						// mvc.href("#/");

						var authData = {
							"merchantId" : getCookieValue("MS_merchantId"),
							'deviceId' : getCookieValue('MS_deviceId'),
							'apMac' : getCookieValue('ap_mac'),
							'terMac' : getCookieValue('MS_terMac'),
							'userMac' : getCookieValue('MS_terMac'),
							'callback' : "callback",
							'username' : getCookieValue("MS_telephone"),
							'password' : getCookieValue("MS_pcode")
						};
						afterLoginFlag = true;
						that.auther(authData);

					}).showModal();
					$("#getFreeBtn").hide();
					$("#getFreePkgWrap").hide();
					// $("#buydivwrap").show();
					$("#buy").show();
				} else if (result.msg) {
					var html = '<div class="row-9 center-text">' + result.msg
							+ '</div>';
					// $("#timeMsg").html('您已领取免费上网体验包');
					Dialogue.modal(html, '25%', '180px').showModal();
				}
			},
			/** 显示倒计时* */
			leftTimeShow : function(cutoffdate) {
				//var cutoffdate = 0;
				// 获得剩余时长
				//if (user_cutoff) {
					cutoffdate = cutoffdate || 0;

				now_time = new Date().getTime();
				// 时长大于0
				leftTime = (cutoffdate - now_time) > 0 ? (cutoffdate - now_time)
						: 0;
				if (leftTime > 0) {

				}

				// 已登录才给弹框

				if (leftTime <= (60 * 60 * 1000)) {
					// 基本没时间了 认为没时间
					if (leftTime <= 3000) {
						// that.noTime='noTime';
						$("#buy").attr("name", "noTime");
						var html = '<div class="row-9 center-text" >您已无上网时长</div>';
						Dialogue.modal(html, '25%', '200px', '').showModal();
					} else {
						var html = '<div class="row-9 center-text "  >您的上网时长已不足一小时</div>'
								+ '<div class="row-9 center-text " >请尽快购买时长</div>';
						$.getJSON("/ms/sms/send", {
							"mobile" : getCookieValue("MS_telephone"),
							"type" : "ms_warn"
						}, function() {
						});
						Dialogue.modal(html, '25%', '200px', '').showModal();
					}
				}
				// 倒计时
				if (window.indexSI.length) {
					for ( var i in window.indexSI) {
						clearInterval(window.indexSI[i]);
					}
				}
				var indexSI = setInterval(function() {
					var _html = '';
					if (leftTime <= 0) {
						if (window.indexSI.length) {
							for ( var i in window.indexSI) {
								clearInterval(window.indexSI[i]);
							}
						}
						_html = '<span>您暂时无法上网</span>';
						$("#leftTimeTitle").html("请购买上网时长");
					} else {
						$("#leftTimeTitle").html("剩余上网时间");
						var d = Math.floor(leftTime / 1000 / 60 / 60 / 24);
						var h = Math.floor(leftTime / 1000 / 60 / 60 % 24);
						var m = Math.floor(leftTime / 1000 / 60 % 60);
						var s = Math.floor(leftTime / 1000 % 60);
						h = h < 10 ? '0' + h : h;
						m = m < 10 ? '0' + m : m;
						s = s < 10 ? '0' + s : s;
						_html = '<span>' + d + '天' + h + ':' + m + ':' + s
								+ '</span>';
					}
					$("#leftTime").html(_html);
					leftTime = leftTime - 1000;
				}, 1000);
				window.indexSI.push(indexSI);

			},
			/**
			 * 主页信息回调
			 *
			 * @param {Object}
			 *            options
			 * @param {Object}
			 *            result
			 * @param {Object}
			 *            textStatus
			 * @param {Object}
			 *            jqXHR
			 */
			indexCallBack : function(options, result, textStatus, jqXHR) {
				var that = this;
				var cutoffdate, now_time, leftTime;
				var topPicList, merchant, broadbandAccount, notice;
				var picHTML = '';
				if (result.r == AJAX_SUCC && result.data) {
					 setCookie("merchantId",result.data.merchant.merchantId , 24, "/");
					topPicList = result.data.topPicList;
					merchant = result.data.merchant;
					broadbandAccount = result.data.merchant.broadbandAccount;
					notice = result.data.notice;

					// 商户信息
					if (merchant) {
						// 园区名
						if (merchant.merchantName) {
							var merchantname = merchant.merchantName;
							$("#merchantname").html(merchantname);
							setCookie("MS_merchantName", merchantname, 24, "/");
						}
						// 园区介绍
						if (merchant.remarks) {
							var info = htmlDecode(merchant.remarks);
							info = htmlEncode(info);
							$("#remarks").html(info);
						}
					}
					// 宽带帐号
					var html = '<div>客服热线：10000 </div>' + '<div>报障请提供宽带账号：'
							+ (broadbandAccount || '') + '</div>';
					$("#accUname").html(html);

					// 轮播图
					if (topPicList && topPicList.length < 1) {
						// 无轮播图放默认图
						picHTML += '<li class="carousel-ul-li" ><a href="javascript:void(0)">'
								+ '<img class="carousel-img row-10" src="'
								+ IMG_PATH
								+ 'img/carousel/ad1.png"  ></a></li>';
					} else {
						for (var i = 0; i < topPicList.length; i++) {
							var img = topPicList[i].picpath || '';
							if (img != '') {
								img = UPLOAD_PATH + '' + img;
							}

							picHTML += '<li class="carousel-ul-li" ><a href="javascript:void(0)">'
									+ '<img  class="carousel-img row-10" src="'
									+ img + '"  /></a></li>';
						}
					}
					// 滚动播报
					if (notice) {
						var notices = eval("(" + notice + ")");
						var noticeHtml = '<marquee direction=left>';
						for ( var i in notices) {
							var noticeTitleInfo = notices[i].noticeTitle || '';
							noticeTitleInfo = htmlDecode(noticeTitleInfo);
							noticeTitleInfo = htmlEncode(noticeTitleInfo);
							var noticeTitle = noticeTitleInfo;

							var noticeTextInfo = notices[i].noticeText || '';
							noticeTextInfo = htmlDecode(noticeTextInfo);
							noticeTextInfo = htmlEncode(noticeTextInfo);
							var noticeText = noticeTextInfo;
							noticeHtml += '&nbsp; &nbsp;&nbsp;<span>'
									+ noticeTitle + '</span> &nbsp;<span>'
									+ noticeText + '</span> ';
						}
						noticeHtml += '</marquee>';
						$("#activeInfoLi").html(noticeHtml);
					}
				} else {
					// 返回不符合期望 展示默认页
					picHTML = '<li class="carousel-ul-li" ><a href="javascript:void(0)">'
							+ '<img class="carousel-img row-10" src="'
							+ IMG_PATH + 'img/carousel/ad1.png"  ></a></li>';
				}

				$("#carousel-ul").html(picHTML);

				// 开启图片轮播
				var width = document.body.offsetWidth;
				var height = width * 9 / 16;
				$(".carousel").show().yxMobileSlider({
					height : height + 'px'
				});
				$(window).resize();
				this.initUserCallBack(null,result);
			},
			initUserCallBack : function(options, result, textStatus, jqXHR) {
				console.log("initUserCallBack");
				var that = this;
				var cutoffdate, now_time, leftTime;

				if (result.r == AJAX_SUCC && result.data) {

					// 获得剩余时长
					if (result.data.user) {
						console.log("已登录");
						setCookie("state","login");
						$("#linkMsg").text("已登录");

						$("#fastLogin").hide(); // 一建登录隐藏
						$("#buydivwrap").show();

						if (result.data.timeInfo) {
							cutoffdate = result.data.timeInfo.endTime || 0;
						} else {
							cutoffdate = 0;
						}
						now_time = result.data.now_date || 0;
						// 时长大于0
						leftTime = (cutoffdate - now_time) > 0 ? (cutoffdate - now_time)
								: 0;
						if (leftTime > 0 || result.data.vip) {
							console.log("发现有时间");
							if (!afterLoginFlag) {
								// console.log("是刚登陆过");
								// imgLoad3();//如果没有登录进行登出操作
								/*
								 * var authData = { "merchantId":
								 * getCookieValue("MS_merchantId"), 'deviceId':
								 * getCookieValue('MS_deviceId'), 'apMac':
								 * getCookieValue('ap_mac'), 'terMac':
								 * getCookieValue('MS_terMac'), 'userMac':
								 * getCookieValue('MS_terMac'), 'callback':
								 * "callback", 'username':
								 * getCookieValue("MS_telephone"), 'password':
								 * getCookieValue("MS_pcode") };
								 * console.log("开始认证"); that.auther(authData);
								 */
							} else {
								console.log("不是刚登陆过，不触发联网");
							}
						}

						var roleType = result.data.user.role;
						var localType = result.data.user.role;
						if (roleType == 2) {// 用户权限 2为管理员 1为普通用户
							$("#mngPower").show();
							$('#enterprise').hide();
						} else if (roleType == 3) {// 用户本地类型 3为企业管理员
													// 4为园区代理//用户本地类型
													// 3为企业管理员 4为园区代理
							$('#enterprise').show();
							$("#mngPower").hide();
						} else {
							$("#mngPower").hide();
							$('#enterprise').hide();
						}
						if(result.data.timeInfo){
						// 是尊贵会员
						if (result.data.timeInfo.vip) {
							// if (true) {
							$("#buy").attr("disabled", "disabled");
							$("#buy").removeClass("back-orange");
							$("#buy").addClass("back-gray");
							$("#shalou").hide();
							$("#leftDiv").addClass("row-5");
							$("#buyDiv").addClass("row-5");
							$("#leftTime")
									.html(
											"<span style='font-size:1.7rem;font-weight:500'>电信尊贵客户</span>");
							$("#leftTimeTitle").html("<span  >无需购买时长</span>");
						} else {
							// 可否领包 vip无需领包
							var free_pkg = result.data.timeInfo.canGetFreePkg || '';
							// free_pkg = 1;
							if (free_pkg ) {
								// $("#buy").hide();//现在分开了 不用一个隐藏一个显示
								$("#getFreeBtn").show();
								$("#getFreePkgWrap").show();

								// return ; //此时return 无倒计时 无弹框 初次进入不会提示 比较符合交互
							} else {
								$("#getFreeBtn").hide();
								$("#getFreePkgWrap").hide();
							}
							// 已登录才给弹框
							//if (result.data.user) {
								if (leftTime <= (60 * 60 * 1000)) {
									// 基本没时间了 认为没时间
									if (leftTime <= 3000) {
										// that.noTime='noTime';
										$("#buy").attr("name", "noTime");
										var html = '<div class="row-9 center-text" >您已无上网时长</div>';
										Dialogue
												.modal(html, '25%', '200px', '')
												.showModal();
									} else {
										var html = '<div class="row-9 center-text "  >您的上网时长已不足一小时</div>'
												+ '<div class="row-9 center-text " >请尽快购买时长</div>';
										$
												.getJSON(
														"/ms/sms/send",
														{
															"mobile" : getCookieValue("MS_telephone"),
															"type" : "ms_warn"
														}, function() {
														});
										Dialogue
												.modal(html, '25%', '200px', '')
												.showModal();
									}
								}
								// 倒计时
								var indexSI = setInterval(
										function() {
											var _html = '';
											if (leftTime <= 0) {
												if (window.indexSI.length) {
													for ( var i in window.indexSI) {
														clearInterval(window.indexSI[i]);
													}
												}
												_html = '<span>您暂时无法上网</span>';
												$("#leftTimeTitle").html(
														"请购买上网时长");
											} else {
												$("#leftTimeTitle").html(
														"剩余上网时间");
												var d = Math.floor(leftTime
														/ 1000 / 60 / 60 / 24);
												var h = Math.floor(leftTime
														/ 1000 / 60 / 60 % 24);
												var m = Math
														.floor(leftTime / 1000 / 60 % 60);
												var s = Math
														.floor(leftTime / 1000 % 60);
												h = h < 10 ? '0' + h : h;
												m = m < 10 ? '0' + m : m;
												s = s < 10 ? '0' + s : s;
												_html = '<span>' + d + '天' + h
														+ ':' + m + ':' + s
														+ '</span>';
											}
											$("#leftTime").html(_html);
											leftTime = leftTime - 1000;
										}, 1000);
								window.indexSI.push(indexSI);
							//}

						}
						}
						// 用户有没有参与问卷调查

						var param = {
							"merchantId" : getCookieValue("merchantId"),
							'apMac' : getCookieValue('apMac'),
							'userId' : getCookieValue("phone"),
							'ssid' : getCookieValue("ssid")
						};

						$
								.ajax({
									type : "post",
									url : "/questionnaire/checkUserStatus",
									data : param,
									dataType : "json",
									success : function(data) {
										if (data.r == 1) {
											// 用户没有参与过.弹询问
											if (data.data == 1) {
												// 用户是否参与问卷调查
												var index =	layer
														.open({
															content : '爱WIFI邀请您参与问卷调查，参与后可获得免费上网时长哦',
															btn : [ '取消', '参与' ],
															style:' font-size: 15px; font-weight: 600;',
															no : function() {
																// 用户同意参与 ,弹问卷
																$
																		.ajax({
																			url : "/questionnaire/isJoinQuest",
																			data : {
																				"agree" : 1
																			},
																			dataType : "json",
																			type : "post",
																			success : function(
																					data) {
																				$
																						.ajax({
																							url : "questionnaire_survey.html",
																							type : "get",
																							success : function(
																									result) {

																								//setTimeout(function(){

																									var questionhtml = result;
																									layerIndex =	 layer
																											.open({
																												type : 1,
																												content : questionhtml,
																												anim : 'up',

																												style : ' position:fixed; bottom:0; left:0;  width: 100%; height: 100%; padding:10px 0; border:none;'
																											});
																								//}

																							//,1000 );

																							}
																						});
																			}
																		});

															},
															yes : function() {
																$
																		.ajax({
																			url : "/questionnaire/isJoinQuest",
																			data : {
																				"agree" : 0
																			},
																			dataType : "json",
																			type : "post",
																			success : function(
																					data) {
																				layer.close(index);
																			}
																		});
															}
														});
												// 如果用户同意,没有提交调查文件,弹问卷
											} /*else if (data.data == 2) {
												$
														.ajax({
															url : "questionnaire_survey.html",
															type : "get",
															success : function(
																	result) {
																setTimeout(function(){
																	var questionhtml = result;

																	layer
																			.open({
																				type : 1,
																				content : questionhtml,
																				anim : 'up',
																				style : 'position:fixed; bottom:0; left:0;  width: 100%; height: 100%; padding:10px 0; border:none;'
																			});
																}
																,1000);

															}
														});

											} else if (data.data == 3) {

											//	alert(data.data);

												layer
														.open({
															content : '您有一个红包未领取！</br> </br><img type="image" src="img/red packet.png" style="width:98px;" onclick="redpacket()"></img></br></br>拆开红包获取免费上网时长'
														,style:' font-size: 15px; font-weight: 600;'
														})
											}*/

										}

									}
								});


						// });

						// }
						// 点击红包后弹窗



					} else {

					}

					// 用户权益===================================
					// 已登录

					//Protal暂停时长购买功能

					//  alert(authData.merchantId);

					  if(getCookieValue("merchantId")=="56059"){
						  $("#buy").hide();
						  $("#buy").prop("disabled",true);
						  $("#buyDiv").removeClass("right-text").html("<span style='font-size:1.2rem; '>尊敬的用户您好，如有爱WIFI上网需求，请联系企业管理员</span>");
						  return false;
					  }


				} else {
					console.log("未登录");
					setCookie("state","notlogin");
					// // 弹窗
					// $("#sureMsg").on("click", function() {
					// $(".modal").hide();
					// $(".mask").hide();
					// });
					var user_phone = getCookieValue("mobilePhone");
					var login_type = getCookieValue("userType");
					console.log("user_phone:" + user_phone + "login_type:"
							+ login_type);



					if (login_type && login_type == "authed") {
						console.log("authed");
						var re = /^1\d{10}$/;

						if (user_phone && re.test(user_phone)) {

							$("#fastLoginAccount").text(user_phone);
							$("#fastLogin").show();
							$("#buydivwrap").hide();
							$("#switch_account").click(function() {
								that.showLoginPanel();
								/*
								 * mvc.routeRun("#/login");
								 * $("#MainContent").hide();
								 * $("#LoginWrap").show();
								 */
							})
							$("#fastLoginBtn")
									.click(
											function() {
												/**
												 * 登录系统
												 */

												var self = $(this);
												var username = user_phone;

												// 存储此次登录的用户名
												setCookie("MS_telephone",
														username, 24, "/");
												loading.show();
												// 请求
												console.log("开始一键登录");
												Protocol
														.request(
																"user.login",
																{
																	parameter : {
																		'username' : getCookieValue("MS_telephone"),
																		'captcha' : '1234',
																		/*'deviceId' : getCookieValue('MS_deviceId'),
																		'apMac' : getCookieValue('ap_mac'),
																		'merchantId' : getCookieValue("MS_merchantId"),
																		'terMac' : getCookieValue('MS_terMac'),
																		'userMac' : getCookieValue('MS_terMac'),*/
																		'authed' : true,
																		'callback' : "callback"
																	},
																	success : that.afterLogin,
																	failure : function(
																			options,
																			jqXHR,
																			textStatus,
																			errorThrown) {

																		var status = jqXHR.status
																				|| '0';
																		var statusText = jqXHR.statusText
																				|| '';

																		//alert(statusText);
																	},
																	complete : function(
																			options,
																			jqXHR,
																			textStatus) {
																		loading
																				.hide();
																	},
																	scope : that
																});

											})
						}
					} else {
						$("#fastLogin").hide();
						$("#buydivwrap").show();
					}
					// 这里判断是否显示一建登录
					console.log("未登陆");
					console.log("获取用户信息失败/或者没有用户登录");
				}
			},
			addEventListener : function() {
				// /以下的代码在修改登录为路由跳转后基本无效可以删除
				// tip("正在初始化");

				// 去除定时器
				if (window.indexSI.length) {
					for ( var i in window.indexSI) {
						clearInterval(window.indexSI[i]);
					}

				}
				// if (window.getSuccSI) {
				// clearInterval(window.getSuccSI);
				// }

				var that = this;
				// 4.X设备对象

				$("#wifiBtn").click(function() {
					that.showLoginPanel();

				})
				/*var devInfo = this.params["dev_info"] || '{}';
				devInfo = decodeURI(devInfo);
				devInfo = eval("(" + devInfo + ")");

				// 设备数据存放Cookie
				var host = this.params["host"] || '';
				if (host != '') {
					setCookie("host", host, 24 * 99, "/");
				}

				var deviceId = this.params["dev_id"] || '';
				if (deviceId != '') {
					setCookie("MS_deviceId", deviceId, 24 * 99, "/");
				}

				var gw_address = this.params["gw_address"] || '';
				if (gw_address != '') {
					setCookie("gw_address", gw_address, 24 * 99, "/");
				}

				var gw_url = this.params["url"] || '';
				if (gw_url != '') {
					setCookie("gw_url", gw_url, 24 * 99, "/");
				}

				var gw_port = this.params["gw_port"] || '';
				if (gw_port != '') {
					setCookie("gw_port", gw_port, 24 * 99, "/");
				}

				var terMac = this.params["user_mac"] || '';

				if (terMac != '') {
					setCookie("MS_terMac", terMac, 24 * 99, "/");
				}//alert("terMac:"+terMac);
				// only 4
				// var ap_mac = devInfo.mac || '';
				// if (ap_mac != '') {
				// setCookie("ap_mac", ap_mac, 24 * 99, "/");
				// }
				// var merchantId = devInfo.merId || '';
				// if (merchantId != '') {
				// setCookie("MS_merchantId", merchantId, 24 * 99, "/");
				// }

				// 兼容3 4的补丁
				var apmacTHREE = this.params["ap_mac"] || '';
				var apmacFOUR = devInfo.mac || '';
				var ap_mac = apmacFOUR != '' ? apmacFOUR : apmacTHREE;
				if (ap_mac != '') {
					setCookie("ap_mac", ap_mac, 24 * 99, "/");
				}
				var meridTHREE = this.params["merchantId"] || '';
				var meridFOUR = devInfo.merId || '';
				var merchantId = meridFOUR != '' ? meridFOUR : meridTHREE;
				if (merchantId != '') {
					setCookie("MS_merchantId", merchantId, 24 * 99, "/");
				}

				var portal_type;
				if (this.params["portal_type"]
						& this.params["portal_type"] != '') {
					portal_type = this.params["portal_type"];
				} else {
					portal_type = "authFatAP";
				}
				console.log("判断设备为:authFatAP");

				if (portal_type != '') {
					setCookie("portal_type", portal_type, 24 * 99, "/");
				}

				var ac_name = this.params["ac_name"] || '';
				if (ac_name != '') {
					setCookie("ac_name", ac_name, 24 * 99, "/");
				}*/
				// ////////////以上的代码可以删除
				/**
				 * 免登录认证
				 */
				if (afterLoginFlag) {
					console.log("登录后跳转首页开始认证");
					var authData = {
						/*"merchantId" : getCookieValue("MS_merchantId"),
						'deviceId' : getCookieValue('MS_deviceId'),
						'apMac' : getCookieValue('ap_mac'),
						'terMac' : getCookieValue('MS_terMac'),
						'userMac' : getCookieValue('MS_terMac'),
						'callback' : "callback",
						'username' : getCookieValue("MS_telephone"),
						'password' : getCookieValue("MS_pcode")*/
					};

					that.auther(authData);
				}
				// 请求首页信息
				that.index();

				/**
				 * 购买按钮
				 */
				$("#buy").on(
						'click',
						function() {
							if ($(this).attr("disabled")) {
								return;
							} else {
								// 每隔5秒才能继续点击购买
								$(this).attr("disabled", true);
								setTimeout($(this).attr("disabled", false),
										5000);
							}

							// $("#buy").attr("disabled","disabled");

							// 获取电渠地址
							var state = getCookieValue("state");
							if(state=="notlogin"){
								$(".indexWrap").hide();
								$(".loginWrap").show();
								return;
							}
							Protocol.request("active.getDqUrl",
									{ // /ms/pay/url/get
										// MsLoginControl.payUrlGet
										parameter : {},
										success : that.getDqUrlRequest,
										scope : that,
										failure : function(options, jqXHR,
												textStatus, errorThrown) {
										},
										complete : function(options, jqXHR,
												textStatus) {
										}
									});
						});

				var roleType = getCookieValue('MS_roleType'); // 用户权限 2为管理员
																// 1为普通用户
				var localtype = getCookieValue('localtype');// 用户本地类型 3为企业管理员
															// 4为园区代理
				console.log(localtype);
				/**
				 * 根据权限开启管理功能
				 */
				if (roleType == 2) {
					// $("#mngPower").show();
				} else if (localtype == 3) {
					// $('#enterprise').show();
				}

				/**
				 * 领取免费流量包
				 */
				$("#getFreeBtn")
						.on(
								"click",
								function() {

									loading.show();
									Protocol
											.request(
													"active.getFree",
													{
														parameter : {
														// "id":
														// getCookieValue("MS_freeMeatId")
														},
														scope : that,
														success : that.getFreeRequest,
														failure : function(
																options, jqXHR,
																textStatus,
																errorThrown) {
															var html = '<div class="row-9 center-text "  >领取失败</div>'
																	+ '<div class="row-9 center-text " >请重新领取</div>';
															Dialogue
																	.modal(
																			html,
																			'25%',
																			'200px')
																	.showModal();
														},
														complete : function(
																options, jqXHR,
																textStatus) {
															loading.hide();
														}
													});

								});
				/**
				 * 关闭园区动态
				 */
				$("#closeActive").on("click", function() {
					$("#activeInfo").parent().parent().hide();
				});

				this.addEventOfLogin();

			},
			afterLogin : function(data) {
				if (data.result.r == AJAX_SUCC) {

					// 成功登陆
					// 隐藏一键登录
					afterLoginFlag = true;
					// TODO test
					// 2016-8-16 18:32:15 增加角色判断 zzw
						/*if (data.result.data) {
							type = data.result.data.type || '';
							if (data.result.data.AWIFI_MS_USER_AUTH) {*/
								//MS_pcode = data.result.data.AWIFI_MS_USER_AUTH.authpswd
										//|| '';
								//setCookie("MS_pcode", MS_pcode, 24 * 99, "/");
						/*	}
						}*/

						/*if (type == '') {
							roleType = 1; // 用户
						} else {

							roleType = 2; // 管理
						}*/
						//setCookie("MS_roleType", roleType, 24 * 99, "/");
						/*if (data.result.user) {
							roleType = data.result.user.role;
						}
						if (localType) {
							setCookie("roleType", roleType, 24, "/");// 已登录用户如果存在本地类型则放入cookie
						}
						if (roleType == 2) {// 用户权限 2为管理员 1为普通用户
							$("#mngPower").show();
							$('#enterprise').hide();
						} else if (roleType == 3) {// 用户本地类型 3为企业管理员
													// 4为园区代理//用户本地类型 3为企业管理员
													// 4为园区代理
							$('#enterprise').show();
							$("#mngPower").hide();
						} else {
							$("#mngPower").hide();
							$('#enterprise').hide();
						}*/
					// /end
					tip("登录成功");
					console.log("登录成功");
					this.goIndex();
					// this.initUser();
					this.initUserCallBack(data, data.result);
					this.autherCallBack(data.result);

				} else {
					tip(data.result.msg||"登录失败");
					console.log("登录失败");
					//alert(data.result.msg);
				}
			},
			/**
			 * 主页信息
			 */
			index : function() {
				var that = this;
				loading.show();
				Protocol
						.request(
								"index.index",
								{
									parameter : {
										"logId" : getCookieValue('logId'),
										"deviceId" : getCookieValue('deviceId'),
										"mobilePhone" : getCookieValue('mobilePhone'),
										"userMac" : getCookieValue('userMac'),
										"userIP" : getCookieValue('userIP'),
										"gwAddress" : getCookieValue('gwAddress'),
										"gwPort" : getCookieValue('gwPort'),
										"nasName" : getCookieValue('nasName'),

									},
									success : this.indexCallBack,
									failure : function(options, jqXHR,
											textStatus, errorThrown) {
										var picHTML = '<li class="carousel-ul-li" ><a href="javascript:void(0)">'
												+ '<img class="carousel-img row-10" src="'
												+ IMG_PATH
												+ 'img/carousel/ad1.png"  ></a></li>';
										$("#carousel-ul").html(picHTML);
										/**
										 * 开启图片轮播
										 */
										var width = document.body.offsetWidth;
										var height = width * 9 / 16;
										$(".carousel").show().yxMobileSlider({
											height : height + 'px'
										});
										$(window).resize();
									},
									complete : function(options, jqXHR,
											textStatus) {
										loading.hide();
									},
									scope : that
								});

			},
			initUser : function() {
				console.log("开始initUser");
				var that = this;
				Protocol.request("index.initUser",
						{
							parameter : {
								/*"deviceId" : getCookieValue('MS_deviceId'),
								"merchantId" : getCookieValue('MS_merchantId'),
								"terMac" : getCookieValue('MS_terMac'),*/

							},
							success : this.initUserCallBack,
							failure : function(options, jqXHR, textStatus,
									errorThrown) {
							},
							complete : function(options, jqXHR, textStatus) {
								loading.hide();
							},
							scope : that
						});
			},

			/**
			 * 购买放通10分钟
			 */
			buy : function(dqurl) {
				var authData = {

					'paytype' : 1
				// 放通10分钟标志
				};
				loading.show();
				this.auther(authData, '1');
			},
			autherCallBack : function(data) {
				var that = this;
				var paytype = data.data.paytype;
				console.log("auther结果");
				console.log(data);
				console.log("请求认证：");
				console.log(data);
				tip("正在请求上网");
				if (loading) {
					loading.hide();
				}
				if (data.data && data.data.msg) {

					console.log("认证报错:" + data.data.msg);
					tip(data.data.msg);
					if (data.data.msg == "已超过当日认证次数") {

						mvc.href("#/buyTran");
					}
					return;
				}

				// var auth, dqurl;
				// if (data.data) {
				// dqurl = data.data.deviceurl || '';
				// if (dqurl == '' || dqurl == 'error') {
				// dqurl = getCookieValue('dqurl');
				// } else {
				// dqurl = dqurl + "&link_phone=" + data.data.deviceusername +
				// "&code_number=" + data.data.deviceinfo + "&yxgh=" +
				// data.data.devicemerid;
				// setCookie("dqurl", dqurl, 24 * 99, "/");
				// }
				// }

				var auth;
				if (data.data && data.data.access_auth) {
					var str = data.data.access_auth.auth || '';
					if (str != '') {
						str = str.substring(8, str.length);
						auth = eval(str);
					}
					if (data.data.access_auth.kickoff
							&& data.data.access_auth.kickoff == 1) { // 提示其他用户被踢下线
						console.log("之前的账号被踢下线");
						var html = '<div class="row-9 center-text" >完成登录,其他设备的账号已经下线！</div>';
						Dialogue.modal(html, '25%', '180px', function() {
						}).showModal();
					}
				} else {
					console.log("即不是vip 也没有时长");
				}
				if (data.data.timeInfo.vip) {

				} else if (data.data.timeInfo.endTime) {
					that.leftTimeShow(data.data.timeInfo.endTime);
				}
				if (auth && auth.resultCode == '0') {

					// if (true) {
					$("#linkMsg").text("已连网");

					console.log("认证登陆成功:" + auth.resultCode);
					// 拿取后端给的token
					var token = auth.data;
					// 测试
					// var token = '';
					var authUrl;
					var host = encodeURIComponent((getCookieValue('host') || ''));
					var dev_id = getCookieValue('MS_deviceId');
					var gw_address = getCookieValue('gw_address');
					var gw_url = encodeURIComponent((getCookieValue('gw_url') || ''));
					var gw_port = getCookieValue('gw_port');
					var user_mac = getCookieValue('MS_terMac');
					var ap_mac = getCookieValue('ap_mac');
					var portal_type = getCookieValue('portal_type');
					var ac_name = getCookieValue('ac_name');
					if (ac_name == null || ac_name == ''
							|| ac_name == 'undefined') {
						ac_name = '';
					}
					if (gw_port && gw_port != '') {
						gw_address += ':' + gw_port;
					}
					if (portal_type == 'authFatAP') {

						authUrl = 'http://' + gw_address
								+ '/smartwifi/auth?url=' + gw_url
								+ '&user_mac=' + user_mac + '&token=' + token
								+ '&ac_name=' + ac_name;
					} else {
						authUrl = host + '?dev_id=' + dev_id + '&dev_mac='
								+ ap_mac + '&site_id=' + '1' + '&user_mac='
								+ user_mac + '&ac_name=' + ac_name;
					}
					console.log("请求" + authUrl);
					$
							.ajax({
								url : authUrl,
								dataType : 'JSONP',
								jsonp : 'callback',
								async : false,
								header : {
									'cache-control' : 'no-cache'
								},
								success : function(data, textStatus, jqXHR) {
									tip("上网成功");
									console.log("请求sb上网成功");
									// $.getJSON("/ms/log/wlan");
									// alert("请求sb上网成功");
									// imgLoad2();

									console.log(data);

								},
								error : function(xhr, status, error) {
									// console.log("请求sb上网失败");
									// imgLoad2();alert("请求sb上网失败");
									console.log(xhr)

								},
								complete : function(xhr) {
									// alert("请求sb上网完成");

									// console.log("请求sb上网完成");
									// console.log("complete");
									// setTimeout(function() { //应该把paytype ==
									// '1' 的判断提到前面来
									if (paytype == '1') {
										if (that.dqurl != '') {
											console.log(that.dqurl);
											// var url =
											// encodeURIComponent(that.dqurl);
											// globalParam["dqUrl"]=that.dqurl;
											mvc.href("#/buyTran");

										} else {
											var html = '<div class="row-9 center-text" >支付服务失败，请重新登录！</div>';
											Dialogue.modal(html, '25%',
													'180px', function() {
														that.showLoginPanel();
													}).showModal();
										}
									}
									imgLoad2();// 测试网络是否通
									// }, 1000);

								}
							});

					/**/

				} else {
					console.log("认证登陆失败:" /* + auth.resultCode */);
				}

			},
			auther : function(authData, paytype) {
				afterLoginFlag = true;

				tip("请稍等，正在网络放行");
				console.log("请稍等，正在网络放行");
				// 触发登录
				var that = this;
				if (authData) { // 其实可以不传authData
					var merchantId = getCookieValue("MS_merchantId");
					var deviceId = getCookieValue("MS_deviceId");
					var apMac = getCookieValue("ap_mac");
					var terMac = getCookieValue("terMac");

					if (merchantId == '' || deviceId == '' || apMac == ''
							|| terMac == '') {
						alert("认证失败，请重新连接设备！");
						return;
					} else {
						var authIndexUrl = '/ms/access/auth';
						$.ajax({
							url : authIndexUrl,
							data : authData,
							dataType : 'JSON',
							success : this.autherCallBack.Apply(this),
							error : function(xhr, status, error) {
							}
						});
					}
				}

			},

			addEventOfLogin : function() {
				var that = this;
				var deviceId = getCookieValue("MS_deviceId");
				var terMac = getCookieValue("MS_terMac");
				//alert();
				var merchantId = getCookieValue("MS_merchantId");
				var $username = $('input[name=username]');
				var $captcha = $('input[name=captcha]');
				var $loginmsg = $(".errorMsg");
				var cutdownTime = 60;
				// 自动填充手机号码
				// if(!StringUtil.isBlank(getCookieValue("MS_telephone")))
				// $username.val(getCookieValue("MS_telephone")||
				// getCookieValue("user_phone"));
				/**
				 * 登录系统
				 */
				$('#loginBtn').on(
						'click',
						function() {
							var self = $(this);
							var username = $username.val() || '';
							var captcha = $captcha.val() || '';
							if (username == '' || captcha == ''
									|| mobileStyle(username)
									|| captchaStyle(captcha)) {
								$loginmsg.text('请输入正确的帐号或验证码');
								return false;
							}
							// 存储此次登录的用户名
							setCookie("MS_telephone", username, 24, "/");
							loading.show();
							// 请求
							Protocol.request("user.login",
									{
										parameter : {
											'username' : username,
											'captcha' : captcha,
											/*'deviceId' : deviceId,
											'apMac' : getCookieValue('ap_mac'),
											'merchantId' : merchantId,
											'terMac' : terMac,
											'userMac' : terMac,
											'callback' : "callback"*/
										},
										success : that.afterLogin,
										failure : function(options, jqXHR,
												textStatus, errorThrown) {
											// $(".errorMsg").text("textStatus："
											// + textStatus+";errorThrown：" +
											// errorThrown+";readyState：" +
											// jqXHR.readyState+";responseText："
											// +
											// jqXHR.responseText+";status:"+jqXHR.status+";statusText:"+jqXHR.statusText);
											// $(".errorMsg").text("登录失败,请重新操作");
											var status = jqXHR.status || '0';
											var statusText = jqXHR.statusText
													|| '';
											$(".errorMsg").text(
													status + ":" + statusText);

											loading.hide();
										},
										complete : function(options, jqXHR,
												textStatus) {
											loading.hide();
										},
										scope : that
									});
						});
				/**
				 * 验证码
				 */
				$('.captcha').find('button').on('click', function() {
					$(".errorMsg").text('');
					var self = $(this);
					if (self.attr("disabled")) {
						return;
					}
					var username = $username.val() || '';
					if (username == '' || mobileStyle(username)) {
						$loginmsg.text("请输入正确的手机号");
						return false;
					}
					self.prop('disabled', true);
					self.text('发送中');
					var time = cutdownTime;
					var sI = setInterval(function() {
						time = time - 1;
						if (time !== 0) {
							self.text(time + '秒后重试');
						} else {
							window.clearInterval(sI);
							time = cutdownTime;
							self.text('重新获取');
							self.removeAttr("disabled");
						}
					}, 1000);
					Protocol.request("user.captcha", {
						parameter : {
							'mobile' : username,
							'type' : 'login'
						},
						success : function(options, result, textStatus, jqXHR) {
							if (result.r != 1 && result.msg) {
								$(".errorMsg").text(result.msg)
							}
						},
						complete : function(options, jqXHR, textStatus) {
							loading.hide();
						}
					});
				});

				// Service
				/**
				 * 用户输入去除错误信息
				 */
				$username.on("change", function() {
					$loginmsg.text('');
				});
				$captcha.on("change", function() {
					$loginmsg.text('');
				});
				/**
				 * 验证手机号格式
				 */
				function mobileStyle(str) {
					var re = /^1\d{10}$/;
					if (!re.test(str)) {
						return true;
					}
				}
				;
				/**
				 * 验证密码格式
				 */
				function captchaStyle(str) {
					var re = /^[0-9]{4}$/;
					if (!re.test(str)) {
						return true;
					}
				}
				;
				/**
				 * 获取uri参数
				 */
				function getParam(name) {
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
				}
				;
			},
			goIndex : function() {
				$(".loginWrap").hide();
				$(".indexWrap").show();
			},
			showLoginPanel : function() {
				/* mvc.href("#/login"); */

				$(".indexWrap").hide();
				$(".loginWrap").show();
				var $username = $('input[name=username]');
				var $captcha = $('input[name=captcha]');
				$username.val("");
				$captcha.val("");
				/*
				 * $("#MainContent").hide(); $("#LoginWrap").show();
				 */
			},

		});
;/**
 * 园区配置控制
 */
var ConfigController = EasyController.extend({
	selector: {
		imgdata: {}
	},
	request: function() {

	},
	carouselEditSearchRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == 1) {
			var that = this;
			var ret = result.data.topPicList;
			var html = "";
			for (var i in ret) {
				html += "<div class='row-2-5 left'><div class='row-9 img-upload-arround'>" +
					"<input class='carousel-edit-input row-2-3' type='file' style='display:none;''/>"+
					"<img class='row-10 img-upload' src='" +UPLOAD_PATH+''+ret[i].picpath +" 'alt='" + i +  "'><input id='picur" + i +
					"'type='hidden' value='"+ ret[i].picpath+"'/></div></div>";
			}
			if (ret.length < 4) {
				var num = i + 1;
				html += "<div class='row-2-5 left'><div class='row-9 img-upload-arround'>" +
					"<input class='carousel-edit-input row-2-3' type='file' style='display:none;'>" +
					"<img class='row-10 img-upload' src='"+IMG_PATH+"img/carousel/add.png' alt='" + num + "'><input id='picur" + num +
					"'style='display: none;'/></div></div>";
			}
			$("#carousel-edit").html(html);
			that.bindUpload();
			if(result.data.merchant){
			$('#introduce').text(result.data.merchant.remarks);
			}
		} else {

		}
		loading.hide();


	},
	//动态新增回调
	addActiveRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == 1) {
			this.activeSearch();
		} else {

		}
		loading.hide();
	},
	//动态删除回调
	delActiveRequest: function(options, result, textStatus, jqXHR) {

		if (result.r == 1) {
			this.activeSearch();
		} else {

		}
		loading.hide();
	},
	//保存配置回调
	saveConfigRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == 1) {
			this.activeSearch();
			tip("保存成功");
		} else {
			tip("保存失败");
		}
		loading.hide();
	},
	//动态查询回调
	searchActiveRequest: function(options, result, textStatus, jqXHR) {
		var that=this;
		if (result.r == 1) {
			//this._data.detail=result.data;
			var that = this;
			var ret = eval('(' + result.data + ')');
			var html = "";
			var size = ret.length;
			if(size>5){
				size=5;
			}
			for (var i = 0; i < size; i++) {
				html += "<div class='row-10  normal-text edit-margin ' name='activeDiv'>" +
					"<div class='row-9 clr-fix'><div class='row-3 left'>" + ret[i].noticeTitle +
					"</div><div class='row-7 left right-text'>"+ret[i].noticeText+
					"<input type='text' value='"+ret[i].id+"'style='display:none'></input>"+
				"<a href='javascript:void(0)' style='color: #2C8FFF;' name='delActive'>" +
				"删除</a></div></div></div><div class='row-10 bottomline'></div>";
			}
			$("#activeList").html(html);
		} else {

		}
		/**
		 * 动态删除
		 */
		$("a[name='delActive']").on("click", function() {

			//动态条目id
			var activeId = $(this).parent().find("input").val() || '';
			that.delActiveSend(activeId);
		});
		loading.hide();
	},
	addBlock: function() {
		var _this = this;
		var num = document.getElementById('carousel-edit').getElementsByTagName('img').length;
		if (num < 4) {
			if ($("#carousel-edit").find("img[src='img/carousel/add.png']").length > 0)
				return;
			num = num + 1;
			var html = $("<div class='row-2-5 left'><div class='row-9 img-upload-arround'>" +
				"<input class='carousel-edit-input row-2-3' type='file' style='display:none;'>" +
				"<img class='row-10 img-upload' src='"+IMG_PATH+"img/carousel/add.png' alt='" + num +
				"'><input id='picur" + num + "'style='display: none;' name='picurl'/></div></div>");
			$('#carousel-edit').append(html);
			_this.bindUpload();
		}
	},
	bindUpload: function() {
		var nowImg = null;
		var _this = this;
		$(".img-upload").unbind();
		$('.carousel-edit-input').unbind(); //移除click
		$(".carousel-edit-input").change(function() {
			console.log("imgDom:" + nowImg);
			var imageUtil = new zImageUtil({
				imgDom: nowImg,
				postUrl: PATH+"/image/upload.json",
				maxWidth: 633,
				maxHeight: 300,
				callback: function(data) {
					_this.addBlock();
					//TODO
					$(nowImg).attr("src",UPLOAD_PATH+data.data);
					$(nowImg).parent().find('input[type=hidden]').val(data.data);
				}
			});
			imageUtil.fileChange(this);
		});
		$(".img-upload").click(function() {
			nowImg = this;
			$(this).parent().find("input").trigger("click");
		});
	},
	addEventListener: function() {
		this.getConfig();
		this.bindUpload();
		var id = this.params["id"] || '1'; // 园区id
		var that = this;
		this.activeSearch();
		$("#addActive").on("click", function() {
			var activeTitle = $("#activeTitle").val() || '';
			var activeMsg = $("#activeMsg").val() || '';
			if(activeTitle==''||activeMsg==''){
				tip("动态不可为空");
				return;
			}
			if($("div[name='activeDiv']").length>=5){
				tip("动态上限为5条");
				return;
			}
			//保存动态
			Protocol.request("mng.addActive", {
				parameter: {
					'notice_title': activeTitle,
					'notice_text': activeMsg
				},
				scope:that,
				success: that.addActiveRequest
			});
		});

		/**
		 * 保存配置信息
		 */
		$("#saveConfig").on("click", function() {
			$(".errorMsg").text("");
			//1.图片url获取
			var src = document.getElementById('carousel-edit').getElementsByTagName('img');
			if (src.length < 2) {
				$(".errorMsg").text("请至少上传一张轮询图");
				return;
			}
			var pic1="";
			var pic2="";
			var pic3="";
			var pic4="";
			var hostport = document.location.host;
			if($("#picur0")){
				 pic1=$("#picur0").val();

			}


		    if($("#picur1")){
				 pic2=$("#picur1").val();
			}
			if($("#picur2")){
				 pic3=$("#picur2").val();
			}
			if($("#picur3")){
				pic4=$("#picur3").val();
			}
			if($(".img-upload")[0].src1){
			    pic1 = $(".img-upload")[0].src1
		    }
			if (src.length > 1 && $(".img-upload")[1].src1) {
			    pic2 = $(".img-upload")[1].src1 || "";
			 }
			if (src.length > 2 && $(".img-upload")[2].src1) {
				pic3 = $(".img-upload")[2].src1|| "";
			}
			if (src.length > 3 && $(".img-upload")[3].src1) {
				pic4 = $(".img-upload")[3].src1 || "";
			}
			var introduce = $("#introduce").val();
			Protocol.request("mng.saveConfig", {
				parameter: {
                   'pic_1' : pic1||"",
                   'pic_2' : pic2||"",
                   'pic_3' : pic3||"",
                   'pic_4' : pic4||"",
                   'remarks' : introduce
				},
				success: that.saveConfigRequest,
				scope:that
			});
		});

		//Service
		/**
		 * 字数限制
		 */
		$("#introduce").on("keyup", function() {
			limitText($(this), 50, $("#introLimit"));
		});
		$("#introduce").on("keydown", function() {
			limitText($(this), 50, $("#introLimit"));
		});
		$("#introduce").on("change", function() {
			limitText($(this), 50, $("#introLimit"));
		});
		$("#activeMsg").on("keyup", function() {
			limitText($(this), 100, $("#activeLimit"));
		});
		$("#activeMsg").on("keydown", function() {
			limitText($(this), 100, $("#activeLimit"));
		});
		$("#activeMsg").on("change", function() {
			limitText($(this), 100, $("#activeLimit"));
		});
		/**
		 *
		 * @param {Object} field  输入框$对象 必须
		 * @param {Object} max    最大长度    必须
		 * @param {Object} num    限制提示$对象(div)
		 */
		function limitText(field, max, num) {
			var v = field.val() || '';
			if (v.length > max) {
				var n = v.substr(0, max);
				field.val(n);
			} else if (num) {
				var l = max - v.length;
				num.html(l);
			}
		}
//	     function activeSearch() {
//			Protocol.request("mng.searchActive", {
//				success: that.searchActiveRequest,
//				complete: function(options, jqXHR, textStatus) {
//					loading.hide();
//				}
//			});
//		}
		var roleType = getCookieValue('MS_roleType'); //用户权限 2为管理员 1为普通用户
		var localtype = getCookieValue('localtype');//用户本地类型 3为企业管理员 4为园区代理
		console.log(localtype);
		/**
		 * 根据权限开启管理功能
		 */
		if (roleType == 2) {
			$("#mngPower").show();
		}else if(localtype==3){
			$('#enterprise').show();
		}
	},
 		activeSearch: function () {
 			var that=this;
			Protocol.request("mng.searchActive", {
				success: this.searchActiveRequest,
				complete: function(options, jqXHR, textStatus) {
					loading.hide();
				},
				scope:that
			});
		},
	 getConfig: function() {
	 	var that=this;
			Protocol.request("mng.carouselEditSearchRequest", {
				parameter: {},
				success: this.carouselEditSearchRequest,
				complete: function(options, jqXHR, textStatus) {
					loading.hide();
			    },
			    scope:that
			});
	},
    delActiveSend:function(activeId){
    	var that=this;
    			Protocol.request("mng.delActive", {
				parameter: {
					'id': activeId
				},
				success: this.delActiveRequest,
				scope:that
			});
    },
     getImgUrl:function(){

     }

});;/**
 * 管理员资料控制
 */
var MngInfoController = EasyController.extend({
	selector: {

	},
	request: function() {},
	//修改园区信息回调
	mngInfoRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == 1) {
			var html = '<div class="row-9 center-text" >园区信息修改成功!</div>' ;
			Dialogue.modal(html,'25%','180px',function(){mvc.href('#/');}).showModal();
		} else if(result.msg){
            var html = '<div class="row-9 center-text">'+result.msg +'</div>' ;
			Dialogue.modal(html,'25%','180px').showModal();
		}
	},
	//管理员信息回调
	mngCenterRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == 1) {
			var faceInfo = result.data.faceInfo || '';
			var faceInfoShow;
			var telephone = result.data.telphone || '';
			var userNick = result.data.userNick || '';
			var sex = result.data.sex || '';
			var birthdayStr = result.data.birthdayStr || '';
			var address = result.data.address || '';
			var merchantName = result.data.merchantName || '';
			var userName = result.data.userName || '';
			if (faceInfo == '') {
				faceInfo ='img/messageboard/avatar.png';
				faceInfoShow=IMG_PATH+''+faceInfo;
			}else{
			    faceInfoShow=UPLOAD_PATH+''+faceInfo;
			}
			$("#faceInfo").attr("src",faceInfoShow);
			$("#saveSrc").val(faceInfo); //保存地址
			$("#telephone").val(telephone);
			$("#userNick").val(userNick);
			$("#birthdayStr").val(birthdayStr);
			$("#address").val(address);
			$("#merchantName").val(merchantName);
			$("#userName").val(userName);
			switch (sex) {
				//				case '0':
				//					$("#secrect").attr("checked", 'true');
				//					break;
				case '1':
					$("#male").attr("checked", 'true');
					break;
				case '2':
					$("#female").attr("checked", 'true');
					break;
			}
		}
	},
	addEventListener: function() {
		var currYear = (new Date()).getFullYear();
		var opt = {};
		opt.date = {
			preset: 'date'
		};
		opt.datetime = {
			preset: 'datetime'
		};
		opt.time = {
			preset: 'time'
		};
		opt.default = {
			theme: 'android-ics light', //皮肤样式
			display: 'modal', //显示方式
			mode: 'scroller', //日期选择模式
			dateFormat: 'yyyy-mm-dd',
			lang: 'zh',
			showNow: true,
			nowText: "今天",
			startYear: currYear - 50, //开始年份
			endYear: currYear + 10 //结束年份
		};

		$("#birthdayStr").mobiscroll($.extend(opt['date'], opt['default']));


		this.mngCenter();
		/**
		 * 头像上传
		 */
		this.bindUpload();
		/**
		 *回退确认
		 */
		$("#historyBack").on("click", function() {
			if (confirm("信息未保存，确认放弃修改？")) {
				history.go(-1);
			}
		});
		var that = this;



		/**
		 * 保存
		 */
		$("#save").on("click", function() {
			var $errorMsg = $(".errorMsg");
			var logo = $("#saveSrc").val() || '';
			var nick = $("#userNick").val() || '';
			var userName = $("#userName").val() || '';
			var address = $("#address").val() || '';
			var telephone = $("#telephone").val() || '';
			var sex = -1;
			if ($("#female").is(':checked')) {
				sex = 2;
			} else if ($("#male").is(':checked')) {
				sex = 1;
			}
			var birthday = $("#birthdayStr").val().trim() || '';
			if (logo == '') {
				$errorMsg.text("请上传头像");
				return;
			}
			if (!telephone.match(/^1\d{10}$/)) {
				$errorMsg.text("请输入正确的手机号");
				return;
			}
			if (!address.match(/^[0-9a-zA-Z(\u4e00-\u9fa5)]{1,50}$/)) {
				$errorMsg.text("请填写地址 1-50位中文、字母或数字");
				return;
			}
			if (!nick.match(/^[0-9a-zA-Z(\u4e00-\u9fa5)]{1,8}$/)) {
				$errorMsg.text("请填写昵称 1-8位中文、字母或数字");
				return;
			}
			if (birthday == '') {
				$errorMsg.text("请选择生日");
				return;
			}
			var result = birthday.match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/);
			if (!result) {
				$errorMsg.text("请填写生日,例如：2016-01-01");
				return;
			}
			if (!userName.match(/^[a-zA-Z(\u4e00-\u9fa5)]{1,8}$/)) {
				$errorMsg.text("请填写姓名 1-8位中文或字母");
				return;
			}
			if (sex == -1) {
				$errorMsg.text("请选择性别");
				return;
			}

			loading.show();
			//保存园区资料
			Protocol.request("mng.mngInfo", {
				parameter: {
					'logo': logo,
					'nick': nick,
					'sex': sex,
					'birthday': birthday,
					'userName': userName,
					'address': address,
					'telephone': telephone
				},
				success: that.mngInfoRequest,
				complete: function(options, jqXHR, textStatus) {
					loading.hide();
				}
			});


		});

		//Service
		/**
		 * checkbox联动
		 */
		$('.info-sex').find('input[type=checkbox]').on('change', function() {
			$('.info-sex').find('input[type=checkbox]').not($(this)).each(function() {
				if ($(this).is(':checked')) {
					$(this).removeAttr('checked');
				}
			});
		});


	},
	/**
	 * 园区信息
	 */
	mngCenter: function() {
		Protocol.request("mng.center", {
			parameter: {
				"merchantId": getCookieValue("MS_merchantId")
			},
			success: this.mngCenterRequest,
			failure: function(options, jqXHR, textStatus, errorThrown) {
				var faceInfo = IMG_PATH+'img/messageboard/avatar.png';
				$("#faceInfo").attr("src", faceInfo);
			}
		});
	},
	/**
	 * 上传事件绑定
	 */
	bindUpload: function() {
		var nowImg = document.getElementById("faceInfo");
		var _this = this;
		$('#imgUpload').unbind(); //移除click
		$("#faceInfo").unbind(); //头像点击事件解绑
		$("#imgUpload").change(function() {
			console.log("imgDom:" + nowImg);
			var imageUtil = new zImageUtil({
				imgDom: nowImg,
				postUrl: "/image/upload.json",
				maxWidth: 633,
				maxHeight: 300,
				callback: function(data) {
					if (data.r == 1) {
						$("#saveSrc").val('/'+data.data);
						tip("上传成功");
					}
				}
			});
			imageUtil.fileChange(this);
		});

	},


});;/**
 * 园区统计控制
 */
var StatiscialController = EasyController.extend({
	selector: {

	},
	request: function() {
		var id = this.params["id"] || '1';
		var that = this;

//	},
	//		加载统计信息
			Protocol.request("mng.statiscial", {
				success: this.statiscialRequest
			});
			this._data = {
				//渲染数据
			}
		},
	//统计信息回调
	statiscialRequest: function(options, result, textStatus, jqXHR) {
		var that = this;
		var rechargetotalnum = 0;
		var rechargeavgnum = 0;
		var rechargeusernum = 0;
		var mer_user_num = 0;
		var money_pay_all = 0;
		if (result.r == 1 && result.data) {
			mer_user_num = result.data.mer_user_num;
			money_pay_all = result.data.money_pay_all;
			if (result.data.mer_stat) {
				var data = result.data.mer_stat;
				if (data.rechargetotalnum) {
					rechargetotalnum = data.rechargetotalnum;
				}
				if (data.rechargeavgnum) {
					rechargeavgnum = data.rechargeavgnum;
				}
				if (data.rechargeusernum) {
					rechargeusernum = data.rechargeusernum;
				}
			}
		}
		$('#rechargetotalnum').html(rechargetotalnum);
		$('#rechargeavgnum').html(parseFloat(rechargeavgnum).toFixed(2));
		$('#rechargeusernum').html(rechargeusernum);
		$('#mer_user_num').html(mer_user_num);
		$('#money_pay_all').text(money_pay_all);
	},

	//付费用户明细回调
	userPayDetailRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == 1) {
			//this._data.detail=result.data;
			var that = this;
			var ret = eval('(' + result.data + ')');
			var html = "";
			if(ret.length==0){
				$('#more').html("无更多账号");
				return;
			}
			for (var i = 0; i < ret.length; i++) {
				var time =  new Date(parseInt(ret[i].createDate)).Format("yyyy/MM/dd hh:mm:ss");
//				var time = new Date(parseInt(ret[i].createDate)).toLocaleString().substr(0, 17);
				html += "<div class='row-10 visit-border-top'>" +
					"<div class='row-9 clr-fix small-text light-gray-text'>" +
					"<span></span><span>" + time +
					"</span></div><div class='row-9 clr-fix'><div class='row-5 left'><div class='normal-text'>" +
					(ret[i].rechargeAccount||"用户") + "</div></div><div class='row-5 left'><div class='row-9 normal-text right-text' id=''>￥" +
					ret[i].payNum + "</div></div></div>";
			}
			$("#payinfo-visit-list").append(html);
		} else {

		}
		loading.hide();
	},

	addEventListener: function() {


		var pagenum = 1;
		var pagesize = 10;
		var that = this;
		var id = that.params["id"] || '1'; //园区id
		getDetail(id, pagenum, pagesize);
		//点击加载更多
		$("#more").on("click", function() {
			pagenum++;
			console.log(pagenum);
			getDetail(id, pagenum, pagesize);
		});
		$("#back").on("click", function() {
	     	mvc.href("#/")
		});
		function getDetail(id, curpage, pagesize) {
			Protocol.request("mng.userPayDetail", {
				parameter: {
					'merchantid': id,
					'pagenum': pagenum,
					'pagesize': pagesize
				},
				success: that.userPayDetailRequest,
				complete: function(options, jqXHR, textStatus) {
					loading.hide();
				}
			});
		}

	},


});;/**
 * 用户个人中心控制
 */
var UserCenterController = EasyController.extend({

	selector: {

	},

	request: function() {},
	//个人中心回调
	userCenterRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == AJAX_SUCC) {
			var faceInfo = result.data.face || '';
			var telephone = result.data.phone || '';
			if (faceInfo == '') {
				faceInfo = IMG_PATH + 'img/messageboard/avatar.png';
			} else {
				faceInfo = UPLOAD_PATH + '' + faceInfo;
			}
			$("#faceInfo").attr("src", faceInfo);
			$("#telephone").html(telephone);
		}
	},
	//管理员中心回调
	mngCenterRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == AJAX_SUCC) {
			var faceInfo = result.data.faceInfo || '';
			var telephone = result.data.telphone || '';
			if (faceInfo == '') {
				faceInfo = IMG_PATH + 'img/messageboard/avatar.png';
			} else {
				faceInfo = UPLOAD_PATH + '' + faceInfo;
			}
			$("#faceInfo").attr("src", faceInfo);
			$("#telephone").html(telephone);
		}
	},
	//消费记录回调
	payRecordsRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == AJAX_SUCC) {

			var data = eval("(" + result.data + ")");
			if (data) {
				if (data.length < 8&&data.length>0) {
					$("#more").html("")
				}
				var _html = '';
				for (var i in data) {
					_html += '<div class="row-10  normal-text"><div class="row-9 clr-fix"><div style="padding-top: 0.7rem;">' +
						'<span class="little-text minspan " style="font-size: 1.2rem;color:#999">' + new Date(data[i].createDate).Format("yyyy/MM/dd hh:mm:ss") + '</span>' +
						'</div><div style="padding: 0.7rem 0;"><div class="row-5 left">上网时长：' + (data[i].addDay || '') + '天</div>' +
						'<div class="row-5 left right-text">￥' + (data[i].payNum || '0') + '</div></div></div></div><div class="row-10  bottomline"></div>';
				}
			}else{
				$("#more").html('暂无消费记录');
			}
			$("#payRecords").append(_html);

		}
	},
	//套餐信息回调
	getSetMealRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == AJAX_SUCC && result.data) {
			var packageInfo = result.data.packageInfo || '';
			var bDate = Number(result.data.beginDate) || new Date().getTime();
			var eDate = Number(result.data.endDate) || new Date().getTime();
			//			var beginDate = new Date(bDate).Format("yyyy/MM/dd hh:mm:ss");
			var endDate = new Date(eDate).Format("yyyy/MM/dd hh:mm:ss");
			$("#packageInfo").text(packageInfo);
			//			$("#beginDate").text(beginDate);
			$("#endDate").text(endDate);
		}
	},

	addEventListener: function() {
		var roleType = getCookieValue("MS_roleType"); //用户权限 2为管理员 1为普通用户
		var curpage = 1;
		var pagesize = 8;
		if (roleType == 2) {
			this.mngCenter();
		} else {
			this.userCenter();
		}
		this.getSetMeal();
		this.getRecords(curpage, pagesize);
		var that = this;
		/**
		 * 点击加载更多
		 */
		$("#more").on("click", function() {
			curpage++;
			console.log(curpage);
			that.getRecords(curpage, pagesize);
		});

		/**
		 * 根据权限显示不同资料详情
		 */
		$("#detailInfo").on("click", function() { //这里用click 保证不会同时触发头像上传的click

			if (roleType == 2) {
				mvc.href("#/mngInfo");
			} else {
				mvc.href("#/userInfo");
			}
		});
	},
	/**
	 * 获取中心信息
	 */
	userCenter: function() {
		Protocol.request("user.center", {
			parameter: {},
			success: this.userCenterRequest,
			failure: function(options, jqXHR, textStatus, errorThrown) {
				var faceInfo = IMG_PATH + 'img/messageboard/avatar.png';
				$("#faceInfo").attr("src", faceInfo);
			}
		});
	},
	mngCenter: function() {
		Protocol.request("mng.center", {
			parameter: {},
			success: this.mngCenterRequest,
			failure: function(options, jqXHR, textStatus, errorThrown) {
				var faceInfo = IMG_PATH + 'img/messageboard/avatar.png';
				$("#faceInfo").attr("src", faceInfo);
			}
		});
	},
	/**
	 * 获取消费记录
	 */
	getRecords: function(curpage, pagesize) {
		Protocol.request("user.payRecords", {
			parameter: {
				'curpage': curpage,
				'pagesize': pagesize
			},
			success: this.payRecordsRequest
		});
	},
	/**
	 * 获取套餐信息
	 */
	getSetMeal: function() {
		Protocol.request("user.setMeal", {
			parameter: {

			},
			success: this.getSetMealRequest
		});
	}

});;/**
 * 用户资料控制
 */
var UserInfoController = EasyController.extend({
	selector: {

	},
	request: function() {

	},
	//个人信息回调
	userCenterRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == AJAX_SUCC) {
			var faceInfo = result.data.face || '';
			var faceInfoShow;
			var telephone = result.data.phone || '';
			var userNick = result.data.nick || '';
			var sex = result.data.sex || '';
			var birthday = result.data.birthday || '';
			if(result.data.birthday >0){
				birthday = new Date(birthday).format("yyyy-MM-dd")
			}

			var address = result.data.address || '';
			if (faceInfo == '') {
				faceInfo = 'img/messageboard/avatar.png';
				faceInfoShow = IMG_PATH + '' + faceInfo;
			} else {
				faceInfoShow = UPLOAD_PATH + '' + faceInfo;
			}
			$("#faceInfo").attr("src", faceInfoShow);
			$("#saveSrc").val(faceInfo); //保存地址
			$("#telephone").val(telephone);
			$("#userNick").val(userNick);
			$("#birthday").val(birthday);
			$("#address").val(address);
			switch (sex) {
				//				case '0':
				//					$("#secrect").attr("checked", 'true');
				//					break;
				case 1:
					$("#male").attr("checked", 'true');
					break;
				case 2:
					$("#female").attr("checked", 'true');
					break;
			}
		}
	},
	//用户信息修改回调
	userInfoRequest: function(options, result, textStatus, jqXHR) {
		if (result.r == AJAX_SUCC) {
			var html = '<div class="row-9 center-text" >个人信息修改成功!</div>';
			Dialogue.modal(html, '25%', '180px', function() {
				mvc.href('#/');
			}).showModal();
		} else if(result.msg){
			var html = '<div class="row-9 center-text">' + result.msg + '</div>';
			Dialogue.modal(html, '25%', '180px').showModal();
		}
	},
	addEventListener: function() {
		var currYear = (new Date()).getFullYear();
		var opt = {};
		opt.date = {
			preset: 'date'
		};
		opt.datetime = {
			preset: 'datetime'
		};
		opt.time = {
			preset: 'time'
		};
		opt.default = {
			theme: 'android-ics light', //皮肤样式
			display: 'modal', //显示方式
			mode: 'scroller', //日期选择模式
			dateFormat: 'yyyy-mm-dd',
			lang: 'zh',
			showNow: true,
			nowText: "今天",
			startYear: currYear - 50, //开始年份
			endYear: currYear + 10 //结束年份
		};

		$("#birthday").mobiscroll($.extend(opt['date'], opt['default']));




		this.userCenter();
		/**
		 * 头像上传
		 */
		this.bindUpload();
		var that = this;

		/**
		 *回退确认
		 */
		$("#historyBack").on("click", function() {
			if (confirm("信息未保存，确认放弃修改？")) {
				history.go(-1);
			}
		});
		/**
		 * 保存
		 */
		$("#save").on("click", function() {
			var $errorMsg = $(".errorMsg");
			var touxiang = $("#saveSrc").val() || '';
			var nick = $("#userNick").val().trim() || '';
			var sex = -1;
			if ($("#female").is(':checked')) {
				sex = 2;
			} else if ($("#male").is(':checked')) {
				sex = 1;
			}
			var birthday = $("#birthday").val().trim() || '';
			var address = $("#address").val().trim() || '';
			if (touxiang == '') {
				$errorMsg.text("请上传头像");
				return;
			}
			if (!nick.match(/^[0-9a-zA-Z(\u4e00-\u9fa5)]{1,8}$/)) {
				$errorMsg.text("请填写昵称 1-8位中文、字母或数字");
				return;
			}
			if (sex == -1) {
				$errorMsg.text("请选择性别");
				return;
			}
			if (birthday == '') {
				$errorMsg.text("请选择生日");
				return;
			}
			var result = birthday.match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/);
			if (!result) {
				$errorMsg.text("请填写生日,例如：2016-01-01");
				return;
			}
			if (!address.match(/^[0-9a-zA-Z(\u4e00-\u9fa5)]{1,50}$/)) {
				$errorMsg.text("请填写地址 1-50位中文、字母或数字");
				return;
			}
			loading.show();
			//保存个人资料
			Protocol.request("user.userInfo", {
				parameter: {
					'face': touxiang,
					'nick': nick,
					'sex': sex,
					'birthday': birthday,
					'address': address
				},
				success: that.userInfoRequest,
				complete: function(options, jqXHR, textStatus) {
					loading.hide();
				}
			});


		});

		//Service
		/**
		 * checkbox联动
		 */
		$('.info-sex').find('input[type=checkbox]').on('change', function() {
			$('.info-sex').find('input[type=checkbox]').not($(this)).each(function() {
				if ($(this).is(':checked')) {
					$(this).removeAttr('checked');
				}
			});
		});


	},
	/**
	 * 个人信息
	 */
	userCenter: function() {
		Protocol.request("user.center", {
			parameter: {},
			success: this.userCenterRequest,
			failure: function(options, jqXHR, textStatus, errorThrown) {
				var faceInfo = IMG_PATH + 'img/messageboard/avatar.png';
				$("#faceInfo").attr("src", faceInfo);
			}
		});
	},
	/**
	 * 上传事件绑定
	 */
	bindUpload: function() {
		var nowImg = document.getElementById("faceInfo");
		var _this = this;
		$("#faceInfo").unbind(); //头像点击事件解绑
		$('#imgUpload').unbind(); //移除click
		$("#imgUpload").change(function() {
			console.log("imgDom:" + nowImg);
			var imageUtil = new zImageUtil({
				imgDom: nowImg,
				postUrl: "/image/upload.json",
				maxWidth: 633,
				maxHeight: 300,
				callback: function(data) {
					if (data.r == AJAX_SUCC) {
						$("#saveSrc").val('/'+data.data);

						tip("上传成功");
					}
				}

			});
			imageUtil.fileChange(this);
		});

	},


});;mvc.template = '<div><script type="text/template" id="buy_tran"> 	<div style="font-size: 24px;"> 			正在请求购买页面,请稍等... 		</div> 		<a style="color:blue;text-decoration:underline" target="_blank" id="dqurllink">如果长时间停留此页，您可以切换到2g网络下点击继续购买</a> </script><script type="text/template" id="index_tpl"> 	 	     <header class="loginWrap" style="display:none;" >         <a href="#/">             <div class="loginlogo row-10 back-blue center-text">                 <img class="row-5-2 loginlogo-icon" src="http://msp-img.51awifi.com/V1/img/header/wifiLogo.png" alt="logo">                 <span class="logoWord">爱WiFi欢迎您</span>             </div>         </a>     </header>     <div  style="display:none;" class="loginWrap container regcontainer" style="margin:0">         <div class="row-9 loginErr">             <span class="errorMsg"></span>         </div>         <div class="row-9">             <input type="text" class="logininput" name="username" placeholder="输入手机号" maxlength="11" style="font-size: 1.5rem;">         </div>         <!--<div class="row-9">             <input type="password" class="logininput" name="password" placeholder="输入密码" style="font-size: 1.5rem;" maxlength="20">         </div>-->         <div class="row-9 captcha">             <input class="row-4-7" type="text" placeholder="输入验证码" name="captcha" maxlength="4" style="font-size: 1.5rem;">             <button class="row-4-7 right clr-fix back-gray gray-text" style="font-size: 1.5rem;">获取验证码</button>         </div>         <!--<div class="row-9">             <a href="#/resetPwd"><span class="right small-text light-gray-text forget">忘记密码</span></a>         </div>-->         <div class="row-9 loginbutton">             <button class="back-orange" id="loginBtn" style="color: #fff;font-size: 1.5rem;">登录</button>             <!--<div class="row-4-7 left">                 <a href="#/register">                     <button class="back-gray gray-text" style="color: #666;font-size: 1.5rem;">注册免费上网</button>                 </a>             </div>             <div class="row-4-7 right">                 <button class="back-orange" id="login" style="color: #fff;font-size: 1.5rem;">登录</button>             </div>-->         </div>     </div>              <header class="indexWrap header back-blue">         <nav>             <a id="login" style="cursor:pointer">                 <div id="wifiBtn" class="header-left center-text row-3 left small-text">                     <img class="header-left-img" src="http://msp-img.51awifi.com/V1/img/header/wifi.png">                     <span class="header-left-span right-text" id="linkMsg">请连网</span>                 </div>             </a>             <div class="center-text row-4 left normal-text" style="white-space:nowrap;text-overflow:ellipsis;overflow: hidden;" id="merchantname">&nbsp;&nbsp;</div>             <a href="#/userCenter" id="userCenter">                 <div class="header-right center-text row-3 left small-text">                     <!--<span class="right-text">园区资料</span>-->                     <img class="header-right-img right" src="http://msp-img.51awifi.com/V1/img/header/information.png" >                 </div>             </a>         </nav>     </header> 	<div  class="indexWrap container"> 		<div class="row-10 activeDiv" style="background-image: url(http://msp-img.51awifi.com/V1/img/index/activeBack.png);"> 			<div class=" text-indent row-8 left small-text" style="height: 30px;line-height: 30px;"> 				<ul id="activeInfo" style="margin: 0;padding:0;list-style: none;"> 					<!--<li >动态: <span>恭喜AWIFI园区开通 !</span></li> 					<li>动态: <span>全场免费！</span></li> 					<li>动态:<span>免费是假的！</span></li> 					<li>动态: <span>当然要收费！</span></li> 					<li>动态:<span>其实真不贵！</span></li>-->  					<li id="activeInfoLi"> 						<!--<marquee direction=left> 							动态: <span>恭喜AWIFI园区开通 !</span> 							&nbsp; &nbsp;&nbsp;动态: <span>全场免费！</span> 							&nbsp;&nbsp;&nbsp;动态:<span>免费是假的！</span> 							&nbsp;&nbsp;&nbsp;动态: <span>当然要收费！</span> 							&nbsp;&nbsp;&nbsp; 动态:<span>其实真不贵！</span> 						</marquee>-->  					</li> 				</ul>  			</div> 			<div class="right-text row-2 left small-text" style="height: 30px;line-height: 30px;" id="closeActive"> 				<div style="height: 20px;line-height: 20px;margin-top: 3px;"> 					<img src="http://msp-img.51awifi.com/V1/img/index/activeClose.png" style="width:20px;height:20px;padding-right: 1rem;vertical-align:middle; " /> 				</div> 			</div> 		</div> 		<div class="carousel clr-fix"> 			<ul class="carousel-ul" id="carousel-ul"> 				<!--<li class="carousel-ul-li" id="pic_1"> 					<a href="javascript:void(0)"><img class="carousel-img row-10" src="img/carousel/ad1.png" alt="1" id="img"></a> 				</li> 				<li class="carousel-ul-li" id="pic_2"> 					<a href="javascript:void(0)"><img class="carousel-img row-10" src="img/carousel/ad2.png" alt="2"></a> 				</li> 				<li class="carousel-ul-li" id="pic_3"> 					<a href="javascript:void(0)"><img class="carousel-img row-10" src="img/carousel/ad3.png" alt="3"></a> 				</li> 				<li class="carousel-ul-li" id="pic_4"> 					<a href="javascript:void(0)"><img class="carousel-img row-10" src="img/carousel/ad4.png" alt="4"></a> 				</li>--> 			</ul> 		</div>  		<!-- app --> 		<div class="app clr-fix"> 			<div class="row-2 left center-text"> 				<div class="app-icon row-8"> 					<a href="javascript:viod(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/lunch.png"></a> 					<div class="small-text row-10">点餐</div> 				</div> 			</div>  			<div class="row-2 left center-text"> 				<div class="app-icon row-8"> 					<a href="javascript:viod(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/services.png"></a> 					<div class="small-text row-10">服务</div> 				</div> 			</div>  			<div class="row-2 left center-text"> 				<div class="app-icon row-8"> 					<a href="javascript:viod(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/order.png"></a> 					<div class="small-text row-10">买单</div> 				</div> 			</div>  			<div class="row-2 left center-text"> 				<div class="app-icon row-8"> 					<a href="javascript:viod(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/cup.png"></a> 					<div class="small-text row-10">续杯</div> 				</div> 			</div>  			<div class="row-2 left center-text"> 				<div class="app-icon row-8"> 					<a href="javascript:viod(0)"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/share.png"></a> 					<div class="small-text row-10">分享</div> 				</div> 			</div> 		</div> 		<div class="bottomline"></div> 		<div class="row-10" id="fastLogin" style="display:none"> 			<div class="row-9 " style="padding: 1rem 0;min-height: 60px;"> 				<div class="row-2 left" > 					<img src="http://msp-img.51awifi.com/V1/img/index/shalouon.png" style="width:4rem" /> 				</div> 				<div class="row-4 left small-text" > 					<div> 						当前账号  					</div> 					<div ><span id="fastLoginAccount" ></span><a style="text-decoration:underline;color:blue;" id="switch_account">切换</a></div> 				</div> 				<div class="row-4 left right-text" > 					<div class="row-9">  						<button type="button" id="fastLoginBtn" class="back-orange" style="color: #fff;font-size: 1.5rem;">一键登录</button> 						 					</div> 				</div> 			</div> 		</div> 		<div class="row-10" id="getFreePkgWrap" style="display:none" > 			<div class="row-9 " style="padding: 1rem 0;min-height: 60px;"> 				 				 					<div class="row-9">  						<button id="getFreeBtn" type="button" class="back-orange" style="color: #fff;font-size: 1.5rem;display: none;">领取免费包</button> 						 					</div> 				 			</div> 		</div> 		<div class="row-10" id="buydivwrap"> 			<div class="row-9 " style="padding: 1rem 0;min-height: 60px;"> 				<div class="row-2 left" id="shalou"> 					<img src="http://msp-img.51awifi.com/V1/img/index/shalouon.png" style="width:4rem" /> 				</div> 				<div class="row-4 left small-text" id="leftDiv"> 					<div id="leftTime"> 						您暂时无法上网 					</div> 					<div class="" id="leftTimeTitle">点击请连网登录</div> 				</div> 				<div class="row-4 left right-text" id="buyDiv"> 					<div class="row-9">  						<button id="buy" type="button" class="back-orange" style="color: #fff;font-size: 1.5rem;">时长购买</button> 						 					</div> 				</div> 			</div> 		</div>  		<div class="title row-10"> 			<div class="row-9"> 				<span class="title-text small-text gray-text">园区介绍</span> 			</div> 		</div> 		<div class="information small-text"> 			<div class="information-text" id="remarks"></div> 		</div> 		 		<div class="title row-10">             <div class="row-9">                 <a href="help.html" class="title-text small-text gray-text">帮助文档</a>             </div>         </div>         <div class="information small-text">             <div class="information-text" id="remarks">             <a style="color:blue;text-decoration:underline" href="help.html" >爱WiFi园区项目FAQ（V1.0）.txt</a>             </div>         </div> 	</div>  	<footer class="row-10 footer light-gray-text">  		<div class="row-10 center-text footer-top foot-text"> 			<span class="foot-text">本系统由中国电信爱WiFi运营中心提供支持</span> 		</div> 		<div class="row-10 center-text foot-text" style="margin-bottom: 1rem;" id="accUname"> 			 		</div> 		 	</footer> 	<!--根据角色显示与否--> 	<div class="nav row-10" style="display:none" id="mngPower"> 		<a href="#/"> 			<div class="left row-2-5 center-text"> 				<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/1.png"></div> 				<div class="nav-title small-text">首页</div> 			</div> 		</a> 		<a href="#/config"> 			<div class="left row-2-5 center-text"> 				<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/2.png"></div> 				<div class="nav-title small-text">配置</div> 			</div> 		</a> 		<a href="#/statiscial"> 			<div class="left row-2-5 center-text"> 				<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/3.png"></div> 				<div class="nav-title small-text">统计</div> 			</div> 		</a> 		 		<a href="userManager.html"> 			<div class="left row-2-5 center-text"> 				<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/3.png"></div> 				<div class="nav-title small-text">管理</div> 			</div> 		</a> 		 	</div> 	     <div class="nav row-10" style="display:none;" id="enterprise">         <a href="#/">             <div class="left row-5 center-text">                 <div class="nav-img"><img src="img/nav/1.png"></div>                 <div class="nav-title small-text">首页</div>             </div>         </a>         <a href="#/config">             <div class="left row-5 center-text">                 <div class="nav-img"><img src="img/nav/2.png"></div>                 <div class="nav-title small-text">配置</div>             </div>         </a>     </div>  	<!--<div class="mask"></div> 	<div class="modal" style="top:30%"> 		<div class="row-9 center-text " id="timeMsg" style="margin:40px auto 5px auto;font-size: 1.5rem;">您的上网时长已不足一小时</div> 		<div class="row-9 center-text " style="margin-bottom:20px;font-size: 1.5rem;" id="timeMsgtwo">请尽快购买时长</div> 		<div class="row-6 center-text"> 			<button class="back-orange " style="color: #fff;font-size: 1.5rem;" id="sureMsg">确定</button> 		</div> 	</div>-->  </script><script type="text/template" id="config_tpl"> 	<header class="header back-blue"> 			<nav> 				<a href="#/"> 					<div class="header-left center-text row-2-5 left small-text"> 						<img class="header-left-img" src="http://msp-img.51awifi.com/V1/img/header/back.png"> 					</div> 				</a> 				<div class="center-text row-5 left normal-text">配置</div> 			</nav> 		</header>  		<div class="container">  			<div class="title text-indent"> 				<span class="title-text small-text gray-text">广告位添加</span> 			</div> 			<!-- carousel-edit --> 	        <div class="carousel-edit clr-fix" id="carousel-edit">                 <div class="row-2-5 left">                     <div class="row-9 img-upload-arround">                     <input class="carousel-edit-input row-2-3" type="file" style="display: none;">                      <img class="row-10 img-upload" src="http://msp-img.51awifi.com/V1/img/carousel/add.png" alt="1">                     <input id="picurl" style="display: none;"/>   	                     </div>                 </div>             </div>  			<!-- app --> 			<div class="title text-indent"> 				<span class="title-text small-text gray-text">添加应用</span> 			</div> 			<div class="app clr-fix"> 				<div class="row-2 left center-text"> 					<div class="app-icon row-8"> 						<a href="#"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/lunch.png"></a> 						<div class="small-text row-10">点餐</div> 					</div> 				</div>  				<div class="row-2 left center-text"> 					<div class="app-icon row-8"> 						<a href="#"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/services.png"></a> 						<div class="small-text row-10">服务</div> 					</div> 				</div>  				<div class="row-2 left center-text"> 					<div class="app-icon row-8"> 						<a href="#"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/order.png"></a> 						<div class="small-text row-10">买单</div> 					</div> 				</div>  				<div class="row-2 left center-text"> 					<div class="app-icon row-8"> 						<a href="#"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/cup.png"></a> 						<div class="small-text row-10">续杯</div> 					</div> 				</div>  			<!--	<div class="row-2 left center-text"> 					<div class="app-icon row-8"> 						<a href="#"><img class="row-9" src="img/app/share.png"></a> 						<div class="small-text row-10">分享</div> 					</div> 				</div>-->  				<div class="row-2 left center-text"> 					<div class="app-icon row-8"> 						<a href="#"><img class="row-9" src="http://msp-img.51awifi.com/V1/img/app/add.png" id="adduse"></a> 						<div class="small-text row-10">添加</div> 					</div> 				</div> 			</div> 			<div class="title text-indent"> 				<span class="title-text small-text gray-text">园区介绍</span> 			</div> 			<div class=" small-text row-10"> 				<div class="row-10 " > 					<!--<label style="color: darkgray;" for="introduce" id="forIntro">请输入园区介绍</label>--> 					<textarea class="row-10 light-gray-text" rows="3" style="border: none;" id="introduce" maxlength="50"></textarea> 					<div class="inputlimit" id="introLimit">50</div> 				</div>  			</div>  			<div class="title text-indent"> 				<span class="title-text small-text gray-text">园区动态</span> 			</div> 			<div class="row-10 clr-fix"> 				<input class="row-10 setting-input left small-text" name="activeTitle" placeholder="请输入动态标题"  					style="border: none;text-indent: 1rem;" maxlength="20" id="activeTitle"> 			</div> 			<div class="title"> 				<span class="title-text small-text gray-text"></span> 			</div> 			<div class=" small-text row-10"> 				<div class="row-10 " > 					<!--<label style="color: darkgray;" for="activeMsg" id="forActive">请输入动态内容</label>--> 					<textarea class="row-10 light-gray-text" rows="3" style="border: none;" id="activeMsg" maxlength="100"></textarea> 					<div class="inputlimit" id="activeLimit">100</div> 				</div> 			</div> 			<div class="row-10 edit-add"> 				<div class="edit-inner-div"> 					<button id="addActive" class="edit-save" style="font-size: 1.5rem;">保存并新增</button> 				</div> 			</div> 			<div class="title text-indent"> 				<span class="title-text small-text gray-text">动态列表</span> 			</div>             <div id="activeList">             	             </div>	             <div class="row-9 loginErr"> 			    <span class="errorMsg"></span> 		    </div> 			<div class="setting-save row-10 " style="margin-top: 50px;"> 				<div class="row-9"> 					<button class="back-orange" style="font-size: 1.5rem;color:#fff;" id="saveConfig">保存</button> 				</div> 			</div>  		</div> 		<footer class="row-10 footer edit light-gray-text"> 			<div class="row-9 center-text footer-top"> 				<span class="small-text">本系统由中国电信爱WiFi运营中心提供支持</span> 			</div> 			<div class="row-9 center-text footer-bottom"> 				<span class="small-text">10000</span> 			</div> 		</footer>  		<div class="nav row-10" style="display:none;" id="mngPower"> 			<a href="#/"> 				<div class="left row-3-3 center-text"> 					<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/1.png"></div> 					<div class="nav-title small-text">首页</div> 				</div> 			</a> 			<a href="#/config"> 				<div class="left row-3-3 center-text"> 					<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/2.png"></div> 					<div class="nav-title small-text">配置</div> 				</div> 			</a> 			<a href="#/statiscial"> 				<div class="left row-3-3 center-text"> 					<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/3.png"></div> 					<div class="nav-title small-text">统计</div> 				</div> 			</a> 		</div> 		<div class="nav row-10" style="display:none;" id="enterprise"> 		    <a href="#/"> 		        <div class="left row-5 center-text"> 		            <div class="nav-img"><img src="img/nav/1.png"></div> 		            <div class="nav-title small-text">首页</div> 		        </div> 		    </a> 		    <a href="#/config"> 		        <div class="left row-5 center-text"> 		            <div class="nav-img"><img src="img/nav/2.png"></div> 		            <div class="nav-title small-text">配置</div> 		        </div> 		    </a> 		</div> 		<div class="img-upload-dialog row-10 normal-text"> 			<div class="img-upload-dialog-text row-9 center-text"> 				<span>图片上传</span> 				<span class="light-gray-text">(大小不得超过1M)</span> 			</div> 			<div class="img-upload-dialog-text row-9 center-text"> 				<span>拍摄</span> 				<input class="img-upload-dialog-input row-10" type="file"> 			</div> 			<div class="img-upload-dialog-text row-9 center-text"> 				<span>本地相册</span> 				<input class="img-upload-dialog-input row-10" type="file"> 			</div> 			<div class="img-upload-dialog-bottom row-9 center-text"> 				<button class="img-upload-dialog-button row-9">取消</button> 			</div> 		</div> 		<div id="overlay"></div> </script><script type="text/template" id="mngInfo_tpl"> 	<header class="header back-blue"> 		<nav> 			<a href="javascript:void(0)"  id="historyBack"> 				<div class="header-left center-text row-2-5 left small-text"> 					<img class="header-left-img" src="http://msp-img.51awifi.com/V1/img/header/back.png"> 				</div> 			</a> 			<div class="center-text row-5 left normal-text">园区资料</div> 			<a href="javascript:void(0)"> 				<div class="header-right right-text row-2-5 left small-text">  				</div> 			</a> 		</nav> 	</header>  	<div class="container clr-fix"> 		<div class="row-9 info-avatar"> 			<div class="row-3 left small-text"><span>园区LOGO</span></div> 			<div class="row-4 right"> 				<input class="info-avatar-file info-input display-alpha right" type="file" id="imgUpload"> 				<img class="info-avatar-img right row-5" src="http://msp-img.51awifi.com/V1/img/messageboard/avatar.png" alt="头像" id="faceInfo"  /> 				<input type="hidden" id="saveSrc" /> 			</div> 		</div> 		<div class="row-9 normal-height info-nick"> 			<div class="row-3 left small-text"><span>园区名称</span></div> 			<div class="row-7 left "> 				<input class="info-input small-height small-text light-gray-text" type="text" id="merchantName" readonly/> 			</div> 		</div>  		<div class="row-9 normal-height info-nick"> 			<div class="row-3 left small-text"><span>园区电话</span></div> 			<div class="row-7 left "> 				<input class="info-input small-height small-text " type="text" id="telephone" maxlength="11" /> 			</div> 		</div> 		<div class="row-9 normal-height clr-fix info-birthday clr-fix"> 			<div class="row-3 left small-text">园区地址</div> 			<div class="row-7 right right-text normal-text"> 				<input class="info-input small-height small-text " type="text" id="address" maxlength="50"/> 				<!--<textarea rows="3"></textarea>--> 			</div> 		</div> 		<div class="row-9 normal-height info-nick"> 			<div class="row-3 left small-text"><span>管理员姓名</span></div> 			<div class="row-7 left"> 				<input class="info-input small-height small-text" type="text" placeholder="请输入姓名" id="userName" maxlength="8"/> 			</div> 		</div> 		<div class="row-9 normal-height info-nick"> 			<div class="row-3 left small-text"><span>管理员昵称</span></div> 			<div class="row-7 left"> 				<input class="info-input small-height small-text" type="text" placeholder="请输入昵称" id="userNick" maxlength="8"/> 			</div> 		</div>  		<div class="row-9 normal-height info-sex"> 			<div class="row-2 left small-text"><span>性别</span></div> 			<div class="row-8 left  "> 				<div class="row-3 right "> 					<input class="info-input info-checkbox-female left small-height" type="checkbox" id="female"> 					<label class="left checkbox-label" for="female"></label> 					<span class="small-text">女</span> 				</div> 				<div class="row-3 right left-text"> 					<input class="info-input info-checkbox-male left small-height" type="checkbox" id="male"> 					<label class="left checkbox-label" for="male"></label> 					<span class="small-text">男</span> 				</div> 			</div> 		</div> 		<div class="row-9 normal-height clr-fix info-birthday clr-fix"> 			<div class="row-3 left small-text">生日</div> 			<div class="row-5 right right-text small-text" style="min-height:48px"> 				<input class="info-birthday-input info-input normal-height" type="text" placeholder="请选择" id="birthdayStr"> 			</div> 		</div>  	</div> 	<div class="information-save row-10 "> 		<div class="row-9"> 			<span class="errorMsg"></span> 			<button class="back-orange" id="save" style="color: #fff;font-size: 1.5rem;">保存</button> 		</div> 	</div> 	</div>  	<footer class="row-10 footer light-gray-text"> 		<div class="row-9 center-text footer-top"> 			<span class="small-text">本系统由中国电信爱WiFi运营中心提供支持</span> 		</div> 		<div class="row-9 center-text footer-bottom"> 			<span class="small-text">10000</span> 		</div> 	</footer>  	<div id="overlay"></div> </script><script type="text/template" id="statiscial_tpl"> 	<header class="header back-blue"> 		<nav> 			<a href="#/"> 				<div class="header-left center-text row-2-5 left small-text"> 					<img class="header-left-img" src="http://msp-img.51awifi.com/V1/img/header/back.png" id="back"> 				</div> 			</a> 			<div class="center-text row-5 left normal-text">统计</div>  		</nav> 	</header>  	<div class="container statiscontainer"> 		<div class="row-10 clr-fix"> 			<div class="row-5 left"> 				<div class="visit-top-number row-9 center-text" id="rechargetotalnum"> 				</div> 				<div class="visit-top-text row-9 normal-text center-text">充值总金额(元)</div> 			</div> 			<div class="row-5 left"> 				<div class="visit-top-number visit-top-border-left row-10 center-text " id="rechargeavgnum"> 				</div> 				<div class="visit-top-text visit-top-border-left row-10 normal-text center-text ">平均充值金额(元)</div> 			</div> 		</div>  		<!--<div class="information-save row-10 "> 				<div class="row-9"> 					<button class="back-orange" id="send">一键发短信</button> 				</div> 			</div>--> 		<!-- 访客 --> 		<div class="title"> 			<span class="title-text small-text gray-text"></span> 		</div>  		<div class="row-9 clr-fix visit-border-top"> 			<div class="row-5 left"> 				<div class="visit-top-padding small-text">当前付费用户</div> 			</div> 			<div class="row-5 left"> 				<div class="visit-top-padding row-10 small-text right-text" id="rechargeusernum"> 				</div> 			</div> 		</div> 		<div class="title"> 			<span class="title-text small-text gray-text"></span> 		</div> 		<div class="row-9 clr-fix visit-border-top"> 			<div class="row-5 left"> 				<div class="visit-top-padding small-text">注册用户:</div> 			</div> 			<div class="row-5 left"> 				<div class="visit-top-padding row-10 small-text right-text" id="mer_user_num"> 				</div> 			</div> 		</div> 		<div class="bottomline"></div> 		<div class="row-9 clr-fix visit-border-top"> 			<div class="row-5 left"> 				<div class="visit-top-padding small-text">付费用户:</div> 			</div> 			<div class="row-5 left"> 				<div class="visit-top-padding row-10 small-text right-text" > 					<span id="money_pay_all"></span> 				</div> 			</div> 		</div>  		<div class="title row-10"> 			<div class="row-9"> 				<span class="title-text small-text gray-text">付费用户明细</span> 			</div>  		</div> 		<!-- 数据内容,重复渲染 --> 		<div id="payinfo-visit-list">  		</div> 		<div class="row-10 clr-fix visit-border-top"></div>  		<!--END--> 		<div class="row-10 small-text center-text "> 			<a id="more" href="javascript:void(0)" class="blue-text">点击加载更多 </a> 		</div> 		<footer class="row-10 footer light-gray-text" style="padding-top: 45px;"> 			<div class="row-9 center-text footer-top"> 				<span class="small-text">本系统由中国电信爱WiFi运营中心提供支持</span> 			</div> 			<div class="row-9 center-text footer-bottom"> 				<span class="small-text">10000</span> 			</div> 		</footer> 		<div class="nav row-10"> 			<a href="#/"> 				<div class="left row-3-3 center-text"> 					<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/1.png"></div> 					<div class="nav-title small-text">首页</div> 				</div> 			</a> 			<a href="#/config"> 				<div class="left row-3-3 center-text"> 					<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/2.png"></div> 					<div class="nav-title small-text">配置</div> 				</div> 			</a> 			<a href="#/statiscial"> 				<div class="left row-3-3 center-text"> 					<div class="nav-img"><img src="http://msp-img.51awifi.com/V1/img/nav/3.png"></div> 					<div class="nav-title small-text">统计</div> 				</div> 			</a> 		</div> 		<div id="overlay"></div> </script><script type="text/template" id="userCenter_tpl"> 	<header class="header back-blue"> 		<nav> 			<a href="#/" > 				<div class="header-left center-text row-2-5 left small-text"> 					<img class="header-left-img" src="http://msp-img.51awifi.com/V1/img/header/back.png"> 				</div> 			</a> 			<div class="center-text row-5 left normal-text">个人中心</div> 		</nav> 	</header>  	<div class="container"> 		<div class="row-10"> 			<!--这里根据角色渲染不同的地址--> 			<a href="javascript:void(0)" id="detailInfo"> 				<div class="row-9 clr-fix"> 					<div class="left personal-img"> 						<img src="http://msp-img.51awifi.com/V1/img/messageboard/avatar.png" id="faceInfo" class="info-avatar-img" style="padding-top:0"> 					</div> 					<div class="row-6 left"> 						<div class="personal-info normal-text">个人头像</div> 						<div class="personal-info normal-text" id="telephone"></div> 					</div> 					<div class="personal-next right"> 						<img class="personal-next-img" src="http://msp-img.51awifi.com/V1/img/visit/next.png"> 					</div> 				</div> 			</a> 		</div> 		<!--套餐信息--> 		<div class="title"> 			<div class="row-9 clr-fix small-text"> 				<span class="title-text" style="color: #999999;"> 套餐信息</span> 			</div> 		</div> 		<div > 			<div class="row-10 normal-height normal-text personal-border-bottom"> 				<a href="javascript:void(0);"> 					<div class="row-9 clr-fix small-text"> 						套餐:<span id="packageInfo"></span> 					</div> 					<div class="row-10  bottomline"></div> 				</a> 			</div> 			<div class="row-10  normal-text"> 				<div class="row-9 clr-fix"> 					<div style="padding-top: 0.7rem;"> 						<span class="small-text minspan ">有效期至：</span> 					</div> 					<div style="padding-bottom: 0.7rem;"> 						<span class="small-text " id="beginDate"> </span><span class="small-text" id="endDate"></span> 					</div> 				</div> 			</div> 		</div>  		<!--套餐信息end-->  		<div class="title"> 			<div class="row-9 clr-fix small-text"> 				<span class="title-text" style="color: #999999;"> 消费记录</span> 			</div> 		</div> 		<div id="payRecords"> 			<!--消费记录-->  		</div> 		<div class="row-10 small-text center-text "> 			<a id="more" href="javascript:void(0)" class="blue-text">点击加载更多 </a> 		</div> 	</div>  	<footer class="row-10 footer light-gray-text"> 		<div class="row-9 center-text footer-top"> 			<span class="small-text">本系统由中国电信爱WiFi运营中心提供支持</span> 		</div> 		<div class="row-9 center-text footer-bottom"> 			<span class="small-text">10000</span> 		</div> 	</footer> 	<!--<div style="display: hidden;" id="hiddenDiv">  		<input type="text" name="sex" />  		<input type="text" name="birthdayStr" />  		<input type="text" name="address" />  		<input type="text" name="merchantName" />  		<input type="text" name="userName" />		  	</div>--> </script><script type="text/template" id="userInfo_tpl"> 	<header class="header back-blue"> 		<nav> 			<a href="javascript:void(0)"  id="historyBack"> 				<div class="header-left center-text row-2-5 left small-text"> 					<img class="header-left-img" src="http://msp-img.51awifi.com/V1/img/header/back.png"> 				</div> 			</a> 			<div class="center-text row-5 left normal-text">个人资料</div> 			<a href="javascript:void(0)"> 				<div class="header-right right-text row-2-5 left small-text">  				</div> 			</a> 		</nav> 	</header>  	<div class="container clr-fix"> 		<div class="row-9 info-avatar"> 			<div class="row-3 left small-text"><span>个人头像</span></div> 			<div class="row-4 right"> 				<input class="info-avatar-file info-input display-alpha right" type="file" id="imgUpload"> 				<img class="info-avatar-img right row-5" src="http://msp-img.51awifi.com/V1/img/messageboard/avatar.png" alt="头像" id="faceInfo" /> 				<input type="hidden" id="saveSrc" />	 			</div> 		</div> 		<div class="row-9 normal-height info-nick"> 			<div class="row-3 left small-text"><span>昵称</span></div> 			<div class="row-7 left"> 				<input class="info-input small-height small-text" type="text" placeholder="请输入昵称" id="userNick" maxlength="8"/> 			</div> 		</div>  	  		<div class="row-9 normal-height info-sex"> 			<div class="row-2 left small-text"><span>性别</span></div> 			<div class="row-8 left  "> 				<div class="row-3 right "> 					<input class="info-input info-checkbox-female left small-height" type="checkbox" id="female"> 					<label class="left checkbox-label" for="female"></label> 					<span class="small-text" >女</span> 				</div> 				<div class="row-3 right left-text"> 					<input class="info-input info-checkbox-male left small-height" type="checkbox" id="male"> 					<label class="left checkbox-label" for="male"></label> 					<span class="small-text">男</span> 				</div> 			</div> 		</div> 		<div class="row-9 normal-height clr-fix info-birthday clr-fix"> 			<div class="row-3 left small-text">生日</div> 			<div class="row-5 right right-text small-text" style="min-height:48px"> 				<input class="info-birthday-input info-input normal-height" type="text" placeholder="请选择" id="birthday"> 			</div> 		</div> 		<div class="row-9 normal-height clr-fix info-birthday clr-fix"> 			<div class="row-3 left small-text">地址</div> 			<div class="row-7 right right-text normal-text"> 				<input class="info-input small-height small-text " type="text"  id="address" placeholder="请输入地址" maxlength="50"/> 			</div> 		</div> 	</div> 	<div class="information-save row-10 "> 		<div class="row-9"> 			<span class="errorMsg"></span> 			<button class="back-orange" id="save" style="color: #fff;font-size: 1.5rem;">保存</button> 		</div> 	</div> 	</div>  	<footer class="row-10 footer light-gray-text"> 		<div class="row-9 center-text footer-top"> 			<span class="small-text">本系统由中国电信爱WiFi运营中心提供支持</span> 		</div> 		<div class="row-9 center-text footer-bottom"> 			<span class="small-text">10000</span> 		</div> 	</footer>  	<div id="overlay"></div> </script></div>';URIList = {

	context : {
		homeURI : '#/home', // 主页地址配置
		templateSuffix : "tpl", // 模版后缀名配置
		// before_controller : BeforeController, //所有uri请求执行之前执行的controller
		before_controller : null,
		// after_controller : AfterController, //所有uri请求执行之后执行的controller
		after_controller : null,
		refresh : true, // uri跳转，如果uri已经访问过是否刷新。
		refreshFlag : "true" // uri参数中存在该参数，则将会刷新页面，否则不刷新。
	},
	//主页
	index_uri : {
		extend : null,
		uri : '#/',
		layout : [ {
			selector : '#MainContent',
			template : "index_tpl",
			controller : IndexController,
			append : false
		} ]
	},
	/*//登录
	login_uri : {
		extend : null,
		uri : '#/login',
		layout : [ {
			selector : '#MainContent',
			template : "login_tpl",
			controller : NewLoginController,
			append : false
		} ]
	},*/
//	//注册
//	register_uri : {
//		extend : null,
//		uri : '#/register',
//		layout : [ {
//			selector : '#MainContent',
//			template : "register_tpl",
//			controller : RegisterController,
//			append : false
//		} ]
//	},
//	//忘记密码
//	resetPwd_uri : {
//		extend : null,
//		uri : '#/resetPwd',
//		layout : [ {
//			selector : '#MainContent',
//			template : "resetPwd_tpl",
//			controller : ResetPwdController,
//			append : false
//		} ]
//	},
	//购买套餐
//	buy_uri : {
//		extend : null,
//		uri : '#/buy',
//		layout : [ {
//			selector : '#MainContent',
//			template : "buy_tpl",
//			controller : BuyController,
//			append : false
//		} ]
//	},
	buy_Tran : {
		extend : null,
		uri : '#/buyTran',
		layout : [ {
			selector : '#MainContent',
			template : "buy_tran",
			controller : BuyTranController,
			append : false
		} ]
	},
//	qrcode_uri : {
//		extend : null,
//		uri : '#/qrcode',
//		layout : [ {
//			selector : '#MainContent',
//			template : "qrcode_tpl",
//			controller : QrcodeController,
//			append : false
//		} ]
//	},
	//用户个人中心
	userCenter_uri : {
		extend : null,
		uri : '#/userCenter',
		layout : [ {
			selector : '#MainContent',
			template : "userCenter_tpl",
			controller : UserCenterController,
			append : false
		} ]
	},
	//用户个人信息
	userInfo_uri : {
		extend : null,
		uri : '#/userInfo',
		layout : [ {
			selector : '#MainContent',
			template : "userInfo_tpl",
			controller : UserInfoController,
			append : false
		} ]
	},
	//管理员个人信息
	mngInfo_uri : {
		extend : null,
		uri : '#/mngInfo',
		layout : [ {
			selector : '#MainContent',
			template : "mngInfo_tpl",
			controller : MngInfoController,
			append : false
		} ]
	},
	//园区配置
	config_uri : {
		extend : null,
		uri : '#/config',
		layout : [ {
			selector : '#MainContent',
			template : "config_tpl",
			controller : ConfigController,
			append : false
		} ]
	},
	//园区统计
	statiscial_uri : {
		extend : null,
		uri : '#/statiscial',
		layout : [ {
			selector : '#MainContent',
			template : "statiscial_tpl",
			controller : StatiscialController,
			append : false
		} ]
	},
	//个人信息修改成功
//	updInfoSucc_uri : {
//		extend : null,
//		uri : '#/updInfoSucc',
//		layout : [ {
//			selector : '#MainContent',
//			template : "updInfoSucc_tpl",
//			controller : UpdInfoSuccController,
//			append : false
//		} ]
//	}
};

URI.addURI(URIList);
window.logger = {
	log : function(msg) {
		console.log(msg);
	},
	error : function(msg) {
		console.log(msg);
	}
};ActiveParam = (function() {
	name = "active"; // 命名空间
	return {
		getName: function() {
			return name;
		},
		paramList: {
			/**
			 * @Comments : 默认请求参数。
			 */
			defaultParam: {
				url: '', //ajax请求的url。
				parameter: { //ajax请求的参数。
					//					curpage: 1,
					//					pagesize: 15
				},
				fileDir: "/src/test-data",
				requestType: 'ajax', //默认请求方式，请求类型(HexinExeType:调用客户端程序, HexinRequestType:调用长连接, NormalRequestType:调用短连接)
				//success : function(options, result, textStatus, jqXHR),						//ajax请求成功后调用的方法。
				//failure : function(options, jqXHR, textStatus, errorThrown),						//ajax请求失败后调用的方法。
				//complete : function(options, jqXHR, textStatus),						//请求结束后执行的方法。
				resultWrapper: function(result) { //如果配置了该方法，ajax请求成功后首先走该方法
					/*if(result&&result.r==504){
						mvc.href("#/login");
					}*/
					return result;
				},
				forceRefresh: false, //是否强制刷新,
				dataType: 'json', //(HexinRequestType、NormalRequestType)数据类型(默认json)
				type: 'GET', //GET,POST请求
				result: {}
			},

			//确认支付
			pay: {
				url: PATH + '/pay/trade',
				parameter: {
					'ordername': '', //订单名
					'paytype': '', //支付方式 默认翼支付 3.支付宝
					'payuser': '', //支付用户
					'packageid': '', //套餐id
					'packagenum': '', //数量
					'paynum': '', //支付价
					'totalnum': '', //总价
					'merchantid': '' //园区id
				},
				type: 'POST'
			},
			//领取免费流量包
			getFree: {
				url: PATH + '/time/pkg/get',
				parameter: {
				},
				type: 'POST'
			},
			//套餐列表
			meat: {
				url: PATH + '/time/pay/list/search',
				parameter: {
				},
				type: 'POST'
			},
			//获得电渠地址
			getDqUrl: {
				url: PATH + '/time/pay/url/get'
			}





		}
	}

})();

URLParam.addParamObj(ActiveParam);IndexParam = (function() {
	name = "index"; // 命名空间
	return {
		getName: function() {
			return name;
		},
		paramList: {
			/**
			 * @Comments : 默认请求参数。
			 */
			defaultParam: {
				url: '', //ajax请求的url。
				parameter: { //ajax请求的参数。
					//					curpage: 1,
					//					pagesize: 15
				},
				fileDir: "/src/test-data",
				requestType: 'ajax', //默认请求方式，请求类型(HexinExeType:调用客户端程序, HexinRequestType:调用长连接, NormalRequestType:调用短连接)
				//success : function(options, result, textStatus, jqXHR),						//ajax请求成功后调用的方法。
				//failure : function(options, jqXHR, textStatus, errorThrown),						//ajax请求失败后调用的方法。
				//complete : function(options, jqXHR, textStatus),						//请求结束后执行的方法。
				resultWrapper: function(result) { //如果配置了该方法，ajax请求成功后首先走该方法
					/*if (result&&result.r == 504) {
						mvc.href("#/login");
					}*/
					return result;
				},
				forceRefresh: false, //是否强制刷新,
				dataType: 'json', //(HexinRequestType、NormalRequestType)数据类型(默认json)
				type: 'GET', //GET,POST请求
				result: {}
			},

			//主页信息
			index: {
				url: PATH + '/merchant/init.json',
				parameter: {
					"logId" : "",
					"deviceId" : "",
					"mobilePhone" : "",
					"userMac" : "",
					"userIP" : "",
					"gwAddress" : "",
					"gwPort" : "",
					"nasName" : "",
				},
				type: 'POST'
			},
			initUser:{
				url: PATH + '/merchant/initUser.json',
				parameter: {

				},
				type: 'POST'
			}



		}
	}

})();

URLParam.addParamObj(IndexParam);MngParam = (function() {
	name = "mng"; // 命名空间
	return {
		getName: function() {
			return name;
		},
		paramList: {
			/**
			 * @Comments : 默认请求参数。
			 */
			defaultParam: {
				url: '', //ajax请求的url。
				parameter: { //ajax请求的参数。
					//					curpage: 1,
					//					pagesize: 15
				},
				fileDir: "/src/test-data",
				requestType: 'ajax', //默认请求方式，请求类型(HexinExeType:调用客户端程序, HexinRequestType:调用长连接, NormalRequestType:调用短连接)
				//success : function(options, result, textStatus, jqXHR),						//ajax请求成功后调用的方法。
				//failure : function(options, jqXHR, textStatus, errorThrown),						//ajax请求失败后调用的方法。
				//complete : function(options, jqXHR, textStatus),						//请求结束后执行的方法。
				resultWrapper: function(result) { //如果配置了该方法，ajax请求成功后首先走该方法
					/*if (result&&result.r == 504) {
						mvc.href("#/login");
					}*/
					return result;
				},
				forceRefresh: false, //是否强制刷新,
				dataType: 'json', //(HexinRequestType、NormalRequestType)数据类型(默认json)
				type: 'GET', //GET,POST请求
				result: {}
			},

			//管理员信息
			mngInfo: {
				url: PATH + '/ms/member/merchant/index/update',
				parameter: {
					'logo': '', //头像地址
					'nick': '', //昵称
					'sex': '', //性别
					'birthday': '', //生日
					'userName': '' //姓名
				},
				type: 'POST'
			},
			//统计信息
			statiscial: {
				url: PATH + '/ms/member/merchant/visit/info',

				parameter: {
					'merchantid': '' //园区id
				},
				type: 'POST'
			},
			//付费用户明细
			userPayDetail: {
				url: PATH + '/ms/member/merchant/visit/payinfo',
				parameter: {
					'merchantid': '', //园区id
					'pagenum': '',
					'pagesize': ''
				},
				type: 'POST'
			},
			//动态新增
			addActive: {
				url: PATH + '/ms/member/merchant/notice/add',
				parameter: {
					'notice_title': '', //标题
					'notice_text': '' //内容
				},
				type: 'POST'
			},
			//动态查询
			searchActive: {
				url: PATH + '/ms/member/merchant/notice/list',
				parameter: {
					'id': '', //园区id
				},
				type: 'POST'
			},
			//动态删除
			delActive: {
				url: PATH + '/ms/member/merchant/notice/del',
				parameter: {
					'id': '', //动态条目id

				},
				type: 'POST'
			},
			//保存配置
			saveConfig: {
				url: PATH + '/ms/member/merchant/index/parkUpdate',
				parameter: {
					'pic_1': "",
					'pic_2': "",
					'pic_3': "",
					'pic_4': "",
					'remarks': ""
				},
				type: 'POST'
			},
			//管理员个人中心
			center: {
				url: PATH + '/ms/member/merchant/index/info',
				parameter: {},
				type: 'POST'
			},
			//查询首页信息
			carouselEditSearchRequest: {
				url: PATH + '/ms/member/merchant/index/init',
				parameter: {

				},
				type: 'POST'
			}

		}
	}

})();

URLParam.addParamObj(MngParam);UserParam = (function() {
	name = "user"; // 命名空间
	return {
		getName: function() {
			return name;
		},
		paramList: {
			/**
			 * @Comments : 默认请求参数。
			 */
			defaultParam: {
				url: '', //ajax请求的url。
				parameter: { //ajax请求的参数。
					//					curpage: 1,
					//					pagesize: 15
				},
				fileDir:"/src/test-data",
				requestType: 'ajax', //默认请求方式，请求类型(HexinExeType:调用客户端程序, HexinRequestType:调用长连接, NormalRequestType:调用短连接)
				//success : function(options, result, textStatus, jqXHR),						//ajax请求成功后调用的方法。
				//failure : function(options, jqXHR, textStatus, errorThrown),						//ajax请求失败后调用的方法。
				//complete : function(options, jqXHR, textStatus),						//请求结束后执行的方法。
				resultWrapper: function(result) { //如果配置了该方法，ajax请求成功后首先走该方法
					/*if(result&&result.r==504){
						mvc.href("#/login");
					}*/
					return result;
				},
				forceRefresh: false, //是否强制刷新,
				dataType: 'json', //(HexinRequestType、NormalRequestType)数据类型(默认json)
				type: 'GET', //GET,POST请求
				result: {}
			},
			// 登录
			login: {
				url: PATH + '/merchant/login/save.json',
				parameter: {
					username: '', //用户名
					password: '', //密码
					'deviceId': '', //设备id
					'terMac': '', //mac地址
					'callback': "callback"
				},
				type: 'POST'
					//				dataType: 'json',
					//				result: {}
			},
			//注册
			register: {
				url: PATH + '/ms/reg/save',
				parameter: {
					phone: '', //用户名
					password: '', //密码
					captcha: '' //验证码
				},
				type: 'POST'
			},
			//找回密码
			resetPwd: {
				url: PATH + '/ms/password/savepwd',
				parameter: {
					mobile: '', //用户名
					pwd: '', //密码
					captcha: '' //验证码
				},
				type: 'POST'
			},
			//验证码
			captcha: {
				url: PATH + '/login/sms/wifi/request.json',
				parameter: {
					'mobile': '',
					'type': ''
				},
				type: 'POST'
			},
			//用户个人中心
			center: {
				url: PATH + '/user/info.json',
				parameter: {

				},
				type: 'GET'
			},
			//加载更多消费记录
			payRecords: {
				url: PATH + '/ms/member/msuser/cosume',
				parameter: {
					'curpage': '', //当前页
					'pagesize': '' //单页显示条数
				},
				type: 'POST'
			},
			//套餐信息
			setMeal: {
				url: PATH + '/ms/member/msuser/package',
				parameter: {
//					'curpage': '', //当前页
//					'pagesize': '' //单页显示条数
				},
				type: 'POST'
			},

			//普通用户信息
			userInfo: {
				url: PATH + '/user/update.json',
				parameter: {
					'touxiang': '', //头像地址
					'nick': '', //昵称
					'sex': '', //性别
					'birthday': '', //生日
					'address': '' //地址
				},
				type: 'POST'
			},


		}
	}

})();

URLParam.addParamObj(UserParam);
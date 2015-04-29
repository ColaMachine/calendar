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
Date.prototype.format = function(format)
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
    if(/(y+)/.test(format)) 
    	format=format.replace(RegExp.$1,    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    	if(new RegExp("("+ k +")").test(format))
    		format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));// 很妙的设计
    return format;
   }
/*Date.prototype.parse=function (dateStr,format){
	
	if(/(y+)(M+)/.test(format)){
		var year=parseInt(dateStr.substr(RegExp.$1.index,RegExp.$1.length));
	}
	if(/(M+)/.test(format)){
		var month=parseInt(dateStr.substr(RegExp.$1.index,RegExp.$1.length));
	}
	if(/(d+)/.test(format)){
		var day=parseInt(dateStr.substr(RegExp.$1.index,RegExp.$1.length));
	}
	var date=Date.parse(year+"-"+month+"-"+day);
	if(/(h+)/.test(format)){
		var hour=parseInt(dateStr.substr(RegExp.$1.index,RegExp.$1.length));
		date+=hour*60*60000;
		
	}
	if(/(m+)/.test(format)){
		var minute=parseInt(dateStr.substr(RegExp.$1.index,RegExp.$1.length));
		date+=minute*60000;
		
	}
	if(/(s+)/.test(format)){
		var seconds=parseInt(dateStr.substr(RegExp.$1.index,RegExp.$1.length));
		date+=seconds*1000;
		
	}
	return new Date(date);
	
}*/
            

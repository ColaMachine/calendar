

function CalendarView() {
	// Calendar.call(this);
	// this.setHashCode();
	this.index = this.hashCode = "a1234";

	System._instances[this.hashCode] = this;
	this.count = 0;
	this.getUniqueId = function() {
		return "ca" + (t.count++).toString(36);
	};
	//this.selectedWeek = new Array(); 2014年2月24日 14:20:25 注释
	// variable
	this.dummyDay = new Date();//一个 用于翻页的日期 指向当前的日历页面中的某个日期
	this.selectedDay = new Date();//用鼠标选中的日期
	this.today = new Date();//今天

	this.sectHeight = 40;//configure 常量 用来设置高度
	this.vampires = [];
	this.preSelectedTd = null;//之前选中的td单位
	this.preSelectedTr = null;//之前选中的tr
	this.preMouseOverTd = null;//之前鼠标移动过的td
	this.eventStack = {};//事件堆栈 暂时没用
	
	this.preColumn = 0;
	this.currColumn = 0;
	this.valueStack = {};
	this.calTypes={};
	this.currentCalType=null;
	this.hourHeight=40;//一个小时有多高
	this.timeSect = ["00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
			"06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00",
			"13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
			"20:00", "21:00", "22:00", "23:00"];
	this.weekName = [ "Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
			"Saturday"];
	this.selectMode = true;// true is week false is day;
	this.viewMode = 1;// 0是天 1 是 周 2 是月;
	this.currentEventId = "";

}
//t = CalendarView;
//t.count = 0;
//t.getUniqueId = function() {
//	return "ca" + (t.count++).toString(36);
//};
//t.selectedWeek = new Array();
// variable
//t.dummyDay = new Date();//当前选中的时间
//t.selectedDay = new Date();
//t.today = new Date();

CalendarView.prototype.goPreYear = function() {
	var day = this.dummyDay;
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
	this.dummyDay = day;
};
CalendarView.prototype.goNextYear = function() {
	var day = this.dummyDay;
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
	this.dummyDay = day;
};
CalendarView.prototype.goPreMonth = function() {
	var day = this.dummyDay;
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
	this.dummyDay = day;
};
CalendarView.prototype.goNextMonth = function() {
	var day = this.dummyDay;
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
	this.dummyDay = day;
};
CalendarView.prototype.goPreDay = function() {
	var day = this.dummyDay;
	var m = day.getMonth();
	var y = day.getFullYear();
	var d = day.getDate();
	if (d > 1)
		d--;
	else {
		if (m > 0) {
			m--;

		} else {
			y--;
			m = 11;

		}
		d = CaculateMonthDays(y, m);
	}
	day.setYear(y);
	day.setMonth(m, d);
	this.dummyDay = day;
};
CalendarView.prototype.goNextDay = function() {
	var day = this.dummyDay;
	var m = day.getMonth();
	var y = day.getFullYear();
	var d = day.getDate();
	if (d < CaculateMonthDays(y, m))
		d++;
	else {
		if (m < 11) {
			m++;

		} else {
			y++;
			m = 0;

		}
		d = 1;
	}
	day.setYear(y);
	day.setMonth(m, d);
	this.dummyDay = day;
};;

// get the view str start
// 界面的初始换--------------------------------------------------------------------------------------------------
CalendarView.prototype.render = function() {
	// var y = this.dummyDay.getFullYear();
	// var m = this.dummyDay.getMonth() + 1;
	var str = "";
	str = "<div id=\"mask\" onclick=\"Instance('"
			+ this.index
			+ "').closeCalendarEventDialog()\" style=\"z-index:100;display:none;position:absolute;height:200%;width:100%;background-color:#000000;filter:alpha  (opacity:20) ;\"></div>"
			+ "<table cellspacing=0 cellpadding=0>"
			+ "<tr>"
			+ "<td  style=\"padding-top:50px\">"
			+ "<button onclick=\"Instance('"
			+ this.index
			+ "').addEvent(null);event.cancelBubble=true;\">写日志</button>"
			+ "</td>"
			+ "<td  align=right>"
			+ "<span  onclick=\"Instance('"
			+ this.index
			+ "').changeToDayView()\">day</span>&nbsp;<span onclick=\"Instance('"
			+ this.index + "').changeToWeekView()\">week</span>"
			+ " &nbsp;<span onclick=\"Instance('" + this.index
			+ "').changeToMonthView()\">month</span>" + "</td>" + "</tr>"
			+ "<tr>" + "<td  style=\"padding-top:50px\">"
			+ "<div id='mcal_canopy_div' class='mcal_canopy_div' style='display:none'><span class='h zippy-arrow2' unselectable='on'></span><span class='calHeaderSpace'>迷你日历</span></div>" +
					"<div  cellspacing=0 	cellpadding=0 id='mcal_div' class='mcal_div' " 
			
			//minicalendar 
			+ "\">" + this.getCalendarStr() + "</div>" 
			+"<h2 id='mycal_h2' class='mycal_h2'>"
			+	"<span class='mycal_arrow_sp'></span>"
			+	"<span  class='mycal_title_sp'>我的日历</span>"
			+	"<span class='mycal_menu_sp clstMenu'>...</span>"
			+"</h2 >"
			+"<div id='mcal_types_div' class='mcal_types_div'>"
				+	"<div class='mcal_type_div' id=''><span  class='mcal_types_icon_sp' style='background-color:red'></span><span style='width:3px'></span>"
				+"<span class='mcal_types_title'>工作</span><span class='mcal_types_arrow'></span></div>"
				+	"<div class='mcal_type_div' id=''><span  class='mcal_types_icon_sp' style='background-color:yellow'></span><span style='width:3px'></span>"
				+"<span class='mcal_types_title'>生活</span><span class='mcal_types_arrow'></span></div>"
				+	"<div class='mcal_type_div' id=''><span  class='mcal_types_icon_sp' style='background-color:blue'></span><span style='width:3px'></span>"
				+"<span class='mcal_types_title'>账务</span><span class='mcal_types_arrow'></span></div>"

			+"</div >"
			+ "</td>"
			+ "<td id=div_CalendarEventView_" + this.index + ">"
			+ this.getCalendarScheduleStr() + "</td>" + "</tr>" + "</table>"
			+ this.getCalendarEventDialogStr();//
	return str;
};
// 界面的拆分--------------------------------------------------------------------------------------------------
CalendarView.prototype.getRowDraw = function() {
	var str = "";
	str = "<div  class=\"horizontal-line-container\"><div  class=\"horizontal-line-container2\">";
	for (var i = 0; i < 24; i++) {
		str += "<div style='height:"+(this.hourHeight-1)+"' class=\"horizontal-line\"><div style='height:"+(this.hourHeight/2-1)+"' ></div></div>";
	}
	str += "</div></div>";
	return str;
};

//显示类别--------------------------------------------------------------------------------------------------
CalendarView.prototype.displayMCalTypes = function() {
	/*testdata start**/
	var type={};
	type.id="321";
	type.color="#FFFFFF";
	type.name="我的日历";
	/*testdata end**/
	var calendar_type_list =$$("calendar_type_list");
	var div =document.createElement("DIV");
	div.innerHTML="<span id='"+type.id+"' class='mcal_types_title_icon' style='background-color:'>"+type.name+"</span><span class='mcal_types_title'></span><span class='mcal_types_arrow'></span>"
};
/*
 * 
 * 右侧布局
 */
CalendarView.prototype.getCalendarScheduleStr = function() {
	// 周视图
	// style='border:solid 1px red'
	var now = new Date();
	var str = "";
	var weekDays;
	if (this.viewMode == 0) {
		weekDays = [this.dummyDay];
	} else if (this.viewMode == 1) {
		weekDays = getDateOfWeek(this.dummyDay);
	} else if (this.viewMode == 2) {
		weekDays = getDateOfMonth(this.dummyDay);
	}
	global_weekdays = weekDays;
	// <b class=\"xb1\" style=\"width:1002px; \"></b><b class=\"xb2\"
	// style=\"width:1006px; \"></b><b class=\"xb3\" style=\"width:1008px;
	// \"></b><b class=\"xb4\" style=\"width:1010px; \"></b>
	str = ""
			+ "<table class=\"firsttable\"  cellpadding=\"0\" cellspacing=\"0\">"
			+ "<tr >"
			+ "<td >"
			+ "<DIV id=\"topcontainerwk\" style=\"\" >"// div 日历右半边最外层的div
			+ "<TABLE class=\"wk-weektop\" cellSpacing=0 cellPadding=0 >"
			+ "<tr>"
			+ "<TD class=\"wk-tzlabel\" style=\"WIDTH: 76px; height:3px\" rowSpan=3>&nbsp;</TD>";

	for (var i = 0; i < weekDays.length; i++) {
		str += "<TD  title=\""
				+ (weekDays[i].getMonth() + 1)
				+ "/"
				+ weekDays[i].getDate()
				+ " ("
				+ this.weekName[weekDays[i].getDay()]
				+ ")\"  align=\"center\" >"
				+ "<DIV class=\"wk-dayname style1\"><SPAN class=\"ca-cdp20537 wk-daylink\" style=\"CURSOR: pointer\">"
				+ (weekDays[i].getMonth() + 1) + "/" + weekDays[i].getDate()
				+ " (" + this.weekName[weekDays[i].getDay()]
				+ ")</SPAN></DIV></TD>";
	}
	str += "<TD class=\"wk-dummyth\" style=\"WIDTH: 16px\" rowSpan=3>&nbsp;</TD>"
			+ "</TR>"
			+ "<TR>"
			+ "<TD class=\"wk-allday\" colSpan=7 height=\"24px\">"
			+ "<DIV id=\"weekViewAllDaywk\" style=\"height:100%\">"
			+ "<TABLE class=\"innertoptable\" cellSpacing=0 cellPadding=0 width=\"100%\"  height=\"100%\" >"
			+ "<TR  height=\"100%\">";
	for (var i = 0; i < weekDays.length; i++) {
		str += "<TD class=\"st-c st-s \"  rowSpan=2></td>";
	}
	str += "</tr>"
			+ "</TABLE>"
			+ "</div>"
			+ "<!--whole day event end -->"
			+ "</td>"
			+ "</tr>"
			+ "</table>"
			+ "</div>"
			+ "</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td>"
			+ "<DIV   class=\"wk-scrolltimedevents\" id=\"scrolltimedeventswk\" >"

			+ "<TABLE cellSpacing=0 cellPadding=0 closure_hashCode_tc9hoq=\"69\" id=\"table2\" class=\"table2\" >"
			+ "<TBODY>" + "<tr >" + "<td  width=\"75px;\">" + "</td>"
			+ "<td colspan=\"7\">" + this.getRowDraw() + "</td>" + "</tr>"
			+ "<TR>"

			+ "<TD  class=\"tg-times-pri\" width=\"75px;\">";

	for (var i = 0; i < this.timeSect.length; i++) {
		str += "<DIV style='height:"+(this.hourHeight-1)+"'  class=\"tg-time-pri\" >" + this.timeSect[i] + "</DIV>";
	}
	str += "</TD>";

	for (var i = 0; i < weekDays.length; i++) {
		var css = "";
		if (weekDays[i].getFullYear() == now.getFullYear()
				&& weekDays[i].getMonth() == now.getMonth()
				&& weekDays[i].getDate() == now.getDate()) {
			css = " class=\"td-time-pri-today\"";
		} else {

		}
		str += "<TD  id=\"td_"
				+ weekDays[i].getFullYear()
				+ "-"
				+ (weekDays[i].getMonth() < 9
						? ("0" + (weekDays[i].getMonth() + 1))
						: (weekDays[i].getMonth() + 1))
				+ "-"
				+ (weekDays[i].getDate() < 10
						? ("0" + weekDays[i].getDate())
						: weekDays[i].getDate()) + "\" " + css
				+ "   align=\"center\">";
		for (var j = 0; j < this.timeSect.length; j++) {
			// str += "<div class=\"dateDIV\" style='width:100%' >&nbsp;</div>";
			// str += "&nbsp";
		}
		str += "<div onclick=\"Instance('" + this.index
				+ "').createEvent(event) \"  class=\"div-tg-time\"></div></TD>";

	}
	str += "</tr></tbody></table></div></td></tr></table><b class=\"xb4\" style=\"width:1010px; \"></b><b class=\"xb3\" style=\"width:1007px; \"></b><b class=\"xb2\" style=\"width:1006px; \"></b><b class=\"xb1\" style=\"width:1002px; \"></b>";
	return str;
};
CalendarView.prototype.getCalendarEventDialogStr = function() {
	var str = "<div onclick=\"try{event.stopPropagation();}catch(e){event.cancelBubble=true;}\" id=\"calendarEventDialog\" class=\"calendarEventDialog\" style=\"display:none\"><table>"
			+ "<tr><td></td><td align=right><div onclick=\"Instance('"
			+ this.index
			+ "').closeCalendarEventDialog()\" class=\"bubbleclose\" style=\"width:15px;height:15px\" src=\"\"></div></td></tr>"
			+ "<tr><td><span>DATE:</span></td><td ><span id=\"calendarEventDialog_date\" ondblclick='showCalendar(this)'>123</span></td></tr>"
			+ "<tr><td><span>TITLE:</span></td><td><input type=\"text\" id=\"calendarEventDialog_title\"></input></td></tr>"
			+ "<tr><td><input type=\"button\" value=\"save\" onclick=\"Instance('"
			+ this.index
			+ "').saveCalendarEvent()\"></input></td><td>"
			+ "<input type=\"button\" value=\"delete\" onclick=\"Instance('"
			+ this.index
			+ "').deleteCalendarEvent()\"></input>"
			+ "</td></tr>"
			+ "</table></div>";
	return str;
};
//minicalendar
CalendarView.prototype.getCalendarStr = function() {

	var today_y = this.today.getFullYear()
	var today_m = this.today.getMonth() + 1;
	var today_d = this.today.getDate();

	var y = this.dummyDay.getFullYear();
	var m = this.dummyDay.getMonth() + 1;
	var b_thisMonth = false;
	if (y == today_y && m == today_m)
		b_thisMonth = true;

	// var d=this.date.getDate();
	var _weekFirstDay = CaculateWeekDay(y, m, 1);
	var _days = CaculateMonthDays(y, m);

	if (m == 1) {
		var pre_days = CaculateMonthDays(y - 1, 12);

	} else {
		var pre_days = CaculateMonthDays(y, m - 1);
	}
	var _weekLastDay = CaculateWeekDay(y, m, _days)
	var _index = new Number(0);
	var _date = new Number(1);
	var _rows = new Number(1);
	var str = "<table  cellspacing=0  onmousewheel=\"alert(1)\"	cellpadding=0 class=\"mcal_table\"  >"
			+ "<tr class='mcal_title_tr'  >"
			+ "<th id='mcal_title_fir_th' class='mcal_title_fir_th' colspan=5 ><span class=\"zippy-arrow\" unselectable=\"on\"></span><span> "
			+ y
			+ "年"
			+ m
			+ "月</span></th>"
			+ "<th > <span id='mcal_prev_span' class='mcal_prev_span'></span> </th>"
			+ "<th  > <span id='mcal_next_span' class='mcal_prev_span mcal_next_span'> </span></th>"
			+ "</tr>"
			+ "<tr class=\"mcal_week_title_tr\"><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th><th>七</th></tr>"
			+ "<tr>";
	var pre_mon;
	var pre_year;
	if (m == 1) {
		var pre_mon = 12;
		pre_year = y - 1;
	} else {
		pre_mon = m - 1;
		pre_year = y;
	}
	for (var i = 1; i < _weekFirstDay; i++) {

		str += "<td id=\"calendar-mini-" + pre_year + "-" + pre_mon + "-"
				+ (pre_days - _weekFirstDay + i + 1)
				+ "\" onclick=\"Instance('" + this.index
				+ "').selectDateTd(this)\" class=\"othermonth\">"
				+ (pre_days - _weekFirstDay + i + 1) + "</td>";
		_index++;
	}
	if (_index == 7)
		str += "</tr>";

	for (var i = 0; i < _days; i++) {
		if (_index % 7 == 0 && _index > 0) {
			str += "<tr>";
			_rows++;
		}
		str += "<td  "
				+ ((b_thisMonth && today_d == _date)
						? "class=\"todayTd \" "
						: "") + "  onclick=\"Instance('" + this.index
				+ "').clickHandle(event)\">"
				+ (_date < 10 ? ("&nbsp;" + _date) : _date) + "</td>";
		_index++;
		_date++;// td中的字符表示日期
		if (_index % 7 == 0)
			str += "</tr>";
	}
	var k = 1;
	if (m == 12) {
		m = 1;
		y++;
	} else {
		m++;
	}
	for (var i = _weekLastDay + 1; i <= 7; i++) {

		str += "<td id=\"calendar-mini-" + y + "-" + m + "-" + (k)
				+ "\" class=\"othermonth\" onclick=\"Instance('" + this.index
				+ "').selectDateTd(this)\">" + k + "</td>";
		k++;
	}

	str += "</tr>";

	if (_rows < 6) {
		str += "<tr>";
		for (var i = 0; i < 7; i++) {

			str += "<td id=\"calendar-mini-" + y + "-" + m + "-" + (k++)
					+ "\" class=\"othermonth\" onclick=\"Instance('"
					+ this.index + "').selectDateTd(this) \">" + (k - 1)
					+ "</td>";
		}
		str += "</tr>";
		_rows++;
	}
	str += "</table>";
	
	return str;
};

// get the view str end
CalendarView.prototype.displaySingleEvent = function(ce) {
	this.valueStack["event_" + ce.id] = ce;
	var title = ce.title;
	var date = ce.day;
	var timeStart = ce.startTime;
	var timeEnd = ce.endTime;
	//alert(ce.startTime);
	// 根据日期找到td 根据kssj 结束时间 找到div 根据结束时间决定buttom的值
	var td = document.getElementById("td_" + date);
	if ("undefined" == (typeof td) || td == null)
		return;
	
	var div_html=document.createElement("DIV");

	var dl_html = document.createElement("DL");
	div_html.appendChild(dl_html);
	
	dl_html.innerHTML = "<DT>"
			+ timeStart
			+ "~"
			+ timeEnd
			+ "</dt> "
			+ "<DD  >"
			+ title
			+ "</DD><div  onclick=\"\" /*onmouseup=\"alert(1);Drag.dragEnd(this);\"*/ onmousedown=\"Drag.drawStart(this)\">_</div>";

	td.childNodes[0].appendChild(div_html);

	// 修改id
	div_html.id = "event_" + ce.id;
	// 修改样式 确定位置
	div_html.className = "calendarEventBar";
	var position = this.timeSectToPosition(timeStart, timeEnd, date);
	div_html.style.position = "absolute";
	
	
	
	/*
	 * div_html.style.position = "absolute";
	div_html.style.top = data_arr[2];
	div.appendChild(div_html);

	// newEvent.style.top=div.style.top;
	// getInfo(div).left;
	div_html.style.left = 0;
	// newEvent.style.top =-38;
	div_html.style.height = this.hourHeight-2;
	 */
	div_html.style.top = position[0]-getInfo($$("table2")).top;
	div_html.style.height = position[1];
	div_html.style.left =0;
	//div_html.style.width = 10;
	//div_html.style.right = position[2] + position[3];
	div_html.attachEvent("onmousedown", function(event){Drag.dragStart(event.srcElement)},true);
	index = this.index;
	div_html.attachEvent("ondblclick", function() {
				Instance(index).openCalendarEventDialog(div_html);
			});
};
CalendarView.prototype.displayAllEvent = function(ces) {
	for (var ce in ces) {
		this.displaySingleEvent(ce);
	}
};

// 大的动作
// 可以拆分为多个连续性的一系列界面类动作---------------------------------------------------------------------------------------------

CalendarView.prototype.createEvent = function(e) {// 分解此方法
													// 该造成addEvent(calendarEvent)的方法

	// 得到当前鼠标位置

	var mouse_cur_p_x = event.clientX;
	var mouse_cur_p_y = event.clientY;

	var scrollTop = 0;
	if ($$("scrolltimedeventswk").scrollTop) {
		scrollTop = $$("scrolltimedeventswk").scrollTop;
	}
	var data_arr = this.getPositionFromMousePosition(mouse_cur_p_y, scrollTop);

	var top = data_arr[2];// 没用到
	// 先删除原来没有保存的
	this.deleteEventData("newEvent");

	// 界面的修改

	this.deleteEventBar("newEvent");

	e.cancelBubble = true;
	e = window.event || e;
	var B;
	e = e.srcElement || e.target;

	// 先设置它的position absolute 获取当前的div
	var div;
	var td;
	if (e.tagName == "DIV") {
		div = e;
		td = e.parentNode;
	} else if (e.tagName == "TD") {
		div = e.childNodes[0];
		td = e;
	} else

	{
		return;
	}
	var timeStart = data_arr[1];
	// console.log("timeStart:"+timeStart);
	var timeEnd = data_arr[0];// getElementsByTagName("div")
	/*
	 * var i; for ( i = 0, divs = td.childNodes; i < divs.length; i++) { if (div ==
	 * divs[i]) { timeStart = this.timeSect[i]; timeEnd = (i + 1) ==
	 * this.timeSect.length ? "24:00" : this.timeSect[i + 1]; break; } }
	 */
	this.currentEventId = "newEvent";
	var div_html=document.createElement("DIV");
	div_html.id = "event_newEvent";
	div_html.className = "calendarEventBar";
	var dl_html = document.createElement("DL");
	div_html.appendChild(dl_html);
	//dl_html.id = "event_newEvent";
	//dl_html.className = "calendarEventBar";
	dl_html.innerHTML = "<DT >"
			+ timeStart
			+ "~"
			+ timeEnd
			+ "</dt> "
			+ "<DD >&nbsp;</DD><div   onmouseup=\"Drag.dragEnd();\" onmousedown=\"Drag.drawStart(this)\">_</div>";

	// 创建新的event
	var ce = new calendarEvent();
	// document.createElement("div");

	ce.id = "newEvent";
	ce.title = "";
	ce.timeStart = timeStart;
	ce.timeEnd = timeEnd;

	ce.j = data_arr[3];
	ce.day = td.id.substr(3);
	// 入栈
	this.valueStack["event_newEvent"] = ce;

	div_html.style.position = "absolute";
	div_html.style.top = data_arr[2];
	div.appendChild(div_html);

	// newEvent.style.top=div.style.top;
	// getInfo(div).left;
	div_html.style.left = 0;
	// newEvent.style.top =-38;
	div_html.style.height = this.hourHeight-2;
	// td.appendChild(newEvent);
	var index = this.index;// ?
	this.showCalendarEventDialog(this.currentEventId);
	dl_html.attachEvent("ondblclick", function() {

				Instance(index).openCalendarEventDialog(div_html)
			});
	// onclick=\"Instance('" + this.index
	// + "').editCalendarEvent(this.id)\"
	div_html.attachEvent("onmousedown", function() {
				Drag.dragStart(div_html);
			});

	// 维护calendareventdata
	ce.startIndex = data_arr[3];
	ce.endIndex = data_arr[3] + 2;
	// ce.cellIndex=0;
	var parentTable = $$("table2").childNodes[0].childNodes[1];

	var mouseCurrentCell;
	for (i = 1; i < parentTable.cells.length; i++) {

		if (div == parentTable.cells[i].childNodes[0]) {

			mouseCurrentCell = i;

			break;
		}
	}
	// alert(mouseCurrentCell);
	ce.cellIndex = mouseCurrentCell;
	// ce.endIndex=0;
	ce.top = data_arr[2] - 18;
	ce.left = 0;
	ce.length = 2;
	// alert("开始："+ce.startIndex+"结束"+ce.endIndex);
	// this.adjust();
};
CalendarView.prototype.rejustPositionAndShape = function(calendarEvent) {

};
CalendarView.prototype.mcalGoPrevMonth = function(e) {
	this.goPreMonth();
	this.refreshView();
	
}
CalendarView.prototype.mcalGoNextMonth = function(e) {
	this.goNextMonth();
	this.refreshView();
}
CalendarView.prototype.clickHandle = function(e) {
	// //console.log("进入clickhandle");
	e = window.event || e;
	var B;
	e = e.srcElement || e.target;

	switch (e.tagName) {
		/*case "TH" :// 选择月份
		{
			if (0 <= e.innerHTML.indexOf("<<")
					|| 0 <= e.innerHTML.indexOf("&gt;&gt;")) {
				this.goNextMonth();
				this.refreshView();
			} else if (0 <= e.innerHTML.indexOf(">>")
					|| 0 <= e.innerHTML.indexOf("&lt;&lt;")) {
				this.goPreMonth();
				this.refreshView();
			}
		}
			break;*/
		case "TD" : // 选择日期
		{
			var bToday = (this.selectedDay != null
					&& this.selectedDay.getFullYear() == this.today
							.getFullYear()
					&& this.selectedDay.getMonth() == this.today.getMonth() && this.selectedDay
					.getDate() == this.today.getDate());
			// edit the value of dummyDay and selectedDay
			// edit the css previous selected Day and edit the current selected
			// Day
			// if previous selected day == selected Day selectedMode change
			// if
			this.selectedDay = this.dummyDay;
			this.selectedDay.setDate(new Number(e.innerHTML.replace("&nbsp;",
					"")));
			this.dummyDay = this.selectedDay;

			var curSelectedTd = e;
			var curSeletedTr = (e.parentNode ? e.parentNode : null);

			if (null != this.preSelectedTd
					&& "undefined" != (typeof this.preSelectedTd)) {
				if (curSelectedTd == this.preSelectedTd) {
					this.selectMode = !this.selectMode;
				}
			}
			// cancel css
			if (null != this.preSelectedTd
					&& "undefined" != (typeof this.preSelectedTd)) {

				this.preSelectedTr.className = "";

				this.preSelectedTd.className = (!bToday ? "" : "todayTd");

			}
			// add css

			curSelectedTd.className = "selectedTd";
			curSeletedTr.className = (this.selectMode ? "selectedTr" : "");
			// appointed td tr
			this.preSelectedTr = e.parentNode;
			this.preSelectedTd = e;
			this.refreshCalendarEventView();
		}
			break;
		default :

			break;
	}
};
CalendarView.prototype.onMouseOverHandle = function(e) {
};
CalendarView.prototype.onDblClickHandle = function(e) {

};
CalendarView.prototype.changeToDayView = function() {
	this.viewMode = 0;
	this.refreshCalendarEventView();
};
CalendarView.prototype.selectDateTd = function(it) {
	var y = it.id.split("-")[2];
	var m = it.id.split("-")[3];
	var d = it.id.split("-")[4];

	var day = new Date();
	day.setYear(y);
	day.setMonth(m - 1);
	day.setDate(d);
	this.dummyDay = day;
	this.selectDate(day);
	this.refreshCalendarEventView();

};
CalendarView.prototype.selectDate = function(day) {
	// 重新设定dummyday selectedDay 重新
	this.dummyDay = day;
	this.selectedDay = day;
	this.refreshView();

};

CalendarView.prototype.saveCalendarEvent = function(event) {
	
	// 数据修改
	var ce = this.getCalendarEvent(this.currentEventId);
	ce.title = $$("calendarEventDialog_title").value;
	if (ce.id == "newEvent") {

		this.currentEventId = this.getEventId();
		delete this.valueStack["event_newEvent"];
		ce.id = this.currentEventId;
		$$("event_newEvent").id = "event_" + ce.id;
	}
	
	this.saveCalendarEventData(ce);

//	this.valueStack["event_" + this.currentEventId] = ce;
	// alert(ce.title);
	// 界面修改
	this.refreshCalendarEventBar(this.currentEventId);
	this.closeCalendarEventDialog(this.currentEventId);
	// ajax保存数据
	
};
function isSuccessFully(data){
	if(data.RESULT){
		alert("保存成功");
	}else{
		alert("保存失败");
	}
}
CalendarView.prototype.editCalendarEvent = function(it) {// alert(231);

	// 数据修改
	var ce = this.getCalendarEvent(it.id);
	$$("calendarEventDialog_title").value = ce.title;
	this.showCalendarEventDialog(this.currentEventId);
	// 显示编辑框
	this.refreshCalendarEventDialog(this.currentEventId);

};

CalendarView.prototype.deleteCalendarEvent = function(event) {
	// 数据的修改

	this.deleteEventData(this.currentEventId);

	// 界面的修改

	this.deleteEventBar(this.currentEventId);
	this.closeCalendarEventDialog();
};

CalendarView.prototype.alert = function() {
	var a = this.positionToTimesect(event.clientY, 200);
	// alert("timeStart:"+a[0]+"timeENd:"+a[1]);
};
CalendarView.prototype.openCalendarEventDialog = function(it) {
	// get the dl element's id
	/*
	 * e = window.event || e; var B; e = e.srcElement || e.target; //
	 * 先设置它的position absolute 获取当前的div // 如果e.tagName="div"的话可能是新建 if (e.tagName ==
	 * "DD" || e.tagName == "DT") { e = e.parentNode; } else if (e.tagName ==
	 * "DL") {
	 *  } else { return; }
	 */
	this.currentEventId = it.id.substr(6);
	var ce = this.getCalendarEvent(this.currentEventId);
	$$("calendarEventDialog_date").innerHTML = ce.day + " " + ce.timeStart + "~"
			+ ce.timeEnd;
	$$("calendarEventDialog_title").value = ce.title;
	// get event information from stack
	// select the current event
	// edit the content of calendarEventDialog2
	this.showCalendarEventDialog(it.id);
};

CalendarView.prototype.changeToMonthView = function() {
	this.viewMode = 2;
	this.refreshCalendarEventView();
};
CalendarView.prototype.changeToWeekView = function() {
	this.viewMode = 1;
	this.refreshCalendarEventView();
};

// 界面的刷新--------------------------------------------------------------------------------------------------------------------------

CalendarView.prototype.refreshView = function() {
	$$("mcal_div" ).innerHTML = this.getCalendarStr();
	this.clear();
	this.init();
	
	

};
CalendarView.prototype.refreshCalendarEventView = function() {
	$$("div_CalendarEventView_" + this.index).innerHTML = this
			.getCalendarScheduleStr();
	document.getElementById("scrolltimedeventswk").style.height = window.innerHeight
			- 120;
};
// data2view

CalendarView.prototype.refreshCalendarEventBar = function(id) {

	// 的到event从 堆栈中
	// 如果id对应的HTML对象存在的话 更新内容 并 重新定位位置
	if ($$("event_" + id)) {
		var ce = this.getCalendarEvent(id);
		if (ce == null || (typeof ce) == "undefined")
			return;
		var dts = $$("event_" + id).getElementsByTagName("DT");
		if (dts != null && dts != "undefined" && dts.length && dts.length > 0) {
			dts[0].innerHTML = ce.timeStart + "~" + ce.timeEnd;
		}
		var dds = $$("event_" + id).getElementsByTagName("DD");
		if (dds != null && dds != "undefined" && dds.length && dds.length > 0) {
			dds[0].innerHTML = ce.title;
		}
		// 开始重新定位
		var start_index = 0;
		var end_index = 0;
		// 以下的计算和drag里的代码重复了
		for (var i = 0; i < this.timeSect.length; i++) {
			if (ce.timeStart >= this.timeSect[i]
					&& (ce.timeStart < this.timeSect[i + 1] || i == (this.timeSect.length - 1))) {
				start_index = i;
			}
			if (ce.timeEnd > this.timeSect[i]
					&& (ce.timeStart <= this.timeSect[i + 1] || i == (this.timeSect.length - 1))) {
				end_index = i;
			}
		}
		// var aDivTimeSect = parentTable.cells[0].getElementsByTagName("DIV");
		// var aDivTimeSect = parentTable.cells[0].getElementsByTagName("DIV");
		// $$("event_"+id).style.top= aDivTimeSect[start_index].offesetTop;
		// $$("event_"+id).style.bottom= aDivTimeSect[end_index+1].offsetTop;
	} else {
		// 创建新的dl对象 并定位
	}

};
CalendarView.prototype.refreshCalendarEventDialog = function(id) {
	// 的到event从 堆栈中
	var ce = this.getCalendarEvent(id);
	
	if (ce == null)// || (typeof event) == "undefined"
		return;
	
	$$("calendarEventDialog_date").innerHTML = ce.day + " " + ce.timeStart + "~"
			+ ce.timeEnd;
	$$("calendarEventDialog_title").value = ce.title;
};
// 界面的调整变化 在动作的驱动下 界面类动作
// ------------------------------------------------------------------------------------------
CalendarView.prototype.showCalendarEventDialog = function(id) {
	// 增加判断使他最低不得超过table2的父亲div的下底线和左右边界还有上边界
	this.refreshCalendarEventDialog(id);
	$$("calendarEventDialog").style.display = "";

	// $$("bb").value= getInfo($$("event_"+id)).top;
	// $$("cc").value= getInfo($$("event_"+id)).left;
	// $$("ee").value= $$("calendarEventDialog").style.height;
	// alert($$("calendarEventDialog").offsetHeight);
	// 获取 scroll的高度
	var scrollTop = 0;
	if ($$("scrolltimedeventswk").scrollTop) {
		scrollTop = $$("scrolltimedeventswk").scrollTop;
	}
	//if (id == "newEvent")
	//	id = "event_newEvent";
	var eventBarInfo=null;
	var event_id="";
	if(!isNull(id)&&id.substr(0,6)!="event_"){
		event_id = "event_"+id;
	}else{
		event_id = id;
	}
	eventBarInfo=getInfo($$(event_id));
	// $$("calendarEventDialog").style.top =
	// eventBarInfo.top-$$("calendarEventDialog").offsetHeight-scrollTop;
	$$("calendarEventDialog").style.left = eventBarInfo.left - 70;
	
	var bottom = eventBarInfo.top - $$("calendarEventDialog").offsetHeight
			- scrollTop;
	var table2ParentInfo = getInfo($$("scrolltimedeventswk"));
	if ((table2ParentInfo.bottom) < (eventBarInfo.top - scrollTop)) {
		$$("calendarEventDialog").style.top = table2ParentInfo.bottom
				- $$("calendarEventDialog").offsetHeight;
	} else {
		$$("calendarEventDialog").style.top = eventBarInfo.top
				- $$("calendarEventDialog").offsetHeight - scrollTop;

	}

};

CalendarView.prototype.closeCalendarEventDialog = function() {
	if (this.currentEventId == "newEvent") {
		this.deleteEventBar("newEvent");
	}
	$$("calendarEventDialog").style.display = "none";
	$$("mask").style.display = "none";
};
CalendarView.prototype.deleteEventBar = function(id) {
	if ($$("event_" + id)) {
		$$("event_" + id).innerHTML = "";
		try {
			$$("event_" + id).removeNode();
		} catch (e) {
			$$("event_" + id).parentNode.removeChild($$("event_" + id));
		}
	}
};
// ----------------------------------------------------------------------------------------------------------------------------
// 数据的初始化------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CalendarView.prototype.clear = function() {
	this.preSelectedTd = null;
	this.preSelectedTr = null;
	this.preMouseOverTd = null;
	this.selectedDay = new Date();
};
// 数据的修改
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CalendarView.prototype.saveCalendarEventData = function(ce) {
	this.valueStack["event_" + ce.id] = ce;
	var jso=translateCE2Activity(ce);
	// activityAjax.saveCalendarEvent(activity ,isSuccessFully);
	AjaxFun("activityAjax.saveActivity",jso,this.isSuccessFully);
};
// 数据的删除
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CalendarView.prototype.deleteEventData = function(id) {
	delete this.valueStack["event_" + id];
};
// 数据的得到------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// 根据坐标求时间区间

CalendarView.prototype.getCalendarEvent = function(id) {
	return this.valueStack["event_" + id];
};
CalendarView.prototype.getEventId = function() {
	var date = new Date();
	var id = "" + date.getFullYear() + (date.getMonth() + 1) + date.getDate()
			+ parseInt(Math.random() * 1000);
	return id;
};

CalendarView.prototype.positionToTimesect = function(top, height) {
	var start_index, end_index, timeStart, timeEnd;
	var parentTable = $$("table2").childNodes[0].childNodes[1];
	var DivTimeSects = parentTable.cells[0].getElementsByTagName("DIV");
	if (DivTimeSects.length != this.timeSect.length) {
		alert("error:aDivTimeSect's length not equal timeSect's!");
		return;
	}
	var _length = DivTimeSects.length;
	for (var i = 0; i < _length; i++) {
		if (getInfo(DivTimeSects[i]).top <= top
				&& getInfo(DivTimeSects[i]).bottom > top) {
			start_index = i;
		}
		if (getInfo(DivTimeSects[i]).top <= (top + height)
				&& getInfo(DivTimeSects[i]).bottom > (top + height)) {
			end_index = i;
		}
	}

	if (end_index <= start_index) {
		alert("error :end_index<=start_index! start_index:" + start_index
				+ " end_index:" + end_index);
	}

	timeStart = this.timeSect[start_index];
	timeEnd = end_index >= _length ? "24:00" : this.timeSect[end_index + 1];
	var a = new Array();
	a[0] = timeStart;
	a[1] = timeEnd;
	return a;
};
/**
 * 根据时间换算出top 高度值
 */
CalendarView.prototype.timeSectToPosition = function(timeStart, timeEnd, date) {
	
	var start_index, end_index, top, height;
	for (var i = 0, _length = this.timeSect.length; i < _length; i++) {
		if (this.timeSect[i] <= timeStart
				&& (i >= (_length - 1) ? "24:00" : this.timeSect[i + 1]) > timeStart) {
			start_index = i;
		}
		if (this.timeSect[i] < timeEnd
				&& this.timeSect[i >= (_length - 1) ? (i + 1) : (_length - 1)])
			end_index = i;
	}var parentTable = $$("table2").childNodes[0].childNodes[1];
	
	var divTimeSects = parentTable.cells[0].getElementsByTagName("DIV");
	
	top = getInfo(divTimeSects[start_index]).top;
	height = getInfo(divTimeSects[end_index]).bottom - top;
	left = getInfo($$("td_" + date)).left;
	width = getInfo($$("td_" + date)).twidth;
	//$$("aa").value = top + "aaa " + height;
	var a = new Array();
	a[0] = top;
	a[1] = height;
	a[2] = left;
	a[3] = width;
	return a;
};

CalendarView.prototype.drawClanderEventTo = function(ce, x, tY, ao, scrollTop) {
	
	var tr1 = $$("table2").childNodes[0].childNodes[1];
	var aDivTimeSect = tr1.cells[0].childNodes;
	var j = 1;
	// 鼠标当前位置 相对于td的顶端
	var y_distance = tY + scrollTop - Drag.getInfo(aDivTimeSect[0]).top;
	// 移动到了第几段位
	var timesect_index = parseInt(y_distance*2 /this.hourHeight);
	//有疑问 高度应该是小时相减*60再加上分钟的差别
	ao.style.height = (timesect_index + 1) *this.hourHeight/2 - getTop(ce.timeStart, this.hourHeight)-2;
	// alert(count_timesect);
	ce.timeEnd = this.timeCompute1(timesect_index + 1);

	// ce.j=count_timesect;
	this.refreshCalendarEventBar(ce.id);

	// 重置calendarEvent 数据
	ce.endIndex = timesect_index + 1;
	ce.height = ao.style.height;
	ce.length = ce.endIndex - ce.startIndex;

	return true;
	/*
	 * for ( j = 0; j < aDivTimeSect.length; j++) {
	 * 
	 * var parentCell = Drag.getInfo(aDivTimeSect[j]);// 已经知道了是哪个div time //
	 * sect
	 * 
	 * if ((tY+scrollTop ) < parentCell.bottom && (tY+scrollTop) >=
	 * parentCell.top) { //如果没有移除格子外面就不需要change if(ce.j>j)return false;
	 * 
	 * ao .style.height= parentCell.bottom- Drag.getInfo( Drag.ao ).top;
	 * ce.timeEnd = this.timeSect[j+1]; //ce.j=j;
	 * this.refreshCalendarEventBar(ce.id); return true;
	 *  } }
	 */
};

/**
 * 拖动event到某个时间段
 * x:鼠标的位置 ty鼠标的坐标 scrolltop滚动条的位置 ao：event的div dom ce 对应的event模型
 */
CalendarView.prototype.moveClanderEventTo = function(ce, x, tY, ao, scrollTop) {
	// 首先获得鼠标移动到了第几列
	var table2=$$("table2");
	var tbody= table2.childNodes[0];
	var tr1=tbody.childNodes[1];
	var td=null;
	var mouseCurrentCell;
	for (i = 1; i < tr1.cells.length; i++) {
		td = Drag.getInfo(tr1.cells[i]);
		if (x >= td.left && x <= td.right) {

			mouseCurrentCell = i;
			if (ce.i != mouseCurrentCell) {
				ce.day = global_weekdays[i - 1].getFullYear()
						+ "-"
						+ (global_weekdays[i - 1].getMonth() < 9
								? ("0" + (global_weekdays[i - 1].getMonth() + 1))
								: (global_weekdays[i].getMonth() + 1))
						+ "-"
						+ (global_weekdays[i - 1].getDate() < 10
								? ("0" + global_weekdays[i - 1].getDate())
								: global_weekdays[i - 1].getDate());

				ce.i = mouseCurrentCell;
			}
			break;
		}
	}

	// 获取时间段div
	var aDivTimeSect = tr1.cells[0].childNodes;
	var mouseCurrentRow;

	if (ce.j != count_timesect) {
		// 获得当前鼠标y轴位置 再根据滚动条的当前位置 即可得出当前在哪个时间段
		var y_distance = tY + scrollTop - Drag.getInfo(aDivTimeSect[0]).top;
		
		var count_timesect = parseInt(y_distance*2 / this.hourHeight);
	
		ao.style.top = count_timesect *  this.hourHeight/2;
		// console.log("ao.style.top"+ao.style.top);
		mouseCurrentRow = count_timesect;
		
		// 计算偏移量
		var deviation = count_timesect - ce.j;
		// 重新计算timestart 和 timeend
		ce.timeStart = this.timeCompute1(count_timesect);
		ce.timeEnd = this.timeCompute1(count_timesect+1);
		ce.j = count_timesect;
	}

	var aDivTimeSect1 = tr1.cells[i].childNodes;

	aDivTimeSect1[0].appendChild(ao);
	// 折算出 转换后的开始时间和结束时间。

	// 更新外表
	this.refreshCalendarEventBar(ce.id);
	// 重置calendarEvent 数据
	ce.startIndex = mouseCurrentRow;
	ce.endIndex = mouseCurrentRow + ce.length;
	ce.cellIndex = mouseCurrentCell;

	;
	// this.adjust();
	this.adjust(mouseCurrentCell, ce);
};

CalendarView.prototype.getPositionFromMousePosition = function(tY, scrollTop) {
	// 获取时间段div
	var table2=$$("table2");
	var tbody= table2.childNodes[0];
	var tr1=tbody.childNodes[1];
	var td=null;
	var aDivTimeSect = tr1.cells[0].childNodes;
	var j = 0;
	var timeStart = "00:00";
	var timeEnd = "00:00";// getElementsByTagName("div")
	var pianyiliang;
	for (j = 0; j < aDivTimeSect.length; j++) {
		var td_info = Drag.getInfo(aDivTimeSect[j]);// 已经知道了是哪个div time
		// sect

		if ((tY + scrollTop) < td_info.bottom
				&& (tY + scrollTop) >= td_info.top) {

			if ((tY + scrollTop) < ((td_info.bottom + td_info.top) / 2)) {
				pianyiliang = td_info.top
						- Drag.getInfo(aDivTimeSect[0]).top;

				timeEnd = (j + 1) == this.timeSect.length
						? "24:00"
						: this.timeSect[j + 1];
				timeStart = this.timeSect[j];
				j = 2 * j;
			} else {
				pianyiliang = ((td_info.bottom + td_info.top) / 2 - Drag
						.getInfo(aDivTimeSect[0]).top);
				timeEnd = (j + 1) == this.timeSect.length
						? "24:00"
						: (this.timeSect[j + 1].substr(0, 3) + "30");
				timeStart = this.timeSect[j].substr(0, 3) + "30";
				j = 2 * j + 1;
			}
			break;
		}
	}
	return [timeEnd, timeStart, pianyiliang, j];// 偏移量 距离td的顶端的距离 j为第几格子 半小时一个格子

};
/**
 * 根据开始时间和间隔时间区间段判断时间
 */
CalendarView.prototype.timeCompute = function(time, timesect) {
	// alert("0:"+time);

	var hour = parseInt(time.substr(0, 2), 10);
	var minute = parseInt(time.substr(3, 2), 10);
	var date = new Date();
	// alert( "1:"+minute);
	date.setHours(hour, minute, 0, 0);
	// alert("3:"+(minute+parseInt(timesect,10)*30));
	date.setMinutes(minute + parseInt(timesect, 10) * 30);

	hour = date.getHours();
	// alert( "2:"+date.getHours());
	minute = date.getMinutes();

	var str = (parseInt(hour, 10) < 10 ? ("0" + hour) : hour) + ":"
			+ (minute < 10 ? ("0" + minute) : minute);

	return str;
};
/**
 * 根据格子数判断时间
 */
CalendarView.prototype.timeCompute1 = function(timesect) {

	var date = new Date();

	date.setHours(0, 0, 0, 0);
	date.setMinutes(parseInt(timesect, 10) * 30);
	hour = date.getHours();

	minute = date.getMinutes();

	var str = (parseInt(hour, 10) < 10 ? ("0" + hour) : hour) + ":"
			+ (minute < 10 ? ("0" + minute) : minute);

	return str;
};

CalendarView.prototype.adjust = function(nowColumn, ce) {

	// bubbleSort

	var nowColumn = nowColumn;// 当前列
	var preColumn = nowColumn;// 之前列
	var currentEvent = ce;// 当前拖曳或新增的块 内涵当前的日期 starttime endtime
	var eventArr = new Array(ce);
	// event=ce;
	if (nowColumn == preColumn) {
		// nowColumn
		{// 取出所有在此列的event
			for (pop in this.valueStack) {
				if (this.valueStack[pop].cellIndex == nowColumn) {
					// this.valueStack[pop].index=-1;
					if (this.valueStack[pop].id != event.id)
						eventArr.push(this.valueStack[pop]);
				}
			}
			/*
			 * var globalNewEvent=new Array(currentEvent); var
			 * leftEvents=eventArr; var relatedEvents=new Array(currentEvent);
			 * ////console.log("currentEvent"+currentEvent+"relatedEvents.length"+relatedEvents.length);
			 * 
			 * globalNewEvent=chongxinfenbu(globalNewEvent,relatedEvents,leftEvents);
			 * //console.log("关联的event个数为"+globalNewEvent);
			 */
			// 如果联系的块只有单独一个就没必要进行算法
			if (eventArr.length == 1)
				return;

			this.VampireAlgorithm(eventArr);
		}
		// preColumn
		{

		}
	} else {
		// nowColumn
		{

		}
	}

};
/*
 * 吸血鬼算法，假设有N个未被感染的人类 ，而生态圈个数可以无限增长，我们先选取一个人类作为吸血鬼，然后遍历剩下的N-1个人类，
 * 任何和第一个吸血鬼接触的人类都会变成吸血鬼，而没有和之前定型的接触的都会成为新种群的吸血鬼的开拓者拥有独立的阶级标志0，
 * 后面的累计+1；如果新成员和第一代接触就成为
 * 该总群吸血鬼的二代份子，如果该种群已经发展到了3代，但是新接触的成员和1带接触了，那他还是2代成员，该种群的代数不增长如果，
 * 
 * 一个新成员同时被多个种群接触，那么他的主种群是代数最多的那个，他的形状和代数也由那个种群所决定， 其他与他接触的种群接触法则就显得复杂了：
 * 如果一个代数是2的种群 的第二代和这个新成员接触了，那么代数要增加，他也是这个种群的第三代份子
 * 
 * 
 * 但是不影响这个新个体。当所有的人类全变成了吸血鬼，那么事情就结束了。这个时候我们可以计算出共有多少个种群的吸血鬼（无用），
 * 还有每个成员所属于的种群的总代数（三世同堂，孤家寡人，四世同堂），以及他们所属的种群中的辈分,而这个就是我们所要的最终数据
 * 
 * 补充： 如果一个
 * 
 * 堆叠算法 把所有的砖块先平铺摆开 这样的话呈现的是关于序号和 y轴高度的坐标图 然后我们把1号砖列为基准 放置第二块砖 如果第二块砖和第一块相加则
 */
CalendarView.prototype.VampireAlgorithm = function(vampires) {
	// 按照top的高度重新排序vampires
	// 设置top
	// console.log("进入VampireAlgorithm的vampires个数为"+vampires.length);
	// 冒泡
	vampires = bubbleSort(vampires);
	console.log("排序后"+vampires[0].title);
	this.vampires = vampires;
	// 开始层叠
	var sects = new Array();
	var curr_generation = 0;
	var generation_arr = new Array();
	var left_vampires = new Array();
	vampires[0].generation = 0;
	// console.log("title:"+vampires[0].title);
	generation_arr[0] = new Array(vampires[0]);
	 //console.log("before slice vampireslength:"+vampires.length);
	left_vampires = vampires.slice(1);
	// console.log("after slice vampireslength:"+left_vampires.length);
	// 给每个块赋值代数
	while (true) {// 代数遍历
		if(curr_generation==1){
			console.log("curr_generation==1");
		}
		if (generation_arr[curr_generation] == null) {
			// 初始化
			generation_arr[curr_generation] = new Array();
		}
		// var temp = left_vampires.slice(0);
		var the_index_of_choose_arr = new Array();
		for (var i = 0; i < left_vampires.length; i++) { // 保证对所有块进行遍历

			if (left_vampires[i] == null)
				continue;
			var flag = true;// 是否需要单独创建基地
			for (var j = 0, _length = generation_arr[curr_generation].length; j < _length; j++) {// 和这一层的所有
																									// 除自己以外的基块做比较
				// console.log("进入isTouchSide"+left_vampires[i]);
				if (generation_arr[curr_generation][j] == null)
					alert("bug");

				if (isTouchSide(left_vampires[i],
						generation_arr[curr_generation][j])) {
					flag = false;// console.log("flag1111111111111"+flag);
					break;
				}
			}// console.log("flag"+flag);
			if (flag) {

				left_vampires[i].generation = curr_generation;// console.log(curr_generation);
				generation_arr[curr_generation].push(left_vampires[i]);
				// left_vampires[i].generation=curr_generation;
				// 判断所属那根柱子
				// left_vampires[i]=null;
				the_index_of_choose_arr.push(i);
			}

		}
		// 对 left_vampires重新整理
		//console.log("the_index_of_choose_arr"+the_index_of_choose_arr);
		// console.log("left_vampires.length"+left_vampires.length);
		// console.log("left_vampires.length"+left_vampires.length+"choose arr"
		// +the_index_of_choose_arr);
		// console.log(left_vampires);
		// console.log(left_vampires[1]);
		// console.log(left_vampires[0]);alert(1);
		// console.log(left_vampires);
		 //console.log("||||||||||||||||||||||||||||||||||||||||||||||||||");
		for (var i = the_index_of_choose_arr.length - 1; i >= 0; i--) {
			// console.log("111111111111111111111111111111111111111111111111111");
			// console.log(i+"splice number:"+(the_index_of_choose_arr[i]));
			// console.log(left_vampires);
			// console.log("the_index_of_choose_arr"+the_index_of_choose_arr+"curr_generation"+curr_generation+"left_vampires.length"+left_vampires.length);
			left_vampires.splice(the_index_of_choose_arr[i], 1);
			// console.log("the_index_of_choose_arr"+the_index_of_choose_arr+"curr_generation"+curr_generation+"left_vampires.length"+left_vampires.length);
			
			// console.log(left_vampires);
			// console.log("2222222222222222222222222222222222222222222222222");
			// if(i==5) break;
		}
		// console.log("-------------------------------------------");
		// console.log("hahaleft_vampires.length"+left_vampires.length);
		// console.log("left_vampires.length"+left_vampires.length);
		if (left_vampires.length == 0 || left_vampires == null)
			break;
		// console.log("curr_generation:"+curr_generation);
		// if(curr_generation==5)break;
		curr_generation++;
		// console.log("curr_generation++"+curr_generation);
	}
	// console.log("generation_arr.length"+generation_arr.length);//console.log(generation_arr.length);
	// 对generation 逆序遍历
	// var temp_arr=generation_arr[curr_generation].slice(0);
	// if(curr_generation==0)return; //console.log(generation_arr[1]);
	// console.log("curr_generation"+curr_generation);
	// console.log("generation_arr[0].length"+generation_arr[0].length);
	// console.log("curr_generation"+curr_generation);

	// console.log(vampires);
	for (var i = curr_generation; i > 0; i--) {
		// 每次染色都要从vampires中删除它
		var currentVampires = generation_arr[i];
		var parentVampires = generation_arr[i - 1];
		for (var j = 0; j < currentVampires.length; j++) {// console.log("currentVampires[j].MaxRepeatedCount"+currentVampires[j].MaxRepeatedCount);
			if (i == curr_generation) {// currentVampires[j].MaxRepeatedCount==0||currentVampires[j].MaxRepeatedCount==null
			// console.log("currentVampires.generation"+currentVampires[j].generation);
				currentVampires[j].MaxRepeatedCount = i;
				// console.log("i:"+i);
				// console.log("currentVampires[j].MaxRepeatedCount"+currentVampires[j].MaxRepeatedCount);
			}
			// console.log("parentVampires.length"+parentVampires.length);
			for (var k = 0; k < parentVampires.length; k++) {// console.log("parentVampires[k]"+k+parentVampires[k]);
				if (isTouchSide(currentVampires[j], parentVampires[k])) {
					if (currentVampires[j].MaxRepeatedCount > parentVampires[k].MaxRepeatedCount)
						parentVampires[k].MaxRepeatedCount = currentVampires[j].MaxRepeatedCount;
				}
			}
		}
	}
	// console.log(vampires);
	// 纠正每个的宽度和左边距；
	// console.log("vampires.lengt"+vampires.length);
	for (var i = 0; i < vampires.length; i++) {
		if (vampires[i].MaxRepeatedCount != 0){
			$$("event_" + vampires[i].id).style.width = parseInt(100
							/ (vampires[i].MaxRepeatedCount + 1) + 20, 10)
					+ "%";
		}
		else{
			
			$$("event_" + vampires[i].id).style.width = this.EventWidth;
		}
		
		if(vampires[i].generation!=0){
			$$("event_" + vampires[i].id).className="calendarEventBar calendarEventBar_side";
			
		}else{
			$$("event_" + vampires[i].id).className="calendarEventBar";
		}
		// console.log(i);
		// console.log("hjasdf"+vampires[i].id+"hhh:"+(vampires[i].MaxRepeatedCount!=0)+":"+vampires[i].MaxRepeatedCount);

		// 计算left
		$$("event_" + vampires[i].id).style.zIndex = vampires[i].generation;

		if (vampires[i].MaxRepeatedCount != 0) {
			$$("event_" + vampires[i].id).style.left = (100 - 120
					/ (vampires[i].MaxRepeatedCount + 1))
					/ (vampires[i].MaxRepeatedCount) * vampires[i].generation;
			// console.log((100-120/(vampires[i].MaxRepeatedCount+1))/(vampires[i].MaxRepeatedCount)*vampires[i].generation);
		}
		// alert(calendarEvent.index);
		// $$("event_"+calendarEvent.id).style.left=calendarEvent.index*10;
		else {
			$$("event_" + vampires[i].id).style.left = 0;

		}
		$$("event_" + vampires[i].id).setAttribute("z-index",
				vampires[i].generation);

	}

};
/*
CalendarView.prototype.adjustNew = function() {
	// 现在的算法太复杂了，其实首先知道他现在移动到第几列了 那么最多他会影响两列 再分析这两列中有哪些被占用的块 对这些块进行分析效率要高的多

	for (var currentCell = 1; currentCell <= 7; currentCell++) {
		// var tr=$$("table2").childNodes[0].childNodes[1];
		// var td=tr.cells[1];
		// var div_container= td.childNodes[0];

		// var div_childs= div_container.childNodes;
		var array = new Array(48);
		// var array2 = new Array(48);
		for (var i = 0; i < 48; i++) {
			array[i] = 0;
			// array2[i]=0;
		}

		// 不应该按照div_childs的顺序来排 应该按照 时间段来排 花的时间长
		// 计算width
		
		 * var max_number_array=new Array(); for(var i =0;i<div_childs.length;i++){
		 * var max_number=0; var begin_index=Math.round(parseInt(
		 * div_childs[i].style.top,10)/20.5+1); var
		 * end_index=begin_index+Math.round(parseInt(div_childs[i].style.
		 * height,10)/20.5); for(var j=begin_index;j<end_index;j++){
		 * if(array[j]>max_number){max_number=array[j];
		 * max_number_array[j]=max_number;}
		 * }//$$("cc").value=parseInt(100/max_number,10)+"%";
		 * 
		 * div_childs[i].style.width=parseInt(100/max_number+20,10)+"%";
		 * 
		 * if(max_number==1){alet(1); div_childs[i].style.width=100%; }
		 *  }
		 

		// 要重新计算left距离和index
		// 根据当前的列数得出当前的calendarevent 数组；

		// 通过原型遍历属性
		// 取出当列的所有日历事件
		var calendarEventArray = new Array();

		for (pop in this.valueStack) {// alert(this.valueStack[pop].cellIndex);
			// console.log("this.valueStack[pop].cellIndex"+this.valueStack[pop].cellIndex);
			if (this.valueStack[pop].cellIndex == currentCell) {
				this.valueStack[pop].index = -1;
				calendarEventArray[calendarEventArray.length] = this.valueStack[pop];
			}

			// alert(calendarEventArray[calendarEventArray.length-1].length);
			// alert(calendarEventArray[calendarEventArray.length-1]);
		}
		if (calendarEventArray.length == 0)
			continue;
		// alert(calendarEventArray.length);
		// 重写开始
		// 对24小时48个阶梯进行日历事件统计；
		for (var i = 0; i < calendarEventArray.length; i++) {// 计算每一格的重叠个数
			// alert("start:"+calendarEventArray[i].startIndex+"end:"+calendarEventArray[i].endIndex);
			var begin_index = calendarEventArray[i].startIndex;
			var end_index = calendarEventArray[i].endIndex;
			for (var j = begin_index; j < end_index; j++) {
				array[j]++;
			}
		}
		// alert(array[1]);
		var max_number_array = new Array();
		for (var i = 0; i < calendarEventArray.length; i++) {
			var max_number = 0;
			var begin_index = calendarEventArray[i].startIndex;
			var end_index = calendarEventArray[i].endIndex;
			for (var j = begin_index; j < end_index; j++) {
				if (array[j] > max_number) {
					max_number = array[j];
					max_number_array[j] = max_number;
				}
			}// $$("cc").value=parseInt(100/max_number,10)+"%";

			if (max_number != 1)
				$$("event_" + calendarEventArray[i].id).style.width = parseInt(
						100 / max_number + 20, 10)
						+ "%";
			else
				$$("event_" + calendarEventArray[i].id).style.width = "90%";

		}
		// alert("相交程序判断");
		// alert("valueStack长度为:"+calendarEventArray.length);
		// 遍历所有的calendar event
		// alert(calendarEventArray.length);
		for (var i = 0, length = calendarEventArray.length; i < length; i++) {
			var calendarEvent = calendarEventArray[i];
			// alert("开始："+calendarEvent.startIndex+"结束："+calendarEvent.endIndex);
			calendarEvent.index = 0;

			var max_number = -1;
			var zuiduoxiangjiaojirownumber = 0;
			for (var k = calendarEvent.startIndex; k < calendarEvent.endIndex; k++) {
				if (max_number_array[k] > max_number) {
					max_number = max_number_array[k];
					zuiduoxiangjiaojirownumber = k;
				}
			}
			var paiweiArray = new Array(10);
			for (var j = 0; j < i; j++) {
				// 如果在最多相交集的哪条线上相逢
				if (zuiduoxiangjiaojirownumber >= calendarEventArray[j].startIndex
						&& zuiduoxiangjiaojirownumber < calendarEventArray[j].endIndex) {
					paiweiArray[calendarEventArray[j].index] = 1;
				}

			}

			for (var k = 0; k < paiweiArray.length; k++) {
				if (paiweiArray[k] != 1) {
					calendarEvent.index = k;
					break;
				}
			}
			$$("event_" + calendarEvent.id).style.zIndex = calendarEvent.index;
			if (max_number_array[zuiduoxiangjiaojirownumber] != 1)
				$$("event_" + calendarEvent.id).style.left = (100 - 120
						/ max_number_array[zuiduoxiangjiaojirownumber])
						/ (max_number_array[zuiduoxiangjiaojirownumber] - 1)
						* calendarEvent.index;
			// alert(calendarEvent.index);
			// $$("event_"+calendarEvent.id).style.left=calendarEvent.index*10;
			else
				$$("event_" + calendarEvent.id).style.left = 0;

			$$("event_" + calendarEvent.id).setAttribute("z-index",
					calendarEvent.index);
			// //console.log(calendarEvent.title+"index:"+calendarEvent.index+"inline"+$$("event_"+calendarEvent.id).style.zIndex+"css"+$$("event_"+calendarEvent.id).getAttribute("z-index"));

		}
	}

}*/

CalendarView.prototype.selectDate = function(day) {
	// 重新设定dummyday selectedDay 重新
	this.dummyDay = day;
	this.selectedDay = day;
	this.refreshView();

};

CalendarView.prototype.addEvent = function(ce) {
	// 生成dom节点
	// dialogue附着

	// 得到当前鼠标位置
	if (ce == null) {// console.log("ce is null");
		ce = new calendarEvent();
		var date = new Date();
		ce.timeStart = getTimeStrFromDate(date);
		ce.timeEnd = getTimeStrFromDate(date);
		ce.id = "newEvent";

		ce.day = getdayStrFromDate(date);
		ce.title = "";
	}
	timeStart = ce.timeStart;
	// console.log("timeStart:"+timeStart);
	timeEnd = ce.timeEnd;// getElementsByTagName("div")

	this.currentEventId = "newEvent";
	
	
	/*
	 */

	
	var div_html=document.createElement("DIV");

	var dl_html = document.createElement("DL");
	div_html.appendChild(dl_html);
	div_html.id = "event_" + ce.id;
	div_html.className = "calendarEventBar";
	dl_html.innerHTML = "<DT >"
			+ timeStart
			+ "~"
			+ timeEnd
			+ "</dt> "
			+ "<DD >"+(Tool.isNull(ce.title)?"&nbsp;":ce.title)+"</DD><div  onmouseup=\"Drag.dragEnd();\" onmousedown=\"Drag.drawStart(this);event.cancelBubble=true;\">_</div>";
	
	ce.j = getTimeEndIndex(ce.timeEnd, 0);
	// 入栈
	this.valueStack["event_" + ce.id] = ce;

	dl_html.style.position = "absolute";
	//dl_html.style.top = getTop(ce.timeStart,this.hourHeight);
	var date = ce.day;
	var position = this.timeSectToPosition(timeStart, timeEnd, date);	
	div_html.style.position = "absolute";
	div_html.style.top = position[0]-getInfo($$("table2")).top;
	div_html.style.height = position[1];
	div_html.style.left =0;
	// console.log(dl_html.style.top);
	if (!$$("td_" + ce.day))
		return;
	// console.log($$("td_"+ce.day));
	$$("td_" + ce.day).childNodes[0].appendChild(div_html);

	dl_html.style.left = 0;
	dl_html.style.height = "100%";
	var index = this.index;// ?
	if(ce.id=="newEvent")
	this.showCalendarEventDialog(ce.id);//this.currentEventId
	
	div_html.attachEvent("onmousedown", function(event){Drag.dragStart(div_html)},true);
	index = this.index;
	div_html.attachEvent("ondblclick", function() {
				Instance(index).openCalendarEventDialog(div_html);
			});

	/*dl_html.attachEvent("ondblclick", function() {
				Instance(index).openCalendarEventDialog(dl_html)
			,true});

	dl_html.attachEvent("onmousedown", function() {
				Drag.dragStart(dl_html);
			},true);*/
	// 维护calendareventdata
	ce.startIndex = ce.j;// console.log("startIndex"+ce.startIndex);
	ce.endIndex = ce.j + 2;
	var tr1 = $$("table2").childNodes[0].childNodes[1];
	// //console.log("table2childNodeschildNodes");
	// //console.log($$("table2").childNodes[0].childNodes[1].childNodes[1]);
	var mouseCurrentColum;
	for (i = 1; i < tr1.cells.length; i++) {// //console.log(ce.day);
		if (ce.day == tr1.cells[i].id.substr(3)) {

			mouseCurrentColum = i;

			break;
		}
	}
	//alert(mouseCurrentCell);
	ce.cellIndex = mouseCurrentColum;
	//ce.endIndex=0;
	//ce.top=data_arr[2]-18;
	ce.left = 0;
	ce.length = 2;////console.log("end");
	//alert("开始："+ce.startIndex+"结束"+ce.endIndex);

	this.adjust(mouseCurrentColum, ce);
};

CalendarView.prototype._eval = function(str) {
	eval(str);
};
CalendarView.prototype.hideMCalTitle = function() {
	$$("mcal_div").style.display="none";
	$$("mcal_canopy_div").style.display="";
};
CalendarView.prototype.showMCalTitle = function() {
	$$("mcal_div").style.display="";
	$$("mcal_canopy_div").style.display="none";
};
CalendarView.prototype.showOrHideMCalTypes = function() {
	if($$("mcal_types_div").style.display==""){
		$$("mcal_types_div").style.display="none";
	}else{
		$$("mcal_types_div").style.display="";
	}
	
};

CalendarView.prototype.changeColor = function(it){
	//当前的click
	//当前的type对象的color属性改值
	this.calTypes[this.currentCalType].color=it.style.backgroundColor;
	$$("_caltype_color_div_"+this.calTypes[this.currentCalType].id).style.backgroundColor=it.style.backgroundColor;
	//对应的id的div的背景颜色修改了
	//将菜单关掉
	
};
/**
 * 
 *初始化 
 **/
CalendarView.prototype.init= function() {

	this.initEvent();
	
	//alert("开始时间"+global_weekdays[0].getMonth());//给他毫秒数 + 格林威治时间差
	//alert("测试UTC"+global_weekdays[0].UTC());
	//alert("测试getTime"+global_weekdays[0].getTime());
	//alert("结束时间");
	
	//var startDate = global_weekdays[0].getTime();
	var startSzShift= global_weekdays[0].getTimezoneOffset();
	//var endDate = global_weekdays[6].getTime();
	//var endSzShift= global_weekdays[6].getTimezoneOffset();
	
	var jso={};
	//alert(global_weekdays[0]);
	jso.STARTDATE=parseInt((new Date(global_weekdays[0].format("yyyy-MM-ddT00:00:00"))).getTime()/60000);
	//jso.STARTSZSHIFT=startSzShift;
	jso.ENDDATE=parseInt((new Date(global_weekdays[6].format("yyyy-MM-ddT00:00:00"))).getTime()/60000);
	
	//jso.endSZSHIFT=startSzShift;
	
	AjaxFun("activityAjax.getActivities",jso,this.showActivities);
};

/**
 * 
 *初始化 事件初始化
 **/
CalendarView.prototype.initEvent= function() {
	bind($$("mcal_canopy_div"),'click',new Function("Instance('"
			+ this.index
			+ "').showMCalTitle()"));
	bind($$("mcal_title_fir_th"),'click',new Function("Instance('"
			+ this.index
			+ "').hideMCalTitle()"));
	bind($$("mcal_prev_span"),'click',new Function("Instance('"
			+ this.index
			+ "').mcalGoPrevMonth()"));
	bind($$("mcal_next_span"),'click',new Function("Instance('"
			+ this.index
			+ "').mcalGoNextMonth ()"));
	bind($$("mycal_h2"),'click',new Function("Instance('"
			+ this.index
			+ "').showOrHideMCalTypes()"));
	var divs=document.getElementById("calpalette").getElementsByTagName("div");
	for(var i=0;i<divs.length;i++){
		divs[i].style.backgroundColor=color_list[i];
		bind(divs[i],'click',
		new Function("Instance('"
				+ this.index
				+ "').changeColor(this)")
		);
		//divs[i].style.borderColor=color_list[i];
	}

	//$$("minical_canopy_"+this.index).add
	//加载活动
	
};

CalendarView.prototype.attachTo= function(id) {
	
	$$(id).innerHTML=this.render();
};


CalendarView.prototype. showActivities=function(data){
	if(data!=null && data.LIST!=null && data.LIST.length>0){
		for(var i=0,length=data.LIST.length;i<length;i++){
			if(data.LIST[i]){
				var ce = changeJson2CE(data.LIST[i]);
				//ca.displaySingleEvent(ce);
				ca.addEvent(ce);
			}
		}
	}
	
};
//action of event 

/*
 CalendarView.prototype.closeEventDialog = function(){
 //如果有id则数据库删除
 //如果没有id则关闭窗口 同时删除event
 //$$("CalendarEventDialog").innerHTML="";
 //$$("CalendarEventDialog").removeNode();
 $$("CalendarEventDialog").style.display="none";
 //$$("CalendarEventDialog2").style.display="none";
 $$("mask").style.display="none";
 if(this.currentEventId==""){
 this.newEvent.src.innerHTML="";
 this.newEvent.src.removeNode();
 }

 };
 */;
window['CalendarView'] = CalendarView;

/**
 * 将activity转换成calendarevent
 * 
 */
function changeJson2CE(data){
	var ce=new calendarEvent();
	ce.id=data.activityId;
	
	ce.title=data.title;
	ce.day=new Date(data.startDate*60000).format("yyyy-MM-dd");
	ce.timeStart=new Date(data.startDate*60000).format("hh:mm");
	ce.timeEnd=new Date(data.endDate*60000).format("hh:mm");
	return ce;
}
function translateCE2Activity(ce){
	var activity =new Object();
	activity.ID=ce.id;
	activity.TITLE=ce.title;
	activity. STARTDATE = getTimes(ce.day,ce.timeStart);
	activity. ENDDATE = getTimes(ce.day,ce.timeEnd);
	return activity;
}

function getTimes(date,time){
	var dateTime = parseInt( Date.parse(date+"T"+time+":00")/60000);
	return dateTime;
}
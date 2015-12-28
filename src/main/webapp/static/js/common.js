/**
 * 
 * 
 * Author: zzw <zzw1986@gmail.com>
 * 
 * 
 * File: common.js Create Date: 2014-04-10 19:54:40
 * 
 * 
 */

// AJAX璋冪敤 濡傦細ACWS.ajax('common/service/UserSelect/Init', inputData,
// afterInit,{async:false});

String.prototype.trim= function(){  
    // 用正则表达式将前后空格  
    // 用空字符串替代。  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
}
var CONTEXTPATH="/calendar";
function AjaxFun(url, inputData, callback, options, callbackOnError) {
	var contextUrl = window.location.href;
	options = options || {};
	options.url =  url;
	options.type = options.type||"POST";
	options.data = inputData;
	//options.data = encodeURIComponent(JSON.stringify(inputData));
	if (typeof options.async == 'undifined')
		options.async = false;
	var param = options.inputData;
	options.success = function(outputData) {//alert("success")
		if(outputData.r=="504"){
			window.location=CONTEXTPATH+"/login";
		}
		if (typeof callback == 'function') {
			callback(outputData);
		}
	};
	options.error = function(jqXHR, textStatus, errorThrown) {//alert("error")
		// $("body").unmask();
		var responseText = jqXHR.responseText || "";
		if ((jqXHR.status == 500 || jqXHR.status == 1000)
				&& responseText.indexOf("dwr.engine.http.1000") >= 0) {
			top.location.replace(acwsContext + '/acwsui/pages/logout.htm');// /j_acegi_logout
			return;
		}
		if (typeof callbackOnError == 'function') {
			callbackOnError(errorThrown);
		} else {alert(typeof callbackOnError);
			alert('参数不是function');
		}
	};
	delete options['inputData'];
	$.ajax(options);
};
function Get(url,callback){
	AjaxFun(url,null,callback,{type:"get"});
}
function Post(url,data,callback){
	AjaxFun(url,data,callback);
}
/**
 * 
 */
function changeForm2JsoTemp(formId) {
	var formNode = document.getElementById(formId);
	var elements = formNode.childNodes;
	var jso = {};
	for (var i = 0; i < elements.length; i++) {
		var ele = elements[i];
		if (typeof ele != 'undefineded') {
			if (ele.tagName.toUpperCase() == 'INPUT') {
				jso[ele.id] = ele.value;
			} else if (ele.tagName.toUpperCase() == 'SELECT') {
				jso[ele.id] = ele.value;
			} else if (ele.tagName.toUpperCase() == 'CHECKBOX') {
				if (ele.checked) {
					jso[ele.id] = true;
				} else {
					jso[ele.id] = false;
				}
			} else if (ele.tagName == 'TEXTAREA') {
				jso[ele.id] = ele.value;
			}

		}
	}
	return jso;
}




function changeForm2Jso(formId) {
	var jso = {};
	var arr = $( formId).serializeArray();
	for (var i = 0; i < arr.length; i++) {
		jso["" + arr[i].name] = arr[i].value;
	}
	return jso;
}
function filJso2Form(formId,jso){
	var arr = $( formId).serializeArray();
	for (var i = 0; i < arr.length; i++) {
		$(formId).find("input[name='"+ arr[i].name+"']").val(jso[ arr[i].name]);
	}
}
/**
 * 
 */
function isNull(str) {
		if(typeof str != 'string'){
			//alert("isNull 使用错误,参数是" +(typeof str));
			if(typeof str == 'undefined' || null == str){
				return true
			}else{
				return false;
			}
		}else 
	if (typeof str == 'undefined' || str == null || str.trim() == '')
		return true;
	else
		return false;

}

/**
 * 限制字符长度
 * 
 * @param {Object}
 *            it
 */
function limitLength(it, maxlength) {
	if (it.value.length > maxlength) {
		// _alert("您输入的内容已经超过"+maxlength+"个字!");
		it.value = it.value.substr(0, maxlength);
	}
}

function handlerAjaxReturnAndGoUrl(data, url) {
	if (data.result) {
		window.location = url;
	} else {
		alert(data.msg);
	}
}
function handlerAjaxReturnAndExeFun(data, fun) {
	if (data.result) {
		fun();
	} else {
		alert(data.msg);
	}
}
/**-------------地市县区多选框插件----------------------------------------------**/
var AreaBox = {
		it:null,
		o:null,
		wrap:null,
		sel:null,
		sProvince:null,//select 控件
		sCity:null,
		container:null,
		sCounty:null,
		
		dataStore:null,
		init : function(it, o) {
		
		this.it=it;
		this.o = $.extend({

			sData : null,
			outputId : null
		}, o || {});
		this. wrap = $(it);
		this.wrap
				.append($("<div class='col-sm-12' id='zareamcbox'>"
						+ "<select style='width:80px'    ></select> "
						+ "<select  style='width:80px'    ></select>"
						+ "<select  style='width:80px'  ></select>"
						+ "<button type='button' class='btn btn-sm' style='height:30px'>"
						+ "<i class='icon-plus align-top bigger-125'></i>添加"
						+ "</button>	"
						+ "</div>"
						+ "<input type='text' name='selected-areas' id='selected-areas' value='' placeholder='请选择地区 ...' style='display: none;'/>"));

		var sel = $("select", this.wrap);// $("select",wrap);
		this.sProvince = sel.eq(0);// $("#provinceSelect");
		 this.sCity = sel.eq(1);// $("#citySelect");//sel.eq(1);
		 this.sCounty = sel.eq(2);
		// var loc = new Location();
		 this.sProvince.empty();
		 this.sCity.empty();
		 this.sCounty.empty();
		// loc.fillOption(sProvince , '0',null);
		// loc.fillOption(sCity , '0,'+sProvince.val(),null);
		var addButton = $("button",this. wrap).eq(0);
		var sPval, sCval;
		this. container = $(".tags").eq(0);
		this. dataStore = {};
		// 添加已经有的数据到box中
		for (var i = 0; i < o.sData.length; i++) {
			this.addArea(o.sData[i]);
		}
		addButton.click(function(area) {
			
			return function(){
			// addArea(sProvince.val()+"|"+ sCity.val());
			var name = "";
			if (area.sCounty && area.sCounty.val()) {
				name = area.sProvince.find("option:selected").text() + "|"
						+ area.sCity.find("option:selected").text() + "|"
						+ area.sCounty.find("option:selected").text();
			} else if (area.sCity.val()) {
				name = area.sProvince.find("option:selected").text() + "|"
						+ area.sCity.find("option:selected").text();
			} else {
				name = area.sProvince.find("option:selected").text();
			}
			area.addArea(name);
		};}(this));

		this.sProvince.change(function(area) {
			return function(){
				area.sCity.empty();
				area.getCityForMulti();
			};
		}(this));
		if (this.sCounty)
			this.sCity.change(function(area) {
				return function(){
				area.sCounty.empty();
				area.getCountyForMulti();
				};
			}(this));
		this.getProvinceForMulti();
	},

	getProvinceForMulti : function() {
			
		$.post(CONTEXTPATH + "/member/getProvince", {}, function(area) {
			return function(data){
			jQuery("<option value='0'>全国</option>").appendTo(area.sProvince);
			for (var i = 0, length = data.list.length; i < length; i++) {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo(area.sProvince);
			}
			area.getCityForMulti();
			};
		}(this));
	},

	getCityForMulti : function() {
		$.post(CONTEXTPATH + "/member/getCity", {
			areaname : this.sProvince.val()
		}, function(area) {
			return function(data){
				area.sCity.empty();// alert(sProvince.val());
			 $("<option value=''>--请选择--</option>").appendTo( area.sCity);
			for (var i = 0, length = data.list.length; i < length; i++) {

				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo( area.sCity);
			}
			if (area.sCounty)
				area.getCountyForMulti();
			};
		}(this));
	},

	getCountyForMulti : function() {
		$.post(CONTEXTPATH + "/member/getCounty", {
			areaname : this.sCity.val()
		}, function(area) {
			return function(data){
				area.sCounty.empty();// alert(sProvince.val());
			$("<option value=''>--请选择--</option>").appendTo(area.sCounty);
			for (var i = 0, length = data.list.length; i < length; i++) {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo(area.sCounty);
			}
			};
		}(this));
	},
	/*
	 * sProvince.change(function() {//alert("sProvincechange"); sCity.empty();
	 * loc.fillOption(sCity , '0,'+sProvince.val()); });
	 */
	addArea : function(name) {
		if (isNull(name))
			return;
		var _province = "";
		var _city = "";
		var _county = "";
		if (name == "全国" && $(this.container).find(".tag").length > 0) {
			alert("你已经选择了地区,无法添加全国");
			return;
		}
		if ($(this.container).find(".tag[name='全国']").length > 0) {
			alert("你已经选择全国,无需添加其他地区");
			return;
		}
		// 添加的只是省
		// 添加的是全国
		if (name == "全国") {
			_province = name.trim();
		} else if (name.indexOf("|") <= 0) {
			_province = name.trim();

			if (this.dataStore[_province]) {
				alert("你已经选择了省份,不能再添加");
				return;
			}
		} else {// 添加的是省市
			if (name.lastIndexOf("|") > name.indexOf("|")) {// 含有区
				_county = name.split("|")[2];
			}
			_province = name.split("|")[0];
			_city = name.split("|")[1];
		}
		
		//选择的是省
		if (this.dataStore[_province] && this.dataStore[_province]["all"]) {
			alert("你已经选择了省份,不能再添加");
			return;
		}
		//有选择市
		if (_city) {
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city]["all"]) {
				alert("你已经选择了省市,不能再添加");
				return;
			}
			if (!_county)
			if (this.dataStore[_province] && this.dataStore[_province][_city]) {
				for (k in this.dataStore[_province][_city]) {
					alert("你已经选择了省市,不能再添加");
					return;
				}
			}
			
		}
		//有选择区
		if (_county) {
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city]["all"]) {
				alert("你已经选择了上级区域,不能再添加");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]
				&& this.dataStore[_province][_city][_county]) {
					alert("你已经选择了该省市区,不能再添加");
					return;
			}
			
		}
		/*if (_county) {// 加的是省市区
			if (this.dataStore[_province] && this.dataStore[_province]["all"]) {
				alert("数据冲突！");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city]["all"]) {
				alert("数据冲突！");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city][_county]) {
				alert("数据冲突！");
				return;
			}
			// this.dataStore[_province][_city][_county]=1;
		} else if (_city) {// 加的是省市
			if (this.dataStore[_province] && this.dataStore[_province]["all"]) {
				alert("数据冲突！");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]
					&& this.dataStore[_province][_city]["all"]) {
				alert("数据冲突！");
				return;
			}
			if (this.dataStore[_province] && this.dataStore[_province][_city]) {
				for (k in this.dataStore[_province][_city]) {
					alert("数据冲突!");
					return;
				}
			}
			// this.dataStore[_province][_city]["all"]=1;
		} else if (_province) {// 加的是省
			if (this.dataStore[_province]) {
				for (k in this.dataStore[_province]) {
					alert("数据冲突!");
					return;
				}
			}

		}*/
		var str = "<span name='"
				+ name
				+ "' class='tag'>"
				+ name
				+ "<button type='button'  class='close'>x</button></span>";
		// 判断是否有数据了
		var ele = $(str);
		this.container.append(ele);
		$("button", ele).click(function(area) {
			return function(){
			area.removeArea(this);
			}
		}(this));
		
		if (_county) {
			if (!this.dataStore[_province]) {
				this.dataStore[_province] = {};
			}
			if (!this.dataStore[_province][_city]) {
				this.dataStore[_province][_city] = {};
			}
			this.dataStore[_province][_city][_county] = 1;
		} else if (_city) {
			if (!this.dataStore[_province]) {
				this.dataStore[_province] = {};
			}
			if (!this.dataStore[_province][_city]) {
				this.dataStore[_province][_city] = {};
			}
			this.dataStore[_province][_city]['all'] = 1;
		} else {
			if (!this.dataStore[_province]) {
				this.dataStore[_province] = {};
			}
			this.dataStore[_province]["all"] = 1;
		}
		this.writeInput();
	},

	removeArea : function(it) {
		var name = $(it).parent().attr("name");
		var _province = "";
		var _city = "";
		var _county = "";

		// 添加的只是省
		if (name == "全国") {
			_province = name.trim();
		} else if (name.indexOf("|") <= 0) {
			_province = name.trim();

		} else {// 添加的是省市
			if (name.lastIndexOf("|") > name.indexOf("|")) {// 含有区
				_county = name.split("|")[2];
			}
			_province = name.split("|")[0];
			_city = name.split("|")[1];
		}

		if (_county) {
			this.dataStore[_province][_city][_county] = null;
			delete this.dataStore[_province][_city][_county];
		} else if (_city) {
			this.dataStore[_province][_city]['all'] = null;
			delete this.dataStore[_province][_city]['all'];
		} else {
			this.dataStore[_province]['all'] = null;
			delete this.dataStore[_province]['all'];
		}
		$(it).parent().remove();
		this.writeInput();
	},

	writeInput : function() {
		var areaNameArr = new Array();
		this.wrap.find(".tag").each(function() {
			areaNameArr.push($(this).attr("name"));
		});
		$('#' + this.o.outputId).val(areaNameArr.join(","));
	},
	viewMode:function(){
		this.sProvince.hide();
		this.sCity.hide();
		this.sCounty.hide();
		$("button",this.wrap).hide();
	},
	getSeleteds : function() {
		var arr = new Array();
		$(this.wrap).find(".tag").each(function() {
			arr.push($(this).attr("name"));
		});

		return arr.join(",");
	}
};
/*$.fn.zAreaMCBox=function(o){
	var areaBox =  Object.create(AreaBox);
	areaBox.init(this,o);
	return areaBox;
}
;
*/
/**-------------地市县区多选框插件end----------------------------------------------**/
function getCellValue(id, index) {
	return $("input:checkbox[value='" + id + "']").parent().parent().parent()
			.find("td").eq(index).text();
}

function goPage(currentPage, everyPage) {
	console.log(currentPage);
	zMenu.loadPage(currentPage);
	return;
//	window.location.href = $("#searchForm")[0].action + "?fy=1&currentPage="
//			+ currentPage + "&everyPage=" + everyPage;
}

function openMenu(id) {
	$("#" + id).addClass("open");
	$("#" + id).find("ul").css("display", "block");
}

function openMenu(level1, level2) {

}
function getAuditHtml(value) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+="'"+arguments[i]+"',";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\"auditInfo("+strs.substr(1)+")\" >审核</a>";
}
function getEditHtml(value) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\"editInfo("+strs.substr(1)+")\" >修改</a>";
}
function getDelHtml(value) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\"deleteInfo("+strs.substr(1)+")\" >删除</a>";
}
function getViewHtml(value) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\"viewInfo("+strs.substr(1)+")\" >查看</a>";
}
function getLockHtml(value) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\"lockinfo("+strs.substr(1)+")\" >禁用</a>";
	}
function getUnLockHtml(value) {
	var strs= "";
	for(var i=0;i<arguments.length;i++){
		strs+=",'"+arguments[i]+"'";
	}
	return "<a style='margin-left:2px' href=\"javascript:void(0)\" onclick=\"unlockinfo("+strs.substr(1)+")\" >启用</a>";
	}
/*function getEditHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"editinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"编辑所选行\"><span class=\"ui-icon ui-icon-pencil\"></span></div>";
}


function getDelHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"deleteinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"删除所选行\"><span class=\"ui-icon ui-icon-trash\"></span></div>";
}
function getViewHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"viewinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"查看所选行\"><span class=\"ui-icon icon-zoom-in\"></span></div>";
}
function getLockHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;padding-top:3px\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"lockinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"禁用\"><span class=\"ui-icon icon-lock \"></span></div>";
}
function getUnLockHtml1(value) {
	return "<div title=\"\" style=\"float:left;cursor:pointer;padding-top:3px\" class=\"ui-pg-div ui-inline-edit\" id=\"jEditButton_1\" onclick=\"unlockinfo('"
			+ value
			+ "')\" onmouseover=\"jQuery(this).addClass('ui-state-hover');\" onmouseout=\"jQuery(this).removeClass('ui-state-hover')\" data-original-title=\"启用\"><span class=\"ui-icon icon-unlock \"></span></div>";
}
*/
//var aiwifi = {};
//aiwifi.alert = bootbox.alert;
//aiwifi.confirm = bootbox.confirm;
function showMask() {
	$("#maskModal").modal('show');
}
function hideMask() {
	$("#maskModal").modal('hide');
}

/*function showMsg(caption, contenttext) {
	$("#errormsg").remove();
	var str = "<div id='errormsg' class='alert alert-warning' id='errormsg' >"
			+ "<a href='#' class='close' data-dismiss='alert'> &times; </a> <strong>"
			+ caption + "</strong>" + contenttext + "</div>";
	$("form").prepend(str);
}*/
function showMsg(caption, contenttext) {
	
	aiwifi.alert(contenttext);
}
function initGrid(gridParam) {

	jQuery(function($) {
		var grid_selector = "#" + gridParam.grid_selector;// grid 的div
		var pager_selector = "#" + gridParam.pager_selector;// 分页的div

		jQuery(grid_selector).jqGrid({
			// direction: "rtl",
			url : gridParam.url,// ajax对应的url
			mtype : 'POST',
			datatype : "json",// 返回的数据格式
			height : 250,// gird的高度
			altclass : true,
			postData : gridParam.searchParams,
			colNames : gridParam.colNames,
			colModel : gridParam.colModel,
			viewrecords : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			pager : pager_selector,
			altRows : true,
			// toppager: true,

			multiselect : gridParam.multiselect,
			/*
			 * onSelectRow: function (rowId, status, e) { var rowIds =
			 * jQuery("#grid-table").jqGrid('getGridParam', 'selarrrow'); },
			 */
			// multikey: "ctrlKey",
			multiboxonly : true,

			loadComplete : function() {
				var table = this;
				setTimeout(function() {
					styleCheckbox(table);

					updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},

		//	editurl : CONTEXTPATH+"/developerinfo/save",
			// caption: "jqGrid with inline editing",

			autowidth : true

		});

		// enable search/filter toolbar
		// jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})

		// navButtons
		jQuery(grid_selector).jqGrid(
				'navGrid',
				pager_selector,
				{ // navbar options
					edit : false,
					editicon : 'icon-pencil blue',
					add : false,
					addicon : 'icon-plus-sign purple',
					del : false,
					delicon : 'icon-trash red',
					search : false,
					searchicon : 'icon-search orange',
					refresh : true,
					refreshicon : 'icon-refresh green',
					view : false,
					viewicon : 'icon-zoom-in grey',
				},
				{
					// edit record form
					// closeAfterEdit: true,
					recreateForm : true,
					beforeShowForm : function(e) {
						var form = $(e[0]);
						form.closest('.ui-jqdialog').find(
								'.ui-jqdialog-titlebar').wrapInner(
								'<div class="widget-header" />')
						style_edit_form(form);
					}
				},
				{
					// new record form
					closeAfterAdd : true,
					recreateForm : true,
					viewPagerButtons : false,
					beforeShowForm : function(e) {
						var form = $(e[0]);
						form.closest('.ui-jqdialog').find(
								'.ui-jqdialog-titlebar').wrapInner(
								'<div class="widget-header" />')
						style_edit_form(form);
					}
				},
				{
					// delete record form
					recreateForm : true,
					beforeShowForm : function(e) {
						var form = $(e[0]);
						if (form.data('styled'))
							return false;

						form.closest('.ui-jqdialog').find(
								'.ui-jqdialog-titlebar').wrapInner(
								'<div class="widget-header" />')
						style_delete_form(form);

						form.data('styled', true);
					},
					onClick : function(e) {
						alert(1);
					}
				},
				{
					// search form
					recreateForm : true,
					afterShowSearch : function(e) {
						var form = $(e[0]);
						form.closest('.ui-jqdialog').find('.ui-jqdialog-title')
								.wrap('<div class="widget-header" />')
						style_search_form(form);
					},
					afterRedraw : function() {
						style_search_filters($(this));
					},
					multipleSearch : true,
				/**
				 * multipleGroup:true, showQuery: true
				 */
				},
				{
					// view record form
					recreateForm : true,
					beforeShowForm : function(e) {
						var form = $(e[0]);
						form.closest('.ui-jqdialog').find('.ui-jqdialog-title')
								.wrap('<div class="widget-header" />')
					}
				})

		function style_edit_form(form) {
			// enable datepicker on "sdate" field and switches for "stock" field
			/*
			 * form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' ,
			 * autoclose:true}) .end().find('input[name=stock]') .addClass('ace
			 * ace-switch ace-switch-5').wrap('<label class="inline"
			 * />').after('<span class="lbl"></span>');
			 */

			// update buttons classes
			var buttons = form.next().find('.EditButton .fm-button');
			buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();// ui-icon,
																				// s-icon
			buttons.eq(0).addClass('btn-primary').prepend(
					'<i class="icon-ok"></i>');
			buttons.eq(1).prepend('<i class="icon-remove"></i>')

			buttons = form.next().find('.navButton a');
			buttons.find('.ui-icon').remove();
			buttons.eq(0).append('<i class="icon-chevron-left"></i>');
			buttons.eq(1).append('<i class="icon-chevron-right"></i>');
		}

		function style_delete_form(form) {
			var buttons = form.next().find('.EditButton .fm-button');
			buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();// ui-icon,
																				// s-icon
			buttons.eq(0).addClass('btn-danger').prepend(
					'<i class="icon-trash"></i>');
			buttons.eq(1).prepend('<i class="icon-remove"></i>')
		}

		function style_search_filters(form) {
			form.find('.delete-rule').val('X');
			form.find('.add-rule').addClass('btn btn-xs btn-primary');
			form.find('.add-group').addClass('btn btn-xs btn-success');
			form.find('.delete-group').addClass('btn btn-xs btn-danger');
		}
		function style_search_form(form) {
			var dialog = form.closest('.ui-jqdialog');
			var buttons = dialog.find('.EditTable')
			buttons.find('.EditButton a[id*="_reset"]').addClass(
					'btn btn-sm btn-info').find('.ui-icon').attr('class',
					'icon-retweet');
			buttons.find('.EditButton a[id*="_query"]').addClass(
					'btn btn-sm btn-inverse').find('.ui-icon').attr('class',
					'icon-comment-alt');
			buttons.find('.EditButton a[id*="_search"]').addClass(
					'btn btn-sm btn-purple').find('.ui-icon').attr('class',
					'icon-search');
		}

		function beforeDeleteCallback(e) {
			var form = $(e[0]);
			if (form.data('styled'))
				return false;

			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
					.wrapInner('<div class="widget-header" />')
			style_delete_form(form);

			form.data('styled', true);
		}

		function beforeEditCallback(e) {alert(before);
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
					.wrapInner('<div class="widget-header" />')
			style_edit_form(form);
		}

		// it causes some flicker when reloading or navigating grid
		// it may be possible to have some custom formatter to do this as the
		// grid is being created to prevent this
		// or go back to default browser checkbox styles for the grid
		function styleCheckbox(table) {

			/*
			 * $(table).find('input:checkbox').addClass('ace') .wrap('<label
			 * />') .after('<span class="lbl align-top" />')
			 * 
			 * 
			 * $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
			 * .find('input.cbox[type=checkbox]').addClass('ace') .wrap('<label
			 * />').after('<span class="lbl align-top" />');
			 */

		}

		// unlike navButtons icons, action icons in rows seem to be hard-coded
		// you can change them like this in here if you want
		function updateActionIcons(table) {
			/**
			 * var replacement = { 'ui-icon-pencil' : 'icon-pencil blue',
			 * 'ui-icon-trash' : 'icon-trash red', 'ui-icon-disk' : 'icon-ok
			 * green', 'ui-icon-cancel' : 'icon-remove red' };
			 * $(table).find('.ui-pg-div span.ui-icon').each(function(){ var
			 * icon = $(this); var $class =
			 * $.trim(icon.attr('class').replace('ui-icon', '')); if($class in
			 * replacement) icon.attr('class', 'ui-icon '+replacement[$class]); })
			 */
		}

		// replace icons with FontAwesome icons like above
		function updatePagerIcons(table) {
			var replacement = {
				'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
				'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
				'ui-icon-seek-next' : 'icon-angle-right bigger-140',
				'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
			};
			$(
					'.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
					.each(
							function() {
								var icon = $(this);
								var $class = $.trim(icon.attr('class').replace(
										'ui-icon', ''));

								if ($class in replacement)
									icon.attr('class', 'ui-icon '
											+ replacement[$class]);
							})
		}

		function enableTooltips(table) {
			$('.navtable .ui-pg-button').tooltip({
				container : 'body'
			});
			$(table).find('.ui-pg-div').tooltip({
				container : 'body'
			});
		}

		// var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
	});

}

function initProCitySel(jso) {
	var defProvince = jso.defProvince;
	var defCity = jso.defCity;
	var provinceId = jso.provinceId;
	var cityId = jso.cityId;

	$("#" + provinceId).change(function() {
		getCity(cityId, provinceId);
	})

	// 初始化省
	$.get(CONTEXTPATH + "/member/getProvince", {}, function(data) {

		jQuery("<option value=''>--请选择--</option>").appendTo("#" + provinceId);
		for (var i = 0, length = data.list.length; i < length; i++) {
			if (defProvince && defProvince == data.list[i]) {
				jQuery(
						"<option selected value='" + data.list[i] + "'>"
								+ data.list[i] + "</option>").appendTo(
						"#" + provinceId);
			} else {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo("#" + provinceId);
			}
		}
		getCity(cityId, provinceId, defCity);
	});

}

function getCity(cityId, provinceId, defCity) {
	$.post(CONTEXTPATH + "/member/getCity", {
		areaname : $("#" + provinceId).val()
	}, function(data) {
		$("#" + cityId).empty();
		jQuery("<option value=''>--请选择--</option>").appendTo("#" + cityId);
		for (var i = 0, length = data.list.length; i < length; i++) {
			if (defCity && defCity == data.list[i]) {
				jQuery(
						"<option selected value='" + data.list[i] + "'>"
								+ data.list[i] + "</option>").appendTo(
						"#" + cityId);
			} else {
				jQuery(
						"<option value='" + data.list[i] + "'>" + data.list[i]
								+ "</option>").appendTo("#" + cityId);
			}
		}
	});
}
/**菜单 导航条**/
function navPath(level1, level2) {
	//alert($("#"+level1).find(".menu-text").text() );
	//alert($("#"+level2).text() );
	$("#nav_fir").text($("#" + level1).find(".menu-text").text());
	$("#nav_sec").text($("#" + level2).text());

	$("#" + level1).addClass("open");
	$("#" + level1).find("ul").css("display", "block");
}
/**表格刷新**/
function reloadGrid(){
	jQuery("#grid-table").trigger("reloadGrid");
	
}
/**查询**/
function searchData(){
	searchParams= changeForm2Jso('searchForm');
	
	$("#grid-table").jqGrid('setGridParam',{ 
        postData:searchParams,
        page:1 
    }).trigger("reloadGrid"); //重新载入  
    
    //jQuery("#grid-table").setPostData(searchParams).trigger("reloadGrid");
}
//ajax 成功标识位
var  AJAX_SUCC=1;
//ajax 失败标识位
var AJAX_FAIL=0;
//ajax 成功与否标识位
var AJAX_RESULT="r";
//ajax 返回消息标识位
var AJAX_MSG="msg";
//多条消息返回标识位
var AJAX_ERRORS="errors";

/**
 * 动态加载js 
 * @param data 数组
 */
function includeJS(data){
	
	for(var i=0;i<data.length;i++){
		if(!isNull(data[i]) ){
		if(!isNull(CONTEXTPATH) && data[i].indexOf(CONTEXTPATH)==-1){
			data[i]=CONTEXTPATH+data[i];
		}
			document.write("<script id='"+i+"' type='text/javascript' src='"+data[i]+"'  charset='utf-8'> </script>");
		}
	}
	
	
}
/**
 * 动态引入css 
 * @param data 数组
 */
function includeCSS(data){
	for(var i=0;i<data.length;i++){
		if(!isNull(data[i]) ){
			if(!isNull(CONTEXTPATH) && data[i].indexOf(CONTEXTPATH)==-1){
				data[i]=CONTEXTPATH+data[i];
			}
				document.write("<link rel='stylesheet' href='"+data[i]+"' type='text/css' />");
		}
	}
	
	
}
/**
 * 跳转链接同一用此处
 * @param url
 */
function goUrl(url){
	window.location=CONTEXTPATH+url;
}



function LoadJS( id, fileUrl ) 
{ 

    var scriptTag = document.getElementById( id ); 

    var oHead = document.getElementsByTagName('HEAD').item(0); 

    var oScript= document.createElement("script"); 

    if ( scriptTag  ) oHead.removeChild( scriptTag  ); 

    oScript.id = id; 

    oScript.type = "text/jsx"; 

    oScript.src=fileUrl ; 

    oHead.appendChild( oScript); 

} 




/**
 * 
 * 
 * Author: zzw <zzw1986@gmail.com>
 * 
 * 
 * File: common.js Create Date: 2014-04-10 19:54:40
 * 
 * 
**/
/**
 * 对于模态框 tab页进行自动绑定触发事件
 */
function pageinit(){
	$("*[data-toggle='modal']").each(function(){
		if($(this).attr("data-target")){
			$(this).on("click",function(){
				showMask();
				$($(this).attr("data-target")).show();
			});
		}
		
	});
	
	$("*[data-dismiss='modal']").each(function(){
		$(this).on("click",function(){
		hideMask();
		$(this).closest(".modal").hide();
		});
		
	});
	
	$(".menu li").each(function(){
		$(this).on("click",function(){
			$(".select").removeClass("select");
			$(this).addClass("select");
		});
	})
	
	$(".nav-tabs li").each(function(){
		$(this).on("click",function(){
			$(".active").removeClass("active");
			$(this).addClass("active");
		});
	});
}



    
function initGrid(gridParam){
	 $(grid_selector).jqGrid(gridParam);
	 return  $(grid_selector);
}




/**
 * 闭包
 */
Function.prototype.Apply = function(thisObj)
{
    var _method = this;
    return function(data)
    {
        return _method.apply(thisObj,[data]);
    };
};

function goMvcUrl(str){
	  mvc.routeRun(str);
}

function ajaxResultHandler(result){
	//权限问题
	//登陆问题
	if(typeof result =='string'){
		result= eval("("+result+")");
	}
	if(result.r!=1){
		if(result.r==504  ){
			alert(result.msg);
			window.location=WEBCONTEXT+"/login";
			
		}
		if(result.r==505  ){
			alert(result.msg);
		}
		return result;
	}
	else
	return result;
}

function showMask(){
	if($(".mask").length==0){
		$("body").append($("<div class=\"mask\" > </div>"));
	}
	$(".mask").show()
}

function showModal(id,w,h){
	if(typeof h !='undefined' && h!=null )
	$(id).css("height",h);
	if(typeof w !='undefined' && w!=null )
	$(id).css("width",w);
	$(id).show();
	showMask();
}



function hideModal(id){
	$(id).hide();
	$(".mask").hide()
}

function showErrorMsg(formid,msg){
	var div = $(formid).find(".alert");
	if(div.length==0){
		var div = $("<div class=\"alert alert-danger\"></div>");
		 $(formid).find(".modal-body").prepend(div);
	}
	div.eq(0).html(msg);
	div.eq(0).show();
}


function clearErrorMsg(formid){
	 $(formid).find(".alert").hide();
}

function showWait(msg){
	
	showMask()
	$(".wait").show();
}
function hideWait(){
	hideWaitTrue();
	///setTimeout("hideWaitTrue()",100);
	
}
function hideWaitTrue(){
	$(".mask").hide()
	$(".wait").hide();
}


function zWidgetBase(){
	showMask();
	if($(".widget").length>0){
		
	}else{
		$("body").append("<div class='widget'></div>");
	}
}/*
function zconfirm(msg,title,fn){
	zWidgetBase();
	var html=$("<div class=\"zwidget_wrap\">"+
	"<div class=\"zwidget_header\"><span>"+title+"</span> <a onclick=\"$(this).parent().parent().hide();$('.mask').hide()\"><img class=\"zclose\" src=\"/static/img/closeIcon.png\"></img></a></div>"+
	"<div class=\"zbody\">"+
	"<div class=\"zinfo-icon\"><img src=\"/static/img/exclamation.png\"/></div>"+
	"<div class=\"zinfo\">"+msg+"</div>"+
	"<div class=\"zbutton_wrap\"><a style='width:20%' onclick=\"$(this).parent().parent().parent().hide();$('.mask').hide()\">确定</a><a style='width:20%' onclick=\"$(this).parent().parent().parent().hide();$('.mask').hide()\">确定</a></div>"+
	"</div>"+
	"</div>");
	$(".widget").html(html);
	if (typeof(fn) != "undefined") 
	$(html).find(".zbutton_wrap").find("a").click(fn);
}*/

/*
function zdialogue(msg,title,src,fontcolor,fn){
	zWidgetBase();
	var html=$("<div class=\"zwidget_wrap\">"+
	"<div class=\"zwidget_header\"><span>"+title+"</span> <a onclick=\"$(this).parent().parent().hide();$('.mask').hide()\"><img class=\"zclose\" src=\""+PATH+"/static/img/closeIcon.png\"></img></a></div>"+
	"<div class=\"zbody\">"+
	"<div class=\"zinfo-icon\"><img src=\""+src+"\"/></div>"+
	"<div class=\"zinfo\" style=\"color:"+fontcolor+"\">"+msg+"</div>"+
	"<div class=\"zbutton_wrap\"><a onclick=\"$(this).parent().parent().parent().hide();$('.mask').hide()\">确定</a></div>"+
	"</div>"+
	"</div>");
	$(".widget").html(html);
	if (typeof(fn) != "undefined") 
	$(html).find(".zbutton_wrap").find("a").click(fn);
}*/
function zdialogue(jso){
	showMask()
	if(StringUtil.isBlank(jso.title)){
		jso.title="提示";
	}
	var html=$("<div class=\"zwidget_wrap \">"+
	"<div class=\"zwidget_header\"><span>"+jso.title+"</span> <a class='zclose'  onclick=\"$(this).parent().parent().hide();$('.mask').hide()\"><i class=' fa fa-close'></i></a></div>"+
	"<div class=\"zbody \">"+
	"<div class=\"zinfo-icon\"><i  class='fa fa-check-circle'></i></div>"+//<img src=\""+jso.src+"\"/>
	"<div class=\"zinfo\" style=\"color:"+jso.fontcolor+"\">"+jso.msg+"</div>"+
	"</div><div class='zfooter' ><div class=\"zbutton_wrap row\">"+
	(jso.type== "confirm"?"<button type=\"button\" class=\"col-xs-5 pull-left btn btn-primary\" >确定</button><button type=\"button\" class=\"col-xs-5 pull-right btn btn-default\" >取消</button>":
		"<button class='col-xs-12 btn btn-primary' >确定</button>")+
	"</div></div>"+
	"</div>");
	$(".widget").html(html);
	if (typeof(jso.okfn) != "undefined") {
		$(html).find(".zbutton_wrap").find(".btn-primary").click(jso.okfn);
	}
		
	$(html).find(".btn").click(function(){$(html).fadeOut();$(".mask").fadeOut()});
	
	if (typeof(jso.cancelfn) != "undefined") {
		$(html).find(".zbutton_wrap").find(".btn-default").click(jso.okfn);
		$(html).find(".zwidget_header").find(".zclose").click(jso.cancelfn);
		$(html).find(".zwidget_header").find(".zclose").click(function(){$(html).fadeOut();$(".mask").fadeOut()});
	}else{
		$(html).find(".zwidget_header").find(".zclose").click(function(){$(html).fadeOut();$(".mask").fadeOut()});
	}
}
/*
function zalert(msg,title,fn){
	zWidgetBase();
	if(StringUtil.isBlank(title)){
		title="提示";
	}
	var html=$("<div class=\"zwidget_wrap\">"+
	"<div class=\"zwidget_header\"><span>"+title+"</span> <a class='zclose'  onclick=\"$(this).parent().parent().hide();$('.mask').hide()\"><i class=' fa fa-close'></i></a></div>"+
	"<div class=\"zbody\">"+
	"<div class=\"zinfo-icon\"><i  class='fa fa-check-circle'></i></div>"+
	"<div class=\"zinfo\">"+msg+"</div>"+
	"<div class=\" zbutton_wrap\"><a class='btn btn-primary' onclick=\"$(this).parent().parent().parent().hide();$('.mask').hide()\">确定</a></div>"+
	"</div>"+
	"</div>");
	$(".widget").html(html);
	if (typeof(fn) != "undefined") 
	$(html).find(".zbutton_wrap").find("a").click(fn);
}*/
function zerror(msg,title,fn){
	zdialogue({"msg":msg,type:"alert","title":title,fontcolor:"#777777",src:PATH+"/static/img/exclamation.png",okfn:fn});
}
function zconfirm(msg,title,okfn,cancelfn){
	zdialogue({"msg":msg,type:"confirm","title":title,fontcolor:"#777777",src:PATH+"/static/img/exclamation.png",okfn:okfn,cancelfn:cancelfn});
}
function zalert(msg,title,fn){
	zdialogue({"msg":msg,type:"alert","title":title,fontcolor:"#777777",src:PATH+"/static/img/nike.png",okfn:fn});
}

var Tool={};
Tool.isNull=function(it){
	if(it==null || typeof it=='undefinded'){
		return true;
	}
	return null;
}
var StringUtil={};
StringUtil.isNull=function(it){
	if(it==null || typeof it=='undefinded' || it==''){
		return true;
	}
	return null;
}
StringUtil.isBlank=function(it){
	if(it==null || typeof it=='undefinded' || it==''){
		return true;
	}
	return null;
}

function getParam(key){
	if(window.data)
		return window.data[key];
	return null;
}

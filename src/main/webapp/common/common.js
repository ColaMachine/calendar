/**
 *
*
* Author:
*	zzw <zzw1986@gmail.com>
*	
*
* File: common.js
* Create Date: 2014-04-10 19:54:40
*
*
*/

//AJAX调用  如：ACWS.ajax('common/service/UserSelect/Init', inputData, afterInit,{async:false});
function AjaxFun(url, inputData, callback, options, callbackOnError){
	var contextUrl= window.location.href;
	options = options || {};
	options.url= url + ".ajax";
	options.type="POST";
	options.data=encodeURIComponent( JSON.stringify(inputData));
	if(typeof options.async == 'undifined') options.async=false;
	var param=options.inputData;
	options.success=function(outputData){if(typeof callback == 'function'){callback(outputData);}};
	options.error=function(jqXHR, textStatus, errorThrown){
		//$("body").unmask();
		var responseText = jqXHR.responseText||"";
		if((jqXHR.status==500||jqXHR.status==1000) && responseText.indexOf("dwr.engine.http.1000")>=0) {
			top.location.replace(acwsContext+'/acwsui/pages/logout.htm');///j_acegi_logout
			return;
		}
		if(typeof callbackOnError == 'function'){
			callbackOnError(errorThrown);
		} else {
			alert('通讯错误！');
		}
	};
	delete options['inputData'];
	
	
	$.ajax(options);
};
/**
 * 将表单转化成jso
 */
function changeForm2Jso(formId){
	var formNode=document.getElementById(formId);
	var elements= formNode.childNodes;
	var jso={};
	for(var i=0;i<elements.length;i++){
		var ele=elements[i];
		if(typeof ele !='undefineded'){
			if(ele.tagName.toUpperCase() =='INPUT'){
				jso[ele.id]=ele.value;
			}else if(ele.tagName.toUpperCase()=='SELECT'){
				jso[ele.id]=ele.value;
			}else if(ele.tagName.toUpperCase()=='CHECKBOX'){
				if(ele.checked){
					jso[ele.id]=true;
				}else{
					jso[ele.id]=false;
				}
			}else if(ele.tagName=='TEXTAREA'){
				jso[ele.id]=ele.value;
			}
		}
	}
	return jso;
}

/**
 * 判断独享是否为空
 */
function isNull(str){
	if(typeof str =='undefined' || str==null || str.trim()=='')
	return true;
	else
	return false;
	
}

function changeForm2Jso(formId) {
	var jso = {};
	var arr = $( formId).serializeArray();
	for (var i = 0; i < arr.length; i++) {
		jso["" + arr[i].name] = arr[i].value;
	}

	return jso;
}


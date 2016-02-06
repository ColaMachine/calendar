<#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
<div class="rgt_body">
    <div class="body_title">| ${table.remark}</div>
        <div class="body_top" >
            <form class="form-inline app-search">
${searchhtml}
 <!-- <#list table.cols as col>
        <div class="form-group">
            <label for="exampleInputName2">${col.remark}</label>
          
            <input type="text" class="form-control" id="${col.name}" name="${col.name}" placeholder="${col.remark}">
           
        </div>
  </#list>-->
  
                <button type="button" onclick="search()" class="btn btn-default">查询</button>
            </form>
        <div >
            <button class="btn" onclick="addInfo()">新增</button>    
        </div>
    </div>
    <div id="grid" class="grid"></div>
    <div id="grid-pager" class="pager"></div>
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
<script>
var gridParam = {
    page:{
        curPage : 1,
        pageSize : 10
    },
    multiselect : false,
    url : PATH+'/${abc}/list.json',
    grid_selector : "#grid",
    pager_selector : "#grid-pager",
    searchParams : {
        name : ''
    },
    colNames : [
     <#list table.cols as col><#if col_index!=0>,</#if>"${col.remark}"</#list> , '操作' ],
    colModel : [
             <#list table.cols as col>
            {
                name : '${col.name}',
                width : 80,
                <#if col.showValue?exists>
                formatter : function(value, grid, rows) {
                    var map ={<#list col.showValue?keys as key>'${key}':'${col.showValue[key]}',</#list>};
                    return map[value];
                }
                </#if>
                <#if col.type=="timestamp">
                 formatter : function(value, grid, rows) {
                    return new Date(value).format("yyyy-MM-dd");
                }
                </#if>
            } <#if col_index<table.cols?size-1>,</#if>
             </#list>
            ,
            {
                name : 'id',
                width : 150,
                formatter : function(value, grid, rows) {
                    return getViewHtml(value)+getEditHtml(value)+getDelHtml(value);
                }
            } ]
            /*
             * ondblClickRow: function(id){
             * 
             * alert("双击选中"+id);
             *  },
             */
         /*
             * onSelectRow: function(id){ alert("单击选中"+id); },
             */
};
var mygrid = $("#grid").jqGrid(this.gridParam); 
 function addInfo(){
    //goPage(PATH+'/${abc}/edit.htm');
    zwindow(PATH+"/${abc}/edit.htm");
    $("#mymodal").modal("toggle");
 }
 function editInfo(id){
     //goPage(PATH+"/${abc}/edit.htm?${table.pk.name}="+id);
      zwindow(PATH+"/${abc}/edit.htm?id="+id);
     $("#mymodal").modal("toggle");
 }
 function search(){
    var jso = changeForm2Jso(".app-search");
    mygrid.jqGrid("search",jso);
 }
 function deleteInfo(id){
     //弹窗
     zconfirm("确定删除数据:"+id,"删除",function(){
        $.post(PATH+"/${abc}/del.json?${table.pk.name}="+id,function(result){
            result=ajaxResultHandler(result);
            if(result.r==AJAX_SUCC){
                zalert("删除成功，数据："+id,"删除",function(){
                $("#grid").jqGrid("reloadGrid");
            });
            }else {
                zerror(result.msg,"提醒",function(){});
            }
        });
    });
}
 function viewInfo(id){
     goPage(PATH+"/${abc}/view.htm?${table.pk.name}="+id);
 }
  function search(){
     var jso= changeForm2Jso(".app-search");
     console.log(jso);
     mygrid.search(jso);
 }
</script>


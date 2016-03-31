<div id="${Abc}List" class="rgt_body">
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
            <button class="btn addBtn" >新增</button>
            <button class="btn deleteBtn">删除</button>
            <button class="btn exportBtn" >导出</button>
        </div>
    </div>
    <div id="${table.name}Grid" class="grid"></div>
    <div id="${table.name}Grid-pager" class="pager"></div>
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

var ${abc}List={
    modal:false,
    mygrid:null,
    treeObj:null,
    root:$("#${table.name}List"),
    init:function(){
        this.mygrid =this.root.find(".grid").jqGrid(this.gridParam);
        this.addEventListener();

    },
    addEventListener:function(){
        $(this.root).find(".addBtn").click(this.addInfo.Apply(this));
        $(this.root).find(".editBtn").click(this.editInfo.Apply(this));
        $(this.root).find(".deleteBtn").click(this.deleteInfo.Apply(this));
        $(this.root).find(".searchBtn").click(this.searchInfo.Apply(this));
    },
    gridParam:{
                  page:{
                      curPage : 1,
                      pageSize : 10
                  },
                  multiselect : true,
                  url : PATH+'/${abc}/list.json',
                  grid_selector:"#${table.name}Grid",
                  pager_selector:"#${table.name}Grid-Pager",
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
                                  return getViewHtml(value,"${abc}List")+getEditHtml(value,"${abc}List")+getDelHtml(value,"${abc}List");
                              }
                          }
                    ],

                    onSelectRow: function(id){ //alert("单击选中"+id);

                    },
    },
    saveInfo:function(){
    },
    addInfo:function (){
        dialog.window('/${abc}/edit.htm',this.modal);
    },
    editInfo:function (id){
        dialog.window("/${abc}/edit.htm?id="+id,this.modal);
    },
    searchInfo:function (){
        var jso = changeForm2Jso(".app-search");
        this.mygrid.jqGrid("search",jso);
    },
    deleteInfo:function (id){
         //弹窗
         zconfirm("确定删除数据:"+id,"删除",function(){
            Ajax.post(PATH+"/${abc}/del.json?${table.pk.name}="+id,function(result){
                result=ajaxResultHandler(result);
                if(result.r==AJAX_SUCC){
                    dialog.alert("删除成功，数据："+id,function(index){
                    $("#grid").jqGrid("reloadGrid");
                    dialog.close(index);
                });
                }else {
                    dialog.error(result.msg,"提醒");
                }
            });
        });
    },
    viewInfo:function (id){
        dialog.window("/${abc}/view.htm?id="+id,this.modal);
    },
    search:function (){
        var jso= changeForm2Jso(".app-search");
        console.log(jso);
        this.mygrid.search(jso);
    },
    exportInfo:function (){
        var jso= changeForm2Jso(".app-search");
        Ajax.getJSON(PATH+"/${abc}/export.json",jso,function(data){
            if(data.r==AJAX_SUCC){
                window.location=PATH+"/"+data.data;
            }else{
                dialog.error(data.msg,"导出失败",null);
            }
        })
    },
    multiDelete:function (){
        //获取ids字符串
        var ids=this.mygrid.jqGrid("getGridParam","selarrrow");
        if(ids.length==0){
            dialog.alert("请勾选数据");
            return;
        }
        var data= this.mygrid.jqGrid("getGridParam","data");
        for(var i=0;i<ids.length;i++){
            ids[i]=data[ids[i]]["id"];
        }
        //弹窗
        dialog.confirm("确定删除数据:"+ids.join(","),function(){
            Ajax.post(PATH+"/${abc}/mdel.json?ids="+ids.join(","),function(result){
                result=ajaxResultHandler(result);
                if(result.r==AJAX_SUCC){
                    dialog.alert("删除成功，数据："+ids.join(","),function(index){
                    this.mygrid.jqGrid("reloadGrid");
                    dialog.close(index);
                });
                }else {
                    dialog.error(result.msg);
                }
            });
        });
    }
};
${abc}List.init()
</script>


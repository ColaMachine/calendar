
<div id="${Abc}List" class="rgt_body">
    <div class="body_title">| ${table.remark}</div>
        <div class="body_top" >
            <form class="form-inline app-search">
${searchhtml}
                <button type="button"  class="btn btn-default searchBtn">查询</button>
            </form>
        <div >
            <button class="btn addBtn" >新增</button>
            <button class="btn deleteBtn">删除</button>
            <button class="btn exportBtn" >导出</button>
        </div>
    </div>
    <table id="${table.name}Grid" class="grid"></table>
    <div id="${table.name}Grid-Pager" class="pager"></div>
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
        datatype: "json",
        viewrecords: true, sortorder: "desc", caption:"",
        rowNum:10,
        rowList:[10,20,30],
        multiselect : true,
        url : PATH+'/${abc}/list.json',
         autowidth:true,
        grid:"#${table.name}Grid",
        pager:"#${table.name}Grid-Pager",
        jsonReader:jsonReader,
        colNames : [
        <#list table.cols as col><#if col_index!=0>,</#if>"${col.remark}"</#list> , '操作' ],
        colModel : [
               <#list table.cols as col>
            {   name : '${col.name}',width : 80,
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
            {   name : 'operation',
                width : 150,
                formatter : function(value, grid, rows) {
                    return getViewHtml(rows.${table.pk.name},"${abc}List")+getEditHtml(rows.${table.pk.name},"${abc}List")+getDelHtml(rows.${table.pk.name},"${abc}List");
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

        this.mygrid.jqGrid("setGridParam", { search: true ,"postData":jso}).trigger("reloadGrid", [{ page: 1}]);  //重载JQGrid
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


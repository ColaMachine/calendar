<div id="${table.name}MapperList" class="rgt_body ">
    <div class="body_title">| ${table.remark}</div>
        <div class="body_top" >
            <form class="form-inline app-search">
${parentsearchhtml}
                <button type="button" class="btn btn-default searchBtn">查询</button>
            </form>
        <div >
            <button class="btn addBtn" >新增</button>
            <button class="btn deleteBtn" >删除</button>
            <button class="btn exportBtn" >导出</button>
             <button class="btn saveBtn" >保存</button>
        </div>
    </div>
     <div class="col-sm-6">
    <div id="${table.name}MapperGrid" class="grid"></div>
    <div id="${table.name}MapperGrid-pager" class="pager"></div>
    </div>
     <div class="col-sm-6">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
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
    newWindow:false,
    mygrid:null,
    treeObj:null,
    root:$("#${table.name}MapperList"),
    init:function(){
        this.mygrid = $("#${table.name}MapperList").find(".grid").jqGrid(this.gridParam);
        this.addEventListener();
        this.initTree();
    },
    initTree:function(){
    var that =this;
     Ajax.getJSON(PATH+"/${table.mapper.child?uncap_first}/listAll.json",null,function(result){
                if(result.r==AJAX_SUCC){
                    if(result.data){
                         that.treeObj=$.fn.zTree.init($(that.root).find("#treeDemo"), setting, result.data);
                    }
                }else{
                    zerror("获取信息失败"+data.msg,"错误",function(){

                    });
                }

            });
    },
    addEventListener:function(){
        var root = $("#${table.name}MapperList");
        $(root).find(".addBtn").click(this.addInfo.Apply(this));
        $(root).find(".editBtn").click(this.editInfo.Apply(this));
        $(root).find(".deleteBtn").click(this.deleteInfo.Apply(this));
         $(root).find(".saveBtn").click(this.saveInfo.Apply(this));
           $(root).find(".searchBtn").click(this.searchInfo.Apply(this));
    },
    gridParam:{
                  page:{
                      curPage : 1,
                      pageSize : 10
                  },
                  multiselect : true,
                  url : PATH+'/${table.mapper.parent?uncap_first}/list.json',
                  grid_selector:"#${table.name}MapperGrid",
                  pager_selector:"#${table.name}MapperGrid-Pager",
                  searchParams : {
                      name : ''
                  },
                  colNames : [
                   <#list parentTable.cols as col><#if col_index!=0>,</#if>"${col.remark}"</#list> , '操作' ],
                  colModel : [
                           <#list parentTable.cols as col>
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
                          } <#if col_index<parentTable.cols?size-1>,</#if>
                           </#list>
                          ,
                          {
                              name : 'id',
                              width : 150,
                              formatter : function(value, grid, rows) {
                                  return getViewHtml(value)+getEditHtml(value)+getDelHtml(value);
                              }
                          }
                    ],

                    onSelectRow: function(id){ //alert("单击选中"+id);
                    var that =this;
                  //  var treeObj = $.fn.zTree.getZTreeObj("tree");
                     var celldata =${abc}List.mygrid.jqGrid('getRowData',id)["${parentTable.pk.name}"];
                     //清空原来的选项
                    ${abc}List.treeObj.checkAllNodes(false);
                      Ajax.getJSON(PATH+"/${table.name?uncap_first}/listAll.json",{"${table.mapper.parentid}":celldata},function(result){
                           if(result.data){
                                          for(var i=0;i<result.data.length;i++){
                                              var node = ${abc}List.treeObj.getNodeByParam("id",result.data[i].${table.mapper.childid});
                                              ${abc}List.treeObj.checkNode(node,true,true);
                                          }
                                      }
                      })
                    },
    },
    saveInfo:function(){
        //首先查出父节点勾选信息
        var selarrrow =this.mygrid.jqGrid("getGridParam","selarrrow");
        if(selarrrow==null || selarrrow.length==0){
            selarrrow .push(this.mygrid.jqGrid('getGridParam','selrow'));
        }
        console.log(selarrrow);
        //再查出子节点勾选信息
         var childids=[];
      var treeObj= this.treeObj;
                         var checkedNodes= this.treeObj.getCheckedNodes(true);
                         for(var i=0;i<checkedNodes.length;i++){
                             var obj=checkedNodes[i];
                             if(!checkedNodes[i].isParent){
                                 childids.push(checkedNodes[i].id);
                             }
                         }
        console.log(selarrrow.join(","));
        var parentIdArray=new Array();
        for(var i=0;i<selarrrow.length;i++){
            parentIdArray.push( this.mygrid.jqGrid("getRowData",selarrrow[i])["id"]);
        }

        console.log(childids.join(","));
         console.log(parentIdArray.join(","));
        //进行保存

        Ajax.post(PATH+"/${abc}/msave.json",{"${table.mapper.parentid}s":parentIdArray.join(","),"${table.mapper.childid}s":childids.join(",")},function(result){
            if(result.r==AJAX_SUCC){
                dialog.alert("关联成功");
            }else{
                dialog.alert(result.msg);
            }
        })
    },
    addInfo:function (){
        showDialogue('/${abc}/edit.htm');
    },
    editInfo:function (id){
        showDialogue("/${abc}/edit.htm?id="+id);
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
                    zalert("删除成功，数据："+id,"删除",function(){
                    $("#grid").jqGrid("reloadGrid");
                });
                }else {
                    zerror(result.msg,"提醒",function(){});
                }
            });
        });
    },
    viewInfo:function (id){
        showDialogue("/${abc}/view.htm?id="+id);
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
                zerror(data.msg,"导出失败",null);
            }
        })
    },
    multiDelete:function (){
        //获取ids字符串
        var ids=this.mygrid.jqGrid("getGridParam","selarrrow");
        if(ids.length==0){
            zalert("请勾选数据","提示");
            return;
        }
        var data= this.mygrid.jqGrid("getGridParam","data");
        for(var i=0;i<ids.length;i++){
            ids[i]=data[ids[i]]["id"];
        }
        //弹窗
        zconfirm("确定删除数据:"+ids.join(","),"删除",function(){
            Ajax.post(PATH+"/${abc}/mdel.json?ids="+ids.join(","),function(result){
                result=ajaxResultHandler(result);
                if(result.r==AJAX_SUCC){
                    zalert("删除成功，数据："+ids.join(","),"删除",function(){
                    this.mygrid.jqGrid("reloadGrid");
                });
                }else {
                    zerror(result.msg,"提醒",function(){});
                }
            });
        });
    }
};
${abc}List.init()
</script>


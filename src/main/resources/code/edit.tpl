<#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">

 <div class="modal-content">
    <form id="editForm" class="form-horizontal" method="post" action="/${abc}/save.json" enctype="multipart/form-data">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title">${table.remark}编辑</h4>
        </div>
        <div class="modal-body">
            ${edithtml}
        </div>
        <div class="modal-footer">
            <button type="button" onclick="save()" class="btn btn-primary">保存</button>
            <button type="button" onclick="cancel()"  class="btn btn-default">取消</button>
        </div>
    </form>
</div><!-- /.modal-content -->
        
<script type="text/javascript">

$(document).ready(function() {
    $("#editForm").validate({
        rules: {
            ${jsrules}
        },
        messages:{
            ${jsmsg}
        }
    });
    //获取传入参数
    if(!StringUtil.isBlank(getParam("id"))){
        GetJSON("${abc}/view.json?id="+getParam("id"),function(data){
            if(data.r==AJAX_SUCC){
                fillJso2Form("#editForm",data.data);
            }else{
                zerror("获取信息失败"+data.msg,"错误",function(){
                    goPage("${abc}/list.htm");
                });
            }
            
        });
        
    }
    
});
    function save(){
           if (!$("#editForm").valid())
                    return;
                     var jso = changeForm2Jso("#editForm");
                     showWait();
                     Post(PATH+$("#editForm").attr("action"),jso,function(data){
                     hideWait();
                     if(data.r==0){
                        zalert(data.msg||"保存成功","提示",function(){
                            //goPage("${abc}/list.htm");
                             $("#mymodal").modal("toggle");
                             $("#grid").jqGrid("reloadGrid");
                        });
                     }else{
                        zerror(data.msg, "失败");
                     }
                     },'json');
    }
    function cancel(){
        $("#mymodal").modal("toggle");
        //goPage("${abc}/list.htm");
    }
</script>
<#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
<div class="rgt_body">
<div class="body_title">| ${table.remark}编辑</div>
<form id="editForm" class="form-horizontal" method="post" action="/${abc}/save.json" enctype="multipart/form-data">
 ${viewhtml}
   <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
     
       <button type="button" onclick="cancel()"  class="btn btn-default">返回</button>
    </div>
  </div>
</form>
</div>
</div>
<script type="text/javascript">

$(document).ready(function() {
    //获取传入参数
    if(!StringUtil.isBlank(getParam("id"))){
        $.getJSON("${abc}/view.json?id="+getParam("id"),function(data){
            if(data.r==AJAX_SUCC){
                fillJso2FormSpan("#editForm",data.data);
            }else{
                zerror("获取信息失败"+data.msg,"错误",function(){
                    goPage("${abc}/list.htm");
                });
            }
            
        });
        
    }
    
});
   
    function cancel(){
          goPage("${abc}/list.htm");
    }
</script>
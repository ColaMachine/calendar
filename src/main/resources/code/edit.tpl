<#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
<div class="rgt_body">
<div class="body_title">| ${table.remark}编辑</div>
<form class="form-horizontal">
<input type="hidden" id="${table.pk.name}" name="${table.pk.name}">
 <#list table.cols as col>
 <#if col.name!=table.pk.name>
 <div class="form-group">
    <label for="${col.name}" class="col-sm-2 control-label">${col.remark}:</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="${col.name}" placeholder="">
    </div>
 </div> 
 </#if>
  </#list>
   <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="button" class="btn btn-primary">保存</button>
       <button type="button" class="btn btn-default">取消</button>
    </div>
  </div>
</form>
</div>
</div>
<script type="text/javascript">
    function save(){
        
    }
    function cancel(){
        
    }
</script>
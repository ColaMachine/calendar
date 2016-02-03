<#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
<div class="rgt_body">
<div class="body_title">| ${table.remark}编辑</div>
<form id="editForm" class="form-horizontal">
 ${edithtml}
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

$().ready(function() {
 $("#editForm").validate({
      
    });
});
    function save(){
           if (!$("#editForm").valid())
                    return;
    }
    function cancel(){
        
    }
</script>
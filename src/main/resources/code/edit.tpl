<#assign abc="${table.name[0]?lower_case}${table.name[1..]}">
<#assign Abc="${table.name[0]?upper_case}${table.name[1..]}">
<div class="rgt_body">
<div class="body_title">| ${table.remark}编辑</div>
<form id="editForm" class="form-horizontal">
<input type="hidden" id="${table.pk.name}" name="${table.pk.name}">
 <#list table.cols as col>
 <#if col.name!=table.pk.name>
 <div class="form-group">
    <label for="${col.name}" class="col-sm-2 control-label">${col.remark}:</label>
    <div class="col-sm-10">
    
      <input type="text"  <#if col.type=='timestamp'> onClick="WdatePicker()" </#if>         
       class="form-control" id="${col.name}" placeholder="">
     
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

$().ready(function() {
 $("#signupForm").validate({
        rules: {
        
         <#list table.cols as col>
 <#if col.name!=table.pk.name>
  email: {
    required: true,
    email: true
   },
   col.name:{
    <#if col.nn==true> required: true, </#if> 
   },
			 <div class="form-group">
			    <label for="${col.name}" class="col-sm-2 control-label">${col.remark}:</label>
			    <div class="col-sm-10">
			    
			      <input type="text"  <#if col.type=='timestamp'> onClick="WdatePicker()" </#if>         
			       class="form-control" id="${col.name}" placeholder="">
			     
			    </div>
			 </div> 
 </#if>
  </#list>
   firstname: "required",
   email: {
    required: true,
    email: true
   },
   password: {
    required: true,
    minlength: 5
   },
   confirm_password: {
    required: true,
    minlength: 5,
    equalTo: "#password"
   }
  },
        messages: {
   firstname: "请输入姓名",
   email: {
    required: "请输入Email地址",
    email: "请输入正确的email地址"
   },
   password: {
    required: "请输入密码",
    minlength: jQuery.format("密码不能小于{0}个字 符")
   },
   confirm_password: {
    required: "请输入确认密码",
    minlength: "确认密码不能小于5个字符",
    equalTo: "两次输入密码不一致不一致"
   }
  }
    });
});
    function save(){
        
    }
    function cancel(){
        
    }
</script>
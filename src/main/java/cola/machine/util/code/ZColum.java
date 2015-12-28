/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package cola.machine.util.code;

public class ZColum {
private String name;
private boolean nn;
private boolean pk;
private String def;
public boolean isNn() {
    return nn;
}
public void setNn(boolean nn) {
    this.nn = nn;
}
public boolean isPk() {
    return pk;
}
public void setPk(boolean pk) {
    this.pk = pk;
}
public String getDef() {
    return def;
}
public void setDef(String def) {
    this.def = def;
}
private String type;
private String remark;
private String valid;
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getType() {
    return type;
}
public void setType(String type) {
    this.type = type;
}
public String getRemark() {
    return remark;
}
public void setRemark(String remark) {
    this.remark = remark;
}
public String getValid() {
    return valid;
}
public void setValid(String valid) {
    this.valid = valid;
}
/*
public String getJavaType(){
    return "long";
}*/
    /*
    name:"id",
    type:varchar(40),
    cn_name:"id",
    valid:"Length(min=1,max=40 ,message='图片名称不能超过40个')",*/
}

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.bean;


public class SmsBatch {
    /**id**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**总发送数据**/
    private Integer total;
    public Integer getTotal(){
        return total;
    }    public void setTotal(Integer total){
        this.total=total;
    }/**成功数量**/
    private Integer succ;
    public Integer getSucc(){
        return succ;
    }    public void setSucc(Integer succ){
        this.succ=succ;
    }/**失败数量**/
    private Integer fail;
    public Integer getFail(){
        return fail;
    }    public void setFail(Integer fail){
        this.fail=fail;
    }/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }/**手机号码**/
    private String phone;
    public String getPhone(){
        return phone;
    }    public void setPhone(String phone){
        this.phone=phone;
    }/**短信内容**/
    private String content;
    public String getContent(){
        return content;
    }    public void setContent(String content){
        this.content=content;
    }/**定时发送**/
    private Timestamp sendTime;
    public Timestamp getSendTime(){
        return sendTime;
    }    public void setSendTime(Timestamp sendTime){
        this.sendTime=sendTime;
    }
}

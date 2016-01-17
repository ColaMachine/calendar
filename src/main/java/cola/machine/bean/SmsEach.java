/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.bean;
import java.sql.Timestamp;

public class SmsEach {
    /**id**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**批次号**/
    private Integer batchId;
    public Integer getBatchId(){
        return batchId;
    }    public void setBatchId(Integer batchId){
        this.batchId=batchId;
    }/**手机号码**/
    private String phone;
    public String getPhone(){
        return phone;
    }    public void setPhone(String phone){
        this.phone=phone;
    }/**发送时间**/
    private Timestamp sendTime;
    public Timestamp getSendTime(){
        return sendTime;
    }    public void setSendTime(Timestamp sendTime){
        this.sendTime=sendTime;
    }/**发送状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }/**失败原因**/
    private String reason;
    public String getReason(){
        return reason;
    }    public void setReason(String reason){
        this.reason=reason;
    }
}

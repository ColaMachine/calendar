/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Configuration {
    /**名称**/
    private String key;
    public String getKey(){
        return key;
    }    public void setKey(String key){
        this.key=key;
    }/**对应值**/
    private String valuate;
    public String getValuate(){
        return valuate;
    }    public void setValuate(String valuate){
        this.valuate=valuate;
    }/**操作者**/
    private String operator;
    public String getOperator(){
        return operator;
    }    public void setOperator(String operator){
        this.operator=operator;
    }/**创建时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
}

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

public class SysConfig {
    /**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**名称**/
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
    }
}
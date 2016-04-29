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

public class Collect {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**用户ID**/
    private Long uid;
    public Long getUid(){
        return uid;
    }    public void setUid(Long uid){
        this.uid=uid;
    }/**视频id**/
    private Long vid;
    public Long getVid(){
        return vid;
    }    public void setVid(Long vid){
        this.vid=vid;
    }/**视频类型**/
    private String vtype;
    public String getVtype(){
        return vtype;
    }    public void setVtype(String vtype){
        this.vtype=vtype;
    }/**创建时间**/
    private Timestamp createtime;
    public Timestamp getCreatetime(){
        return createtime;
    }    public void setCreatetime(Timestamp createtime){
        this.createtime=createtime;
    }/**更新时间**/
    private Timestamp updatetime;
    public Timestamp getUpdatetime(){
        return updatetime;
    }    public void setUpdatetime(Timestamp updatetime){
        this.updatetime=updatetime;
    }
}

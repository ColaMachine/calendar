/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.bean;
import java.sql.Timestamp;

public class ZUser {
    /**id**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**邮箱**/
    private String email;
    public String getEmail(){
        return email;
    }    public void setEmail(String email){
        this.email=email;
    }/**密码**/
    private String pwd;
    public String getPwd(){
        return pwd;
    }    public void setPwd(String pwd){
        this.pwd=pwd;
    }/**昵称**/
    private String nick;
    public String getNick(){
        return nick;
    }    public void setNick(String nick){
        this.nick=nick;
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
    }/**注册时间**/
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return createTime;
    }    public void setCreateTime(Timestamp createTime){
        this.createTime=createTime;
    }
}

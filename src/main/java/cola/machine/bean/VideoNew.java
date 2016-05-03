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

public class VideoNew {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**视频名称**/
    private String vname;
    public String getVname(){
        return vname;
    }    public void setVname(String vname){
        this.vname=vname;
    }/**主播名称**/
    private String zbname;
    public String getZbname(){
        return zbname;
    }    public void setZbname(String zbname){
        this.zbname=zbname;
    }/**视频类型**/
    private Integer viewnum;
    public Integer getViewnum(){
        return viewnum;
    }    public void setViewnum(Integer viewnum){
        this.viewnum=viewnum;
    }/**视屏url**/
    private String url;
    public String getUrl(){
        return url;
    }    public void setUrl(String url){
        this.url=url;
    }/**视屏url**/
    private String type;
    public String getType(){
        return type;
    }    public void setType(String type){
        this.type=type;
    }/**视频图片**/
    private String img;
    public String getImg(){
        return img;
    }    public void setImg(String img){
        this.img=img;
    }/**视频主键**/
    private String vkey;
    public String getVkey(){
        return vkey;
    }    public void setVkey(String vkey){
        this.vkey=vkey;
    }
}

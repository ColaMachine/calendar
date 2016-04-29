/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package cola.machine.action;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import cola.machine.service.VideoNewService;
import cola.machine.bean.VideoNew;
import cola.machine.util.ResultUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;
import core.util.RequestUtil;
import core.action.ResultDTO;
import cola.machine.util.DateUtil;
@Controller
@RequestMapping("/videoNew")
public class VideoNewController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(VideoNewController.class);
    /** 权限service **/
    @Autowired
    private VideoNewService videoNewService;
    
    /**
     * 说明: 跳转到角色列表页面
     * 
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/VideoNewList.html";
    }

    @RequestMapping(value = "/listMapper.htm", method = RequestMethod.GET)
    public String listMapper() {
        return "/static/html/VideoNewListMapper.html";
    }

    /**
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object list(HttpServletRequest request) {
        Page page = RequestUtil.getPage(request);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }
        
        HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String vname = request.getParameter("vname");
        if(!StringUtil.isBlank(vname)){
            params.put("vname",vname);
        }
        String vnameLike = request.getParameter("vnameLike");
        if(!StringUtil.isBlank(vnameLike)){
            params.put("vnameLike",vnameLike);
        }
        String zbname = request.getParameter("zbname");
        if(!StringUtil.isBlank(zbname)){
            params.put("zbname",zbname);
        }
        String zbnameLike = request.getParameter("zbnameLike");
        if(!StringUtil.isBlank(zbnameLike)){
            params.put("zbnameLike",zbnameLike);
        }
        String viewnum = request.getParameter("viewnum");
        if(!StringUtil.isBlank(viewnum)){
            params.put("viewnum",viewnum);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }

        params.put("page",page);
        List<VideoNew> videoNews = videoNewService.listByParams4Page(params);
        return ResultUtil.getResult(videoNews, page);
    }
    
   /**
    * 说明:ajax请求角色信息 无分页版本
    * @return Object
    * @author dozen.zhang
    * @date 2015年11月15日下午12:31:55
    */
    @RequestMapping(value = "/listAll.json")
    @ResponseBody
    public Object listAll(HttpServletRequest request) {
                HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String vname = request.getParameter("vname");
        if(!StringUtil.isBlank(vname)){
            params.put("vname",vname);
        }
        String vnameLike = request.getParameter("vnameLike");
        if(!StringUtil.isBlank(vnameLike)){
            params.put("vnameLike",vnameLike);
        }
        String zbname = request.getParameter("zbname");
        if(!StringUtil.isBlank(zbname)){
            params.put("zbname",zbname);
        }
        String zbnameLike = request.getParameter("zbnameLike");
        if(!StringUtil.isBlank(zbnameLike)){
            params.put("zbnameLike",zbnameLike);
        }
        String viewnum = request.getParameter("viewnum");
        if(!StringUtil.isBlank(viewnum)){
            params.put("viewnum",viewnum);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }

        List<VideoNew> videoNews = videoNewService.listByParams(params);
        return ResultUtil.getResult(videoNews);
    }
    
    /**
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/VideoNewEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/VideoNewView.html";
    }
   
    @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
            String id = request.getParameter("id");
        HashMap<String,Object> result =new HashMap<String,Object>();
        if(!StringUtil.isBlank(id)){
            VideoNew bean = videoNewService.selectByPrimaryKey(Long.valueOf(id));
            result.put("bean", bean);
        }
        return this.getResult(result);

      /*  String id = request.getParameter("id");
        VideoNew bean = videoNewService.selectByPrimaryKey(Long.valueOf(id));
        HashMap<String,Object> result =new HashMap<String,Object>();
        result.put("bean", bean);
        return this.getResult(bean);*/
    }

    
    /**
     * 说明:保存角色信息
     * 
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        VideoNew videoNew =new  VideoNew();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            videoNew.setId(Long.valueOf(id)) ;
        }
        
        String vname = request.getParameter("vname");
        if(!StringUtil.isBlank(vname)){
            videoNew.setVname(String.valueOf(vname)) ;
        }
        
        String zbname = request.getParameter("zbname");
        if(!StringUtil.isBlank(zbname)){
            videoNew.setZbname(String.valueOf(zbname)) ;
        }
        
        String viewnum = request.getParameter("viewnum");
        if(!StringUtil.isBlank(viewnum)){
            videoNew.setViewnum(Integer.valueOf(viewnum)) ;
        }
        
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            videoNew.setUrl(String.valueOf(url)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            videoNew.setId(Long.valueOf(id));
        }
        String vname = request.getParameter("vname");
        if(!StringUtil.isBlank(vname)){
            videoNew.setVname(vname);
        }
        String zbname = request.getParameter("zbname");
        if(!StringUtil.isBlank(zbname)){
            videoNew.setZbname(zbname);
        }
        String viewnum = request.getParameter("viewnum");
        if(!StringUtil.isBlank(viewnum)){
            videoNew.setViewnum(Integer.valueOf(viewnum));
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            videoNew.setUrl(url);
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "主键",  new Rule[]{new Digits(15,0)});
        vu.add("vname", vname, "视频名称",  new Rule[]{new Length(50),new NotEmpty()});
        vu.add("zbname", zbname, "主播名称",  new Rule[]{new Length(20),new NotEmpty()});
        vu.add("viewnum", viewnum, "视频类型",  new Rule[]{new Digits(10,0)});
        vu.add("url", url, "视屏url",  new Rule[]{new Length(300)});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return videoNewService.save(videoNew);
       
    }

    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Long id = Long.valueOf(idStr);
        videoNewService.delete(id);
        return this.getResult(SUCC);
    }
     /**
     * 多行删除
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public Object multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("ids");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        Long idAry[]=new Long[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String id = idStrAry[i];
                    vu.add("id", id, "主键",  new Rule[]{});

            try{
                validStr=vu.validateString();
            }catch(Exception e){
                e.printStackTrace();
                validStr="验证程序异常";
                return ResultUtil.getResult(302,validStr);
            }
            
            if(StringUtil.isNotEmpty(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=Long.valueOf(idStrAry[i]);
        }
       return  videoNewService.multilDelete(idAry);
    }

    /**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/export.json")
    @ResponseBody   
    public Object exportExcel(HttpServletRequest request){
               HashMap<String,Object> params= new HashMap<String,Object>();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            params.put("id",id);
        }
        String vname = request.getParameter("vname");
        if(!StringUtil.isBlank(vname)){
            params.put("vname",vname);
        }
        String vnameLike = request.getParameter("vnameLike");
        if(!StringUtil.isBlank(vnameLike)){
            params.put("vnameLike",vnameLike);
        }
        String zbname = request.getParameter("zbname");
        if(!StringUtil.isBlank(zbname)){
            params.put("zbname",zbname);
        }
        String zbnameLike = request.getParameter("zbnameLike");
        if(!StringUtil.isBlank(zbnameLike)){
            params.put("zbnameLike",zbnameLike);
        }
        String viewnum = request.getParameter("viewnum");
        if(!StringUtil.isBlank(viewnum)){
            params.put("viewnum",viewnum);
        }
        String url = request.getParameter("url");
        if(!StringUtil.isBlank(url)){
            params.put("url",url);
        }
        String urlLike = request.getParameter("urlLike");
        if(!StringUtil.isBlank(urlLike)){
            params.put("urlLike",urlLike);
        }

        // 查询list集合
        List<VideoNew> list =videoNewService.listByParams(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String folder = request.getSession().getServletContext()
                .getRealPath("/")
                + "xlstmp";
        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator
                + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS")
                + ".xlsx";
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("id", "主键");
        colTitle.put("vname", "视频名称");
        colTitle.put("zbname", "主播名称");
        colTitle.put("viewnum", "视频类型");
        colTitle.put("url", "视屏url");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            VideoNew sm = list.get(i);
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("id",  list.get(i).getId());
            map.put("vname",  list.get(i).getVname());
            map.put("zbname",  list.get(i).getZbname());
            map.put("viewnum",  list.get(i).getViewnum());
            map.put("url",  list.get(i).getUrl());
            finalList.add(map);
        }
        try {
            if (cola.machine.util.ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC,fileName,"导出成功");
            }
            /*
             * return new ResponseEntity<byte[]>(
             * FileUtils.readFileToByteArray(new File(fileName)), headers,
             * HttpStatus.CREATED);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");
    
    }
    @RequestMapping(value = "/import.json")
    public void importExcel(){
        
    }
}

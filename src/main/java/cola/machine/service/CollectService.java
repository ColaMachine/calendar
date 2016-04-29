/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */

package cola.machine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cola.machine.bean.Collect;
import cola.machine.dao.CollectMapper;
import cola.machine.util.CacheUtil;
import cola.machine.util.ResultUtil;
import cola.machine.util.UUIDUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.StringUtil;
import core.page.Page;
import java.util.StringTokenizer;
import cola.machine.bean.Collect;
import cola.machine.dao.CollectMapper;

import cola.machine.bean.SysUser;
import cola.machine.bean.VideoHot;
import cola.machine.dao.SysUserMapper;
import cola.machine.dao.VideoHotMapper;

import core.action.ResultDTO;

@Service("collectService")
public class CollectService extends BaseService {
    private static final Logger logger = LoggerFactory
            .getLogger(CollectService.class);
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private VideoHotMapper videoHotMapper;
    /**
     * 说明:list by page and params根据参数返回列表
     * @return List<HashMap>
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public List<Collect> listByParams4Page(HashMap params) {
        return collectMapper.listByParams4Page(params);
    }
    public List<Collect> listByParams(HashMap params) {
        return collectMapper.listByParams(params);
    }

     /**
     * 说明:countByParams 根据参数提取个数
     * @return int
     * @author dozen.zhang
     * @date 2015年11月15日下午12:36:24
     */
    public int countByParams(HashMap params) {
           return collectMapper.countByParams(params);
    }

    /*
     * 说明:
     * @param Collect
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:54
     */
    public ResultDTO save(Collect collect) {
        // 进行字段验证
       ValidateUtil<Collect> vu = new ValidateUtil<Collect>();
        ResultDTO result = vu.valid(collect);
        if (result.getR() != 1) {
            return result;
        }
         //逻辑业务判断判断
       //判断是否有uq字段
       
       //判断是更新还是插入
        if (collect.getId()==null) {
               
            collectMapper.insert(collect);
        } else {
             collectMapper.updateByPrimaryKey(collect);
        }
        return ResultUtil.getSuccResult();
    }
    /**
    * 说明:根据主键删除数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public void delete(Long  id){
        collectMapper.deleteByPrimaryKey(id);
    }   
    /**
    * 说明:根据主键获取数据
    * description:delete by key
    * @param id
    * @return void
    * @author dozen.zhang
    * @date 2015年12月27日下午10:56:38
    */
    public Collect selectByPrimaryKey(Long id){
       return collectMapper.selectByPrimaryKey(id);
    }
    /**多id删除
     * @param idAry
     * @return
     * @author dozen.zhang
     */
    public ResultDTO multilDelete(Long[] idAry) {
        for(int i=0;i<idAry.length;i++){
            collectMapper.deleteByPrimaryKey(idAry[i]);
        }
        return ResultUtil.getSuccResult();
    }
     /**
         * 多项关联保存
         * @param uids
         * @param rids
         * @return
         */
        public ResultDTO msave(String uids,String vids) {
            if(StringUtil.isBlank(uids)){
                return ResultUtil.getResult(101,"参数错误");
            }

            String[] uidAry= uids.split(",");
            String[] vidAry=vids.split(",");
            Long[] uidAryReal =new  Long[uidAry.length];
            Long[] vidAryReal =new  Long[vidAry.length];
            for(int i=0;i<uidAry.length;i++){
                if(!StringUtil.checkNumeric(uidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                uidAryReal[i]=Long.valueOf(uidAry[i]);
            }
            if(StringUtil.isBlank(vids)){
                vidAryReal=null;
                 vidAry=null;
            }
            if(vidAry!=null)
            for(int i=0;i<vidAry.length;i++){
                if(!StringUtil.checkNumeric(vidAry[i])){
                    return ResultUtil.getResult(101,"参数错误");
                }
                vidAryReal[i]=Long.valueOf(vidAry[i]);
            }
            //验证父亲id 正确性 是否存在
             if(uidAryReal!=null)
            for(int i=0;i< uidAryReal.length;i++){
                //
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(uidAryReal[i]);
                if(sysUser==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
                //查询的数据不存在
            }
             if(vidAryReal!=null)
            for(int i=0;i<vidAryReal.length;i++){
                 VideoHot videoHot = videoHotMapper.selectByPrimaryKey(vidAryReal[i]);
                //查询的数据不存在
                if(videoHot==null ){
                    return ResultUtil.getResult(101,"数据不存在");
                }
            }
             HashMap params =new HashMap();
            //验证子id 正确性 是否存在
             if(vidAryReal!=null)
            for(int i=0;i<uidAryReal.length;i++){
                for(int j=0;j<vidAryReal.length;j++){
                   Collect collect =new  Collect();
                    Long uid =uidAryReal[i];
                    Long vid =vidAryReal[j];
                    //查找是否已经有关联数据了

                    params.put("vid",vid);
                    params.put("uid",uid);
                    int count = collectMapper.countByParams(params);
                    if(count>0)continue;
                    collect.setVid(vid);
                    collect.setUid(uid);
                    collectMapper.insert(collect);
                }
            }
            //删除多余的数据
            params.clear();
            params.put("vids",vidAryReal);
            params.put("uids",uidAryReal);
            collectMapper.deleteExtra(params);
            //delete from SysUserRole where uid in (1,2,3,4,5) and rid not in(1,2,3)
            return ResultUtil.getSuccResult();
        }
}

package cola.machine.util;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.core.msg.ErrorMessage;
import core.action.ResultDTO;

/**
 * 版权所有：bean验证工具类
 * 项目名称:kaqm
 * 创建者: 宋展辉
 * 创建日期: 2015年7月10日
 * 文件说明: 
 */
public class ValidateUtil<T> {
    
    /**
     * 
     */
    private static final Logger log = LoggerFactory.getLogger(ValidateUtil.class);
    /**
     * @param object 对象
     * @return  ValidateResult
     */
  /*  public ValidateResult valid (T object){
        ValidateResult vr = new ValidateResult();
        vr.setStatus(true);
        //bean 验证
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        java.util.Set<ConstraintViolation<T>> constraintViolations = validator
                .validate(object);
        String errormsg ="";
        Map<String,String> errormap =vr.getErrorMap();
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                //getMessage的内容为属性上配置的message，getPropertyPath的内容为属性名
                //因为在处理的过程中同一个属性可能会报出多个错误，因此还是使用message名作为key来处理错误集合
                errormap.put(constraintViolation.getMessage(),constraintViolation.getPropertyPath().toString() );
                errormsg+=constraintViolation.getMessage()+";";
                log.info("新增"+object.getClass().getName()+"表单验证未通过:"+errormsg);
            }
            vr.setStatus(false);
            vr.setMsg(errormsg);
        }
        return vr;
    }*/
    
    
   
    /**
     * 说明:
     * @param object
     * @return
     * @return ResultDTO
     * @author dozen.zhang
     * @date 2015年12月12日下午4:28:52
     */
    public ResultDTO valid (T object){
        ResultDTO result =new ResultDTO();
        result.setR(1);
        //bean 验证
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        java.util.Set<ConstraintViolation<T>> constraintViolations = validator
                .validate(object);
        String errormsg ="";
        Map<String,String> errormap =new HashMap();;
        if (!constraintViolations.isEmpty()) {
            
            result.setR(300);
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                //getMessage的内容为属性上配置的message，getPropertyPath的内容为属性名
                //因为在处理的过程中同一个属性可能会报出多个错误，因此还是使用message名作为key来处理错误集合
                errormap.put(constraintViolation.getPropertyPath().toString(),
                        ErrorMessage.getErrorMsg(constraintViolation
                                .getMessage()));
                log.info("新增"+object.getClass().getName()+"表单验证未通过:"+errormsg);
            }
            result.setData(errormap);
            result.setMsg("参数校验失败");
        }
        return result;
    }
}

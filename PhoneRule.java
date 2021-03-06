/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年2月6日
 * 文件说明: 
 */
package cola.machine.util.rules;


public class PhoneRule extends Rule {
    
    public PhoneRule() {
        
    }
    
    @Override
    public boolean valid() throws Exception {
        if(this.getValue() == null || this.getValue().equals("")){
            return true;
        }else{
            if (StringUtil.isPhone(this.getValue())) {
                return true;
            }
            else {
                this.setMessage("err.param.format");
                return false;
            }
        }
    }



}

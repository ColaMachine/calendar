package cola.machine.util.log;

public enum ServiceMsg {


    /**
     *类名_方法名
     */
    MERCHANT_QUERYCOUNT("统计商户总数"),
    USER_NOT_FOUND("查无此用户"),
    ACCOUNT_FORMAT_ERR("账号格式错误"),
    VALIDCODE_MATCH_ERR("验证码匹配错误"),
    VALIDCODE_USED("验证码已使用"),
    MERCHANT_QUERYPACKAGELIST("查询套餐信息");

    /**
     *方法名
     */
    private String serviceName;
    /**
     * 构造函数
     * @param context
     */
    private ServiceMsg(String context){
        this.serviceName = context;
    }

    public String toString(){
        return this.serviceName;
    }
}

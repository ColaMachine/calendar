package cola.machine.util.log;

public enum ServiceMsg {


    /**
     *类名_方法名
     */

    SEND_FAIL("信息发送失败"),
    SMS_SEND_FAIL("短信发送失败"),
    SEND_TOO_MUCH_TIMES("发送次数过多"),
    VALIDCODE_REQUEST_FAST( "验证码刷新过快"),
    PHONE_FORMAT_ERR("手机格式不正确"),
    PHONE_NOT_EMPTY("手机号码不能为空"),
    EMAIL_NOT_EMPTY("邮箱号码不能为空"),
    EMAIL_FORMAT_ERR("邮箱格式不正确"),
    MERCHANT_QUERYCOUNT("统计商户总数"),
    USER_NOT_FOUND("查无此用户"),
    ACCOUNT_FORMAT_ERR("账号格式错误"),
    VALIDCODE_MATCH_ERR("验证码匹配错误"),
    VALIDCODE_USED("验证码已使用"),
    MERCHANT_QUERYPACKAGELIST("查询套餐信息"),
    SERVICECODE_ERR("系统代号不正确"),
    UNKNOWN("系统繁忙");

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

package com.lovetotravel.user.result;

public class CodeMsg {

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问高峰期，请稍等！");
    //登录模块 5002XX
    public static CodeMsg USER_EXIST = new CodeMsg(500201, "用户已存在");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500202, "用户不存在");
    public static CodeMsg CODE_ERROR = new CodeMsg(500203, "验证码错误");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500204, "密码错误");
    public static CodeMsg EMAIL_EMPTY = new CodeMsg(500205, "邮箱为空");
    public static CodeMsg FOLLOW_ERROR = new CodeMsg(500206, "您不能关注自己");

    //勋章模块 5003XX
    public static CodeMsg MEDAL_NOT_EXIST = new CodeMsg(500301, "勋章不存在");
    public static CodeMsg EXPERIENCE_LACK = new CodeMsg(500302, "经验值不足");


    private int code;
    private String msg;


    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 返回带参数的错误码
     *
     * @param args
     * @return
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }


}

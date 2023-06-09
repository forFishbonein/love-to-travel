package com.lovetotravel.travel.result;

public class CodeMsg {

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问高峰期，请稍等！");

    //用户模块 5002XX
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500202, "用户不存在");
    public static CodeMsg EMAIL_EMPTY = new CodeMsg(500205, "邮箱为空");


    //游记模块 5004XX
    public static CodeMsg NOTE_NOT_EXIST = new CodeMsg(500401, "游记不存在");
    public static CodeMsg QUERY_EMPTY = new CodeMsg(500402, "内容为空");
    public static CodeMsg CITY_NOT_EXIST = new CodeMsg(500403, "城市不存在");


    //行程模块 5005XX

    public static CodeMsg PLAN_NOT_EXIST = new CodeMsg(500501, "行程不存在");

    //组队模块 5006XX
    public static CodeMsg TEAM_NOT_EXIST = new CodeMsg(500601, "队伍不存在");


    //景区模块 5007XX
    public static CodeMsg SCENERY_NOT_EXIST = new CodeMsg(500701, "景区不存在");

    //评论模块 5008XX
    public static CodeMsg COMMENT_NOT_EXIST = new CodeMsg(500801, "评论不存在");

    //产品模块 5009XX
    public static CodeMsg PRODUCT_NOT_EXIST = new CodeMsg(500901, "产品不存在");


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

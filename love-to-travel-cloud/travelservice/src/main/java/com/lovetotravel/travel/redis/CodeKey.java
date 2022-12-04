package com.lovetotravel.travel.redis;

public class CodeKey extends BasePrefix {

    //code存在时间
    //5分钟
    public static final int CODE_EXPIRE = 60 * 5;

    //缓存的前缀
    public static final String CODE_PREFIX = "code";
    /**
     * 需要缓存的字段
     */
    public static CodeKey code = new CodeKey(CODE_EXPIRE, CODE_PREFIX);

    public CodeKey(String prefix) {
        super(prefix);
    }

    public CodeKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}

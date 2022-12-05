package com.lovetotravel.travel.exception;


import com.lovetotravel.travel.result.CodeMsg;

/**
 * Created by jiangyunxiong on 2018/5/22.
 * <p>
 * 自定义全局异常类
 */
public class GlobalException extends RuntimeException {

    private static final long servialVersionUID = 1L;

    private final CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}

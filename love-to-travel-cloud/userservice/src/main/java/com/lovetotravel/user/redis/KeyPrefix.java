package com.lovetotravel.user.redis;


public interface KeyPrefix {

    /**
     * 有效期
     *
     * @return
     */
    int expireSeconds();

    /**
     * 前缀
     *
     * @return
     */
    String getPrefix();
}

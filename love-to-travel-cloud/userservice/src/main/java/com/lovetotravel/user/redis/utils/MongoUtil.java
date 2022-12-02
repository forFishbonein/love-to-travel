package com.lovetotravel.user.redis.utils;

import java.util.Random;

public class MongoUtil {

    public static Integer getMongoId() {
        Integer id = null;
        id = new Random().nextInt(999999);//生成随机数，最大为999999
        return id;
    }
}

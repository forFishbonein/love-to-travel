package com.lovetotravel.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserserviceApplicationTests {

    @Test
    void contextLoads() {
        String md5Password = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(md5Password);
    }

}

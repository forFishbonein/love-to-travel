package com.lovetotravel.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
        System.out.println("------userservice启动成功------");
        System.out.println("------knife4j接口文档：http://localhost:8081/doc.html------");
    }

}

package com.lovetotravel.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelserviceApplication.class, args);
        System.out.println("------travelservice启动成功------");
        System.out.println("------knife4j接口文档：http://localhost:8082/doc.html");
    }

}

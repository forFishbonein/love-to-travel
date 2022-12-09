package com.lovetotravel.travel;

import com.lovetotravel.feign.clients.UserClient;
import com.lovetotravel.feign.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = UserClient.class,defaultConfiguration = DefaultFeignConfiguration.class)
public class TravelserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelserviceApplication.class, args);
        System.out.println("------travelservice启动成功------");
        System.out.println("------knife4j接口文档：http://localhost:8082/doc.html");
    }

}

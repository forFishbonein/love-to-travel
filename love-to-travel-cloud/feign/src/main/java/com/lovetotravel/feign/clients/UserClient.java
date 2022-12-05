package com.lovetotravel.feign.clients;

import com.lovetotravel.feign.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userservice")
public interface UserClient {

    @GetMapping("/user/{id}")
    User getById(@PathVariable("id") Long id);

}

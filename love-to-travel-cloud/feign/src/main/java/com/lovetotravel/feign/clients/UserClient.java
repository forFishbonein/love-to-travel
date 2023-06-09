package com.lovetotravel.feign.clients;

import com.lovetotravel.feign.entity.Result;
import com.lovetotravel.feign.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "localhost:8081")
public interface UserClient {

    @GetMapping("/user/{id}")
    Result<User> getById(@PathVariable("id") Long id);

    @GetMapping("/user/email/{email}")
    User getByEmail(@PathVariable("email") String email);


}

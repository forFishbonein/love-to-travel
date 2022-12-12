package com.lovetotravel.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lovetotravel.user.entity.User;
import com.lovetotravel.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserserviceApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println("----- selectPage method test ------");
        //分页参数
        Page<User> page = Page.of(1,2);

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getDeleted,"0");
        userMapper.selectPage(page,queryWrapper);
        page.getRecords().forEach(System.out::println);
    }

}

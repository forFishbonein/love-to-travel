package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public interface UserService {
    List<User> findUserAll();

    User findUserByID(Integer id);

    Integer insertUser(User user);

    Integer updateUser(User user);

    Integer deleteUser(Integer id);

}

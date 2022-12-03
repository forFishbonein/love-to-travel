package com.example.demo.mapper;

import com.example.demo.entity.Login;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LoginMapper {
    //查找所有用户登录信息
    @Select("select * from login order by id")
    List<Login> findLoginAll();

    //根据ID查找用户登录信息
    @Select("select * from login where id=#{id}")
    Login findLoginByID(Integer id);

    //用户登录信息添加
    @Insert("insert into login(id,email,password,status,deleted,update_time)" +
            "values(#{id},#{email},#{password},#{status},#{deleted},#{update_time})")
    Integer insertLogin(Login login);

    //用户登录信息改变
    @Update("update login set email=#{email},password=#{password},status=#{status},deleted=#{deleted},update_time=#{update_time},create_time=#{create_time}" +
            " where id=#{id}")
    Integer updateLogin(Login login);

    //根据ID删除用户登录信息
    @Delete("delete from login where id=#{id}")
    Integer deleteLogin(Integer id);

}

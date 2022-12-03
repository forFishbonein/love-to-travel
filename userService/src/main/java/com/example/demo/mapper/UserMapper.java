package com.example.demo.mapper;

import com.example.demo.entity.Login;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user order by id")
    List<User> findUserAll();

    @Select("select * from user where id=#{id}")
    User findUserByID(Integer id);

    @Insert("insert into user(id,email,url,name,grade,experience,tele,birthday,post,profession,signature,gender,address,visits,status)" +
            "values(#{id},#{email},#{url},#{name},#{grade},#{experience},#{tele},#{birthday},#{post},#{profession},#{signature},#{gender},#{address},#{visits},#{status})")
    Integer insertUser(User user);

    @Update("update user set email=#{email},url=#{url},name=#{name},grade=#{grade},experience=#{experience},experience=#{experience},tele=#{tele}," +
            "birthday=#{birthday},post=#{post},profession=#{profession},signature=#{signature},gender=#{gender},gender=#{gender},address=#{address},visits=#{visits},status=#{status} where id=#{id}")
    Integer updateUser(User user);

    @Delete("delete from user where id=#{id}")
    Integer deleteUser(Integer id);

}

package com.example.demo.mapper;

import com.example.demo.entity.Login;
import com.example.demo.entity.Medal;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MedalMapper {

    @Select("select * from medal order by id")
    List<Medal> findMedalAll();

    @Select("select * from medal where id=#{id}")
    Medal findMedalByID(Integer id);

    @Insert("insert into medal(id,name,experience,describe,url,deleted,update_time)" +
            "values(#{id},#{name},#{experience},#{describe},#{url},#{deleted},#{update_time})")
    Integer insertMedal(Medal medal);

    @Update("update medal set name=#{name},experience=#{experience},describe=#{describe},url=#{url}," +
            "deleted=#{deleted},create_time=#{create_time},update_time=#{update_time} where id=#{id}")
    Integer updateMedal(Medal medal);

    @Delete("delete from medal where id=#{id}")
    Integer deleteMedal(Integer id);



}

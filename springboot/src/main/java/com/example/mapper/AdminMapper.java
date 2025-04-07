package com.example.mapper;

import com.example.model.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {

    List<Admin> selectAll(String username, String name, List<Integer> idsArr);

    void insert(Admin admin);

    @Select("select * from `admin` where username = #{username}")
    Admin selectByUsername(String username);

    void updateById(Admin admin);

    @Delete("delete from `admin` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `admin` where id = #{id}")
    Admin selectById(String id);
}
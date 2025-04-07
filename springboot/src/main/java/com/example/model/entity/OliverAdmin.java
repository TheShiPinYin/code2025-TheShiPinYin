package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author OliverKim
 * @since 2025-04-07
 */

/**
 * entity只包含自己的信息，不包含任何其它多余的东西
 * 用了mybatis-plus, TableName用于标记表明（数据库里的名字）, TableId标记主键, type = IdType.AUTO表示是自增主键
 */

@Data
@Builder
@TableName("admin")
public class OliverAdmin {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String role;
    private String avatar;
}
